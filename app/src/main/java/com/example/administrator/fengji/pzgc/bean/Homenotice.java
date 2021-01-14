package com.example.administrator.fengji.pzgc.bean;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/3/4 0004}
 * 描述：通知item的实体类
 *{@link com.example.administrator.fengji.pzgc.fragment.HomeFragment}
 */
public class Homenotice {
    int icon;
    String id;
    String title;
    String content;
    String data;
    String number;

    public Homenotice(int icon, String id, String title, String content, String data, String number) {
        this.icon = icon;
        this.id = id;
        this.title = title;
        this.content = content;
        this.data = data;
        this.number = number;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
