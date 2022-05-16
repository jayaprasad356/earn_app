package com.lsa.ayu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lsa.ayu.helper.ApiConfig;
import com.lsa.ayu.helper.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    Button signup_btn;
    Activity activity;
    EditText etName,etMobile,etReferral,etUpi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signup_btn = findViewById(R.id.signup_btn);
        etName = findViewById(R.id.etName);
        etMobile = findViewById(R.id.etMobile);
        etReferral = findViewById(R.id.etReferral);
        etUpi = findViewById(R.id.etUpi);
        activity = SignUpActivity.this;

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().trim().equals("")){
                    etName.setError("Name is Empty");
                    etName.requestFocus();
                }
                else if (etMobile.getText().toString().trim().equals("")){
                    etMobile.setError("Mobile Number is Empty");
                    etMobile.requestFocus();
                }
                else if (etUpi.getText().toString().trim().equals("")){
                    etUpi.setError("UPI is Empty");
                    etUpi.requestFocus();
                }
                else {
                    signUp();

                }
            }
        });
    }

    private void signUp()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.MOBILE,etMobile.getText().toString().trim());
        params.put(Constant.NAME,etName.getText().toString().trim());
        params.put(Constant.REFERRAL,etReferral.getText().toString().trim());
        params.put(Constant.UPI,etUpi.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Toast.makeText(this,jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(activity, LoginActivity.class));
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
        }, activity, Constant.SIGNUP_USER_URL, params,true);



    }

}