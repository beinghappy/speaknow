package com.baidu.android.voicedemo.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.android.voicedemo.bean.Record;
import com.baidu.android.voicedemo.db.DbHelper;
import com.baidu.android.voicedemo.utils.CommonSpUtil;
import com.baidu.android.voicedemo.utils.EventBusUtils;
import com.baidu.android.voicedemo.utils.FormatUtils;
import com.baidu.android.voicedemo.utils.StaticUtils;
import com.baidu.speech.recognizerdemo.R;

import java.util.ArrayList;
import java.util.List;

public class FailRecordHandActivity extends AppCompatActivity implements View.OnClickListener {

    List<Record> list;
    private static final String TAG = "FailRecordHandActivity";
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

    Record record;
    TextView tips;
    List<String> mList;
    List<EditText> inputViewList;
    TextView tv_280, tv_280_2, tv_300, tv_300_2;
//    Plan plan;
    String machineType ;

    Record firstRecord,secondRecord,avgRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail_record_item);
        record = getIntent().getParcelableExtra("data");
        Log.e("failRecord",record.toString());

        setTitle("订单号:" + record.getOrderId());
        tips = (TextView) findViewById(R.id.tip_tv);
        tips.setText(record.getOrderSuffix() + record.getProduceIndex());


        mList = new ArrayList<>();
        inputViewList = new ArrayList<>();

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


        findViewById(R.id.pay_keyboard_one).setOnClickListener(this);
        findViewById(R.id.pay_keyboard_two).setOnClickListener(this);
        findViewById(R.id.pay_keyboard_three).setOnClickListener(this);
        findViewById(R.id.pay_keyboard_four).setOnClickListener(this);
        findViewById(R.id.pay_keyboard_five).setOnClickListener(this);
        findViewById(R.id.pay_keyboard_sex).setOnClickListener(this);
        findViewById(R.id.pay_keyboard_zero).setOnClickListener(this);
        findViewById(R.id.pay_keyboard_del).setOnClickListener(this);


        machineType = record.getMachinetype();
        initView(machineType);
        resetEditText();


        //获取该订单，该序号的record记录，如果是残次品则有多个，如果是尾货则只有一个
        List<Record> result = DbHelper.getInstance(this).getAllFailRecordById(record.getOrderId(),record.getProduceIndex());
        if (result.size() > 1) {
            //result是与position相同的getOrderId和getProduceIndex有几个
            //里面有若干个，说明是已经检测过的fail产品，而不是没有检测过的尾货
            for (Record initRe : result) {
                if (initRe.getTestIndex().equals("0")) {
                    firstRecord = initRe;
                    //第一次测试数据
                } else if (initRe.getTestIndex().equals("1")) {
                    //第二次测试数据
                    secondRecord = initRe;
                } else if (initRe.getTestIndex().equals("2")) {
                    //平均测试数据
                    avgRecord = initRe;
                }

            }
        }else{
            firstRecord = record;
            secondRecord = null;
            avgRecord = null;
        }



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

    }


    private void initView(String machineType) {
        Log.e("recorditem", "===" + machineType);
        if (machineType.equals(String.valueOf(HomeActivity.CREATE_COMMON))) {
            editText300.setVisibility(View.GONE);
            editText300_2.setVisibility(View.GONE);
            editText300_test2.setVisibility(View.GONE);
            editText300_2_test2.setVisibility(View.GONE);
            tv_300.setVisibility(View.GONE);
            tv_300_2.setVisibility(View.GONE);
        } else if (record.getMachinetype().equals(String.valueOf(HomeActivity.CREATE_UM_10))) {
            editText300.setVisibility(View.VISIBLE);
            editText300_2.setVisibility(View.VISIBLE);
            editText300_test2.setVisibility(View.VISIBLE);
            editText300_2_test2.setVisibility(View.VISIBLE);
            tv_300.setVisibility(View.VISIBLE);
            tv_300_2.setVisibility(View.VISIBLE);
        } else if (record.getMachinetype().equals(String.valueOf(HomeActivity.CREATE_299))){
            editText300.setVisibility(View.GONE);
            editText300_2.setVisibility(View.GONE);
            editText300_test2.setVisibility(View.GONE);
            editText300_2_test2.setVisibility(View.GONE);
            tv_300.setVisibility(View.GONE);
            tv_300_2.setVisibility(View.GONE);

            tv_280.setText(String.valueOf(299));
            tv_280_2.setText(String.valueOf(299));
        }else if (record.getMachinetype().equals(String.valueOf(HomeActivity.CREATE_300))) {
            editText300.setVisibility(View.GONE);
            editText300_2.setVisibility(View.GONE);
            editText300_test2.setVisibility(View.GONE);
            editText300_2_test2.setVisibility(View.GONE);
            tv_300.setVisibility(View.GONE);
            tv_300_2.setVisibility(View.GONE);
            tv_280.setText(String.valueOf(300));
            tv_280_2.setText(String.valueOf(300));
        }

        inputViewList.add(editText0);
        inputViewList.add(editText50);
        inputViewList.add(editText100);
        inputViewList.add(editText150);
        inputViewList.add(editText200);
        inputViewList.add(editText250);
        if (machineType.equals(String.valueOf(HomeActivity.CREATE_UM_10))) {
            inputViewList.add(editText300);
        }
        inputViewList.add(editText280);

        if (machineType.equals(String.valueOf(HomeActivity.CREATE_UM_10))) {
            inputViewList.add(editText300_2);
        }

        inputViewList.add(editText280_2);

        inputViewList.add(editText250_2);
        inputViewList.add(editText200_2);
        inputViewList.add(editText150_2);
        inputViewList.add(editText100_2);
        inputViewList.add(editText50_2);
        inputViewList.add(editText0_2);

        //test
        inputViewList.add(editText0_test2);
        inputViewList.add(editText50_test2);
        inputViewList.add(editText100_test2);
        inputViewList.add(editText150_test2);
        inputViewList.add(editText200_test2);
        inputViewList.add(editText250_test2);

        if (machineType.equals(String.valueOf(HomeActivity.CREATE_UM_10))) {
            inputViewList.add(editText300_test2);
        }
        inputViewList.add(editText280_test2);

        if (machineType.equals(String.valueOf(HomeActivity.CREATE_UM_10))) {
            inputViewList.add(editText300_2_test2);
        }

        inputViewList.add(editText280_2_test2);
        inputViewList.add(editText250_2_test2);
        inputViewList.add(editText200_2_test2);
        inputViewList.add(editText150_2_test2);
        inputViewList.add(editText100_2_test2);
        inputViewList.add(editText50_2_test2);
        inputViewList.add(editText0_2_test2);

    }

    boolean testOK = true;

    private void updateUi(List<String> mlist) {
        // TODO Auto-generated method stub
        if (mList.size() <= inputViewList.size()) {
            resetEditText();
            int len = mlist.size();
            for (int i = 0; i < len; i++) {
                //给超出范围的值变成红色
                boolean inputvalid = FormatUtils.handleInput(mlist.get(i));
                Log.e("tang", "boolean == " + inputvalid + ",value == " + mlist.get(i));
                if (!inputvalid) {
                    inputViewList.get(i).setTextColor(Color.RED);
                    testOK = false;
                } else {
                    inputViewList.get(i).setTextColor(Color.BLACK);
                }

                inputViewList.get(i).setText(mlist.get(i));
            }
        }else if(mList.size() > 0){
            if (!inputIsEmpty()) {
                int len = mlist.size();
                boolean inputvalid = true;
                for (int i = 0; i < len; i++) {
                    inputvalid = FormatUtils.handleInput(mlist.get(i));
                    if (!inputvalid) {
                        testOK = false;
                        break;
                    }
                }

                if (!inputvalid) {
                    //fail
                    record.setResult(Record.result_fail);
                    record.setDone(false);
                } else {
                    record.setResult(Record.result_ok);
                    record.setDone(true);
                }
                boolean b = saveOneRecord(record, testOK);
                if (b) {
                    Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show();
                    EventBusUtils.sendCommand(StaticUtils.COMMAND_REFLASH,record.getOrderId());
                    finish();
                } else {
                    Toast.makeText(this, "保存失败", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "还未完成输入", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.pay_keyboard_one:
                mList.add(1 + "");
                updateUi(mList);
                break;
            case R.id.pay_keyboard_two:
                mList.add(2 + "");
                updateUi(mList);
                break;
            case R.id.pay_keyboard_three:
                mList.add(3 + "");
                updateUi(mList);
                break;
            case R.id.pay_keyboard_four:
                mList.add(-1 + "");
                updateUi(mList);
                break;
            case R.id.pay_keyboard_five:
                mList.add(-2 + "");
                updateUi(mList);
                break;
            case R.id.pay_keyboard_sex:
                mList.add("-3");
                updateUi(mList);
                break;
            case R.id.pay_keyboard_zero:
                mList.add("0");
                updateUi(mList);
                break;
            case R.id.pay_keyboard_del:
                int len = mList.size();
                if (len > 0) {
                    //fix 回退时异常
                    mList.remove(len-1);
                }
                updateUi(mList);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            if (!inputIsEmpty()) {
                boolean b = saveOneRecord(record,testOK);
                if (b) {
                    Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show();
                    EventBusUtils.sendCommand(StaticUtils.COMMAND_REFLASH,record.getOrderId());
                    finish();
                } else {
                    Toast.makeText(this, "保存失败", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "还未完成输入", Toast.LENGTH_LONG).show();
            }

        } else if (item.getItemId() == R.id.cancel) {
            finish();
        } else if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private boolean inputIsEmpty() {
        boolean a = TextUtils.isEmpty(editText0.getText().toString().trim())
                || TextUtils.isEmpty(editText50.getText().toString().trim())
                || TextUtils.isEmpty(editText100.getText().toString().trim())
                || TextUtils.isEmpty(editText150.getText().toString().trim())
                || TextUtils.isEmpty(editText200.getText().toString().trim())
                || TextUtils.isEmpty(editText250.getText().toString().trim())
                || TextUtils.isEmpty(editText280.getText().toString().trim())
                || TextUtils.isEmpty(editText280_2.getText().toString().trim())
                || TextUtils.isEmpty(editText250_2.getText().toString().trim())
                || TextUtils.isEmpty(editText200_2.getText().toString().trim())
                || TextUtils.isEmpty(editText150_2.getText().toString().trim())
                || TextUtils.isEmpty(editText100_2.getText().toString().trim())
                || TextUtils.isEmpty(editText50_2.getText().toString().trim())
                || TextUtils.isEmpty(editText0_2.getText().toString().trim());

        boolean b = TextUtils.isEmpty(editText0_test2.getText().toString().trim())
                || TextUtils.isEmpty(editText50_test2.getText().toString().trim())
                || TextUtils.isEmpty(editText100_test2.getText().toString().trim())
                || TextUtils.isEmpty(editText150_test2.getText().toString().trim())
                || TextUtils.isEmpty(editText200_test2.getText().toString().trim())
                || TextUtils.isEmpty(editText250_test2.getText().toString().trim())
                || TextUtils.isEmpty(editText280_test2.getText().toString().trim())
                || TextUtils.isEmpty(editText280_2_test2.getText().toString().trim())
                || TextUtils.isEmpty(editText250_2_test2.getText().toString().trim())
                || TextUtils.isEmpty(editText200_2_test2.getText().toString().trim())
                || TextUtils.isEmpty(editText150_2_test2.getText().toString().trim())
                || TextUtils.isEmpty(editText100_2_test2.getText().toString().trim())
                || TextUtils.isEmpty(editText50_2_test2.getText().toString().trim())
                || TextUtils.isEmpty(editText0_2_test2.getText().toString().trim());

        boolean c = TextUtils.isEmpty(editText300.getText().toString().trim())
                || TextUtils.isEmpty(editText300_2.getText().toString().trim())
                || TextUtils.isEmpty(editText300_2_test2.getText().toString().trim())
                || TextUtils.isEmpty(editText300_2_test2.getText().toString().trim());

        if (machineType.equals(String.valueOf(HomeActivity.CREATE_UM_10))) {
            return a || b || c;
        } else {
            return a || b;
        }


    }

    /**
     * 判断值里面是否有+-3，如果有，则返回为不合格
     * @return
     */
    private boolean judgValue(){
        boolean result = true;
        if(mList!=null && mList.size() > 0 ){
            if (mList.contains("3") || mList.contains("-3")){
                return false;
            }
        }
        return result;
    }

    /**
     * update checkdate
     *
     * @param record
     * @return
     */
    private boolean saveOneRecord(Record record, boolean testOK) {
        Log.e("tang", "保存数值是否没有非法值" + testOK);
        record.setDone(testOK);
        record.setResult(testOK ? Record.result_ok : Record.result_fail);
        record.setCheckValue50(Float.parseFloat(editText50.getText().toString().trim()));
        record.setCheckValue100(Float.parseFloat(editText100.getText().toString().trim()));
        record.setCheckValue150(Float.parseFloat(editText150.getText().toString().trim()));
        record.setCheckValue200(Float.parseFloat(editText200.getText().toString().trim()));
        record.setCheckValue250(Float.parseFloat(editText250.getText().toString().trim()));
        record.setCheckValue280(Float.parseFloat(editText280.getText().toString().trim()));
        if (record.getMachinetype().equals(String.valueOf(HomeActivity.CREATE_UM_10))) {
            record.setCheckValue300(Float.parseFloat(editText300.getText().toString().trim()));
        }

        record.setCheckValue50_2(Float.parseFloat(editText50_2.getText().toString().trim()));
        record.setCheckValue100_2(Float.parseFloat(editText100_2.getText().toString().trim()));
        record.setCheckValue150_2(Float.parseFloat(editText150_2.getText().toString().trim()));
        record.setCheckValue200_2(Float.parseFloat(editText200_2.getText().toString().trim()));
        record.setCheckValue250_2(Float.parseFloat(editText250_2.getText().toString().trim()));
        record.setCheckValue280_2(Float.parseFloat(editText280_2.getText().toString().trim()));
        if (record.getMachinetype().equals(String.valueOf(HomeActivity.CREATE_UM_10))) {
            record.setCheckValue300_2(Float.parseFloat(editText300_2.getText().toString().trim()));
        }
        record.setTestIndex(String.valueOf(0));
        //更新添加线、时间、甚至worker
        record.setLine(CommonSpUtil.getLine(record.getLine()));
        record.setCheckDate(FormatUtils.formatNowTime());
        record.setCheckWorker(CommonSpUtil.getWorker(record.getCheckWorker()));


        //第二次
        //不能直接等于record；不然更新的就是第二个record
        if (secondRecord == null) {
            secondRecord = new Record();
        }
        secondRecord.setTestIndex(String.valueOf(1));
        secondRecord.setDone(testOK);
        secondRecord.setResult(testOK ? Record.result_ok : Record.result_fail);
        secondRecord.setOrderId(record.getOrderId());
        secondRecord.setMachinetype(record.getMachinetype());
        secondRecord.setOrderSuffix(record.getOrderSuffix());
        secondRecord.setProduceIndex(record.getProduceIndex());
        secondRecord.setCheckValue0(Float.parseFloat(editText0_test2.getText().toString().trim()));
        secondRecord.setCheckValue50(Float.parseFloat(editText50_test2.getText().toString().trim()));
        secondRecord.setCheckValue100(Float.parseFloat(editText100_test2.getText().toString().trim()));
        secondRecord.setCheckValue150(Float.parseFloat(editText150_test2.getText().toString().trim()));
        secondRecord.setCheckValue200(Float.parseFloat(editText200_test2.getText().toString().trim()));
        secondRecord.setCheckValue250(Float.parseFloat(editText250_test2.getText().toString().trim()));
        secondRecord.setCheckValue280(Float.parseFloat(editText280_test2.getText().toString().trim()));
        if (machineType.equals(String.valueOf(HomeActivity.CREATE_UM_10))) {
            secondRecord.setCheckValue300(Float.parseFloat(editText300_test2.getText().toString().trim()));
        }


        secondRecord.setCheckValue0_2(Float.parseFloat(editText0_2_test2.getText().toString().trim()));
        secondRecord.setCheckValue50_2(Float.parseFloat(editText50_2_test2.getText().toString().trim()));
        secondRecord.setCheckValue100_2(Float.parseFloat(editText100_2_test2.getText().toString().trim()));
        secondRecord.setCheckValue150_2(Float.parseFloat(editText150_2_test2.getText().toString().trim()));
        secondRecord.setCheckValue200_2(Float.parseFloat(editText200_2_test2.getText().toString().trim()));
        secondRecord.setCheckValue250_2(Float.parseFloat(editText250_2_test2.getText().toString().trim()));
        secondRecord.setCheckValue280_2(Float.parseFloat(editText280_2_test2.getText().toString().trim()));
        if (machineType.equals(String.valueOf(HomeActivity.CREATE_UM_10))) {
            secondRecord.setCheckValue300_2(Float.parseFloat(editText300_2_test2.getText().toString().trim()));
        }

        secondRecord.setCheckDate(FormatUtils.formatNowTime());
        //更新添加线、时间、甚至worker
        secondRecord.setLine(CommonSpUtil.getLine(record.getLine()));
        secondRecord.setCheckDate(FormatUtils.formatNowTime());
        secondRecord.setCheckWorker(CommonSpUtil.getWorker(record.getCheckWorker()));

        Record avgrecord = getAvarageRecord(testOK, record, secondRecord);
        List<Record> list = new ArrayList<>();
        list.add(record);
        list.add(secondRecord);
        list.add(avgrecord);
        boolean avg = DbHelper.getInstance(this).updateRecord(list);

        Log.e("手写saveone", "one==" + record.toString());
        Log.e("手写saveone", "second==" + secondRecord.toString());
        Log.e("手写saveone", "avg==" + avgrecord.toString());
        return avg;


    }


    /**
     * update checkdate
     *
     * @param testOK
     * @param record
     * @param f2
     * @return
     */
    private Record getAvarageRecord(boolean testOK, Record record, Record f2) {
        Record resultRecord = null;
        if (avgRecord == null) {
            resultRecord = new Record();
        } else {
            resultRecord = avgRecord;
        }
        resultRecord.setDone(testOK);
        resultRecord.setResult(testOK ? Record.result_ok : Record.result_fail);
        resultRecord.setTestIndex(String.valueOf(2));
        resultRecord.setOrderId(record.getOrderId());
        resultRecord.setMachinetype(record.getMachinetype());
        resultRecord.setOrderSuffix(record.getOrderSuffix());
        resultRecord.setProduceIndex(record.getProduceIndex());
        resultRecord.setCheckValue0(getAvarage(record.getCheckValue0(), f2.getCheckValue0()));
        resultRecord.setCheckValue50(getAvarage(record.getCheckValue50(), f2.getCheckValue50()));
        resultRecord.setCheckValue100(getAvarage(record.getCheckValue100(), f2.getCheckValue100()));
        resultRecord.setCheckValue150(getAvarage(record.getCheckValue150(), f2.getCheckValue150()));
        resultRecord.setCheckValue200(getAvarage(record.getCheckValue200(), f2.getCheckValue200()));
        resultRecord.setCheckValue250(getAvarage(record.getCheckValue250(), f2.getCheckValue250()));
        resultRecord.setCheckValue280(getAvarage(record.getCheckValue280(), f2.getCheckValue280()));
        if (record.getMachinetype().equals(String.valueOf(HomeActivity.CREATE_UM_10))) {
            resultRecord.setCheckValue300(getAvarage(record.getCheckValue300(), f2.getCheckValue300()));
        }

        resultRecord.setCheckValue0_2(getAvarage(record.getCheckValue0_2(), f2.getCheckValue0_2()));
        resultRecord.setCheckValue50_2(getAvarage(record.getCheckValue50_2(), f2.getCheckValue50_2()));
        resultRecord.setCheckValue100_2(getAvarage(record.getCheckValue100_2(), f2.getCheckValue100_2()));
        resultRecord.setCheckValue150_2(getAvarage(record.getCheckValue150_2(), f2.getCheckValue150_2()));
        resultRecord.setCheckValue200_2(getAvarage(record.getCheckValue200_2(), f2.getCheckValue200_2()));
        resultRecord.setCheckValue250_2(getAvarage(record.getCheckValue250_2(), f2.getCheckValue250_2()));
        resultRecord.setCheckValue280_2(getAvarage(record.getCheckValue280_2(), f2.getCheckValue280_2()));
        if (record.getMachinetype().equals(String.valueOf(HomeActivity.CREATE_UM_10))) {
            resultRecord.setCheckValue300_2(getAvarage(record.getCheckValue300_2(), f2.getCheckValue300_2()));
        }

        resultRecord.setCheckDate(FormatUtils.formatNowTime());
        //更新添加线、时间、甚至worker
        resultRecord.setLine(CommonSpUtil.getLine(record.getLine()));
        resultRecord.setCheckDate(FormatUtils.formatNowTime());
        resultRecord.setCheckWorker(CommonSpUtil.getWorker(record.getCheckWorker()));

        Log.e("tang", "手动记录平均record" + resultRecord.toString());
        return resultRecord;
    }

    private float getAvarage(float f1, float f2) {
        float result = (f1 + f2) / 2;
        Log.e("手动记录", "f1 == " + f1 + ", f2 == " + f2 + " , result == " + result);
        return result;
    }


}
