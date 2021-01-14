package com.example.administrator.fengji.pzgc.bean;

import com.example.administrator.fengji.pzgc.activity.home.HomeTaskDetailsActivity;

/**
 * @author lx
 * @data :2019/3/11 0011
 * @描述 : 今日完成任务标段详情
 * @see HomeTaskDetailsActivity
 */
public class TodayDetailsBean {

    /**
     * orgId : minim exercitation dolore nostrud
     * noFinishCount : 3.8778269832030624E7
     * totalTask : 1856348.0087019354
     * fOrgId : incididunt
     * orgName : ex dolor
     * FinishCount : 3.965085973310161E7
     * startUpTaskCount : 2.5134736915280923E7
     * toDayFinishCount : 5.710765245983359E7
     */

    private String orgId;  //标段Id
    private String noFinishCount;//未完成
    private String totalTask;//总任务
    private String fOrgId;//分公司Id
    private String orgName;//标段名称
    private String FinishCount;//已完成
    private String startUpTaskCount;//已启动
    private String toDayFinishCount;//今日完成
    private String noStartTaskCount;//未启动

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getNoFinishCount() {
        return noFinishCount;
    }

    public void setNoFinishCount(String noFinishCount) {
        this.noFinishCount = noFinishCount;
    }

    public String getTotalTask() {
        return totalTask;
    }

    public void setTotalTask(String totalTask) {
        this.totalTask = totalTask;
    }

    public String getfOrgId() {
        return fOrgId;
    }

    public void setfOrgId(String fOrgId) {
        this.fOrgId = fOrgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getFinishCount() {
        return FinishCount;
    }

    public void setFinishCount(String finishCount) {
        FinishCount = finishCount;
    }

    public String getStartUpTaskCount() {
        return startUpTaskCount;
    }

    public void setStartUpTaskCount(String startUpTaskCount) {
        this.startUpTaskCount = startUpTaskCount;
    }

    public String getToDayFinishCount() {
        return toDayFinishCount;
    }

    public void setToDayFinishCount(String toDayFinishCount) {
        this.toDayFinishCount = toDayFinishCount;
    }

    public String getNoStartTaskCount() {
        return noStartTaskCount;
    }

    public void setNoStartTaskCount(String noStartTaskCount) {
        this.noStartTaskCount = noStartTaskCount;
    }
}
