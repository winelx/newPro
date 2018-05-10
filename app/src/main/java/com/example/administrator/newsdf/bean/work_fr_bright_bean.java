package com.example.administrator.newsdf.bean;

/**
 * Created by Administrator on 2018/5/10 0010.
 */

public class work_fr_bright_bean {
    String id;
    String orgid;
    String orgName;
    String leadername;
    String laederImg;
    int type;

    public work_fr_bright_bean(String id, String orgid, String orgName, String leadername, String laederImg, int type) {
        this.id = id;
        this.orgid = orgid;
        this.orgName = orgName;
        this.leadername = leadername;
        this.laederImg = laederImg;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getLeadername() {
        return leadername;
    }

    public void setLeadername(String leadername) {
        this.leadername = leadername;
    }

    public String getLaederImg() {
        return laederImg;
    }

    public void setLaederImg(String laederImg) {
        this.laederImg = laederImg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
