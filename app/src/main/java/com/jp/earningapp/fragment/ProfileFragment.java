package com.jp.earningapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jp.earningapp.R;
import com.jp.earningapp.Recharge_History_Activity;
import com.jp.earningapp.TransactionDetailsActivity;
import com.jp.earningapp.UPI_Information_Activity;


public class ProfileFragment extends Fragment {

    RelativeLayout relayout_1;
    RelativeLayout relayout_5;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_profile, container, false);

        relayout_1 = rootview.findViewById(R.id.relayout_1);
        relayout_5 = rootview.findViewById(R.id.relayout_5);

        relayout_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UPI_Information_Activity.class);
                startActivity(intent);
            }
        });

        relayout_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Recharge_History_Activity.class);
                startActivity(intent);
            }
        });

        return rootview;
    }

}