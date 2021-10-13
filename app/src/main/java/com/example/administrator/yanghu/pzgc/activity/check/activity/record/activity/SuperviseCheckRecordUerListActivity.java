package com.example.administrator.yanghu.pzgc.activity.check.activity.record.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.activity.check.activity.record.bean.RecordUerListBean;
import com.example.administrator.yanghu.pzgc.activity.check.activity.record.utils.RecodModel;
import com.example.administrator.yanghu.pzgc.utils.Dates;
import com.example.administrator.yanghu.pzgc.utils.ToastUtils;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.utils.network.NetworkAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 说明：检查/被检查人员列表
 * 创建时间： 2020/7/30 0030 13:49
 *
 * @author winelx
 */
public class SuperviseCheckRecordUerListActivity extends BaseActivity implements View.OnClickListener {
    private TextView comtitle;
    //显示数据集合
    private List<RecordUerListBean> search;
    //原数据
    private List<RecordUerListBean> list;
    //选择数据
    private List<String> choice;
    private String orgId;
    private SmartRefreshLayout refreshlayout;
    private TextView deleteSearch, com_button;
    private EditText searchEditext;
    private MoretasklistAdapter adapter;
    private RecyclerView recyclerView;
    private boolean lean;
    private RecodModel recodModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_uerlist);
        recodModel = new RecodModel();
        Intent intent = getIntent();
        orgId = intent.getStringExtra("orgId");
        String content = intent.getStringExtra("content");
        lean = intent.getBooleanExtra("type", true);
        findViewById(R.id.com_back).setOnClickListener(this);
        refreshlayout = findViewById(R.id.refreshlayout);
        //禁止上拉
        refreshlayout.setEnableLoadmore(false);
        //仿ios越界
        refreshlayout.setEnableOverScrollBounce(true);
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                search.clear();
                search.addAll(list);
                adapter.setNewData(search);
                refreshlayout.finishRefresh(true);
            }
        });
        comtitle = findViewById(R.id.com_title);
        comtitle.setText("选择责任人");
        com_button = findViewById(R.id.com_button);
        com_button.setOnClickListener(this);
        com_button.setText("确定");
        deleteSearch = findViewById(R.id.delete_search);
        searchEditext = findViewById(R.id.search_editext);
        recyclerView = findViewById(R.id.wbs_listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MoretasklistAdapter(R.layout.adapter_record_uerlist, new ArrayList<>());
        recyclerView.setAdapter(adapter);
        deleteSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = searchEditext.getText().toString();
                if (!content.isEmpty()) {
                    search.clear();
                    for (int i = 0; i < list.size(); i++) {
                        String name = list.get(i).getName();
                        if (name.contains(content)) {
                            search.add(list.get(i));
                        }
                    }
                    adapter.setNewData(search);
                } else {
                    ToastUtils.showLongToast("搜索内容不能为空");
                }
            }
        });
        list = new ArrayList<>();
        search = new ArrayList<>();
        choice = new ArrayList<>();
        if (!TextUtils.isEmpty(content)) {
            choice = Dates.stringToList(content, ",");
        }
        if (choice.size() == 0) {
            com_button.setVisibility(View.GONE);
        }
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                RecordUerListBean bean = (RecordUerListBean) adapter.getData().get(position);
                int v = view.getId();
                if (v == R.id.check_status) {
                    if (choice.contains(bean.getName())) {
                        choice.remove(bean.getName());
                        bean.setLean(false);
                        if (choice.size() == 0) {
                            com_button.setVisibility(View.GONE);
                        }
                    } else {
                        String name=bean.getName();
                        choice.add(name);
                        bean.setLean(true);
                        com_button.setVisibility(View.VISIBLE);
                    }
                    adapter.setData(position, bean);
                }
            }
        });
        requset();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                finish();
                break;
            case R.id.com_button:
                getdata();
                break;
            default:
                break;
        }
    }

    public void getdata() {
        Intent intent = new Intent();
        intent.putExtra("content", Dates.listToStrings(choice));
        intent.putExtra("type", lean);
        setResult(101, intent);
        finish();
    }

    public void requset() {
        Map<String, String> map = new HashMap<>();
        map.put("orgId", orgId);
        map.put("showLevel", "3");
        recodModel.choosepersionbyapp(map, new NetworkAdapter() {
            @Override
            public void onsuccess(Object object) {
                super.onsuccess(object);
                list.addAll((List<RecordUerListBean>) object);
                if (choice.size() != 0) {
                    for (int i = 0; i < list.size(); i++) {
                        RecordUerListBean bean = list.get(i);
                        if (choice.contains(bean.getName())) {
                            bean.setLean(true);
                        }
                    }
                }

                if (list.size() != 0) {
                    search.addAll(list);
                }
                adapter.setNewData(search);
            }

            @Override
            public void onerror(String string) {
                super.onerror(string);
                ToastUtils.showShortToast(string);
            }
        });
    }

    public class MoretasklistAdapter extends BaseQuickAdapter<RecordUerListBean, BaseViewHolder> {

        public MoretasklistAdapter(int layoutResId, @Nullable List<RecordUerListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, RecordUerListBean item) {
            helper.setText(R.id.content_name, item.getName());
            helper.setText(R.id.content_zhiw, item.getPosition());
            helper.addOnClickListener(R.id.check_status);
            if (item.isLean()) {
                helper.setBackgroundRes(R.id.check_status, R.mipmap.checkbox_blue);
            } else {
                helper.setBackgroundRes(R.id.check_status, R.mipmap.checkbox_gray);
            }

        }
    }

}
