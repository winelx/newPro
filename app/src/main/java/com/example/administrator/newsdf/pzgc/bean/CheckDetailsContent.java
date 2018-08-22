package com.example.administrator.newsdf.pzgc.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/8 0008.
 */

public class CheckDetailsContent {

    String replyPersonName;
    String replyDescription;
    String replyDate;
    ArrayList<Audio> imageList;

    public CheckDetailsContent(String replyPersonName, String replyDescription, String replyDate, ArrayList<Audio> imageList) {
        this.replyPersonName = replyPersonName;
        this.replyDescription = replyDescription;
        this.replyDate = replyDate;
        this.imageList = imageList;
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

}
