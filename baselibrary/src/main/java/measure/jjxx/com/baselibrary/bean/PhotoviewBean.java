package measure.jjxx.com.baselibrary.bean;

/**
 * @author lx
 * @Created by: 2018/10/17 0017.
 * @description:
 */

public class PhotoviewBean {
    String pathtext;
    String imgpath;
    boolean status;

    public PhotoviewBean(String pathtext, String imgpath, boolean status) {
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
