package com.ats.hreasy.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ats.hreasy.R;
import com.ats.hreasy.adapter.LeaveApprovalPendingAdapter;
import com.ats.hreasy.interfaces.AllTaskLeaveInterface;
import com.ats.hreasy.model.LeaveAppTemp;

import java.util.ArrayList;

public class AllTaskLeaveFragment extends Fragment implements AllTaskLeaveInterface {

    private RecyclerView recyclerView;
    LeaveApprovalPendingAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_task_leave, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        LeaveAppTemp leave1 = new LeaveAppTemp(1, "Anmol Shirke", "Medical Leave", "12/4/2019", "15/4/2019", "3", "aaaaaa");
        LeaveAppTemp leave2 = new LeaveAppTemp(1, "Aditya Joshi", "Medical Leave", "13/4/2019", "14/4/2019", "2", "aaaaaa");
        LeaveAppTemp leave3 = new LeaveAppTemp(1, "Gaurav Patil", "Medical Leave", "14/4/2019", "16/4/2019", "3", "aaaaaa");
        LeaveAppTemp leave4 = new LeaveAppTemp(1, "Rishi Balani", "Medical Leave", "15/4/2019", "18/4/2019", "4", "aaaaaa");

        ArrayList<LeaveAppTemp> leaveList = new ArrayList<>();
        leaveList.add(leave1);
        leaveList.add(leave2);
        leaveList.add(leave3);
        leaveList.add(leave4);

        adapter = new LeaveApprovalPendingAdapter(leaveList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        return view;
    }

    @Override
    public void fragmentBecameVisible() {

    }
}
