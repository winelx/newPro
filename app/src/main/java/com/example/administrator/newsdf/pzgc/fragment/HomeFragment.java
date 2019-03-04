package com.example.administrator.newsdf.pzgc.fragment;

import android.content.Context;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.home.NoticeActivity;
import com.example.administrator.newsdf.pzgc.utils.LazyloadFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;


/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/3/4 0004}
 * 描述：消息推送通知页
 * {@link }
 */

public class HomeFragment extends LazyloadFragment implements View.OnClickListener {
    private SmartRefreshLayout refreshLayout;
    private Context mContext;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected int setContentView() {
        return R.layout.fragment_homemessage;
    }

    @Override
    protected void init() {
        mContext = getActivity();
        rootView.findViewById(R.id.noticed_lin).setOnClickListener(this);
        rootView.findViewById(R.id.agency_lin).setOnClickListener(this);
        rootView.findViewById(R.id.complete_lin).setOnClickListener(this);
        mRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.mRefreshLayout);
// 下拉刷新颜色控制
        mRefreshLayout.setColorSchemeResources(R.color.colorAccent,
                R.color.finish_green, R.color.Orange,
                R.color.yellow);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            sleep(3000);
                        } catch (Exception e) {

                        }
                        mRefreshLayout.setRefreshing(false);
                    }
                }.start();
            }
        });
//停止刷新
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void lazyLoad() {
        /*请求数据*/
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.noticed_lin:
                //消息通知
                Intent noticed = new Intent(mContext, NoticeActivity.class);
                noticed.putExtra("title", "消息通知");
                startActivity(noticed);

                break;
            case R.id.agency_lin:
                //代办事项
                Intent agency = new Intent(mContext, NoticeActivity.class);
                agency.putExtra("title", "代办事项");
                startActivity(agency);
                break;
            case R.id.complete_lin:
                //已办事项
                Intent complete = new Intent(mContext, NoticeActivity.class);
                complete.putExtra("title", "已办事项");
                startActivity(complete);
                break;
            default:
                break;
        }
    }
}
