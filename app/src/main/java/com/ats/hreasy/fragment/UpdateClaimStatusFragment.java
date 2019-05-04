package com.ats.hreasy.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
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
import com.ats.hreasy.activity.ClaimHistoryActivity;
import com.ats.hreasy.adapter.ClaimAttachmentAdapter;
import com.ats.hreasy.adapter.ClaimTrailAdapter;
import com.ats.hreasy.adapter.LeaveTrailAdapter;
import com.ats.hreasy.constant.Constants;
import com.ats.hreasy.model.ClaimApp;
import com.ats.hreasy.model.ClaimAppTemp;
import com.ats.hreasy.model.ClaimProofList;
import com.ats.hreasy.model.ClaimTrailstatus;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.LeaveApp;
import com.ats.hreasy.model.LeaveTrailTemp;
import com.ats.hreasy.model.Login;
import com.ats.hreasy.model.MyLeaveTrailData;
import com.ats.hreasy.model.SaveClaimTrail;
import com.ats.hreasy.model.SaveLeaveTrail;
import com.ats.hreasy.utils.CommonDialog;
import com.ats.hreasy.utils.CustomSharedPreference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateClaimStatusFragment extends Fragment implements View.OnClickListener {

    private TextView tvEmpName, tvEmpDesg, tvProject, tvClaimType, tvDate, tvAmount, tvRemark, tvStatus;
    private ImageView ivPhoto1, ivPhoto2, ivPhoto3;
    private Button btnApprove, btnReject;
    private EditText edRemark;
    private CircleImageView ivPhoto;

    private RecyclerView recyclerView, rvAttachment;

    ClaimApp claimModel;

    ArrayList<ClaimApp> claimModelList = new ArrayList<>();
    ClaimApp claimAppModel = new ClaimApp();

    Login loginUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_claim_status, container, false);

        tvEmpName = view.findViewById(R.id.tvEmpName);
        tvEmpDesg = view.findViewById(R.id.tvEmpDesg);
        tvProject = view.findViewById(R.id.tvProject);
        tvClaimType = view.findViewById(R.id.tvClaimType);
        tvDate = view.findViewById(R.id.tvDate);
        tvAmount = view.findViewById(R.id.tvAmount);
        tvRemark = view.findViewById(R.id.tvRemark);
        tvStatus = view.findViewById(R.id.tvStatus);

        btnApprove = view.findViewById(R.id.btnApprove);
        btnReject = view.findViewById(R.id.btnReject);

        btnApprove.setOnClickListener(this);
        btnReject.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.recyclerView);
        rvAttachment = view.findViewById(R.id.rvAttachment);


        ivPhoto = view.findViewById(R.id.ivPhoto);

        ivPhoto1 = view.findViewById(R.id.ivPhoto1);
        ivPhoto2 = view.findViewById(R.id.ivPhoto2);
        ivPhoto3 = view.findViewById(R.id.ivPhoto3);

        btnApprove = view.findViewById(R.id.btnApprove);
        btnReject = view.findViewById(R.id.btnReject);

        edRemark = view.findViewById(R.id.edRemark);

        try {
            String userStr = CustomSharedPreference.getString(getActivity(), CustomSharedPreference.KEY_USER);
            Gson gson = new Gson();
            loginUser = gson.fromJson(userStr, Login.class);
            Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            String str = getArguments().getString("modelList");
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<ClaimApp>>() {
            }.getType();
            claimModelList = gson.fromJson(str, type);

            String str1 = getArguments().getString("model");
            Gson gson1 = new Gson();
            claimAppModel = gson1.fromJson(str1, ClaimApp.class);


            Log.e("MODEL LIST --------- ", "-------------------" + claimModelList);

            setData();

        } catch (Exception e) {
            e.printStackTrace();
        }



        return view;
    }

    public void setData() {
        if (claimModelList != null) {

            if (claimModelList.size() > 0) {

                int pos = 0;
                for (int i = 0; i < claimModelList.size(); i++) {

                    if (claimAppModel.getClaimId() == claimModelList.get(i).getClaimId()) {
                        pos = i;
                    }
                }

                claimModel = claimModelList.get(pos);

                tvEmpName.setText("" + claimModel.getEmpName());
                tvProject.setText("" + claimModel.getProjectTitle());
                tvClaimType.setText("" + claimModel.getClaimTypeName());
                tvDate.setText("" + claimModel.getClaimDate());
                tvAmount.setText("" + claimModel.getClaimAmount() + "/-");
                tvRemark.setText("" + claimModel.getClaimRemarks());

                String imageUri = String.valueOf(claimModel.getEmpPhoto());
                try {
                    Picasso.with(getContext()).load(Constants.IMAGE_URL + "" + imageUri).placeholder(getActivity().getResources().getDrawable(R.drawable.profile)).into(ivPhoto);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                getClaimTrail(claimModel.getClaimId());
                getClaimProofList(claimModel.getClaimId());

                if (claimModel.getExInt1() == 1) {
                    tvStatus.setText("Initial Pending");
                    tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
                } else if (claimModel.getExInt1() == 2) {
                    tvStatus.setText("Final Pending");
                    tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
                } else if (claimModel.getExInt1() == 3) {
                    tvStatus.setText("Final Approved");
                    tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorApproved));
                } else if (claimModel.getExInt1() == 8) {
                    tvStatus.setText("Initial Rejected");
                    tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorRejected));
                } else if (claimModel.getExInt1() == 9) {
                    tvStatus.setText("Final Rejected");
                    tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorRejected));
                } else if (claimModel.getExInt1() == 7) {
                    tvStatus.setText("Leave Cancelled");
                    tvStatus.setTextColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
                }

            } else {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new ClaimApprovalPendingFragment(), "HomeFragment");
                ft.commit();

            }
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnApprove) {

            final String remark = edRemark.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            final String currDate = sdf.format(System.currentTimeMillis());

            if (claimModel != null && loginUser != null) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Confirmation");
                builder.setMessage("Do you want to APPROVE the claim of employee " + claimModel.getEmpName() + " ");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (claimModel.getCaFinAuthEmpId() == loginUser.getEmpId()) {

                            SaveClaimTrail saveClaimTrail = new SaveClaimTrail(0, claimModel.getClaimId(), claimModel.getEmpId(), remark, 3, loginUser.getMakerUserId(), currDate);
                            updateClaimStatus(claimModel.getClaimId(), 3, saveClaimTrail);

                        } else if (claimModel.getCaIniAuthEmpId() == loginUser.getEmpId()) {

                            SaveClaimTrail saveClaimTrail = new SaveClaimTrail(0, claimModel.getClaimId(), claimModel.getEmpId(), remark, 2, loginUser.getMakerUserId(), currDate);
                            updateClaimStatus(claimModel.getClaimId(), 2, saveClaimTrail);

                        }

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

            } else {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Alert");
                builder.setMessage("Oops something went wrong!");

                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }

        } else if (v.getId() == R.id.btnReject) {

            final String remark = edRemark.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            final String currDate = sdf.format(System.currentTimeMillis());


            if (claimModel != null && loginUser != null) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Confirmation");
                builder.setMessage("Do you want to REJECT the claim of employee " + claimModel.getEmpName() + " ");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (claimModel.getCaFinAuthEmpId() == loginUser.getEmpId()) {

                            SaveClaimTrail saveClaimTrail = new SaveClaimTrail(0, claimModel.getClaimId(), claimModel.getEmpId(), remark, 9, loginUser.getMakerUserId(), currDate);
                            updateClaimStatus(claimModel.getClaimId(), 9, saveClaimTrail);

                        } else if (claimModel.getCaIniAuthEmpId() == loginUser.getEmpId()) {

                            SaveClaimTrail saveClaimTrail = new SaveClaimTrail(0, claimModel.getClaimId(), claimModel.getEmpId(), remark, 8, loginUser.getMakerUserId(), currDate);
                            updateClaimStatus(claimModel.getClaimId(), 8, saveClaimTrail);

                        }

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

            } else {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                builder.setTitle("Alert");
                builder.setMessage("Oops something went wrong!");

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
    }


    private void getClaimTrail(final Integer claimId) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<ClaimTrailstatus>> listCall = Constants.myInterface.getClaimTrail(authHeader, claimId);
            listCall.enqueue(new Callback<ArrayList<ClaimTrailstatus>>() {
                @Override
                public void onResponse(Call<ArrayList<ClaimTrailstatus>> call, Response<ArrayList<ClaimTrailstatus>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("LEAVE TRAIL DATA : ", " - " + response.body());

                            ClaimTrailAdapter adapter = new ClaimTrailAdapter(response.body(), getContext());
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
                public void onFailure(Call<ArrayList<ClaimTrailstatus>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void getClaimProofList(final Integer claimId) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<ClaimProofList>> listCall = Constants.myInterface.getClaimProofList(authHeader, claimId);
            listCall.enqueue(new Callback<ArrayList<ClaimProofList>>() {
                @Override
                public void onResponse(Call<ArrayList<ClaimProofList>> call, Response<ArrayList<ClaimProofList>> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("CLAIM PROOF DATA : ", " - " + response.body());

                            ClaimAttachmentAdapter adapter = new ClaimAttachmentAdapter(response.body(), getContext());
//                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ClaimHistoryActivity.this);
//                            recyclerView.setLayoutManager(mLayoutManager);
//                            recyclerView.setItemAnimator(new DefaultItemAnimator());
//                            recyclerView.setAdapter(adapter);
                            rvAttachment.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                            rvAttachment.setAdapter(adapter);

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
                public void onFailure(Call<ArrayList<ClaimProofList>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


    private void updateClaimStatus(final Integer claimId, int status, final SaveClaimTrail saveClaimTrail) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateClaimStatus(authHeader, claimId, status);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPDATE CLAIM : ", " - " + response.body());

                            if (!response.body().getError()) {
                                saveClaimTrail(claimId, saveClaimTrail);
                            }

                            commonDialog.dismiss();

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
                        builder.setMessage("Oops something went wrong!");

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

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                    builder.setMessage("Oops something went wrong!");

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

    private void saveClaimTrail(final Integer claimId, SaveClaimTrail saveClaimTrail) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<SaveClaimTrail> listCall = Constants.myInterface.saveClaimTrail(authHeader, saveClaimTrail);
            listCall.enqueue(new Callback<SaveClaimTrail>() {
                @Override
                public void onResponse(Call<SaveClaimTrail> call, Response<SaveClaimTrail> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPDATE CLAIM : ", " - " + response.body());

                            if (response.body().getClaimTrailPkey() > 0) {
                                updateClaimTrailId(claimId, response.body().getClaimTrailPkey());
                            }

                            commonDialog.dismiss();

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
                        builder.setMessage("Oops something went wrong!");

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
                    builder.setMessage("Oops something went wrong!");

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

    private void updateClaimTrailId(final Integer claimId, int trailId) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateClaimTrailId(authHeader, claimId, trailId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPDATE CLAIM TRAID ID: ", " - " + response.body());

                            if (!response.body().getError()) {

                                // Toast.makeText(getContext(), "SUCCESS", Toast.LENGTH_SHORT).show();

                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                                builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                                builder.setMessage("Success");

                                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if (claimModelList.size() > 0) {

                                            int pos = 0;
                                            for (int i = 0; i < claimModelList.size(); i++) {

                                                if (claimAppModel.getClaimId() == claimModelList.get(i).getClaimId()) {
                                                    pos = i;
                                                }
                                            }
                                            claimModelList.remove(pos);

                                            setData();
                                            edRemark.setText("");
                                        }

                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();


                            } else {

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
                            Toast.makeText(getContext(), "FAILED", Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                        builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                        builder.setMessage("Oops something went wrong!");

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

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    builder.setTitle("" + getActivity().getResources().getString(R.string.app_name));
                    builder.setMessage("Oops something went wrong!");

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


}
