package com.baidu.android.voicedemo.utils;

import android.os.Environment;

import com.baidu.android.voicedemo.bean.Plan;
import com.baidu.android.voicedemo.ui.HomeActivity;

import java.io.File;

/**
 * Created by Administrator on 2016/11/20.
 * 主要功能
 * 1 创建文件路径、文件
 * 2 获取设备SD卡路径
 */
public class FileUtils {

    /**
     * 获取db的文件夹，没有就创建
     *
     * @return
     */
    public static String getDBDir() {
        String dir = Environment.getExternalStorageDirectory().getPath() + "/AND/db3";
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        return dir;
    }

    /**
     * 获取excel的文件夹，没有就创建
     *
     * @return
     */
    public static String getExcelDir() {
        String dir = Environment.getExternalStorageDirectory().getPath() + "/AND/OUTexcel";
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        return dir;
    }

    public static String getExcelName(Plan plan) {
        String divider = "-";
        String filaName = plan.getOrderId() + divider + plan.getProduceClass() + divider + plan.getProduceLine() + ".xls";
        return filaName;
    }

    public static String getMachineNameByType(int planType) {
        String text = "";
        if(planType == HomeActivity.CREATE_COMMON){
            text ="通用型号";
        }else  if(planType == HomeActivity.CREATE_UM_10){
            //um-10
            text ="UM-10";
        }else{
            text ="专用299档";
        }

        return text;
    }

    public static String getCheckArrayByType(int planType) {
        String text = "";
        if(planType == HomeActivity.CREATE_COMMON){
            text ="通用型号";
        }else  if(planType == HomeActivity.CREATE_UM_10){
            //um-10
            text ="UM-10";
        }else{
            text ="专用299档";
        }

        return text;
    }




    /**
     * 创建文件，或文件路径
     *
     * @param dir
     */
    public static void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }


    /**
     * 获取设备的SD路径
     *
     * @return
     */
    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        String dir = sdDir.toString();
        return dir;


    }
}
