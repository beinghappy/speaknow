package com.baidu.android.voicedemo.ui.admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.android.voicedemo.bean.UserInfo;
import com.baidu.android.voicedemo.bean.UserManagerEvent;
import com.baidu.android.voicedemo.db.DbHelper;
import com.baidu.android.voicedemo.ui.user.UserRegisterActivity;
import com.baidu.speech.recognizerdemo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class UserManageActivity extends AppCompatActivity {

    ListView listView;
    TextView emptyTV;
    private List<UserInfo> list = null;// 数据

    private MyAdapter adapter;// ListView的Adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage);
        listView = (ListView) findViewById(R.id.listview_user);
        emptyTV = (TextView) findViewById(R.id.empty_tv);
        listView.setEmptyView(emptyTV);
        emptyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toUserRegister();
            }
        });


        list = DbHelper.getInstance(this).getUserInfos();
        if (list != null && list.size() > 0) {
            initListView(list);
        }

        EventBus.getDefault().register(this);

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(UserManagerEvent event) {
        list = DbHelper.getInstance(this).getUserInfos();
        if (list != null && list.size() > 0) {
            initListView(list);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void toUserRegister() {
        startActivity(new Intent(this, UserRegisterActivity.class));
    }

    private void initListView(List<UserInfo> list) {
        adapter = new MyAdapter(UserManageActivity.this, list);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.notifyDataSetChanged();
                supportInvalidateOptionsMenu();
                Log.e("tang", "click position == " + position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.manage_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.function_select:
                if (listView.getCheckedItemCount() == adapter.getCount()) {
                    unSelectedAll();
                } else {
                    selectedAll();
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.function_add:
                toUserRegister();
                break;
            case R.id.delete:
                showPathDialog();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    AlertDialog inputDialog;

    private void showPathDialog() {

        inputDialog = new AlertDialog.Builder(this)
                .setMessage("是否删除所选用户")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<UserInfo> deleteList = new ArrayList<>();
                        for (int i = 0; i < adapter.getCount(); i++) {
                            if (listView.isItemChecked(i)) {
                                deleteList.add(list.get(i));
                            }
                        }
                        if (deleteList != null && deleteList.size() > 0) {
                            boolean b = DbHelper.getInstance(UserManageActivity.this).deleteUserInfo(deleteList);
                            if (b) {
                                Toast.makeText(UserManageActivity.this, "delete success " + deleteList.size(), Toast.LENGTH_LONG).show();
                                EventBus.getDefault().post(new UserManagerEvent());

                            } else {
                                Toast.makeText(UserManageActivity.this, "delete error " + deleteList.size(), Toast.LENGTH_LONG).show();
                            }
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(false).show();
    }

    public void selectedAll() {
        for (int i = 0; i < adapter.getCount(); i++) {
            listView.setItemChecked(i, true);
        }
    }

    public void unSelectedAll() {
        listView.clearChoices();
    }

    private class MyAdapter extends BaseAdapter {

        private List<UserInfo> list;
        private LayoutInflater inflater;

        @SuppressLint("UseSparseArrays")
        public MyAdapter(Context context, List<UserInfo> list) {
            inflater = LayoutInflater.from(context);
            this.list = list;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list != null ? list.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return list != null ? list.get(position) : null;
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
            if (str != null) {
                viewHolder.tv_WorkerNum.setText(str.getUsername());
                viewHolder.tv_Password.setText(str.getPassword());
                if (listView.isItemChecked(position)) {
                    viewHolder.cb.setChecked(true);
                } else {
                    viewHolder.cb.setChecked(false);
                }
            }
            return convertView;
        }

        class ViewHolder {
            public TextView tv_WorkerNum;
            public TextView tv_Password;
            public CheckBox cb;
        }

    }
}
