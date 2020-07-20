package com.seatrend.routinginspection.entity;

import java.util.List;

/**
 * Created by ly on 2020/7/1 16:18
 *
 {"status":true,"code":0,"message":"成功","data":{"pageNo":2,"pageSize":10,"count":20,"list":[{"jhbh":"JHBH2007011607360741031","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":11,"bmmc":"部门名称0"},{"jhbh":"JHBH2007011607360741031","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":12,"bmmc":"部门名称1"},{"jhbh":"JHBH2007011607360741031","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":13,"bmmc":"部门名称2"},{"jhbh":"JHBH2007011607360741031","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":14,"bmmc":"部门名称3"},{"jhbh":"JHBH2007011607360741031","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":15,"bmmc":"部门名称4hhhh"},{"jhbh":"JHBH2007011612093274830","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":16,"bmmc":"部门名称0"},{"jhbh":"JHBH2007011612093274830","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":17,"bmmc":"部门名称1"},{"jhbh":"JHBH2007011612093274830","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":18,"bmmc":"部门名称2"},{"jhbh":"JHBH2007011612093274830","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":19,"bmmc":"部门名称3"},{"jhbh":"JHBH2007011612093274830","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":20,"bmmc":"部门名称4hhhh"}],"orderDirection":""},"total":0}
 
 
 
 */
public class PLANEntity extends BaseEntity {
    /**
     * data : {"pageNo":2,"pageSize":10,"count":20,"list":[{"jhbh":"JHBH2007011607360741031","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":11,"bmmc":"部门名称0"},{"jhbh":"JHBH2007011607360741031","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":12,"bmmc":"部门名称1"},{"jhbh":"JHBH2007011607360741031","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":13,"bmmc":"部门名称2"},{"jhbh":"JHBH2007011607360741031","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":14,"bmmc":"部门名称3"},{"jhbh":"JHBH2007011607360741031","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":15,"bmmc":"部门名称4hhhh"},{"jhbh":"JHBH2007011612093274830","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":16,"bmmc":"部门名称0"},{"jhbh":"JHBH2007011612093274830","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":17,"bmmc":"部门名称1"},{"jhbh":"JHBH2007011612093274830","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":18,"bmmc":"部门名称2"},{"jhbh":"JHBH2007011612093274830","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":19,"bmmc":"部门名称3"},{"jhbh":"JHBH2007011612093274830","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":20,"bmmc":"部门名称4hhhh"}],"orderDirection":""}
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
         * pageNo : 2
         * pageSize : 10
         * count : 20
         * list : [{"jhbh":"JHBH2007011607360741031","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":11,"bmmc":"部门名称0"},{"jhbh":"JHBH2007011607360741031","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":12,"bmmc":"部门名称1"},{"jhbh":"JHBH2007011607360741031","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":13,"bmmc":"部门名称2"},{"jhbh":"JHBH2007011607360741031","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":14,"bmmc":"部门名称3"},{"jhbh":"JHBH2007011607360741031","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":15,"bmmc":"部门名称4hhhh"},{"jhbh":"JHBH2007011612093274830","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":16,"bmmc":"部门名称0"},{"jhbh":"JHBH2007011612093274830","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":17,"bmmc":"部门名称1"},{"jhbh":"JHBH2007011612093274830","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":18,"bmmc":"部门名称2"},{"jhbh":"JHBH2007011612093274830","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":19,"bmmc":"部门名称3"},{"jhbh":"JHBH2007011612093274830","xzlx":"1","jhzt":"0","xzrq":null,"ROW_ID":20,"bmmc":"部门名称4hhhh"}]
         * orderDirection : 
         */

        private int pageNo;
        private int pageSize;
        private int count;
        private String orderDirection;
        private List<TASK> list;

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

        public List<TASK> getList() {
            return list;
        }

        public void setList(List<TASK> list) {
            this.list = list;
        }

        public static class TASK {
            /**
             * jhbh : JHBH2007011607360741031
             * xzlx : 1
             * jhzt : 0
             * xzrq : null
             * ROW_ID : 11
             * bmmc : 部门名称0
             * glbm:++++
             */

            private String jhbh;
            private String xzlx;
            private String jhzt;
            private Long xzrq;
            private int ROW_ID;
            private String bmmc;
            private String glbm;

            public String getGlbm() {
                return glbm;
            }

            public void setGlbm(String glbm) {
                this.glbm = glbm;
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

            public Long getXzrq() {
                return xzrq;
            }

            public void setXzrq(Long xzrq) {
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
