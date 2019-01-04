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



    //orgiinalZcActivity的menu回调接口
    public interface onclick {
        void openonclick(String string);
    }

    public static void dismantling(Context mContext, String titles, final onclick click) {
        // 创建对话框构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        // 获取布局
        View view = View.inflate(mContext, R.layout.meun_dialog_dismantling_zc, null);
        // 获取布局中的控件
        final EditText content = (EditText) view.findViewById(R.id.dialog_editext);
        final TextView dismiss = (TextView) view.findViewById(R.id.dialog_dismiss);
        final TextView success = (TextView) view.findViewById(R.id.dialog_success);
        final TextView title = (TextView) view.findViewById(R.id.dialog_title);
        title.setText(titles);
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
