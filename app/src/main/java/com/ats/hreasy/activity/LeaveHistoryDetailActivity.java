package com.ats.hreasy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.ats.hreasy.R;
import com.ats.hreasy.model.LeaveHistoryTemp;
import com.google.gson.Gson;

public class LeaveHistoryDetailActivity extends AppCompatActivity {
    LeaveHistoryTemp leaveHistoryTemp;
    public TextView tvLeaveType,tvDayesType,tvDayes,tvDate,tvStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_history_detail);

        tvLeaveType=(TextView)findViewById(R.id.tvLeaveType);
        tvDayesType=(TextView)findViewById(R.id.tvDayType);
        tvDayes=(TextView)findViewById(R.id.tvDays);
        tvDate=(TextView)findViewById(R.id.tvDate);
        tvStatus=(TextView)findViewById(R.id.tvStatus);

        String upcomingStr = getIntent().getStringExtra("model");
        Gson gson = new Gson();
        leaveHistoryTemp = gson.fromJson(upcomingStr, LeaveHistoryTemp.class);
        Log.e("responce", "-----------------------" + leaveHistoryTemp);

        tvLeaveType.setText(leaveHistoryTemp.getLeaveType());
        tvDayesType.setText(leaveHistoryTemp.getDayType());
        tvDayes.setText(leaveHistoryTemp.getDayes());
        tvDate.setText(leaveHistoryTemp.getDate());
        tvStatus.setText(leaveHistoryTemp.getStatus());
    }
}
