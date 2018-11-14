package com.example.administrator.newsdf.pzgc.activity.check.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.CheckMessageMineAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckRectificationActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.ChecknoticeMessagelistActivity;
import com.example.administrator.newsdf.pzgc.bean.Home_item;
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

import okhttp3.Call;
import okhttp3.Response;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;


/**
 * description: 我的检查通知
 *
 * @author lx
 *         date: 2018/8/8 0008 上午 10:09
 *         update: 2018/8/8 0008
 *         version:
 */
public class CheckdownMessageMeFragment extends Fragment {
    private View view;
    ArrayList<Home_item> mData;
    CheckMessageMineAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_collection, container, false);
        final Context mContext = getActivity();
        mData = new ArrayList<>();

        ImageView checkNewadd = view.findViewById(R.id.check_newadd);
        checkNewadd.setVisibility(View.VISIBLE);
        LinearLayout nullposion = view.findViewById(R.id.nullposion);
        nullposion.setVisibility(View.GONE);
        //下拉刷新
        SmartRefreshLayout refreshLayout = view.findViewById(R.id.SmartRefreshLayout);
        //布局控件
        RecyclerView recyclerView = view.findViewById(R.id.home_list);
        //设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //设置分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, VERTICAL));
        //设置适配器
        mAdapter = new CheckMessageMineAdapter(mContext, mData);
        recyclerView.setAdapter(mAdapter);
        refreshLayout.setEnableLoadmore(false);
        /**
         *   下拉刷新
         */
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //传入false表示刷新失败
                getdata();
                refreshlayout.finishRefresh(800);
            }
        });
        mAdapter.setOnItemClickListener(new CheckMessageMineAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mContext, ChecknoticeMessagelistActivity.class);
                intent.putExtra("id", mData.get(position).getId());
                intent.putExtra("orgName", mData.get(position).getOrgname());
                intent.putExtra("status", false);
                mContext.startActivity(intent);
            }
        });
        checkNewadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, CheckRectificationActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getdata();
    }

    public void getdata() {
        OkGo.get(Requests.GET_ALL_NOTICE_ORG_APP)
                .params("isAll", false)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                mData.clear();
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        String id = jsonObject1.getString("id");
                                        String name = jsonObject1.getString("name");
                                        String orgtype = jsonObject1.getString("count");
                                        String parentId = jsonObject1.getString("parentId");
                                        String parentName = jsonObject1.getString("parentName");
                                        mData.add(new Home_item("", "", id, "", name, orgtype, "", parentName, parentId, false));
                                    }
                                }
                                mAdapter.getData(mData);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                    }
                });
    }

}
