package com.example.administrator.newsdf.pzgc.bean;

import java.util.ArrayList;

/**
 * Created by 10942 on 2018/8/23 0023.
 */

public class CheckDetailsContents {
    String replyPersonName;
    String replyDescription;
    String replyDate;
    String count;
    String stsuts;
    ArrayList<Audio> imageList;

    public CheckDetailsContents(String replyPersonName, String replyDescription, String replyDate, String count, String stsuts, ArrayList<Audio> imageList) {
        this.replyPersonName = replyPersonName;
        this.replyDescription = replyDescription;
        this.replyDate = replyDate;
        this.imageList = imageList;
        this.stsuts = stsuts;
        this.count = count;
    }

    public String getReplyPersonName() {
        return replyPersonName;
    }

    public void setReplyPersonName(String replyPersonName) {
        this.replyPersonName = replyPersonName;
    }

    public String getReplyDescription() {
        return replyDescription;
    }

    public void setReplyDescription(String replyDescription) {
        this.replyDescription = replyDescription;
    }

    public String getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(String replyDate) {
        this.replyDate = replyDate;
    }

    public ArrayList<Audio> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<Audio> imageList) {
        this.imageList = imageList;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getStsuts() {
        return stsuts;
    }

    public void setStsuts(String stsuts) {
        this.stsuts = stsuts;
    }
}
