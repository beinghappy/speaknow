package com.baidu.android.voicedemo.frg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.baidu.android.voicedemo.bean.ReflashEvent;
import com.baidu.android.voicedemo.db.DbHelper;
import com.baidu.android.voicedemo.ui.EditPlanActivity;
import com.baidu.android.voicedemo.ui.HomeActivity;
import com.baidu.android.voicedemo.ui.MainActivity;
import com.baidu.android.voicedemo.utils.CreateExcel;
import com.baidu.android.voicedemo.utils.EventBusUtils;
import com.baidu.android.voicedemo.utils.FileUtils;
import com.baidu.android.voicedemo.utils.StaticUtils;
import com.baidu.speech.recognizerdemo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


/**
 * 未完成计划的主要界面
 */
public class UndoneFragment extends Fragment {

    MyAdapter myAdapter;
    List<Plan> list;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_undone,null);
        //注册
        EventBus.getDefault().register(this);
        listView = (ListView) view.findViewById(R.id.listview);
        TextView emptyTv = (TextView) view.findViewById(R.id.empty_tv);
        listView.setEmptyView(emptyTv);

        initListView();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注册
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ReflashEvent event){
        Log.e("tang:undoneFragment","onEventMainThread========"+event.getCommondId());
        if(event.getCommondId() == StaticUtils.COMMAND_REFLASH || event.getCommondId() == StaticUtils.COMMAND_REFLASH_PLAN){
            initListView();
        }
    }

    private void initListView() {
        list = getPlanList();
        if (list != null && list.size() > 0) {
            myAdapter = new MyAdapter(getActivity(), list);
            listView.setAdapter(myAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //如果是已测试的话，则有多个相同的orderid，则必须过滤相同的，不然会出现两个相同的产品凑成一个
                    List<Record> recordlist = DbHelper.getInstance(getActivity()).getAllInitRecord(list.get(position).getOrderId());
                    if (recordlist != null && recordlist.size() >= MainActivity.checkNumOneMachine) {
                        //可以拼凑成一组
                        openSpeakPlan(list.get(position));
                    }else{
                        Toast.makeText(getActivity(),"未完成的只有尾数",Toast.LENGTH_LONG).show();
                    }

                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    showUndoneNoticeDialog(list.get(position));
                    return true;
                }
            });
        }else{
            listView.setAdapter(null);
        }
    }

    private void showUndoneNoticeDialog(final Plan plan) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        int arrayId = StaticUtils.currentUser.getRoleType() == StaticUtils.Role_Admin ? R.array.plan_list_functions : R.array.plan_list_functions_user;
        builder.setItems(getResources().getStringArray(arrayId), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    //导出已完成的数据
                    doExport(plan);
                } else if (which == 1) {
                    //详情
                    doViewUndonePlanDetail(plan);
                } else {
                    //删除
                    doDelete(plan);
                }

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

            List<Record> list = DbHelper.getInstance(getActivity()).getAllDoneRecord(plan.getOrderId());
            if (list != null && list.size() > 0) {
                createExcel.saveDataToExcel(list, plan);
                String path = Environment.getExternalStorageDirectory() + "/AND/" + FileUtils.getExcelName(plan);
                Toast.makeText(getActivity(), "写入成功,路径:" + path, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "没有具体数据", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Exception:"+e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


    private void doDelete(final Plan plan) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("请确认是否删除该检测计划");
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean b = DbHelper.getInstance(getActivity()).deletePlan(plan);
                boolean deleteReord = DbHelper.getInstance(getActivity()).deleteRecord(plan);
                if (b && deleteReord) {
                    list.remove(plan);
                    myAdapter.notifyDataSetChanged();
                    EventBusUtils.sendCommand(StaticUtils.COMMAND_REFLASH);
                    Log.i("tag", "onClick: 删除plan和record成功");
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


    private void doViewUndonePlanDetail(Plan plan) {
        startActivity(new Intent(getActivity(), EditPlanActivity.class).putExtra("type", HomeActivity.UNDONE_PLAN).putExtra("data", plan));
    }

    /**
     * 开启语音
     * @param plan
     */
    private void openSpeakPlan(Plan plan) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("data", plan);
        startActivity(intent);
    }


    private List<Plan> getPlanList() {
       return DbHelper.getInstance(getActivity()).getAllUndonePlan();
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
        public Plan getItem(int position) {
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
            tv.setText(getItem(position).getOrderId());
            tv.setTextColor(Color.RED);
            return convertView;
        }
    }
}
