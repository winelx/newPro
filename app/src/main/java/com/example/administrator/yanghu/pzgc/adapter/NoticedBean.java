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


    /**
     * beNoticeOrgId : ce1d81430d874fd28728135e9ecd5865
     * beNoticeOrgName : 三独一标
     * id : 3f6576af97e74ba89b41d31546aa7cca
     * modelId : 1bf7dfdcb6814db8a068628574b081c4
     * modelName : 整改通知单
     * modelType : 1
     * noticeCode : ZGTZ-20190307-30
     * noticeDate : 2019-03-07 14:08:10
     * noticeOrgId : 7347f225f97e431fb8acfc7927e8f285
     * noticePerson : ae680484f74d4504be797c8d16fb62e6
     * optionContent : 被下发
     * sendPerson : 4028ea815a3d2a8c015a3d2f8d2a0002
     * showContent : 三独一标【2019-03-07 14:08:10】被下发【整改通知单ZGTZ-20190307-30】
     */

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
    private int totalCount;//任务数量
    private int iwork;

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
