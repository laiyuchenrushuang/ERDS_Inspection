package com.seatrend.routinginspection.entity;

import java.util.List;

/**
 * Created by ly on 2020/7/1 16:27
 *
 *

 {
 "status": true,
 "code": 0,
 "message": "成功",
 "data": {
 "pageNo": 1,
 "pageSize": 10,
 "count": 12,
 "list": [
 {
 "jhzxr": null,
 "jhwcsj": null,
 "jhbh": "JHBH200628162935516",
 "xzlx": "1",
 "jhzt": "2",
 "xzrq": 1593323883000,
 "ROW_ID": 1,
 "bmmc": "部门名称0"
 },
 {
 "jhzxr": null,
 "jhwcsj": null,
 "jhbh": "JHBH200628162935516",
 "xzlx": "1",
 "jhzt": "2",
 "xzrq": 1593323883000,
 "ROW_ID": 2,
 "bmmc": "部门名称1"
 },
 {
 "jhzxr": null,
 "jhwcsj": null,
 "jhbh": "JHBH200628162935516",
 "xzlx": "1",
 "jhzt": "2",
 "xzrq": 1593323883000,
 "ROW_ID": 3,
 "bmmc": "部门名称2"
 },
 {
 "jhzxr": null,
 "jhwcsj": null,
 "jhbh": "JHBH200628162935516",
 "xzlx": "1",
 "jhzt": "2",
 "xzrq": 1593323883000,
 "ROW_ID": 4,
 "bmmc": "部门名称3"
 },
 {
 "jhzxr": null,
 "jhwcsj": null,
 "jhbh": "JHBH200628162935516",
 "xzlx": "1",
 "jhzt": "2",
 "xzrq": 1593323883000,
 "ROW_ID": 5,
 "bmmc": "部门名称4hhhh"
 },
 {
 "jhzxr": null,
 "jhwcsj": null,
 "jhbh": "JHBH2007011607360741031",
 "xzlx": "1",
 "jhzt": "2",
 "xzrq": null,
 "ROW_ID": 6,
 "bmmc": "部门名称2"
 },
 {
 "jhzxr": null,
 "jhwcsj": null,
 "jhbh": "JHBH2007011606230941247",
 "xzlx": "1",
 "jhzt": "2",
 "xzrq": null,
 "ROW_ID": 7,
 "bmmc": "部门名称1"
 },
 {
 "jhzxr": null,
 "jhwcsj": null,
 "jhbh": "JHBH2007011606230941247",
 "xzlx": "1",
 "jhzt": "2",
 "xzrq": null,
 "ROW_ID": 8,
 "bmmc": "部门名称2"
 },
 {
 "jhzxr": null,
 "jhwcsj": null,
 "jhbh": "JHBH2007011606230941247",
 "xzlx": "1",
 "jhzt": "2",
 "xzrq": null,
 "ROW_ID": 9,
 "bmmc": "部门名称4hhhh"
 },
 {
 "jhzxr": null,
 "jhwcsj": null,
 "jhbh": "JHBH2007011607360741031",
 "xzlx": "1",
 "jhzt": "2",
 "xzrq": null,
 "ROW_ID": 10,
 "bmmc": "部门名称0"
 }
 ],
 "orderDirection": ""
 },
 "total": 0
 }


 */
