package com.lsa.ayu.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lsa.ayu.BuildConfig;
import com.lsa.ayu.FaqActivity;
import com.lsa.ayu.MinorActivity;
import com.lsa.ayu.R;
import com.lsa.ayu.Recharge_History_Activity;
import com.lsa.ayu.UPI_Information_Activity;
import com.lsa.ayu.WithdrawalListActivity;
import com.lsa.ayu.helper.ApiConfig;
import com.lsa.ayu.helper.Constant;
import com.lsa.ayu.helper.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment {

    RelativeLayout relayout_1,withdrawal_layout,miner_layout,recharge_layout,relayout_2,share_re;
    Session session;
    TextView nadila_txt,tvBalance;
    TextView tvPurchasedPlans,tvTodayProfit,tvTotalProfit,tvTodayProfit2;
    View rootview;
    Activity activity;
    RelativeLayout logout;



    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_profile, container, false);

        relayout_1 = rootview.findViewById(R.id.relayout_1);
        relayout_2 = rootview.findViewById(R.id.relayout_2);
        share_re = rootview.findViewById(R.id.share_re);
        tvBalance = rootview.findViewById(R.id.tvBalance);
        logout = rootview.findViewById(R.id.logout);
        activity = getActivity();
        session = new Session(getActivity());

        recharge_layout = rootview.findViewById(R.id.recharge_layout);
        withdrawal_layout = rootview.findViewById(R.id.withdrawal_layout);
        miner_layout = rootview.findViewById(R.id.miner_layout);
        nadila_txt = rootview.findViewById(R.id.nadila_txt);

        tvPurchasedPlans = rootview.findViewById(R.id.tvPurchasedPlans);
        tvTodayProfit = rootview.findViewById(R.id.tvTodayProfit);
        tvTotalProfit = rootview.findViewById(R.id.tvTotalProfit);
        tvTodayProfit2 = rootview.findViewById(R.id.tvTodayProfit2);

        nadila_txt.setText(session.getData(Constant.NAME));
        tvBalance.setText(session.getData(Constant.BALANCE));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser(activity);
            }
        });

        withdrawal_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WithdrawalListActivity.class);
                startActivity(intent);

            }
        });
        miner_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MinorActivity.class);
                startActivity(intent);

            }
        });

        relayout_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UPI_Information_Activity.class);
                startActivity(intent);
            }
        });
        relayout_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FaqActivity.class);
                startActivity(intent);
            }
        });

        share_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
                    String shareMessage= "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });

        recharge_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Recharge_History_Activity.class);
                startActivity(intent);
            }
        });
        minorDetails();

        return rootview;
    }
    private void minorDetails()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        tvPurchasedPlans.setText(jsonObject.getString(Constant.PURCHASED_PLANS));
                        tvTodayProfit.setText(jsonObject.getString(Constant.TODAY_PROFIT));
                        tvTotalProfit.setText(jsonObject.getString(Constant.TOTAL_PROFIT));
                        tvTodayProfit2.setText(jsonObject.getString(Constant.TODAY_PROFIT));
                    }
                    else {
                        Log.d("MAINACTIVITY",jsonObject.getString(Constant.MESSAGE));

                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

        }, activity, Constant.MINER_URL, params,true);


    }

}