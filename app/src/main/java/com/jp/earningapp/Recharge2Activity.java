package com.jp.earningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class Recharge2Activity extends AppCompatActivity {
    Button Proceed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge2);
        Proceed = findViewById(R.id.proceed);
        Proceed.setText("Proceed with ₹ "+getIntent().getStringExtra("price"));
    }
}