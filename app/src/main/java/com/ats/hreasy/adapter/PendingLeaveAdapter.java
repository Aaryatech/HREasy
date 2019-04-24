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
import com.ats.hreasy.activity.LeaveHistoryDetailActivity;
import com.ats.hreasy.model.MyLeaveData;
import com.google.gson.Gson;

import java.util.ArrayList;

public class PendingLeaveAdapter extends RecyclerView.Adapter<PendingLeaveAdapter.MyViewHolder>{
    private ArrayList<MyLeaveData> pendingLeaveList;
    private Context context;

    public PendingLeaveAdapter(ArrayList<MyLeaveData> pendingLeaveList, Context context) {
        this.pendingLeaveList = pendingLeaveList;
        this.context = context;
    }

    @NonNull
    @Override
    public PendingLeaveAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_leave_history, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingLeaveAdapter.MyViewHolder myViewHolder, int i) {
        final MyLeaveData model = pendingLeaveList.get(i);
        myViewHolder.tvType.setText(model.getLvTitle());
        myViewHolder.tvDay.setText(""+model.getLeaveNumDays()+ " dayes");
        myViewHolder.tvDate.setText(""+model.getLeaveFromdt() + " to " + model.getLeaveTodt());
        if(model.getExInt1()==1) {
            myViewHolder.tvStatus.setText("Pending");
        }

        if(model.getLeaveDuration().equals("1"))
        {
            myViewHolder.tvDayType.setText("Full Day");
        }else {
            myViewHolder.tvDayType.setText("Half Day");
        }


        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(model);

                Intent intent=new Intent(context, LeaveHistoryDetailActivity.class);
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
        return pendingLeaveList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDate, tvType, tvDayType, tvDay,tvStatus;
        public CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvType = itemView.findViewById(R.id.tvLeaveType);
            tvDayType = itemView.findViewById(R.id.tvDayType);
            tvDay = itemView.findViewById(R.id.tvDays);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            cardView=itemView.findViewById(R.id.cardView);
        }
    }
}
