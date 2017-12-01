package com.baidu.android.voicedemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.baidu.android.voicedemo.ui.admin.AdminLoginActivity;
import com.baidu.android.voicedemo.ui.admin.AdminRegisterActivity;
import com.baidu.speech.recognizerdemo.R;

/**
 * Created by Administrator on 2017/2/11.
 */
public class WelcomeActivity extends AppCompatActivity {

    private static final int ADMIN = 1;
    private static final int LOGIN = 2;
    private static final int NORMAL = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        handler.sendEmptyMessageDelayed(NORMAL,1400);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ADMIN:
                    toAdmin();
                    break;
                case LOGIN:
                    toLogin();
                    break;
                case NORMAL:
                    toNormal();
                    break;
            }
        }
    };

    private void toLogin() {
        startActivity(new Intent(this, AdminLoginActivity.class));
        finish();
    }
    private void toAdmin() {
        startActivity(new Intent(this, AdminRegisterActivity.class));
        finish();
    }
    private void toNormal() {
        startActivity(new Intent(this, NormalActivity.class));
        finish();
    }
}
