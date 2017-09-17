package com.baidu.android.voicedemo;

import android.app.Application;

import com.baidu.android.voicedemo.utils.CommonSpUtil;

/**
 * Created by Administrator on 2016/11/19.
 */
public class SpeakApp extends Application {

    public static String APPID = "5e659d022b9e21ad0a86e1d9d66ee2ae";


    @Override
    public void onCreate() {
        super.onCreate();
        CommonSpUtil.init(this);
    }
}
