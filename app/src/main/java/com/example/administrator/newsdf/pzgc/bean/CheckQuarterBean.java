package com.example.administrator.newsdf.pzgc.bean;

/**
 * Created by Administrator on 2018/8/27 0027.
 */

public class CheckQuarterBean {
    String id;
    String parentid;
    String orgname;
    String company;
    String number;
    String rankingSorce;
    public CheckQuarterBean(String id, String parentid, String orgname, String company, String number,String rankingSorce) {
        this.id = id;
        this.parentid = parentid;
        this.orgname = orgname;
        this.company = company;
        this.number = number;
        this.rankingSorce = rankingSorce;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getRankingSorce() {
        return rankingSorce;
    }

    public void setRankingSorce(String rankingSorce) {
        this.rankingSorce = rankingSorce;
    }
}
