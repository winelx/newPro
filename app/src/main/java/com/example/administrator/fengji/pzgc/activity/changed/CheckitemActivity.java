package com.example.administrator.fengji.pzgc.activity.changed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.utils.ToastUtils;
import com.example.administrator.fengji.pzgc.activity.changed.adapter.CheckitemAdapter;
import com.example.administrator.fengji.pzgc.bean.Checkitem;
import com.example.baselibrary.base.BaseActivity;
import com.example.administrator.fengji.pzgc.utils.Dates;
import com.example.administrator.fengji.pzgc.utils.EmptyUtils;
import com.example.baselibrary.view.EmptyRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/13 0013}
 * 描述：检查项(多选)
 * {@link }
 */
public class CheckitemActivity extends BaseActivity implements View.OnClickListener {
    private SmartRefreshLayout refreshLayout;
    private EmptyRecyclerView recyclerView;
    private TextView inspectContent, comButton, com_title, inspect_content;
    private CheckitemAdapter mAdapter;
    private ArrayList<Object> list;
    private Context mContext;
    private ChagedUtils chagedUtils;
    private String checkManageId, noticeId;
    private EmptyUtils emptyUtils;
    ArrayList<String> itemes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_itemlist);
        mContext = this;
        list = new ArrayList<>();
        chagedUtils = new ChagedUtils();
        emptyUtils = new EmptyUtils(mContext);
        Intent intent = getIntent();
        checkManageId = intent.getStringExtra("checkManageId");
        noticeId = intent.getStringExtra("noticeId");
        findViewById(R.id.com_back).setOnClickListener(this);
        com_title = (TextView) findViewById(R.id.com_title);
        com_title.setText(intent.getStringExtra("title"));
        inspect_content = (TextView) findViewById(R.id.inspect_content);
        if (!TextUtils.isEmpty(intent.getStringExtra("content"))) {
            inspect_content.setText("检查部位：" + intent.getStringExtra("content"));
        } else {
            inspect_content.setVisibility(View.GONE);
        }
        comButton = (TextView) findViewById(R.id.com_button);
        comButton.setOnClickListener(this);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshlayout);
        //是否启用下拉刷新功能
        refreshLayout.setEnableRefresh(false);
        //是否启用上拉加载功能
        refreshLayout.setEnableLoadmore(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableOverScrollDrag(true);
        recyclerView = (EmptyRecyclerView) findViewById(R.id.recycler_list);
        recyclerView.setEmptyView(emptyUtils.init());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new CheckitemAdapter(list);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setItemOnclick(new CheckitemAdapter.ItemClickListener() {
            @Override
            public void onclick(String str, int position) {
                Checkitem item = (Checkitem) list.get(position);
                item.setLeam(true);
                mAdapter.setNewData(list);
                comButton.setText("确定");
                itemes.add(item.getDetailsIds());
            }

            @Override
            public void ondelete(String str, int position) {
                Checkitem item = (Checkitem) list.get(position);
                item.setLeam(false);
                mAdapter.setNewData(list);
                for (int i = 0; i < itemes.size(); i++) {
                    String strs = itemes.get(i);
                    if (strs.equals(item.getDetailsIds())) {
                        itemes.remove(i);
                    }
                }
                if (itemes.size() == 0) {
                    comButton.setText("");
                }
            }
        });
        request();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                finish();
                break;
            case R.id.com_button:
                save();
                break;
            default:
                break;
        }
    }

    private void request() {
        chagedUtils.getdetailsofimport(checkManageId, new ChagedUtils.CallBack() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                ArrayList<Object> data = (ArrayList<Object>) map.get("list");
                list.clear();
                list.addAll(data);
                mAdapter.setNewData(list);
            }

            @Override
            public void onerror(String str) {

            }
        });
    }

    private void save() {
        /*保存导入问题项*/
        chagedUtils.batchSaveNoteceDel(checkManageId, noticeId, Dates.listToStrings(itemes), new ChagedUtils.CallBacks() {
            @Override
            public void onsuccess(String string) {
                ToastUtils.showShortToast(string);
                Intent intent = new Intent();
                //回传数据到主Activity
                setResult(1, intent);
                finish(); //此方法后才能返回主Activity
            }

            @Override
            public void onerror(String string) {
                ToastUtils.showsnackbar(com_title, string);
            }
        });
    }
}
