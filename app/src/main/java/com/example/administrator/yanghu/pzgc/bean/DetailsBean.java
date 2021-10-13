package com.example.administrator.yanghu.pzgc.bean;



/**
 * @author lx
 * @Created by: 2018/12/12 0012.
 * @description:特种设备新增整改问题项的实体
 * @Activity：NewDeviceActivity
 */

public class DetailsBean {
    /**
     * cisId : 1feb097532b844cc8897424e5115bc35
     * cisName : c.指挥人员和操作人员须配置对讲机；
     * id : 31d2100c0f16427e9c160304250d222c
     * sequence :
     */
    private String cisId;
    private String cisName;
    private String id;
    private String sequence;

    public String getCisId() {
        return cisId;
    }

    public void setCisId(String cisId) {
        this.cisId = cisId;
    }

    public String getCisName() {
        return cisName;
    }

    public void setCisName(String cisName) {
        this.cisName = cisName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
