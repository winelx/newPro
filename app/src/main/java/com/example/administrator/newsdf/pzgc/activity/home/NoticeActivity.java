package com.example.administrator.newsdf.pzgc.activity.home;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckNewAddActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckNewAddsActivity;
import com.example.administrator.newsdf.pzgc.special.loedger.activity.LoedgerDetailsActivity;
import com.example.administrator.newsdf.pzgc.special.loedger.activity.LoedgerRecordDetailActivity;
import com.example.administrator.newsdf.pzgc.special.programme.activity.ProgrammeDetailsActivity;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.adapter.CompleteBean;
import com.example.administrator.newsdf.pzgc.adapter.NoticeAdapter;
import com.example.administrator.newsdf.pzgc.adapter.NoticedBean;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.ChagedreplyDetailsActivity;
import com.example.administrator.newsdf.pzgc.activity.changed.ChagedNoticeDetailsActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckListDetailsActivity;
import com.example.administrator.newsdf.pzgc.activity.home.utils.HomeFragmentUtils;
import com.example.administrator.newsdf.pzgc.bean.AgencyBean;
import com.example.administrator.newsdf.pzgc.fragment.HomeFragment;
import com.example.baselibrary.base.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.EmptyUtils;
import com.example.administrator.newsdf.pzgc.utils.Enums;
import com.example.baselibrary.utils.rx.RxBus;
import com.example.baselibrary.view.EmptyRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.functions.Consumer;


/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/3/4 0004}
 * 描述：首页通知任务展示列表
 * @see HomeFragment
 */
public class NoticeActivity extends BaseActivity implements View.OnClickListener {
    private EmptyRecyclerView recycler;
    private TextView comTitle;
    private EmptyUtils emptyUtils;
    private NoticeAdapter mAdapter;
    private SmartRefreshLayout refreshLayout;
    private ArrayList<Object> list;
    private Context mContext;
    private int page = 1;
    private String content;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            page = 1;
            onrefreshs();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        mContext = this;

