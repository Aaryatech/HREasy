package com.ats.hreasy.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.hreasy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private FloatingActionButton fab1, fab2, fab;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    TextView tv_fab1, tv_fab2;
    Boolean isOpen = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home, container, false);

        tv_fab1 = (TextView) view.findViewById(R.id.tv_fab1);
        tv_fab2 = (TextView) view.findViewById(R.id.tv_fab2);

        fab_close = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab1 = (FloatingActionButton) view.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) view.findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                if (isOpen) {

                    tv_fab1.setVisibility(View.INVISIBLE);
                    tv_fab2.setVisibility(View.INVISIBLE);
                    fab1.startAnimation(fab_close);
                    fab2.startAnimation(fab_close);
                    fab1.setClickable(false);
                    fab2.setClickable(false);
                    isOpen = false;

                } else {
                    tv_fab1.setVisibility(View.VISIBLE);
                    tv_fab2.setVisibility(View.VISIBLE);
                    fab1.setClickable(true);
                    fab2.setClickable(true);
                    fab1.startAnimation(fab_open);
                    fab2.startAnimation(fab_open);
                    isOpen = true;
                }

            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "Fab1", Toast.LENGTH_SHORT).show();

            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Fab2", Toast.LENGTH_SHORT).show();

            }
        });




        return view;
    }

}
