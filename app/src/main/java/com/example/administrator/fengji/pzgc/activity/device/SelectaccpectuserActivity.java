package com.example.administrator.fengji.pzgc.activity.device;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.utils.ToastUtils;
import com.example.administrator.fengji.pzgc.adapter.SettingAdapter;
import com.example.administrator.fengji.pzgc.bean.Audio;
import com.example.administrator.fengji.pzgc.callback.Networkinterface;
import com.example.baselibrary.base.BaseActivity;
import com.example.administrator.fengji.pzgc.utils.Dates;
import com.example.baselibrary.utils.Requests;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author lx
 * @Created by: 2018/12/13 0013.
 * @description: 查看整改问题详情
 * @Activity： 跳转界面DeviceDetailsActivity
 */

public class SelectaccpectuserActivity extends BaseActivity {
    private SmartRefreshLayout refreshLayout;
    private ListView Listview;
    private SettingAdapter adapter;
    private ArrayList<Audio> list;
    private int page = 1;
    private String orgId, checkId;
    private TextView delete_search;
    private EditText search_editext;
    private RelativeLayout list_search;
    ArrayList<Audio> search = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wbs);

        final Intent intent = getIntent();
        orgId = intent.getStringExtra("orgId");
        checkId = intent.getStringExtra("id");
        list = new ArrayList<>();
        TextView title = (TextView) findViewById(R.id.com_title);
        title.setText("选择责任人");
        list_search = (RelativeLayout) findViewById(R.id.list_search);
        list_search.setVisibility(View.VISIBLE);
        //搜索按钮
        delete_search = (TextView) findViewById(R.id.delete_search);
        //搜索框
        search_editext = (EditText) findViewById(R.id.search_editext);
        Listview = (ListView) findViewById(R.id.wbs_listview);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        //是否启用下拉刷新功能
        refreshLayout.setEnableRefresh(true);
        //是否启用上拉加载功能
        refreshLayout.setEnableLoadmore(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableOverScrollDrag(true);
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dates.hintKeyBoard(SelectaccpectuserActivity.this);
                finish();
            }
        });
        Listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent();
                intent1.putExtra("id", search.get(position).getContent());
                intent1.putExtra("name", search.get(position).getName());
                setResult(202, intent1);
                finish();
            }
        });
        //  下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                requeset(true);
            }
        });
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                requeset(false);
            }
        });
        adapter = new SettingAdapter<Audio>(list, R.layout.choose_item) {
            @Override
            public void bindView(ViewHolder holder, Audio obj) {
                holder.setText(R.id.check, obj.getName());
            }
        };
        delete_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = search_editext.getText().toString();
                if (!content.isEmpty()) {
                    search.clear();
                    for (int i = 0; i < list.size(); i++) {
                        String name = list.get(i).getName();
                        if (name.contains(content)) {
                            search.add(list.get(i));
                        }
                    }
                    Dates.hintKeyBoard(SelectaccpectuserActivity.this);
                    adapter.getData(search);
                } else {
                    ToastUtils.showLongToast("搜索内容不能为空");
                }
            }
        });
        Listview.setAdapter(adapter);
        requeset(true);
    }

    public void requeset(final boolean lean) {
        network(lean, new Networkinterface() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                //如果请求的第一页，清除之前数据
                if (page == 1) {
                    list.clear();
                    search.clear();
                }
                //获取网络请求的参数
                list.addAll((Collection<? extends Audio>) map.get("data"));
                search.addAll(list);
                //更新数据
                adapter.getData(search);
            }
        });
    }


    public void network(final boolean status, final Networkinterface networkinterface) {
        Dates.getDialogs(this, "请求数据中...");
        OkGo.get(Requests.SELECTACCPECTUSER)
                .params("page", page)
                .params("size", 1000)
                .params("orgId", orgId)
                .params("checkId", checkId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String string, Call call, Response response) {
                        Dates.disDialog();
                        if (status) {
                            refreshLayout.finishRefresh();
                        } else {
                            refreshLayout.finishLoadmore();
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                ArrayList<Audio> data = new ArrayList<>();
                                Map<String, Object> map = new HashMap<>();
                                JSONArray results = jsonObject1.getJSONArray("results");
                                for (int i = 0; i < results.length(); i++) {
                                    JSONObject json = results.getJSONObject(i);
                                    String id = json.getString("id");
                                    String realname = json.getString("realname");
                                    data.add(new Audio(realname, id));
                                    map.put("data", data);
                                    networkinterface.onsuccess(map);
                                }
                            } else {
                                ToastUtils.showLongToast(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Dates.disDialog();
                    }
                });
    }
 }


