package com.ats.hreasy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ats.hreasy.R;
import com.ats.hreasy.adapter.PendingLeaveAdapter;
import com.ats.hreasy.model.LeaveHistoryTemp;
import com.ats.hreasy.model.LeavePendingTemp;

import java.util.ArrayList;

public class PendingLeaveListFragment extends Fragment {
    public RecyclerView recyclerView;
    public TextView tv_empName,tv_empDesignation;
    public ImageView iv_empPhoto;
    private ArrayList<LeaveHistoryTemp> historyList = new ArrayList<>();
    private PendingLeaveAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_leave_list, container, false);
        getActivity().setTitle("My Leave");

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        tv_empName=(TextView)view.findViewById(R.id.tvEmpName);
        tv_empDesignation=(TextView)view.findViewById(R.id.tvEmpDesg);
        iv_empPhoto=(ImageView) view.findViewById(R.id.ivPhoto);

        mAdapter = new PendingLeaveAdapter(historyList,getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareData();
        return view;
    }

    private void prepareData() {
        LeaveHistoryTemp leaveHistoryTemp = new LeaveHistoryTemp("Medical Leave","Half Day","15/4/2019 to 18/4/2019","3 Days","Pending");
        historyList.add(leaveHistoryTemp);

        leaveHistoryTemp = new LeaveHistoryTemp("Medical Leave","Half Day","15/4/2019 to 18/4/2019","1 Days","Pending");
        historyList.add(leaveHistoryTemp);

        leaveHistoryTemp = new LeaveHistoryTemp("Medical Leave","Full Day","15/4/2019 to 18/4/2019","3 Days","Approve");
        historyList.add(leaveHistoryTemp);

        leaveHistoryTemp = new LeaveHistoryTemp("Sick Leave","Half Day","15/4/2019 to 18/4/2019","4 Days","Pending");
        historyList.add(leaveHistoryTemp);

        leaveHistoryTemp = new LeaveHistoryTemp("Medical Leave","Full Day","15/4/2019 to 18/4/2019","3 Days","Rejected");
        historyList.add(leaveHistoryTemp);

        leaveHistoryTemp = new LeaveHistoryTemp("Casual Leave","Half Day","15/4/2019 to 18/4/2019","5 Days","Pending");
        historyList.add(leaveHistoryTemp);

        leaveHistoryTemp = new LeaveHistoryTemp("Medical Leave","Full Day","15/4/2019 to 18/4/2019","3 Days","Approve");
        historyList.add(leaveHistoryTemp);

        leaveHistoryTemp = new LeaveHistoryTemp("Medical Leave","Half Day","15/4/2019 to 18/4/2019","3 Days","Pending");
        historyList.add(leaveHistoryTemp);

        leaveHistoryTemp = new LeaveHistoryTemp("Medical Leave","Half Day","15/4/2019 to 18/4/2019","3 Days","Pending");
        historyList.add(leaveHistoryTemp);
    }

}
