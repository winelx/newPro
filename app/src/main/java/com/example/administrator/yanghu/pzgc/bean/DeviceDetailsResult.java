package com.example.administrator.yanghu.pzgc.bean;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/12/12 0012.
 * @description:
 * @Activity：
 */

public class DeviceDetailsResult {

        /**
         * business :
         * operTime : 2018-12-12 10:30:58
         * realname : 柴广
         * times : 3
         * type : 5
         * view : 同时官方绝对是个房间看电视艰苦奋斗是富华大厦健康规范健康的身份但是房间看电视看房的说法就肯定是个开放第三方会尽快都是官方肯定是
         * file : [{"fileext":"jpeg","filename":"20170621201348_4719","filepath":"upload/2018/12/12/a62bb3e653634598a42b485bd3ba1c91.jpeg","id":"8615961eb103440eb1647f06d7d61349"}]
         */

        private String business;
        private String operTime;
        private String realname;
        private String times;
        private int type;
        private String view;
        private ArrayList<FileTypeBean> file;

    public DeviceDetailsResult(String business, String operTime, String realname, String times, int type, String view, ArrayList<FileTypeBean> file) {
        this.business = business;
        this.operTime = operTime;
        this.realname = realname;
        this.times = times;
        this.type = type;
        this.view = view;
        this.file = file;
    }

    public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public String getOperTime() {
            return operTime;
        }

        public void setOperTime(String operTime) {
            this.operTime = operTime;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
        }

        public ArrayList<FileTypeBean> getFile() {
            return file;
        }

        public void setFile(ArrayList<FileTypeBean> file) {
            this.file = file;

    }
}
