package com.lsa.ayu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.lsa.ayu.fragment.CallUsFragment;
import com.lsa.ayu.fragment.HomeFragment;
import com.lsa.ayu.fragment.ProfileFragment;
import com.lsa.ayu.fragment.ShopFragment;
import com.lsa.ayu.fragment.TeamFragment;
import com.lsa.ayu.helper.ApiConfig;
import com.lsa.ayu.helper.Constant;
import com.lsa.ayu.helper.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    HomeFragment homeFragment;
    ShopFragment shopFragment;
    TeamFragment teamFragment;
    CallUsFragment callUsFragment;
    ProfileFragment profileFragment;
    BottomNavigationView bottomNavigationView;
    Session session;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new Session(MainActivity.this);
        activity = MainActivity.this;
        homeFragment = new HomeFragment();
        shopFragment = new ShopFragment();
        teamFragment = new TeamFragment();
        callUsFragment = new CallUsFragment();
        profileFragment = new ProfileFragment();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment,"HOME").commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getUserDetails();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment,"HOME").commit();
                return true;

            case R.id.nav_shop:
                getUserDetails();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,shopFragment,"SHOP" ).commit();
                return true;
            case R.id.nav_team:
                getUserDetails();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,teamFragment,"TEAM" ).commit();
                return true;
            case R.id.nav_callus:
                getUserDetails();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,callUsFragment,"CALLUS" ).commit();
                return true;
            case R.id.nav_profile:
                getUserDetails();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,profileFragment,"PROFILE" ).commit();
                return true;
        }

        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        getUserDetails();

    }

    private void getDailyIncome() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);

                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        session.setData(Constant.BALANCE,jsonArray.getJSONObject(0).getString(Constant.BALANCE));
                    }
                    else {
                        Log.d("MAINACTIVITY",jsonObject.getString(Constant.MESSAGE));

                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

        }, activity, Constant.DAILY_INCOME_URL, params,true);

    }

    private void getUserDetails()
    {
        getDailyIncome();
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);

                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        session.setData(Constant.BALANCE,jsonArray.getJSONObject(0).getString(Constant.BALANCE));
                        session.setData(Constant.EARN,jsonArray.getJSONObject(0).getString(Constant.EARN));
                    }
                    else {
                        Log.d("MAINACTIVITY",jsonObject.getString(Constant.MESSAGE));

                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

        }, activity, Constant.USER_DETAILS_URL, params,true);

    }
}

