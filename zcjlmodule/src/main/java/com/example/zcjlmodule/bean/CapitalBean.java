package com.example.zcjlmodule.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author lx
 * @Created by: 2018/11/26 0026.
 * @description:
 */

public class CapitalBean {
    /**
     * attachment :
     * attachmentCount : 0
     * auditStatus : 0
     * createBy : {"credentialsSalt":"nullnull","delFlag":"0","id":"4028ea815a3d2a8c015a3d2f8d2a0002","new":false,"realname":"系统管理员","status":"1"}
     * createDate : 2018-11-26 11:35:17
     * delFlag : 0
     * id : c2dc318a5f1a472d8cd4b2ad4e93ed22
     * new : false
     * orgId : c4fcd77b464744b28f6977c51958ba8d
     * period : 2
     * periodId : 9db2a9b2522f43a2aded67ed137e1b38
     * periodNumber : 第2期
     * remarks : 第二期
     * totalApplyAmount : 5.591290335E7
     * updateBy : {"credentialsSalt":"nullnull","delFlag":"0","id":"4028ea815a3d2a8c015a3d2f8d2a0002","new":false,"status":"1"}
     * updateDate : 2018-11-26 11:35:23
     */

    private String attachment;
    private int attachmentCount;
    private int auditStatus;
    private CreateByBean createBy;
    private String createDate;
    private String delFlag;
    private String id;
    private boolean newX;
    private String orgId;
    private int period;
    private String periodId;
    private String periodNumber;
    private String remarks;
    private String totalApplyAmount;
    private String updateDate;

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public int getAttachmentCount() {
        return attachmentCount;
    }

    public void setAttachmentCount(int attachmentCount) {
        this.attachmentCount = attachmentCount;
    }

    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }

    public CreateByBean getCreateBy() {
        return createBy;
    }

    public void setCreateBy(CreateByBean createBy) {
        this.createBy = createBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isNewX() {
        return newX;
    }

    public void setNewX(boolean newX) {
        this.newX = newX;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getPeriodId() {
        return periodId;
    }

    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    public String getPeriodNumber() {
        return periodNumber;
    }

    public void setPeriodNumber(String periodNumber) {
        this.periodNumber = periodNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTotalApplyAmount() {
        return totalApplyAmount;
    }

    public void setTotalApplyAmount(String totalApplyAmount) {
        this.totalApplyAmount = totalApplyAmount;
    }


    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public static class CreateByBean {
        /**
         * credentialsSalt : nullnull
         * delFlag : 0
         * id : 4028ea815a3d2a8c015a3d2f8d2a0002
         * new : false
         * realname : 系统管理员
         * status : 1
         */

        private String credentialsSalt;
        private String delFlag;
        private String id;
        private boolean newX;
        private String realname;
        private String status;

        public String getCredentialsSalt() {
            return credentialsSalt;
        }

        public void setCredentialsSalt(String credentialsSalt) {
            this.credentialsSalt = credentialsSalt;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isNewX() {
            return newX;
        }

        public void setNewX(boolean newX) {
            this.newX = newX;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


}
