package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.newsdf.App;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.NotSubmitTaskAdapter;
import com.example.administrator.newsdf.pzgc.bean.CheckTasklistAdapter;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.ScreenUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;


/**
 * description: 检查管理_标准的检查任务
 *
 * @author lx
 *         date: 2018/8/2 0002 下午 2:41
 *         update: 2018/8/2 0002
 *         version:
 */
public class CheckTasklistActivity extends AppCompatActivity implements View.OnClickListener {
    private NotSubmitTaskAdapter mAdapter;
    private ArrayList<CheckTasklistAdapter> list;
    private Context mContext;
    private PopupWindow mPopupWindow;
    private LinearLayout checklistmeun;
    private float resolution;
    private SmartRefreshLayout smartrefreshlayout;
    private int pages = 1;
    private String orgId, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkmanagementlist);
        mContext = CheckTasklistActivity.this;
        try {
            Intent intent = getIntent();
            name = intent.getStringExtra("name");
            orgId = intent.getStringExtra("orgId");
        } catch (NullPointerException e) {
            name = "";
            orgId = "";
        }

        list = new ArrayList<>();
        //获取屏幕对比比例1DP=？PX 比例有 1 ，2 ，3 ，4
        resolution = ScreenUtil.getDensity(App.getInstance());
        list.add(new CheckTasklistAdapter("测试公司项目", "张三，2018-00-00", "所属标准", "检查组织", "88", "1"));
        list.add(new CheckTasklistAdapter("测试公司项目", "张三，2018-00-00", "所属标准", "检查组织", "88", "2"));
        list.add(new CheckTasklistAdapter("测试公司项目", "张三，2018-00-00", "所属标准", "检查组织", "88", "2"));
        list.add(new CheckTasklistAdapter("测试公司项目", "张三，2018-00-00", "所属标准", "检查组织", "88", "1"));
        list.add(new CheckTasklistAdapter("测试公司项目", "张三，2018-00-00", "所属标准", "检查组织", "88", "1"));

        TextView titleView = (TextView) findViewById(R.id.titleView);
        titleView.setText(name);
        smartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);
        //是否开启越界
        smartrefreshlayout.setEnableOverScrollDrag(true);
        //取消内容不满一页时开启上拉加载功能
        smartrefreshlayout.setEnableLoadmoreWhenContentNotFull(false);
        //是否在刷新的时候禁止列表的操作
        smartrefreshlayout.setDisableContentWhenRefresh(true);
        //是否在加载的时候禁止列表的操作
        smartrefreshlayout.setDisableContentWhenLoading(true);
        ImageView checklistmeunimage = (ImageView) findViewById(R.id.checklistmeunimage);
        checklistmeunimage.setVisibility(View.VISIBLE);
        checklistmeunimage.setBackgroundResource(R.mipmap.meun);
        checklistmeun = (LinearLayout) findViewById(R.id.checklistmeun);
        findViewById(R.id.checklistback).setOnClickListener(this);
        checklistmeun.setOnClickListener(this);
        findViewById(R.id.newcheck).setOnClickListener(this);
        RecyclerView rmanageRecy = (RecyclerView) findViewById(R.id.rmanage_recy);
        //设置布局管理器
        rmanageRecy.setLayoutManager(new LinearLayoutManager(this));
        //设置适配器
        rmanageRecy.setAdapter(mAdapter = new NotSubmitTaskAdapter(this));
        //设置Item增删的动画
        rmanageRecy.setItemAnimator(new DefaultItemAnimator());
        mAdapter.getData(list);
        /**
         *   下拉刷新
         */
        smartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pages = 1;
                list.clear();
                for (int i = 0; i < 5; i++) {
                    list.add(new CheckTasklistAdapter("测试公司项目", "张三，2018-00-00", "所属标准", "检查组织", "88", "1"));
                    list.add(new CheckTasklistAdapter("测试公司项目", "张三，2018-00-00", "所属标准", "检查组织", "88", "2"));
                    list.add(new CheckTasklistAdapter("测试公司项目", "张三，2018-00-00", "所属标准", "检查组织", "88", "2"));
                    list.add(new CheckTasklistAdapter("测试公司项目", "张三，2018-00-00", "所属标准", "检查组织", "88", "1"));
                    list.add(new CheckTasklistAdapter("测试公司项目", "张三，2018-00-00", "所属标准", "检查组织", "88", "1"));
                }
                mAdapter.getData(list);
                //结束刷新
                smartrefreshlayout.finishRefresh();
            }
        });
        //上拉加载
        smartrefreshlayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pages++;
                for (int i = 0; i < 10; i++) {
                    list.add(new CheckTasklistAdapter("测试公司项目", "张三，2018-00-00", "所属标准", "检查组织1", "88", "1"));
                    list.add(new CheckTasklistAdapter("测试公司项目", "张三，2018-00-00", "所属标准", "检查组1", "88", "2"));
                    list.add(new CheckTasklistAdapter("测试公司项目", "张三，2018-00-00", "所属标准", "检查组织2", "88", "1"));
                }
                mAdapter.getData(list);
                //结束加载
                smartrefreshlayout.finishLoadmore();

            }
        });
        mAdapter.setOnItemClickListener(new NotSubmitTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String status = list.get(position).getStatus();
                if ("1".equals(status)) {
                    ToastUtils.showLongToast("已提交");
                } else {
                    ToastUtils.showLongToast("未提交");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checklistback:
                finish();
                break;
            case R.id.checklistmeun:
                MeunPop();
                break;
            case R.id.newcheck:
                Intent intent = new Intent(mContext, CheckNewAddActivity.class);
                intent.putExtra("orgId", orgId);
                intent.putExtra("name", name);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    //弹出框
    private void MeunPop() {
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
        mPopupWindow.setOnDismissListener(new poponDismissListener());
    }

    //设置pop的点击事件
    private View getPopupWindowContentView() {
        // 一个自定义的布局，作为显示的内容
        // 布局ID
        int layoutId = R.layout.checkmanagementlist_pop;
        View contentView = LayoutInflater.from(this).inflate(layoutId, null);
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.pop_All:
                        ToastUtils.showShortToastCenter("全部");
                        break;
                    case R.id.pop_financial:
                        ToastUtils.showShortToastCenter("已提交");
                        break;
                    case R.id.pop_manage:
                        ToastUtils.showShortToastCenter("未提交");
                        break;
                    default:
                        break;
                }
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        };

        contentView.findViewById(R.id.pop_All).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_financial).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_manage).setOnClickListener(menuItemOnClickListener);
        return contentView;
    }

    //界面亮度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    /**
     * popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }

}
