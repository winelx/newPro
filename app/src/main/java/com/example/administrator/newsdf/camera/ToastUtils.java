package com.example.administrator.newsdf.camera;

import android.view.Gravity;
import android.widget.Toast;

import com.example.administrator.newsdf.baseApplication;

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
        if (baseApplication.getInstance() == null) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(baseApplication.getInstance(), message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.show();
        } else {
            toast.setGravity(Gravity.BOTTOM, 0, 100);
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
        if (baseApplication.getInstance() == null)
            return;
        if (toast == null) {
            toast = Toast.makeText(baseApplication.getInstance(), message, Toast.LENGTH_SHORT);
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
        if (baseApplication.getInstance() == null)
            return;
        if (toast == null) {
            toast = Toast.makeText(baseApplication.getInstance(), message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 100);
            toast.show();
        } else {
            toast.setGravity(Gravity.CENTER, 0, 100);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText(message);
            toast.show();
        }
    }
}
