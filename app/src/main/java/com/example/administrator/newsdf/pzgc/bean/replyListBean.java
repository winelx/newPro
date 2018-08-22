package com.example.administrator.newsdf.pzgc.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/22 0022.
 */

public class replyListBean {
    String replyPersonName;
    String replyDescription;
    String replyDate;
    String motionCount;
    ArrayList<Audio> replyimgList;

    public replyListBean(String replyPersonName, String replyDescription, String replyDate, String motionCount, ArrayList<Audio> replyimgList) {
        this.replyPersonName = replyPersonName;
        this.replyDescription = replyDescription;
        this.replyDate = replyDate;
        this.motionCount = motionCount;
        this.replyimgList = replyimgList;
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

    public ArrayList<Audio> getReplyimgList() {
        return replyimgList;
    }

    public void setReplyimgList(ArrayList<Audio> replyimgList) {
        this.replyimgList = replyimgList;
    }

    public String getMotionCount() {
        return motionCount;
    }

    public void setMotionCount(String motionCount) {
        this.motionCount = motionCount;
    }
}
