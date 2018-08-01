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
import android.support.v7.app.AppCompatActivity;
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
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.AllTaskListItem;
import com.example.administrator.newsdf.pzgc.Adapter.Imageloaders;
import com.example.administrator.newsdf.pzgc.Adapter.TaskPhotoAdapter;
import com.example.administrator.newsdf.pzgc.bean.Inface_all_item;
import com.example.administrator.newsdf.pzgc.bean.OrganizationEntity;
import com.example.administrator.newsdf.pzgc.bean.PhotoBean;
import com.example.administrator.newsdf.pzgc.callback.TaskCallback;
import com.example.administrator.newsdf.pzgc.callback.TaskCallbackUtils;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.FloatMeunAnims;
import com.example.administrator.newsdf.pzgc.utils.LogUtil;
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
public class AllListmessageActivity extends AppCompatActivity implements View.OnClickListener, TaskCallback {
    private Context mContext;
    private int pages = 1;
    private String id, wbsid, name;
    private EditText searchEditext;
    private AllTaskListItem adapters;
    RecyclerView recycler_att;
    private String notall = "2", nodeiD = "1";
    private boolean swip = true;
    private ArrayList<Inface_all_item> Alldata;
    private SmartRefreshLayout refreshLayout;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listtread);
        TaskCallbackUtils.setCallBack(this);
        mContext = getApplicationContext();
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
        okgoall(null, null, pages);

    }

    private void initData() {
    }

    private void initArray() {
        Alldata = new ArrayList<>();
    }


    //初始化控件
    private void findbyId() {
        searchEditext = (EditText) findViewById(R.id.search_editext);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        recycler_att = (RecyclerView) findViewById(R.id.recycler_att);
        recycler_att.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //滑动关闭软键盘

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //触摸关闭图册和标准


            }
        });
        recycler_att.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler_att.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //初始化适配器
        adapters = new AllTaskListItem(Alldata, mContext);
        recycler_att.setAdapter(adapters);
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //初始化页数为第一页
                pages = 1;
                //当前为刷新数据。设置false 加载数据时清除之前的
                swip = false;
                smart();
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
                pages++;
                smart();
                //传入false表示加载失败
                refreshlayout.finishLoadmore(1000);
            }
        });
    }

    //下拉上拉抽的网络请求方
    //请求数据
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void smart() {
//        String content = searchEditext.getText().toString();
        String content="";
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
        ToastUtils.showShortToastCenter(i + "");
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

                    parsingjson(s);
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    super.onError(call, response, e);
                }
            });
        } else {
            mPostRequest.params("msgStatus", notall)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {

                            parsingjson(s);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
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
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray1 = jsonObject.getJSONArray("data");
            if (jsonArray1.length() != 0) {
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
                    ArrayList<String> paths = new ArrayList<>();
                    ArrayList<String> pathsname = new ArrayList<>();
                    if (files.length() > 0) {
                        for (int j = 0; j < files.length(); j++) {
                            JSONObject jsonfilse = files.getJSONObject(j);
                            String filepath = jsonfilse.getString("filepath");
                            String filename = jsonfilse.getString("filename");
                            paths.add(Requests.networks + filepath);
                            pathsname.add(filename);
                        }
                    }
                    int comments = json2.length();
                    Alldata.add(new Inface_all_item(wbsPath, updateDate, content, taskId, id, wbsId, createTime,
                            pointName, isFinish, upload_time, userId, uploador, upload_content, upload_addr, protrait, paths, comments, pathsname));
                }
                if (Alldata.size() != 0) {
                    adapters.getData(Alldata);
                }
            } else {
                adapters.getData(Alldata);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void taskCallback() {

    }
}

