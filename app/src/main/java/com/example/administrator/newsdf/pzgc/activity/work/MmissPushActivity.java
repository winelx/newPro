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
import com.example.administrator.newsdf.pzgc.activity.work.pchoose.StandardActivity;
import com.example.administrator.newsdf.pzgc.bean.OrganizationEntity;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.baselibrary.base.BaseActivity;
import com.example.administrator.newsdf.treeView.Node;
import com.example.administrator.newsdf.treeView.PushListviewAdapter;
import com.example.administrator.newsdf.treeView.TreeListViewAdapter;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.baselibrary.utils.log.LogUtil;
import com.example.baselibrary.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.TreeUtlis;
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
 * description: 任务推送的wbs树
 *
 * @author: lx
 * date: 2018/2/6 0006 上午 9:20
 * update: 2018/2/6 0006
 * version:
 */
public class MmissPushActivity extends BaseActivity {
    private ArrayList<OrganizationEntity> organizationList;
    private ArrayList<OrganizationEntity> addOrganizationList;
    private List<OrganizationEntity> mTreeDatas;
    private ArrayList<String> ids = new ArrayList<>();

    private ListView mTree;
    private PushListviewAdapter<OrganizationEntity> mTreeAdapter;
    private int addPosition;
    private Context mContext;
    String org_status, wbsID;
    private SmartRefreshLayout refreshLayout;
    ArrayList<String> titlename;

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
            e.printStackTrace();
        }
        wbsID = intent.getExtras().
                getString("wbsID");
        LinearLayout back = (LinearLayout) findViewById(R.id.com_back);
        TextView title = (TextView) findViewById(R.id.com_title);
        title.setText("选择WBS节点");
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        refreshLayout.setEnableOverScrollDrag(true);//是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableLoadmore(false);//禁止上拉
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableOverScrollBounce(true);//仿ios越界

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
        PostRequest PostRequest;
        if (org_status.equals("standard")) {
            PostRequest = OkGo.post(Requests.STANDARD_TREE).params("nodeid", "");
        } else {
            PostRequest = OkGo.post(Requests.WBSTress).params("nodeid", "");
        }
        PostRequest.execute(new StringCallback() {
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
        organizationList = TreeUtlis.parseOrganizationList(result);
        getOrganization(organizationList);
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
        if (node.iswbs()) {
            switch (org_status) {
                case "push":
                    getOko(node.getId(), node.getTitle(), node.getName(), node.getType(), node.isperent(), node.iswbs());
                    break;
                case "standard":
                    //标准
                    Intent standard = new Intent(mContext, StandardActivity.class);
                    standard.putExtra("groupId", node.getId());
                    standard.putExtra("title", node.getName());
                    standard.putExtra("status", "standard");
                    startActivity(standard);
                    break;
                case "Photo":
                    //图册界面
                    Intent intent1 = new Intent(mContext, PhotoadmActivity.class);
                    intent1.putExtra("wbsId", node.getId());
                    intent1.putExtra("title", node.getName());
                    startActivity(intent1);
                    break;
                case "reply":
                    //主动任务
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
                    //新增推送界面
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
                    details.putExtra("wbspath", node.getName());
                    details.putExtra("type", node.getType());
                    details.putExtra("iswbs", node.iswbs());
                    details.putExtra("isParent", node.isperent());
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

    void getOko(final String str, final String wbspath, final String wbsname, final String type, final boolean isParent, final boolean iswbs) {
        Dates.getDialogs(MmissPushActivity.this, "请求数据中");
        titlename = new ArrayList<>();
        OkGo.post(Requests.PUSHList)
                .params("wbsId", str)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.contains("data")) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

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
                                //当前节点下任务项ID
                                intent.putExtra("ids", ids);
                                //当前节点任务项名
                                intent.putExtra("title", titlename);
                                intent.putExtra("titles", "任务下发");
                                //当前节点名称
                                intent.putExtra("wbsname", wbsname);
                                //当前节点ID
                                intent.putExtra("id", str);
                                //当前节点路径
                                intent.putExtra("wbsPath", wbspath);
                                //当前节点类型
                                intent.putExtra("type", type);
                                //当前节点是否是父节点
                                intent.putExtra("isParent", isParent);
                                //当前节点是否是wbs
                                intent.putExtra("iswbs", iswbs);
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Dates.disDialog();
                        } else {
                            ToastUtils.showShortToast("该节点未启动");
                            Intent intent = new Intent(MmissPushActivity.this, MissionpushActivity.class);
                            //当前节点下任务项ID
                            intent.putExtra("ids", ids);
                            //当前节点任务项名
                            intent.putExtra("title", titlename);
                            intent.putExtra("titles", "任务下发");
                            //当前节点名称
                            intent.putExtra("wbsname", wbsname);
                            //当前节点ID
                            intent.putExtra("id", str);
                            //当前节点路径
                            intent.putExtra("wbsPath", wbspath);
                            //当前节点类型
                            intent.putExtra("type", type);
                            //当前节点是否是父节点
                            intent.putExtra("isParent", isParent);
                            //当前节点是否是wbs
                            intent.putExtra("iswbs", iswbs);
                            startActivity(intent);
                            Dates.disDialog();
                        }
                    }
                });
    }

    public String getstatus() {
        return org_status;
    }

}
