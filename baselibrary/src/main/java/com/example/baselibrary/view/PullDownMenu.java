package com.example.baselibrary.view;

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

import com.example.baselibrary.R;
import com.example.baselibrary.utils.ScreenUtil;

/**
 * @author lx
 * @Created by: 2018/12/5 0005.
 * @description: 安卓menu下拉菜单（已做屏幕适配）
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

    /**
     * @内容: 安卓menu下拉菜单（已做屏幕适配）
     * @author lx
     * @date: 2018/12/25 0025 上午 11:08
     */
    public void showPopMeun(final Activity activity, View view, final String[] strings) {
        //计算屏幕密度
        float resolution = getDisplayMetrics(activity).density;
        int layoutId = R.layout.pull_down_menu_layout;
        View contentView = LayoutInflater.from(activity).inflate(layoutId, null);
        ListView mListView = contentView.findViewById(R.id.list_item);
        //创建一个simpleAdapter
        ArrayAdapter myAdapter = new ArrayAdapter(activity, R.layout.meun_list_item, R.id.text, strings);
        mListView.setAdapter(myAdapter);
        //屏幕高度
        int hight = strings.length * 40;
        mPopupWindow = new PopupWindow(contentView,
                withFontSize(resolution), ScreenUtil.dp2px(activity, hight) + hightFontSize(activity), true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置好参数之后再show
        // 默认在mButton2的左下角显示
        mPopupWindow.showAsDropDown(view);
        //解决背景闪烁问题
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //显示隐藏动画
        mPopupWindow.setAnimationStyle(R.style.popmenu_animation);
        ScreenUtil.backgroundAlpha(activity, 0.5f);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ScreenUtil.backgroundAlpha(activity, 1f);
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

    /**
     * @内容: 根据密度设置宽度
     * @author lx
     * @date: 2018/12/25 0025 上午 10:38
     */
    public static int withFontSize(float screenWidth) {
        float flies = (float) 100.0;
        int writh = (int) (screenWidth * flies);
        return writh;
    }

    public static int hightFontSize(Activity activity) {
        float hoghtdp = ScreenUtil.getDensity(activity);
        float flies = (float) 10.0;
        int hight = (int) (hoghtdp * flies);
        return hight;
    }
}