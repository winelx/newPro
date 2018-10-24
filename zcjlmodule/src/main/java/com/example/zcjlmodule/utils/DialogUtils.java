package com.example.zcjlmodule.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.zcjlmodule.R;

import measure.jjxx.com.baselibrary.utils.DatesUtils;

/**
 * @author lx
 * @Created by: 2018/10/16 0016.
 * @description:
 */

public class DialogUtils {
    private static PopupWindow mPopupWindow;

    //界面亮度
    public static void backgroundAlpha(float bgAlpha, Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }


    //任务状态弹出窗
    public static void meunPop(final Activity activity, View view, float DIMENSION, final DialogUtils.onclick onclick) {
        int layoutId = R.layout.activity_original_pop_zc;
        View contentView = LayoutInflater.from(activity).inflate(layoutId, null);
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = v.getId();
                if (i == R.id.pop_dismantling_zc) {
                    //                        mPopupWindow.dismiss();
                    onclick.openonclick("征拆类型查询");

                } else if (i == R.id.pop_region_zc) {
                    onclick.openonclick("按区域查询");

                } else if (i == R.id.pop_form_zc) {
                    onclick.openonclick("按表单查询");

                } else if (i == R.id.pop_details_zc) {
                    onclick.openonclick("按户主明细查询");

                } else if (i == R.id.pop_data_zc) {
                    //                        mPopupWindow.dismiss();
                    onclick.openonclick("按期数查询");

                } else {
                }
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        };
        contentView.findViewById(R.id.pop_dismantling_zc).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_region_zc).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_form_zc).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_details_zc).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_data_zc).setOnClickListener(menuItemOnClickListener);

        mPopupWindow = new PopupWindow(contentView,
                DatesUtils.withFontSize(DIMENSION) + 20, DatesUtils.higtFontSize(DIMENSION), true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置好参数之后再show
        // 默认在mButton2的左下角显示
        mPopupWindow.showAsDropDown(view);
        DialogUtils.backgroundAlpha(0.5f, activity);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1.0f;
                activity.getWindow().setAttributes(lp);
                activity.getWindow().clearFlags(
                        WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });
    }

    //orgiinalZcActivity的menu回调接口
    public interface onclick {
        void openonclick(String string);
    }


    public static void dismantling(Context mContext, final onclick click) {
        // 创建对话框构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        // 获取布局
        View view = View.inflate(mContext, R.layout.meun_dialog_dismantling_zc, null);
        // 获取布局中的控件
        final EditText content = (EditText) view.findViewById(R.id.dialog_editext);
        final TextView dismiss = (TextView) view.findViewById(R.id.dialog_dismiss);
        final TextView success = (TextView) view.findViewById(R.id.dialog_success);
        // 创建对话框
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        dialog.setView(view);//添加布局
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();// 对话框消失
            }
        });
        success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.openonclick(content.getText().toString());
                dialog.dismiss();// 对话框消失
            }
        });
        dialog.show();
    }




}
