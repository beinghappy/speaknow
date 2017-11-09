package com.baidu.android.voicedemo.bean;


/**
 * Created by Administrator on 2016/11/19.
 */

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;


@Table(name = "plan")
public class Plan implements Serializable {
    /**
     * 计划的数据库的序号
     */
    @Id(column = "_id")
    private int _id;
    /**
     * 订单编号
     */
    @Id(column = "orderId")
    private String orderId;
    /**
     * 机型的标识:普通机型： 1 ；um-10：2； 299：3；300,4;
     */
    private String machineType;


    /**
     * 机型标志的显示文字
     */
    private String machineTypeDisplay;

    /**
     * 订单数量
     */
    private String orderAccount;
    /**
     * 生产课别
     */
    private String produceClass;
    /**
     * 生产线
     */
    private String produceLine;

    private String checkerJobIndex1;

    private String checkerJobIndex2;


    /**
     * 批量序号前缀
     */
    private String orderSuffix;
    /**
     * 批量序号起始
     */
    private String orderIndexBegin;
    /**
     * 开始检测的日期
     */
    private String checkDate;

    /**
     * 检查工位
     */
    private int checkTableIndex;

    private int workPositionAccount;//工位总数，默认为3
    private int checkMachineNumPer; //每次检测的机器数量

    private boolean done;//是否完成
    private boolean hasTail = false;//是否含有尾货

    public Plan(){

    }

    @Override
    public String toString() {
        return orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public String getMachineTypeDisplay() {
        return machineTypeDisplay;
    }

    public void setMachineTypeDisplay(String machineTypeDisplay) {
        this.machineTypeDisplay = machineTypeDisplay;
    }
    public String getOrderAccount() {
        return orderAccount;
    }

    public void setOrderAccount(String orderAccount) {
        this.orderAccount = orderAccount;
    }

    public String getProduceClass() {
        return produceClass;
    }

    public void setProduceClass(String produceClass) {
        this.produceClass = produceClass;
    }

    public String getProduceLine() {
        return produceLine;
    }

    public void setProduceLine(String produceLine) {
        this.produceLine = produceLine;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public int getCheckTableIndex() {
        return checkTableIndex;
    }

    public void setCheckTableIndex(int checkTableIndex) {
        this.checkTableIndex = checkTableIndex;
    }

    public String getCheckerJobIndex1() {
        return checkerJobIndex1;
    }

    public void setCheckerJobIndex1(String checkerJobIndex1) {
        this.checkerJobIndex1 = checkerJobIndex1;
    }

    public String getCheckerJobIndex2() {
        return checkerJobIndex2;
    }

    public void setCheckerJobIndex2(String checkerJobIndex2) {
        this.checkerJobIndex2 = checkerJobIndex2;
    }

    public String getOrderSuffix() {
        return orderSuffix;
    }

    public void setOrderSuffix(String orderSuffix) {
        this.orderSuffix = orderSuffix;
    }

    public String getOrderIndexBegin() {
        return orderIndexBegin;
    }

    public void setOrderIndexBegin(String orderIndexBegin) {
        this.orderIndexBegin = orderIndexBegin;
    }

    public int getWorkPositionAccount() {
        return workPositionAccount;
    }

    public void setWorkPositionAccount(int workPositionAccount) {
        this.workPositionAccount = workPositionAccount;
    }

    public int getCheckMachineNumPer() {
        return checkMachineNumPer;
    }

    public void setCheckMachineNumPer(int checkMachineNumPer) {
        this.checkMachineNumPer = checkMachineNumPer;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isHasTail() {
        return hasTail;
    }

    public void setHasTail(boolean hasTail) {
        this.hasTail = hasTail;
    }
}
