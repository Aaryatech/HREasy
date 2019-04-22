package com.ats.hreasy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ats.hreasy.R;
import com.ats.hreasy.model.LeaveTrailTemp;

import java.util.ArrayList;

public class LeaveTrailAdapter extends RecyclerView.Adapter<LeaveTrailAdapter.MyViewHolder> {

    private ArrayList<LeaveTrailTemp> msgList;
    private Context context;

    public LeaveTrailAdapter(ArrayList<LeaveTrailTemp> msgList, Context context) {
        this.msgList = msgList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEmpName, tvRemark, tvDate,tvStatus;

        public MyViewHolder(View view) {
            super(view);
            tvEmpName = view.findViewById(R.id.tvEmpName);
            tvRemark = view.findViewById(R.id.tvRemark);
            tvStatus = view.findViewById(R.id.tvStatus);
            tvDate = view.findViewById(R.id.tvDate);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_leave_trail, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final LeaveTrailTemp model = msgList.get(position);

        holder.tvEmpName.setText(model.getEmpName());
        holder.tvRemark.setText(model.getRemark());
        holder.tvDate.setText(model.getDate());
        holder.tvStatus.setText(model.getStatus());


    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
