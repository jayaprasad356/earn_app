package com.lsa.ayu.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lsa.ayu.R;
import com.lsa.ayu.helper.Session;
import com.lsa.ayu.model.PurchasedPlan;

import java.util.ArrayList;

public class PurchasedPlanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final Activity activity;
    final ArrayList<PurchasedPlan> purchasedPlans;
    Session session;

    public PurchasedPlanAdapter(Activity activity, ArrayList<PurchasedPlan> purchasedPlans) {
        this.activity = activity;
        this.purchasedPlans = purchasedPlans;
        session = new Session(activity);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.purchased_plan_lyt, parent, false);
        return new ItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ItemHolder holder = (ItemHolder) holderParent;
        final PurchasedPlan purchasedPlan = purchasedPlans.get(position);
        holder.tvPlanname.setText(purchasedPlan.getName());
        Glide.with(activity).load(purchasedPlan.getImage()).into(holder.imgPlan);

        holder.daily_income.setText("₹ "+purchasedPlan.getDaily_income());
        holder.plan_price.setText("₹ "+purchasedPlan.getPrice());
        holder.validation.setText(purchasedPlan.getValid() + " Days");
        holder.start_date.setText(purchasedPlan.getStart_date());
        holder.end_date.setText(purchasedPlan.getEnd_date());
        holder.served_time.setText(purchasedPlan.getServed_time() + "/" + purchasedPlan.getValid());
        holder.earned_amt.setText(purchasedPlan.getEarned_amt());
    }




    @Override
    public int getItemCount() {
        return purchasedPlans.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        TextView daily_income,plan_price,validation,start_date,end_date,served_time,earned_amt;
        ImageView imgPlan;
        TextView tvPlanname;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            daily_income = itemView.findViewById(R.id.daily_income);
            plan_price = itemView.findViewById(R.id.plan_price);
            validation = itemView.findViewById(R.id.validation);
            tvPlanname = itemView.findViewById(R.id.tvPlanname);
            imgPlan = itemView.findViewById(R.id.imgPlan);
            start_date = itemView.findViewById(R.id.start_date);
            end_date = itemView.findViewById(R.id.end_date);
            served_time = itemView.findViewById(R.id.served_time);
            earned_amt = itemView.findViewById(R.id.earned_amt);



        }
    }
}

