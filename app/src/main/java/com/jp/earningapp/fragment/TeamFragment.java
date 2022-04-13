package com.jp.earningapp.fragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.gson.Gson;
import com.jp.earningapp.R;
import com.jp.earningapp.adapter.TeamAdapter;
import com.jp.earningapp.helper.ApiConfig;
import com.jp.earningapp.helper.Constant;
import com.jp.earningapp.helper.Session;
import com.jp.earningapp.model.Team;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeamFragment extends Fragment {

    TextView code_txt;
    Session session;
    public static RecyclerView recyclerView;
    public static TeamAdapter teamAdapter;
    View rootview;
    Activity activity;
    Chip Level1,Level2,Level3;
    ArrayList<Team> teams = new ArrayList<>();


    public TeamFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_team, container, false);
        activity = getActivity();
        recyclerView = rootview.findViewById(R.id.recyclerView);

        code_txt = rootview.findViewById(R.id.code_txt);
        Level1 = rootview.findViewById(R.id.level1);
        Level2 = rootview.findViewById(R.id.level2);
        Level3 = rootview.findViewById(R.id.level3);
        session = new Session(getActivity());
        teamAdapter = new TeamAdapter(activity, teams);


        code_txt.setText(session.getData(Constant.MY_REFER_CODE));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);

        code_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("TextView", code_txt.getText().toString());
                clipboard.setPrimaryClip(clip);

                clip.getDescription();

                Toast.makeText(getActivity(), "Copied", Toast.LENGTH_SHORT).show();
            }
        });
        Level1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    teamList("1");
                }
            }
        });
        Level2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    teamList("2");
                }
            }
        });
        Level3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    teamList("3");
                }
            }
        });
        teamList("1");



        return rootview;
    }

    private void teamList(String s)
    {
        teams.clear();
        teamAdapter.notifyDataSetChanged();
        Map<String, String> params = new HashMap<>();
        params.put(Constant.LEVEL, s);
        params.put(Constant.REFERRAL, session.getData(Constant.MY_REFER_CODE));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1 != null) {
                                Team group = g.fromJson(jsonObject1.toString(), Team.class);
                                teams.add(group);
                            } else {
                                break;
                            }
                        }


                        recyclerView.setAdapter(teamAdapter);
                    }
                    else {
                        Log.d("TEAM_RESPONSE",""+jsonObject.getString(Constant.MESSAGE));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("TEAM_RESPONSE",""+e.getMessage());
                }
            }
        }, activity, Constant.REFER_DETAILS_URL, params, true);

    }

}