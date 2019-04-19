package com.ats.hreasy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.ats.hreasy.R;
import com.ats.hreasy.model.LeavePendingTemp;
import com.google.gson.Gson;

public class LeavePendingDetailActivity extends AppCompatActivity {
    LeavePendingTemp leavePendingTemp;
    public TextView tvLeaveType,tvDayesType,tvDayes,tvDate,tvStatus,tvEmpRemark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_pending_detail);

        tvLeaveType=(TextView)findViewById(R.id.tvLeaveType);
        tvDayesType=(TextView)findViewById(R.id.tvDayType);
        tvDayes=(TextView)findViewById(R.id.tvDays);
        tvDate=(TextView)findViewById(R.id.tvDate);
        tvStatus=(TextView)findViewById(R.id.tvStatus);
        tvEmpRemark=(TextView)findViewById(R.id.tvEmpRemark);

        String upcomingStr = getIntent().getStringExtra("model");
        Gson gson = new Gson();
        leavePendingTemp = gson.fromJson(upcomingStr, LeavePendingTemp.class);
        Log.e("responce", "-----------------------" + leavePendingTemp);

        tvLeaveType.setText(leavePendingTemp.getLeaveType());
        tvDayesType.setText(leavePendingTemp.getDayType());
        tvDayes.setText(leavePendingTemp.getDayes());
        tvDate.setText(leavePendingTemp.getDate());
        tvStatus.setText(leavePendingTemp.getStatus());
    }
}
