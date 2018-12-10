package com.example.administrator.newsdf.pzgc.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;



/**
 * @author lx
 * @Created by: 2018/11/7 0007.
 * @description:
 */

/**
 * @Title: ListJsonUtils.java .<br>
 * @Package baseframe .<br>
 * @Description: listJSON转换工具类 .<br>
 * @author 郑成功 .<br>
 * @email a876459952@qq.com .<br>
 * @date 2018-11-7 下午4:09:55.<br>
 * @version V1.0.<br>
 *     解析json
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
                try {
                    JSONObject jsonObject = (JSONObject) object;
                    T t = JSONObject.toJavaObject(jsonObject, cls);
                    list.add(t);
                }catch (Exception e){
                }
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


}
