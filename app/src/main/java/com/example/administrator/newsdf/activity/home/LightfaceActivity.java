package com.example.administrator.newsdf.activity.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.newsdf.Adapter.Listinter_Adfapter;
import com.example.administrator.newsdf.Adapter.TaskPhotoAdapter;
import com.example.administrator.newsdf.GreenDao.LoveDao;
import com.example.administrator.newsdf.GreenDao.Shop;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.baseApplication;
import com.example.administrator.newsdf.bean.List_interface;
import com.example.administrator.newsdf.bean.OrganizationEntity;
import com.example.administrator.newsdf.bean.PhotoBean;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.callback.TaskCallback;
import com.example.administrator.newsdf.callback.TaskCallbackUtils;
import com.example.administrator.newsdf.treeView.Node;
import com.example.administrator.newsdf.treeView.TaskTreeListViewAdapter;
import com.example.administrator.newsdf.treeView.TreeListViewAdapter;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.Request;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.jpush.android.api.JPushInterface;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

import static com.lzy.okgo.OkGo.post;

/**
 * @author lx
 *         我的消息列表界面
 */
public class LightfaceActivity extends AppCompatActivity implements View.OnClickListener, TaskCallback {
    private Context mContext;
    private Listinter_Adfapter mAdapter = null;
    /**
     * 任务界面数据集合
     */
    private ArrayList<List_interface> mDatas;
    /**
     * titlew 标题
     * delete_search 搜索框的取消按钮
     */
    private TextView titlew, delete_search;
    /**
     * 搜索框
     */
    private EditText searchEditext;
    /**
     * orgid 由上一个界面传过来，每次请求都需要的固定值
     * wbsid 从wsb节点返回，默认请求可以没有，
     * fixedwbsId 用在选择wbs节点，用来筛选wbs，减少节点
     */
    private String orgId, wbsid, fixedwbsId;
    /**
     * pages 任务列表 请求页数
     * page  图册请求页数
     */
    private int pages = 1, page = 1;
    /**
     * 侧拉界面数据集合
     */
    private ArrayList<PhotoBean> imagePaths;
    /**
     * 打开状态选择弹窗
     */
    private LinearLayout imageView;
    /**
     * 用来判断请求数据的状态
     * 0未完
     * 1已完成
     * 3全部
     */
    private String status = "0";
    /**
     * 抽屉控件
     */
    private DrawerLayout drawerLayout;
    /**
     * 右侧wbs节点
     */
    private ListView drawerLayoutList;
    /**
     * 判断是否刷新还是上拉加载
     */
    private String notall = "false";
    /**
     * 下拉刷新控件
     */
    private SmartRefreshLayout refreshLayout;
    /**
     * 图册按钮
     */
    private CircleImageView fab;
    /**
     * 图册适配器
     */
    private TaskPhotoAdapter taskAdapter;
    /**
     * 判断是上拉还是下拉
     */
    /**
     * drew  drew
     * 判断是否需要原本的数据
     */
    private static boolean drew = true;
    //任务列表
    private static boolean swip = false;
    /**
     * wbs的节点层级，在侧拉界面查看图片时用来拼接节点层级
     */
    private String titles;
    /**
     * 主界面的listview
     */
    private ListView uslistView;
    /**
     * 数据库集合， 保存了是否查看推送消息
     */
    private List<Shop> list;
    /**
     * 右侧抽屉布局
     */
    private LinearLayout drawer_content;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            /**
             * 删除数据库数据，改变状态
             */
            for (int i = 0; i < list.size(); i++) {
                LoveDao.deleteLove(list.get(i).getId());
            }
        }
    };
    //选项wbs的树
    private ListView mTree;
    private ArrayList<OrganizationEntity> organizationList;
    private ArrayList<OrganizationEntity> addOrganizationList;
    private List<OrganizationEntity> mTreeDatas;
    private TaskTreeListViewAdapter<OrganizationEntity> mTreeAdapter;
    private int addPosition;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listinterface);
        mContext = getApplicationContext();

        //
        TaskCallbackUtils.setCallBack(this);
        //清除小红点
        list = new ArrayList<>();
        mTreeDatas = new ArrayList<>();
        addOrganizationList = new ArrayList<>();
        organizationList = new ArrayList<>();
        list = LoveDao.JPushCart();
        Message mes = new Message();
        handler.sendMessage(mes);
        JPushInterface.clearAllNotifications(baseApplication.getInstance());
        Dates.getDialog(LightfaceActivity.this, "请求数据中...");
        //拿到上一个界面传递的数据，
        Intent intent = getIntent();
        try {
            orgId = intent.getExtras().getString("orgId");
            fixedwbsId = intent.getExtras().getString("orgId");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        //任务列表
        mDatas = new ArrayList<>();
        //图册
        imagePaths = new ArrayList<>();
        //获得控件id，初始化id
        //返回
        findViewById(R.id.com_back).setOnClickListener(this);
        //标题
        titlew = (TextView) findViewById(R.id.com_title);
        //查询图册
        fab = (CircleImageView) findViewById(R.id.fab);
        //点击pop
        imageView = (LinearLayout) findViewById(R.id.com_img);
        //任务列表
        uslistView = (ListView) findViewById(R.id.list_recycler);
        //搜索
        searchEditext = (EditText) findViewById(R.id.search_editext);
        //侧拉
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayoutList = (ListView) findViewById(R.id.drawer_layout_list);
        mTree = (ListView) findViewById(R.id.drawer_right_list);
        drawer_content = (LinearLayout) findViewById(R.id.drawer_content);
        //任务列表的下拉
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        //侧拉界面的下拉
        SmartRefreshLayout drawerlayoutSmart = (SmartRefreshLayout) findViewById(R.id.drawerLayout_smart);
        //禁止下拉
        drawerlayoutSmart.setEnableRefresh(false);
        //启用或禁用与所有抽屉的交互。
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //侧滑栏关闭手势滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //展示侧拉界面后，背景透明度（当前透明度为完全透明）
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        //隐藏图片查看，
        fab.setVisibility(View.GONE);
        //界面数据填充
        mAdapter = new Listinter_Adfapter(mContext, mDatas);
        //标题
        titlew.setText(intent.getExtras().getString("name"));
        //标题文字大小
        titlew.setTextSize(17);
        //搜索界面的取消按钮
        delete_search = (TextView) findViewById(R.id.delete_search);
        //侧拉界面数据适配
        taskAdapter = new TaskPhotoAdapter(imagePaths, LightfaceActivity.this);
        //侧拉界面数据填充
        drawerLayoutList.setAdapter(taskAdapter);
        //任务界面数据填充
        uslistView.setAdapter(mAdapter);
        //搜索框
        searchEditext.setOnKeyListener(new View.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //是否是回车键
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    //隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(LightfaceActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //初始化页数为第一页
                    pages = 1;
                    //当前为刷新数据。false 加载数据时清除之前的
                    swip = false;
                    //搜索
                    search(1);
                }
                return false;
            }
        });

        //deditext获取焦点的处理
        searchEditext.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    // 触摸移动时的操作
                    delete_search.setVisibility(View.VISIBLE);
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(LightfaceActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    delete_search.setVisibility(View.GONE);
                }
            }
        });

        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //初始化页数为第一页
                pages = 1;
                //当前为刷新数据。设置false 加载数据时清除之前的
                swip = false;
                if (Objects.equals(notall, "false")) {
                    //已处理或未处理
                    okgo(wbsid, status, null, 1);
                } else if (Objects.equals(notall, "true")) {
                    //已处理或未处理
                    okgo(wbsid, status, null, 1);
                } else if (Objects.equals(notall, "all")) {
                    //全部
                    okgoall(wbsid, null, 1);
                } else if (Objects.equals(notall, "search")) {
                    mDatas.clear();
                    //搜索
                    search(1);
                }
                //传入false表示刷新失败
                refreshlayout.finishRefresh(1500);
            }
        });

        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //上拉加载 设置为ture 解析数据时添加到 原来的数据集合
                swip = true;
                if (Objects.equals(notall, "false")) {
                    pages = pages + 1;
                    //已处理或未处理
                    okgo(null, status, null, pages);
                } else if (Objects.equals(notall, "true")) {
                    pages = pages + 1;
                    //已处理或未处理
                    okgo(null, status, null, pages);
                } else if (Objects.equals(notall, "all")) {
                    pages = pages + 1;
                    //全部
                    okgoall(null, null, pages);
                } else if (Objects.equals(notall, "search")) {
                    pages = pages + 1;
                    //搜索
                    search(pages);
                }
                //传入false表示加载失败
                refreshlayout.finishLoadmore(1500);
            }
        });

        //侧拉listview上拉加载
        drawerlayoutSmart.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                drew = false;
                homeUtils.photoAdm(wbsid, page, imagePaths, drew, taskAdapter, titles);
                //传入false表示加载失败
                refreshlayout.finishLoadmore(1500);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeunPop();//打开弹出框
                searchEditext.clearFocus();//失去焦点

            }
        });
        //listview的点击事件
        uslistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (mDatas.get(position).getIsFinish() + "") {
                    //回复转发(自己回复或选择转发)
                    case "0":
                        //未上传进入详情
//                        Intent intent = new Intent(mContext, AuditparticularsActivity.class);
                        Intent intent = new Intent(mContext, MoretaskActivity.class);
//                        intent.putExtra("frag_id", mDatas.get(position).getTaskId());
//                        intent.putExtra("wbsid", mDatas.get(position).getWbsId());
//                        intent.putExtra("status", "one");
                        startActivity(intent);
                        break;
                    //通过的详情
                    case "1":
                        //    Intent audio = new Intent(mContext, AuditparticularsActivity.class);
                        Intent audio = new Intent(mContext, MoretaskActivity.class);
//                        audio.putExtra("frag_id", mDatas.get(position).getTaskId());
//                        audio.putExtra("wbsid", mDatas.get(position).getWbsId());
//                        audio.putExtra("status", "two");
                        startActivity(audio);
                        break;
                    default:
                        break;

                }
                backgroundAlpha(1f);
            }
        });
        //listview滑动时让editext失去焦点，并且关闭软键盘
        uslistView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        // 触摸移动时的操作
                        searchEditext.clearFocus();//失去焦点
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(LightfaceActivity.this.getCurrentFocus()
                                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        //图册查看按钮，如果有弹窗，先关闭弹窗
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                drew = true;
                homeUtils.photoAdm(wbsid, page, imagePaths, drew, taskAdapter, titles);
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        /**
         *    侧拉listview上拉加载
         */
        drawerlayoutSmart.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                drew = false;
                homeUtils.photoAdm(wbsid, page, imagePaths, drew, taskAdapter, titles);
                //传入false表示加载失败
                refreshlayout.finishLoadmore(1500);
            }
        });
        /**
         * 请求任务列表
         */
        okgo(wbsid, status, null, 1);
        /**
         * 拼接 选择wbs的节点
         */
        OrganizationEntity bean = new OrganizationEntity(fixedwbsId, "",
                intent.getExtras().getString("name"), "0", false,
                true, "3,5", "",
                "", "", intent.getExtras().getString("name"), "", true);
        organizationList.add(bean);
        getOrganization(organizationList);
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
                mTreeAdapter.getStatus("mine");
                initEvent();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void initEvent() {
        mTreeAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
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

    void addOrganiztion(final String id, final boolean iswbs,
                        final boolean isparent, String type) {
        Dates.getDialogs(LightfaceActivity.this, "请求数据中");
        OkGo.post(Request.WBSTress)
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
            addOrganizationList = homeUtils.parseOrganizationList(result);
            /**
             * 动态添加
             */
            homeUtils.addOrganizationList(addOrganizationList, addPosition, mTreeAdapter);
            Dates.disDialog();
        } else {
            Dates.disDialog();
        }
    }


    //搜索方法
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void search(final int str) {
        String searchContext = searchEditext.getText().toString().trim();
        if (TextUtils.isEmpty(searchContext)) {
            Toast.makeText(getApplicationContext(), "输入框为空，请输入搜索内容！", Toast.LENGTH_SHORT).show();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (Objects.equals(status, "3")) {
                    searchokgo1(wbsid, searchContext, str);
                } else {
                    searchokgo(wbsid, status, searchContext, str);
                }
            }
        }
    }


    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            //返回
            case R.id.com_back:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 组织id查询
     */
    private void okgo(String wbsId, String msgStatus, String content, int i) {
        post(Request.CascadeList)
                .params("orgId", orgId)
                .params("page", i)
                .params("rows", 25)
                .params("wbsId", wbsId)
                .params("msgStatus", msgStatus)
                .params("content", content)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        getJson(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    /**
     * 搜索
     */

    void searchokgo(String wbsId, String msgStatus, String content, int i) {
        notall = "search";
        OkGo.post(Request.CascadeList)
                .params("orgId", orgId)
                .params("page", i)
                .params("rows", 25)
                .params("wbsId", wbsId)
                .params("msgStatus", msgStatus)
                .params("content", content)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        getJson(s);
                    }
                });
    }

    /**
     * 搜索
     */
    void searchokgo1(String wbsId, String content, int i) {
        notall = "search";
        OkGo.post(Request.CascadeList)
                .params("orgId", orgId)
                .params("page", i)
                .params("rows", 25)
                .params("wbsId", wbsId)
                .params("content", content)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        getJson(s);

                    }
                });
    }

    /**
     * 请求全部数据
     */
    void okgoall(String wbsId, String content, int i) {
        post(Request.CascadeList)
                .params("orgId", orgId)
                .params("page", i)
                .params("rows", 25)
                .params("wbsId", wbsId)
                .params("content", content)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        getJson(s);
                    }
                });
    }


    /**
     * 重写返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }

    /**
     * 界面不可见
     */
    @Override
    protected void onStop() {
        super.onStop();
        //传入false表示刷失败
        refreshLayout.finishRefresh(true);
    }

    /**
     * 解析json
     *
     * @param s
     */
    void getJson(String s) {
        refreshLayout.finishRefresh(true);
        if (!swip) {
            mDatas.clear();
        }
        if (s.contains("data")) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    String id = json.getString("id");
                    //任务id
                    String taskId = json.getString("taskId");
                    ///检查点id
                    String cascadeId = json.getString("cascadeId");
                    //任务状态，0 和1;
                    String isFinish = json.getString("isFinish");
                    //推送内容
                    String content = json.getString("content");
                    //负责人
                    String groupName = json.getString("groupName");
                    //创建时间;
                    String createTime = json.getString("createTime");
                    //检查点名称
                    String pointName = json.getString("pointName");
                    // Wbs路径;
                    String wbsPath = json.getString("wbsPath");
                    String wbsId = json.getString("wbsId");
                    mDatas.add(new List_interface(id, taskId, cascadeId, isFinish, content, groupName, createTime, pointName, wbsPath, wbsId));
                }
                mAdapter.getDate(mDatas);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtils.showShortToast("暂无数据！");
            mAdapter.getDate(mDatas);
        }
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        //0.0-1.0
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    PopupWindow mPopupWindow;

    private void MeunPop() {
        View contentView = getPopupWindowContentView();
        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置好参数之后再show
        // 默认在mButton2的左下角显示
        mPopupWindow.showAsDropDown(imageView);
        backgroundAlpha(0.5f);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());
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
                        drawerLayout.openDrawer(Gravity.END);
                        break;
                    case R.id.pop_All:
                        Dates.getDialog(LightfaceActivity.this, "请求数据中...");
                        mDatas.clear();
                        searchEditext.setText("");
                        pages = 1;
                        status = "3";
                        notall = "all";
                        okgoall(wbsid, null, 1);
                        uslistView.setSelection(0);
                        break;
                    case R.id.pop_financial:
                        Dates.getDialog(LightfaceActivity.this, "请求数据中...");
                        mDatas.clear();
                        searchEditext.setText("");
                        pages = 1;
                        notall = "false";
                        status = "0";
                        okgo(wbsid, status, null, 1);
                        uslistView.setSelection(0);
                        break;
                    case R.id.pop_manage:
                        Dates.getDialog(LightfaceActivity.this, "请求数据中...");
                        searchEditext.setText("");
                        pages = 1;
                        mDatas.clear();
                        notall = "true";
                        status = "1";
                        okgo(wbsid, status, null, 1);
                        uslistView.setSelection(0);
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
        return contentView;
    }

    /**
     * 详情页数据状态发生改变，刷新当前界面
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void taskCallback() {
        //初始化页数为第一页
        pages = 1;
        //当前为刷新数据。设置false 加载数据时清除之前的
        swip = false;
        if (Objects.equals(notall, "false")) {
            //已处理或未处理
            okgo(wbsid, status, null, 1);
        } else if (Objects.equals(notall, "true")) {
            //已处理或未处理
            okgo(wbsid, status, null, 1);
        } else if (Objects.equals(notall, "all")) {
            //全部
            okgoall(wbsid, null, 1);
        } else if (Objects.equals(notall, "search")) {
            mDatas.clear();
            //搜索
            search(1);
        }
    }

    /**
     * 弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }

    public void switchAct(Node node) {
        if (node.iswbs()) {
            //关闭抽屉控件
            drawerLayout.closeDrawer(drawer_content);
            titles = node.getTitle();
            titlew.setText(node.getName());
            wbsid = node.getId();
            fab.setVisibility(View.VISIBLE);
            swip = false;
            page = 1;
            pages = 1;
            homeUtils.photoAdm(wbsid, page, imagePaths, drew, taskAdapter, titles);
            uslistView.setSelection(0);
            status = "0";
            okgo(wbsid, status, null, pages);
        }
    }
}