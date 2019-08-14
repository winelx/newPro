package com.example.administrator.newsdf.pzgc.special.programme.bean;

import java.util.List;

public class ProDetails {


    /**
     * recordList : [{"isDeal":5.310544805258483E7,"id":"magna Ut aute","dealPerson":"pariatur esse dolor Ut","node":-8.540590825336075E7,"dealOpin":"voluptate pariatur ","isAssign":2.1036435501694635E7,"ownOrg":9.060384313702509E7,"dealResult":"eiusmod cupidatat cillum","dealDate":"sed anim culpa cupidatat","assignNode":1.9533709005962715E7},{"isDeal":-7.602899418081307E7,"id":"Duis sed in Ut","dealPerson":"al","node":2.7368872204831213E7,"dealOpin":"deserunt eu aliqua com","isAssign":-9.566399046215732E7,"ownOrg":6.427320052246049E7,"dealResult":"labore cupidatat te","dealDate":"","assignNode":-5.1851517714489505E7}]
     * data : {"id":"sit anim aute","specialItemDelName":"aute ipsum aliquip","specialItemMainName":"nisi molli","createPerosn":"irure Excepteur magna","submitDate":"do consectetu","projectDescription":"exercitation nulla commodo do","orgName":"incididunt consectetur","specialItemBaseName":"ex ullamco incididunt"}
     * fileResultList : [{"fileName":"elit officia ut anim laboris","onlineRead":-9.388577447779855E7,"fileUrl":"dolore dolore in"},{"fileName":"in laboris aute","onlineRead":5.7338981605532706E7,"fileUrl":"consectetur exercitation esse et irure"},{"fileName":"ipsum","onlineRead":-5.277889353784313E7,"fileUrl":"in"}]
     * permission : -4.122768881977077E7
     * isAssign : -9.529522531975245E7
     */

    private DataBean data;
    private int permission;
    private String isAssign;
    private List<RecordListBean> recordList;
    private List<FileResultListBean> fileResultList;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public String getIsAssign() {
        return isAssign;
    }

    public void setIsAssign(String isAssign) {
        this.isAssign = isAssign;
    }

    public List<RecordListBean> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<RecordListBean> recordList) {
        this.recordList = recordList;
    }

    public List<FileResultListBean> getFileResultList() {
        return fileResultList;
    }

    public void setFileResultList(List<FileResultListBean> fileResultList) {
        this.fileResultList = fileResultList;
    }

    public static class DataBean {
        /**
         * id : sit anim aute
         * specialItemDelName : aute ipsum aliquip
         * specialItemMainName : nisi molli
         * createPerosn : irure Excepteur magna
         * submitDate : do consectetu
         * projectDescription : exercitation nulla commodo do
         * orgName : incididunt consectetur
         * specialItemBaseName : ex ullamco incididunt
         */

        private String id;
        private String specialItemDelName;
        private String specialItemMainName;
        private String createPerosn;
        private String submitDate;
        private String projectDescription;
        private String orgName;
        private String specialItemBaseName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getCreatePerosn() {
            return createPerosn;
        }

        public void setCreatePerosn(String createPerosn) {
            this.createPerosn = createPerosn;
        }

        public String getSubmitDate() {
            return submitDate;
        }

        public void setSubmitDate(String submitDate) {
            this.submitDate = submitDate;
        }

        public String getProjectDescription() {
            return projectDescription;
        }

        public void setProjectDescription(String projectDescription) {
            this.projectDescription = projectDescription;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getSpecialItemBaseName() {
            return specialItemBaseName;
        }

        public void setSpecialItemBaseName(String specialItemBaseName) {
            this.specialItemBaseName = specialItemBaseName;
        }
    }

    public static class RecordListBean {
        /**
         * isDeal : 5.310544805258483E7
         * id : magna Ut aute
         * dealPerson : pariatur esse dolor Ut
         * node : -8.540590825336075E7
         * dealOpin : voluptate pariatur
         * isAssign : 2.1036435501694635E7
         * ownOrg : 9.060384313702509E7
         * dealResult : eiusmod cupidatat cillum
         * dealDate : sed anim culpa cupidatat
         * assignNode : 1.9533709005962715E7
         */

        private String isDeal;
        private String id;
        private String dealPerson;
        private String node;
        private String dealOpin;
        private String isAssign;
        private String ownOrg;
        private String dealResult;
        private String dealDate;
        private String assignNode;

        public String getIsDeal() {
            return isDeal;
        }

        public void setIsDeal(String isDeal) {
            this.isDeal = isDeal;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDealPerson() {
            return dealPerson;
        }

        public void setDealPerson(String dealPerson) {
            this.dealPerson = dealPerson;
        }

        public String getNode() {
            return node;
        }

        public void setNode(String node) {
            this.node = node;
        }

        public String getDealOpin() {
            return dealOpin;
        }

        public void setDealOpin(String dealOpin) {
            this.dealOpin = dealOpin;
        }

        public String getIsAssign() {
            return isAssign;
        }

        public void setIsAssign(String isAssign) {
            this.isAssign = isAssign;
        }

        public String getOwnOrg() {
            return ownOrg;
        }

        public void setOwnOrg(String ownOrg) {
            this.ownOrg = ownOrg;
        }

        public String getDealResult() {
            return dealResult;
        }

        public void setDealResult(String dealResult) {
            this.dealResult = dealResult;
        }

        public String getDealDate() {
            return dealDate;
        }

        public void setDealDate(String dealDate) {
            this.dealDate = dealDate;
        }

        public String getAssignNode() {
            return assignNode;
        }

        public void setAssignNode(String assignNode) {
            this.assignNode = assignNode;
        }
    }

    public static class FileResultListBean {
        /**
         * fileName : elit officia ut anim laboris
         * onlineRead : -9.388577447779855E7
         * fileUrl : dolore dolore in
         */

        private String fileName;
        private String onlineRead;
        private String fileUrl;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getOnlineRead() {
            return onlineRead;
        }

        public void setOnlineRead(String onlineRead) {
            this.onlineRead = onlineRead;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }
    }
}