        emptyUtils = new EmptyUtils(mContext);
        RxBus.getInstance().subscribe(String.class, new Consumer<String>() {
            @Override
            public void accept(String path) {
                handler.sendMessage(new Message());
            }
        });
        Intent intent = getIntent();
        list = new ArrayList<>();
        content = intent.getStringExtra("title");
        comTitle = (TextView) findViewById(R.id.com_title);
        //标题
        comTitle.setText(intent.getStringExtra("title"));
        findViewById(R.id.com_back).setOnClickListener(this);
        recycler = (EmptyRecyclerView) findViewById(R.id.noticerecycler);
        //设置展示style
        recycler.setLayoutManager(new LinearLayoutManager(mContext));
        //设置adapter
        mAdapter = new NoticeAdapter(list);
        //设置空白提示
        mAdapter.setEmptyView(emptyUtils.init());
        recycler.setAdapter(mAdapter);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.smartrefresh);
        //是否启用下拉刷新功能
        refreshLayout.setEnableRefresh(true);
        //是否启用上拉加载功能
        refreshLayout.setEnableLoadmore(true);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableOverScrollDrag(false);
        //是否在列表不满一页时候开启上拉加载功能
        refreshLayout.setEnableLoadmoreWhenContentNotFull(false);
        /**
         *   下拉刷新
         */
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                onrefreshs();
                refreshlayout.finishRefresh();
            }
        });
        /**
         * 上拉加载
         */
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                onLoadmores();
                refreshlayout.finishLoadmore();
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Object object = list.get(position);
                if (object instanceof NoticedBean) {
                    noticedOnclick(position);
                } else if (object instanceof AgencyBean) {
                    agencyOnclick(position);
                } else if (object instanceof CompleteBean) {
                    completeOnclick(position);
                } else {

                }
            }
        });
        //根据标题选择网络请求
        if (Enums.NOTICE.equals(content)) {
            request();
        } else if (Enums.AGENCY.equals(content)) {
            mynotast();
        } else if (Enums.COMPLETE.equals(content)) {
            myyestast();
        }
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.com_back:
                //返回
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 消息通知网络请求
     */
    public void request() {
        HomeFragmentUtils.mysystemnotice(page, new HomeFragmentUtils.requestCallBack() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                //如果page=1，清空集合的数据
                if (page == 1) {
                    list.clear();
                }
                //获取返回数据
                list.addAll((ArrayList<NoticedBean>) map.get("notice"));
                if (list.size() == 0) {
                    //如果列表无数据，展示空白提示
                    emptyUtils.noData("暂无数据，下拉刷新");
                }
                //更新数据
                mAdapter.setNewData(list);
            }

            @Override
            public void onerror(String string) {
                if (Enums.REQUEST_ERROR.equals(string)) {
                    //如果page==1，就不减，
                    if (page != 1) {
                        page--;
                    }
                }
                if (list.size() == 0) {
                    //如果列表无数据，展示空白提示
                    emptyUtils.noData("暂无数据，下拉刷新");
                }
                //弹出提示
                ToastUtils.showsnackbar(comTitle, string);
            }
        });
    }

    /**
     * 待办事项
     */
    public void mynotast() {
        HomeFragmentUtils.mynotast(page, new HomeFragmentUtils.requestCallBack() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                //如果page=1，清空集合的数据
                if (page == 1) {
                    list.clear();
                }
                //获取返回数据
                list.addAll((ArrayList<AgencyBean>) map.get("agency"));
                if (list.size() == 0) {
                    //如果列表无数据，展示空白提示
                    emptyUtils.noData("暂无数据，下拉刷新");
                }
                //更新数据
                mAdapter.setNewData(list);
            }

            @Override
            public void onerror(String string) {
                if (Enums.REQUEST_ERROR.equals(string)) {
                    //如果page==1，就不减，
                    if (page != 1) {
                        page--;
                    }
                }
                if (list.size() == 0) {
                    //如果列表无数据，展示空白提示
                    emptyUtils.noData("暂无数据，下拉刷新");
                }
                //弹出提示
                ToastUtils.showsnackbar(comTitle, string);
            }
        });
    }

    /**
     * 已办事项
     */
    public void myyestast() {
        HomeFragmentUtils.myyestast(page, new HomeFragmentUtils.requestCallBack() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                //如果page=1，清空集合的数据
                if (page == 1) {
                    list.clear();
                }
                //获取返回数据
                list.addAll((ArrayList<CompleteBean>) map.get("complete"));
                if (list.size() == 0) {
                    //如果列表无数据，展示空白提示
                    emptyUtils.noData("暂无数据，下拉刷新");
                }
                //更新数据
                mAdapter.setNewData(list);
            }

            @Override
            public void onerror(String string) {
                if (Enums.REQUEST_ERROR.equals(string)) {
                    //如果page==1，就不减，
                    if (page != 1) {
                        page--;
                    }
                }
                if (list.size() == 0) {
                    //如果列表无数据，展示空白提示
                    emptyUtils.noData("暂无数据，下拉刷新");
                }
                //弹出提示
                ToastUtils.showsnackbar(comTitle, string);
            }
        });
    }

    /**
     * 下拉刷新
     */
    private void onrefreshs() {
        if (Enums.NOTICE.equals(content)) {
            request();
        } else if (Enums.AGENCY.equals(content)) {
            mynotast();
        } else if (Enums.COMPLETE.equals(content)) {
            myyestast();
        }
    }

    /**
     * 上拉加载
     */
    private void onLoadmores() {
        if (Enums.NOTICE.equals(content)) {
            request();
        } else if (Enums.AGENCY.equals(content)) {
            mynotast();
        } else if (Enums.COMPLETE.equals(content)) {
            mynotast();
        }
    }

    /**
     * 通知消息点击事件
     */
    private void noticedOnclick(int position) {
        NoticedBean bean = (NoticedBean) list.get(position);
        int modelname = bean.getModelType();
        if (modelname == 1) {
            //整改通知单操作
            Intent notice = new Intent(mContext, ChagedNoticeDetailsActivity.class);
            notice.putExtra("id", bean.getModelId());
            notice.putExtra("status", false);
            notice.putExtra("orgId", bean.getBeNoticeOrgId());
            notice.putExtra("orgName", bean.getBeNoticeOrgName());
            startActivity(notice);
        } else if (modelname == 2) {
            //回复验证单操作
            Intent reply = new Intent(mContext, ChagedreplyDetailsActivity.class);
            reply.putExtra("id", bean.getModelId());
            reply.putExtra("status", false);
            reply.putExtra("orgName", bean.getBeNoticeOrgName());
            startActivity(reply);
        } else if (modelname == 3) {
            //监督检查
            Intent intent = new Intent(mContext, CheckListDetailsActivity.class);
            intent.putExtra("id", bean.getModelId());
            intent.putExtra("type", "3");
            startActivity(intent);
        } else if (modelname == 5) {
            int iwork = bean.getIwork();
            if (iwork == 1) {
                Intent intent = new Intent(mContext, CheckNewAddActivity.class);
                intent.putExtra("orgId", bean.getBeNoticeOrgId());
                intent.putExtra("name", bean.getBeNoticeOrgName());
                intent.putExtra("type", iwork + "");
                intent.putExtra("taskId", bean.getModelId());
                startActivity(intent);
            } else {
                Intent intent = new Intent(mContext, CheckNewAddsActivity.class);
                intent.putExtra("orgId", bean.getBeNoticeOrgId());
                intent.putExtra("name", bean.getBeNoticeOrgName());
                intent.putExtra("taskId", bean.getModelId());
                intent.putExtra("type", iwork + "");
                startActivity(intent);
            }
        }
    }

    /**
     * 待办事项
     */
    private void agencyOnclick(int position) {
        AgencyBean bean = (AgencyBean) list.get(position);
        int modelType = bean.getModelType();
        if (modelType == 1) {
            //整改通知单
            Intent notice = new Intent(mContext, ChagedNoticeDetailsActivity.class);
            notice.putExtra("id", bean.getModelId());
            notice.putExtra("orgId", bean.getReceiveOrgId());
            notice.putExtra("orgName", bean.getReceiveOrgName());
            startActivity(notice);
        } else if (modelType == 2) {
            //回复验证单
            Intent reply = new Intent(mContext, ChagedreplyDetailsActivity.class);
            reply.putExtra("id", bean.getModelId());
            reply.putExtra("orgName", bean.getReceiveOrgName());
            startActivity(reply);
        } else if (modelType == 3) {
            //监督检查
            Intent intent = new Intent(mContext, CheckListDetailsActivity.class);
            intent.putExtra("id", bean.getModelId());
            intent.putExtra("type", "3");
            startActivity(intent);
        } else if (modelType == 4) {
            //台账
            Intent intent = new Intent(mContext, LoedgerDetailsActivity.class);
            intent.putExtra("id", bean.getModelId());
            intent.putExtra("taskId", bean.getId());
            intent.putExtra("type", true);
            intent.putExtra("title",bean.getModelCode());
            startActivity(intent);
        } else if (modelType == 6) {
            //方案报批
            Intent intent = new Intent(mContext, ProgrammeDetailsActivity.class);
            intent.putExtra("id", bean.getModelId());
            intent.putExtra("taskid", bean.getId());
            intent.putExtra("orgid", bean.getModelName());
            startActivity(intent);
        }
    }

    /**
     * 已办事件
     */
    private void completeOnclick(int position) {
        CompleteBean bean = (CompleteBean) list.get(position);
        int modelType = bean.getModelType();
        if (modelType == 1) {
            //整改通知单
            Intent notice = new Intent(mContext, ChagedNoticeDetailsActivity.class);
            notice.putExtra("id", bean.getModelId());
            notice.putExtra("status", false);
            notice.putExtra("orgId", bean.getReceiveOrgId());
            notice.putExtra("orgName", bean.getReceiveOrgName());
            startActivity(notice);
        } else if (modelType == 2) {
            //回复验证单
            Intent reply = new Intent(mContext, ChagedreplyDetailsActivity.class);
            reply.putExtra("id", bean.getModelId());
            reply.putExtra("status", false);
            reply.putExtra("orgName", bean.getReceiveOrgName());
            startActivity(reply);
        } else if (modelType == 3) {
            //监督检查
            Intent intent = new Intent(mContext, CheckListDetailsActivity.class);
            intent.putExtra("id", bean.getModelId());
            intent.putExtra("type", 3);
            startActivity(intent);
        } else if (modelType == 4) {
            //台账
            Intent intent = new Intent(mContext, LoedgerDetailsActivity.class);
            intent.putExtra("id", bean.getModelId());
            intent.putExtra("taskId", "");
            intent.putExtra("type", true);
            intent.putExtra("title", bean.getModelCode());
            startActivity(intent);
        } else if (modelType == 6) {
            //方案报批
            Intent intent = new Intent(mContext, ProgrammeDetailsActivity.class);
            intent.putExtra("id", bean.getModelId());
            intent.putExtra("taskid", "");
            intent.putExtra("orgid", bean.getModelCode());
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unSubcribe();

    }


}
