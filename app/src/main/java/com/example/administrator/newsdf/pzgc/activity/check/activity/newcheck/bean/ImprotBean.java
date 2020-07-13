package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean;

public class ImprotBean {
    String id;
    String   orgId; //组织id
    String   orgName; //组织名称
    String   name; //组织名称
    String   checkType;//检查类型{8：安全检查；9:质量检查}
    String   wbsTaskTypeId;//工程类型id
    String   wbsTaskTypeName;//工程类型名称
    String   checkDate;//检查日期
    String   checkOrgId;///检查组织id
    String   checkOrgName;//检查组织名称
    String   checkPersonId;//检查人id
    String   checkPersonName;//检查人名称
    String   status;//状态
    String   fTotalSocre;//


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getfTotalSocre() {
        return fTotalSocre;
    }

    public void setfTotalSocre(String fTotalSocre) {
        this.fTotalSocre = fTotalSocre;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getWbsTaskTypeId() {
        return wbsTaskTypeId;
    }

    public void setWbsTaskTypeId(String wbsTaskTypeId) {
        this.wbsTaskTypeId = wbsTaskTypeId;
    }

    public String getWbsTaskTypeName() {
        return wbsTaskTypeName;
    }

    public void setWbsTaskTypeName(String wbsTaskTypeName) {
        this.wbsTaskTypeName = wbsTaskTypeName;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckOrgId() {
        return checkOrgId;
    }

    public void setCheckOrgId(String checkOrgId) {
        this.checkOrgId = checkOrgId;
    }

    public String getCheckOrgName() {
        return checkOrgName;
    }

    public void setCheckOrgName(String checkOrgName) {
        this.checkOrgName = checkOrgName;
    }

    public String getCheckPersonId() {
        return checkPersonId;
    }

    public void setCheckPersonId(String checkPersonId) {
        this.checkPersonId = checkPersonId;
    }

    public String getCheckPersonName() {
        return checkPersonName;
    }

    public void setCheckPersonName(String checkPersonName) {
        this.checkPersonName = checkPersonName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
