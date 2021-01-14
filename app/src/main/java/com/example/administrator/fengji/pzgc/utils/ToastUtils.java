package com.example.administrator.fengji.pzgc.utils;

import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.fengji.App;

/**
 * Created by solexit04 on 2016/12/20.
 * 提示框帮助类
 */

public class ToastUtils {
    private static Toast toast;

    /**
     * 显示长时间的Toast
     *
     * @param message 消息
     */
    public static void showLongToast(String message) {
        if (App.getInstance() == null) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(App.getInstance(), message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.show();
        } else {
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setText(message);
            toast.show();
        }
    }

    public static void showLongToastCenter(String message) {
        if (App.getInstance() == null) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(App.getInstance(), message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 100);
            toast.show();
        } else {
            toast.setGravity(Gravity.CENTER, 0, 100);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setText(message);
            toast.show();
        }
    }


    /**
     * 显示短时间Toast
     *
     * @param message 消息
     */
    public static void showShortToast(String message) {
        if (App.getInstance() == null) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(App.getInstance(), message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.show();
        } else {
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText(message);
            toast.show();
        }
    }

    /**
     * 显示短时间Toast
     *
     * @param message 消息
     */
    public static void showShortToastCenter(String message) {
        if (App.getInstance() == null) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(App.getInstance(), message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 100);
            toast.show();
        } else {
            toast.setGravity(Gravity.CENTER, 0, 100);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText(message);
            toast.show();
        }
    }

    public static void showsnackbar(View view, String string) {
        Snackbar.make(view, string, Snackbar.LENGTH_SHORT).show();
    }
}
