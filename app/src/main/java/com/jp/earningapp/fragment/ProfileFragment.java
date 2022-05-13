package com.jp.earningapp.fragment;

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

import com.jp.earningapp.AboutUsActivity;
import com.jp.earningapp.FaqActivity;
import com.jp.earningapp.MainActivity;
import com.jp.earningapp.MinorActivity;
import com.jp.earningapp.R;
import com.jp.earningapp.Recharge_History_Activity;
import com.jp.earningapp.TransactionDetailsActivity;
import com.jp.earningapp.UPI_Information_Activity;
import com.jp.earningapp.WithdrawalActivity;
import com.jp.earningapp.WithdrawalListActivity;
import com.jp.earningapp.helper.ApiConfig;
import com.jp.earningapp.helper.Constant;
import com.jp.earningapp.helper.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment {

    RelativeLayout relayout_1,withdrawal_layout,miner_layout,recharge_layout,relayout_2;
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