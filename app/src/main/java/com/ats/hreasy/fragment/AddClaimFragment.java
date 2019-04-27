package com.ats.hreasy.fragment;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.hreasy.R;
import com.ats.hreasy.constant.Constants;
import com.ats.hreasy.interfaces.AddClaimInterface;
import com.ats.hreasy.model.AuthorityIds;
import com.ats.hreasy.model.ClaimApply;
import com.ats.hreasy.model.ClaimType;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.Login;
import com.ats.hreasy.model.ProjectList;
import com.ats.hreasy.model.SaveClaimTrail;
import com.ats.hreasy.utils.CommonDialog;
import com.ats.hreasy.utils.CustomSharedPreference;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.FileDescriptor;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.ats.hreasy.fragment.ClaimFragment.staticEmpClaimModel;


public class AddClaimFragment extends Fragment implements AddClaimInterface,View.OnClickListener {
    public Spinner spType,spProject;
    public EditText edDate,edAmount,edRemark;
    public TextView tvDate,tvPhoto,tvEmpName,tvEmpDesg;
    private CircleImageView ivPhoto;
    public Button btnApply;
    public ImageView imageViewImage,imageViewPhotoAttach,imageViewImage1,imageViewPhotoAttach1,imageViewImage2,imageViewPhotoAttach2;
    long fromDateMillis, toDateMillis;
    int yyyy, mm, dd;
    private static final int SELECTED_PIC = 1;
    private static final int RESULT_LOAD_IMAGE = 1;
    CommonDialog commonDialog;
    Login loginUser;

    ArrayList<String> typeNameArray = new ArrayList<>();
    ArrayList<String> projectArray = new ArrayList<>();

    ArrayList<String> claimTypeNameList = new ArrayList<>();
    ArrayList<Integer> claimTypeIdList = new ArrayList<>();

