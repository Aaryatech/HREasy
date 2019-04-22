package com.ats.hreasy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ats.hreasy.R;
import com.ats.hreasy.model.LeaveAppTemp;
import com.google.gson.Gson;

public class UpdateLeaveStatusFragment extends Fragment implements View.OnClickListener {

    LeaveAppTemp leaveModel;

    private TextView tvEmpName, tvEmpDesg, tvLeaveType, tvDayType, tvDays, tvRemark, tvDate;
    private EditText edRemark;
    private Button btnApprove, btnReject;

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

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnApprove) {

        } else if (v.getId() == R.id.btnReject) {

        }
    }
}
