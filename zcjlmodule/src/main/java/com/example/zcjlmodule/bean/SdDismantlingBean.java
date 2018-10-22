package com.example.zcjlmodule.bean;

/**
 * @author lx
 * @Created by: 2018/10/22 0022.
 * @description:
 */

public class SdDismantlingBean {

    String id;
    String title;
    String content;
    String filename;
    String region;
    String datatime;

    public SdDismantlingBean(String id, String title, String content, String filename, String region, String datatime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.filename = filename;
        this.region = region;
        this.datatime = datatime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String copntent) {
        this.content = copntent;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDatatime() {
        return datatime;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }
}
