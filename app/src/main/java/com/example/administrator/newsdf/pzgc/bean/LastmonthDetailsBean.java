package com.example.administrator.newsdf.pzgc.bean;

import com.example.administrator.newsdf.pzgc.activity.home.HomeTaskDetailsActivity;

/**
* @author lx
* @data :2019/3/11 0011
* @描述 : 上月统计整改单标段详情
*@see HomeTaskDetailsActivity
*/
public class LastmonthDetailsBean {
    private String string;

    public LastmonthDetailsBean(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
