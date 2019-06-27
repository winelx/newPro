package com.example.administrator.newsdf.pzgc.activity.audit.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.DialogUtils;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.adapter.DailyrecordAdapter;
import com.example.administrator.newsdf.pzgc.bean.DailyrecordBean;
import com.example.administrator.newsdf.pzgc.activity.audit.ReportActivity;
import com.example.administrator.newsdf.pzgc.callback.CallBack;
import com.example.administrator.newsdf.pzgc.callback.CallBackUtils;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.baselibrary.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;


/**
 * description:每日审核
 *
 * @author lx
 * date: 2018/7/3 0003 上午 10:03
 * update: 2018/7/3 0003
 * version:
 */

public class DailyrecordFragment extends Fragment implements View.OnClickListener, CallBack {
    private View rootView;
    private ListView dailyList;
    private TextView datatime;
    private ArrayList<DailyrecordBean> list;
    private DailyrecordAdapter mAdapter;
    private Context mContext;
    private ReportActivity activity;
    private String data;
    private DialogUtils dialogUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_daily, null);
            dailyList = rootView.findViewById(R.id.daily_list);
            datatime = rootView.findViewById(R.id.datatime);
            rootView.findViewById(R.id.audit_title).setOnClickListener(this);
            rootView.findViewById(R.id.dayRetry).setOnClickListener(this);

        }
        list = new ArrayList<>();
        mContext = getActivity();
        CallBackUtils.setCallBack(this);
        dialogUtils = new DialogUtils();
        activity = (ReportActivity) mContext;
        datatime.setText(Utils.titleDay());
        mAdapter = new DailyrecordAdapter(mContext, list);
        dailyList.setAdapter(mAdapter);
        dailyList.setEmptyView(rootView.findViewById(R.id.nullposion));
        data = Dates.getDay();
        okgo(data, false);
        return rootView;

    }


    /**
     * 选择时间弹出框
     */
    private void meunpop() {
        dialogUtils.selectiontime(mContext, new DialogUtils.OnClickListener() {
            @Override
            public void onsuccess(String str) {
                datatime.setText(str);
            }
        });
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
                meunpop();
                break;
            case R.id.dayRetry:
                okgo(data, true);
                break;
            default:
                break;
        }
    }

    /**
     * 回调接口
     */
    @Override
    public void deleteTop() {
        okgo(data, false);
    }


    /**
     * 网络请求
     *
     * @param data
     */
    public void okgo(String data, final boolean lean) {
        OkGo.<String>get(Requests.REPORT_IMG_DATE_APP)
                .params("type", "1")
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
