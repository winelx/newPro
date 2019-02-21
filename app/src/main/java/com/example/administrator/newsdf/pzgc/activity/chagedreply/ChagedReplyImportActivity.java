package com.example.administrator.newsdf.pzgc.activity.chagedreply;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.adapter.ChagedReplyImportAdapter;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.utils.ChagedreplyUtils;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.utils.bean.ImprotItem;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/15 0015}
 * 描述：导入问题项
 * {@link }
 */
public class ChagedReplyImportActivity extends BaseActivity implements View.OnClickListener {
    private SmartRefreshLayout refreshLayout;
    private ChagedReplyImportAdapter adapter;
    private TextView inspectContent, comButton;
    private RecyclerView recyclerView;
    private ArrayList<ImprotItem> list;
    private Context mContext;
    private int page = 0;
    private String noticeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_itemlist);
        mContext = this;
        list = new ArrayList<>();
        findViewById(R.id.com_back).setOnClickListener(this);
        findViewById(R.id.toolbar_menu).setOnClickListener(this);
        comButton = (TextView) findViewById(R.id.com_button);
        TextView title = (TextView) findViewById(R.id.com_title);
        title.setText("导入问题项");
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
        adapter = new ChagedReplyImportAdapter(R.layout.adapter_chagedreply_import, list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ImprotItem improtItem = list.get(position);
                if (improtItem.isNewX()) {
                    improtItem.setNewX(false);
                    page++;
                } else {
                    improtItem.setNewX(true);
                    page--;
                }
                if (page == 0) {
                    comButton.setText("");
                } else {
                    comButton.setText("确定");
                }
                adapter.setNewData(list);
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
            case R.id.toolbar_menu:
                Intent intent = new Intent();
                intent.putExtra("id", "12");
                intent.putExtra("str", "12");
                setResult(0, intent);
                fileList();
                break;
            default:
                break;
        }
    }

    /*查询导入问题列表*/
    public void request() {
        ChagedreplyUtils.chooseNoticeDelData("9eb299c3c3f549219d86ac0ec75c367e", new ChagedreplyUtils.MapCallBack() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                list.clear();
                list.addAll((ArrayList<ImprotItem>) map.get("list"));
                adapter.setNewData(list);
            }

            @Override
            public void onerror(String str) {

            }
        });
    }

    /*保存导入问题项*/
    private void save() {
        ChagedreplyUtils.batchSaveReplyDel(new ChagedreplyUtils.ObjectCallBacks() {
            @Override
            public void onsuccess(String string) {
                finish();
            }

            @Override
            public void onerror(String string) {
                Snackbar.make(refreshLayout, string, Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}