package com.jp.earningapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.slider.Slider;

public class RechargeActivity extends AppCompatActivity {
    Slider priceslider;
    EditText etPay;
    Button paybtn;
    int amt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        priceslider = findViewById(R.id.priceslider);
        etPay = findViewById(R.id.etPay);
        paybtn = findViewById(R.id.paybtn);

        priceslider.addOnChangeListener(new Slider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                amt = (int) value;
                etPay.setText(""+amt);

            }
        });
        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RechargeActivity.this,Recharge2Activity.class);
                intent.putExtra("price",etPay.getText().toString());
                startActivity(intent);
            }
        });
    }
}