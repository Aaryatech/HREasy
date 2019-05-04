package com.ats.hreasy.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.hreasy.R;
import com.ats.hreasy.activity.ClaimHistoryActivity;
import com.ats.hreasy.activity.HomeActivity;
import com.ats.hreasy.constant.Constants;
import com.ats.hreasy.fragment.PendingClaimListFragment;
import com.ats.hreasy.fragment.PendingLeaveListFragment;
import com.ats.hreasy.model.ClaimHistoryModel;
import com.ats.hreasy.model.ClaimHistoryTemp;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.SaveClaimTrail;
import com.ats.hreasy.model.SaveLeaveTrail;
import com.ats.hreasy.utils.CommonDialog;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingClaimAdapter extends RecyclerView.Adapter<PendingClaimAdapter.MyViewHolder> {
    private ArrayList<ClaimHistoryModel> pendingClaimList;
    private Context context;
    private int loginUserId;

   /* public PendingClaimAdapter(ArrayList<ClaimHistoryModel> pendingClaimList, Context context) {
        this.pendingClaimList = pendingClaimList;
        this.context = context;
    }*/

    public PendingClaimAdapter(ArrayList<ClaimHistoryModel> pendingClaimList, Context context, int loginUserId) {
        this.pendingClaimList = pendingClaimList;
        this.context = context;
        this.loginUserId = loginUserId;
    }

    @NonNull
    @Override
    public PendingClaimAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_claim_history, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingClaimAdapter.MyViewHolder myViewHolder, int i) {
        final ClaimHistoryModel model = pendingClaimList.get(i);
        myViewHolder.tvDate.setText(model.getClaimDate());
        myViewHolder.tvClaimType.setText(model.getClaimTypeTitle());
        myViewHolder.tvProject.setText(model.getProjectTypeTitle());
        myViewHolder.tvAmount.setText("" + model.getClaimAmount() + "/-");


        if (model.getExInt1() == 1) {
            myViewHolder.tvCancel.setVisibility(View.VISIBLE);
            myViewHolder.tvStatus.setText("Initial Pending");
            myViewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        } else if (model.getExInt1() == 2) {
            myViewHolder.tvCancel.setVisibility(View.VISIBLE);
            myViewHolder.tvStatus.setText("Final Pending");
            myViewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        } else if (model.getExInt1() == 3) {
            myViewHolder.tvStatus.setText("Final Approved");
            myViewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorApproved));
        } else if (model.getExInt1() == 8) {
            myViewHolder.tvStatus.setText("Initial Rejected");
            myViewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorRejected));
        } else if (model.getExInt1() == 9) {
            myViewHolder.tvStatus.setText("Final Rejected");
            myViewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorRejected));
        } else if (model.getExInt1() == 7) {
            myViewHolder.tvStatus.setText("Leave Cancelled");
            myViewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }



        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(model);
                Intent intent=new Intent(context, ClaimHistoryActivity.class);
                Bundle args = new Bundle();
                args.putString("model", json);
                intent.putExtra("model", json);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });

        myViewHolder.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeActivity activity=(HomeActivity)context;

                AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);
                builder.setTitle("Confirmation");
                builder.setMessage("Do you want to CANCEL the Claim for Rs. " + model.getClaimAmount() + "/-");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        final String currDate = sdf.format(System.currentTimeMillis());

                        SaveClaimTrail saveClaimTrail=new SaveClaimTrail(0,model.getClaimId(),model.getEmpId(),"Claim cancelled by employee",7,loginUserId,""+currDate);
                        updateClaimStatus(model.getClaimId(),7,saveClaimTrail);


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
        });


    }

    @Override
    public int getItemCount() {
        return pendingClaimList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  tvDate, tvClaimType, tvProject, tvAmount,tvStatus,tvCancel;
        public CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvClaimType = itemView.findViewById(R.id.tvClaimType);
            tvProject = itemView.findViewById(R.id.tvProjectName);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvCancel = itemView.findViewById(R.id.tvCancel);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }


    private void updateClaimStatus(final Integer claimId, int status, final SaveClaimTrail saveClaimTrail) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            final HomeActivity activity=(HomeActivity)context;

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


                            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);
                            builder.setTitle("" +activity.getResources().getString(R.string.app_name));
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

                        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);
                        builder.setTitle("" + activity.getResources().getString(R.string.app_name));
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

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);
                    builder.setTitle("" + activity.getResources().getString(R.string.app_name));
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
            Toast.makeText(context, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveClaimTrail(final Integer claimId, SaveClaimTrail saveClaimTrail) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        final HomeActivity activity=(HomeActivity)context;

        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
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

                            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);
                            builder.setTitle("" + activity.getResources().getString(R.string.app_name));
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

                        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);
                        builder.setTitle("" + activity.getResources().getString(R.string.app_name));
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

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);
                    builder.setTitle("" + activity.getResources().getString(R.string.app_name));
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
            Toast.makeText(context, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateClaimTrailId(final Integer claimId, int trailId) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        final HomeActivity activity=(HomeActivity)context;

        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateClaimTrailId(authHeader, claimId, trailId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPDATE CLAIM TRAID ID: ", " - " + response.body());

                            if (!response.body().getError()) {

                                Toast.makeText(context, "SUCCESS", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);
                                builder.setTitle("" + activity.getResources().getString(R.string.app_name));
                                builder.setMessage("Claim cancelled successfully.");

                                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                        HomeActivity act = (HomeActivity) context;

                                        FragmentTransaction ft = act.getSupportFragmentManager().beginTransaction();
                                        ft.replace(R.id.content_frame, new PendingClaimListFragment(), "HomeFragment");
                                        ft.commit();

                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();

                            } else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);
                                builder.setTitle("" + activity.getResources().getString(R.string.app_name));
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
                            Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);
                        builder.setTitle("" + activity.getResources().getString(R.string.app_name));
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

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);
                    builder.setTitle("" + activity.getResources().getString(R.string.app_name));
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
            Toast.makeText(context, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

}
