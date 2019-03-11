package com.example.administrator.newsdf.pzgc.bean;

import com.example.administrator.newsdf.pzgc.activity.home.HomeTaskDetailsActivity;

/**
 * @author lx
 * @data :2019/3/11 0011
 * @描述 : 今日完成任务标段详情
 * @see HomeTaskDetailsActivity
 */
public class TodayDetailsBean {
    String string;

    public TodayDetailsBean(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
