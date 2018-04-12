package com.example.administrator.newsdf.activity.home;

import com.example.administrator.newsdf.bean.OrganizationEntity;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.treeView.TaskTreeListViewAdapter;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.Request;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

import static com.lzy.okgo.OkGo.post;

/**
 * Created by Administrator on 2018/4/11 0011.
 */

public class homeUtils {

    /**
     * 组织机构
     *
     * @param json 字符串
     * @return 实体
     */
    public static ArrayList<OrganizationEntity> parseOrganizationList(String json) {
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
            Dates.disDialog();
        }
    }

    String result;

    public String get() {
        post(Request.CascadeList)

                .params("rows", 25)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.contains("data")) {
                            result = s;
                        } else {
                            ToastUtils.showShortToast("没有更多数据了！");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
        return result;
    }

}
