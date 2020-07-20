package com.seatrend.routinginspection.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ly on 2020/7/9 16:55
 */
public class RecordDetailEntity extends BaseEntity {
    /**
     * data : {"jhbh":"JHBH2007091623183293652","xzlx":"0","xzrq":1594224000000,"xzrq1":null,"jhcjr":"system1","jhcjrq":1594282998000,"stationPlanList":[{"glbm":"512000CY0002","bmmc":"乐至万盛服务站查验区","jhbh":"JHBH2007091623183293652","jhzt":"2","id":null,"jhzxr":"JHBH2007091623183293652","jhwcbz":"噢噢噢","xzlx":"0","xzrq":1594224000000,"xzrq1":null,"jhcjr":"system1","jhcjrq":1594282998000,"jhwcsj":1594282867000}],"planTaskList":["RWBH200628155758664","RWBH2007031350407159306"],"planTaskListObject":[{"jhbh":"JHBH2007091623183293652","rwbh":"RWBH200628155758664","rwmc":"场地是否符合规定","rwcjr":null,"rwcjsj":null,"rwxgsj":null,"rwms":"场地大小是否合格","rwzt":null,"rwzxjg":"1","id":"55660c7144634f83b9b0e9381156d394","glbm":"512000CY0002","sjly":null},{"jhbh":"JHBH2007091623183293652","rwbh":"RWBH2007031350407159306","rwmc":"场地是否整洁","rwcjr":null,"rwcjsj":null,"rwxgsj":null,"rwms":"有无垃圾、设备摆放是否整齐等。","rwzt":null,"rwzxjg":"1","id":"87b46eb24a9d42fba6321f17e3d17200","glbm":"512000CY0002","sjly":null}]}
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
         * jhbh : JHBH2007091623183293652
         * xzlx : 0
         * xzrq : 1594224000000
         * xzrq1 : null
         * jhcjr : system1
         * jhcjrq : 1594282998000
         * stationPlanList : [{"glbm":"512000CY0002","bmmc":"乐至万盛服务站查验区","jhbh":"JHBH2007091623183293652","jhzt":"2","id":null,"jhzxr":"JHBH2007091623183293652","jhwcbz":"噢噢噢","xzlx":"0","xzrq":1594224000000,"xzrq1":null,"jhcjr":"system1","jhcjrq":1594282998000,"jhwcsj":1594282867000}]
         * planTaskList : ["RWBH200628155758664","RWBH2007031350407159306"]
         * planTaskListObject : [{"jhbh":"JHBH2007091623183293652","rwbh":"RWBH200628155758664","rwmc":"场地是否符合规定","rwcjr":null,"rwcjsj":null,"rwxgsj":null,"rwms":"场地大小是否合格","rwzt":null,"rwzxjg":"1","id":"55660c7144634f83b9b0e9381156d394","glbm":"512000CY0002","sjly":null},{"jhbh":"JHBH2007091623183293652","rwbh":"RWBH2007031350407159306","rwmc":"场地是否整洁","rwcjr":null,"rwcjsj":null,"rwxgsj":null,"rwms":"有无垃圾、设备摆放是否整齐等。","rwzt":null,"rwzxjg":"1","id":"87b46eb24a9d42fba6321f17e3d17200","glbm":"512000CY0002","sjly":null}]
         */

        private String jhbh;
        private String xzlx;
        private long xzrq;
        private Object xzrq1;
        private String jhcjr;
        private long jhcjrq;
        private List<StationPlanListBean> stationPlanList;
        private List<String> planTaskList;
        private ArrayList<PlanTaskListObjectBean> planTaskListObject;

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

        public long getXzrq() {
            return xzrq;
        }

        public void setXzrq(long xzrq) {
            this.xzrq = xzrq;
        }

        public Object getXzrq1() {
            return xzrq1;
        }

        public void setXzrq1(Object xzrq1) {
            this.xzrq1 = xzrq1;
        }

        public String getJhcjr() {
            return jhcjr;
        }

        public void setJhcjr(String jhcjr) {
            this.jhcjr = jhcjr;
        }

        public long getJhcjrq() {
            return jhcjrq;
        }

        public void setJhcjrq(long jhcjrq) {
            this.jhcjrq = jhcjrq;
        }

        public List<StationPlanListBean> getStationPlanList() {
            return stationPlanList;
        }

        public void setStationPlanList(List<StationPlanListBean> stationPlanList) {
            this.stationPlanList = stationPlanList;
        }

        public List<String> getPlanTaskList() {
            return planTaskList;
        }

        public void setPlanTaskList(List<String> planTaskList) {
            this.planTaskList = planTaskList;
        }

        public ArrayList<PlanTaskListObjectBean> getPlanTaskListObject() {
            return planTaskListObject;
        }

        public void setPlanTaskListObject(ArrayList<PlanTaskListObjectBean> planTaskListObject) {
            this.planTaskListObject = planTaskListObject;
        }

