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
import com.ats.hreasy.adapter.ClaimHistoryAdapter;
import com.ats.hreasy.interfaces.ClaimHistoryInterface;
import com.ats.hreasy.model.ClaimHistoryTemp;

import java.util.ArrayList;


public class ClaimHistoryFragment extends Fragment implements ClaimHistoryInterface {
    public RecyclerView recyclerView;
    public TextView tv_empName,tv_empDesignation;
    public ImageView iv_empPhoto;
    private ClaimHistoryAdapter mAdapter;
    private ArrayList<ClaimHistoryTemp> claimHistoryList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cliam_history, container, false);

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        tv_empName=(TextView)view.findViewById(R.id.tvEmpName);
        tv_empDesignation=(TextView)view.findViewById(R.id.tvEmpDesg);
        iv_empPhoto=(ImageView) view.findViewById(R.id.ivPhoto);

        mAdapter = new ClaimHistoryAdapter(claimHistoryList,getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareData();

        return view;
    }

    private void prepareData() {
        ClaimHistoryTemp claimHistoryTemp = new ClaimHistoryTemp("Shiv shambhoo","Claim Type","18/4/2019",100,"An employee can call in sick if he is not in a state to come to office for work. Usually, an employee is entitled to sick leave only after a stipulated period of employment in an organization");
        claimHistoryList.add(claimHistoryTemp);

         claimHistoryTemp = new ClaimHistoryTemp("Shiv shambhoo","Claim Type","18/4/2019",100,"An employee can call in sick if he is not in a state to come to office for work. Usually, an employee is entitled to sick leave only after a stipulated period of employment in an organization");
        claimHistoryList.add(claimHistoryTemp);

         claimHistoryTemp = new ClaimHistoryTemp("Shiv shambhoo","Claim Type","18/4/2019",100,"An employee can call in sick if he is not in a state to come to office for work. Usually, an employee is entitled to sick leave only after a stipulated period of employment in an organization");
        claimHistoryList.add(claimHistoryTemp);

         claimHistoryTemp = new ClaimHistoryTemp("Rusa Application","Claim Type","18/4/2019",100,"An employee can call in sick if he is not in a state to come to office for work. Usually, an employee is entitled to sick leave only after a stipulated period of employment in an organization");
        claimHistoryList.add(claimHistoryTemp);

         claimHistoryTemp = new ClaimHistoryTemp("HREasy","Claim Type","18/4/2019",100,"An employee can call in sick if he is not in a state to come to office for work. Usually, an employee is entitled to sick leave only after a stipulated period of employment in an organization");
        claimHistoryList.add(claimHistoryTemp);

        claimHistoryTemp = new ClaimHistoryTemp("HREasy","Claim Type","18/4/2019",100,"An employee can call in sick if he is not in a state to come to office for work. Usually, an employee is entitled to sick leave only after a stipulated period of employment in an organization");
        claimHistoryList.add(claimHistoryTemp);

        claimHistoryTemp = new ClaimHistoryTemp("HREasy","Claim Type","18/4/2019",100,"An employee can call in sick if he is not in a state to come to office for work. Usually, an employee is entitled to sick leave only after a stipulated period of employment in an organization");
        claimHistoryList.add(claimHistoryTemp);

        claimHistoryTemp = new ClaimHistoryTemp("HREasy","Claim Type","15/4/2019",100,"An employee can call in sick if he is not in a state to come to office for work. Usually, an employee is entitled to sick leave only after a stipulated period of employment in an organization");
        claimHistoryList.add(claimHistoryTemp);
    }

    @Override
    public void fragmentBecameVisible() {

    }
}
