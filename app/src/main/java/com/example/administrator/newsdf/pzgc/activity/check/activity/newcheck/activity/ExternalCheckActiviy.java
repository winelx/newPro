package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.activity;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.adapter.ExternalListAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.CheckOrgBean;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.utils.ExternalApi;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.utils.ExternalModel;
import com.example.administrator.newsdf.pzgc.adapter.CheckListAdapter;
import com.example.administrator.newsdf.pzgc.bean.Home_item;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.utils.network.NetWork;
import com.example.baselibrary.utils.network.NetworkAdapter;
import com.example.baselibrary.utils.rx.LiveDataBus;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 说明：外业检查
 * 创建时间： 2020/6/23 0023 14:20
 *
 * @author winelx
 */
public class ExternalCheckActiviy extends BaseActivity implements View.OnClickListener {
    private LinearLayout comBack;
    private ExpandableListView expandable;
    private SmartRefreshLayout refreshLayout;
    private ExternalListAdapter mAdapter;
    private ArrayList<String> list;
    private Map<String, List<CheckOrgBean>> map;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_check_external);
        mContext = this;
        list = new ArrayList<>();
        map = new HashMap<>();
        comBack = findViewById(R.id.com_back);
        comBack.setOnClickListener(this);
        expandable = findViewById(R.id.expandable);
        refreshLayout = findViewById(R.id.refreshlayout);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.finishRefresh(true);
        mAdapter = new ExternalListAdapter(new ArrayList<>(), map, mContext);
        expandable.setAdapter(mAdapter);
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //传入false表示刷新失败
                request();
                refreshlayout.finishRefresh(true);

            }
        });
        expandable.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String ids = map.get(list.get(groupPosition)).get(childPosition).getId();
                String orgname = map.get(list.get(groupPosition)).get(childPosition).getName();
                Intent intent = new Intent(mContext, ExternalCheckListActiviy.class);
                intent.putExtra("orgid", ids);
                intent.putExtra("orgName", orgname);
                startActivity(intent);
                return true;
            }
        });
        request();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.com_back) {
            finish();
        }
    }

    /**
     * 说明：获取组织数据接口
     * 创建时间： 2020/7/1 0001 13:49
     *
     * @author winelx
     */
    public void request() {
        NetWork.getHttp(ExternalApi.GETORGINFOBYSAFETYCHECK, null, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        List<CheckOrgBean> checklist = com.alibaba.fastjson.JSONObject.parseArray(data.toString(), CheckOrgBean.class);
                        for (int i = 0; i < checklist.size(); i++) {
                            String parentName = checklist.get(i).getParentName();
                            if (!list.contains(parentName)) {
                                list.add(parentName);
                            }
                        }
                        //是否有数据
                        if (checklist.size() != 0) {
                            for (String str : list) {
                                List<CheckOrgBean> datas = new ArrayList<CheckOrgBean>();
                                for (CheckOrgBean item : checklist) {
                                    String name = item.getParentName();
                                    if (str.equals(name)) {
                                        datas.add(item);
                                        map.put(str, datas);
                                    }
                                }
                            }
                        }
                        mAdapter.setNewData(list, map);
                    } else {
                        ToastUtils.showShortToast(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                ToastUtils.showShortToast("请求失败");
            }
        });

    }


}
