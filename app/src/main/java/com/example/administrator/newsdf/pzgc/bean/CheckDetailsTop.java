package com.example.administrator.newsdf.pzgc.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/8 0008.
 */

public class CheckDetailsTop {
    String wbspath;
    String sendPersonName;
    String sendDate;
    //所属标段
    String rectificationOrgName;
    //违反标准
    String standardDelName;
    //整改事由
    String checkplan;
    //检查组织
    String checkOrgName;
    //附件
    ArrayList<Audio> attachmentList;
    //整改负责人
    String rectificationPersonName;
    //整改最后时间
    String rectificationDate;
    //通知状态
    String status;

    public CheckDetailsTop(String wbspath, String sendPersonName, String sendDate, String rectificationOrgName, String standardDelName, String checkplan, String checkOrgName, ArrayList<Audio> attachmentList, String rectificationPersonName, String rectificationDate, String status) {
        this.wbspath = wbspath;
        this.sendPersonName = sendPersonName;
        this.sendDate = sendDate;
        this.rectificationOrgName = rectificationOrgName;
        this.standardDelName = standardDelName;
        this.checkplan = checkplan;
        this.checkOrgName = checkOrgName;
        this.attachmentList = attachmentList;
        this.rectificationPersonName = rectificationPersonName;
        this.rectificationDate = rectificationDate;
        this.status = status;
    }

    public String getWbspath() {
        return wbspath;
    }

    public void setWbspath(String wbspath) {
        this.wbspath = wbspath;
    }

    public String getSendPersonName() {
        return sendPersonName;
    }

    public void setSendPersonName(String sendPersonName) {
        this.sendPersonName = sendPersonName;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getRectificationOrgName() {
        return rectificationOrgName;
    }

    public void setRectificationOrgName(String rectificationOrgName) {
        this.rectificationOrgName = rectificationOrgName;
    }

    public String getStandardDelName() {
        return standardDelName;
    }

    public void setStandardDelName(String standardDelName) {
        this.standardDelName = standardDelName;
    }

    public String getCheckplan() {
        return checkplan;
    }

    public void setCheckplan(String checkplan) {
        this.checkplan = checkplan;
    }

    public String getCheckOrgName() {
        return checkOrgName;
    }

    public void setCheckOrgName(String checkOrgName) {
        this.checkOrgName = checkOrgName;
    }

    public ArrayList<Audio> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(ArrayList<Audio> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public String getRectificationPersonName() {
        return rectificationPersonName;
    }

    public void setRectificationPersonName(String rectificationPersonName) {
        this.rectificationPersonName = rectificationPersonName;
    }

    public String getRectificationDate() {
        return rectificationDate;
    }

    public void setRectificationDate(String rectificationDate) {
        this.rectificationDate = rectificationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
