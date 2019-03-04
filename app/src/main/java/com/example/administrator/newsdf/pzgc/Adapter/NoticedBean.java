package com.example.administrator.newsdf.pzgc.Adapter;

import com.example.administrator.newsdf.pzgc.activity.home.NoticeActivity;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/3/4 0004}
 * 描述：消息通知
 * {@link NoticeActivity}
 */
public class NoticedBean {
    private String name;

    public NoticedBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
