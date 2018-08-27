package com.example.administrator.newsdf.treeviews.bean;

/**
 * Created by Administrator on 2018/3/20 0020.
 */

public class OrgenBeans {
    String id;
    String parentId;
    String name;
    String type;

    public OrgenBeans(String id, String parentId, String name,String type) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
