package com.jp.earningapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.slider.Slider;
import com.jp.earningapp.helper.ApiConfig;
import com.jp.earningapp.helper.Constant;
import com.jp.earningapp.helper.Session;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RechargeActivity extends AppCompatActivity implements PaymentStatusListener, PaymentResultListener {
    Slider priceslider;
    EditText etPay;
    Button paybtn;
    int amt;
    Activity activity;
    Session session;
    Chip razorpay,upi;
    TextView tvBalance;
    String UPI_ID = "";
    String RAZORPAY_KEY = "";
    String RAZORPAY_PAYMENT_METHOD = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        session = new Session(RechargeActivity.this);
        priceslider = findViewById(R.id.priceslider);
        etPay = findViewById(R.id.etPay);
        paybtn = findViewById(R.id.paybtn);
        razorpay = findViewById(R.id.razorpay);
        upi = findViewById(R.id.upi);
        tvBalance = findViewById(R.id.tvBalance);
        activity = RechargeActivity.this;
        tvBalance.setText("My Balance Rs. "+session.getData(Constant.BALANCE));
        priceslider.addOnChangeListener(new Slider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                amt = (int) value;
                etPay.setText(""+amt);

            }
        });
        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etPay.getText().toString().equals("") || etPay.getText().toString().equals("0")){
                    Toast.makeText(RechargeActivity.this, "Enter Recharge Amount", Toast.LENGTH_SHORT).show();
                }
                else {
                    Map<String, String> params = new HashMap<>();
                    params.put(Constant.AMOUNT,etPay.getText().toString().trim());
                    ApiConfig.RequestToVolley((result, response) -> {
                        if (result) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getBoolean(Constant.SUCCESS)) {
                                    if(razorpay.isChecked()){
                                       // launchPayUMoneyFlow(Double.parseDouble("50"));
                                        launchRayzorpay();

                                    }
                                    else if(upi.isChecked()){
                                        Toast.makeText(activity, UPI_ID, Toast.LENGTH_SHORT).show();
                                        if (!UPI_ID.equals("")){

                                            try {
                                                Date c = Calendar.getInstance().getTime();
                                                SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault());
                                                String transcId = df.format(c);
                                                makePayment(""+Double.parseDouble(etPay.getText().toString()), UPI_ID, session.getData(Constant.NAME), "recharge amount", transcId);


                                            }catch (Exception e){
                                                Log.d("PAYMENT_GATEWAY",e.getMessage());

                                            }

                                        }


                                    }

                                }
                                else {
                                    Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        else {
                            Toast.makeText(activity, String.valueOf(response) +String.valueOf(result), Toast.LENGTH_SHORT).show();

                        }
                        //pass url
                    }, activity, Constant.CHECK_RECHARGE_URL, params,true);



                }
            }
        });
    }

    private void launchRayzorpay() {

        // on below line we are getting
        // amount that is entered by user.
        String samount = etPay.getText().toString();
        int amount = Math.round(Float.parseFloat(samount) * 100);
        // initialize Razorpay account.
        Checkout checkout = new Checkout();
        checkout.setKeyID(RAZORPAY_KEY);


        // initialize json object
        JSONObject object = new JSONObject();
        try {
            // to put name
            object.put("name", session.getData(Constant.NAME));

            // put description
            object.put("description", "App payment");

            // to set theme color
            object.put("theme.color", "");

            // put the currency
            object.put("currency", "INR");

            // put amount
            object.put("amount", amount);

            // put mobile number
            object.put("prefill.contact", session.getData(Constant.MOBILE));

            // put email
            object.put("prefill.email", "test@gmail.com");

            // open razorpay to checkout activity
            checkout.open(RechargeActivity.this, object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    private void launchPayUMoneyFlow(double amount) {
        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();

        String txnId = "0nf7" + System.currentTimeMillis();
        // String txnId = "TXNID720431525261327973";
        String phone = "7777777777";
        String productName = "Sample Product";
        String firstName = "loopwiki";
        String email = "sample@sample.com";
        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";

        //AppEnvironment appEnvironment = ((BaseApplication) getApplication()).getAppEnvironment();
        builder.setAmount(String.valueOf(amount))
                .setTxnId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl(getString(R.string.sUrl))
                .setfUrl(getString(R.string.fUrl))
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setIsDebug(false)
                .setKey(getString(R.string.MerchantKey))
                .setMerchantId(getString(R.string.MerchantId));

        try {
            PayUmoneySdkInitializer.PaymentParam mPaymentParams = builder.build();

            /*
             * Hash should always be generated from your server side.
             * */
            //    generateHashFromServer(mPaymentParams);

            /*            *//**
             * Do not use below code when going live
             * Below code is provided to generate hash from sdk.
             * It is recommended to generate hash from server side only.
             * */
            mPaymentParams = calculateServerSideHashAndInitiatePayment1(mPaymentParams);

            PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, RechargeActivity.this, R.style.AppTheme, false);

        } catch (Exception e) {
            // some exception occurred
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    // Method to create hash
    public static String hashCal(String str) {
        byte[] hashseq = str.getBytes();
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (byte aMessageDigest : messageDigest) {
                String hex = Integer.toHexString(0xFF & aMessageDigest);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ignored) {
        }
        return hexString.toString();
    }
    @Override
    public void onPaymentSuccess(String s) {
        // this method is called on payment success.
        Toast.makeText(this, "Payment is successful : " + s, Toast.LENGTH_SHORT).show();
        rechargeAmount();
    }

    @Override
    public void onPaymentError(int i, String s) {
        // on payment failed.
        Log.d("PAYMENT",s);
       // Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
    }
    private PayUmoneySdkInitializer.PaymentParam calculateServerSideHashAndInitiatePayment1(final PayUmoneySdkInitializer.PaymentParam paymentParam) {

        StringBuilder stringBuilder = new StringBuilder();
        HashMap<String, String> params = paymentParam.getParams();
        stringBuilder.append(params.get(PayUmoneyConstants.KEY)).append("|");
        stringBuilder.append(params.get(PayUmoneyConstants.TXNID)).append("|");
        stringBuilder.append(params.get(PayUmoneyConstants.AMOUNT)).append("|");
        stringBuilder.append(params.get(PayUmoneyConstants.PRODUCT_INFO)).append("|");
        stringBuilder.append(params.get(PayUmoneyConstants.FIRSTNAME)).append("|");
        stringBuilder.append(params.get(PayUmoneyConstants.EMAIL)).append("|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF1)).append("|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF2)).append("|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF3)).append("|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF4)).append("|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF5)).append("||||||");

        stringBuilder.append(R.string.MerchantSalt);

        String hash = hashCal(stringBuilder.toString());
        paymentParam.setMerchantHash(hash);

        return paymentParam;
    }


    @Override
    protected void onStart() {
        super.onStart();
        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        UPI_ID = jsonArray.getJSONObject(0).getString(Constant.UPI_ID);
                        RAZORPAY_KEY = jsonArray.getJSONObject(0).getString(Constant.RAZORPAY_KEY);
                        RAZORPAY_PAYMENT_METHOD = jsonArray.getJSONObject(0).getString(Constant.RAZORPAY_PAYMENT_METHOD);
                        if (RAZORPAY_PAYMENT_METHOD.equals("1") && !RAZORPAY_KEY.equals("")){
                            razorpay.setVisibility(View.VISIBLE);

                        }

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
        }, activity, Constant.EARN_SETTINGS_URL, params,true);

    }

    private void rechargeAmount()
    {
        String type = "";
        if (razorpay.isChecked()){
            type = "razorpay";

        }
        else {
            type = "upi";
        }
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        params.put(Constant.AMOUNT,etPay.getText().toString());
        params.put(Constant.TYPE,type);
        ApiConfig.RequestToVolley((result, response) -> {
            Log.d("UPI_RESPONSE",response);
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(this,jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
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
        }, activity, Constant.RECHARGE_URL, params,true);
    }

    @Override
    public void onTransactionCompleted(TransactionDetails transactionDetails) {


    }

    @Override
    public void onTransactionSuccess() {
        rechargeAmount();
        Toast.makeText(activity, "Transaction Success", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onTransactionSubmitted() {

    }

    @Override
    public void onTransactionFailed() {

        Toast.makeText(activity, "Transaction Failed", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onTransactionCancelled() {
        Toast.makeText(activity, "Transaction Cancelled", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onAppNotFound() {

    }
    private void makePayment(String amount, String upi, String name, String desc, String transactionId) {
        // on below line we are calling an easy payment method and passing
        // all parameters to it such as upi id,name, description and others.
        final EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder()
                .with(this)
                // on below line we are adding upi id.
                .setPayeeVpa(upi)
                // on below line we are setting name to which we are making oayment.
                .setPayeeName(name)
                // on below line we are passing transaction id.
                .setTransactionId(transactionId)
                // on below line we are passing transaction ref id.
                .setTransactionRefId(transactionId)
                // on below line we are adding description to payment.
                .setDescription(desc)
                // on below line we are passing amount which is being paid.
                .setAmount(amount)
                // on below line we are calling a build method to build this ui.
                .build();
        // on below line we are calling a start
        // payment method to start a payment.
        easyUpiPayment.startPayment();

        // on below line we are calling a set payment
        // status listener method to call other payment methods.
        easyUpiPayment.setPaymentStatusListener(this);
    }
}