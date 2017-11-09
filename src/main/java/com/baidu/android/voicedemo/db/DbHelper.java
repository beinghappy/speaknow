package com.baidu.android.voicedemo.db;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.android.voicedemo.bean.Plan;
import com.baidu.android.voicedemo.bean.Record;
import com.baidu.android.voicedemo.bean.UserInfo;
import com.baidu.android.voicedemo.utils.FileUtils;
import com.baidu.android.voicedemo.utils.StaticUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import java.util.List;

import static com.baidu.speech.recognizerdemo.BuildConfig.isUM;

/**
 * Created by Administrator on 2016/11/19.
 */
public class DbHelper implements DbUtils.DbUpgradeListener {

//    private static DbManager db;

    private static DbUtils db;

    public static void initDb(Context context) {
        //本地数据的初始化
        String dir = FileUtils.getDBDir();
        Log.e("tag", "create db on " + dir);
        if(isUM){
            db = DbUtils.create(context, dir, StaticUtils.UM_DB_NAME);
        }else{
            db = DbUtils.create(context, dir, StaticUtils.DB_NAME);
        }
    }

    private static DbHelper dbHelper;

    public static DbHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DbHelper();
            initDb(context);
        }

        return dbHelper;
    }

    private DbHelper() {

    }

    /**
     * 新建一个计划
     *
     * @param plan
     * @return
     */
    public static final int EXSIT = 1;
    public static final int ERROR = 2;
    public static final int SUCCESS = 3;

    public int addPlan(Plan plan) {
        try {
            Plan exsitPlan = db.findFirst(Plan.class, WhereBuilder.b().and("orderId", "==", plan.getOrderId()));
            if (exsitPlan != null) {
                //存在orderId一致的Plan
                return EXSIT;
            } else {
                db.saveBindingId(plan);
                return SUCCESS;
            }
        } catch (DbException e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    public boolean deletePlan(Plan plan) {
        try {
            db.delete(plan);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除与改计划相关的所有具体测试的记录，如果有的话
     *
     * @param plan
     * @return
     */
    public boolean deleteRecord(Plan plan) {
        try {
            db.delete(Record.class, WhereBuilder.b("orderId", "==", plan.getOrderId()));
            return true;
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePlan(Plan plan) {
        try {
            db.saveOrUpdate(plan);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updatePlanByOrderId(String orderId) {
        try {
            Plan plan = db.findFirst(Plan.class,WhereBuilder.b().and("orderId","==",orderId));
            if(plan!=null){
                plan.setDone(true);
                db.saveOrUpdate(plan);
                return true;
            }else{
                return false;
            }
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取已完成的计划
     *
     * @return
     * @throws DbException
     */
    public List<Plan> getAllDonePlan() {
        try {
            return db.findAll(Selector.from(Plan.class).where("done", "==", true)
            );
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取未完成的计划
     *
     * @return
     * @throws DbException
     */
    public List<Plan> getAllUndonePlan() {
        try {
            return db.findAll(Selector.from(Plan.class).where("done", "==", false));
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 如果有多个fail的record，则取index为0的，即第一个
     * @return
     */
    public List<Record> getFailAndTailRecord() {
        try {
            //update by long.tang for "2017-7-13 第一条"
            //只获取不合格的；不管是done为true（输入不合格），还是done为false（尾数直接）//不合格的done也为false
            return db.findAll(Selector.from(Record.class).where("result", "==", Record.result_fail)
//            return db.findAll(Selector.from(Record.class).where("done", "==", false)
            );
            //update by long.tang for "2017-7-13 第一条"
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<Record> getAllFailRecordById(String orderid,String produceIndex) {
        try {
            //update by long.tang for "2017-7-13 第一条"
            //只获取不合格的；不管是done为true（输入不合格），还是done为false（尾数直接）//不合格的done也为false
//            return db.findAll(Selector.from(Record.class).where("orderid", "==", orderid)
            return db.findAll(Selector.from(Record.class).where("orderid", "==", orderid)
                    .and("produceIndex", "==", produceIndex)
            );
            //update by long.tang for "2017-7-13 第一条"
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 这个orderId是否还有残次品未处理
     * @param orderId
     * @return
     */
    public boolean hasFailRecord(String orderId) {
        try {
            List<Record> list = db.findAll(Selector.from(Record.class).where("result", "!=", Record.result_ok)
                    .and("orderId", "==", orderId));

            if(list!=null && list.size()>0){
                return true;
            }else{
                return false;
            }

        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 所有未测试的初始record
     * 需要按组分类：每一组如果有一个false，那么这一组都需要被重新测试。
     * @param orderId
     * @return
     */
    public List<Record> getAllInitRecord(String orderId) {
        try {
            if (TextUtils.isEmpty(orderId)) {
                return db.findAll(Record.class);
            }
            return db.findAll(Selector.from(Record.class).where("orderId", "==", orderId)
                    .and("result", "==", Record.result_init));
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Record> getAllInitExceptTailRecord(String orderId) {
        try {
            if (TextUtils.isEmpty(orderId)) {
                return db.findAll(Record.class);
            }
            return db.findAll(Selector.from(Record.class).where("orderId", "==", orderId)
                    .and("result", "==", Record.result_init)
                    .and("isTail", "==", false)
            );
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Record> getAllDoneRecord(String orderId) {
        try {

            if (TextUtils.isEmpty(orderId)) {
                return db.findAll(Record.class);
            }
//            return db.findAll(Selector.from(Record.class).where("orderId", "==", orderId).and("done", "==", true).and("extend", "==", true).orderBy("produceIndex"));
            //update for "完成的数据都要导出，不管是不是合格。"
            return db.findAll(Selector.from(Record.class).where("orderId", "==", orderId).and("done", "==", true).orderBy("produceIndex"));
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Record> getAllDoneRecord2(String orderId) {
        try {

            if (TextUtils.isEmpty(orderId)) {
                return db.findAll(Record.class);
            }
//            return db.findAll(Selector.from(Record.class).where("orderId", "==", orderId).and("done", "==", true).and("extend", "==", true));
            //update for "测试不合格的也要显示"
            return db.findAll(Selector.from(Record.class).where("orderId", "==", orderId).and("done", "==", true));
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 是否覆盖就的record；
     * 只有当testIndex=1时，为false
     * @param list
     * @param updateOld
     * @return
     */
    public boolean addRecordList(List<Record> list, boolean updateOld) {
        try {
            if (updateOld) {
                db.saveOrUpdateAll(list);
            } else {
                db.saveBindingIdAll(list);
            }
            return true;
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean removeRecordList(List<Record> list) {
        try {
            db.deleteAll(list);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新，或添加Record
     *
     * @param record
     * @return
     */
    public boolean updateRecord(Record record) {
        try {
            db.saveOrUpdate(record);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRecord(List<Record> list) {
        try {
            db.saveOrUpdateAll(list);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * @param mUserInfo
     * @return
     */
    public boolean addUserInfo(UserInfo mUserInfo) {
        try {
            return db.saveBindingId(mUserInfo);
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateUserInfo(UserInfo mUserInfo) {
        try {
            db.saveOrUpdate(mUserInfo);
            return true;
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public List<UserInfo> getUserInfos() {
        try {
            return db.findAll(UserInfo.class,WhereBuilder.b().and("roleType","==",StaticUtils.Role_User));
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param mInfo
     * @return
     */
    public UserInfo checkUserValidate(UserInfo mInfo){
        try {

            UserInfo userInfo = db.findFirst(UserInfo.class,WhereBuilder.b().and("username","==",mInfo.getUsername())
                    .and("password","==",mInfo.getPassword()));
            return userInfo;
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public void deleteUserInfoByName(String username) {
        try {
            WhereBuilder mwhere = WhereBuilder.b("username", "=",
                    username);
            db.delete(UserInfo.class, mwhere);
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean deleteUserInfo(List<UserInfo> list) {
        try {
            db.deleteAll(list);
            return true;
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public void onUpgrade(DbUtils dbUtils, int oldVersion,int newVersion) {

    }
}
