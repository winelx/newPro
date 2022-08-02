package com.example.administrator.yanghu.pzgc.bean;

public class LodingBean {
    //地址
    String address;
    //纬度
    String Latitudel;
    //经度
    String Longitude;

    public LodingBean() {

    }

    public LodingBean(String address, String latitudel, String longitude) {
        this.address = address;
        Latitudel = latitudel;
        Longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitudel() {
        return Latitudel;
    }

    public void setLatitudel(String latitudel) {
        Latitudel = latitudel;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    @Override
    public String toString() {
        return "{" +
                "address:'" + address + '\'' +
                ", Latitudel:'" + Latitudel + '\'' +
                ", Longitude:'" + Longitude + '\'' +
                '}';
    }
}
