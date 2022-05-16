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
import com.lsa.ayu.model.Bonus;

import java.util.ArrayList;

public class BonusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final Activity activity;
    final ArrayList<Bonus> bonuses;
    Session session;

    public BonusAdapter(Activity activity, ArrayList<Bonus> bonuses) {
        this.activity = activity;
        this.bonuses = bonuses;
        session = new Session(activity);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.bonus_lyt, parent, false);
        return new ItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ItemHolder holder = (ItemHolder) holderParent;
        final Bonus bonus = bonuses.get(position);

        holder.tvBonus.setText(bonus.getBonus_amount());
        holder.tvName.setText(bonus.getName());
        String s1 = bonus.getMobile().substring(0,2);
        String s2 = bonus.getMobile().substring(8,10);

        holder.tvMobile.setText(s1+"******"+s2);
        holder.tvLevel.setText(bonus.getLevel());
        holder.tvTime.setText(bonus.getDate_created());


    }



    @Override
    public int getItemCount() {
        return bonuses.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        final TextView tvBonus,tvName,tvMobile,tvLevel,tvTime;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tvBonus = itemView.findViewById(R.id.tvBonus);
            tvName = itemView.findViewById(R.id.tvName);
            tvMobile = itemView.findViewById(R.id.tvMobile);
            tvLevel = itemView.findViewById(R.id.tvLevel);
            tvTime = itemView.findViewById(R.id.tvTime);



        }
    }
}

