package com.ats.hreasy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ats.hreasy.R;
import com.ats.hreasy.model.BalanceLeaveModel;

import java.util.ArrayList;

public class BalanceLeaveAdapter extends RecyclerView.Adapter<BalanceLeaveAdapter.MyViewHolder> {
    private ArrayList<BalanceLeaveModel> balanceList;
    private Context context;

    public BalanceLeaveAdapter(ArrayList<BalanceLeaveModel> balanceList, Context context) {
        this.balanceList = balanceList;
        this.context = context;
    }

    @NonNull
    @Override
    public BalanceLeaveAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.balance_list_adapter, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BalanceLeaveAdapter.MyViewHolder myViewHolder, int i) {
        BalanceLeaveModel model = balanceList.get(i);
        myViewHolder.tv_leaveType.setText("" + model.getLvTitle());
        myViewHolder.tv_leaveCount.setText("" + ((model.getBalLeave() + model.getLvsAllotedLeaves()) - model.getSactionLeave()));

        myViewHolder.tvOB.setText("" + model.getBalLeave());
        myViewHolder.tvEarned.setText("" + model.getLvsAllotedLeaves());
        myViewHolder.tvSanction.setText("" + model.getSactionLeave());


    }

    @Override
    public int getItemCount() {
        return balanceList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_leaveType, tv_leaveCount, tvOB, tvEarned, tvSanction;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_leaveType = itemView.findViewById(R.id.tv_leaveType);
            tv_leaveCount = itemView.findViewById(R.id.tv_leaveCount);

            tvOB = itemView.findViewById(R.id.tvOB);
            tvEarned = itemView.findViewById(R.id.tvEarned);
            tvSanction = itemView.findViewById(R.id.tvSanction);

        }
    }
}
