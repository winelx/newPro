package com.example.administrator.newsdf.activity.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.newsdf.Adapter.MoretaskAdapter;
import com.example.administrator.newsdf.Adapter.TaskPhotoAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.same.DirectlyreplyActivity;
import com.example.administrator.newsdf.bean.Aduio_content;
import com.example.administrator.newsdf.bean.MoretasklistBean;
import com.example.administrator.newsdf.bean.PhotoBean;
import com.example.administrator.newsdf.callback.TaskCallbackUtils;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.LogUtil;
import com.example.administrator.newsdf.utils.Requests;
import com.example.administrator.newsdf.utils.SPUtils;
import com.joanzapata.iconify.widget.IconTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;


/**
 * description: 多次任务上传界面
 *
 * @author lx
 *         date: 2018/4/16 0016 上午 11:11
 *         update: 2018/4/16 0016
 *         version:
 */
public class MoretaskActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private TextView wbsNode;
    private RecyclerView mRecyclerView;
    private MoretaskAdapter mAdapter;
    private ArrayList<Aduio_content> contents;
    private ArrayList<MoretasklistBean> Dats;
    public String id, wbsid, status, taskID;
    private DrawerLayout drawerLayout;
    private String DATA = "data", userId;
    private LinearLayout newmoretask;
    private TaskPhotoAdapter taskPhotoAdapter;
    private ListView drawerLayoutList;
    private ArrayList<PhotoBean> imagePaths;
    private SmartRefreshLayout drawerLayout_smart;
    private IconTextView iconTextView;
    private CircleImageView Circlephoto, fab, Circlestandard, delete;

    /**
     * 是否需要返回后刷新界面状态
     */
    private boolean Refresh = true;
    /**
     * 请求图册的页数
     */
    private int page = 1;
    /**
     * 判断状态，是上拉还是下拉
     */
    private boolean drew = true;
    private String wbsName = "";
    Animation scaleAnimation;
    boolean anim = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moretask);
        mContext = this;
        scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.translate);
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
        OkGo();
        //请求图册图片
        getPhoto();
        //侧拉listview上拉加载
        drawerLayout_smart.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                drew = false;
                getPhoto();
                //传入false表示加载失败
                refreshlayout.finishLoadmore(1500);
            }
        });
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
        fab = (CircleImageView) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    //初始化集合
    private void initArry() {
        Dats = new ArrayList<>();
        contents = new ArrayList<>();
        imagePaths = new ArrayList<>();
    }

    //暴露给adapter的方法，给点击事件使用,跳转界面
    public void onclick(int pos) {
        Intent intent = new Intent(mContext, TaskdetailsActivity.class);
        intent.putExtra("TaskId", Dats.get(pos).getId());
        intent.putExtra("wbsid", wbsid);
        //判断能否可以跳转任务管理
        intent.putExtra("status", status);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                popwindow();
//                //加载第一页
//                page = 1;
//                //请求数据时清除之前的
//                drew = true;
//                //网络请求
//                getPhoto();
//                drawerLayout.openDrawer(GravityCompat.START);

//                if (anim) {
//                    doclickt(Circlephoto, Circlestandard);
//                    anim = false;
//                } else {
//                    doclicktclose(Circlephoto, Circlestandard);
//                    anim = true;
//                }
                break;

            case R.id.newmoretask:
                //点击回复
                Intent intent = new Intent(MoretaskActivity.this, DirectlyreplyActivity.class);
                intent.putExtra("id", taskID);
                startActivityForResult(intent, 1);
                break;
            case R.id.com_back:
                //返回
                //抛出异常，在任务管理界面返回时不需要刷新数据，
                try {
                    //判断状态是否改变
                    if (!Refresh) {
                        //改变了，调用刷新数据方法
                        TaskCallbackUtils.removeCallBackMethod();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
                break;
            case R.id.taskManagement:
                if (status.equals("true")) {
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
        Dates.getDialogs(MoretaskActivity.this, "请求数据中");
        OkGo.<String>get(Requests.ContentDetail)
                .params("id", taskID)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LogUtil.i("result", s);
                        getJson(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Dates.disDialog();
                    }
                });
    }

    //解析当前页面数据
    public void getJson(String s) {
        if (s.contains(DATA)) {
            try {
                contents.clear();
                Dats.clear();
                JSONObject jsonObject = new JSONObject(s);
                //返回数据
                JSONObject Data = jsonObject.getJSONObject("data");
                JSONObject jsonArray = Data.getJSONObject("data");

                //创建时间
                String createDate;
                try {
                    createDate = jsonArray.getString("createDate");
                } catch (JSONException e) {
                    e.printStackTrace();
                    createDate = "";
                }
                //推送天数
                String sendedTimeStr;
                try {
                    sendedTimeStr = jsonArray.getString("sendedTimeStr");
                } catch (JSONException e) {
                    e.printStackTrace();
                    sendedTimeStr = "";
                }
                //责任人
                String leaderName;
                try {
                    leaderName = jsonArray.getString("leaderName");
                } catch (JSONException e) {
                    e.printStackTrace();
                    leaderName = "";
                }
                //责任人Id
                String leaderId;
                try {
                    leaderId = jsonArray.getString("leaderId");
                } catch (JSONException e) {
                    e.printStackTrace();
                    leaderId = "";
                }
                //是否已读
                String isread = "";
                //创建人ID
                String createByUserID = "";
                //标题名称
                String name;
                try {
                    name = jsonArray.getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                    name = "";
                }
                //id
                String id;
                try {
                    id = jsonArray.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                    id = "";
                }
                //任务内容
                String content;
                try {
                    content = jsonArray.getString("content");
                } catch (JSONException e) {
                    e.printStackTrace();
                    content = "";
                }
                //状态
                String status;
                try {
                    status = jsonArray.getString("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                    status = "";
                }
                //状态
                String checkStandard;
                try {
                    checkStandard = jsonArray.getString("checkStandard");
                } catch (JSONException e) {
                    e.printStackTrace();
                    checkStandard = "";
                }
                try {
                    JSONArray parts = Data.getJSONArray("parts");
                    for (int i = 0; i < parts.length(); i++) {
                        JSONObject json = parts.getJSONObject(i);
                        String ids = json.getString("id");

                        String partContent;
                        try {
                            partContent = json.getString("partContent");
                        } catch (JSONException e) {
                            partContent = "";
                        }
                        String uploadDate;
                        try {
                            uploadDate = json.getString("updateDate");
                        } catch (JSONException e) {
                            uploadDate = "";
                        }
                        Dats.add(new MoretasklistBean(uploadDate, partContent, ids));
                    }
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                contents.add(new Aduio_content(id, name, status, content, leaderName, leaderId, isread, createByUserID, checkStandard, createDate, wbsName, null, sendedTimeStr, ""));
                mAdapter.getContent(contents, Dats);
                wbsNode.setText(jsonArray.getString("WbsName"));
                Dates.disDialog();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    //请求图册
    public void getPhoto() {
        //将请求方法封装到工具类，因为多个界面需要相同的请求
        HomeUtils.photoAdm(wbsid, page, imagePaths, drew, taskPhotoAdapter, wbsName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            taskID = data.getStringExtra("frag_id");
            newmoretask.setVisibility(View.VISIBLE);
            Refresh = false;
            OkGo();
        }
    }

    //判断id
    public String getId() {
        return taskID;
    }


//    public void Translate() {
//        AnimationSet animationSet = new AnimationSet(true);
//        TranslateAnimation translateAnimation = new TranslateAnimation(
//                //X轴的开始位置
//                Animation.RELATIVE_TO_SELF, 0f,
//                //X轴的结束位置
//                Animation.RELATIVE_TO_SELF, 0f,
//                //Y轴的开始位置
//                Animation.RELATIVE_TO_SELF, 0f,
//                //Y轴的结束位置
//                Animation.RELATIVE_TO_SELF, -1.0f);
//        //执行时间
//        translateAnimation.setDuration(1500);
//        animationSet.addAnimation(translateAnimation);
//        //插值器
//        animationSet.setInterpolator(new AccelerateInterpolator());
//
//        //置如果为true，则动画执行完之后效果定格在执行完之后的状态
//        animationSet.setFillAfter(true);
//        //设置如果为false，则动画执行完之后效果定格在执行完之后的状态
//        animationSet.setFillBefore(false);
//        //设置的是一个long类型的值，是指动画延迟多少毫秒之后执行
//        animationSet.setStartOffset(100);
//        //动画重复几次执行
//        animationSet.setDuration(1);
//        Circlephoto.startAnimation(animationSet);
//    }

    public void doclickt(View view, View view2) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator translation = ObjectAnimator.ofFloat(view, "translationY", 0f, -150f);
        ObjectAnimator translation2 = ObjectAnimator.ofFloat(view2, "translationY", -150f, -300f);
        translation.setDuration(300);
        translation2.setDuration(300);
        // 可以设置重复次数
        translation.setRepeatCount(0);
        translation2.setRepeatCount(0);
        //  可以设置插值器,使之按照一定的规律运动
        translation.setInterpolator(new BounceInterpolator());
        translation2.setInterpolator(new BounceInterpolator());
        // 开始方法
        animatorSet.play(translation2).with(translation);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            //动画开始
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                fab.setVisibility(View.GONE);
            }
        });
        animatorSet.start();
    }

    public void doclicktclose(View view, View view2) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator translation = ObjectAnimator.ofFloat(view, "translationY", -150f, 0f);
        ObjectAnimator translation2 = ObjectAnimator.ofFloat(view2, "translationY", -300f, 0f);
        translation.setDuration(100);
        translation2.setDuration(100);
        // 可以设置重复次数
        translation.setRepeatCount(0);
        translation2.setRepeatCount(0);
        //  可以设置插值器,使之按照一定的规律运动
        translation.setInterpolator(new BounceInterpolator());
        translation2.setInterpolator(new BounceInterpolator());
        // 开始方法
        animatorSet.play(translation).with(translation2);
        //为动画设置监听
        animatorSet.addListener(new AnimatorListenerAdapter() {
            //动画开始
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                fab.setVisibility(View.VISIBLE);
            }


        });
        animatorSet.start();
    }

    private PopupWindow mPopupWindow;

    public void popwindow() {
        View contentView = getPopupWindowContentView();
        mPopupWindow = new PopupWindow(contentView,
                300, 800, true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置好参数之后再show
        // 默认在mButton2的左下角显示
        mPopupWindow.showAsDropDown(fab);
        backgroundAlpha(0.5f);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());
        fab.setVisibility(View.GONE);
    }

    private View getPopupWindowContentView() {
        // 一个自定义的布局，作为显示的内容
        // 布局ID
        int layoutId = R.layout.floatbuttonmeun_anim;
        View contentView = LayoutInflater.from(this).inflate(layoutId, null);
        final View view1 = contentView.findViewById(R.id.photo_meun);
        final View view2 = contentView.findViewById(R.id.stard_meun);
        final View view3 = contentView.findViewById(R.id.dismiss_meun);
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.stard_meun:

                        break;
                    case R.id.photo_meun:

                        break;
                    case R.id.dismiss_meun:
                        doclicktclose(view1, view2);
                    default:

                        break;
                }
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        };

        view1.setOnClickListener(menuItemOnClickListener);
        view2.setOnClickListener(menuItemOnClickListener);
        view3.setOnClickListener(menuItemOnClickListener);
        return contentView;

    }


    //界面亮度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    /**
     * popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
            fab.setVisibility(View.VISIBLE);
        }
    }
}
