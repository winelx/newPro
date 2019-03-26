package com.example.administrator.newsdf.pzgc.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.Enums;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.callback.OgranCallbackUtils;
import com.example.administrator.newsdf.pzgc.callback.OgranCallbackUtils1;
import com.example.administrator.newsdf.pzgc.callback.OgranCallbackUtils2;
import com.example.administrator.newsdf.pzgc.callback.OgranCallbackUtils3;
import com.example.baselibrary.view.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;
import com.example.administrator.newsdf.treeviews.SimpleTreeListViewAdapters;
import com.example.administrator.newsdf.treeviews.bean.OrgBeans;
import com.example.administrator.newsdf.treeviews.bean.OrgenBeans;
import com.example.administrator.newsdf.treeviews.utils.Nodes;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 切换组织界面
 */
public class OrganizationaActivity extends BaseActivity {
    private ListView mTree;
    private SimpleTreeListViewAdapters<OrgBeans> mAdapter;
    private List<OrgBeans> mDatas2;
    private List<OrgenBeans> mData;
    private Context mContext;
    private boolean status = true;
    public String data;
    private LinearLayout nullposion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizationa);
        mDatas2 = new ArrayList<OrgBeans>();
        mData = new ArrayList<OrgenBeans>();
        mContext = OrganizationaActivity.this;
        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");
        data = intent.getExtras().getString("data");
        nullposion = (LinearLayout) findViewById(R.id.nullposion);
        TextView comtitle = (TextView) findViewById(R.id.com_title);
        mTree = (ListView) findViewById(R.id.organ_list_item);

        comtitle.setText(title);
        initDatas();
        findViewById(R.id.retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatas();
            }
        });
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initDatas() {
        Dates.getDialog(OrganizationaActivity.this, "请求数据中...");
        OkGo.<String>post(Requests.Swatchmakeup)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        mDatas2.clear();
                        mData.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                            if (jsonArray1.length() > 0) {
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                                    JSONObject json = jsonObject1.getJSONObject("organization");
                                    String Id;
                                    try {
                                        Id = json.getString("id");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Id = "";
                                    }
                                    String type;
                                    try {
                                        type = json.getString("orgType");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        type = "";
                                    }
                                    String parentId;
                                    try {
                                        parentId = json.getString("parentId");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        //如果父ID为null
                                        parentId = "";
                                        //当做第一级处理
                                        status = false;
                                        mDatas2.add(new OrgBeans(1, 0, json.getString("name"), Id, parentId, type));
                                    }
                                    String name;
                                    try {
                                        name = json.getString("name");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        name = "";
                                    }
                                    try {
                                        mAdapter = new SimpleTreeListViewAdapters<OrgBeans>(mTree, OrganizationaActivity.this,
                                                mDatas2, 0);
                                        mTree.setAdapter(mAdapter);
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                    mData.add(new OrgenBeans(Id, parentId, name, type));
                                }
                                if (status) {
                                    //拿到所有的ID
                                    final ArrayList<String> IDs = new ArrayList<String>();
                                    for (int i = 0; i < mData.size(); i++) {
                                        IDs.add(mData.get(i).getId());
                                    }
                                    //循环集合
                                    for (int i = 0; i < mData.size(); i++) {
                                        //取出父ID，
                                        String pernID = mData.get(i).getParentId();
                                        //用ID判断是否有父级相同的
                                        if (IDs.contains(pernID)) {
                                            //存在相同的的不处理
                                        } else {
                                            //不存在相同的当做第一级
                                            mDatas2.add(new OrgBeans(1, 0, mData.get(i).getName(), mData.get(i).getId(), mData.get(i).getParentId(), mData.get(i).getType()));
                                            try {
                                                mAdapter = new SimpleTreeListViewAdapters<OrgBeans>(mTree, OrganizationaActivity.this,
                                                        mDatas2, 0);
                                                mTree.setAdapter(mAdapter);
                                            } catch (IllegalAccessException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                                nullposion.setVisibility(View.GONE);
                            } else {
                                nullposion.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Dates.disDialog();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShortToastCenter("请确认网络是否通畅...");
                        Dates.disDialog();
                    }
                });


    }

    //添加数据
    public void getAdd(int position, Nodes node) {
        String str = node.getIds();
        for (int i = 0; i < mData.size(); i++) {
            String pid = mData.get(i).getParentId();
            if (str.equals(pid)) {
                mAdapter.addExtraNode(position, mData.get(i).getName(), mData.get(i).getId(), mData.get(i).getParentId(), mData.get(i).getType());
                mAdapter.expandOrCollapse(position);
            }
            mAdapter.expandOrCollapse(position);
        }
    }

    //判断是否显示图标
    public boolean getmIcon(Nodes node) {
        String str = node.getIds();
        for (int i = 0; i < mData.size(); i++) {
            String pid = mData.get(i).getParentId();
            if (str.equals(pid)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 切换组织
     */
    public void member(final String orgid, final String name, String type) {
        if ("Rectifi".equals(data)) {
            if (type.contains("5")) {
                Intent intent = new Intent();
                //回传数据到主Activity
                intent.putExtra("id", orgid);
                intent.putExtra("name", name);
                setResult(2, intent);
                finish();
            } else {
                ToastUtils.showShortToast("");
            }
        } else {
            Dates.getDialogs(OrganizationaActivity.this, "切换组织中..");
            OkGo.post(Requests.Swatch)
                    .params("orgId", orgid)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                String msg = jsonObject.getString("msg");
                                int ret = jsonObject.getInt("ret");
                                if (ret == 0) {
                                    SPUtils.deleShare(mContext, "orgName");
                                    SPUtils.deleShare(mContext, "orgId");
                                    //所在组织ID
                                    SPUtils.putString(mContext, "orgId", orgid);
                                    //所在组织名称
                                    SPUtils.putString(mContext, "username", name);
                                    //刷新全部和我的界面的数据
                                    try {
                                        OgranCallbackUtils.removeCallBackMethod();
                                        OgranCallbackUtils1.removeCallBackMethod();
                                        OgranCallbackUtils2.removeCallBackMethod();
                                        OgranCallbackUtils3.removeCallBackMethod();
                                    } catch (Exception e) {
                                    }
                                    Dates.disDialog();
                                    finish(); //此方法后才能返回主Activity
                                } else {
                                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            Dates.disDialog();
                            ToastUtils.showLongToastCenter(Enums.REQUEST_ERROR);
                        }
                    });
        }

    }


}
