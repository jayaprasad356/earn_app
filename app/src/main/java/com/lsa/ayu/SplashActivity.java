package com.lsa.ayu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.lsa.ayu.helper.Session;

import org.jsoup.Jsoup;

import java.io.IOException;

public class SplashActivity extends AppCompatActivity {
    Handler handler;
    Session session;
    Activity activity;
    private String sLatestVersion,sCurrentVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new GetLatestVersion().execute();
        activity = SplashActivity.this;
        session = new Session(activity);
        handler = new Handler();


    }
    private class GetLatestVersion extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {

                sLatestVersion = Jsoup.connect("https://play.google.com/store/apps/details?id="+getPackageName()).timeout(30000).get().select("div.hAyfc:nth-child(4)>"+
                        "span:nth-child(2) > div:nth-child(1)"+"> span:nth-child(1)").first().ownText();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sLatestVersion;
        }

        @Override
        protected void onPostExecute(String s) {
            if(CheckNetwork.isInternetAvailable(SplashActivity.this)){
                sCurrentVersion = BuildConfig.VERSION_NAME;
                /*Log.e("VERSION",sCurrentVersion + " "+sLatestVersion);
                long cVersion = Long.parseLong(sCurrentVersion);
                long lVersion = Long.parseLong(sLatestVersion);*/

                if (sLatestVersion != null && !sLatestVersion.equals(sCurrentVersion)){
                    updateAlertDialog();

                }
                else {

                    GotoActivity();
                    //new Handler().postDelayed(() -> startActivity(new Intent(SplashActivity.this, WelcomeActivity.class).putExtra(Constant.FROM, "").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)), SPLASH_TIME_OUT);

                }

            }
            else {
                Toast.makeText(SplashActivity.this, "Please Connect Internet", Toast.LENGTH_SHORT).show();


            }

        }
    }
    private void updateAlertDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Yep! Here you can see a new update \uD83D\uDC3B\n");
        builder.setMessage(
                "We are working hard for your better experience \uD83E\uDD29\n" +
                        "\n" +
                        "So, Update it and enter in a new and updated version \uD83D\uDE0E\n" +
                        "\n");
        builder.setCancelable(false);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getPackageName())));

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                finish();
            }
        });
        builder.show();
    }

    private void GotoActivity()
    {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (session.getBoolean("is_logged_in")){
                    Intent intent = new Intent(activity,MainActivity.class);
                    startActivity(intent);
                    finish();

                }
                else {
                    Intent intent = new Intent(activity,LoginActivity.class);
                    startActivity(intent);
                    finish();

                }




            }
        },2000);

    }

}