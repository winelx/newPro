package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.CheckType;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.Enum;
import com.example.baselibrary.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明：选择检查类型
 * 创建时间： 2020/6/30 0030 16:16
 *
 * @author winelx
 */
public class ExternalCheckTypeActivity extends BaseActivity {
    private ImageView listAdd;
    private TextView title;
    private SmartRefreshLayout refreshlayout;
    private RecyclerView recyclerView;
    private TypeAdapter typeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_check_external_list);
        listAdd = findViewById(R.id.list_add);
        listAdd.setVisibility(View.GONE);
        title = findViewById(R.id.com_title);
        title.setText("选择检查类型");
        refreshlayout = findViewById(R.id.refreshlayout);
        //是否启用下拉刷新功能
        refreshlayout.setEnableRefresh(false);
        //是否启用上拉加载功能
        refreshlayout.setEnableLoadmore(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshlayout.setEnableOverScrollDrag(true);
        recyclerView = findViewById(R.id.recycler);
        ArrayList<CheckType> lsit = new ArrayList<>();
        lsit.add(new CheckType("安全管理", "8"));
        lsit.add(new CheckType("质量管理", "9"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        typeAdapter = new TypeAdapter(R.layout.taskphoto_item, lsit);
        recyclerView.setAdapter(typeAdapter);
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        typeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CheckType bean = (CheckType) adapter.getData().get(position);
                Intent intent = new Intent();
                intent.putExtra("name", bean.getName());
                intent.putExtra("id", bean.getId());
                setResult(Enum.CHECK_TYPE, intent);
                finish();
            }
        });
    }

    class TypeAdapter extends BaseQuickAdapter<CheckType, BaseViewHolder> {
        public TypeAdapter(int layoutResId, @Nullable List<CheckType> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CheckType item) {
            helper.setText(R.id.pop_task_item, item.getName());
        }
    }
}
