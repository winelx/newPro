package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.activity;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.adapter.TreeAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.TreeBean;
import com.example.administrator.newsdf.pzgc.activity.mine.OrganizationaActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.treeView.Node;
import com.example.administrator.newsdf.treeView.TreeHelper;
import com.example.administrator.newsdf.treeView.TreeListViewAdapter;
import com.example.administrator.newsdf.treeviews.SimpleTreeListViewAdapters;
import com.example.administrator.newsdf.treeviews.bean.OrgBeans;
import com.example.administrator.newsdf.treeviews.bean.OrgenBeans;
import com.example.administrator.newsdf.treeviews.utils.Nodes;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.utils.Requests;
import com.example.baselibrary.utils.rx.LiveDataBus;
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
 * 说明：选择组织
 * 创建时间： 2020/6/29 0029 17:29
 *
 * @author winelx
 */
public class ExternalTreeActivity extends BaseActivity {
    private ListView tree;
    private TextView comTitle, comButton;
    private List<OrgBeans> mDatas2;
    private List<OrgenBeans> mData;
    private boolean status = true;
    private TreeAdapter adaper;
    private Context mContext;
    private List<String> preservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_tree);
        mContext = this;
        preservation = new ArrayList<>();
        tree = findViewById(R.id.tree);
        comTitle = findViewById(R.id.com_title);
        comTitle.setText("选择部位");
        comButton = findViewById(R.id.com_button);
        comButton.setText("确定");
        mDatas2 = new ArrayList<>();
        mData = new ArrayList<>();
        initDatas();
        findViewById(R.id.com_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preservation.size() != 0) {
                    Intent intent=new Intent();
                    intent.putExtra("content",Dates.listToString(preservation));
                    setResult(102,intent);
                    finish();
                } else {
                    ToastUtils.showShortToast("请选择组织");
                }

            }
        });
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LiveDataBus.get().with("ex_tree", TreeBean.class)
                .observe(this, new Observer<TreeBean>() {
                    @Override
                    public void onChanged(@Nullable TreeBean bean) {
                        if (bean.isLean()) {
                            preservation.add(bean.getName());
                        } else {
                            for (int i = 0; i < preservation.size(); i++) {
                                String str = preservation.get(i);
                                if (str.equals(bean.getName())) {
                                    preservation.remove(i);
                                }
                            }
                        }
                    }
                });
    }

    private void initDatas() {
        OkGo.post(Requests.Swatchmakeup)
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
                                        adaper = new TreeAdapter(tree, mContext,
                                                mDatas2, 0, mData);
                                        tree.setAdapter(adaper);
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
                                                adaper = new TreeAdapter(tree, mContext,
                                                        mDatas2, 0, mData);
                                                tree.setAdapter(adaper);
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
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShortToastCenter("请确认网络是否通畅...");
                        Dates.disDialog();
                    }
                });


    }


}


