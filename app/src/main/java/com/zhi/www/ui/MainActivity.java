package com.zhi.www.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhi.www.base.BaseActivity;

// 此项目主要是使用Sharedreferences实现自动登录与记住密码
public class MainActivity extends BaseActivity implements View.OnClickListener{

    private SharedPreferences sp;

    private TextView mTvName;
    private Button mBtnCancelLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = this.getSharedPreferences("userInfo", MODE_PRIVATE);
        initViews();
        initData();
    }

    private void initViews() {
        mTvName = (TextView) findViewById(R.id.tv_name);
        mBtnCancelLogin = (Button) findViewById(R.id.btn_cancel_login);
        mBtnCancelLogin.setOnClickListener(this);
    }

    private void initData() {
        String name = sp.getString("username", "");
        if(!"".equals(name.trim())){
            mTvName.setText(name + ":" );
        }
        if(!sp.getBoolean("autoLogin",true)){
            mBtnCancelLogin.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel_login:
                cancelAutoLogin();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }

    private void cancelAutoLogin() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("autoLogin", false);
        editor.commit();
    }
}