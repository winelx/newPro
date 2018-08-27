package com.example.administrator.newsdf.pzgc.activity.audit.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.DailyrecordAdapter;
import com.example.administrator.newsdf.pzgc.Adapter.DailyrecordBean;
import com.example.administrator.newsdf.pzgc.activity.audit.ReportActivity;
import com.example.administrator.newsdf.pzgc.callback.HideCallback;
import com.example.administrator.newsdf.pzgc.callback.HideCallbackUtils;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/7/3 0003.
 * 月度报表
 */

public class MonthrecordFragment extends Fragment implements View.OnClickListener, HideCallback {
    private View rootView;
    private Context mContext;
    private TextView datatime, title;
    private DailyrecordAdapter mAdapter;
    private ListView daily_list;
    private ArrayList<DailyrecordBean> list = new ArrayList();
    private PopupWindow mPopupWindow;
    private NumberPicker yearPicker, monthPicker;
    private Date myDate = new Date();
    private int dateMonth;
    private ReportActivity activity;
    private String data;
    private LinearLayout checkQueater;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_month, null);
            daily_list = rootView.findViewById(R.id.daily_list);
            datatime = rootView.findViewById(R.id.datatime);
            title = rootView.findViewById(R.id.audit_fr_title);
            checkQueater = rootView.findViewById(R.id.check_queater);
            rootView.findViewById(R.id.audit_title).setOnClickListener(this);
            rootView.findViewById(R.id.monthRetry).setOnClickListener(this);
        }
        title.setText("选择月份");
        HideCallbackUtils.setCallBack(this);
        datatime.setText(Utils.titleMonth());
        mContext = getActivity();
        activity = (ReportActivity) mContext;
        mAdapter = new DailyrecordAdapter(mContext, list);
        daily_list.setAdapter(mAdapter);
        daily_list.setEmptyView(rootView.findViewById(R.id.nullposion));
        dateMonth = myDate.getMonth();
        data = Dates.getMonth();
       okgo(data, false);
        return rootView;
    }

    /**
     * 时间选择器弹出框
     */
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

    /**
     * 设置pop的点击事件
     */
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
                        //获取月
                        int month = monthPicker.getValue();
                        String monthdata = Utils.month[month];
                        datatime.setText(yeardata + "-" + monthdata);
                        data = yeardata + "-" + monthdata;
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

        contentView.findViewById(R.id.pop_dismiss).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_determine).setOnClickListener(menuItemOnClickListener);
        yearPicker = contentView.findViewById(R.id.years);
        //初始化年的选择器
        Utils.setPicker(yearPicker, Utils.year, Utils.titleyear());
        monthPicker = contentView.findViewById(R.id.month);
        //初始化月的选择器
        Utils.setPicker(monthPicker, Utils.month, dateMonth);
        return contentView;
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.audit_title:
                MeunPop();
                break;
            case R.id.monthRetry:
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
    public void deleteTop() {
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

    public void okgo(String data, final boolean lean) {
        OkGo.<String>get(Requests.REPORT_IMG_DATE_APP)
                .params("type", "2")
                .params("orgId", activity.getOrgId())
                .params("startDate", data)
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
