package com.example.administrator.newsdf.pzgc.bean;

/**
 * @author lx
 * @Created by: 2018/12/10 0010.
 * @description:特种设备违反标准
 * @Activity：
 */

public class SecstandardlistBean {


        /**
         * check_standard : a.吊装作业应专人指挥，分工明确；
         * group_id : a066899fffa94dbd8d3b9f2d9933b6ed
         * id : fb2dbeacdf664ce5b342aa93530d0efb
         * qa_detection_id : 8edc035d94a449bf97d111eb135992d3
         */

        private String check_standard;
        private String group_id;
        private String id;
        private String qa_detection_id;

        public String getCheck_standard() {
            return check_standard;
        }

        public void setCheck_standard(String check_standard) {
            this.check_standard = check_standard;
        }

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQa_detection_id() {
            return qa_detection_id;
        }

        public void setQa_detection_id(String qa_detection_id) {
            this.qa_detection_id = qa_detection_id;
        }

}
