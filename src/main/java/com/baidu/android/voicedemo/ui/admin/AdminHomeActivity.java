package com.baidu.android.voicedemo.ui.admin;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.baidu.android.voicedemo.ui.HomeActivity;
import com.baidu.speech.recognizerdemo.R;

/**
 * 管理员首页：管理用户，及管理订单
 */
public class AdminHomeActivity extends AppCompatActivity {
    private TextView manageUserTV;
    private TextView managePlanTV;
    private TextView editMyPasswordTV;

    TextView container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_home_admin);
        container = (TextView) findViewById(R.id.container);
        manageUserTV = (TextView) findViewById(R.id.btn_manager_user);
        managePlanTV = (TextView) findViewById(R.id.btn_manage_order);
        editMyPasswordTV = (TextView) findViewById(R.id.btn_edit_pass);

        manageUserTV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //管理用户
                toUserManage();
            }
        });
        editMyPasswordTV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //修改自己密码
                toEditPassword();
            }
        });
        managePlanTV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //直接登陆
                toHome();
            }
        });

    }

    private void toUserManage() {
        startActivity(new Intent(this, UserManageActivity.class));
    }

    private void toEditPassword() {
        startActivity(new Intent(this, EditPasswordActivity.class));
    }

    private void toHome() {
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.version,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.version){
            String pkName = this.getPackageName();
            try {
                String versionName = getPackageManager().getPackageInfo(pkName, 0).versionName;
                Snackbar.make(container, "版本："+versionName,Snackbar.LENGTH_LONG).show();

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }


        }
        return true;
    }

}
