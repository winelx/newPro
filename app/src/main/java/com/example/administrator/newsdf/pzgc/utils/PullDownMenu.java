package com.example.administrator.newsdf.pzgc.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import com.example.administrator.newsdf.R;

/**
 * @author lx
 * @Created by: 2018/12/5 0005.
 * @description:
 * @Activity：
 */

public class PullDownMenu {
    private PopupWindow mPopupWindow;

    public interface OnItemClickListener {
        void onclick(int position, String string);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void showPopMeun(final Activity activity, View view, final String[] strings) {
        //计算屏幕密度
        float resolution = getDisplayMetrics(activity).density;
        int layoutId = R.layout.pull_down_menu_layout;
        View contentView = LayoutInflater.from(activity).inflate(layoutId, null);
        ListView mListView = contentView.findViewById(R.id.list_item);
        //创建一个simpleAdapter
        ArrayAdapter myAdapter = new ArrayAdapter(activity, R.layout.meun_list_item, R.id.text, strings);
        mListView.setAdapter(myAdapter);
        mPopupWindow = new PopupWindow(contentView,
                withFontSize(resolution) + 20, 115 * strings.length, true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置好参数之后再show
        // 默认在mButton2的左下角显示
        mPopupWindow.showAsDropDown(view);
        backgroundAlpha(0.5f, activity);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f, activity);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mOnItemClickListener.onclick(position, strings[position]);
                mPopupWindow.dismiss();
            }
        });
    }

    //界面亮度
    public void backgroundAlpha(float bgAlpha, Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }

    /**
     * @description: 计算屏幕密度
     * @author lx
     * @date: 2018/12/5 0005 上午 11:13
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    //根据密度设置宽度
    public static int withFontSize(float screenWidth) {
        // 240X320 屏幕
        if (screenWidth == 1.0) {
            return 250;
            // 320X480 屏幕
        } else if (screenWidth == 2.0) {
            return 250;
            // 480X800 或 480X854 屏幕
        } else if (screenWidth == 3.0) {
            return 300;
        } else {
            return 300;

        }
    }

}
