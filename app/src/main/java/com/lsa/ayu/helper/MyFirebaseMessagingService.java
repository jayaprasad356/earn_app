package com.lsa.ayu.helper;


import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.lsa.ayu.MainActivity;
import com.lsa.ayu.SplashActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                sendPushNotification(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    void sendPushNotification(JSONObject json) {
        try {

            JSONObject data = json.getJSONObject(Constant.DATA);

            String type = data.getString("type");
            String title = data.getString("title");
            String message = data.getString("message");
            String imageUrl = data.getString("image");
            String id = data.getString("id");

            Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());
            mNotificationManager.showSmallNotification(title, message, intent);




        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getLocalizedMessage());

        }
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }

}
