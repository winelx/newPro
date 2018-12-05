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
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.administrator.newsdf.pzgc.Adapter.DeviceMessageListAdapter;
import com.example.administrator.newsdf.pzgc.activity.device.utils.DeviceUtils;
import com.example.administrator.newsdf.pzgc.bean.MyNoticeDataBean;
import com.example.administrator.newsdf.pzgc.inter.ItemClickListener;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.ScreenUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * @author lx
 * @Created by: 2018/12/5 0005.
 * @description:特种设备全部消息列表界面（全部）
 * @Activity：
 */

public class DeviceMessageAllActivity extends BaseActivity implements View.OnClickListener {

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
        });
    }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 这条表示加载菜单文件，第一个参数表示通过那个资源文件来创建菜单
        // 第二个表示将菜单传入那个对象中。这里我们用Menu传入menu
        // 这条语句一般系统帮我们创建好
        getMenuInflater().inflate(R.menu.__picker_menu_picker, menu);
        return true;
    }
    // 菜单的监听方法
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            default:
                ToastUtils.showLongToast("sss");
                break;
        }
        return true;

    }
    //网络请求
    public void getdate() {
        deviceUtils.decicemessagelist(new DeviceUtils.MeListOnclickLitener() {
            @Override
            public void onsuccess(ArrayList<MyNoticeDataBean> data) {
                mAdapter.getData(data);
            }
        });
    }

    //menu弹出窗
    private void meun() {
        //弹出框=
        View contentView = getPopupWindowContentView();
        mPopupWindow = new PopupWindow(contentView,
                Dates.withFontSize(resolution) + 20, Dates.higtFontSize(resolution), true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置好参数之后再show
        // 默认在mButton2的左下角显示
        mPopupWindow.showAsDropDown(checklistmeun);
        backgroundAlpha(0.5f);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    //界面亮度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    public View getPopupWindowContentView() {
        // 一个自定义的布局，作为显示的内容
        // 布局ID
        int layoutId = R.layout.pop_device_menu;
        View contentView = LayoutInflater.from(this).inflate(layoutId, null);
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                mData.clear();
                switch (v.getId()) {
                    case R.id.pop_All:
                        ToastUtils.showLongToast("全部");

                        break;
                    case R.id.pop_submit:
                        ToastUtils.showLongToast("未回复");
                        //待提交

                        break;
                    case R.id.pop_financial:
                        ToastUtils.showLongToast("未验证");

                        break;
                    case R.id.pop_manage:
                        ToastUtils.showLongToast("打回");

                        break;
                    case R.id.pop_back:
                        ToastUtils.showLongToast("已完成");

                        break;
                    default:
                        break;
                }
                getdate();
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        };
        contentView.findViewById(R.id.pop_All).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_submit).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_financial).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_manage).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_back).setOnClickListener(menuItemOnClickListener);
        return contentView;
    }

    public void status() {
        startActivity(new Intent(mContext, DeviceDetailsActivity.class));

    }
}
