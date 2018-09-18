package com.example.administrator.newsdf.pzgc.bean;

/**
 * Created by Administrator on 2018/8/2 0002.
 */

public class Checkbean {
    String name;
    int path;

    public Checkbean(String name, int path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPath() {
        return path;
    }

    public void setPath(int path) {
        this.path = path;
    }
}
