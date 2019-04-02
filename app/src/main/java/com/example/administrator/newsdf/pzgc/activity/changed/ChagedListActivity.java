package com.example.administrator.newsdf.pzgc.activity.changed;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.changed.adapter.ChagedListAdapter;
import com.example.administrator.newsdf.pzgc.bean.ChagedList;
import com.example.administrator.newsdf.pzgc.callback.TaskCallback;
import com.example.administrator.newsdf.pzgc.callback.TaskCallbackUtils;
import com.example.baselibrary.view.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.EmptyUtils;
import com.example.administrator.newsdf.pzgc.view.SwipeMenuLayout;
import com.example.baselibrary.view.EmptyRecyclerView;
import com.example.baselibrary.view.PullDownMenu;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/1 0001}
 * 描述：整改通知单列表
 * {@link }
 */
public class ChagedListActivity extends BaseActivity implements View.OnClickListener, TaskCallback {
    private SmartRefreshLayout refreshlayout;
    private EmptyRecyclerView recyclerList;
    private TextView title;
    private ImageView toolbarImage;
    private ChagedListAdapter adapter;
    private ArrayList<ChagedList> list;
    private Context mContext;
    private PullDownMenu pullDownMenu;
    private ChagedUtils chagedUtils;
    private String[] strings = {"全部", "未处理", "已处理"};
    private String orgId;
    private int page = 1;
    private int status = -1;
    private EmptyUtils emptyUtils;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_list);
        addActivity(this);
        mContext = this;
        Intent intent = getIntent();
        orgId = intent.getStringExtra("orgid");
        chagedUtils = new ChagedUtils();
        emptyUtils = new EmptyUtils(mContext);
        TaskCallbackUtils.setCallBack(this);
        list = new ArrayList<>();
        recyclerList = (EmptyRecyclerView) findViewById(R.id.recycler_list);
        recyclerList.setEmptyView(emptyUtils.init());
        title = (TextView) findViewById(R.id.com_title);
        title.setText(intent.getStringExtra("orgName"));
        findViewById(R.id.toolbar_menu).setOnClickListener(this);
        toolbarImage = (ImageView) findViewById(R.id.com_img);
        toolbarImage.setImageResource(R.mipmap.meun);
        toolbarImage.setVisibility(View.VISIBLE);
        findViewById(R.id.com_back).setOnClickListener(this);
        //设置列表参数
        recyclerList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChagedListAdapter(list, mContext);
        recyclerList.setAdapter(adapter);
        refreshlayout = (SmartRefreshLayout) findViewById(R.id.refreshlayout);
        //是否启用下拉刷新功能
        refreshlayout.setEnableRefresh(true);
        //是否启用上拉加载功能
        refreshlayout.setEnableLoadmore(true);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshlayout.setEnableOverScrollDrag(false);
        //是否在列表不满一页时候开启上拉加载功能
        refreshlayout.setEnableLoadmoreWhenContentNotFull(false);
        /* 下拉刷新*/
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                request();
                //关闭刷新
                refreshlayout.finishRefresh();
            }
        });
        /* 上拉加载*/
        refreshlayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                request();
                //关闭上拉加载
                refreshlayout.finishLoadmore();
            }
        });
        adapter.setOnDelListener(new ChagedListAdapter.onSwipeListener() {
            @Override
            public void onDel(final int pos, final SwipeMenuLayout layout) {
                AlertDialog alertDialog2 = new AlertDialog.Builder(mContext)
                        .setTitle("删除")
                        .setMessage("是否删除该项问题")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            //添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                /*删除按钮*/
                                delete(pos);
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            //添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create();
                alertDialog2.show();

            }

            @Override
            public void onClick(int pos) {
                int status = list.get(pos).getStatus();
                if (status == 0 || status == 20) {
                    int permission = list.get(pos).getPermission();
                    if (permission == 1) {
                        /*保存状态，调整新增页面，进行修改*/
                        Intent intent = new Intent(mContext, ChangedNewActivity.class);
                        intent.putExtra("status", true);
                        intent.putExtra("id", list.get(pos).getId());
                        intent.putExtra("orgName", title.getText().toString());
                        startActivity(intent);
                    } else {
                        /*跳转详情*/
                        Intent intent1 = new Intent(mContext, ChagedNoticeDetailsActivity.class);
                        intent1.putExtra("id", list.get(pos).getId());
                        intent1.putExtra("orgName", title.getText().toString());
                        intent1.putExtra("orgId", orgId);
                        startActivity(intent1);
                    }
                } else {
                    /*跳转详情*/
                    Intent intent1 = new Intent(mContext, ChagedNoticeDetailsActivity.class);
                    intent1.putExtra("id", list.get(pos).getId());
                    intent1.putExtra("orgName", title.getText().toString());
                    intent1.putExtra("orgId", orgId);
                    startActivity(intent1);
                }
            }

        });
        request();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                finish();
                break;
            case R.id.toolbar_menu:
                pullDownMenu = new PullDownMenu();
                pullDownMenu.showPopMeun((Activity) mContext, toolbarImage, strings);
                pullDownMenu.setOnItemClickListener(new PullDownMenu.OnItemClickListener() {
                    @Override
                    public void onclick(int position, String string) {
                        page = 1;
                        switch (string) {
                            case "全部":
                                status = -1;
                                request();
                                break;
                            case "未处理":
                                status = 20;
                                request();
                                break;
                            case "已处理":
                                status = 30;
                                request();
                                break;
                            default:
                                break;
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * 网络请求
     */
    private void request() {
        chagedUtils.getcnflist(false, status, orgId, page, false, new ChagedUtils.CallBack() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                ArrayList<ChagedList> data = (ArrayList<ChagedList>) map.get("list");
                if (page == 1) {
                    list.clear();
                }
                list.addAll(data);
                adapter.setNewData(list);
            }

            @Override
            public void onerror(String str) {
                Snackbar.make(title, str, Snackbar.LENGTH_LONG).show();
                if (1 != page) {
                    page--;
                }
            }
        });
    }

    /**
     * 删除
     */
    private void delete(final int pos) {
        chagedUtils.deletebill(list.get(pos).getId(), new ChagedUtils.CallBacks() {
            @Override
            public void onsuccess(String string) {
                //删除item
                list.remove(pos);
                //刷新
                adapter.setNewData(list);
                ToastUtils.showShortToastCenter("删除成功");
            }

            @Override
            public void onerror(String string) {
                ToastUtils.showsnackbar(title, string);
            }
        });
    }

    //回調刷新
    @Override
    public void taskCallback() {
        page = 1;
        request();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
