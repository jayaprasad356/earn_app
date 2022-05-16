package com.lsa.ayu.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lsa.ayu.BonusActivity;
import com.lsa.ayu.MinorActivity;
import com.lsa.ayu.R;
import com.lsa.ayu.RechargeActivity;
import com.lsa.ayu.ReferEarnActivity;
import com.lsa.ayu.WithdrawalListActivity;
import com.lsa.ayu.helper.Constant;
import com.lsa.ayu.helper.Session;

public class HomeFragment extends Fragment {

    RelativeLayout miner_layout;
    RelativeLayout recharge_layout;
    RelativeLayout withdrawal_layout;
    RelativeLayout activity_layout,refer_layout;
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
        refer_layout = rootview.findViewById(R.id.refer_layout);
        tvEarn = rootview.findViewById(R.id.tvEarn);
        tvRecharge = rootview.findViewById(R.id.tvRecharge);
        session = new Session(getActivity());
        if (session.getData(Constant.EARN).equals("")){
            tvEarn.setText("0");

        }else {
            tvEarn.setText(session.getData(Constant.EARN));
        }


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
        refer_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReferEarnActivity.class);
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
                Intent intent = new Intent(getActivity(), WithdrawalListActivity.class);
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