package com.example.administrator.newsdf.pzgc.bean;

/**
 * Created by Administrator on 2018/5/4 0004.
 */

public class MoretasklistBean {
    String uploadTime;
    String partContent;
    String id;
    String userid;

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
}
