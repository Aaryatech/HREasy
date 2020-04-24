package com.ats.hreasy.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.hreasy.R;
import com.ats.hreasy.constant.Constants;
import com.ats.hreasy.model.DashboardCount;
import com.ats.hreasy.model.LeaveEmployeeModel;
import com.ats.hreasy.model.Login;
import com.ats.hreasy.utils.CommonDialog;
import com.ats.hreasy.utils.CustomSharedPreference;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private FloatingActionButton fabLeave, fabClaim, fab;
    private Animation fab_open, fab_close;
    TextView tv_fab1, tv_fab2;
    Boolean isOpen = false;
    Login loginUser;

    private CardView cvLeaveAppPend, cvClaimAppPend, cvMyLeavePend, cvMyClaimPend;
    private TextView tvLeaveAppPendingCount, tvLeavependingCount, tvHome_claimAppPendingCount, tvHome_claimPendingCount;

    DashboardCount dashboardCount=new DashboardCount();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("Home");

        tv_fab1 = (TextView) view.findViewById(R.id.tv_fab1);
        tv_fab2 = (TextView) view.findViewById(R.id.tv_fab2);

        fab_close = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fabLeave = (FloatingActionButton) view.findViewById(R.id.fabLeave);
        fabClaim = (FloatingActionButton) view.findViewById(R.id.fabClaim);

        cvLeaveAppPend = view.findViewById(R.id.cvLeaveAppPend);
        cvClaimAppPend = view.findViewById(R.id.cvClaimAppPend);
        cvMyLeavePend = view.findViewById(R.id.cvMyLeavePend);
        cvMyClaimPend = view.findViewById(R.id.cvMyClaimPend);
        tvLeaveAppPendingCount = view.findViewById(R.id.tvHome_leaveAppPendingCount);
        tvLeavependingCount = view.findViewById(R.id.tvHome_leavePendingCount);

        tvHome_claimAppPendingCount = view.findViewById(R.id.tvHome_claimAppPendingCount);
        tvHome_claimPendingCount = view.findViewById(R.id.tvHome_claimPendingCount);

        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
        Gson gson = new Gson();
        loginUser = gson.fromJson(userStr, Login.class);
        Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);

        if (loginUser != null) {
            getDashboardCount(loginUser.getEmpId());
        }

        cvLeaveAppPend.setOnClickListener(this);
        cvClaimAppPend.setOnClickListener(this);
        cvMyLeavePend.setOnClickListener(this);
        cvMyClaimPend.setOnClickListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpen) {

//                    tv_fab1.setVisibility(View.INVISIBLE);
//                    tv_fab2.setVisibility(View.INVISIBLE);
                    fabLeave.startAnimation(fab_close);
                    fabClaim.startAnimation(fab_close);
                    fabLeave.setClickable(false);
                    fabClaim.setClickable(false);
                    isOpen = false;

                } else {
//                    tv_fab1.setVisibility(View.VISIBLE);
//                    tv_fab2.setVisibility(View.VISIBLE);
                    fabLeave.setClickable(true);
                    fabClaim.setClickable(true);
                    fabLeave.startAnimation(fab_open);
                    fabClaim.startAnimation(fab_open);
                    isOpen = true;
                }

            }
        });

        fabLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment adf = new EmployeeListFragment();
                Bundle args = new Bundle();
                args.putString("type", "leave");
                args.putString("isAuth", "" + dashboardCount.getIsAuthorized());
                adf.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();

            }
        });

        fabClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment adf = new EmployeeListFragment();
                Bundle args = new Bundle();
                args.putString("type", "claim");
                args.putString("isAuth", "" + dashboardCount.getIsAuthorizedClaim());
                adf.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();

            }
        });

        return view;
    }

    private void getDashboardCount(Integer empId) {
        Log.e("PARAMETERS : ", "       EMPID : " + empId);
        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            String base = Constants.userName + ":" + Constants.password;
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

            Call<DashboardCount> listCall = Constants.myInterface.getDashboardCount(authHeader, empId);
            listCall.enqueue(new Callback<DashboardCount>() {
                @Override
                public void onResponse(Call<DashboardCount> call, Response<DashboardCount> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Dashboard Count : ", "------------" + response.body());
                            commonDialog.dismiss();

                            DashboardCount data = response.body();
                            dashboardCount = data;

                            tvLeaveAppPendingCount.setText("" + data.getPendingRequest() + "/" + data.getInfo());
                            tvLeavependingCount.setText("" + data.getMyLeave());

                            tvHome_claimAppPendingCount.setText("" + data.getPendingClaim() + "/" + data.getInfoClaim());
                            tvHome_claimPendingCount.setText("" + data.getMyClaim());

                            if (data.getIsAuthorized() == 1) {
                                cvLeaveAppPend.setVisibility(View.VISIBLE);
                            } else {
                                cvLeaveAppPend.setVisibility(View.GONE);
                            }

                            if (data.getIsAuthorizedClaim() == 1) {
                                cvClaimAppPend.setVisibility(View.VISIBLE);
                            } else {
                                cvClaimAppPend.setVisibility(View.GONE);
                            }


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
                public void onFailure(Call<DashboardCount> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cvLeaveAppPend) {

            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new LeaveApprovalPendingFragment(), "HomeFragment");
            ft.commit();

        } else if (v.getId() == R.id.cvClaimAppPend) {

            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new ClaimApprovalPendingFragment(), "HomeFragment");
            ft.commit();

        } else if (v.getId() == R.id.cvMyLeavePend) {

            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new PendingLeaveListFragment(), "HomeFragment");
            ft.commit();

        } else if (v.getId() == R.id.cvMyClaimPend) {

            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new PendingClaimListFragment(), "HomeFragment");
            ft.commit();

        }
    }
}
