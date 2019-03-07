package com.example.administrator.newsdf.pzgc.activity.home;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.CompleteBean;
import com.example.administrator.newsdf.pzgc.Adapter.NoticeAdapter;
import com.example.administrator.newsdf.pzgc.Adapter.NoticedBean;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.ChagedreplyDetailsActivity;
import com.example.administrator.newsdf.pzgc.activity.changed.ChagedNoticeDetailsActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckListDetailsActivity;
import com.example.administrator.newsdf.pzgc.activity.home.utils.HomeFragmentUtils;
import com.example.administrator.newsdf.pzgc.bean.AgencyBean;
import com.example.administrator.newsdf.pzgc.bean.ChagedNoticeDetails;
import com.example.administrator.newsdf.pzgc.callback.Onclicktener;
import com.example.administrator.newsdf.pzgc.fragment.HomeFragment;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Enums;
import com.example.baselibrary.EmptyRecyclerView;
import com.example.baselibrary.EmptyUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Map;


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
    String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        mContext = this;
        Intent intent = getIntent();
        list = new ArrayList<>();
        content = intent.getStringExtra("title");
        if (Enums.NOTICE.equals(content)) {
            request();
        } else if (Enums.AGENCY.equals(content)) {
            mynotast();
        } else if (Enums.COMPLETE.equals(content)) {
            list.add(new CompleteBean("4545"));
            list.add(new CompleteBean("4545"));
            list.add(new CompleteBean("4545"));
        }
        emptyUtils = new EmptyUtils(this);
        comTitle = (TextView) findViewById(R.id.com_title);
        comTitle.setText(intent.getStringExtra("title"));
        findViewById(R.id.com_back).setOnClickListener(this);
        recycler = (EmptyRecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(mContext));
        recycler.setEmptyView(emptyUtils.init());
        mAdapter = new NoticeAdapter(list);
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
        mAdapter.setOnclicktener(new Onclicktener() {
            @Override
            public void onClick(String content, int position) {
                if (Enums.NOTICE.equals(content)) {
                    //消息通知的点击事件
                    onclicknotice(position);
                } else if (Enums.AGENCY.equals(content)) {
                    ToastUtils.showShortToastCenter("2");
                } else if (Enums.COMPLETE.equals(content)) {
                    ToastUtils.showShortToastCenter("3");
                }
            }


        });
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
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                onLoadmores();
                refreshlayout.finishLoadmore();
            }
        });
    }

    /*点击事件*/
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

    /*消息通知网络请求*/
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
                //弹出提示
                ToastUtils.showsnackbar(comTitle, string);
            }
        });
    }

    /*待办事项*/
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
                //弹出提示
                ToastUtils.showsnackbar(comTitle, string);
            }
        });
    }

    /*下拉刷新*/
    private void onrefreshs() {
        if (Enums.NOTICE.equals(content)) {
            request();
        } else if (Enums.AGENCY.equals(content)) {
            mynotast();
        } else if (Enums.COMPLETE.equals(content)) {
            list.add(new CompleteBean("4545"));
            list.add(new CompleteBean("4545"));
            list.add(new CompleteBean("4545"));
        }
    }

    /*上拉加载*/
    private void onLoadmores() {
        if (Enums.NOTICE.equals(content)) {
            request();
        } else if (Enums.AGENCY.equals(content)) {
            mynotast();
        } else if (Enums.COMPLETE.equals(content)) {
            list.add(new CompleteBean("4545"));
            list.add(new CompleteBean("4545"));
            list.add(new CompleteBean("4545"));
        }
    }

    /*通知消息点击事件*/
    private void onclicknotice(int position) {
        NoticedBean bean = (NoticedBean) list.get(position);
        int modelname = bean.getModelType();
        if (modelname == 1) {
            //整改通知单
            Intent notice = new Intent(mContext, ChagedNoticeDetailsActivity.class);
            notice.putExtra("id", bean.getModelId());
            notice.putExtra("orgId", bean.getBeNoticeOrgId());
            notice.putExtra("orgName", bean.getBeNoticeOrgName());
            startActivity(notice);
        } else if (modelname == 2) {
            //回复验证单
            Intent reply = new Intent(mContext, ChagedreplyDetailsActivity.class);
            reply.putExtra("id", bean.getModelId());
            reply.putExtra("orgName", bean.getBeNoticeOrgName());
            startActivity(reply);
        } else {
            //监督检查
            Intent intent = new Intent(mContext, CheckListDetailsActivity.class);
            intent.putExtra("id", bean.getModelId());
            intent.putExtra("type", bean.getBeNoticeOrgName());
            startActivity(intent);
        }
    }

}
