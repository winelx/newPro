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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2018/7/3 0003.
 */

public class QuarterrecordFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private DailyrecordAdapter mAdapter;
    private ListView daily_list;
    private Context mContext;
    private TextView datatime, title;
    private ArrayList<String> list = new ArrayList();
    private PopupWindow mPopupWindow;
    private NumberPicker yearPicker, monthPicker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_quarter, null);
            daily_list = rootView.findViewById(R.id.daily_list);
            datatime = rootView.findViewById(R.id.datatime);
            title = rootView.findViewById(R.id.audit_fr_title);
            rootView.findViewById(R.id.audit_title).setOnClickListener(this);
        }
        for (int i = 0; i < 100; i++) {
            list.add("ass" + i);
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy年");
        // new Date()为获取当前系统时间，也可使用当前时间戳
        String date = df.format(new Date());
        datatime.setText(date + Utils.quarter[Utils.getquarter()]);
        title.setText("选择季度");
        mContext = getActivity();
        mAdapter = new DailyrecordAdapter(mContext, list);
        daily_list.setAdapter(mAdapter);
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
        int layoutId = R.layout.popwind_month;
        final View contentView = LayoutInflater.from(mContext).inflate(layoutId, null);
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.pop_determine:
                        //获取年
                        String yeardata = Utils.years[yearPicker.getValue()];
                        //获取季度
                        int month = monthPicker.getValue();
                        String monthdata = Utils.quarter[month];
                        datatime.setText(yeardata + monthdata);
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
        TextView pop_vaulee = contentView.findViewById(R.id.pop_vaulee);
        pop_vaulee.setText("季度");
        contentView.findViewById(R.id.pop_dismiss).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_determine).setOnClickListener(menuItemOnClickListener);
        yearPicker = contentView.findViewById(R.id.years);
        Utils.setPicker(yearPicker, Utils.years, Utils.titleyear());
        monthPicker = contentView.findViewById(R.id.month);
        Utils.setPicker(monthPicker, Utils.quarter, Utils.getquarter());
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
}
