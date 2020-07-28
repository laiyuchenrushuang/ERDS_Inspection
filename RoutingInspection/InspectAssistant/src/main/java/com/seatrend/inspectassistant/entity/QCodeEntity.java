package com.seatrend.inspectassistant.entity;

import java.io.Serializable;

/**
 * Created by ly on 2020/7/20 10:49
 */
public class QCodeEntity implements Serializable {
    /**
     * bh : 7353432a-b79a-4317-b074-c209dac40452
     * uuid : 1234567
     * cyqid : 1
     * cyqmc : 通安服务站查验区
     * ywlx : 0
     * yyrq : 2020-07-19
     * yysd : 6
     * zt : 1
     * xm : 测试人
     * lxfs : 18559393025
     * gcjk : 2
     * clsbdh : 1234456566
     * fdjh : 123546565
     * ccrq : 20140101
     * qfrq : 20180101
     * hphm : 12345
     * djzsbh : 123234
     * ywlxmc : 注册登记
     */

    private String bh;
    private String uuid;
    private int cyqid;
    private String cyqmc;
    private String ywlx; //0注册1转移2变更3转入9其他
    private String yyrq;
    private String yyrqmc;
    private String yysd;
    private String zt;
    private String xm;
    private String lxfs;
    private String gcjk;
    private String clsbdh;
    private String fdjh;
    private String ccrq;
    private String qfrq;
    private String hphm;
    private String djzsbh;
    private String ywlxmc;
    private String dmsm2; //++++代码说明 （A 注册 B转移 D变更 I转入）

    public String getDmsm2() {
        return dmsm2;
    }

    public void setDmsm2(String dmsm2) {
        this.dmsm2 = dmsm2;
    }



    public String getYyrqmc() {
        return yyrqmc;
    }

    public void setYyrqmc(String yyrqmc) {
        this.yyrqmc = yyrqmc;
    }

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getCyqid() {
        return cyqid;
    }

    public void setCyqid(int cyqid) {
        this.cyqid = cyqid;
    }

    public String getCyqmc() {
        return cyqmc;
    }

    public void setCyqmc(String cyqmc) {
        this.cyqmc = cyqmc;
    }

    public String getYwlx() {
        return ywlx;
    }

    public void setYwlx(String ywlx) {
        this.ywlx = ywlx;
    }

    public String getYyrq() {
        return yyrq;
    }

    public void setYyrq(String yyrq) {
        this.yyrq = yyrq;
    }

    public String getYysd() {
        return yysd;
    }

    public void setYysd(String yysd) {
        this.yysd = yysd;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getLxfs() {
        return lxfs;
    }

    public void setLxfs(String lxfs) {
        this.lxfs = lxfs;
    }

    public String getGcjk() {
        return gcjk;
    }

    public void setGcjk(String gcjk) {
        this.gcjk = gcjk;
    }

    public String getClsbdh() {
        return clsbdh;
    }

    public void setClsbdh(String clsbdh) {
        this.clsbdh = clsbdh;
    }

    public String getFdjh() {
        return fdjh;
    }

    public void setFdjh(String fdjh) {
        this.fdjh = fdjh;
    }

    public String getCcrq() {
        return ccrq;
    }

    public void setCcrq(String ccrq) {
        this.ccrq = ccrq;
    }

    public String getQfrq() {
        return qfrq;
    }

    public void setQfrq(String qfrq) {
        this.qfrq = qfrq;
    }

    public String getHphm() {
        return hphm;
    }

    public void setHphm(String hphm) {
        this.hphm = hphm;
    }

    public String getDjzsbh() {
        return djzsbh;
    }

    public void setDjzsbh(String djzsbh) {
        this.djzsbh = djzsbh;
    }

    public String getYwlxmc() {
        return ywlxmc;
    }

    public void setYwlxmc(String ywlxmc) {
        this.ywlxmc = ywlxmc;
    }
}
