package com.example.administrator.fengji.pzgc.fragment.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.adapter.CollectionFrAdapter;
import com.example.administrator.fengji.pzgc.bean.Home_item;
import com.example.administrator.fengji.pzgc.callback.HideCallback;
import com.example.administrator.fengji.pzgc.callback.HideCallbackUtils;
import com.example.administrator.fengji.pzgc.callback.OgranCallback;
import com.example.administrator.fengji.pzgc.callback.OgranCallbackUtils3;
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
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author lx
 *         Created by Administrator on 2018/5/16 0016.
 *         收藏
 */

public class CollectionFragment extends Fragment implements HideCallback,OgranCallback {
    /**
     * 下拉控件
     */
    private SmartRefreshLayout refreshLayout;
    /**
     * 显示数据控件
     */
    private RecyclerView listView;
    //数据库数据
    private List<Home_item> mData;
    private CollectionFrAdapter mAdapter = null;
    private Context mContext;

    private LinearLayout nullposion;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null){
            view = inflater.inflate(R.layout.fragment_collection, container, false);
            mContext = getActivity();
            mData = new ArrayList<>();
            Okgo();
            refreshLayout = view.findViewById(R.id.SmartRefreshLayout);
            refreshLayout.setEnableLoadmore(false);//禁止上拉
            refreshLayout.setEnableOverScrollBounce(true);//仿ios越界
            refreshLayout.setEnableOverScrollDrag(true);//是否启用越界拖动（仿苹果效果）1.0.4
            listView = view.findViewById(R.id.home_list);
            nullposion = view.findViewById(R.id.nullposion);
            HideCallbackUtils.setCallBack(this);
            OgranCallbackUtils3.setCallBack(this);
            //设置布局管理器
            listView.setLayoutManager(new LinearLayoutManager(mContext));
            //设置适配器
            mAdapter = new CollectionFrAdapter(mContext, mData);
            listView.setAdapter(mAdapter);
            //设置控制Item增删的动画
            listView.setItemAnimator(new DefaultItemAnimator());
            refreshLayout.setEnableLoadmore(false);
            refreshLayout.setEnableAutoLoadmore(false);
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(final RefreshLayout refreshlayout) {
                    //传入false表示刷新失败
                    if (mAdapter.menuIsOpen()) {
                        mAdapter.closeMenu();
                    }
                    mData.clear();
                    Okgo();
                    refreshlayout.finishRefresh(1200);
                }
            });

            mAdapter.setOnItemClickListener(new CollectionFrAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(mContext, CollectionlistActivity.class);
                    intent.putExtra("name", mData.get(position).getOrgname());
                    intent.putExtra("orgId", mData.get(position).getOrgid());
                    startActivity(intent);
                }
            });
        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter.menuIsOpen()) {
            mAdapter.closeMenu();
        }
    }

    //收藏全部任务后刷新该界面
    @Override
    public void deleteTop() {
        Okgo();
    }

    public void Okgo() {
        OkGo.get(Requests.GET_LIS)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        mData.clear();
                        if (s.contains("data")) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    String content;
                                    try {
                                        content = json.getString("content");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        content = "";
                                    }
                                    String createTime;
                                    try {
                                        createTime = json.getString("updateDate");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        createTime = "";
                                    }
                                    String id = "";
                                    String orgId;
                                    try {
                                        orgId = json.getString("orgId");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        orgId = "";
                                    }
                                    String orgName;
                                    try {
                                        orgName = json.getString("orgName");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        orgName = "";
                                    }
                                    String unfinish = "";
                                    try {
                                        unfinish = json.getString("task_counts");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        unfinish = "";
                                    }
                                    String parentid = json.getString("parent_id");
                                    String parentname = json.getString("parent_name");
                                    //最后一个false是判断是否置顶的，这个界面复用的实体类，但又没有置顶，所以用false
                                    mData.add(new Home_item(content, createTime, id, orgId, orgName, unfinish, parentid, parentname, "", false));
                                }
                                if (mData.size() > 0) {
                                    mAdapter.getData(mData);
                                    listView.setVisibility(View.VISIBLE);
                                    nullposion.setVisibility(View.GONE);
                                } else {
                                    listView.setVisibility(View.GONE);
                                    nullposion.setVisibility(View.VISIBLE);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            listView.setVisibility(View.GONE);
                            nullposion.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        listView.setVisibility(View.GONE);
                        nullposion.setVisibility(View.VISIBLE);
                    }

                });
    }

    @Override
    public void taskCallback() {
        Okgo();
    }
}