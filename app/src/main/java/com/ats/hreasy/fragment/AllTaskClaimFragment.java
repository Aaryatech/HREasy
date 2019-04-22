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
import com.ats.hreasy.interfaces.AllTaskLeaveInterface;
import com.ats.hreasy.model.ClaimAppTemp;

import java.util.ArrayList;

public class AllTaskClaimFragment extends Fragment implements AllTaskLeaveInterface {

    private RecyclerView recyclerView;
    ClaimApprovalPendingAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_task_claim, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        ClaimAppTemp claim1=new ClaimAppTemp(1,"Anmol Shirke","HR Management System","Claim Type","15/4/2019",2500f,"Remark");
        ClaimAppTemp claim2=new ClaimAppTemp(1,"Aditya Joshi","HR Management System","Claim Type","15/4/2019",1500f,"Remark");
        ClaimAppTemp claim3=new ClaimAppTemp(1,"Gaurav Patil","HR Management System","Claim Type","15/4/2019",1750f,"Remark");
        ClaimAppTemp claim4=new ClaimAppTemp(1,"Rishi Balani","HR Management System","Claim Type","15/4/2019",350f,"Remark");

        ArrayList<ClaimAppTemp> claimList = new ArrayList<>();
        claimList.add(claim1);
        claimList.add(claim2);
        claimList.add(claim3);
        claimList.add(claim4);

        adapter = new ClaimApprovalPendingAdapter(claimList, getContext());
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
