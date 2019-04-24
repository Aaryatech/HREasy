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
import com.ats.hreasy.adapter.PendingLeaveAdapter;
import com.ats.hreasy.constant.Constants;
import com.ats.hreasy.model.Login;
import com.ats.hreasy.model.MyLeaveData;
import com.ats.hreasy.utils.CommonDialog;
import com.ats.hreasy.utils.CustomSharedPreference;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingLeaveListFragment extends Fragment {
    public RecyclerView recyclerView;
    public TextView tv_empName,tv_empDesignation;
    public ImageView iv_empPhoto;
    private ArrayList<MyLeaveData> LeaveList = new ArrayList<>();
    private PendingLeaveAdapter mAdapter;
    Login loginUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_leave_list, container, false);
        getActivity().setTitle("My Leave");

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        tv_empName=(TextView)view.findViewById(R.id.tvEmpName);
        tv_empDesignation=(TextView)view.findViewById(R.id.tvEmpDesg);
        iv_empPhoto=(ImageView) view.findViewById(R.id.ivPhoto);

        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
        Gson gson = new Gson();
        loginUser = gson.fromJson(userStr, Login.class);
        Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);

        tv_empName.setText(""+loginUser.getEmpFname()+ " "+loginUser.getEmpSname());

        getLeaveList(loginUser.getEmpId(),1);

       // prepareData();
        return view;
    }

    private void getLeaveList(int empId, int status) {
        Log.e("PARAMETERS : ", "        EMP ID : " + empId + "      STATUS : " + status);

        String base= Constants.userName +":" +Constants.password;
        String authHeader= "Basic "+ Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<MyLeaveData>> listCall = Constants.myInterface.getLeaveStatusList(authHeader,empId, status);
            listCall.enqueue(new Callback<ArrayList<MyLeaveData>>() {
                @Override
                public void onResponse(Call<ArrayList<MyLeaveData>> call, Response<ArrayList<MyLeaveData>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("LEAVE LIST : ", " ************* " + response.body());

                            LeaveList.clear();
                            LeaveList = response.body();

                            mAdapter = new PendingLeaveAdapter(LeaveList,getActivity());
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
    }

//    private void prepareData() {
//        LeaveHistoryTemp leaveHistoryTemp = new LeaveHistoryTemp("Medical Leave","Half Day","15/4/2019 to 18/4/2019","3 Days","Pending");
//        historyList.add(leaveHistoryTemp);
//
//        leaveHistoryTemp = new LeaveHistoryTemp("Medical Leave","Half Day","15/4/2019 to 18/4/2019","1 Days","Pending");
//        historyList.add(leaveHistoryTemp);
//
//        leaveHistoryTemp = new LeaveHistoryTemp("Medical Leave","Full Day","15/4/2019 to 18/4/2019","3 Days","Approve");
//        historyList.add(leaveHistoryTemp);
//
//        leaveHistoryTemp = new LeaveHistoryTemp("Sick Leave","Half Day","15/4/2019 to 18/4/2019","4 Days","Pending");
//        historyList.add(leaveHistoryTemp);
//
//        leaveHistoryTemp = new LeaveHistoryTemp("Medical Leave","Full Day","15/4/2019 to 18/4/2019","3 Days","Rejected");
//        historyList.add(leaveHistoryTemp);
//
//        leaveHistoryTemp = new LeaveHistoryTemp("Casual Leave","Half Day","15/4/2019 to 18/4/2019","5 Days","Pending");
//        historyList.add(leaveHistoryTemp);
//
//        leaveHistoryTemp = new LeaveHistoryTemp("Medical Leave","Full Day","15/4/2019 to 18/4/2019","3 Days","Approve");
//        historyList.add(leaveHistoryTemp);
//
//        leaveHistoryTemp = new LeaveHistoryTemp("Medical Leave","Half Day","15/4/2019 to 18/4/2019","3 Days","Pending");
//        historyList.add(leaveHistoryTemp);
//
//        leaveHistoryTemp = new LeaveHistoryTemp("Medical Leave","Half Day","15/4/2019 to 18/4/2019","3 Days","Pending");
//        historyList.add(leaveHistoryTemp);
//    }

}
