package com.example.zcjlmodule.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zcjlmodule.R;
import com.example.zcjlmodule.callback.CapitalBackUtils;
import com.example.zcjlmodule.callback.PayDetailCallBackUtils;
import com.example.zcjlmodule.treeView.ChangeTreeListViewAdapters;
import com.example.zcjlmodule.treeView.bean.OrgBeans;
import com.example.zcjlmodule.treeView.bean.OrgenBeans;
import com.example.zcjlmodule.treeView.utils.Nodes;
import com.example.zcjlmodule.utils.activity.UserOrgZcUtils;

import java.util.ArrayList;
import java.util.List;

import measure.jjxx.com.baselibrary.base.BaseActivity;

/**
 * @author lx
 * @Created by: 2018/11/1 0001.
 * @description:列表切换组织
 */

public class ChangeorganizeZcActivity extends BaseActivity {
    private ListView mTree;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;
    private TextView emptyViewText;
    private UserOrgZcUtils utils;
    private Context mContext;
    private List<OrgenBeans> mData;
    private ChangeTreeListViewAdapters mAdapter;
    private String status = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_org_zc);
        mContext = this;
        utils = new UserOrgZcUtils();
        mData = new ArrayList<>();
        Intent intent = getIntent();
        try {
            status = intent.getStringExtra("type");
        } catch (Exception e) {
            e.printStackTrace();
        }
        mTree = (ListView) findViewById(R.id.userorg_zc_list);
        linearLayout = (LinearLayout) findViewById(R.id.layout_emptyView);
        progressBar = (ProgressBar) findViewById(R.id.layout_emptyView_bar);
        emptyViewText = (TextView) findViewById(R.id.layout_emptyView_text);
        TextView title = (TextView) findViewById(R.id.toolbar_icon_title);
        title.setText("切换组织");
        findViewById(R.id.toolbar_icon_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        utils.getOrgs(new UserOrgZcUtils.OnClickListener() {
            @Override
            public void onClick(List<OrgBeans> data, List<OrgenBeans> data2) {
                mData.clear();
                if (data.size() > 0) {
                    linearLayout.setVisibility(View.GONE);
                }
                mData.addAll(data2);
                try {
                    mAdapter = new ChangeTreeListViewAdapters<OrgBeans>(mTree, mContext,
                            data, 0);
                    mTree.setAdapter(mAdapter);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //判断是否显示图标
    public boolean getmIcon(Nodes node) {
        String str = node.getIds();
        for (int i = 0; i < mData.size(); i++) {
            String pid = mData.get(i).getParentId() + "";
            if (str.equals(pid)) {
                return true;
            }
        }
        return false;
    }

    //添加数据
    public void getAdd(int position, Nodes node) {
        String str = node.getIds();
        for (int i = 0; i < mData.size(); i++) {
            String pid = mData.get(i).getParentId() + "";
            if (str.equals(pid)) {
                mAdapter.addExtraNode(position, mData.get(i).getName(), mData.get(i).getId(), mData.get(i).getParentId() + "", mData.get(i).getType());
                mAdapter.expandOrCollapse(position);
            }
        }
    }

    /**
     * 切换组织
     */
    public void member(final String orgid, final String name, String type) {
        try {
            if ("true".equals(status)) {
                PayDetailCallBackUtils.CallBack(orgid, name, type);
            } else if ("false".equals(status)) {
                CapitalBackUtils.CallBack(orgid, name, type);
            }
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (utils != null) {
            utils = null;
        }
    }
}
