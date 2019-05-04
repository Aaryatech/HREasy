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
import com.ats.hreasy.adapter.ClaimHistoryAdapter;
import com.ats.hreasy.constant.Constants;
import com.ats.hreasy.interfaces.ClaimHistoryInterface;
import com.ats.hreasy.model.ClaimHistoryModel;
import com.ats.hreasy.utils.CommonDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ats.hreasy.fragment.ClaimFragment.staticEmpClaimModel;


public class ClaimHistoryFragment extends Fragment implements ClaimHistoryInterface {
    public RecyclerView recyclerView;
    public TextView tv_empName,tv_empDesignation;
    public CircleImageView iv_empPhoto;
    private ClaimHistoryAdapter mAdapter;
    private ArrayList<ClaimHistoryModel> claimHistoryList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cliam_history, container, false);

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        tv_empName=(TextView)view.findViewById(R.id.tvEmpName);
        tv_empDesignation=(TextView)view.findViewById(R.id.tvEmpDesg);
        iv_empPhoto= view.findViewById(R.id.ivPhoto);

        try {
            if (staticEmpClaimModel != null) {

                tv_empName.setText("" + staticEmpClaimModel.getEmpFname() + " " + staticEmpClaimModel.getEmpMname() + " " + staticEmpClaimModel.getEmpSname());
                tv_empDesignation.setText("" + staticEmpClaimModel.getEmpMobile1());
                getClaimList(staticEmpClaimModel.getEmpId());

                String imageUri = String.valueOf(staticEmpClaimModel.getEmpPhoto());
                try {
                    Picasso.with(getContext()).load(Constants.IMAGE_URL+""+imageUri).placeholder(getActivity().getResources().getDrawable(R.drawable.profile)).into(iv_empPhoto);

                } catch (Exception e) {
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


      //  prepareData();

        return view;
    }

    private void getClaimList(Integer empId) {

        Log.e("PARAMETERS : ", "        EMP ID : " + empId);

        ArrayList<Integer> statusList = new ArrayList<>();
        statusList.add(1);
        statusList.add(2);
        statusList.add(3);
        statusList.add(7);
        statusList.add(8);
        statusList.add(9);

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<ClaimHistoryModel>> listCall = Constants.myInterface.getClaimStatusList(authHeader, empId, statusList);
            listCall.enqueue(new Callback<ArrayList<ClaimHistoryModel>>() {
                @Override
                public void onResponse(Call<ArrayList<ClaimHistoryModel>> call, Response<ArrayList<ClaimHistoryModel>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("LEAVE LIST : ", " ************* " + response.body());

                            claimHistoryList.clear();
                            claimHistoryList = response.body();

                            mAdapter = new ClaimHistoryAdapter(claimHistoryList,getActivity());
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
                public void onFailure(Call<ArrayList<ClaimHistoryModel>> call, Throwable t) {
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
//        ClaimHistoryTemp claimHistoryTemp = new ClaimHistoryTemp("Shiv shambhoo","Claim Type","18/4/2019",100,"An employee can call in sick if he is not in a state to come to office for work. Usually, an employee is entitled to sick leave only after a stipulated period of employment in an organization","Pending");
//        claimHistoryList.add(claimHistoryTemp);
//
//         claimHistoryTemp = new ClaimHistoryTemp("Shiv shambhoo","Claim Type","18/4/2019",100,"An employee can call in sick if he is not in a state to come to office for work. Usually, an employee is entitled to sick leave only after a stipulated period of employment in an organization","Pending");
//        claimHistoryList.add(claimHistoryTemp);
//
//         claimHistoryTemp = new ClaimHistoryTemp("Shiv shambhoo","Claim Type","18/4/2019",100,"An employee can call in sick if he is not in a state to come to office for work. Usually, an employee is entitled to sick leave only after a stipulated period of employment in an organization","Pending");
//        claimHistoryList.add(claimHistoryTemp);
//
//         claimHistoryTemp = new ClaimHistoryTemp("Rusa Application","Claim Type","18/4/2019",100,"An employee can call in sick if he is not in a state to come to office for work. Usually, an employee is entitled to sick leave only after a stipulated period of employment in an organization","Pending");
//        claimHistoryList.add(claimHistoryTemp);
//
//         claimHistoryTemp = new ClaimHistoryTemp("HREasy","Claim Type","18/4/2019",100,"An employee can call in sick if he is not in a state to come to office for work. Usually, an employee is entitled to sick leave only after a stipulated period of employment in an organization","Approve");
//        claimHistoryList.add(claimHistoryTemp);
//
//        claimHistoryTemp = new ClaimHistoryTemp("HREasy","Claim Type","18/4/2019",100,"An employee can call in sick if he is not in a state to come to office for work. Usually, an employee is entitled to sick leave only after a stipulated period of employment in an organization","Pending");
//        claimHistoryList.add(claimHistoryTemp);
//
//        claimHistoryTemp = new ClaimHistoryTemp("HREasy","Claim Type","18/4/2019",100,"An employee can call in sick if he is not in a state to come to office for work. Usually, an employee is entitled to sick leave only after a stipulated period of employment in an organization","Pending");
//        claimHistoryList.add(claimHistoryTemp);
//
//        claimHistoryTemp = new ClaimHistoryTemp("HREasy","Claim Type","15/4/2019",100,"An employee can call in sick if he is not in a state to come to office for work. Usually, an employee is entitled to sick leave only after a stipulated period of employment in an organization","Pending");
//        claimHistoryList.add(claimHistoryTemp);
//    }

    @Override
    public void fragmentBecameVisible() {

    }
}
