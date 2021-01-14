package com.example.administrator.fengji.pzgc.activity.check.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.utils.ToastUtils;
import com.example.administrator.fengji.pzgc.adapter.SettingAdapter;
import com.example.administrator.fengji.pzgc.bean.MoretasklistBean;
import com.example.baselibrary.base.BaseActivity;
import com.example.administrator.fengji.pzgc.utils.Dates;
import com.example.baselibrary.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on
 */

/**
 * description: 检查模块——下发通知的选择联系人
 *
 * @author lx
 *         date: 2018/8/8 0008 上午 9:37
 *         update: 2018/8/7 0007
 *         version:
 */

public class CheckuserActivity extends BaseActivity implements View.OnClickListener {
    private ListView expandableListView;
    private TextView comtitle;
    private Context mContext;
    private ArrayList<MoretasklistBean> list;
    private ArrayList<MoretasklistBean> search = new ArrayList<>();
    private SettingAdapter mAdapter;
    String orgId;
    private TextView deleteSearch;
    private EditText searchEditext;
    private RelativeLayout listSearch;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkuser_list);
        mContext = CheckuserActivity.this;
        Intent intent = getIntent();
        orgId = intent.getStringExtra("orgId");
        listSearch = (RelativeLayout) findViewById(R.id.list_search);
        listSearch.setVisibility(View.VISIBLE);
        //搜索按钮
        deleteSearch = (TextView) findViewById(R.id.delete_search);
        //搜索框
        searchEditext = (EditText) findViewById(R.id.search_editext);
        expandableListView = (ListView) findViewById(R.id.wbs_listview);
        comtitle = (TextView) findViewById(R.id.com_title);
        comtitle.setText("选择责任人");
        findViewById(R.id.com_back).setOnClickListener(this);
        mAdapter = new SettingAdapter<MoretasklistBean>(list, R.layout.checkuser_item) {
            @Override
            public void bindView(SettingAdapter.ViewHolder holder, final MoretasklistBean obj) {
                holder.setText(R.id.content_name, obj.getPartContent());
                holder.setText(R.id.content_zhiw, obj.getUploadTime());
                holder.setOnClickListener(R.id.check_user, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent newpush = new Intent();
                        newpush.putExtra("name", obj.getPartContent());
                        newpush.putExtra("id", obj.getId());
                        newpush.putExtra("userid", obj.getUserid());
                        //回传数据到主Activity
                        setResult(2, newpush);
                        finish(); //此方法后才能返回主Activity
                    }
                });
            }
        };
        expandableListView.setAdapter(mAdapter);
        expandableListView.setEmptyView(findViewById(R.id.nullposion));
        OkGo.post(Requests.GET_PERSON_DATA_APP)
                .params("orgId", orgId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                list = new ArrayList<MoretasklistBean>();
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json = jsonArray.getJSONObject(i);
                                        String id = json.getString("id");
                                        String name = json.getString("name");
                                        String position = json.getString("position");
                                        String user_id = json.getString("user_id");
                                        list.add(new MoretasklistBean(position, name, id, user_id));
                                    }
                                }
                            }
                            search.addAll(list);
                            mAdapter.getData(search);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        /**
         * @内容: 搜索
         * @author lx
         * @date: 2018/12/18 0018 下午 3:17
         */
        deleteSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = searchEditext.getText().toString();
                if (!content.isEmpty()) {
                    search.clear();
                    for (int i = 0; i < list.size(); i++) {
                        String name = list.get(i).getPartContent();
                        if (name.contains(content)) {
                            search.add(list.get(i));
                        }
                    }
                    mAdapter.getData(search);
                    Dates.hintKeyBoard(CheckuserActivity.this);
                } else {
                    ToastUtils.showLongToast("搜索内容不能为空");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                Dates.hintKeyBoard(CheckuserActivity.this);
                finish();
                break;
            default:
                break;
        }
    }

    public void getdata(String name, String id) {
        Intent intent = new Intent();
        intent.putExtra("name", name);
        intent.putExtra("id", id);
        setResult(3, intent);
        finish();
    }


}
