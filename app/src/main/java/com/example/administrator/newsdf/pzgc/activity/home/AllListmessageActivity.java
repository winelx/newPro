package com.example.administrator.newsdf.pzgc.activity.home;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.newsdf.App;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.AllTaskListItem;
import com.example.administrator.newsdf.pzgc.Adapter.TaskPhotoAdapter;
import com.example.administrator.newsdf.pzgc.activity.home.utils.HomeUtils;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.bean.Inface_all_item;
import com.example.administrator.newsdf.pzgc.bean.OrganizationEntity;
import com.example.administrator.newsdf.pzgc.bean.PhotoBean;
import com.example.administrator.newsdf.pzgc.callback.TaskCallback;
import com.example.administrator.newsdf.pzgc.callback.TaskCallbackUtils;
import com.example.baselibrary.view.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.FloatMeunAnims;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.ScreenUtil;
import com.example.administrator.newsdf.treeView.Node;
import com.example.administrator.newsdf.treeView.TaskTreeListViewAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;


/**
 * description:全部消息的列表界面，
 *
 * @author: lx
 * date: 2018/2/6 0006 上午 9:43
 * update: 2018/2/6 0006
 * version:
 */
public class AllListmessageActivity extends BaseActivity implements View.OnClickListener, TaskCallback {
    private Context mContext;

    private TextView Titlew, deleteSearch, drawer_layout_text;
    private EditText searchEditext;
    private String id, wbsid, name, titles;
    private String notall = "2", nodeiD = "1";
    //抽屉控件
    private DrawerLayout drawerLayout;
    //判断是否是加载更多
    private boolean swip = false;
    private boolean drew = true;
    private PopupWindow mPopupWindow;
    private ListView mTree, drawerLayoutList;
    private TaskPhotoAdapter taskAdapter;
    //状态值
    private int addPosition;
    private int page = 1;
    private int pages = 1;
    //图册
    private ArrayList<Audio> paths;
    private ArrayList<Inface_all_item> Alldata;
    private ArrayList<PhotoBean> imagePaths, stardPaths;
    private List<OrganizationEntity> mTreeDatas;
    private ArrayList<OrganizationEntity> organizationList;
    private ArrayList<OrganizationEntity> addOrganizationList;

    private LinearLayout drawerContent, imageViewMeun, nullposion;
    private SmartRefreshLayout refreshLayout, drawerlayoutSmart;

