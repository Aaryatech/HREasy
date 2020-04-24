package com.ats.hreasy.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.hreasy.R;
import com.ats.hreasy.constant.Constants;
import com.ats.hreasy.fragment.HomeFragment;
import com.ats.hreasy.model.ForgetPass;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.utils.CommonDialog;
import com.ats.hreasy.utils.CustomSharedPreference;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity  implements View.OnClickListener {

    private EditText edMobile;
    private Button btnSubmit;
    private TextView tvLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        setTitle("Forgot Password");

        edMobile=findViewById(R.id.edMobile);
        btnSubmit=findViewById(R.id.btnSubmit);
        tvLogin=findViewById(R.id.tvLogin);

        btnSubmit.setOnClickListener(this);
        tvLogin.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.btnSubmit){

            String mobile=edMobile.getText().toString().trim();

            if (mobile.isEmpty()){
                edMobile.setError("Required");
            }else {
                edMobile.setError(null);

                forgetPassword(mobile);

            }



        }else if (v.getId()==R.id.tvLogin){
            startActivity(new Intent(ForgetPasswordActivity.this,LoginActivity.class));
            finish();
        }
    }

    private void forgetPassword(String mobile) {
        Log.e("PARAMETERS : ", "      Mobile : " + mobile);
        if (Constants.isOnline(ForgetPasswordActivity.this)) {
            final CommonDialog commonDialog = new CommonDialog(ForgetPasswordActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            String base = Constants.userName + ":" + Constants.password;
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

            Call<ForgetPass> listCall = Constants.myInterface.forgetPassword(authHeader, mobile);
            listCall.enqueue(new Callback<ForgetPass>() {
                @Override
                public void onResponse(Call<ForgetPass> call, Response<ForgetPass> response) {
                    try {
                        if (response.body() != null) {
                            commonDialog.dismiss();
                            Log.e("Data : ", "------------" + response.body());
                            ForgetPass info=response.body();
                            if (!info.getError()){

                                AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPasswordActivity.this, R.style.AlertDialogTheme);
                                builder.setTitle(getResources().getString(R.string.app_name));
                                builder.setMessage("Please check your email for password request.");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                        startActivity(new Intent(ForgetPasswordActivity.this,LoginActivity.class));
                                        finish();


                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }else{

                                Toast.makeText(ForgetPasswordActivity.this, "Unable to process!", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                            Toast.makeText(ForgetPasswordActivity.this, "Unable to process!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        Toast.makeText(ForgetPasswordActivity.this, "Unable to process!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();


                    }
                }

                @Override
                public void onFailure(Call<ForgetPass> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    Toast.makeText(ForgetPasswordActivity.this, "Unable to process!", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(ForgetPasswordActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(new Intent(ForgetPasswordActivity.this,LoginActivity.class));
        finish();

    }
}