public class RecordEntity extends BaseEntity {
    /**
     * data : {"pageNo":1,"pageSize":10,"count":12,"list":[{"jhzxr":null,"jhwcsj":null,"jhbh":"JHBH200628162935516","xzlx":"1","jhzt":"2","xzrq":1593323883000,"ROW_ID":1,"bmmc":"部门名称0"},{"jhzxr":null,"jhwcsj":null,"jhbh":"JHBH200628162935516","xzlx":"1","jhzt":"2","xzrq":1593323883000,"ROW_ID":2,"bmmc":"部门名称1"},{"jhzxr":null,"jhwcsj":null,"jhbh":"JHBH200628162935516","xzlx":"1","jhzt":"2","xzrq":1593323883000,"ROW_ID":3,"bmmc":"部门名称2"},{"jhzxr":null,"jhwcsj":null,"jhbh":"JHBH200628162935516","xzlx":"1","jhzt":"2","xzrq":1593323883000,"ROW_ID":4,"bmmc":"部门名称3"},{"jhzxr":null,"jhwcsj":null,"jhbh":"JHBH200628162935516","xzlx":"1","jhzt":"2","xzrq":1593323883000,"ROW_ID":5,"bmmc":"部门名称4hhhh"},{"jhzxr":null,"jhwcsj":null,"jhbh":"JHBH2007011607360741031","xzlx":"1","jhzt":"2","xzrq":null,"ROW_ID":6,"bmmc":"部门名称2"},{"jhzxr":null,"jhwcsj":null,"jhbh":"JHBH2007011606230941247","xzlx":"1","jhzt":"2","xzrq":null,"ROW_ID":7,"bmmc":"部门名称1"},{"jhzxr":null,"jhwcsj":null,"jhbh":"JHBH2007011606230941247","xzlx":"1","jhzt":"2","xzrq":null,"ROW_ID":8,"bmmc":"部门名称2"},{"jhzxr":null,"jhwcsj":null,"jhbh":"JHBH2007011606230941247","xzlx":"1","jhzt":"2","xzrq":null,"ROW_ID":9,"bmmc":"部门名称4hhhh"},{"jhzxr":null,"jhwcsj":null,"jhbh":"JHBH2007011607360741031","xzlx":"1","jhzt":"2","xzrq":null,"ROW_ID":10,"bmmc":"部门名称0"}],"orderDirection":""}
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
         * pageNo : 1
         * pageSize : 10
         * count : 12
         * list : [{"jhzxr":null,"jhwcsj":null,"jhbh":"JHBH200628162935516","xzlx":"1","jhzt":"2","xzrq":1593323883000,"ROW_ID":1,"bmmc":"部门名称0"},{"jhzxr":null,"jhwcsj":null,"jhbh":"JHBH200628162935516","xzlx":"1","jhzt":"2","xzrq":1593323883000,"ROW_ID":2,"bmmc":"部门名称1"},{"jhzxr":null,"jhwcsj":null,"jhbh":"JHBH200628162935516","xzlx":"1","jhzt":"2","xzrq":1593323883000,"ROW_ID":3,"bmmc":"部门名称2"},{"jhzxr":null,"jhwcsj":null,"jhbh":"JHBH200628162935516","xzlx":"1","jhzt":"2","xzrq":1593323883000,"ROW_ID":4,"bmmc":"部门名称3"},{"jhzxr":null,"jhwcsj":null,"jhbh":"JHBH200628162935516","xzlx":"1","jhzt":"2","xzrq":1593323883000,"ROW_ID":5,"bmmc":"部门名称4hhhh"},{"jhzxr":null,"jhwcsj":null,"jhbh":"JHBH2007011607360741031","xzlx":"1","jhzt":"2","xzrq":null,"ROW_ID":6,"bmmc":"部门名称2"},{"jhzxr":null,"jhwcsj":null,"jhbh":"JHBH2007011606230941247","xzlx":"1","jhzt":"2","xzrq":null,"ROW_ID":7,"bmmc":"部门名称1"},{"jhzxr":null,"jhwcsj":null,"jhbh":"JHBH2007011606230941247","xzlx":"1","jhzt":"2","xzrq":null,"ROW_ID":8,"bmmc":"部门名称2"},{"jhzxr":null,"jhwcsj":null,"jhbh":"JHBH2007011606230941247","xzlx":"1","jhzt":"2","xzrq":null,"ROW_ID":9,"bmmc":"部门名称4hhhh"},{"jhzxr":null,"jhwcsj":null,"jhbh":"JHBH2007011607360741031","xzlx":"1","jhzt":"2","xzrq":null,"ROW_ID":10,"bmmc":"部门名称0"}]
         * orderDirection :
         */

        private int pageNo;
        private int pageSize;
        private int count;
        private String orderDirection;
        private List<ListBean> list;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getOrderDirection() {
            return orderDirection;
        }

        public void setOrderDirection(String orderDirection) {
            this.orderDirection = orderDirection;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {


            /**
             * jhzxr : null
             * jhwcsj : null
             * jhbh : JHBH200628162935516
             * xzlx : 1
             * jhzt : 2
             * xzrq : 1593323883000
             * ROW_ID : 1
             * bmmc : 部门名称0
             */

            private String glbm;
            private String jhzxr;
            private long jhwcsj;
            private String jhbh;
            private String xzlx;
            private String jhzt;
            private long xzrq;
            private int ROW_ID;
            private String bmmc;

            public String getGlbm() {
                return glbm;
            }

            public void setGlbm(String glbm) {
                this.glbm = glbm;
            }

            public String getJhzxr() {
                return jhzxr;
            }

            public void setJhzxr(String jhzxr) {
                this.jhzxr = jhzxr;
            }

            public long getJhwcsj() {
                return jhwcsj;
            }

            public void setJhwcsj(long jhwcsj) {
                this.jhwcsj = jhwcsj;
            }

            public String getJhbh() {
                return jhbh;
            }

            public void setJhbh(String jhbh) {
                this.jhbh = jhbh;
            }

            public String getXzlx() {
                return xzlx;
            }

            public void setXzlx(String xzlx) {
                this.xzlx = xzlx;
            }

            public String getJhzt() {
                return jhzt;
            }

            public void setJhzt(String jhzt) {
                this.jhzt = jhzt;
            }

            public long getXzrq() {
                return xzrq;
            }

            public void setXzrq(long xzrq) {
                this.xzrq = xzrq;
            }

            public int getROW_ID() {
                return ROW_ID;
            }

            public void setROW_ID(int ROW_ID) {
                this.ROW_ID = ROW_ID;
            }

            public String getBmmc() {
                return bmmc;
            }

            public void setBmmc(String bmmc) {
                this.bmmc = bmmc;
            }
        }
    }
}
