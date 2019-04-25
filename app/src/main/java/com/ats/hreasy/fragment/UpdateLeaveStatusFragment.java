package com.ats.hreasy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.hreasy.R;
import com.ats.hreasy.adapter.LeaveTrailAdapter;
import com.ats.hreasy.constant.Constants;
import com.ats.hreasy.model.CurrentYearModel;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.LeaveApp;
import com.ats.hreasy.model.LeaveTrailTemp;
import com.ats.hreasy.model.Login;
import com.ats.hreasy.model.MyLeaveTrailData;
import com.ats.hreasy.model.SaveLeaveTrail;
import com.ats.hreasy.utils.CommonDialog;
import com.ats.hreasy.utils.CustomSharedPreference;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateLeaveStatusFragment extends Fragment implements View.OnClickListener {

    LeaveApp leaveModel;

    private TextView tvEmpName, tvEmpDesg, tvLeaveType, tvDayType, tvDays, tvRemark, tvDate;
    private EditText edRemark;
    private Button btnApprove, btnReject;
    private ImageView ivPhoto;

    private RecyclerView recyclerView;

    Login loginUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_leave_status, container, false);

        tvEmpName = view.findViewById(R.id.tvEmpName);
        tvEmpDesg = view.findViewById(R.id.tvEmpDesg);
        ivPhoto = view.findViewById(R.id.ivPhoto);
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
            leaveModel = gsonPlant.fromJson(json, LeaveApp.class);
        } catch (Exception e) {
        }

        try {
            String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
            Gson gson = new Gson();
            loginUser = gson.fromJson(userStr, Login.class);
            Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);

        } catch (Exception e) {
            e.printStackTrace();
        }


        if (leaveModel != null) {
            tvEmpName.setText("" + leaveModel.getEmpName());
            tvLeaveType.setText("" + leaveModel.getLeaveTitle());
            tvDate.setText("" + leaveModel.getLeaveFromdt() + " to " + leaveModel.getLeaveTodt());
            tvDays.setText("" + leaveModel.getLeaveNumDays() + " days");
            tvRemark.setText("" + leaveModel.getLeaveEmpReason());
            if (leaveModel.getLeaveDuration().equals("1")) {
                tvDayType.setText("Full Day");
            } else {
                tvDayType.setText("Half Day");
            }

            getLeaveTrail(leaveModel.getLeaveId());

        }


        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnApprove) {

            String remark=edRemark.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String currDate = sdf.format(System.currentTimeMillis());

            if (leaveModel != null && loginUser != null) {

                if (leaveModel.getFinAuthEmpId().equalsIgnoreCase(String.valueOf(loginUser.getEmpId()))) {

                    SaveLeaveTrail saveLeaveTrail = new SaveLeaveTrail(0, leaveModel.getLeaveId(), leaveModel.getEmpId(), remark, 3, loginUser.getEmpId(), "" + currDate);
                    updateLeaveStatus(leaveModel.getLeaveId(), 3, saveLeaveTrail);

                } else if (leaveModel.getIniAuthEmpId().equalsIgnoreCase(String.valueOf(loginUser.getEmpId()))) {

                    SaveLeaveTrail saveLeaveTrail = new SaveLeaveTrail(0, leaveModel.getLeaveId(), leaveModel.getEmpId(), remark, 2, loginUser.getEmpId(), "" + currDate);
                    updateLeaveStatus(leaveModel.getLeaveId(), 2, saveLeaveTrail);

                }

            }


        } else if (v.getId() == R.id.btnReject) {

        }
    }


    private void getLeaveTrail(final Integer leaveId) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<MyLeaveTrailData>> listCall = Constants.myInterface.getLeaveTrail(authHeader, leaveId);
            listCall.enqueue(new Callback<ArrayList<MyLeaveTrailData>>() {
                @Override
                public void onResponse(Call<ArrayList<MyLeaveTrailData>> call, Response<ArrayList<MyLeaveTrailData>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("LEAVE TRAIL DATA : ", " - " + response.body());

                            LeaveTrailAdapter adapter = new LeaveTrailAdapter(response.body(), getContext());
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(adapter);

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                    }
                }

                @Override
                public void onFailure(Call<ArrayList<MyLeaveTrailData>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLeaveStatus(final Integer leaveId, int status, final SaveLeaveTrail saveLeaveTrail) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateLeaveStatus(authHeader, leaveId, status);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPDATE LEAVE : ", " - " + response.body());

                            if (!response.body().getError()) {
                                saveLeaveTrail(leaveId, saveLeaveTrail);
                            }

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveLeaveTrail(final Integer leaveId, SaveLeaveTrail saveLeaveTrail) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<SaveLeaveTrail> listCall = Constants.myInterface.saveLeaveTrail(authHeader, saveLeaveTrail);
            listCall.enqueue(new Callback<SaveLeaveTrail>() {
                @Override
                public void onResponse(Call<SaveLeaveTrail> call, Response<SaveLeaveTrail> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPDATE LEAVE : ", " - " + response.body());

                            if (response.body().getTrailPkey() > 0) {
                                updateLeaveTrailId(leaveId, response.body().getTrailPkey());
                            }

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                    }
                }

                @Override
                public void onFailure(Call<SaveLeaveTrail> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLeaveTrailId(final Integer leaveId, int trailId) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateLeaveTrailId(authHeader, leaveId, trailId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPDATE LEAVE : ", " - " + response.body());

                            if (!response.body().getError()) {

                                Toast.makeText(getContext(), "SUCCESS", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getContext(), "FAILED", Toast.LENGTH_SHORT).show();

                            }

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                            Toast.makeText(getContext(), "FAILED", Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                        Toast.makeText(getContext(), "FAILED", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                    Toast.makeText(getContext(), "FAILED", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

}
