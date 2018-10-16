package com.example.zcjlmodule.bean;

/**
 * Created by Administrator on 2018/10/12 0012.
 */

public class PayCheckZcBean {
    String id;
    String name;
    String number;
    String payname;
    String paymoney;
    String checkmoney;


    public PayCheckZcBean(String id, String name, String number, String payname, String paymoney, String checkmoney) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.payname = payname;
        this.paymoney = paymoney;
        this.checkmoney = checkmoney;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPayname() {
        return payname;
    }

    public void setPayname(String payname) {
        this.payname = payname;
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
}
