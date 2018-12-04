package com.example.administrator.newsdf.pzgc.utils.list;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.CheckUtils;
import com.example.administrator.newsdf.pzgc.utils.Utils;

import java.util.Date;

/**
 * @author lx
 * @Created by: 2018/12/4 0004.
 * @description:
 * @Activity：
 */

public class DialogUtils {
    CheckUtils checkUtils = new CheckUtils();
    //拿到月
    String[] numbermonth = Utils.month;
    //拿到年
    String[] numberyear = Utils.year;

    public void selectiontime(Context mContext, final OnClickListener click) {
        // 创建对话框构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        // 获取布局
        View view = View.inflate(mContext, R.layout.popwind_daily, null);
        // 获取布局中的控件
        //取消
        final TextView dismiss = (TextView) view.findViewById(R.id.pop_dismiss);
        //确定
        final TextView success = (TextView) view.findViewById(R.id.pop_determine);
        final NumberPicker yearPicker = (NumberPicker) view.findViewById(R.id.years);
        final NumberPicker monthPicker = (NumberPicker) view.findViewById(R.id.month);
        final NumberPicker dayPicker = (NumberPicker) view.findViewById(R.id.day);
        //初始化数据---年
        Utils.setPicker(yearPicker, Utils.year, Utils.titleyear());
        //初始化数据---月
        Date myDate = new Date();
        int dateMonth = myDate.getMonth();
        int dayDate = myDate.getDate() - 1;
        Utils.setPicker(monthPicker, Utils.month, dateMonth);
        //初始化数据---日
        String yeardata = Utils.year[yearPicker.getValue()];
        //如果当前月份是2月
        if (dateMonth == 2) {
            if (Utils.getyear().contains(yeardata)) {
                Utils.setPicker(dayPicker, Utils.daytwos, dayDate);
                //闰年
            } else {
                //平年
                Utils.setPicker(dayPicker, Utils.daytwo, dayDate);
            }
        } else {
            if (dateMonth == 0 || dateMonth == 2 || dateMonth == 4 || dateMonth == 6 || dateMonth == 7 || dateMonth == 9 || dateMonth == 11) {
                Utils.setPicker(dayPicker, Utils.day, dayDate);
            } else {
                Utils.setPicker(dayPicker, Utils.dayth, dayDate);
            }
        }
        //年份选择器。如果当前的月份是二月，
        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                checkUtils.setyear(monthPicker, dayPicker, i1, numberyear);
            }
        });
        //月份选择器。如果当前的月份是二月，
        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal,
                                      int newVal) {
                checkUtils.setMonth(yearPicker, monthPicker, dayPicker, newVal, numbermonth, numberyear);
            }
        });
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
                //获取年
                String yeardata = Utils.year[yearPicker.getValue()];
                //获取月
                int month = monthPicker.getValue();
                String monthdata = Utils.month[month];
                //获取天
                int day = dayPicker.getValue();
                String daydata;
                if (monthdata.equals("02")) {
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
                    if (monthdata.equals("01") || monthdata.equals("03") || monthdata.equals("05") || monthdata.equals("07") || monthdata.equals("08") || monthdata.equals("10") || monthdata.equals("012")) {
                        daydata = Utils.day[day];
                    } else {
                        daydata = Utils.dayth[day];
                    }

                }
                click.onsuccess(yeardata + "-" + monthdata + "-" + daydata);
                dialog.dismiss();// 对话框消失
            }
        });
        dialog.show();
    }

    public interface OnClickListener {
        void onsuccess(String str);
    }
}
