package com.example.administrator.newsdf.pzgc.bean;

/**
 * @author lx
 * @Created by: 2018/11/29 0029.
 * @description:
 */

public class FileTypeBean {
    String name;
    String url;
    String type;

    public FileTypeBean(String name, String url, String type) {
        this.name = name;
        this.url = url;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
