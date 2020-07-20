package com.seatrend.routinginspection.entity;

/**
 * Created by ly on 2020/7/9 13:19
 */
public class FileUploadEntity extends BaseEntity {
    /**
     * data : {"path":"/home/ftpadmin/patrol/1594266799892908.jpeg","type":null}
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
         * path : /home/ftpadmin/patrol/1594266799892908.jpeg
         * type : null
         */

        private String path;
        private String type;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
