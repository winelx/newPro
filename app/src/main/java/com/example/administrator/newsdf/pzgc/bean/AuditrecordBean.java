package com.example.administrator.newsdf.pzgc.bean;

/**
 * Created by Administrator on 2018/7/5 0005.
 */

public class AuditrecordBean {
    String Id;
    String title;
    String wbspath;
    String user;
    String status;


    public AuditrecordBean(String id, String title, String wbspath, String user, String status) {
        Id = id;
        this.title = title;
        this.wbspath = wbspath;
        this.user = user;
        this.status = status;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWbspath() {
        return wbspath;
    }

    public void setWbspath(String wbspath) {
        this.wbspath = wbspath;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
