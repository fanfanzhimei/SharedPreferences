package com.zhi.www.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.zhi.www.base.BaseActivity;

/**
 * Created by Administrator on 2016/9/23.
 */
public class LoginActivity  extends BaseActivity {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private EditText mEtUsername;
    private EditText mEtPassword;
    private CheckBox mCbAutoLogin;
    private CheckBox mCbRemenberUserInfo;
    private Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        readAccount();
        login();
    }

    private void initViews() {
        mEtUsername = (EditText) findViewById(R.id.et_username);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mCbAutoLogin = (CheckBox) findViewById(R.id.cb_auto_login);
        mCbRemenberUserInfo = (CheckBox) findViewById(R.id.cb_remenber_userInfo);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
    }

    private void readAccount() {
        sp = this.getSharedPreferences("userInfo", MODE_PRIVATE);
        String username = sp.getString("username", "");
        String password = sp.getString("password", "");
        mEtUsername.setText(username);
        mEtPassword.setText(password);

        if(sp.contains("remenber") && sp.getBoolean("remenber", true)){
            mCbRemenberUserInfo.setChecked(true);
        } else {
            mCbRemenberUserInfo.setChecked(false);
        }

        if(sp.contains("autoLogin") && sp.getBoolean("autoLogin", true)){
            mCbAutoLogin.setChecked(true);
            startWelcomeActivity();
        } else {
            mCbAutoLogin.setChecked(false);
        }
    }

    private void login() {
        editor  = sp.edit();
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoLogin();
                remenberUserInfo();
                editor.commit();
                startWelcomeActivity();
            }
        });
    }

    private void remenberUserInfo() {
        if (mCbRemenberUserInfo.isChecked()) {
            editor.putBoolean("remenber", true);
            putData();
        } else {
            editor.putBoolean("remenber", false);
            clearData();
        }
    }

    private void autoLogin() {
        if (mCbAutoLogin.isChecked()) {
            editor.putBoolean("autoLogin", true);
            putData();
        } else {
            editor.putBoolean("autoLogin", false);
            clearData();
        }
    }

    private void putData() {
        editor.putString("username", mEtUsername.getText().toString());
        editor.putString("password", mEtPassword.getText().toString());
    }

    private void clearData() {
        editor.putString("username", "");
        editor.putString("password", "");
    }

    private void startWelcomeActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}