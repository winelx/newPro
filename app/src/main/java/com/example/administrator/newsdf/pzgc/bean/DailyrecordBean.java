package com.example.administrator.newsdf.pzgc.bean;

/**
 * Created by Administrator on 2018/7/18 0018.
 * 任务审核报表
 */

public class DailyrecordBean {
    String position;//岗位
    String personName;//责任人
    String percentage;//百分比
    String noAuditCount;//未审核
    String auditCount;//已审核
    String waitTask;//待审核
    String timeout;//超时
    String orgName;//所属标段
    String aimsTask;//目标任务

    public DailyrecordBean(String position, String personName, String percentage, String noAuditCount, String auditCount, String waitTask, String timeout, String orgName,String aimsTask) {
        this.position = position;
        this.personName = personName;
        this.percentage = percentage;
        this.noAuditCount = noAuditCount;
        this.auditCount = auditCount;
        this.waitTask = waitTask;
        this.timeout = timeout;
        this.orgName = orgName;
        this.aimsTask = aimsTask;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getNoAuditCount() {
        return noAuditCount;
    }

    public void setNoAuditCount(String noAuditCount) {
        this.noAuditCount = noAuditCount;
    }

    public String getAuditCount() {
        return auditCount;
    }

    public void setAuditCount(String auditCount) {
        this.auditCount = auditCount;
    }

    public String getWaitTask() {
        return waitTask;
    }

    public void setWaitTask(String waitTask) {
        this.waitTask = waitTask;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAimsTask() {
        return aimsTask;
    }

    public void setAimsTask(String aimsTask) {
        this.aimsTask = aimsTask;
    }
}
