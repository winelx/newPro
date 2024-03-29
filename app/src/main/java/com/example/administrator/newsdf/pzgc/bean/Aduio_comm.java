package com.example.administrator.newsdf.pzgc.bean;

import java.util.ArrayList;

/**
 *
 * @author Administrator
 * @date 2017/12/13 0013
 * 详情回复
 */

public class Aduio_comm {
    String id;//唯一标识
    String replyId;//回复人ID
    String replyUserName;//回复人姓名(路径：comments –> user -> realname)
    String replyUserHeaderURL;//回复人头像(路径：comments –> user -> portrait)
    String taskId;//
    String status;//
    String statusName;//
    String content;//Pinglun内容说明
    String replyTime;//评论时间
    ArrayList<String> filePathsMin;
    ArrayList<String> filePaths;

    public Aduio_comm(String id, String replyId, String replyUserName,
                      String replyUserHeaderURL, String taskId, String status,
                      String statusName, String content, String replyTime, ArrayList<String> filePathsMin,
                      ArrayList<String> filePaths) {
        this.id = id;
        this.replyId = replyId;
        this.replyUserName = replyUserName;
        this.replyUserHeaderURL = replyUserHeaderURL;
        this.taskId = taskId;
        this.status = status;
        this.statusName = statusName;
        this.content = content;
        this.replyTime = replyTime;
        this.filePathsMin = filePathsMin;
        this.filePaths = filePaths;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    public ArrayList<String> getFilePathsMin() {
        return filePathsMin;
    }

    public void setFilePathsMin(ArrayList<String> filePathsMin) {
        this.filePathsMin = filePathsMin;
    }

    public ArrayList<String> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(ArrayList<String> filePaths) {
        this.filePaths = filePaths;
    }
}
