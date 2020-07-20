package com.seatrend.routinginspection.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ly on 2020/7/9 17:43
 */
public class ZpdzListEntity extends BaseEntity {
    private ArrayList<DataBean> data;

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 798a6d7d17ef4758888e891cdff4717b
         * jhbh : JHBH2007091623183293652
         * rwbh : null
         * glbm : 512000CY0002
         * zpdz : /home/ftpadmin/patrol/1594283648497927.jpeg
         * scsj : 1594283663000
         * scbj : null
         */

        private String id;
        private String jhbh;
        private Object rwbh;
        private String glbm;
        private String zpdz;
        private long scsj;
        private Object scbj;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJhbh() {
            return jhbh;
        }

        public void setJhbh(String jhbh) {
            this.jhbh = jhbh;
        }

        public Object getRwbh() {
            return rwbh;
        }

        public void setRwbh(Object rwbh) {
            this.rwbh = rwbh;
        }

        public String getGlbm() {
            return glbm;
        }

        public void setGlbm(String glbm) {
            this.glbm = glbm;
        }

        public String getZpdz() {
            return zpdz;
        }

        public void setZpdz(String zpdz) {
            this.zpdz = zpdz;
        }

        public long getScsj() {
            return scsj;
        }

        public void setScsj(long scsj) {
            this.scsj = scsj;
        }

        public Object getScbj() {
            return scbj;
        }

        public void setScbj(Object scbj) {
            this.scbj = scbj;
        }
    }
}
