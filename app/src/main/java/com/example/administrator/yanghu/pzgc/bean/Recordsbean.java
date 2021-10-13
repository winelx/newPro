package com.example.administrator.yanghu.pzgc.bean;

/**
 * Created by Administrator on 2018/7/16 0016.
 */

public class Recordsbean {

    String createdata;
    String realname;
    String jobs;
    String pass;

    public Recordsbean(String createdata, String realname, String jobs, String pass) {
        this.createdata = createdata;
        this.realname = realname;
        this.jobs = jobs;
        this.pass = pass;
    }

    public String getCreatedata() {
        return createdata;
    }

    public void setCreatedata(String createdata) {
        this.createdata = createdata;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getJobs() {
        return jobs;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
