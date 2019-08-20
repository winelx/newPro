package com.example.administrator.newsdf.pzgc.special.programme.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.special.programme.adapter.ProgrammeListAdapter;
import com.example.administrator.newsdf.pzgc.special.programme.bean.ProAllListBean;
import com.example.administrator.newsdf.pzgc.special.programme.bean.ProMineListBean;
import com.example.administrator.newsdf.pzgc.special.programme.model.ProgrammeListModel;
import com.example.administrator.newsdf.pzgc.utils.EmptyUtils;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.bean.bean;
import com.example.baselibrary.utils.rx.LiveDataBus;
import com.example.baselibrary.view.EmptyRecyclerView;
import com.example.baselibrary.view.PullDownMenu;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lx
 * @创建时间 2019/8/1 0001 13:42
 * @说明 专项施工方案管理list
 **/
@SuppressLint("Registered")
public class ProgrammeListActivity extends BaseActivity implements View.OnClickListener {
    private EmptyRecyclerView recyclerList;
    private ImageView toolbarImage;
    private TextView title;
    private SmartRefreshLayout refreshLayout;

    private Context mContext;
    private PullDownMenu pullDownMenu;
    private EmptyUtils emptyUtils;
    private ProgrammeListAdapter mAdapter;
    private ProgrammeListModel programmeListModel;
    private Observer<List<Object>> observer;

    private String[] alllist = {"全部", "打回", "审核中", "完成"};
    private String[] minelist = {"全部", "未处理", "已处理"};
    private int page = 1;
    private String orgId;
    private boolean type;
    private String choice = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_list);
        mContext = this;
        Intent intent = getIntent();
        //当前组织id
        orgId = intent.getStringExtra("orgid");
        //当前跳转界面（false：全部组织，true：我的组织）
        type = intent.getBooleanExtra("type", false);
        emptyUtils = new EmptyUtils(mContext);
        findViewById(R.id.com_back).setOnClickListener(this);
        findViewById(R.id.toolbar_menu).setOnClickListener(this);
        toolbarImage = findViewById(R.id.com_img);
        refreshLayout = findViewById(R.id.refreshlayout);
        toolbarImage.setImageResource(R.mipmap.meun);
        toolbarImage.setVisibility(View.VISIBLE);
        title = findViewById(R.id.com_title);
        title.setText("专项施工方案管理");
        recyclerList = findViewById(R.id.recycler_list);
        recyclerList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ProgrammeListAdapter(R.layout.adapter_loedger_item, new ArrayList<>());
        mAdapter.setEmptyView(emptyUtils.init());
        mAdapter.openLoadAnimation();
        recyclerList.setAdapter(mAdapter);
        programmeListModel = ViewModelProviders.of(this).get(ProgrammeListModel.class);
        observer = new Observer<List<Object>>() {
            @Override
            public void onChanged(@Nullable List<Object> strings) {
                if (strings.size() == 0) {
                    emptyUtils.noData("暂无数据");
                }
                mAdapter.setNewData(strings);
            }
        };

        //请求失败的回调
        programmeListModel.setRequestError(new ProgrammeListModel.Modelinface() {
            @Override
            public void onerror() {
                if (page == 1) {
                    emptyUtils.noData("请求失败");
                } else {
                    page--;
                }
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent1 = new Intent(mContext, ProgrammeDetailsActivity.class);
                if (type) {
                    //我的
                    ProMineListBean bean = (ProMineListBean) adapter.getData().get(position);
                    intent1.putExtra("id", bean.getId());
                    intent1.putExtra("taskid", bean.getTaskId());
                    intent1.putExtra("orgid", orgId);
                } else {
                    //全部
                    ProAllListBean bean = (ProAllListBean) adapter.getData().get(position);
                    intent1.putExtra("id", bean.getId());
                    intent1.putExtra("taskid", bean.getTaskId());
                    intent1.putExtra("orgid", orgId);
                }
                startActivity(intent1);
            }
        });
        LiveDataBus.get().with("prolist", String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        page = 1;
                        request(orgId, choice);
                    }
                });
        /**
         *   下拉刷新
         */
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                //网络请求
                request(orgId, choice);
                refreshlayout.finishRefresh();
            }
        });
        /**
         * 上拉加载
         */
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                //网络请求
                request(orgId, choice);
                refreshlayout.finishLoadmore();
            }
        });
        //网络请求
        request(orgId, choice);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_menu:
                talbarMenu();
                break;
            case R.id.com_back:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * @Author lx
     * @创建时间 2019/7/31 0031 13:41
     * @说明 menu下拉选择项
     **/
    private void talbarMenu() {
        pullDownMenu = new PullDownMenu();
        if (type) {
            pullDownMenu.showPopMeun((Activity) mContext, toolbarImage, minelist);
        } else {
            pullDownMenu.showPopMeun((Activity) mContext, toolbarImage, alllist);
        }
        pullDownMenu.setOnItemClickListener(new PullDownMenu.OnItemClickListener() {
            @Override
            public void onclick(int position, String string) {
                page = 1;
                switch (string) {
                    case "全部":
                        choice = "";
                        break;
                    case "打回":
                        choice = "3";
                        break;
                    case "审核中":
                        choice = "2";
                        break;
                    case "完成":
                        choice = "4";
                        break;
                    case "已处理":
                        choice = "2";
                        break;
                    case "未处理":
                        choice = "1";
                        break;
                    default:
                        break;
                }
                request(orgId, choice);
            }
        });
    }

    /**
     * @Author lx
     * @创建时间 2019/7/31 0031 14:14
     * @说明 网络请求
     **/
    private void request(String id, String choice) {
        if (type) {
            programmeListModel.getMySpecialitemproject(id, page, choice).observe(this, observer);
        } else {
            programmeListModel.getSpecialitemproject(id, page, choice).observe(this, observer);

        }
    }

    public boolean getType() {
        return type;
    }
}
