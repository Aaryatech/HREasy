package com.ats.hreasy.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ats.hreasy.R;
import com.ats.hreasy.constant.Constants;
import com.ats.hreasy.interfaces.AllTaskLeaveInterface;
import com.ats.hreasy.interfaces.MyTaskLeaveInterface;
import com.ats.hreasy.model.CurrentYearModel;
import com.ats.hreasy.model.LeaveApp;
import com.ats.hreasy.model.Login;
import com.ats.hreasy.utils.CommonDialog;
import com.ats.hreasy.utils.CustomSharedPreference;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaveApprovalPendingFragment extends Fragment {

    private ViewPager viewPager;
    public static TabLayout tab;
    FragmentPagerAdapter adapterViewPager;
    public static ArrayList<LeaveApp> staticPendingLeave = new ArrayList<>();
    public static ArrayList<LeaveApp> staticInfoLeave = new ArrayList<>();

    CommonDialog commonDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leave_approval_pending, container, false);
        getActivity().setTitle("Leave Approval Pending");

        viewPager = view.findViewById(R.id.viewPager);
        tab = view.findViewById(R.id.tab);

        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
        Gson gson = new Gson();
        Login loginUser = gson.fromJson(userStr, Login.class);
        Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);

        if (loginUser != null) {
            getCurrentYear(loginUser.getEmpId());
        }


        adapterViewPager = new ViewPagerAdapter(getChildFragmentManager(), getContext());
        viewPager.setAdapter(adapterViewPager);
        tab.post(new Runnable() {
            @Override
            public void run() {
                try {
                    tab.setupWithViewPager(viewPager);
                } catch (Exception e) {
                }
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ////Log.e("POSITION : ", "----------------------" + position);

                if (position == 0) {
                    MyTaskLeaveInterface fragmentMyTask = (MyTaskLeaveInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentMyTask != null) {
                        fragmentMyTask.fragmentBecameVisible();
                    }
                } else if (position == 1) {
                    AllTaskLeaveInterface fragmentAllTask = (AllTaskLeaveInterface) adapterViewPager.instantiateItem(viewPager, position);
                    if (fragmentAllTask != null) {
                        fragmentAllTask.fragmentBecameVisible();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }


    public static class ViewPagerAdapter extends FragmentPagerAdapter {

        private Context mContext;

        public ViewPagerAdapter(FragmentManager fm, Context mContext) {
            super(fm);
            this.mContext = mContext;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new MyTaskLeaveFragment();
            } else {
                return new AllTaskLeaveFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Pending Task";
                case 1:
                    return "Info";
                default:
                    return null;
            }
        }
    }

    private void getCurrentYear(final Integer empId) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<CurrentYearModel> listCall = Constants.myInterface.getCurrentYear(authHeader);
            listCall.enqueue(new Callback<CurrentYearModel>() {
                @Override
                public void onResponse(Call<CurrentYearModel> call, Response<CurrentYearModel> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("CURRENT YEAR : ", " - " + response.body());

                            getLeaveList(empId, response.body().getCalYrId());
                            // getLeaveInfoList(empId, response.body().getCalYrId());


                            // commonDialog.dismiss();

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
                public void onFailure(Call<CurrentYearModel> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getLeaveList(final Integer empId, final int currId) {
        Log.e("PARAMETERS : ", "        EMP ID : " + empId + "            CURR ID : " + currId);

        ArrayList<Integer> statusList = new ArrayList<>();
        statusList.add(1);


        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
//            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
//            commonDialog.show();

            Call<ArrayList<LeaveApp>> listCall = Constants.myInterface.getLeaveApplyListForPending(authHeader, empId, currId);
            listCall.enqueue(new Callback<ArrayList<LeaveApp>>() {
                @Override
                public void onResponse(Call<ArrayList<LeaveApp>> call, Response<ArrayList<LeaveApp>> response) {
                    try {

                        if (response.body() != null) {

                            Log.e("PENDING LEAVE LIST : ", " - " + response.body());

                            staticPendingLeave.clear();
                            staticPendingLeave = response.body();
                            // createQuotationPDF(quotList);

                            viewPager.setCurrentItem(1);
                            viewPager.setCurrentItem(0);

                            TabLayout.Tab tab0 = tab.getTabAt(0);
                            tab0.setText("Pending Task (" + staticPendingLeave.size() + ")");


                            //  commonDialog.dismiss();


                        } else {
                            //  commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                            viewPager.setCurrentItem(1);
                            viewPager.setCurrentItem(0);
                        }

                        getLeaveInfoList(empId, currId);

                    } catch (Exception e) {
                        // commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                        getLeaveInfoList(empId, currId);

                        viewPager.setCurrentItem(1);
                        viewPager.setCurrentItem(0);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<LeaveApp>> call, Throwable t) {
                    //  commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                    getLeaveInfoList(empId, currId);

                    viewPager.setCurrentItem(1);
                    viewPager.setCurrentItem(0);
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getLeaveInfoList(Integer empId, int currId) {

        Log.e("PARAMETERS : ", "        EMP ID : " + empId + "      CURR ID : " + currId);

        ArrayList<Integer> statusList = new ArrayList<>();
        statusList.add(1);


        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
//            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
//            commonDialog.show();

            Call<ArrayList<LeaveApp>> listCall = Constants.myInterface.getLeaveApplyListForInfo(authHeader, empId, currId);
            listCall.enqueue(new Callback<ArrayList<LeaveApp>>() {
                @Override
                public void onResponse(Call<ArrayList<LeaveApp>> call, Response<ArrayList<LeaveApp>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("INFO LEAVE LIST : ", " - " + response.body());

                            staticInfoLeave.clear();
                            staticInfoLeave = response.body();
                            // createQuotationPDF(quotList);

                            TabLayout.Tab tab1 = tab.getTabAt(1);
                            tab1.setText("Info (" + staticInfoLeave.size() + ")");

                            viewPager.setCurrentItem(1);
                            viewPager.setCurrentItem(0);
                            viewPager.setCurrentItem(1);
                            viewPager.setCurrentItem(0);

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                            viewPager.setCurrentItem(1);
                            viewPager.setCurrentItem(0);
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                        viewPager.setCurrentItem(1);
                        viewPager.setCurrentItem(0);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<LeaveApp>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                    viewPager.setCurrentItem(1);
                    viewPager.setCurrentItem(0);
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

}
