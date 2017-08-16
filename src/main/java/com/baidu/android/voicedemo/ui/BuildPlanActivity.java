package com.baidu.android.voicedemo.ui;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.baidu.android.voicedemo.bean.Plan;
import com.baidu.android.voicedemo.bean.Record;
import com.baidu.android.voicedemo.db.DbHelper;
import com.baidu.android.voicedemo.utils.CommonSpUtil;
import com.baidu.android.voicedemo.utils.FormatUtils;
import com.baidu.speech.recognizerdemo.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 创建不同类型的订单
 */
public class BuildPlanActivity extends AppCompatActivity {

    private EditText editText_filename;
    private EditText editText_order_num;
    private EditText editText_machine_type;
    private Spinner spinner_produce_class;
    private Spinner spinner_produce_line;

    private EditText editText_checker1;
//    private EditText editText_checker2;
    private TextInputLayout check1_holder;
    private TextInputLayout check2_holder;

    private EditText editText_index_headcode;
    private EditText editText_index_start;
    private EditText editText_checker_num;
    private EditText editText_machine_num_per;
    private Spinner spinner_current_index;
    private EditText editText_check_start_time;


    int planType = HomeActivity.CREATE_COMMON;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_plan);
        initView();
        initValues();
        initListener();
    }

    private void initListener() {
        editText_checker1.setSelection(editText_checker1.getText().length());
//        editText_checker2.setSelection(editText_checker1.getText().length());
        editText_checker1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //检测错误输入，当输入错误时，hint会变成红色并提醒
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //检查实际是否匹配，由自己实现
                doCheckWorkNumber(charSequence.toString(), check1_holder);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
//        editText_checker2.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            //检测错误输入，当输入错误时，hint会变成红色并提醒
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                doCheckWorkNumber(s.toString(), check2_holder);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
    }

    private void initValues() {
        setTitle("基本资料设置");
        planType = getIntent().getIntExtra("type", HomeActivity.CREATE_COMMON);
        String text = "";
        if (planType == HomeActivity.CREATE_COMMON) {
            text = "";
        } else if (planType == HomeActivity.CREATE_UM_10) {
            //um-10
            text = "UM-10";
        } else if (planType == HomeActivity.CREATE_299) {
            text = "专用299档";
        } else {
            text = "专用300档";
        }
        editText_machine_type.setText(text);
    }

    private void initView() {
        editText_filename = (EditText) findViewById(R.id.editText_filename);
        editText_machine_type = (EditText) findViewById(R.id.editText_machine_type);
        editText_order_num = (EditText) findViewById(R.id.editText_order_num);
        spinner_produce_class = (Spinner) findViewById(R.id.spinner_produce_class);
        spinner_produce_line = (Spinner) findViewById(R.id.spinner_produce_line);
        editText_checker1 = (EditText) findViewById(R.id.editText_checker1);
//        editText_checker2 = (EditText) findViewById(R.id.editText_checker2);

        check1_holder = (TextInputLayout) findViewById(R.id.checker1_holder);
        check2_holder = (TextInputLayout) findViewById(R.id.checker2_holder);


        editText_index_headcode = (EditText) findViewById(R.id.editText_order_index_suffix);
        editText_index_start = (EditText) findViewById(R.id.editText_index_start);
        editText_checker_num = (EditText) findViewById(R.id.editText_checker_num);
        editText_machine_num_per = (EditText) findViewById(R.id.editText_machine_num_per);
        spinner_current_index = (Spinner) findViewById(R.id.spinner_current_index);
        editText_check_start_time = (EditText) findViewById(R.id.editText_check_start_time);
        editText_check_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editText_check_start_time);
            }
        });

        editText_checker_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s)){
                    initSpinnerCurrentIndex(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void showDatePickerDialog(final EditText dateET){
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(BuildPlanActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                String text = String.format("%s%02d%02d",String.valueOf(year),monthOfYear+1,dayOfMonth);
                Log.e("tang","text=="+text);
                dateET.setText(text);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    List<String> currentIndexList = null;
    private void initSpinnerCurrentIndex(String checkNum){
        int checkAcount = Integer.parseInt(checkNum);
        currentIndexList = new ArrayList<>();
        for(int i = 0 ; i<checkAcount;i++){
            currentIndexList.add(String.valueOf(i+1));
        }
        if(currentIndexList.size() > 0 ){
            spinner_current_index.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,currentIndexList));
        }
    }

    private void doCheckWorkNumber(String input, TextInputLayout holder) {
        //检查实际是否匹配，由自己实现
        if (!checkWorkNumber(input)) {
            holder.setErrorEnabled(true);
            holder.setError("请检查格式");
            return;
        } else {
            holder.setErrorEnabled(false);
            holder.setError(null);
        }
    }

    /**
     * 匹配输入的字符
     *
     * @param input
     * @return
     */
    public boolean checkWorkNumber(String input) {
        String tmp = "[B](\\d{4})";
        Pattern p = Pattern.compile(tmp);
        Matcher m = p.matcher(input);
        System.out.println(m.matches() + "---");
        return m.matches();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            doSave();
        } else if (item.getItemId() == R.id.cancel) {
            finish();
        } else if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void doSave() {
        String fileName = editText_filename.getText().toString().trim();
        String machineTypeDisplay = editText_machine_type.getText().toString().trim();
        String orderNumber = editText_order_num.getText().toString().trim();
        String produceClass = (String) spinner_produce_class.getSelectedItem();
        String produceLine = (String) spinner_produce_line.getSelectedItem();
        String checkJobIndex1 = editText_checker1.getText().toString().trim();
//        String checkJobIndex2 = editText_checker2.getText().toString().trim();

        String orderSuffix = editText_index_headcode.getText().toString().trim();
        String orderStartIndex = editText_index_start.getText().toString().trim();
        String checkerNumber = editText_checker_num.getText().toString().trim();
        String machineNumberPer = editText_machine_num_per.getText().toString().trim();
        String checkCurrentJobIndex = (String) spinner_current_index.getSelectedItem();
        String checkBeginTime = editText_check_start_time.getText().toString().trim();

        if (TextUtils.isEmpty(fileName)
                || TextUtils.isEmpty(orderNumber)
                || TextUtils.isEmpty(produceClass)
                || TextUtils.isEmpty(produceLine)
//                || TextUtils.isEmpty(checkJobIndex1)
//                || TextUtils.isEmpty(checkJobIndex2)
                || TextUtils.isEmpty(orderSuffix)
                || TextUtils.isEmpty(orderStartIndex)
                || TextUtils.isEmpty(checkerNumber)
                || TextUtils.isEmpty(machineNumberPer)
                || TextUtils.isEmpty(checkCurrentJobIndex)
                || TextUtils.isEmpty(checkBeginTime)
                || TextUtils.isEmpty(machineTypeDisplay)
                ) {
            Toast.makeText(BuildPlanActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        Plan plan = new Plan();
        plan.setOrderId(fileName);
        plan.setMachineType(planType + "");
        plan.setMachineTypeDisplay(machineTypeDisplay);
        plan.setOrderAccount(orderNumber);
        plan.setProduceClass(produceClass);
        plan.setProduceLine(produceLine);
//        plan.setCheckerJobIndex1(checkJobIndex1);
//        plan.setCheckerJobIndex2(checkJobIndex2);

        plan.setOrderSuffix(orderSuffix);
        plan.setOrderIndexBegin(orderStartIndex);
        try {
            plan.setCheckMachineNumPer(Integer.parseInt(machineNumberPer));
        } catch (Exception e) {
            plan.setCheckMachineNumPer(3);
        }
        try {
            plan.setWorkPositionAccount(Integer.parseInt(checkerNumber));
        } catch (Exception e) {
            plan.setWorkPositionAccount(3);
        }
        try {
            plan.setCheckTableIndex(Integer.parseInt(checkCurrentJobIndex));
        } catch (Exception e) {
            plan.setCheckTableIndex(1);
        }

        int orderAccount = -1;
        try {
            orderAccount = Integer.parseInt(orderNumber);
        } catch (Exception e) {
            orderAccount = -1;
        }
        plan.setCheckDate(checkBeginTime);

        //新建计划，done == false
        plan.setDone(false);


        if (orderAccount != -1 && !checkLogic(orderAccount, plan.getWorkPositionAccount(), plan.getCheckMachineNumPer(), plan.getCheckTableIndex())) {
            Toast.makeText(BuildPlanActivity.this, "机器数量不够安排到该工位，请与领导联系", Toast.LENGTH_SHORT).show();
            return;
        }


        int code = DbHelper.getInstance(this).addPlan(plan);
        if (code == DbHelper.SUCCESS) {
            buildPlanRecord(plan);
        } else if (code == DbHelper.ERROR) {
            Toast.makeText(this, "add fail", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "已存在该计划，请重新输入订单号", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * 判断是否符合逻辑
     *
     * @param orderNumber
     * @param checkerNumber
     * @param machineNumberPer
     * @param checkCurrentJobIndex
     * @return
     */
    private boolean checkLogic(int orderNumber, int checkerNumber, int machineNumberPer, int checkCurrentJobIndex) {
        if (machineNumberPer * checkCurrentJobIndex <= orderNumber) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 创建与该Plan有关的，本工位的测试Record
     */
    private void buildPlanRecord(Plan plan) {
        int produceNumber = 0;
        //多少个产品
        try {
            produceNumber = Integer.parseInt(plan.getOrderAccount());
        } catch (Exception e) {
            produceNumber = 0;
        }

        //从哪开始
        int startNo = 0;
        try {
            startNo = Integer.parseInt(plan.getOrderIndexBegin());
        } catch (Exception e) {
            startNo = 0;
        }

        //工位:3
        int workPosition = plan.getWorkPositionAccount();

        //本机的工位:1,2,3
        int currentPosition = plan.getCheckTableIndex();

        //每个工位每次几台机:3
        int machinePer = plan.getCheckMachineNumPer();

        if (produceNumber > 0) {
            show(this);
            //这里的开始，要加上初始的开始值
            int realStartNo = (currentPosition - 1) * machinePer + startNo;
            ArrayList<Record> recordList = new ArrayList<>();


            int groudId = 1 ;
            do {
                for (int j = 0; j < machinePer && (realStartNo + j < produceNumber + startNo); j++) {
                    //创建时，只创建一个record
                    Record record = new Record();
                    record.setOrderId(plan.getOrderId());
                    record.setDone(false);
                    record.setTestIndex("0");
                    record.setProduceIndex(String.format("%05d", realStartNo + j));
                    record.setOrderSuffix(plan.getOrderSuffix());
                    record.setMachinetype(plan.getMachineType());
                    record.setLevel(groudId);
                    Log.e("tag:tang", "index==" + record.getProduceIndex()+",getLevel =="+record.getLevel());
                    //更新添加线、时间、甚至worker
                    record.setLine(plan.getProduceClass()+"课"+plan.getProduceLine()+"线");
                    record.setCheckDate(FormatUtils.formatNowTime());
                    record.setCheckWorker(CommonSpUtil.getWorker(plan.getCheckerJobIndex1()));
                    //update by long.tang for "2017-7-13 第一条"
                    if(realStartNo + machinePer <= produceNumber + startNo){
                        //正常一组一组
                        record.setResult(Record.result_init);
                        record.setTail(false);
                    }else{
                        //尾数
                        record.setResult(Record.result_init);
                        record.setTail(true);
                    }
                    //update by long.tang for "2017-7-13 第一条"
                    recordList.add(record);

                }


                realStartNo = realStartNo + workPosition * machinePer;
                groudId++;
            } while (realStartNo < produceNumber + startNo);

            if (recordList != null && recordList.size() > 0) {
                boolean b = DbHelper.getInstance(this).addRecordList(recordList,true);
                if (b) {
                    hide();
                    Toast.makeText(this, "初始化订单成功", Toast.LENGTH_LONG).show();
                    jumpRecord(plan, recordList);
                    finish();
                } else {
                    hide();
                    Toast.makeText(this, "初始化订单失败", Toast.LENGTH_LONG).show();
                    finish();
                }

            } else {
                hide();
            }


        }


    }

    private void jumpRecord(Plan plan, ArrayList<Record> recordList) {
        startActivity(new Intent(this, MainActivity.class)
                .putExtra("data", plan)
                .putParcelableArrayListExtra("datalist", recordList));
    }


    private ProgressDialog progressDialog;

    public void show(Context context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("正在创建...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
        }

        progressDialog.show();
    }

    public void hide() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }
}
