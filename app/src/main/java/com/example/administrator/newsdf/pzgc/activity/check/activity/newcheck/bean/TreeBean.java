package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean;

import com.example.administrator.newsdf.treeviews.utils.Nodes;
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
