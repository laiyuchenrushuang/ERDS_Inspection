package com.seatrend.routinginspection.entity;

/**
 * Created by ly on 2020/7/8 14:01
 */
public class PhotoTypeEntity {

    private String zplj;
    private String zpzl;
    private String zpmc;

    private String zpId; //服务器返回的照片id地址

    public String getZpId() {
        return zpId;
    }

    public void setZpId(String zpId) {
        this.zpId = zpId;
    }

    public String getZplj() {
        return zplj;
    }

    public void setZplj(String zplj) {
        this.zplj = zplj;
    }

    public String getZpzl() {
        return zpzl;
    }

    public void setZpzl(String zpzl) {
        this.zpzl = zpzl;
    }

    public String getZpmc() {
        return zpmc;
    }

    public void setZpmc(String zpmc) {
        this.zpmc = zpmc;
    }

}
