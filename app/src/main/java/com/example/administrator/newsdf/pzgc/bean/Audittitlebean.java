package com.example.administrator.newsdf.pzgc.bean;

/**
 * Created by Administrator on 2018/7/2 0002.
 */

public class Audittitlebean {
    String title;
    String complete;
    String unfinished;
    String day;
    public Audittitlebean(String title, String complete, String unfinished, String day) {
        this.title = title;
        this.complete = complete;
        this.unfinished = unfinished;
        this.day = day;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getUnfinished() {
        return unfinished;
    }

    public void setUnfinished(String unfinished) {
        this.unfinished = unfinished;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
