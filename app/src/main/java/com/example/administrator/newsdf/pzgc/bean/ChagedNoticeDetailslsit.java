package com.example.administrator.newsdf.pzgc.bean;


import com.example.administrator.newsdf.pzgc.activity.changed.adapter.ChagedNoticeDetailsAdapter;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/19 0019}
 * 描述：MainActivity
 *{@link  ChagedNoticeDetailsAdapter}
 */
public class ChagedNoticeDetailslsit {


        /**
         * id : esse non nulla
         * isOverdue : 4.284347487879789E7
         * isVerify : -3.618239021937377E7
         * standardDelName : mollit
         */

        private String id;
        private String isOverdue;
        private String isVerify;
        private String standardDelName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIsOverdue() {
            return isOverdue;
        }

        public void setIsOverdue(String isOverdue) {
            this.isOverdue = isOverdue;
        }

        public String getIsVerify() {
            return isVerify;
        }

        public void setIsVerify(String isVerify) {
            this.isVerify = isVerify;
        }

        public String getStandardDelName() {
            return standardDelName;
        }

        public void setStandardDelName(String standardDelName) {
            this.standardDelName = standardDelName;
        }

}
