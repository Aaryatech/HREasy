package com.ats.hreasy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ats.hreasy.R;
import com.ats.hreasy.model.ClaimHistoryTemp;
import com.google.gson.Gson;

public class ClaimHistoryActivity extends AppCompatActivity {
    ClaimHistoryTemp claimHistoryTemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_history);

        String upcomingStr = getIntent().getStringExtra("model");
        Gson gson = new Gson();
        claimHistoryTemp = gson.fromJson(upcomingStr, ClaimHistoryTemp.class);
        Log.e("responce", "-----------------------" + claimHistoryTemp);
    }
}
