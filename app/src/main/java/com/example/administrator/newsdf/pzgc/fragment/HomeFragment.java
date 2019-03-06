package com.example.administrator.newsdf.pzgc.fragment;

import android.content.Context;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.home.HometaskActivity;
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
    private LinearLayout layoutRanking;

    @Override
    protected int setContentView() {
        return R.layout.fragment_homemessage;
    }

    @Override
    protected void init() {
        mContext = getActivity();
        //排名
        rootView.findViewById(R.id.layout_ranking).setOnClickListener(this);
        //任务总数
        rootView.findViewById(R.id.tasktotal).setOnClickListener(this);
        //今日任务
        rootView.findViewById(R.id.todaytotal).setOnClickListener(this);
        //本月
        rootView.findViewById(R.id.lastmonthtotal).setOnClickListener(this);
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
            case R.id.layout_ranking:
                ToastUtils.showShortToastCenter("排名");
                break;
            case R.id.tasktotal:
                //累计完成任务
                Intent tasktotal = new Intent(mContext, HometaskActivity.class);
                tasktotal.putExtra("title", "累计完成任务");
                startActivity(tasktotal);
                break;
            case R.id.todaytotal:
                //今日完成任务
                Intent todaytotal = new Intent(mContext, HometaskActivity.class);
                todaytotal.putExtra("title", "今日完成任务");
                startActivity(todaytotal);
                break;
            case R.id.lastmonthtotal:
                //上月整改统计
                Intent lastmonthtotal = new Intent(mContext, HometaskActivity.class);
                lastmonthtotal.putExtra("title", "上月整改统计");
                startActivity(lastmonthtotal);
                break;
            default:
                break;
        }
    }
}
