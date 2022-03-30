package com.jp.earningapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.jp.earningapp.fragment.CallUsFragment;
import com.jp.earningapp.fragment.HomeFragment;
import com.jp.earningapp.fragment.ProfileFragment;
import com.jp.earningapp.fragment.ShopFragment;
import com.jp.earningapp.fragment.TeamFragment;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    HomeFragment homeFragment;
    ShopFragment shopFragment;
    TeamFragment teamFragment;
    CallUsFragment callUsFragment;
    ProfileFragment profileFragment;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment,"EXPLORE").commit();
                return true;

            case R.id.nav_shop:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,shopFragment,"SHOP" ).commit();
                return true;
            case R.id.nav_team:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,teamFragment,"TEAM" ).commit();
                return true;
            case R.id.nav_callus:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,callUsFragment,"CALLUS" ).commit();
                return true;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,profileFragment,"PROFILE" ).commit();
                return true;
        }

        return false;
    }
}

