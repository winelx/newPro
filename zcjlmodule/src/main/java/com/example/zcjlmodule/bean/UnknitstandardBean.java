package com.example.zcjlmodule.bean;

/**
 * @author lx
 * @Created by: 2018/10/22 0022.
 * @description:
 */

public class UnknitstandardBean {
    String id;;
    String title;
    String type;
    String region;
    String price;
    String unit;
    String compensate;

    public UnknitstandardBean(String id, String title, String type, String region, String price, String unit, String compensate) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.region = region;
        this.price = price;
        this.unit = unit;
        this.compensate = compensate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCompensate() {
        return compensate;
    }

    public void setCompensate(String compensate) {
        this.compensate = compensate;
    }
}
