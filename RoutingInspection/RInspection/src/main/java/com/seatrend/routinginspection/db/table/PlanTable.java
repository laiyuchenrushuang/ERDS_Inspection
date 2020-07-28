package com.seatrend.routinginspection.db.table;

import org.greenrobot.greendao.annotation.*;

import java.util.List;
import org.greenrobot.greendao.DaoException;
import com.greendao.gen.DaoSession;
import com.greendao.gen.PictureTableDao;
import com.greendao.gen.PlanTableDao;
import com.greendao.gen.JudgeTableDao;

/**
 * Created by ly on 2020/7/7 13:14
 */
@Entity
public class PlanTable extends BaseTable{
    @Id
    private Long id;         //planid

    @Index(unique = true)
    private String lsh  = "";     //巡站流水 jhbh

    //    private String jhbh;
    private String xzlx = ""; //巡站类型
    private String jhzt = ""; //计划状态 2已经完成
    private Long xzrq = null; //巡站日期
    private int ROW_ID ; //id
    private String bmmc = ""; //部门名称
    private String glbm = ""; //管理部门编号
    private String userdh = ""; //用户代号

    private String jhbz=""; //计划备注

    @ToMany(joinProperties = {@JoinProperty(name = "lsh", referencedName = "lsh")})
    List<JudgeTable> judgeTableList;


    @ToMany(joinProperties = {@JoinProperty(name = "lsh", referencedName = "lsh")})
    List<PictureTable> pictureTableList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 887964398)
    private transient PlanTableDao myDao;


    @Generated(hash = 470769906)
    public PlanTable(Long id, String lsh, String xzlx, String jhzt, Long xzrq, int ROW_ID, String bmmc,
            String glbm, String userdh, String jhbz) {
        this.id = id;
        this.lsh = lsh;
        this.xzlx = xzlx;
        this.jhzt = jhzt;
        this.xzrq = xzrq;
        this.ROW_ID = ROW_ID;
        this.bmmc = bmmc;
        this.glbm = glbm;
        this.userdh = userdh;
        this.jhbz = jhbz;
    }


    @Generated(hash = 1636733453)
    public PlanTable() {
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getLsh() {
        return this.lsh;
    }


    public void setLsh(String lsh) {
        this.lsh = lsh;
    }


    public String getXzlx() {
        return this.xzlx;
    }


    public void setXzlx(String xzlx) {
        this.xzlx = xzlx;
    }


    public String getJhzt() {
        return this.jhzt;
    }


    public void setJhzt(String jhzt) {
        this.jhzt = jhzt;
    }


    public Long getXzrq() {
        return this.xzrq;
    }


    public void setXzrq(Long xzrq) {
        this.xzrq = xzrq;
    }


    public int getROW_ID() {
        return this.ROW_ID;
    }


    public void setROW_ID(int ROW_ID) {
        this.ROW_ID = ROW_ID;
    }


    public String getBmmc() {
        return this.bmmc;
    }


    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }


    public String getGlbm() {
        return this.glbm;
    }


    public void setGlbm(String glbm) {
        this.glbm = glbm;
    }


    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 164528902)
    public List<JudgeTable> getJudgeTableList() {
        if (judgeTableList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            JudgeTableDao targetDao = daoSession.getJudgeTableDao();
            List<JudgeTable> judgeTableListNew = targetDao
                    ._queryPlanTable_JudgeTableList(lsh);
            synchronized (this) {
                if (judgeTableList == null) {
                    judgeTableList = judgeTableListNew;
                }
            }
        }
        return judgeTableList;
    }


    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 937721855)
    public synchronized void resetJudgeTableList() {
        judgeTableList = null;
    }


    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 2134858310)
    public List<PictureTable> getPictureTableList() {
        if (pictureTableList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PictureTableDao targetDao = daoSession.getPictureTableDao();
            List<PictureTable> pictureTableListNew = targetDao
                    ._queryPlanTable_PictureTableList(lsh);
            synchronized (this) {
                if (pictureTableList == null) {
                    pictureTableList = pictureTableListNew;
                }
            }
        }
        return pictureTableList;
    }


    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 2110042553)
    public synchronized void resetPictureTableList() {
        pictureTableList = null;
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }


    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 374138245)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPlanTableDao() : null;
    }


    public String getJhbz() {
        return this.jhbz;
    }


    public void setJhbz(String jhbz) {
        this.jhbz = jhbz;
    }


    public String getUserdh() {
        return this.userdh;
    }


    public void setUserdh(String userdh) {
        this.userdh = userdh;
    }
}
