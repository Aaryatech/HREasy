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
import com.ats.hreasy.adapter.PendingClaimAdapter;
import com.ats.hreasy.model.ClaimHistoryTemp;

import java.util.ArrayList;

public class PendingClaimListFragment extends Fragment {
public TextView tvEmpName,tvEmpDesg;
public ImageView ivPhoto;
public RecyclerView recyclerView;
PendingClaimAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_pending_claim_list, container, false);
        getActivity().setTitle("My Claim");

        tvEmpName=(TextView)view.findViewById(R.id.tvEmpName);
        tvEmpDesg=(TextView)view.findViewById(R.id.tvEmpDesg);
        ivPhoto=(ImageView) view.findViewById(R.id.ivPhoto);
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);

        ClaimHistoryTemp claim1=new ClaimHistoryTemp("HR Management System","Claim Type","15/4/2019",1500,"Remark","Pending");
        ClaimHistoryTemp claim2=new ClaimHistoryTemp("HR Management System","Claim Type","15/4/2019",1500,"Remark","Pending");
        ClaimHistoryTemp claim3=new ClaimHistoryTemp("HR Management System","Claim Type","15/4/2019",1500,"Remark","Pending");
        ClaimHistoryTemp claim4=new ClaimHistoryTemp("HR Management System","Claim Type","15/4/2019",1500,"Remark","Pending");

        ArrayList<ClaimHistoryTemp> claimList = new ArrayList<>();
        claimList.add(claim1);
        claimList.add(claim2);
        claimList.add(claim3);
        claimList.add(claim4);

        adapter = new PendingClaimAdapter(claimList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        return view;
    }

}
