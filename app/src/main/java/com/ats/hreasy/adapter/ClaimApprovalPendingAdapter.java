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
import com.ats.hreasy.fragment.UpdateClaimStatusFragment;
import com.ats.hreasy.fragment.UpdateLeaveStatusFragment;
import com.ats.hreasy.model.ClaimAppTemp;
import com.ats.hreasy.model.LeaveAppTemp;
import com.google.gson.Gson;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClaimApprovalPendingAdapter extends RecyclerView.Adapter<ClaimApprovalPendingAdapter.MyViewHolder> {

    private ArrayList<ClaimAppTemp> claimList;
    private Context context;

    public ClaimApprovalPendingAdapter(ArrayList<ClaimAppTemp> claimList, Context context) {
        this.claimList = claimList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView ivPhoto;
        public TextView tvEmpName, tvEmpDesg, tvDate, tvClaimType, tvProject, tvAmount;
        public LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            ivPhoto = view.findViewById(R.id.ivPhoto);
            tvEmpName = view.findViewById(R.id.tvEmpName);
            tvEmpDesg = view.findViewById(R.id.tvEmpDesg);
            tvDate = view.findViewById(R.id.tvDate);
            tvClaimType = view.findViewById(R.id.tvClaimType);
            tvProject = view.findViewById(R.id.tvProject);
            tvAmount = view.findViewById(R.id.tvAmount);
            linearLayout = view.findViewById(R.id.linearLayout);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_claim_approval_pending, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ClaimAppTemp model = claimList.get(position);

        holder.tvEmpName.setText(model.getEmpName());
        //holder.tvEmpDesg.setText(model.getName());
        holder.tvDate.setText(model.getDate());
        holder.tvClaimType.setText(model.getClaimType());
        holder.tvProject.setText(model.getProject());
        holder.tvAmount.setText("" + model.getAmount() + "/-");

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson = new Gson();
                String json = gson.toJson(model);

                HomeActivity activity = (HomeActivity) context;

//                Fragment adf = new UpdateClaimStatusFragment();
//                Bundle args = new Bundle();
//                args.putString("model", json);
//                adf.setArguments(args);
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "ClaimApprovalPendingFragment").commit();

            }
        });


    }

    @Override
    public int getItemCount() {
        return claimList.size();
    }
}
