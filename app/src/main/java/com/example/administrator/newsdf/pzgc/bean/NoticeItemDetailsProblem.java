package com.example.administrator.newsdf.pzgc.bean;

import com.example.administrator.newsdf.pzgc.activity.changed.ChagedNoticeItemDetailsActivity;
import com.example.administrator.newsdf.pzgc.activity.changed.adapter.ChagedNoticeItemDetailsAdapter;
import com.example.baselibrary.bean.photoBean;

import java.util.ArrayList;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/13 0013}
 * 描述：整改问题项
 * {@link  ChagedNoticeItemDetailsAdapter}
 * {@link  ChagedNoticeItemDetailsActivity }
 */
public class NoticeItemDetailsProblem {
    //整改部位名称
    String rectificationPartName;
    //整改期限
    String rectificationDate;
    //违反标准
    String standardDelName;
    //存在问题
    String rectificationReason;
    //图片
    ArrayList<photoBean> afterFileslist;

    public NoticeItemDetailsProblem(String rectificationPartName, String rectificationDate, String standardDelName, String rectificationReason, ArrayList<photoBean> afterFileslist) {
        this.rectificationPartName = rectificationPartName;
        this.rectificationDate = rectificationDate;
        this.standardDelName = standardDelName;
        this.rectificationReason = rectificationReason;
        this.afterFileslist = afterFileslist;
    }

    public String getRectificationPartName() {
        return rectificationPartName;
    }

    public void setRectificationPartName(String rectificationPartName) {
        this.rectificationPartName = rectificationPartName;
    }

    public String getRectificationDate() {
        return rectificationDate;
    }

    public void setRectificationDate(String rectificationDate) {
        this.rectificationDate = rectificationDate;
    }

    public String getStandardDelName() {
        return standardDelName;
    }

    public void setStandardDelName(String standardDelName) {
        this.standardDelName = standardDelName;
    }

    public String getRectificationReason() {
        return rectificationReason;
    }

    public void setRectificationReason(String rectificationReason) {
        this.rectificationReason = rectificationReason;
    }

    public ArrayList<photoBean> getAfterFileslist() {
        return afterFileslist;
    }

    public void setAfterFileslist(ArrayList<photoBean> afterFileslist) {
        this.afterFileslist = afterFileslist;
    }
}
