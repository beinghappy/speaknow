package com.baidu.android.voicedemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;

import com.baidu.android.voicedemo.bean.Plan;
import com.baidu.speech.recognizerdemo.R;


public class EditPlanActivity extends AppCompatActivity {

    private EditText editText_filename;
    private EditText editText_order_num;
    private EditText editText_machine_type;
    private EditText editText_produce_class;
    private EditText editText_produce_line;
    private EditText editText_checker1;
    private EditText editText_checker2;

    private EditText editText_index_headcode;
    private EditText editText_index_start;
    private EditText editText_checker_num;
    private EditText editText_machine_num_per;
    private Spinner spinner_current_index;
    private EditText editText_check_start_time;


    int planType = HomeActivity.CREATE_COMMON;
    Plan plan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plan);
        setTitle("查看工作计划");

        planType = getIntent().getIntExtra("type", HomeActivity.UNDONE_PLAN);
        plan = (Plan) getIntent().getSerializableExtra("data");

        editText_filename = (EditText) findViewById(R.id.editText_filename);
        editText_machine_type = (EditText) findViewById(R.id.editText_machine_type);
        editText_order_num = (EditText) findViewById(R.id.editText_order_num);
        editText_produce_class = (EditText) findViewById(R.id.editText_produce_class);
        editText_produce_line = (EditText) findViewById(R.id.editText_produce_line);
        editText_checker1 = (EditText) findViewById(R.id.editText_checker1);
        editText_checker2 = (EditText) findViewById(R.id.editText_checker2);


        editText_index_headcode = (EditText) findViewById(R.id.editText_order_index_suffix);
        editText_index_start = (EditText) findViewById(R.id.editText_index_start);
        editText_checker_num = (EditText) findViewById(R.id.editText_checker_num);
        editText_machine_num_per = (EditText) findViewById(R.id.editText_machine_num_per);
        spinner_current_index = (Spinner) findViewById(R.id.spinner_current_index);
        editText_check_start_time = (EditText) findViewById(R.id.editText_check_start_time);

        initValues();
        setForbindden();
    }

    private void setForbindden(){
        editText_filename.setEnabled(false);
        editText_order_num.setEnabled(false);
        editText_machine_type.setEnabled(false);
        editText_produce_class.setEnabled(false);
        editText_produce_line.setEnabled(false);
        editText_checker1.setEnabled(false);
        editText_checker2.setEnabled(false);

        editText_index_headcode.setEnabled(false);
        editText_index_start.setEnabled(false);
        editText_checker_num.setEnabled(false);
        editText_machine_num_per.setEnabled(false);
        editText_check_start_time.setEnabled(false);

        spinner_current_index.setEnabled(false);
    }


    private void initValues(){
        editText_filename.setText(plan.getOrderId());
        editText_machine_type.setText(plan.getMachineTypeDisplay());
        editText_order_num.setText(plan.getOrderAccount());
        editText_produce_class.setText(plan.getProduceClass());
        editText_produce_line.setText(plan.getProduceLine());
        editText_checker1.setText(plan.getCheckerJobIndex1());
        editText_checker2.setText(plan.getCheckerJobIndex2());


        editText_index_headcode.setText(plan.getOrderSuffix());
        editText_index_start.setText(plan.getOrderIndexBegin());
        editText_checker_num.setText(plan.getWorkPositionAccount()+"");
        editText_machine_num_per.setText(plan.getCheckMachineNumPer()+"");
        int currentIndex = plan.getCheckTableIndex();
        //值为1，2,3,但是postion为0,1,2
        spinner_current_index.setSelection(currentIndex-1);
        editText_check_start_time.setText(plan.getCheckDate());
    }

}
