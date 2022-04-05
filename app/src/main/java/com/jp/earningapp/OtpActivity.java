package com.jp.earningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jp.earningapp.helper.Constant;

public class OtpActivity extends AppCompatActivity {
    Button VerifyBtn;
    String Mobile;
    TextView tvOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        VerifyBtn = findViewById(R.id.verify_btn);
        tvOtp = findViewById(R.id.tvOtp);
        Mobile = getIntent().getStringExtra(Constant.MOBILE);
        tvOtp.setText("Otp Sent to +91 "+Mobile);
        VerifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(OtpActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
    }
}