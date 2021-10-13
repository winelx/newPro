package com.example.administrator.yanghu.pzgc.bean;


/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/19 0019}
 * 描述：MainActivity
 *{@link }
 */
public class Checkitem {

    /**
     * content : 1、安全管理人员
     * describe : 111
     * detailsIds : 21fc6deb4f1241d99b88c22cab1dc870
     * iwork : 2
     * name : 一、从业人员资格条件
     * partDetails :
     * sequence : 1.0
     * standard : 1. 标准配备人数=年度目标计划产值÷5000万；查实际配备人数及安管人员持证情况；
     * standardDelScore : 1.0
     * wbsMainId :
     * wbsMainName :
     */

    private String content;
    private String describe;
    private String detailsIds;
    private int iwork;
    private String name;
    private String partDetails;
    private String sequence;
    private String standard;
    private String standardDelScore;
    private String wbsMainId;
    private String wbsMainName;
    private String standardScore;
    private boolean isLeam =false;
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getDetailsIds() {
        return detailsIds;
    }

    public void setDetailsIds(String detailsIds) {
        this.detailsIds = detailsIds;
    }

    public int getIwork() {
        return iwork;
    }

    public void setIwork(int iwork) {
        this.iwork = iwork;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartDetails() {
        return partDetails;
    }

    public void setPartDetails(String partDetails) {
        this.partDetails = partDetails;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getStandardDelScore() {
        return standardDelScore;
    }

    public void setStandardDelScore(String standardDelScore) {
        this.standardDelScore = standardDelScore;
    }

    public String getWbsMainId() {
        return wbsMainId;
    }

    public void setWbsMainId(String wbsMainId) {
        this.wbsMainId = wbsMainId;
    }

    public String getWbsMainName() {
        return wbsMainName;
    }

    public void setWbsMainName(String wbsMainName) {
        this.wbsMainName = wbsMainName;
    }


    public boolean isLeam() {
        return isLeam;
    }

    public void setLeam(boolean leam) {
        isLeam = leam;
    }

    public String getStandardScore() {
        return standardScore;
    }

    public void setStandardScore(String standardScore) {
        this.standardScore = standardScore;
    }
}
