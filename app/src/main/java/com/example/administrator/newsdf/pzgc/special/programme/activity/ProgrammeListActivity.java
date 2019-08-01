package com.example.administrator.newsdf.pzgc.special.programme.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.special.loedger.activity.LoedgerDetailsActivity;
import com.example.administrator.newsdf.pzgc.special.programme.adapter.ProgrammeListAdapter;
import com.example.administrator.newsdf.pzgc.special.programme.model.ProgrammeListModel;
import com.example.administrator.newsdf.pzgc.utils.EmptyUtils;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.view.EmptyRecyclerView;
import com.example.baselibrary.view.PullDownMenu;

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

    private Context mContext;
    private PullDownMenu pullDownMenu;
    private EmptyUtils emptyUtils;
    private ProgrammeListAdapter mAdapter;
    private ProgrammeListModel programmeListModel;
    private Observer<List<String>> observer;

    private String[] strings = {"全部", "打回", "审核中", "完成"};
    private int page = 1;
    private String orgId;
    private String choice = "全部";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_list);
        mContext = this;
        Intent intent = getIntent();
        orgId = intent.getStringExtra("orgid");
        emptyUtils = new EmptyUtils(mContext);
        findViewById(R.id.com_back).setOnClickListener(this);
        findViewById(R.id.toolbar_menu).setOnClickListener(this);
        toolbarImage = findViewById(R.id.com_img);
        toolbarImage.setImageResource(R.mipmap.meun);
        toolbarImage.setVisibility(View.VISIBLE);
        title = findViewById(R.id.com_title);
        title.setText(intent.getStringExtra("orgName"));
        recyclerList = findViewById(R.id.recycler_list);
        recyclerList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ProgrammeListAdapter(R.layout.adapter_loedger_item, new ArrayList<>());
        mAdapter.setEmptyView(emptyUtils.init());
        mAdapter.openLoadAnimation();
        recyclerList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, LoedgerDetailsActivity.class));
            }
        });
        programmeListModel = ViewModelProviders.of(this).get(ProgrammeListModel.class);
        observer = new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                if (strings.size() == 0) {
                    emptyUtils.noData("暂无数据");
                }
                mAdapter.setNewData(strings);
            }
        };
        //网络请求
        request(orgId, choice);
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
                startActivity(new Intent(mContext, ProgrammeDetailsActivity.class));
            }
        });
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
        pullDownMenu.showPopMeun((Activity) mContext, toolbarImage, strings);
        pullDownMenu.setOnItemClickListener(new PullDownMenu.OnItemClickListener() {
            @Override
            public void onclick(int position, String string) {
                page = 1;
                switch (string) {
                    case "全部":
                        choice = "全部";
                        break;
                    case "打回":
                        choice = "打回";
                        break;
                    case "审核中":
                        choice = "审核中";
                        break;
                    case "审核通过":
                        choice = "审核通过";
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
        programmeListModel.getData().observe(this, observer);
    }
}
