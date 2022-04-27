package com.jp.earningapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jp.earningapp.BonusActivity;
import com.jp.earningapp.MinorActivity;
import com.jp.earningapp.R;
import com.jp.earningapp.RechargeActivity;
import com.jp.earningapp.WithdrawalActivity;
import com.jp.earningapp.helper.Constant;
import com.jp.earningapp.helper.Session;

public class HomeFragment extends Fragment {

    RelativeLayout miner_layout;
    RelativeLayout recharge_layout;
    RelativeLayout withdrawal_layout;
    RelativeLayout activity_layout;
    RelativeLayout news_layout;
    TextView tvEarn,tvRecharge;
    Session session;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_home, container, false);

        miner_layout = rootview.findViewById(R.id.miner_layout);
        recharge_layout = rootview.findViewById(R.id.recharge_layout);
        withdrawal_layout = rootview.findViewById(R.id.withdrawal_layout);
        news_layout = rootview.findViewById(R.id.news_layout);
        activity_layout = rootview.findViewById(R.id.activity_layout);
        tvEarn = rootview.findViewById(R.id.tvEarn);
        tvRecharge = rootview.findViewById(R.id.tvRecharge);
        session = new Session(getActivity());

        tvEarn.setText(session.getData(Constant.EARN));
        miner_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MinorActivity.class);
                startActivity(intent);
            }
        });

        recharge_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RechargeActivity.class);
                startActivity(intent);
            }
        });
        tvRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RechargeActivity.class);
                startActivity(intent);
            }
        });

        withdrawal_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WithdrawalActivity.class);
                startActivity(intent);
            }
        });
        activity_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BonusActivity.class);
                startActivity(intent);
            }
        });

        news_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), MinorActivity.class);
//                startActivity(intent);
            }
        });

        return rootview;
    }

}