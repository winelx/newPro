package com.example.baselibrary.utils.dialog;

import android.app.DatePickerDialog;
import android.content.Context;

import android.widget.DatePicker;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DialogdataPick {
    //时间选择器（日历版）
    public void gettime(Context mContext, String string, OnClickListener clickListener) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yyyy, int mm, int dd) {
                clickListener.onsuccess(yyyy + "-" + (mm + 1) + "-" + dd);
            }
        }, year, monthOfYear, dayOfMonth);
        DatePicker datePicker = datePickerDialog.getDatePicker();
        //当天
        Date taday = Calendar.getInstance().getTime();
        long yesterday = taday.getTime() - 24 * 60 * 60 * 1000;
        long twoday = yesterday - 24 * 60 * 60 * 1000;
        try {
            //最小日期
            datePicker.setMinDate(twoday);
        } catch (Exception e) {
        }
        datePickerDialog.show();
    }

    //年
    public String[] getyear(int year, int length) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            list.add((year + i) + "");

        }
        String[] array = (String[]) list.toArray();
        return array;
    }

    //月
    public String[] month(int month, int length) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            list.add((month + i) + "");
        }
        String[] array = (String[]) list.toArray();
        return array;
    }

    //日

    public String[] day(int day, int length) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            list.add((day + i) + "");
        }
        String[] array = (String[]) list.toArray();
        return array;
    }


    public interface OnClickListener {
        /**
         * 选择时间后返回
         *
         * @param str 返回的时间
         */
        void onsuccess(String str);
    }
}
