package com.example.administrator.yanghu.pzgc.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.activity.notice.fragment.MessageNoticeFragment;
import com.example.administrator.yanghu.pzgc.bean.Audio;
import com.example.administrator.yanghu.pzgc.bean.Proclamation;
import com.example.administrator.yanghu.pzgc.utils.Dates;
import com.example.administrator.yanghu.pzgc.utils.Enums;
import com.example.administrator.yanghu.pzgc.utils.ToastUtils;
import com.example.administrator.yanghu.pzgc.adapter.CompleteBean;
import com.example.administrator.yanghu.pzgc.adapter.NoticedBean;
import com.example.administrator.yanghu.pzgc.activity.check.activity.CheckReportActivity;
import com.example.administrator.yanghu.pzgc.activity.home.HometaskActivity;
import com.example.administrator.yanghu.pzgc.activity.home.NoticeActivity;
import com.example.administrator.yanghu.pzgc.activity.home.utils.HomeFragmentUtils;
import com.example.administrator.yanghu.pzgc.bean.AgencyBean;
import com.example.baselibrary.utils.rx.RxBus;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.functions.Consumer;


/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/3/4 0004}
 * 描述：消息推送通知页
 * {@link }
 */

public class HomeFragment extends Fragment implements View.OnClickListener {
    private Context mContext;
    private SwipeRefreshLayout mRefreshLayout;

