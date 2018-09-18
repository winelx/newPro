package com.example.administrator.newsdf.pzgc.bean;

/**
 * Created by Administrator on 2018/9/11 0011.
 * 办公界面recyclerview的实体
 */

public class WorkrecBean {
    String name;
    int drawable;

    public WorkrecBean(String name, int drawable) {
        this.name = name;
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
