package com.example.baselibrary.base;



import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;

import android.view.Window;




import com.example.baselibrary.utils.manager.AppManager;
import com.example.baselibrary.view.PermissionListener;


import java.util.ArrayList;

import java.util.List;

/**
 * @author lx
 * @data :2019/4/24 0024
 * @描述 : 基础activity
 * @see
 */
public class BaseActivity extends AppCompatActivity {

    private PermissionListener mListener;
    private static final int PERMISSION_REQUESTCODE = 10086;
    /**
     * activity堆栈管理
     */
    protected AppManager appManager = AppManager.getAppManager();
    /**
     * 是否允许全屏
     **/
    private boolean mAllowFullScreen = false;
    /**
     * 是否禁止旋转屏幕
     **/
    private boolean isAllowScreenRoate = false;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appManager.addActivity(this);
        mContext=this;
        //是否允许屏幕旋转
        if (!isAllowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        if (mAllowFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

    }

    /**
     * 权限申请
     *
     * @param permissions 权限集合
     * @param listener    回调
     */
    public void requestRunPermisssion(String[] permissions, PermissionListener listener) {
        mListener = listener;
        List<String> permissionLists = new ArrayList<>();
        //遍历申请权限
        for (String permission : permissions) {
            if (PermissionChecker.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionLists.add(permission);
            }
        }
        //判断是否授权
        if (!permissionLists.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionLists.toArray(new String[permissionLists.size()]), PERMISSION_REQUESTCODE);
        } else {
            //表示全都授权了
            mListener.onGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUESTCODE:
                if (grantResults.length > 0) {
                    //存放没授权的权限
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissions.add(permission);
                        }
                    }
                    if (deniedPermissions.isEmpty()) {
                        //说明都授权了
                        mListener.onGranted();
                    } else {
                        mListener.onDenied(deniedPermissions);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 从栈中移除activity
        appManager.finishActivity(this);
    }

    /**
     * [是否允许屏幕旋转]
     *
     * @param isAllowScreenRoate
     */
    public void setScreenRoate(boolean isAllowScreenRoate) {
        this.isAllowScreenRoate = isAllowScreenRoate;
    }

    /**
     * [是否允许全屏]
     *
     * @param allowFullScreen
     */
    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }


}
