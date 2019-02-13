package com.example.administrator.newsdf.pzgc.bean;

public class Checkitem {
    String string;
    boolean leam;

    public Checkitem(String string, boolean leam) {
        this.string = string;
        this.leam = leam;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public boolean isLeam() {
        return leam;
    }

    public void setLeam(boolean leam) {
        this.leam = leam;
    }
}
