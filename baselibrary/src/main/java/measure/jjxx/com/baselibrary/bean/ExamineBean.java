package measure.jjxx.com.baselibrary.bean;

/**
 * Created by Administrator on 2018/11/5 0005.
 */

public class ExamineBean {
    String name;
    String path;
    String type;

    public ExamineBean(String name, String path, String type) {
        this.name = name;
        this.path = path;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
