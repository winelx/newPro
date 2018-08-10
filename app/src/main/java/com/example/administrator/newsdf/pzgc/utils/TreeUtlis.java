package com.example.administrator.newsdf.pzgc.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;

import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckTreeActivity;
import com.example.administrator.newsdf.pzgc.activity.home.HomeUtils;
import com.example.administrator.newsdf.pzgc.bean.OrganizationEntity;
import com.example.administrator.newsdf.treeView.Node;
import com.example.administrator.newsdf.treeView.TaskTreeListViewAdapter;
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
 * Created by Administrator on 2018/4/13 0013.
 */

public class TreeUtlis {

    private int addPosition;
    private Activity activity;


    /**
     * 组织机构
     *
     * @param json 字符串
     * @return 实体
     */
    public static ArrayList<OrganizationEntity> parseOrganizationList(String json) {
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


    public void getOrganization(Context mContext, ArrayList<OrganizationEntity> organizationList, TaskTreeListViewAdapter mTreeAdapter, List<OrganizationEntity> mTreeDatas, ListView mTree) {
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
                mTreeAdapter = new TaskTreeListViewAdapter<>(mTree, mContext,
                        mTreeDatas, 0);
                mTree.setAdapter(mTreeAdapter);
                mTreeAdapter.getStatus("Check");
                initEvent(mContext, mTreeAdapter);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void initEvent(final Context mContext, final TaskTreeListViewAdapter<OrganizationEntity> mTreeAdapter) {
        mTreeAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
            @Override
            public void onClick(Node node, int position) {
                if (node.isLeaf()) {
                } else {
                    //  如果不是，判断该节点是否有数据，
                    if (node.getChildren().size() == 0) {
                        //  如果没有，就请求数据，
                        addPosition = position;
                        if (node.isperent()) {
                            //从拿到该节点的名称和id
                            CheckTreeActivity addActivity = (CheckTreeActivity) mContext;
                            addActivity.dialog();
                            addOrganiztion(mContext, node.getId(), node.iswbs(), node.isperent(), node.getType(), mTreeAdapter);
                        }
                    }
                }
            }
        });
    }

    //wsb树的数据
    private void addOrganiztion(final Context mContext, final String id, final boolean iswbs,
                                final boolean isparent, String type, final TaskTreeListViewAdapter<OrganizationEntity> mTreeAdapter) {
        OkGo.get(Requests.WBSTress)
                .params("nodeid", id)
                .params("iswbs", iswbs)
                .params("isparent", isparent)
                .params("type", type)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String result, Call call, Response response) {
                        addOrganizationList(mContext, result, mTreeAdapter);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        CheckTreeActivity activity = (CheckTreeActivity) mContext;
                        activity.dissdialog();
                    }
                });

    }

    /**
     * 解析SoapObject对象
     *
     * @return
     */
    private void addOrganizationList(final Context mContext, String result, TaskTreeListViewAdapter<OrganizationEntity> mTreeAdapter) {
        if (result.contains("data")) {
            /**
             * 解析数据
             */
            ArrayList<OrganizationEntity> addOrganizationList = new ArrayList<>();
            addOrganizationList = HomeUtils.parseOrganizationList(result);
            /**
             * 动态添加
             */
            addOrganizationList(addOrganizationList, addPosition, mTreeAdapter);
            CheckTreeActivity activity = (CheckTreeActivity) mContext;
            activity.dissdialog();
        } else {
            CheckTreeActivity activity = (CheckTreeActivity) mContext;
            activity.dissdialog();
        }
    }

    /**
     * 动态添加数据
     */
    public static void addOrganizationList(ArrayList<OrganizationEntity> addOrganizationList, int addPosition, TaskTreeListViewAdapter<OrganizationEntity> mTreeAdapter) {
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

        }
    }


}
