package com.example.administrator.newsdf.pzgc.activity.device;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.App;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.CheckRectifyMessageAdapter;
import com.example.administrator.newsdf.pzgc.Adapter.DeviceMessageListAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckRectificationActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.ChecknoticeMessagelistActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.IssuedTaskDetailsActivity;
import com.example.administrator.newsdf.pzgc.activity.device.utils.DeviceUtils;
import com.example.administrator.newsdf.pzgc.bean.MyNoticeDataBean;
import com.example.administrator.newsdf.pzgc.callback.TaskCallbackUtils;
import com.example.administrator.newsdf.pzgc.inter.ItemClickListener;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.PullDownMenu;
import com.example.administrator.newsdf.pzgc.utils.ScreenUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/11/28 0028.
 * @description:我的特种设备整改通知（我的）
 */

public class DeviceMessageListActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView listView;
    private ArrayList<MyNoticeDataBean> mData;
    private Context mContext;
    private TextView titleView;
    private ImageView checklistmeunimage;
    private PopupWindow mPopupWindow;
    private SmartRefreshLayout refreshLayout;
    private float resolution;
    private String id;
    private int page = 1;
    private DeviceMessageListAdapter mAdapter;
    private RelativeLayout back_not_null;
    private DeviceUtils deviceUtils;
    private LinearLayout checklistmeun;
    private String[] meuns = {"全部", "未下发", "未回复", "未验证", "已处理"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checknoticemessage);
        mContext = this;
        mData = new ArrayList<>();
        deviceUtils = new DeviceUtils();
        Intent intent = getIntent();
        //获取传递的id
        //获取系统dp尺寸密度值
        resolution = ScreenUtil.getDensity(App.getInstance());
        checklistmeun = (LinearLayout) findViewById(R.id.checklistmeun);
        checklistmeun.setOnClickListener(this);
        //刷新加载控件
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        //空数据
        back_not_null = (RelativeLayout) findViewById(R.id.back_not_null);
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
//        titleView.setText(intent.getStringExtra("orgName"));
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
                page = 1;
                mData.clear();
                getdate();
                //传入false表示刷新失败
                refreshlayout.finishRefresh();
            }
        });
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getdate();
                //传入false表示加载失败
                refreshlayout.finishLoadmore();
            }
        });
        listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //初始化适配器
        mAdapter = new DeviceMessageListAdapter(mContext);
        listView.setAdapter(mAdapter);
        getdate();
        mAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void Onclick(View view, int position) {
                startActivity(new Intent(mContext, DeviceDetailsActivity.class));
            }

            @Override
            public void ondelete(int position) {

            }
        });
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
        deviceUtils.decicemessagelist(new DeviceUtils.MeListOnclickLitener() {
            @Override
            public void onsuccess(ArrayList<MyNoticeDataBean> data) {
                mAdapter.getData(data);
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
        mData.clear();
        switch (string) {
            case "全部":
                ToastUtils.showLongToast("全部");
                break;
            case "未下发":
                ToastUtils.showLongToast("未下发");
                //待提交
                break;
            case "未回复":
                ToastUtils.showLongToast("未回复");
                break;
            case "未验证":
                ToastUtils.showLongToast("未验证");
                break;
            case "已处理":
                ToastUtils.showLongToast("已处理");
                break;
            default:
                break;
        }

    }

}
