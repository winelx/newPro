package com.example.administrator.yanghu.pzgc.bean;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/11/29 0029.
 * @description:
 */

public class DeviceDetailsTop {

    DeviceDetailsBean bean;
    ArrayList<DeviceTrem> list;

    public DeviceDetailsTop(DeviceDetailsBean bean, ArrayList<DeviceTrem> list) {
        this.bean = bean;
        this.list = list;
    }

    public DeviceDetailsBean getBean() {
        return bean;
    }

    public void setBean(DeviceDetailsBean bean) {
        this.bean = bean;
    }

    public ArrayList<DeviceTrem> getList() {
        return list;
    }

    public void setList(ArrayList<DeviceTrem> list) {
        this.list = list;
    }
}
