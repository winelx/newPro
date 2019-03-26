package com.example.administrator.newsdf.pzgc.activity.work;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.OrganizationEntity;
import com.example.baselibrary.view.BaseActivity;
import com.example.administrator.newsdf.treeView.Node;
import com.example.administrator.newsdf.treeView.TaskTreeListViewAdapter;
import com.example.administrator.newsdf.treeView.TreeListViewAdapter;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.LogUtil;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


/**
 * description:赛选的wbs树
 *
 * @author lx
 *         date: 2018/3/22 0022 下午 2:39
 *         update: 2018/3/22 0022
 *         version:
 */
public class TaskWbsActivity extends BaseActivity {
    private ArrayList<OrganizationEntity> organizationList;
    private ArrayList<OrganizationEntity> addOrganizationList;
    private List<OrganizationEntity> mTreeDatas;
    private ListView mTree;
    private TaskTreeListViewAdapter<OrganizationEntity> mTreeAdapter;
    private TextView com_title;
    private int addPosition;
    private Context mContext;
    private SmartRefreshLayout refreshLayout;
    String wbsname, wbsID, type, wbspath;
    private boolean isParent, iswbs;
    private String DATA = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wbs);
        Intent intent = getIntent();
        wbsID = intent.getStringExtra("wbsID");
        wbsname = intent.getExtras().getString("wbsname");
        wbspath = intent.getExtras().getString("wbspath");
        type = intent.getExtras().getString("type");
        isParent = intent.getExtras().getBoolean("isParent");
        iswbs = intent.getExtras().getBoolean("iswbs");

        mContext = TaskWbsActivity.this;
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                okgo();
                //传入false表示刷新失败
                refreshlayout.finishRefresh(2000);
            }
        });
        LinearLayout back = (LinearLayout) findViewById(R.id.com_back);
        com_title = (TextView) findViewById(R.id.com_title);
        com_title.setText("选择wbs");
        mTree = (ListView) findViewById(R.id.wbs_listview);
        mContext = TaskWbsActivity.this;
        mTreeDatas = new ArrayList<>();
        addOrganizationList = new ArrayList<>();
        organizationList = new ArrayList<>();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        OrganizationEntity bean = new OrganizationEntity(wbsID, "",
                wbsname, "0", iswbs,
                isParent, type, "",
                "", "", wbspath, "", true);
        organizationList.add(bean);
        getOrganization(organizationList);

    }


    private void okgo() {
        OkGo.post(Requests.WBSTress)
                .params("nodeid", wbsID)
                .params("iswbs", iswbs)
                .params("isparent", isParent)
                .params("type", type)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        mTreeDatas.clear();
                        getWorkOrganizationList(s);
                    }
                });
    }

    void addOrganiztion(final String id, final boolean iswbs,
                        final boolean isparent, String type) {
        Dates.getDialogs(TaskWbsActivity.this, "请求数据中");
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
    private ArrayList<OrganizationEntity> parseOrganizationList(String json) {
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
                        organization.setPhone(obj.getJSONObject("extend").getInt("taskNum") + "");
                    } catch (JSONException e) {
                        organization.setPhone("");
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
    private void addOrganizationList(String result) {
        if (result.contains(DATA)) {
            addOrganizationList = parseOrganizationList(result);
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
                            addOrganizationList.get(i).isDrawingGroup());
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
            for (OrganizationEntity entity : organizationList) {
                String departmentName = entity.getDepartname();
                OrganizationEntity bean = new OrganizationEntity(entity.getId(), entity.getParentId(),
                        departmentName, entity.getIsleaf(), entity.iswbs(),
                        entity.isparent(), entity.getTypes(), entity.getUsername(),
                        entity.getNumber(), entity.getUserId(), entity.getTitle(), entity.getPhone(), entity.isDrawingGroup());
                mTreeDatas.add(bean);
            }
            try {
                mTreeAdapter = new TaskTreeListViewAdapter<>(mTree, this,
                        mTreeDatas, 0);
                mTree.setAdapter(mTreeAdapter);
                mTreeAdapter.getStatus("task");
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
                            addOrganiztion(node.getId(), node.iswbs(), node.isperent(), node.getType());
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
        if (node.iswbs()) {
            Intent list = new Intent();
            list.putExtra("id", node.getId());
            list.putExtra("titles", node.getTitle());
            LogUtil.i("result", node.getTitle() + node.getName() + node);
            //回传数据到主Activity
            setResult(RESULT_OK, list);
            //此方法后才能返回主Activity
            finish();

        }
    }
}
