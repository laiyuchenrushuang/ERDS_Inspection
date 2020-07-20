package com.seatrend.routinginspection.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ly on 2020/7/9 9:44
 */
@Entity
public class PhotoOnlineDataBean {
    @Id
    private Long id;

    private String jhbh="";//计划编号
    private String rwbh ="";//任务编号编号  [没用 不管了]
    private String glbm  ="";//管理部门
    private String zpdz   ="";//照片地址

    @Index(unique = true)
    private String zpPath   ;//照片路径

    @Generated(hash = 156681277)
    public PhotoOnlineDataBean(Long id, String jhbh, String rwbh, String glbm,
            String zpdz, String zpPath) {
        this.id = id;
        this.jhbh = jhbh;
        this.rwbh = rwbh;
        this.glbm = glbm;
        this.zpdz = zpdz;
        this.zpPath = zpPath;
    }

    @Generated(hash = 1510262786)
    public PhotoOnlineDataBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJhbh() {
        return this.jhbh;
    }

    public void setJhbh(String jhbh) {
        this.jhbh = jhbh;
    }

    public String getRwbh() {
        return this.rwbh;
    }

    public void setRwbh(String rwbh) {
        this.rwbh = rwbh;
    }

    public String getGlbm() {
        return this.glbm;
    }

    public void setGlbm(String glbm) {
        this.glbm = glbm;
    }

    public String getZpdz() {
        return this.zpdz;
    }

    public void setZpdz(String zpdz) {
        this.zpdz = zpdz;
    }

    public String getZpPath() {
        return this.zpPath;
    }

    public void setZpPath(String zpPath) {
        this.zpPath = zpPath;
    }
}
