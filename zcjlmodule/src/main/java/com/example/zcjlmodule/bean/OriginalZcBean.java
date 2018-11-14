package com.example.zcjlmodule.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author lx
 * @Created by: 2018/10/16 0016.
 * @description:原始勘丈表界面的数据实体 界面：OrginalZcActivity
 */

public class OriginalZcBean {

    /**
     * beneficiary :
     * cityName : 遵义市
     * countyName : 湄潭县
     * createBy : {"credentialsSalt":"nullnull","delFlag":"0","id":"3d978d1f866c426b9c01c1b028d8828c","new":false,"status":"1"}
     * createDate : 2018-11-12 18:21:50
     * createName : 徐耀宗
     * declareNum : 0.3952
     * detailAddress : 黄家坝镇（街道办事处）大寨村龙口组
     * headquarter : 402881145fddf1be015fe14339320008
     * headquarterName : 湄潭县指挥部
     * householder : 周懿芳
     * householderIdcard : 522128197908111518
     * householderPhone :
     * id : 787360e15a3d48d0ac9ea1383a5c1659
     * levyTypeName : 永久用地/旱地
     * meterUnitName : 元/亩
     * new : false
     * number : XSXM-MT-HD-08763
     * orgId : c4fcd77b464744b28f6977c51958ba8d
     * period : 3e74613ba014446e8281a714a7d940b9
     * periodName : 第1期
     * price : 39600.0
     * project : 2b7631d7a1dc4043b4842d8461b13870
     * projectName : 新石项目
     * provinceName : 贵州省
     * rawNumber : MS61
     * remarks :
     * standardDetail : 4028811461c5b4a10161f5a458770a7f
     * standardDetailNumber : XSGS-TDZS-209002
     * status : 0
     * tender : 491785c0647b4fe4ae78bc1cbd3cc1ef
     * tenderName : MSTJ-1
     * totalPrice : 15649.92
     * townName : 黄家坝街道办事处
     * filePath : 5
     */

    private String beneficiary;
    private String cityName;
    private String countyName;
    private String createDate;
    private String createName;
    private String declareNum;
    private String detailAddress;
    private String headquarter;
    private String headquarterName;
    private String householder;
    private String householderIdcard;
    private String householderPhone;
    private String id;
    private String levyTypeName;
    private String meterUnitName;
    @JSONField(name = "new")
    private boolean newX;
    private String number;
    private String orgId;
    private String period;
    private String periodName;
    private double price;
    private String project;
    private String projectName;
    private String provinceName;
    private String rawNumber;
    private String remarks;
    private String standardDetail;
    private String standardDetailNumber;
    private int status;
    private String tender;
    private String tenderName;
    private String totalPrice;
    private String townName;
    private String filePath;

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
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


    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getDeclareNum() {
        return declareNum;
    }

    public void setDeclareNum(String declareNum) {
        this.declareNum = declareNum;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getHeadquarter() {
        return headquarter;
    }

    public void setHeadquarter(String headquarter) {
        this.headquarter = headquarter;
    }

    public String getHeadquarterName() {
        return headquarterName;
    }

    public void setHeadquarterName(String headquarterName) {
        this.headquarterName = headquarterName;
    }

    public String getHouseholder() {
        return householder;
    }

    public void setHouseholder(String householder) {
        this.householder = householder;
    }

    public String getHouseholderIdcard() {
        return householderIdcard;
    }

    public void setHouseholderIdcard(String householderIdcard) {
        this.householderIdcard = householderIdcard;
    }

    public String getHouseholderPhone() {
        return householderPhone;
    }

    public void setHouseholderPhone(String householderPhone) {
        this.householderPhone = householderPhone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevyTypeName() {
        return levyTypeName;
    }

    public void setLevyTypeName(String levyTypeName) {
        this.levyTypeName = levyTypeName;
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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getRawNumber() {
        return rawNumber;
    }

    public void setRawNumber(String rawNumber) {
        this.rawNumber = rawNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStandardDetail() {
        return standardDetail;
    }

    public void setStandardDetail(String standardDetail) {
        this.standardDetail = standardDetail;
    }

    public String getStandardDetailNumber() {
        return standardDetailNumber;
    }

    public void setStandardDetailNumber(String standardDetailNumber) {
        this.standardDetailNumber = standardDetailNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTender() {
        return tender;
    }

    public void setTender(String tender) {
        this.tender = tender;
    }

    public String getTenderName() {
        return tenderName;
    }

    public void setTenderName(String tenderName) {
        this.tenderName = tenderName;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return
                "beneficiary=" + beneficiary +
                        ",cityName=" + cityName +
                        ",countyName=" + countyName +
                        ",createDate=" + createDate +
                        ",createName=" + createName +
                        ",declareNum=" + declareNum +
                        ",detailAddress=" + detailAddress +
                        ",headquarter=" + headquarter +
                        ",headquarterName=" + headquarterName +
                        ",householder=" + householder +
                        ",householderIdcard=" + householderIdcard +
                        ",householderPhone=" + householderPhone +
                        ",id=" + id +
                        ",levyTypeName=" + levyTypeName +
                        ",meterUnitName=" + meterUnitName +
                        ",newX=" + newX +
                        ",number=" + number +
                        ",orgId=" + orgId +
                        ",period=" + period +
                        ",periodName=" + periodName +
                        ",price=" + price +
                        ",project=" + project +
                        ",projectName=" + projectName +
                        ",provinceName=" + provinceName +
                        ",rawNumber=" + rawNumber +
                        ",remarks=" + remarks +
                        ",standardDetail=" + standardDetail +
                        ",standardDetailNumber=" + standardDetailNumber +
                        ",status=" + status +
                        ",tender=" + tender +
                        ",tenderName=" + tenderName +
                        ",totalPrice=" + totalPrice +
                        ",townName=" + townName +
                        ",filePath=" + filePath
                ;
    }
}
