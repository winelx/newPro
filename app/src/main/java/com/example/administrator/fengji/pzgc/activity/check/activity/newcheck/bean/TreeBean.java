package com.example.administrator.fengji.pzgc.activity.check.activity.newcheck.bean;

/**
 *
 *说明：
 *创建时间： 2020/6/30 0030 10:47
 *@author winelx
 */
public class TreeBean {
    String  name;
    boolean lean;

    public TreeBean(String name,boolean lean) {
        this.name = name;
        this.lean = lean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLean() {
        return lean;
    }

    public void setLean(boolean lean) {
        this.lean = lean;
    }
}
