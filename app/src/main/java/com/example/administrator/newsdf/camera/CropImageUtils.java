package com.example.administrator.newsdf.camera;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.example.administrator.newsdf.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by solexit04 on 2017/3/8.
 * 裁剪图片工具
 */

public class CropImageUtils {
    //7.0  ContentUri
    public static final String FILE_CONTENT_FILEPROVIDER = "com.example.administrator.newsdf";
    private static CropImageUtils instance;
    public static final String APP_NAME = "bao";
    //打开相机的返回码
    public static final int REQUEST_CODE_TAKE_PHOTO = 11111;
    //打开相册的返回码
    public static final int REQUEST_CODE_SELECT_PICTURE = 11112;
    //剪切图片的返回码
    public static final int REQUEST_CODE_CROP_PICTURE = 11113;
    //相机拍照默认存储路径
    public static final String PICTURE_DIR = Environment.getExternalStorageDirectory()
            + File.separator + "pictures" + File.separator + "cropUtils";
    public String DATE = "";
    //照片图片名
    private String photo_image;
    //截图图片名
    private String crop_image;

    public static CropImageUtils getInstance() {
        if (instance == null) {
            synchronized (CropImageUtils.class) {
                if (instance == null) {
                    instance = new CropImageUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 打开系统相册
     */
    public void openAlbum(Activity activity) {
        DATE = new SimpleDateFormat("yyyy_MMdd_hhmmss").format(new Date());
        if (isSdCardExist()) {
            Intent intent;
            if (Build.VERSION.SDK_INT < 19) {
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
            } else {
                intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }
            activity.startActivityForResult(intent, REQUEST_CODE_SELECT_PICTURE);
        } else {
            ToastUtils.showShortToast(activity.getResources().getString(R.string.sdcard_no_exist));
        }
    }

    /**
     * 打开系统相机
     */
    public void takePhoto(Activity activity) {
        DATE = new SimpleDateFormat("yyyy_MMdd_hhmmss").format(new Date());
        if (isSdCardExist()) {
            photo_image = createImagePath(APP_NAME + DATE);
            File file = new File(photo_image);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //Android7.0以上URI
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                //通过FileProvider创建一个content类型的Uri
                Uri uri = FileProvider.getUriForFile(activity, FILE_CONTENT_FILEPROVIDER, file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            }
            try {
                activity.startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
            } catch (ActivityNotFoundException anf) {
                ToastUtils.showShortToast(activity.getResources().getString(R.string.camera_not_prepared));
            }
        } else {
            ToastUtils.showShortToast(activity.getResources().getString(R.string.sdcard_no_exist));
        }
    }


    /**
     * 拍照/打开相册/剪裁图片的回调
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(Activity activity, int requestCode, int resultCode
            , Intent data, OnResultListener listener) {
        switch (requestCode) {
            case REQUEST_CODE_TAKE_PHOTO:
                if (!TextUtils.isEmpty(photo_image)) {
                    File file = new File(photo_image);
                    if (file.isFile() && listener != null)
                        listener.takePhotoFinish(photo_image);
                    // 最后通知图库更新
//                    activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                }
                break;
            case REQUEST_CODE_SELECT_PICTURE:
                if (data != null) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        String path = GetPathFromUri.getInstance().getPath(activity, uri);
                        File file = new File(path);
                        if (file.isFile() && listener != null) {
                        }
                        //         listener.selectPictureFinish(path);
                    }
                }
                break;
            case REQUEST_CODE_CROP_PICTURE:
                if (!TextUtils.isEmpty(crop_image)) {
                    File file = new File(crop_image);
                    if (file.isFile() && listener != null)
                        //  listener.cropPictureFinish(crop_image);
                        // 最后通知图库更新
                    {
                        activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 创建图片的存储路径
     */
    public static String createImagePath(String imageName) {
        String dir = PICTURE_DIR;
        File destDir = new File(dir);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        File file = null;
        if (!TextUtils.isEmpty(imageName)) {
            file = new File(dir, imageName + ".jpeg");
        }
        return file.getAbsolutePath();
    }

    /**
     * 检查SD卡是否存在
     */
    public boolean isSdCardExist() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public interface OnResultListener {
        //拍照回调
        void takePhotoFinish(String path);

//        //选择图片回调
//        void selectPictureFinish(String path);
//
//        //截图回调
//        void cropPictureFinish(String path);
    }


    /**
     * 读取照片旋转角度
     *
     * @param path 照片路径
     * @return 角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * 旋转图片
     * @param angle 被旋转角度
     * @param bitmap 图片对象
     * @return 旋转后的图片
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bitmap;
        }
        if (bitmap != returnBm) {
            bitmap.recycle();
        }
        return returnBm;
    }
}
