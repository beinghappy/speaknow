package com.baidu.android.voicedemo.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * SharedPreferences的工具类
 *
 */

public class CommonSpUtil {

    private static SharedPreferences SSP;
    private static SharedPreferences.Editor sipEditor;
    public static final String KEY_Line = "Line";
    public static final String KEY_Worker = "worker";
    public static final String KEY_First = "fisrt";
    public static final String KEY_FIRST_UPDATE_PASSWORD = "fisrt_update";
    public static final String KEY_SIP_ACCOUNT = "and";
    public static final String KEY_Role = "role";

    private static CommonSpUtil commonSpUtil;
    
    private CommonSpUtil(Context context){
        SSP = context.getSharedPreferences(KEY_SIP_ACCOUNT, Context.MODE_PRIVATE);
        sipEditor = SSP.edit();
    }

    public static CommonSpUtil init(Context context) {
        if (commonSpUtil == null) {
            commonSpUtil = new CommonSpUtil(context);
        }
        
        return commonSpUtil;
    }

    /**
     * 获取SharedPreferences文件中的int值
     *
     * @return
     */
    public static int getIntValue(String key) {
        return SSP.getInt(key, 0);
    }

    public static void setValue(String key, String values) {
        sipEditor.putString(key, values).commit();
    }

    public static void setValue(String key, boolean values) {
        sipEditor.putBoolean(key, values).commit();
    }

    public static void setValue(String key, int values) {
        sipEditor.putInt(key, values).commit();
    }

    /**
     * 获取SharedPreferences文件中的  string值
     *
     * @return
     */
    public static String getValue(String key) {
        return SSP.getString(key, "");
    }


    public static Float getFloatValue(String key) {
        return SSP.getFloat(key, 0);
    }

    public static void setFolatValue(String key, Float values) {
        sipEditor.putFloat(key, values).commit();
    }


    public static String getLine() {
        return SSP.getString(KEY_Line, null);
    }

    public static String getLine(String defaultLine) {
        return SSP.getString(KEY_Line, defaultLine);
    }

    public static void setLine(String name) {
        sipEditor.putString(KEY_Line, name).commit();
    }

    public static String getWorker() {
        return SSP.getString(KEY_Worker, null);
    }

    public static String getWorker(String defaultWorker) {
        return SSP.getString(KEY_Worker, defaultWorker);
    }

    public static int getCurrentRole() {
        return SSP.getInt(KEY_Role, StaticUtils.Role_User);
    }

    public static void setCurrentRole(int role) {
        sipEditor.putInt(KEY_Role, role).commit();
    }

    public static void setWorker(String name) {
        sipEditor.putString(KEY_Worker, name).commit();
    }
    public static void setNoFirst() {
        sipEditor.putBoolean(KEY_First, false).commit();
    }

    public static boolean getFirst() {
        return SSP.getBoolean(KEY_First, true);
    }

    /**
     * 是否已修改默认密码
     * @return
     */
    public static boolean firstUpdatePassword() {
        return SSP.getBoolean(KEY_FIRST_UPDATE_PASSWORD, true);
    }

    
    public static void setFirstUpdatePassword() {
        sipEditor.putBoolean(KEY_FIRST_UPDATE_PASSWORD, false).commit();
    }



}
