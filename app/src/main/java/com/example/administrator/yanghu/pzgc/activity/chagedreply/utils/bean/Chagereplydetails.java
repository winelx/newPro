package com.example.administrator.yanghu.pzgc.activity.chagedreply.utils.bean;

import com.example.administrator.yanghu.pzgc.activity.chagedreply.ChagedReplyNewActivity;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/23 0023}
 * 描述：MainActivity
 * {@link  ChagedReplyNewActivity}
 */
public class Chagereplydetails {


    /**
     * standardDelName : 仰拱最小厚度低于设计厚度90%必须返工处理
     * isOverdue : 1
     * noticeDelId : 3da336b770cc4894ab2fdaf9a56c8dce
     * replyId : 40fe8d3e6b824a698cefcae556fbcc6c
     * id : 983472db3bbb437ea85b3cff2b4c58bd
     * noticeId : df297808770745738590418b0cb75ea0
     * isReply : 1
     */

    private String standardDelName;
    private int isOverdue;
    private String noticeDelId;
    private String replyId;
    private String id;
    private String noticeId;
    private int isReply;

    public String getStandardDelName() {
        return standardDelName;
    }

    public void setStandardDelName(String standardDelName) {
        this.standardDelName = standardDelName;
    }

    public int getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(int isOverdue) {
        this.isOverdue = isOverdue;
    }

    public String getNoticeDelId() {
        return noticeDelId;
    }

    public void setNoticeDelId(String noticeDelId) {
        this.noticeDelId = noticeDelId;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public int getIsReply() {
        return isReply;
    }

    public void setIsReply(int isReply) {
        this.isReply = isReply;
    }
}
