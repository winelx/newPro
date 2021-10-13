package com.example.administrator.yanghu.pzgc.utils;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


import com.example.baselibrary.view.PermissionListener;

import java.util.ArrayList;
import java.util.List;
/**
 *创建人：lx
 *调加时间：2019/5/13 0013 9:34
 *说明：相机权限申请
 **/
public class CameraUtils {

    private static PermissionListener mListener;
    private static final int PERMISSION_REQUESTCODE = 10086;
    private static String[] str={Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    /*回调*/
    public static void requestRunPermisssion(Activity activity, PermissionListener listener) {
        mListener = listener;
        List<String> permissionLists = new ArrayList<>();
        for (String permission : str) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionLists.add(permission);
            }
        }
        if (!permissionLists.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissionLists.toArray(new String[permissionLists.size()]), PERMISSION_REQUESTCODE);
        } else {
            //表示全都授权了
            mListener.onGranted();
        }
    }
}
