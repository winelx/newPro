package com.example.zcjlmodule.ui.activity.dismantling;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
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
import com.example.zcjlmodule.bean.UnknitstandardBean;
import com.example.zcjlmodule.presenter.UnknitstandardPresenter;
import com.example.zcjlmodule.view.UnknitstandardView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import measure.jjxx.com.baselibrary.base.BaseMvpActivity;
import measure.jjxx.com.baselibrary.utils.SPUtils;

/**
 * description: 标准分解
 *
 * @author lx
 *         date: 2018/10/19 0019 下午 3:53
 *         update: 2018/10/19 0019
 *         version:
 *         跳转页面 StandardDismantlingZcActivity
 */
public class UnknitstandardActivity extends BaseMvpActivity<UnknitstandardPresenter> implements UnknitstandardView {
    private StandardDecomposeAdapter mAdapter;
    private Context mContext;
    private ArrayList<UnknitstandardBean> list;
    private SmartRefreshLayout refreshLayout;
    private TextView prompt, unknits_title;
    private LinearLayout emptyView;
    private ProgressBar gressBar;
    private int page = 1;
    private String orgId;
    private boolean status = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unknitstandard);
        mContext = this;
        Intent intent = getIntent();
        list = new ArrayList<>();
        mPresenter = new UnknitstandardPresenter();
        mPresenter.mView = this;
        //界面初始化
        init();
        //recyclerview 初始化
        recycler();
        //刷新加载
        refresh();
        //标准编号
        orgId=intent.getStringExtra("orgId");
        unknits_title.setText("标准编号：" + intent.getStringExtra("filenumber"));
        list = new ArrayList<>();
        //网络请求
        mPresenter.getdata(orgId, page);
    }

    /**
     * 刷新加载
     */
    private void refresh() {
        //  下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                status = true;
                //传入false表示刷新失败
                mPresenter.getdata(orgId, page);
            }
        });
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                status = false;
                mPresenter.getdata(orgId, page);
            }
        });

    }

    /**
     * recyclerview 初始化
     */
    private void recycler() {
        RecyclerView recycler = (RecyclerView) findViewById(R.id.unknit_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(mAdapter = new StandardDecomposeAdapter(R.layout.adapter_decompose_zc, list));
        //是否启用列表惯性滑动到底部时自动加载更多
        refreshLayout.setEnableAutoLoadmore(false);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    /**
     * 初始化界面
     */
    private void init() {
        unknits_title = (TextView) findViewById(R.id.unknits_title);
        emptyView = (LinearLayout) findViewById(R.id.layout_emptyView);
        //等待的滚动条
        gressBar = (ProgressBar) findViewById(R.id.layout_emptyView_bar);
        //错误提示
        prompt = (TextView) findViewById(R.id.layout_emptyView_text);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        TextView title = (TextView) findViewById(R.id.toolbar_icon_title);
        title.setText("标准分解");
        //返回
        findViewById(R.id.toolbar_icon_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {finish();}
        });
    }
    /**
     * 请求成功
     *
     * @param data
     */
    @Override
    public void onSuccess(ArrayList<UnknitstandardBean> data) {
        //判断加载页，判断是否删除之前的数据
        if (page == 1) {
            list.clear();
        }
        //将网络请求的数据添加到集合
        list.addAll(data);
        //更新数据
        mAdapter.setNewData(list);
        blankview(3);
        //判断加载刷新
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
    public void onError() {
        //判断加载刷新
        if (status) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadmore();
        }
        blankview(1);
    }

    /**
     * recycler的适配器
     */
    public class StandardDecomposeAdapter extends BaseQuickAdapter<UnknitstandardBean, BaseViewHolder> {

        public StandardDecomposeAdapter(@LayoutRes int layoutResId, @Nullable List<UnknitstandardBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, UnknitstandardBean item) {
            helper.setText(R.id.unknit_stand_title, item.getTitle());
            helper.setText(R.id.unknit_stand_compensate,"补偿类型："+ item.getCompensate());
            helper.setText(R.id.unknit_stand_type, item.getType());
            helper.setText(R.id.unknit_stand_region, item.getRegion());
            helper.setText(R.id.unknit_stand_unit, "单位：" + item.getUnit());
            helper.setText(R.id.unknit_stand_price, "单价：" + item.getPrice());
        }
    }

    /**
     * 空布局
     */
    public void blankview(int status) {
        //判断集合中是否有数据，如果有数据，隐藏空白布局，
        if (list.size() > 0) {
            emptyView.setVisibility(View.GONE);
        } else {
            //如果列表为空，判断状态是请求成功没数据，还是请求失败没有数据
            switch (status) {
                case 1:
                    //第一次进入界面或者下拉刷新数据请求失败
                    gressBar.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    prompt.setVisibility(View.VISIBLE);
                    prompt.setText("请求失败");
                    break;
                case 3:
                    //第一次进入界面或者下拉刷新数据请求成功
                    gressBar.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    prompt.setVisibility(View.VISIBLE);
                    prompt.setText("暂无数据");
                    break;
                default:
                    break;
            }
        }

    }
}
