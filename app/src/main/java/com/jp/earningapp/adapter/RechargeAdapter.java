package com.jp.earningapp.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jp.earningapp.R;
import com.jp.earningapp.helper.ApiConfig;
import com.jp.earningapp.helper.Constant;
import com.jp.earningapp.helper.Session;
import com.jp.earningapp.model.Plan;
import com.jp.earningapp.model.Recharge;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RechargeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final Activity activity;
    final ArrayList<Recharge> recharges;
    Session session;

    public RechargeAdapter(Activity activity, ArrayList<Recharge> recharges) {
        this.activity = activity;
        this.recharges = recharges;
        session = new Session(activity);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.recharge_lyt, parent, false);
        return new ItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ItemHolder holder = (ItemHolder) holderParent;
        final Recharge recharge = recharges.get(position);

        holder.tvAmount.setText(recharge.getAmount());
        if (recharge.getStatus().equals("0")){
            holder.tvStatus.setText("Pending");

        }
        else {
            holder.tvStatus.setText("Received");
        }
        holder.tvType.setText(recharge.getPayment_type());
        holder.tvTime.setText(recharge.getDate_created());


    }



    @Override
    public int getItemCount() {
        return recharges.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        final TextView tvAmount,tvStatus,tvType,tvTime;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvType = itemView.findViewById(R.id.tvType);
            tvTime = itemView.findViewById(R.id.tvTime);



        }
    }
}

