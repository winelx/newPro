package com.example.administrator.newsdf.pzgc.bean;

import com.example.administrator.newsdf.pzgc.activity.changed.ChagedNoticeItemDetailsActivity;
import com.example.administrator.newsdf.pzgc.activity.changed.adapter.ChagedNoticeItemDetailsAdapter;
import com.example.baselibrary.bean.photoBean;

import java.util.ArrayList;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/13 0013}
 * 描述：整改问题项
 * {@link  ChagedNoticeItemDetailsAdapter}
 * {@link  ChagedNoticeItemDetailsActivity }
 */
public class NoticeItemDetailsProblem {
    //整改部位名称
    String rectificationPartName;
    //整改期限
    String rectificationDate;
    //违反标准
    String standardDelName;
    //存在问题
    String rectificationReason;
    //扣分
    String standardDelScore;
    // 工区长id
    String  chiefId;
    //工区长名称
    String  chiefName;
    // 技术负责人id
    String  technicianId;
    //技术负责人名称
    String  technicianName;
    //施工班主
    String  team;

    //图片
    ArrayList<photoBean> afterFileslist;


    public NoticeItemDetailsProblem(String rectificationPartName, String rectificationDate, String standardDelName, String rectificationReason, String standardDelScore, ArrayList<photoBean> afterFileslist) {
        this.rectificationPartName = rectificationPartName;
        this.rectificationDate = rectificationDate;
        this.standardDelName = standardDelName;
        this.rectificationReason = rectificationReason;
        this.standardDelScore = standardDelScore;
        this.afterFileslist = afterFileslist;
    }




    public NoticeItemDetailsProblem(String rectificationPartName, String rectificationDate, String standardDelName, String rectificationReason, String standardDelScore, String chiefId, String chiefName, String technicianId, String technicianName, String team, ArrayList<photoBean> afterFileslist) {
        this.rectificationPartName = rectificationPartName;
        this.rectificationDate = rectificationDate;
        this.standardDelName = standardDelName;
        this.rectificationReason = rectificationReason;
        this.standardDelScore = standardDelScore;
        this.chiefId = chiefId;
        this.chiefName = chiefName;
        this.technicianId = technicianId;
        this.technicianName = technicianName;
        this.team = team;
        this.afterFileslist = afterFileslist;
    }

    public String getChiefId() {
        return chiefId;
    }

    public void setChiefId(String chiefId) {
        this.chiefId = chiefId;
    }

    public String getChiefName() {
        return chiefName;
    }

    public void setChiefName(String chiefName) {
        this.chiefName = chiefName;
    }

    public String getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(String technicianId) {
        this.technicianId = technicianId;
    }

    public String getTechnicianName() {
        return technicianName;
    }

    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getRectificationPartName() {
        return rectificationPartName;
    }

    public void setRectificationPartName(String rectificationPartName) {
        this.rectificationPartName = rectificationPartName;
    }

    public String getRectificationDate() {
        return rectificationDate;
    }

    public void setRectificationDate(String rectificationDate) {
        this.rectificationDate = rectificationDate;
    }

    public String getStandardDelName() {
        return standardDelName;
    }

    public void setStandardDelName(String standardDelName) {
        this.standardDelName = standardDelName;
    }

    public String getRectificationReason() {
        return rectificationReason;
    }

    public void setRectificationReason(String rectificationReason) {
        this.rectificationReason = rectificationReason;
    }

    public ArrayList<photoBean> getAfterFileslist() {
        return afterFileslist;
    }

    public void setAfterFileslist(ArrayList<photoBean> afterFileslist) {
        this.afterFileslist = afterFileslist;
    }

    public String getStandardDelScore() {
        return standardDelScore;
    }

    public void setStandardDelScore(String standardDelScore) {
        this.standardDelScore = standardDelScore;
    }
}
