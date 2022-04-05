package com.jp.earningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jp.earningapp.helper.ApiConfig;
import com.jp.earningapp.helper.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextView tvsignup;
    EditText etMobile;
    Button btnSendOtp;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvsignup = findViewById(R.id.tvsignup);
        btnSendOtp = findViewById(R.id.btnSendOtp);
        etMobile = findViewById(R.id.etMobile);
        tvsignup.setPaintFlags(tvsignup.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        activity = LoginActivity.this;
        btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etMobile.getText().toString().trim().equals("")){
                    etMobile.setError("Mobile Number is Empty");
                    etMobile.requestFocus();
                }
                else {
                    checkMobileNumber();

                }

            }
        });
        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkMobileNumber()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.MOBILE,etMobile.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Toast.makeText(this,jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity,OtpActivity.class);
                        intent.putExtra(Constant.MOBILE,etMobile.getText().toString().trim());
                        startActivity(intent);
                        finish();
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
        }, activity, Constant.CHECK_MOBILE_NUMBER_URL, params,true);

    }
}