package com.example.administrator.newsdf.pzgc.bean;

import java.util.List;

public class ChagedList {




        private int page;
        private int rows;
        private int total;
        private int totalPages;
        private List<ResultsBean> results;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getRows() {
            return rows;
        }

        public void setRows(int rows) {
            this.rows = rows;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public List<ResultsBean> getResults() {
            return results;
        }

        public void setResults(List<ResultsBean> results) {
            this.results = results;
        }

        public static class ResultsBean {
            /**
             * noticeFinishCount : 0
             * code : ZGTZ-20190214-4
             * noticeCount : 0
             * id : 1d1b40e0ce1c46b782e3f94e9c9184af
             * ruserName : 田江
             * auserName : 系统管理员
             * rorgName : 三独一标
             * sorgName : 贵州路桥集团有限公司
             * status : 20
             */

            private int noticeFinishCount;
            private String code;

            public int getNoticeFinishCount() {
                return noticeFinishCount;
            }

            public void setNoticeFinishCount(int noticeFinishCount) {
                this.noticeFinishCount = noticeFinishCount;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }
        }

}
