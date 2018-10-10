package measure.jjxx.com.baselibrary.bean;

/**
 * Created by Administrator on 2018/10/10 0010.
 */

public class BaseTab {
    private int title;
    private int icon;
    private Class fragemnt;
    private Integer number;
    public BaseTab(Class fragemnt, int title, int icon,Integer number) {
        this.fragemnt = fragemnt;
        this.title = title;
        this.icon = icon;
        this.number = number;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Class getFragemnt() {
        return fragemnt;
    }

    public void setFragemnt(Class fragemnt) {
        this.fragemnt = fragemnt;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
