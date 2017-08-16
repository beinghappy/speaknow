package com.baidu.android.voicedemo.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.android.voicedemo.bean.Plan;
import com.baidu.android.voicedemo.bean.Record;
import com.baidu.android.voicedemo.db.DbHelper;
import com.baidu.android.voicedemo.utils.CommonSpUtil;
import com.baidu.android.voicedemo.utils.DialogUtils;
import com.baidu.android.voicedemo.utils.EventBusUtils;
import com.baidu.android.voicedemo.utils.FormatUtils;
import com.baidu.android.voicedemo.utils.StaticUtils;
import com.baidu.speech.recognizerdemo.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private Button btn;
    private long speechEndTime = -1;
    private static final int EVENT_ERROR = 11;
    private NumberPicker projectPicker = null;

    String[] commonArray = {"0","50", "100", "150", "200", "250", "280", "280", "250", "200", "150", "100", "50","0"};
    String[] um_10Array = {"0","50", "100", "150", "200", "250",  "300", "280", "300", "280","250", "200", "150", "100", "50","0"};
    String[] checkArrays3 = {"0","50", "100", "150", "200", "250", "299", "299", "250", "200", "150", "100", "50","0"};
    String[] checkArrays4 = {"0","50", "100", "150", "200", "250", "300", "300", "250", "200", "150", "100", "50","0"};
    String[] checkProject = um_10Array;

    private TextView txtStartTV;

    int planType = HomeActivity.CREATE_COMMON;
    private Plan plan;
    int start = 0;//从list的第0个取produceIndex值
    List<Record> recordlist;

    List<String> mList;
    List<EditText> inputViewList;

    List<Record> firstDoneList = null;
    List<Record> secondDoneList = null;

    /**
     * 有几台pad ; 默认是3
     */
    public static int machine = 3;
    /**
     * 每台pad一次测试几台血压测试计；默认为3
     */
    public static int checkNumOneMachine = 3;
    LinearLayout container_test_layout; //包含几种测试数据的容器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main_new);
        initView();
        initValues();
        initHandView();

        DialogUtils.showChangeLineDialog(this);
    }

    List<LinearLayout> layoutList;
    List<TextView> titleList;

    private void initValues() {
        plan = (Plan) getIntent().getParcelableExtra("data");
        //给checkNumOneMachine赋值
        checkNumOneMachine = plan.getCheckMachineNumPer();

        try {
            planType = Integer.parseInt(plan.getMachineType());
        } catch (Exception e) {
            planType = HomeActivity.CREATE_UM_10;
        }
        initNumberPicker(planType);


        txtStartTV.setText("序列号前缀：" + plan.getOrderSuffix());
        recordlist = DbHelper.getInstance(this).getAllInitExceptTailRecord(plan.getOrderId());
        //应该在此处理一下，以level为key，以list为value
        // TODO: 2017/8/16 0016  
        if (recordlist != null && recordlist.size() >= checkNumOneMachine) {
            //数量大于checkNumOneMachine
            initOneLayout(checkNumOneMachine);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(perssion, REQUEST_ID);
        }

    }

    /**
     * 添加若干个布局到contaier：包括title和内容容器
     * 并将若干个内容容器，放入一个list
     *
     * @param num
     */
    private void initOneLayout(int num) {
        layoutList = new ArrayList<>();
        recordArrayList = new ArrayList<>();
        titleList = new ArrayList<>();

        //根据num来初始化title的个数
        for (int i = 0; i < num; i++) {
            View view = getLayoutInflater().inflate(R.layout.main_layout_item, null);
            TextView goodTV1 = (TextView) view.findViewById(R.id.txtGood);
            LinearLayout googlayout = (LinearLayout) view.findViewById(R.id.goodlayout);
            float[] recordarray = new float[checkProject.length];
            recordArrayList.add(recordarray);
            titleList.add(goodTV1);//标题
            layoutList.add(googlayout);//单个record对应的纵向布局
            container_test_layout.addView(view);

        }

        //初始化
        for (int i = 0; i < titleList.size(); i++) {
            titleList.get(i).setText(recordlist.get(start + i).getProduceIndex());
        }

    }

    private void initView() {
        projectPicker = (NumberPicker) findViewById(R.id.numberPicker);
        txtStartTV = (TextView) findViewById(R.id.txtStart);
        btn = (Button) findViewById(R.id.btn);
        container_test_layout = (LinearLayout) findViewById(R.id.container_test);

    }


    private void initNumberPicker(int planType) {
        if (planType == HomeActivity.CREATE_COMMON) {
            checkProject = commonArray;
        } else if (planType == HomeActivity.CREATE_UM_10) {
            checkProject = um_10Array;
        } else if (planType == HomeActivity.CREATE_299){
            checkProject = checkArrays3;
        }else{
            checkProject = checkArrays4;
        }
        projectPicker.setDisplayedValues(checkProject);
        projectPicker.setMinValue(0);
        projectPicker.setMaxValue(checkProject.length - 1);
        projectPicker.setValue(0);
        projectPicker.clearFocus();
        projectPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.e("tag", "oldval==" + oldVal + ",newval==" + newVal);

            }
        });

    }

    public static final int REQUEST_ID = 123;
    String[] perssion = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onDestroy() {
        EventBusUtils.sendCommand(StaticUtils.COMMAND_REFLASH);
        super.onDestroy();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //如果没有record，那么是不显示menu的
        if (recordlist != null && start == recordlist.size() - 1) {
            menu.findItem(R.id.next).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }
//输入界面，取消下一组

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.speakmain, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.next) {
//            nextRow(true);
//            resetGoodLayout();
//        } else if (item.getItemId() == R.id.cancel) {
//            finish();
//        } else if (item.getItemId() == android.R.id.home) {
//            finish();
//        }
//        return true;
//    }

    private void initHandView() {
        mList = new ArrayList<>();
        inputViewList = new ArrayList<>();
        findViewById(R.id.pay_keyboard_one).setOnClickListener(this);
        findViewById(R.id.pay_keyboard_two).setOnClickListener(this);
        findViewById(R.id.pay_keyboard_three).setOnClickListener(this);
        findViewById(R.id.pay_keyboard_four).setOnClickListener(this);
        findViewById(R.id.pay_keyboard_five).setOnClickListener(this);
        findViewById(R.id.pay_keyboard_sex).setOnClickListener(this);
        findViewById(R.id.pay_keyboard_zero).setOnClickListener(this);
        findViewById(R.id.pay_keyboard_del).setOnClickListener(this);
    }

    /**
     * 每次输入一个，需要刷新UI
     * @param mlist
     */
    boolean rowTestIsOK = true;
    private void updateUi(List<String> mlist) {
        resetGoodLayout();
        int len = 0;//输入数值的个数
        if (mlist != null && mlist.size() > 0)
            len = mlist.size();
        int level = len / checkNumOneMachine;
        projectPicker.setValue(level);

        if (len > 0) {
            if (len <= checkProject.length * checkNumOneMachine) {
                //还没有输完
                for (int i = 0; i < len; i++) {
                    int index = i % checkNumOneMachine;//第几个record的记录
                    int levelIndex = i / checkNumOneMachine;//某个record的第几个记录

                    if (index == 0 && len != checkProject.length * checkNumOneMachine) {
                        //第一个
                        TextView textView = new TextView(this);
                        textView.setText(mlist.get(i));
                        //赋值recordArrayList；利于保存于数据库
                        try {
                            recordArrayList.get(0)[levelIndex] = Integer.parseInt(mlist.get(i));
                        } catch (Exception e) {
                            e.printStackTrace();
                            recordArrayList.get(0)[levelIndex] = -99;
                        }
                        //给超出范围的值变成红色
                        boolean inputOK = FormatUtils.handleInput(recordArrayList.get(index)[levelIndex]);
                        if(!inputOK){
                            rowTestIsOK = false;
                            textView.setTextColor(Color.RED);
                        }else{
                            textView.setTextColor(Color.BLACK);
                        }
                        //todo
                        layoutList.get(0).addView(textView);
                    } else {
                        //其他的个数
                        TextView textView = new TextView(this);
                        textView.setText(mlist.get(i));
                        //赋值recordArrayList；利于保存于数据库
                        try {
                            recordArrayList.get(index)[levelIndex] = Integer.parseInt(mlist.get(i));
                        } catch (Exception e) {
                            e.printStackTrace();
                            recordArrayList.get(index)[levelIndex] = -99;
                        }
                        //给超出范围的值变成红色
                        boolean inputvalid = FormatUtils.handleInput(recordArrayList.get(index)[levelIndex]);
                        if(!inputvalid){
                            textView.setTextColor(Color.RED);
                            rowTestIsOK = false;
                        }else{
                            textView.setTextColor(Color.BLACK);
                        }
                        //todo
                        layoutList.get(index).addView(textView);
                    }

                }


                if (len == checkProject.length * checkNumOneMachine) {
                    //最后一个记录完成之后；
                    //第一组（0）的话，则保存之后继续；第二组（1）的话,下一组测试
                    if (testIndex == 0) {
                        dosaveRowReord1();
                        projectPicker.setValue(0);
                        resetGoodLayout();
                        testIndex = 1 ;
                        Toast.makeText(this, "本组第二次测试", Toast.LENGTH_LONG).show();
                    } else {
                        dosaveRowReord2();
                        testIndex = 0;
                        projectPicker.setValue(0);
                        nextRow(false);
                        resetGoodLayout();

                    }

                }

            }
        }

    }





    /**
     * 计划第一遍的值
     */
    private void dosaveRowReord1() {
        List<Record> list = new ArrayList<>();
        //todo 单组（三个）结束。
        if (recordlist != null && recordlist.size() > 0) {
            Log.e(TAG, "recordlist 有值 ====" + recordlist.size());
        } else {
            Log.e(TAG, "recordlist 没有值");
            return;
        }

        for (int i = 0; i < checkNumOneMachine; i++) {
            Log.e("tag", "dosaveRowReord==start+i==" + (start + i));
            if (start + i < recordlist.size()) {
                Log.e("tang","before: "+recordlist.get(start + i).toString());
                Record record1 = buildRecordByOld(recordlist.get(start + i), recordArrayList.get(i));
                Log.e("tang", "after record == " + record1.toString());
                record1.setTestIndex("0");

                //更新添加线、时间、甚至worker
                record1.setLine(CommonSpUtil.getLine(plan.getProduceClass()+"课"+plan.getProduceLine()+"线"));
                record1.setCheckDate(FormatUtils.formatNowTime());
                record1.setCheckWorker(CommonSpUtil.getWorker(plan.getCheckerJobIndex1()));
                record1.setDone(true);

                //判断是否包含残次品
                boolean inputvalid = FormatUtils.handleInput(recordArrayList.get(i));
                if(!inputvalid){
                    //fail
                    testOK1 = false;
                }



                list.add(record1);
            }
        }

        firstDoneList = list;

        if (list != null && list.size() > 0) {
            //delete it for "在第二次完成测试之后一起保存第一次，第二次，及平均的值"
//            boolean b = DbHelper.getInstance(this).addRecordList(list, true);
//            if (!b) {
//                Toast.makeText(this, "保存记录失败", Toast.LENGTH_LONG).show();
//            } else {
                Log.e("tag", "保存记录已成功");
                resetGoodLayout();
                //必须reset mlist这个全局变量
                if (mList != null) {
                    mList.clear();
                }

//            }
        }
    }

    /**
     * 计划第二遍的值
     */
    public boolean testOK2 = true;
    private void dosaveRowReord2() {
        List<Record> list = new ArrayList<>();
        //todo 单组（三个）结束。
        if (recordlist != null && recordlist.size() > 0) {
            Log.e(TAG, "recordlist 有值 ====" + recordlist.size());
        } else {
            Log.e(TAG, "recordlist 没有值");
            return;
        }

        for (int i = 0; i < checkNumOneMachine; i++) {
            Log.e("tag", "dosaveRowReord==start+i==" + (start + i));
            if (start + i < recordlist.size()) {
                Record record1 = buildNewRecord(recordlist.get(start + i),recordArrayList.get(i));
                Log.e("tang", "dosaveRowReord2: buildOneRecord == " + record1.toString());
                record1.setTestIndex("1");

                //更新添加线、时间、甚至worker
                record1.setLine(CommonSpUtil.getLine(plan.getProduceClass() + "课" + plan.getProduceLine() + "线"));
                record1.setCheckDate(FormatUtils.formatNowTime());
                record1.setCheckWorker(CommonSpUtil.getWorker(plan.getCheckerJobIndex1()));
                record1.setDone(true);
//                //判断是否包含残次品
                boolean inputvalid = FormatUtils.handleInput(recordArrayList.get(i));
                if (!inputvalid) {
                    //fail
                    testOK2 = false;

                } else {

                }

                list.add(record1);
            }
        }

        //第二遍
        //计算出平均的
        secondDoneList = list;

        boolean firstSave = false;
        boolean secondSave = false;


        syncTwoListExtendDoneValues();

        if(firstDoneList!=null && firstDoneList.size()>0){

            firstSave = DbHelper.getInstance(this).addRecordList(firstDoneList, true);
        }

        if (secondDoneList != null && secondDoneList.size() > 0) {

            secondSave = DbHelper.getInstance(this).addRecordList(secondDoneList, false);

        }

        if(firstSave&&secondSave){
                Log.e("tag", "保存二次测试记录已成功");
                resetGoodLayout();
                //必须reset mlist这个全局变量
                if (mList != null) {
                    mList.clear();
                }

        }else{
            Toast.makeText(this, "保存二次测试记录失败", Toast.LENGTH_LONG).show();

        }

        doSaveRecordList(firstDoneList, secondDoneList);


    }

    //让produceindex对应的record的extend和done都一致
    private void syncTwoListExtendDoneValues() {
        if (firstDoneList != null && secondDoneList != null) {
            if (firstDoneList.size() == secondDoneList.size()) {
                for (int i = 0; i < firstDoneList.size(); i++) {
                    boolean isDone = testOK2 && testOK1;

                    firstDoneList.get(i).setResult(isDone ? Record.result_ok : Record.result_fail);
                    secondDoneList.get(i).setResult(isDone ? Record.result_ok : Record.result_fail);
                }
            }
        }
    }


    @Override
    public void onBackPressed() {
        if(testIndex==1){
            //第一组测试已完成，正在做第二组测试
            DialogUtils.showExitDialog(this, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //退出，取消数据库保存
//                    if(firstDoneList!=null && firstDoneList.size()>0){
//                        for(Record record:firstDoneList){
//                            if(record.getTestIndex().equals("0")){
//                                firstDoneList.remove(record);
//                                break;
//                            }
//                        }
//
//                        if(firstDoneList.size()>0){
//                            boolean b = DbHelper.getInstance(MainActivity.this).removeRecordList(firstDoneList);
//                            if(b){
//                                Toast.makeText(MainActivity.this,"cancel ok",Toast.LENGTH_LONG).show();
//                                finish();
//                            }
//                        }else{
//
//                        }
//                    }else{
//                        Log.e("tang","firstDonelist == null");
//                    }
//

                }
            });
        }else{
            super.onBackPressed();
        }
    }

    /**
     * 计算有第一个list和第二个list组合得到的平均
     *
     * @param firstList
     * @param secondList
     */
    private void doSaveRecordList(List<Record> firstList, List<Record> secondList) {
        if (firstList != null && secondList != null) {
            if (firstList.size() == secondList.size()) {
                List<Record> newList = new ArrayList<>();
                int len = firstList.size();
                for (int i = 0; i < len; i++) {
                    Record first = firstList.get(i);
                    Record secondRecord = secondList.get(i);
                    Log.e("tang","save == first : "+first.toString());
                    Log.e("tang","save == secondRecord : "+secondRecord.toString());
                    newList.add(getAvarageRecord(first, secondRecord));
                }

                if (newList != null && newList.size() > 0) {
                    boolean b = DbHelper.getInstance(this).addRecordList(newList, false);
                    if (!b) {
                        Log.e("tang", "保存平均记录失败");
                    } else {
                        Log.e("tang", "保存平均记录已成功");

                    }
                }
            }
        }

    }


    private float getAvarage(float f1, float f2) {
        float result = (f1 + f2) / 2;
        return result;
    }


    /**
     * update checkdate
     * @param record
     * @param f2
     * @return
     */
    private Record getAvarageRecord(Record record, Record f2) {
        Record resultRecord = new Record();
        resultRecord.setResult(record.getResult());
        resultRecord.setDone(record.isDone());
        resultRecord.setOrderId(record.getOrderId());
        resultRecord.setProduceIndex(record.getProduceIndex());
        resultRecord.setMachinetype(record.getMachinetype());
        resultRecord.setOrderSuffix(record.getOrderSuffix());
        resultRecord.setTestIndex("2");
        //update add 0
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

        //add 0
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

        //更新添加线、时间、甚至worker
        resultRecord.setLine(CommonSpUtil.getLine(plan.getProduceClass()+"课"+plan.getProduceLine()+"线"));
        resultRecord.setCheckDate(FormatUtils.formatNowTime());
        resultRecord.setCheckWorker(CommonSpUtil.getWorker(plan.getCheckerJobIndex1()));

        Log.e("tang", "record1:" + record.toString());
        Log.e("tang", "f2:" + f2.toString());
        Log.e("tang", "平均record:" + resultRecord.toString());
        return resultRecord;
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
                    mList.remove(len-1);
                }
                updateUi(mList);
                break;
        }
    }

