package com.example.administrator.newsdf.pzgc.bean;

/**
 * Created by Administrator on 2018/8/20 0020.
 */

public class MyNoticeDataBean {
    //标题
    String partDetails ;
    //检查组织
    String checkOrgName ;
    //责任人
    String checkPersonName;
    //所属标段
    String rectificationOrgName;
    //更新时间
    String updateDate ;
    //积分
    String standardDelScore ;
    //检查类别
    String standardDelName ;
    //id
    String noticeId;
    //状态
    String status;
    //
    String motionNode;
    boolean isDeal;
    public MyNoticeDataBean(String partDetails, String checkOrgName, String checkPersonName,
                            String rectificationOrgName, String updateDate, String standardDelScore,
                            String standardDelName, String noticeId, String status,String motionNode,boolean isDeal) {
        this.partDetails = partDetails;
        this.checkOrgName = checkOrgName;
        this.checkPersonName = checkPersonName;
        this.rectificationOrgName = rectificationOrgName;
        this.updateDate = updateDate;
        this.standardDelScore = standardDelScore;
        this.standardDelName = standardDelName;
        this.noticeId = noticeId;
        this.status = status;
        this.motionNode =  motionNode;
        this.isDeal =  isDeal;
    }

    public String getPartDetails() {
        return partDetails;
    }

    public void setPartDetails(String partDetails) {
        this.partDetails = partDetails;
    }

    public String getCheckOrgName() {
        return checkOrgName;
    }

    public void setCheckOrgName(String checkOrgName) {
        this.checkOrgName = checkOrgName;
    }

    public String getCheckPersonName() {
        return checkPersonName;
    }

    public void setCheckPersonName(String checkPersonName) {
        this.checkPersonName = checkPersonName;
    }

    public String getRectificationOrgName() {
        return rectificationOrgName;
    }

    public void setRectificationOrgName(String rectificationOrgName) {
        this.rectificationOrgName = rectificationOrgName;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getStandardDelScore() {
        return standardDelScore;
    }

    public void setStandardDelScore(String standardDelScore) {
        this.standardDelScore = standardDelScore;
    }

    public String getStandardDelName() {
        return standardDelName;
    }

    public void setStandardDelName(String standardDelName) {
        this.standardDelName = standardDelName;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMotionNode() {
        return motionNode;
    }

    public void setMotionNode(String motionNode) {
        this.motionNode = motionNode;
    }

    public boolean isDeal() {
        return isDeal;
    }

    public void setDeal(boolean deal) {
        isDeal = deal;
    }
}
