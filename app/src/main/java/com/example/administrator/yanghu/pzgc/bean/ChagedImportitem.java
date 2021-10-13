package com.example.administrator.yanghu.pzgc.bean;

import com.example.administrator.yanghu.pzgc.activity.changed.ChagedImportitemActivity;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/19 0019}
 * 描述：MainActivity
 *{@link  ChagedImportitemActivity}
 */
public class ChagedImportitem {
    String titlle;
    String content;
    String scord;
    String realname;
    String checkDate;
    String checkOrgName;
    String id;
    int iwork;

    public ChagedImportitem(String titlle, String content, String scord, String realname, String checkDate, String checkOrgName, String id, int iwork) {
        this.titlle = titlle;
        this.content = content;
        this.scord = scord;
        this.realname = realname;
        this.checkDate = checkDate;
        this.checkOrgName = checkOrgName;
        this.id = id;
        this.iwork = iwork;
    }

    public String getTitlle() {
        return titlle;
    }

    public void setTitlle(String titlle) {
        this.titlle = titlle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getScord() {
        return scord;
    }

    public void setScord(String scord) {
        this.scord = scord;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIwork() {
        return iwork;
    }

    public void setIwork(int iwork) {
        this.iwork = iwork;
    }
}