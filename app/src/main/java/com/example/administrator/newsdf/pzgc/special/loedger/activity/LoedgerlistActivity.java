package com.example.administrator.newsdf.pzgc.special.loedger.activity;

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
import com.example.administrator.newsdf.pzgc.special.loedger.adapter.LoedgerlistAdapter;
import com.example.administrator.newsdf.pzgc.special.loedger.bean.LoedgerListbean;
import com.example.administrator.newsdf.pzgc.special.loedger.model.LoedgerlistModel;
import com.example.administrator.newsdf.pzgc.utils.EmptyUtils;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.view.EmptyRecyclerView;
import com.example.baselibrary.view.PullDownMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lx
 * @创建时间 2019/7/31 0031 13:47
 * @说明 专项施工台账列表
 * @see LoedgerlistModel
 **/
@SuppressLint("Registered")
public class LoedgerlistActivity extends BaseActivity implements View.OnClickListener {
    private LoedgerlistAdapter mAdapter;
    private PullDownMenu pullDownMenu;
    private EmptyUtils emptyUtils;
    private Context mContext;

    private LoedgerlistModel loedgerlistModel;
    private Observer<List<LoedgerListbean>> observer;

    private EmptyRecyclerView recyclerList;
    private ImageView toolbarImage;
    private TextView title;
    private String[] alllist = {"全部", "打回", "审核中", "审核通过"};
    private String[] mylist = {"全部", "已处理", "未处理"};
    private int page = 1;
    private boolean type;
    private String orgId;
    private String choice = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_list);
        mContext = this;
        Intent intent = getIntent();
        orgId = intent.getStringExtra("orgid");
        //判断入口，根据入口判断网络请求（false:全部组织，true：我的组织）
        type = intent.getBooleanExtra("type", false);

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
        mAdapter = new LoedgerlistAdapter(R.layout.adapter_loedger_item, new ArrayList<>());
        mAdapter.setEmptyView(emptyUtils.init());
        recyclerList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent1 = new Intent(mContext, LoedgerDetailsActivity.class);
                    startActivity(intent1);
            }
        });
        loedgerlistModel = ViewModelProviders.of(this).get(LoedgerlistModel.class);
        observer = new Observer<List<LoedgerListbean>>() {
            @Override
            public void onChanged(@Nullable List<LoedgerListbean> strings) {
                if (strings.size() == 0) {
                    emptyUtils.noData("暂无数据");
                }
                mAdapter.setNewData(strings);
            }
        };
        //网络请求
        request(orgId, choice);
        //请求失败的回调
        loedgerlistModel.setRequestError(new LoedgerlistModel.Modelinface() {
            @Override
            public void onerror() {
                if (page == 1) {
                    emptyUtils.noData("请求失败");
                } else {
                    page--;
                }
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
        if (type) {
            pullDownMenu.showPopMeun((Activity) mContext, toolbarImage, mylist);
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
                    case "审核通过":
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
    private void request(String orgid, String choice) {
        if (type) {
            loedgerlistModel.getMyData(choice, orgid, page).observe(this, observer);
        } else {
            loedgerlistModel.getAllData(choice, orgid, page).observe(this, observer);
        }
    }

    public boolean getType() {
        return type;
    }
}

