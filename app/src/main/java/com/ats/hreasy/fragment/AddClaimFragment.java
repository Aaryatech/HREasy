package com.ats.hreasy.fragment;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
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

import com.ats.hreasy.BuildConfig;
import com.ats.hreasy.R;
import com.ats.hreasy.constant.Constants;
import com.ats.hreasy.interfaces.AddClaimInterface;
import com.ats.hreasy.model.AuthorityIds;
import com.ats.hreasy.model.ClaimApply;
import com.ats.hreasy.model.ClaimProof;
import com.ats.hreasy.model.ClaimType;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.Login;
import com.ats.hreasy.model.ProjectList;
import com.ats.hreasy.model.SaveClaimTrail;
import com.ats.hreasy.utils.CommonDialog;
import com.ats.hreasy.utils.CustomSharedPreference;
import com.ats.hreasy.utils.PermissionsUtil;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.ats.hreasy.fragment.ClaimFragment.staticEmpClaimModel;


public class AddClaimFragment extends Fragment implements AddClaimInterface, View.OnClickListener {
    public Spinner spType, spProject;
    public EditText edDate, edAmount, edRemark;
    public TextView tvDate, tvPhoto, tvEmpName, tvEmpDesg;
    private CircleImageView ivPhoto;
    public Button btnApply;
    public ImageView imageViewImage, imageViewPhotoAttach, imageViewImage1, imageViewPhotoAttach1, imageViewImage2, imageViewPhotoAttach2;
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


    //Image
    File folder = new File(Environment.getExternalStorageDirectory() + File.separator, "HREasy_Attachment");
    File f;

