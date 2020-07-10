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
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.utils.ExternalApi;
import com.example.administrator.newsdf.pzgc.activity.mine.OrganizationaActivity;
import com.example.administrator.newsdf.pzgc.activity.work.OrganiwbsActivity;
import com.example.administrator.newsdf.pzgc.bean.OrganizationEntity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.utils.TreeUtlis;
import com.example.administrator.newsdf.treeView.Node;
import com.example.administrator.newsdf.treeView.SimpleTreeListViewAdapter;
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
    private Context mContext;
    private List<String> preservation;
    private String orgId, nodeId = "";
    private int addPosition;
    private ArrayList<OrganizationEntity> organizationList;
    private ArrayList<OrganizationEntity> addOrganizationList;
    private List<OrganizationEntity> mTreeDatas;
    private TreeAdapter<OrganizationEntity> mTreeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_tree);
        mContext = this;
        preservation = new ArrayList<>();
        organizationList=new ArrayList<>();
        addOrganizationList=new ArrayList<>();
        mTreeDatas=new ArrayList<>();
        Intent intent=getIntent();
        orgId = intent.getStringExtra("orgid");
        tree = findViewById(R.id.tree);
        comTitle = findViewById(R.id.com_title);
        comTitle.setText("选择部位");
        comButton = findViewById(R.id.com_button);
        comButton.setText("确定");
        findViewById(R.id.com_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preservation.size() != 0) {
                    Intent intent = new Intent();
                    intent.putExtra("content", Dates.listToString(preservation));
                    setResult(102, intent);
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
        LiveDataBus.get().with("ex_node",String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {

                    }
                });
        okgo();
    }

    private void okgo() {
        OkGo.post(ExternalApi.GETWBSTREEBYAPP)
                .params("orgId",orgId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        mTreeDatas.clear();;
                        if (s.contains("data")) {
                            getWorkOrganizationList(s);
                        } else {
                            ToastUtils.showLongToast("数据加载失败");
                        }
                        Dates.disDialog();

                    }
                });
    }
    /**
     * 解析组织机构对象
     *
     * @param result
     * @return
     */
    private void getWorkOrganizationList(String result) {
        organizationList = TreeUtlis.parseOrganizationList(result);
        getOrganization(organizationList);
    }

    private void getOrganization(ArrayList<OrganizationEntity> organizationList) {
        if (organizationList != null) {
            for (OrganizationEntity entity : organizationList) {
                String departmentName = entity.getDepartname();
                OrganizationEntity bean = new OrganizationEntity(entity.getId(), entity.getParentId(),
                        departmentName, entity.getIsleaf(), entity.iswbs(),
                        entity.isparent(), entity.getTypes(), entity.getUsername(),
                        entity.getNumber(), entity.getUserId(), entity.getTitle(), entity.getPhone(), entity.isDrawingGroup());
                mTreeDatas.add(bean);
            }
            try {
                mTreeAdapter = new TreeAdapter<>(tree, this,
                        mTreeDatas, 0);
                tree.setAdapter(mTreeAdapter);
                initEvent();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    private void initEvent() {
        mTreeAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
            @Override
            public void onClick(com.example.administrator.newsdf.treeView.Node node, int position) {
                if (node.isLeaf()) {
                } else {
                    if (node.getChildren().size() == 0) {
                        addOrganizationList.clear();
                        addPosition = position;
                        if (node.isperent()) {
                            addOrganiztion(node.getId(), node.iswbs(), node.isperent(), node.getType());
                        }
                    }
                }
            }
        });
    }
    private void addOrganiztion(final String id, final boolean iswbs, final boolean isparent, String type) {
        Dates.getDialogs(this, "请求数据中");
        OkGo.post(Requests.WBSTress)
                .params("nodeid", id)
                .params("iswbs", iswbs)
                .params("isparent", isparent)
                .params("type", type)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Call call, Response response) {
                        addOrganizationList(result);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Dates.disDialog();
                    }
                });

    }
    /**
     * 解析SoapObject对象
     *
     * @return
     */
    private void addOrganizationList(String result) {
        if (result.contains("data")) {
            addOrganizationList = TreeUtlis.parseOrganizationList(result);
            if (addOrganizationList.size() != 0) {
                for (int i = addOrganizationList.size() - 1; i >= 0; i--) {
                    mTreeAdapter.addExtraNode(addPosition,
                            addOrganizationList.get(i).getId(),
                            addOrganizationList.get(i).getParentId(),
                            addOrganizationList.get(i).getDepartname(),
                            addOrganizationList.get(i).getIsleaf(),
                            addOrganizationList.get(i).iswbs(),
                            addOrganizationList.get(i).isparent(),
                            addOrganizationList.get(i).getTypes(),
                            addOrganizationList.get(i).getUsername(),
                            addOrganizationList.get(i).getNumber(),
                            addOrganizationList.get(i).getUserId(),
                            addOrganizationList.get(i).getTitle(),
                            addOrganizationList.get(i).getPhone(),
                            addOrganizationList.get(i).isDrawingGroup()
                    );
                }
                Dates.disDialog();
            }
            Dates.disDialog();
        } else {
            Dates.disDialog();
        }
    }
}


