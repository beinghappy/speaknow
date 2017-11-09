package com.baidu.android.voicedemo.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.android.voicedemo.bean.Plan;
import com.baidu.android.voicedemo.bean.Record;
import com.baidu.android.voicedemo.db.DbHelper;
import com.baidu.android.voicedemo.utils.CreateExcel;
import com.baidu.android.voicedemo.utils.FileUtils;
import com.baidu.android.voicedemo.utils.StaticUtils;
import com.baidu.speech.recognizerdemo.R;

import java.util.List;

import static com.baidu.speech.recognizerdemo.BuildConfig.isUM;

/**
 * 已完成生产单列表
 */
public class DonePlanListActivity extends AppCompatActivity {

    MyAdapter myAdapter;
    List<Plan> list;
    ListView listView;
    private static final String TAG = "DonePlanListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list);
        setTitle("已完成生产单列表");
        listView = (ListView) findViewById(R.id.listview);
        TextView emptyTv = (TextView) findViewById(R.id.empty_tv);
        listView.setEmptyView(emptyTv);

        initListView();

    }

    private void initListView() {
        list = getPlanList();
        if (list != null && list.size() > 0) {
            myAdapter = new MyAdapter(this, list);
            listView.setAdapter(myAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //非UM类型才有查看完成的订单的功能
                    if(!isUM){
                        openHandPlan(list.get(position));
                    }
                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    longclickPlan = list.get(position);
                    showDoneNoticeDialog(list.get(position));
                    return true;
                }
            });
        }
    }

    Plan longclickPlan;

    private void openHandPlan(Plan plan) {
        Intent intent = new Intent(this, ViewdoneRecordActivity.class);
        intent.putExtra("data", plan);
        startActivity(intent);
    }

    /**
     * 获取已完成的订单：没有残次品（尾货）
     * @return
     */
    private List<Plan> getPlanList() {
        return DbHelper.getInstance(this).getAllDonePlan();
    }

    private void doDelete(final Plan plan) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("请确认是否删除该检测计划");
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean b = DbHelper.getInstance(DonePlanListActivity.this).deletePlan(plan);
                boolean deleteReord = DbHelper.getInstance(DonePlanListActivity.this).deleteRecord(plan);
                if (b && deleteReord) {
                    list.remove(plan);
                    myAdapter.notifyDataSetChanged();
                    Log.i(TAG, "onClick: 删除plan和record成功");
                }
            }
        });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();

    }


    private void doEditDonePlan(Plan plan) {
        startActivity(new Intent(this, EditPlanActivity.class).putExtra("type", HomeActivity.DONE_PLAN).putExtra("data", plan));
    }


    private void showDoneNoticeDialog(final Plan plan) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        int arrayId = StaticUtils.currentUser.getRoleType() == StaticUtils.Role_Admin ? R.array.done_plan_list_functions : R.array.done_plan_list_functions_user;
        builder.setItems(getResources().getStringArray(arrayId), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    //详情
                    doEditDonePlan(plan);
                } else if (which == 1) {
                    //导出
                    boolean hasFail = DbHelper.getInstance(DonePlanListActivity.this).hasFailRecord(plan.getOrderId());
                    if (hasFail) {
                        showExportDialog(plan);
                    } else {
                        doExport(plan);
                    }
                } else {
                    //删除
                    doDelete(plan);
                }

            }
        }).create();
        builder.show();

    }

    private void showExportDialog(final Plan plan) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("该项目还有残次品未处理。是否继续导出信息？");
        builder.setPositiveButton("继续导出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doExport(plan);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }

    CreateExcel createExcel;

    //可能一次进来多次。每次为不同的machinetype
    private void doExport(Plan plan) {
        //startActivity(new Intent(this,ExportExcelActivity.class));
//        if (createExcel == null) {
        createExcel = new CreateExcel(plan);
//        }

        try {

            List<Record> list = DbHelper.getInstance(this).getAllDoneRecord(plan.getOrderId());
            if (list != null && list.size() > 0) {
                createExcel.saveDataToExcel(list, plan);
                String path = Environment.getExternalStorageDirectory() + "/AND/" + FileUtils.getExcelName(plan);
                Toast.makeText(this, "写入成功,路径:" + path, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "没有具体数据", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception:" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private class MyAdapter extends BaseAdapter {
        List<Plan> list;
        Context context;

        public MyAdapter(Context context, List<Plan> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.plan_item, null);
            }
            TextView tv = (TextView) convertView.findViewById(R.id.plan_name);
            tv.setText(getItem(position).toString());
            tv.setTextColor(Color.BLUE);
            return convertView;
        }
    }


    private class MyRecordAdapter extends BaseAdapter {
        List<Record> list;
        Context context;

        public MyRecordAdapter(Context context, List<Record> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.plan_item, null);
            }
            TextView tv = (TextView) convertView.findViewById(R.id.plan_name);
            tv.setText(getItem(position).toString());
            tv.setTextColor(Color.BLUE);
            return convertView;
        }
    }
}
