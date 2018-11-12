package measure.jjxx.com.baselibrary.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import measure.jjxx.com.baselibrary.R;


/**
 * 加载时的等待dialog
 */
public class BaseDialog {
    public static Dialog dialog;

    public static void getDialog(Context mContext, String str, boolean status) {
        dialog = new Dialog(mContext, R.style.progress_dialog);
        dialog.setContentView(R.layout.base_waiting_dialog);
        dialog.setCanceledOnTouchOutside(status);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView text = (TextView) dialog.findViewById(R.id.id_tv_loadingmsg);
        text.setText(str);
        dialog.show();
    }


    /**
     * 打开 APP 的详情设置
     */
    public static void openAppDetails(final Context mContext) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("查看pdf需要访问内存权限，请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
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

}
