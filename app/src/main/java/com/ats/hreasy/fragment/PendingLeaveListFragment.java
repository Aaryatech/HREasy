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
import com.ats.hreasy.model.LeavePendingTemp;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PendingLeaveListFragment extends Fragment {
    public RecyclerView recyclerView;
    public TextView tv_empName,tv_empDesignation;
    public ImageView iv_empPhoto;
    private ArrayList<LeavePendingTemp> pendingLeaveList = new ArrayList<>();
    private PendingLeaveAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pending_leave_list, container, false);

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        tv_empName=(TextView)view.findViewById(R.id.tvEmpName);
        tv_empDesignation=(TextView)view.findViewById(R.id.tvEmpDesg);
        iv_empPhoto=(ImageView) view.findViewById(R.id.ivPhoto);

        mAdapter = new PendingLeaveAdapter(pendingLeaveList,getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareData();
        return view;
    }

    private void prepareData() {
        LeavePendingTemp leavePendingTemp = new LeavePendingTemp("Medical Leave","Half Day","15/4/2019 to 18/4/2019","3dayes","Pending");
        pendingLeaveList.add(leavePendingTemp);

        leavePendingTemp = new LeavePendingTemp("Medical Leave","Half Day","15/4/2019 to 18/4/2019","1dayes","Pending");
        pendingLeaveList.add(leavePendingTemp);

        leavePendingTemp = new LeavePendingTemp("Medical Leave","Full Day","15/4/2019 to 18/4/2019","3dayes","Pending");
        pendingLeaveList.add(leavePendingTemp);

        leavePendingTemp = new LeavePendingTemp("Sick Leave","Half Day","15/4/2019 to 18/4/2019","4dayes","Pending");
        pendingLeaveList.add(leavePendingTemp);

        leavePendingTemp = new LeavePendingTemp("Medical Leave","Full Day","15/4/2019 to 18/4/2019","3dayes","Pending");
        pendingLeaveList.add(leavePendingTemp);

        leavePendingTemp = new LeavePendingTemp("Casual Leave","Half Day","15/4/2019 to 18/4/2019","5dayes","Pending");
        pendingLeaveList.add(leavePendingTemp);

        leavePendingTemp = new LeavePendingTemp("Medical Leave","Full Day","15/4/2019 to 18/4/2019","3dayes","Pending");
        pendingLeaveList.add(leavePendingTemp);

        leavePendingTemp = new LeavePendingTemp("Medical Leave","Half Day","15/4/2019 to 18/4/2019","3dayes","Pending");
        pendingLeaveList.add(leavePendingTemp);

        leavePendingTemp = new LeavePendingTemp("Medical Leave","Half Day","15/4/2019 to 18/4/2019","3dayes","Pending");
        pendingLeaveList.add(leavePendingTemp);
    }

}
