package com.ats.hreasy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ats.hreasy.R;
import com.ats.hreasy.adapter.LeaveTrailAdapter;
import com.ats.hreasy.model.ClaimHistoryTemp;
import com.ats.hreasy.model.LeaveTrailTemp;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ClaimHistoryActivity extends AppCompatActivity {
    ClaimHistoryTemp claimHistoryTemp;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_history);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        setTitle("Claim History");


        String upcomingStr = getIntent().getStringExtra("model");
        Gson gson = new Gson();
        claimHistoryTemp = gson.fromJson(upcomingStr, ClaimHistoryTemp.class);
        Log.e("responce", "-----------------------" + claimHistoryTemp);

        recyclerView=findViewById(R.id.recyclerView);

        LeaveTrailTemp temp1 = new LeaveTrailTemp(1, "Anmol Shirke", "Claim rejected price is not appropriate", "Rejected", "15 APR 2019");
        LeaveTrailTemp temp2 = new LeaveTrailTemp(2, "Amit Patil", "Claim approved", "Approved", "16 APR 2019");

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
