package com.jp.earningapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.jp.earningapp.helper.Constant;
import com.jp.earningapp.helper.Session;

public class SplashActivity extends AppCompatActivity {
    Handler handler;
    Session session;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activity = SplashActivity.this;
        session = new Session(activity);

        handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (session.getData(Constant.ID).equals("")){
                    Intent intent = new Intent(activity,LoginActivity.class);
                    startActivity(intent);
                    finish();

                }
                else if (session.getData(Constant.ID) != null || !session.getData(Constant.ID).equals("")){
                    Intent intent = new Intent(activity,MainActivity.class);
                    startActivity(intent);
                    finish();
                }



            }
        },3000);
    }
}