package com.ats.hreasy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ats.hreasy.R;
import com.ats.hreasy.model.ClaimHistoryModel;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import static com.ats.hreasy.fragment.ClaimFragment.staticEmpClaimModel;

public class ClaimHistoryActivity extends AppCompatActivity {
    ClaimHistoryModel claimHistoryTemp;
    private TextView tvProject,tvClaimType,tvDate,tvAmount,tvRemark,tvStatus,tv_empName,tv_empDesignation;
    private ImageView tvPhoto1,ivPhoto2,ivPhoto3,iv_empPhoto;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_history);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        setTitle("Claim History");

        tvProject=(TextView)findViewById(R.id.tvProject);
        tvClaimType=(TextView)findViewById(R.id.tvClaimType);
        tvDate=(TextView)findViewById(R.id.tvDate);
        tvAmount=(TextView)findViewById(R.id.tvAmount);
        tvRemark=(TextView)findViewById(R.id.tvRemark);
        tvStatus=(TextView)findViewById(R.id.tvStatus);

        tv_empName=(TextView)findViewById(R.id.tvEmpName);
        tv_empDesignation=(TextView)findViewById(R.id.tvEmpDesg);
        iv_empPhoto=(ImageView)findViewById(R.id.ivPhoto);

        try {
            if (staticEmpClaimModel != null) {

                tv_empName.setText("" + staticEmpClaimModel.getEmpFname() + " " + staticEmpClaimModel.getEmpMname() + " " + staticEmpClaimModel.getEmpSname());
                tv_empDesignation.setText("" + staticEmpClaimModel.getEmpMobile1());
                String imageUri = String.valueOf(staticEmpClaimModel.getEmpPhoto());
                try {
                    Picasso.with(getApplicationContext()).load(imageUri).placeholder(getApplicationContext().getResources().getDrawable(R.drawable.profile)).into(iv_empPhoto);

                } catch (Exception e) {
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        String upcomingStr = getIntent().getStringExtra("model");
        Gson gson = new Gson();
        claimHistoryTemp = gson.fromJson(upcomingStr, ClaimHistoryModel.class);
        Log.e("responce", "-----------------------" + claimHistoryTemp);

        tvProject.setText(claimHistoryTemp.getProjectTypeTitle());
        tvClaimType.setText(claimHistoryTemp.getClaimTypeTitle());
        tvDate.setText(claimHistoryTemp.getClaimDate());
        tvAmount.setText(""+claimHistoryTemp.getClaimAmount());
        tvRemark.setText(claimHistoryTemp.getClaimRemarks());

        if (claimHistoryTemp.getExInt1() == 1) {
            tvStatus.setText("Initial Pending");
            tvStatus.setTextColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));
        } else if (claimHistoryTemp.getExInt1() == 2) {
            tvStatus.setText("Final Pending");
            tvStatus.setTextColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));
        } else if (claimHistoryTemp.getExInt1() == 3) {
            tvStatus.setText("Final Approved");
            tvStatus.setTextColor(getApplicationContext().getResources().getColor(R.color.colorApproved));
        } else if (claimHistoryTemp.getExInt1() == 8) {
            tvStatus.setText("Initial Rejected");
            tvStatus.setTextColor(getApplicationContext().getResources().getColor(R.color.colorRejected));
        } else if (claimHistoryTemp.getExInt1() == 9) {
            tvStatus.setText("Final Rejected");
            tvStatus.setTextColor(getApplicationContext().getResources().getColor(R.color.colorRejected));
        } else if (claimHistoryTemp.getExInt1() == 7) {
            tvStatus.setText("Leave Cancelled");
            tvStatus.setTextColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));
        }

        recyclerView=findViewById(R.id.recyclerView);

      /*  LeaveTrailTemp temp1 = new LeaveTrailTemp(1, "Anmol Shirke", "Claim rejected price is not appropriate", "Rejected", "15 APR 2019");
        LeaveTrailTemp temp2 = new LeaveTrailTemp(2, "Amit Patil", "Claim approved", "Approved", "16 APR 2019");

        ArrayList<LeaveTrailTemp> leaveTrailTemps=new ArrayList<>();
        leaveTrailTemps.add(temp1);
        leaveTrailTemps.add(temp2);

        LeaveTrailAdapter adapter = new LeaveTrailAdapter(leaveTrailTemps, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
*/
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
