package com.example.baselibrary.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.baselibrary.R;
import com.example.baselibrary.inface.Onclicklitener;

public class BaseDialog {

    /*简单提示框*/
    public static void confirmdialog(Context mContext, String titles, String contents, final Onclicklitener onclicklitener) {
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        // 获取布局
        View view = View.inflate(mContext, R.layout.base_dialog_choice, null);
        //添加布局
        dialog.setView(view);
        //标题
        TextView title = view.findViewById(R.id.title);
        if (title != null) {
            title.setText(titles);
        }
        //内容
        TextView content = view.findViewById(R.id.content);
        if (contents != null) {
            content.setText(contents);
        }
        //取消
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclicklitener.cancel("取消");
                dialog.dismiss();
            }
        });
        //确定
        view.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclicklitener.cancel("确定");
                dialog.dismiss();
            }
        });
        dialog.show();
        //设置背景样式
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.base_dialog_bg);
    }

    /*自定义*/
    public static void confirmmessagedialog(Context mContext, String titles, String contents, String cancels, String confirms, final Onclicklitener onclicklitener) {
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        // 获取布局
        View view = View.inflate(mContext, R.layout.base_dialog_choice, null);
        //添加布局
        dialog.setView(view);
        //标题
        TextView title = view.findViewById(R.id.title);
        if (title != null) {
            title.setText(titles);
        }
        //内容
        TextView content = view.findViewById(R.id.content);
        if (contents != null) {
            content.setText(contents);
        }
        //取消
        TextView cancel = view.findViewById(R.id.cancel);
        if (cancels != null) {
            cancel.setText(cancels);
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclicklitener.cancel("取消");
                dialog.dismiss();
            }
        });
        //确定
        TextView confirm = view.findViewById(R.id.confirm);
        if (confirms != null) {
            confirm.setText(confirms);
        }
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclicklitener.cancel("确定");
                dialog.dismiss();
            }
        });
        dialog.show();
        //设置背景样式
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.base_dialog_bg);
    }
}
