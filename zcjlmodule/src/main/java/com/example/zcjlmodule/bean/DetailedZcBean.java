package com.example.zcjlmodule.bean;

/**
 * @author lx
 * @Created by: 2018/10/11 0011.
 * @description:
 */

public class DetailedZcBean {
    String title;
    String titlecode;
    String titlenumber;
    String content;
    String paysum;
    String Tcheckedsum;
    String Fcheckedsum;

    public DetailedZcBean(String title, String titlecode, String titlenumber, String content, String paysum, String tcheckedsum, String fcheckedsum) {
        this.title = title;
        this.titlecode = titlecode;
        this.titlenumber = titlenumber;
        this.content = content;
        this.paysum = paysum;
        Tcheckedsum = tcheckedsum;
        Fcheckedsum = fcheckedsum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitlecode() {
        return titlecode;
    }

    public void setTitlecode(String titlecode) {
        this.titlecode = titlecode;
    }

    public String getTitlenumber() {
        return titlenumber;
    }

    public void setTitlenumber(String titlenumber) {
        this.titlenumber = titlenumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPaysum() {
        return paysum;
    }

    public void setPaysum(String paysum) {
        this.paysum = paysum;
    }

    public String getTcheckedsum() {
        return Tcheckedsum;
    }

    public void setTcheckedsum(String tcheckedsum) {
        Tcheckedsum = tcheckedsum;
    }

    public String getFcheckedsum() {
        return Fcheckedsum;
    }

    public void setFcheckedsum(String fcheckedsum) {
        Fcheckedsum = fcheckedsum;
    }
}
