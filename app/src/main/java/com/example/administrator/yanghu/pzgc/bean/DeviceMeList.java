package com.example.administrator.yanghu.pzgc.bean;

/**
 * @author lx
 * @Created by: 2018/12/10 0010.
 * @description: 特种设备整改单据列表的实体
 * @Activity： DeviceMessageListActivity，DeviceMessageAllActivity
 */

public class DeviceMeList {

    /**
     * checkOrgName : 路桥公司本部
     * checkUserName : 刘波
     * check_date : 2018-12-07
     * id : 520c8aae20084c68869b21bda09cb230
     * number : TZSBXJ-20181207-2
     * org_id : 539d8c12c94943449dca5d87c1d8e8e5
     * personLiableName : 徐涛海
     * status : 2
     * typeName : 龙门吊(A0202)
     */

    private String checkOrgName;
    private String checkUserName;
    private String check_date;
    private String id;
    private String number;
    private String org_id;
    private String personLiableName;
    private int status;
    private String typeName;

    public String getCheckOrgName() {
        return checkOrgName;
    }

    public void setCheckOrgName(String checkOrgName) {
        this.checkOrgName = checkOrgName;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public String getCheck_date() {
        return check_date;
    }

    public void setCheck_date(String check_date) {
        this.check_date = check_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public String getPersonLiableName() {
        return personLiableName;
    }

    public void setPersonLiableName(String personLiableName) {
        this.personLiableName = personLiableName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}


