package com.example.administrator.yanghu.pzgc.adapter;

import com.example.administrator.yanghu.pzgc.activity.home.NoticeActivity;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/3/4 0004}
 * 描述：消息通知
 * {@link NoticeActivity}
 */
public class NoticedBean {

    private String beNoticeOrgId;
    private String beNoticeOrgName;
    private String id;
    private String isRead;
    private String modelId;
    private String modelName;
    private String sysMsgNoticeId;
    private int modelType;
    private String noticeCode;
    private String noticeDate;
    private String noticeOrgId;
    private String noticePerson;
    private String optionContent;
    private String sendPerson;
    private String showContent;
    private String authority;
    private String appFormUrl;
    private String taskid;
    private int totalCount;//任务数量
    private int iwork;

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getAppFormUrl() {
        return appFormUrl;
    }

    public void setAppFormUrl(String appFormUrl) {
        this.appFormUrl = appFormUrl;
    }

    public String getSysMsgNoticeId() {
        return sysMsgNoticeId;
    }

    public void setSysMsgNoticeId(String sysMsgNoticeId) {
        this.sysMsgNoticeId = sysMsgNoticeId;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getBeNoticeOrgId() {
        return beNoticeOrgId;
    }

    public void setBeNoticeOrgId(String beNoticeOrgId) {
        this.beNoticeOrgId = beNoticeOrgId;
    }

    public String getBeNoticeOrgName() {
        return beNoticeOrgName;
    }

    public void setBeNoticeOrgName(String beNoticeOrgName) {
        this.beNoticeOrgName = beNoticeOrgName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getModelType() {
        return modelType;
    }

    public void setModelType(int modelType) {
        this.modelType = modelType;
    }

    public String getNoticeCode() {
        return noticeCode;
    }

    public void setNoticeCode(String noticeCode) {
        this.noticeCode = noticeCode;
    }

    public String getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(String noticeDate) {
        this.noticeDate = noticeDate;
    }

    public String getNoticeOrgId() {
        return noticeOrgId;
    }

    public void setNoticeOrgId(String noticeOrgId) {
        this.noticeOrgId = noticeOrgId;
    }

    public String getNoticePerson() {
        return noticePerson;
    }

    public void setNoticePerson(String noticePerson) {
        this.noticePerson = noticePerson;
    }

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public String getSendPerson() {
        return sendPerson;
    }

    public void setSendPerson(String sendPerson) {
        this.sendPerson = sendPerson;
    }

    public String getShowContent() {
        return showContent;
    }

    public void setShowContent(String showContent) {
        this.showContent = showContent;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getIwork() {
        return iwork;
    }

    public void setIwork(int iwork) {
        this.iwork = iwork;
    }
}
