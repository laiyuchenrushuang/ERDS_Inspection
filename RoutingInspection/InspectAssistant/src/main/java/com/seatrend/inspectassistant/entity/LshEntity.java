package com.seatrend.inspectassistant.entity;

import java.io.Serializable;

/**
 * Created by ly on 2020/7/17 18:44
 */
public class LshEntity implements Serializable {

    /**
     * status : true
     * code : 0
     * message : 成功
     * data : {"lsh":"1200617878833","cyry":"51072619901229023X","lszt":"99","lczt":"业务办结","clsbdh":"LVGCJE238HG236202","cllx":"K33","cyrq":1592383216000,"SYR":"杨恒"}
     * total : 0
     */

    private boolean status;
    private int code;
    private String message;
    private DataBean data;
    private int total;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    public static class DataBean implements Serializable{
        /**
         * lsh : 1200617878833
         * cyry : 51072619901229023X
         * lszt : 99
         * lczt : 业务办结
         * clsbdh : LVGCJE238HG236202
         * cllx : K33
         * cyrq : 1592383216000
         * SYR : 杨恒
         */

        private String lsh;
        private String cyry;
        private String lszt;
        private String lczt;
        private String clsbdh;
        private String cllx;
        private long cyrq;
        private String SYR;

        public String getLsh() {
            return lsh;
        }

        public void setLsh(String lsh) {
            this.lsh = lsh;
        }

        public String getCyry() {
            return cyry;
        }

        public void setCyry(String cyry) {
            this.cyry = cyry;
        }

        public String getLszt() {
            return lszt;
        }

        public void setLszt(String lszt) {
            this.lszt = lszt;
        }

        public String getLczt() {
            return lczt;
        }

        public void setLczt(String lczt) {
            this.lczt = lczt;
        }

        public String getClsbdh() {
            return clsbdh;
        }

        public void setClsbdh(String clsbdh) {
            this.clsbdh = clsbdh;
        }

        public String getCllx() {
            return cllx;
        }

        public void setCllx(String cllx) {
            this.cllx = cllx;
        }

        public long getCyrq() {
            return cyrq;
        }

        public void setCyrq(long cyrq) {
            this.cyrq = cyrq;
        }

        public String getSYR() {
            return SYR;
        }

        public void setSYR(String SYR) {
            this.SYR = SYR;
        }
    }
}
