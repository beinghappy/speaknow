package com.baidu.android.voicedemo.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.baidu.android.voicedemo.bean.Record;
import com.baidu.android.voicedemo.ui.FailRecordHandActivity;

/**
 * ${desc} 类的功能描述
 * Created by tanglong on 2017/7/10 0010 19:14.
 *
 * @since version 2.1.6
 */

public class DialogUtils {
    public static void showChangeLineDialog(final Context context, final int position , final Record failRecord) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("要改变新的线别，和检查人员工号，点击确定");
//        View view  = LayoutInflater.from(context).inflate(R.layout.change_setting,null);
//        final Spinner spinner_produce_class = (Spinner) view.findViewById(R.id.spinner_produce_class);
//        final Spinner spinner_produce_line = (Spinner) view.findViewById(R.id.spinner_produce_line);
//        final EditText editText_checker1 = (EditText) view.findViewById(R.id.editText_checker1);
//        builder.setView(view);
//        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String produceClass = (String) spinner_produce_class.getSelectedItem();
//                String produceLine = (String) spinner_produce_line.getSelectedItem();
//                String checkJobIndex1 = editText_checker1.getText().toString().trim();
//
//                if(TextUtils.isEmpty(produceClass)
//                        || TextUtils.isEmpty(produceLine)
//                        || TextUtils.isEmpty(checkJobIndex1)
//                        ){
//                    Toast.makeText(context,"输入为空",Toast.LENGTH_LONG).show();
//
//                }else{
//                    dialog.dismiss();
//                    CommonSpUtil.setLine(produceClass+"课"+produceLine+"线");
//                    CommonSpUtil.setWorker(checkJobIndex1);
//
                    context.startActivity(new Intent(context, FailRecordHandActivity.class)
                                .putExtra("index", position)
                                .putExtra("data",failRecord));
//
//                }
//
//            }
//        });
//        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                context.startActivity(new Intent(context, FailRecordHandActivity.class)
//                        .putExtra("index", position)
//                        .putExtra("data",failRecord));
//            }
//        });
//        builder.create();
//        builder.show();

    }

    public static void showChangeLineDialog(final Context context) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("要改变新的线别，和检查人员工号，点击确定");
//        View view  = LayoutInflater.from(context).inflate(R.layout.change_setting,null);
//        final Spinner spinner_produce_class = (Spinner) view.findViewById(R.id.spinner_produce_class);
//        final Spinner spinner_produce_line = (Spinner) view.findViewById(R.id.spinner_produce_line);
//        final EditText editText_checker1 = (EditText) view.findViewById(R.id.editText_checker1);
//        builder.setView(view);
//        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String produceClass = (String) spinner_produce_class.getSelectedItem();
//                String produceLine = (String) spinner_produce_line.getSelectedItem();
//                String checkJobIndex1 = editText_checker1.getText().toString().trim();
//
//                if(TextUtils.isEmpty(produceClass)
//                        || TextUtils.isEmpty(produceLine)
//                        || TextUtils.isEmpty(checkJobIndex1)
//                        ){
//                    Toast.makeText(context,"输入为空",Toast.LENGTH_LONG).show();
//
//                }else{
//
//                    dialog.dismiss();
//                    CommonSpUtil.setLine(produceClass+"课"+produceLine+"线");
//                    CommonSpUtil.setWorker(checkJobIndex1);
//
//                }
//
//            }
//        });
//        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        builder.create();
//        builder.show();

    }


    public static void showExitDialog(final Context context,DialogInterface.OnClickListener onExitClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("温馨提示");
        builder.setMessage("您好，该组测试并未完成。请按继续测试");
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
//        builder.setPositiveButton("退出",onExitClickListener);
        builder.create();
        builder.show();

    }



}
