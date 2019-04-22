package com.ats.hreasy.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.hreasy.R;
import com.ats.hreasy.activity.HomeActivity;
import com.ats.hreasy.fragment.UpdateLeaveStatusFragment;
import com.ats.hreasy.model.LeaveAppTemp;
import com.google.gson.Gson;

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
        public LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            ivPhoto = view.findViewById(R.id.ivPhoto);
            tvEmpName = view.findViewById(R.id.tvEmpName);
            tvEmpDesg = view.findViewById(R.id.tvEmpDesg);
            tvDate = view.findViewById(R.id.tvDate);
            tvType = view.findViewById(R.id.tvLeaveType);
            tvDayType = view.findViewById(R.id.tvDayType);
            tvDay = view.findViewById(R.id.tvDays);
            linearLayout = view.findViewById(R.id.linearLayout);
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

        holder.tvEmpName.setText(model.getName());
        //holder.tvEmpDesg.setText(model.getName());
        holder.tvDate.setText(model.getFromDate() + " to " + model.getToDate());
        holder.tvType.setText(model.getType());
        holder.tvDay.setText(model.getDays() + " days");
        holder.tvDayType.setText("Full Day");

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson = new Gson();
                String json = gson.toJson(model);

                HomeActivity activity = (HomeActivity) context;

                Fragment adf = new UpdateLeaveStatusFragment();
                Bundle args = new Bundle();
                args.putString("model", json);
                adf.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "LeaveApprovalPendingFragment").commit();

            }
        });


    }

    @Override
    public int getItemCount() {
        return leaveList.size();
    }


}