package com.example.administrator.newsdf.bean;

/**
 * Created by Administrator on 2018/5/4 0004.
 */

public class MoretasklistBean {
    String createDate;
    String detetionName;
    String wbsId;
    String orgId;
    String id;

    public MoretasklistBean(String createDate, String detetionName, String wbsId, String orgId, String id) {
        this.createDate = createDate;
        this.detetionName = detetionName;
        this.wbsId = wbsId;
        this.orgId = orgId;
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDetetionName() {
        return detetionName;
    }

    public void setDetetionName(String detetionName) {
        this.detetionName = detetionName;
    }

    public String getWbsId() {
        return wbsId;
    }

    public void setWbsId(String wbsId) {
        this.wbsId = wbsId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
