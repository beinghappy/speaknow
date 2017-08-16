package com.baidu.android.voicedemo.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.speech.recognizerdemo.R;

/**
 * 首页：三种功能按钮，和查看版本
 */
public class HomeActivity extends AppCompatActivity {
    private TextView createOrderTV;//新建订单
    private TextView undonePlanTV;//继续未完成
    private TextView donePlanTV;//已完成的

    public final static int DONE_PLAN = 1 ;
    public final static int UNDONE_PLAN = 2 ;


    public final static int CREATE_COMMON = 1 ;
    public final static int CREATE_UM_10 = 2 ;
    public final static int CREATE_299 = 3 ;
    public final static int CREATE_300 = 4 ;
    TextView container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_home);
        container = (TextView) findViewById(R.id.container);
        createOrderTV = (TextView) findViewById(R.id.btn_create_common);
        donePlanTV = (TextView) findViewById(R.id.btn_done);
        undonePlanTV = (TextView) findViewById(R.id.btn_undone);

        createOrderTV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //跳转机型选择界面
                jumpMachineType();
            }
        });
        donePlanTV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                jumpDonePlan(DONE_PLAN);
            }
        });
        undonePlanTV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                jumpUndonePlan();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(perssion, REQUEST_ID);
        }
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

    public static final int REQUEST_ID = 123;
    String[] perssion = {Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};



    private void jumpMachineType() {
        Intent intent = new Intent(this,MachineTypeActivity.class);
        startActivity(intent);
    }
    private void jumpDonePlan(int type) {
        Intent intent = new Intent(this,DonePlanListActivity.class);
        intent.putExtra("type",type);
        startActivity(intent);
    }

    private void jumpUndonePlan(){
        Intent intent = new Intent(this,UnDonePlanListActivity.class);
        startActivity(intent);
    }

    AlertDialog inputDialog;
    private void showPathDialog() {

        inputDialog = new AlertDialog.Builder(this)
                .setTitle("存储权限不可用")
                .setMessage("请在-应用设置-权限-中，允许使用存储权限来保存用户数据")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_ID) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        showDialogTipUserGoToAppSettting();
                    } else
                        finish();
                } else {
                    //Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    // 提示用户去应用设置界面手动开启权限

    AlertDialog dialog;
    private void showDialogTipUserGoToAppSettting() {

        dialog = new AlertDialog.Builder(this)
                .setTitle("存储权限不可用")
                .setMessage("请在-应用设置-权限-中，允许支付宝使用存储权限来保存用户数据")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();

        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);

        startActivityForResult(intent, 123);
    }

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(this, perssion[0]);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 提示用户应该去应用设置界面手动开启权限
                    showDialogTipUserGoToAppSettting();
                } else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
