package com.baidu.android.voicedemo.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/11/20.
 * 主要功能，调用静态方法
 * 1 判断语音识别结果是否合法
 * 2
 */
public class FormatUtils {

    /**
     * 每组测试的个数：3个
     */
    public final static int CHECK_NUM_PER = 3 ;

    /**
     * 判断的原则:
     * 除了100以外，其他的每个结果的长度都应该是和被检测数的长度的三倍。
     * 100，可能是三个99，也可以是三个101;所以是6到9倍
     * @param checkProject  检查血压的那一项：0--250；step：50
     * @param resultStr 语音识别后的结果
     * @return
     */
    public static boolean judgeResultIsValid(String checkProject,int machineNum,String resultStr){
        boolean result = false;
        int resultLen = resultStr.length();
        int checkLen = checkProject.length();
        if(checkProject.equals("100")){
            //
            if(resultLen >= (checkLen-1)*machineNum && resultLen <= checkLen*machineNum){
                result = true;
            }else{
                result = false;
            }
        }else{
            if(resultLen == checkLen*machineNum){
                result = true;
            }else{
                result = false;
            }
        }

        return result;

    }

    /**
     * 判断手动输入的值是否OK
     * true，ok ； false ， 错误
     * @param value
     * @return
     */
    public static boolean handleInput(float value){
        if(Math.abs(value) <= StaticUtils.MAX_RAGE){
            return true;
        }else{
            return false;
        }
    }
    public static boolean handleInput(String value){
        float result = - 99;
        try {
            result = Float.parseFloat(value);
        } catch (Exception e) {
            e.printStackTrace();
            result = -99;
        }
        if(Math.abs(result) <= StaticUtils.MAX_RAGE){
            return true;
        }else{
            return false;
        }
    }

    public static boolean handleInput(float[] values){
        boolean inputvalid = true;
        int len = values.length;
        for (int i = 0; i < len; i++) {
            inputvalid = FormatUtils.handleInput(values[i]);
            if(!inputvalid){
                return false;
            }
        }
        return true;
    }


    /**
     * 比较判断，checkData和resultStr
     * @param checkData
     * @param resultStr
     * @return -1 成功 ； 0 ，1,2 失败的那一组
     */
    public static List<String> judgeResultAllSuccess(String checkData,List<String> resultStr){
        int check = Integer.parseInt(checkData);
            List<String> result =  new ArrayList<>();
            for(int i = 0 ; i < resultStr.size();i++){
                try{
                    int tmp = Integer.parseInt(resultStr.get(i));
                    if(Math.abs(tmp-check)> StaticUtils.MAX_RAGE){
                        result.add(String.valueOf(i));
                    }
                }catch (NumberFormatException e){
                    e.printStackTrace();
                    //外面会处理为有残品
                    result.add(String.valueOf(404));

                }

            }

        return result;

    }
    public static boolean judgeResultInValid(String checkProject,String resultStr){
        int resultDig = -1 ;
        int checkdig = -1;
        try{
            resultDig = Integer.parseInt(resultStr);
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            checkdig = Integer.parseInt(checkProject);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(resultDig!=-1 && checkdig!=-1 && Math.abs(resultDig-checkdig) <= 3){
            return true;
        }else{
            return false;
        }

    }


    /**
     * 将混一的结果分割成CHECK_NUM_PER个
     * 是100的时候，首字是1，则截三位；否则截两位。
     * 其他情况，按checkProject的长度截数。
     * @param checkProject  检查血压的那一项：0--250；step：50
     * @param resultStr 语音识别后的结果
     * @return
     */
    public static List<String> getRealResult(String checkProject,int checkMachineNum, String resultStr){
        if(!judgeResultIsValid(checkProject,checkMachineNum,resultStr)){
            System.out.println("result ["+resultStr+"] 不合法; 与检查血压 "+checkProject+" 的范围不匹配");
            return null;
        }
        int resultLen = resultStr.length();
        int checkLen = checkProject.length();
        List<String>  result = new ArrayList<>();
        if(checkProject.equals("100")){
            int len = 0 ;
            while((len+1) < resultLen){
                System.out.println("char["+(len)+"]=="+resultStr.charAt(len));
                if(resultStr.charAt(len)=='1'){
                    String text = resultStr.substring(len,len+checkLen);
                    result.add(text);
                    System.out.println(len+"到:"+(len+checkLen-1)+" : "+text);
                    len += checkLen;
                }else{
                    String text = resultStr.substring(len,len+checkLen-1);
                    result.add(text);
                    System.out.println(len+"到:"+(len+checkLen-2)+" : "+text);
                    len += checkLen-1;
                }
            }
        }else{
            int len = 0 ;
            while((len+1) < resultLen){
                String text = resultStr.substring(len,len+checkLen);
                result.add(text);
                System.out.println(len+"到"+(len+checkLen-1)+" : "+text);
                len += checkLen;

            }
        }


        return  result;
    }

    public static String formatNowTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }


}
