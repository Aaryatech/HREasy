package com.ats.hreasy.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ats.hreasy.R;
import com.ats.hreasy.activity.LoginActivity;
import com.ats.hreasy.constant.Constants;
import com.ats.hreasy.fcm.SharedPrefManager;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.Login;
import com.ats.hreasy.utils.CommonDialog;
import com.ats.hreasy.utils.CustomSharedPreference;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends Fragment implements View.OnClickListener {

    private EditText edPass,edConfirmPass;
    private Button btnSubmit;

    private static final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";

    private Pattern pattern;
    private Matcher matcher;

    Login loginUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        getActivity().setTitle("Change Password");

        edPass=view.findViewById(R.id.edPass);
        edConfirmPass=view.findViewById(R.id.edConfirmPass);
        btnSubmit=view.findViewById(R.id.btnSubmit);

        pattern = Pattern.compile(PASSWORD_PATTERN);

        btnSubmit.setOnClickListener(this);

        String userStr = CustomSharedPreference.getString(getContext(), CustomSharedPreference.KEY_USER);
        Gson gson = new Gson();
        loginUser = gson.fromJson(userStr, Login.class);
        Log.e("CHANGE_PASS : ", "--------USER-------" + loginUser);



        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnSubmit){

            String pass=edPass.getText().toString().trim();
            String confirmPass=edConfirmPass.getText().toString().trim();

            if (pass.isEmpty()){
                edPass.setError("Required");
            }else if (!validate(pass)){
                edPass.setError("Invalid format (password must be between 8 to 40 characters long, Contains at leadt one digit, Contain at least one lower case character, Contain at least one upper case character, Contain at least on special character from [ @ # $ % ! . ])");
            }else if (confirmPass.isEmpty()){
                edPass.setError(null);
                edConfirmPass.setError("Required");
            }else if (!pass.equalsIgnoreCase(confirmPass)){
                edPass.setError(null);
                edConfirmPass.setError("Confirm password not matched");
            }else{
                edPass.setError(null);
                edConfirmPass.setError(null);

                if (loginUser!=null) {
                    changePassword(loginUser.getUserId(), pass);
                }

            }

        }
    }

    public boolean validate(final String password) {

        matcher = pattern.matcher(password);
        return matcher.matches();

    }


    private void changePassword(int empId,String pass) {
        Log.e("PARAMETERS : ", "      EMPID : " + empId + "      PASSWORD : " + pass);
        if (Constants.isOnline(getContext())) {
            final CommonDialog commonDialog = new CommonDialog(getContext(), "Loading", "Please Wait...");
            commonDialog.show();

            String base = Constants.userName + ":" + Constants.password;
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

            Call<Info> listCall = Constants.myInterface.updatePassword(authHeader, empId ,pass);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {
                            commonDialog.dismiss();
                            Log.e("Data : ", "------------" + response.body());
                            Info info=response.body();
                            if (!info.getError()){

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
                                builder.setTitle(getResources().getString(R.string.app_name));
                                builder.setMessage("Password changed successfully");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                        loginUser.setIsVisit(0);

                                        Gson gson = new Gson();
                                        String json = gson.toJson(loginUser);
                                        CustomSharedPreference.putString(getContext(), CustomSharedPreference.KEY_USER, json);

                                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                        ft.replace(R.id.content_frame, new HomeFragment(), "Exit");
                                        ft.commit();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }else{

                                Toast.makeText(getContext(), "Unable to process!", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                            Toast.makeText(getContext(), "Unable to process!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        Toast.makeText(getContext(), "Unable to process!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();


                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    Toast.makeText(getContext(), "Unable to process!", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(getContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

}