    private TaskTreeListViewAdapter<OrganizationEntity> mTreeAdapter;
    //根据评论分辨率返回的尺寸
    private float ste;
    //动画类
    private FloatMeunAnims floatMeunAnims;
    private CircleImageView fab;
    private LinearLayout meunStandard, meunPhoto;
    private boolean liststatus = true;
    boolean anim = true;
    private AllTaskListItem adapters;
    private RecyclerView recyclerAtt;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listtread);
        addActivity(this);
        TaskCallbackUtils.setCallBack(this);
        floatMeunAnims = new FloatMeunAnims();
        //获取屏幕对比比例1DP=？PX 比例有 1 ，2 ，3 ，4
        ste = ScreenUtil.getDensity(App.getInstance());
        Dates.getDialog(AllListmessageActivity.this, "请求数据中...");
        mContext = AllListmessageActivity.this;
        Intent intent = getIntent();
        try {
            id = intent.getExtras().getString("orgId");
            wbsid = intent.getExtras().getString("orgId");
            name = intent.getExtras().getString("name");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        //初始化集合
        initArray();
        //初始化控件
        findbyId();
        initData();
        /**
         *    侧拉listview上拉加载
         */
        drawerlayoutSmart.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                drew = false;
                if (liststatus) {
                    //加载图片
                    HomeUtils.photoAdm(nodeiD, page, imagePaths, drew, taskAdapter, titles);
                } else {
                    //加载标准
                    HomeUtils.getStard(nodeiD, page, stardPaths, drew, taskAdapter, titles);

                }
                refreshlayout.finishLoadmore(1000);

            }
        });
        /**
         *   下拉刷新
         */
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                swip = false;
                pages = 1;
                smart();

                //传入false表示刷新失败
                refreshlayout.finishRefresh(800);
            }
        });
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                swip = true;
                pages++;
                smart();
                //传入false表示加载失败
                refreshlayout.finishLoadmore(800);
            }
        });

        /**
         * editext回车键搜索
         */
        searchEditext.setOnKeyListener(new View.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //是否是回车键
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    //隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(AllListmessageActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    swip = false;
                    pages = 1;
                    String search = searchEditext.getText().toString();
                    if (search.length() != 0) {
                        smart();
                    } else {
                        Toast.makeText(mContext, "输入框为空，请输入搜索内容！", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
        searchEditext.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    // 触摸移动时的操作
                    deleteSearch.setVisibility(View.VISIBLE);
                } else {
                    deleteSearch.setVisibility(View.GONE);
                    // 此处为失去焦点时的处理内容
                }
            }
        });
        //搜索框的取消按钮
        deleteSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditext.setText("");
            }
        });
        imageViewMeun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditext.clearFocus();//失去焦点
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(AllListmessageActivity.this.getCurrentFocus()
                                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                //打开弹出框
                MeunPop();
            }
        });

        okgoall(null, null, page);
        OrganizationEntity bean = new OrganizationEntity(wbsid, "",
                name, "0", false,
                true, "3,5", "",
                "", "", name, "", true);
        organizationList.add(bean);
        getOrganization(organizationList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
    private void getOrganization(ArrayList<OrganizationEntity> organizationList) {
        if (organizationList != null) {
            for (OrganizationEntity entity : organizationList) {
                String departmentName = entity.getDepartname();
                OrganizationEntity bean = new OrganizationEntity(entity.getId(), entity.getParentId(),
                        departmentName, entity.getIsleaf(), entity.iswbs(),
                        entity.isparent(), entity.getTypes(), entity.getUsername(),
                        entity.getNumber(), entity.getUserId(), entity.getTitle(), entity.getPhone(), entity.isDrawingGroup());
                mTreeDatas.add(bean);
            }
            try {
                mTreeAdapter = new TaskTreeListViewAdapter<>(mTree, this,
                        mTreeDatas, 0);
                mTree.setAdapter(mTreeAdapter);
                mTreeAdapter.getStatus("all");
                initEvent();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    private void initEvent() {
        mTreeAdapter.setOnTreeNodeClickListener(new com.example.administrator.newsdf.treeView.TreeListViewAdapter.OnTreeNodeClickListener() {
            @Override
            public void onClick(com.example.administrator.newsdf.treeView.Node node, int position) {
                //判断是否是字节点，
                if (node.isLeaf()) {
                } else {
                    //  如果不是，判断该节点是否有数据，
                    if (node.getChildren().size() == 0) {
                        //  如果没有，就请求数据，
                        addOrganizationList.clear();
                        addPosition = position;
                        if (node.isperent()) {
                            //从拿到该节点的名称和id
                            addOrganiztion(node.getId(), node.iswbs(), node.isperent(), node.getType());
                        }
                    }
                }
            }
        });
    }

    //wsb树的数据
    void addOrganiztion(final String id, final boolean iswbs,
                        final boolean isparent, String type) {
        Dates.getDialogs(AllListmessageActivity.this, "请求数据中");
        OkGo.post(Requests.WBSTress)
                .params("nodeid", id)
                .params("iswbs", iswbs)
                .params("isparent", isparent)
                .params("type", type)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Call call, Response response) {
                        addOrganizationList(result);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Dates.disDialog();
                    }
                });

    }

    /**
     * 解析SoapObject对象
     *
     * @return
     */
    private void addOrganizationList(String result) {
        if (result.contains("data")) {
            /**
             * 解析数据
             */
            addOrganizationList = HomeUtils.parseOrganizationList(result);
            /**
             * 动态添加
             */
            HomeUtils.addOrganizationList(addOrganizationList, addPosition, mTreeAdapter);
            Dates.disDialog();
        } else {
            Dates.disDialog();
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            //返回
            case R.id.com_back:
                finish();
                break;
            case R.id.search_img:
                break;
            case R.id.fab:
                if (anim) {
                    floatMeunAnims.doclickt(meunPhoto, meunStandard, fab);
                    anim = false;
                } else {
                    floatMeunAnims.doclicktclose(meunPhoto, meunStandard, fab);
                    anim = true;
                }
                break;
            case R.id.meun_photo:
                //请求图纸
                //加载第一页
                page = 1;
                //请求数据时清除之前的
                drew = true;
                //网络请求
                drawer_layout_text.setText("图纸");
                imagePaths.clear();
                taskAdapter.getData(imagePaths, "");
                Dates.getDialog(AllListmessageActivity.this, "请求数据中...");
                HomeUtils.photoAdm(nodeiD, page, imagePaths, drew, taskAdapter, titles);
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
                drawer_layout_text.setText("标准");
                imagePaths.clear();
                taskAdapter.getData(imagePaths, "");
                Dates.getDialog(AllListmessageActivity.this, "请求数据中...");
                HomeUtils.getStard(nodeiD, page, stardPaths, drew, taskAdapter, titles);
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        //传入false表示刷新失败
        refreshLayout.finishRefresh(true);
    }

    //设置pop的点击事件
    private View getPopupWindowContentView() {
        // 一个自定义的布局，作为显示的内容
        // 布局ID
        int layoutId = R.layout.popuplayout;
        View contentView = LayoutInflater.from(this).inflate(layoutId, null);
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.pop_computer:
                        drawerLayout.openDrawer(GravityCompat.END);
                        break;
                    case R.id.pop_All:
                        Dates.getDialog(AllListmessageActivity.this, "请求数据中...");
                        searchEditext.setText("");
                        pages = 1;
                        swip = false;
                        notall = "10";
                        recyclerAtt.scrollToPosition(0);
                        if (nodeiD != "1") {
                            okgoall(nodeiD, null, pages);
                        } else {
                            okgoall(null, null, pages);
                        }
                        break;
                    case R.id.pop_financial:
                        Dates.getDialog(AllListmessageActivity.this, "请求数据中...");
                        searchEditext.setText("");
                        pages = 1;
                        swip = false;
                        notall = "0";
                        recyclerAtt.scrollToPosition(0);
                        if (nodeiD != "1") {
                            okgoall(nodeiD, null, pages);
                        } else {
                            okgoall(null, null, pages);
                        }

                        break;
                    case R.id.pop_manage:
                        Dates.getDialog(AllListmessageActivity.this, "请求数据中...");
                        searchEditext.setText("");
                        pages = 1;
                        swip = false;
                        notall = "2";
                        recyclerAtt.scrollToPosition(0);
                        if (nodeiD != "1") {
                            okgoall(nodeiD, null, pages);
                        } else {
                            okgoall(null, null, pages);
                        }
                        break;
                    case R.id.pop_backup:
                        Dates.getDialog(AllListmessageActivity.this, "请求数据中...");
                        searchEditext.setText("");
                        pages = 1;
                        swip = false;
                        notall = "3";
                        recyclerAtt.scrollToPosition(0);
                        if (nodeiD != "1") {
                            okgoall(nodeiD, null, pages);
                        } else {
                            okgoall(null, null, pages);
                        }
                        break;
                    default:
                        break;
                }
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        };
        contentView.findViewById(R.id.pop_computer).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_All).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_financial).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_manage).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_backup).setOnClickListener(menuItemOnClickListener);
        return contentView;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void taskCallback() {
        swip = false;
        pages = 1;
        smart();
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

    //暴露给adapter调用
    public void switchAct(Node node) {
        if (node.iswbs()) {
            drawerLayout.closeDrawer(drawerContent);
            titles = node.getTitle();
            Titlew.setText(node.getName());
            nodeiD = node.getId();
            fab.setVisibility(View.VISIBLE);
            swip = false;
            page = 1;
            pages = 1;
            okgoall(nodeiD, null, pages);
        }
    }

    //初始化集合
    private void initArray() {
        Alldata = new ArrayList<>();
        imagePaths = new ArrayList<>();
        mTreeDatas = new ArrayList<>();
        addOrganizationList = new ArrayList<>();
        organizationList = new ArrayList<>();
        stardPaths = new ArrayList<>();
    }

    //初始化控件
    private void findbyId() {
        nullposion = (LinearLayout) findViewById(R.id.nullposion);
        recyclerAtt = (RecyclerView) findViewById(R.id.recycler_att);
        recyclerAtt.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //滑动关闭软键盘
                searchEditext.clearFocus();//失去焦点
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(AllListmessageActivity.this.getCurrentFocus()
                                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //触摸关闭图册和标准
                if (!anim) {
                    floatMeunAnims.doclicktclose(meunPhoto, meunStandard, fab);
                    anim = true;
                }

            }
        });
        drawer_layout_text = (TextView) findViewById(R.id.drawer_layout_text);
        //获得控件id，初始化id
        drawerContent = (LinearLayout) findViewById(R.id.drawer_content);
        drawerLayoutList = (ListView) findViewById(R.id.drawer_layout_list);
        mTree = (ListView) findViewById(R.id.wbslist);
        deleteSearch = (TextView) findViewById(R.id.delete_search);
        //图册圆形控件
        fab = (CircleImageView) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        //侧拉布局
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //列表界面listview的下拉
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);

        //侧拉界面下拉
        drawerlayoutSmart = (SmartRefreshLayout) findViewById(R.id.drawerLayout_smart);
        findViewById(R.id.com_back).setOnClickListener(this);
        //标题
        Titlew = (TextView) findViewById(R.id.com_title);
        //meun
        imageViewMeun = (LinearLayout) findViewById(R.id.com_img);
        //搜索
        searchEditext = (EditText) findViewById(R.id.search_editext);
        meunStandard = (LinearLayout) findViewById(R.id.meun_standard);
        meunPhoto = (LinearLayout) findViewById(R.id.meun_photo);
        meunStandard.setVisibility(View.GONE);
        meunPhoto.setVisibility(View.GONE);
        meunPhoto.setOnClickListener(this);
        meunStandard.setOnClickListener(this);
        fab.setOnClickListener(this);

        recyclerAtt.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerAtt.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //初始化适配器
        adapters = new AllTaskListItem(Alldata, mContext, "all");
        adapters.setHasStableIds(true);
        recyclerAtt.setAdapter(adapters);
        adapters.setOnItemClickListener(new AllTaskListItem.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String status = Alldata.get(position).getIsFinish() + "";
                if ("2".equals(status)) {
                    Intent intent = new Intent(mContext, TaskdetailsActivity.class);
                    intent.putExtra("TaskId", Alldata.get(position).getTaskId());
                    intent.putExtra("wbsid", Alldata.get(position).getWbsId());
                    intent.putExtra("status", "true");
                    intent.putExtra("activity", "all");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, MoretaskActivity.class);
                    intent.putExtra("TaskId", Alldata.get(position).getTaskId());
                    intent.putExtra("wbsid", Alldata.get(position).getWbsId());
                    intent.putExtra("status", "true");
                    intent.putExtra("activity", "all");
                    startActivity(intent);
                }
            }
        });

    }

    //初始化数据
    private void initData() {
        //关闭边缘滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //侧滑栏关闭手势滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //展示侧拉界面后，背景透明度（当前透明度为完全透明）
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerlayoutSmart.setEnableRefresh(false);
        //是否在加载的时候禁止列表的操作
//        refreshLayout.setDisableContentWhenLoading(true);
        //设置标题
        Titlew.setText(name);
        //设置标题字体
        Titlew.setTextSize(17);
        //打开抽屉控件的圆形控件
        //图册适配器
        taskAdapter = new TaskPhotoAdapter(imagePaths, AllListmessageActivity.this);
        //图册listview
        drawerLayoutList.setAdapter(taskAdapter);
        //抽屉控件打开后背景颜色
        drawerLayout.setScrimColor(Color.TRANSPARENT);
    }

    //界面亮度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }


    //弹出框
    private void MeunPop() {
        if (!anim) {
            floatMeunAnims.doclicktclose(meunPhoto, meunStandard, fab);
            anim = true;
        }
        View contentView = getPopupWindowContentView();
        mPopupWindow = new PopupWindow(contentView,
                Dates.withFontSize(ste) + 20, Dates.higtFontSize(ste), true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置好参数之后再show
        // 默认在mButton2的左下角显示
        mPopupWindow.setAnimationStyle(R.style.popmenu_animation);
        mPopupWindow.showAsDropDown(imageViewMeun);
        backgroundAlpha(0.5f);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());
    }


    //请求数据
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void smart() {
        String content = searchEditext.getText().toString();
        //判断是否需要传内容
        if (content.length() != 0) {
            //判断是否有节点ID
            if (nodeiD != "1") {
                okgoall(nodeiD, content, pages);
            } else {
                okgoall(null, content, pages);
            }
        } else {
            if (nodeiD != "1") {
                okgoall(nodeiD, null, pages);
            } else {
                okgoall(nodeiD, null, pages);
            }

        }
    }

    /**
     * @param wbsId   wbs ID
     * @param content 搜索内容
     * @param i       页数
     */
    private void okgoall(String wbsId, String content, int i) {
        PostRequest mPostRequest = OkGo.<String>post(Requests.CascadeList)
                .params("orgId", id)
                .params("page", i)
                .params("rows", 10)
                .params("wbsId", wbsId)
                .params("isAll", "true")
                .params("content", content);
        //如果==3 那么就不传
        if ("10".equals(notall)) {
            mPostRequest.execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    Dates.disDialog();
                    parsingjson(s);
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    super.onError(call, response, e);
                    Dates.disDialog();
                }
            });
        } else {
            mPostRequest.params("msgStatus", notall)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            Dates.disDialog();
                            parsingjson(s);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            Dates.disDialog();
                        }
                    });
        }
    }

    //返回back
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }


    //解析json
    private void parsingjson(String s) {
        String wbsPath;
        String updateDate;
        String content;
        String taskId;
        String id;
        String wbsId;
        String createTime;
        String pointName;
        int isFinish = 0;//状态
        if (!swip) {
            Alldata.clear();
        }
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray1 = jsonObject.getJSONArray("data");
            if (jsonArray1.length() > 0) {
                for (int i = 0; i < jsonArray1.length(); i++) {
                    JSONObject json = jsonArray1.getJSONObject(i);
                    JSONObject json1 = new JSONObject();
                    JSONArray json2 = new JSONArray();
                    try {
                        json1 = json.getJSONObject("children");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        json2 = json.getJSONArray("comments");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray files = new JSONArray();
                    try {
                        files = json1.getJSONArray("file");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        wbsPath = json.getString("wbsPath");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        wbsPath = "";
                    }
                    try {
                        wbsId = json.getString("wbsId");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        wbsId = "";
                    }
                    try {
                        updateDate = json.getString("updateDate");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        updateDate = "";
                    }
                    try {
                        taskId = json.getString("taskId");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        taskId = "";
                    }
                    try {
                        isFinish = json.getInt("isFinish");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        isFinish = 0;
                    }
                    try {
                        id = json.getString("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        id = "";
                    }
                    try {
                        pointName = json.getString("pointName");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        pointName = "";
                    }
                    try {
                        createTime = json.getString("createTime");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        createTime = "";
                    }
                    try {
                        content = json.getString("content");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        content = "";
                    }
                    String userId = "", protrait = "", upload_addr = "", upload_content = "", upload_time = "", uploador = "";
                    //个人信息
                    try {
                        userId = json1.getString("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        userId = "";
                    }
                    try {
                        protrait = json1.getString("portrait");
                        protrait = Requests.networks + protrait;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        protrait = "";
                    }
                    try {
                        upload_addr = json1.getString("upload_addr");
                    } catch (JSONException e) {
                        upload_addr = "";
                    }
                    try {
                        upload_time = json1.getString("upload_time");
                    } catch (JSONException e) {
                        upload_time = "";
                    }
                    try {
                        uploador = json1.getString("uploador");
                    } catch (JSONException e) {
                        uploador = "";
                    }
                    try {
                        upload_content = json1.getString("upload_content");
                    } catch (JSONException e) {
                        upload_content = "";
                    }
                    paths = new ArrayList<>();
                    ArrayList<String> pathsname = new ArrayList<>();
                    if (files.length() > 0) {
                        for (int j = 0; j < files.length(); j++) {
                            JSONObject jsonfilse = files.getJSONObject(j);
                            String filepath = jsonfilse.getString("filepath");
                            String filename = jsonfilse.getString("filename");
                            paths.add(new Audio(filename, Requests.networks + filepath));
                            pathsname.add(filename);
                        }
                    }
                    int comments = json2.length();
                    Alldata.add(new Inface_all_item(wbsPath, updateDate, content, taskId, id, wbsId, createTime,
                            pointName, isFinish, upload_time, userId, uploador, upload_content, upload_addr, protrait, paths, comments, pathsname));
                }
                if (Alldata.size() > 0) {
                    nullposion.setVisibility(View.GONE);
                } else {
                    nullposion.setVisibility(View.VISIBLE);
                }
                adapters.getData(Alldata);

            } else {
                if (!swip) {
                    Alldata.clear();
                }
                if (Alldata.size() > 0) {
                    nullposion.setVisibility(View.GONE);
                } else {
                    nullposion.setVisibility(View.VISIBLE);
                }
                adapters.getData(Alldata);
            }
            refreshLayout.finishRefresh(true);
            refreshLayout.finishLoadmore(true);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getumber(int pos) {
        String status = Alldata.get(pos).getIsFinish() + "";
        if ("2".equals(status)) {
            Intent intent = new Intent(mContext, TaskdetailsActivity.class);
            intent.putExtra("TaskId", Alldata.get(pos).getTaskId());
            intent.putExtra("wbsid", Alldata.get(pos).getWbsId());
            intent.putExtra("status", "true");
            intent.putExtra("activity", "all");
            startActivity(intent);
        } else {
            Intent intent = new Intent(mContext, MoretaskActivity.class);
            intent.putExtra("TaskId", Alldata.get(pos).getTaskId());
            intent.putExtra("wbsid", Alldata.get(pos).getWbsId());
            intent.putExtra("status", "true");
            intent.putExtra("activity", "all");
            startActivity(intent);
        }
    }
}
