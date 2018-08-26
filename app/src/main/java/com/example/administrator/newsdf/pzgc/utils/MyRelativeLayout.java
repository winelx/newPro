package com.example.administrator.newsdf.pzgc.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.example.administrator.newsdf.camera.ToastUtils;

/**
 * Created by 10942 on 2018/8/26 0026.
 */

public class MyRelativeLayout extends RelativeLayout {
    boolean lanere =false;
    public MyRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRelativeLayout(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//            if (lanere){
//                ToastUtils.showShortToast("当前不是编辑状态");
//            }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return false;
    }
    public  void  setstatusT(){
        lanere =true;
    }
    public  void  setstatusF(){
        lanere =false;
    }
}
