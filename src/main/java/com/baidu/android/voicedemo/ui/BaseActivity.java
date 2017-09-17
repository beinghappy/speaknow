package com.baidu.android.voicedemo.ui;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.os.PowerManager;
import android.view.WindowManager;

import com.baidu.speech.recognizerdemo.R;

public class BaseActivity extends Activity {

    PowerManager.WakeLock mWakeLock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWakeLock.acquire();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWakeLock.release();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
