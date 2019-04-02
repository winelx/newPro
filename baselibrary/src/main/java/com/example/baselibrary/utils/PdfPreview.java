package com.example.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.baselibrary.ui.PdfActivity;


/**
 * @author lx
 * @data :2019/3/26 0026
 * @描述 : 打开pdf的v帮助类
 * @see
 */
public class PdfPreview {
    //地址
    public final static String PDF = "http";
    //回调返回值
    private final static int REQUEST_CODE = 5566;

    public static PdfPreview.PhotoPreviewBuilder builder() {
        return new PdfPreview.PhotoPreviewBuilder();
    }

    public static class PhotoPreviewBuilder {
        private Bundle mPreviewOptionsBundle;
        private Intent mPreviewIntent;

        public PhotoPreviewBuilder() {
            mPreviewOptionsBundle = new Bundle();
            mPreviewIntent = new Intent();
        }

        /**
         * 使用自定义请求代码从活动发送意图
         *
         * @param activity    Activity to receive result
         * @param requestCode requestCode for result 自定义回调code，
         */
        public void start(@NonNull Activity activity, int requestCode) {
            activity.startActivityForResult(getIntent(activity), requestCode);
        }


        /**
         * 使用自定义请求代码从活动发送意图
         *
         * @param fragment    Fragment to receive result
         * @param requestCode requestCode for result 自定义回调code，
         */
        public void start(@NonNull Context context, @NonNull android.support.v4.app.Fragment fragment, int requestCode) {
            fragment.startActivityForResult(getIntent(context), requestCode);
        }

        /**
         * 使用自定义请求代码从活动发送意图
         *
         * @param fragment Fragment to receive result 使用默认的5566作为返回值
         */
        public void start(@NonNull Context context, @NonNull android.support.v4.app.Fragment fragment) {
            fragment.startActivityForResult(getIntent(context), REQUEST_CODE);
        }

        /**
         * 使用自定义请求代码从活动发送意图
         *
         * @param activity Activity to receive result 使用默认的5566作为返回值
         */
        public void start(@NonNull Activity activity) {
            start(activity, REQUEST_CODE);
        }

        /**
         * @内容: 指定传递的界面
         * @author lx
         * @date: 2018/12/26 0026 上午 10:43
         */
        public Intent getIntent(@NonNull Context context) {
            mPreviewIntent.setClass(context, PdfActivity.class);
            mPreviewIntent.putExtras(mPreviewOptionsBundle);
            return mPreviewIntent;
        }

        /**
         * @内容:传递的pdf
         * @author lx
         * @date: 2018/12/26 0026 上午 10:45
         */
        public PdfPreview.PhotoPreviewBuilder setPdfUrl(String currentItem) {
            mPreviewOptionsBundle.putString(PDF, currentItem);
            return this;
        }

    }
}
