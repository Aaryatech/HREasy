package com.ats.hreasy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.hreasy.R;
import com.ats.hreasy.adapter.ClaimAttachmentAdapter;
import com.ats.hreasy.adapter.ClaimTrailAdapter;
import com.ats.hreasy.constant.Constants;
import com.ats.hreasy.model.ClaimHistoryModel;
import com.ats.hreasy.model.ClaimProofList;
import com.ats.hreasy.model.ClaimTrailstatus;
import com.ats.hreasy.utils.CommonDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ats.hreasy.fragment.ClaimFragment.staticEmpClaimModel;

public class ClaimHistoryActivity extends AppCompatActivity {
    ClaimHistoryModel claimHistoryTemp;
    private TextView tvProject, tvClaimType, tvDate, tvAmount, tvRemark, tvStatus, tv_empName, tv_empDesignation;
    private ImageView tvPhoto1, ivPhoto2, ivPhoto3, iv_empPhoto;
    private RecyclerView recyclerView, rvAttachment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_history);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        setTitle("Claim History");

        tvProject = (TextView) findViewById(R.id.tvProject);
        tvClaimType = (TextView) findViewById(R.id.tvClaimType);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvAmount = (TextView) findViewById(R.id.tvAmount);
        tvRemark = (TextView) findViewById(R.id.tvRemark);
        tvStatus = (TextView) findViewById(R.id.tvStatus);

        tv_empName = (TextView) findViewById(R.id.tvEmpName);
        tv_empDesignation = (TextView) findViewById(R.id.tvEmpDesg);
        iv_empPhoto = (ImageView) findViewById(R.id.ivPhoto);

        recyclerView = findViewById(R.id.recyclerView);
        rvAttachment = findViewById(R.id.rvAttachment);

        try {
            if (staticEmpClaimModel != null) {

                tv_empName.setText("" + staticEmpClaimModel.getEmpFname() + " " + staticEmpClaimModel.getEmpMname() + " " + staticEmpClaimModel.getEmpSname());
                tv_empDesignation.setText("" + staticEmpClaimModel.getEmpMobile1());

                String imageUri = String.valueOf(staticEmpClaimModel.getEmpPhoto());
                try {
                    Picasso.with(getApplicationContext()).load(Constants.IMAGE_URL+""+imageUri).placeholder(getApplicationContext().getResources().getDrawable(R.drawable.profile)).into(iv_empPhoto);

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
        tvAmount.setText("" + claimHistoryTemp.getClaimAmount());
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


        getClaimTrail(claimHistoryTemp.getClaimId());
        getClaimProofList(claimHistoryTemp.getClaimId());


    }


    private void getClaimTrail(final Integer claimId) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(ClaimHistoryActivity.this)) {
            final CommonDialog commonDialog = new CommonDialog(ClaimHistoryActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<ClaimTrailstatus>> listCall = Constants.myInterface.getClaimTrail(authHeader, claimId);
            listCall.enqueue(new Callback<ArrayList<ClaimTrailstatus>>() {
                @Override
                public void onResponse(Call<ArrayList<ClaimTrailstatus>> call, Response<ArrayList<ClaimTrailstatus>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("CLAIM TRAIL DATA : ", " - " + response.body());

                            ClaimTrailAdapter adapter = new ClaimTrailAdapter(response.body(), ClaimHistoryActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ClaimHistoryActivity.this);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(adapter);

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                    }
                }

                @Override
                public void onFailure(Call<ArrayList<ClaimTrailstatus>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(ClaimHistoryActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getClaimProofList(final Integer claimId) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(ClaimHistoryActivity.this)) {
            final CommonDialog commonDialog = new CommonDialog(ClaimHistoryActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<ClaimProofList>> listCall = Constants.myInterface.getClaimProofList(authHeader, claimId);
            listCall.enqueue(new Callback<ArrayList<ClaimProofList>>() {
                @Override
                public void onResponse(Call<ArrayList<ClaimProofList>> call, Response<ArrayList<ClaimProofList>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("CLAIM PROOF DATA : ", " - " + response.body());

                            ClaimAttachmentAdapter adapter = new ClaimAttachmentAdapter(response.body(), ClaimHistoryActivity.this);
//                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ClaimHistoryActivity.this);
//                            recyclerView.setLayoutManager(mLayoutManager);
//                            recyclerView.setItemAnimator(new DefaultItemAnimator());
//                            recyclerView.setAdapter(adapter);
                            rvAttachment.setLayoutManager(new LinearLayoutManager(ClaimHistoryActivity.this, LinearLayoutManager.HORIZONTAL, false));
                            rvAttachment.setAdapter(adapter);

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                    }
                }

                @Override
                public void onFailure(Call<ArrayList<ClaimProofList>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(ClaimHistoryActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
