package com.ats.hreasy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ats.hreasy.R;
import com.ats.hreasy.activity.HomeActivity;
import com.ats.hreasy.fragment.ClaimFragment;
import com.ats.hreasy.fragment.LeaveFragment;
import com.ats.hreasy.model.EmpListTemp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.MyViewHolder> {
    private ArrayList<EmpListTemp> empList;
    private Context context;
    private String pageType;

    public EmployeeListAdapter(ArrayList<EmpListTemp> empList, Context context) {
        this.empList = empList;
        this.context = context;
    }

    public EmployeeListAdapter(ArrayList<EmpListTemp> empList, Context context, String pageType) {
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
        EmpListTemp model = empList.get(i);
        myViewHolder.tv_empName.setText(model.getName());
        myViewHolder.tv_empDesignation.setText(model.getDesignation());
        String imageUri = String.valueOf(model.getImg());
        try {
            Picasso.with(context).load(imageUri).placeholder(context.getResources().getDrawable(R.drawable.profile)).into(myViewHolder.imageView_emp);

        } catch (Exception e) {
        }

        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pageType.equalsIgnoreCase("leave")) {


                    HomeActivity activity = (HomeActivity) context;

                    FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new LeaveFragment(), "EmployeeListFragment");
                    ft.commit();

                }else if (pageType.equalsIgnoreCase("claim")) {


                    HomeActivity activity = (HomeActivity) context;

                    FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new ClaimFragment(), "EmployeeListFragment");
                    ft.commit();

                }

            }
        });

        if (i==0){
            myViewHolder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorSelected));
        }

    }

    @Override
    public int getItemCount() {
        return empList.size();
    }

    public void updateList(ArrayList<EmpListTemp> temp) {
        empList = temp;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_empName, tv_empDesignation;
        ImageView imageView_emp;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_empName = itemView.findViewById(R.id.tv_emp_name);
            tv_empDesignation = itemView.findViewById(R.id.tv_emp_designation);
            imageView_emp = itemView.findViewById(R.id.iv_emp);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
