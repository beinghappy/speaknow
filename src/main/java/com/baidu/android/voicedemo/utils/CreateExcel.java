package com.baidu.android.voicedemo.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.android.voicedemo.bean.Plan;
import com.baidu.android.voicedemo.bean.Record;
import com.baidu.android.voicedemo.ui.HomeActivity;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * Created by Administrator on 2016/11/20.
 * 主要功能，将数据库数据转化成Excel表格
 */
public class CreateExcel {

    // 准备设置excel工作表的标题
    private WritableSheet sheet;
    /**
     * 创建Excel工作薄
     */
    private WritableWorkbook wwb;

    /**
     * 要写入表格的头部
     */
    private static String[] title = {"序列号", "测试次数", "0", "50", "100", "150", "200", "250", "280", "280", "250", "200", "150", "100", "50", "0", "检查员", "日期","线别"};
    private static String[] title1 = {"序列号", "测试次数", "0", "50", "100", "150", "200", "250", "280", "280", "250", "200", "150", "100", "50", "0", "检查员", "日期","线别"};
    private static String[] title2 = {"序列号", "测试次数", "0", "50", "100", "150", "200", "250", "300", "280", "300",  "280","250", "200", "150", "100", "50", "0", "检查员", "日期","线别"};
    private static String[] title3 = {"序列号", "测试次数", "0", "50", "100", "150", "200", "250", "299", "299", "250", "200", "150", "100", "50", "0", "检查员", "日期","线别"};
    private static String[] title_300 = {"序列号", "测试次数", "0", "50", "100", "150", "200", "250", "300", "300", "250", "200", "150", "100", "50", "0", "检查员", "日期","线别"};

    private String machineType;
    private HashMap<String, String> indexMap = null;

    public CreateExcel(Plan plan) {
//        initExcel(dir, fileName, sheetTitle);
        indexMap = new HashMap<>();
        this.machineType = plan.getMachineType();
        if (machineType.equals(HomeActivity.CREATE_COMMON + "")) {
            title = title1;
        } else if (machineType.equals(HomeActivity.CREATE_UM_10 + "")) {
            title = title2;
        } else if (machineType.equals(HomeActivity.CREATE_300 + "")){
            title = title_300;
        }else{
            title = title3;
        }

        initExcel(FileUtils.getExcelDir(), FileUtils.getExcelName(plan), plan.getOrderId());

    }

//    public CreateExcel(String fileName,String sheetTitle) {
//        excelCreate(fileName,sheetTitle);
//    }


    /**
     * 初始化excel文件
     *
     * @param dir
     * @param fileName
     * @param sheetTitle
     */
    public void initExcel(String dir, String fileName, String sheetTitle) {
        try {
            /**输出的excel文件的路径*/
            String filePath = null;
            if (TextUtils.isEmpty(dir)) {

                filePath = Environment.getExternalStorageDirectory() + "/" + fileName;
            } else {
                filePath = dir + "/" + fileName;
            }
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            wwb = Workbook.createWorkbook(file);
            /**添加第一个工作表并设置第一个Sheet的名字*/
            sheet = wwb.createSheet(sheetTitle, 0);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("tang", "initExcel: --", e);
        }
    }


    @Deprecated
    public void saveDataToExcel(int index, String[] content) throws Exception {
        Label label;
        for (int i = 0; i < title.length; i++) {
            /**Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是y
             * 在Label对象的子对象中指明单元格的位置和内容
             * */
            label = new Label(i, 0, title[i]);
            /**将定义好的单元格添加到工作表中*/
            sheet.addCell(label);
        }
        /*
        * 把数据填充到单元格中
        * 需要使用jxl.write.Number
        * 路径必须使用其完整路径，否则会出现错误
        */
        for (int i = 0; i < title.length; i++) {
            Label labeli = new Label(i, index, content[i]);
            sheet.addCell(labeli);
        }
        // 写入数据
        wwb.write();
        // 关闭文件
        wwb.close();
    }




