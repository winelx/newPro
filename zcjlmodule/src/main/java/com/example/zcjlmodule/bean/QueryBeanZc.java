package com.example.zcjlmodule.bean;

/**
 * @author lx
 * @Created by: 2018/10/16 0016.
 * @description:
 */

public class QueryBeanZc {
    String id;
    String content;

    public QueryBeanZc(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
