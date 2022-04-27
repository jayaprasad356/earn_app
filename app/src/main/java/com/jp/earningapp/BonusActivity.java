package com.jp.earningapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.jp.earningapp.adapter.BonusAdapter;
import com.jp.earningapp.adapter.TeamAdapter;
import com.jp.earningapp.helper.ApiConfig;
import com.jp.earningapp.helper.Constant;
import com.jp.earningapp.helper.Session;
import com.jp.earningapp.model.Bonus;
import com.jp.earningapp.model.Team;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BonusActivity extends AppCompatActivity {
    public static RecyclerView recyclerView;
    public static BonusAdapter bonusAdapter;
    Activity activity;
    Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus);
        activity = BonusActivity.this;
        recyclerView = findViewById(R.id.recyclerView);

        session = new Session(activity);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        bonusList();
    }

    private void bonusList()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID, session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        ArrayList<Bonus> bonuses = new ArrayList<>();
                        bonusAdapter = new BonusAdapter(activity, bonuses);
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1 != null) {
                                Bonus group = g.fromJson(jsonObject1.toString(), Bonus.class);
                                bonuses.add(group);
                            } else {
                                break;
                            }
                        }


                        recyclerView.setAdapter(bonusAdapter);
                    }
                    else {
                        Log.d("TEAM_RESPONSE",""+jsonObject.getString(Constant.MESSAGE));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("TEAM_RESPONSE",""+e.getMessage());
                }
            }
        }, activity, Constant.BONUS_LIST_URL, params, true);

    }
}