package com.example.administrator.fengji.pzgc.bean;

import com.example.administrator.fengji.pzgc.activity.home.NoticeActivity;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/3/4 0004}
 * 描述：代办事项
 *{@link NoticeActivity}
 */
public class AgencyBean {

    /**
     * id : d3486d6f0262488498e47f7911b591d1
     * isDeal : 1
     * modelCode : ZGTZ-20190307-37
     * modelId : dfb78defaf0548ea8453a7f4d27f913d
     * modelName : 整改通知单
     * modelType : 1
     * receiveOrgId : ce1d81430d874fd28728135e9ecd5865
     * receiveOrgName : 三独一标
     * receivePerson : ae680484f74d4504be797c8d16fb62e6
     * sendDate : 2019-03-07 17:18:48
     * sendOrgId : 7347f225f97e431fb8acfc7927e8f285
     * sendOrgName : 贵州路桥集团有限公司
     * sendPerson : 4028ea815a3d2a8c015a3d2f8d2a0002
     */

    private String id;
    private int isDeal;
    private String modelCode;
    private String modelId;
    private String modelName;
    private int modelType;
    private String receiveOrgId;
    private String receiveOrgName;
    private String receivePerson;
    private String sendDate;
    private String sendOrgId;
    private String sendOrgName;
    private String sendPerson;

    private int totalCount;//任务数量
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(int isDeal) {
        this.isDeal = isDeal;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
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

    public String getReceiveOrgId() {
        return receiveOrgId;
    }

    public void setReceiveOrgId(String receiveOrgId) {
        this.receiveOrgId = receiveOrgId;
    }

    public String getReceiveOrgName() {
        return receiveOrgName;
    }

    public void setReceiveOrgName(String receiveOrgName) {
        this.receiveOrgName = receiveOrgName;
    }

    public String getReceivePerson() {
        return receivePerson;
    }

    public void setReceivePerson(String receivePerson) {
        this.receivePerson = receivePerson;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendOrgId() {
        return sendOrgId;
    }

    public void setSendOrgId(String sendOrgId) {
        this.sendOrgId = sendOrgId;
    }

    public String getSendOrgName() {
        return sendOrgName;
    }

    public void setSendOrgName(String sendOrgName) {
        this.sendOrgName = sendOrgName;
    }

    public String getSendPerson() {
        return sendPerson;
    }

    public void setSendPerson(String sendPerson) {
        this.sendPerson = sendPerson;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
