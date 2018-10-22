package com.example.zcjlmodule.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zcjlmodule.bean.UnknitstandardBean;
import com.example.zcjlmodule.presenter.UnknitstandardPresenter;
import com.example.zcjlmodule.view.UnknitstandardView;
import com.example.zcmodule.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import measure.jjxx.com.baselibrary.base.BaseMvpActivity;

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
    private TextView prompt;
    private LinearLayout emptyView;
    private ProgressBar gressBar;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unknitstandard);
        mContext = this;
        list = new ArrayList<>();
        mPresenter = new UnknitstandardPresenter();
        mPresenter.mView = this;
        emptyView = (LinearLayout) findViewById(R.id.layout_emptyView);
        //等待的滚动条
        gressBar = (ProgressBar) findViewById(R.id.layout_emptyView_bar);
        //错误提示
        prompt = (TextView) findViewById(R.id.layout_emptyView_text);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        TextView title = (TextView) findViewById(R.id.toolbar_icon_title);
        title.setText("标准分解");
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
        //  下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page=1;
                //传入false表示刷新失败
                mPresenter.getdata();
                refreshlayout.finishRefresh(800);
            }
        });
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                mPresenter.getdata();
                //传入false表示加载失败
                refreshlayout.finishLoadmore(800);
            }
        });
        findViewById(R.id.toolbar_icon_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mPresenter.getdata();
    }

    @Override
    public void getdata(ArrayList<UnknitstandardBean> data) {
        //判断加载页，判断是否删除之前的数据
        if (page == 1) {
            list.clear();
        }
        //将网络请求的数据添加到集合
        list.addAll(data);
        //如果集合的数据大于0，就隐藏空白数据提示
        if (list.size() > 0) {
            emptyView.setVisibility(View.GONE);
        } else {
            //如果不大于0，显示空白页，隐藏等待框，显示提示
            gressBar.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            prompt.setVisibility(View.VISIBLE);
        }
        //更新数据
        mAdapter.setNewData(list);

    }

    public class StandardDecomposeAdapter extends BaseQuickAdapter<UnknitstandardBean, BaseViewHolder> {

        public StandardDecomposeAdapter(@LayoutRes int layoutResId, @Nullable List<UnknitstandardBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, UnknitstandardBean item) {
            helper.setText(R.id.unknit_stand_title, item.getTitle());
            helper.setText(R.id.unknit_stand_compensate, item.getCompensate());
            helper.setText(R.id.unknit_stand_type, item.getType());
            helper.setText(R.id.unknit_stand_region, item.getRegion());
            helper.setText(R.id.unknit_stand_unit, item.getUnit());
            helper.setText(R.id.unknit_stand_price, item.getPrice());
        }
    }
}
