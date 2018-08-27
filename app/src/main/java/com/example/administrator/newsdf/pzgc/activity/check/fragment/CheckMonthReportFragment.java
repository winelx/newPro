package com.example.administrator.newsdf.pzgc.activity.check.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.CheckQuarteradapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckReportActivity;
import com.example.administrator.newsdf.pzgc.bean.CheckQuarterBean;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.newsdf.pzgc.utils.Utils.getquarter;
import static com.example.administrator.newsdf.pzgc.utils.Utils.quarter;
import static com.example.administrator.newsdf.pzgc.utils.Utils.year;


/**
 * description: 检查季度报表
 *
 * @author lx
 *         date: 2018/8/14 0014 下午 2:48
 *         update: 2018/8/14 0014
 *         version:
 */
public class CheckMonthReportFragment extends Fragment {
    private View view;
    private Context mContext;
    private RecyclerView categoryList;
    private TextView data_time, title;
    private LinearLayout nullposion;
    private CheckQuarteradapter mAdapter;
    private ArrayList<CheckQuarterBean> mData;
    private PopupWindow mPopupWindow;
    private LinearLayout linearDataTime;
    private String orgId, years, mqnum, data;
    private NumberPicker yearPicker, monthPicker;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_checkquarter, container, false);
        mData = new ArrayList<>();
        mContext = getActivity();
        categoryList = view.findViewById(R.id.category_list);
        linearDataTime=view.findViewById(R.id.linear_data_time);
        data_time = view.findViewById(R.id.linear_data);
        title = view.findViewById(R.id.title);
        nullposion = view.findViewById(R.id.nullposion);
//        mAdapter = new CheckQuarteradapter(mContext);
        title.setText("统计季度");
        linearDataTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeunPop();
            }
        });
        SimpleDateFormat df = new SimpleDateFormat("yyyy年");
        String date = df.format(new Date());
        int quarter = Utils.getquarter();
        data_time.setText(date + Utils.quarter[quarter - 1]);
        return view;
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
        mPopupWindow.showAsDropDown(title);
        Utils.backgroundAlpha(0.5f, CheckReportActivity.getInstance());
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
                        String yeardata = Utils.year[yearPicker.getValue()];
                        //获取季度
                        int month = monthPicker.getValue();
                        String monthdata = quarter[month];
                        data_time.setText(yeardata + monthdata);
                        month = month + 1;
//                        data = yeardata + "-" + month;
//                        okgo(data, true);
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
        TextView titledata = contentView.findViewById(R.id.titledata);
        titledata.setText("选择季度");
        contentView.findViewById(R.id.pop_dismiss).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_determine).setOnClickListener(menuItemOnClickListener);
        yearPicker = contentView.findViewById(R.id.years);
        Utils.setPicker(yearPicker, year, Utils.titleyear());
        monthPicker = contentView.findViewById(R.id.month);
        Utils.setPicker(monthPicker, quarter, getquarter() - 1);
        return contentView;
    }
    /**
     * popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            Utils.backgroundAlpha(1f, CheckReportActivity.getInstance());
        }
    }
    public void getdate() {
        OkGo.post(Requests.getOrgRanking)
                //组织Id
                .params("orgId", orgId)
                //查询年费
                .params("year", years)
                //查询季度
                .params("mqnum", mqnum)
                //查询类型：季度
                .params("selectType", "Q")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                    }
                });
    }
}
