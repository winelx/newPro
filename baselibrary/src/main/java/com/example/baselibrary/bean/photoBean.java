package com.example.baselibrary.bean;

public class photoBean {
    String photopath;
    String photoname;
    String phototype;//(id)

    public photoBean(String photopath, String photoname, String phototype) {
        this.photopath = photopath;
        this.photoname = photoname;
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
