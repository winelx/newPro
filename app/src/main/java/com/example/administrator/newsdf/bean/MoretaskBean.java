package com.example.administrator.newsdf.bean;

/**
 * Created by Administrator on 2018/5/3 0003.
 */

/**
 * description: 多任务上传任务内容
 *
 * @author lx
 *         date: 2018/5/3 0003 上午 10:37
 *         update: 2018/5/3 0003
 *         version:
 */
public class MoretaskBean {
    //任务名称
    String name;

    String image;

    public MoretaskBean() {
    }

    public MoretaskBean(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
