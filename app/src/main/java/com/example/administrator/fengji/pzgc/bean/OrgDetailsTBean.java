package com.example.administrator.fengji.pzgc.bean;

/**
 * Created by Administrator on 2018/8/28 0028.
 */

public class OrgDetailsTBean {
    /**
     * check_date : 2018-08-24
     * check_org_name : 测试公司
     * check_plan_name : pc端新增检查计划，pc端完成
     * check_user_name : 测试分公司质安负责人001
     * id : d4e2e65d640b465eb304480c1c1c69d3
     * part_details : 部位详情
     * score : 84.0
     * wbs_name : 0#台左幅0-0桩基混凝土浇筑
     * wbs_type_name : 桥梁工程>钻孔灌注桩
     */

    private String check_date;
    private String check_org_name;
    private String check_plan_name;
    private String check_user_name;
    private String id;
    private String part_details;
    private String score;
    private String wbs_name;
    private String wbs_type_name;
    private int  iwork;


    public OrgDetailsTBean(String check_date, String check_org_name, String check_plan_name, String check_user_name,
                           String id, String part_details, String score, String wbs_name, String wbs_type_name, int  iwork) {
        this.check_date = check_date;
        this.check_org_name = check_org_name;
        this.check_plan_name = check_plan_name;
        this.check_user_name = check_user_name;
        this.id = id;
        this.part_details = part_details;
        this.score = score;
        this.wbs_name = wbs_name;
        this.wbs_type_name = wbs_type_name;
        this.iwork = iwork;
    }

    public String getCheck_date() {
        return check_date;
    }

    public void setCheck_date(String check_date) {
        this.check_date = check_date;
    }

    public String getCheck_org_name() {
        return check_org_name;
    }

    public void setCheck_org_name(String check_org_name) {
        this.check_org_name = check_org_name;
    }

    public String getCheck_plan_name() {
        return check_plan_name;
    }

    public void setCheck_plan_name(String check_plan_name) {
        this.check_plan_name = check_plan_name;
    }

    public String getCheck_user_name() {
        return check_user_name;
    }

    public void setCheck_user_name(String check_user_name) {
        this.check_user_name = check_user_name;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPart_details() {
        return part_details;
    }

    public void setPart_details(String part_details) {
        this.part_details = part_details;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getWbs_name() {
        return wbs_name;
    }

    public void setWbs_name(String wbs_name) {
        this.wbs_name = wbs_name;
    }

    public String getWbs_type_name() {
        return wbs_type_name;
    }

    public void setWbs_type_name(String wbs_type_name) {
        this.wbs_type_name = wbs_type_name;
    }

    public int getIwork() {
        return iwork;
    }

    public void setIwork(int iwork) {
        this.iwork = iwork;
    }
}
