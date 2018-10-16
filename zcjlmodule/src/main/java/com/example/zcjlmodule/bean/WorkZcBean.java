package com.example.zcjlmodule.bean;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/10/11 0011.
 * @description:
 */

public class WorkZcBean {
    String title;
    ArrayList<WorkItemZcBean> list;

    public WorkZcBean(String title, ArrayList<WorkItemZcBean> list) {
        this.title = title;
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<WorkItemZcBean> getList() {
        return list;
    }

    public void setList(ArrayList<WorkItemZcBean> list) {
        this.list = list;
    }
}
