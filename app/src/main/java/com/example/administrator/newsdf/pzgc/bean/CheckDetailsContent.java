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


    String replyPersonName2;
    String replyDescription2;
    String replyDate2;
    ArrayList<Audio> imageList2;


    public CheckDetailsContent( String replyPersonName, String replyDescription, String replyDate, ArrayList<Audio> imageList, String replyPersonName2, String replyDescription2, String replyDate2, ArrayList<Audio> imageList2) {

        this.replyPersonName = replyPersonName;
        this.replyDescription = replyDescription;
        this.replyDate = replyDate;
        this.imageList = imageList;

        this.replyPersonName2 = replyPersonName2;
        this.replyDescription2 = replyDescription2;
        this.replyDate2 = replyDate2;
        this.imageList2 = imageList2;
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



    public String getReplyPersonName2() {
        return replyPersonName2;
    }

    public void setReplyPersonName2(String replyPersonName2) {
        this.replyPersonName2 = replyPersonName2;
    }

    public String getReplyDescription2() {
        return replyDescription2;
    }

    public void setReplyDescription2(String replyDescription2) {
        this.replyDescription2 = replyDescription2;
    }

    public String getReplyDate2() {
        return replyDate2;
    }

    public void setReplyDate2(String replyDate2) {
        this.replyDate2 = replyDate2;
    }

    public ArrayList<Audio> getImageList2() {
        return imageList2;
    }

    public void setImageList2(ArrayList<Audio> imageList2) {
        this.imageList2 = imageList2;
    }
}
