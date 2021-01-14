package com.example.administrator.fengji.pzgc.activity.audit.fragment;

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

import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.utils.ToastUtils;
import com.example.administrator.fengji.pzgc.adapter.DailyrecordAdapter;
import com.example.administrator.fengji.pzgc.bean.DailyrecordBean;
import com.example.administrator.fengji.pzgc.activity.audit.ReportActivity;
import com.example.administrator.fengji.pzgc.callback.TaskCallback;
import com.example.administrator.fengji.pzgc.callback.TaskCallbackUtils;
import com.example.administrator.fengji.pzgc.utils.Dates;
import com.example.baselibrary.utils.Requests;
import com.example.administrator.fengji.pzgc.utils.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.fengji.pzgc.utils.Utils.getquarter;

/**
 * Created by Administrator on 2018/7/3 0003.
 * 季度报表
 */

public class QuarterrecordFragment extends Fragment implements View.OnClickListener, TaskCallback {
    private View rootView;
    private DailyrecordAdapter mAdapter;
    private ListView daily_list;
    private Context mContext;
    private TextView datatime, title;
    private ArrayList<DailyrecordBean> list = new ArrayList();
    private PopupWindow mPopupWindow;
    private NumberPicker yearPicker, monthPicker;
    private ReportActivity activity;
    private String data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_quarter, null);
            daily_list = rootView.findViewById(R.id.daily_list);
            datatime = rootView.findViewById(R.id.datatime);
            title = rootView.findViewById(R.id.audit_fr_title);
            rootView.findViewById(R.id.audit_title).setOnClickListener(this);
            rootView.findViewById(R.id.quarterRetry).setOnClickListener(this);
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy年");
        // new Date()为获取当前系统时间，也可使用当前时间戳
        String date = df.format(new Date());
        int quarter = Utils.getquarter();
        datatime.setText(date + Utils.quarter[quarter - 1]);
        TaskCallbackUtils.setCallBack(this);
        title.setText("选择季度");
        mContext = getActivity();
        activity = (ReportActivity) mContext;
        mAdapter = new DailyrecordAdapter(mContext, list);
        daily_list.setAdapter(mAdapter);
        daily_list.setEmptyView(rootView.findViewById(R.id.nullposion));
        data = Dates.getYear() + "-" + Utils.getquarter();
        okgo(data, false);
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
                        String yeardata = Utils.year[yearPicker.getValue()];
                        //获取季度
                        int month = monthPicker.getValue();
                        String monthdata = Utils.quarter[month];
                        datatime.setText(yeardata + monthdata);
                        month = month + 1;
                        data = yeardata + "-" + month;
                        okgo(data, true);
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
        Utils.setPicker(yearPicker, Utils.year, Utils.titleyear());
        monthPicker = contentView.findViewById(R.id.month);
        Utils.setPicker(monthPicker, Utils.quarter, getquarter() - 1);
        return contentView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.audit_title:
                MeunPop();
                break;
            case R.id.quarterRetry:
                okgo(data, true);
                break;
            default:
                break;
        }
    }

    /**
     * 接口回调
     */
    @Override
    public void taskCallback() {
        okgo(data, false);
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

    public void okgo(String date, final boolean lean) {
        OkGo.<DailyrecordBean>get(Requests.REPORT_IMG_DATE_APP)
                .params("type", "3")
                .params("orgId", activity.getOrgId())
                .params("startDate", date)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            list.clear();
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray data = jsonObject.getJSONArray("data");
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject json = data.getJSONObject(i);
                                    //岗位
                                    String position;
                                    try {
                                        position = json.getString("position");
                                    } catch (JSONException e) {
                                        position = "";
                                    }
                                    //责任人
                                    String personName;
                                    try {
                                        personName = json.getString("personName");
                                    } catch (JSONException e) {
                                        personName = "";
                                    }
                                    //百分比
                                    String percentage;
                                    try {
                                        percentage = json.getString("percentage");
                                    } catch (JSONException e) {
                                        percentage = "";
                                    }
                                    //未审核
                                    String noAuditCount;
                                    try {
                                        noAuditCount = json.getString("noAuditCount");
                                    } catch (JSONException e) {
                                        noAuditCount = "";
                                    }
                                    //已审核
                                    String auditCount;
                                    try {
                                        auditCount = json.getString("auditCount");
                                    } catch (JSONException e) {
                                        auditCount = "";
                                    }
//待审核
                                    String waitTask;
                                    try {
                                        waitTask = json.getString("waitTask");
                                    } catch (JSONException e) {
                                        waitTask = "";
                                    }
//                                String timeout = json.getString("timeout");//超时
                                    //所属标段
                                    String orgName;
                                    try {
                                        orgName = json.getString("orgName");
                                    } catch (JSONException e) {
                                        orgName = "";
                                    }
                                    //目标数量
                                    String aimsTask;
                                    try {
                                        aimsTask = json.getString("aimsTask");
                                    } catch (JSONException e) {
                                        aimsTask = "";
                                    }
                                    list.add(new DailyrecordBean(position, personName, percentage, noAuditCount, auditCount, waitTask, "", orgName, aimsTask));
                                }
                                if (list.size() > 0) {
                                    mAdapter.getData(list);
                                } else {
                                    list.clear();
                                    mAdapter.getData(list);
                                }
                            } else {
                                if (lean) {
                                    ToastUtils.showShortToastCenter("暂无数据");
                                }
                                list.clear();
                                mAdapter.getData(list);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShortToastCenter("请求失败");
                    }
                });
    }
}
