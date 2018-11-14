package com.example.zcjlmodule.ui.activity.original.enclosure;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zcjlmodule.R;
import com.example.zcjlmodule.bean.AttachProjectBean;
import com.example.zcjlmodule.callback.PayDetailCallBackUtils;
import com.example.zcjlmodule.treeView.ChangeTreeListViewAdapters;
import com.example.zcjlmodule.treeView.ZhengcTreeListViewAdapters;
import com.example.zcjlmodule.treeView.bean.OrgBeans;
import com.example.zcjlmodule.treeView.bean.OrgenBeans;
import com.example.zcjlmodule.treeView.utils.Nodes;
import com.example.zcjlmodule.treeView.utils.adapter.TreeListViewAdapters;
import com.example.zcjlmodule.utils.activity.MeasureUtils;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

import measure.jjxx.com.baselibrary.base.BaseActivity;


/**
 * description: 征拆类型查询
 *
 * @author lx
 *         date: 2018/10/18 0018 下午 4:07
 *         update: 2018/10/18 0018
 *         version:
 *         跳转界面 StandardDecomposeZcActivity
 *         这个是一个listview 写的tree界面
 */
public class DismantlingtypequeryZcActivity extends BaseActivity {
    private ListView mListView;
    private MeasureUtils utils;
    private String ogrId;
    private Context mContext;
    private List<OrgenBeans> mData;
    private LinearLayout layout_emptyView;
    private ProgressBar layout_emptyView_bar;
    private TextView layout_emptyView_text;
    private ZhengcTreeListViewAdapters mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dismantlingtypequery_zc);
        utils = new MeasureUtils();
        mContext = this;
        Intent intent = getIntent();
        mData = new ArrayList<>();
        ogrId = intent.getStringExtra("orgId");
        layout_emptyView = (LinearLayout) findViewById(R.id.layout_emptyView);
        layout_emptyView_bar = (ProgressBar) findViewById(R.id.layout_emptyView_bar);
        layout_emptyView_text = (TextView) findViewById(R.id.layout_emptyView_text);
        mListView = (ListView) findViewById(R.id.dis_typequery_list);
        findViewById(R.id.toolbar_icon_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = (TextView) findViewById(R.id.toolbar_icon_title);
        title.setText("征拆类型查询");
        httprequest();
    }

    private void httprequest() {
        utils.collectiontype(ogrId, new MeasureUtils.TypeOnClickListener() {
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
                    mAdapter = new ZhengcTreeListViewAdapters<OrgBeans>(mListView, mContext,
                            data, 0);
                    mListView.setAdapter(mAdapter);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onerror() {
                if (mData.size() > 0) {
                    layout_emptyView.setVisibility(View.GONE);
                } else {
                    layout_emptyView.setVisibility(View.VISIBLE);
                    layout_emptyView_bar.setVisibility(View.GONE);
                    layout_emptyView_text.setText("暂无数据");
                }
            }
        });
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
