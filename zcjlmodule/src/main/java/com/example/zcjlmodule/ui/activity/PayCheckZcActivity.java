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
import com.example.zcjlmodule.bean.PayCheckBean;
import com.example.zcjlmodule.presenter.PayCheckPresenter;
import com.example.zcjlmodule.view.PayCheckView;
import com.example.zcmodule.R;
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
public class PayCheckZcActivity extends BaseMvpActivity<PayCheckPresenter> implements PayCheckView {
    private PayCheckZcActivity.RecyclerAdapter adapter;
    private SmartRefreshLayout refreshLayout;
    private LinearLayout emptyView;
    private ProgressBar gressBar;
    private TextView prompt;
    private List<String> list;
    private ArrayList<PayCheckBean> mData;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_check_zc);
        list = new ArrayList<>();
        mData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("sss");
        }
        mContext = this;
        //获取实例
        mPresenter = new PayCheckPresenter();
        //拿到当前界面的实例
        mPresenter.mView = this;
        //传递数据到presenter层处理
        mPresenter.init("12");
        emptyView = (LinearLayout) findViewById(R.id.layout_emptyView);
        //等待的滚动条
        gressBar = (ProgressBar) findViewById(R.id.layout_emptyView_bar);
        //错误提示
        prompt = (TextView) findViewById(R.id.layout_emptyView_text);
        //刷新加载
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        //展示数据
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.paycheck_recycler);
        //设置展示数据样式，list或者grid
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //调加适配器，初始化布局和数据
        recyclerView.setAdapter(adapter = new PayCheckZcActivity.RecyclerAdapter(R.layout.adapter_paycheck_zc, list));
        //数据加载动画
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        if (list.size() > 0) {
            emptyView.setVisibility(View.GONE);
        }
        //item的点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        //  下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //传入false表示刷新失败
//                mPresenter.init();
                refreshlayout.finishRefresh(800);
            }
        });
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //传入false表示加载失败
//                mPresenter.init();
                refreshlayout.finishLoadmore(800);
            }
        });
    }

    // 处理数据
    @Override
    public void getdata(String str) {
        //   adapter.getData(mData);
    }

    //recyclerview适配器
    public class RecyclerAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public RecyclerAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
//            helper.setText(R.id.activity_paycheck_payname)
        }
    }
}
