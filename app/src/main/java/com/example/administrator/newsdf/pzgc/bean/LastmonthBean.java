package com.example.administrator.newsdf.pzgc.bean;

import com.example.administrator.newsdf.pzgc.activity.home.HometaskActivity;

import java.io.Serializable;

/**
 * @author lx
 * @data :2019/3/6 0006
 * @描述 :  上月整改单统计
 * @see HometaskActivity
 */
public class LastmonthBean implements Serializable {

    /**
     * finish : 1
     * finishRate : 100.0%
     * id : f41d8a54aa674d59b0e18f04bd266765
     * name : 第一分公司
     * noFinish : 0
     * notFinish : 0
     * notOverdueFinish : 1
     * noticeCount : 1
     * overdueRate : 0.0%
     * yesOverdueFinish : 0
     */

    private int finish;
    private String finishRate;
    private String id;
    private String name;
    private int noFinish;
    private int notFinish;
    private int notOverdueFinish;
    private int noticeCount;
    private String overdueRate;
    private int yesOverdueFinish;

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public String getFinishRate() {
        return finishRate;
    }

    public void setFinishRate(String finishRate) {
        this.finishRate = finishRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoFinish() {
        return noFinish;
    }

    public void setNoFinish(int noFinish) {
        this.noFinish = noFinish;
    }

    public int getNotFinish() {
        return notFinish;
    }

    public void setNotFinish(int notFinish) {
        this.notFinish = notFinish;
    }

    public int getNotOverdueFinish() {
        return notOverdueFinish;
    }

    public void setNotOverdueFinish(int notOverdueFinish) {
        this.notOverdueFinish = notOverdueFinish;
    }

    public int getNoticeCount() {
        return noticeCount;
    }

    public void setNoticeCount(int noticeCount) {
        this.noticeCount = noticeCount;
    }

    public String getOverdueRate() {
        return overdueRate;
    }

    public void setOverdueRate(String overdueRate) {
        this.overdueRate = overdueRate;
    }

    public int getYesOverdueFinish() {
        return yesOverdueFinish;
    }

    public void setYesOverdueFinish(int yesOverdueFinish) {
        this.yesOverdueFinish = yesOverdueFinish;
    }
}
