package com.baidu.android.voicedemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.baidu.speech.recognizerdemo.R;

/**
 * 选择创建的机器类型
 */
public class MachineTypeActivity extends AppCompatActivity {
    private TextView txtLog;
    private TextView createCommonOrderTV;
    private TextView createEspOrderTV;
    private TextView create299OrderTV;
    private TextView create300OrderTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_machine_type);
        createCommonOrderTV = (TextView) findViewById(R.id.btn_280);
        createEspOrderTV = (TextView) findViewById(R.id.btn_um);
        create299OrderTV = (TextView) findViewById(R.id.btn_299);
        create300OrderTV = (TextView) findViewById(R.id.btn_300);

        createCommonOrderTV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //新建280普通机型
                buildPlan(HomeActivity.CREATE_COMMON);
            }
        });
        createEspOrderTV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //新建um-10特殊机型
                buildPlan(HomeActivity.CREATE_UM_10);

            }
        });
        create299OrderTV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //新建299特殊机型
                buildPlan(HomeActivity.CREATE_299);

            }
        });
        create300OrderTV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //新建300特殊机型
                buildPlan(HomeActivity.CREATE_300);

            }
        });

    }

    private void buildPlan(int type) {
        Intent intent = new Intent(this,BuildPlanActivity.class);
        intent.putExtra("type",type);
        startActivity(intent);
    }

}
