package com.example.zcjlmodule.bean;

/**
 * @author lx
 * @Created by: 2018/11/8 0008.
 * @description:
 */

public class StandardDecomposeBean {

    /**
     * city : f8cc65205b17b1f9015b18d6b6ac0231
     * cityName : 毕节市
     * compensateType : HBBC
     * county : f8cc6566637ef244016382ba96e40216
     * countyName : 赫章县
     * createDate : 2018-05-24 14:36:10
     * delFlag : 0
     * id : f8cc6566638e72da016390dca80400cc
     * levyType : f8cc785057b6e1c60157b7eca22d01d3
     * levyTypeName : 拆迁管理/零星林木/产前期/桃
     * meterUnit : 402881145fed20050160245591ad0b07
     * meterUnitName : 元/株
     * new : false
     * number : HLHZ-LXLM-0001
     * orgId : 36a78c5e512a4b8998482769f2f3592f
     * price : 20.00
     * province : 402881145fec091f015feccfa26100f2
     * provinceName : 贵州省
     * remarks :
     * standardId : f8cc656663893dcd01638ae785d002b5
     * standardName : 毕高铁指通【2017】20号
     * standardNumber : HLGS-CQBC-001
     * status : 0
     */

    private String city;
    private String cityName;
    private String compensateType;
    private String county;
    private String countyName;
    private String createDate;
    private String delFlag;
    private String id;
    private String levyType;
    private String levyTypeName;
    private String meterUnit;
    private String meterUnitName;

    private boolean newX;
    private String number;
    private String orgId;
    private String price;
    private String province;
    private String provinceName;
    private String remarks;
    private String standardId;
    private String standardName;
    private String standardNumber;
    private int status;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCompensateType() {
        return compensateType;
    }

    public void setCompensateType(String compensateType) {
        this.compensateType = compensateType;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevyType() {
        return levyType;
    }

    public void setLevyType(String levyType) {
        this.levyType = levyType;
    }

    public String getLevyTypeName() {
        return levyTypeName;
    }

    public void setLevyTypeName(String levyTypeName) {
        this.levyTypeName = levyTypeName;
    }

    public String getMeterUnit() {
        return meterUnit;
    }

    public void setMeterUnit(String meterUnit) {
        this.meterUnit = meterUnit;
    }

    public String getMeterUnitName() {
        return meterUnitName;
    }

    public void setMeterUnitName(String meterUnitName) {
        this.meterUnitName = meterUnitName;
    }

    public boolean isNewX() {
        return newX;
    }

    public void setNewX(boolean newX) {
        this.newX = newX;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getStandardNumber() {
        return standardNumber;
    }

    public void setStandardNumber(String standardNumber) {
        this.standardNumber = standardNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
                 return
                        "city" + city +
                        ",cityName" + cityName +
                        ",compensateType" + compensateType +
                        ",county" + county +
                        ",countyName" + countyName +
                        ",createDate" + createDate +
                        ",delFlag" + delFlag +
                        ",id" + id +
                        ",levyType" + levyType +
                        ",levyTypeName" + levyTypeName +
                        ",meterUnit" + meterUnit +
                        ",meterUnitName" + meterUnitName +
                        ",newX" + newX +
                        ",number" + number +
                        ",orgId" + orgId +
                        ",price" + price +
                        ",province" + province +
                        ",provinceName" + provinceName +
                        ",remarks" + remarks +
                        ",standardId" + standardId +
                        ",standardName" + standardName +
                        ",standardNumber" + standardNumber +
                        ",status" + status;
    }
}
