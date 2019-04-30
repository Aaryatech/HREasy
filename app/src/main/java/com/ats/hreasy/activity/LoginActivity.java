package com.ats.hreasy.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.hreasy.R;
import com.ats.hreasy.constant.Constants;
import com.ats.hreasy.model.Login;
import com.ats.hreasy.utils.CommonDialog;
import com.ats.hreasy.utils.CustomSharedPreference;
import com.google.gson.Gson;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public EditText ed_userName, ed_password;
    public Button btn_login;
    public TextView tv_forgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed_userName = (EditText) findViewById(R.id.ed_userName);
        ed_password = (EditText) findViewById(R.id.ed_password);
        tv_forgotPass = (TextView) findViewById(R.id.tv_forgotPassword);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            String strUserName, strPass;
            boolean isValidUserName = false, isValidPass = false;

            strUserName = ed_userName.getText().toString().trim();
            strPass = ed_password.getText().toString().trim();

            if (strUserName.isEmpty()) {
                ed_userName.setError("required");
            } else {
                ed_userName.setError(null);
                isValidUserName = true;
            }

            if (strPass.isEmpty()) {
                ed_password.setError("required");
            } else {
                ed_password.setError(null);
                isValidPass = true;
            }
            if (isValidUserName && isValidPass) {
                doLogin(strUserName, strPass);
            }
        }
    }

    private void doLogin(String strUserName, String strPass) {
        Log.e("PARAMETERS : ", "       USER NAME : " + strUserName + "      PASSWORD : " + strPass);
        if (Constants.isOnline(this)) {
            final CommonDialog commonDialog = new CommonDialog(this, "Loading", "Please Wait...");
            commonDialog.show();

            String base = Constants.userName + ":" + Constants.password;
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

            Call<Login> listCall = Constants.myInterface.doLogin(authHeader, strUserName, strPass);
            listCall.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("User Data : ", "------------" + response.body());

                            Login data = response.body();
                            if (data.getError()) {
                                commonDialog.dismiss();
                                //Toast.makeText(LoginActivity.this, "Unable to login", Toast.LENGTH_SHORT).show();

                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialogTheme);
                                builder.setTitle(getResources().getString(R.string.app_name));
                                builder.setMessage("Oops something went wrong! please check username & password.");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();

                            } else {

                                commonDialog.dismiss();
                                Gson gson = new Gson();
                                String json = gson.toJson(data);
                                CustomSharedPreference.putString(LoginActivity.this, CustomSharedPreference.KEY_USER, json);

                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();
                            }
                        } else {
                            commonDialog.dismiss();
                            //Toast.makeText(LoginActivity.this, "Unable to login", Toast.LENGTH_SHORT).show();
                            Log.e("Data Null : ", "-----------");

                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialogTheme);
                            builder.setTitle(getResources().getString(R.string.app_name));
                            builder.setMessage("Oops something went wrong! please check username & password.");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
                        //Toast.makeText(LoginActivity.this, "Unable to login", Toast.LENGTH_SHORT).show();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialogTheme);
                        builder.setTitle(getResources().getString(R.string.app_name));
                        builder.setMessage("Oops something went wrong! please check username & password.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
                public void onFailure(Call<Login> call, Throwable t) {
                    commonDialog.dismiss();
                   // Toast.makeText(LoginActivity.this, "Unable to login", Toast.LENGTH_SHORT).show();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialogTheme);
                    builder.setTitle(getResources().getString(R.string.app_name));
                    builder.setMessage("Oops something went wrong! please check username & password.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
            Toast.makeText(this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

}
