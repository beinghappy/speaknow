package com.baidu.android.voicedemo.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Administrator on 2017/2/8.
 */
public class LoadingDialog{
    private static ProgressDialog progressDialog;

    public static void show(Context context){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("正在创建...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
        }

        progressDialog.show();
    }

    public static void hide(){
        if(progressDialog!=null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

}
