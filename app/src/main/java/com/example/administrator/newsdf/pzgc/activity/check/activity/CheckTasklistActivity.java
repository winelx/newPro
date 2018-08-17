package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.newsdf.App;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.NotSubmitTaskAdapter;
import com.example.administrator.newsdf.pzgc.Adapter.SCheckTasklistAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.CheckUtils;
import com.example.administrator.newsdf.pzgc.activity.check.Checkjson;
import com.example.administrator.newsdf.pzgc.bean.CheckTasklistAdapter;
import com.example.administrator.newsdf.pzgc.callback.TaskCallback;
import com.example.administrator.newsdf.pzgc.callback.TaskCallbackUtils;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.ScreenUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;


/**
 * description: 检查管理_标准的检查任务
 *
 * @author lx
 *         date: 2018/8/2 0002 下午 2:41
 *         update: 2018/8/2 0002
 *         version:
 */
public class CheckTasklistActivity extends AppCompatActivity implements View.OnClickListener, TaskCallback {
    private static final String TAG = "CheckTasklistActivity";
    private NotSubmitTaskAdapter mAdapter;
    private ArrayList<Object> list;
    private ArrayList<CheckTasklistAdapter> listsuccess;
    private ArrayList<SCheckTasklistAdapter> listsub;

    private Context mContext;
    private PopupWindow mPopupWindow;
    private LinearLayout checklistmeun;
    private float resolution;
    private SmartRefreshLayout smartrefreshlayout;
    private int pages = 1;
    private String orgId, name;
    private RecyclerView rmanageRecy;
    private CheckUtils checkUtils;
    private String status = "3";
    private Checkjson checkjson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkmanagementlist);
        mContext = CheckTasklistActivity.this;
        TaskCallbackUtils.setCallBack(this);
        checkUtils = new CheckUtils();
        checkjson = new Checkjson();
        try {
            Intent intent = getIntent();
            name = intent.getStringExtra("name");
            orgId = intent.getStringExtra("orgId");
        } catch (NullPointerException e) {
            name = "";
            orgId = "";
        }

        list = new ArrayList<>();
        listsub = new ArrayList<>();
        //获取屏幕对比比例1DP=？PX 比例有 1 ，2 ，3 ，4
        resolution = ScreenUtil.getDensity(App.getInstance());
        TextView titleView = (TextView) findViewById(R.id.titleView);
        titleView.setText(name);
        smartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);
        //是否开启越界
        smartrefreshlayout.setEnableOverScrollDrag(true);
        //取消内容不满一页时开启上拉加载功能
        smartrefreshlayout.setEnableLoadmoreWhenContentNotFull(false);
        //是否在刷新的时候禁止列表的操作
        smartrefreshlayout.setDisableContentWhenRefresh(true);
        //是否在加载的时候禁止列表的操作
        smartrefreshlayout.setDisableContentWhenLoading(true);
        //是否启用列表惯性滑动到底部时自动加载更多
        smartrefreshlayout.setEnableAutoLoadmore(false);

        smartrefreshlayout.setEnableOverScrollDrag(true);//是否启用越界拖动（仿苹果效果）1.0.4
        ImageView checklistmeunimage = (ImageView) findViewById(R.id.checklistmeunimage);
        checklistmeunimage.setVisibility(View.VISIBLE);
        checklistmeunimage.setBackgroundResource(R.mipmap.meun);
        checklistmeun = (LinearLayout) findViewById(R.id.checklistmeun);
        findViewById(R.id.checklistback).setOnClickListener(this);
        checklistmeun.setOnClickListener(this);
        findViewById(R.id.newcheck).setOnClickListener(this);
        rmanageRecy = (RecyclerView) findViewById(R.id.rmanage_recy);
        //设置布局管理器
        rmanageRecy.setLayoutManager(new LinearLayoutManager(this));
        //设置适配器
        rmanageRecy.setAdapter(mAdapter = new NotSubmitTaskAdapter(this));
        //设置Item增删的动画
        rmanageRecy.setItemAnimator(new DefaultItemAnimator());
        /**
         *   下拉刷新
         */
        smartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pages = 1;
                list.clear();
                checkmamgrlist();


            }
        });
        //上拉加载
        smartrefreshlayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pages++;
                checkmamgrlist();
            }
        });
        /**
         * 网络请求
         */
        checkmamgrlist();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checklistback:
                finish();
                break;
            case R.id.checklistmeun:
                MeunPop();
                break;
            case R.id.newcheck:
                Intent intent = new Intent(mContext, CheckNewAddActivity.class);
                intent.putExtra("orgId", orgId);
                intent.putExtra("name", name);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    //弹出框
    private void MeunPop() {
        View contentView = getPopupWindowContentView();
        mPopupWindow = new PopupWindow(contentView,
                Dates.withFontSize(resolution) + 20, Dates.higtFontSize(resolution), true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置好参数之后再show
        // 默认在mButton2的左下角显示
        mPopupWindow.showAsDropDown(checklistmeun);
        backgroundAlpha(0.5f);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());
    }

    //设置pop的点击事件
    private View getPopupWindowContentView() {
        // 一个自定义的布局，作为显示的内容
        int layoutId = R.layout.checkmanagementlist_pop;
        View contentView = LayoutInflater.from(this).inflate(layoutId, null);
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pages = 1;
                list.clear();
                switch (v.getId()) {
                    case R.id.pop_All:
                        status = "3";
                        break;
                    case R.id.pop_financial:
                        status = "1";
                        break;
                    case R.id.pop_manage:
                        status = "0";
                        break;
                    default:
                        break;
                }
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
                checkmamgrlist();
            }
        };

        contentView.findViewById(R.id.pop_All).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_financial).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_manage).setOnClickListener(menuItemOnClickListener);
        return contentView;
    }

    //界面亮度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    //编辑页面新增或者修改后刷新界面
    @Override
    public void taskCallback() {
        pages = 1;
        list.clear();
        checkmamgrlist();
    }

    /**
     * popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }

    public void checkmamgrlist() {
        PostRequest mPostRequest = OkGo.<String>post(Requests.CHECKMANGERLIST)
                .params("orgId", orgId)
                .params("page", pages)
                .params("size", 20);
        if ("1".equals(status) || "0".equals(status)) {
            mPostRequest.params("status", status)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            checkjson.taskmanagerlist(s, list, mAdapter);
                            smartrefreshlayout.finishRefresh(true);
                            smartrefreshlayout.finishLoadmore(true);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            ToastUtils.showShortToast("请求失败");
                        }
                    });
        } else {
            mPostRequest.execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    checkjson.taskmanagerlist(s, list, mAdapter);
                    smartrefreshlayout.finishRefresh(true);
                    smartrefreshlayout.finishLoadmore(true);
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    super.onError(call, response, e);
                    ToastUtils.showShortToast("请求失败");
                }
            });

        }

    }

    public void submit(String  id) {
        Intent intent = new Intent(mContext, CheckNewAddActivity.class);
        intent.putExtra("orgId", orgId);
        intent.putExtra("name", name);
        intent.putExtra("taskId",id);
        startActivity(intent);
    }

}