    ArrayList<String> projectNameList = new ArrayList<>();
    ArrayList<Integer> projectIdList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_add_claim, container, false);

        spType=(Spinner)view.findViewById(R.id.spType);
        spProject=(Spinner)view.findViewById(R.id.spProject);
        edDate=(EditText)view.findViewById(R.id.edDate);
        edAmount=(EditText)view.findViewById(R.id.edAmount);
        edRemark=(EditText)view.findViewById(R.id.edRemark);
        tvDate=(TextView) view.findViewById(R.id.tvDate);
        tvPhoto=(TextView) view.findViewById(R.id.tvphoto);
        imageViewImage=(ImageView)view.findViewById(R.id.ivImage);
        imageViewPhotoAttach=(ImageView)view.findViewById(R.id.ivPhotoAttach);
        imageViewImage1=(ImageView)view.findViewById(R.id.ivImage1);
        imageViewPhotoAttach1=(ImageView)view.findViewById(R.id.ivPhotoAttach1);
        imageViewImage2=(ImageView)view.findViewById(R.id.ivImage2);
        imageViewPhotoAttach2=(ImageView)view.findViewById(R.id.ivPhotoAttach2);
        btnApply=(Button)view.findViewById(R.id.btn_apply);

        tvEmpName = view.findViewById(R.id.tvEmpName);
        tvEmpDesg = view.findViewById(R.id.tvEmpDesg);
        ivPhoto = view.findViewById(R.id.ivPhoto);

        String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
        Gson gson = new Gson();
        loginUser = gson.fromJson(userStr, Login.class);
        Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);


        btnApply.setOnClickListener(this);
        edDate.setOnClickListener(this);
        imageViewPhotoAttach.setOnClickListener(this);
        imageViewPhotoAttach1.setOnClickListener(this);
        imageViewPhotoAttach2.setOnClickListener(this);

        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = formatter.format(todayDate);
        Log.e("Mytag", "todayString" + currentDate);
        edDate.setText(currentDate);
        
        getClaimType();
        getProjectList(loginUser.getCompanyId());


        try {
            if (staticEmpClaimModel != null) {

                tvEmpName.setText("" + staticEmpClaimModel.getEmpFname() + " " + staticEmpClaimModel.getEmpMname() + " " + staticEmpClaimModel.getEmpSname());
                tvEmpDesg.setText("" + staticEmpClaimModel.getEmpMobile1());

                String imageUri = String.valueOf(staticEmpClaimModel.getEmpPhoto());
                try {
                    Picasso.with(getContext()).load(imageUri).placeholder(getActivity().getResources().getDrawable(R.drawable.profile)).into(ivPhoto);

                } catch (Exception e) {
                }

                //getCurrentYear(staticEmpModel.getEmpId());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void getProjectList(Integer companyId) {
        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            String base= Constants.userName +":" +Constants.password;
            String authHeader= "Basic "+ Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);

            Call<ArrayList<ProjectList>> listCall = Constants.myInterface.getProjectsListByCompanyId(authHeader,companyId);
            listCall.enqueue(new Callback<ArrayList<ProjectList>>() {
                @Override
                public void onResponse(Call<ArrayList<ProjectList>> call, Response<ArrayList<ProjectList>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("PROJECT LIST : ", " - " + response.body());

                            projectNameList.clear();
                            projectIdList.clear();

                            projectNameList.add("Select Project");
                            projectIdList.add(0);

                            if (response.body().size() > 0) {
                                for (int i = 0; i < response.body().size(); i++) {
                                    projectIdList.add(response.body().get(i).getProjectId());
                                    projectNameList.add(response.body().get(i).getProjectTitle());
                                }

                                ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, projectNameList);
                                spProject.setAdapter(projectAdapter);

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
                public void onFailure(Call<ArrayList<ProjectList>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getClaimType() {
        if (Constants.isOnline(getActivity())) {
            final CommonDialog commonDialog = new CommonDialog(getActivity(), "Loading", "Please Wait...");
            commonDialog.show();

            String base= Constants.userName +":" +Constants.password;
            String authHeader= "Basic "+ Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);

            Call<ArrayList<ClaimType>> listCall = Constants.myInterface.getClaimList(authHeader);
            listCall.enqueue(new Callback<ArrayList<ClaimType>>() {
                @Override
                public void onResponse(Call<ArrayList<ClaimType>> call, Response<ArrayList<ClaimType>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("CLAIM TYPE LIST : ", " - " + response.body());

                            claimTypeNameList.clear();
                            claimTypeIdList.clear();

                            claimTypeNameList.add("Select Claim Type");
                            claimTypeIdList.add(0);

                            if (response.body().size() > 0) {
                                for (int i = 0; i < response.body().size(); i++) {
                                    claimTypeIdList.add(response.body().get(i).getClaimTypeId());
                                    claimTypeNameList.add(response.body().get(i).getClaimTypeTitle());
                                }

                                ArrayAdapter<String> projectAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, claimTypeNameList);
                                spType.setAdapter(projectAdapter);

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
                public void onFailure(Call<ArrayList<ClaimType>> call, Throwable t) {
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
        if (v.getId() == R.id.edDate) {
            int yr, mn, dy;
            if (fromDateMillis > 0) {
                Calendar purchaseCal = Calendar.getInstance();
                purchaseCal.setTimeInMillis(fromDateMillis);
                yr = purchaseCal.get(Calendar.YEAR);
                mn = purchaseCal.get(Calendar.MONTH);
                dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
            } else {
                Calendar purchaseCal = Calendar.getInstance();
                yr = purchaseCal.get(Calendar.YEAR);
                mn = purchaseCal.get(Calendar.MONTH);
                dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
            }
            DatePickerDialog dialog = new DatePickerDialog(getContext(), dateListener, yr, mn, dy);
            dialog.show();

        }else if(v.getId()==R.id.ivPhotoAttach)
        {
//            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            startActivityForResult(intent, 1);
            showCameraDialog("Photo1");
        }else if(v.getId()==R.id.ivPhotoAttach1)
        {
            showCameraDialog("Photo2");
        }else if(v.getId()==R.id.ivPhotoAttach2)
        {
            showCameraDialog("Photo3");
        }else  if(v.getId()==R.id.btn_apply)
        {
            String strDate, strAmount, strTotalDayes, strRemark;

            strDate = edDate.getText().toString();
            strAmount = edAmount.getText().toString();
            strRemark = edRemark.getText().toString().trim();
            float amount = 0;
            try {
                 amount = Float.parseFloat(strAmount);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            

            SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

            Date ToDate = null;
            try {
                ToDate = formatter1.parse(strDate);//catch exception
            } catch (ParseException e) {
                e.printStackTrace();
            }

            final String DateTo = formatter3.format(ToDate);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            final String currDate = sdf.format(System.currentTimeMillis());

            int claimType = claimTypeIdList.get(spType.getSelectedItemPosition());
            int projectList = projectIdList.get(spProject.getSelectedItemPosition());
            if (claimType == 0) {
                TextView viewType = (TextView) spType.getSelectedView();
                viewType.setError("required");

            }else if(projectList==0)
            {
                TextView viewType = (TextView) spProject.getSelectedView();
                viewType.setError("required");

            } else if(strAmount.isEmpty()){
                edAmount.setError("required");
            }else
            {
                edAmount.setError(null);
                final ClaimApply claimApply=new ClaimApply(0,staticEmpClaimModel.getEmpId(),projectList,claimType,DateTo,amount,strRemark,1,"",1,1,1,currDate,1,0,0,"","","");

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Confirmation");
                builder.setMessage("Applied for Leave from  " + strDate);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        getAuthIdByEmpId(staticEmpClaimModel.getEmpId(), loginUser.getCompanyId(),claimApply);

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

            }

        }
    }

    private void getAuthIdByEmpId(Integer empId,Integer companyId, final ClaimApply claimApply) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<AuthorityIds> listCall = Constants.myInterface.getClaimAuthIds(authHeader, empId,companyId);
            listCall.enqueue(new Callback<AuthorityIds>() {
                @Override
                public void onResponse(Call<AuthorityIds> call, Response<AuthorityIds> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("AUTHORITY MODEL : ", " - " + response.body());
                            AuthorityIds authorityIds = response.body();


                            if (loginUser.getEmpId() == authorityIds.getFinAuthEmpId()) {
                                claimApply.setClaimFinalStatus(3);
                                claimApply.setExInt1(3);
                                getApplyClaim(claimApply);

                            } else if (loginUser.getEmpId() == authorityIds.getIniAuthEmpId()) {
                                claimApply.setClaimFinalStatus(2);
                                claimApply.setExInt1(2);
                                getApplyClaim(claimApply);

                            } else {
                                getApplyClaim(claimApply);
                            }

                            //commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                            builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                            builder.setMessage("Unable to process! please try again.");

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                        builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                        builder.setMessage("Unable to process! please try again.");

                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                }

                @Override
                public void onFailure(Call<AuthorityIds> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                    builder.setMessage("Unable to process! please try again.");

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private void getApplyClaim(ClaimApply claimApply) {

        Log.e("PARAMETERS : ", "      ------------------------------------- CLAIM MODEL :------------------- " + claimApply);
        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            //final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            //commonDialog.show();

            Call<ClaimApply> listCall = Constants.myInterface.saveClaimApply(authHeader, claimApply);
            listCall.enqueue(new Callback<ClaimApply>() {
                @Override
                public void onResponse(Call<ClaimApply> call, Response<ClaimApply> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("APPLY CLAIM : ", " ---------------------APPLY CLAIM---------------------- " + response.body());
                            ClaimApply model = response.body();

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            String currDate = sdf.format(System.currentTimeMillis());

                            SaveClaimTrail saveClaimTrail = new SaveClaimTrail(0, model.getClaimId(), staticEmpClaimModel.getEmpId(), "NA", model.getExInt1(), model.getMakerUserId(), "" + currDate);
                            saveLeaveTrail(model.getClaimId(), saveClaimTrail);

                            //commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                            builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                            builder.setMessage("Unable to process! please try again.");

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                        builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                        builder.setMessage("Unable to process! please try again.");

                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                }

                @Override
                public void onFailure(Call<ClaimApply> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                    builder.setMessage("Unable to process! please try again.");

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveLeaveTrail(final int claimId, SaveClaimTrail saveClaimTrail) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            //final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            // commonDialog.show();

            Call<SaveClaimTrail> listCall = Constants.myInterface.saveClaimTrail(authHeader, saveClaimTrail);
            listCall.enqueue(new Callback<SaveClaimTrail>() {
                @Override
                public void onResponse(Call<SaveClaimTrail> call, Response<SaveClaimTrail> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("SAVE CLAIM TRAIL : ", " ------------------------------SAVE CLAIM TRAIL------------------------- " + response.body());

                            if (response.body().getClaimTrailPkey() > 0) {
                                updateClaimTrailId(claimId, response.body().getClaimTrailPkey());
                            }

                            //commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                            builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                            builder.setMessage("Unable to process! please try again.");

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                        builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                        builder.setMessage("Unable to process! please try again.");

                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                }

                @Override
                public void onFailure(Call<SaveClaimTrail> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                    builder.setMessage("Unable to process! please try again.");

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateClaimTrailId(int claimId, int claimTrailPkey) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            // final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            // commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateClaimTrailId(authHeader, claimId, claimTrailPkey);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPDATE CLAIM : ", " - " + response.body());

                            if (!response.body().getError()) {

                                //Toast.makeText(getContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                                builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                                builder.setMessage("Claim applied successfully");

                                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                        ft.replace(R.id.content_frame, new HomeFragment(), "Exit");
                                        ft.commit();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();

                            } else {
                                //Toast.makeText(getContext(), "FAILED", Toast.LENGTH_SHORT).show();

                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                                builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                                builder.setMessage("Unable to process! please try again.");

                                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();

                            }

                            commonDialog.dismiss();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                            // Toast.makeText(getContext(), "FAILED", Toast.LENGTH_SHORT).show();

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                            builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                            builder.setMessage("Unable to process! please try again.");

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                        //   Toast.makeText(getContext(), "FAILED", Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                        builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                        builder.setMessage("Unable to process! please try again.");

                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                    //  Toast.makeText(getContext(), "FAILED", Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                    builder.setMessage("Unable to process! please try again.");

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void showCameraDialog(String type) {
        if (type.equalsIgnoreCase("Photo1")) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, SELECTED_PIC);
        }else  if (type.equalsIgnoreCase("Photo2")) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);
        }else  if (type.equalsIgnoreCase("Photo3")) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 3);
        }
    }


    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            yyyy = year;
            mm = month + 1;
            dd = dayOfMonth;
            edDate.setText(dd + "-" + mm + "-" + yyyy);
            tvDate.setText(yyyy + "-" + mm + "-" + dd);

            Calendar calendar = Calendar.getInstance();
            calendar.set(yyyy, mm - 1, dd);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            fromDateMillis = calendar.getTimeInMillis();
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
//        RESULT_OK
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();


                Bitmap bmp = null;
                try {
                    bmp = getBitmapFromUri(selectedImage);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                imageViewImage.setImageBitmap(bmp);
                //tvPhoto.setText((CharSequence) selectedImage);

            }else if(requestCode == 2 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();


            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            imageViewImage1.setImageBitmap(bmp);
        }else if(requestCode == 3 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();


            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            imageViewImage2.setImageBitmap(bmp);
        }

    }

        private Bitmap getBitmapFromUri(Uri uri) throws IOException {
            ParcelFileDescriptor parcelFileDescriptor =
                    getActivity().getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return image;
        }
    @Override
    public void fragmentBecameVisible() {

    }
}
