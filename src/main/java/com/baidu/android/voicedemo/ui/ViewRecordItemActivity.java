package com.baidu.android.voicedemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.android.voicedemo.bean.Record;
import com.baidu.speech.recognizerdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 查看已完成的订单的单个产品的数据
 */
public class ViewRecordItemActivity extends AppCompatActivity {

    List<Record> list;
    private static final String TAG = "ViewRecordItemActivity";
    EditText editText0;
    EditText editText50;
    EditText editText100;
    EditText editText150;
    EditText editText200;
    EditText editText250;
    EditText editText280;
    EditText editText300;
    EditText editText0_2;
    EditText editText50_2;
    EditText editText100_2;
    EditText editText150_2;
    EditText editText200_2;
    EditText editText250_2;
    EditText editText280_2;
    EditText editText300_2;

    EditText editText0_test2;
    EditText editText50_test2;
    EditText editText100_test2;
    EditText editText150_test2;
    EditText editText200_test2;
    EditText editText250_test2;
    EditText editText280_test2;
    EditText editText300_test2;
    EditText editText0_2_test2;
    EditText editText50_2_test2;
    EditText editText100_2_test2;
    EditText editText150_2_test2;
    EditText editText200_2_test2;
    EditText editText250_2_test2;
    EditText editText280_2_test2;
    EditText editText300_2_test2;

    //avg
    EditText editText0_avg;
    EditText editText50_avg;
    EditText editText100_avg;
    EditText editText150_avg;
    EditText editText200_avg;
    EditText editText250_avg;
    EditText editText280_avg;
    EditText editText300_avg;
    EditText editText0_2_avg;
    EditText editText50_2_avg;
    EditText editText100_2_avg;
    EditText editText150_2_avg;
    EditText editText200_2_avg;
    EditText editText250_2_avg;
    EditText editText280_2_avg;
    EditText editText300_2_avg;

