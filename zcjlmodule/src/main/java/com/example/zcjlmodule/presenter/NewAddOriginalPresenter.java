package com.example.zcjlmodule.presenter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.zcjlmodule.view.NewAddOriginalView;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BasePresenters;

/**
 * @author lx
 * @Created by: 2018/11/2 0002.
 * @description:
 */

public class NewAddOriginalPresenter extends BasePresenters<NewAddOriginalView> {
    /**
     * 显示隐藏图标
     */
    public void visibility(boolean lean, ArrayList<ImageView> imagelist) {
        if (lean) {
            for (int i = 0; i < imagelist.size(); i++) {
                imagelist.get(i).setVisibility(View.VISIBLE);
            }
        } else {
            for (int i = 0; i < imagelist.size(); i++) {
                imagelist.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 输入框点击事件屏蔽
     */
    public void clickable(boolean lean, ArrayList<EditText> editTexlist) {
        if (lean) {
            for (int i = 0; i < editTexlist.size(); i++) {
                editTexlist.get(i).setEnabled(true);
            }
        } else {
            for (int i = 0; i < editTexlist.size(); i++) {
                editTexlist.get(i).setEnabled(false);
            }
        }
    }

    /**
     * 界面控件的margin的设置
     *
     * @param v 控件
     * @param l 左
     * @param t 上
     * @param r 右
     * @param b 下
     */
    public void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
