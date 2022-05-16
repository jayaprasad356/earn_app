package com.lsa.ayu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class TransactionDetailsActivity extends AppCompatActivity {
    Button ConfirmPay;
    ImageButton Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);
        ConfirmPay = findViewById(R.id.confirmpay);
        Back = findViewById(R.id.back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ConfirmPay.setText("Confirm Pay With ₹ 100");
    }
}