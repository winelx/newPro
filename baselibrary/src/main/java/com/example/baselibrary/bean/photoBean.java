package com.example.baselibrary.bean;

/**
 * @author lx
 * @data :2019/3/28 0028
 * @描述 : 图片展示实体类
 * @see
 */
public class photoBean {
    String photopath;
    String photoname;
    String phototype;//(id)
    public photoBean(String photopath, String photoname, String phototype) {
        this.photopath = photopath;
        this.photoname = photoname;
        this.phototype = phototype;
    }
    public photoBean(String photopath,  String phototype) {
        this.photopath = photopath;
        this.phototype = phototype;
    }
    public String getPhotopath() {
        return photopath;
    }

    public void setPhotopath(String photopath) {
        this.photopath = photopath;
    }

    public String getPhotoname() {
        return photoname;
    }

    public void setPhotoname(String photoname) {
        this.photoname = photoname;
    }

    public String getPhototype() {
        return phototype;
    }

    public void setPhototype(String phototype) {
        this.phototype = phototype;
    }
}
