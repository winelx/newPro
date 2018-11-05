package com.example.zcjlmodule.bean;

/**
 * @author lx
 * @Created by: 2018/10/16 0016.
 * @description:原始勘丈表界面的数据实体 界面：OrginalZcActivity
 */

public class OriginalZcBean {
    String id;
    //标题
    String titile;
    //内容
    String content;
    //期数
    String datanumber;
    //户主名称
    String namecontent;
    //征拆类别
    String category;
    //创建人
    String createName;
    //创建时间
    String createdata;
    //总价格
    String totalPrice;
    //省份
    String provinceName;
    //城市
    String cityName;
    //区
    String countyName;
    //乡镇
    String townName;
    //地址
    String detailAddress;
    //计量单位
    String meterUnitName;
    //单价
    String price;
    //身份证
    String householderIdcard;
    //申报数量
    String declareNum;
    //标准分解
    String standardDetailNumber;
    //原始单号
    String rawNumber;
    public OriginalZcBean(String id, String titile, String content, String datanumber,
                          String namecontent, String category, String createName,
                          String createdata, String totalPrice, String provinceName,
                          String cityName, String countyName, String townName,
                          String detailAddress, String meterUnitName, String price,
                          String householderIdcard,String declareNum,String standardDetailNumber, String rawNumber) {
        this.id = id;
        this.titile = titile;
        this.content = content;
        this.datanumber = datanumber;
        this.namecontent = namecontent;
        this.category = category;
        this.createName = createName;
        this.createdata = createdata;
        this.totalPrice = totalPrice;
        this.provinceName = provinceName;
        this.cityName = cityName;
        this.countyName = countyName;
        this.townName = townName;
        this.detailAddress = detailAddress;
        this.meterUnitName = meterUnitName;
        this.price = price;
        this.householderIdcard = householderIdcard;
        this.declareNum = declareNum;
        this.standardDetailNumber = standardDetailNumber;
        this.rawNumber = rawNumber;
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

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatanumber() {
        return datanumber;
    }

    public void setDatanumber(String datanumber) {
        this.datanumber = datanumber;
    }

    public String getNamecontent() {
        return namecontent;
    }

    public void setNamecontent(String namecontent) {
        this.namecontent = namecontent;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreatedata() {
        return createdata;
    }

    public void setCreatedata(String createdata) {
        this.createdata = createdata;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getMeterUnitName() {
        return meterUnitName;
    }

    public void setMeterUnitName(String meterUnitName) {
        this.meterUnitName = meterUnitName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHouseholderIdcard() {
        return householderIdcard;
    }

    public void setHouseholderIdcard(String householderIdcard) {
        this.householderIdcard = householderIdcard;
    }

    public String getDeclareNum() {
        return declareNum;
    }

    public void setDeclareNum(String declareNum) {
        this.declareNum = declareNum;
    }

    public String getStandardDetailNumber() {
        return standardDetailNumber;
    }

    public void setStandardDetailNumber(String standardDetailNumber) {
        this.standardDetailNumber = standardDetailNumber;
    }

    public String getRawNumber() {
        return rawNumber;
    }

    public void setRawNumber(String rawNumber) {
        this.rawNumber = rawNumber;
    }
}
