package com.lsa.ayu.helper;


import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;


import com.lsa.ayu.R;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class PaymentModelClass {
    public final Activity activity;
    final String TAG = "PAYMENT_MODE";
    public PayUmoneySdkInitializer.PaymentParam mPaymentParams;
    public String status, udf5, udf4, udf3, udf2, udf1, email, firstName, productInfo, amount, txnId, key, addedOn, msg, Product, address;
    public static Map<String, String> sendParams;
    ProgressDialog mProgressDialog;

    public PaymentModelClass(Activity activity) {
        this.activity = activity;
    }

    //this method calculate hash from sdk
    public static String hashCal(String type, String hashString) {
        StringBuilder hash = new StringBuilder();
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(type);
            messageDigest.update(hashString.getBytes());
            byte[] bytes = messageDigest.digest();
            for (byte hashByte : bytes) {
                hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash.toString();
    }

    public static String hashCal1(String str) {
        byte[] hashsets = str.getBytes();
        StringBuilder hexString = new StringBuilder();

        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashsets);
            byte[] messageDigest = algorithm.digest();
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

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(activity);
            mProgressDialog.setMessage(activity.getString(R.string.processing));
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void OnPayClick(Activity activity, String OrderType, String amount) {
        try {

           // PaymentModelClass.sendParams = sendParams;

            PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();

            //Use this to set your custom text on result screen button
            payUmoneyConfig.setDoneButtonText("Done");
            //Use this to set your custom title for the activity
            payUmoneyConfig.setPayUmoneyActivityTitle(activity.getResources().getString(R.string.app_name));
            PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();
            String txnId = System.currentTimeMillis() + "";
            String phone = "9876543210";
            String firstName = "Prasad";
            String email = "jp@gmail.com";
            String udf1 = "";
            String udf2 = "";
            String udf3 = "";
            String udf4 = "";
            String udf5 = "";
            String udf6 = "";
            String udf7 = "";
            String udf8 = "";
            String udf9 = "";
            String udf10 = "";
            AppEnvironment appEnvironment = ((ApiConfig) activity.getApplication()).getAppEnvironment();
            //builder.setAmount(amount)
            builder.setAmount(amount)
                    .setTxnId(txnId)
                    .setPhone(phone)
                    .setProductName(OrderType)
                    .setFirstName(firstName)
                    .setEmail(email)
                    .setsUrl("https://www.payumoney.com/mobileapp/payumoney/success.php")     // Success URL (surl)
                    .setfUrl("https://www.payumoney.com/mobileapp/payumoney/failure.php")
                    .setUdf1(udf1)
                    .setUdf2(udf2)
                    .setUdf3(udf3)
                    .setUdf4(udf4)
                    .setUdf5(udf5)
                    .setUdf6(udf6)
                    .setUdf7(udf7)
                    .setUdf8(udf8)
                    .setUdf9(udf9)
                    .setUdf10(udf10)
                    .setIsDebug(appEnvironment.debug())
                    .setKey(appEnvironment.merchant_Key())
                    .setMerchantId(appEnvironment.merchant_ID());
            try {
                mPaymentParams = builder.build();
                mPaymentParams = calculateServerSideHashAndInitiatePayment1(mPaymentParams);
                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, activity, R.style.AppTheme, true);
                // generateHashFromServer(mPaymentParams);

            } catch (Exception e) {
                Toast.makeText(activity, "build " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public PayUmoneySdkInitializer.PaymentParam calculateServerSideHashAndInitiatePayment1(final PayUmoneySdkInitializer.PaymentParam paymentParam) {
        StringBuilder stringBuilder = new StringBuilder();
        HashMap<String, String> params = paymentParam.getParams();
        try {
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

            AppEnvironment appEnvironment = ((ApiConfig) activity.getApplication()).getAppEnvironment();
            stringBuilder.append(appEnvironment.salt());

            String hash = hashCal("SHA-512", stringBuilder.toString());
            paymentParam.setMerchantHash(hash);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return paymentParam;
    }


}