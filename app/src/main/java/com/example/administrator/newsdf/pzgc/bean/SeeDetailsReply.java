package com.example.administrator.newsdf.pzgc.bean;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/11/29 0029.
 * @description:
 */

public class SeeDetailsReply {
    String title;
    ArrayList<FileTypeBean> list;

    public SeeDetailsReply(String title, ArrayList<FileTypeBean> list) {
        this.title = title;
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<FileTypeBean> getList() {
        return list;
    }

    public void setList(ArrayList<FileTypeBean> list) {
        this.list = list;
    }
}
