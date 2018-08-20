package com.example.administrator.newsdf.pzgc.bean;

/**
 * Created by Administrator on 2018/8/20 0020.
 */

public class standarBean {
    String standardDel;
    String standardDelCode;
    String standardDelName;
    String standardDelScore;
    String standardType;
    String standardTypeName;

    public standarBean(String standardDel, String standardDelCode, String standardDelName, String standardDelScore, String standardType, String standardTypeName) {
        this.standardDel = standardDel;
        this.standardDelCode = standardDelCode;
        this.standardDelName = standardDelName;
        this.standardDelScore = standardDelScore;
        this.standardType = standardType;
        this.standardTypeName = standardTypeName;
    }

    public String getStandardDel() {
        return standardDel;
    }

    public void setStandardDel(String standardDel) {
        this.standardDel = standardDel;
    }

    public String getStandardDelCode() {
        return standardDelCode;
    }

    public void setStandardDelCode(String standardDelCode) {
        this.standardDelCode = standardDelCode;
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

    public String getStandardType() {
        return standardType;
    }

    public void setStandardType(String standardType) {
        this.standardType = standardType;
    }

    public String getStandardTypeName() {
        return standardTypeName;
    }

    public void setStandardTypeName(String standardTypeName) {
        this.standardTypeName = standardTypeName;
    }
}