//    private boolean isHandStatus = true;
//
//    private void showHandViews(boolean b) {
//        isHandStatus = b;
//        if (b) {
//            findViewById(R.id.keyboard).setVisibility(View.VISIBLE);
//            btn.setVisibility(View.GONE);
//            initHandView();
//        } else {
//            findViewById(R.id.keyboard).setVisibility(View.GONE);
//            btn.setVisibility(View.VISIBLE);
//        }
//    }


    /**
     * 发出成功识别的消息，并开始下一个;
     */
//    private void sendSuccess(List<String> result) {
//        handler.removeMessages(SUCCESS_CODE);
//        Message message = handler.obtainMessage(SUCCESS_CODE);
//        message.what = SUCCESS_CODE;
//        message.obj = result;
//        handler.sendMessage(message);
//    }

    public static final int MAX_RAGE = 2;

    /**
     * 将数据保存于数据库
     *
     * @param result
     */

    List<float[]> recordArrayList = null;

    private void updateGoodsTV(List<String> result) {
        int index = projectPicker.getValue();
        float checkData = Float.parseFloat(checkProject[index]);

        for (int i = 0; i < result.size(); i++) {
            float item;
            try {
                item = Float.parseFloat(result.get(i)) - checkData;
            } catch (Exception e) {
                item = -99;
            }

            TextView textView = new TextView(this);
            textView.setText(item + "");
            if (Math.abs(item) > MAX_RAGE) {
                textView.setTextColor(Color.RED);
            }

            //赋值
            recordArrayList.get(i)[index] = item;
            layoutList.get(i).addView(textView);

        }

    }

    /**
     * 发出失败的消息，并提示重新录制。
     */
