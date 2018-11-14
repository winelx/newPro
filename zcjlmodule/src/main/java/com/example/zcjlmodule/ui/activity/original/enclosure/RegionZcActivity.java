package com.example.zcjlmodule.ui.activity.original.enclosure;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.zcjlmodule.treeView.ZhengcTreeListViewAdapters;
import com.example.zcjlmodule.treeView.bean.OrgBeans;
import com.example.zcjlmodule.treeView.bean.OrgenBeans;
import com.example.zcjlmodule.treeView.utils.Nodes;
import com.example.zcjlmodule.utils.activity.MeasureUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
    private ProgressBar layout_emptyView_bar;
    private TextView layout_emptyView_text;
    private LinearLayout layout_emptyView;
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
        layout_emptyView = (LinearLayout) findViewById(R.id.layout_emptyView);
        //等待进度
        layout_emptyView_bar = (ProgressBar) findViewById(R.id.layout_emptyView_bar);
        //空白提示
        layout_emptyView_text = (TextView) findViewById(R.id.layout_emptyView_text);
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
                    layout_emptyView.setVisibility(View.GONE);
                } else {
                    layout_emptyView.setVisibility(View.VISIBLE);
                    layout_emptyView_bar.setVisibility(View.GONE);
                    layout_emptyView_text.setText("暂无数据");
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
                    layout_emptyView_text.setText("暂无数据");
                } else {
                    layout_emptyView.setVisibility(View.GONE);
                }
                layout_emptyView_bar.setVisibility(View.GONE);
            }

            @Override
            public void onerror() {
                layout_emptyView_text.setText("暂无数据");
                layout_emptyView_bar.setVisibility(View.GONE);
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
}
