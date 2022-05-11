package com.jp.earningapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jp.earningapp.adapter.PurchasedPlanAdapter;
import com.jp.earningapp.adapter.RechargeAdapter;
import com.jp.earningapp.helper.ApiConfig;
import com.jp.earningapp.helper.Constant;
import com.jp.earningapp.helper.Session;
import com.jp.earningapp.model.PurchasedPlan;
import com.jp.earningapp.model.Recharge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MinorActivity extends AppCompatActivity {
    TextView tvPurchasedPlans,tvTodayProfit,tvTotalProfit,norecord;
    Activity activity;
    Session session;
    RecyclerView recyclerView;
    PurchasedPlanAdapter purchasedPlanAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minor);
        activity = MinorActivity.this;
        session = new Session(MinorActivity.this);
        recyclerView = findViewById(R.id.recyclerView);
        tvPurchasedPlans = findViewById(R.id.tvPurchasedPlans);
        tvTodayProfit = findViewById(R.id.tvTodayProfit);
        tvTotalProfit = findViewById(R.id.tvTotalProfit);
        norecord = findViewById(R.id.norecord);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                purchasedList();
            }
        });
        minorDetails();
        purchasedList();
    }

    private void purchasedList()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        norecord.setVisibility(View.GONE);
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();
                        ArrayList<PurchasedPlan> purchasedPlans = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {
                                PurchasedPlan group = g.fromJson(jsonObject1.toString(), PurchasedPlan.class);
                                purchasedPlans.add(group);
                            } else {
                                break;
                            }
                        }

                        purchasedPlanAdapter = new PurchasedPlanAdapter(activity, purchasedPlans);
                        recyclerView.setAdapter(purchasedPlanAdapter);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    else {
                        norecord.setVisibility(View.VISIBLE);
                        Toast.makeText(activity, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
            }
        }, activity, Constant.PURCHASED_PLAN_LIST_URL, params, true);
    }

    private void minorDetails()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        tvPurchasedPlans.setText(jsonObject.getString(Constant.PURCHASED_PLANS));
                        tvTodayProfit.setText(jsonObject.getString(Constant.TODAY_PROFIT));
                        tvTotalProfit.setText(jsonObject.getString(Constant.TOTAL_PROFIT));
                    }
                    else {
                        Log.d("MAINACTIVITY",jsonObject.getString(Constant.MESSAGE));

                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

        }, activity, Constant.MINER_URL, params,true);


    }
}