package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean;

public class MessageExternal {

    /**
     * checkStandard : 未审批、未按方案 5分/次
     * description : 1212
     * id : 3610f93bdf8e4cdab40f930c0f52efee
     * name : 施工方案执行情况
     * position : ZK8+530～ZK8+700钢筋网支护，ZK9+000～ZK9+090钢筋网支护
     */

    private String checkStandard;
    private String standardScore;
    private String description;
    private String id;
    private String name;
    private String position;
    private boolean status = false;

    public String getStandardScore() {
        return standardScore;
    }

    public void setStandardScore(String standardScore) {
        this.standardScore = standardScore;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCheckStandard() {
        return checkStandard;
    }

    public void setCheckStandard(String checkStandard) {
        this.checkStandard = checkStandard;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
