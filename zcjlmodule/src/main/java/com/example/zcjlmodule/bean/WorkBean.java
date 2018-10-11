package com.example.zcjlmodule.bean;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/10/11 0011.
 * @description:
 */

public class WorkBean {
    String title;
    ArrayList<WorkItemBean> list;

    public WorkBean(String title, ArrayList<WorkItemBean> list) {
        this.title = title;
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<WorkItemBean> getList() {
        return list;
    }

    public void setList(ArrayList<WorkItemBean> list) {
        this.list = list;
    }
}
