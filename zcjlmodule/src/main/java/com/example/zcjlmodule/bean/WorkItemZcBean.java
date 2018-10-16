package com.example.zcjlmodule.bean;

/**
 * @author lx
 * @Created by: 2018/10/11 0011.
 * @description:
 */

public class WorkItemZcBean {
    int icon;
    String name;

    public WorkItemZcBean(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
