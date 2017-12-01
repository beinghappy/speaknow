package com.baidu.android.voicedemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;


/**
 * Created by Administrator on 2016/11/23.
 * 数据库格式
 * 1.12增加 line 和 checkDate
 */
@Table(name = "record_um")
public class Record implements Parcelable {
    @Id(column = "_id")
    private int _id;

    /**
     * 计划的编号
     */
    private String orderId;

    /**
     * 产品的编号
     */
    private String produceIndex;

    /**
     * 产品的一个序列号前缀
     */
    private String orderSuffix;

    /**
     * {planId and produceId}决定的
     * testIndex：0,1,2
     * 即每个产品测两次,以及平均
     */
    private String testIndex = "0";
    /**
     * 第N组；以plan的每组机器个数为例；如果每个人每次测试4个机器，则每个level为4个
     */
    private int level;
    private String line;//检查产品的线别
    private String checkDate;//检查产品的日期
    private String checkWorker;//检查产品的工人

    //是否测试过
    private boolean done = false;

    //是否尾货
    private boolean isTail = false;

    //机器型号（手动书写时需要）
    private String machinetype;
    //测试的结果：未测试，0 ； 合格 ：１　，　不合格：２
    private int result = 0;

    //"0D", "0A","50D","50A",  "100D", "100A", "150D","150A",  "200D", "200A", "250D", "250A",
    // "300D", "280A", "300D", "280A", "250D","250A", "200D","200A", "150D","150A", "100D","100A", "50D","50A", "0D", "0A",

    //位写后缀的是D
    //从0到300
    private float checkValue0 = 0;
    private float checkValue0A = 0;
    private float checkValue50;
    private float checkValue50A;
    private float checkValue100;
    private float checkValue100A;
    private float checkValue150;
    private float checkValue150A;
    private float checkValue200;
    private float checkValue200A;
    private float checkValue250;
    private float checkValue250A;
    //um-10
    private float checkValue300;
    private float checkValue280;//这个280特殊一点
    //um-10
    private float checkValue300_2;
    private float checkValue280_2;

    //从300到0
    private float checkValue250_2;
    private float checkValue250_2A;
    private float checkValue200_2;
    private float checkValue200_2A;
    private float checkValue150_2;
    private float checkValue150_2A;
    private float checkValue100_2;
    private float checkValue100_2A;
    private float checkValue50_2;
    private float checkValue50_2A;
    private float checkValue0_2 = 0;
    private float checkValue0_2A = 0;


    public static final int result_init = 0;
    public static final int result_ok = 1;
    public static final int result_fail = 2;


    protected Record(Parcel in) {
        _id = in.readInt();
        orderId = in.readString();
        produceIndex = in.readString();
        orderSuffix = in.readString();
        testIndex = in.readString();
        level = in.readInt();
        line = in.readString();
        checkDate = in.readString();
        checkWorker = in.readString();
        done = in.readByte() != 0;
        isTail = in.readByte() != 0;
        machinetype = in.readString();
        result = in.readInt();
        checkValue0 = in.readFloat();
        checkValue0A = in.readFloat();
        checkValue50 = in.readFloat();
        checkValue50A = in.readFloat();
        checkValue100 = in.readFloat();
        checkValue100A = in.readFloat();
        checkValue150 = in.readFloat();
        checkValue150A = in.readFloat();
        checkValue200 = in.readFloat();
        checkValue200A = in.readFloat();
        checkValue250 = in.readFloat();
        checkValue250A = in.readFloat();
        checkValue300 = in.readFloat();
        checkValue280 = in.readFloat();
        checkValue300_2 = in.readFloat();
        checkValue280_2 = in.readFloat();
        checkValue250_2 = in.readFloat();
        checkValue250_2A = in.readFloat();
        checkValue200_2 = in.readFloat();
        checkValue200_2A = in.readFloat();
        checkValue150_2 = in.readFloat();
        checkValue150_2A = in.readFloat();
        checkValue100_2 = in.readFloat();
        checkValue100_2A = in.readFloat();
        checkValue50_2 = in.readFloat();
        checkValue50_2A = in.readFloat();
        checkValue0_2 = in.readFloat();
        checkValue0_2A = in.readFloat();
    }