    private TextView noticedData, agencyData, completeData, propagandaData;
    private TextView noticedContent, agencyContent, completeContent, propagandaContent;
    private TextView noticedNumber, agencyNumber, taskfinishcount, todaytotalnumber, lastmonthtotalnumber, propagandaNumber;
    private TextView thirdRanking, fristRanking, secondRanking, thirdRankingSocrd, secondRankingSocrd, fristRankingSocrd;
    private View rootView;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            request();
        }
    };

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
        //注册事件总线
        RxBus.getInstance().subscribe(String.class, new Consumer<String>() {
            @Override
            public void accept(String path) {
                if ("home".equals(path)) {
                    handler.sendMessage(new Message());
                }
            }
        });
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
        rootView.findViewById(R.id.propaganda_lin).setOnClickListener(this);
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
        //消息通知是
        noticedData = rootView.findViewById(R.id.noticed_data);
        /*最新消息内容 */
        noticedContent = rootView.findViewById(R.id.noticed_content);
        /* 消息数量*/
        noticedNumber = rootView.findViewById(R.id.noticed_number);
        //待办消息
        agencyData = rootView.findViewById(R.id.agency_data);
        agencyContent = rootView.findViewById(R.id.agency_content);
        agencyNumber = rootView.findViewById(R.id.agency_number);
        //已办消息
        completeData = rootView.findViewById(R.id.complete_data);
        completeContent = rootView.findViewById(R.id.complete_content);
        taskfinishcount = rootView.findViewById(R.id.taskfinishcount);
        todaytotalnumber = rootView.findViewById(R.id.todaytotalnumber);
        lastmonthtotalnumber = rootView.findViewById(R.id.lastmonthtotalnumber);
        //通知公告
        propagandaData = rootView.findViewById(R.id.propaganda_data);
        propagandaContent = rootView.findViewById(R.id.propaganda_content);
        propagandaNumber = rootView.findViewById(R.id.propaganda_number);
        /**/
        thirdRanking = rootView.findViewById(R.id.third_ranking);
        thirdRankingSocrd = rootView.findViewById(R.id.third_ranking_socrd);
        fristRanking = rootView.findViewById(R.id.frist_ranking);
        fristRankingSocrd = rootView.findViewById(R.id.frist_ranking_socrd);
        secondRankingSocrd = rootView.findViewById(R.id.second_ranking_socrd);
        secondRanking = rootView.findViewById(R.id.second_ranking);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.noticed_lin:
                //消息通知
                Intent noticed = new Intent(mContext, NoticeActivity.class);
                noticed.putExtra("title", Enums.NOTICE);
                noticedNumber.setVisibility(View.GONE);
                startActivity(noticed);
                break;
            case R.id.agency_lin:
                //代办事项
                Intent agency = new Intent(mContext, NoticeActivity.class);
                agency.putExtra("title", "待办事项");
                startActivity(agency);
                break;
            case R.id.complete_lin:
                //已办事项
                Intent complete = new Intent(mContext, NoticeActivity.class);
                complete.putExtra("title", "已办事项");
                startActivity(complete);
                break;
            case R.id.layout_ranking:
                //标段排名
                Intent intents= new Intent(mContext, CheckReportActivity.class);
                intents.putExtra("type","month");
                startActivity(intents);
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
            case R.id.propaganda_lin:
                //通知公告
                Intent messagenotice = new Intent(mContext, MessageNoticeFragment.class);
                startActivity(messagenotice);
                break;
            default:
                break;
        }
    }

    private void request() {
        HomeFragmentUtils.getmsgnoticepagedata(new HomeFragmentUtils.requestCallBack() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onsuccess(Map<String, Object> map) {
                //累计完成任务数
                taskfinishcount.setText(map.get("grandCount") + "");
                //今日完任务数
                todaytotalnumber.setText(map.get("todayCount") + "");
                //上月整改单统计
                lastmonthtotalnumber.setText(map.get("lastCount") + "");
                if (map.containsKey("orgRanke")) {
                    ArrayList<Audio> list = (ArrayList<Audio>) map.get("orgRanke");
                    for (int i = 0; i < list.size(); i++) {
                        if (i == 0) {
                            fristRanking.setText(list.get(i).getName());
                            fristRankingSocrd.setText(Dates.setText(mContext, 5, "综合评分：" + list.get(i).getContent(), R.color.red));
                        } else if (i == 1) {
                            secondRanking.setText(list.get(i).getName());
                            secondRankingSocrd.setText(Dates.setText(mContext, 5, "综合评分：" + list.get(i).getContent(), R.color.red));
                        } else if (i == 2) {
                            thirdRanking.setText(list.get(i).getName());
                            thirdRankingSocrd.setText(Dates.setText(mContext, 5, "综合评分：" + list.get(i).getContent(), R.color.red));
                        }
                    }
                }

                //消息通知
                if (map.containsKey("noticed")) {
                    noticedNumber.setVisibility(View.VISIBLE);
                    analysisNoticed((NoticedBean) map.get("noticed"));
                } else {
                    noticedData.setText("");
                    noticedContent.setText("暂无消息");
                    noticedNumber.setVisibility(View.INVISIBLE);
                }
                //待办消息
                if (map.containsKey("agency")) {
                    agencyNumber.setVisibility(View.VISIBLE);
                    analysisAgency((AgencyBean) map.get("agency"));
                } else {
                    agencyNumber.setVisibility(View.INVISIBLE);
                    agencyContent.setText("暂无消息");
                    agencyData.setText("");
                }
                //已办消息
                if (map.containsKey("complete")) {
                    analysisComplete((CompleteBean) map.get("complete"));
                } else {
                    completeData.setText("");
                    completeContent.setText("暂无消息");
                }
                //通知公告  proclamation
                if (map.containsKey("proclamation")) {
                    proclamation((Proclamation) map.get("proclamation"));
                } else {
                    propagandaData.setText("");
                    propagandaContent.setText("暂无消息");
                }
            }

            @Override
            public void onerror(String string) {
                ToastUtils.showShortToastCenter(string);
            }
        });
    }

    /*消息通知*/
    private void analysisNoticed(NoticedBean bean) {
        if (bean.getNoticeDate() != null) {
            noticedData.setText(bean.getNoticeDate().substring(0, 10));
        } else {
            noticedData.setText("");
        }
        if (bean.getShowContent() != null) {
            noticedContent.setText(bean.getShowContent());
        } else {
            noticedContent.setText("");
        }
        if (bean.getTotalCount() != 0) {
            if (bean.getTotalCount() > 99) {
                noticedNumber.setText(99 + "+");
            } else {
                noticedNumber.setText(bean.getTotalCount() + "");
            }

            noticedNumber.setVisibility(View.VISIBLE);
        } else {
            noticedNumber.setVisibility(View.INVISIBLE);
        }
    }

    /*待办事项*/
    private void analysisAgency(AgencyBean bean) {
        agencyData.setText(bean.getSendDate().substring(0, 10));
        if (bean.getReceiveOrgName() != null) {
            agencyContent.setText(bean.getModelName() + "(" + bean.getModelCode() + ")" + "需要处理。");
        } else {
            agencyContent.setText("");
        }
        if (bean.getTotalCount() != 0) {
            if (bean.getTotalCount() > 99) {
                agencyNumber.setText(99 + "+");
            } else {
                agencyNumber.setText(bean.getTotalCount() + "");
            }
            agencyNumber.setVisibility(View.VISIBLE);
        } else {
            agencyNumber.setVisibility(View.INVISIBLE);
        }
    }

    /*已办事项*/
    private void analysisComplete(CompleteBean bean) {
        completeData.setText(bean.getSendDate().substring(0, 10));
        if (bean.getModelName() != null) {
            completeContent.setText(bean.getReceiveOrgName() + bean.getModelName() + "(" + bean.getModelCode() + ")" + bean.getDealResult());
        } else {
            completeContent.setText("");
        }
    }

    /*通知公告*/
    private void proclamation(Proclamation bean) {
        //时间
        if (!bean.getPublishDate().isEmpty()) {
            propagandaData.setText(bean.getPublishDate().substring(0, 10));
        } else {
            propagandaData.setText("");
        }
        //消息
        if (!bean.getContent().isEmpty()) {
            propagandaContent.setText(bean.getTitle());
        } else {
            propagandaContent.setText("暂无消息");
        }
        //通知数量
        if (!bean.getNumber().isEmpty()) {
            propagandaNumber.setText(bean.getNumber());
        } else {
            propagandaNumber.setText("");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unSubcribe();
    }
}
