package measure.jjxx.com.baselibrary.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import measure.jjxx.com.baselibrary.R;


/**
 * 加载时的等待dialog
 */
public class BaseDialogUtils {
    public static Dialog dialog;
    public static AlertDialog alertdialog;

    /**
     * @param mContext 上下文
     * @param str      提示内容
     * @param status   是否允许点击外部消失
     */
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
     * 提示
     *
     * @param mContext
     * @param str
     * @param status
     */
    public static void getprompt(Context mContext, String str, final onclicktlister onclicktlister) {
        alertdialog = new AlertDialog.Builder(mContext).setMessage(str).
                setPositiveButton("保存", null).setNegativeButton("放弃", null).create();
        alertdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positionButton = alertdialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = alertdialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                positionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onclicktlister.onsuccess();
                    }
                });
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onclicktlister.onerror();
                    }
                });
            }
        });
        alertdialog.show();
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

    public interface onclicktlister {
        void onsuccess();

        void onerror();
    }
}
