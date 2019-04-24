package com.example.administrator.newsdf.pzgc.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.bean.OrganizationEntity;
import com.example.baselibrary.base.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.baselibrary.utils.Requests;
import com.example.administrator.newsdf.treeView.MeberlistViewAdapter;
import com.example.administrator.newsdf.treeView.TreeListViewAdapter;
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
 * description: 项目成员树
 *
 * @author lx
 *         date: 2018/3/21 0021 上午 10:31
 *         update: 2018/3/21 0021
 *         version:
 */
public class ProjectMembersTreeActivity extends BaseActivity {
    private ListView maberTree;
    private ArrayList<OrganizationEntity> addOrganizationList;
    private List<OrganizationEntity> mTreeDatas;
    private MeberlistViewAdapter<OrganizationEntity> mTreeAdapter;
    private int addPosition;
    private TextView Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectmb_tree);

        //树
        maberTree = (ListView) findViewById(R.id.maber_tree);
        //标题
        Title = (TextView) findViewById(R.id.titleView);
        Title.setText("选择组织");
        //请求数据存放集合
        mTreeDatas = new ArrayList<>();
        addOrganizationList = new ArrayList<>();
        okgo();
        //关闭
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okgo();
            }
        });
    }


    /**
     * 解析组织机构对象
     *
     * @param result
     * @return
     */
    private void getWorkOrganizationList(String result, String title, String numner) {
        ArrayList<OrganizationEntity> organizationList = parseOrganizationList(result, title, numner);
        getOrganization(organizationList);
    }

    /**
     * 组织机构
     *
     * @param json 字符串
     * @return 实体
     */
    public ArrayList<OrganizationEntity> parseOrganizationList(String json, String title, String number) {
        if (json == null) {
            return null;
        } else {
            ArrayList<OrganizationEntity> organizationList = new ArrayList<OrganizationEntity>();
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    OrganizationEntity organization = new OrganizationEntity();
                    try {
                        //节点id
                        organization.setId(obj.getString("id"));
                    } catch (JSONException e) {

                        organization.setId("");
                    }
                    try {
                        //节点名称
                        organization.setDepartname(obj.getString("name"));
                    } catch (JSONException e) {

                        organization.setDepartname("");
                    }
                    try {
                        //组织类型
                        organization.setTypes(obj.getString("type"));
                    } catch (JSONException e) {
                        organization.setTypes("");
                    }
                    try {
                        //是否swbs
                        organization.setIswbs(obj.getBoolean("iswbs"));
                    } catch (JSONException e) {
                        organization.setIswbs(false);
                    }

                    try {
                        //是否是父节点
                        organization.setIsparent(obj.getBoolean("isParent"));
                    } catch (JSONException e) {

                        organization.setIsparent(false);
                    }
                    try {
                        boolean isParentFlag = obj.getBoolean("isParent");
                        if (isParentFlag) {
                            //不是叶子节点
                            organization.setIsleaf("0");
                        } else {
                            //是叶子节点
                            organization.setIsleaf("1");
                        }
                    } catch (JSONException e) {

                        organization.setIsleaf("");
                    }
                    try {
                        //组织机构父级节点
                        organization.setParentId(obj.getString("parentId"));
                    } catch (JSONException e) {

                        organization.setParentId("");
                    }

                    try {
                        //负责人 //进度
                        organization.setUsername(obj.getJSONObject("extend").getString("leaderName"));
                    } catch (JSONException e) {
                        organization.setUsername("");
                    }

                    try {
                        //负责热ID
                        organization.setUserId(obj.getJSONObject("extend").getString("leaderId"));
                    } catch (JSONException e) {
                        organization.setUserId("");
                    }
                    try {
                        //节点层级
                        organization.setTitle(obj.getString("title"));

                    } catch (JSONException e) {
                        organization.setTitle("");
                    }
                    //根据title的长度判断是否是第一次加载数据
                    if (title.length() == 0) {
                        //这棵树的number和phone 没有数据，用来存放id和名称集合，便于查询每个节点所在的层级路径
                        organization.setNumber(number + obj.getString("id"));
                        organization.setPhone(title + obj.getString("title"));
                    } else {
                        organization.setNumber(number + "," + obj.getString("id"));
                        organization.setPhone(title + "," + obj.getString("title"));
                    }
                    organizationList.add(organization);

                }

                return organizationList;

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * 解析SoapObject对象
     *
     * @return
     */
    private void addOrganizationList(String result, String title, String number) {
        if (result.contains("data")) {
            //解析json    并将名称和id传递下去，便于解析是形成节点层级路径
            addOrganizationList = parseOrganizationList(result, title, number);
            if (addOrganizationList.size() != 0) {
                //循环添加节点
                for (int i = addOrganizationList.size() - 1; i >= 0; i--) {
                    //动态添加节点
                    mTreeAdapter.addExtraNode(addPosition, addOrganizationList.get(i).getId(),
                            addOrganizationList.get(i).getParentId(),
                            addOrganizationList.get(i).getDepartname(), addOrganizationList.get(i).getIsleaf(),
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

    private void getOrganization(ArrayList<OrganizationEntity> organizationList) {
        if (organizationList != null) {
            //便利数据，将他从新加入集合
            for (OrganizationEntity entity : organizationList) {
                String departmentName = entity.getDepartname();
                OrganizationEntity bean = new OrganizationEntity(entity.getId(), entity.getParentId(),
                        departmentName, entity.getIsleaf(), entity.iswbs(),
                        entity.isparent(), entity.getTypes(), entity.getUsername(),
                        entity.getNumber(), entity.getUserId(), entity.getTitle(), entity.getPhone(), entity.isDrawingGroup());
                mTreeDatas.add(bean);
            }
            try {
                //更新界面数据
                mTreeAdapter = new MeberlistViewAdapter<>(maberTree, this,
                        mTreeDatas, 0);
                maberTree.setAdapter(mTreeAdapter);
                maberTree.setEmptyView(findViewById(R.id.nullposion));
                initEvent(organizationList);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    private void initEvent(ArrayList<OrganizationEntity> organizationList) {
        mTreeAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
            @Override
            public void onClick(com.example.administrator.newsdf.treeView.Node node, int position) {
                //判断是否是字节点，
                if (node.isLeaf()) {
                } else {
                    //  如果不是，判断该节点是否有数据，
                    if (node.getChildren().size() == 0) {
                        //  如果没有，就请求数据，
                        addOrganizationList.clear();
                        addPosition = position;
                        if (node.isperent()) {
                            //从拿到该节点的名称和id
                            addOrganiztion(node.getId(), node.getPhone(), node.getNumber());
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void addOrganiztion(final String id, final String title, final String number) {
        Dates.getDialogs(ProjectMembersTreeActivity.this, "请求数据中");
        OkGo.<String>post(Requests.ORGTREE)
                .params("orgId", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //解析json  并将名称和id传递下去，便于解析是形成节点层级路径
                        addOrganizationList(s, title, number);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Dates.disDialog();
                    }
                });
    }

    private void okgo() {
        OkGo.<String>post(Requests.ORGTREE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        mTreeDatas.clear();
                        getWorkOrganizationList(s, "", "");
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShortToastCenter("请确认网络是否畅通...");
                    }
                });
    }


}
