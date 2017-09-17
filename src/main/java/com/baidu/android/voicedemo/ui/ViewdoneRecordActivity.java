package com.baidu.android.voicedemo.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.baidu.android.voicedemo.bean.Plan;
import com.baidu.android.voicedemo.bean.Record;
import com.baidu.android.voicedemo.db.DbHelper;
import com.baidu.speech.recognizerdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 浏览已完成的订单号的所有机器（包括残次品）
 */
public class ViewdoneRecordActivity extends AppCompatActivity {

    MyAdapter myAdapter;
    List<Record> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_records_list);
        Plan plan = (Plan) getIntent().getSerializableExtra("data");
        setTitle("订单号:" + plan.getOrderId());
        TextView tips = (TextView) findViewById(R.id.tip_tv);
        tips.setText("已完成的机器序列号(" + plan.getOrderSuffix() + "...)");

        list = getDoneRecordList(plan);

        GridView listView = (GridView) findViewById(R.id.gridview);
        TextView emptyTv = (TextView) findViewById(R.id.empty_tv);
        listView.setEmptyView(emptyTv);
        if (list != null && list.size() > 0) {
            myAdapter = new MyAdapter(this, list);
            listView.setAdapter(myAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //查看item record 的数据
                    Record first = null;
                    Record second = null;
                    Record record = list.get(position);
                    for (Record initRe : initlist) {
                        if (initRe.getProduceIndex().equals(record.getProduceIndex())) {
                            if (initRe.getTestIndex().equals("0")) {
                                first = initRe;
                                //第一次测试数据
                            } else if (initRe.getTestIndex().equals("1")) {
                                //第二次测试数据
                                second = initRe;
                            }
                        }

                    }
                    Intent intent = new Intent(ViewdoneRecordActivity.this, ViewRecordItemActivity.class);
                    if(first!=null){
                        intent.putExtra("first", first);
                    }
                    if(second!=null){
                        intent.putExtra("second", second);
                    }
                    startActivity(intent);


                }
            });

        }
    }

    List<Record> initlist;

    /**
     * 取出重复的produceIndex
     * @param plan
     * @return
     */
    private List<Record> getDoneRecordList(Plan plan) {
        List<String> key = new ArrayList<>();
        List<Record> result = new ArrayList<>();
        initlist = DbHelper.getInstance(this).getAllDoneRecord2(plan.getOrderId());
        if (initlist != null && initlist.size() > 0) {
            for (Record record : initlist) {
                if (!key.contains(record.getProduceIndex())) {
                    result.add(record);
                    key.add(record.getProduceIndex());
                    Log.e("tag", "add  " + record.getProduceIndex());
                }
            }
        }
        return result;
    }

    private class MyAdapter extends BaseAdapter {
        List<Record> list;
        Context context;

        public MyAdapter(Context context, List<Record> list) {
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
            Record record = (Record) getItem(position);
            tv.setText(record.getProduceIndex());
            tv.setBackgroundColor(Color.YELLOW);
            return convertView;
        }
    }
}
