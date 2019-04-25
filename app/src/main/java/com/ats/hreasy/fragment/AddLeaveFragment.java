package com.ats.hreasy.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.hreasy.R;
import com.ats.hreasy.adapter.BalanceLeaveAdapter;
import com.ats.hreasy.constant.Constants;
import com.ats.hreasy.interfaces.AddLeaveInterface;
import com.ats.hreasy.model.AuthorityIds;
import com.ats.hreasy.model.BalanceLeaveModel;
import com.ats.hreasy.model.BalanceLeaveTemp;
import com.ats.hreasy.model.CurrentYearModel;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.LeaveApply;
import com.ats.hreasy.model.Login;
import com.ats.hreasy.model.SaveLeaveTrail;
import com.ats.hreasy.utils.CommonDialog;
import com.ats.hreasy.utils.CustomSharedPreference;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ats.hreasy.fragment.LeaveFragment.staticEmpModel;

public class AddLeaveFragment extends Fragment implements View.OnClickListener, AddLeaveInterface {

    public Spinner spType;
    private EditText edFromDate, edToDate, edDays,edRemark;
    private TextView tvFromDate, tvToDate, tvViewBalnceLeave, tvEmpName, tvEmpDesg;
    private CircleImageView ivPhoto;
    private Button btn_apply;
    long fromDateMillis, toDateMillis;
    int yyyy, mm, dd;
    private RadioButton rbFullDay, rbHalfDay;
    private RadioGroup rgDayes;
    //int spinnerPosition;
    String selectedtext;
    CurrentYearModel currentYearModel;
    Login loginUser;

    ArrayList<BalanceLeaveModel> balanceLeaveList = new ArrayList<>();
    ArrayList<String> leaveTypeNameArray = new ArrayList<>();
    ArrayList<Integer> leaveTypeBalArray = new ArrayList<>();
    ArrayList<Integer> leaveTypeIdArray = new ArrayList<>();


