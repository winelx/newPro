package com.example.administrator.newsdf.pzgc.activity.chagedreply;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.adapter.ChangedReplyNewAdapter;
import com.example.administrator.newsdf.pzgc.activity.changed.ChagedProblemitemActivity;

import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.SimpleDividerItemDecoration;

import java.util.ArrayList;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/15 0015}
 * 描述：新增整改回复单
 * {@link }
 */
public class ChagedReplyNewActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView recycler;
    private ChangedReplyNewAdapter adapter;
    private Context mContext;
    private ArrayList<String> list;
    private TextView com_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chagedreply_new);
        mContext = this;
        list = new ArrayList<>();
        list.add("1212");
        list.add("1212");
        list.add("1212");
        com_button = (TextView) findViewById(R.id.com_button);
        findViewById(R.id.com_back).setOnClickListener(this);
        findViewById(R.id.reply_import_problem).setOnClickListener(this);
        findViewById(R.id.chaged_organize_lin).setOnClickListener(this);
        findViewById(R.id.toolbar_menu).setOnClickListener(this);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new SimpleDividerItemDecoration(mContext));
        adapter = new ChangedReplyNewAdapter(R.layout.adapter_item_chagedreyply_new, list);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, ChagedProblemitemActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                finish();
                break;
            case R.id.reply_import_problem:
                if (com_button.getText().toString().equals("保存")) {
                    startActivityForResult(new Intent(mContext, ChagedReplyImportActivity.class), 0);
                } else {
                    ToastUtils.showShortToast("当前不是编辑状态");
                }
                break;
            case R.id.chaged_organize_lin:
                //关联整改通知单
                startActivityForResult(new Intent(mContext, ChagedReplyRelationActivity.class), 1);
                break;
            case R.id.toolbar_menu:
                if (com_button.getText().toString().equals("保存")) {
                    com_button.setText("保存");
                } else {
                    com_button.setText("编辑");
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            //整改组织
        } else if (requestCode == 0 && resultCode == 0) {
            //导入问题项
        }
    }
}
