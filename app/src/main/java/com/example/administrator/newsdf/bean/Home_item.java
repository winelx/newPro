package com.example.administrator.newsdf.bean;

/**
 * 作者：winelx
 * 时间：2017/11/28 0028:下午 16:11
 * 说明：
 */
public class Home_item {

    String content;
    String creaeTime;
    String id;
    String orgid;
    String orgname;
    String unfinish;
    String isfavorite;
    boolean putTop;
    public Home_item(String content, String creaeTime, String id, String orgid, String orgname, String unfinish,String isfavorite,boolean putTop) {
        this.content = content;
        this.creaeTime = creaeTime;
        this.id = id;
        this.orgid = orgid;
        this.orgname = orgname;
        this.unfinish = unfinish;
        this.isfavorite = isfavorite;
        this.putTop = putTop;

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreaeTime() {
        return creaeTime;
    }

    public void setCreaeTime(String creaeTime) {
        this.creaeTime = creaeTime;
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

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getUnfinish() {
        return unfinish;
    }

    public void setUnfinish(String unfinish) {
        this.unfinish = unfinish;
    }

    public boolean isPutTop() {
        return putTop;
    }
    public void setPutTop(boolean putTop) {
        this.putTop = putTop;
    }

    public String getIsfavorite() {
        return isfavorite;
    }

    public void setIsfavorite(String isfavorite) {
        this.isfavorite = isfavorite;
    }
}
