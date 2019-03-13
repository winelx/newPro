package com.example.administrator.newsdf.pzgc.bean;

import com.example.administrator.newsdf.pzgc.activity.home.HomeTaskDetailsActivity;

/**
 * @author lx
 * @data :2019/3/11 0011
 * @描述 : 今日完成任务标段详情
 * @see HomeTaskDetailsActivity
 */
public class TodayDetailsBean {
    /**
     * fOrgId : f41d8a54aa674d59b0e18f04bd266765
     * finishCount : 6810
     * orgId : 12e07f4cc22a4e659fe2228b4ac1bce9
     * orgName : 湄石二标
     * percentage : 1.61%
     * personName : 邓发义
     */

    private String fOrgId;
    private int finishCount;
    private String orgId;
    private String orgName;
    private String percentage;
    private String personName;

    public String getFOrgId() {
        return fOrgId;
    }

    public void setFOrgId(String fOrgId) {
        this.fOrgId = fOrgId;
    }

    public int getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(int finishCount) {
        this.finishCount = finishCount;
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

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
