package com.example.administrator.newsdf.pzgc.bean;

/**
 * Created by Administrator on 2018/8/28 0028.
 */

public class OrgDetailsFBean {
    /**
     * check_date : 2018-08-23 20:08:52
     * check_org_name : 测试公司
     * check_person_name : 测试分公司质安负责人001
     * id : 01ee44b85d0a4567884158ad472514e2
     * part_details : 安卓
     * rectification_date : 2018-08-04 00:00:00
     * rectification_org_name : 测试9标
     * rectification_person_name : 九标项目经理001
     * rectification_reason : 嗷呜good
     * standard_del_name : 对危险性较大的高边坡、高墩、大跨桥梁和地质复杂隧道，未进行风险评估
     * standard_del_score : 10.0
     * wbs_name : 桐木冲1号大桥桥梁总体
     */

    private String checkDate;
    private String checkOrgName;
    private String checkPersonName;
    private String id;
    private String partDetails;
    private String rectificationDate;
    private String rectificationOrgName;
    private String rectificationPersonName;
    private String rectificationReason;
    private String standardDelName;
    private String standardDelScore;
    private String wbsName;
    private String standardtypeName;

    public OrgDetailsFBean(String checkDate, String checkOrgName, String checkPersonName, String id, String partDetails,
                           String rectificationDate, String rectificationOrgName, String rectificationPersonName,
                           String rectificationReason, String standardDelName, String standardDelScore, String wbsName,String standardtypeName) {
        this.checkDate = checkDate;
        this.checkOrgName = checkOrgName;
        this.checkPersonName = checkPersonName;
        this.id = id;
        this.partDetails = partDetails;
        this.rectificationDate = rectificationDate;
        this.rectificationOrgName = rectificationOrgName;
        this.rectificationPersonName = rectificationPersonName;
        this.rectificationReason = rectificationReason;
        this.standardDelName = standardDelName;
        this.standardDelScore = standardDelScore;
        this.wbsName = wbsName;
        this.standardtypeName = standardtypeName;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartDetails() {
        return partDetails;
    }

    public void setPartDetails(String partDetails) {
        this.partDetails = partDetails;
    }

    public String getRectificationDate() {
        return rectificationDate;
    }

    public void setRectificationDate(String rectificationDate) {
        this.rectificationDate = rectificationDate;
    }

    public String getRectificationOrgName() {
        return rectificationOrgName;
    }

    public void setRectificationOrgName(String rectificationOrgName) {
        this.rectificationOrgName = rectificationOrgName;
    }

    public String getRectificationPersonName() {
        return rectificationPersonName;
    }

    public void setRectificationPersonName(String rectificationPersonName) {
        this.rectificationPersonName = rectificationPersonName;
    }

    public String getRectificationReason() {
        return rectificationReason;
    }

    public void setRectificationReason(String rectificationReason) {
        this.rectificationReason = rectificationReason;
    }

    public String getStandardDelName() {
        return standardDelName;
    }

    public void setStandardDelName(String standardDelName) {
        this.standardDelName = standardDelName;
    }

    public String getStandardDelScore() {
        return standardDelScore;
    }

    public void setStandardDelScore(String standardDelScore) {
        this.standardDelScore = standardDelScore;
    }

    public String getWbsName() {
        return wbsName;
    }

    public void setWbsName(String wbsName) {
        this.wbsName = wbsName;
    }

    public String getStandardtypeName() {
        return standardtypeName;
    }

    public void setStandardtypeName(String standardtypeName) {
        this.standardtypeName = standardtypeName;
    }
}
