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
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckReportOrgDetailsActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.IssuedTaskDetailsActivity;
import com.example.administrator.newsdf.pzgc.bean.OrgDetailsFBean;
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
 * 统计报表扣分页
 */

public class CheckReportOrgDetailsF extends Fragment {
    private View view;
    private LinearLayout checkQueater;
    private RecyclerView categoryList;
    private CheckReportOrgDetailsAdapter mAdapter;
    private String year, selectType, mqnum, orgId;
    private Context mContext;
    private Map<String, Object> map;
    private CheckReportOrgDetailsActivity activity;
    private ArrayList<Object> list;
    private LinearLayout check_queater,layout_loading;
    private SmartRefreshLayout recycler_att;
    private int page=1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_checkreportdetails, container, false);
        checkQueater = view.findViewById(R.id.check_queater);
        categoryList = view.findViewById(R.id.category_list);
        check_queater = view.findViewById(R.id.check_queater);
        layout_loading = view.findViewById(R.id.layout_loading);
        recycler_att=view.findViewById(R.id.recycler_att);
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
        mAdapter = new CheckReportOrgDetailsAdapter(list,mContext);
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
                Object obj=list.get(position);
                Intent intent =new Intent(mContext, IssuedTaskDetailsActivity.class);
                OrgDetailsFBean tBean= (OrgDetailsFBean) obj;
                intent.putExtra("id",tBean.getId());
                intent.putExtra("title",tBean.getRectificationOrgName());
                intent.putExtra("isDeal",true);
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
                .params("page",page)
                .params("row",20)
                //查询类别
                .params("modeType", "ZG")
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
                                        String checkDate = json.getString("check_date");
                                        String checkOrgName = json.getString("check_org_name");
                                        String checkPersonName;
                                        try {
                                            checkPersonName   = json.getString("check_person_name");
                                        }catch (JSONException e){
                                            checkPersonName="";
                                        }
                                        String id = json.getString("id");
                                        String partDetails ;
                                        try {
                                            partDetails = json.getString("part_details");
                                        }catch (JSONException e){
                                            partDetails="";
                                        }
                                        String rectificationDate;
                                        try {
                                            rectificationDate = json.getString("rectification_date");
                                        }catch (JSONException e){
                                            rectificationDate="";
                                        }
                                        String rectificationOrgName = json.getString("rectification_org_name");
                                        String rectificationPersonName = json.getString("rectification_person_name");
                                        String rectificationReason = json.getString("rectification_reason");
                                        String standardDelName = json.getString("standard_del_name");
                                        String  standardDelScore = json.getString("standard_del_score");
                                        String wbsName = json.getString("wbs_name");
                                        String standardTypeName = json.getString("standardType_name");
                                        list.add(new OrgDetailsFBean(checkDate, checkOrgName, checkPersonName, id, partDetails, rectificationDate,
                                                rectificationOrgName, rectificationPersonName, rectificationReason, standardDelName, standardDelScore, wbsName,standardTypeName));
                                    }
                                    if (list.size() > 0) {
                                        check_queater.setVisibility(View.GONE);
                                    } else {
                                        check_queater.setVisibility(View.VISIBLE);
                                    }
                                    mAdapter.getmData(list);
                                }else {
                                    if (list.size()>0){
                                        check_queater.setVisibility(View.GONE);
                                    }else {
                                        check_queater.setVisibility(View.VISIBLE);
                                    }
                                    mAdapter.getmData(list);
                                }
                            } else {
                                ToastUtils.showLongToast(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        recycler_att.finishLoadmore();//结束加载
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        layout_loading.setVisibility(View.GONE);
                        check_queater.setVisibility(View.VISIBLE);
                    }
                });
    }
}
