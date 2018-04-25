package com.example.administrator.newsdf.bean;

/**
 * Created by Administrator on 2018/4/25 0025.
 */

public class BrightBean {
    String name;
    String content;
    String block;
    String time;
    String list;

    public BrightBean(String name, String content, String block, String time, String list) {
        this.name = name;
        this.content = content;
        this.block = block;
        this.time = time;
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }
}
