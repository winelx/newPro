package com.example.zcjlmodule.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
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
    private TextView prompt;
    private ArrayList<PayCheckZcBean> mData;
    private Context mContext;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_check_zc);
        mData = new ArrayList<>();
        mContext = this;
        //获取实例
        mPresenter = new PayCheckPresenter();
        //拿到当前界面的实例
        mPresenter.mView = this;

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
        //展示数据
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.paycheck_recycler);
        //设置展示数据样式，list或者grid
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //调加适配器，初始化布局和数据
        recyclerView.setAdapter(adapter = new PayCheckZcActivity.RecyclerAdapter(R.layout.adapter_paycheck_zc, mData));
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //传入false表示刷新失败
                page = 1;
                mPresenter.init("12");
                refreshlayout.finishRefresh(800);
            }
        });
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //传入false表示加载失败
                page++;
                mPresenter.init("12");
                refreshlayout.finishLoadmore(800);
            }
        });
        //传递数据到presenter层处理
        mPresenter.init("12");
        //item的点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    // 处理数据
    @Override
    public void getdata(ArrayList<PayCheckZcBean> list) {
        //判断加载页，判断是否删除之前的数据
        if (page == 1) {
            mData.clear();
        }
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
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.toolbar_icon_back) {
            finish();
        } else {

        }
    }

    //recyclerview适配器
    public class RecyclerAdapter extends BaseQuickAdapter<PayCheckZcBean, BaseViewHolder> {
        public RecyclerAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, PayCheckZcBean item) {
            helper.setText(R.id.activity_paycheck_name, item.getName());
            helper.setText(R.id.activity_paycheck_number, item.getNumber());
            helper.setText(R.id.activity_paycheck_payname, item.getName());
            helper.setText(R.id.activity_paycheck_paymoney, item.getPaymoney());
            helper.setText(R.id.activity_paycheck_checkmoney, item.getCheckmoney());
        }
    }
}