        public static class StationPlanListBean {
            /**
             * glbm : 512000CY0002
             * bmmc : 乐至万盛服务站查验区
             * jhbh : JHBH2007091623183293652
             * jhzt : 2
             * id : null
             * jhzxr : JHBH2007091623183293652
             * jhwcbz : 噢噢噢
             * xzlx : 0
             * xzrq : 1594224000000
             * xzrq1 : null
             * jhcjr : system1
             * jhcjrq : 1594282998000
             * jhwcsj : 1594282867000
             */

            private String glbm;
            private String bmmc;
            private String jhbh;
            private String jhzt;
            private Object id;
            private String jhzxr;
            private String jhwcbz;
            private String xzlx;
            private long xzrq;
            private Object xzrq1;
            private String jhcjr;
            private long jhcjrq;
            private long jhwcsj;

            public String getGlbm() {
                return glbm;
            }

            public void setGlbm(String glbm) {
                this.glbm = glbm;
            }

            public String getBmmc() {
                return bmmc;
            }

            public void setBmmc(String bmmc) {
                this.bmmc = bmmc;
            }

            public String getJhbh() {
                return jhbh;
            }

            public void setJhbh(String jhbh) {
                this.jhbh = jhbh;
            }

            public String getJhzt() {
                return jhzt;
            }

            public void setJhzt(String jhzt) {
                this.jhzt = jhzt;
            }

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public String getJhzxr() {
                return jhzxr;
            }

            public void setJhzxr(String jhzxr) {
                this.jhzxr = jhzxr;
            }

            public String getJhwcbz() {
                return jhwcbz;
            }

            public void setJhwcbz(String jhwcbz) {
                this.jhwcbz = jhwcbz;
            }

            public String getXzlx() {
                return xzlx;
            }

            public void setXzlx(String xzlx) {
                this.xzlx = xzlx;
            }

            public long getXzrq() {
                return xzrq;
            }

            public void setXzrq(long xzrq) {
                this.xzrq = xzrq;
            }

            public Object getXzrq1() {
                return xzrq1;
            }

            public void setXzrq1(Object xzrq1) {
                this.xzrq1 = xzrq1;
            }

            public String getJhcjr() {
                return jhcjr;
            }

            public void setJhcjr(String jhcjr) {
                this.jhcjr = jhcjr;
            }

            public long getJhcjrq() {
                return jhcjrq;
            }

            public void setJhcjrq(long jhcjrq) {
                this.jhcjrq = jhcjrq;
            }

            public long getJhwcsj() {
                return jhwcsj;
            }

            public void setJhwcsj(long jhwcsj) {
                this.jhwcsj = jhwcsj;
            }
        }

        public static class PlanTaskListObjectBean {
            /**
             * jhbh : JHBH2007091623183293652
             * rwbh : RWBH200628155758664
             * rwmc : 场地是否符合规定
             * rwcjr : null
             * rwcjsj : null
             * rwxgsj : null
             * rwms : 场地大小是否合格
             * rwzt : null
             * rwzxjg : 1
             * id : 55660c7144634f83b9b0e9381156d394
             * glbm : 512000CY0002
             * sjly : null
             */

            private String jhbh;
            private String rwbh;
            private String rwmc;
            private Object rwcjr;
            private Object rwcjsj;
            private Object rwxgsj;
            private String rwms;
            private Object rwzt;
            private String rwzxjg;
            private String id;
            private String glbm;
            private Object sjly;

            public String getJhbh() {
                return jhbh;
            }

            public void setJhbh(String jhbh) {
                this.jhbh = jhbh;
            }

            public String getRwbh() {
                return rwbh;
            }

            public void setRwbh(String rwbh) {
                this.rwbh = rwbh;
            }

            public String getRwmc() {
                return rwmc;
            }

            public void setRwmc(String rwmc) {
                this.rwmc = rwmc;
            }

            public Object getRwcjr() {
                return rwcjr;
            }

            public void setRwcjr(Object rwcjr) {
                this.rwcjr = rwcjr;
            }

            public Object getRwcjsj() {
                return rwcjsj;
            }

            public void setRwcjsj(Object rwcjsj) {
                this.rwcjsj = rwcjsj;
            }

            public Object getRwxgsj() {
                return rwxgsj;
            }

            public void setRwxgsj(Object rwxgsj) {
                this.rwxgsj = rwxgsj;
            }

            public String getRwms() {
                return rwms;
            }

            public void setRwms(String rwms) {
                this.rwms = rwms;
            }

            public Object getRwzt() {
                return rwzt;
            }

            public void setRwzt(Object rwzt) {
                this.rwzt = rwzt;
            }

            public String getRwzxjg() {
                return rwzxjg;
            }

            public void setRwzxjg(String rwzxjg) {
                this.rwzxjg = rwzxjg;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getGlbm() {
                return glbm;
            }

            public void setGlbm(String glbm) {
                this.glbm = glbm;
            }

            public Object getSjly() {
                return sjly;
            }

            public void setSjly(Object sjly) {
                this.sjly = sjly;
            }
        }
    }
}