    public static final Creator<Record> CREATOR = new Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel in) {
            return new Record(in);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };

    public String getMachinetype() {
        return machinetype;
    }

    public void setMachinetype(String machinetype) {
        this.machinetype = machinetype;
    }

    public Record() {

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public float getCheckValue0() {
        return checkValue0;
    }

    public void setCheckValue0(float checkValue0) {
        this.checkValue0 = checkValue0;
    }

    public float getCheckValue50() {
        return checkValue50;
    }

    public void setCheckValue50(float checkValue50) {
        this.checkValue50 = checkValue50;
    }

    public float getCheckValue100() {
        return checkValue100;
    }

    public void setCheckValue100(float checkValue100) {
        this.checkValue100 = checkValue100;
    }

    public float getCheckValue150() {
        return checkValue150;
    }

    public void setCheckValue150(float checkValue150) {
        this.checkValue150 = checkValue150;
    }

    public float getCheckValue200() {
        return checkValue200;
    }

    public void setCheckValue200(float checkValue200) {
        this.checkValue200 = checkValue200;
    }

    public float getCheckValue250() {
        return checkValue250;
    }

    public void setCheckValue250(float checkValue250) {
        this.checkValue250 = checkValue250;
    }

    public float getCheckValue280() {
        return checkValue280;
    }

    public float getCheckValue300() {
        return checkValue300;
    }

    public void setCheckValue300(float checkValue300) {
        this.checkValue300 = checkValue300;
    }

    public float getCheckValue300_2() {
        return checkValue300_2;
    }

    public void setCheckValue300_2(float checkValue300_2) {
        this.checkValue300_2 = checkValue300_2;
    }

    public void setCheckValue280(float checkValue280) {
        this.checkValue280 = checkValue280;
    }

    public float getCheckValue280_2() {
        return checkValue280_2;
    }

    public void setCheckValue280_2(float checkValue280_2) {
        this.checkValue280_2 = checkValue280_2;
    }

    public float getCheckValue250_2() {
        return checkValue250_2;
    }

    public void setCheckValue250_2(float checkValue250_2) {
        this.checkValue250_2 = checkValue250_2;
    }

    public float getCheckValue200_2() {
        return checkValue200_2;
    }

    public void setCheckValue200_2(float checkValue200_2) {
        this.checkValue200_2 = checkValue200_2;
    }

    public float getCheckValue150_2() {
        return checkValue150_2;
    }

    public void setCheckValue150_2(float checkValue150_2) {
        this.checkValue150_2 = checkValue150_2;
    }

    public float getCheckValue100_2() {
        return checkValue100_2;
    }

    public void setCheckValue100_2(float checkValue100_2) {
        this.checkValue100_2 = checkValue100_2;
    }

    public float getCheckValue50_2() {
        return checkValue50_2;
    }

    public void setCheckValue50_2(float checkValue50_2) {
        this.checkValue50_2 = checkValue50_2;
    }

    public float getCheckValue0_2() {
        return checkValue0_2;
    }

    public void setCheckValue0_2(float checkValue0_2) {
        this.checkValue0_2 = checkValue0_2;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTestIndex() {
        return testIndex;
    }

    public void setTestIndex(String testIndex) {
        this.testIndex = testIndex;
    }

    public String getProduceIndex() {
        return produceIndex;
    }

    public void setProduceIndex(String produceIndex) {
        this.produceIndex = produceIndex;
    }

    public String getOrderSuffix() {
        return orderSuffix;
    }

    public void setOrderSuffix(String orderSuffix) {
        this.orderSuffix = orderSuffix;
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckWorker() {
        return checkWorker;
    }

    public void setCheckWorker(String checkWorker) {
        this.checkWorker = checkWorker;
    }

    public boolean isTail() {
        return isTail;
    }

    public void setTail(boolean tail) {
        isTail = tail;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public float getCheckValue0A() {
        return checkValue0A;
    }

    public void setCheckValue0A(float checkValue0A) {
        this.checkValue0A = checkValue0A;
    }

    public float getCheckValue50A() {
        return checkValue50A;
    }

    public void setCheckValue50A(float checkValue50A) {
        this.checkValue50A = checkValue50A;
    }

    public float getCheckValue100A() {
        return checkValue100A;
    }

    public void setCheckValue100A(float checkValue100A) {
        this.checkValue100A = checkValue100A;
    }

    public float getCheckValue150A() {
        return checkValue150A;
    }

    public void setCheckValue150A(float checkValue150A) {
        this.checkValue150A = checkValue150A;
    }

    public float getCheckValue200A() {
        return checkValue200A;
    }

    public void setCheckValue200A(float checkValue200A) {
        this.checkValue200A = checkValue200A;
    }

    public float getCheckValue250A() {
        return checkValue250A;
    }

    public void setCheckValue250A(float checkValue250A) {
        this.checkValue250A = checkValue250A;
    }

    public float getCheckValue250_2A() {
        return checkValue250_2A;
    }

    public void setCheckValue250_2A(float checkValue250_2A) {
        this.checkValue250_2A = checkValue250_2A;
    }

    public float getCheckValue200_2A() {
        return checkValue200_2A;
    }

    public void setCheckValue200_2A(float checkValue200_2A) {
        this.checkValue200_2A = checkValue200_2A;
    }

    public float getCheckValue150_2A() {
        return checkValue150_2A;
    }

    public void setCheckValue150_2A(float checkValue150_2A) {
        this.checkValue150_2A = checkValue150_2A;
    }

    public float getCheckValue100_2A() {
        return checkValue100_2A;
    }

    public void setCheckValue100_2A(float checkValue100_2A) {
        this.checkValue100_2A = checkValue100_2A;
    }

    public float getCheckValue50_2A() {
        return checkValue50_2A;
    }

    public void setCheckValue50_2A(float checkValue50_2A) {
        this.checkValue50_2A = checkValue50_2A;
    }

    public float getCheckValue0_2A() {
        return checkValue0_2A;
    }

    public void setCheckValue0_2A(float checkValue0_2A) {
        this.checkValue0_2A = checkValue0_2A;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(orderId);
        dest.writeString(produceIndex);
        dest.writeString(orderSuffix);
        dest.writeString(testIndex);
        dest.writeInt(level);
        dest.writeString(line);
        dest.writeString(checkDate);
        dest.writeString(checkWorker);
        dest.writeByte((byte) (done ? 1 : 0));
        dest.writeByte((byte) (isTail ? 1 : 0));
        dest.writeString(machinetype);
        dest.writeInt(result);
        dest.writeFloat(checkValue0);
        dest.writeFloat(checkValue0A);
        dest.writeFloat(checkValue50);
        dest.writeFloat(checkValue50A);
        dest.writeFloat(checkValue100);
        dest.writeFloat(checkValue100A);
        dest.writeFloat(checkValue150);
        dest.writeFloat(checkValue150A);
        dest.writeFloat(checkValue200);
        dest.writeFloat(checkValue200A);
        dest.writeFloat(checkValue250);
        dest.writeFloat(checkValue250A);
        dest.writeFloat(checkValue300);
        dest.writeFloat(checkValue280);
        dest.writeFloat(checkValue300_2);
        dest.writeFloat(checkValue280_2);
        dest.writeFloat(checkValue250_2);
        dest.writeFloat(checkValue250_2A);
        dest.writeFloat(checkValue200_2);
        dest.writeFloat(checkValue200_2A);
        dest.writeFloat(checkValue150_2);
        dest.writeFloat(checkValue150_2A);
        dest.writeFloat(checkValue100_2);
        dest.writeFloat(checkValue100_2A);
        dest.writeFloat(checkValue50_2);
        dest.writeFloat(checkValue50_2A);
        dest.writeFloat(checkValue0_2);
        dest.writeFloat(checkValue0_2A);
    }
}
