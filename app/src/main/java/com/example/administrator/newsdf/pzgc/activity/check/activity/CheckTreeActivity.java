package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.OrganizationEntity;
import com.example.baselibrary.base.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.TreeUtlis;
import com.example.administrator.newsdf.treeView.Node;
import com.example.administrator.newsdf.treeView.TaskTreeListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 检查的wbs树
 *
 * @author lx
 *         date: 2018/8/6 0006 下午 1:35
 *         update: 2018/8/6 0006
 *         version:
 */

public class CheckTreeActivity extends BaseActivity {
    //tree
    private List<OrganizationEntity> mTreeDatas;
    private ArrayList<OrganizationEntity> organizationList;
    private TaskTreeListViewAdapter<OrganizationEntity> mTreeAdapter;
    private TreeUtlis treeUtlis;
    private Context mContext;
    private ListView checklist;
    private Dialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkuser_list);

        Intent intent = getIntent();
        mContext = this;
        treeUtlis = new TreeUtlis();
        TextView com_title= (TextView) findViewById(R.id.com_title);
        com_title.setText("选择部位");
        mTreeDatas = new ArrayList<>();
        organizationList = new ArrayList<>();
        checklist = (ListView) findViewById(R.id.wbs_listview);
        //添加list数据
        String name = intent.getStringExtra("name");
        OrganizationEntity bean = new OrganizationEntity(intent.getStringExtra("orgId"), "",
                name, "0", false,
                true, "3,5", "",
                "", "", name, "", true);
        organizationList.add(bean);
        treeUtlis.getOrganization(mContext, organizationList, mTreeAdapter, mTreeDatas, checklist);
        checklist.setEmptyView(findViewById(R.id.nullposion));
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void switchAct(Node node) {
        Intent intent = new Intent();
        intent.putExtra("id", node.getId());
        intent.putExtra("name", node.getName());
        intent.putExtra("title", node.getTitle());
        setResult(3, intent);
        finish();
    }

    public void dialog() {
        progressDialog = new Dialog(mContext, R.style.progress_dialog);
        progressDialog = new Dialog(mContext, R.style.progress_dialog);
        progressDialog.setContentView(R.layout.waiting_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView text = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        text.setText("请求数据中...");
        progressDialog.show();

    }

    public void dissdialog() {
        progressDialog.dismiss();
    }
}
