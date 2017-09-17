package com.baidu.android.voicedemo.bean;

/**
 * Created by Administrator on 2017/3/26.
 */
public class ReflashEvent {
    private String orderId;

    public int getCommondId() {
        return commondId;
    }

    public void setCommondId(int commondId) {
        this.commondId = commondId;
    }

    private int commondId;

    public ReflashEvent(int commondId) {//事件传递参数
        this.commondId = commondId;
    }
    public ReflashEvent(int commondId,String orderId) {//事件传递参数
        this.commondId = commondId;
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
