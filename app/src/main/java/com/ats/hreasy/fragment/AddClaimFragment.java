package com.ats.hreasy.fragment;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ats.hreasy.R;
import com.ats.hreasy.interfaces.AddClaimInterface;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class AddClaimFragment extends Fragment implements AddClaimInterface,View.OnClickListener {
    public Spinner spType,spProject;
    public EditText edDate,edAmount,edRemark;
    public TextView tvDate,tvPhoto;
    public Button btnApply;
    public ImageView imageViewImage,imageViewPhotoAttach,imageViewImage1,imageViewPhotoAttach1,imageViewImage2,imageViewPhotoAttach2;
    long fromDateMillis, toDateMillis;
    int yyyy, mm, dd;
    private static final int SELECTED_PIC = 1;
    private static final int RESULT_LOAD_IMAGE = 1;

    ArrayList<String> typeNameArray = new ArrayList<>();
    ArrayList<String> projectArray = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_claim, container, false);
        spType=(Spinner)view.findViewById(R.id.spType);
        spProject=(Spinner)view.findViewById(R.id.spProject);
        edDate=(EditText)view.findViewById(R.id.edDate);
        edAmount=(EditText)view.findViewById(R.id.edAmount);
        edRemark=(EditText)view.findViewById(R.id.edRemark);
        tvDate=(TextView) view.findViewById(R.id.tvDate);
        tvPhoto=(TextView) view.findViewById(R.id.tvphoto);
        imageViewImage=(ImageView)view.findViewById(R.id.ivImage);
        imageViewPhotoAttach=(ImageView)view.findViewById(R.id.ivPhotoAttach);
        imageViewImage1=(ImageView)view.findViewById(R.id.ivImage1);
        imageViewPhotoAttach1=(ImageView)view.findViewById(R.id.ivPhotoAttach1);
        imageViewImage2=(ImageView)view.findViewById(R.id.ivImage2);
        imageViewPhotoAttach2=(ImageView)view.findViewById(R.id.ivPhotoAttach2);
        btnApply=(Button)view.findViewById(R.id.btn_apply);

        edDate.setOnClickListener(this);
        imageViewPhotoAttach.setOnClickListener(this);
        imageViewPhotoAttach1.setOnClickListener(this);
        imageViewPhotoAttach2.setOnClickListener(this);

        typeNameArray.add("Select Claim Type");
        typeNameArray.add("Claim Type 1");
        typeNameArray.add("Claim Type 2");
        typeNameArray.add("Claim Type 3");

        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, typeNameArray);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
        // android.R.layout.simple_spinner_dropdown_item
        spType.setAdapter(spinnerAdapter);

        projectArray.add("Select Project");
        projectArray.add("Java");
        projectArray.add("Android");
        projectArray.add("Php");

        final ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, projectArray);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
        // android.R.layout.simple_spinner_dropdown_item
        spProject.setAdapter(spinnerAdapter1);

        return view;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edDate) {
            int yr, mn, dy;
            if (fromDateMillis > 0) {
                Calendar purchaseCal = Calendar.getInstance();
                purchaseCal.setTimeInMillis(fromDateMillis);
                yr = purchaseCal.get(Calendar.YEAR);
                mn = purchaseCal.get(Calendar.MONTH);
                dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
            } else {
                Calendar purchaseCal = Calendar.getInstance();
                yr = purchaseCal.get(Calendar.YEAR);
                mn = purchaseCal.get(Calendar.MONTH);
                dy = purchaseCal.get(Calendar.DAY_OF_MONTH);
            }
            DatePickerDialog dialog = new DatePickerDialog(getContext(), dateListener, yr, mn, dy);
            dialog.show();

        }else if(v.getId()==R.id.ivPhotoAttach)
        {
//            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            startActivityForResult(intent, 1);
            showCameraDialog("Photo1");
        }else if(v.getId()==R.id.ivPhotoAttach1)
        {
            showCameraDialog("Photo2");
        }else if(v.getId()==R.id.ivPhotoAttach2)
        {
            showCameraDialog("Photo3");
        }
    }

    private void showCameraDialog(String type) {
        if (type.equalsIgnoreCase("Photo1")) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, SELECTED_PIC);
        }else  if (type.equalsIgnoreCase("Photo2")) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);
        }else  if (type.equalsIgnoreCase("Photo3")) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 3);
        }
    }


    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            yyyy = year;
            mm = month + 1;
            dd = dayOfMonth;
            edDate.setText(dd + "-" + mm + "-" + yyyy);
            tvDate.setText(yyyy + "-" + mm + "-" + dd);

            Calendar calendar = Calendar.getInstance();
            calendar.set(yyyy, mm - 1, dd);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            fromDateMillis = calendar.getTimeInMillis();
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
//        RESULT_OK
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();


                Bitmap bmp = null;
                try {
                    bmp = getBitmapFromUri(selectedImage);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                imageViewImage.setImageBitmap(bmp);
                //tvPhoto.setText((CharSequence) selectedImage);

            }else if(requestCode == 2 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();


            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            imageViewImage1.setImageBitmap(bmp);
        }else if(requestCode == 3 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();


            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            imageViewImage2.setImageBitmap(bmp);
        }

    }

        private Bitmap getBitmapFromUri(Uri uri) throws IOException {
            ParcelFileDescriptor parcelFileDescriptor =
                    getActivity().getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return image;
        }
    @Override
    public void fragmentBecameVisible() {

    }
}
