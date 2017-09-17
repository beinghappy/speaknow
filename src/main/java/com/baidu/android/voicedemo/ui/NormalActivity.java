package com.baidu.android.voicedemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.baidu.android.voicedemo.ui.admin.AdminLoginActivity;
import com.baidu.android.voicedemo.ui.user.UserLoginActivity;
import com.baidu.speech.recognizerdemo.R;

/**
 * 两种入口：管理员和普通用户
 */
public class NormalActivity extends AppCompatActivity {
    private TextView btn_admin;
    private TextView btn_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_normal);
        btn_admin = (TextView) findViewById(R.id.btn_admin);
        btn_user = (TextView) findViewById(R.id.btn_user);

        btn_admin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //跳转机型选择界面
                toAdminLogin();
            }
        });
        btn_user.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toUserLogin();
            }
        });
    }

    private void toUserLogin() {
        startActivity(new Intent(this, UserLoginActivity.class));
        finish();
    }
    private void toAdminLogin() {
        startActivity(new Intent(this, AdminLoginActivity.class));
        finish();
    }

}
