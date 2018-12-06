package com.example.administrator.newsdf.pzgc.utils;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;


import com.example.administrator.newsdf.R;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * popwind类，相机相册选择弹窗
 */
public class PopCameraUtils {
    /**
     * 这是图册相机时的弹出框
     */
    public void showPopwindow(final Activity activity, final CameraCallback clickListener) {
        ((InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        View parent = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        View popView = View.inflate(activity, R.layout.camera_pop_menu, null);
        RelativeLayout btn_camera_pop = popView.findViewById(R.id.btn_pop_add);
        Button btnCamera = (Button) popView.findViewById(R.id.btn_camera_pop_camera);
        Button btnAlbum = (Button) popView.findViewById(R.id.btn_camera_pop_album);
        Button btnCancel = (Button) popView.findViewById(R.id.btn_camera_pop_cancel);
        //计算屏幕宽高
        int width = activity.getResources().getDisplayMetrics().widthPixels;
        int height = activity.getResources().getDisplayMetrics().heightPixels;
        final PopupWindow popWindow = new PopupWindow(popView, width, height);
//        //添加显示隐藏动画
        popWindow.setAnimationStyle(R.style.popmenu_animation);
        popWindow.setAnimationStyle(R.style.AnimBottom);
        popWindow.setFocusable(true);
       backgroundAlpha((float) 0.5, activity);
        // 设置同意在外点击消失
        popWindow.setOutsideTouchable(true);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = v.getId();
                if (i == R.id.btn_camera_pop_camera) {
                    //打开相机
                    clickListener.oncamera();
                } else if (i == R.id.btn_camera_pop_album) {
                    //开启相
                    clickListener.onalbum();
                } else {
                }
                popWindow.dismiss();
                backgroundAlpha((float) 1, activity);
            }
        };
        btnCamera.setOnClickListener(listener);
        btnAlbum.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);
        btn_camera_pop.setOnClickListener(listener);
        popWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    public interface CameraCallback {
        /**
         * 获取数据的方法
         *
         * @param
         */
        void oncamera();

        void onalbum();

    }

    //界面亮度
    public void backgroundAlpha(float bgAlpha, Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }

}
