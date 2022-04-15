package com.jp.earningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.jp.earningapp.helper.ApiConfig;
import com.jp.earningapp.helper.Constant;
import com.jp.earningapp.helper.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MinorActivity extends AppCompatActivity {
    TextView tvPurchasedPlans,tvTodayProfit,tvTotalProfit;
    Activity activity;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minor);
        activity = MinorActivity.this;
        session = new Session(MinorActivity.this);
        tvPurchasedPlans = findViewById(R.id.tvPurchasedPlans);
        tvTodayProfit = findViewById(R.id.tvTodayProfit);
        tvTotalProfit = findViewById(R.id.tvTotalProfit);
        minorDetails();
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