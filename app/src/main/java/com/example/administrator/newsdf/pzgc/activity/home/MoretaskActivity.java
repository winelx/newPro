package com.example.administrator.newsdf.pzgc.activity.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.adapter.MoretaskAdapter;
import com.example.administrator.newsdf.pzgc.adapter.TaskPhotoAdapter;
import com.example.administrator.newsdf.pzgc.activity.home.same.DirectlyreplyActivity;
import com.example.administrator.newsdf.pzgc.activity.home.utils.HomeUtils;
import com.example.administrator.newsdf.pzgc.activity.home.utils.MoredetailsUrils;
import com.example.administrator.newsdf.pzgc.bean.AduioContent;
import com.example.administrator.newsdf.pzgc.bean.MoretasklistBean;
import com.example.administrator.newsdf.pzgc.bean.PhotoBean;
import com.example.administrator.newsdf.pzgc.inter.JsonCallback;
import com.example.baselibrary.base.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.FloatMeunAnims;
import com.example.baselibrary.utils.log.LogUtil;
import com.example.baselibrary.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;
import com.joanzapata.iconify.widget.IconTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;


/**
 * description: 多次任务上传界面
 *
 * @author lx
 * date: 2018/4/16 0016 上午 11:11
 * update: 2018/4/16 0016
 * version:
 */
