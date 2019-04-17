package com.ats.hreasy.fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ats.hreasy.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddLeaveFragment extends Fragment implements View.OnClickListener  {
    public Spinner spType;
    private EditText edFromDate, edToDate;
    private TextView tvFromDate, tvToDate;
    long fromDateMillis, toDateMillis;
    int yyyy, mm, dd;


    ArrayList<String> typeNameArray = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_leave, container, false);
        spType = (Spinner) view.findViewById(R.id.spType);
        edFromDate = view.findViewById(R.id.edFromDate);
        edToDate = view.findViewById(R.id.edToDate);
        tvFromDate = view.findViewById(R.id.tvFromDate);
        tvToDate = view.findViewById(R.id.tvToDate);

        edFromDate.setOnClickListener(this);
        edToDate.setOnClickListener(this);

        typeNameArray.add("Leave Type");
        typeNameArray.add("Sick Leave");
        typeNameArray.add("Casual Leave");
        typeNameArray.add("Maternity Leave");

        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, typeNameArray);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
        // android.R.layout.simple_spinner_dropdown_item
        spType.setAdapter(spinnerAdapter);

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

        }
    }

    DatePickerDialog.OnDateSetListener fromDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            yyyy = year;
            mm = month + 1;
            dd = dayOfMonth;
            edFromDate.setText(dd + "-" + mm + "-" + yyyy);
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

//            Date todate = null;
//            try {
//                todate = formatter1.parse(toDate);
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            if (formatter1.format(fromdate).compareTo((formatter1.format(todate))) > 0){
//                edToDate.setError("Invalid Date");
//            }else{
//                edToDate.setError(null);
//            }

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

}
