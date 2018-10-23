package com.example.zcjlmodule.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zcjlmodule.R;
import com.example.zcjlmodule.bean.QueryBeanZc;

import java.util.ArrayList;
import java.util.List;

import measure.jjxx.com.baselibrary.base.BaseActivity;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;

/**
 * description: 原始勘丈表的menu的选项，按期数查询
 *
 * @author lx
 *         date: 2018/10/16 0016 上午 9:51
 *         界面主要控件，recyclerview，
 */
public class PeriodsQueryZcActivity extends BaseActivity implements View.OnClickListener {
    private MyAdapter mAdapter;
    private List<QueryBeanZc> list;
    private Context mContext;
    private LinearLayout layout_emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periods_query);
        mContext = this;
        list = new ArrayList<>();
        list.add(new QueryBeanZc("1212","12121"));
        list.add(new QueryBeanZc("1212","12121"));
        list.add(new QueryBeanZc("1212","12121"));
        //返回
        findViewById(R.id.toolbar_icon_back).setOnClickListener(this);
        layout_emptyView = (LinearLayout) findViewById(R.id.layout_emptyView);
        //标题
        TextView title = (TextView) findViewById(R.id.toolbar_icon_title);
        title.setText("按期数查询");
        if (list.size()>0){
            layout_emptyView.setVisibility(View.GONE);
        }
        //列表
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.perio_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration divider = new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(mContext,R.drawable.custom_divider));
        recyclerView.addItemDecoration(divider);
        recyclerView.setAdapter(mAdapter = new MyAdapter(R.layout.adapter_periodsquery_zc, list));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtlis.getInstance().showShortToast(list.get(position).getContent());
            }
        });
    }

    //点击事件
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.toolbar_icon_back) {
            finish();

        } else {
        }
    }

    public class MyAdapter extends BaseQuickAdapter<QueryBeanZc, BaseViewHolder> {

        public MyAdapter(@LayoutRes int layoutResId, List<QueryBeanZc> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, QueryBeanZc item) {
            helper.setText(R.id.item_content, item.getContent());
        }
    }

}
