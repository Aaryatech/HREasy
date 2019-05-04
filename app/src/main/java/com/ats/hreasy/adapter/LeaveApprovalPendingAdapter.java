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
import com.ats.hreasy.constant.Constants;
import com.ats.hreasy.fragment.UpdateLeaveInfoFragment;
import com.ats.hreasy.fragment.UpdateLeaveStatusFragment;
import com.ats.hreasy.model.LeaveApp;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaveApprovalPendingAdapter extends RecyclerView.Adapter<LeaveApprovalPendingAdapter.MyViewHolder> {

    private ArrayList<LeaveApp> leaveList;
    private Context context;
    private String type;


    public LeaveApprovalPendingAdapter(ArrayList<LeaveApp> leaveList, Context context, String type) {
        this.leaveList = leaveList;
        this.context = context;
        this.type = type;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView ivPhoto;
        public TextView tvEmpName, tvEmpDesg, tvDate, tvType, tvDayType, tvDay, tvStatus;
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
            tvStatus = view.findViewById(R.id.tvStatus);
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
        final LeaveApp model = leaveList.get(position);

        holder.tvEmpName.setText(model.getEmpName());
        //holder.tvEmpDesg.setText(model.getName());
//        holder.tvDate.setText(model.getLeaveFromdt() + " to " + model.getLeaveTodt());
        holder.tvType.setText(model.getLeaveTitle());
        holder.tvDay.setText(model.getLeaveNumDays() + " days");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date d1 = sdf.parse(model.getLeaveFromdt());
            Date d2 = sdf.parse(model.getLeaveTodt());

            holder.tvDate.setText("" + sdf1.format(d1.getTime()) + " to " + sdf1.format(d2.getTime()));

        } catch (ParseException e) {
            e.printStackTrace();
        }


        String imageUri = String.valueOf(model.getEmpPhoto());
        try {
            Picasso.with(context).load(Constants.IMAGE_URL + "" + imageUri).placeholder(context.getResources().getDrawable(R.drawable.profile)).into(holder.ivPhoto);

        } catch (Exception e) {

        }

        if (model.getLeaveDuration().equals("1")) {
            holder.tvDayType.setText("Half Day");
        } else {
            holder.tvDayType.setText("Full Day");
        }


        if (type.equalsIgnoreCase("pending")) {

            if (model.getExInt1() == 1) {
                holder.tvStatus.setText("Initial Pending");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            } else if (model.getExInt1() == 2) {
                holder.tvStatus.setText("Final Pending");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            } else if (model.getExInt1() == 3) {
                holder.tvStatus.setText("Final Approved");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorApproved));
            } else if (model.getExInt1() == 8) {
                holder.tvStatus.setText("Initial Rejected");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorRejected));
            } else if (model.getExInt1() == 9) {
                holder.tvStatus.setText("Final Rejected");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorRejected));
            } else if (model.getExInt1() == 7) {
                holder.tvStatus.setText("Leave Cancelled");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }

        } else if (type.equalsIgnoreCase("info")) {

            if (model.getExInt1() == 1) {
                holder.tvStatus.setText("Initial Pending & Final Pending");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            } else if (model.getExInt1() == 2) {
                holder.tvStatus.setText("Final Pending");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            } else if (model.getExInt1() == 3) {
                holder.tvStatus.setText("Final Approved");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorApproved));
            } else if (model.getExInt1() == 8) {
                holder.tvStatus.setText("Initial Rejected");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorRejected));
            } else if (model.getExInt1() == 9) {
                holder.tvStatus.setText("Final Rejected");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorRejected));
            } else if (model.getExInt1() == 7) {
                holder.tvStatus.setText("Leave Cancelled");
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }

        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson = new Gson();
                String json = gson.toJson(model);

                Gson gson1 = new Gson();
                String json1 = gson1.toJson(leaveList);

                HomeActivity activity = (HomeActivity) context;

                if (type.equalsIgnoreCase("pending")) {
                    Fragment adf = new UpdateLeaveStatusFragment();
                    Bundle args = new Bundle();
                    args.putString("model", json);
                    args.putString("modelList", json1);
                    adf.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "LeaveApprovalPendingFragment").commit();

                } else if (type.equalsIgnoreCase("info")) {
                    Fragment adf = new UpdateLeaveInfoFragment();
                    Bundle args = new Bundle();
                    args.putString("model", json);
                    args.putString("modelList", json1);
                    adf.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "LeaveApprovalPendingFragment").commit();

                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return leaveList.size();
    }


}