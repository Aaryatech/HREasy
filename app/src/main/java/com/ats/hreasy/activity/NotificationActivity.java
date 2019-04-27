package com.ats.hreasy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ats.hreasy.R;
import com.ats.hreasy.adapter.NotificationAdapter;
import com.ats.hreasy.model.NotificationTemp;
import com.ats.hreasy.sqlite.DatabaseHandler;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    NotificationAdapter adapter;
    DatabaseHandler db;
    ArrayList<NotificationTemp> notificationList1=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        setTitle("Notification");

        db = new DatabaseHandler(this);

        recyclerView = findViewById(R.id.recyclerView);

        NotificationTemp temp1 = new NotificationTemp(1, "Anmol Shirke leave approved", "Leave approved for 3 days", "15/4/2019");
        NotificationTemp temp2 = new NotificationTemp(2, "Claim approval pending", "Hello, please check the pending claim", "13/4/2019");
        NotificationTemp temp3 = new NotificationTemp(4, "Anmol Shirke leave approved", "Leave approved for 3 days", "15/4/2019");
        NotificationTemp temp4 = new NotificationTemp(5, "Anmol Shirke leave approved", "Leave approved for 3 days", "15/4/2019");
        NotificationTemp temp5 = new NotificationTemp(3, "Claim approval pending", "Hello, please check the pending claim", "12/4/2019");

        ArrayList<NotificationTemp> notificationTemps=new ArrayList<>();
        notificationTemps.add(temp1);
        notificationTemps.add(temp2);
        notificationTemps.add(temp3);
        notificationTemps.add(temp4);
        notificationTemps.add(temp5);

       // getAddData(notificationTemps);

        notificationList1.clear();
        notificationList1 = db.getAllNotification();
        Log.e("NOTIFICATION","-----------------------------notification---------------"+notificationList1);

        adapter = new NotificationAdapter(notificationList1, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    private void getAddData(ArrayList<NotificationTemp> notificationTemps) {
        db.insertNotification(notificationTemps);
        notificationTemps.clear();
        db.close();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}
