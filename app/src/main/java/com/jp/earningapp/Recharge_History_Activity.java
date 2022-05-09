package com.jp.earningapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jp.earningapp.adapter.PlanAdapter;
import com.jp.earningapp.adapter.RechargeAdapter;
import com.jp.earningapp.helper.ApiConfig;
import com.jp.earningapp.helper.Constant;
import com.jp.earningapp.helper.Session;
import com.jp.earningapp.model.Plan;
import com.jp.earningapp.model.Recharge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Recharge_History_Activity extends AppCompatActivity {

    Activity activity;
    public static RecyclerView recyclerView;
    public static RechargeAdapter rechargeAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    Session session;
    TextView norecharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_history);
        recyclerView = findViewById(R.id.recyclerView);
        norecharge = findViewById(R.id.norecharge);
        activity = Recharge_History_Activity.this;
        session = new Session(activity);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rechargeList();
            }
        });
        rechargeList();
    }

    private void rechargeList()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        norecharge.setVisibility(View.GONE);
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();
                        ArrayList<Recharge> recharges = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {
                                Recharge group = g.fromJson(jsonObject1.toString(), Recharge.class);
                                recharges.add(group);
                            } else {
                                break;
                            }
                        }

                        rechargeAdapter = new RechargeAdapter(activity, recharges);
                        recyclerView.setAdapter(rechargeAdapter);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    else {
                        norecharge.setVisibility(View.VISIBLE);
                        Toast.makeText(activity, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        }, activity, Constant.RECHARGE_LIST_URL, params, true);
    }
}