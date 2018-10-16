package com.example.zcjlmodule.bean;

/**
 * Created by Administrator on 2018/10/10 0010.
 */

public class MessageZcItem {
    //图标
    int Icon;
    //标题
    String title;
    //消息
    String messagecontent;
    //时间
    String data;
    //消息数量
    Integer messagenumber;

    public MessageZcItem(int icon, String title, String messagecontent, String data, Integer messagenumber) {
        Icon = icon;
        this.title = title;
        this.messagecontent = messagecontent;
        this.data = data;
        this.messagenumber = messagenumber;
    }

    public int getIcon() {
        return Icon;
    }

    public void setIcon(int icon) {
        Icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessagecontent() {
        return messagecontent;
    }

    public void setMessagecontent(String messagecontent) {
        this.messagecontent = messagecontent;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getMessagenumber() {
        return messagenumber;
    }

    public void setMessagenumber(Integer messagenumber) {
        this.messagenumber = messagenumber;
    }
}
