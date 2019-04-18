package com.ats.hreasy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ats.hreasy.R;
import com.ats.hreasy.model.LeaveAppTemp;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaveApprovalPendingAdapter extends RecyclerView.Adapter<LeaveApprovalPendingAdapter.MyViewHolder> {

    private ArrayList<LeaveAppTemp> leaveList;
    private Context context;

    public LeaveApprovalPendingAdapter(ArrayList<LeaveAppTemp> leaveList, Context context) {
        this.leaveList = leaveList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView ivPhoto;
        public TextView tvEmpName, tvEmpDesg, tvDate, tvType, tvDayType, tvDay;

        public MyViewHolder(View view) {
            super(view);
            ivPhoto = view.findViewById(R.id.ivPhoto);
            tvEmpName = view.findViewById(R.id.tvEmpName);
            tvEmpDesg = view.findViewById(R.id.tvEmpDesg);
            tvDate = view.findViewById(R.id.tvDate);
            tvType = view.findViewById(R.id.tvLeaveType);
            tvDayType = view.findViewById(R.id.tvDayType);
            tvDay = view.findViewById(R.id.tvDays);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_leave_approval_pending, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final LeaveAppTemp model = leaveList.get(position);

//        holder.tvQue.setHtml(model.getFaqQue());
//        holder.tvAns.setHtml(model.getFaqAns());


    }

    @Override
    public int getItemCount() {
        return leaveList.size();
    }


}