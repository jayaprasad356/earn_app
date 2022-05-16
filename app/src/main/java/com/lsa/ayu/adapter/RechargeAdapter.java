package com.lsa.ayu.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lsa.ayu.R;
import com.lsa.ayu.helper.Session;
import com.lsa.ayu.model.Recharge;

import java.util.ArrayList;

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

