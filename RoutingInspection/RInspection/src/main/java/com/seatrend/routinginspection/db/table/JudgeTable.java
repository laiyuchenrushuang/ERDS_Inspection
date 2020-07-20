package com.seatrend.routinginspection.db.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ly on 2020/7/7 13:15
 */
@Entity
public class JudgeTable extends BaseTable{

    /**
     * jhbh : JHBH2007011607360741031
     * rwbh : RWBH200628155451988
     * rwmc : 检查场地设备是否异常
     * rwcjr : null
     * rwcjsj : null
     * rwxgsj : null
     * rwms : 是否漏油，正常运转，是否通电
     * rwzt : null
     * rwzxjg : 0
     * id : 36dcb91326ac4d62a46bd27e084d82cd
     * glbm : 510000000000
     */
    @Id
    private Long id;         //planid

    private String lsh;     //巡站流水 jhbh

//    private String jhbh;
    private String rwbh = "";  //任务编号
    private String rwmc="";  //任务名称
    private String rwcjr = ""; //任务创建人
    private String rwcjsj =""; //任务创建时间
    private String rwxgsj =""; //任务时间
    private String rwms = "";//任务描述
    private String rwzt = "";//任务状态
    private String rwzxjg = "";//任务结论
    private String rwid = "";  //服务器的任务id
    private String glbm = "";//任务管理部门
    private String rwzxr = "";//任务执行人
    @Generated(hash = 2127854659)
    public JudgeTable(Long id, String lsh, String rwbh, String rwmc, String rwcjr,
            String rwcjsj, String rwxgsj, String rwms, String rwzt, String rwzxjg,
            String rwid, String glbm, String rwzxr) {
        this.id = id;
        this.lsh = lsh;
        this.rwbh = rwbh;
        this.rwmc = rwmc;
        this.rwcjr = rwcjr;
        this.rwcjsj = rwcjsj;
        this.rwxgsj = rwxgsj;
        this.rwms = rwms;
        this.rwzt = rwzt;
        this.rwzxjg = rwzxjg;
        this.rwid = rwid;
        this.glbm = glbm;
        this.rwzxr = rwzxr;
    }
    @Generated(hash = 1829595970)
    public JudgeTable() {
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
    public String getRwbh() {
        return this.rwbh;
    }
    public void setRwbh(String rwbh) {
        this.rwbh = rwbh;
    }
    public String getRwmc() {
        return this.rwmc;
    }
    public void setRwmc(String rwmc) {
        this.rwmc = rwmc;
    }
    public String getRwcjr() {
        return this.rwcjr;
    }
    public void setRwcjr(String rwcjr) {
        this.rwcjr = rwcjr;
    }
    public String getRwcjsj() {
        return this.rwcjsj;
    }
    public void setRwcjsj(String rwcjsj) {
        this.rwcjsj = rwcjsj;
    }
    public String getRwxgsj() {
        return this.rwxgsj;
    }
    public void setRwxgsj(String rwxgsj) {
        this.rwxgsj = rwxgsj;
    }
    public String getRwms() {
        return this.rwms;
    }
    public void setRwms(String rwms) {
        this.rwms = rwms;
    }
    public String getRwzt() {
        return this.rwzt;
    }
    public void setRwzt(String rwzt) {
        this.rwzt = rwzt;
    }
    public String getRwzxjg() {
        return this.rwzxjg;
    }
    public void setRwzxjg(String rwzxjg) {
        this.rwzxjg = rwzxjg;
    }
    public String getRwid() {
        return this.rwid;
    }
    public void setRwid(String rwid) {
        this.rwid = rwid;
    }
    public String getGlbm() {
        return this.glbm;
    }
    public void setGlbm(String glbm) {
        this.glbm = glbm;
    }
    public String getRwzxr() {
        return this.rwzxr;
    }
    public void setRwzxr(String rwzxr) {
        this.rwzxr = rwzxr;
    }

}
