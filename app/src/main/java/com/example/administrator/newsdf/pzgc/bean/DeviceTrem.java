package com.example.administrator.newsdf.pzgc.bean;

/**
 * @author lx
 * @Created by: 2018/11/29 0029.
 * @description:
 */

public class DeviceTrem {
    String title;
    String status;

    public DeviceTrem(String title, String status) {
        this.title = title;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
