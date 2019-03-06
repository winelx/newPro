package com.example.administrator.newsdf.pzgc.bean;

import com.example.administrator.newsdf.pzgc.activity.home.HometaskActivity;

/**
 * @author lx
 * @data :2019/3/6 0006
 * @描述 : 今日任务数
 * @see HometaskActivity
 */
public class TodayBean {
    String string;

    public TodayBean(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
