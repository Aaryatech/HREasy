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

public class PendingClaimAdapter extends RecyclerView.Adapter<PendingClaimAdapter.MyViewHolder> {
    private ArrayList<ClaimHistoryTemp> pendingClaimList;
    private Context context;

    public PendingClaimAdapter(ArrayList<ClaimHistoryTemp> pendingClaimList, Context context) {
        this.pendingClaimList = pendingClaimList;
        this.context = context;
    }

    @NonNull
    @Override
    public PendingClaimAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_claim_history, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingClaimAdapter.MyViewHolder myViewHolder, int i) {
        final ClaimHistoryTemp model = pendingClaimList.get(i);
        myViewHolder.tvDate.setText(model.getDate());
        myViewHolder.tvClaimType.setText(model.getLeaveType());
        myViewHolder.tvProject.setText(model.getProjectType());
        myViewHolder.tvStatus.setText(model.getStatus());
        myViewHolder.tvAmount.setText("" + model.getAmt() + "/-");

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
        return pendingClaimList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  tvDate, tvClaimType, tvProject, tvAmount,tvStatus;
        public CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvClaimType = itemView.findViewById(R.id.tvClaimType);
            tvProject = itemView.findViewById(R.id.tvProjectName);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
