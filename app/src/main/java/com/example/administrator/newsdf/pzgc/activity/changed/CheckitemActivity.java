package com.example.administrator.newsdf.pzgc.activity.changed;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.changed.adapter.CheckitemAdapter;
import com.example.administrator.newsdf.pzgc.bean.Checkitem;
import com.example.administrator.newsdf.pzgc.inter.ItemClickListener;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.LogUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/13 0013}
 * 描述：检查项
 * {@link }
 */
public class CheckitemActivity extends BaseActivity implements View.OnClickListener {
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private TextView inspect_content;
    private CheckitemAdapter mAdapter;
    private ArrayList<Object> list;
    private Context mContext;
    private TextView com_button;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_itemlist);
        mContext = this;
        list = new ArrayList<>();
        findViewById(R.id.com_back).setOnClickListener(this);
        com_button = (TextView) findViewById(R.id.com_button);
        com_button.setOnClickListener(this);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshlayout);
        //是否启用下拉刷新功能
        refreshLayout.setEnableRefresh(true);
        //是否启用上拉加载功能
        refreshLayout.setEnableLoadmore(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableOverScrollDrag(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        list.add("标题");
        list.add(new Checkitem("测试数据", false));
        list.add(new Checkitem("测试数据", false));
        list.add(new Checkitem("测试数据", false));
        list.add("标题");
        list.add(new Checkitem("测试数据", false));
        list.add(new Checkitem("测试数据", false));
        list.add(new Checkitem("测试数据", false));
        list.add("标题");
        list.add(new Checkitem("测试数据", false));
        list.add(new Checkitem("测试数据", false));
        list.add(new Checkitem("测试数据", false));
        mAdapter = new CheckitemAdapter(list);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setItemOnclick(new CheckitemAdapter.ItemClickListener() {
            @Override
            public void Onclick(String str, int position) {
                Checkitem item = (Checkitem) list.get(position);
                item.setLeam(true);
                mAdapter.setNewData(list);
                com_button.setText("确定");
                count++;
                LogUtil.i("count",count);
            }

            @Override
            public void ondelete(String str, int position) {
                Checkitem item = (Checkitem) list.get(position);
                item.setLeam(false);
                mAdapter.setNewData(list);
                count--;
                LogUtil.i("count",count);
                if (count == 0) {
                    com_button.setText("");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                finish();
                break;
            case R.id.com_button:
                ArrayList<String> data = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    Object obj = list.get(i);
                    if (obj instanceof Checkitem) {
                        Checkitem item = (Checkitem) obj;
                        if (item.isLeam()) {
                            data.add("第" + i + "个");
                        }
                    }
                }
                for (int i = 0; i < data.size(); i++) {
                    LogUtil.i("选择数据", data.get(i));
                }
                break;
            default:
                break;
        }
    }
}
