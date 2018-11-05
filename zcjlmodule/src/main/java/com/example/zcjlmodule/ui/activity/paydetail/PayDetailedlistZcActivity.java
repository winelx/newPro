package com.example.zcjlmodule.ui.activity.paydetail;

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
import com.example.zcjlmodule.bean.PayDetailedlistBean;
import com.example.zcjlmodule.callback.Callback;
import com.example.zcjlmodule.callback.PayDetailCallBackUtils;
import com.example.zcjlmodule.presenter.DetailedlistPresenter;
import com.example.zcjlmodule.ui.activity.mine.ChangeorganizeZcActivity;
import com.example.zcjlmodule.view.DetailedlistView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import measure.jjxx.com.baselibrary.base.BaseMvpActivity;
import measure.jjxx.com.baselibrary.utils.SPUtils;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;
import release.App;

/**
 * description: 支付清册
 * 这个界面使用recycler作为展示控件，SmartRefreshLayout为刷新加载控件，使用mvp模式来处理数据，减少界面的代码量。
 * （从 WorkfragmentZc的WorkFragmentItemAdapter 跳转）
 *
 * @author lx
 *         date: 2018/10/11 0011 下午 3:33
 *         跳转界面：workFragment
 */
public class PayDetailedlistZcActivity extends BaseMvpActivity<DetailedlistPresenter> implements DetailedlistView, View.OnClickListener, Callback {
    //适配器
    private PayDetailedlistZcActivity.RecyclerAdapter mAdapter;
    private List<PayDetailedlistBean> list;
    private Context mContext;
    private LinearLayout emptyView;
    private ProgressBar gressBar;
    private TextView prompt, detailedlistProjectname;
    private SmartRefreshLayout refreshLayout;
    private int page = 1;
    private boolean status = true;
    private String orgId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailedlist_zc);
        mContext = this;
        PayDetailCallBackUtils.setCallBack(this);
        //初始化presenter
        mPresenter = new DetailedlistPresenter();
        //初始化mview
        mPresenter.mView = this;
        orgId=SPUtils.getString(mContext, "orgId", null);
        list = new ArrayList<>();
        findId();
        refresh();
        //请求网络
        mPresenter.register(orgId, (page-1));
        //列表的点击事件
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //跳转支付清册核查，传递支付单id
                Intent intent = new Intent(mContext, PayCheckZcActivity.class);
                intent.putExtra("id", list.get(position).getId());
                intent.putExtra("filenumber", list.get(position).getNumber());
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化控件
     */
    private void findId() {
        findViewById(R.id.detailedlist_project).setOnClickListener(this);
        //返回键初始化并添加点击事件
        findViewById(R.id.toolbar_icon_back).setOnClickListener(this);
        //所属组织名称
        detailedlistProjectname = (TextView) findViewById(R.id.detailedlist_projectname);
        //默认组织名称
        detailedlistProjectname.setText(SPUtils.getString(App.getInstance(), "orgName", ""));
        //刷新加载
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        //是否启用列表惯性滑动到底部时自动加载更多
        refreshLayout.setEnableAutoLoadmore(false);
        //空布局
        emptyView = (LinearLayout) findViewById(R.id.layout_emptyView);
        //进入时界面时显示等待条
        gressBar = (ProgressBar) findViewById(R.id.layout_emptyView_bar);
        //加载失败的提示
        prompt = (TextView) findViewById(R.id.layout_emptyView_text);
        //标题初始化
        TextView textView = (TextView) findViewById(R.id.toolbar_icon_title);
        textView.setText("支付清册");
        //recycler
        RecyclerView recycler = (RecyclerView) findViewById(R.id.detailedlist_recycler);
        //设置控件显示样式
        recycler.setLayoutManager(new LinearLayoutManager(mContext));
        //调加适配器，初始化布局和数据
        recycler.setAdapter(mAdapter = new RecyclerAdapter(R.layout.adapter_detailedlist_activity_ac, list));
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
                httprequest(true);
            }
        });
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                httprequest(false);
            }
        });
    }

    /**
     * description: 点击事件
     *
     * @author lx
     * date: 2018/10/22 0022 下午 2:06
     */
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.toolbar_icon_back) {
            finish();
        } else if (i == R.id.detailedlist_project) {
            Intent intent = new Intent(mContext, ChangeorganizeZcActivity.class);
            startActivity(intent);
        } else {

        }
    }

    /**
     * description: 处理请求返回的数据展示到界面
     *
     * @author lx
     * date: 2018/10/22 0022 下午 2:06
     */
    @Override
    public void getdata(ArrayList<PayDetailedlistBean> str) {

        if (status) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadmore();
        }
        if (page == 1) {
            list.clear();
        }
        //将网络请求的数据添加到list
        list.addAll(str);
        if (list.size() > 0) {
            //如果list大于0，隐藏空白数据提示
            emptyView.setVisibility(View.GONE);
        } else {
            ToastUtlis.getInstance().showShortToast("暂无数据");
            //反之显示空数据提示，隐藏等待框
            emptyView.setVisibility(View.VISIBLE);
            prompt.setVisibility(View.VISIBLE);
            gressBar.setVisibility(View.GONE);
        }
        //更新数据
        mAdapter.setNewData(list);
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
        if (list.size() < 1) {
            emptyView.setVisibility(View.VISIBLE);
            prompt.setVisibility(View.VISIBLE);
            prompt.setText("请求失败");
            gressBar.setVisibility(View.GONE);
        }
        //刷新加载关闭
        if (status) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadmore();
        }

    }


    @Override
    public void callback(Map<String, Object> map) {
        orgId=(String) map.get("orgId");
        detailedlistProjectname.setText( map.get("orgname")+"");
        //请求网络
        httprequest(true);
    }

    /**
     * recyclerview适配器
     */
    public class RecyclerAdapter extends BaseQuickAdapter<PayDetailedlistBean, BaseViewHolder> {
        public RecyclerAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, PayDetailedlistBean item) {
            //标题
            helper.setText(R.id.pay_list_title, item.getTitile());
            //文件编号
            helper.setText(R.id.pay_list_number, item.getNumber());
            //期数
            helper.setText(R.id.pay_list_datanumber, item.getDatetime());
            //文件内容
            helper.setText(R.id.pay_list_content, item.getContent());
            //支付金额
            helper.setText(R.id.pay_list_paymoney, "支付金额：" + item.getPaymoney());
            //已检查金额
            helper.setText(R.id.pay_list_checkmoney, "已检查金额：" + item.getCheckmoney());
            //未检查金额
            helper.setText(R.id.pay_list_nocheckmoney, "未核查金额：" + item.getNocheckmoney());
        }

    }

    /**
     * 请求网络数据处理
     *
     * @param lean
     */
    public void httprequest(boolean lean) {
        if (lean) {
            page = 1;
            //标记刷新还是加载状态
            status = true;
        } else {
            page++;
            //标记刷新还是加载状态
            status = false;
        }
        mPresenter.register(orgId, (page-1));
    }
}
