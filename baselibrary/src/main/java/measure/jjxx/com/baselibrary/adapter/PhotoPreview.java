package measure.jjxx.com.baselibrary.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.bean.PhotoviewBean;
import measure.jjxx.com.baselibrary.ui.activity.PhotoViewActivity;

/**
 * Created by Administrator on 2018/10/17 0017.
 */

public class PhotoPreview {
    private final static int REQUEST_CODE = 666;
    //图片显示第几张位置
    public final static String ITEM_POSITION = "item_position";
    //图片地址
    public final static String PHOTOS_PATH = "photos_path";
    //图片状态
    public final static String PHOTOS_STATUS = "photos_status";
    //图片名称
    public final static String PHOTOS_NAME = "photos_name";
    //图片名字是否显示
    public final static String PHOTOS_NAME_LEAN = "photos_name_lean";

    public static PhotoPreviewBuilder builder() {
        return new PhotoPreviewBuilder();
    }

    public static class PhotoPreviewBuilder {
        private Bundle mPreviewOptionsBundle;
        private Intent mPreviewIntent;

        public PhotoPreviewBuilder() {
            mPreviewOptionsBundle = new Bundle();
            mPreviewIntent = new Intent();
        }

        /**
         * Send the Intent from an Activity with a custom request code
         *
         * @param activity    Activity to receive result
         * @param requestCode requestCode for result
         */
        public void start(@NonNull Activity activity, int requestCode) {
            activity.startActivityForResult(getIntent(activity), requestCode);
        }

        /**
         * Send the Intent with a custom request code
         *
         * @param fragment    Fragment to receive result
         * @param requestCode requestCode for result
         */
        public void start(@NonNull Context context, @NonNull android.support.v4.app.Fragment fragment, int requestCode) {
            fragment.startActivityForResult(getIntent(context), requestCode);
        }

        /**
         * Send the Intent with a custom request code
         *
         * @param fragment Fragment to receive result
         */
        public void start(@NonNull Context context, @NonNull android.support.v4.app.Fragment fragment) {
            fragment.startActivityForResult(getIntent(context), REQUEST_CODE);
        }

        /**
         * Send the crop Intent from an Activity
         *
         * @param activity Activity to receive result
         */
        public void start(@NonNull Activity activity) {
            start(activity, REQUEST_CODE);
        }

        public Intent getIntent(@NonNull Context context) {
            mPreviewIntent.setClass(context, PhotoViewActivity.class);
            mPreviewIntent.putExtras(mPreviewOptionsBundle);
            return mPreviewIntent;
        }

        //图片集合
        public PhotoPreviewBuilder setPhotos(ArrayList<PhotoviewBean> photoPaths) {
            //图片路径
            ArrayList<String> imagepath = new ArrayList<>();
            //图片类型
            ArrayList<String> imagestatus = new ArrayList<>();
            //图片名称
            ArrayList<String> imagename = new ArrayList<>();
            for (int i = 0; i < photoPaths.size(); i++) {
                imagepath.add(photoPaths.get(i).getImgpath());
                imagestatus.add(photoPaths.get(i).isStatus());
                imagename.add(photoPaths.get(i).getPathtext());
            }
            //图片地址
            mPreviewOptionsBundle.putStringArrayList(PHOTOS_PATH, imagepath);
            //图片状态（是否要下载）
            mPreviewOptionsBundle.putStringArrayList(PHOTOS_STATUS, imagestatus);
            //图片名字
            mPreviewOptionsBundle.putStringArrayList(PHOTOS_NAME, imagename);
            return this;
        }

        //当前图片的位置
        public PhotoPreviewBuilder setCurrentItem(int currentItem) {
            mPreviewOptionsBundle.putInt(ITEM_POSITION, currentItem);
            return this;
        }

        //是否显示图片名字
        public PhotoPreviewBuilder setPhotoName(boolean lean) {
            mPreviewOptionsBundle.putBoolean(PHOTOS_NAME_LEAN, lean);
            return this;
        }
    }
}