    Bitmap myBitmap1 = null, myBitmap2 = null, myBitmap3 = null;
    public static String imagePath1 = null, imagePath2 = null, imagePath3 = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_claim, container, false);

        spType = (Spinner) view.findViewById(R.id.spType);
        spProject = (Spinner) view.findViewById(R.id.spProject);
        edDate = (EditText) view.findViewById(R.id.edDate);
        edAmount = (EditText) view.findViewById(R.id.edAmount);
        edRemark = (EditText) view.findViewById(R.id.edRemark);
        tvDate = (TextView) view.findViewById(R.id.tvDate);
        tvPhoto = (TextView) view.findViewById(R.id.tvphoto);
        imageViewImage = (ImageView) view.findViewById(R.id.ivImage);
        imageViewPhotoAttach = (ImageView) view.findViewById(R.id.ivPhotoAttach);
        imageViewImage1 = (ImageView) view.findViewById(R.id.ivImage1);
        imageViewPhotoAttach1 = (ImageView) view.findViewById(R.id.ivPhotoAttach1);
        imageViewImage2 = (ImageView) view.findViewById(R.id.ivImage2);
        imageViewPhotoAttach2 = (ImageView) view.findViewById(R.id.ivPhotoAttach2);
        btnApply = (Button) view.findViewById(R.id.btn_apply);

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


        if (PermissionsUtil.checkAndRequestPermissions(getActivity())) {
        }

        createFolder();

        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = formatter.format(todayDate);
        Log.e("Mytag", "todayString" + currentDate);
        edDate.setText(currentDate);

        getClaimType();

        if (loginUser != null) {
            getProjectList(loginUser.getCompanyId());
        }

        try {
            if (staticEmpClaimModel != null) {

                tvEmpName.setText("" + staticEmpClaimModel.getEmpFname() + " " + staticEmpClaimModel.getEmpMname() + " " + staticEmpClaimModel.getEmpSname());
                tvEmpDesg.setText("" + staticEmpClaimModel.getEmpMobile1());

                String imageUri = String.valueOf(staticEmpClaimModel.getEmpPhoto());
                try {
                    Picasso.with(getContext()).load(Constants.IMAGE_URL+""+imageUri).placeholder(getActivity().getResources().getDrawable(R.drawable.profile)).into(ivPhoto);

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

            String base = Constants.userName + ":" + Constants.password;
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

            Call<ArrayList<ProjectList>> listCall = Constants.myInterface.getProjectsListByCompanyId(authHeader, companyId);
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

            String base = Constants.userName + ":" + Constants.password;
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

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

        } else if (v.getId() == R.id.ivPhotoAttach) {

            Log.e("PHOTO 1", "--------------------------***************************");

            showCameraDialog("Photo1");

        } else if (v.getId() == R.id.ivPhotoAttach1) {

            showCameraDialog("Photo2");

        } else if (v.getId() == R.id.ivPhotoAttach2) {

            showCameraDialog("Photo3");

        } else if (v.getId() == R.id.btn_apply) {

            String strDate, strAmount, strTotalDayes, strRemark;

            strDate = edDate.getText().toString();
            strAmount = edAmount.getText().toString();
            strRemark = edRemark.getText().toString().trim();
            float amount = 0;

            try {

                amount = Float.parseFloat(strAmount);

            } catch (Exception e) {

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

            if (projectList == 0) {

                TextView viewProj = (TextView) spProject.getSelectedView();
                viewProj.setError("required");

            } else if (claimType == 0) {

                TextView viewProj = (TextView) spProject.getSelectedView();
                viewProj.setError(null);

                TextView viewType = (TextView) spType.getSelectedView();
                viewType.setError("required");

            } else if (strAmount.isEmpty()) {

                TextView viewType = (TextView) spType.getSelectedView();
                viewType.setError(null);

                edAmount.setError("required");

            } else {

                edAmount.setError(null);

                final ClaimApply claimApply = new ClaimApply(0, staticEmpClaimModel.getEmpId(), projectList, claimType, DateTo, amount, strRemark, 1, "", 1, 1, loginUser.getUserId(), currDate, 1, 0, 0, "", "", "");

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Confirmation");
                builder.setMessage("Applied claim for Rs. " + amount + "/- of type " + claimTypeNameList.get(spType.getSelectedItemPosition()) + " on date " + DateTo);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        getAuthIdByEmpId(staticEmpClaimModel.getEmpId(), loginUser.getCompanyId(), claimApply);

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

    private void getAuthIdByEmpId(Integer empId, Integer companyId, final ClaimApply claimApply) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<AuthorityIds> listCall = Constants.myInterface.getClaimAuthIds(authHeader, empId, companyId);
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

                            SaveClaimTrail saveClaimTrail = new SaveClaimTrail(0, model.getClaimId(), staticEmpClaimModel.getEmpId(), "NA", model.getExInt1(), loginUser.getUserId(), "" + currDate);
                            saveClaimTrail(model.getClaimId(), saveClaimTrail);

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

    private void saveClaimTrail(final int claimId, SaveClaimTrail saveClaimTrail) {

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

    private void updateClaimTrailId(final int claimId, int claimTrailPkey) {

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

                                commonDialog.dismiss();

                                if (imagePath1 == null && imagePath2 == null && imagePath3 == null) {
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

                                    //Toast.makeText(getContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                               /* AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
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
                                dialog.show();*/


                                    ArrayList<String> pathArray = new ArrayList<>();
                                    ArrayList<String> fileNameArray = new ArrayList<>();

                                    String photo1 = "", photo2 = "", photo3 = "";

                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");

                                    if (imagePath1 != null) {

                                        pathArray.add(imagePath1);

                                        File imgFile1 = new File(imagePath1);
                                        int pos = imgFile1.getName().lastIndexOf(".");
                                        String ext = imgFile1.getName().substring(pos + 1);
                                        photo1 = sdf.format(System.currentTimeMillis()) + "_p1." + ext;
                                        fileNameArray.add(photo1);
                                    }

                                    if (imagePath2 != null) {

                                        pathArray.add(imagePath2);

                                        File imgFile2 = new File(imagePath2);
                                        int pos2 = imgFile2.getName().lastIndexOf(".");
                                        String ext2 = imgFile2.getName().substring(pos2 + 1);
                                        photo2 = sdf.format(System.currentTimeMillis()) + "_p2." + ext2;
                                        fileNameArray.add(photo2);

                                    }

                                    if (imagePath3 != null) {

                                        pathArray.add(imagePath3);

                                        File imgFile3 = new File(imagePath3);
                                        int pos3 = imgFile3.getName().lastIndexOf(".");
                                        String ext3 = imgFile3.getName().substring(pos3 + 1);
                                        photo3 = sdf.format(System.currentTimeMillis()) + "_p3." + ext3;
                                        fileNameArray.add(photo3);

                                    }

                                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                    final String currDate = sdf1.format(System.currentTimeMillis());

                                    ClaimProof claimProof = new ClaimProof(0, claimId, "", "NA", 1, 1, loginUser.getUserId(), currDate);

                                    sendImage(pathArray, fileNameArray, claimProof);
                                }

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

    private void saveClaimDocs(ClaimProof claimProof, final String lastRec) {

        Log.e("PARAMETERS : ", "      ------------------------------------- CLAIM PROOF MODEL :------------------- " + claimProof);
        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog1 = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog1.show();

            Call<ClaimProof> listCall = Constants.myInterface.saveClaimProof(authHeader, claimProof);
            listCall.enqueue(new Callback<ClaimProof>() {
                @Override
                public void onResponse(Call<ClaimProof> call, Response<ClaimProof> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("APPLY CLAIM : ", " ---------------------APPLY CLAIM---------------------- " + response.body());
                            ClaimProof model = response.body();

                            commonDialog1.dismiss();

                            if (lastRec.equalsIgnoreCase("yes")) {
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

                            }


                        } else {
                            commonDialog1.dismiss();
                            Log.e("Data Null : ", "-----------");

                        }
                    } catch (Exception e) {
                        commonDialog1.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                    }
                }

                @Override
                public void onFailure(Call<ClaimProof> call, Throwable t) {
                    commonDialog1.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    private void sendImage(final ArrayList<String> filePath, final ArrayList<String> fileName, final ClaimProof claimProof) {

        Log.e("PARAMETER : ", "   FILE PATH : " + filePath + "            FILE NAME : " + fileName);

        final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
        commonDialog.show();

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);


        File imgFile = null;

        MultipartBody.Part[] uploadImagesParts = new MultipartBody.Part[filePath.size()];

        for (int index = 0; index < filePath.size(); index++) {
            Log.e("ATTACH ACT", "requestUpload:  image " + index + "  " + filePath.get(index));
            imgFile = new File(filePath.get(index));
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), imgFile);
            uploadImagesParts[index] = MultipartBody.Part.createFormData("file", "" + fileName.get(index), surveyBody);
        }


        // RequestBody imgName = RequestBody.create(MediaType.parse("text/plain"), "photo1");
        RequestBody imgType = RequestBody.create(MediaType.parse("text/plain"), "1");

        Call<JSONObject> call = Constants.myInterface.imageUpload(authHeader, uploadImagesParts, fileName, imgType);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                commonDialog.dismiss();

                imagePath1 = null;
                imagePath2 = null;
                imagePath3 = null;

                Log.e("Response : ", "--" + response.body());

                if (filePath.size() > 0) {
                    for (int i = 0; i < filePath.size(); i++) {
                        claimProof.setCpDocPath(fileName.get(i));

                        if (i == (fileName.size() - 1)) {
                            saveClaimDocs(claimProof, "yes");
                        } else {
                            saveClaimDocs(claimProof, "no");
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.e("Error : ", "--" + t.getMessage());
                commonDialog.dismiss();
                t.printStackTrace();
                Toast.makeText(getContext(), "Unable To Process", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void sendImageTest(final ArrayList<String> filePath, final ArrayList<String> fileName) {

        Log.e("PARAMETER : ", "   FILE PATH : " + filePath + "            FILE NAME : " + fileName);

        final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
        commonDialog.show();

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);


        File imgFile = null;

        MultipartBody.Part[] uploadImagesParts = new MultipartBody.Part[filePath.size()];

        for (int index = 0; index < filePath.size(); index++) {
            Log.e("ATTACH ACT", "requestUpload:  image " + index + "  " + filePath.get(index));
            imgFile = new File(filePath.get(index));
            RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"), imgFile);
            uploadImagesParts[index] = MultipartBody.Part.createFormData("file", "" + fileName.get(index), surveyBody);
        }


        // RequestBody imgName = RequestBody.create(MediaType.parse("text/plain"), "photo1");
        RequestBody imgType = RequestBody.create(MediaType.parse("text/plain"), "1");

        Call<JSONObject> call = Constants.myInterface.imageUpload(authHeader, uploadImagesParts, fileName, imgType);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                commonDialog.dismiss();

                imagePath1 = null;
                imagePath2 = null;
                imagePath3 = null;

                Log.e("Response : ", "--" + response.body());

            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.e("Error : ", "--" + t.getMessage());
                commonDialog.dismiss();
                t.printStackTrace();
                Toast.makeText(getContext(), "Unable To Process", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showCameraDialog(String type) {

        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                Log.e("showCameraDialog", "------------------------------PIE");

                if (type.equalsIgnoreCase("Photo1")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    f = new File(folder + File.separator, "" + System.currentTimeMillis() + "_p1.jpg");
                    String authorities = BuildConfig.APPLICATION_ID + ".provider";
                    Uri imageUri = FileProvider.getUriForFile(getActivity(), authorities, f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(intent, 101);

                } else if (type.equalsIgnoreCase("Photo2")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    f = new File(folder + File.separator, "" + System.currentTimeMillis() + "_p2.jpg");
                    String authorities = BuildConfig.APPLICATION_ID + ".provider";
                    Uri imageUri = FileProvider.getUriForFile(getActivity(), authorities, f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(intent, 102);

                } else if (type.equalsIgnoreCase("Photo3")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    f = new File(folder + File.separator, "" + System.currentTimeMillis() + "_p3.jpg");
                    String authorities = BuildConfig.APPLICATION_ID + ".provider";
                    Uri imageUri = FileProvider.getUriForFile(getActivity(), authorities, f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(intent, 103);

                }

            } else {

                if (type.equalsIgnoreCase("Photo1")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    f = new File(folder + File.separator, "" + System.currentTimeMillis() + "_p1.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(intent, 101);

                } else if (type.equalsIgnoreCase("Photo2")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    f = new File(folder + File.separator, "" + System.currentTimeMillis() + "_p2.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(intent, 102);

                } else if (type.equalsIgnoreCase("Photo3")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    f = new File(folder + File.separator, "" + System.currentTimeMillis() + "_p3.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(intent, 103);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
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

        if (resultCode == RESULT_OK && requestCode == 101) {
            try {
                String path = f.getAbsolutePath();
                File imgFile = new File(path);
                if (imgFile.exists()) {
                    myBitmap1 = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imageViewImage.setImageBitmap(myBitmap1);

                    myBitmap1 = shrinkBitmap(imgFile.getAbsolutePath(), 720, 720);

                    try {
                        FileOutputStream out = new FileOutputStream(path);
                        myBitmap1.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.flush();
                        out.close();
                        Log.e("Image Saved  ", "---------------");

                    } catch (Exception e) {
                        Log.e("Exception : ", "--------" + e.getMessage());
                        e.printStackTrace();
                    }
                }
                imagePath1 = f.getAbsolutePath();
                //tv.setText("" + f.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (resultCode == RESULT_OK && requestCode == 102) {
            try {
                String path = f.getAbsolutePath();
                File imgFile = new File(path);
                if (imgFile.exists()) {
                    myBitmap2 = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imageViewImage1.setImageBitmap(myBitmap2);

                    myBitmap2 = shrinkBitmap(imgFile.getAbsolutePath(), 720, 720);

                    try {
                        FileOutputStream out = new FileOutputStream(path);
                        myBitmap2.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.flush();
                        out.close();
                        Log.e("Image Saved  ", "---------------");

                    } catch (Exception e) {
                        Log.e("Exception : ", "--------" + e.getMessage());
                        e.printStackTrace();
                    }
                }
                imagePath2 = f.getAbsolutePath();
                //tv.setText("" + f.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (resultCode == RESULT_OK && requestCode == 103) {
            try {
                String path = f.getAbsolutePath();
                File imgFile = new File(path);
                if (imgFile.exists()) {
                    myBitmap3 = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imageViewImage2.setImageBitmap(myBitmap3);

                    myBitmap3 = shrinkBitmap(imgFile.getAbsolutePath(), 720, 720);

                    try {
                        FileOutputStream out = new FileOutputStream(path);
                        myBitmap3.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.flush();
                        out.close();
                        Log.e("Image Saved  ", "---------------");

                    } catch (Exception e) {
                        Log.e("Exception : ", "--------" + e.getMessage());
                        e.printStackTrace();
                    }
                }
                imagePath3 = f.getAbsolutePath();
                //tv.setText("" + f.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static Bitmap shrinkBitmap(String file, int width, int height) {
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) height);
        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) width);

        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }

        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        return bitmap;
    }


    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getActivity().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    public void createFolder() {
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    @Override
    public void fragmentBecameVisible() {

    }
}
