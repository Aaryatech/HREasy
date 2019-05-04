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
import com.ats.hreasy.constant.Constants;
import com.ats.hreasy.model.Login;
import com.ats.hreasy.model.MyLeaveData;
import com.ats.hreasy.model.MyLeaveTrailData;
import com.ats.hreasy.utils.CustomSharedPreference;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.ats.hreasy.fragment.ClaimFragment.staticEmpClaimModel;

public class LeaveDetailActivity extends AppCompatActivity {
    MyLeaveData leaveHistory;
    public TextView tvLeaveType, tvDayesType, tvDayes, tvDate, tvStatus, tvEmpRemark, tvEmpName, tvEmpDesignation;
    private RecyclerView recyclerView;
    Login loginUser;
    CircleImageView ivPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_leave_detail);
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

        ivPhoto = findViewById(R.id.ivPhoto);

        String userStr = CustomSharedPreference.getString(getApplicationContext(), CustomSharedPreference.KEY_USER);
        Gson gson = new Gson();
        loginUser = gson.fromJson(userStr, Login.class);
        Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);

        String upcomingStr = getIntent().getStringExtra("model");
        leaveHistory = gson.fromJson(upcomingStr, MyLeaveData.class);
        Log.e("responce", "-----------------------" + leaveHistory);
        tvEmpName.setText(leaveHistory.getEmpFname() + " " + leaveHistory.getEmpMname() + " " + leaveHistory.getEmpSname());

        if (leaveHistory != null) {
            tvLeaveType.setText(leaveHistory.getLvTitle());
            tvDayes.setText(leaveHistory.getLeaveNumDays() + " days");
           // tvDate.setText(leaveHistory.getLeaveFromdt() + " to " + leaveHistory.getLeaveTodt());
            tvEmpRemark.setText(leaveHistory.getLeaveEmpReason());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

            try {
                Date dateFrom = sdf.parse(leaveHistory.getLeaveFromdt());
                Date dateTo = sdf.parse(leaveHistory.getLeaveTodt());
                tvDate.setText("" + sdf1.format(dateFrom.getTime()) + " to " + sdf1.format(dateTo.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String imageUri = String.valueOf(leaveHistory.getEmpPhoto());
            try {
                Picasso.with(getApplicationContext()).load(Constants.IMAGE_URL + "" + imageUri).placeholder(getApplicationContext().getResources().getDrawable(R.drawable.profile)).into(ivPhoto);

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (leaveHistory.getExInt1() == 1) {
                tvStatus.setText("Initial Pending");
                tvStatus.setTextColor(this.getResources().getColor(R.color.colorPrimaryDark));
            } else if (leaveHistory.getExInt1() == 2) {
                tvStatus.setText("Final Pending");
                tvStatus.setTextColor(this.getResources().getColor(R.color.colorPrimaryDark));
            } else if (leaveHistory.getExInt1() == 3) {
                tvStatus.setText("Final Approved");
                tvStatus.setTextColor(this.getResources().getColor(R.color.colorApproved));
            } else if (leaveHistory.getExInt1() == 8) {
                tvStatus.setText("Initial Rejected");
                tvStatus.setTextColor(this.getResources().getColor(R.color.colorRejected));
            } else if (leaveHistory.getExInt1() == 9) {
                tvStatus.setText("Final Rejected");
                tvStatus.setTextColor(this.getResources().getColor(R.color.colorRejected));
            } else if (leaveHistory.getExInt1() == 7) {
                tvStatus.setText("Leave Cancelled");
                tvStatus.setTextColor(this.getResources().getColor(R.color.colorPrimaryDark));
            }

            if (leaveHistory.getLeaveDuration().equals("2")) {
                tvDayesType.setText("Full Day");
            } else {
                tvDayesType.setText("Half Day");
            }

        }


        if (leaveHistory.getGetLeaveStatusList() != null) {
            if (leaveHistory.getGetLeaveStatusList().size() > 0) {

                ArrayList<MyLeaveTrailData> leaveTrailTemps = new ArrayList<>();

                for (int i = 0; i < leaveHistory.getGetLeaveStatusList().size(); i++) {
                    leaveTrailTemps.add(leaveHistory.getGetLeaveStatusList().get(i));
                }

                LeaveTrailListAdapter adapter = new LeaveTrailListAdapter(leaveTrailTemps, this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);

            }
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
