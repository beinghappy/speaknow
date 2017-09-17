package com.baidu.android.voicedemo.frg;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.android.voicedemo.bean.Record;
import com.baidu.android.voicedemo.bean.ReflashEvent;
import com.baidu.android.voicedemo.db.DbHelper;
import com.baidu.android.voicedemo.ui.ViewRecordItemActivity;
import com.baidu.android.voicedemo.utils.DialogUtils;
import com.baidu.android.voicedemo.utils.EventBusUtils;
import com.baidu.android.voicedemo.utils.StaticUtils;
import com.baidu.speech.recognizerdemo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


/**
 * 残次品界面:含有残次品和尾货
 */
public class FailFragment extends Fragment {

    ListView listView;
    List<Record> failrecordList;
    MyRecordAdapter myAdapter;
    List<Record> initlist;//原始的所有数据

    private static final String TAG = "FailFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_fail, null);
        EventBus.getDefault().register(this);
        listView = (ListView) view.findViewById(R.id.listview);
        TextView emptyTv = (TextView) view.findViewById(R.id.empty_tv);
        listView.setEmptyView(emptyTv);
        initFailListView();
        return view;
    }

    Record first = null;
    Record second = null;

    private void initFailListView() {
        //有可能生成多个残次的record;要过滤相同的
        failrecordList = getFailRecordList();
        if (failrecordList != null && failrecordList.size() > 0) {
            myAdapter = new MyRecordAdapter(getActivity(), failrecordList);
            listView.setAdapter(myAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //只有编辑
                    DialogUtils.showChangeLineDialog(getActivity(), position, failrecordList.get(position));
                }
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Record item = failrecordList.get(position);
                    List<Record> result = DbHelper.getInstance(getActivity()).getAllFailRecordById(item.getOrderId(),item.getProduceIndex());
                    if (result.size() > 1) {
                        //result是与position相同的getOrderId和getProduceIndex有几个
                        //里面有若干个，说明是已经检测过的fail产品，而不是没有检测过的尾货
                        for (Record initRe : result) {
                            if (initRe.getTestIndex().equals("0")) {
                                first = initRe;
                                //第一次测试数据
                            } else if (initRe.getTestIndex().equals("1")) {
                                //第二次测试数据
                                second = initRe;
                            }

                        }
                        showQueryDetailDialog(new DialogInterface.OnClickListener() {
                                                  @Override
                                                  public void onClick(DialogInterface dialog, int which) {

                                                      Intent intent = new Intent(getActivity(), ViewRecordItemActivity.class);
                                                      if (first != null) {
                                                          intent.putExtra("first", first);
                                                      }
                                                      if (second != null) {
                                                          intent.putExtra("second", second);
                                                      }
                                                      startActivity(intent);
                                                  }
                                              }
                        );
                    } else {
                        Toast.makeText(getActivity(), "没有测试，没有详情可查看", Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
            });

        } else {
            failrecordList = new ArrayList<>();
            myAdapter = new MyRecordAdapter(getActivity(), failrecordList);
            listView.setAdapter(myAdapter);
        }

    }


    private void showQueryDetailDialog(DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(getResources().getStringArray(R.array.fail_function), onClickListener);
        builder.create();
        builder.show();

    }


    /**
     * 取出重复的produceIndex
     *
     * @return
     */
    private List<Record> getFailRecordList() {
        List<String> key = new ArrayList<>();
        List<Record> result = new ArrayList<>();
        initlist = DbHelper.getInstance(getActivity()).getFailAndTailRecord();
        if (initlist != null && initlist.size() > 0) {
            for (Record record : initlist) {
                if (!key.contains(record.getOrderId() + ":" + record.getProduceIndex())) {
                    result.add(record);
                    key.add(record.getOrderId() + ":" + record.getProduceIndex());
                    Log.e(TAG, "add  " + record.getOrderId() + ":" + record.getProduceIndex());
                }
            }
        }
        return result;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ReflashEvent command) {
        Log.e(TAG, "onEventMainThread----failfragment : " + command.getOrderId());
        if (command.getCommondId() == StaticUtils.COMMAND_REFLASH) {
            initFailListView();
            //除了刷新界面之外，还需要刷新一下plan的数据库
            List<Record> tmpList = DbHelper.getInstance(getActivity()).getAllInitRecord(command.getOrderId());
            if (tmpList != null && tmpList.size() > 0) {
                Log.e(TAG, "onEventMainThread----该订单还有record "+tmpList.size());
            } else {
                //此orderId的plan的done应update为true
                boolean b = DbHelper.getInstance(getActivity()).updatePlanByOrderId(command.getOrderId());
                Log.e(TAG, "onEventMainThread----更新orderid为" + command.getOrderId() + "的plan成功");
                if(b){
                    EventBusUtils.sendCommand(StaticUtils.COMMAND_REFLASH_PLAN);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
        public Record getItem(int position) {
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
            tv.setText(getItem(position).getOrderId() + " : " + getItem(position).getProduceIndex());
            if (getItem(position).getResult() == Record.result_init) {
                tv.setTextColor(Color.BLUE);
            } else {
                tv.setTextColor(Color.RED);
            }
            return convertView;
        }
    }
}