    ArrayList<String> typeNameArray = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_leave, container, false);
        spType = (Spinner) view.findViewById(R.id.spType);
        edFromDate = view.findViewById(R.id.edFromDate);
        edToDate = view.findViewById(R.id.edToDate);
        tvFromDate = view.findViewById(R.id.tvFromDate);
        tvToDate = view.findViewById(R.id.tvToDate);
        tvViewBalnceLeave = view.findViewById(R.id.tv_balanceLeave);
        edDays = view.findViewById(R.id.edTotalDays);
        edRemark = view.findViewById(R.id.edRemark);

        btn_apply=view.findViewById(R.id.btn_apply);

        tvEmpName = view.findViewById(R.id.tvEmpName);
        tvEmpDesg = view.findViewById(R.id.tvEmpDesg);
        ivPhoto = view.findViewById(R.id.ivPhoto);

        rbFullDay = view.findViewById(R.id.rbFullday);
        rbHalfDay = view.findViewById(R.id.rbHalfDay);
        rgDayes = view.findViewById(R.id.rg);

        rbFullDay.setChecked(true);

        edFromDate.setOnClickListener(this);
        edToDate.setOnClickListener(this);
        btn_apply.setOnClickListener(this);
        tvViewBalnceLeave.setOnClickListener(this);

        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
        Gson gson = new Gson();
        loginUser = gson.fromJson(userStr, Login.class);
        Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);


        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = formatter.format(todayDate);
        Log.e("Mytag", "todayString" + currentDate);
        edFromDate.setText(currentDate);

        String toDate = edFromDate.getText().toString();
        edToDate.setText(toDate);

        getDays(edFromDate.getText().toString().trim(), edToDate.getText().toString().trim());

        try {
            if (staticEmpModel != null) {

                tvEmpName.setText("" + staticEmpModel.getEmpFname() + " " + staticEmpModel.getEmpMname() + " " + staticEmpModel.getEmpSname());
                tvEmpDesg.setText("" + staticEmpModel.getEmpMobile1());

                String imageUri = String.valueOf(staticEmpModel.getEmpPhoto());
                try {
                    Picasso.with(getContext()).load(imageUri).placeholder(getActivity().getResources().getDrawable(R.drawable.profile)).into(ivPhoto);

                } catch (Exception e) {
                }

                getCurrentYear(staticEmpModel.getEmpId());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


//        rgDayes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                int radioButtonID = group.getCheckedRadioButtonId();
//                View radioButton = group.findViewById(radioButtonID);
//                int idx = group.indexOfChild(radioButton);
//                RadioButton r = (RadioButton) group.getChildAt(idx);
//                selectedtext = r.getText().toString();
//
//                Log.e("Radio1", "----------" + selectedtext);
//                Log.e("Radio", "----------" + idx);
//
//            }
//        });
//
//        if(rgDayes.getCheckedRadioButtonId()==-1){
//
//            //     boolean checked = ((RadioButton) view).isChecked();
//            selectedtext= "Full day";
//
//        }

        return view;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edFromDate) {
            int yr, mn, dy;
            Calendar purchaseCal;
            long minDate = 0;
            purchaseCal = Calendar.getInstance();
            purchaseCal.add(Calendar.DAY_OF_MONTH, -7);
            minDate = purchaseCal.getTime().getTime();
            purchaseCal.setTimeInMillis(fromDateMillis);
            yr = purchaseCal.get(Calendar.YEAR);
            mn = purchaseCal.get(Calendar.MONTH);
            dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getContext(), fromDateListener, yr, mn, dy);
            dialog.getDatePicker().setMinDate(minDate);
            dialog.show();

        } else if (v.getId() == R.id.edToDate) {

            int yr, mn, dy;
            long minValue = 0;
            Calendar purchaseCal;
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
            String fromDate = edFromDate.getText().toString().trim();
            Date fromdate = null;

            try {
                fromdate = formatter1.parse(fromDate);//catch exception
            } catch (ParseException e) {
                e.printStackTrace();
            }

            purchaseCal = Calendar.getInstance();
            purchaseCal.add(Calendar.DAY_OF_MONTH, -7);
            minValue = purchaseCal.getTime().getTime();
            purchaseCal.setTimeInMillis(toDateMillis);
            yr = purchaseCal.get(Calendar.YEAR);
            mn = purchaseCal.get(Calendar.MONTH);
            dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getContext(), toDateListener, yr, mn, dy);
            dialog.getDatePicker().setMinDate(fromdate.getTime());
            //dialog.getDatePicker().setMinDate(purchaseCal.getTimeInMillis());
            dialog.show();

        } else if (v.getId() == R.id.tv_balanceLeave) {
            new LeaveTypeDialog(getContext()).show();
        }else if(v.getId()==R.id.btn_apply)
        {


            getAuthIdByEmpId(staticEmpModel.getEmpId());

//            LeaveApply leaveApply=new LeaveApply(0,currentYearModel.getCalYrId(),staticEmpModel.getEmpId(),1,"",strFromDate,strTodate,dayes,strRemark,1,"",1,1,1,"",0,0,0,"","","");



        }
    }

    private void getAuthIdByEmpId(Integer empId) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<AuthorityIds> listCall = Constants.myInterface.getAuthIdByEmpId(authHeader,empId);
            listCall.enqueue(new Callback<AuthorityIds>() {
                @Override
                public void onResponse(Call<AuthorityIds> call, Response<AuthorityIds> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("AUTHORITY MODEL : ", " - " + response.body());
                            AuthorityIds authorityIds=response.body();

                            int spinnerPosition = 0;

                            try {
                                spinnerPosition= leaveTypeIdArray.get(spType.getSelectedItemPosition());
                            }catch(Exception e){

                            }

                            if (spinnerPosition == 0) {

                                Toast.makeText(getActivity(), "Please select Leave Type", Toast.LENGTH_SHORT).show();
                            }else {
                                Log.e("Spinner", "--------------" + spinnerPosition);

                                String strFromDate, strTodate, strTotalDayes, strRemark;
                                strFromDate = edFromDate.getText().toString();
                                strTodate = edToDate.getText().toString();
                                strTotalDayes = edDays.getText().toString();
                                strRemark = edRemark.getText().toString();

                                float dayes = Float.parseFloat(strTotalDayes);

                                SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd");
                                SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

                                Date ToDate = null;
                                try {
                                    ToDate = formatter1.parse(strTodate);//catch exception
                                } catch (ParseException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                final String DateTo = formatter3.format(ToDate);

                                Date FromDate = null;
                                try {
                                    FromDate = formatter1.parse(strFromDate);//catch exception
                                } catch (ParseException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                final String DateFrom = formatter3.format(FromDate);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                String currDate = sdf.format(System.currentTimeMillis());

                                Log.e("fromDate", "-------------" + strFromDate);
                                Log.e("fromTo", "-------------" + strTodate);
                                Log.e("Dayes", "-------------" + strTotalDayes);
                                Log.e("Remark", "-------------" + strRemark);
                                //Log.e("Model","-------------"+staticEmpModel);

                                String dayType = "2";
                                if (rbFullDay.isChecked()) {
                                    dayType = "2";
                                } else if (rbHalfDay.isChecked()) {
                                    dayType = "1";
                                }

                                if (loginUser.getEmpId() == authorityIds.getIniAuthEmpId()) {
                                    LeaveApply leaveApply = new LeaveApply(0, currentYearModel.getCalYrId(), staticEmpModel.getEmpId(), spinnerPosition, dayType, DateFrom, DateTo, dayes, strRemark, 1, "", 1, 1, 1, currDate, 2, 0, 0, "", "", "");
                                    getApplyLeave(leaveApply);

                                } else if (loginUser.getEmpId() == authorityIds.getFinAuthEmpId()) {
                                    LeaveApply leaveApply = new LeaveApply(0, currentYearModel.getCalYrId(), staticEmpModel.getEmpId(), spinnerPosition, dayType, DateFrom, DateTo, dayes, strRemark, 1, "", 1, 1, 1, currDate, 3, 0, 0, "", "", "");
                                    getApplyLeave(leaveApply);

                                } else {
                                    LeaveApply leaveApply = new LeaveApply(0, currentYearModel.getCalYrId(), staticEmpModel.getEmpId(), spinnerPosition, dayType, DateFrom, DateTo, dayes, strRemark, 1, "", 1, 1, 1, currDate, 1, 0, 0, "", "", "");
                                    getApplyLeave(leaveApply);
                                }
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
                public void onFailure(Call<AuthorityIds> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private void getApplyLeave(LeaveApply leaveApply) {
        Log.e("PARAMETERS : ", "      ------------------------------------- LEAVE MODEL :------------------- " + leaveApply );
        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<LeaveApply> listCall = Constants.myInterface.saveLeaveApply(authHeader,leaveApply);
            listCall.enqueue(new Callback<LeaveApply>() {
                @Override
                public void onResponse(Call<LeaveApply> call, Response<LeaveApply> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("APPLY LEAVE : ", " ---------------------APPLY LEAVE---------------------- " + response.body());
                            LeaveApply model=response.body();

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            String currDate = sdf.format(System.currentTimeMillis());

                            SaveLeaveTrail saveLeaveTrail = new SaveLeaveTrail(0, model.getLeaveId(), staticEmpModel.getEmpId(), "",model.getExInt1(), model.getMakerUserId(), "" + currDate);
                            saveLeaveTrail(model.getLeaveId(),saveLeaveTrail);

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
                public void onFailure(Call<LeaveApply> call, Throwable t) {
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

                            Log.e("SAVE TRAIL : ", " ------------------------------SAVE TRAIL------------------------- " + response.body());

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
    DatePickerDialog.OnDateSetListener fromDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            yyyy = year;
            mm = month + 1;
            dd = dayOfMonth;
            edFromDate.setText(dd + "-" + mm + "-" + yyyy);
            edToDate.setText(dd + "-" + mm + "-" + yyyy);
            tvFromDate.setText(yyyy + "-" + mm + "-" + dd);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -7);
            calendar.set(yyyy, mm - 1, dd);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            fromDateMillis = calendar.getTimeInMillis();

            getDays(edFromDate.getText().toString().trim(), edToDate.getText().toString().trim());

        }
    };

    DatePickerDialog.OnDateSetListener toDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            yyyy = year;
            mm = month + 1;
            dd = dayOfMonth;
            edToDate.setText(dd + "-" + mm + "-" + yyyy);
            tvToDate.setText(yyyy + "-" + mm + "-" + dd);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -7);
            calendar.set(yyyy, mm - 1, dd);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            toDateMillis = calendar.getTimeInMillis();

            getDays(edFromDate.getText().toString().trim(), edToDate.getText().toString().trim());
        }
    };

    @Override
    public void fragmentBecameVisible() {

    }

    private class LeaveTypeDialog extends Dialog {

        public Button btnCancel;
        public RecyclerView recyclerView;
        private BalanceLeaveAdapter mAdapter;
        private ArrayList<BalanceLeaveTemp> balanceList = new ArrayList<>();

        public LeaveTypeDialog(@NonNull Context context) {
            super(context);
        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setTitle("Filter");
            setContentView(R.layout.dialog_layout_balance_leave);
            setCancelable(false);

            Window window = getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.TOP | Gravity.RIGHT;
            wlp.x = 10;
            wlp.y = 10;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(wlp);

            btnCancel = (Button) findViewById(R.id.btnCancel);
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            mAdapter = new BalanceLeaveAdapter(balanceLeaveList, getActivity());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

            //prepareMovieData();
        }

        private void prepareMovieData() {

            BalanceLeaveTemp balanceLeaveTemp = new BalanceLeaveTemp("Medical Leave", 8);
            balanceList.add(balanceLeaveTemp);

            balanceLeaveTemp = new BalanceLeaveTemp("Sick Leave", 8);
            balanceList.add(balanceLeaveTemp);

            balanceLeaveTemp = new BalanceLeaveTemp("Casual Leave", 8);
            balanceList.add(balanceLeaveTemp);

            balanceLeaveTemp = new BalanceLeaveTemp("Maternity Leave", 8);
            balanceList.add(balanceLeaveTemp);

           /*  balanceLeaveTemp = new BalanceLeaveTemp("Maternity Leave", 10);
            balanceList.add(balanceLeaveTemp);

            balanceLeaveTemp = new BalanceLeaveTemp("Action & Adventure Leave", 10);
            balanceList.add(balanceLeaveTemp);

            balanceLeaveTemp = new BalanceLeaveTemp("Action & Adventure Leave", 10);
            balanceList.add(balanceLeaveTemp);*/


        }
    }


    public float getDays(String dt1, String dt2) {

        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");

        float result = 0;

        try {
            Date date1 = myFormat.parse(dt1);
            Date date2 = myFormat.parse(dt2);
            long diff = date2.getTime() - date1.getTime();
            System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            Log.e("DAYS----------------", "***************------------ " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            result = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            edDays.setText("" + ((int) result + 1));
        } catch (ParseException e) {
            e.printStackTrace();
            result = 0;
            edDays.setText("" + (int) result);
        }
        return result;
    }

    private void getCurrentYear(final Integer empId) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<CurrentYearModel> listCall = Constants.myInterface.getCurrentYear(authHeader);
            listCall.enqueue(new Callback<CurrentYearModel>() {
                @Override
                public void onResponse(Call<CurrentYearModel> call, Response<CurrentYearModel> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("CURRENT YEAR : ", " - " + response.body());
                             currentYearModel=response.body();

                            getBalanceLeave(empId, response.body().getCalYrId());

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


    private void getBalanceLeave(Integer empId, int curYrId) {
        Log.e("PARAMETERS : ", "        EMP ID : " + empId + "           CURR_YEAR_ID : " + curYrId);

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<BalanceLeaveModel>> listCall = Constants.myInterface.getBalanceLeave(authHeader, empId, curYrId);
            listCall.enqueue(new Callback<ArrayList<BalanceLeaveModel>>() {
                @Override
                public void onResponse(Call<ArrayList<BalanceLeaveModel>> call, Response<ArrayList<BalanceLeaveModel>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("BALANCE LIST : ", " - " + response.body());
                            balanceLeaveList.clear();
                            leaveTypeNameArray.clear();
                            leaveTypeIdArray.clear();
                            leaveTypeBalArray.clear();

                            leaveTypeNameArray.add("Select Leave Type");
                            leaveTypeBalArray.add(0);
                            leaveTypeIdArray.add(0);

                            balanceLeaveList = response.body();
                            for (int i = 0; i < balanceLeaveList.size(); i++) {
                                leaveTypeNameArray.add(balanceLeaveList.get(i).getLvTitle());
                                leaveTypeBalArray.add(balanceLeaveList.get(i).getBalLeave());
                                leaveTypeIdArray.add(balanceLeaveList.get(i).getLvTypeId());
                            }

                            final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, leaveTypeNameArray);
                            spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
                            spType.setAdapter(spinnerAdapter);


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
                public void onFailure(Call<ArrayList<BalanceLeaveModel>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


}
