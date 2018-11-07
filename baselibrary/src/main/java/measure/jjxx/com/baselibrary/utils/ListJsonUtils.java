package measure.jjxx.com.baselibrary.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/**
 * @author lx
 * @Created by: 2018/11/7 0007.
 * @description:
 */

import java.util.List;

import measure.jjxx.com.baselibrary.base.Bean;

/**
 * @Title: ListJsonUtils.java .<br>
 * @Package baseframe .<br>
 * @Description: listJSON转换工具类 .<br>
 * @author 郑成功 .<br>
 * @email a876459952@qq.com .<br>
 * @date 2018-11-7 下午4:09:55.<br>
 * @version V1.0.<br>
 */
public class ListJsonUtils {

    /**
     * @Description: 根据JSONArray String获取到List .<br>
     * @param cls 接收对象.<br>
     * @param jArrayStr json数组字符串.<br>
     * @return 对应的List.<br>
     * @author 郑成功 .<br>
     * @date 2018-11-7 下午4:10:27.<br>
     */
    public static <T> List<T> getListByArray(Class<T> cls,String jArrayStr) {
        List<T> list = new ArrayList<>();
            JSONArray jsonArray = JSONArray.parseArray(jArrayStr);
            if (jsonArray==null || jsonArray.isEmpty()) {
                return list;
            }
            for (Object object : jsonArray) {
                JSONObject jsonObject = (JSONObject) object;
                T t = JSONObject.toJavaObject(jsonObject, cls);
                list.add(t);
            }
        return list;
    }

    /**
     * @Description: 将list<T>转换成JSONArray数组 .<br>
     * @param list 转换的数组.<br>
     * @author 郑成功 .<br>
     * @date 2018-11-7 下午4:11:32.<br>
     */
    public static JSONArray getJSONArrayByList(List<?> list){
        JSONArray jsonArray = new JSONArray();
        if (list==null ||list.isEmpty()) {
            return jsonArray;
        }

        for (Object object : list) {
            jsonArray.add(object);
        }

        return jsonArray;
    }

    public static void main(String[] args) {
        JSONArray array = new JSONArray();
        JSONObject o = new JSONObject();
        o.put("id", "1");
        o.put("name", "你好");
        array.add(o);
        List<Bean> list = ListJsonUtils.getListByArray(Bean.class, array.toJSONString());
        for(Bean b:list){
            System.err.println(list.size()+" id："+b.getId()+" 名："+b.getName());
        }
        JSONArray newarray = getJSONArrayByList(list);
        System.out.println(newarray);
    }
}
