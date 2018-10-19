package com.example.administrator.newsdf.pzgc.bean;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/8/31 0031.
 */

public class ChekItemBean {
    String id;
    BigDecimal score;
    String content;
    String status;
    String stype;
    String resultscore;

    public ChekItemBean(String id, BigDecimal score, String content, String status, String stype, String resultscore) {
        this.id = id;
        this.score = score;
        this.content = content;
        this.status = status;
        this.stype = stype;
        this.resultscore = resultscore;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getResultscore() {
        return resultscore;
    }

    public void setResultscore(String resultscore) {
        this.resultscore = resultscore;
    }
}
