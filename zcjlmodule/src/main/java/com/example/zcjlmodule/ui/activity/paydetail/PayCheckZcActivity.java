package com.example.zcjlmodule.ui.activity.paydetail;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zcjlmodule.R;
import com.example.zcjlmodule.bean.PayCheckZcBean;
import com.example.zcjlmodule.presenter.PayCheckPresenter;
import com.example.zcjlmodule.view.PayCheckView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import measure.jjxx.com.baselibrary.base.BaseMvpActivity;

/**
 * description: 支付清册核查
 *
 * @author lx
 *         date: 2018/10/12 0012 上午 10:51
 *         update: 2018/10/12 0012
 *         version:
 *         跳转界面:DetailedlistActivity
 *         传递参数 :
 */
public class PayCheckZcActivity extends BaseMvpActivity<PayCheckPresenter> implements PayCheckView, View.OnClickListener {
    private PayCheckZcActivity.RecyclerAdapter adapter;
    private SmartRefreshLayout refreshLayout;
    private LinearLayout emptyView;
    private ProgressBar gressBar;
    private TextView prompt, filenumber;
    private ArrayList<PayCheckZcBean> mData;
    private Context mContext;
    private int page = 0;
    private String id;
    private boolean status = true;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_check_zc);
        mData = new ArrayList<>();
        mContext = this;
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        //获取实例
        mPresenter = new PayCheckPresenter();
        //拿到当前界面的实例
        mPresenter.mView = this;
        findId();
        //文件编号
        filenumber.setText("清册文件编号：" + intent.getStringExtra("filenumber"));
        //加载刷新
        refresh();
        //传递数据到presenter层处理
        mPresenter.init(id, page);
        //item的点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    /**
     * 初始化控件
     */
    private void findId() {
        filenumber = (TextView) findViewById(R.id.paycheck_filenumber);
        TextView title = (TextView) findViewById(R.id.toolbar_icon_title);
        title.setText("支付清册核查");
        findViewById(R.id.toolbar_icon_back).setOnClickListener(this);
        emptyView = (LinearLayout) findViewById(R.id.layout_emptyView);
        //等待的滚动条
        gressBar = (ProgressBar) findViewById(R.id.layout_emptyView_bar);
        //错误提示
        prompt = (TextView) findViewById(R.id.layout_emptyView_text);
        //刷新加载
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        //是否启用列表惯性滑动到底部时自动加载更多
        refreshLayout.setEnableAutoLoadmore(false);
        //是否启用下拉刷新功能
        refreshLayout.setEnableRefresh(false);
        //是否启用上拉加载功能
        refreshLayout.setEnableLoadmore(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableOverScrollDrag(true);
        //展示数据
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.paycheck_recycler);
        //设置展示数据样式，list或者grid
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //调加适配器，初始化布局和数据
        recyclerView.setAdapter(adapter = new RecyclerAdapter(R.layout.adapter_paycheck_zc, mData));
    }

    /**
     * 请求成功
     */
    @Override
    public void getdata(ArrayList<PayCheckZcBean> list) {
        //删除之前的数据
            mData.clear();
        //将网络请求的数据添加到集合
        mData.addAll(list);
        //如果集合的数据大于0，就隐藏空白数据提示
        if (mData.size() > 0) {
            emptyView.setVisibility(View.GONE);
        } else {
            //如果不大于0，显示空白页，隐藏等待框，显示提示
            gressBar.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            prompt.setVisibility(View.VISIBLE);
        }
        //更新数据
        adapter.setNewData(mData);
        //刷新加载关闭
        if (status) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadmore();
        }
    }

    /**
     * 请求失败
     */
    @Override
    public void onerror() {
        //如果不大于0，显示空白页，隐藏等待框，显示提示
        if (mData.size() < 1) {
            gressBar.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            prompt.setVisibility(View.VISIBLE);
            prompt.setText("请求失败");
        }
        //刷新加载关闭
        if (status) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadmore();
        }
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.toolbar_icon_back) {
            finish();
        } else {

        }
    }

    /**
     * 刷新加载
     */
    private void refresh() {
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //传入false表示刷新失败
                page = 0;
                mPresenter.init(id, page);
                //标记状态，判断是刷新还是加载
                status = true;
            }
        });
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //传入false表示加载失败
                page++;
                mPresenter.init(id, page);
                //标记状态，判断是刷新还是加载
                status = false;
            }
        });

    }

    /**
     * recycler的适配器
     */
    public class RecyclerAdapter extends BaseQuickAdapter<PayCheckZcBean, BaseViewHolder> {
        public RecyclerAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, PayCheckZcBean item) {
            helper.setText(R.id.activity_paycheck_name, item.getName());
            helper.setText(R.id.activity_paycheck_number, item.getNumber());
            helper.setText(R.id.activity_paycheck_payname, "支付人："+item.getPayname());
            helper.setText(R.id.activity_paycheck_paymoney, "应支金额：" + item.getPaymoney());
            helper.setText(R.id.activity_paycheck_checkmoney, "核查金额：" + item.getCheckmoney());
        }
    }
}
