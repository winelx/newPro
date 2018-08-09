package com.example.administrator.newsdf.pzgc.bean;

/**
 * Created by Administrator on 2018/8/9 0009.
 */

public class CheckTasklistAdapter {
    String title;
    String userdata;
    String block;
    String org;
    String number;
    String status;


    public CheckTasklistAdapter(String title, String userdata, String block, String org, String number, String status) {
        this.title = title;
        this.userdata = userdata;
        this.block = block;
        this.org = org;
        this.number = number;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserdata() {
        return userdata;
    }

    public void setUserdata(String userdata) {
        this.userdata = userdata;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
