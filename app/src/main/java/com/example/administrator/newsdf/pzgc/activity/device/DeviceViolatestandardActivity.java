package com.example.administrator.newsdf.pzgc.activity.device;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.device.utils.DeviceUtils;
import com.example.administrator.newsdf.pzgc.callback.CheckCallback3;
import com.example.administrator.newsdf.pzgc.callback.ProblemItemCallbackUtils;
import com.example.administrator.newsdf.pzgc.callback.ViolateCallbackUtils;
import com.example.baselibrary.view.BaseActivity;
import com.example.administrator.newsdf.treeviews.ViolateTreeListViewAdapters;
import com.example.administrator.newsdf.treeviews.bean.OrgBeans;
import com.example.administrator.newsdf.treeviews.bean.OrgenBeans;
import com.example.administrator.newsdf.treeviews.utils.Nodes;


import java.util.ArrayList;
import java.util.List;

/**
 * @author lx
 * @description: 特种设备违反标准
 * @date: 2018/12/3 0003 上午 11:07
 */
public class DeviceViolatestandardActivity extends BaseActivity implements CheckCallback3 {
    private LinearLayout listlinear;
    private ListView mTree;
    private ViolateTreeListViewAdapters<OrgBeans> mAdapter;
    private List<OrgenBeans> mData;
    private DeviceUtils deviceUtils;
    private Context mContext;
    private String facility;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_violatestandard);
        addActivity(this);
        deviceUtils = new DeviceUtils();
        Intent intent = getIntent();
        facility = intent.getStringExtra("facility");
        mContext = this;
        ViolateCallbackUtils.setCallBack(this);
        mData = new ArrayList<>();
        TextView title = (TextView) findViewById(R.id.titleView);
        title.setText("违反标准");
        listlinear = (LinearLayout) findViewById(R.id.listlinear);
        mTree = (ListView) findViewById(R.id.list_view);
        //网络请求
        deviceUtils.violateetree(facility, new DeviceUtils.ViolickLitener() {
            @Override
            public void onsussess(List<OrgBeans> data, List<OrgenBeans> data2) {
                mData.clear();
                mData.addAll(data2);
                try {
                    mAdapter = new ViolateTreeListViewAdapters<OrgBeans>(mTree, DeviceViolatestandardActivity.this,
                            data, 0);
                    mTree.setAdapter(mAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //返回
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * @description: 判断是否显示图标
     * @author lx
     * @date: 2018/12/5 0005 下午 3:19
     */
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

    /**
     * @内容: 添加数据
     * @author lx
     * @date: 2018/12/5 0005 下午 3:21
     */
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

    public void itemonclick(String id, String name, String type) {
        if (type.equals("qd")) {
            Intent intent = new Intent(mContext, ViolatedetailsActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        } else {
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (deviceUtils != null) {
            deviceUtils = null;
        }
        removeActivity(this);
    }

    @Override
    public void update(String string) {
        ProblemItemCallbackUtils.CheckCallback3(string);
        finish();
    }
}
