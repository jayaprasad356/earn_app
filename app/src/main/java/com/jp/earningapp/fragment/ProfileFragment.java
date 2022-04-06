package com.jp.earningapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jp.earningapp.MainActivity;
import com.jp.earningapp.MinorActivity;
import com.jp.earningapp.R;
import com.jp.earningapp.Recharge_History_Activity;
import com.jp.earningapp.TransactionDetailsActivity;
import com.jp.earningapp.UPI_Information_Activity;
import com.jp.earningapp.WithdrawalActivity;
import com.jp.earningapp.helper.Constant;
import com.jp.earningapp.helper.Session;


public class ProfileFragment extends Fragment {

    RelativeLayout relayout_1,withdrawal_layout,miner_layout,recharge_layout;
    Session session;
    TextView nadila_txt;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_profile, container, false);

        relayout_1 = rootview.findViewById(R.id.relayout_1);
        session = new Session(getActivity());
        recharge_layout = rootview.findViewById(R.id.recharge_layout);
        withdrawal_layout = rootview.findViewById(R.id.withdrawal_layout);
        miner_layout = rootview.findViewById(R.id.miner_layout);
        nadila_txt = rootview.findViewById(R.id.nadila_txt);

        nadila_txt.setText(session.getData(Constant.NAME));

        withdrawal_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WithdrawalActivity.class);
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

        recharge_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Recharge_History_Activity.class);
                startActivity(intent);
            }
        });

        return rootview;
    }

}