package com.example.administrator.newsdf.pzgc.activity.changed;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class ChagedListActivity extends BaseActivity implements View.OnClickListener {
    private SmartRefreshLayout refreshlayout;
    private TextView title;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_list);
        title = (TextView) findViewById(R.id.com_title);
        imageView = (ImageView) findViewById(R.id.com_img);
        imageView.setImageResource(R.mipmap.meun);
        imageView.setVisibility(View.VISIBLE);
        Utils.setMargins(imageView, 0, 0, 10, 0);
        findViewById(R.id.com_back).setOnClickListener(this);
        refreshlayout = (SmartRefreshLayout) findViewById(R.id.refreshlayout);
        //是否启用下拉刷新功能
        refreshlayout.setEnableRefresh(true);
        //是否启用上拉加载功能
        refreshlayout.setEnableLoadmore(true);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshlayout.setEnableOverScrollDrag(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                httprequest();
            }
        });
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                httprequest(false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                finish();
                break;
            default:
                break;
        }
    }
}
