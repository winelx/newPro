package com.example.administrator.newsdf.pzgc.bean;

import com.example.administrator.newsdf.pzgc.activity.home.HometaskActivity;

/**
 * @author lx
 * @data :2019/3/6 0006
 * @描述 : 今日任务数
 * @see HometaskActivity
 */
public class TodayBean {
    String fOrgName;
    String finishCount;
    String fOrgId;

    public String getfOrgName() {
        return fOrgName;
    }

    public void setfOrgName(String fOrgName) {
        this.fOrgName = fOrgName;
    }

    public String getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(String finishCount) {
        this.finishCount = finishCount;
    }

    public String getfOrgId() {
        return fOrgId;
    }

    public void setfOrgId(String fOrgId) {
        this.fOrgId = fOrgId;
    }
}
