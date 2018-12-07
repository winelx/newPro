package com.example.administrator.newsdf.pzgc.bean;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/12/6 0006.
 * @description:
 * @Activity：
 */

public class NewDeviceBean {
    String titile;//标题
    String id;//id
    String data;//事件
    String grade;//隐患等级
    String reason;//原因
    ArrayList<Audio> list;

    public NewDeviceBean(String titile, String id, String data, String grade, String reason, ArrayList<Audio> list) {
        this.titile = titile;
        this.id = id;
        this.data = data;
        this.grade = grade;
        this.reason = reason;
        this.list = list;
    }

    public NewDeviceBean() {

    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ArrayList<Audio> getList() {
        return list;
    }

    public void setList(ArrayList<Audio> list) {
        this.list = list;
    }
}
