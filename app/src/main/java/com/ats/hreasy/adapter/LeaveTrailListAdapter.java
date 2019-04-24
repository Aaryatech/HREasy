package com.ats.hreasy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ats.hreasy.R;
import com.ats.hreasy.model.MyLeaveData;

import java.util.ArrayList;

    public class LeaveTrailListAdapter extends RecyclerView.Adapter<LeaveTrailListAdapter.MyViewHolder> {

        private ArrayList<MyLeaveData> msgList;
        private Context context;

        public LeaveTrailListAdapter(ArrayList<MyLeaveData> msgList, Context context) {
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
        public LeaveTrailListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_leave_trail, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(LeaveTrailListAdapter.MyViewHolder holder, int position) {
            final MyLeaveData model = msgList.get(position);

            holder.tvEmpName.setText(model.getGetLeaveStatusList().get(position).getEmpFname()+ " "+model.getGetLeaveStatusList().get(position).getEmpSname());
            holder.tvRemark.setText(model.getGetLeaveStatusList().get(position).getEmpRemarks());
            holder.tvDate.setText(model.getGetLeaveStatusList().get(position).getMakerEnterDatetime());

            if(model.getGetLeaveStatusList().get(position).getLeaveStatus()==1) {
                holder.tvStatus.setText("Pending");
            }else if(model.getGetLeaveStatusList().get(position).getLeaveStatus()==2)
            {
                holder.tvStatus.setText("Rejected");
            }

        }

        @Override
        public int getItemCount() {
            return msgList.size();
        }


}
