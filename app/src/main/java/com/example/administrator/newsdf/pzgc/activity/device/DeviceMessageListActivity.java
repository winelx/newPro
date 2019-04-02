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
import com.example.administrator.newsdf.pzgc.Adapter.DeviceMessageListAdapter;
import com.example.administrator.newsdf.pzgc.activity.device.utils.DeviceUtils;
import com.example.administrator.newsdf.pzgc.bean.DeviceMeList;
import com.example.administrator.newsdf.pzgc.callback.TaskCallback;
import com.example.administrator.newsdf.pzgc.callback.TaskCallbackUtils;
import com.example.administrator.newsdf.pzgc.inter.ItemClickListener;
import com.example.baselibrary.view.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.PullDownMenu;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/11/28 0028.
 * @description: 我的特种设备整改通知（我的）
 */

public class DeviceMessageListActivity extends BaseActivity implements View.OnClickListener, TaskCallback {
    private RecyclerView listView;
    private ArrayList<DeviceMeList> mData;
    private Context mContext;
    private TextView titleView;
    private ImageView checklistmeunimage;
    private SmartRefreshLayout refreshLayout;
    private String orgId;
    private DeviceMessageListAdapter mAdapter;
    private RelativeLayout backNotNull;
    private DeviceUtils deviceUtils;
    private LinearLayout checklistmeun;
    private String[] meuns = {"全部", "未下发", "未回复", "未验证","打回", "已处理"};
    private int status = -1, page = 1;
    private boolean refresh = true;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checknoticemessage);
        addActivity(this);
        mContext = this;
        mData = new ArrayList<>();
        Dates.getDialogs(this, "请求数据中...");
        TaskCallbackUtils.setCallBack(this);
        deviceUtils = new DeviceUtils();
        final Intent intent = getIntent();
        orgId = intent.getStringExtra("orgId");
        //获取传递的id
        checklistmeun = (LinearLayout) findViewById(R.id.checklistmeun);
        checklistmeun.setOnClickListener(this);
        //刷新加载控件
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        //空数据
        backNotNull = (RelativeLayout) findViewById(R.id.back_not_null);
        backNotNull.setVisibility(View.GONE);
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
        listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //初始化适配器
        mAdapter = new DeviceMessageListAdapter(mContext);
        listView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void Onclick(View view, int position) {
                int status = mData.get(position).getStatus();
                if (status == 0) {
                    Intent intent = new Intent(DeviceMessageListActivity.this, NewDeviceActivity.class);
                    intent.putExtra("id", mData.get(position).getId());
                    startActivity(intent);
                } else {
                    Intent intent1 = new Intent(mContext, DeviceDetailsActivity.class);
                    intent1.putExtra("id", mData.get(position).getId());
                    intent1.putExtra("orgname", titleView.getText().toString());
                    intent1.putExtra("orgId", orgId);
                    startActivity(intent1);
                }
            }

            @Override
            public void ondelete(final int position) {
                //删除
                deviceUtils.devicedelete(mData.get(position).getId(), new DeviceUtils.devicesavelitener() {
                    @Override
                    public void success(String number, String id) {
                        mData.remove(position);
                        mAdapter.getData(mData);
                    }
                });
            }
        });
        /**
         *   下拉刷新
         */
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                mData.clear();
                refresh=true;
                getdate();

            }
        });
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                refresh=false;
                getdate();
            }
        });
        getdate();
    }

    /**
     * @description: 界面点击事件
     * @author lx
     * @date: 2018/12/5 0005 下午 3:17
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
     * @date: 2018/12/5 0005 下午 3:17
     */
    public void getdate() {
        deviceUtils.decicemessagelist(false, orgId, page, status, new DeviceUtils.MeListOnclickLitener() {
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
     * @date: 2018/12/5 0005 下午 3:17
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
     * @description: menu弹窗按钮点击事件处理
     * @author lx
     * @date: 2018/12/5 0005 下午 3:17
     */
    public void ContentView(String string) {
        page = 1;
        switch (string) {
            case "全部":
                getdate();
                status = -1;
                break;
            case "未下发":
                status = 0;
                break;
            case "未回复":
                status = 1;
                break;
            case "未验证":
                status = 2;
                break;
            case "已处理":
                status = 4;
                break;
            case "打回":
                status = 3;
                break;
            default:
                break;
        }
        getdate();
    }

    @Override
    public void taskCallback() {
        page = 1;
        getdate();
    }
}
