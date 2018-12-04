package com.example.administrator.newsdf.pzgc.bean;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/12/4 0004.
 * @description:
 * @Activity：
 */

public class CorrectReplyBean {
    // 违反标准
    String standard;
    //  隐患等级
    String Grade;
    // 整改期限
    String term;
    //整改事由
    String Reason;
    //整改描述
    String describe;
    ArrayList<Audio> list;
    ArrayList<FileTypeBean> filelist;

    public CorrectReplyBean(String standard, String grade, String term, String reason, String describe, ArrayList<Audio> list, ArrayList<FileTypeBean> filelist) {
        this.standard = standard;
        Grade = grade;
        this.term = term;
        Reason = reason;
        this.describe = describe;
        this.list = list;
        this.filelist = filelist;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public ArrayList<Audio> getList() {
        return list;
    }

    public void setList(ArrayList<Audio> list) {
        this.list = list;
    }

    public ArrayList<FileTypeBean> getFilelist() {
        return filelist;
    }

    public void setFilelist(ArrayList<FileTypeBean> filelist) {
        this.filelist = filelist;
    }
}
