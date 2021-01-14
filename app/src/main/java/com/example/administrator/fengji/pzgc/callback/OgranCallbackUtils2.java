package com.example.administrator.fengji.pzgc.callback;

/**
 * Created by Administrator on 2018/4/4 0004.
 */
/**
 * description: 切换组织界面刷新我的界面和全部界面
 * @author lx
 * date: 2018/6/11 0011 上午 9:33
 * update: 2018/6/11 0011
 * version:
*/
public class OgranCallbackUtils2 {
    private static OgranCallback mCallBack;

    public static void setCallBack(OgranCallback callBack) {
        mCallBack = callBack;
    }

    public static void removeCallBackMethod() {
        mCallBack.taskCallback();
    }
}
