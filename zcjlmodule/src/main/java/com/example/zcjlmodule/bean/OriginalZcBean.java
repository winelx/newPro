package com.example.zcjlmodule.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author lx
 * @Created by: 2018/10/16 0016.
 * @description:原始勘丈表界面的数据实体 界面：OrginalZcActivity
 */

public class OriginalZcBean {

        /**
         * cityName : 遵义市
         * countyName : 余庆县
         * createBy : {"credentialsSalt":"nullnull","delFlag":"0","id":"c686446e387643a0b400d50958373e17","new":false,"status":"1"}
         * createDate : 2018-08-16 11:51:23
         * createName : 郑成功
         * declareNum : 0.0055
         * detailAddress : 贵州省遵义市余庆县松烟镇火炭组
         * filePath : 1
         * headquarter : 402881145fddf1be015fe1439730000a
         * headquarterName : 余庆县指挥部
         * householder : 王兴连
         * householderIdcard : 522129195709305026
         * householderPhone : 522129195709305026
         * id : 4028811461c5b4a101627213fccb1073
         * levyTypeName : 永久用地/旱地
         * meterUnitName : 元/亩
         * new : false
         * number : XSXM-YQ-HD-00010
         * orgId : 92ad8a9378864accb56b90fd7fab405e
         * period : 3e74613ba014446e8281a714a7d940b9
         * periodName : 第1期
         * price : 39600.0
         * provinceName : 贵州省
         * quoteNumber : 1
         * rawNumber : MS02Y10
         * remarks :
         * standardDetail : 4028811461c5b4a10161e458385b0656
         * standardDetailNumber : XSGS-TDZD-301002
         * status : 0
         * tender : 31c03ade58a24405acb751e78e08b12a
         * totalPrice : 217.80
         * townName : 松烟镇
         */

        private String cityName;
        private String countyName;

        private String createDate;
        private String createName;
        private String declareNum;
        private String detailAddress;
        private String filePath;
        private String headquarter;
        private String headquarterName;
        private String householder;
        private String householderIdcard;
        private String householderPhone;
        private String id;
        private String levyTypeName;
        private String meterUnitName;

        private boolean newX;
        private String number;
        private String orgId;
        private String period;
        private String periodName;
        private double price;
        private String provinceName;
        private String quoteNumber;
        private String rawNumber;
        private String remarks;
        private String standardDetail;
        private String standardDetailNumber;
        private int status;
        private String tender;
        private String totalPrice;
        private String townName;
        private String beneficiary;
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

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
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

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getQuoteNumber() {
            return quoteNumber;
        }

        public void setQuoteNumber(String quoteNumber) {
            this.quoteNumber = quoteNumber;
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



    @Override
    public String toString() {
        return
                "cityName=" + cityName +
                ",countyName=" + countyName +
                ",createDate=" + createDate +
                ",createName=" + createName +
                ",declareNum=" + declareNum +
                ",detailAddress=" + detailAddress +
                ",filePath=" + filePath +
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
                ",provinceName=" + provinceName +
                ",quoteNumber=" + quoteNumber +
                ",rawNumber=" + rawNumber +
                ",remarks=" + remarks +
                ",standardDetail=" + standardDetail +
                ",standardDetailNumber=" + standardDetailNumber +
                ",status=" + status +
                ",tender=" + tender +
                ",totalPrice=" + totalPrice +
                ",townName=" + townName;
    }

}
