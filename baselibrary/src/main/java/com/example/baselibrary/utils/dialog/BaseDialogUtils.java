package com.example.baselibrary.utils.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.example.baselibrary.utils.network.NetworkAdapter;

public class BaseDialogUtils {
    /**
     * @内容: 权限的提示，开APP 的详情设置
     * @author lx
     * @date: 2018/12/25 0025 上午 9:16
     */
    public static void openAppDetails(final Context mContext, String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(str);
        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + mContext.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                mContext.startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }
    public static void openAppDetails(final Context mContext, String str, NetworkAdapter adapter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(str);
        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.onsuccess();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

}
