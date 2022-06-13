package com.lsa.ayu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lsa.ayu.helper.ApiConfig;
import com.lsa.ayu.helper.Constant;
import com.lsa.ayu.helper.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WithdrawalActivity extends AppCompatActivity {
    EditText etWithdrawal;
    Button btnWithdrawal;
    Session session;
    Activity activity;
    TextView earning,tvWatax;
    int total_withdrawal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
        session = new Session(WithdrawalActivity.this);

        etWithdrawal = findViewById(R.id.etWithdrawal);
        btnWithdrawal = findViewById(R.id.btnWithdrawal);
        earning = findViewById(R.id.earning);
        tvWatax = findViewById(R.id.tvWatax);
        activity = WithdrawalActivity.this;
        tvWatax.setText("Withdrawal Amount (6%) = Rs. 0");

        etWithdrawal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    Double amount = Double.parseDouble(etWithdrawal.getText().toString().trim());
                    double res = (amount / 100.0f) * 6;
                    double wares = amount - res;
                    total_withdrawal = (int) Math.round(wares);
//                    int wa = (int) res;
//                    int twa = Integer.parseInt(etWithdrawal.getText().toString().trim()) - wa ;
                    tvWatax.setText("Withdrawal Amount (6%) = Rs. "+total_withdrawal);
                }catch (Exception e){
                    tvWatax.setText("Withdrawal Amount (6%) = Rs. 0");

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        earning.setText("Earning Rs. "+session.getData(Constant.EARN));

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
        params.put(Constant.AMOUNT,""+total_withdrawal);
        params.put(Constant.ACT_AMOUNT,etWithdrawal.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        session.setData(Constant.BALANCE,jsonArray.getJSONObject(0).getString(Constant.BALANCE));
                        session.setData(Constant.EARN,jsonArray.getJSONObject(0).getString(Constant.EARN));
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