package com.ats.hreasy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ats.hreasy.R;

public class LoginActivity extends AppCompatActivity {
    public EditText ed_userName, ed_password;
    public Button btn_login;
    public TextView tv_forgotPass, tv_signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed_userName = (EditText) findViewById(R.id.ed_userName);
        ed_password = (EditText) findViewById(R.id.ed_password);
        tv_forgotPass = (TextView) findViewById(R.id.tv_forgotPassword);
        tv_signUp = (TextView) findViewById(R.id.tv_signUp);
        btn_login = (Button) findViewById(R.id.btn_login);
    }
}
