package com.seatrend.routinginspection.db.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ly on 2020/7/7 13:15
 */
@Entity
public class PictureTable extends BaseTable{
    @Id
    private Long id;         //照片id

//    @Index(unique = true)
    private String lsh = "";     //巡站流水

    private String zpPath = "";  //照片路径
    private String glbm  ="";//管理部门
    private String zpId = "";  //zp存服务器的id  照片地址
    @Generated(hash = 1458219291)
    public PictureTable(Long id, String lsh, String zpPath, String glbm,
            String zpId) {
        this.id = id;
        this.lsh = lsh;
        this.zpPath = zpPath;
        this.glbm = glbm;
        this.zpId = zpId;
    }
    @Generated(hash = 633761473)
    public PictureTable() {
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
    public String getZpPath() {
        return this.zpPath;
    }
    public void setZpPath(String zpPath) {
        this.zpPath = zpPath;
    }
    public String getZpId() {
        return this.zpId;
    }
    public void setZpId(String zpId) {
        this.zpId = zpId;
    }
    public String getGlbm() {
        return this.glbm;
    }
    public void setGlbm(String glbm) {
        this.glbm = glbm;
    }
}
