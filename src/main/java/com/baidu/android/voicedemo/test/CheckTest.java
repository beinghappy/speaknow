package com.baidu.android.voicedemo.test;

import com.baidu.android.voicedemo.utils.FormatUtils;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by Administrator on 2016/11/20.
 */
public class CheckTest extends TestCase{

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testGetRealResult() {

        String checkProject = "100";
        String checkProject1 = "150";
        String resultStr = "9810199";
        String resultStr1 = "148149151149";

//        List<String> test = FormatUtils.getRealResult(checkProject,resultStr);
        List<String> test1 = FormatUtils.getRealResult(checkProject1,4,resultStr1);

//        print(test);
        print(test1);


    }




    public void testTry(){
        

    }
    public void testWriteExcel(){

        float f = 1.0f;
        float w = 0f;

        float result = (f + w )/2 ;
        System.out.println("result == "+result);

    }

    public void testFormatDate(){
        String date = FormatUtils.formatNowTime();
        System.out.println(date);
    }

    public void testBuild(){
        int produceNumber = 30;
        int workPosition = 3;
        int machinePer = 3;
        int currentPosition = 1 ;
        int beginIndex = 1 ;

        int startNo = (currentPosition - 1) * machinePer + beginIndex ;

        do{
            for (int j = 0; j < machinePer && (startNo+j < produceNumber+beginIndex) ; j++) {
                if(startNo + machinePer<=produceNumber+beginIndex){
                    String text = String.format("%06d", startNo + j);
                    System.out.println("index==" + text);
                }else{
                    String text = String.format("%06d", startNo + j);
                    System.out.println("尾数  index==" + text);
                }

            }
            startNo = startNo + workPosition * machinePer;


//        }
        }while(startNo < produceNumber + beginIndex );
    }

    public void testSomeMath(){
        int testiindex = 2 ;
        for(int i = 0 ; i < 2 ; i ++){
            System.out.println("index==" + i%2);
        }
    }

    public void testBuildLevel(){
        int produceNumber = 30;//多少个产品
        int workNum = 2;//有几个工位
        int machinePer = 4;//每个工位每次几台机:3
        int myWorkPosition = 1 ;//自己的工位
        int mystartNo = (myWorkPosition - 1) * machinePer ;

        int level = produceNumber / machinePer;//分多少组
            do{
                nextRow(mystartNo,machinePer*workNum,produceNumber);
                mystartNo = mystartNo + workNum * machinePer;
            }while(mystartNo < produceNumber );





    }

    private void nextRow(int start,int checkNumOneMachine,int total){
        if (start + checkNumOneMachine <= total) {
            //如果还有下一组的话
            for (int i = 0; i < checkNumOneMachine; i++) {
                String text = String.format("%06d", start + i);
                System.out.println("index==" + text);
            }
        } else {
            //多若干个
            for (int i = start; i < total ; i++) {
                //保存为尾货
                String text = String.format("%06d", start + i);
                System.out.println("尾货 index ==" + text);
            }

        }


    }


    public void testAvarage(){
        float f1 = 1.0f;
        float f2 = -2.0f;
        float f3 = (f1+f2)/2;
        System.out.println("f3==" + f3);
    }


    private void print(List<String> list){
        if(list!=null && list.size()>0){
            for(String text : list){
                System.out.println(text);
            }
        }else{
            System.out.println("list is null");
        }
    }
}
