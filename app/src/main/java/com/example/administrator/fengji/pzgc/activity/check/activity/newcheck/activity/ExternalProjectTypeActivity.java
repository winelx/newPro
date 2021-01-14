package com.example.administrator.fengji.pzgc.activity.check.activity.newcheck.activity;


import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.activity.check.activity.newcheck.adapter.ProjectTypeTreeAdapter;
import com.example.administrator.fengji.pzgc.activity.check.activity.newcheck.bean.Enum;
import com.example.administrator.fengji.pzgc.activity.check.activity.newcheck.utils.ExternalApi;
import com.example.administrator.fengji.pzgc.utils.Dates;
import com.example.administrator.fengji.pzgc.utils.ToastUtils;
import com.example.administrator.fengji.treeviews.bean.OrgBeans;
import com.example.administrator.fengji.treeviews.bean.OrgenBeans;
import com.example.administrator.fengji.treeviews.utils.Nodes;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.utils.rx.LiveDataBus;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 说明：工程类型
 * 创建时间： 2020/6/30 0030 18:07
 *
 * @author winelx
 */
public class ExternalProjectTypeActivity extends BaseActivity {
    private List<OrgBeans> mDatas2;
    private List<OrgenBeans> mData;
    private ListView mTree;
    private TextView title;
    private Context mContext;
    private boolean status = true;
    private ProjectTypeTreeAdapter mAdapter;
    private String type = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_tree);
        mContext = this;
        mTree = findViewById(R.id.tree);
        mDatas2 = new ArrayList<>();
        mData = new ArrayList<>();
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        if ("8".equals(intent.getStringExtra("name"))) {
            type = "8";
        } else {
            type = "9";
        }
        title = findViewById(R.id.com_title);
        title.setText("选择工程类型");
        initDatas();
        LiveDataBus.get().with("pro_tree", Object.class).observe(this, new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object o) {
                Map<String, Object> map = (Map<String, Object>) o;
                Nodes nodes = (Nodes) map.get("node");
                String pid = nodes.getPid();
                if (!getmIcon(nodes)) {
                    for (int i = 0; i < mData.size(); i++) {
                        String id = mData.get(i).getId();
                        if (pid.equals(id)) {
                            Intent intent1 = getIntent();
                            intent1.putExtra("name", mData.get(i).getName() + "/" + nodes.getName());
                            intent1.putExtra("id", nodes.getIds());
                            setResult(Enum.PRROJECT_TYPE, intent1);
                            finish();
                        }
                    }
                } else {
                    ToastUtils.showShortToast("必须是末节的");
                }
            }
        });
    }

    private void initDatas() {
        Dates.getDialog(this, "请求数据中...");
        OkGo.post(ExternalApi.GETWBSTASKTYPEBYAPP)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        mDatas2.clear();
                        mData.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONArray jsonArray1 = data.getJSONArray(type);
                            if (jsonArray1.length() > 0) {
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject json = jsonArray1.getJSONObject(i);
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
                                        mAdapter = new ProjectTypeTreeAdapter(mTree, mContext,
                                                mDatas2, 0, mData);
                                        mTree.setAdapter(mAdapter);
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                    mData.add(0, new OrgenBeans(Id, parentId, name, type));
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
                                            mDatas2.add(0, new OrgBeans(1, 0, mData.get(i).getName(), mData.get(i).getId(), mData.get(i).getParentId(), mData.get(i).getType()));
                                            try {
                                                mAdapter = new ProjectTypeTreeAdapter(mTree, mContext,
                                                        mDatas2, 0, mData);
                                                mTree.setAdapter(mAdapter);
                                            } catch (IllegalAccessException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
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

    /**
     * 说明：判断是否显示图标
     * 创建时间： 2020/7/2 0002 13:30
     *
     * @author winelx
     */
    public boolean getmIcon(Nodes node) {
        String str = node.getIds();
        for (int i = 0; i < mData.size(); i++) {
            String pid = mData.get(i).getParentId() + "";
            if (str.equals(pid)) {
                return true;
            }
        }
        return false;
    }
}
