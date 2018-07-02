package com.example.administrator.newsdf.pzgc.bean;

/**
 * Created by Administrator on 2018/7/2 0002.
 */

public class Auditbean {
    String title;
    String wbspaths;
    String username;
    String status;

    public Auditbean(String title, String wbspaths, String username, String status) {
        this.title = title;
        this.wbspaths = wbspaths;
        this.username = username;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWbspaths() {
        return wbspaths;
    }

    public void setWbspaths(String wbspaths) {
        this.wbspaths = wbspaths;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
