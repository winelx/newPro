package com.example.administrator.newsdf.pzgc.bean;

import com.example.administrator.newsdf.pzgc.activity.chagedreply.ChagedreplyDetailsActivity;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.adapter.ChagedreplyDetailsAdapter;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/16 0016}
 * 描述：整改回复详情界面单据内容
 * {@link  ChagedreplyDetailsAdapter}
 * {@link  ChagedreplyDetailsActivity}
 */
public class ReplyDetailsContent {


    /**
     * code : ZGTZ-20190221-23-R-2
     * id : de1cff21ba8f4e8ca1797ce3bc85f508
     * motionNode : 1
     * noticeCode : ZGTZ-20190221-23
     * noticeId : 45e603749bb1452da66b88a91a7bc782
     * replyDate : 2019-02-21
     * rorgName : 德务二标
     * ruserName : 张猛
     * sendDate : 2019-02-21
     * sendUserName : 刘波
     * sorgName : 贵州路桥集团有限公司
     * status : 2
     */

    private String code;
    private String id;
    private int motionNode;
    private String noticeCode;
    private String noticeId;
    private String replyDate;
    private String rorgName;
    private String ruserName;
    private String sendDate;
    private String sendUserName;
    private String sorgName;
    private int status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMotionNode() {
        return motionNode;
    }

    public void setMotionNode(int motionNode) {
        this.motionNode = motionNode;
    }

    public String getNoticeCode() {
        return noticeCode;
    }

    public void setNoticeCode(String noticeCode) {
        this.noticeCode = noticeCode;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(String replyDate) {
        this.replyDate = replyDate;
    }

    public String getRorgName() {
        return rorgName;
    }

    public void setRorgName(String rorgName) {
        this.rorgName = rorgName;
    }

    public String getRuserName() {
        return ruserName;
    }

    public void setRuserName(String ruserName) {
        this.ruserName = ruserName;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public String getSorgName() {
        return sorgName;
    }

    public void setSorgName(String sorgName) {
        this.sorgName = sorgName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
