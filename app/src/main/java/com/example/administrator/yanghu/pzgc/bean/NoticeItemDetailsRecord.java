package com.example.administrator.yanghu.pzgc.bean;

import com.example.administrator.yanghu.pzgc.activity.changed.ChagedNoticeItemDetailsActivity;
import com.example.administrator.yanghu.pzgc.activity.changed.adapter.ChagedNoticeItemDetailsAdapter;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/13 0013}
 * 描述： 操作记录
 * {@link  ChagedNoticeItemDetailsAdapter}
 * {@link  ChagedNoticeItemDetailsActivity }
 */
public class NoticeItemDetailsRecord {

    /**
     * beDealPerson :
     * dealContent : 我来回复
     * dealDate : 2019-02-19 17:34:44
     * dealOpinion :
     * dealPerson : 周宇( 15 )
     * id : 9d7c98c4e3a4457fbb21e60d67f4034a
     * isShow : 1
     * noticeId : e9f6dd891751469688d2a47d27796b64
     */


    private String beDealPerson;
    private String dealContent;
    private String dealDate;
    private String dealOpinion;
    private String dealPerson;
    private String id;
    private int isShow;
    private String noticeId;

    public String getBeDealPerson() {
        return beDealPerson;
    }

    public void setBeDealPerson(String beDealPerson) {
        this.beDealPerson = beDealPerson;
    }

    public String getDealContent() {
        return dealContent;
    }

    public void setDealContent(String dealContent) {
        this.dealContent = dealContent;
    }

    public String getDealDate() {
        return dealDate;
    }

    public void setDealDate(String dealDate) {
        this.dealDate = dealDate;
    }

    public String getDealOpinion() {
        return dealOpinion;
    }

    public void setDealOpinion(String dealOpinion) {
        this.dealOpinion = dealOpinion;
    }

    public String getDealPerson() {
        return dealPerson;
    }

    public void setDealPerson(String dealPerson) {
        this.dealPerson = dealPerson;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }
}
