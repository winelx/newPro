package com.example.zcjlmodule.ui.activity.dismantling;

import android.annotation.TargetApi;
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
import com.example.zcjlmodule.bean.SdDismantlingBean;
import com.example.zcjlmodule.presenter.SdDismantlingPresenter;
import com.example.zcjlmodule.view.SdDismantlingView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import measure.jjxx.com.baselibrary.base.BaseMvpActivity;
import measure.jjxx.com.baselibrary.utils.SPUtils;

/**
 * description: 征拆标准
 *
 * @author lx
 *         date: 2018/10/19 0019 下午 2:31
 *         update: 2018/10/19 0019
 *         version:
 *         跳转界面 workFragment
 */
public class StandardDismantlingZcActivity extends BaseMvpActivity<SdDismantlingPresenter> implements SdDismantlingView {
    private SmartRefreshLayout refreshLayout;
    private LinearLayout emptyView;
    private ProgressBar gressBar;
    private TextView prompt;

    private DismantAdapter mAdapter;

    private List<SdDismantlingBean> list;
    private Context mContext;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_dismantling_zc);
        mContext = this;
        list = new ArrayList<>();
        mPresenter = new SdDismantlingPresenter();
        mPresenter.mView = this;
        init();
        recycler();
        refresh();
        //网络请求
        mPresenter.getdata(SPUtils.getString(mContext, "orgId", null), page);
    }

    /**
     * /初始化
     */
    private void init() {
        findViewById(R.id.toolbar_icon_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        emptyView = (LinearLayout) findViewById(R.id.layout_emptyView);
        //等待的滚动条
        gressBar = (ProgressBar) findViewById(R.id.layout_emptyView_bar);
        //错误提示
        prompt = (TextView) findViewById(R.id.layout_emptyView_text);
        TextView title = (TextView) findViewById(R.id.toolbar_icon_title);
        title.setText("征拆标准");
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.original_refreshlayout);
        //是否启用列表惯性滑动到底部时自动加载更多
        refreshLayout.setEnableAutoLoadmore(false);
    }

    /**
     * /初始化recyclerview
     */
    private void recycler() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.dismantling_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter = new DismantAdapter(R.layout.adapter_standard_dismantling_zc, list));


        //recyclerview的item 的点击事件
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, ExamineDismantlingActivity.class));
            }
        });

        //recyclerview的item子项的点击事件处理
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                int i = view.getId();
                if (i == R.id.see_standard_dismantiling) {
                    startActivity(new Intent(mContext, UnknitstandardActivity.class));
                } else {
                }
            }
        });
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
                //空白布局
                blankview(3);
                page = 1;
                mPresenter.getdata(SPUtils.getString(mContext, "orgId", null), page);

            }
        });
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                mPresenter.getdata(SPUtils.getString(mContext, "orgId", null), page);
                //传入false表示加载失败
            }
        });
    }

    /**
     * @param data
     */
    @Override
    public void getdata(ArrayList<SdDismantlingBean> data) {
        //判断加载页，判断是否删除之前的数据
        if (page == 1) {
            list.clear();
        }
        //空白布局
        blankview(3);
        //将网络请求的数据添加到集合
        list.addAll(data);
        //更新数据
        mAdapter.setNewData(list);
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
    }

    /**
     * 加载失败
     */

    @Override
    public void onError() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        blankview(1);
    }

    /**
     * recyclerview 的适配器
     */
    class DismantAdapter extends BaseQuickAdapter<SdDismantlingBean, BaseViewHolder> {
        public DismantAdapter(@LayoutRes int layoutResId, @Nullable List<SdDismantlingBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, SdDismantlingBean item) {
            helper.addOnClickListener(R.id.see_standard_dismantiling);
            helper.setText(R.id.standard_dismantiling_title, item.getTitle());
            helper.setText(R.id.standard_dismantiling_date, item.getDatatime());
            helper.setText(R.id.standard_dismantiling_content, item.getContent());
            helper.setText(R.id.standard_dismantiling_filename, item.getFilename());
            helper.setText(R.id.standard_dismantiling_region, item.getRegion());
        }
    }

    /**
     * 空白布局
     *
     * @param status
     */
    public void blankview(int status) {
        switch (status) {
            case 1:
                //请求失败，
                if (list.size() > 0) {
                    emptyView.setVisibility(View.GONE);
                } else {
                    //如果不大于0，显示空白页，隐藏等待框，显示提示
                    gressBar.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    prompt.setVisibility(View.VISIBLE);
                    prompt.setText("请求失败");
                }
                break;
            case 2:
                //没有更多数据
                if (list.size() > 0) {
                    emptyView.setVisibility(View.GONE);
                }
                break;
            case 3:
                //空数据下拉刷新
                if (list.size() > 0) {
                    emptyView.setVisibility(View.GONE);
                } else {
                    gressBar.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.VISIBLE);
                    prompt.setVisibility(View.GONE);

                }
            default:
                break;
        }
    }
}
