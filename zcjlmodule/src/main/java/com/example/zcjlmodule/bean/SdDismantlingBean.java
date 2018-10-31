package com.example.zcjlmodule.bean;

/**
 * @author lx
 * @Created by: 2018/10/22 0022.
 * @description:
 */

public class SdDismantlingBean {



    String id;
    //编号
    String number;
    //文件编号
    String filenumber;
    //文件名称
    String filename;
    //行政区域
    String region;
    //创建时间
    String datatime;
    //省份
    String provinceName;
    //城市
    String cityName;
    //区域
    String countyName;
    //乡镇
    String townName;
    //备注
    String remarks;
    //发布人
    String releasor;

    public SdDismantlingBean(String id, String number, String filenumber, String filename, String region, String datatime, String provinceName, String cityName, String countyName, String townName, String remarks, String releasor) {
        this.id = id;
        this.number = number;
        this.filenumber = filenumber;
        this.filename = filename;
        this.region = region;
        this.datatime = datatime;
        this.provinceName = provinceName;
        this.cityName = cityName;
        this.countyName = countyName;
        this.townName = townName;
        this.remarks = remarks;
        this.releasor = releasor;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getReleasor() {
        return releasor;
    }

    public void setReleasor(String releasor) {
        this.releasor = releasor;
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

    public void setNumber(String title) {
        this.number = title;
    }

    public String getFilenumber() {
        return filenumber;
    }

    public void setFilenumber(String filenumber) {
        this.filenumber = filenumber;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDatatime() {
        return datatime;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }
}
