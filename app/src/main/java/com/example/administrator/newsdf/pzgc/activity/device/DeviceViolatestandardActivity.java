package com.example.administrator.newsdf.pzgc.activity.device;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.device.utils.DeviceUtils;
import com.example.administrator.newsdf.pzgc.callback.CheckCallback3;
import com.example.administrator.newsdf.pzgc.callback.ViolateCallbackUtils;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
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
public class DeviceViolatestandardActivity extends BaseActivity {
    private LinearLayout violation_fragment, listlinear;
    private ListView mTree;
    private ViolateTreeListViewAdapters<OrgBeans> mAdapter;
    private List<OrgenBeans> mData;
    private DeviceUtils deviceUtils;
    private Context mContext;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_violatestandard);
        deviceUtils = new DeviceUtils();
        mContext = this;
        mData = new ArrayList<>();
        TextView title = (TextView) findViewById(R.id.titleView);
        title.setText("违反标准");
        violation_fragment = (LinearLayout) findViewById(R.id.violation_fragment);
        listlinear = (LinearLayout) findViewById(R.id.listlinear);
        mTree = (ListView) findViewById(R.id.list_view);
        showView();
        //网络请求
        deviceUtils.violateetree(new DeviceUtils.ViolickLitener() {
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
        hideView();
        ViolateCallbackUtils.CheckCallback3(name);
    }

    public void showView() {
        listlinear.setVisibility(View.VISIBLE);
        violation_fragment.setVisibility(View.GONE);
    }

    public void hideView() {
        listlinear.setVisibility(View.GONE);
        violation_fragment.setVisibility(View.VISIBLE);
    }
}
