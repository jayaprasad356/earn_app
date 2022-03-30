package com.jp.earningapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jp.earningapp.R;
import com.jp.earningapp.TransactionDetailsActivity;

public class ShopFragment extends Fragment {
    RelativeLayout relative_1;

    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_shop, container, false);

        relative_1 = rootview.findViewById(R.id.relative_1);

        relative_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TransactionDetailsActivity.class);
                startActivity(intent);
            }
        });


        return rootview;
    }
}