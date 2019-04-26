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
import com.ats.hreasy.interfaces.MyTaskLeaveInterface;

import static com.ats.hreasy.fragment.LeaveApprovalPendingFragment.staticPendingLeave;

public class MyTaskLeaveFragment extends Fragment implements MyTaskLeaveInterface {

    private RecyclerView recyclerView;
    LeaveApprovalPendingAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_task_leave, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);


        adapter = new LeaveApprovalPendingAdapter(staticPendingLeave, getContext(),"pending");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);



        return view;
    }


    @Override
    public void fragmentBecameVisible() {
        adapter = new LeaveApprovalPendingAdapter(staticPendingLeave, getContext(),"pending");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }
}
