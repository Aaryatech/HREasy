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
import android.widget.TextView;

import com.ats.hreasy.R;
import com.ats.hreasy.adapter.LeaveTrailAdapter;
import com.ats.hreasy.model.LeaveAppTemp;
import com.ats.hreasy.model.LeaveTrailTemp;
import com.google.gson.Gson;

import java.util.ArrayList;

public class UpdateLeaveStatusFragment extends Fragment implements View.OnClickListener {

    LeaveAppTemp leaveModel;

    private TextView tvEmpName, tvEmpDesg, tvLeaveType, tvDayType, tvDays, tvRemark, tvDate;
    private EditText edRemark;
    private Button btnApprove, btnReject;

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_leave_status, container, false);

        tvEmpName = view.findViewById(R.id.tvEmpName);
        tvEmpDesg = view.findViewById(R.id.tvEmpDesg);
        tvLeaveType = view.findViewById(R.id.tvLeaveType);
        tvDayType = view.findViewById(R.id.tvDayType);
        tvDays = view.findViewById(R.id.tvDays);
        tvDate = view.findViewById(R.id.tvDate);
        tvRemark = view.findViewById(R.id.tvRemark);
        edRemark = view.findViewById(R.id.edRemark);
        btnApprove = view.findViewById(R.id.btnApprove);
        btnReject = view.findViewById(R.id.btnReject);
        recyclerView = view.findViewById(R.id.recyclerView);

        try {
            String json = getArguments().getString("model");
            Gson gsonPlant = new Gson();
            leaveModel = gsonPlant.fromJson(json, LeaveAppTemp.class);
        } catch (Exception e) {
        }


        if (leaveModel != null) {

            tvLeaveType.setText("" + leaveModel.getType());
            tvDayType.setText("Full Day");
            tvDate.setText("" + leaveModel.getFromDate() + " to " + leaveModel.getToDate());
            tvDays.setText("" + leaveModel.getDays() + " days");
            tvRemark.setText("" + leaveModel.getRemark());
        }


        LeaveTrailTemp temp1 = new LeaveTrailTemp(1, "Anmol Shirke", "Leave rejected because you already taken leave this month", "Rejected", "15 APR 2019");
        LeaveTrailTemp temp2 = new LeaveTrailTemp(2, "Amit Patil", "Leave approved", "Approved", "16 APR 2019");

        ArrayList<LeaveTrailTemp> leaveTrailTemps=new ArrayList<>();
        leaveTrailTemps.add(temp1);
        leaveTrailTemps.add(temp2);

        LeaveTrailAdapter adapter = new LeaveTrailAdapter(leaveTrailTemps, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnApprove) {

        } else if (v.getId() == R.id.btnReject) {

        }
    }
}
