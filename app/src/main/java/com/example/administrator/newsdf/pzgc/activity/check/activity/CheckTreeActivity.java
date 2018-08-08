package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.OrganizationEntity;
import com.example.administrator.newsdf.pzgc.utils.TreeUtlis;
import com.example.administrator.newsdf.treeView.Node;
import com.example.administrator.newsdf.treeView.TaskTreeListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/** 
 * description: 检查的wbs树
 * @author lx
 * date: 2018/8/6 0006 下午 1:35 
 * update: 2018/8/6 0006
 * version: 
*/

public class CheckTreeActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_wbs);
        mContext=this;
        treeUtlis = new TreeUtlis();

        mTreeDatas = new ArrayList<>();
        organizationList = new ArrayList<>();
        checklist= (ListView) findViewById(R.id.wbs_listview);
        //添加list数据
        OrganizationEntity bean = new OrganizationEntity("724ebfdc08b04e5dbad91b4693b20bfa", "",
                "测试9标", "0", false,
                true, "3,5", "",
                "", "", "测试9标", "", true);
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
        Intent intent=new Intent();
        intent.putExtra("title",node.getTitle());
        setResult(3, intent);
        finish();
    }

    public void Dialog() {
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
