package com.example.administrator.newsdf.pzgc.bean;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/11/29 0029.
 * @description:
 */

public class SeeDetailsTop {

    String cisName;
    String HTLName;
    String term;
    String cause;
    ArrayList<FileTypeBean> list;

    public SeeDetailsTop(String cisName, String HTLName, String term, String cause, ArrayList<FileTypeBean> list) {
        this.cisName = cisName;
        this.HTLName = HTLName;
        this.term = term;
        this.cause = cause;
        this.list = list;
    }

    public String getCisName() {
        return cisName;
    }

    public void setCisName(String cisName) {
        this.cisName = cisName;
    }

    public String getHTLName() {
        return HTLName;
    }

    public void setHTLName(String HTLName) {
        this.HTLName = HTLName;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public ArrayList<FileTypeBean> getList() {
        return list;
    }

    public void setList(ArrayList<FileTypeBean> list) {
        this.list = list;
    }
}
