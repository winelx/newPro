package com.example.administrator.newsdf.bean;

/**
 * Created by Administrator on 2018/4/25 0025.
 */

public class BrightBean {
    String id;
    String orgId;
    String orgName;
    String taskName;
    String leadername;
    String leaderImg;
    String updateDate;

    public BrightBean(String id, String orgId, String orgName, String taskName, String leadername, String leaderImg, String updateDate) {
        this.id = id;
        this.orgId = orgId;
        this.orgName = orgName;
        this.taskName = taskName;
        this.leadername = leadername;
        this.leaderImg = leaderImg;
        this.updateDate = updateDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getLeadername() {
        return leadername;
    }

    public void setLeadername(String leadername) {
        this.leadername = leadername;
    }

    public String getLeaderImg() {
        return leaderImg;
    }

    public void setLeaderImg(String leaderImg) {
        this.leaderImg = leaderImg;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
