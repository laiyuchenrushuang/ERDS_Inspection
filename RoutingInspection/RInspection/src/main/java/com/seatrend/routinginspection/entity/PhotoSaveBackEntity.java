package com.seatrend.routinginspection.entity;

/**
 * Created by ly on 2020/7/9 14:00
 */
public class PhotoSaveBackEntity extends BaseEntity {
    /**
     * data : {"jhbh":"JHBH2007081510051042359","scbj":null}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * jhbh : JHBH2007081510051042359
         * scbj : null
         */

        private String jhbh;
        private String scbj;

        public String getJhbh() {
            return jhbh;
        }

        public void setJhbh(String jhbh) {
            this.jhbh = jhbh;
        }

        public String getScbj() {
            return scbj;
        }

        public void setScbj(String scbj) {
            this.scbj = scbj;
        }
    }
}
