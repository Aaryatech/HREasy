package com.ats.hreasy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ats.hreasy.R;
import com.ats.hreasy.adapter.EmployeeListAdapter;
import com.ats.hreasy.constant.Constants;
import com.ats.hreasy.model.LeaveEmployeeModel;
import com.ats.hreasy.model.Login;
import com.ats.hreasy.utils.CommonDialog;
import com.ats.hreasy.utils.CustomSharedPreference;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeListFragment extends Fragment {
    private ArrayList<LeaveEmployeeModel> empList = new ArrayList<>();
    ArrayList<LeaveEmployeeModel> temp;
    private RecyclerView recyclerView;
    private EmployeeListAdapter mAdapter;
    private EditText ed_search;
    static String type;
    Login loginUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employee_list, container, false);
        getActivity().setTitle("Team");

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        ed_search = (EditText) view.findViewById(R.id.ed_search);

        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
        Gson gson = new Gson();
        loginUser = gson.fromJson(userStr, Login.class);
        Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);


        try {
            type = getArguments().getString("type");
        } catch (Exception e) {
        }

        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                FilterSearch(charSequence.toString());
                // empAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        getEmployeeList(1);


       // prepareData();
        return view;
    }

    private void getEmployeeList(Integer empId) {
        Log.e("PARAMETERS : ", "       EMPID : " + empId );
        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            String base= Constants.userName +":" +Constants.password;
            String authHeader= "Basic "+ Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);

            Call<ArrayList<LeaveEmployeeModel>> listCall = Constants.myInterface.getEmpListForClaimAuthByEmpId(authHeader,empId);
            listCall.enqueue(new Callback<ArrayList<LeaveEmployeeModel>>() {
                @Override
                public void onResponse(Call<ArrayList<LeaveEmployeeModel>> call, Response<ArrayList<LeaveEmployeeModel>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Employee List : ", "------------" + response.body());
                           empList.clear();
                           empList = response.body();

                           LeaveEmployeeModel leaveEmployeeModel=new LeaveEmployeeModel(loginUser.getEmpId(),loginUser.getEmpCode(),loginUser.getCompanyId(),loginUser.getEmpCatId(),loginUser.getEmpTypeId(),loginUser.getEmpDeptId(),loginUser.getLocId(),loginUser.getEmpFname(),loginUser.getEmpMname(),loginUser.getEmpSname(),loginUser.getEmpPhoto(),loginUser.getEmpMobile1(),loginUser.getEmpMobile2(),loginUser.getEmpEmail(),loginUser.getEmpAddressTemp(),loginUser.getEmpAddressPerm(),loginUser.getEmpBloodgrp(),loginUser.getEmpEmergencyPerson1(),loginUser.getEmpEmergencyNo1(),loginUser.getEmpEmergencyPerson2(),loginUser.getEmpEmergencyNo2(),loginUser.getEmpRatePerhr(),loginUser.getEmpJoiningDate(),loginUser.getEmpPrevExpYrs(),loginUser.getEmpPrevExpMonths(),loginUser.getEmpLeavingDate(),loginUser.getEmpLeavingReason(),loginUser.getDelStatus(),loginUser.getIsActive(),loginUser.getMakerUserId(),loginUser.getMakerEnterDatetime(),loginUser.getExInt1(),loginUser.getExInt2(),loginUser.getExInt3(),loginUser.getExVar1(),loginUser.getExVar2(),loginUser.getExVar3());
                            empList.add(0,leaveEmployeeModel);
                            Log.e("Employee List Model : ", "****************" + response.body());
                            mAdapter = new EmployeeListAdapter(empList, getActivity(), type);
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
                public void onFailure(Call<ArrayList<LeaveEmployeeModel>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private void FilterSearch(String s) {
        temp = new ArrayList();
        for (LeaveEmployeeModel d : empList) {
            if (d.getEmpFname().toLowerCase().contains(s.toLowerCase()) || d.getEmpSname().toLowerCase().contains(s.toLowerCase()) || d.getEmpMname().toLowerCase().contains(s.toLowerCase()) ) {
                temp.add(d);
            }
        }
        mAdapter.updateList(temp);
    }

//    private void prepareData() {
//
//        EmpListTemp empListTemp = new EmpListTemp(R.drawable.profile, "Anmol Shirke", "Developer");
//        empList.add(empListTemp);
//
//        empListTemp = new EmpListTemp(R.drawable.profile, "Sachin Handge", "Student");
//        empList.add(empListTemp);
//
//        empListTemp = new EmpListTemp(R.drawable.profile, "Aditya Joshi", "Student");
//        empList.add(empListTemp);
//
//        empListTemp = new EmpListTemp(R.drawable.profile, "Anmol Shirke", "Student");
//        empList.add(empListTemp);
//
//        empListTemp = new EmpListTemp(R.drawable.profile, "Pravin Bhamre", "Student");
//        empList.add(empListTemp);
//
//        empListTemp = new EmpListTemp(R.drawable.profile, "Jayant Patil", "Student");
//        empList.add(empListTemp);
//
//        empListTemp = new EmpListTemp(R.drawable.profile, "Tejas Patil", "Student");
//        empList.add(empListTemp);
//
//        empListTemp = new EmpListTemp(R.drawable.profile, "Action & Adventure", "Student");
//        empList.add(empListTemp);
//
//        empListTemp = new EmpListTemp(R.drawable.profile, "Monika", "Student");
//        empList.add(empListTemp);
//
//        empListTemp = new EmpListTemp(R.drawable.profile, "Action & Adventure", "Student");
//        empList.add(empListTemp);
//
//
//    }

}
