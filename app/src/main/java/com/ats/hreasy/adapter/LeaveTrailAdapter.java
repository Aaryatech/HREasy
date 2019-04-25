package com.ats.hreasy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ats.hreasy.R;
import com.ats.hreasy.model.LeaveTrailTemp;
import com.ats.hreasy.model.MyLeaveTrailData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LeaveTrailAdapter extends RecyclerView.Adapter<LeaveTrailAdapter.MyViewHolder> {

    private ArrayList<MyLeaveTrailData> msgList;
    private Context context;

    public LeaveTrailAdapter(ArrayList<MyLeaveTrailData> msgList, Context context) {
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
        final MyLeaveTrailData model = msgList.get(position);

        holder.tvEmpName.setText(""+model.getEmpFname()+" "+model.getEmpMname()+" "+model.getEmpSname());
        holder.tvRemark.setText(model.getEmpRemarks());

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat sdf1=new SimpleDateFormat("dd MMM yyyy");

        try {
            Date date=sdf.parse(model.getMakerEnterDatetime());
            String dt=sdf1.format(date.getTime());
            holder.tvDate.setText(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        String leaveStatus="";
        if (model.getLeaveStatus()==1){
            leaveStatus="Pending";
        }else if (model.getLeaveStatus()==2){
            leaveStatus="FA Pending";
        }else if (model.getLeaveStatus()==3){
            leaveStatus="FA Approved";
        }else if (model.getLeaveStatus()==8){
            leaveStatus="IA Rejected";
        }else if (model.getLeaveStatus()==9){
            leaveStatus="FA Rejected";
        }else if (model.getLeaveStatus()==7){
            leaveStatus="Cancelled";
        }

        holder.tvStatus.setText(leaveStatus);


    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
