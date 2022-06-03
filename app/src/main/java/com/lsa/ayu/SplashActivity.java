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
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.Task;
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
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                updateAlertDialog();
            }
            else {

                GotoActivity();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                GotoActivity();

            }
        });



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