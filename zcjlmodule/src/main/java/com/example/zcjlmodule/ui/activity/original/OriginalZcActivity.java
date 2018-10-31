package com.example.zcjlmodule.ui.activity.original;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zcjlmodule.R;
import com.example.zcjlmodule.bean.OriginalZcBean;
import com.example.zcjlmodule.callback.Callback;
import com.example.zcjlmodule.callback.PayDetailCallBackUtils;
import com.example.zcjlmodule.presenter.OriginalPresenter;
import com.example.zcjlmodule.ui.activity.mine.UserOrgZcActivity;
import com.example.zcjlmodule.utils.DialogUtils;
import com.example.zcjlmodule.view.OriginalView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import measure.jjxx.com.baselibrary.base.BaseMvpActivity;
import measure.jjxx.com.baselibrary.utils.SPUtils;
import measure.jjxx.com.baselibrary.utils.ScreenUtil;
import measure.jjxx.com.baselibrary.utils.TextUtils;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;
import release.App;

/**
 * description: 原始勘丈表
 *
 * @author lx
 *         date: 2018/10/15 0015 下午 3:37
 *         update: 2018/10/15 0015
 *         跳转界面 WorkFragment
 */
public class OriginalZcActivity extends BaseMvpActivity<OriginalPresenter> implements OriginalView, View.OnClickListener, Callback {
    private LinearLayout toolbarIconMeun, layoutEmptyView;
    private TextView toolbarIconTitle, emptyViewText;
    private TextView moneynumber, originalOrgname;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView originalRecycler;
    private OriginalAdapter mAdapter;
    private ProgressBar emptyViewBar;

    private Context mContext;
    private List<OriginalZcBean> list;
    private String str = "合计金额：154512748";

    //页数
    private int page = 1;
    private boolean status = true;
    //根据手机分辨率返回的尺寸
    private float dimension;
    //查询类型
    private static final String TYPE_ONE = "征拆类型查询";
    private static final String TYPE_TWO = "按区域查询";
    private static final String TYPE_THREE = "按表单查询";
    private static final String TYPE_FOUR = "按户主明细查询";
    private static final String TYPE_FIVE = "按期数查询";

