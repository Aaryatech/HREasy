package com.ats.hreasy.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import com.ats.hreasy.activity.HomeActivity;
import com.ats.hreasy.activity.LeaveHistoryDetailActivity;
import com.ats.hreasy.constant.Constants;
import com.ats.hreasy.fragment.PendingLeaveListFragment;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.MyLeaveData;
import com.ats.hreasy.model.SaveLeaveTrail;
import com.ats.hreasy.utils.CommonDialog;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingLeaveAdapter extends RecyclerView.Adapter<PendingLeaveAdapter.MyViewHolder> {
    private ArrayList<MyLeaveData> pendingLeaveList;
    private Context context;
    private Activity activity;
    private int loginUserId;


    public PendingLeaveAdapter(ArrayList<MyLeaveData> pendingLeaveList, Context context, Activity activity, int loginUserId) {
        this.pendingLeaveList = pendingLeaveList;
        this.context = context;
        this.activity = activity;
        this.loginUserId = loginUserId;
    }

    @NonNull
    @Override
    public PendingLeaveAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_leave_history, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingLeaveAdapter.MyViewHolder myViewHolder, int i) {
        final MyLeaveData model = pendingLeaveList.get(i);
        myViewHolder.tvType.setText(model.getLvTitle());
        myViewHolder.tvDay.setText("" + model.getLeaveNumDays() + " days");
        myViewHolder.tvDate.setText("" + model.getLeaveFromdt() + " to " + model.getLeaveTodt());

        if (model.getExInt1() == 1) {
            myViewHolder.tvCancel.setVisibility(View.VISIBLE);
            myViewHolder.tvStatus.setText("Initial Pending");
            myViewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        } else if (model.getExInt1() == 2) {
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


        if (model.getLeaveDuration().equals("2")) {
            myViewHolder.tvDayType.setText("Full Day");
        } else {
            myViewHolder.tvDayType.setText("Half Day");
        }


        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(model);

                Intent intent = new Intent(context, LeaveHistoryDetailActivity.class);
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

                AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);
                builder.setTitle("Confirmation");
                builder.setMessage("Do you want to CANCEL the leave from  " + model.getLeaveFromdt() + " to " + model.getLeaveTodt() + " for " + model.getLeaveNumDays() + " days.");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        final String currDate = sdf.format(System.currentTimeMillis());


                        SaveLeaveTrail saveLeaveTrail = new SaveLeaveTrail(0, model.getLeaveId(), model.getEmpId(), "Leave cancelled by employee", 7, loginUserId, "" + currDate);
                        updateLeaveStatus(model.getLeaveId(), 7, saveLeaveTrail);


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
        return pendingLeaveList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDate, tvType, tvDayType, tvDay, tvStatus, tvCancel;
        public CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvType = itemView.findViewById(R.id.tvLeaveType);
            tvDayType = itemView.findViewById(R.id.tvDayType);
            tvDay = itemView.findViewById(R.id.tvDays);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            cardView = itemView.findViewById(R.id.cardView);
            tvCancel = itemView.findViewById(R.id.tvCancel);
        }
    }


    private void updateLeaveStatus(final Integer leaveId, int status, final SaveLeaveTrail saveLeaveTrail) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateLeaveStatus(authHeader, leaveId, status);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPDATE LEAVE : ", " - " + response.body());

                            if (!response.body().getError()) {
                                saveLeaveTrail(leaveId, saveLeaveTrail);
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

    private void saveLeaveTrail(final Integer leaveId, SaveLeaveTrail saveLeaveTrail) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<SaveLeaveTrail> listCall = Constants.myInterface.saveLeaveTrail(authHeader, saveLeaveTrail);
            listCall.enqueue(new Callback<SaveLeaveTrail>() {
                @Override
                public void onResponse(Call<SaveLeaveTrail> call, Response<SaveLeaveTrail> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("UPDATE LEAVE : ", " - " + response.body());

                            if (response.body().getTrailPkey() > 0) {
                                updateLeaveTrailId(leaveId, response.body().getTrailPkey());
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
                public void onFailure(Call<SaveLeaveTrail> call, Throwable t) {
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

    private void updateLeaveTrailId(final Integer leaveId, int trailId) {

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        if (Constants.isOnline(context)) {
            final CommonDialog commonDialog = new CommonDialog(context, "Loading", "Please Wait...");
            commonDialog.show();

            Call<Info> listCall = Constants.myInterface.updateLeaveTrailId(authHeader, leaveId, trailId);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            //   Log.e("UPDATE LEAVE : ", " - " + response.body());

                            if (!response.body().getError()) {

                                Toast.makeText(context, "SUCCESS", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);
                                builder.setTitle("" + activity.getResources().getString(R.string.app_name));
                                builder.setMessage("Leave cancelled successfully.");

                                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                        HomeActivity act = (HomeActivity) context;

                                        FragmentTransaction ft = act.getSupportFragmentManager().beginTransaction();
                                        ft.replace(R.id.content_frame, new PendingLeaveListFragment(), "HomeFragment");
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
                            Toast.makeText(activity, "FAILED", Toast.LENGTH_SHORT).show();

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
            Toast.makeText(activity, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }


}
