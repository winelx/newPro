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
     * code : ZGTZ-20190222-44-R-2
     * id : f062997f59e743fa888cd200d154c1c6
     * motionNode : 2
     * noticeCode : ZGTZ-20190222-44
     * noticeId : 255ddac7fcc64170b230c773a8985bbc
     * permission : 2
     * replyDate : 2019-02-22
     * rorgName : 三独一标
     * ruserName : 江洁
     * sendDate : 2019-02-22
     * sendUserName : 系统管理员
     * sorgName : 贵州路桥集团有限公司
     * status : 1
     */

    private String code;
    private String id;
    private int motionNode;
    private String noticeCode;
    private String noticeId;
    private int permission=0;
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

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
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
