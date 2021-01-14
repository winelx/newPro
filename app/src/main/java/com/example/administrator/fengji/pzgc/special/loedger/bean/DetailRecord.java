package com.example.administrator.fengji.pzgc.special.loedger.bean;
/**
 * @Author lx
 * @创建时间 2019/8/7 0007 9:25
 * @说明 专项施工台账施工方案详情数据
 **/

public class DetailRecord {

    /**
     * beDealPerson :
     * dealContent : 审核通过
     * dealDate : 2019-08-01 14:40:38
     * dealOpinion :
     * dealPerson : 黄多 ( 工区长 )
     * id : 872a74a98850483ea852b92874d5519e
     */

    private String beDealPerson;
    private String dealContent;
    private String dealDate;
    private String dealOpinion;
    private String dealPerson;
    private String id;

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
}
