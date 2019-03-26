package com.example.administrator.newsdf.pzgc.activity.work;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.work.pchoose.StandardActivity;
import com.example.administrator.newsdf.pzgc.bean.OrganizationEntity;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.baselibrary.view.BaseActivity;
import com.example.administrator.newsdf.treeView.Node;
import com.example.administrator.newsdf.treeView.PhotolistViewAdapter;
import com.example.administrator.newsdf.treeView.TreeListViewAdapter;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * description:选择图册wbs树
 *
 * @author lx
 *         date: 2018/3/6 0006 下午 4:54
 *         update: 2018/3/6 0006
 *         version:
 */
public class PhotoListActivity extends BaseActivity {
    private ArrayList<OrganizationEntity> organizationList;
    private ArrayList<OrganizationEntity> addOrganizationList;
    private List<OrganizationEntity> mTreeDatas;
    private ListView mTree;
    private PhotolistViewAdapter<OrganizationEntity> mTreeAdapter;
    private TextView com_title;
    private int addPosition;
    private Context mContext;
    private SmartRefreshLayout refreshLayout;
    private String stauts;
    PostRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        refreshLayout.setEnableOverScrollDrag(true);//是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableLoadmore(false);//禁止上拉
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableOverScrollBounce(true);//仿ios越界
        Intent intent = getIntent();
        stauts = intent.getExtras().getString("status");

        LinearLayout back = (LinearLayout) findViewById(R.id.com_back);
        com_title = (TextView) findViewById(R.id.com_title);
        com_title.setText(intent.getExtras().getString("title"));
        mTree = (ListView) findViewById(R.id.wbs_listview);
        mContext = PhotoListActivity.this;
        mTreeDatas = new ArrayList<>();
        addOrganizationList = new ArrayList<>();
        organizationList = new ArrayList<>();
        okgo();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void okgo() {
        //判断是那个入口进入的
        if (stauts.equals("standard")) {
            request = OkGo.<String>post(Requests.STANDARD_TREE).params("nodeid", "");
        } else {
            request = OkGo.<String>post(Requests.PhotoList).params("nodeid", "");
        }
        request.execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                mTreeDatas.clear();
                getWorkOrganizationList(s);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
            }
        });
    }

    private void addOrganiztion(final String id, final boolean isDrawing, final boolean isparent, final String type) {
        Dates.getDialogs(PhotoListActivity.this, "请求数据中");
        if (stauts.equals("standard")) {
            OkGo.<String>post(Requests.STANDARD_TREE)
                    .params("nodeid", id)
                    .params("isStandardGroup", isDrawing)
                    .params("isParent", isparent)
                    .params("type", type)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String result, Call call, Response response) {
                            addOrganizationList(result);
                        }
                    });
        } else {
            OkGo.<String>post(Requests.PhotoList)
                    .params("nodeid", id)
                    .params("isDrawingGroup", isDrawing)
                    .params("isParent", isparent)
                    .params("type", type)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String result, Call call, Response response) {
                            addOrganizationList(result);
                        }
                    });
        }
    }

    /**
     * 解析组织机构对象
     *
     * @param result
     * @return
     */
    private void getWorkOrganizationList(String result) {
        organizationList = parseOrganizationList(result);
        getOrganization(organizationList);
    }

    /**
     * 组织机构
     *
     * @param json 字符串
     * @return 实体
     */
    public ArrayList<OrganizationEntity> parseOrganizationList(String json) {
        if (json == null) {
            return null;
        } else {
            ArrayList<OrganizationEntity> organizationList = new ArrayList<OrganizationEntity>();
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                if (jsonArray.length() > 0) {
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
                            //进度
                            organization.setNumber(obj.getJSONObject("extend").getString("finish"));
                        } catch (JSONException e) {
                            organization.setNumber("");
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
                        try {
                            //节点层级
                            organization.setPhone(obj.getJSONObject("extend").getInt("taskNum") + "");
                        } catch (JSONException e) {
                            organization.setPhone("");
                        }
                        try {
                            organization.setDrawingGroup(obj.getBoolean("isDrawingGroup"));
                        } catch (JSONException e) {
                            organization.setDrawingGroup(obj.getBoolean("isStandardGroup"));
                        }
                        organizationList.add(organization);
                    }
                } else {
                    ToastUtils.showLongToast("暂无数据");
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
    private void addOrganizationList(String result) {
        if (result.contains("data")) {
            addOrganizationList = parseOrganizationList(result);
            for (int i = addOrganizationList.size() - 1; i >= 0; i--) {
                String departmentName = addOrganizationList.get(i).getDepartname();
                mTreeAdapter.addExtraNode(addPosition, addOrganizationList.get(i).getId(),
                        addOrganizationList.get(i).getParentId(), departmentName, addOrganizationList.get(i).getIsleaf(),
                        addOrganizationList.get(i).iswbs(),
                        addOrganizationList.get(i).isparent(),
                        addOrganizationList.get(i).getTypes(), addOrganizationList.get(i).getUsername(),
                        addOrganizationList.get(i).getNumber(), addOrganizationList.get(i).getUserId(),
                        addOrganizationList.get(i).getTitle(), addOrganizationList.get(i).getPhone(), addOrganizationList.get(i).isDrawingGroup());
            }
            Dates.disDialog();
        } else {
            Dates.disDialog();
        }
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
                mTreeAdapter = new PhotolistViewAdapter<>(mTree, this,
                        mTreeDatas, 0);
                mTree.setAdapter(mTreeAdapter);
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
                if (node.isLeaf()) {
                } else {
                    if (node.getChildren().size() == 0) {
                        addOrganizationList.clear();
                        addPosition = position;
                        if (node.isperent()) {
                            addOrganiztion(node.getId(), node.isDrawingGroup(), node.isperent(), node.getType());
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

    public void switchAct(Node node) {
        if (stauts.equals("standard")) {
            Intent intent1 = new Intent(mContext, StandardActivity.class);
            intent1.putExtra("groupId", node.getId());
            intent1.putExtra("title", node.getName());
            intent1.putExtra("status", "PhotoList");
            startActivity(intent1);
        } else {
            if (node.isDrawingGroup()) {
                Intent intent1 = new Intent(mContext, ListPhActivity.class);
                intent1.putExtra("groupId", node.getId());
                intent1.putExtra("title", node.getName());
                startActivity(intent1);

            }
        }
    }
}