package com.tmri.size.app.entity;

/**
 * Created by seatrend on 2020/7/15.
 */

public class VehicleEntity extends BaseEntity{


    /**
     * data : {"lsh":"1191229848395","cwkc":4712,"zbzl":1615,"hpzl":"02","wbzl":null,"clsbdh":"LSVUD60T2J2131811","clpp1":"大众汽车牌","zzl":2085,"zj":2791,"ywlx":"B","hdzzl":0,"cwkk":1839,"hphm":"AS60P4","cwkg":1673}
     * total : 0
     */

    private DataBean data;
    private int total;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class DataBean {
        /**
         * lsh : 1191229848395
         * cwkc : 4712
         * zbzl : 1615
         * hpzl : 02
         * wbzl : null
         * clsbdh : LSVUD60T2J2131811
         * clpp1 : 大众汽车牌
         * zzl : 2085
         * zj : 2791
         * ywlx : B
         * hdzzl : 0
         * cwkk : 1839
         * hphm : AS60P4
         * cwkg : 1673
         */

        private String lsh;
        private int cwkc;
        private int zbzl;
        private String hpzl;
        private String wbzl;
        private String clsbdh;
        private String clpp1;
        private int zzl;
        private int zj;
        private String ywlx;
        private int hdzzl;
        private int cwkk;
        private String hphm;
        private int cwkg;

        private String clc;
        private String clk;
        private String clg;
        private String clzbzl;
        private String clcsfhg;
        private String clksfhg;
        private String clgsfhg;
        private String clzbzlsfhg;
        private String clcsm;
        private String clksm;
        private String clgsm;
        private String clzbzlsm;


        public String getClc() {
            return clc;
        }

        public void setClc(String clc) {
            this.clc = clc;
        }

        public String getClk() {
            return clk;
        }

        public void setClk(String clk) {
            this.clk = clk;
        }

        public String getClg() {
            return clg;
        }

        public void setClg(String clg) {
            this.clg = clg;
        }

        public String getClzbzl() {
            return clzbzl;
        }

        public void setClzbzl(String clzbzl) {
            this.clzbzl = clzbzl;
        }

        public String getClcsfhg() {
            return clcsfhg;
        }

        public void setClcsfhg(String clcsfhg) {
            this.clcsfhg = clcsfhg;
        }

        public String getClksfhg() {
            return clksfhg;
        }

        public void setClksfhg(String clksfhg) {
            this.clksfhg = clksfhg;
        }

        public String getClgsfhg() {
            return clgsfhg;
        }

        public void setClgsfhg(String clgsfhg) {
            this.clgsfhg = clgsfhg;
        }

        public String getClzbzlsfhg() {
            return clzbzlsfhg;
        }

        public void setClzbzlsfhg(String clzbzlsfhg) {
            this.clzbzlsfhg = clzbzlsfhg;
        }

        public String getClcsm() {
            return clcsm;
        }

        public void setClcsm(String clcsm) {
            this.clcsm = clcsm;
        }

        public String getClksm() {
            return clksm;
        }

        public void setClksm(String clksm) {
            this.clksm = clksm;
        }

        public String getClgsm() {
            return clgsm;
        }

        public void setClgsm(String clgsm) {
            this.clgsm = clgsm;
        }

        public String getClzbzlsm() {
            return clzbzlsm;
        }

        public void setClzbzlsm(String clzbzlsm) {
            this.clzbzlsm = clzbzlsm;
        }

        public String getLsh() {
            return lsh;
        }

        public void setLsh(String lsh) {
            this.lsh = lsh;
        }

        public int getCwkc() {
            return cwkc;
        }

        public void setCwkc(int cwkc) {
            this.cwkc = cwkc;
        }

        public int getZbzl() {
            return zbzl;
        }

        public void setZbzl(int zbzl) {
            this.zbzl = zbzl;
        }

        public String getHpzl() {
            return hpzl;
        }

        public void setHpzl(String hpzl) {
            this.hpzl = hpzl;
        }

        public String getWbzl() {
            return wbzl;
        }

        public void setWbzl(String wbzl) {
            this.wbzl = wbzl;
        }

        public String getClsbdh() {
            return clsbdh;
        }

        public void setClsbdh(String clsbdh) {
            this.clsbdh = clsbdh;
        }

        public String getClpp1() {
            return clpp1;
        }

        public void setClpp1(String clpp1) {
            this.clpp1 = clpp1;
        }

        public int getZzl() {
            return zzl;
        }

        public void setZzl(int zzl) {
            this.zzl = zzl;
        }

        public int getZj() {
            return zj;
        }

        public void setZj(int zj) {
            this.zj = zj;
        }

        public String getYwlx() {
            return ywlx;
        }

        public void setYwlx(String ywlx) {
            this.ywlx = ywlx;
        }

        public int getHdzzl() {
            return hdzzl;
        }

        public void setHdzzl(int hdzzl) {
            this.hdzzl = hdzzl;
        }

        public int getCwkk() {
            return cwkk;
        }

        public void setCwkk(int cwkk) {
            this.cwkk = cwkk;
        }

        public String getHphm() {
            return hphm;
        }

        public void setHphm(String hphm) {
            this.hphm = hphm;
        }

        public int getCwkg() {
            return cwkg;
        }

        public void setCwkg(int cwkg) {
            this.cwkg = cwkg;
        }
    }
}
