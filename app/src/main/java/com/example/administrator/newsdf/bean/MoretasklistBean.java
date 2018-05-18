package com.example.administrator.newsdf.bean;

/**
 * Created by Administrator on 2018/5/4 0004.
 */

public class MoretasklistBean {
    String uploadTime;
    String uploadName;
    String partContent;
    String portrait;
    String id;


    public MoretasklistBean(String uploadTime, String uploadName, String partContent, String portrait, String id) {
        this.uploadTime = uploadTime;
        this.uploadName = uploadName;
        this.partContent = partContent;
        this.portrait = portrait;
        this.id = id;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUploadName() {
        return uploadName;
    }

    public void setUploadName(String uploadName) {
        this.uploadName = uploadName;
    }

    public String getPartContent() {
        return partContent;
    }

    public void setPartContent(String partContent) {
        this.partContent = partContent;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
