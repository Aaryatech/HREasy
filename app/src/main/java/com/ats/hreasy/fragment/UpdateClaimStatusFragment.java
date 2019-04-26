package com.ats.hreasy.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ats.hreasy.R;
import com.ats.hreasy.adapter.LeaveTrailAdapter;
import com.ats.hreasy.model.ClaimApp;
import com.ats.hreasy.model.ClaimAppTemp;
import com.ats.hreasy.model.LeaveApp;
import com.ats.hreasy.model.LeaveTrailTemp;
import com.ats.hreasy.model.Login;
import com.ats.hreasy.model.SaveLeaveTrail;
import com.ats.hreasy.utils.CustomSharedPreference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UpdateClaimStatusFragment extends Fragment implements View.OnClickListener {

    private TextView tvEmpName, tvEmpDesg, tvProject, tvClaimType, tvDate, tvAmount, tvRemark, tvStatus;
    private ImageView ivPhoto1, ivPhoto2, ivPhoto3;
    private Button btnApprove, btnReject;
    private EditText edRemark;

    private RecyclerView recyclerView;

    ClaimApp claimModel;

    ArrayList<ClaimApp> claimModelList = new ArrayList<>();

    Login loginUser;

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
        tvStatus = view.findViewById(R.id.tvStatus);

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
            String str = getArguments().getString("modelList");
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<LeaveApp>>() {
            }.getType();
            claimModelList = gson.fromJson(str, type);


            Log.e("MODEL LIST --------- ", "-------------------" + claimModelList);

            setData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
            Gson gson = new Gson();
            loginUser = gson.fromJson(userStr, Login.class);
            Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

    public void setData() {
        if (claimModelList != null) {

            if (claimModelList.size() > 0) {

                claimModel = claimModelList.get(0);

                tvEmpName.setText("" + claimModel.getEmpName());
                tvProject.setText("" + claimModel.getProjectTitle());
                tvClaimType.setText("" + claimModel.getClaimTypeName());
                tvDate.setText("" + claimModel.getClaimDate());
                tvAmount.setText("" + claimModel.getClaimAmount() + "/-");
                tvRemark.setText("" + claimModel.getClaimRemarks());

                // getLeaveTrail(claimModel.getLeaveId());

                if (claimModel.getExInt1() == 1) {
                    tvStatus.setText("Initial Pending");
                    tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
                } else if (claimModel.getExInt1() == 2) {
                    tvStatus.setText("Final Pending");
                    tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
                } else if (claimModel.getExInt1() == 3) {
                    tvStatus.setText("Final Approved");
                    tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorApproved));
                } else if (claimModel.getExInt1() == 8) {
                    tvStatus.setText("Initial Rejected");
                    tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorRejected));
                } else if (claimModel.getExInt1() == 9) {
                    tvStatus.setText("Final Rejected");
                    tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorRejected));
                } else if (claimModel.getExInt1() == 7) {
                    tvStatus.setText("Leave Cancelled");
                    tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
                }

            } else {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new ClaimApprovalPendingFragment(), "HomeFragment");
                ft.commit();

            }
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnApprove) {

            final String remark = edRemark.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            final String currDate = sdf.format(System.currentTimeMillis());

            if (claimModel != null && loginUser != null) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Confirmation");
                builder.setMessage("Do you want to APPROVE the claim of employee " + claimModel.getEmpName() + " " );
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (claimModel.getCaFinAuthEmpId() == loginUser.getEmpId()) {

                            SaveLeaveTrail saveLeaveTrail = new SaveLeaveTrail(0, leaveModel.getLeaveId(), leaveModel.getEmpId(), remark, 3, loginUser.getEmpId(), "" + currDate);
                            updateLeaveStatus(leaveModel.getLeaveId(), 3, saveLeaveTrail);

                        } else if (claimModel.getCaIniAuthEmpId()==loginUser.getEmpId()) {

                            SaveLeaveTrail saveLeaveTrail = new SaveLeaveTrail(0, leaveModel.getLeaveId(), leaveModel.getEmpId(), remark, 2, loginUser.getEmpId(), "" + currDate);
                            updateLeaveStatus(leaveModel.getLeaveId(), 2, saveLeaveTrail);

                        }

                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            } else {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Alert");
                builder.setMessage("Oops something went wrong!");

                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }

        } else if (v.getId() == R.id.btnReject) {

        }
    }


}
