package com.jp.earningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FaqActivity extends AppCompatActivity {

    TextView answer,answer1,answer2;
    LinearLayout queslyt,queslyt1,queslyt2;
    boolean q1 = false , q2 = false , q3 = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        answer = findViewById(R.id.answer);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        queslyt = findViewById(R.id.queslyt);
        queslyt1 = findViewById(R.id.queslyt1);
        queslyt2 = findViewById(R.id.queslyt2);

        queslyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(q1){
                    answer.setVisibility(View.GONE);
                    q1 = false;
                }
                else {
                    answer.setVisibility(View.VISIBLE);
                    q1 = true;
                }



            }
        });
        queslyt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q2){
                    answer1.setVisibility(View.GONE);
                    q2 = false;
                }
                else {
                    answer1.setVisibility(View.VISIBLE);
                    q2 = true;
                }
            }
        });
        queslyt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(q3){
                    answer2.setVisibility(View.GONE);
                    q3 = false;
                }
                else {
                    answer2.setVisibility(View.VISIBLE);
                    q3 = true;
                }
            }
        });




    }
}