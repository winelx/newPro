package com.example.administrator.newsdf.pzgc.utils;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;


import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.fragment.homepage.CommentmessageActivity;
import com.example.baselibrary.utils.screen.ScreenUtil;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * popwind类，相机相册选择弹窗
 */
public class PopCameraUtils {
    /**
     * 这是图册相机时的弹出框
     */
    public void showPopwindow(final Activity activity, final CameraCallback clickListener) {
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        backgroundAlpha((float) msg.obj, activity);
                        break;
                    default:
                        break;
                }
            }
        };
        //隐藏键盘
        try {
            ((InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(activity.getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
        }
        View parent = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        View popView = View.inflate(activity, R.layout.camera_pop_menu, null);
        Button btnCamera = popView.findViewById(R.id.btn_camera_pop_camera);
        Button btnAlbum = popView.findViewById(R.id.btn_camera_pop_album);
        Button btnCancel = popView.findViewById(R.id.btn_camera_pop_cancel);
        RelativeLayout btn_camera_pop = popView.findViewById(R.id.btn_pop_add);
        int width = activity.getResources().getDisplayMetrics().widthPixels;
        int height = activity.getResources().getDisplayMetrics().heightPixels;
        final PopupWindow popWindow = new PopupWindow(popView, width, height);
        popWindow.setAnimationStyle(R.style.AnimBottom);
        popWindow.setClippingEnabled(false);
        popWindow.setFocusable(true);
        // 设置同意在外点击消失
        popWindow.setOutsideTouchable(false);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    //调用相机
                    case R.id.btn_camera_pop_camera:
                        clickListener.oncamera();
                        break;
                    //相册图片
                    case R.id.btn_camera_pop_album:
                        clickListener.onalbum();
                        break;
                    //
                    case R.id.btn_camera_pop_cancel:
                        //关闭pop
                    case R.id.btn_pop_add:
                    default:
                        popWindow.dismiss();
                        break;
                }
                popWindow.dismiss();
                ScreenUtil.backgroundAlpha(activity, 1.0f);
            }
        };
        //延迟动画变暗效果，和pop同步
        new Thread(new Runnable() {
            @Override
            public void run() {
                float alpha = 1f;
                while (alpha > 0.5f) {
                    try {
                        //根据弹出动画时间和减少的透明度计算
                        Thread.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = mHandler.obtainMessage();
                    msg.what = 1;
                    //每次减少0.01，精度越高，变暗的效果越流畅
                    alpha -= 0.01f;
                    msg.obj = alpha;
                    mHandler.sendMessage(msg);
                }
            }
        }).start();
        btnCamera.setOnClickListener(listener);
        btn_camera_pop.setOnClickListener(listener);
        btnAlbum.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        popWindow.setBackgroundDrawable(dw);
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
