package com.ats.hreasy.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.ats.hreasy.R;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private FloatingActionButton fabLeave, fabClaim, fab;
    private Animation fab_open, fab_close;
    TextView tv_fab1, tv_fab2;
    Boolean isOpen = false;

    private CardView cvLeaveAppPend, cvClaimAppPend, cvMyLeavePend, cvMyClaimPend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("Home");

        tv_fab1 = (TextView) view.findViewById(R.id.tv_fab1);
        tv_fab2 = (TextView) view.findViewById(R.id.tv_fab2);

        fab_close = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fabLeave = (FloatingActionButton) view.findViewById(R.id.fabLeave);
        fabClaim = (FloatingActionButton) view.findViewById(R.id.fabClaim);

        cvLeaveAppPend = view.findViewById(R.id.cvLeaveAppPend);
        cvClaimAppPend = view.findViewById(R.id.cvClaimAppPend);
        cvMyLeavePend = view.findViewById(R.id.cvMyLeavePend);
        cvMyClaimPend = view.findViewById(R.id.cvMyClaimPend);

        cvLeaveAppPend.setOnClickListener(this);
        cvClaimAppPend.setOnClickListener(this);
        cvMyLeavePend.setOnClickListener(this);
        cvMyClaimPend.setOnClickListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpen) {

//                    tv_fab1.setVisibility(View.INVISIBLE);
//                    tv_fab2.setVisibility(View.INVISIBLE);
                    fabLeave.startAnimation(fab_close);
                    fabClaim.startAnimation(fab_close);
                    fabLeave.setClickable(false);
                    fabClaim.setClickable(false);
                    isOpen = false;

                } else {
//                    tv_fab1.setVisibility(View.VISIBLE);
//                    tv_fab2.setVisibility(View.VISIBLE);
                    fabLeave.setClickable(true);
                    fabClaim.setClickable(true);
                    fabLeave.startAnimation(fab_open);
                    fabClaim.startAnimation(fab_open);
                    isOpen = true;
                }

            }
        });

        fabLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment adf = new EmployeeListFragment();
                Bundle args = new Bundle();
                args.putString("type", "leave");
                adf.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();

            }
        });

        fabClaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment adf = new EmployeeListFragment();
                Bundle args = new Bundle();
                args.putString("type", "claim");
                adf.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();

            }
        });




        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cvLeaveAppPend) {

            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new LeaveApprovalPendingFragment(), "HomeFragment");
            ft.commit();
        }else  if (v.getId() == R.id.cvClaimAppPend) {

            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new ClaimApprovalPendingFragment(), "HomeFragment");
            ft.commit();
        }else  if (v.getId() == R.id.cvMyLeavePend) {

            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new PendingLeaveListFragment(), "HomeFragment");
            ft.commit();
        }else  if (v.getId() == R.id.cvMyClaimPend) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new PendingClaimListFragment(), "HomeFragment");
            ft.commit();

        }
    }
}
