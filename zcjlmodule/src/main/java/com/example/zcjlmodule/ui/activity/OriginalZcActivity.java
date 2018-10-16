package com.example.zcjlmodule.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zcjlmodule.bean.OriginalZcBean;
import com.example.zcjlmodule.presenter.OriginalPresenter;
import com.example.zcjlmodule.utils.DialogUtils;
import com.example.zcjlmodule.view.OriginalView;
import com.example.zcmodule.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import measure.jjxx.com.baselibrary.base.BaseMvpActivity;
import measure.jjxx.com.baselibrary.utils.ScreenUtil;
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
public class OriginalZcActivity extends BaseMvpActivity<OriginalPresenter> implements OriginalView, View.OnClickListener {
    private TextView toolbarIconTitle, emptyViewText;
    private RecyclerView originalRecycler;
    private SmartRefreshLayout refreshLayout;
    private LinearLayout toolbarIconMeun, layoutEmptyView;
    private OriginalAdapter mAdapter;
    private ProgressBar emptyViewBar;
    private List<OriginalZcBean> list;
    private TextView moneynumber;
    private Context mContext;
    //根据评论分辨率返回的尺寸
    private float DIMENSION;
    String str = "合计金额：154512748";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original_zc);
        mContext = this;
        list = new ArrayList<>();
        //获取屏幕对比比例1DP=？PX 比例有 1 ，2 ，3 ，4
        DIMENSION = ScreenUtil.getDensity(App.getInstance());
        mPresenter = new OriginalPresenter();
        mPresenter.mView = this;
        for (int i = 0; i < 5; i++) {
            list.add(new OriginalZcBean("0", "MSTJ-01-002", "赫章县高速公路铁路建设指挥部",
                    "第 01 期", "户主名字：集体土地 (1245421.5)", "征拆类别：拆迁管理/拆迁管理/拆迁管理/拆迁管理",
                    "张三", "2018-03-01"));
        }
        moneynumber = (TextView) findViewById(R.id.original_moneynumber);
        SpannableString string = setText(str, str.indexOf("：") + 1);
        moneynumber.setText(string);
        //加载错误提示
        emptyViewText = (TextView) findViewById(R.id.layout_emptyView_text);
        emptyViewBar = (ProgressBar) findViewById(R.id.layout_emptyView_bar);
        layoutEmptyView = (LinearLayout) findViewById(R.id.layout_emptyView);
        //标题
        toolbarIconTitle = (TextView) findViewById(R.id.toolbar_icon_title);
        toolbarIconTitle.setText("原始勘丈表");
        //调加
        findViewById(R.id.original_add).setOnClickListener(this);
        //返回
        findViewById(R.id.toolbar_icon_back).setOnClickListener(this);
        //menu 状态处理
        toolbarIconMeun = (LinearLayout) findViewById(R.id.toolbar_icon_meun);
        toolbarIconMeun.setVisibility(View.VISIBLE);
        toolbarIconMeun.setOnClickListener(this);
        //列表
        originalRecycler = (RecyclerView) findViewById(R.id.original_recycler);
        //刷新加载
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.original_refreshlayout);
        //设置控件显示样式
        originalRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        //调加适配器，初始化布局和数据
        originalRecycler.setAdapter(mAdapter = new OriginalZcActivity.OriginalAdapter(R.layout.adapter_original_zc, list));
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        if (list.size() > 0) {
            layoutEmptyView.setVisibility(View.GONE);
        }
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
                //传入false表示刷新失败
                refreshlayout.finishRefresh(800);
            }
        });
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //传入false表示加载失败
                refreshlayout.finishLoadmore(800);
            }
        });

    }


    //点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_icon_back:
                finish();
                break;
            case R.id.original_add:
                startActivity(new Intent(mContext, NewAddOriginalZcActivity.class));
                break;
            case R.id.toolbar_icon_meun:
                //menu
                DialogUtils.meunPop(OriginalZcActivity.this, toolbarIconMeun, DIMENSION, new DialogUtils.onclick() {
                    @Override
                    public void Openonclick(String string) {
                        if ("征拆类型查询".equals(string)) {
                            DialogUtils.dismantling(mContext, new DialogUtils.onclick() {
                                @Override
                                public void Openonclick(String string) {
                                    ToastUtlis.getInstance().showShortToast(string);
                                }
                            });
                        } else if ("按区域查询".equals(string)) {

                        } else if ("按表单查询".equals(string)) {
                            ToastUtlis.getInstance().showShortToast("按表单查询");

                        } else if ("按户主明细查询".equals(string)) {
                            ToastUtlis.getInstance().showShortToast("按户主明细查询");

                        } else if ("按期数查询".equals(string)) {
                            Intent intent = new Intent(mContext, PeriodsQueryZcActivity.class);
                            startActivity(intent);
                        } else {

                        }
                    }
                });
                break;
            default:
                break;
        }
    }


    //recyclerview适配器
    public class OriginalAdapter extends BaseQuickAdapter<OriginalZcBean, BaseViewHolder> {
        public OriginalAdapter(int layoutResId, List data) {
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
            SpannableString string = setText(item.getNamecontent(), item.getNamecontent().indexOf("("));
            helper.setText(R.id.original_adapter_namecontent, string);
            //类别
            helper.setText(R.id.original_adapter_category, item.getCategory());
            //创建人
            helper.setText(R.id.original_adapter_createname, "创建人：" + item.getCreateName());
            //创建时间
            helper.setText(R.id.original_adapter_createdate, "创建时间：" + item.getCreatedata());
        }
    }

    //处理数据
    @Override
    public void getData() {

    }

    /**
     * 设置有颜色文字
     */
    public SpannableString setText(String str, int num) {
        SpannableString sp = new SpannableString(str);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(R.color.red)), num,
                str.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }

}