    private String orgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original_zc);
        mContext = this;
        list = new ArrayList<>();
        PayDetailCallBackUtils.setCallBack(this);
        orgId = SPUtils.getString(App.getInstance(), "orgName", "");
        //获取屏幕对比比例1DP=？PX 比例有 1 ，2 ，3 ，4
        dimension = ScreenUtil.getDensity(App.getInstance());
        mPresenter = new OriginalPresenter();
        mPresenter.mView = this;
        //调加
        findViewById(R.id.original_add).setOnClickListener(this);
        //返回
        findViewById(R.id.toolbar_icon_back).setOnClickListener(this);
        //组织机构
        findViewById(R.id.original_org).setOnClickListener(this);
        originalOrgname = (TextView) findViewById(R.id.original_orgname);
        originalOrgname.setText(SPUtils.getString(App.getInstance(), "orgName", ""));
        //合计金额
        moneynumber = (TextView) findViewById(R.id.original_moneynumber);
        moneynumber.setText(TextUtils.setText(mContext, str, str.indexOf("：") + 1));
        //加载错误提示
        emptyViewText = (TextView) findViewById(R.id.layout_emptyView_text);
        emptyViewBar = (ProgressBar) findViewById(R.id.layout_emptyView_bar);
        layoutEmptyView = (LinearLayout) findViewById(R.id.layout_emptyView);
        //标题
        toolbarIconTitle = (TextView) findViewById(R.id.toolbar_icon_title);
        toolbarIconTitle.setText("原始勘丈表");
        //menu 状态处理
        toolbarIconMeun = (LinearLayout) findViewById(R.id.toolbar_icon_meun);
        toolbarIconMeun.setVisibility(View.VISIBLE);
        toolbarIconMeun.setOnClickListener(this);
        //刷新加载
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.original_refreshlayout);
        //是否启用列表惯性滑动到底部时自动加载更多
        refreshLayout.setEnableAutoLoadmore(false);
        recycler();
        refresh();
        //网络请求
        mPresenter.getdata(orgId, page);

    }

    /**
     * recyclerView
     */
    private void recycler() {
        //列表
        originalRecycler = (RecyclerView) findViewById(R.id.original_recycler);
        //设置控件显示样式
        originalRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        //调加适配器，初始化布局和数据
        originalRecycler.setAdapter(mAdapter = new OriginalAdapter(R.layout.adapter_original_zc, list));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    /**
     * 加载刷新
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
     * 点击事件
     */
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.toolbar_icon_back) {
            finish();
        } else if (i == R.id.original_org) {
            Intent intent = new Intent(mContext, UserOrgZcActivity.class);
            intent.putExtra("status", 1);
            startActivity(intent);
        } else if (i == R.id.original_add) {
            startActivity(new Intent(mContext, NewAddOriginalZcActivity.class));
        } else if (i == R.id.toolbar_icon_meun) {
            //menu
            DialogUtils.meunPop(OriginalZcActivity.this, toolbarIconMeun, dimension, new DialogUtils.onclick() {
                @Override
                public void openonclick(String string) {
                    if (TYPE_ONE.equals(string)) {
                        DialogUtils.dismantling(mContext, new DialogUtils.onclick() {
                            @Override
                            public void openonclick(String string) {
                                ToastUtlis.getInstance().showShortToast(string);
                            }
                        });
                    } else if (TYPE_TWO.equals(string)) {
                        ToastUtlis.getInstance().showShortToast("按区域查询");
                    } else if (TYPE_THREE.equals(string)) {
                        ToastUtlis.getInstance().showShortToast("按表单查询");
                    } else if (TYPE_FOUR.equals(string)) {
                        ToastUtlis.getInstance().showShortToast("按户主明细查询");
                    } else if (TYPE_FIVE.equals(string)) {
                        Intent intent = new Intent(mContext, PeriodsQueryZcActivity.class);
                        startActivity(intent);
                    } else {
                    }
                }
            });
        } else {
        }
    }

    /**
     * 请求成功
     *
     * @param data
     */
    @Override
    public void onSuccess(ArrayList<OriginalZcBean> data) {
        if (page == 1) {
            list.clear();
        }
        list.addAll(data);
        if (list.size() > 0) {
            //list大于0，隐藏空白提示布局
            layoutEmptyView.setVisibility(View.GONE);
        } else {
            //list小于0，显示空白提示布局，隐藏等待框
            emptyViewText.setVisibility(View.VISIBLE);
            layoutEmptyView.setVisibility(View.VISIBLE);
            emptyViewBar.setVisibility(View.GONE);
        }
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
    public void onError() {
        if (list.size() > 0) {
            //list大于0，隐藏空白提示布局
            layoutEmptyView.setVisibility(View.GONE);
        } else {
            //list小于0，显示空白提示布局，隐藏等待框
            emptyViewText.setVisibility(View.VISIBLE);
            layoutEmptyView.setVisibility(View.VISIBLE);
            emptyViewBar.setVisibility(View.GONE);
            emptyViewText.setText("请求失败");
        }
        //刷新加载关闭
        if (status) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadmore();
        }
    }

    /**
     * 切换组织
     *
     * @param map
     */
    @Override
    public void callback(Map<String, Object> map) {
        originalOrgname.setText(map.get("orgName") + "");
        orgId = (String) map.get("orgId");
        httprequest(true);
    }

    /**
     * recyclerview适配器
     */
    public class OriginalAdapter extends BaseQuickAdapter<OriginalZcBean, BaseViewHolder> {
        OriginalAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, OriginalZcBean item) {
            //标题
            helper.setText(R.id.original_adapter_title, item.getTitile());
            //期数
            helper.setText(R.id.original_adapter_data, item.getDatanumber());
            //内容
            helper.setText(R.id.original_adapter_content, item.getContent());
            //户主名称
            String str = item.getNamecontent() + "(" + item.getTotalPrice() + ")";
            SpannableString string = TextUtils.setText(mContext, "户主名称：" + str, item.getNamecontent().indexOf("("));
            helper.setText(R.id.original_adapter_namecontent, str);
            //类别
            helper.setText(R.id.original_adapter_category, "征拆类别：" + item.getCategory());
            //创建人
            helper.setText(R.id.original_adapter_createname, "创建人：" + item.getCreateName());
            //创建时间
            helper.setText(R.id.original_adapter_createdate, "创建时间：" + item.getCreatedata());
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
        mPresenter.getdata(orgId, page);
    }

}
