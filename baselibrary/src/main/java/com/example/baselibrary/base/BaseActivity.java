package com.example.baselibrary.base;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;

import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;


import com.example.baselibrary.R;
import com.example.baselibrary.utils.dialog.DownLogindUtils;
import com.example.baselibrary.utils.manager.AppManager;
import com.example.baselibrary.view.NumberProgressBar;
import com.example.baselibrary.view.PermissionListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;


import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


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
    /**
     * 进度条
     */
    private NumberProgressBar progressbar;
    /**
     * 下载弹窗
     */
    private Dialog dialogs;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appManager.addActivity(this);

        mContext = this;
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


    public void updatadownload(String url) {
        //设置样式
        dialogs = new Dialog(mContext, R.style.progress_dialog);
        //设置布局
        dialogs.setContentView(R.layout.dialog_download);
        //是否允许点击返回键或者提示框外部取消
        dialogs.setCanceledOnTouchOutside(false);
        //物理返回键
        dialogs.setCancelable(false);
        //设置dialog弹出时背景颜色
        dialogs.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressbar = (NumberProgressBar) dialogs.findViewById(R.id.par);
        dialogs.show();
        DownLogindUtils.down(url, getCallingPackage(), new DownLogindUtils.DownCallback() {
            @Override
            public void onsuccess(String path) {
                installApk(mContext, path);
                dialogs.dismiss();
            }

            @Override
            public void onerror(String str) {
                Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
                ;
            }

            @Override
            public void onprogress(int progress) {
                progressbar.setProgress(progress);
            }
        });
    }

    /**
     * 安装apk
     *
     * @param mContext
     * @param filePath
     */
    public static void installApk(Context mContext, String filePath) {
        File file = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Android 7.0及以上
        if (Build.VERSION.SDK_INT >= 24) {
            // 参数2 清单文件中provider节点里面的authorities ; 参数3  共享的文件,即apk包的file类
            Uri apkUri = FileProvider.getUriForFile(mContext, "com.example.administrator.newsdf", file);
            //对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }

        mContext.startActivity(intent);
    }


}
