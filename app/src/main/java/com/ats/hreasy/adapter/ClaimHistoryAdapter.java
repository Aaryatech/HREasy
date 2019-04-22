package com.ats.hreasy.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ats.hreasy.R;
import com.ats.hreasy.activity.ClaimHistoryActivity;
import com.ats.hreasy.model.ClaimHistoryTemp;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ClaimHistoryAdapter extends RecyclerView.Adapter<ClaimHistoryAdapter.MyViewHolder>{
    private ArrayList<ClaimHistoryTemp> ClaimHistoryList;
    private Context context;

    public ClaimHistoryAdapter(ArrayList<ClaimHistoryTemp> claimHistoryList, Context context) {
        ClaimHistoryList = claimHistoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public ClaimHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_claim_history, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClaimHistoryAdapter.MyViewHolder myViewHolder, int i) {
        final ClaimHistoryTemp model = ClaimHistoryList.get(i);

        myViewHolder.tvType.setText(model.getLeaveType());
        myViewHolder.tvProjectName.setText(model.getProjectType());
        myViewHolder.tvAmount.setText(""+model.getAmt()+"/-");
        myViewHolder.tvDate.setText(model.getDate());

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(model);

                Intent intent=new Intent(context, ClaimHistoryActivity.class);
                Bundle args = new Bundle();
                args.putString("model", json);
                intent.putExtra("model", json);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ClaimHistoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDate, tvType, tvProjectName, tvAmount;
        public CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvType = itemView.findViewById(R.id.tvClaimType);
            tvProjectName = itemView.findViewById(R.id.tvProjectName);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            cardView=itemView.findViewById(R.id.cardView);
        }
    }
}
