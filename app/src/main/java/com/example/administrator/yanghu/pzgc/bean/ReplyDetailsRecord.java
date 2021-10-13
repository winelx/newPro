package com.example.administrator.yanghu.pzgc.bean;


import com.example.administrator.yanghu.pzgc.activity.chagedreply.adapter.ChagedreplyDetailsAdapter;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/16 0016}
 * 描述：问题项
 *{@link ChagedreplyDetailsAdapter}
 */
public class ReplyDetailsRecord {

    /**
     * id : voluptate quis Excepteur Lorem
     * isOverdue : -6.535249581308991E7
     * isReply : 9.520934324822515E7
     * noticeDelId : eu irure exercitation dolore laboris
     * noticeId : exercitation sunt sint
     * replyId : sunt elit velit
     * standardDelName : voluptate eu sint
     */

    private String id;
    private int isOverdue;
    private int isReply;
    private int isVerify;
    private String noticeDelId;
    private String noticeId;
    private String replyId;
    private String standardDelName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(int isOverdue) {
        this.isOverdue = isOverdue;
    }

    public int getIsReply() {
        return isReply;
    }

    public void setIsReply(int isReply) {
        this.isReply = isReply;
    }

    public String getNoticeDelId() {
        return noticeDelId;
    }

    public void setNoticeDelId(String noticeDelId) {
        this.noticeDelId = noticeDelId;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getStandardDelName() {
        return standardDelName;
    }

    public void setStandardDelName(String standardDelName) {
        this.standardDelName = standardDelName;
    }

    public int getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(int isVerify) {
        this.isVerify = isVerify;
    }
}
