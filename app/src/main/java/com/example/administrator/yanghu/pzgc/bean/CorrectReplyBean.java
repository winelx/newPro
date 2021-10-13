package com.example.administrator.yanghu.pzgc.bean;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/12/4 0004.
 * @description:
 * @Activity：
 */

public class CorrectReplyBean {
    ProblemitemBean bean;
    ArrayList<Audio> list;
    ArrayList<FileTypeBean> filelist;

    public CorrectReplyBean(ProblemitemBean bean, ArrayList<Audio> list, ArrayList<FileTypeBean> filelist) {
        this.bean = bean;
        this.list = list;
        this.filelist = filelist;
    }

    public ProblemitemBean getBean() {
        return bean;
    }

    public void setBean(ProblemitemBean bean) {
        this.bean = bean;
    }

    public ArrayList<Audio> getList() {
        return list;
    }

    public void setList(ArrayList<Audio> list) {
        this.list = list;
    }

    public ArrayList<FileTypeBean> getFilelist() {
        return filelist;
    }

    public void setFilelist(ArrayList<FileTypeBean> filelist) {
        this.filelist = filelist;
    }
}
