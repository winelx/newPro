package com.example.administrator.yanghu.pzgc.bean;

import java.io.Serializable;

public class ChagedNoticeDetails implements Serializable {

    /**
     * acceptPerson : 2e7dbcfd86a84a1ba5919290a924995f
     * auserName : 周宇
     * code : ZGTZ-20190220-13
     * dealId : f75a885d48604a768a0f06ad5d13f04f
     * details : []
     * id : 1b40c46db9f64c728b5a713ccb9b9421
     * motionNode : 1
     * noticeCount : -2
     * noticeFinishCount : 0
     * permission : 1
     * rectificationOrgid : ce1d81430d874fd28728135e9ecd5865
     * rectificationPerson : b0a235e19e0b423299da1a211037a6a5
     * rorgName : 三独一标
     * ruserName : 胡扬
     * sendOrgid : ce1d81430d874fd28728135e9ecd5865
     * sorgName : 三独一标
     * status : 0
     */

    private String acceptPerson;
    private String auserName;
    private String code;
    private String dealId;
    private String id;
    private int motionNode;
    private int noticeCount;
    private int noticeFinishCount;
    private int permission;
    private String send_date;
    private String rectificationOrgid;
    private String rectificationPerson;
    private String rorgName;
    private String ruserName;
    private String sendOrgid;
    private String sorgName;
    private String sendUserName;
    private int status;

    public String getAcceptPerson() {
        return acceptPerson;
    }

    public void setAcceptPerson(String acceptPerson) {
        this.acceptPerson = acceptPerson;
    }

    public String getAuserName() {
        return auserName;
    }

    public void setAuserName(String auserName) {
        this.auserName = auserName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMotionNode() {
        return motionNode;
    }

    public void setMotionNode(int motionNode) {
        this.motionNode = motionNode;
    }

    public int getNoticeCount() {
        return noticeCount;
    }

    public void setNoticeCount(int noticeCount) {
        this.noticeCount = noticeCount;
    }

    public int getNoticeFinishCount() {
        return noticeFinishCount;
    }

    public void setNoticeFinishCount(int noticeFinishCount) {
        this.noticeFinishCount = noticeFinishCount;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public String getRectificationOrgid() {
        return rectificationOrgid;
    }

    public void setRectificationOrgid(String rectificationOrgid) {
        this.rectificationOrgid = rectificationOrgid;
    }

    public String getRectificationPerson() {
        return rectificationPerson;
    }

    public void setRectificationPerson(String rectificationPerson) {
        this.rectificationPerson = rectificationPerson;
    }

    public String getRorgName() {
        return rorgName;
    }

    public void setRorgName(String rorgName) {
        this.rorgName = rorgName;
    }

    public String getRuserName() {
        return ruserName;
    }

    public void setRuserName(String ruserName) {
        this.ruserName = ruserName;
    }

    public String getSendOrgid() {
        return sendOrgid;
    }

    public void setSendOrgid(String sendOrgid) {
        this.sendOrgid = sendOrgid;
    }

    public String getSorgName() {
        return sorgName;
    }

    public void setSorgName(String sorgName) {
        this.sorgName = sorgName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getSend_date() {
        return send_date;
    }

    public void setSend_date(String send_date) {
        this.send_date = send_date;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }
}
