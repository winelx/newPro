package com.example.administrator.fengji.pzgc.bean;

import com.example.administrator.fengji.pzgc.fragment.HomeFragment;

/**
 *@author： lx
 *@创建时间： 2019/5/29 0029 14:55
 *@说明： 通知公告
 * @see HomeFragment
 **/
public class Proclamation {
        String content;
        String createName;
        String id;
        String orgName;
        String publishDate;
        String title;
        String number;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
