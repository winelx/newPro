package com.example.administrator.newsdf.bean;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public class FileBean {
    String  id;
    String  filepath;
    String  filename;
    String  bill_id;

    public FileBean(String id, String filepath, String filename, String bill_id) {
        this.id = id;
        this.filepath = filepath;
        this.filename = filename;
        this.bill_id = bill_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }
}
