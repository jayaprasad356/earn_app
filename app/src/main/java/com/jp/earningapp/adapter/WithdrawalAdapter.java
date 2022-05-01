package com.jp.earningapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jp.earningapp.R;
import com.jp.earningapp.helper.Session;
import com.jp.earningapp.model.Recharge;
import com.jp.earningapp.model.Withdrawal;

import java.util.ArrayList;

public class WithdrawalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final Activity activity;
    final ArrayList<Withdrawal> withdrawals;
    Session session;

    public WithdrawalAdapter(Activity activity, ArrayList<Withdrawal> withdrawals) {
        this.activity = activity;
        this.withdrawals = withdrawals;
        session = new Session(activity);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.withdrawal_lyt, parent, false);
        return new ItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ItemHolder holder = (ItemHolder) holderParent;
        final Withdrawal withdrawal = withdrawals.get(position);

        holder.tvAmount.setText(withdrawal.getAmount());
        holder.tvStatus.setText(withdrawal.getStatus());
        holder.tvTime.setText(withdrawal.getDate_created());


    }



    @Override
    public int getItemCount() {
        return withdrawals.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        final TextView tvAmount,tvStatus,tvTime;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvTime = itemView.findViewById(R.id.tvTime);



        }
    }
}

