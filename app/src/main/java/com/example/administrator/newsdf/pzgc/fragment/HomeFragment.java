package com.example.administrator.newsdf.pzgc.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.CompleteBean;
import com.example.administrator.newsdf.pzgc.Adapter.NoticedBean;
import com.example.administrator.newsdf.pzgc.activity.home.HometaskActivity;
import com.example.administrator.newsdf.pzgc.activity.home.NoticeActivity;
import com.example.administrator.newsdf.pzgc.activity.home.utils.HomeFragmentUtils;
import com.example.administrator.newsdf.pzgc.bean.AgencyBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.Map;


/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/3/4 0004}
 * 描述：消息推送通知页
 * {@link }
 */

public class HomeFragment extends Fragment implements View.OnClickListener {
    private SmartRefreshLayout refreshLayout;
    private Context mContext;
    private SwipeRefreshLayout mRefreshLayout;
    private LinearLayout layoutRanking;
    private TextView noticedData, agencyData, completeData;
    private TextView noticedContent, agencyContent, completeContent;
    private TextView noticedNumber, agencyNumber;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //避免重复绘制界面
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_homemessage, null);
            init();
        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        request();
        return rootView;
    }

    protected void init() {
        mContext = getActivity();
        findId();
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
                request();
                mRefreshLayout.setRefreshing(false);
            }
        });
        //停止刷新
        mRefreshLayout.setRefreshing(false);
    }

    private void findId() {
        noticedData = rootView.findViewById(R.id.noticed_data);
        agencyData = rootView.findViewById(R.id.agency_data);
        completeData = rootView.findViewById(R.id.complete_data);

        noticedContent = rootView.findViewById(R.id.noticed_content);
        agencyContent = rootView.findViewById(R.id.agency_content);
        completeContent = rootView.findViewById(R.id.complete_content);

        noticedNumber = rootView.findViewById(R.id.noticed_number);
        agencyNumber = rootView.findViewById(R.id.agency_number);
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

    private void request() {
        HomeFragmentUtils.getmsgnoticepagedata(new HomeFragmentUtils.requestCallBack() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                //消息通知
                analysisNoticed((NoticedBean) map.get("noticed"));
                //待办消息
                analysisAgency((AgencyBean) map.get("agency"));
                //已办消息
                analysisComplete((CompleteBean) map.get("complete"));
            }
            @Override
            public void onerror(String string) {

            }
        });
    }

    private void analysisNoticed(NoticedBean bean) {
        if (bean.getNoticeDate() != null) {
            noticedData.setText(bean.getNoticeDate());
        } else {
            noticedData.setText("");
        }
        if (bean.getShowContent() != null) {
            noticedContent.setText(bean.getShowContent());
        } else {
            noticedContent.setText("");
        }
        if (bean.getTotalCount() != 0) {
            noticedNumber.setText(bean.getTotalCount() + "");
            noticedNumber.setVisibility(View.VISIBLE);
        } else {
            noticedNumber.setVisibility(View.INVISIBLE);
        }
    }

    private void analysisAgency(AgencyBean bean) {
        agencyData.setText(bean.getSendDate());
        if (bean.getReceiveOrgName() != null) {
            agencyContent.setText(bean.getModelName() + "(" + bean.getModelCode() + ")" + "需要处理。");
        } else {
            agencyContent.setText("");
        }
        if (bean.getTotalCount() != 0) {
            agencyNumber.setText(bean.getTotalCount() + "");
            agencyNumber.setVisibility(View.VISIBLE);
        } else {
            agencyNumber.setVisibility(View.INVISIBLE);
        }
    }

    private void analysisComplete(CompleteBean bean) {
        completeData.setText(bean.getSendDate());
        if (bean.getModelName() != null) {
            completeContent.setText(bean.getReceiveOrgName() + bean.getModelName() + "(" + bean.getModelCode() + ")" + bean.getDealResult());
        } else {
            completeContent.setText("");
        }
    }

}
