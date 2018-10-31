package com.example.zcjlmodule.bean;

/**
 * @author lx
 * @Created by: 2018/10/31 0031.
 * @description:
 * 查看征拆标准 ExamineDismantlingActivity
 */

public class ExamineBean {
    String name;
    String path;
    String type;

    public ExamineBean(String name, String path, String type) {
        this.name = name;
        this.path = path;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
