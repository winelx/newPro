package com.example.administrator.newsdf.pzgc.special.programme.bean;

import java.util.List;

public class ProDetails {

    /**
     * data : {"createPerosn":"系统管理员","id":"3de5ba73e7ce4d21983237ed60a3dfdf","orgName":"测试一标","projectDescription":"爱的发声大地方","specialItemBaseName":"基坑开挖专项施工方案","specialItemDelName":"基坑开挖专项施工方案","specialItemMainName":"安卓测试新增","submitDate":"2019-08-08 09:07:42"}
     * fileResultList : [{"fileName":"WBS结构划分（沱溪河大桥）.xlsx","fileUrl":"upload/2019/08/08/dbd084924065480583a32ae014b6302c.pdf","onlineRead":2},{"fileName":"一般路基设计图.dwg","fileUrl":"upload/2019/08/08/b7619388f7cb42be98a3fc5004688b63.dwg","onlineRead":1},{"fileName":"Java4.pdf","fileUrl":"upload/2019/08/08/0937247eac5c48c589b1d292647d9cde.pdf","onlineRead":2}]
     * isAssign : 0
     * permission : 0
     * recordList : [{"dealDate":"2019-08-08 09:08:08","dealOpin":"通过","dealPerson":"周冉","dealResult":"同意","id":"df4f1361117d41cf8341356e551540ec","isAssign":0,"isDeal":2,"node":1,"ownOrg":1},{"dealDate":"2019-08-08 09:08:55","dealOpin":"同意","dealPerson":"冯丹","dealResult":"同意","id":"fbe2ac9ec9f64918b4257c9de667c7c2","isAssign":0,"isDeal":2,"node":2,"ownOrg":2},{"assignNode":3,"dealDate":"2019-08-08 09:10:19","dealOpin":"同意","dealPerson":"张三","dealResult":"同意","id":"4889739d276d4e0a98d07d698ff07dac","isAssign":1,"isDeal":2,"node":3,"ownOrg":3},{"assignNode":3,"dealDate":"2019-08-08 09:11:23","dealOpin":"同意","dealPerson":"系统管理员","dealResult":"同意","id":"b0ad11333e614a7586e27a1c8c128c5e","isAssign":3,"isDeal":2,"node":20,"ownOrg":4},{"dealDate":"2019-08-08 09:11:59","dealOpin":"同意","dealPerson":"杨方红","dealResult":"同意","id":"765a178ddc1448dbb177207579c31b5e","isAssign":0,"isDeal":2,"node":4,"ownOrg":5}]
     */

    private DataBean data;
    private int isAssign;
    private int permission;
    private List<FileResultListBean> fileResultList;
    private List<RecordListBean> recordList;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getIsAssign() {
        return isAssign;
    }

    public void setIsAssign(int isAssign) {
        this.isAssign = isAssign;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public List<FileResultListBean> getFileResultList() {
        return fileResultList;
    }

    public void setFileResultList(List<FileResultListBean> fileResultList) {
        this.fileResultList = fileResultList;
    }

    public List<RecordListBean> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<RecordListBean> recordList) {
        this.recordList = recordList;
    }

    public static class DataBean {
        /**
         * createPerosn : 系统管理员
         * id : 3de5ba73e7ce4d21983237ed60a3dfdf
         * orgName : 测试一标
         * projectDescription : 爱的发声大地方
         * specialItemBaseName : 基坑开挖专项施工方案
         * specialItemDelName : 基坑开挖专项施工方案
         * specialItemMainName : 安卓测试新增
         * submitDate : 2019-08-08 09:07:42
         */

        private String createPerosn;
        private String id;
        private String orgName;
        private String projectDescription;
        private String specialItemBaseName;
        private String specialItemDelName;
        private String specialItemMainName;
        private String submitDate;

        public String getCreatePerosn() {
            return createPerosn;
        }

        public void setCreatePerosn(String createPerosn) {
            this.createPerosn = createPerosn;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getProjectDescription() {
            return projectDescription;
        }

        public void setProjectDescription(String projectDescription) {
            this.projectDescription = projectDescription;
        }

        public String getSpecialItemBaseName() {
            return specialItemBaseName;
        }

        public void setSpecialItemBaseName(String specialItemBaseName) {
            this.specialItemBaseName = specialItemBaseName;
        }

        public String getSpecialItemDelName() {
            return specialItemDelName;
        }

        public void setSpecialItemDelName(String specialItemDelName) {
            this.specialItemDelName = specialItemDelName;
        }

        public String getSpecialItemMainName() {
            return specialItemMainName;
        }

        public void setSpecialItemMainName(String specialItemMainName) {
            this.specialItemMainName = specialItemMainName;
        }

        public String getSubmitDate() {
            return submitDate;
        }

        public void setSubmitDate(String submitDate) {
            this.submitDate = submitDate;
        }
    }

    public static class FileResultListBean {
        /**
         * fileName : WBS结构划分（沱溪河大桥）.xlsx
         * fileUrl : upload/2019/08/08/dbd084924065480583a32ae014b6302c.pdf
         * onlineRead : 2
         */

        private String fileName;
        private String fileUrl;
        private int onlineRead;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        public int getOnlineRead() {
            return onlineRead;
        }

        public void setOnlineRead(int onlineRead) {
            this.onlineRead = onlineRead;
        }
    }

    public static class RecordListBean {
        /**
         * dealDate : 2019-08-08 09:08:08
         * dealOpin : 通过
         * dealPerson : 周冉
         * dealResult : 同意
         * id : df4f1361117d41cf8341356e551540ec
         * isAssign : 0
         * isDeal : 2
         * node : 1
         * ownOrg : 1
         * assignNode : 3
         */

        private String dealDate;
        private String dealOpin;
        private String dealPerson;
        private String dealResult;
        private String id;
        private int isAssign;
        private int isDeal;
        private int node;
        private int ownOrg;
        private int assignNode;

        public String getDealDate() {
            return dealDate;
        }

        public void setDealDate(String dealDate) {
            this.dealDate = dealDate;
        }

        public String getDealOpin() {
            return dealOpin;
        }

        public void setDealOpin(String dealOpin) {
            this.dealOpin = dealOpin;
        }

        public String getDealPerson() {
            return dealPerson;
        }

        public void setDealPerson(String dealPerson) {
            this.dealPerson = dealPerson;
        }

        public String getDealResult() {
            return dealResult;
        }

        public void setDealResult(String dealResult) {
            this.dealResult = dealResult;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIsAssign() {
            return isAssign;
        }

        public void setIsAssign(int isAssign) {
            this.isAssign = isAssign;
        }

        public int getIsDeal() {
            return isDeal;
        }

        public void setIsDeal(int isDeal) {
            this.isDeal = isDeal;
        }

        public int getNode() {
            return node;
        }

        public void setNode(int node) {
            this.node = node;
        }

        public int getOwnOrg() {
            return ownOrg;
        }

        public void setOwnOrg(int ownOrg) {
            this.ownOrg = ownOrg;
        }

        public int getAssignNode() {
            return assignNode;
        }

        public void setAssignNode(int assignNode) {
            this.assignNode = assignNode;
        }
    }
}
