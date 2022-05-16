package com.lsa.ayu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lsa.ayu.helper.Constant;
import com.lsa.ayu.helper.Session;

public class ReferEarnActivity extends AppCompatActivity {
    Button share_friends;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_earn);
        session = new Session(ReferEarnActivity.this);
        share_friends = findViewById(R.id.share_friends);

        share_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Create an ACTION_SEND Intent*/
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                /*This will be the actual content you wish you share.*/
                String shareBody = session.getData(Constant.MY_REFER_CODE) + " Use My Refer Code to Join Our App and Share with Your Friends Also";
                /*The type of the content is text, obviously.*/
                intent.setType("text/plain");
                /*Applying information Subject and Body.*/
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Refer & Earn");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                /*Fire!*/
                startActivity(Intent.createChooser(intent, "Share Via"));
            }
        });
    }
}