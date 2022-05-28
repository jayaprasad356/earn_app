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
import android.util.Log;
import android.widget.Toast;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;
import com.lsa.ayu.helper.Session;

import org.jsoup.Jsoup;

import java.io.IOException;

public class SplashActivity extends AppCompatActivity {
    Handler handler;
    Session session;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activity = SplashActivity.this;
        session = new Session(activity);
        handler = new Handler();
        AppUpdaterUtils appUpdaterUtils = new AppUpdaterUtils(this)
                .setUpdateFrom(UpdateFrom.GOOGLE_PLAY)
                .withListener(new AppUpdaterUtils.UpdateListener() {
                    @Override
                    public void onSuccess(Update update, Boolean isUpdateAvailable) {
                        Log.d("Latest Version", update.getLatestVersion());
                        Log.d("Latest Version Code", String.valueOf(update.getLatestVersionCode()));
                        Log.d("Release notes", update.getReleaseNotes());
                        Log.d("URL", String.valueOf(update.getUrlToDownload()));
                        Log.d("Is update available?", Boolean.toString(isUpdateAvailable));
                        if (isUpdateAvailable){
                            updateAlertDialog();

                        }
                        else {
                            GotoActivity();

                        }
                    }

                    @Override
                    public void onFailed(AppUpdaterError error) {
                        Log.d("AppUpdater Error", "Something went wrong");
                    }
                });
        appUpdaterUtils.start();


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