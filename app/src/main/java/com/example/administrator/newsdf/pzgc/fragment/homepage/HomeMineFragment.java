package com.example.administrator.newsdf.pzgc.fragment.homepage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.FragmentHomeListAdapter;
import com.example.administrator.newsdf.pzgc.activity.home.MineListmessageActivity;
import com.example.administrator.newsdf.pzgc.bean.Home_item;
import com.example.administrator.newsdf.pzgc.callback.OgranCallback;
import com.example.administrator.newsdf.pzgc.callback.OgranCallbackUtils;
import com.example.administrator.newsdf.pzgc.callback.frehomeCallBack;
import com.example.administrator.newsdf.pzgc.callback.frehomeCallBackUtils;
import com.example.baselibrary.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
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
 * @author lx
 * Created by Administrator on 2017/11/21 0021.
 * 我的消息
 */

public class HomeMineFragment extends Fragment implements AdapterView.OnItemClickListener, OgranCallback, frehomeCallBack {
    private View rootView;
    private LinearLayout probar;
    private ExpandableListView expandable;
    private FragmentHomeListAdapter mAdapter;
    private ArrayList<Home_item> mData;
    private ArrayList<Home_item> Audit;
    private ArrayList<String> title;
    private Context mContext;
    private SmartRefreshLayout refreshLayout;
    private View.OnClickListener ivGoToChildClickListener;
    private Map<String, List<Home_item>> hasMap;
    private LinearLayout nullposion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //避免重复绘制界面
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, null);
            expandable = rootView.findViewById(R.id.expandable);
            refreshLayout = rootView.findViewById(R.id.SmartRefreshLayout);
            probar = rootView.findViewById(R.id.probar);
            nullposion = rootView.findViewById(R.id.nullposion);
            //禁止上拉
            refreshLayout.setEnableLoadmore(false);
            //仿ios越界
            refreshLayout.setEnableOverScrollBounce(true);
            //
            init();
        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        return rootView;
    }

    private void init() {
        mContext = getActivity();
        //切换组织接口回调（OrganizationaActivity）
        OgranCallbackUtils.setCallBack(this);
        //收藏刷新
        frehomeCallBackUtils.setCallBack(this);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (hasMap.size() == 0) {
                    expandable.setVisibility(View.GONE);
                    nullposion.setVisibility(View.GONE);
                    probar.setVisibility(View.VISIBLE);
                }
                Okgo();
                //传入false表示刷新失败
                refreshlayout.finishRefresh(1000);
            }
        });
        //网络请求
        ivGoToChildClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取被点击图标所在的group的索引
                Map<String, Object> map = (Map<String, Object>) v.getTag();
                int groupPosition = (int) map.get("groupPosition");
                //判断分组是否展开
                boolean isExpand = expandable.isGroupExpanded(groupPosition);
                if (isExpand) {
                    //收缩
                    expandable.collapseGroup(groupPosition);
                } else {
                    //展开
                    expandable.expandGroup(groupPosition);
                }
            }
        };
        Okgo();
    }

    //网络请求
    private void Okgo() {
        Dates.getDialogs((Activity) mContext, "请求数据中...");
        //请求数据库的数据
        OkGo.post(Requests.TaskMain)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Dates.disDialog();
                        if (s.contains("data")) {
                            mData = new ArrayList<>();
                            Audit = new ArrayList<>();
                            title = new ArrayList<>();
                            hasMap = new HashMap<>();
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONObject data = jsonObject.getJSONObject("data");
                                JSONArray task;
                                try {
                                    task = data.getJSONArray("task");
                                } catch (JSONException e) {
                                    task = new JSONArray();
                                }
                                JSONArray audit;
                                try {
                                    audit = data.getJSONArray("audit");
                                } catch (JSONException e) {
                                    audit = new JSONArray();
                                }
                                if (task.length() > 0) {
                                    for (int i = 0; i < task.length(); i++) {
                                        JSONObject json = task.getJSONObject(i);
                                        String content = json.getString("content");
                                        String createTime, id, orgId, orgName, parentid, parentname, unfinish;
                                        try {
                                            createTime = json.getString("createTime");
                                            if (createTime != null && !"".equals(createTime)) {
                                                createTime = createTime.substring(0, 10);
                                            } else {
                                                createTime = "";
                                            }
                                            id = json.getString("id");
                                            orgId = json.getString("orgId");
                                            orgName = json.getString("orgName");
                                            parentid = json.getString("parent_id");
                                            parentname = json.getString("parent_name");
                                            unfinish = json.getString("unfinish");
                                        } catch (JSONException e) {
                                            createTime = "";
                                            id = "";
                                            orgId = "";
                                            orgName = "";
                                            parentid = "";
                                            parentname = "";
                                            unfinish = "";
                                        }
                                        mData.add(new Home_item(content, createTime, id, orgId, orgName, unfinish, "", parentname, parentid, false));
                                    }
                                    title.add("待回复");
                                    hasMap.put("待回复", mData);

                                }
                                if (audit.length() > 0) {
                                    for (int i = 0; i < audit.length(); i++) {
                                        JSONObject json1 = audit.getJSONObject(i);
                                        String id = json1.getString("id");
                                        String orgName = json1.getString("name");
                                        String parentname = json1.getString("parentName");
                                        //任务未完成
                                        String unfinish;
                                        try {
                                            unfinish = json1.getString("unfinish");
                                        } catch (JSONException e) {
                                            unfinish = "0";
                                        }
                                        Audit.add(new Home_item("", "", "", id, orgName, unfinish, "", parentname, "", false));
                                    }
                                    title.add("待审核");
                                    hasMap.put("待审核", Audit);
                                }
                                if (hasMap.size() > 0) {
                                    expandable.setVisibility(View.VISIBLE);
                                    nullposion.setVisibility(View.GONE);
                                    probar.setVisibility(View.GONE);
                                    mAdapter = new FragmentHomeListAdapter(title, hasMap, mContext,
                                            ivGoToChildClickListener);
                                    expandable.setAdapter(mAdapter);
                                } else {
                                    nullposion.setVisibility(View.VISIBLE);
                                    expandable.setVisibility(View.GONE);
                                    probar.setVisibility(View.GONE);
                                }
                                //关闭刷新提示
                                refreshLayout.finishRefresh(true);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.showShortToast("没有更多数据");
                            nullposion.setVisibility(View.VISIBLE);
                            probar.setVisibility(View.GONE);
                            expandable.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Dates.disDialog();
                        probar.setVisibility(View.GONE);
                        nullposion.setVisibility(View.VISIBLE);
                        expandable.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //跳转列表界面
        Intent intent = new Intent(mContext, MineListmessageActivity.class);
        intent.putExtra("name", mData.get(position).getOrgname());
        intent.putExtra("orgId", mData.get(position).getOrgid());
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //关闭dialog和刷新
        refreshLayout.finishRefresh(true);
    }

    //切换组织后刷新
    @Override
    public void taskCallback() {
        Okgo();
    }

    //取消收藏后刷新
    @Override
    public void bright() {
        Okgo();
    }
}