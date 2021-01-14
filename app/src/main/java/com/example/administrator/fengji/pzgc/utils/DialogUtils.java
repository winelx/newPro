package com.example.administrator.fengji.pzgc.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.activity.check.CheckUtils;
import com.example.baselibrary.view.NumberProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lx
 * @Created by: 2018/12/4 0004.
 * @description:时间选择器
 * @Activity：
 */

public class DialogUtils {
    public static NumberProgressBar progressBar;
    CheckUtils checkUtils = new CheckUtils();
    public static String[] month = new String[]{
            "01", "02", "03",
            "04", "05", "06",
            "07", "08", "09",
            "10", "11", "12"};
    public static String[] year = new String[]{"2016", "2017", "2018", "2019", "2020", "2021", "2022",
            "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};

    public static String[] daytwos = new String[]{
            "01", "02", "03", "04", "05",
            "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25",
            "26", "27", "28", "29"};
    public static String[] daytwo = new String[]{
            "01", "02", "03", "04", "05",
            "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25",
            "26", "27", "28"};

    public static String[] days = new String[]{
            "01", "02", "03", "04", "05",
            "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25",
            "26", "27", "28", "29", "30", "31"};
    public static String[] dayth = new String[]{
            "01", "02", "03", "04", "05",
            "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25",
            "26", "27", "28", "29", "30"};


    public void selectiontime(Context mContext, final OnClickListener click) {
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
        setPicker(yearPicker, year, titleyear());
        //初始化数据---月
        Date myDate = new Date();
        int dateMonth = myDate.getMonth();
        int dayDate = myDate.getDate() - 1;
        setPicker(monthPicker, month, dateMonth);
        //初始化数据---日
        String yeardata = year[yearPicker.getValue()];
        //如果当前月份是2月
        if ((dateMonth + 1) == 2) {
            if (getyear().contains(yeardata)) {
                setPicker(dayPicker, daytwos, dayDate);
                //闰年
            } else {
                //平年
                setPicker(dayPicker, daytwo, dayDate);
            }
        } else {
            if (dateMonth == 0 || dateMonth == 2 || dateMonth == 4 || dateMonth == 6 || dateMonth == 7 || dateMonth == 9 || dateMonth == 11) {
                setPicker(dayPicker, days, dayDate);
            } else {
                setPicker(dayPicker, dayth, dayDate);
            }
        }
        //年份选择器。如果当前的月份是二月，
        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                checkUtils.setyear(monthPicker, dayPicker, i1, year);
            }
        });
        //月份选择器。如果当前的月份是二月，
        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal,
                                      int newVal) {
                checkUtils.setMonth(yearPicker, monthPicker, dayPicker, newVal, month, year);
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
                String yeardata = year[yearPicker.getValue()];
                //获取月
                int months = monthPicker.getValue();
                String monthdata = month[months];
                //获取天
                int day = dayPicker.getValue();
                String daydata;
                if ("02".equals(monthdata)) {
                    //是二月份
                    if (getyear().contains(yeardata)) {
                        daydata = daytwos[day];
                        //闰年
                    } else {
                        //平年
                        daydata = daytwo[day];
                    }
                } else {
                    //不是二月份
                    if ("01".equals(monthdata) || "03".equals(monthdata) || "05".equals(monthdata) || "07".equals(monthdata)
                            || "08".equals(monthdata) || "10".equals(monthdata) || "012".equals(monthdata)) {
                        daydata = days[day];
                    } else {
                        daydata = dayth[day];
                    }

                }
                click.onsuccess(yeardata + "-" + monthdata + "-" + daydata);
                dialog.dismiss();// 对话框消失
            }
        });
        dialog.show();
    }

    public interface OnClickListener {
        /**
         * 选择时间后返回
         *
         * @param str 返回的时间
         */
        void onsuccess(String str);
    }


    //选择器抽取方法
    public static void setPicker(NumberPicker View, String[] str, int time) {
        //设置需要显示的数组
        View.setDisplayedValues(str);
        View.setWrapSelectorWheel(true);
        //这两行不能缺少,不然只能显示第一个，关联到format方法
        View.setMinValue(0);
        View.setMaxValue(str.length - 1);
        //中间不可点击
        View.setValue(time);
        View.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
    }

    public static int titleyear() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        // new Date()为获取当前系统时间，也可使用当前时间戳
        String date = df.format(new Date());
        int datayear = 0;
        for (int i = 0; i < year.length; i++) {
            String str = year[i];
            if (str.equals(date)) {
                datayear = i;
            }
        }
        return datayear;
    }

    public static List<String> getyear() {
        ArrayList<String> year = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int teger = 2012 + i * 4;
            year.add(teger + "");
        }
        return year;
    }

    public static void Tipsdialog(Context mContext, String title, String[] strings, final OnClickListener selectiontime) {
        //删除
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext)
                .setMessage(title)
                //点击对话框以外的区域是否让对话框消失
                .setCancelable(false)
                //设置按钮
                .setPositiveButton(strings[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        selectiontime.onsuccess("str");

                    }
                })
                //设置按钮
                .setNegativeButton(strings[1], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        android.app.AlertDialog dialog = builder.create();
        //显示对话框
        dialog.show();
    }

    public String[] setday(String day, String[] strings) {
        int index = 0;
        for (int i = 0; i < strings.length; i++) {
            String item = strings[i];
            if (day.equals(item)) {
                index = i;
            }
        }
        return strings;
    }


    public void selectiontime(Context mContext, String tr, final OnClickListener click) {
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
        setPicker(yearPicker, year, titleyear());
        //初始化数据---月
        Date myDate = new Date();
        int dateMonth = myDate.getMonth();
        int dayDate = myDate.getDate() - 1;
        setPicker(monthPicker, month, dateMonth);
        //初始化数据---日
        String yeardata = year[yearPicker.getValue()];
        //如果当前月份是2月
        if ((dateMonth + 1) == 2) {
            if (getyear().contains(yeardata)) {
                setPicker(dayPicker, daytwos, dayDate);
                //闰年
            } else {
                //平年
                setPicker(dayPicker, daytwo, dayDate);
            }
        } else {
            if (dateMonth == 0 || dateMonth == 2 || dateMonth == 4 || dateMonth == 6 || dateMonth == 7 || dateMonth == 9 || dateMonth == 11) {
                setPicker(dayPicker, days, dayDate);
            } else {
                setPicker(dayPicker, dayth, dayDate);
            }
        }
        //年份选择器。如果当前的月份是二月，
        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                checkUtils.setyear(monthPicker, dayPicker, i1, year);
            }
        });
        //月份选择器。如果当前的月份是二月，
        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal,
                                      int newVal) {
                checkUtils.setMonth(yearPicker, monthPicker, dayPicker, newVal, month, year);
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
                String yeardata = year[yearPicker.getValue()];
                //获取月
                int months = monthPicker.getValue();
                String monthdata = month[months];
                //获取天
                int day = dayPicker.getValue();
                String daydata;
                if ("02".equals(monthdata)) {
                    //是二月份
                    if (getyear().contains(yeardata)) {
                        daydata = daytwos[day];
                        //闰年
                    } else {
                        //平年
                        daydata = daytwo[day];
                    }
                } else {
                    //不是二月份
                    if ("01".equals(monthdata) || "03".equals(monthdata) || "05".equals(monthdata) || "07".equals(monthdata)
                            || "08".equals(monthdata) || "10".equals(monthdata) || "012".equals(monthdata)) {
                        daydata = days[day];
                    } else {
                        daydata = dayth[day];
                    }

                }
                click.onsuccess(yeardata + "-" + monthdata + "-" + daydata);
                dialog.dismiss();// 对话框消失
            }
        });
        dialog.show();
    }

    public static AlertDialog dialog;

    //下载进度条
    public static void download(Context mContext) {
        // 获取布局
        View view = View.inflate(mContext, R.layout.dialog_download, null);
        // 创建对话框
        progressBar = view.findViewById(R.id.download_bar);
        dialog = new AlertDialog.Builder(mContext).create();
        //添加布局
        dialog.setView(view);
        dialog.show();
    }

    public static void dismiss() {
        dialog.dismiss();
    }


}