public class MoretaskActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private TextView wbsNode, drawer_layout_text;
    private RecyclerView mRecyclerView;
    private MoretaskAdapter mAdapter;
    private ArrayList<AduioContent> contents;
    private ArrayList<MoretasklistBean> Dats;
    public String id, wbsid, status, taskID;
    private DrawerLayout drawerLayout;
    private String userId;
    private LinearLayout newmoretask;
    private TaskPhotoAdapter taskPhotoAdapter;
    private ListView drawerLayoutList;
    private ArrayList<PhotoBean> imagePaths;
    private SmartRefreshLayout drawerLayout_smart;
    private IconTextView iconTextView;
    /**
     * 请求图册的页数
     */
    private int page = 1;
    /**
     * 判断状态，是上拉还是下拉
     */
    private boolean drew = true;
    private String wbsName = "", activity;

    //弹出框
    private CircleImageView fab;
    private LinearLayout meun_standard, meun_photo;
    private FloatMeunAnims floatMeunAnims;
    private boolean liststatus = true;
    boolean anim = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moretask);
        mContext = this;
        floatMeunAnims = new FloatMeunAnims();

        //初始化集合
        initArry();
        //初始化ID
        initfind();
        //初始化数据
        initdata();
        final Intent intent = getIntent();
        try {
            taskID = intent.getExtras().getString("TaskId");
            wbsid = intent.getExtras().getString("wbsid");
            status = intent.getExtras().getString("status");
            activity = intent.getExtras().getString("activity");
            if (status.equals("true")) {
                iconTextView.setVisibility(View.VISIBLE);
            } else {
                iconTextView.setVisibility(View.GONE);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();

        }
        userId = SPUtils.getString(mContext, "staffId", null);
        //网络请求
        //侧拉listview上拉加载
        drawerLayout_smart.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                drew = false;
                page++;
                if (liststatus) {
                    getPhoto();
                    //传入false表示加载失败
                } else {
                    getSatard();
                    //传入false表示加载失败
                }
                refreshlayout.finishLoadmore(1000);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        OkGo();
    }

    //初始化数据
    private void initdata() {
        //侧滑栏关闭
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //侧滑栏关闭手势滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //展示侧拉界面后，背景透明度（当前透明度为完全透明）
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        //设置recyclerview显示样式
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //添加分割线
        mAdapter = new MoretaskAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
        //抽屉控件listview
        taskPhotoAdapter = new TaskPhotoAdapter(imagePaths, mContext);
        drawerLayoutList.setAdapter(taskPhotoAdapter);
        //关闭下拉刷新
        drawerLayout_smart.setEnableRefresh(false);
    }

    //初始化控件ID
    private void initfind() {
        drawer_layout_text = (TextView) findViewById(R.id.drawer_layout_text);
        findViewById(R.id.com_back).setOnClickListener(this);
        //wbs路径
        iconTextView = (IconTextView) findViewById(R.id.iconTextView);
        //图册界面上拉控件
        drawerLayout_smart = (SmartRefreshLayout) findViewById(R.id.drawerLayout_smart);
        //wbs路径
        wbsNode = (TextView) findViewById(R.id.wbsnode);
        //抽屉控件
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //任务内容
        mRecyclerView = (RecyclerView) findViewById(R.id.task_content);
        //新增任务
        newmoretask = (LinearLayout) findViewById(R.id.newmoretask);
        //设置点击事件
        newmoretask.setOnClickListener(this);
        //图册侧拉界面
        drawerLayoutList = (ListView) findViewById(R.id.drawer_layout_list);
        /**
         *meun
         */
        fab = (CircleImageView) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        meun_standard = (LinearLayout) findViewById(R.id.meun_standard);
        meun_photo = (LinearLayout) findViewById(R.id.meun_photo);
        meun_photo.setOnClickListener(this);
        meun_standard.setOnClickListener(this);
        findViewById(R.id.taskManagemented).setOnClickListener(this);
    }

    //初始化集合
    private void initArry() {
        Dats = new ArrayList<>();
        contents = new ArrayList<>();
        //图纸
        imagePaths = new ArrayList<>();
    }

    /**
     * 暴露给adapter的方法，给点击事件使用,跳转界面
     */
    public void onclick(int pos) {
        Intent intent = new Intent(mContext, TaskdetailsActivity.class);
        intent.putExtra("TaskId", Dats.get(pos).getId());
        LogUtil.i("ss", Dats.get(pos).getId());
        intent.putExtra("wbsid", wbsid);
        //判断能否可以跳转任务管理
        intent.putExtra("status", status);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.meun_photo:
                //请求图纸
                //加载第一页
                page = 1;
                //请求数据时清除之前的
                drew = true;
                //网络请求
                imagePaths.clear();
                taskPhotoAdapter.getData(imagePaths, "");
                drawer_layout_text.setText("图纸");
                getPhoto();
                //上拉加载的状态判断
                liststatus = true;
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.meun_standard:
                //标准
                //加载第一页
                page = 1;
                //请求数据时清除之前的
                drew = true;
                //上拉加载的状态判断
                liststatus = false;
                imagePaths.clear();
                taskPhotoAdapter.getData(imagePaths, "");
                drawer_layout_text.setText("标准");
                getSatard();
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.fab:
                //打开meun选项
                if (anim) {
                    floatMeunAnims.doclickt(meun_photo, meun_standard, fab);
                    anim = false;
                } else {
                    floatMeunAnims.doclicktclose(meun_photo, meun_standard, fab);
                    anim = true;
                }
                break;
            case R.id.newmoretask:
                //点击回复
                Intent intent = new Intent(MoretaskActivity.this, DirectlyreplyActivity.class);
                intent.putExtra("id", taskID);
                intent.putExtra("wbsId", wbsid);
                intent.putExtra("status", status);
                intent.putExtra("activity", activity);
                startActivityForResult(intent, 1);
                break;
            case R.id.com_back:
                finish();
                break;
            case R.id.taskManagemented:
                if ("true".equals(status)) {
                    Dates.getDialogs((Activity) mContext, "请求数据中..");
                    HomeUtils.getOko(wbsid, null, false, null, false, null, MoretaskActivity.this);
                }
                break;
            default:
                break;
        }
    }

    //请求网络
    public void OkGo() {
        LogUtil.i("result", taskID);
        OkGo.<String>get(Requests.ContentDetail)
                .params("id", taskID)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LogUtil.i("result", s);
                        MoredetailsUrils.getStringTolist(s, wbsName, new JsonCallback() {
                            @Override
                            public void onsuccess(Map<String, Object> map) {
                                contents.clear();
                                Dats.clear();
                                String status = (String) map.get("status");
                                String leaderId = (String) map.get("leaderId");
                                switch (status) {
                                    case "2":
                                        //已完成不需要回复
                                        newmoretask.setVisibility(View.GONE);
                                        break;
                                    case "0":
                                        //未完成，判断责任人ID和登录人的ID是否相同
                                        if (!leaderId.equals(userId)) {
                                            newmoretask.setVisibility(View.GONE);
                                        } else {
                                            newmoretask.setVisibility(View.VISIBLE);
                                        }
                                        break;
                                    default:
                                        newmoretask.setVisibility(View.GONE);
                                        break;
                                }
                                contents.addAll((ArrayList<AduioContent>) map.get("list1"));
                                Dats.addAll((ArrayList<MoretasklistBean>) map.get("list2"));
                                mAdapter.getContent(contents, Dats);
                                wbsNode.setText((String) map.get("WbsName"));
                            }
                        });
                        /*  getJson(s);*/
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                    }
                });
    }

    //请求图册
    public void getPhoto() {
        Dates.getDialog(MoretaskActivity.this, "请求数据中...");
        //将请求方法封装到工具类，因为多个界面需要相同的请求
        HomeUtils.photoAdm(wbsid, page, imagePaths, drew, taskPhotoAdapter, wbsName);
    }

    //请求标准
    public void getSatard() {
        Dates.getDialog(MoretaskActivity.this, "请求数据中...");
        //将请求方法封装到工具类，因为多个界面需要相同的请求
        HomeUtils.getStard(wbsid, page, imagePaths, drew, taskPhotoAdapter, wbsName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            taskID = data.getStringExtra("frag_id");
            newmoretask.setVisibility(View.VISIBLE);
            OkGo();
        }
    }

    //判断id
    public String getId() {
        return taskID;
    }


}
