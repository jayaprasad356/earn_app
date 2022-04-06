package com.jp.earningapp.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jp.earningapp.LoginActivity;
import com.jp.earningapp.R;
import com.jp.earningapp.TransactionDetailsActivity;
import com.jp.earningapp.helper.Constant;
import com.jp.earningapp.helper.Session;

public class TeamFragment extends Fragment {

    TextView code_txt;
    Session session;


    public TeamFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_team, container, false);

        code_txt = rootview.findViewById(R.id.code_txt);
        session = new Session(getActivity());


        code_txt.setText(session.getData(Constant.MY_REFER_CODE));

        code_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("TextView", code_txt.getText().toString());
                clipboard.setPrimaryClip(clip);

                clip.getDescription();

                Toast.makeText(getActivity(), "Copied", Toast.LENGTH_SHORT).show();
            }
        });



        return rootview;
    }

}