package com.example.administrator.newsdf.pzgc.activity.audit.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.DailyrecordAdapter;
import com.example.administrator.newsdf.pzgc.activity.audit.ReportActivity;
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
    private NumberPicker yearPicker, monthPicker, dayPicker;
    private String[] NumberMonth, NumberYear;
    private Date myDate = new Date();
    private int dateMonth, dayDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_daily, null);
            daily_list = rootView.findViewById(R.id.daily_list);
            datatime = rootView.findViewById(R.id.datatime);
            rootView.findViewById(R.id.audit_title).setOnClickListener(this);

        }
        for (int i = 0; i < 100; i++) {
            list.add("");
        }
        mContext = getActivity();
        mAdapter = new DailyrecordAdapter(mContext, list);
        daily_list.setAdapter(mAdapter);
        NumberMonth = Utils.month;
        NumberYear = Utils.years;
        dayDate = myDate.getDate() - 1;
        dateMonth = myDate.getMonth();
        datatime.setText(Utils.titleDay());
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
        final View contentView = LayoutInflater.from(mContext).inflate(layoutId, null);
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.pop_determine:
                        //获取年
                        String yeardata = Utils.years[yearPicker.getValue()];
                        //获取月
                        int month = monthPicker.getValue();
                        String monthdata = Utils.month[month];
                        //获取天
                        int day = dayPicker.getValue();
                        String daydata;
                        if (monthdata.equals("02月")) {
                            //是二月份
                            if (Utils.getyear().contains(yeardata)) {
                                daydata = Utils.daytwos[day];
                                //闰年
                            } else {
                                //平年
                                daydata = Utils.daytwo[day];
                            }
                        } else {
                            //不是二月份
                            daydata = Utils.day[day];
                        }
                        datatime.setText(yeardata + monthdata + daydata);
                        break;
                    case R.id.pop_dismiss:
                    default:
                        break;
                }
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        };
        contentView.findViewById(R.id.pop_dismiss).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_determine).setOnClickListener(menuItemOnClickListener);
        yearPicker = contentView.findViewById(R.id.years);
        Utils.setPicker(yearPicker, Utils.years, Utils.titleyear());
        monthPicker = contentView.findViewById(R.id.month);
        Utils.setPicker(monthPicker, Utils.month, dateMonth);
        dayPicker = contentView.findViewById(R.id.day);
        if (dateMonth == 2) {
            Utils.setPicker(dayPicker, Utils.daytwo, dayDate);
        } else if (dateMonth == 1 || dateMonth == 3 || dateMonth == 5 || dateMonth == 7 || dateMonth == 8 || dateMonth == 10 || dateMonth == 12) {
            Utils.setPicker(dayPicker, Utils.day, dayDate);
        } else {
            Utils.setPicker(dayPicker, Utils.dayth, dayDate);
        }
        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                setyear(i1);

            }
        });
        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal,
                                      int newVal) {
                setMonth(newVal);
            }
        });

        return contentView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.audit_title:
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

    //设置选择年，控制二月天数
    public void setyear(int i1) {
        //月份
        String mont = Utils.month[monthPicker.getValue()];
        //年份
        String str = NumberYear[i1];
        //如果选择中的月份是二月
        if (mont.equals("02月")) {
            //判断是闰年还是平年
            if (Utils.getyear().contains(str)) {
                dayPicker.setDisplayedValues(null);
                dayPicker.setMaxValue(Utils.daytwos.length - 1);
                dayPicker.setDisplayedValues(Utils.daytwos);
                dayPicker.setMinValue(0);
            } else {
                dayPicker.setDisplayedValues(null);
                dayPicker.setMaxValue(Utils.daytwo.length - 1);
                dayPicker.setDisplayedValues(Utils.daytwo);
                dayPicker.setMinValue(0);
            }
        }
    }

    //设置选择月，控制二月天数
    public void setMonth(int newVal) {
        String NewVal = NumberMonth[newVal];
        String years = NumberYear[yearPicker.getValue()];
        if (NewVal.equals("02月")) {
            if (Utils.getyear().contains(years)) {
                //如果是闰年。二月有29天
                dayPicker.setDisplayedValues(null);
                dayPicker.setMaxValue(Utils.daytwos.length - 1);
                dayPicker.setDisplayedValues(Utils.daytwos);
                dayPicker.setMinValue(0);
            } else {
                //如果是平年。二月有28天
                dayPicker.setDisplayedValues(null);
                dayPicker.setMaxValue(Utils.daytwo.length - 1);
                dayPicker.setDisplayedValues(Utils.daytwo);
                dayPicker.setMinValue(0);
            }
        } else if (NewVal.equals("01月") || NewVal.equals("03月") || NewVal.equals("05月") ||
                NewVal.equals("07月") || NewVal.equals("08月") || NewVal.equals("10月") || NewVal.equals("12月")) {
            dayPicker.setDisplayedValues(null);
            dayPicker.setMaxValue(Utils.day.length - 1);
            dayPicker.setDisplayedValues(Utils.day);
            dayPicker.setMinValue(0);
        } else if (NewVal.equals("04月") || NewVal.equals("06月") || NewVal.equals("09月") || NewVal.equals("11月")) {
            dayPicker.setDisplayedValues(null);
            dayPicker.setMaxValue(Utils.dayth.length - 1);
            dayPicker.setDisplayedValues(Utils.dayth);
            dayPicker.setMinValue(0);
        }
    }

    public String getData() {
        //获取年
        String yeardata = Utils.years[yearPicker.getValue()];
        //获取月
        int month = monthPicker.getValue();
        String monthdata = Utils.month[month];
        //获取天
        int day = dayPicker.getValue();
        String daydata;
        if (monthdata.equals("02月")) {
            //是二月份
            if (Utils.getyear().contains(yeardata)) {
                daydata = Utils.daytwos[day];
                //闰年
            } else {
                //平年
                daydata = Utils.daytwo[day];
            }
        } else {
            //不是二月份
            daydata = Utils.day[day];
        }
        return yeardata + monthdata + daydata;
    }
}
