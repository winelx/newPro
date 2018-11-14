package measure.jjxx.com.baselibrary.bean;

/**
 * description: 图片泛型类
 * @author lx
 * date: 2018/11/14 0014 上午 10:12
*/
public class ExamineBean {
    String id;
    String name;
    String path;
    String type;

    public ExamineBean(String id, String name, String path, String type) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
