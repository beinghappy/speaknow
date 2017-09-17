package com.baidu.android.voicedemo.ui.admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.android.voicedemo.bean.UserInfo;
import com.baidu.android.voicedemo.db.DbHelper;
import com.baidu.android.voicedemo.ui.user.UserRegisterActivity;
import com.baidu.speech.recognizerdemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserManageActivity2 extends AppCompatActivity {

    ListView listView;
    TextView emptyTV;
    private static final int NOSELECT_STATE = -1;// 表示未选中任何CheckBox
    private List<UserInfo> list = new ArrayList<UserInfo>();// 数据
    private List<UserInfo> list_delete = new ArrayList<UserInfo>();// 需要删除的数据
    private boolean isMultiSelect = false;// 是否处于多选状态

    private MyAdapter adapter;// ListView的Adapter
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage);
        listView = (ListView) findViewById(R.id.listview_user);
        emptyTV = (TextView) findViewById(R.id.empty_tv);
        listView.setEmptyView(emptyTV);
        list = DbHelper.getInstance(this).getUserInfos();
        if(list != null){
            //
            initListView(list);
        }

        emptyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toUserRegister();
            }
        });

    }



    private void toUserRegister() {
        startActivity(new Intent(this, UserRegisterActivity.class));
    }

    private void initListView(List<UserInfo> list){
        adapter = new MyAdapter(UserManageActivity2.this, list, NOSELECT_STATE);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    private class MyAdapter extends BaseAdapter {

        private List<UserInfo> list;
        private LayoutInflater inflater;

        private HashMap<Integer, Integer> isCheckBoxVisible;// 用来记录是否显示checkBox
        private HashMap<Integer, Boolean> isChecked;// 用来记录是否被选中

        @SuppressLint("UseSparseArrays")
        public MyAdapter(Context context, List<UserInfo> list, int position) {
            inflater = LayoutInflater.from(context);
            this.list = list;
            isCheckBoxVisible = new HashMap<Integer, Integer>();
            isChecked = new HashMap<Integer, Boolean>();
            // 如果处于多选状态，则显示CheckBox，否则不显示
            if (isMultiSelect) {
                for (int i = 0; i < list.size(); i++) {
                    isCheckBoxVisible.put(i, CheckBox.VISIBLE);
                    isChecked.put(i, false);
                }
            } else {
                for (int i = 0; i < list.size(); i++) {
                    isCheckBoxVisible.put(i, CheckBox.INVISIBLE);
                    isChecked.put(i, false);
                }
            }

            // 如果长按Item，则设置长按的Item中的CheckBox为选中状态
            if (isMultiSelect && position >= 0) {
                isChecked.put(position, true);
            }
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_layout, null);
                viewHolder.tv_WorkerNum = (TextView) convertView.findViewById(R.id.tv_workNum);
                viewHolder.tv_Password = (TextView) convertView.findViewById(R.id.tv_password);
                viewHolder.cb = (CheckBox) convertView.findViewById(R.id.cb_select);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final UserInfo str = list.get(position);
            viewHolder.tv_WorkerNum.setText(str.getUsername());
            viewHolder.tv_Password.setText(str.getPassword());
            // 根据position设置CheckBox是否可见，是否选中
            viewHolder.cb.setChecked(isChecked.get(position));
            viewHolder.cb.setVisibility(isCheckBoxVisible.get(position));
            // ListView每一个Item的长按事件
            convertView.setOnLongClickListener(new onMyLongClick(position, list));
            /*
             * 在ListView中点击每一项的处理
             * 如果CheckBox未选中，则点击后选中CheckBox，并将数据添加到list_delete中
             * 如果CheckBox选中，则点击后取消选中CheckBox，并将数据从list_delete中移除
             */
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 处于多选模式
                    if (isMultiSelect) {
                        if (viewHolder.cb.isChecked()) {
                            viewHolder.cb.setChecked(false);
                            list_delete.remove(str);
                        } else {
                            viewHolder.cb.setChecked(true);
                            list_delete.add(str);
                        }
                        //                        tv_sum.setText("共选择了" + list_delete.size() + "项");
                    }
                }
            });
            return convertView;
        }

        class ViewHolder {
            public TextView tv_WorkerNum;
            public TextView tv_Password;
            public CheckBox cb;
        }

        // 自定义长按事件
        class onMyLongClick implements View.OnLongClickListener {

            private int position;
            private List<UserInfo> list;

            // 获取数据，与长按Item的position
            public onMyLongClick(int position, List<UserInfo> list) {
                this.position = position;
                this.list = list;
            }

            // 在长按监听时候，切记将监听事件返回ture
            @Override
            public boolean onLongClick(View v) {
                isMultiSelect = true;
                list_delete.clear();
                // 添加长按Item到删除数据list中
                list_delete.add(list.get(position));
                for (int i = 0; i < list.size(); i++) {
                    adapter.isCheckBoxVisible.put(i, CheckBox.VISIBLE);
                }
                // 根据position，设置ListView中对应的CheckBox为选中状态
                adapter = new UserManageActivity2.MyAdapter(UserManageActivity2.this, list, position);
                listView.setAdapter(adapter);
                return true;
            }
        }
    }
}
