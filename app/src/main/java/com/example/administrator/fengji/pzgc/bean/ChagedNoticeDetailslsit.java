package com.example.administrator.fengji.pzgc.bean;


import com.example.administrator.fengji.pzgc.activity.changed.adapter.ChagedNoticeDetailsAdapter;

import java.io.Serializable;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/19 0019}
 * 描述：MainActivity
 * {@link  ChagedNoticeDetailsAdapter}
 */
public class ChagedNoticeDetailslsit implements Serializable {

    /**
     * id : 3928e269035d4ffe9214e992e514120b
     * isOverdue : 1
     * isVerify : 1
     * iwork : 2
     * standardDelName : 1. ①生产安全事故统计上报是否严格按照《关于实行生产安全事故报告制度的通知》（黔交建发〔2015〕116号）严格执行； 2. ②质量事故统计上报是否根据交通运输部《公路水运建设工程质量事故等级划分和报告制度》和贵州省交通建设工程质量监督局《贵州省公路水运工程质量事故等级划分和调查处理规定》（试行）的要求严格执行；
     */

    private String id;
    private int isOverdue;
    private int isVerify;
    private int iwork;
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

    public int getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(int isVerify) {
        this.isVerify = isVerify;
    }

    public int getIwork() {
        return iwork;
    }

    public void setIwork(int iwork) {
        this.iwork = iwork;
    }

    public String getStandardDelName() {
        return standardDelName;
    }

    public void setStandardDelName(String standardDelName) {
        this.standardDelName = standardDelName;
    }
}
