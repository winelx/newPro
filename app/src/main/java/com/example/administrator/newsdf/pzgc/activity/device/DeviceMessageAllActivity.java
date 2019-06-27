package com.example.administrator.newsdf.pzgc.activity.device;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.adapter.DeviceMessageListAdapter;
import com.example.administrator.newsdf.pzgc.activity.device.utils.DeviceUtils;
import com.example.administrator.newsdf.pzgc.bean.DeviceMeList;
import com.example.administrator.newsdf.pzgc.inter.ItemClickListener;
import com.example.baselibrary.base.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.PullDownMenu;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/12/5 0005.
 * @description: 特种设备全部消息列表界面（全部）
 * @Activity：
 */

public class DeviceMessageAllActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView listView;
    private ArrayList<DeviceMeList> mData;
    private Context mContext;
    private TextView titleView;
    private ImageView checklistmeunimage;
    private SmartRefreshLayout refreshLayout;
    private int page = 1;
    private DeviceMessageListAdapter mAdapter;
    private RelativeLayout backNotNull;
    private DeviceUtils deviceUtils;
    private LinearLayout checklistmeun;
    private String[] meuns = {"全部", "未回复", "未验证", "打回", "已完成"};
    //当前任务状态（）
    private int status = -1;
    private boolean refresh = true;
    private String orgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checknoticemessage);

        final Intent intent = getIntent();
        orgId = intent.getStringExtra("orgId");
        mContext = this;
        mData = new ArrayList<>();
        deviceUtils = new DeviceUtils();
        Dates.getDialogs(this, "请求数据中...");
        checklistmeun = (LinearLayout) findViewById(R.id.checklistmeun);
        checklistmeun.setOnClickListener(this);
        //刷新加载控件
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        //空数据
        backNotNull = (RelativeLayout) findViewById(R.id.back_not_null);
        //menu
        checklistmeunimage = (ImageView) findViewById(R.id.checklistmeunimage);
        //设置menu控件背景
        checklistmeunimage.setBackgroundResource(R.mipmap.meun);
        //显示menu控件
        checklistmeunimage.setVisibility(View.VISIBLE);
        //recyclerview
        listView = (RecyclerView) findViewById(R.id.maber_tree);
        //标题
        titleView = (TextView) findViewById(R.id.titleView);
        //设置标题
        titleView.setText(intent.getStringExtra("orgName"));
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /**
         *   下拉刷新
         */
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //加载第一页
                page = 1;
                //标记状态为刷新
                refresh = true;
                //请求数据
                getdate();

            }
        });
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                //标记状态为加载
                refresh = false;
                //请求数据
                getdate();

            }
        });
        listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //初始化适配器
        mAdapter = new DeviceMessageListAdapter(mContext);
        listView.setAdapter(mAdapter);
        //点击事件
        mAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void Onclick(View view, int position) {
                //点击
                Intent intent = new Intent(mContext, DeviceDetailsActivity.class);
                intent.putExtra("id", mData.get(position).getId());
                intent.putExtra("status", false);
                intent.putExtra("orgId", orgId);
                intent.putExtra("orgname", titleView.getText().toString());
                mContext.startActivity(intent);
            }

            @Override
            public void ondelete(int position) {
                //侧滑删除
                mData.remove(position);
            }
        });
        //网络请求
        getdate();
    }

    /**
     * @description: 点击事件
     * @author lx
     * @date: 2018/12/5 0005 下午 3:19
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checklistmeun:
                meun();
                break;
            default:
                break;
        }
    }

    /**
     * @description: 网络请求
     * @author lx
     * @date: 2018/12/5 0005 下午 3:19
     */
    public void getdate() {
        deviceUtils.decicemessagelist(true, orgId, page, status, new DeviceUtils.MeListOnclickLitener() {
            @Override
            public void onsuccess(ArrayList<DeviceMeList> data) {
                if (page == 1) {
                    mData.clear();
                }
                if (refresh) {
                    //关闭刷新
                    refreshLayout.finishRefresh();
                } else {
                    //关闭加载
                    refreshLayout.finishLoadmore();
                }
                mData.addAll(data);
                mAdapter.getData(mData);
                if (mData.size() > 0) {
                    backNotNull.setVisibility(View.GONE);
                } else {
                    backNotNull.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    /**
     * @description: menu弹出窗
     * @author lx
     * @date: 2018/12/5 0005 下午 3:18
     */
    private void meun() {
        PullDownMenu pullDownMenu = new PullDownMenu();
        pullDownMenu.showPopMeun(this, checklistmeun, meuns);
        pullDownMenu.setOnItemClickListener(new PullDownMenu.OnItemClickListener() {
            @Override
            public void onclick(int position, String string) {
                ContentView(string);
            }
        });
    }

    /**
     * @description: menu弹窗点击事件
     * @author lx
     * @date: 2018/12/5 0005 下午 3:18
     */
    public void ContentView(String str) {
        page = 1;
//        mData.clear();
        switch (str) {
            case "全部":
                status = -1;
                break;
            case "未回复":
                status = 1;
                break;
            case "未验证":
                status = 2;
                break;
            case "打回":
                status = 3;
                break;
            case "已完成":
                status = 5;
                break;
            default:
                break;
        }
        getdate();
    }

    public void status() {
        startActivity(new Intent(mContext, DeviceDetailsActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (deviceUtils != null) {
            deviceUtils = null;
        }

    }
}
