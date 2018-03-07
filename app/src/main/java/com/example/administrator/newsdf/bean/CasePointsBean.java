package com.example.administrator.newsdf.bean;

/**
 * Created by Administrator on 2018/3/7 0007.
 */

public class CasePointsBean {
    String ID;
    String label;
    String content;

    public CasePointsBean(String ID, String label, String content) {
        this.ID = ID;
        this.label = label;
        this.content = content;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
