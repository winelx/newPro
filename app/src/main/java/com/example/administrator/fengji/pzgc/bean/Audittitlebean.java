package com.example.administrator.fengji.pzgc.bean;

/**
 * Created by Administrator on 2018/7/2 0002.
 */

public class Audittitlebean {
    String cnDay;
    String ratio;
    String tip;
    String date;

    public Audittitlebean(String cnDay, String ratio, String tip, String date) {
        this.cnDay = cnDay;
        this.ratio = ratio;
        this.tip = tip;
        this.date = date;
    }

    public String getCnDay() {
        return cnDay;
    }

    public void setCnDay(String cnDay) {
        this.cnDay = cnDay;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
