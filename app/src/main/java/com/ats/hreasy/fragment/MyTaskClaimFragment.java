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
import com.ats.hreasy.adapter.ClaimApprovalPendingAdapter;
import com.ats.hreasy.adapter.LeaveApprovalPendingAdapter;
import com.ats.hreasy.interfaces.MyTaskLeaveInterface;
import com.ats.hreasy.model.ClaimAppTemp;
import com.ats.hreasy.model.LeaveAppTemp;

import java.util.ArrayList;

import static com.ats.hreasy.fragment.ClaimApprovalPendingFragment.staticPendingClaim;

public class MyTaskClaimFragment extends Fragment implements MyTaskLeaveInterface {

    private RecyclerView recyclerView;
    ClaimApprovalPendingAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_task_claim, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);


        adapter = new ClaimApprovalPendingAdapter(staticPendingClaim, getContext(), "pending");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        return view;
    }

    @Override
    public void fragmentBecameVisible() {
        adapter = new ClaimApprovalPendingAdapter(staticPendingClaim, getContext(), "pending");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}
