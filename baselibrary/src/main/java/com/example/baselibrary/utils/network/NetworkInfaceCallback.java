package com.example.baselibrary.utils.network;
/**
 * @author： lx
 * @创建时间：2019/10/17 0017 14:14
 * @说明： 接口回调类，方便在函数中进行回调
 **/
public interface NetworkInfaceCallback {

    void onerror();

    void onerror(String string);

    void onerror(Object o);

    void onsuccess();

    void onsuccess(String string);

    void onsuccess(Object o);

}