    Record recordFisrt;
    Record recordSecond;
    TextView tips;
    List<String> mList;
    TextView tv_280, tv_280_2, tv_300, tv_300_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record_item);
        recordFisrt = getIntent().getParcelableExtra("first");
        recordSecond = getIntent().getParcelableExtra("second");

        if(recordFisrt!=null){
            setTitle("订单号:" + recordFisrt.getOrderId());
        }
        tips = (TextView) findViewById(R.id.tip_tv);
        tips.setText(recordFisrt.getOrderSuffix() + recordFisrt.getProduceIndex());

        mList = new ArrayList<>();

        tv_280 = (TextView) findViewById(R.id.tv_280);
        tv_280_2 = (TextView) findViewById(R.id.tv_280_2);
        tv_300 = (TextView) findViewById(R.id.tv_300);
        tv_300_2 = (TextView) findViewById(R.id.tv_300_2);

        editText0 = (EditText) findViewById(R.id.record_et_0);
        editText50 = (EditText) findViewById(R.id.record_et_50);
        editText100 = (EditText) findViewById(R.id.record_et_100);
        editText150 = (EditText) findViewById(R.id.record_et_150);
        editText200 = (EditText) findViewById(R.id.record_et_200);
        editText250 = (EditText) findViewById(R.id.record_et_250);
        editText280 = (EditText) findViewById(R.id.record_et_280);
        editText300 = (EditText) findViewById(R.id.record_et_300);
        editText0_2 = (EditText) findViewById(R.id.record_et_0_2);
        editText50_2 = (EditText) findViewById(R.id.record_et_50_2);
        editText100_2 = (EditText) findViewById(R.id.record_et_100_2);
        editText150_2 = (EditText) findViewById(R.id.record_et_150_2);
        editText200_2 = (EditText) findViewById(R.id.record_et_200_2);
        editText250_2 = (EditText) findViewById(R.id.record_et_250_2);
        editText280_2 = (EditText) findViewById(R.id.record_et_280_2);
        editText300_2 = (EditText) findViewById(R.id.record_et_300_2);

        //第二次
        editText0_test2 = (EditText) findViewById(R.id.record_et_0_test2);
        editText50_test2 = (EditText) findViewById(R.id.record_et_50_test2);
        editText100_test2 = (EditText) findViewById(R.id.record_et_100_test2);
        editText150_test2 = (EditText) findViewById(R.id.record_et_150_test2);
        editText200_test2 = (EditText) findViewById(R.id.record_et_200_test2);
        editText250_test2 = (EditText) findViewById(R.id.record_et_250_test2);
        editText280_test2 = (EditText) findViewById(R.id.record_et_280_test2);
        editText300_test2 = (EditText) findViewById(R.id.record_et_300_test2);
        editText0_2_test2 = (EditText) findViewById(R.id.record_et_0_2_test2);
        editText50_2_test2 = (EditText) findViewById(R.id.record_et_50_2_test2);
        editText100_2_test2 = (EditText) findViewById(R.id.record_et_100_2_test2);
        editText150_2_test2 = (EditText) findViewById(R.id.record_et_150_2_test2);
        editText200_2_test2 = (EditText) findViewById(R.id.record_et_200_2_test2);
        editText250_2_test2 = (EditText) findViewById(R.id.record_et_250_2_test2);
        editText280_2_test2 = (EditText) findViewById(R.id.record_et_280_2_test2);
        editText300_2_test2 = (EditText) findViewById(R.id.record_et_300_2_test2);

        //平均
        editText0_avg = (EditText) findViewById(R.id.record_et_0_avg);
        editText50_avg = (EditText) findViewById(R.id.record_et_50_avg);
        editText100_avg = (EditText) findViewById(R.id.record_et_100_avg);
        editText150_avg = (EditText) findViewById(R.id.record_et_150_avg);
        editText200_avg = (EditText) findViewById(R.id.record_et_200_avg);
        editText250_avg = (EditText) findViewById(R.id.record_et_250_avg);
        editText280_avg = (EditText) findViewById(R.id.record_et_280_avg);
        editText300_avg = (EditText) findViewById(R.id.record_et_300_avg);
        editText0_2_avg = (EditText) findViewById(R.id.record_et_0_2_avg);
        editText50_2_avg = (EditText) findViewById(R.id.record_et_50_2_avg);
        editText100_2_avg = (EditText) findViewById(R.id.record_et_100_2_avg);
        editText150_2_avg = (EditText) findViewById(R.id.record_et_150_2_avg);
        editText200_2_avg = (EditText) findViewById(R.id.record_et_200_2_avg);
        editText250_2_avg = (EditText) findViewById(R.id.record_et_250_2_avg);
        editText280_2_avg = (EditText) findViewById(R.id.record_et_280_2_avg);
        editText300_2_avg = (EditText) findViewById(R.id.record_et_300_2_avg);

        resetEditText();
        initEditText(recordFisrt,recordSecond);
        initView(recordFisrt);

    }


    /**
     * 重置为零
     */
    private void resetEditText() {
        editText0.setText("");
        editText50.setText("");
        editText100.setText("");
        editText150.setText("");
        editText200.setText("");
        editText250.setText("");
        editText280.setText("");
        editText300.setText("");
        editText0_2.setText("");
        editText50_2.setText("");
        editText100_2.setText("");
        editText150_2.setText("");
        editText200_2.setText("");
        editText250_2.setText("");
        editText280_2.setText("");
        editText300_2.setText("");

        editText0_test2.setText("");
        editText50_test2.setText("");
        editText100_test2.setText("");
        editText150_test2.setText("");
        editText200_test2.setText("");
        editText250_test2.setText("");
        editText280_test2.setText("");
        editText300_test2.setText("");
        editText0_2_test2.setText("");
        editText50_2_test2.setText("");
        editText100_2_test2.setText("");
        editText150_2_test2.setText("");
        editText200_2_test2.setText("");
        editText250_2_test2.setText("");
        editText280_2_test2.setText("");
        editText300_2_test2.setText("");

        editText0_avg.setText("");
        editText50_avg.setText("");
        editText100_avg.setText("");
        editText150_avg.setText("");
        editText200_avg.setText("");
        editText250_avg.setText("");
        editText280_avg.setText("");
        editText300_avg.setText("");
        editText0_2_avg.setText("");
        editText50_2_avg.setText("");
        editText100_2_avg.setText("");
        editText150_2_avg.setText("");
        editText200_2_avg.setText("");
        editText250_2_avg.setText("");
        editText280_2_avg.setText("");
        editText300_2_avg.setText("");
    }


    /**
     * 某些类型不需要显示部分内容
     * @param record
     */
    private void initView(Record record) {
        Log.e("recorditem", "===" + record.getMachinetype());
        if (record.getMachinetype().equals(String.valueOf(HomeActivity.CREATE_COMMON))) {
            editText300.setVisibility(View.GONE);
            editText300_2.setVisibility(View.GONE);
            editText300_test2.setVisibility(View.GONE);
            editText300_2_test2.setVisibility(View.GONE);
            editText300_avg.setVisibility(View.GONE);
            editText300_2_avg.setVisibility(View.GONE);
            tv_300.setVisibility(View.GONE);
            tv_300_2.setVisibility(View.GONE);
        } else if (record.getMachinetype().equals(String.valueOf(HomeActivity.CREATE_UM_10))) {
            editText300.setVisibility(View.VISIBLE);
            editText300_2.setVisibility(View.VISIBLE);
            editText300_test2.setVisibility(View.VISIBLE);
            editText300_2_test2.setVisibility(View.VISIBLE);
            editText300_avg.setVisibility(View.VISIBLE);
            editText300_2_avg.setVisibility(View.VISIBLE);
            tv_300.setVisibility(View.VISIBLE);
            tv_300_2.setVisibility(View.VISIBLE);
        } else if (record.getMachinetype().equals(String.valueOf(HomeActivity.CREATE_299))){
            editText300.setVisibility(View.GONE);
            editText300_2.setVisibility(View.GONE);
            editText300_test2.setVisibility(View.GONE);
            editText300_2_test2.setVisibility(View.GONE);
            editText300_avg.setVisibility(View.GONE);
            editText300_2_avg.setVisibility(View.GONE);
            tv_300.setVisibility(View.GONE);
            tv_300_2.setVisibility(View.GONE);

            tv_280.setText(String.valueOf(299));
            tv_280_2.setText(String.valueOf(299));
        }else if (record.getMachinetype().equals(String.valueOf(HomeActivity.CREATE_300))){
            editText300.setVisibility(View.GONE);
            editText300_2.setVisibility(View.GONE);
            editText300_test2.setVisibility(View.GONE);
            editText300_2_test2.setVisibility(View.GONE);
            editText300_avg.setVisibility(View.GONE);
            editText300_2_avg.setVisibility(View.GONE);
            tv_300.setVisibility(View.GONE);
            tv_300_2.setVisibility(View.GONE);

            tv_280.setText(String.valueOf(300));
            tv_280_2.setText(String.valueOf(300));
        }


    }

    private void initEditText(Record firstRecord,Record secondRecord) {
        if(firstRecord!=null){
            editText0.setText(String.valueOf(firstRecord.getCheckValue0()));
            editText50.setText(String.valueOf(firstRecord.getCheckValue50()));
            editText100.setText(String.valueOf(firstRecord.getCheckValue100()));
            editText150.setText(String.valueOf(firstRecord.getCheckValue150()));
            editText200.setText(String.valueOf(firstRecord.getCheckValue200()));
            editText250.setText(String.valueOf(firstRecord.getCheckValue250()));
            editText280.setText(String.valueOf(firstRecord.getCheckValue280()));
            editText300.setText(String.valueOf(firstRecord.getCheckValue300()));
            editText0_2.setText(String.valueOf(firstRecord.getCheckValue0_2()));
            editText50_2.setText(String.valueOf(firstRecord.getCheckValue50_2()));
            editText100_2.setText(String.valueOf(firstRecord.getCheckValue100_2()));
            editText150_2.setText(String.valueOf(firstRecord.getCheckValue150_2()));
            editText200_2.setText(String.valueOf(firstRecord.getCheckValue200_2()));
            editText250_2.setText(String.valueOf(firstRecord.getCheckValue250_2()));
            editText280_2.setText(String.valueOf(firstRecord.getCheckValue280_2()));
            editText300_2.setText(String.valueOf(firstRecord.getCheckValue300_2()));
        }

        if(secondRecord!=null){
            editText0_test2.setText(String.valueOf(secondRecord.getCheckValue0()));
            editText50_test2.setText(String.valueOf(secondRecord.getCheckValue50()));
            editText100_test2.setText(String.valueOf(secondRecord.getCheckValue100()));
            editText150_test2.setText(String.valueOf(secondRecord.getCheckValue150()));
            editText200_test2.setText(String.valueOf(secondRecord.getCheckValue200()));
            editText250_test2.setText(String.valueOf(secondRecord.getCheckValue250()));
            editText280_test2.setText(String.valueOf(secondRecord.getCheckValue280()));
            editText300_test2.setText(String.valueOf(secondRecord.getCheckValue300()));
            editText0_2_test2.setText(String.valueOf(secondRecord.getCheckValue0_2()));
            editText50_2_test2.setText(String.valueOf(secondRecord.getCheckValue50_2()));
            editText100_2_test2.setText(String.valueOf(secondRecord.getCheckValue100_2()));
            editText150_2_test2.setText(String.valueOf(secondRecord.getCheckValue150_2()));
            editText200_2_test2.setText(String.valueOf(secondRecord.getCheckValue200_2()));
            editText250_2_test2.setText(String.valueOf(secondRecord.getCheckValue250_2()));
            editText280_2_test2.setText(String.valueOf(secondRecord.getCheckValue280_2()));
            editText300_2_test2.setText(String.valueOf(secondRecord.getCheckValue300_2()));
        }

        if(firstRecord!=null && secondRecord!=null){
            editText0_avg.setText(String.valueOf(getAvag(firstRecord.getCheckValue0(),secondRecord.getCheckValue0())));
            editText50_avg.setText(String.valueOf(getAvag(firstRecord.getCheckValue50(),secondRecord.getCheckValue50())));
            editText100_avg.setText(String.valueOf(getAvag(firstRecord.getCheckValue100(),secondRecord.getCheckValue100())));
            editText150_avg.setText(String.valueOf(getAvag(firstRecord.getCheckValue150(),secondRecord.getCheckValue150())));
            editText200_avg.setText(String.valueOf(getAvag(firstRecord.getCheckValue200(),secondRecord.getCheckValue200())));
            editText250_avg.setText(String.valueOf(getAvag(firstRecord.getCheckValue250(),secondRecord.getCheckValue250())));
            editText280_avg.setText(String.valueOf(getAvag(firstRecord.getCheckValue280(),secondRecord.getCheckValue280())));
            editText300_avg.setText(String.valueOf(getAvag(firstRecord.getCheckValue300(),secondRecord.getCheckValue300())));
            editText0_2_avg.setText(String.valueOf(getAvag(firstRecord.getCheckValue0_2(),secondRecord.getCheckValue0_2())));
            editText50_2_avg.setText(String.valueOf(getAvag(firstRecord.getCheckValue50_2(),secondRecord.getCheckValue50_2())));
            editText100_2_avg.setText(String.valueOf(getAvag(firstRecord.getCheckValue100_2(),secondRecord.getCheckValue100_2())));
            editText150_2_avg.setText(String.valueOf(getAvag(firstRecord.getCheckValue150_2(),secondRecord.getCheckValue150_2())));
            editText200_2_avg.setText(String.valueOf(getAvag(firstRecord.getCheckValue200_2(),secondRecord.getCheckValue200_2())));
            editText250_2_avg.setText(String.valueOf(getAvag(firstRecord.getCheckValue250_2(),secondRecord.getCheckValue250_2())));
            editText280_2_avg.setText(String.valueOf(getAvag(firstRecord.getCheckValue280_2(),secondRecord.getCheckValue280_2())));
            editText300_2_avg.setText(String.valueOf(getAvag(firstRecord.getCheckValue300_2(),secondRecord.getCheckValue300_2())));
        }



    }

    private float getAvag (float a, float b){
        return (a+b)/2 ;
    }


}
