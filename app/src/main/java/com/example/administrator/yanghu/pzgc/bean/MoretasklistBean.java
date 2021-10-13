package com.example.administrator.yanghu.pzgc.bean;

/**
 * Created by Administrator on 2018/5/4 0004.
 */

public class MoretasklistBean {
    String uploadTime;
    String partContent;
    String id;
    String userid;
    boolean lean;

    public MoretasklistBean(String uploadTime, String partContent, String id) {
        this.uploadTime = uploadTime;

        this.partContent = partContent;

        this.id = id;
    }


    public MoretasklistBean(String uploadTime, String partContent, String id, String userid) {
        this.uploadTime = uploadTime;

        this.partContent = partContent;

        this.id = id;
        this.userid = userid;
    } public MoretasklistBean(String uploadTime, String partContent, String id, String userid,boolean lean) {
        this.uploadTime = uploadTime;

        this.partContent = partContent;

        this.id = id;
        this.userid = userid;
        this.lean = lean;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }


    public String getPartContent() {
        return partContent;
    }

    public void setPartContent(String partContent) {
        this.partContent = partContent;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public boolean isLean() {
        return lean;
    }

    public void setLean(boolean lean) {
        this.lean = lean;
    }
}
