package com.example.administrator.yanghu.pzgc.bean;

/**
 * 作者：winelx
 * 时间：2017/12/4 0004:上午 9:18
 * 说明：
 */
public class Push_item {
    //所属名称
    private String content;
    //ID
    private String id;
    //内容
    private String label;
    //前置条件
    private String preconditionsName;
    //负责人
    private String leaderName;
    //发送时间
    private String sendTime;
    //发送次数
    private String sendTimes;
    private Boolean checked;
    //负责人ID
    private String leaderId;
    //前置项ID
    private String preconditions;
    //标准
    private String checkStandard;


    public Push_item(String content, String id, String label, String preconditionsName,
                     String leaderName, String sendTime, String sendTimes, Boolean checked,
                     String leaderId, String preconditions,String checkStandard) {
        this.content = content;
        this.id = id;
        this.label = label;
        this.preconditionsName = preconditionsName;
        this.leaderName = leaderName;
        this.sendTime = sendTime;
        this.sendTimes = sendTimes;
        this.checked = checked;
        this.leaderId = leaderId;
        this.preconditions = preconditions;
        this.checkStandard = checkStandard;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPreconditionsName() {
        return preconditionsName;
    }

    public void setPreconditionsName(String preconditionsName) {
        this.preconditionsName = preconditionsName;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendTimes() {
        return sendTimes;
    }

    public void setSendTimes(String sendTimes) {
        this.sendTimes = sendTimes;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public String getPreconditions() {
        return preconditions;
    }

    public void setPreconditions(String preconditions) {
        this.preconditions = preconditions;
    }

    public String getCheckStandard() {
        return checkStandard;
    }

    public void setCheckStandard(String checkStandard) {
        this.checkStandard = checkStandard;
    }
}
