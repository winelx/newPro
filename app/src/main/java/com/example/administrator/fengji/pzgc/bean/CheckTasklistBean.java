package com.example.administrator.fengji.pzgc.bean;

/**
 * Created by Administrator on 2018/8/9 0009.
 */

public class CheckTasklistBean {
    String checkOrgName;
    String checkUser;
    String createDate;
    String id;
    String orgName;
    String score;
    String status;
    int iwork;
    String wbsMainName;

//        "checkOrgName": "测试9标",
//                "checkUser": "九标工区长",
//                "createDate": "2018-08-13 10:09:15",
//                "id": "a6975b78e49043a1838bc411915c1265",
//                "orgName": "测试9标",
//                "score": 4.00,
//                "status": 0,
//                "wbsMainName": "K77+046～K77+287.455"


    public CheckTasklistBean(String checkOrgName, String checkUser, String createDate, String id, String orgName, String score, String status, int iwork, String wbsMainName) {
        this.checkOrgName = checkOrgName;
        this.checkUser = checkUser;
        this.createDate = createDate;
        this.id = id;
        this.orgName = orgName;
        this.score = score;
        this.status = status;
        this.iwork = iwork;
        this.wbsMainName = wbsMainName;
    }

    public String getCheckOrgName() {
        return checkOrgName;
    }

    public void setCheckOrgName(String checkOrgName) {
        this.checkOrgName = checkOrgName;
    }

    public String getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(String checkUser) {
        this.checkUser = checkUser;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWbsMainName() {
        return wbsMainName;
    }

    public void setWbsMainName(String wbsMainName) {
        this.wbsMainName = wbsMainName;
    }

    public int getIwork() {
        return iwork;
    }

    public void setIwork(int iwork) {
        this.iwork = iwork;
    }
}
