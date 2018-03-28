package com.example.administrator.newsdf.activity.work;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.bean.OrganizationEntity;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.treeView.TaskTreeListViewAdapter;
import com.example.administrator.newsdf.treeView.TreeListViewAdapter;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.Request;
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

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionItem;
import okhttp3.Call;
import okhttp3.Response;


/**
 * description:任务列表界面的赛选的wbs树
 *
 * @author lx
 *         date: 2018/3/22 0022 下午 2:39
 *         update: 2018/3/22 0022
 *         version:
 */
public class TaskWbsActivity extends Activity {
    private ArrayList<OrganizationEntity> organizationList;
    private ArrayList<OrganizationEntity> addOrganizationList;
    private List<OrganizationEntity> mTreeDatas;
    private ListView mTree;
    private TaskTreeListViewAdapter<OrganizationEntity> mTreeAdapter;
    private TextView com_title;
    private int addPosition;
    private Context mContext;
    private SmartRefreshLayout refreshLayout;
    String wbsname, wbsID, org_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wbs);
        Intent intent = getIntent();
        wbsID = intent.getStringExtra("WbsID");
        org_status = intent.getExtras().getString("data");
        wbsname = intent.getExtras().getString("wbsname");
        mContext = TaskWbsActivity.this;
        refreshLayout = findViewById(R.id.SmartRefreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                okgo();
                //传入false表示刷新失败
                refreshlayout.finishRefresh(2000);
            }
        });
        LinearLayout back = (LinearLayout) findViewById(R.id.com_back);
        com_title = findViewById(R.id.com_title);
        com_title.setText(intent.getExtras().getString("wbsname"));
        List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
        permissonItems.add(new PermissionItem(Manifest.permission.CALL_PHONE,
                getString(R.string.permission_cus_item_phone), R.drawable.permission_ic_phone));
        HiPermission.create(TaskWbsActivity.this)
                .permissions(permissonItems)
                .checkMutiPermission(null);
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
                wbsname, "", true,
                true, "3,5", "",
                "", "", wbsname, "", true);
        mTreeDatas.add(bean);
        organizationList.add(bean);
        try {
            mTreeAdapter = new TaskTreeListViewAdapter<OrganizationEntity>(mTree, this,
                    mTreeDatas, 0);
            mTree.setAdapter(mTreeAdapter);
            initEvent(organizationList);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void okgo() {
        OkGo.post(Request.WBSTress)
                .params("nodeid", wbsID)
                .params("iswbs", false)
                .params("isparent", true)
                .params("type", "3,5")
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
        OkGo.post(Request.WBSTress)
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
    public ArrayList<OrganizationEntity> parseOrganizationList(String json) {
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
        if (result.contains("data")) {
            addOrganizationList = parseOrganizationList(result);
            ToastUtils.showShortToast("解析完数据");
            if (addOrganizationList.size() != 0) {
                ToastUtils.showShortToast("解析完数据1");
                for (int i = addOrganizationList.size() - 1; i >= 0; i--) {
                    ToastUtils.showShortToast("填充");
                    String departmentName = addOrganizationList.get(i).getDepartname();
                    mTreeAdapter.addExtraNode(addPosition, addOrganizationList.get(i).getId(),
                            addOrganizationList.get(i).getParentId(),
                            departmentName, addOrganizationList.get(i).getIsleaf(),
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
            for (OrganizationEntity entity : organizationList) {
                String departmentName = entity.getDepartname();
                OrganizationEntity bean = new OrganizationEntity(entity.getId(), entity.getParentId(),
                        departmentName, entity.getIsleaf(), entity.iswbs(),
                        entity.isparent(), entity.getTypes(), entity.getUsername(),
                        entity.getNumber(), entity.getUserId(), entity.getTitle(), entity.getPhone(), entity.isDrawingGroup());
                mTreeDatas.add(bean);
            }
            try {
                mTreeAdapter = new TaskTreeListViewAdapter<OrganizationEntity>(mTree, this,
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

                //判断是否是字节点，
                    ToastUtils.showShortToast("按钮3");
                    //  如果不是，判断该节点是否有数据，
                    if (node.getChildren().size() == 0) {
                        //  如果没有，就请求数据，
                        addOrganizationList.clear();
                        addPosition = position;
                        if (node.isperent()) {
                            ToastUtils.showShortToast("按钮4");
                            //从拿到该节点的名称和id
                            ToastUtils.showShortToast(node.getId()+"  "+node.iswbs()+"  "+node.isperent()+"  "+ node.getType());
                            Log.i("toast",node.getId()+"  "+node.iswbs()+"  "+node.isperent()+"  "+ node.getType());
                            addOrganiztion(node.getId(),false,false, node.getType());
                        }
                    }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
