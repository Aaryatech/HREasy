package com.ats.hreasy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.ats.hreasy.R;
import com.ats.hreasy.adapter.LeaveTrailListAdapter;
import com.ats.hreasy.model.LeaveTrailTemp;
import com.ats.hreasy.model.Login;
import com.ats.hreasy.model.MyLeaveData;
import com.ats.hreasy.utils.CustomSharedPreference;
import com.google.gson.Gson;

import java.util.ArrayList;

public class LeaveHistoryDetailActivity extends AppCompatActivity {
    MyLeaveData leaveHistory;
    public TextView tvLeaveType, tvDayesType, tvDayes, tvDate, tvStatus, tvEmpRemark,tvEmpName,tvEmpDesignation;
    private RecyclerView recyclerView;
    Login loginUser;

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
        tvEmpName = (TextView) findViewById(R.id.tvEmpName);
        tvEmpDesignation = (TextView) findViewById(R.id.tvEmpDesg);
        recyclerView = findViewById(R.id.recyclerView);

        String userStr = CustomSharedPreference.getString(getApplicationContext(), CustomSharedPreference.KEY_USER);
        Gson gson = new Gson();
        loginUser = gson.fromJson(userStr, Login.class);
        Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);

        tvEmpName.setText(loginUser.getEmpFname()+ " " +loginUser.getEmpSname());

        String upcomingStr = getIntent().getStringExtra("model");
        leaveHistory = gson.fromJson(upcomingStr, MyLeaveData.class);
        Log.e("responce", "-----------------------" + leaveHistory);


        if(leaveHistory!=null) {
            tvLeaveType.setText(leaveHistory.getLvTitle());
            tvDayes.setText(leaveHistory.getLeaveNumDays()+ " days");
            tvDate.setText(leaveHistory.getLeaveFromdt() + " to " + leaveHistory.getLeaveTodt());
            tvEmpRemark.setText(leaveHistory.getLeaveEmpReason());
            if(leaveHistory.getExInt1()==1) {
                tvStatus.setText("Pending");
            }
            if(leaveHistory.getLeaveDuration().equals("1"))
            {
                tvDayesType.setText("Full Day");
            }else {
                tvDayesType.setText("Half Day");
            }

        }


        LeaveTrailTemp temp1 = new LeaveTrailTemp(1, "Anmol Shirke", "Leave rejected because you already taken leave this month", "Rejected", "15 APR 2019");
        LeaveTrailTemp temp2 = new LeaveTrailTemp(2, "Amit Patil", "Leave approved", "Approved", "16 APR 2019");

        ArrayList<MyLeaveData> leaveTrailTemps=new ArrayList<>();
        leaveTrailTemps.add(leaveHistory);
        //leaveTrailTemps.add(temp2);

        LeaveTrailListAdapter adapter = new LeaveTrailListAdapter(leaveTrailTemps, this);
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
