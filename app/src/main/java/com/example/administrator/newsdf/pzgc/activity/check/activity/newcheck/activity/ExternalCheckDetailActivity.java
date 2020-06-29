package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.adapter.ExternalCheckDetailAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.adapter.NewExternalCheckGridAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.utils.DrawableUtils;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.utils.GridLayoutItemDecoration;
import com.example.administrator.newsdf.pzgc.adapter.PhotoAdapter;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.view.DKDragView;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.utils.rx.LiveDataBus;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

/**
 * 说明：外业检查：单据详情审核页面
 * 创建时间： 2020/6/24 0024 9:57
 *
 * @author winelx
 */
public class ExternalCheckDetailActivity extends BaseActivity implements View.OnClickListener {
    private TextView toolbartext, title, tab_previous, tab_next;
    private DKDragView dkDragView;
    private RecyclerView drawableRecycler, recycler, rec_photo;
    private DrawerLayout drawerLayout;
    private NewExternalCheckGridAdapter gridAdapter;
    private ExternalCheckDetailAdapter detailAdapter;
    private int Page = 0;
    private ArrayList<String> pagelist;
    private SmartRefreshLayout refreshlayout;
    private PhotoAdapter adapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_externalcheck_detail);
        mContext = this;
        pagelist = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            pagelist.add("");
        }
        findViewById(R.id.toolbar_menu).setOnClickListener(this);
        findViewById(R.id.com_back).setOnClickListener(this);
        toolbartext = findViewById(R.id.com_button);
        tab_previous = findViewById(R.id.tab_previous);
        tab_previous.setOnClickListener(this);
        tab_next = findViewById(R.id.tab_next);
        tab_next.setOnClickListener(this);
        title = findViewById(R.id.com_title);
        drawerLayout = findViewById(R.id.drawer_layout);
        DrawableUtils.setDrawLayout(drawerLayout);
        //展示侧拉界面后，背景透明度（当前透明度为完全透明）
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        dkDragView = findViewById(R.id.suspension);
        //设置不允许超过的边界（左上右下）
        dkDragView.setBoundary(0, 130, 0, 230);
        dkDragView.setOnDragViewClickListener(new DKDragView.onDragViewClickListener() {
            @Override
            public void onClick() {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });
        drawableRecycler = findViewById(R.id.drawable_recycler);
        drawableRecycler.setLayoutManager(new GridLayoutManager(this, 5));
        gridAdapter = new NewExternalCheckGridAdapter(R.layout.check_new_grid_item, pagelist);
        drawableRecycler.addItemDecoration(new GridLayoutItemDecoration(this, R.drawable.item_divider));
        drawableRecycler.setAdapter(gridAdapter);
        Intent intent = getIntent();
        Page = intent.getIntExtra("page", 1);
        toolbartext.setText("保存");
        setTitle();
        gridAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Page = position;
                setTitle();
                drawerLayout.closeDrawers();
            }
        });
        refreshlayout = findViewById(R.id.refreshlayout);
        //是否启用下拉刷新功能
        refreshlayout.setEnableRefresh(false);
        //是否启用上拉加载功能
        refreshlayout.setEnableLoadmore(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshlayout.setEnableOverScrollDrag(true);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> lis = new ArrayList<>();
        lis.add("");
        lis.add("");
        lis.add("");
        detailAdapter = new ExternalCheckDetailAdapter(R.layout.adapter_item_external_checkdetail, lis);
        detailAdapter.setHeaderView(getHeaderView());
        detailAdapter.setFooterView(getFooterView());
        recycler.setAdapter(detailAdapter);
        LiveDataBus.get().with("recycler", String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String str) {
                        recycler.smoothScrollToPosition(Integer.parseInt(str));
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_menu:
                //保存
                break;
            case R.id.tab_next:
                if (Page != pagelist.size()-1) {
                    Page++;
                    setTitle();
                } else {
                    ToastUtils.showShortToast("已经是最后一项了");
                }
                break;
            case R.id.tab_previous:
                if (Page >0) {
                    Page--;
                    setTitle();
                } else {
                    ToastUtils.showShortToast("已经是第一项了");
                }
                break;
            case R.id.com_back:
                finish();
                break;
            default:
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    public void setTitle() {
        title.setText((Page + 1) + "/" + pagelist.size());
        if (Page == 0) {
            tab_previous.setBackgroundResource(R.drawable.tab_choose_up_gray);
        } else {
            tab_previous.setBackgroundResource(R.drawable.tab_choose_up_blue);
        }
        if (Page == (pagelist.size() - 1)) {
            tab_next.setBackgroundResource(R.drawable.tab_choose_down_gray);
        } else {
            tab_next.setBackgroundResource(R.drawable.tab_choose_down_blue);
        }
    }


    /**
     * 说明：添加尾部布局
     * 创建时间： 2020/6/10 0010 14:16
     *
     * @author winelx
     */
    public View getHeaderView() {
        View headerView = getLayoutInflater().inflate(R.layout.adapter_check_detail_header, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return headerView;
    }

    /**
     * 说明：添加尾部布局
     * 创建时间： 2020/6/10 0010 14:16
     *
     * @author winelx
     */
    public View getFooterView() {
        View footerView = getLayoutInflater().inflate(R.layout.adapter_check_detail_footer, null);
        footerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        rec_photo = footerView.findViewById(R.id.rec_photo);
        rec_photo.setLayoutManager(new GridLayoutManager(this, 4));
        ArrayList<String> photoPaths = new ArrayList<>();
        photoPaths.add("http://img1.gtimg.com/rushidao/pics/hv1/20/108/1744/113431160.jpg");
        photoPaths.add("http://a4.att.hudong.com/47/66/01300000337727123266663353910.jpg");
        adapter = new PhotoAdapter(mContext, photoPaths, "other");
        adapter.setOnItemClickListener(new PhotoAdapter.OnItemClickListener() {
            @Override
            public void addlick(View view, int position) {
                ToastUtils.showShortToast("添加");
            }
        });
        rec_photo.setAdapter(adapter);
        return footerView;
    }
}
