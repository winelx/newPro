package com.example.administrator.newsdf.pzgc.bean;

import com.example.administrator.newsdf.pzgc.activity.home.HometaskActivity;

/**
* @author lx
* @data :2019/3/6 0006
* @描述 :  上月整改单统计
*@see HometaskActivity
*/
public class LastmonthBean {
    String string;

    public LastmonthBean(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
