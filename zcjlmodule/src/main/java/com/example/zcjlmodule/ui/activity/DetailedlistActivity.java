package com.example.zcjlmodule.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zcjlmodule.bean.DetailedBean;
import com.example.zcjlmodule.bean.MessageItem;
import com.example.zcjlmodule.model.DetailedlistModel;
import com.example.zcjlmodule.presenter.DetailedlistPresenter;
import com.example.zcjlmodule.ui.fragment.MessageFragmentZc;
import com.example.zcjlmodule.view.DetailedlistView;
import com.example.zcmodule.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import measure.jjxx.com.baselibrary.base.BaseActivity;
import measure.jjxx.com.baselibrary.base.BaseMvpActivity;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;

/**
 * description: 支付清册
 *
 * @author lx
 *         date: 2018/10/11 0011 下午 3:33
 */
public class DetailedlistActivity extends BaseMvpActivity<DetailedlistPresenter> implements DetailedlistView, View.OnClickListener {
    private DetailedlistActivity.RecyclerAdapter mAdapter;//适配器
    private ArrayList<String> list;
    private Context mContext;
    private LinearLayout emptyView;
    private ProgressBar gressBar;
    private TextView prompt;
    private SmartRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailedlist_zc);
        mContext = this;
        mPresenter = new DetailedlistPresenter();
        mPresenter.mView = this;
        //返回键初始化并添加点击事件
        findViewById(R.id.toolbar_icon_back).setOnClickListener(this);
        //刷新加载
        refreshLayout= (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        //空布局
        emptyView = (LinearLayout) findViewById(R.id.detailedlist_emptyView);
        //进入时界面时显示等待条
        gressBar = (ProgressBar) findViewById(R.id.detailedlist_emptyView_bar);
        //加载失败的提示
        prompt = (TextView) findViewById(R.id.detailedlist_emptyView_text);
        //标题初始化
        TextView textView = (TextView) findViewById(R.id.toolbar_icon_title);
        RecyclerView detailedlist_recycler = (RecyclerView) findViewById(R.id.detailedlist_recycler);
        textView.setText("支付清册");
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add((i + 1) + "");
        }
        if (list.size() > 0) {
            emptyView.setVisibility(View.GONE);
        }

        //设置控件显示样式
        detailedlist_recycler.setLayoutManager(new LinearLayoutManager(mContext));
        //调加适配器，初始化布局和数据
        detailedlist_recycler.setAdapter(mAdapter = new DetailedlistActivity.RecyclerAdapter(R.layout.adapter_detailedlist_activity_ac, list));
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtlis.getInstance().showShortToast(position + "");
            }
        });

        /**
         *   下拉刷新
         */
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {


                //传入false表示刷新失败
                refreshlayout.finishRefresh(800);
            }
        });
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

                //传入false表示加载失败
                refreshlayout.finishLoadmore(800);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_icon_back:
                finish();
                break;
            default:
                break;
        }
    }

    //处理请求返回的数据展示到界面
    @Override
    public void getdata(String str) {

    }

    //recyclerview适配器
    public class RecyclerAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public RecyclerAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }
}
