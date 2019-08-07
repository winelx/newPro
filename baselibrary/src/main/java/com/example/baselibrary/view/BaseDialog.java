package com.example.baselibrary.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baselibrary.R;
import com.example.baselibrary.inface.Onclicklitener;

import java.util.concurrent.atomic.DoubleAccumulator;

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
                onclicklitener.confirm("确定");
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
                onclicklitener.confirm("确定");
                dialog.dismiss();
            }
        });
        dialog.show();
        //设置背景样式
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.base_dialog_bg);
    }

    /**
     * 单选
     */

    // 单选提示框
    private AlertDialog alertDialog2;
    private String content;
    public void getadio(Context  mContext,final Onclicklitener onclicklitener){
        final String[] items = {"是","否"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
        alertBuilder.setTitle("是否列入奖罚");
        alertBuilder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int index) {
                content=items[index];
            }
        });
        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //TODO 业务逻辑代码
                if (arg1!=-1){
                    Toast.makeText(mContext, items[arg1], Toast.LENGTH_SHORT).show();
                }
                onclicklitener.confirm(content);
                // 关闭提示框
                alertDialog2.dismiss();
            }
        });
        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO 业务逻辑代码
                // 关闭提示框
                alertDialog2.dismiss();
            }
        });
        alertDialog2 = alertBuilder.create();
        alertDialog2.show();
    }
}
