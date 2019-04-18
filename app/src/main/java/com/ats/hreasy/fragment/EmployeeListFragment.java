package com.ats.hreasy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ats.hreasy.R;
import com.ats.hreasy.adapter.EmployeeListAdapter;
import com.ats.hreasy.model.EmpListTemp;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeeListFragment extends Fragment {
    private ArrayList<EmpListTemp> empList = new ArrayList<>();
    ArrayList<EmpListTemp> temp;
    private RecyclerView recyclerView;
    private EmployeeListAdapter mAdapter;
    private EditText ed_search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_employee_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        ed_search=(EditText)view.findViewById(R.id.ed_search);

        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                FilterSearch(charSequence.toString());
                // empAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        mAdapter = new EmployeeListAdapter(empList,getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareMovieData();
        return view;
    }

    private void FilterSearch(String s) {
        temp = new ArrayList();
        for (EmpListTemp d : empList) {
            if (d.getName().toLowerCase().contains(s.toLowerCase())) {
                temp.add(d);
            }
        }
        mAdapter.updateList(temp);
    }

    private void prepareMovieData() {

        EmpListTemp empListTemp = new EmpListTemp(R.drawable.logo, "Action & Adventure", "Student");
        empList.add(empListTemp);

         empListTemp = new EmpListTemp(R.drawable.logo, "Action & Adventure", "Student");
        empList.add(empListTemp);

         empListTemp = new EmpListTemp(R.drawable.logo, "Action & Adventure", "Student");
        empList.add(empListTemp);

        empListTemp = new EmpListTemp(R.drawable.logo, "Action & Adventure", "Student");
        empList.add(empListTemp);

        empListTemp = new EmpListTemp(R.drawable.logo, "Action & Adventure", "Student");
        empList.add(empListTemp);

        empListTemp = new EmpListTemp(R.drawable.logo, "Action & Adventure", "Student");
        empList.add(empListTemp);

        empListTemp = new EmpListTemp(R.drawable.logo, "Action & Adventure", "Student");
        empList.add(empListTemp);

        empListTemp = new EmpListTemp(R.drawable.logo, "Action & Adventure", "Student");
        empList.add(empListTemp);

        empListTemp = new EmpListTemp(R.drawable.logo, "Action & Adventure", "Student");
        empList.add(empListTemp);

        empListTemp = new EmpListTemp(R.drawable.logo, "Action & Adventure", "Student");
        empList.add(empListTemp);

        empListTemp = new EmpListTemp(R.drawable.logo, "Action & Adventure", "Student");
        empList.add(empListTemp);

        empListTemp = new EmpListTemp(R.drawable.logo, "Action & Adventure", "Student");
        empList.add(empListTemp);

        empListTemp = new EmpListTemp(R.drawable.logo, "Monika", "Student");
        empList.add(empListTemp);

        empListTemp = new EmpListTemp(R.drawable.logo, "Action & Adventure", "Student");
        empList.add(empListTemp);





    }

}
