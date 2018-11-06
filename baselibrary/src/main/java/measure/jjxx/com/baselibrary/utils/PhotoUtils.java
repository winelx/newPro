package measure.jjxx.com.baselibrary.utils;

/**
 * Created by Administrator on 2018/11/5 0005.
 */

import java.util.ArrayList;
import java.util.List;

import measure.jjxx.com.baselibrary.bean.ExamineBean;
import measure.jjxx.com.baselibrary.bean.PhotoviewBean;

/**
 * description: 点击图片展示帮助类，
 *
 * @author lx
 *         date: 2018/11/5 0005 下午 2:01
 *         update: 2018/11/5 0005
 *         version:
 */
public class PhotoUtils {
    private static ArrayList<String> str;
    private static ArrayList<Boolean> status;

    //传递文字

    /**
     * @param str    传递的数据图片地址
     * @param status 图片状态，是否需要显示下载按钮
     * @return
     */
    public static ArrayList<PhotoviewBean> getPhoto(String str, boolean status) {
        ArrayList<PhotoviewBean> pathlsit = new ArrayList<>();
        if (str != null) {
            pathlsit.add(new PhotoviewBean("", str, status + ""));
        }
        return pathlsit;
    }

    public static ArrayList<PhotoviewBean> getPhoto(String str) {
        ArrayList<PhotoviewBean> pathlsit = new ArrayList<>();
        if (str != null) {
            pathlsit.add(new PhotoviewBean("", str, "false"));
        }
        return pathlsit;
    }

    /**
     * @param str    传递的数据图片地址
     * @param status 图片状态，是否需要显示下载按钮
     * @return
     */
    public static ArrayList<PhotoviewBean> getPhoto(ArrayList<String> str, boolean status) {
        ArrayList<PhotoviewBean> pathlsit = new ArrayList<>();
        for (int i = 0; i < str.size(); i++) {
            pathlsit.add(new PhotoviewBean("", str.get(i), status + ""));
        }
        return pathlsit;
    }

    public static ArrayList<PhotoviewBean> getPhoto(ArrayList<String> str) {
        ArrayList<PhotoviewBean> pathlsit = new ArrayList<>();
        for (int i = 0; i < str.size(); i++) {
            pathlsit.add(new PhotoviewBean("", str.get(i), "false"));
        }
        return pathlsit;
    }

    /**
     * @param str    传递的数据图片地址
     * @param status 图片状态，是否需要显示下载按钮
     * @return
     */
    public static ArrayList<PhotoviewBean> getPhoto(List<ExamineBean> str, ArrayList<Boolean> status) {
        ArrayList<PhotoviewBean> pathlsit = new ArrayList<>();
        //图片路径
        ArrayList<String> imagepath = new ArrayList<>();
        //图片类型
        ArrayList<String> imagestatus = new ArrayList<>();
        //图片名称
        ArrayList<String> imagename = new ArrayList<>();
        for (int i = 0; i < str.size(); i++) {

        }
        return pathlsit;
    }

    public static ArrayList<PhotoviewBean> getPhoto(List<ExamineBean> str, boolean status) {
        ArrayList<PhotoviewBean> pathlsit = new ArrayList<>();
        for (int i = 0; i < str.size(); i++) {
            pathlsit.add(new PhotoviewBean(str.get(i).getName(), str.get(i).getPath(), status + ""));
        }
        return pathlsit;
    }

    public static ArrayList<PhotoviewBean> getPhoto(List<ExamineBean> str) {
        ArrayList<PhotoviewBean> pathlsit = new ArrayList<>();
            for (int i = 0; i < str.size(); i++) {
                //筛选出图片
                String type1 = str.get(i).getType();
                if ("pdf".equals(type1) || "doc".equals(type1) || "docx".equals(type1) || "xlsx".equals(type1) || "xls".equals(type1)) {
                } else {
                    pathlsit.add(new PhotoviewBean(str.get(i).getName(), str.get(i).getPath(), "false"));
                }
            }
        return pathlsit;
    }
}
