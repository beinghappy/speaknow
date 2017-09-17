package com.baidu.android.voicedemo.utils;

import com.baidu.android.voicedemo.bean.ReflashEvent;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by Administrator on 2017/3/26.
 */
public class EventBusUtils {
    public static void sendCommand(int command){
        EventBus.getDefault().post(new ReflashEvent(command));
    }
    public static void sendCommand(int command,String orderId){
        EventBus.getDefault().post(new ReflashEvent(command,orderId));
    }
}
