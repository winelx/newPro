package com.example.administrator.newsdf.activity.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.work.TaskWbsActivity;
import com.example.administrator.newsdf.adapter.Listinter_Adfapter;
import com.example.administrator.newsdf.adapter.TaskPhotoAdapter;
import com.example.administrator.newsdf.bean.List_interface;
import com.example.administrator.newsdf.bean.PhotoBean;
import com.example.administrator.newsdf.camera.ToastUtils;
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
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

import static com.lzy.okgo.OkGo.post;


/**
 * @author lx
 *         列表界面
 */
public class LightfaceActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private Listinter_Adfapter mAdapter = null;
    //任务界面数据集合
    private ArrayList<List_interface> mData;
    private TextView titlew;
    private PopupWindow mPopWindow;
    private EditText searchEditext;
    private String id, wbsid, intentBack;
    private int s = 1, page = 1;
    //侧拉界面数据集合
    private ArrayList<PhotoBean> imagePaths;
    boolean popwind = false;
    private LinearLayout imageView;
    private String status = "0";
    private DrawerLayout drawerLayout;
    /**
     * 判断是否刷新还是上拉加载
     */
    private String notall = "false";
    //下拉刷新控件
    private SmartRefreshLayout refreshLayout;

    private CircleImageView fab;
    private TaskPhotoAdapter taskAdapter;
    /**
     * 判断是上拉还是下拉
     */
    //侧拉界面
    private static boolean drew = true;
    //任务列表
    private static boolean swip = false;
    String titles;
    private String wbsId, name;
    private Dates mDate;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listinterface);
        mContext = getApplicationContext();
        mDate = new Dates();
        //清除小红点
        mDate.getclear();
        Dates.getDialog(LightfaceActivity.this, "请求数据中...");
        //拿到上一个界面传递的数据，
        Intent intent = getIntent();
        try {
            name = intent.getExtras().getString("name");
            id = intent.getExtras().getString("orgId");
            wbsId = intent.getExtras().getString("orgId");
            intentBack = intent.getExtras().getString("back");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        //任务列表
        mData = new ArrayList<>();
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
        ListView uslistView = (ListView) findViewById(R.id.list_recycler);
        //搜索
        searchEditext = (EditText) findViewById(R.id.search_editext);
        //侧拉
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ListView drawerLayoutList = (ListView) findViewById(R.id.drawer_layout_list);
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
        mAdapter = new Listinter_Adfapter(mContext, mData);
        //标题
        titlew.setText(intent.getExtras().getString("name"));
        //标题文字大小
        titlew.setTextSize(17);
        //侧拉界面数据适配
        taskAdapter = new TaskPhotoAdapter(imagePaths, LightfaceActivity.this);
        //侧拉界面数据填充
        drawerLayoutList.setAdapter(taskAdapter);
        //任务界面数据填充
        uslistView.setAdapter(mAdapter);
        //搜索框
        searchEditext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //是否是回车键
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    //隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(LightfaceActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //如歌pop开着，就关闭
                    if (popwind) {
                        mPopWindow.dismiss();
                    }
                    //初始化页数为第一页
                    s = 1;
                    //当前为刷新数据。设置false 加载数据时清除之前的
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
                    if (popwind) {
                        mPopWindow.dismiss();
                    }
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(LightfaceActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });


        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //初始化页数为第一页
                s = 1;
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
                    mData.clear();
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
                    s = s + 1;
                    //已处理或未处理
                    okgo(null, status, null, s);
                } else if (Objects.equals(notall, "true")) {
                    s = s + 1;
                    //已处理或未处理
                    okgo(null, status, null, s);
                } else if (Objects.equals(notall, "all")) {
                    s = s + 1;
                    //全部
                    okgoall(null, null, s);
                } else if (Objects.equals(notall, "search")) {
                    s = s + 1;
                    //搜索
                    search(s);
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
                photoAdm(wbsid);
                //传入false表示加载失败
                refreshlayout.finishLoadmore(1500);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!popwind) {
                    PopupWindow();//打开弹出框
                    popwind = true;
                } else {
                    mPopWindow.dismiss();
                    popwind = false;
                }
            }
        });
        //listview的点击事件
        uslistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (mData.get(position).getIsFinish() + "") {
                    //回复转发(自己回复或选择转发)
                    case "0":
                        //未上传进入详情
                        Intent intent = new Intent(mContext, AuditparticularsActivity.class);
                        intent.putExtra("frag_id", mData.get(position).getTaskId());
                        intent.putExtra("wbsid", mData.get(position).getWbsId());
                        intent.putExtra("status", "one");
                        startActivity(intent);
                        break;
                    //通过的详情
                    case "1":
                        Intent audio = new Intent(mContext, AuditparticularsActivity.class);
                        audio.putExtra("frag_id", mData.get(position).getTaskId());
                        audio.putExtra("wbsid", mData.get(position).getWbsId());
                        audio.putExtra("status", "two");
                        startActivity(audio);
                        break;
                    default:
                        break;

                }
            }
        });
        //list在 触摸是判断弹窗是否打开， 如果打开关闭
        uslistView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        // 触摸移动时的操作
                        if (popwind) {
                            mPopWindow.dismiss();
                        }
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
                if (popwind) {
                    mPopWindow.dismiss();
                }
                page = 1;
                drew = true;
                photoAdm(wbsid);
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
                photoAdm(wbsid);
                //传入false表示加载失败
                refreshlayout.finishLoadmore(1500);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onStart() {
        super.onStart();
        //初始化页数为第一页
        s = 1;
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
            //搜索
            search(1);

        }
    }

        //搜索方法
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
     * meun弹出框
     */
    void PopupWindow() {
        View contentView = LayoutInflater.from(LightfaceActivity.this).inflate(R.layout.popuplayout, null);
        mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        contentView.findViewById(R.id.pop_computer).setOnClickListener(this);
        contentView.findViewById(R.id.pop_financial).setOnClickListener(this);
        contentView.findViewById(R.id.pop_manage).setOnClickListener(this);
        contentView.findViewById(R.id.pop_All).setOnClickListener(this);
        contentView.findViewById(R.id.recycler_view).setOnClickListener(this);
        mPopWindow.showAsDropDown(imageView, 0, 0);
        popwind = true;
        searchEditext.clearFocus();
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(LightfaceActivity.this.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
            case R.id.recycler_view:
                mPopWindow.dismiss();
                popwind = false;
                break;
            //选择wbs
            case R.id.pop_computer:
                mPopWindow.dismiss();
                Intent intent = new Intent(LightfaceActivity.this, TaskWbsActivity.class);
                intent.putExtra("data", "List");
                intent.putExtra("WbsID", wbsId);
                intent.putExtra("wbsname", name);
                startActivityForResult(intent, 1);
                popwind = false;
                break;
            //全部
            case R.id.pop_All:
                Dates.getDialog(LightfaceActivity.this, "请求数据中...");
                mData.clear();
                searchEditext.setText("");
                s = 1;
                status = "3";
                notall = "all";
                okgoall(null, null, 1);
                mPopWindow.dismiss();
                popwind = false;
                break;
            //未处理
            case R.id.pop_financial:
                Dates.getDialog(LightfaceActivity.this, "请求数据中...");
                mData.clear();
                searchEditext.setText("");
                s = 1;
                notall = "false";
                status = "0";
                okgo(null, status, null, 1);
                mPopWindow.dismiss();
                popwind = false;
                break;
            //已处理
            case R.id.pop_manage:
                Dates.getDialog(LightfaceActivity.this, "请求数据中...");
                searchEditext.setText("");
                s = 1;
                mData.clear();
                notall = "true";
                status = "1";
                okgo(null, status, null, 1);
                mPopWindow.dismiss();
                popwind = false;
                break;
            //返回
            case R.id.com_back:
                if (intentBack != null) {
                    finish();
                } else {
                    finish();
                }
                break;
            case R.id.search_img:
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
                .params("orgId", id)
                .params("page", i)
                .params("rows", 25)
                .params("wbsId", wbsId)
                .params("msgStatus", msgStatus)
                .params("content", content)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //判断是否是下拉还是上拉
                        if (!swip) {
                            mData.clear();
                        }
                        //判断是否请求到数据
                        if (s.contains("data")) {
                            getJson(s);
                        } else {
                            ToastUtils.showShortToast("没有更多数据了！");
                        }
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
                .params("orgId", id)
                .params("page", i)
                .params("rows", 25)
                .params("wbsId", wbsId)
                .params("msgStatus", msgStatus)
                .params("content", content)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.contains("data")) {
                            if (!swip) {
                                mData.clear();
                            }
                            getJson(s);
                        } else {
                            ToastUtils.showShortToast("没有更多数据了！");

                        }
                    }
                });
    }

    /**
     * 搜索
     */
    void searchokgo1(String wbsId, String content, int i) {
        notall = "search";
        OkGo.post(Request.CascadeList)
                .params("orgId", id)
                .params("page", i)
                .params("rows", 25)
                .params("wbsId", wbsId)
                .params("content", content)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.contains("data")) {
                            if (!swip) {
                                mData.clear();
                            }
                            getJson(s);
                        } else {
                            ToastUtils.showShortToast("没有更多数据了！");
                        }
                    }
                });
    }

    /**
     * 请求全部数据
     */
    void okgoall(String wbsId, String content, int i) {
        post(Request.CascadeList)
                .params("orgId", id)
                .params("page", i)
                .params("rows", 25)
                .params("wbsId", wbsId)
                .params("content", content)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.contains("data")) {
                            if (!swip) {
                                mData.clear();
                            }
                            getJson(s);
                        } else {
                            ToastUtils.showShortToast("没有更多数据了！");
                            mData.clear();
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    /**
     * result返回事件处理
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断是不是Activity的返回，
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            titles = data.getStringExtra("titles");
            titlew.setText(title);
            wbsid = data.getStringExtra("id");
            popwind = data.getBooleanExtra("iswbs", false);
            if (popwind) {
                fab.setVisibility(View.VISIBLE);
            } else {
                fab.setVisibility(View.GONE);
            }
            page = 1;
            photoAdm(wbsid);
            okgoall(wbsid, null, 1);
            mData.clear();
            searchEditext.setText("");
            s = 1;
            notall = "false";
            status = "0";
            okgo(null, status, null, 1);
            mPopWindow.dismiss();
            popwind = false;
        }
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
        if (popwind) {
            mPopWindow.dismiss();
        }
    }

    /**
     * 解析json
     *
     * @param s
     */
    void getJson(String s) {
        refreshLayout.finishRefresh(true);
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
                mData.add(new List_interface(id, taskId, cascadeId, isFinish, content, groupName, createTime, pointName, wbsPath, wbsId));
            }
            mAdapter.getDate(mData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 查询图册
     */
    private void photoAdm(final String string) {
        OkGo.post(Request.Photolist)
                .params("WbsId", string)
                .params("page", page)
                .params("rows", 30)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.contains("data")) {
                            if (drew) {
                                imagePaths.clear();
                            }
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    String id = (String) json.get("id");
                                    String filePath = (String) json.get("filePath");
                                    String drawingNumber = (String) json.get("drawingNumber");
                                    String drawingName = (String) json.get("drawingName");
                                    String drawingGroupName = (String) json.get("drawingGroupName");
                                    filePath = Request.networks + filePath;
                                    imagePaths.add(new PhotoBean(id, filePath, drawingNumber, drawingName, drawingGroupName));
                                }
                                taskAdapter.getData(imagePaths, titles);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (drew) {
                                imagePaths.clear();
                                imagePaths.add(new PhotoBean(id, "暂无数据", "暂无数据", "暂无数据", "暂无数据"));
                            }
                            taskAdapter.getData(imagePaths, titles);
                        }

                    }
                });
    }

}
