package com.example.administrator.newsdf.activity.work;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.bean.OrganizationEntity;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.treeView.Node;
import com.example.administrator.newsdf.treeView.PushListviewAdapter;
import com.example.administrator.newsdf.treeView.TreeListViewAdapter;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.LogUtil;
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

import okhttp3.Call;
import okhttp3.Response;


/**
 * description: 任务推送的wbs树
 *
 * @author: lx
 * date: 2018/2/6 0006 上午 9:20
 * update: 2018/2/6 0006
 * version:
 */
public class MmissPushActivity extends AppCompatActivity {
    private ArrayList<OrganizationEntity> organizationList;
    private ArrayList<OrganizationEntity> addOrganizationList;
    private List<OrganizationEntity> mTreeDatas;
    private ArrayList<String> ids = new ArrayList<>();
    private ArrayList<String> titlename;
    private ArrayList<String> titles = new ArrayList<>();
    private ListView mTree;
    private PushListviewAdapter<OrganizationEntity> mTreeAdapter;
    private int addPosition;
    private Context mContext;
    String org_status, wbsID;
    private SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wbs);
        Intent intent = getIntent();
        org_status = intent.getExtras().getString("data");
        try {
            wbsID = intent.getExtras().getString("wbsID");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        wbsID = intent.getExtras().
                getString("wbsID");
        LinearLayout back = (LinearLayout) findViewById(R.id.com_back);
        TextView title = (TextView) findViewById(R.id.com_title);
        title.setText("选择WBS节点");
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                okgo();
                //传入false表示刷新失败
                refreshlayout.finishRefresh(2000);
            }
        });
        mContext = MmissPushActivity.this;
        mTreeDatas = new ArrayList<>();
        addOrganizationList = new ArrayList<>();
        organizationList = new ArrayList<>();
        initView();
        Dates.getDialogs(MmissPushActivity.this, "请求数据中...");
        okgo();
        back.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void okgo() {
        OkGo.post(Request.WBSTress)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        mTreeDatas.clear();
                        if (s.contains("data")) {
                            getWorkOrganizationList(s);
                        } else {
                            ToastUtils.showLongToast("数据加载失败");
                        }
                        Dates.disDialog();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Dates.disDialog();
                    }
                });
    }

    private void initView() {

        mTree = (ListView) findViewById(R.id.wbs_listview);
    }

    void addOrganiztion(final String id, final boolean iswbs, final boolean isparent, String type) {
        Dates.getDialogs(MmissPushActivity.this, "请求数据中");
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
    private static ArrayList<OrganizationEntity> parseOrganizationList(String json) {
        LogUtil.i("orgin", json);
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
                        //节点层级
                        organization.setPhone(obj.getJSONObject("extend").getInt("taskNum") + "");
                    } catch (JSONException e) {
                        organization.setPhone("");
                    }

                    organizationList.add(organization);
                }
                if (organizationList.size() != 0) {
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
            if (addOrganizationList.size() != 0) {
                for (int i = addOrganizationList.size() - 1; i >= 0; i--) {
                    LogUtil.i("addPosition", addPosition + "");
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
            for (OrganizationEntity entity : organizationList) {
                String departmentName = entity.getDepartname();
                OrganizationEntity bean = new OrganizationEntity(entity.getId(), entity.getParentId(),
                        departmentName, entity.getIsleaf(), entity.iswbs(),
                        entity.isparent(), entity.getTypes(), entity.getUsername(),
                        entity.getNumber(), entity.getUserId(), entity.getTitle(), entity.getPhone(), entity.isDrawingGroup());
                mTreeDatas.add(bean);
            }
            try {
                mTreeAdapter = new PushListviewAdapter<>(mTree, this,
                        mTreeDatas, 0);
                mTree.setAdapter(mTreeAdapter);
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
                    //判断是否为空
                    if (node.getChildren().size() == 0) {
                        addOrganizationList.clear();
                        LogUtil.i("node", position + "");
                        addPosition = position;
                        //是否是父级点
                        if (node.isperent()) {
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
        if (node.iswbs() != false) {
            switch (org_status) {
                case "push":
                    getOko(node.getId(), node.getTitle(), node.getName());
                    break;
                case "Photo":
                    Intent intent1 = new Intent(mContext, PhotoadmActivity.class);
                    intent1.putExtra("wbsId", node.getId());
                    intent1.putExtra("title", node.getName());
                    startActivity(intent1);
                    break;
                case "reply":
                    Intent reply = new Intent();
                    reply.putExtra("position", node.getId());
                    reply.putExtra("title", node.getTitle());
                    //回传数据到主Activity
                    setResult(RESULT_OK, reply);
                    //此方法后才能返回主Activity
                    finish();
                    break;
                case "List":
                    Intent list = new Intent();
                    list.putExtra("id", node.getId());
                    list.putExtra("title", node.getName());
                    list.putExtra("titles", node.getTitle());
                    list.putExtra("iswbs", node.iswbs());
                    //回传数据到主Activity
                    setResult(RESULT_OK, list);
                    //此方法后才能返回主Activity
                    finish();
                    break;
                case "newpush":
                    Intent newpush = new Intent();
                    newpush.putExtra("wbsId", node.getId());
                    newpush.putExtra("title", node.getTitle());
                    //回传数据到主Activity
                    setResult(1, newpush);
                    //此方法后才能返回主Activity
                    finish();
                    break;
                //任务详情
                case "details":
                    Intent details = new Intent(mContext, NodedetailsActivity.class);
                    //节点ID
                    details.putExtra("wbsId", node.getId());
                    //节点名称
                    details.putExtra("Name", node.getName());
                    details.putExtra("wbsName", node.getTitle());
                    startActivity(details);
                    break;
                default:
                    break;
            }
        } else {
//            Toast.makeText(mContext, "不是wbs,无法跳转", Toast.LENGTH_SHORT).show();
        }
    }

    String name, id;

    void getOko(final String str, final String wbsname, final String wbspath) {
        Dates.getDialogs(MmissPushActivity.this, "请求数据中");
        OkGo.post(Request.PUSHList)
                .params("wbsId", str)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.contains("data")) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                titlename = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    try {
                                        id = json.getString("id");
                                    } catch (JSONException e) {
                                        id = "";
                                    }
                                    //可能界面没有数据,name可能为空
                                    try {
                                        name = json.getString("name");
                                    } catch (JSONException e) {

                                        name = "";
                                    }
                                    ids.add(id);
                                    //保存标题
                                    titlename.add(name);
                                }

                                Intent intent = new Intent(MmissPushActivity.this, MissionpushActivity.class);
                                intent.putExtra("ids", ids);
                                intent.putExtra("title", titlename);
                                intent.putExtra("titles", "任务下发");
                                intent.putExtra("wbsPath", wbspath);
                                intent.putExtra("id", str);
                                intent.putExtra("wbsnam", wbsname);
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Dates.disDialog();
                        } else {
                            ToastUtils.showShortToast("该节点未启动");
                            Intent intent = new Intent(MmissPushActivity.this, MissionpushActivity.class);
                            intent.putExtra("ids", ids);
                            intent.putExtra("title", titles);
                            intent.putExtra("id", str);
                            intent.putExtra("wbsnam", wbsname);
                            startActivity(intent);
                            Dates.disDialog();
                        }
                    }
                });
    }


}