    /**
     * 写入content；使用默认title
     */
    public void saveDataToExcel(List<Record> content, Plan plan) throws Exception {
        Label label;
        for (int i = 0; i < title.length; i++) {
            /**Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是y
             * 在Label对象的子对象中指明单元格的位置和内容
             * */
            WritableFont font = new WritableFont(WritableFont.createFont("宋体"),
                    12, WritableFont.BOLD);// 字体样式
            WritableCellFormat wcf = new WritableCellFormat(font);
            //设置对齐方式为水平居中
            wcf.setAlignment(Alignment.CENTRE);
            //设置对齐方式为垂直居中
            wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
            label = new Label(i, 0, title[i], wcf);
            /**将定义好的单元格添加到工作表中*/

            sheet.addCell(label);
        }
        /*
        * 把数据填充到单元格中
        * 需要使用jxl.write.Number
        * 路径必须使用其完整路径，否则会出现错误
        */

        //使用record的checkdata
//        String checkdate = plan.getCheckDate().isEmpty() ? FormatUtils.formatNowTime() : plan.getCheckDate();

//        String checker = "工号：" + plan.getCheckerJobIndex1() + " 、 " + plan.getCheckerJobIndex2();

        for (int i = 0; i < content.size(); i++) {
            //从第一排开始，一个record一行
            Record record = content.get(i);
            //使用record的检查日期
            String checkdate = record.getCheckDate().isEmpty() ? FormatUtils.formatNowTime() : record.getCheckDate();


            //定义颜色
            WritableFont font = new WritableFont(WritableFont.createFont("宋体"),
                    10, WritableFont.NO_BOLD);// 字体样式
            WritableCellFormat wcf = new WritableCellFormat(font);
            //设置对齐方式为水平居中
            wcf.setAlignment(Alignment.CENTRE);
            //设置对齐方式为垂直居中
            wcf.setVerticalAlignment(VerticalAlignment.CENTRE);

            if (record.getTestIndex().equals("0")) {
                wcf.setBackground(Colour.BLUE_GREY);
            } else if (record.getTestIndex().equals("1")) {
                wcf.setBackground(Colour.DARK_YELLOW);
            } else {
                wcf.setBackground(Colour.GREY_50_PERCENT);
            }


            //定义第0行写入
            int index = 0;
            //第0行,第i+1列
            Label labeli = new Label(index, i + 1, record.getOrderSuffix() + record.getProduceIndex(), wcf);
            sheet.addCell(labeli);
            //第几次测试。要加1
            index++;
            labeli = new Label(index, i + 1, (Integer.parseInt(record.getTestIndex()) + 1) + "", wcf);
            sheet.addCell(labeli);

            index++;
            labeli = new Label(index, i + 1, record.getCheckValue0() + "", wcf);
            sheet.addCell(labeli);

            index++;
            labeli = new Label(index, i + 1, record.getCheckValue50() + "", wcf);
            sheet.addCell(labeli);

            index++;
            labeli = new Label(index, i + 1, record.getCheckValue100() + "", wcf);
            sheet.addCell(labeli);

            index++;
            labeli = new Label(index, i + 1, record.getCheckValue150() + "", wcf);
            sheet.addCell(labeli);

            index++;
            labeli = new Label(index, i + 1, record.getCheckValue200() + "", wcf);
            sheet.addCell(labeli);

            index++;
            labeli = new Label(index, i + 1, record.getCheckValue250() + "", wcf);
            sheet.addCell(labeli);

            //um10的 顺序是  300,280,300,280
            if (machineType.equals(HomeActivity.CREATE_UM_10 + "")) {
                index++;
                labeli = new Label(index, i + 1, record.getCheckValue300() + "", wcf);
                sheet.addCell(labeli);
            }

            index++;
            labeli = new Label(index, i + 1, record.getCheckValue280() + "", wcf);
            sheet.addCell(labeli);

            if (machineType.equals(HomeActivity.CREATE_UM_10)) {
                index++;
                labeli = new Label(index, i + 1, record.getCheckValue300_2() + "", wcf);
                sheet.addCell(labeli);
            }

            index++;
            labeli = new Label(index, i + 1, record.getCheckValue280_2() + "", wcf);
            sheet.addCell(labeli);

            index++;
            labeli = new Label(index, i + 1, record.getCheckValue250_2() + "", wcf);
            sheet.addCell(labeli);

            index++;
            labeli = new Label(index, i + 1, record.getCheckValue200_2() + "", wcf);
            sheet.addCell(labeli);

            index++;
            labeli = new Label(index, i + 1, record.getCheckValue150_2() + "", wcf);
            sheet.addCell(labeli);

            index++;
            labeli = new Label(index, i + 1, record.getCheckValue100_2() + "", wcf);
            sheet.addCell(labeli);

            index++;
            labeli = new Label(index, i + 1, record.getCheckValue50_2() + "", wcf);
            sheet.addCell(labeli);

            index++;
            labeli = new Label(index, i + 1, record.getCheckValue0_2() + "", wcf);
            sheet.addCell(labeli);

            index++;
            //修改checker的获取
            String checker = record.getCheckWorker();
            labeli = new Label(index, i + 1, checker, wcf);
            sheet.addCell(labeli);

            index++;
            labeli = new Label(index, i + 1, checkdate, wcf);
            sheet.addCell(labeli);
            
            //添加线别
            index++;
            //修复“因为没有保存课线与record而导致的问题”
            String line = plan.getProduceClass()+"课"+plan.getProduceLine()+"线";
            labeli = new Label(index, i + 1, line, wcf);
            sheet.addCell(labeli);
        }

        // 写入数据
        wwb.write();
        // 关闭文件
        wwb.close();
    }



}
