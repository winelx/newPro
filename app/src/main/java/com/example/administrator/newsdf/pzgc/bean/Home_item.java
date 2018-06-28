package com.example.administrator.newsdf.pzgc.bean;

/**
 * 作者：winelx
 * 时间：2017/11/28 0028:下午 16:11
 * 说明：
 */
public class Home_item {
    /**
     * 内存
     */

    String content;
    /**
     * 创建时间
     */
    String creaeTime;
    /**
     * id
     */
    String id;
    /**
     * 组织ID
     */
    String orgid;
    /**
     * 组织名称
     */
    String orgname;
    /**
     * 消息数量
     */
    String unfinish;
    /**
     * 是否收藏
     */
    String isfavorite;
    /**
     * 是否置顶
     */
    boolean putTop;
    /**
     * 组织所属公司
     */
    String parentname;
    /**
     * 组织所属公司Id
     */
    String parentid;

    public Home_item(String content, String creaeTime, String id, String orgid, String orgname, String unfinish, String isfavorite, String parentname, String parentid, boolean putTop) {
        this.content = content;
        this.creaeTime = creaeTime;
        this.id = id;
        this.orgid = orgid;
        this.orgname = orgname;
        this.unfinish = unfinish;
        this.isfavorite = isfavorite;
        this.putTop = putTop;
        this.parentname = parentname;
        this.parentid = parentid;



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

    public String getParentname() {
        return parentname;
    }

    public void setParentname(String parentname) {
        this.parentname = parentname;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

}
