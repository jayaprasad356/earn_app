package com.lsa.ayu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.lsa.ayu.helper.ApiConfig;
import com.lsa.ayu.helper.Constant;
import com.lsa.ayu.helper.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UPI_Information_Activity extends AppCompatActivity {

    ImageButton back;
    EditText etUpi;
    Button btnUpdate;
    Activity activity;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upi_information);

        back = findViewById(R.id.back);
        etUpi = findViewById(R.id.etUpi);
        btnUpdate = findViewById(R.id.btnUpdate);
        activity = UPI_Information_Activity.this;
        session = new Session(activity);

        etUpi.setText(session.getData(Constant.UPI));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etUpi.getText().toString().equals("")){
                    etUpi.setError("empty");
                    etUpi.requestFocus();
                }
                else {
                    updateUpi();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void updateUpi()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.UPI,etUpi.getText().toString().trim());
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("UPI_INFOmar",response);
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);

                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        session.setData(Constant.ID,jsonArray.getJSONObject(0).getString(Constant.ID));
                        session.setData(Constant.NAME,jsonArray.getJSONObject(0).getString(Constant.NAME));
                        session.setData(Constant.MY_REFER_CODE,jsonArray.getJSONObject(0).getString(Constant.MY_REFER_CODE));
                        session.setData(Constant.EARN,jsonArray.getJSONObject(0).getString(Constant.EARN));
                        session.setData(Constant.UPI,jsonArray.getJSONObject(0).getString(Constant.UPI));
                        Toast.makeText(activity, "Upi Updated", Toast.LENGTH_SHORT).show();
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
        }, activity, Constant.UPDATE_UPI_URL, params,true);


    }
}