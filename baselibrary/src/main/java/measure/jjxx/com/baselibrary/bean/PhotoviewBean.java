package measure.jjxx.com.baselibrary.bean;

/**
 * @author lx
 * @Created by: 2018/10/17 0017.
 * @description:
 */

public class PhotoviewBean {
    //名字
    String pathtext;
    //地址
    String imgpath;
    //状态 是否需要下载
    String status;

    public PhotoviewBean(String pathtext, String imgpath, String status) {
        this.pathtext = pathtext;
        this.imgpath = imgpath;
        this.status = status;
    }

    public String getPathtext() {
        return pathtext;
    }

    public void setPathtext(String pathtext) {
        this.pathtext = pathtext;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
