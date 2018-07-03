package com.example.administrator.newsdf.pzgc.activity.audit.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.newsdf.App;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.DailyrecordAdapter;
import com.example.administrator.newsdf.pzgc.activity.audit.ReportActivity;
import com.example.administrator.newsdf.pzgc.utils.ScreenUtil;
import com.example.administrator.newsdf.pzgc.utils.Utils;

import java.util.ArrayList;
import java.util.Date;

/**
 * description:每日审核
 *
 * @author lx
 *         date: 2018/7/3 0003 上午 10:03
 *         update: 2018/7/3 0003
 *         version:
 */

public class DailyrecordFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private ListView daily_list;
    private TextView datatime;
    private ArrayList<String> list = new ArrayList();
    private DailyrecordAdapter mAdapter;
    private Context mContext;
    private PopupWindow mPopupWindow;
    private float ste;
    private NumberPicker yearPicker, monthPicker, dayPicker;
    private String[] NumberMonth;
    private Date myDate = new Date();
    private int dateMonth, dayDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_daily, null);
            daily_list = rootView.findViewById(R.id.daily_list);
            datatime = rootView.findViewById(R.id.datatime);
        }
        ste = ScreenUtil.getDensity(App.getInstance());
        mContext = getActivity();
        datatime.setOnClickListener(this);
        mAdapter = new DailyrecordAdapter(mContext, list);
        daily_list.setAdapter(mAdapter);
        NumberMonth = Utils.month;
        dayDate = myDate.getDate();
        dateMonth = myDate.getMonth() + 1;
        return rootView;
    }


    //弹出框
    private void MeunPop() {
        View contentView = getPopupWindowContentView();
        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置好参数之后再show
        // 默认在mButton2的左下角显示
        mPopupWindow.showAsDropDown(datatime);
        Utils.backgroundAlpha(0.5f, ReportActivity.getInstance());
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());
    }

    //设置pop的点击事件
    private View getPopupWindowContentView() {
        // 一个自定义的布局，作为显示的内容
        // 布局ID
        int layoutId = R.layout.popwind_daily;
        View contentView = LayoutInflater.from(mContext).inflate(layoutId, null);
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    default:
                        break;
                }
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        };
        TextView time = contentView.findViewById(R.id.createdate);
        yearPicker = contentView.findViewById(R.id.years);
        setPicker(yearPicker, Utils.years, 8);
        monthPicker = contentView.findViewById(R.id.month);
        setPicker(monthPicker, Utils.month, dateMonth - 1);
        dayPicker = contentView.findViewById(R.id.day);
        if (dateMonth == 2) {
            setPicker(dayPicker, Utils.daytwo, dayDate);
        } else if (dateMonth == 1 || dateMonth == 3 || dateMonth == 5 || dateMonth == 7 || dateMonth == 8 || dateMonth == 10 || dateMonth == 12) {
            setPicker(dayPicker, Utils.day, dayDate);
        } else {
            setPicker(dayPicker, Utils.dayth, dayDate);
        }

        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal,
                                      int newVal) {
                String str = NumberMonth[newVal];
                if (str.equals("02月")) {
                    dayPicker.setDisplayedValues(Utils.daytwo);
                } else if (str.equals("01月") || str.equals("03月") || str.equals("05月") || str.equals("07月") || str.equals("08月") || str.equals("10月") || str.equals("12月")) {
                    dayPicker.setDisplayedValues(Utils.day);
                } else if (str.equals("04月") || str.equals("06月") || str.equals("09月") || str.equals("11月")) {
                    dayPicker.setDisplayedValues(Utils.dayth);
                }
            }
        });
        return contentView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.datatime:
                MeunPop();
                break;
            default:
                break;
        }
    }

    /**
     * popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            Utils.backgroundAlpha(1f, ReportActivity.getInstance());
        }
    }

    public void setPicker(NumberPicker View, String[] str, int time) {
        //设置需要显示的数组
        View.setDisplayedValues(str);
        View.setValue(5);
        View.setMinValue(0);
        View.setWrapSelectorWheel(true);
        //这两行不能缺少,不然只能显示第一个，关联到format方法
        View.setMaxValue(str.length - 1);
        //中间不可点击
        View.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
    }
}