//    private void sendFail(String text) {
//        Log.e("tag", "sendFail start");
//        handler.removeMessages(FAIL_CODE);
//        Message message = handler.obtainMessage(FAIL_CODE);
//        message.what = FAIL_CODE;
//        message.obj = text;
//        handler.sendMessage(message);
//        Log.e("tag", "sendFail end");
//    }


//    private void sendStart() {
//        handler.removeMessages(START_CODE);
//        handler.sendEmptyMessageDelayed(START_CODE, DELAYTIME);
//    }

    public final static int SUCCESS_CODE = 200;
    public final static int FAIL_CODE = 404;
    public final static int START_CODE = 303;
    public final static int DELAYTIME = 1000;//工人干活的频率
    public final static boolean ISAPI = false;
    int testIndex = 0;
//    Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//            if (msg.what == SUCCESS_CODE) {
//                List<String> resultList = (List<String>) msg.obj;
//                updateGoodsTV(resultList);
//                next();
//                System.out.println("receive..SUCCESS_CODE..");
//            } else if (msg.what == FAIL_CODE) {
//                String text = (String) msg.obj;
//                System.out.println("receive...FAIL_CODE." + text);
//                tips(text);
//                sendStart();
//            } else if (msg.what == START_CODE) {
//            }
//        }
//
//    };

