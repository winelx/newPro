package com.example.administrator.newsdf.activity.work;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.bean.OrganizationEntity;
import com.example.administrator.newsdf.treeView.Node;
import com.example.administrator.newsdf.treeView.PhotolistViewAdapter;
import com.example.administrator.newsdf.treeView.TreeListViewAdapter;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.Request;
import com.example.administrator.newsdf.utils.TreeUtlis;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
public class PhotoListActivity extends AppCompatActivity {
    private ArrayList<OrganizationEntity> organizationList;
    private ArrayList<OrganizationEntity> addOrganizationList;
    private List<OrganizationEntity> mTreeDatas;
    private ListView mTree;
    private PhotolistViewAdapter<OrganizationEntity> mTreeAdapter;
    private TextView com_title;
    private int addPosition;
    private Context mContext;
    private SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);
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
        com_title.setText("选择图册");

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
        OkGo.post(Request.PhotoList)
                .execute(new StringCallback() {
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

    void addOrganiztion(final String id, final boolean iswbs, final boolean isparent, final String type) {
        Dates.getDialogs(PhotoListActivity.this, "请求数据中");
        OkGo.post(Request.PhotoList)
                .params("nodeid", id)
                .params("isDrawingGroup", iswbs)
                .params("isParent", isparent)
                .params("type", type)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Call call, Response response) {
                        addOrganizationList(result);
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
     * 动态添加数据
     *
     * @return
     */
    private void addOrganizationList(String result) {
        if (result.contains("data")) {
            addOrganizationList = TreeUtlis.parseOrganizationList(result);
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
    /**
     *  点击事件
     */
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

    /**
     * 查看当前图册详细列表
     * @param node
     */
    public void switchAct(Node node) {
        if (node.isDrawingGroup()) {
            Intent intent1 = new Intent(mContext, ListPhActivity.class);
            intent1.putExtra("groupId", node.getId());
            intent1.putExtra("title", node.getName());
            startActivity(intent1);
        }
    }
}
