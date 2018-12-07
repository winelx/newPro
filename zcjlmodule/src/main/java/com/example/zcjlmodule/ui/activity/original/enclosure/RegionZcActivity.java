package com.example.zcjlmodule.ui.activity.original.enclosure;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zcjlmodule.R;
import com.example.zcjlmodule.bean.AttachProjectBean;
import com.example.zcjlmodule.treeView.RegionTreeListViewAdapters;
import com.example.zcjlmodule.treeView.bean.OrgBeans;
import com.example.zcjlmodule.treeView.bean.OrgenBeans;
import com.example.zcjlmodule.treeView.utils.Nodes;
import com.example.zcjlmodule.utils.activity.MeasureUtils;

import java.util.ArrayList;
import java.util.List;

import measure.jjxx.com.baselibrary.base.BaseActivity;

/**
 * description: 区域查询
 *
 * @author lx
 *         date: 2018/11/7 0007 下午 2:55
 */
public class RegionZcActivity extends BaseActivity implements View.OnClickListener {

    private RegionTreeListViewAdapters mAdapter;
    private Context mContext;
    private ProgressBar layoutEmptyviewBar;
    private TextView layoutEmptyviewText;
    private LinearLayout layoutEmptyview;
    private MeasureUtils utils;
    private String orgId;
    private List<OrgenBeans> mData;
    private String regionId;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_zc);
        Intent intent = getIntent();
        orgId = intent.getStringExtra("orgId");
        mContext = this;
        mData = new ArrayList<>();
        utils = new MeasureUtils();
        //提示布局
        layoutEmptyview = (LinearLayout) findViewById(R.id.layout_emptyView);
        //等待进度
        layoutEmptyviewBar = (ProgressBar) findViewById(R.id.layout_emptyView_bar);
        //空白提示
        layoutEmptyviewText = (TextView) findViewById(R.id.layout_emptyView_text);
        findViewById(R.id.toolbar_icon_back).setOnClickListener(this);
        TextView title = (TextView) findViewById(R.id.toolbar_icon_title);
        title.setText("地区查询");
        mListView = (ListView) findViewById(R.id.attachproject_recycler);
        httprequest();
    }

    /**
     * 网络请求
     */
    private void httprequest() {
        //网络请求
        utils.ascriptionqyxx(orgId, new MeasureUtils.TypeOnClickListener() {
            @Override
            public void onsuccess(List<OrgBeans> data, List<OrgenBeans> data2) {
                mData.clear();
                if (data.size() > 0) {
                    layoutEmptyview.setVisibility(View.GONE);
                } else {
                    layoutEmptyview.setVisibility(View.VISIBLE);
                    layoutEmptyviewBar.setVisibility(View.GONE);
                    layoutEmptyviewText.setText("暂无数据");
                }
                mData.addAll(data2);
                try {
                    mAdapter = new RegionTreeListViewAdapters<OrgBeans>(mListView, mContext,
                            data, 0);
                    mListView.setAdapter(mAdapter);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (data.size() <= 0) {
                    layoutEmptyviewText.setText("暂无数据");
                } else {
                    layoutEmptyview.setVisibility(View.GONE);
                }
                layoutEmptyviewBar.setVisibility(View.GONE);
            }

            @Override
            public void onerror() {
                layoutEmptyviewText.setText("暂无数据");
                layoutEmptyviewBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.toolbar_icon_back) {
            finish();
        } else {
        }
    }

    private class AttachProjectAdapter extends BaseQuickAdapter<AttachProjectBean, BaseViewHolder> {
        public AttachProjectAdapter(@LayoutRes int layoutResId, @Nullable List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, AttachProjectBean item) {
            helper.setText(R.id.attachproject_text, item.getName());
        }
    }

    /**
     * /判断是否显示图标
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
     * 添加数据
     *
     * @param position
     * @param node
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

    /**
     * 切换组织
     */
    public void member(final String id, final String name, String type) {
        try {
            Intent intent = new Intent();
            // 获取用户计算后的结果
            intent.putExtra("name", name);
            intent.putExtra("id", id);
            setResult(101, intent);
            finish(); //结束当前的activity的生命周期
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
