package measure.jjxx.com.baselibrary.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 郑成功 .<br>
 * @version V1.0.<br>
 * @Title: MapUtils.java .<br>
 * @Package baseframe .<br>
 * @Description: map和Bean互转工具类 .<br>
 * @email a876459952@qq.com .<br>
 * @date 2018-11-7 下午4:19:16.<br>
 */
public class MapUtils {

    /**
     * @param bean 要转化的JavaBean 对象  .<br>
     * @return 转化出来的  Map 对象  .<br>
     * @throws Exception 返回异常.<br>
     * @Description: 将一个 JavaBean 对象转化为一个  Map  .<br>
     * @author 郑成功 .<br>
     * @date 2018-11-7 下午4:18:04.<br>
     */
//    public static Map<String, Object> convertBean(Object bean) throws Exception {
//        Class<?> type = bean.getClass();
//        Map<String, Object> returnMap = new HashMap<String, Object>();
//        BeanInfo beanInfo = Introspector.getBeanInfo(type);
//        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//        for (int i = 0; i < propertyDescriptors.length; i++) {
//            PropertyDescriptor descriptor = propertyDescriptors[i];
//            String propertyName = descriptor.getName();
//            if (!propertyName.equals("class")) {
//                Method readMethod = descriptor.getReadMethod();
//                Object result = readMethod.invoke(bean, new Object[0]);
//                if (result != null) {
//                    returnMap.put(propertyName, result);
//                } else {
//                    returnMap.put(propertyName, "");
//                }
//            }
//        }
//        return returnMap;
//    }


    /**
     * @param type 要转化的类型  .<br>
     * @param map  包含属性值的 map .<br>
     * @return 转化出来的 JavaBean 对象 .<br>
     * @throws Exception .<br>
     * @Description: * 将一个 Map 对象转化为一个 JavaBean .<br>
     * @author 郑成功 .<br>
     * @date 2018-11-7 下午4:18:54.<br>
     */
//    public static Object convertMap(Class type, Map map) throws Exception {
//        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
//        Object obj = type.newInstance(); // 创建 JavaBean 对象
//
//        // 给 JavaBean 对象的属性赋值
//        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//        for (int i = 0; i < propertyDescriptors.length; i++) {
//            PropertyDescriptor descriptor = propertyDescriptors[i];
//            String propertyName = descriptor.getName();
//
//            if (map.containsKey(propertyName)) {
//                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
//                Object value = map.get(propertyName);
//
//                Object[] args = new Object[1];
//                args[0] = value;
//
//                descriptor.getWriteMethod().invoke(obj, args);
//            }
//        }
//        return obj;
//    }

}
