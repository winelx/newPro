package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean;

public class ExternalCheckListBean {
    //待处理人
    String dealPerson;
    //单据状态
    Integer status;
    //检查日期
    String checkDate;
    //得分
    String totalSocre;
    //检查人名称
    String checkPersonName;
    //检查组织名称
    String checkOrgName;
    //单据名称
    String name;
    String id;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getTotalSocre() {
        return totalSocre;
    }

    public void setTotalSocre(String totalSocre) {
        this.totalSocre = totalSocre;
    }

    public String getCheckPersonName() {
        return checkPersonName;
    }

    public void setCheckPersonName(String checkPersonName) {
        this.checkPersonName = checkPersonName;
    }

    public String getCheckOrgName() {
        return checkOrgName;
    }

    public void setCheckOrgName(String checkOrgName) {
        this.checkOrgName = checkOrgName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
