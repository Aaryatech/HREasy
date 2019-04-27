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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.hreasy.R;
import com.ats.hreasy.adapter.PendingClaimAdapter;
import com.ats.hreasy.adapter.PendingLeaveAdapter;
import com.ats.hreasy.constant.Constants;
import com.ats.hreasy.model.ClaimHistoryTemp;
import com.ats.hreasy.model.Login;
import com.ats.hreasy.model.MyLeaveData;
import com.ats.hreasy.utils.CommonDialog;
import com.ats.hreasy.utils.CustomSharedPreference;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingClaimListFragment extends Fragment {

    public TextView tvEmpName, tvEmpDesg;
    public ImageView ivPhoto;
    public RecyclerView recyclerView;
    PendingClaimAdapter adapter;

    Login loginUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_claim_list, container, false);
        getActivity().setTitle("My Claim");

        tvEmpName = (TextView) view.findViewById(R.id.tvEmpName);
        tvEmpDesg = (TextView) view.findViewById(R.id.tvEmpDesg);
        ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
        Gson gson = new Gson();
        loginUser = gson.fromJson(userStr, Login.class);
        Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);

        if (loginUser != null) {
            tvEmpName.setText("" + loginUser.getEmpFname() + " " + loginUser.getEmpMname() + " " + loginUser.getEmpSname());
            tvEmpDesg.setText("" + loginUser.getEmpMobile1());
           // getLeaveList(loginUser.getEmpId());

            String imageUri = String.valueOf(loginUser.getEmpPhoto());
            try {
                Picasso.with(getContext()).load(imageUri).placeholder(getActivity().getResources().getDrawable(R.drawable.profile)).into(ivPhoto);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        /*adapter = new PendingClaimAdapter(claimList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);*/

        return view;
    }


    /*private void getClaimList(int empId) {
        Log.e("PARAMETERS : ", "        EMP ID : " + empId);

        ArrayList<Integer> statusList = new ArrayList<>();
        statusList.add(1);
        statusList.add(2);

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<MyLeaveData>> listCall = Constants.myInterface.getLeaveStatusList(authHeader, empId, statusList);
            listCall.enqueue(new Callback<ArrayList<MyLeaveData>>() {
                @Override
                public void onResponse(Call<ArrayList<MyLeaveData>> call, Response<ArrayList<MyLeaveData>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("LEAVE LIST : ", " ************* " + response.body());

                            LeaveList.clear();
                            LeaveList = response.body();

                            mAdapter = new PendingLeaveAdapter(LeaveList, getActivity(),getActivity(),loginUser.getEmpId());
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(mAdapter);


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
                public void onFailure(Call<ArrayList<MyLeaveData>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }*/


}
