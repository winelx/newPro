package com.example.administrator.newsdf.pzgc.activity.check.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.CheckReportOrgDetailsAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckListDetailsActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckReportOrgDetailsActivity;
import com.example.administrator.newsdf.pzgc.bean.OrgDetailsTBean;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/8/28 0028.
 * 统计报表得分页
 */

public class CheckReportOrgDetailsT extends Fragment {
    private View view;
    private RecyclerView categoryList;
    private CheckReportOrgDetailsAdapter mAdapter;
    private Context mContext;
    private Map<String, Object> map;
    private CheckReportOrgDetailsActivity activity;
    private ArrayList<Object> list;
    private LinearLayout checkQueater, layout_loading;
    private SmartRefreshLayout recycler_att;

    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_checkreportdetails, container, false);
        categoryList = view.findViewById(R.id.category_list);
        checkQueater = view.findViewById(R.id.check_queater);
        layout_loading = view.findViewById(R.id.layout_loading);
        recycler_att = view.findViewById(R.id.recycler_att);
        recycler_att.setEnableRefresh(false);//是否启用下拉刷新功能
        recycler_att.setEnableOverScrollBounce(true);//是否启用越界回弹
        recycler_att.setDisableContentWhenLoading(true);//加载时禁止操作界面
        recycler_att.setEnableLoadmoreWhenContentNotFull(false);//不满一页启动上拉加载
        mContext = CheckReportOrgDetailsActivity.getInstance();
        activity = (CheckReportOrgDetailsActivity) mContext;
        list = new ArrayList<>();
        map = activity.getOrgId();
        categoryList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        categoryList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mAdapter = new CheckReportOrgDetailsAdapter(list, mContext);
        categoryList.setAdapter(mAdapter);
        getdata();
        //上拉加载
        recycler_att.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getdata();
                recycler_att.finishLoadmore(3000);
            }
        });

        mAdapter.setOnItemClickListener(new CheckReportOrgDetailsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Object obj = list.get(position);
                Intent intent = new Intent(mContext, CheckListDetailsActivity.class);
                OrgDetailsTBean tBean = (OrgDetailsTBean) obj;
                int iworkd = tBean.getIwork();
                if (iworkd == 1) {
                    //外业标识
                    intent.putExtra("type", "1");
                } else {
                    //内业标识
                    intent.putExtra("type", "0");
                }
                intent.putExtra("id", tBean.getId());
                startActivity(intent);
            }
        });
        return view;
    }

    public void getdata() {
        String mqnum;
        try {
            mqnum = map.get("mqnum") + "";
        } catch (Exception e) {
            mqnum = "";
        }
        OkGo.post(Requests.getOrgScoreDetail)
                .params("orgId", map.get("Id") + "")
                //统计年份
                .params("year", map.get("year") + "")
                //统计类型
                .params("selectType", map.get("type") + "")
                //统计时间数值 季/月
                .params("mqnum", mqnum)
                .params("row", 20)
                .params("page", 1)
                //查询类别
                .params("modeType", "JC")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        layout_loading.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.getInt("ret") == 0) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json = jsonArray.getJSONObject(i);
                                        String check_date = json.getString("check_date");
                                        String check_org_name = json.getString("check_org_name");
                                        String check_plan_name = json.getString("check_plan_name");
                                        String check_user_name = json.getString("check_user_name");
                                        String id = json.getString("id");
                                        String part_detail = "";
                                        try {
                                            part_detail = json.getString("part_details");
                                        } catch (JSONException e) {
                                            part_detail = "";
                                        }
                                        String score = json.getString("score");
                                        String wbs_name;
                                        try {
                                            wbs_name = json.getString("wbs_name");
                                        } catch (JSONException e) {
                                            wbs_name = "";
                                        }
                                        int iwork;
                                        try {
                                            iwork = json.getInt("iwork");
                                        } catch (JSONException e) {
                                            iwork = 1;
                                        }
                                        String wbs_type_name = json.getString("wbs_type_name");

                                        list.add(new OrgDetailsTBean(check_date, check_org_name, check_plan_name, check_user_name, id, part_detail, score, wbs_name, wbs_type_name, iwork));
                                    }
                                    if (list.size() > 0) {
                                        mAdapter.getmData(list);
                                        checkQueater.setVisibility(View.GONE);
                                    } else {
                                        checkQueater.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    if (list.size() > 0) {
                                        checkQueater.setVisibility(View.GONE);
                                    } else {
                                        checkQueater.setVisibility(View.VISIBLE);
                                    }
                                    mAdapter.getmData(list);
                                }

                            } else {
                                ToastUtils.showLongToast(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        layout_loading.setVisibility(View.GONE);
                        checkQueater.setVisibility(View.VISIBLE);
                    }
                });
    }
}
