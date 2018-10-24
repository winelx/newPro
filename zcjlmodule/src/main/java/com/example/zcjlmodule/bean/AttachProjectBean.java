package com.example.zcjlmodule.bean;

/**
 * Created by Administrator on 2018/10/24 0024.
 */

public class AttachProjectBean {
    private String id;
    private String name;

    public AttachProjectBean(String id, String name) {
        this.id = id;
        this.name = name;
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
}
