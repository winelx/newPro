package com.example.zcjlmodule.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zcjlmodule.presenter.OriginalPresenter;
import com.example.zcjlmodule.view.OriginalView;
import com.example.zcmodule.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import measure.jjxx.com.baselibrary.base.BaseMvpActivity;
import measure.jjxx.com.baselibrary.utils.DatesUtils;
import measure.jjxx.com.baselibrary.utils.ScreenUtil;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;
import release.App;

/**
 * description: 原始勘丈表
 *
 * @author lx
 *         date: 2018/10/15 0015 下午 3:37
 *         update: 2018/10/15 0015
 *         version:
 */
public class OriginalZcActivity extends BaseMvpActivity<OriginalPresenter> implements OriginalView, View.OnClickListener {
    private TextView toolbarIconTitle, emptyViewText;
    private RecyclerView originalRecycler;
    private SmartRefreshLayout refreshLayout;
    private LinearLayout toolbarIconMeun, layoutEmptyView;
    private OriginalAdapter mAdapter;
    private ProgressBar emptyViewBar;
    private List<String> list;
    private Context mContext;
    private PopupWindow mPopupWindow;
    //根据评论分辨率返回的尺寸
    private float ste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original_zc);
        mContext = this;
        list = new ArrayList<>();
        //获取屏幕对比比例1DP=？PX 比例有 1 ，2 ，3 ，4
        ste = ScreenUtil.getDensity(App.getInstance());
        mPresenter = new OriginalPresenter();
        mPresenter.mView = this;
        for (int i = 0; i < 10; i++) {
            list.add("1");
        }
        //加载错误提示
        emptyViewText = (TextView) findViewById(R.id.layout_emptyView_text);
        emptyViewBar = (ProgressBar) findViewById(R.id.layout_emptyView_bar);
        layoutEmptyView = (LinearLayout) findViewById(R.id.layout_emptyView);
        //标题
        toolbarIconTitle = (TextView) findViewById(R.id.toolbar_icon_title);
        toolbarIconTitle.setText("原始勘丈表");
        //返回
        findViewById(R.id.toolbar_icon_back).setOnClickListener(this);
        //menu
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
            case R.id.toolbar_icon_meun:
                MeunPop();
                break;
            default:
                break;
        }
    }


    //recyclerview适配器
    public class OriginalAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public OriginalAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }

    //处理数据
    @Override
    public void getData() {

    }

    //任务状态弹出窗
    private void MeunPop() {
        View contentView = getPopupWindowContentView();
        mPopupWindow = new PopupWindow(contentView,
                DatesUtils.withFontSize(ste) + 20, DatesUtils.higtFontSize(ste), true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置好参数之后再show
        // 默认在mButton2的左下角显示
        mPopupWindow.showAsDropDown(toolbarIconMeun);
        backgroundAlpha(0.5f);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());
    }

    //设置pop的点击事件
    private View getPopupWindowContentView() {
        // 一个自定义的布局，作为显示的内容
        // 布局ID
        int layoutId = R.layout.activity_original_pop_zc;
        View contentView = LayoutInflater.from(this).inflate(layoutId, null);
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.pop_dismantling_zc:
                        ToastUtlis.getInstance().showShortToast("征拆类型查询");
                        break;
                    case R.id.pop_region_zc:
                        ToastUtlis.getInstance().showShortToast("按区域查询");
                        break;
                    case R.id.pop_form_zc:
                        ToastUtlis.getInstance().showShortToast("按表单查询");
                        break;
                    case R.id.pop_details_zc:
                        ToastUtlis.getInstance().showShortToast("按户主明细查询");
                        break;
                    case R.id.pop_data_zc:
                        ToastUtlis.getInstance().showShortToast("按期数查询");
                        break;
                    default:
                        break;
                }
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        };
        contentView.findViewById(R.id.pop_dismantling_zc).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_region_zc).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_form_zc).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_details_zc).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_data_zc).setOnClickListener(menuItemOnClickListener);
        return contentView;
    }

    //界面亮度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    /**
     * popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }


    
}
