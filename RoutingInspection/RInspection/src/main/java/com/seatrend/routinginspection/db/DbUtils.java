package com.seatrend.routinginspection.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import com.greendao.gen.*;
import com.seatrend.routinginspection.db.table.BaseTable;
import com.seatrend.routinginspection.db.table.JudgeTable;
import com.seatrend.routinginspection.db.table.PictureTable;
import com.seatrend.routinginspection.db.table.PlanTable;
import com.seatrend.routinginspection.entity.PhotoOnlineDataBean;
import com.seatrend.routinginspection.utils.GsonUtils;

import java.util.List;

import static com.seatrend.routinginspection.db.DbUtils.TABLE.*;

/**
 * Created by ly on 2020/7/7 14:03
 */
public class DbUtils {


    enum TABLE {
        PICTURETABLE("PictureTable"),
        JUDGETABLE("JudgeTable"),
        PLANTABLE("PlanTable"),
        PHOTOONLINEDATABEAN("PHOTOONLINEDATABEAN")//计划列表(在线)
        ;

        private String name;

        TABLE(String name) {

            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

    /**
     * 数据库名字
     */
    private String DB_NAME = "plan.db";  //数据库名字
    /**
     * Helper
     */
    private DaoMaster.DevOpenHelper mHelper;//获取Helper对象
    /**
     * 数据库
     */
    private SQLiteDatabase db;
    /**
     * DaoMaster
     */
    private DaoMaster mDaoMaster;
    /**
     * DaoSession
     */
    private DaoSession mDaoSession;
    /**
     * 上下文
     */
    private Context context;

    private JudgeTableDao judgeTableDao;  //判定
    private PictureTableDao pictureTableDao; //图片
    private PlanTableDao planTableDao; //计划列表
    private PhotoOnlineDataBeanDao photoOnlineDataBeanDao; //计划列表(在线)


    private static DbUtils mDbController;

    public DbUtils(Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        mDaoMaster = new DaoMaster(getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
        judgeTableDao = mDaoSession.getJudgeTableDao();
        pictureTableDao = mDaoSession.getPictureTableDao();
        planTableDao = mDaoSession.getPlanTableDao();
        photoOnlineDataBeanDao = mDaoSession.getPhotoOnlineDataBeanDao();
    }

    /**
     * 获取可写数据库
     *
     * @return
     */
    private SQLiteDatabase getWritableDatabase() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db;
    }


    /**
     * 获取单例（context 最好用application的context  防止内存泄漏）
     */
    public static DbUtils getInstance(Context context) {
        if (mDbController == null) {
            synchronized (DbUtils.class) {
                if (mDbController == null) {
                    mDbController = new DbUtils(context);
                }
            }
        }
        return mDbController;
    }


    /**
     * ====================================增加================================================
     * ====================================insert==============================================
     */

    /**
     * 插入一条记录，表里面要没有与之相同的记录
     *
     * @param baseTable
     */
    public void insert(BaseTable baseTable) {
        if (baseTable instanceof PlanTable) {
            planTableDao.insert((PlanTable) baseTable);
        }
        if (baseTable instanceof JudgeTable) {
            judgeTableDao.insert((JudgeTable) baseTable);
        }
        if (baseTable instanceof PictureTable) {
            pictureTableDao.insert((PictureTable) baseTable);
        }
    }

    /**
     * 【在线】模式的增加bean
     *
     * @param bean
     */
    public void insert(PhotoOnlineDataBean bean) {
        photoOnlineDataBeanDao.insertOrReplace(bean);
    }


    /**
     * ====================================删除================================================
     * ====================================delete===============================================
     * delete特定项
     *
     * @param lsh    计划流水
     * @param tClass 对应表的类名
     */
    public <T> void delete(String lsh, Class<T> tClass) {
        if (PICTURETABLE.name.equals(tClass.getSimpleName())) {
            pictureTableDao.queryBuilder().where(PictureTableDao.Properties.Lsh.eq(lsh)).buildDelete().executeDeleteWithoutDetachingEntities();
        } else if (JUDGETABLE.name.equals(tClass.getSimpleName())) {
            judgeTableDao.queryBuilder().where(JudgeTableDao.Properties.Lsh.eq(lsh)).buildDelete().executeDeleteWithoutDetachingEntities();
        } else if (PLANTABLE.name.equals(tClass.getSimpleName())) {
            planTableDao.queryBuilder().where(PlanTableDao.Properties.Lsh.eq(lsh)).buildDelete().executeDeleteWithoutDetachingEntities();
        }
    }

    /**
     * 删除计划任务的某一张照片
     *
     * @param lsh
     * @param zpPath 唯一的
     */
    public void deletePicture(String lsh, String zpPath) {
        if ("".equals(lsh)) {
            pictureTableDao.queryBuilder().where(PictureTableDao.Properties.ZpPath.eq(zpPath)).buildDelete().executeDeleteWithoutDetachingEntities();
        } else {
            pictureTableDao.queryBuilder().where(PictureTableDao.Properties.Lsh.eq(lsh), PictureTableDao.Properties.ZpPath.eq(zpPath)).buildDelete().executeDeleteWithoutDetachingEntities();
        }
    }

    /**
     * 【离线线删除某个bean】
     *
     * @param id
     */
    public void deletePhotoBean(Long id) {
        pictureTableDao.queryBuilder().where(PictureTableDao.Properties.Lsh.eq(id)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    /**
     * 【在线删除某个bean】
     *
     * @param id
     */
    public void deletePhotoOnlineBean(Long id) {
        photoOnlineDataBeanDao.queryBuilder().where(PhotoOnlineDataBeanDao.Properties.Id.eq(id)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    /**
     * 【在线删除某个bean】
     *
     * @param jhbh
     * @param zpPath
     */
    public void deletePhotoOnlineBean(String jhbh, String zpPath) {
        photoOnlineDataBeanDao.queryBuilder().where(PhotoOnlineDataBeanDao.Properties.Jhbh.eq(jhbh), PhotoOnlineDataBeanDao.Properties.ZpPath.eq(zpPath)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    /**
     * [离线]删除某个bean
     *
     * @param jhbh
     * @param zpPath
     */
    public void deletePictureBean(String jhbh, String zpPath) {
        pictureTableDao.queryBuilder().where(PictureTableDao.Properties.Lsh.eq(jhbh), PictureTableDao.Properties.ZpPath.eq(zpPath)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    /**
     * 删除对应表所有数据
     */
    public <T> void deleteAll(Class<T> tClass) {
        if (PICTURETABLE.name.equals(tClass.getSimpleName())) {
            pictureTableDao.deleteAll();
        } else if (JUDGETABLE.name.equals(tClass.getSimpleName())) {
            judgeTableDao.deleteAll();
        } else if (PLANTABLE.name.equals(tClass.getSimpleName())) {
            planTableDao.deleteAll();
        }
    }

    /**
     * 离线删除plan judge piture的数据
     *
     * @param lsh
     */

    public void deleteDbPlanJudgePicture(String lsh) {
        //删除plan
        planTableDao.queryBuilder().where(PlanTableDao.Properties.Lsh.eq(lsh)).buildDelete().executeDeleteWithoutDetachingEntities();
        //删除judge
        judgeTableDao.queryBuilder().where(JudgeTableDao.Properties.Lsh.eq(lsh)).buildDelete().executeDeleteWithoutDetachingEntities();
        //删除picture
        pictureTableDao.queryBuilder().where(PictureTableDao.Properties.Lsh.eq(lsh)).buildDelete().executeDeleteWithoutDetachingEntities();
    }


    /**
     * 离线删除plan judge 的数据  上传判定成功删除掉数据防止查看
     *
     * @param lsh
     */

    public void deleteDbPlanJudge(String lsh) {
        //删除plan
        planTableDao.queryBuilder().where(PlanTableDao.Properties.Lsh.eq(lsh)).buildDelete().executeDeleteWithoutDetachingEntities();
        //删除judge
        judgeTableDao.queryBuilder().where(JudgeTableDao.Properties.Lsh.eq(lsh)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    /**
     * 删除所有数据
     */

    public void deleteAll() {
        pictureTableDao.deleteAll();
        judgeTableDao.deleteAll();
        planTableDao.deleteAll();
    }

    /**
     * 删除对应的照片bean内容【在线】
     *
     * @param lsh
     * @param zpPath
     */
    public void delete(String lsh, String zpPath) {
        pictureTableDao.queryBuilder().where(PictureTableDao.Properties.Lsh.eq(lsh), PictureTableDao.Properties.ZpPath.eq(zpPath)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    /**
     * ====================================更新================================================
     * ====================================update===============================================
     * <p>
     * 更新数据
     *
     * @param baseTable
     */
    public void update(BaseTable baseTable) {
        if (baseTable instanceof PlanTable) {
            PlanTable planTable = (PlanTable) baseTable;
            PlanTable mOldPlanTable = planTableDao.queryBuilder().where(PlanTableDao.Properties.Lsh.eq(planTable.getLsh())).build().unique();//拿到之前的记录

            Log.d("lylog", " mOldPlanTable = " + GsonUtils.toJson(mOldPlanTable));
            if (mOldPlanTable != null) {

                //计划状态
                if (!"".equals(planTable.getJhzt()) && planTable.getJhzt() != null) {
                    mOldPlanTable.setJhzt(planTable.getJhzt());   //保存计划状态
                }
                //计划备注
                if (!"".equals(planTable.getJhbz()) && planTable.getJhbz() != null) {
                    mOldPlanTable.setJhbz(planTable.getJhbz());   //保存计划状态
                }

                planTableDao.update(mOldPlanTable);
            }
        }
    }

    /**
     * 修改判定结果
     *
     * @param jhbh
     * @param rwbh
     * @param rwzxjg
     */
    public void updateJudge(String jhbh, String rwbh, String rwzxjg) {
        JudgeTable mOldJudgeTable = judgeTableDao.queryBuilder().where(JudgeTableDao.Properties.Lsh.eq(jhbh), JudgeTableDao.Properties.Rwbh.eq(rwbh)).build().unique();//拿到之前的记录
        if (mOldJudgeTable != null) {
            mOldJudgeTable.setRwzxjg(rwzxjg);
            judgeTableDao.update(mOldJudgeTable);
        }
    }


    /**
     * 【离线】 更新表的zpdz字段
     *
     * @param jhbh
     * @param zpPath
     * @param zpdz
     */
    public void updatePictureBean(String jhbh, String zpPath, String zpdz) {
        PictureTable mOldPhotoOnlineDataBean = pictureTableDao.queryBuilder().where(PictureTableDao.Properties.Lsh.eq(jhbh), PictureTableDao.Properties.ZpPath.eq(zpPath)).build().unique();//拿到之前的记录

        if (mOldPhotoOnlineDataBean != null) {
            mOldPhotoOnlineDataBean.setZpId(zpdz);
            pictureTableDao.update(mOldPhotoOnlineDataBean);
        }
    }

    /**
     * 【在线】 更新表的zpdz字段
     *
     * @param jhbh
     * @param zpPath
     * @param zpdz
     */
    public void updatePhotoOnlineBean(String jhbh, String zpPath, String zpdz) {
        PhotoOnlineDataBean mOldPhotoOnlineDataBean = photoOnlineDataBeanDao.queryBuilder().where(PhotoOnlineDataBeanDao.Properties.Jhbh.eq(jhbh), PhotoOnlineDataBeanDao.Properties.ZpPath.eq(zpPath)).build().unique();//拿到之前的记录

        if (mOldPhotoOnlineDataBean != null) {
            mOldPhotoOnlineDataBean.setZpdz(zpdz);
            photoOnlineDataBeanDao.update(mOldPhotoOnlineDataBean);
        }
    }

    /**
     * ====================================查询================================================
     * ====================================search==============================================
     * 查询所有数据
     */
    public List<PlanTable> searchPlanTableAll() {
        List<PlanTable> planTables = planTableDao.queryBuilder().list();
//        List<PlanTable> planTables = planTableDao.loadAll();
        return planTables;
    }

    /**
     * 查询所有数据(做完业务的)
     */
    public List<PlanTable> searchPlanTableAllIsDone() {
        //删除掉没有执行完成的数据
        List<PlanTable> planTables = planTableDao.queryBuilder().list();
        if(planTables.size() > 0){
            for (int i =0;i<planTables.size();i++){
                if(!"2".equals(planTables.get(i).getJhzt())){  //1代表已经完成
                    planTables.remove(planTables.get(i));
                }
            }
        }
        return planTables;
    }

    /**
     * 条件查询判定项[judge]
     *
     * @return
     */
    public List<JudgeTable> searchJudgeTableAll(String lsh) {
        List<JudgeTable> judgeTables = judgeTableDao.queryBuilder().where(PlanTableDao.Properties.Lsh.eq(lsh)).build().list();
        return judgeTables;
    }

    /**
     * 按条件查询数据[plan]
     */
    public List<PlanTable> searchPlanByWhere(String lsh) {
        List<PlanTable> planTables = planTableDao.queryBuilder().where(PlanTableDao.Properties.Lsh.eq(lsh)).build().list();
        return planTables;
    }

    /**
     * 按条件查询数据[plan]
     */
    public PlanTable searchPlanByLsh(String lsh) {
        PlanTable planTables = planTableDao.queryBuilder().where(PlanTableDao.Properties.Lsh.eq(lsh)).build().unique();
        return planTables;
    }

    /**
     * 按条件查询数据[plan] 是否存在
     */
    public boolean searchPlanByLshHas(String lsh) {
        PlanTable planTables = planTableDao.queryBuilder().where(PlanTableDao.Properties.Lsh.eq(lsh)).build().unique();
        if (planTables != null) return true;
        return false;
    }
    /**
     * 按条件查询数据[plan] 是否存在,并且已经完成了  执行完成 为2  就不能删除
     */
    public boolean searchPlanByLshHadDone(String lsh) {
        PlanTable planTables = planTableDao.queryBuilder().where(PlanTableDao.Properties.Lsh.eq(lsh)).build().unique();
        if (planTables != null) {
            if("2".equals(planTables.getJhzt())){
                return true;
            }
        }
        return false;
    }


    /**
     * 按条件查询数据是否已经完成[plan]
     * <p>
     * 1 代表完成了的
     */
    public boolean searchPlanByLshIsDone(String lsh) {
        PlanTable planTables = planTableDao.queryBuilder().where(PlanTableDao.Properties.Lsh.eq(lsh)).build().unique();
        if (planTables != null) {
            if ("1".equals(planTables.getJhzt())) {
                return true;
            }

        }
        return false;
    }

    /**
     * 查看照片集合
     *
     * @param lsh
     * @return
     */
    public List<PictureTable> searchPictureTableAll(String lsh) {
        List<PictureTable> pictureTables = pictureTableDao.queryBuilder().where(PictureTableDao.Properties.Lsh.eq(lsh)).build().list();
        return pictureTables;
    }

    /**
     * 查看照片集合
     *
     * @param
     * @return
     */
    public List<PictureTable> searchPictureTableAll() {
        List<PictureTable> pictureTables = pictureTableDao.queryBuilder().list();
        return pictureTables;
    }

    /**
     * 【在线】查看照片集合
     *
     * @return
     */
    public List<PhotoOnlineDataBean> searchPhotoOnlineDataBeanAll() {
        List<PhotoOnlineDataBean> pictureTables = photoOnlineDataBeanDao.queryBuilder().list();
        return pictureTables;
    }


    /**
     * 条件查询判定条目
     *
     * @param lsh  计划流水号
     * @param rwbh 任务编号
     * @return
     */

    public JudgeTable searchJudgeByWhere(String lsh, String rwbh) {
        JudgeTable judgeTables = judgeTableDao.queryBuilder().where(JudgeTableDao.Properties.Lsh.eq(lsh), JudgeTableDao.Properties.Rwbh.eq(rwbh)).build().unique();
        return judgeTables;
    }


    /**
     * 条件查询判定条目
     *
     * @param lsh 计划流水号
     * @return 返回集合
     */

    public List<JudgeTable> searchJudgeByWhere(String lsh) {
        List<JudgeTable> judgeTables = judgeTableDao.queryBuilder().where(JudgeTableDao.Properties.Lsh.eq(lsh)).build().list();
        return judgeTables;
    }

    /**
     * 【在线】 查询bean
     *
     * @param jhbh
     * @param zpPath
     * @return true 有照片地址
     */
    public boolean searchPhototOnlineBean(String jhbh, String zpPath) {
        PhotoOnlineDataBean photoOnlineDataBean = photoOnlineDataBeanDao.queryBuilder().where(PhotoOnlineDataBeanDao.Properties.Jhbh.eq(jhbh), PhotoOnlineDataBeanDao.Properties.ZpPath.eq(zpPath)).build().unique();

        if (photoOnlineDataBean != null) {
            if (TextUtils.isEmpty(photoOnlineDataBean.getZpdz()) || "".equals(photoOnlineDataBean.getZpdz()) || null == photoOnlineDataBean.getZpdz()) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