//    private void next() {
//
//        int currentValues = projectPicker.getValue();
//        if (currentValues < checkProject.length - 1) {
//            Log.e(TAG, "next: ---在一轮之内，继续:" + currentValues);
//            //在一轮之内，继续
//            projectPicker.setValue(currentValues + 1);
//            sendStart();
//        } else {
//            //一轮最后一个
//            Log.e(TAG, "next: ---一轮最后一个: testIndex == " + testIndex);
//            //第一组（0）的话，则保存之后继续；第二组（1）的话,下一组测试
//            if (testIndex % 2 == 0) {
//                Log.e(TAG, "第一次测试:" + testIndex);
//                doSaveSomeReord("0", true);
//                testIndex++;
//                projectPicker.setValue(currentValues + 1);
//                sendStart();
//                resetGoodLayout();
//                Toast.makeText(this, "本组第二次测试", Toast.LENGTH_LONG).show();
//            } else {
//                //第二次需要add新的record，因为buildplan时没有创建
//                doSaveSomeReord("1", false);
//                testIndex = 0;
//                Toast.makeText(this, "the row test is over", Toast.LENGTH_LONG).show();
//                projectPicker.setValue(currentValues + 1);
//                //list里面均为未测试的。
//                resetGoodLayout();
//                nextRow(false);
//            }
//
//
//        }
//
//        Log.e(TAG, "next: ---end");
//
//    }

    /**
     * 清空所有的
     */
    private void resetGoodLayout() {
        for (int i = 0; i < checkNumOneMachine; i++) {
            layoutList.get(i).removeAllViews();
        }
    }

    /**
     * 保存尾货，尾货不合格，但是未完成；
     * @param record
     */
