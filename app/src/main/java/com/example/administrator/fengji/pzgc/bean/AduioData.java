package com.example.administrator.fengji.pzgc.bean;

import java.util.ArrayList;

/**
 * 详情内容
 * Created by Administrator on 2017/12/13 0013.
 */

public class AduioData {
    String replyID;//唯一标识
    String uploadId;//上传人ID
    String replyUserName;// 上传人姓名 （路径：subWbsTaskMains  -> uploadUser -> realname）
    String replyUserHeaderURL;// 上传人头像  （ 路径：subWbsTaskMains  -> uploadUser -> portrait）

    String name;///检查点
    String wbsName;//wbsName
    String uploadContent;//上传内容说明
    String updateDate;//上传时间
    String uploadAddr;//上传地点
    //第一级
    boolean smartprojectMain1Down;
    boolean smartprojectMain1Up;
    //第二级
    boolean smartprojectMain2Down;
    boolean smartprojectMain2Up;
    //第三级
    boolean smartprojectMain3Down;//打回s
    boolean smartprojectMain3Up;//打回人ID

    ArrayList<String> attachments;//附件list（
    ArrayList<String> filename;//附件list（
    String commentCount; //评论条数
    String userpath;
    //提亮
    int isSmartProject;
    //是否收藏
    boolean isFavorite;
    int smartProjectType;

    public AduioData(String replyID, String uploadId, String replyUserName,
                     String replyUserHeaderURL, String name, String wbsName, String uploadContent,
                     String updateDate, String uploadAddr, boolean smartprojectMain1Down, boolean smartprojectMain1Up,
                     boolean smartprojectMain2Down, boolean smartprojectMain2Up, boolean smartprojectMain3Down,
                     boolean smartprojectMain3Up, ArrayList<String> attachments, String commentCount, String userpath, ArrayList<String> filename, int isSmartProject
            , boolean isFavorite, int smartProjectType
    ) {
        this.replyID = replyID;
        this.uploadId = uploadId;
        this.replyUserName = replyUserName;
        this.replyUserHeaderURL = replyUserHeaderURL;
        this.name = name;
        this.wbsName = wbsName;
        this.uploadContent = uploadContent;
        this.updateDate = updateDate;
        this.uploadAddr = uploadAddr;
        this.smartprojectMain1Down = smartprojectMain1Down;
        this.smartprojectMain1Up = smartprojectMain1Up;
        this.smartprojectMain2Down = smartprojectMain2Down;
        this.smartprojectMain2Up = smartprojectMain2Up;
        this.smartprojectMain3Down = smartprojectMain3Down;
        this.smartprojectMain3Up = smartprojectMain3Up;
        this.attachments = attachments;
        this.commentCount = commentCount;
        this.userpath = userpath;
        this.filename = filename;
        this.isSmartProject = isSmartProject;
        this.isFavorite = isFavorite;
        this.smartProjectType = smartProjectType;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getReplyID() {
        return replyID;
    }

    public void setReplyID(String replyID) {
        this.replyID = replyID;
    }

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public String getReplyUserName() {
        return replyUserName;
    }

    public void setReplyUserName(String replyUserName) {
        this.replyUserName = replyUserName;
    }

    public String getReplyUserHeaderURL() {
        return replyUserHeaderURL;
    }

    public void setReplyUserHeaderURL(String replyUserHeaderURL) {
        this.replyUserHeaderURL = replyUserHeaderURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWbsName() {
        return wbsName;
    }

    public void setWbsName(String wbsName) {
        this.wbsName = wbsName;
    }

    public String getUploadContent() {
        return uploadContent;
    }

    public void setUploadContent(String uploadContent) {
        this.uploadContent = uploadContent;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUploadAddr() {
        return uploadAddr;
    }

    public void setUploadAddr(String uploadAddr) {
        this.uploadAddr = uploadAddr;
    }

    public boolean isSmartprojectMain1Down() {
        return smartprojectMain1Down;
    }

    public void setSmartprojectMain1Down(boolean smartprojectMain1Down) {
        this.smartprojectMain1Down = smartprojectMain1Down;
    }

    public boolean isSmartprojectMain1Up() {
        return smartprojectMain1Up;
    }

    public void setSmartprojectMain1Up(boolean smartprojectMain1Up) {
        this.smartprojectMain1Up = smartprojectMain1Up;
    }

    public boolean isSmartprojectMain2Down() {
        return smartprojectMain2Down;
    }

    public void setSmartprojectMain2Down(boolean smartprojectMain2Down) {
        this.smartprojectMain2Down = smartprojectMain2Down;
    }

    public boolean isSmartprojectMain2Up() {
        return smartprojectMain2Up;
    }

    public void setSmartprojectMain2Up(boolean smartprojectMain2Up) {
        this.smartprojectMain2Up = smartprojectMain2Up;
    }

    public boolean isSmartprojectMain3Down() {
        return smartprojectMain3Down;
    }

    public void setSmartprojectMain3Down(boolean smartprojectMain3Down) {
        this.smartprojectMain3Down = smartprojectMain3Down;
    }

    public boolean isSmartprojectMain3Up() {
        return smartprojectMain3Up;
    }

    public void setSmartprojectMain3Up(boolean smartprojectMain3Up) {
        this.smartprojectMain3Up = smartprojectMain3Up;
    }

    public ArrayList<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<String> attachments) {
        this.attachments = attachments;
    }

    public String getUserpath() {
        return userpath;
    }

    public void setUserpath(String userpath) {
        this.userpath = userpath;
    }

    public ArrayList<String> getFilename() {
        return filename;
    }

    public void setFilename(ArrayList<String> filename) {
        this.filename = filename;
    }

    public int getIsSmartProject() {
        return isSmartProject;
    }

    public void setIsSmartProject(int isSmartProject) {
        this.isSmartProject = isSmartProject;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }


    public int getSmartProjectType() {
        return smartProjectType;
    }

    public void setSmartProjectType(int smartProjectType) {
        this.smartProjectType = smartProjectType;
    }
}
