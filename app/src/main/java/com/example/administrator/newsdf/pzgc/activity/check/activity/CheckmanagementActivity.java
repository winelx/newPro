package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.CheckManagementAdapter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.newsdf.R.id.expandable;

/**
 * description: 检查管理_所有有权限的组织列表
 *
 * @author lx
 *         date: 2018/8/2 0002 下午 2:39
 *         update: 2018/8/2 0002
 *         version:
 */
public class CheckmanagementActivity extends AppCompatActivity implements View.OnClickListener {
    private ExpandableListView expandableListView;
    private SmartRefreshLayout refreshLayout;
    private TextView comtitle;
    private CheckManagementAdapter mAdapter;
    private View.OnClickListener ivGoToChildClickListener;
    private Context mContext;
    /**
     * 父级数据集合
     */
    private ArrayList<String> parentlist;
    /**
     * 所有数据
     */
    private List<Home_item> mData;
    /**
     * 每层子集的具体数据
     */
    private Map<String, List<Home_item>> hasMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkmanagement);
        mContext = CheckmanagementActivity.this;
        expandableListView = (ExpandableListView) findViewById(expandable);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        comtitle = (TextView) findViewById(R.id.titleView);
        comtitle.setText("检查管理");
        findViewById(R.id.checklistback).setOnClickListener(this);
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getdata();
                refreshlayout.finishRefresh(800);
            }
        });
        getdata();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checklistback:
                finish();
                break;
            default:
                break;
        }
    }

    public void getdata() {
        OkGo.get(Requests.getCheckOrg)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        mData = new ArrayList<>();
                        parentlist = new ArrayList<>();
                        hasMap = new HashMap<>();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.getInt("ret") == 0) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json = jsonArray.getJSONObject(i);
                                        String count = json.getString("count");
                                        String id = json.getString("id");
                                        String name = json.getString("name");
                                        String orgType = json.getString("orgType");
                                        String parentId = json.getString("parentId");
                                        String parentName = json.getString("parentName");
//将组织所属公司添加到集合
                                        if (!parentlist.contains(parentName)) {
                                            parentlist.add(parentName);
                                        }
                                        mData.add(new Home_item("", "", "", id, name, count, "", parentName, parentId, false));
                                    }
                                    //是否有数据
                                    if (mData.size() > 0) {
                                        int random = (int) (Math.random() * 4) + 1;
                                        for (String str : parentlist) {
                                            List<Home_item> list = new ArrayList<>();
                                            for (Home_item item : mData) {
                                                String name = item.getParentname();
                                                if (str.equals(name)) {
                                                    list.add(item);
                                                    hasMap.put(str, list);
                                                }
                                            }
                                        }
                                    }
                                    mAdapter = new CheckManagementAdapter(parentlist, hasMap, mContext,
                                            ivGoToChildClickListener);
                                    expandableListView.setAdapter(mAdapter);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
//    @Override
//    public void setAdapter(List<String> list, Map<String, List<Home_item>> map) {
//        mAdapter = new CheckManagementAdapter(list, map, mContext,
//                ivGoToChildClickListener);
//        expandableListView.setAdapter(mAdapter);
//        refreshLayout.finishRefresh(true);
//    }
}
