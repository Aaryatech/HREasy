package com.ats.hreasy.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ats.hreasy.R;
import com.ats.hreasy.adapter.BalanceLeaveAdapter;
import com.ats.hreasy.interfaces.AddLeaveInterface;
import com.ats.hreasy.model.BalanceLeaveTemp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddLeaveFragment extends Fragment implements View.OnClickListener, AddLeaveInterface {
    public Spinner spType;
    private EditText edFromDate, edToDate;
    private TextView tvFromDate, tvToDate, tvViewBalnceLeave;
    long fromDateMillis, toDateMillis;
    int yyyy, mm, dd;


    ArrayList<String> typeNameArray = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_leave, container, false);
        spType = (Spinner) view.findViewById(R.id.spType);
        edFromDate = view.findViewById(R.id.edFromDate);
        edToDate = view.findViewById(R.id.edToDate);
        tvFromDate = view.findViewById(R.id.tvFromDate);
        tvToDate = view.findViewById(R.id.tvToDate);
        tvViewBalnceLeave = view.findViewById(R.id.tv_balanceLeave);

        edFromDate.setOnClickListener(this);
        edToDate.setOnClickListener(this);
        tvViewBalnceLeave.setOnClickListener(this);

        typeNameArray.add("Leave Type");
        typeNameArray.add("Sick Leave");
        typeNameArray.add("Casual Leave");
        typeNameArray.add("Maternity Leave");

        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, typeNameArray);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
        // android.R.layout.simple_spinner_dropdown_item
        spType.setAdapter(spinnerAdapter);

        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = formatter.format(todayDate);
        Log.e("Mytag", "todayString" + currentDate);
        edFromDate.setText(currentDate);

        String toDate = edFromDate.getText().toString();
        edToDate.setText(toDate);
        return view;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edFromDate) {
            int yr, mn, dy;
            Calendar purchaseCal;
            long minDate = 0;
            purchaseCal = Calendar.getInstance();
            purchaseCal.add(Calendar.DAY_OF_MONTH, -7);
            minDate = purchaseCal.getTime().getTime();
            purchaseCal.setTimeInMillis(fromDateMillis);
            yr = purchaseCal.get(Calendar.YEAR);
            mn = purchaseCal.get(Calendar.MONTH);
            dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getContext(), fromDateListener, yr, mn, dy);
            dialog.getDatePicker().setMinDate(minDate);
            dialog.show();

        } else if (v.getId() == R.id.edToDate) {
            int yr, mn, dy;
            long minValue = 0;
            Calendar purchaseCal;
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
            String fromDate = edFromDate.getText().toString().trim();
            Date fromdate = null;
            try {
                fromdate = formatter1.parse(fromDate);//catch exception
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            purchaseCal = Calendar.getInstance();
            purchaseCal.add(Calendar.DAY_OF_MONTH, -7);
            minValue = purchaseCal.getTime().getTime();
            purchaseCal.setTimeInMillis(toDateMillis);
            yr = purchaseCal.get(Calendar.YEAR);
            mn = purchaseCal.get(Calendar.MONTH);
            dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getContext(), toDateListener, yr, mn, dy);
            dialog.getDatePicker().setMinDate(fromdate.getTime());
            //dialog.getDatePicker().setMinDate(purchaseCal.getTimeInMillis());
            dialog.show();

        } else if (v.getId() == R.id.tv_balanceLeave) {
            new FilterDialog(getContext()).show();
        }
    }

    DatePickerDialog.OnDateSetListener fromDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            yyyy = year;
            mm = month + 1;
            dd = dayOfMonth;
            edFromDate.setText(dd + "-" + mm + "-" + yyyy);
            edToDate.setText(dd + "-" + mm + "-" + yyyy);
            tvFromDate.setText(yyyy + "-" + mm + "-" + dd);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -7);
            calendar.set(yyyy, mm - 1, dd);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            fromDateMillis = calendar.getTimeInMillis();
        }
    };

    DatePickerDialog.OnDateSetListener toDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            yyyy = year;
            mm = month + 1;
            dd = dayOfMonth;
            edToDate.setText(dd + "-" + mm + "-" + yyyy);
            tvToDate.setText(yyyy + "-" + mm + "-" + dd);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -7);
            calendar.set(yyyy, mm - 1, dd);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            toDateMillis = calendar.getTimeInMillis();
        }
    };

    @Override
    public void fragmentBecameVisible() {

    }

    private class FilterDialog extends Dialog {

        public Button btnCancel;
        public RecyclerView recyclerView;
        private BalanceLeaveAdapter mAdapter;
        private ArrayList<BalanceLeaveTemp> balanceList = new ArrayList<>();

        public FilterDialog(@NonNull Context context) {
            super(context);
        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setTitle("Filter");
            setContentView(R.layout.dialog_layout_balance_leave);
            setCancelable(false);

            Window window = getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.TOP | Gravity.RIGHT;
            wlp.x = 10;
            wlp.y = 10;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(wlp);

            btnCancel = (Button) findViewById(R.id.btnCancel);
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });


            mAdapter = new BalanceLeaveAdapter(balanceList, getActivity());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

            prepareMovieData();
        }

        private void prepareMovieData() {

            BalanceLeaveTemp balanceLeaveTemp = new BalanceLeaveTemp("Medical Leave", 10);
            balanceList.add(balanceLeaveTemp);

            balanceLeaveTemp = new BalanceLeaveTemp("Sick Leave", 10);
            balanceList.add(balanceLeaveTemp);

            balanceLeaveTemp = new BalanceLeaveTemp("Casual Leave", 2);
            balanceList.add(balanceLeaveTemp);

            balanceLeaveTemp = new BalanceLeaveTemp("Maternity Leave", 7);
            balanceList.add(balanceLeaveTemp);

            balanceLeaveTemp = new BalanceLeaveTemp("Maternity Leave", 10);
            balanceList.add(balanceLeaveTemp);

            balanceLeaveTemp = new BalanceLeaveTemp("Action & Adventure Leave", 10);
            balanceList.add(balanceLeaveTemp);

            balanceLeaveTemp = new BalanceLeaveTemp("Action & Adventure Leave", 10);
            balanceList.add(balanceLeaveTemp);

            balanceLeaveTemp = new BalanceLeaveTemp("Action & Adventure Leave", 10);
            balanceList.add(balanceLeaveTemp);

            balanceLeaveTemp = new BalanceLeaveTemp("Maternity Leave", 7);
            balanceList.add(balanceLeaveTemp);

            balanceLeaveTemp = new BalanceLeaveTemp("Maternity Leave", 7);
            balanceList.add(balanceLeaveTemp);

            balanceLeaveTemp = new BalanceLeaveTemp("Maternity Leave", 7);
            balanceList.add(balanceLeaveTemp);

            balanceLeaveTemp = new BalanceLeaveTemp("Maternity Leave", 7);
            balanceList.add(balanceLeaveTemp);

        }
    }
}
