package com.example.administrator.newsdf.fragment.homepage;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
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

import com.example.administrator.newsdf.Adapter.CommentsAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.bean.Home_item;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
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
 * @date 2018/5/16 0016
 * 评论
 */

public class CommentsFragment extends Fragment {
    private CommentsAdapter mAdapter;
    private Context mContext;
    private List<Home_item> mData;
    /**
     * 下拉控件
     */
    private SmartRefreshLayout refreshLayout;
    /**
     * 显示数据控件
     */
    private RecyclerView listView;
    private RelativeLayout home_frag_img;
    private ImageView home_img_nonews;
    private TextView home_img_text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mContext = getActivity();
        mData = new ArrayList<>();
        mAdapter = new CommentsAdapter(mContext);
        home_frag_img = view.findViewById(R.id.home_frag_img);
        home_img_text = view.findViewById(R.id.home_img_text);
        home_img_nonews = view.findViewById(R.id.home_img_nonews);
        refreshLayout = view.findViewById(R.id.SmartRefreshLayout);
        listView = view.findViewById(R.id.home_list);
        listView.setLayoutManager(new LinearLayoutManager(mContext));
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.setAdapter(mAdapter);
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
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

                //传入false表示加载失败
                Okgo();
                refreshlayout.finishLoadmore(800);
            }
        });
        //加载数据
        home_frag_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dates.getDialogs(getActivity(), "请求数据中");
                Okgo();
            }
        });
        Okgo();
        return view;

    }

    private void Okgo() {
        OkGo.get(Requests.GET_MY_LIST)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Dates.disDialog();
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
                                        createTime = json.getString("changeReviewTime");
                                        createTime = Dates.stampToDates(createTime);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        createTime = "";
                                    }
                                    String id;
                                    try {
                                        id = json.getString("id");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        id = "";
                                    }
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
                                    String unfinish;
                                    try {
                                        unfinish = json.getString("wbsName");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        unfinish = "";
                                    }
                                    //最后一个false是判断是否置顶的，这个界面复用的实体类，但又没有置顶，所以用false
                                    mData.add(new Home_item(content, createTime, id, orgId, orgName, unfinish, false));
                                }
                                mAdapter.getData(mData);
                                home_frag_img.setVisibility(View.GONE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            home_frag_img.setVisibility(View.VISIBLE);
                            home_img_text.setText("暂无数据，点击刷新");
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
}
