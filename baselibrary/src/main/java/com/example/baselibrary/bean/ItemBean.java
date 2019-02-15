package com.example.baselibrary.bean;

import java.util.ArrayList;

public class ItemBean {
    String string;
    ArrayList<bean> list;

    public ItemBean(ArrayList<bean> list, String string) {
        this.string = string;
        this.list = list;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public ArrayList<bean> getList() {
        return list;
    }

    public void setList(ArrayList<bean> list) {
        this.list = list;
    }

}
