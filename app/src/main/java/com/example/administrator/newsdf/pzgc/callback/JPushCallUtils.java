package com.example.administrator.newsdf.pzgc.callback;

/**
 * Created by Administrator on 2018/4/2 0002.
 */

/**
 * description: 推送消息后，更新首页tab的红点（idexfragment，mianactivity/）
 *
 * @author lx
 *         date: 2018/6/11 0011 上午 9:41
 *         update: 2018/6/11 0011
 *         version:
 */
public class JPushCallUtils {
    private static JPushCallBack mCallBack;

    public static void setCallBack(JPushCallBack callBack) {

        mCallBack = callBack;
    }

    public static void removeCallBackMethod() {
        mCallBack.doSomeThing();
    }
}
