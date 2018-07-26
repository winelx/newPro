package com.example.administrator.newsdf.pzgc.fragment.homepage;

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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.pzgc.Adapter.CollectionFrAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.Home_item;
import com.example.administrator.newsdf.pzgc.callback.HideCallback;
import com.example.administrator.newsdf.pzgc.callback.HideCallbackUtils;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.Requests;
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

public class CollectionFragment extends Fragment implements HideCallback {
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
    private int page = 1;
    private int size = 20;
    private RelativeLayout home_frag_img;
    private ImageView home_img_nonews;
    private TextView home_img_text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        mContext = getActivity();
        mData = new ArrayList<>();
        Okgo(false);
        refreshLayout = view.findViewById(R.id.SmartRefreshLayout);
        refreshLayout.setEnableLoadmore(false);//禁止上拉
        refreshLayout.setEnableOverScrollBounce(true);//仿ios越界
        refreshLayout.setEnableOverScrollDrag(true);//是否启用越界拖动（仿苹果效果）1.0.4
        listView = view.findViewById(R.id.home_list);
        home_frag_img = view.findViewById(R.id.home_frag_img);
        home_img_text = view.findViewById(R.id.home_img_text);
        home_img_nonews = view.findViewById(R.id.home_img_nonews);
        HideCallbackUtils.setCallBack(this);
        //设置布局管理器
        listView.setLayoutManager(new LinearLayoutManager(mContext));
        //设置适配器
        mAdapter = new CollectionFrAdapter(mContext,mData);
        listView.setAdapter(mAdapter);
        //设置控制Item增删的动画
        listView.setItemAnimator(new DefaultItemAnimator());
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setEnableAutoLoadmore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                lsit();

                //传入false表示刷新失败
                if (mAdapter.menuIsOpen()) {
                    mAdapter.closeMenu();
                }
                mData.clear();
                Okgo(true);
                refreshlayout.finishRefresh(1200);
            }
        });
        //加载数据
        home_frag_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lsit();
                Dates.getDialogs(getActivity(), "请求数据中");
                Okgo(true);
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
        Okgo(true);
    }

    public void Okgo(final boolean newdata) {
        OkGo.get(Requests.GET_LIS)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Dates.disDialog();
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
                                        createTime = json.getString("createTime");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        createTime = "";
                                    }
                                    String id="";
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
                                    String unfinish="";
                                    try {
                                        unfinish = json.getString("task_counts");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        unfinish = "";
                                    }
                                    String parentid = json.getString("parent_id");
                                    String parentname = json.getString("parent_name");
                                    //最后一个false是判断是否置顶的，这个界面复用的实体类，但又没有置顶，所以用false
                                    mData.add(new Home_item(content, createTime, id, orgId, orgName, unfinish, parentid,parentname,"" ,false));
                                }
                                if (mData.size()>0){
                                    mAdapter.getData(mData);
                                    home_frag_img.setVisibility(View.GONE);
                                }else {
                                    home_frag_img.setVisibility(View.VISIBLE);
                                    home_img_text.setText("暂无数据");
                                    if (newdata){
                                        ToastUtils.showLongToast("暂无数据");
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            home_frag_img.setVisibility(View.VISIBLE);
                            home_img_text.setText("暂无数据");
                            if (newdata){
                                ToastUtils.showLongToast("暂无数据");
                            }
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Dates.disDialog();
                        ToastUtils.showShortToast("网络连接失败");
                        home_frag_img.setVisibility(View.VISIBLE);
                        home_img_nonews.setBackgroundResource(R.mipmap.nonetwork);
                        home_img_text.setText("请求确认网络是否通畅，点击再次请求");
                    }

                });
    }
    public void lsit(){


    }
}