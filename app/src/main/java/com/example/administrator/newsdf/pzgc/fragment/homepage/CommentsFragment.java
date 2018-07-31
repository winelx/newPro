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
import android.widget.LinearLayout;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.CommentsAdapter;
import com.example.administrator.newsdf.pzgc.bean.Home_item;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    List<Home_item> setList;
    /**
     * 下拉控件
     */
    private SmartRefreshLayout refreshLayout;
    /**
     * 显示数据控件
     */
    private RecyclerView listView;
    private LinearLayout nullposion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        mContext = getActivity();

        mAdapter = new CommentsAdapter(mContext);
        nullposion = view.findViewById(R.id.nullposion);
        refreshLayout = view.findViewById(R.id.SmartRefreshLayout);
        listView = view.findViewById(R.id.home_list);
        listView.setLayoutManager(new LinearLayoutManager(mContext));
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.setAdapter(mAdapter);

        //禁止上拉
        refreshLayout.setEnableLoadmore(false);
        //仿ios越界
        refreshLayout.setEnableOverScrollBounce(true);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableOverScrollDrag(true);
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                Okgo(true);
                refreshlayout.finishRefresh(1200);
            }
        });

        mAdapter.setOnItemClickListener(new CommentsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                setList.get(position).setUnfinish("0");
                mAdapter.getData(setList);
                Intent intent = new Intent(mContext, CommentmessageActivity.class);
                intent.putExtra("name", setList.get(position).getOrgname());
                intent.putExtra("orgId", setList.get(position).getOrgid());
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Okgo(false);
    }

    private void Okgo(final boolean newdata) {
        OkGo.get(Requests.GET_MY_LIST)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (newdata){
                            Dates.disDialog();
                        }
                        if (s.contains("data")) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                Map<String, Object> map = new HashMap<String, Object>();

                                setList = new ArrayList<Home_item>();
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
                                        createTime = createTime.substring(0, 10);
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
                                    String contentNumBer = json.getString("task_counts");
                                    String parentid = json.getString("parent_id");
                                    String parentname = json.getString("parent_name");
                                    //最后一个false是判断是否置顶的，这个界面复用的实体类，但又没有置顶，所以用false
                                    setList.add(new Home_item(content, createTime, id, orgId, orgName, contentNumBer, "", parentname, parentid, false));
                                }
                                if (setList.size() > 0) {
                                    mAdapter.getData(setList);
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
                        try {
                            Dates.disDialog();
                        }catch (NullPointerException e11){
                            e11.printStackTrace();
                        }
                        ToastUtils.showShortToast("网络连接失败");
                        listView.setVisibility(View.GONE);
                        nullposion.setVisibility(View.VISIBLE);
                    }

                });
    }


}
