package com.jp.earningapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.jp.earningapp.fragment.CallUsFragment;
import com.jp.earningapp.fragment.HomeFragment;
import com.jp.earningapp.fragment.ProfileFragment;
import com.jp.earningapp.fragment.ShopFragment;
import com.jp.earningapp.fragment.TeamFragment;
import com.jp.earningapp.helper.ApiConfig;
import com.jp.earningapp.helper.Constant;
import com.jp.earningapp.helper.Session;

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
    TextView userName;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new Session(MainActivity.this);
        userName = findViewById(R.id.userName);
        activity = MainActivity.this;
        homeFragment = new HomeFragment();
        shopFragment = new ShopFragment();
        teamFragment = new TeamFragment();
        callUsFragment = new CallUsFragment();
        profileFragment = new ProfileFragment();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment,"HOME").commit();
        userName.setText(session.getData(Constant.NAME));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getUserDetails();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment,"EXPLORE").commit();
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

    private void getUserDetails()
    {
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

        }, activity, Constant.USER_DETAILS_URL, params,true);

    }
}

