package com.baidu.android.voicedemo.ui;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.baidu.android.voicedemo.bean.Plan;
import com.baidu.android.voicedemo.bean.Record;
import com.baidu.android.voicedemo.utils.CreateExcel;
import com.baidu.android.voicedemo.utils.FileUtils;
import com.baidu.speech.recognizerdemo.R;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plan plan = new Plan();
                plan.setOrderId("普通");
                plan.setMachineType("1");
                plan.setProduceLine("3线");
                plan.setProduceClass("二组");
                plan.setDone(true);
                plan.setCheckTableIndex(3);
                plan.setCheckMachineNumPer(3);
                plan.setCheckerJobIndex1("B1001");
                plan.setCheckerJobIndex2("B1002");
                plan.setCheckDate("20170315");
                plan.setOrderAccount("12");
                plan.setOrderIndexBegin("1");
                plan.setOrderSuffix("SN");

                List<Record>  list = initList(plan,String.valueOf(HomeActivity.CREATE_COMMON));
                doExport(plan,list);
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plan plan = new Plan();
                plan.setOrderId("um-10");
                plan.setMachineType("2");
                plan.setProduceLine("1线");
                plan.setProduceClass("二组");
                plan.setDone(true);
                plan.setCheckTableIndex(3);
                plan.setCheckMachineNumPer(3);
                plan.setCheckerJobIndex1("B1001");
                plan.setCheckerJobIndex2("B1002");
                plan.setCheckDate("20170315");
                plan.setOrderAccount("12");
                plan.setOrderIndexBegin("1");
                plan.setOrderSuffix("SNum");

                List<Record>  list = initList(plan,HomeActivity.CREATE_UM_10+"");
                doExport(plan,list);
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plan plan = new Plan();
                plan.setOrderId("m299");
                plan.setMachineType("3");
                plan.setProduceLine("4线");
                plan.setProduceClass("二组");
                plan.setDone(true);
                plan.setCheckTableIndex(3);
                plan.setCheckMachineNumPer(3);
                plan.setCheckerJobIndex1("B1001");
                plan.setCheckerJobIndex2("B1002");
                plan.setCheckDate("20170315");
                plan.setOrderAccount("12");
                plan.setOrderIndexBegin("1");
                plan.setOrderSuffix("SNM99");
                List<Record>  list = initList(plan,String.valueOf(HomeActivity.CREATE_299));
                doExport(plan,list);
            }
        });
    }

    CreateExcel createExcel;
    private void doExport(Plan plan,List<Record> list) {
        createExcel = new CreateExcel(plan);
        try {

            if (list != null && list.size() > 0) {
                createExcel.saveDataToExcel(list, plan);
                String path = Environment.getExternalStorageDirectory() + "/AND/" + FileUtils.getExcelName(plan);
                Toast.makeText(this, "写入成功,路径:" + path, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "没有具体数据", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private List<Record> initList(Plan plan,String type){
        List<Record>  list = new ArrayList<>();
        for(int i = 0 ; i < 9 ;i++){
            for(int j = 0 ; j < 2 ;j++){
                Record record = new Record();
                record.setOrderId(plan.getOrderId()+i);
                record.setDone(true);
                record.setCheckValue50(1);
                record.setCheckValue100(2);
                record.setCheckValue150(3);
                record.setCheckValue200(1);
                record.setCheckValue250(2);
                record.setCheckValue280(3);
                if (type.equals(String.valueOf(HomeActivity.CREATE_UM_10))) {
                    record.setCheckValue300(0);
                }

                record.setCheckValue50_2(1);
                record.setCheckValue100_2(2);
                record.setCheckValue150_2(3);
                record.setCheckValue200_2(3);
                record.setCheckValue250_2(2);
                record.setCheckValue280_2(1);
                if (type.equals(String.valueOf(HomeActivity.CREATE_UM_10))) {
                    record.setCheckValue300_2(0);
                }

                record.setTestIndex(j+"");

                list.add(record);

            }




        }

        return list;
    }

}
