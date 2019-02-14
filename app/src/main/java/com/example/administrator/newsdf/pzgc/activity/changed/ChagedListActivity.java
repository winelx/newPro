package com.example.administrator.newsdf.pzgc.activity.changed;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.changed.adapter.ChagedListAdapter;
import com.example.administrator.newsdf.pzgc.bean.ChagedList;
import com.example.administrator.newsdf.pzgc.callback.JPushCallUtils;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;

import com.example.administrator.newsdf.pzgc.utils.ListJsonUtils;
import com.example.administrator.newsdf.pzgc.view.SwipeMenuLayout;
import com.example.baselibrary.PullDownMenu;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/1 0001}
 * 描述：整改通知单列表
 * {@link }
 */
public class ChagedListActivity extends BaseActivity implements View.OnClickListener {
    private SmartRefreshLayout refreshlayout;
    private RecyclerView recyclerList;
    private TextView title;
    private ImageView toolbarImage;
    private ChagedListAdapter adapter;
    private ArrayList<ChagedList> list;
    private Context mContext;
    private PullDownMenu pullDownMenu;
    private ChagedUtils chagedUtils;
    String[] strings = {"全部", "保存", "未处理", "已处理"};
    private boolean isAll;
    private String orgId;
    private int page = 1;
    private int status = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_list);
        mContext = this;
        Intent intent = getIntent();
        isAll = intent.getBooleanExtra("isAll", false);
        orgId = intent.getStringExtra("orgid");
        chagedUtils = new ChagedUtils();
        list = new ArrayList<>();
        recyclerList = (RecyclerView) findViewById(R.id.recycler_list);
        title = (TextView) findViewById(R.id.com_title);
        title.setText(intent.getStringExtra("orgname"));
        findViewById(R.id.toolbar_menu).setOnClickListener(this);
        toolbarImage = (ImageView) findViewById(R.id.com_img);
        toolbarImage.setImageResource(R.mipmap.meun);
        toolbarImage.setVisibility(View.VISIBLE);
        findViewById(R.id.com_back).setOnClickListener(this);
        //设置列表参数
        recyclerList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChagedListAdapter(list, mContext);
        recyclerList.setAdapter(adapter);
        refreshlayout = (SmartRefreshLayout) findViewById(R.id.refreshlayout);
        //是否启用下拉刷新功能
        refreshlayout.setEnableRefresh(true);
        //是否启用上拉加载功能
        refreshlayout.setEnableLoadmore(true);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshlayout.setEnableOverScrollDrag(false);
        /* 下拉刷新*/
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                adapter.setNewData(list);
                //关闭刷新
                refreshlayout.finishRefresh();
            }
        });
        /* 上拉加载*/
        refreshlayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //关闭上拉加载
                refreshlayout.finishLoadmore();
            }
        });
        adapter.setOnDelListener(new ChagedListAdapter.onSwipeListener() {
            @Override
            public void onDel(int pos, SwipeMenuLayout layout) {
                /*删除按钮*/
                list.remove(pos);
                adapter.setNewData(list);
            }

            @Override
            public void onClick(int pos) {
                /*点击按钮*/
                startActivity(new Intent(mContext, ChagedNoticeDetailsActivity.class));
            }

        });
        chagedUtils.getcnflist(isAll, status, orgId, page, new ChagedUtils.CallBack() {

            @Override
            public void onsuccess(Map<String, Object> map) {
                ArrayList<ChagedList> data = (ArrayList<ChagedList>) map.get("list");
                list.addAll(data);
                adapter.setNewData(list);
            }

            @Override
            public void onerror(String str) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                finish();
                break;
            case R.id.toolbar_menu:
                pullDownMenu = new PullDownMenu();
                pullDownMenu.showPopMeun((Activity) mContext, toolbarImage, strings);
                pullDownMenu.setOnItemClickListener(new PullDownMenu.OnItemClickListener() {
                    @Override
                    public void onclick(int position, String string) {
                        switch (string) {
                            case "全部":
                                ToastUtils.showShortToast("全部");
                                break;
                            case "未处理":
                                ToastUtils.showShortToast("未处理");
                                break;
                            case "已处理":
                                ToastUtils.showShortToast("已处理");
                                break;
                            default:
                                break;
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

}
