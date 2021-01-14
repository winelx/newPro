package com.example.administrator.fengji.pzgc.special.programme.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.bean.Audio;
import com.example.administrator.fengji.pzgc.bean.MoretasklistBean;
import com.example.administrator.fengji.pzgc.special.programme.adapter.ProgrammeAuditorAdapter;
import com.example.administrator.fengji.pzgc.utils.EmptyUtils;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;


/**
 * @Author lx
 * @创建时间 2019/8/2 0002 15:15
 * @说明 赛选审核人
 **/

public class ProgrammeAuditorActivity extends BaseActivity implements View.OnClickListener {
    private EditText searchEditext;
    private TextView delete_search, com_button, title;
    private SmartRefreshLayout refreshlayout;
    private Context mContext;
    private RecyclerView recycler_list;
    private RelativeLayout list_search;
    private ProgrammeAuditorAdapter mAdapter;
    private EmptyUtils emptyUtils;
    //数据源
    private ArrayList<MoretasklistBean> list;
    //展示数据源
    private ArrayList<MoretasklistBean> searchlist;
    private String orgId;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_list);
        mContext = this;
        emptyUtils = new EmptyUtils(this);
        findViewById(R.id.com_back).setOnClickListener(this);
        findViewById(R.id.toolbar_menu).setOnClickListener(this);
        searchEditext = findViewById(R.id.search_editext);
        title = findViewById(R.id.com_title);
        refreshlayout = findViewById(R.id.refreshlayout);
        //禁止下拉
        refreshlayout.setEnableRefresh(false);
        //禁止上拉
        refreshlayout.setEnableLoadmore(false);
        //仿ios越界
        refreshlayout.setEnableOverScrollBounce(true);
        title.setText("选择下一节点审核人");
        com_button = findViewById(R.id.com_button);
        list_search = findViewById(R.id.list_search);
        list_search.setVisibility(View.VISIBLE);
        delete_search = findViewById(R.id.delete_search);
        delete_search.setVisibility(View.VISIBLE);
        recycler_list = findViewById(R.id.recycler_list);
        recycler_list.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ProgrammeAuditorAdapter(R.layout.adapter_programmeauditor, new ArrayList<>());
        mAdapter.setEmptyView(emptyUtils.init());
        mAdapter.openLoadAnimation();
        recycler_list.setAdapter(mAdapter);
        //editext回车键搜索
        searchEditext.setOnKeyListener(new View.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //是否是回车键
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    //隐藏键盘
                    try {
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(ProgrammeAuditorActivity.this.getCurrentFocus()
                                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    } catch (Exception e) {

                    }
                    String search = searchEditext.getText().toString();
                    if (search.length() != 0) {
                        searchlist.clear();
                        for (int i = 0; i < list.size(); i++) {
                            String name = list.get(i).getPartContent();
                            if (name.contains(search)) {
                                searchlist.add(list.get(i));
                            }
                        }
                        count = 0;
                        mAdapter.setNewData(searchlist);
                    } else {

                        Toast.makeText(mContext, "输入框为空，请输入搜索内容！", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MoretasklistBean bean = searchlist.get(position);
                if (bean.isLean()) {
                    bean.setLean(false);
                    count--;
                } else {
                    count++;
                    bean.setLean(true);
                }
                if (count > 0) {
                    com_button.setText("确定");
                } else {
                    com_button.setText("");
                }
                mAdapter.setData(position, bean);
            }
        });
        delete_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchEditext.setText("");
            }
        });
        rquest();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                finish();
                break;
            case R.id.toolbar_menu:
                if (count > 0) {
                    callback();
                }
                break;
            default:
                break;
        }
    }

    public void rquest() {
        OkGo.post(Requests.GET_PERSON_DATA_APP)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                list = new ArrayList<MoretasklistBean>();
                                searchlist = new ArrayList<>();
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json = jsonArray.getJSONObject(i);
                                        String id = json.getString("id");
                                        String name = json.getString("name");
                                        String position = json.getString("position");
                                        String user_id = json.getString("user_id");
                                        list.add(new MoretasklistBean(position, name, id, user_id, false));
                                    }
                                }
                                searchlist.addAll(list);
                                mAdapter.setNewData(searchlist);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    public void callback() {
        ArrayList<Audio> mData = new ArrayList<>();
        for (int i = 0; i < searchlist.size(); i++) {
            if (searchlist.get(i).isLean()) {
                String name = searchlist.get(i).getPartContent();
                String id = searchlist.get(i).getUserid();
                mData.add(new Audio(name, id));
            }
        }
        Intent intent = new Intent();
        intent.putExtra("list", mData);
        //回传数据到主Activity
        setResult(1, intent);
        finish(); //此方法后才能返回主Activity

    }
}
