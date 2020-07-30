package com.example.baselibrary.bean;

public class FileTypeBean {
    /**
     * id : a241e558981b477e86d132a0ea26e8e1
     * filename : 2020年清明节放假安排的通知
     * filepath : upload/2020/07/30/3459c2e60cb7479fad52896548287547.pdf
     * fileext : pdf
     */

    private String id;
    private String filename;
    private String filepath;
    private String fileext;


    public FileTypeBean(String id, String filename, String filepath, String fileext) {
        this.id = id;
        this.filename = filename;
        this.filepath = filepath;
        this.fileext = fileext;
    }

    public FileTypeBean() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFileext() {
        return fileext;
    }

    public void setFileext(String fileext) {
        this.fileext = fileext;
    }
}
