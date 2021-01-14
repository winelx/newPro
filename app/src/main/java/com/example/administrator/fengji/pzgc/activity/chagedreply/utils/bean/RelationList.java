package com.example.administrator.fengji.pzgc.activity.chagedreply.utils.bean;

import com.example.administrator.fengji.pzgc.activity.chagedreply.ChagedReplyRelationActivity;

import java.io.Serializable;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/18 0018}
 * 描述：关联整改通知单
 * {@link ChagedReplyRelationActivity}
 */
public class RelationList implements Serializable {



    /**
     * rectificationPersonName : do commodo exercitation magna tempor
     * code : cupidatat tempor
     * send_orgid : sed adipisicing ea
     * rectificationOrgName : incididunt Duis
     * rectification_orgid : cillum dolor
     * acceptPersonName : nulla
     * id : cillum dolore nostrud
     * rectification_person : sunt eiusmod
     * acceptPerson : sed mollit aute
     * motionNode : -5.234254251592869E7
     * sendOrgName : commod
     * sendUserName : sint esse exercitation aliqua
     * sendDate : magna ut consequat occaecat
     */

    private String rectificationPersonName;
    private String code;
    private String send_orgid;
    private String rectificationOrgName;
    private String rectification_orgid;
    private String acceptPersonName;
    private String id;
    private String rectification_person;
    private String acceptPerson;
    private int motionNode;
    private String sendOrgName;
    private String sendUserName;
    private String sendDate;

    public String getRectificationPersonName() {
        return rectificationPersonName;
    }

    public void setRectificationPersonName(String rectificationPersonName) {
        this.rectificationPersonName = rectificationPersonName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSend_orgid() {
        return send_orgid;
    }

    public void setSend_orgid(String send_orgid) {
        this.send_orgid = send_orgid;
    }

    public String getRectificationOrgName() {
        return rectificationOrgName;
    }

    public void setRectificationOrgName(String rectificationOrgName) {
        this.rectificationOrgName = rectificationOrgName;
    }

    public String getRectification_orgid() {
        return rectification_orgid;
    }

    public void setRectification_orgid(String rectification_orgid) {
        this.rectification_orgid = rectification_orgid;
    }

    public String getAcceptPersonName() {
        return acceptPersonName;
    }

    public void setAcceptPersonName(String acceptPersonName) {
        this.acceptPersonName = acceptPersonName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRectification_person() {
        return rectification_person;
    }

    public void setRectification_person(String rectification_person) {
        this.rectification_person = rectification_person;
    }

    public String getAcceptPerson() {
        return acceptPerson;
    }

    public void setAcceptPerson(String acceptPerson) {
        this.acceptPerson = acceptPerson;
    }

    public int  getMotionNode() {
        return motionNode;
    }

    public void setMotionNode(int motionNode) {
        this.motionNode = motionNode;
    }

    public String getSendOrgName() {
        return sendOrgName;
    }

    public void setSendOrgName(String sendOrgName) {
        this.sendOrgName = sendOrgName;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }
}
