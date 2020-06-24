package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.adapter.NewExternalCheckAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.adapter.NewExternalCheckGridAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.utils.DrawableUtils;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.utils.GridLayoutItemDecoration;
import com.example.administrator.newsdf.pzgc.view.DKDragView;
import com.example.baselibrary.base.BaseActivity;

import java.util.ArrayList;

/**
 * 说明：外业检查：新增检查项
 * 创建时间： 2020/6/23 0023 16:43
 *
 * @author winelx
 */
public class NewExternalCheckActiviy extends BaseActivity implements View.OnClickListener {
    private RecyclerView recyclerview;
    private RecyclerView drawableRecycler;
    private DKDragView dkDragView;
    private TextView comTitle, comButton, commit;
    private DrawerLayout drawerLayout;
    private NewExternalCheckAdapter adapter;
    private NewExternalCheckGridAdapter gridAdapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_new_externalcheck);
        mContext = this;
        findViewById(R.id.toolbar_menu).setOnClickListener(this);
        findViewById(R.id.com_back).setOnClickListener(this);
        comTitle = findViewById(R.id.com_title);
        commit = findViewById(R.id.commit);
        commit.setOnClickListener(this);
        comButton = findViewById(R.id.com_button);
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
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("");
        }
        adapter = new NewExternalCheckAdapter(R.layout.adapter_new_externalcheck, list);
        recyclerview.setAdapter(adapter);
        drawableRecycler = findViewById(R.id.drawable_recycler);
        drawableRecycler.setLayoutManager(new GridLayoutManager(mContext, 5));
        gridAdapter = new NewExternalCheckGridAdapter(R.layout.check_new_grid_item, list);
        drawableRecycler.addItemDecoration(new GridLayoutItemDecoration(this, R.drawable.item_divider));
        drawableRecycler.setAdapter(gridAdapter);
        Intent intent = getIntent();
        boolean status = intent.getBooleanExtra("status", false);
        if (status) {
            comTitle.setText("新增检查");
            comButton.setText("保存");
        } else {
            comTitle.setText("外业检查");
            comButton.setText("编辑");
        }
        gridAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                drawerLayout.closeDrawers();
                Intent intent1 = new Intent(mContext, ExternalCheckDetailActivity.class);
                intent1.putExtra("page", position);
                startActivity(intent1);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_menu:
                break;
            case R.id.commit:
                Intent intent1 = new Intent(mContext, ExternalCheckDetailActivity.class);
                intent1.putExtra("page", 0);
                startActivity(intent1);
                break;
            case R.id.com_back:
                finish();
                break;
            default:
                break;
        }
    }
}
