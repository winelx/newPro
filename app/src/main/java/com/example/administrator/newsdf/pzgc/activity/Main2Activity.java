package com.example.administrator.newsdf.pzgc.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.AllTaskListItem;
import com.example.administrator.newsdf.pzgc.bean.Inface_all_item;
import com.example.administrator.newsdf.pzgc.utils.Requests;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class Main2Activity extends AppCompatActivity {
    private AllTaskListItem adapters;
    RecyclerView recycler_att;
    private Context mContext;
    private ArrayList<Inface_all_item> Alldata;
    private ArrayList<String> list1;
    private ArrayList<String> list2;
    private SmartRefreshLayout refreshLayout;
    private String id, wbsid, name;
    private String notall = "2", nodeiD = "1";
    private int pages = 1;
    private EditText searchEditext;
    private Boolean swip = true;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        try {
            id = intent.getExtras().getString("orgId");
            wbsid = intent.getExtras().getString("orgId");
            name = intent.getExtras().getString("name");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        Alldata = new ArrayList<>();
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        searchEditext = (EditText) findViewById(R.id.search_editext);
        recycler_att = (RecyclerView) findViewById(R.id.recycler_att);
        recycler_att.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler_att.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        //是否在加载完成时滚动列表显示新的内容
        refreshLayout.setEnableScrollContentWhenLoaded(true);
        //是否在刷新的时候禁止列表的操作
        refreshLayout.setDisableContentWhenRefresh(false);
        //是否在加载的时候禁止列表的操作
        refreshLayout.setDisableContentWhenLoading(false);
        adapters = new AllTaskListItem(Alldata, mContext);
        recycler_att.setAdapter(adapters);

        /**
         *   下拉刷新
         */
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Alldata.clear();
                swip = false;
                pages = 1;
                smart();
                refreshlayout.finishRefresh(1000);
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
                refreshlayout.finishLoadmore(1000);
            }
        });

        //搜索框
        searchEditext.setOnKeyListener(new View.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //是否是回车键
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    //隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(Main2Activity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //初始化页数为第一页
                    pages = 1;
                    //当前为刷新数据。false 加载数据时清除之前的
                    swip = false;
                    String searchContext = searchEditext.getText().toString();
                    //搜索
                    if (TextUtils.isEmpty(searchContext)) {
                        Toast.makeText(getApplicationContext(), "输入框为空，请输入搜索内容！", Toast.LENGTH_SHORT).show();
                    } else {
                        smart();
                    }
                }
                return false;
            }
        });
        smart();
    }

    //下拉上拉抽的网络请求方
    //请求数据
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void smart() {
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
                okgoall(nodeiD, content, pages);
            } else {
                okgoall(nodeiD, content, pages);
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
                    ArrayList paths = new ArrayList<>();
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
                if (!swip) {
                    Alldata.clear();
                }
                adapters.getData(Alldata);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
