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
import com.example.zcjlmodule.callback.Callback;
import com.example.zcjlmodule.callback.PayDetailCallBackUtils;
import com.example.zcjlmodule.presenter.SdDismantlingPresenter;
import com.example.zcjlmodule.ui.activity.mine.ChangeorganizeZcActivity;
import com.example.zcjlmodule.view.SdDismantlingView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import measure.jjxx.com.baselibrary.base.BaseMvpActivity;
import measure.jjxx.com.baselibrary.utils.SPUtils;
import release.App;

/**
 * description: 征拆标准
 *
 * @author lx
 *         date: 2018/10/19 0019 下午 2:31
 *         update: 2018/10/19 0019
 *         version:
 *         跳转界面 workFragment
 */
public class StandardDismantlingZcActivity extends BaseMvpActivity<SdDismantlingPresenter> implements SdDismantlingView, Callback {
    private SmartRefreshLayout refreshLayout;
    private LinearLayout emptyView;
    private ProgressBar gressBar;
    private TextView prompt;
    private DismantAdapter mAdapter;
    private List<SdDismantlingBean> list;
    private Context mContext;
    private int page = 1;
    private boolean status = true;
    private TextView original_orgname;
    private String orgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_dismantling_zc);
        mContext = this;
        orgId = SPUtils.getString(mContext, "orgId", null);
        list = new ArrayList<>();
        mPresenter = new SdDismantlingPresenter();
        mPresenter.mView = this;
        PayDetailCallBackUtils.setCallBack(this);
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
        original_orgname = (TextView) findViewById(R.id.original_orgname);
        findViewById(R.id.original_org).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChangeorganizeZcActivity.class);
                startActivity(intent);
            }
        });
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
        //当前所在的标段
        original_orgname.setText(SPUtils.getString(mContext, "orgName", ""));
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
                Intent intent = new Intent(mContext, ExamineDismantlingActivity.class);
                SdDismantlingBean bean = list.get(position);
                //id
                intent.putExtra("id", list.get(position).getId());
                //省份
                intent.putExtra("provincename", list.get(position).getProvinceName());
                //城市
                intent.putExtra("cityname", list.get(position).getCityName());
                //区域
                intent.putExtra("countyName", list.get(position).getCountyName());
                //乡镇
                intent.putExtra("townName", list.get(position).getTownName());
                //文件名称
                intent.putExtra("filename", list.get(position).getFilename());
                //文件编号
                intent.putExtra("filenumber", list.get(position).getFilenumber());
                //备注
                intent.putExtra("remarks", list.get(position).getRemarks());
                //发布人
                intent.putExtra("releasor", list.get(position).getReleasor());
                //时间
                intent.putExtra("createDate", list.get(position).getDatatime());
                //标准编号
                intent.putExtra("number", list.get(position).getNumber());
                startActivity(intent);
            }
        });

        //recyclerview的item子项的点击事件处理（查标准分解）
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                int i = view.getId();
                if (i == R.id.see_standard_dismantiling) {
                    Intent intent = new Intent(mContext, UnknitstandardActivity.class);
                    intent.putExtra("filenumber", list.get(position).getFilenumber());
                    intent.putExtra("orgId", orgId);
                    startActivity(intent);
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
                status = true;
                page = 1;
                mPresenter.getdata(orgId, page);

            }
        });
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                status = false;
                mPresenter.getdata(orgId, page);
                //传入false表示加载失败
            }
        });
    }

    /**
     * 请求成功
     */
    @Override
    public void onSuccess(ArrayList<SdDismantlingBean> data) {
        //判断加载页，判断是否删除之前的数据
        if (page == 1) {
            list.clear();
        }
        //将网络请求的数据添加到集合
        list.addAll(data);
        //空白布局
        blankview(3);
        //更新数据
        mAdapter.setNewData(list);
        //判断加载刷新
        if (status) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadmore();
        }
    }

    /**
     * 加载失败
     */
    @Override
    public void onError() {
        //判断加载刷新
        if (status) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadmore();
        }
        //布局显示
        blankview(3);
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
            helper.setText(R.id.standard_dismantiling_title, item.getNumber());
            helper.setText(R.id.standard_dismantiling_date, item.getDatatime());
            helper.setText(R.id.standard_dismantiling_content, item.getFilenumber());
            helper.setText(R.id.standard_dismantiling_filename, "文件名称：" + item.getFilename());
            helper.setText(R.id.standard_dismantiling_region, "行政区域：" + item.getRegion());
        }
    }

    /**
     * 空白布局控制
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
                    gressBar.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    prompt.setVisibility(View.VISIBLE);
                    prompt.setText("暂无数据");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 切换组织
     *
     * @param map
     */
    @Override
    public void callback(Map<String, Object> map) {
        //显示组织名称
        original_orgname.setText(map.get("orgname") + "");
        //变更组织ID
        orgId = (String) map.get("orgId");
        status = true;
        page = 1;
        mPresenter.getdata(orgId, page);
    }
}
