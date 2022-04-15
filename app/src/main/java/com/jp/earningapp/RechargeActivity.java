package com.jp.earningapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.slider.Slider;
import com.jp.earningapp.helper.ApiConfig;
import com.jp.earningapp.helper.Constant;
import com.jp.earningapp.helper.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RechargeActivity extends AppCompatActivity {
    Slider priceslider;
    EditText etPay;
    Button paybtn;
    int amt;
    Activity activity;
    Session session;
    Chip paytm,upi;
    TextView tvBalance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        session = new Session(RechargeActivity.this);
        priceslider = findViewById(R.id.priceslider);
        etPay = findViewById(R.id.etPay);
        paybtn = findViewById(R.id.paybtn);
        paytm = findViewById(R.id.paytm);
        upi = findViewById(R.id.upi);
        tvBalance = findViewById(R.id.tvBalance);
        activity = RechargeActivity.this;
        tvBalance.setText("My Balance Rs. "+session.getData(Constant.BALANCE));
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
                if(etPay.getText().toString().equals("") || etPay.getText().toString().equals("0")){
                    Toast.makeText(RechargeActivity.this, "Enter Recharge Amount", Toast.LENGTH_SHORT).show();
                }
                else {
                    rechargeAmount();
                }
            }
        });
    }

    private void rechargeAmount()
    {
        String type = "";
        if (paytm.isChecked()){
            type = "paytm";

        }
        else {
            type = "upi";
        }
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        params.put(Constant.AMOUNT,etPay.getText().toString());
        params.put(Constant.TYPE,type);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(this,jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(this, String.valueOf(response) +String.valueOf(result), Toast.LENGTH_SHORT).show();

            }
            //pass url
        }, activity, Constant.RECHARGE_URL, params,true);
    }
}