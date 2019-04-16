package com.example.administrator.newsdf.pzgc.Adapter;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.TodayDetailsBean;
import com.example.baselibrary.adapter.multiitem.BaseItemProvider;
import com.example.baselibrary.bean.bean;
import com.example.baselibrary.utils.DataHandleUtil;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lx
 * @data :2019/3/11 0011
 * @描述 : 今日完成任务标段详情
 * @see HomeTaskDetailsAdapter
 */
public class TodayDetailsAdapter extends BaseItemProvider<TodayDetailsBean, BaseViewHolder> {
    @Override
    public int viewType() {
        return HomeTaskDetailsAdapter.TYPE_TWO;
    }

    @Override
    public int layout() {
        return R.layout.adapter_total_org_details;
    }

    @Override
    public void convert(BaseViewHolder helper, TodayDetailsBean bean, int position) {
        Legend legend;
        PieChart mPieChart = helper.getView(R.id.item_piechart);
        Description description = new Description();
        description.setText("");
        description.setTextSize(14);
        description.setTextColor(ContextCompat.getColor(mContext, android.R.color.holo_purple));
        description.setPosition(150, 200);
        mPieChart.getDescription().setEnabled(true);
        mPieChart.setDescription(description);
        mPieChart.setCenterText("累计完成请况");
        mPieChart.setCenterTextSize(10);
        mPieChart.setDrawCenterText(true);
        mPieChart.setEntryLabelColor(ContextCompat.getColor(mContext, android.R.color.white));
        mPieChart.setEntryLabelTextSize(10);
        mPieChart.setCenterTextColor(ContextCompat.getColor(mContext, android.R.color.darker_gray));
        //是否显示中心圈，默认显示
        mPieChart.setDrawHoleEnabled(true);
        //设置中心圈的颜色
        mPieChart.setHoleColor(ContextCompat.getColor(mContext, R.color.white));
        //设置中心圈的半径大小
        mPieChart.setHoleRadius(55);
//                mPieChart.setMaxAngle(10);//设置饼图的最大角度
        //设置中心圈中外侧透明圈的透明度
        mPieChart.setTransparentCircleAlpha(50);
        // 圆盘是否可转动
        mPieChart.setRotationEnabled(true);
        //设置中心圈中外侧透明圈的颜色
        mPieChart.setTransparentCircleColor(ContextCompat.getColor(mContext, R.color.white));
        //设置中心圈透明圈的半径 这个要设置比setHoleRadius的值大不然没法显示
        mPieChart.setTransparentCircleRadius(20);
        legend = mPieChart.getLegend();
        //  legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART); //最右边显示
//        legend.setTextSize();
        legend.setXEntrySpace(-120f);
        legend.setYOffset(-10f);
// 设置可触摸
        mPieChart.setTouchEnabled(false);
        ArrayList colors = new ArrayList<>();
        colors.add(Color.parseColor("#5096F8"));
        colors.add(Color.parseColor("#FFF8B837"));
        List<PieEntry> yVals = new ArrayList<>();
        //已启动
        int startcount = Integer.parseInt(bean.getStartUpTaskCount());
        //已完成
        int finishsount = Integer.parseInt(bean.getFinishCount());
        String string = DataHandleUtil.division(finishsount, startcount);
        float flost = new Float(string);
        //未完成
        int nofinishcount = Integer.parseInt(bean.getNoFinishCount());
        String string1 = DataHandleUtil.division(nofinishcount, startcount);
        float flost1 = new Float(string1);

        yVals.add(new PieEntry(flost, "已完成"));
        yVals.add(new PieEntry(flost1, "未完成"));
        PieDataSet pieDataSet = new PieDataSet(yVals, "");
        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(10.0f);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextColor(Color.WHITE);

        mPieChart.setData(pieData);
        //设置界面数据
        if (TextUtils.isEmpty(bean.getOrgName())) {
            helper.setText(R.id.total_org, "");
        } else {
            helper.setText(R.id.total_org, bean.getOrgName());
        }
        if (TextUtils.isEmpty(bean.getToDayFinishCount())) {
            helper.setText(R.id.toadaycount, "今日总数：");
        } else {
            helper.setText(R.id.toadaycount, "今日总数：" + bean.getToDayFinishCount());
        }
        //总任务
        if (TextUtils.isEmpty(bean.getTotalTask())) {
            helper.setText(R.id.count, "总任务：");
        } else {
            helper.setText(R.id.count, "总任务：" + bean.getTotalTask());
        }
        //已启动
        if (TextUtils.isEmpty(bean.getStartUpTaskCount())) {
            helper.setText(R.id.start_task, "已启动：");
        } else {
            helper.setText(R.id.start_task, "已启动：" + bean.getStartUpTaskCount());
        }
        //已完成
        if (TextUtils.isEmpty(bean.getFinishCount())) {
            helper.setText(R.id.finishcount, "已完成：");
        } else {
            helper.setText(R.id.finishcount, "已完成：" + bean.getFinishCount());

        }
        //未完成
        if (TextUtils.isEmpty(bean.getNoFinishCount())) {
            helper.setText(R.id.nofinishcount, "未完成：");
        } else {
            helper.setText(R.id.nofinishcount, "未完成：" + bean.getNoFinishCount());

        }
    }
}
