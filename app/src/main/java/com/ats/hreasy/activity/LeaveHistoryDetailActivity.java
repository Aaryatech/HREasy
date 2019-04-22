package com.ats.hreasy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.ats.hreasy.R;
import com.ats.hreasy.adapter.LeaveTrailAdapter;
import com.ats.hreasy.adapter.NotificationAdapter;
import com.ats.hreasy.model.LeaveHistoryTemp;
import com.ats.hreasy.model.LeaveTrailTemp;
import com.google.gson.Gson;

import java.util.ArrayList;

public class LeaveHistoryDetailActivity extends AppCompatActivity {
    LeaveHistoryTemp leaveHistoryTemp;
    public TextView tvLeaveType, tvDayesType, tvDayes, tvDate, tvStatus, tvEmpRemark;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_history_detail);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        setTitle("Leave History");

        tvLeaveType = (TextView) findViewById(R.id.tvLeaveType);
        tvDayesType = (TextView) findViewById(R.id.tvDayType);
        tvDayes = (TextView) findViewById(R.id.tvDays);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvEmpRemark = (TextView) findViewById(R.id.tvEmpRemark);

        recyclerView = findViewById(R.id.recyclerView);

        String upcomingStr = getIntent().getStringExtra("model");
        Gson gson = new Gson();
        leaveHistoryTemp = gson.fromJson(upcomingStr, LeaveHistoryTemp.class);
        Log.e("responce", "-----------------------" + leaveHistoryTemp);

        tvLeaveType.setText(leaveHistoryTemp.getLeaveType());
        tvDayesType.setText(leaveHistoryTemp.getDayType());
        tvDayes.setText(leaveHistoryTemp.getDayes());
        tvDate.setText(leaveHistoryTemp.getDate());
        tvStatus.setText(leaveHistoryTemp.getStatus());


        LeaveTrailTemp temp1 = new LeaveTrailTemp(1, "Anmol Shirke", "Leave rejected because you already taken leave thin month", "Rejected", "15 APR 2019");
        LeaveTrailTemp temp2 = new LeaveTrailTemp(2, "Amit Patil", "Leave approved", "Approved", "16 APR 2019");

        ArrayList<LeaveTrailTemp> leaveTrailTemps=new ArrayList<>();
        leaveTrailTemps.add(temp1);
        leaveTrailTemps.add(temp2);

        LeaveTrailAdapter adapter = new LeaveTrailAdapter(leaveTrailTemps, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
