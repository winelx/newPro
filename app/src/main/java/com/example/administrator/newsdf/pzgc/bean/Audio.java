package com.example.administrator.newsdf.pzgc.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/8 0008.
 */

public class Audio  implements Serializable {

    String name;
    String content;
    String title;
    public Audio(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public Audio(String name, String content, String title) {
        this.name = name;
        this.content = content;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}

