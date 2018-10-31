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
import com.example.zcjlmodule.callback.ChangOrgCallBackUtils;
import com.example.zcjlmodule.callback.PayDetailCallBackUtils;
import com.example.zcjlmodule.treeView.SimpleTreeListViewAdapters;
import com.example.zcjlmodule.treeView.bean.OrgBeans;
import com.example.zcjlmodule.treeView.bean.OrgenBeans;
import com.example.zcjlmodule.treeView.utils.Nodes;
import com.example.zcjlmodule.utils.activity.UserOrgZcUtils;

import java.util.ArrayList;
import java.util.List;

import measure.jjxx.com.baselibrary.base.BaseActivity;
import measure.jjxx.com.baselibrary.utils.SPUtils;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;

/**
 * description: 切换组织（获取当前用户的所有组织）
 *
 * @author lx
 *         date: 2018/10/25 0025 上午 9:50
 *         update: 2018/10/25 0025
 *         version:
 *         跳转界面 MineFragment
 */
public class UserOrgZcActivity extends BaseActivity {
    private ListView mTree;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;
    private TextView emptyViewText;
    private UserOrgZcUtils utils;
    private Context mContext;
    private List<OrgenBeans> mData;
    private SimpleTreeListViewAdapters mAdapter;
    private int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        utils = new UserOrgZcUtils();
        mData = new ArrayList<>();
        Intent intent = getIntent();
        try {
            status = intent.getIntExtra("status", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_user_org_zc);
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
        utils.getuserorg(new UserOrgZcUtils.OnClickListener() {
            @Override
            public void onClick(List<OrgBeans> data, List<OrgenBeans> data2) {
                mData.clear();
                if (data.size() > 0) {
                    linearLayout.setVisibility(View.GONE);
                }
                mData.addAll(data2);
                try {
                    mAdapter = new SimpleTreeListViewAdapters<OrgBeans>(mTree, mContext,
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
        if (status == 0) {
            utils.changorg(orgid, name, new UserOrgZcUtils.OnChangeClickListener() {
                @Override
                public void onClick(int ret) {
                    if (ret == 0) {
                        ToastUtlis.getInstance().showShortToast("切换组织成功");
                        SPUtils.deleShare(mContext, "orgName");
                        SPUtils.deleShare(mContext, "orgId");
                        //所在组织ID
                        SPUtils.putString(mContext, "orgId", orgid);
                        //所在组织名称
                        SPUtils.putString(mContext, "orgName", name);
                        ChangOrgCallBackUtils.CallBack();
                        finish();
                    }
                }
            });
        } else {
            try {
                PayDetailCallBackUtils.CallBack(orgid, name);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