//    private void saveTailRecord(Record record) {
//        Log.e("tang", "saveTailRecord index ==" + record.getProduceIndex());
//        record.setDone(false);
//        record.setMachinetype(record.getMachinetype());
//        record.setExtend(false);
//        //更新添加线、时间、甚至worker
//        record.setLine(CommonSpUtil.getLine(plan.getProduceClass() + "课" + plan.getProduceLine() + "线"));
//        record.setCheckDate(FormatUtils.formatNowTime());
//        record.setCheckWorker(CommonSpUtil.getWorker(plan.getCheckerJobIndex1()));
//
//        DbHelper.getInstance(this).updateRecord(record);
//    }

    /**
     * 3,-3 ,不合格
     * @param values
     * @return
     */
    private boolean judgValue(float[] values){
        boolean result = true;
        if(values!=null && values.length > 0 ){
            for(float fv : values){
                if(fv==3 || fv == -3){
                    return false;
                }
            }

        }
        return result;
    }

    /**
     * 第一个record，可以用recordlist的record的id；其他则不能
     *
     * @param resultRecord
     * @param recordArray
     * @return
     */
    private Record buildRecordByOld(Record resultRecord, float[] recordArray) {
        boolean isOK = judgValue(recordArray);
        resultRecord.setDone(true);
        resultRecord.setResult(isOK ? Record.result_ok : Record.result_fail);
        resultRecord.setMachinetype(plan.getMachineType());
        if (planType == HomeActivity.CREATE_COMMON) {
            //update for "add 两个 0档位"
            resultRecord.setCheckValue0(recordArray[0]);
            resultRecord.setCheckValue50(recordArray[1]);
            resultRecord.setCheckValue100(recordArray[2]);
            resultRecord.setCheckValue150(recordArray[3]);
            resultRecord.setCheckValue200(recordArray[4]);
            resultRecord.setCheckValue250(recordArray[5]);
            resultRecord.setCheckValue280(recordArray[6]);

            //注意这是是反的
            resultRecord.setCheckValue0_2(recordArray[13]);
            resultRecord.setCheckValue50_2(recordArray[12]);
            resultRecord.setCheckValue100_2(recordArray[11]);
            resultRecord.setCheckValue150_2(recordArray[10]);
            resultRecord.setCheckValue200_2(recordArray[9]);
            resultRecord.setCheckValue250_2(recordArray[8]);
            resultRecord.setCheckValue280_2(recordArray[7]);
        } else if (planType == HomeActivity.CREATE_UM_10) {
            //update for "add 两个 0档位"
            resultRecord.setCheckValue0(recordArray[0]);
            resultRecord.setCheckValue50(recordArray[1]);
            resultRecord.setCheckValue100(recordArray[2]);
            resultRecord.setCheckValue150(recordArray[3]);
            resultRecord.setCheckValue200(recordArray[4]);
            resultRecord.setCheckValue250(recordArray[5]);
            resultRecord.setCheckValue280(recordArray[6]);
            resultRecord.setCheckValue300(recordArray[7]);

            resultRecord.setCheckValue280_2(recordArray[8]);
            resultRecord.setCheckValue300_2(recordArray[9]);
            resultRecord.setCheckValue250_2(recordArray[10]);
            resultRecord.setCheckValue200_2(recordArray[11]);
            resultRecord.setCheckValue150_2(recordArray[12]);
            resultRecord.setCheckValue100_2(recordArray[13]);
            resultRecord.setCheckValue50_2(recordArray[14]);
            resultRecord.setCheckValue0_2(recordArray[15]);
        } else {
            //update for "add 两个 0档位"
            resultRecord.setCheckValue0(recordArray[0]);
            resultRecord.setCheckValue50(recordArray[1]);
            resultRecord.setCheckValue100(recordArray[2]);
            resultRecord.setCheckValue150(recordArray[3]);
            resultRecord.setCheckValue200(recordArray[4]);
            resultRecord.setCheckValue250(recordArray[5]);
            resultRecord.setCheckValue280(recordArray[6]);

            //注意这是是反的
            resultRecord.setCheckValue0_2(recordArray[13]);
            resultRecord.setCheckValue50_2(recordArray[12]);
            resultRecord.setCheckValue100_2(recordArray[11]);
            resultRecord.setCheckValue150_2(recordArray[10]);
            resultRecord.setCheckValue200_2(recordArray[9]);
            resultRecord.setCheckValue250_2(recordArray[8]);
            resultRecord.setCheckValue280_2(recordArray[7]);

        }

        return resultRecord;

    }


    /**
     * update checkdate
     * @param record
     * @param recordArray
     * @return
     */
    private Record buildNewRecord(Record record,float[] recordArray) {
        Record resultRecord = new Record();
        resultRecord.setDone(true);
        boolean isOK = judgValue(recordArray);
        resultRecord.setResult(isOK ? Record.result_ok : Record.result_fail);
        resultRecord.setOrderId(record.getOrderId());
        resultRecord.setMachinetype(record.getMachinetype());
        resultRecord.setOrderSuffix(record.getOrderSuffix());
        resultRecord.setProduceIndex(record.getProduceIndex());
        resultRecord.setMachinetype(plan.getMachineType());
        if (planType == HomeActivity.CREATE_COMMON) {
            resultRecord.setCheckValue0(recordArray[0]);
            resultRecord.setCheckValue50(recordArray[1]);
            resultRecord.setCheckValue100(recordArray[2]);
            resultRecord.setCheckValue150(recordArray[3]);
            resultRecord.setCheckValue200(recordArray[4]);
            resultRecord.setCheckValue250(recordArray[5]);
            resultRecord.setCheckValue280(recordArray[6]);

            resultRecord.setCheckValue280_2(recordArray[7]);
            resultRecord.setCheckValue250_2(recordArray[8]);
            resultRecord.setCheckValue200_2(recordArray[9]);
            resultRecord.setCheckValue150_2(recordArray[10]);
            resultRecord.setCheckValue100_2(recordArray[11]);
            resultRecord.setCheckValue50_2(recordArray[12]);
            resultRecord.setCheckValue0_2(recordArray[13]);
        } else if (planType == HomeActivity.CREATE_UM_10) {
            resultRecord.setCheckValue0(recordArray[0]);
            resultRecord.setCheckValue50(recordArray[1]);
            resultRecord.setCheckValue100(recordArray[2]);
            resultRecord.setCheckValue150(recordArray[3]);
            resultRecord.setCheckValue200(recordArray[4]);
            resultRecord.setCheckValue250(recordArray[5]);
            resultRecord.setCheckValue280(recordArray[6]);
            resultRecord.setCheckValue300(recordArray[7]);

            resultRecord.setCheckValue280_2(recordArray[8]);
            resultRecord.setCheckValue300_2(recordArray[9]);
            resultRecord.setCheckValue250_2(recordArray[10]);
            resultRecord.setCheckValue200_2(recordArray[11]);
            resultRecord.setCheckValue150_2(recordArray[12]);
            resultRecord.setCheckValue100_2(recordArray[13]);
            resultRecord.setCheckValue50_2(recordArray[14]);
            resultRecord.setCheckValue0_2(recordArray[15]);
        } else {
            resultRecord.setCheckValue0(recordArray[0]);
            resultRecord.setCheckValue50(recordArray[1]);
            resultRecord.setCheckValue100(recordArray[2]);
            resultRecord.setCheckValue150(recordArray[3]);
            resultRecord.setCheckValue200(recordArray[4]);
            resultRecord.setCheckValue250(recordArray[5]);
            resultRecord.setCheckValue280(recordArray[6]);

            resultRecord.setCheckValue280_2(recordArray[7]);
            resultRecord.setCheckValue250_2(recordArray[8]);
            resultRecord.setCheckValue200_2(recordArray[9]);
            resultRecord.setCheckValue150_2(recordArray[10]);
            resultRecord.setCheckValue100_2(recordArray[11]);
            resultRecord.setCheckValue50_2(recordArray[12]);
            resultRecord.setCheckValue0_2(recordArray[13]);
        }

        resultRecord.setCheckDate(FormatUtils.formatNowTime());
        resultRecord.setLine(CommonSpUtil.getLine());

        return resultRecord;

    }


    private void nextRow(boolean menuClick) {
        //改变序号
        start = start + checkNumOneMachine;//start = 0 , 3, 6, 9
        if (start + checkNumOneMachine <= recordlist.size()) {
            //如果还有下一组的话
            for (int i = 0; i < checkNumOneMachine; i++) {
                titleList.get(i).setText(recordlist.get(start + i).getProduceIndex());
            }
        } else {
            //多若干个
            plan.setDone(true);
            for (int i = start; i < recordlist.size(); i++) {
                //保存为尾货
//                saveTailRecord(recordlist.get(i));
                plan.setDone(false);
            }
            //没有下一组完整的数组的话
            Toast.makeText(this, "没有下一组数据了", Toast.LENGTH_LONG).show();
            if (!menuClick) {
                DbHelper.getInstance(this).updatePlan(plan);
                EventBusUtils.sendCommand(StaticUtils.COMMAND_REFLASH);
                finish();
            } else {
                //有可能菜单栏点击，但并没有开始
                start = start - checkNumOneMachine;
            }
        }
    }

    private void tips(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }


}
