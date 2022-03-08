package com.example.administrator.yanghu.pzgc.activity.changed;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.utils.ToastUtils;
import com.example.administrator.yanghu.pzgc.adapter.SettingAdapter;
import com.example.administrator.yanghu.pzgc.bean.MoretasklistBean;
import com.example.baselibrary.base.BaseActivity;
import com.example.administrator.yanghu.pzgc.utils.Dates;
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
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/16 0016}
 * 描述：（新）整改通知单指派联系人
 * {@link }
 */
@SuppressLint("Registered")
public class ChagedContactsActivity extends BaseActivity implements View.OnClickListener {
    private ListView expandableListView;
    private TextView comtitle;
    private ArrayList<MoretasklistBean> list;
    private ArrayList<MoretasklistBean> search = new ArrayList<>();
    private SettingAdapter mAdapter;
    private String orgId;
    private TextView deleteSearch;
    private EditText searchEditext;
    private RelativeLayout listSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkuser_list);
        Intent intent = getIntent();
        orgId = intent.getStringExtra("orgId");
        listSearch = (RelativeLayout) findViewById(R.id.list_search);
        listSearch.setVisibility(View.VISIBLE);
        //搜索按钮
        deleteSearch = (TextView) findViewById(R.id.delete_search);
        deleteSearch.setOnClickListener(this);
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
                holder.setVisibility(R.id.content_zhiw, 8);
                holder.setOnClickListener(R.id.check_user, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent newpush = new Intent();
                        newpush.putExtra("name", obj.getPartContent());
                        newpush.putExtra("id", obj.getId());
                        newpush.putExtra("userid", obj.getUserid());
                        newpush.putExtra("orgid", obj.getUploadTime());
                        //回传数据到主Activity
                        setResult(2, newpush);
                        finish(); //此方法后才能返回主Activity
                    }
                });
            }
        };
        expandableListView.setAdapter(mAdapter);
        expandableListView.setEmptyView(findViewById(R.id.nullposion));
        request();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                //返回
                Dates.hintKeyBoard(ChagedContactsActivity.this);
                finish();
                break;
            case R.id.delete_search:
                //搜索
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
                    Dates.hintKeyBoard(ChagedContactsActivity.this);
                } else {
                    ToastUtils.showLongToast("搜索内容不能为空");
                }
                break;
            default:
                break;
        }
    }

    public void getdata(String name, String id) {
        Intent intent = new Intent();
        intent.putExtra("name", name);
        intent.putExtra("id", id);
        intent.putExtra("id", id);
        setResult(3, intent);
        finish();
    }

    /*网络请求*/
    public void request() {
        OkGo.get(Requests.GETPERSONDATA)
                .params("orgId", orgId)
                .params("page.size", 1000)
                .params("page.en", 1)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                list = new ArrayList<MoretasklistBean>();
                                JSONObject data = jsonObject.getJSONObject("data");
                                JSONArray jsonArray = data.getJSONArray("results");
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json = jsonArray.getJSONObject(i);
                                        String id = json.getString("id");
                                        String name = json.getString("name");
                                        String orgId = json.getString("orgId");
                                        /* String position = json.getString("position");*/
                                        String user_id = json.getString("userId");
                                        list.add(new MoretasklistBean(orgId, name, id, user_id));
                                    }
                                }
                            }else {
                                ToastUtils.showShortToast(jsonObject.getString("msg"));
                            }
                            if (list==null){
                                list = new ArrayList<MoretasklistBean>();
                            }
                            search.addAll(list);
                            mAdapter.getData(search);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }
}
