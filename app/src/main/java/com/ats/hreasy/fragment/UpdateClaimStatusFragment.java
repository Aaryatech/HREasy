package com.ats.hreasy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ats.hreasy.R;
import com.ats.hreasy.adapter.LeaveTrailAdapter;
import com.ats.hreasy.model.ClaimAppTemp;
import com.ats.hreasy.model.LeaveTrailTemp;
import com.google.gson.Gson;

import java.util.ArrayList;

public class UpdateClaimStatusFragment extends Fragment implements View.OnClickListener {

    private TextView tvEmpName, tvEmpDesg, tvProject, tvClaimType, tvDate, tvAmount, tvRemark;
    private ImageView ivPhoto1, ivPhoto2, ivPhoto3;
    private Button btnApprove, btnReject;
    private EditText edRemark;

    private RecyclerView recyclerView;

    ClaimAppTemp model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_claim_status, container, false);

        tvEmpName = view.findViewById(R.id.tvEmpName);
        tvEmpDesg = view.findViewById(R.id.tvEmpDesg);
        tvProject = view.findViewById(R.id.tvProject);
        tvClaimType = view.findViewById(R.id.tvClaimType);
        tvDate = view.findViewById(R.id.tvDate);
        tvAmount = view.findViewById(R.id.tvAmount);
        tvRemark = view.findViewById(R.id.tvRemark);

        btnApprove.setOnClickListener(this);
        btnReject.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.recyclerView);


        ivPhoto1 = view.findViewById(R.id.ivPhoto1);
        ivPhoto2 = view.findViewById(R.id.ivPhoto2);
        ivPhoto3 = view.findViewById(R.id.ivPhoto3);

        btnApprove = view.findViewById(R.id.btnApprove);
        btnReject = view.findViewById(R.id.btnReject);

        edRemark = view.findViewById(R.id.edRemark);

        try {

            String json = getArguments().getString("model");
            Gson gson = new Gson();
            model = gson.fromJson(json, ClaimAppTemp.class);

        } catch (Exception e) {
        }

        if (model != null) {

            tvEmpName.setText("" + model.getEmpName());
            tvProject.setText("" + model.getProject());
            tvClaimType.setText("" + model.getClaimType());
            tvDate.setText("" + model.getDate());
            tvAmount.setText("" + model.getAmount() + "/-");
            tvRemark.setText("" + model.getRemark());

        }


      /*  LeaveTrailTemp temp1 = new LeaveTrailTemp(1, "Anmol Shirke", "Leave rejected because you already taken leave this month", "Rejected", "15 APR 2019");
        LeaveTrailTemp temp2 = new LeaveTrailTemp(2, "Amit Patil", "Leave approved", "Approved", "16 APR 2019");

        ArrayList<LeaveTrailTemp> leaveTrailTemps=new ArrayList<>();
        leaveTrailTemps.add(temp1);
        leaveTrailTemps.add(temp2);

        LeaveTrailAdapter adapter = new LeaveTrailAdapter(leaveTrailTemps, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);*/


        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnApprove) {

        } else if (v.getId() == R.id.btnReject) {

        }
    }




}
