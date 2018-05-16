package com.example.administrator.newsdf.activity.work;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.bean.OrganizationEntity;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.treeView.Node;
import com.example.administrator.newsdf.treeView.SimpleTreeListViewAdapter;
import com.example.administrator.newsdf.treeView.TreeListViewAdapter;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.Requests;
import com.example.administrator.newsdf.utils.TreeUtlis;
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
 * description: 任务维护的wbs树
 *
 * @author lx
 *         date: 2018/3/22 0022 下午 2:39
 *         update: 2018/3/22 0022
 *         version:
 */
public class OrganiwbsActivity extends Activity {
    private ArrayList<OrganizationEntity> organizationList;
    private ArrayList<OrganizationEntity> addOrganizationList;
    private List<OrganizationEntity> mTreeDatas;
    private ListView mTree;
    private SimpleTreeListViewAdapter<OrganizationEntity> mTreeAdapter;
    private TextView com_title;
    private int addPosition;
    private Context mContext;
    private SmartRefreshLayout refreshLayout;
    String wbsname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wbs);
        mContext = OrganiwbsActivity.this;
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
        com_title.setText("选择WBS节点");

        mTree = (ListView) findViewById(R.id.wbs_listview);
        mContext = OrganiwbsActivity.this;
        mTreeDatas = new ArrayList<>();
        addOrganizationList = new ArrayList<>();
        organizationList = new ArrayList<>();
        Dates.getDialogs(OrganiwbsActivity.this, "请求数据中...");
        okgo();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void okgo() {
        OkGo.post(Requests.WBSTress)
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
                });
    }

    void addOrganiztion(final String id, final boolean iswbs,
                        final boolean isparent, String type) {
        Dates.getDialogs(OrganiwbsActivity.this, "请求数据中");
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
                mTreeAdapter = new SimpleTreeListViewAdapter<OrganizationEntity>(mTree, this,
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

    public void switchAct(Node node, final String name) {
        wbsname = name;
        if (node.iswbs() != false) {
            getOko(node.getId(), node.getTitle(), node.isperent(), node.getName(), node.iswbs(), node.getType());
        } else {
//            Toast.makeText(mContext, "不是wbs,无法跳转", Toast.LENGTH_SHORT).show();
        }
    }

    void getOko(final String str, final String wbspath, final boolean isParent, final String wbsname, final boolean iswbs, final String type) {
        final ArrayList<String> namess = new ArrayList<>();
        final ArrayList<String> ids = new ArrayList<>();
        final ArrayList<String> titlename = new ArrayList<>();
        Dates.getDialog(OrganiwbsActivity.this, "请求数据中");
        OkGo.post(Requests.WbsTaskGroup)
                .params("wbsId", str)
                .params("isNeedTotal", "true")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.contains("data")) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    String id = json.getString("id");
                                    String name = json.getString("detectionName");
                                    String totalNum = json.getString("totalNum");
                                    namess.add(name + "(" + totalNum + ")");
                                    ids.add(id);
                                    titlename.add(name);

                                }
                                Intent intent = new Intent(OrganiwbsActivity.this, TenanceviewActivity.class);
                                //加了任务数量的检查点
                                intent.putExtra("name", namess);
                                //检查点ID
                                intent.putExtra("ids", ids);
                                //检查点名称
                                intent.putExtra("title", titlename);
                                //节点ID
                                intent.putExtra("id", str);
                                //节点路径
                                intent.putExtra("wbspath", wbspath);
                                //是否是父节点
                                intent.putExtra("isParent", isParent);
                                intent.putExtra("wbsname", wbsname);
                                intent.putExtra("iswbs", iswbs);
                                intent.putExtra("type", type);
                                startActivity(intent);
                                Dates.disDialog();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.showShortToast("该节点未启动");
                            Intent intent = new Intent(OrganiwbsActivity.this, TenanceviewActivity.class);
                            //加了任务数量的检查点
                            intent.putExtra("name", namess);
                            //检查点ID
                            intent.putExtra("ids", ids);
                            //检查点名称
                            intent.putExtra("title", titlename);
                            //节点ID
                            intent.putExtra("id", str);
                            //节点路径
                            intent.putExtra("wbspath", wbspath);
                            //是否是父节点
                            intent.putExtra("isParent", isParent);
                            intent.putExtra("wbsname", wbsname);
                            intent.putExtra("iswbs", iswbs);
                            intent.putExtra("type", type);
                            startActivity(intent);
                            Dates.disDialog();
                        }

                    }
                });
    }


}
