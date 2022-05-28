package com.lsa.ayu.helper;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;
import android.widget.Toast;
import co.paystack.android.PaystackSdk;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

public class ApiConfig extends Application {
    static ApiConfig mInstance;
    public static final String TAG = ApiConfig.class.getSimpleName();
    RequestQueue mRequestQueue;
    static AppEnvironment appEnvironment;

    public static String VolleyErrorMessage(VolleyError error) {
        String message = "";
        try {
            if (error instanceof NetworkError) {
                message = "Cannot connect to Internet...Please check your connection!";
            } else if (error instanceof ServerError) {
                message = "The server could not be found. Please try again after some time!";
            } else if (error instanceof AuthFailureError) {
                message = "Cannot connect to Internet...Please check your connection!";
            } else if (error instanceof ParseError) {
                message = "Parsing error! Please try again after some time!";
            } else if (error instanceof TimeoutError) {
                message = "Connection TimeOut! Please check your internet connection.";
            } else
                message = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
    public AppEnvironment getAppEnvironment() {
        return appEnvironment;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void RequestToVolley(final VolleyCallback callback, final Activity activity, final String url, final Map<String, String> params, final boolean isProgress) {
        if (ProgressDisplay.mProgressBar != null) {
            ProgressDisplay.mProgressBar.setVisibility(View.GONE);
        }
        final ProgressDisplay progressDisplay = new ProgressDisplay(activity);
        progressDisplay.hideProgress();

        if (isProgress)
            progressDisplay.showProgress();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            if (ApiConfig.isConnected(activity))
                callback.onSuccess(true, response);
            if (isProgress)
                progressDisplay.hideProgress();
        }, error -> {
            if (isProgress)
                progressDisplay.hideProgress();
            if (ApiConfig.isConnected(activity))
                callback.onSuccess(false, "");
            String message = VolleyErrorMessage(error);
            if (!message.equals(""))
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params1 = new HashMap<>();
                params1.put(Constant.AUTHORIZATION, "Bearer " + createJWT("eKart", "eKart Authentication"));
                return params1;
            }


            @Override
            protected Map<String, String> getParams() {
                params.put(Constant.AccessKey, Constant.AccessKeyVal);

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, 0, 0));
        ApiConfig.getInstance().getRequestQueue().getCache().clear();
        ApiConfig.getInstance().addToRequestQueue(stringRequest);
    }
    public static String createJWT(String issuer, String subject) {
        try {
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);
            byte[] apiKeySecretBytes = Constant.JWT_KEY.getBytes();
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
            JwtBuilder builder = Jwts.builder()
                    .setIssuedAt(now)
                    .setSubject(subject)
                    .setIssuer(issuer)
                    .signWith(signatureAlgorithm, signingKey);

            return builder.compact();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void SetAppEnvironment(Activity activity) {
        if (Constant.PAYUMONEY_MODE.equals("production")) {
            appEnvironment = AppEnvironment.PRODUCTION;
        } else if (Constant.PAYUMONEY_MODE.equals("sandbox")) {
            appEnvironment = AppEnvironment.SANDBOX;
        } else {
            appEnvironment = AppEnvironment.SANDBOX;
        }
        PaystackSdk.initialize(activity);
    }

    public static void RequestToVolley(final VolleyCallback callback, final Activity activity, final String url, final Map<String, String> params, final Map<String, String> fileParams) {
        if(isConnected(activity)) {
            VolleyMultiPartRequest multipartRequest = new VolleyMultiPartRequest(url,
                    response -> callback.onSuccess(true, response),
                    error -> callback.onSuccess(false, "")) {
                @Override
                public Map<String, String> getDefaultParams() {
                    return params;
                }


                @Override
                public Map<String, String> getFileParams() {
                    return fileParams;
                }
            };

            multipartRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            getInstance().getRequestQueue().getCache().clear();
            getInstance().addToRequestQueue(multipartRequest);
        }
    }

    public static synchronized ApiConfig getInstance() {
        return mInstance;
    }

    public static Boolean isConnected(final Activity activity) {
        boolean check = false;
        try {
            ConnectivityManager ConnectionManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                check = true;
            } else {
                Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;


    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }


}