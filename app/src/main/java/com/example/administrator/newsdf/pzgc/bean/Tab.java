package com.example.administrator.newsdf.pzgc.bean;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class Tab {
    private int title;
    private int icon;
    private Class fragemnt;
    private int number;
    public Tab(Class fragemnt, int title, int icon,int number) {
        this.fragemnt = fragemnt;
        this.title = title;
        this.icon = icon;
        this.number = number;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Class getFragemnt() {
        return fragemnt;
    }

    public void setFragemnt(Class fragemnt) {
        this.fragemnt = fragemnt;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
