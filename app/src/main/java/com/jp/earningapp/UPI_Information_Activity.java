package com.jp.earningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jp.earningapp.fragment.ProfileFragment;

public class UPI_Information_Activity extends AppCompatActivity {

    ImageView left_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upi_information);

        left_img = findViewById(R.id.left_img);

        left_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UPI_Information_Activity.this, ProfileFragment.class);
                startActivity(intent);
            }
        });
    }
}