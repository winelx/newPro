package com.example.administrator.newsdf.pzgc.Adapter;

import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.TotalDetailsBean;
import com.example.baselibrary.adapter.multiitem.BaseItemProvider;
import com.example.baselibrary.bean.bean;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;

/**
 * @author lx
 * @data :2019/3/11 0011
 * @描述 : 累计完任务标段详情
 * @see HomeTaskDetailsAdapter
 */
public class TotalDetailsAdapter extends BaseItemProvider<PieData, BaseViewHolder> {
    @Override
    public int viewType() {
        return HomeTaskDetailsAdapter.TYPE_ONE;
    }

    @Override
    public int layout() {
        return R.layout.adapter_total_org_details;
    }

    @Override
    public void convert(BaseViewHolder helper, PieData bean, int position) {
//        String str = bean.getPercentage();
//        str = str.replace("%", "");
//        BigDecimal decimal = new BigDecimal(str);
//        helper.setProgress(R.id.total_bar, decimal.intValue());
//        helper.setText(R.id.total_number, "累计完成任务数：" + bean.getFinishCount());
//        if (bean.getPersonName() != null) {
//            helper.setText(R.id.total_user, "项目经理：" + bean.getPersonName());
//        } else {
//            helper.setText(R.id.total_user, "项目经理：");
//        }
//        helper.setText(R.id.total_bartext, bean.getPercentage() + "");
        Legend legend;
        PieChart mPieChart = helper.getView(R.id.item_piechart);
        Description description = new Description();
        description.setText("");
        description.setTextSize(14);
        description.setTextColor(ContextCompat.getColor(mContext, android.R.color.holo_purple));
        description.setPosition(150, 200);
        mPieChart.getDescription().setEnabled(true);
        mPieChart.setDescription(description);
        mPieChart.setCenterText("累计完成任务");
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
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setXEntrySpace(-200f);
        legend.setYEntrySpace(-100f);
// 设置可触摸
        mPieChart.setTouchEnabled(false);
        mPieChart.setData(bean);
        helper.setText(R.id.total_org, "测试数据");

    }
}
