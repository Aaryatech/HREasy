package com.ats.hreasy.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.hreasy.R;
import com.ats.hreasy.activity.HomeActivity;
import com.ats.hreasy.constant.Constants;
import com.ats.hreasy.fragment.ClaimFragment;
import com.ats.hreasy.fragment.LeaveFragment;
import com.ats.hreasy.model.LeaveEmployeeModel;
import com.ats.hreasy.model.Login;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.MyViewHolder> {
    private ArrayList<LeaveEmployeeModel> empList;
    private Context context;
    private String pageType;
    Login loginUser;

    public EmployeeListAdapter(ArrayList<LeaveEmployeeModel> empList, Context context) {
        this.empList = empList;
        this.context = context;
    }

    public EmployeeListAdapter(ArrayList<LeaveEmployeeModel> empList, Context context, String pageType) {
        this.empList = empList;
        this.context = context;
        this.pageType = pageType;
    }

    @NonNull
    @Override
    public EmployeeListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.emp_list_adapter, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeListAdapter.MyViewHolder myViewHolder, int i) {
        final LeaveEmployeeModel model = empList.get(i);

        myViewHolder.tv_empName.setText(model.getEmpFname() + " " + model.getEmpMname() + " " + model.getEmpSname());
        myViewHolder.tv_empMob.setText("" + model.getEmpMobile1());
        String imageUri = String.valueOf(model.getEmpPhoto());

        try {
            Picasso.with(context).load(Constants.IMAGE_URL + "" + imageUri).placeholder(context.getResources().getDrawable(R.drawable.profile)).into(myViewHolder.imageView_emp);

        } catch (Exception e) {

        }

        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pageType.equalsIgnoreCase("leave")) {

                    Gson gson = new Gson();
                    String json = gson.toJson(model);
                    HomeActivity activity = (HomeActivity) context;

//                    FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.content_frame, new LeaveFragment(), "EmployeeListFragment");
//                    ft.commit();
                    LeaveFragment adf = new LeaveFragment();
                    Bundle args = new Bundle();
                    args.putString("empModel", json);
                    adf.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "EmployeeListFragment").commit();

                } else if (pageType.equalsIgnoreCase("claim")) {

                    Gson gson = new Gson();
                    String json = gson.toJson(model);
                    HomeActivity activity = (HomeActivity) context;
//                    FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.content_frame, new ClaimFragment(), "EmployeeListFragment");
//                    ft.commit();
                    ClaimFragment adf = new ClaimFragment();
                    Bundle args = new Bundle();
                    args.putString("empModel", json);
                    adf.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "EmployeeListFragment").commit();


                }

            }
        });

        if (i == 0) {
            myViewHolder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorSelected));
        }

    }

    @Override
    public int getItemCount() {
        return empList.size();
    }

    public void updateList(ArrayList<LeaveEmployeeModel> temp) {
        empList = temp;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_empName, tv_empMob;
        ImageView imageView_emp;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_empName = itemView.findViewById(R.id.tv_emp_name);
            tv_empMob = itemView.findViewById(R.id.tv_emp_mob);
            imageView_emp = itemView.findViewById(R.id.iv_emp);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
