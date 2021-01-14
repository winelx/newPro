package com.example.administrator.fengji.pzgc.bean;


import com.example.administrator.fengji.pzgc.activity.changed.ChagedNoticeItemDetailsActivity;
import com.example.administrator.fengji.pzgc.activity.changed.adapter.ChagedNoticeItemDetailsAdapter;
import com.example.baselibrary.bean.photoBean;

import java.util.ArrayList;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/13 0013}
 * 描述：整改后问题项
 * {@link  ChagedNoticeItemDetailsAdapter}
 * {@link  ChagedNoticeItemDetailsActivity }
 */
public class NoticeItemDetailsChaged {
    String replyDate;
    //整改描述
    String replyDescription ;
    //图片集合
    ArrayList<photoBean> beforeFileslist ;

    public NoticeItemDetailsChaged(String replyDate, String replyDescription, ArrayList<photoBean> beforeFileslist) {
        this.replyDate = replyDate;
        this.replyDescription = replyDescription;
        this.beforeFileslist = beforeFileslist;
    }

    public String getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(String replyDate) {
        this.replyDate = replyDate;
    }

    public String getReplyDescription() {
        return replyDescription;
    }

    public void setReplyDescription(String replyDescription) {
        this.replyDescription = replyDescription;
    }

    public ArrayList<photoBean> getBeforeFileslist() {
        return beforeFileslist;
    }

    public void setBeforeFileslist(ArrayList<photoBean> beforeFileslist) {
        this.beforeFileslist = beforeFileslist;
    }
}
