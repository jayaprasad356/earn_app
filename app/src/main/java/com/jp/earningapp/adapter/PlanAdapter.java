package com.jp.earningapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.jp.earningapp.R;
import com.jp.earningapp.helper.ApiConfig;
import com.jp.earningapp.helper.Constant;
import com.jp.earningapp.helper.Session;
import com.jp.earningapp.model.Plan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final Activity activity;
    final ArrayList<Plan> plans;
    Session session;

    public PlanAdapter(Activity activity, ArrayList<Plan> plans) {
        this.activity = activity;
        this.plans = plans;
        session = new Session(activity);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.plan_lyt, parent, false);
        return new ItemHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {
        final ItemHolder holder = (ItemHolder) holderParent;
        final Plan plan = plans.get(position);

        holder.daily_income.setText("₹ "+plan.getDaily_income());
        holder.plan_price.setText("₹ "+plan.getPrice());
        holder.validation.setText(plan.getValid() + " Days");
        holder.Purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                purchasePlan(plan.getId(),plan.getDaily_income(),plan.getPrice(),plan.getValid());

            }
        });
    }

    private void purchasePlan(String plan_id, String daily_income, String price, String valid)
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        params.put(Constant.PLAN_ID,plan_id);
        params.put(Constant.PRICE,price);
        params.put(Constant.DAILY_INCOME,daily_income);
        params.put(Constant.VALID,valid);
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Toast.makeText(activity,jsonObject.getString(Constant.MESSAGE) , Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Log.d("PURCHASE_PLAN",jsonObject.getString(Constant.MESSAGE));
                        Toast.makeText(activity,jsonObject.getString(Constant.MESSAGE) , Toast.LENGTH_SHORT).show();


                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

        }, activity, Constant.PURCHASE_PLAN_URL, params,true);


    }


    @Override
    public int getItemCount() {
        return plans.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        final TextView daily_income,plan_price,validation;
        Button Purchase;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            daily_income = itemView.findViewById(R.id.daily_income);
            plan_price = itemView.findViewById(R.id.plan_price);
            validation = itemView.findViewById(R.id.validation);
            Purchase = itemView.findViewById(R.id.purchase);



        }
    }
}

