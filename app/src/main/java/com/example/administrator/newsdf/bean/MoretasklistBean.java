package com.example.administrator.newsdf.bean;

/**
 * Created by Administrator on 2018/5/4 0004.
 */

public class MoretasklistBean {
    String uploadTime;
    String partContent;
    String id;


    public MoretasklistBean(String uploadTime, String partContent, String id) {
        this.uploadTime = uploadTime;

        this.partContent = partContent;

        this.id = id;
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
}
