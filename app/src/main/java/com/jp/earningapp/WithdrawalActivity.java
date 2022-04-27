package com.jp.earningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jp.earningapp.helper.ApiConfig;
import com.jp.earningapp.helper.Constant;
import com.jp.earningapp.helper.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WithdrawalActivity extends AppCompatActivity {
    EditText etWithdrawal;
    Button btnWithdrawal;
    Session session;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
        session = new Session(WithdrawalActivity.this);

        etWithdrawal = findViewById(R.id.etWithdrawal);
        btnWithdrawal = findViewById(R.id.btnWithdrawal);
        activity = WithdrawalActivity.this;

        btnWithdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etWithdrawal.getText().toString().equals("") || etWithdrawal.getText().toString().equals("0")){
                    etWithdrawal.setError("Enter Amount");
                    etWithdrawal.requestFocus();
                }
                else {
                    withdrawal();
                }
            }
        });


    }

    private void withdrawal()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        params.put(Constant.AMOUNT,etWithdrawal.getText().toString().trim());
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
        }, activity, Constant.WITHDRAWAL_URL, params,true);
    }
}