package com.example.zcjlmodule.bean;

import com.example.zcmodule.R;

/**
 * @author lx
 * @Created by: 2018/10/22 0022.
 * @description:
 */

public class PayDetailedlistBean {
    String id;
    //标题
    String titile;
    //文件编号
    String number;
    //期数
    String datetime;
    //文件内容
    String content;
    //支付金额
    String paymoney;
    //检查金额
    String checkmoney;
    //未检查金额
    String nocheckmoney;

    public PayDetailedlistBean(String id, String titile, String number, String datetime, String content, String paymoney, String checkmoney, String nocheckmoney) {
        this.id = id;
        this.titile = titile;
        this.number = number;
        this.datetime = datetime;
        this.content = content;
        this.paymoney = paymoney;
        this.checkmoney = checkmoney;
        this.nocheckmoney = nocheckmoney;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPaymoney() {
        return paymoney;
    }

    public void setPaymoney(String paymoney) {
        this.paymoney = paymoney;
    }

    public String getCheckmoney() {
        return checkmoney;
    }

    public void setCheckmoney(String checkmoney) {
        this.checkmoney = checkmoney;
    }

    public String getNocheckmoney() {
        return nocheckmoney;
    }

    public void setNocheckmoney(String nocheckmoney) {
        this.nocheckmoney = nocheckmoney;
    }
}
