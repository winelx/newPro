package com.example.administrator.newsdf.pzgc.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author lx
 * @Created by: 2018/12/10 0010.
 * @description:
 * @Activity：
 */

public class HiddendangerBean {
    /**
     * code : hiddenTroubleLevel
     * createBy :
     * createDate :
     * delFlag : 0
     * gid : fb3a579e0aec4b9fbe468c082b4efba8
     * id : a9230ef74c1546979377296be7287765
     * label : A
     * new : false
     * remarks : A
     * sort : 1
     * updateBy :
     * updateDate :
     * value : 1
     */

    private String code;
    private String createBy;
    private String createDate;
    private String delFlag;
    private String gid;
    private String id;
    private String label;
    @JSONField(name = "new")
    private boolean newX;
    private String remarks;
    private int sort;
    private String updateBy;
    private String updateDate;
    private String value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isNewX() {
        return newX;
    }

    public void setNewX(boolean newX) {
        this.newX = newX;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
