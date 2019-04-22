package com.ats.hreasy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ats.hreasy.R;
import com.ats.hreasy.adapter.ClaimApprovalPendingAdapter;
import com.ats.hreasy.adapter.NotificationAdapter;
import com.ats.hreasy.model.NotificationTemp;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        setTitle("Notification");

        recyclerView = findViewById(R.id.recyclerView);

        NotificationTemp temp1 = new NotificationTemp(1, "Anmol Shirke leave approved", "Leave approved for 3 days", "15/4/2019");
        NotificationTemp temp2 = new NotificationTemp(1, "Claim approval pending", "Hello, please check the pending claim", "13/4/2019");
        NotificationTemp temp3 = new NotificationTemp(1, "Anmol Shirke leave approved", "Leave approved for 3 days", "15/4/2019");
        NotificationTemp temp4 = new NotificationTemp(1, "Anmol Shirke leave approved", "Leave approved for 3 days", "15/4/2019");

        ArrayList<NotificationTemp> notificationTemps=new ArrayList<>();
        notificationTemps.add(temp1);
        notificationTemps.add(temp2);
        notificationTemps.add(temp3);
        notificationTemps.add(temp4);

        adapter = new NotificationAdapter(notificationTemps, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
       // recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}
