package com.example.administrator.newsdf.activity.home;

import android.app.Activity;
import android.content.Intent;

import com.example.administrator.newsdf.Adapter.TaskPhotoAdapter;
import com.example.administrator.newsdf.activity.work.TenanceviewActivity;
import com.example.administrator.newsdf.bean.OrganizationEntity;
import com.example.administrator.newsdf.bean.PhotoBean;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.treeView.TaskTreeListViewAdapter;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

import static com.lzy.okgo.OkGo.post;

/**
 * description: 解析json和封装了查询图册的
 *
 * @author lx
 *         date: 2018/5/4 0004 下午 4:06
 *         update: 2018/5/4 0004
 *         version:
 */

public class HomeUtils {

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

    public static ArrayList<OrganizationEntity> parseOrganizationLists(String json) {
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

                        int hasChildren = obj.getInt("hasChildren");
                        if (hasChildren > 0) {
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
        post(Requests.CascadeList)
                .params("rows", 25)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.contains("data")) {
                            result = s;
                        } else {
                            ToastUtils.showShortToast("暂无数据！");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
        return result;
    }


    /**
     * 查询图册
     *
     * @param string           wbsID
     * @param page             请求页数
     * @param imagePaths       集合数据
     * @param drew             是否删除之前集合数据
     * @param taskPhotoAdapter 适配器
     * @param wbsName          节点层级
     */
    public static void photoAdm(final String string, int page,
                                final ArrayList<PhotoBean> imagePaths,
                                final boolean drew,
                                final TaskPhotoAdapter taskPhotoAdapter,
                                final String wbsName) {
        OkGo.post(Requests.Photolist)
                .params("WbsId", string)
                .params("page", page)
                .params("rows", 30)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.contains("data")) {
                            if (drew) {
                                imagePaths.clear();
                            }
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    String id = (String) json.get("id");
                                    String filePath = (String) json.get("filePath");
                                    String drawingNumber = (String) json.get("drawingNumber");
                                    String drawingName = (String) json.get("drawingName");
                                    String drawingGroupName = (String) json.get("drawingGroupName");
                                    filePath = Requests.networks + filePath;
                                    imagePaths.add(new PhotoBean(id, filePath, drawingNumber, drawingName, drawingGroupName));
                                }
                                taskPhotoAdapter.getData(imagePaths, wbsName);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (drew) {
                                imagePaths.clear();
                                imagePaths.add(new PhotoBean(
                                        "", "暂无数据", "暂无数据", "暂无数据", "暂无数据"));
                            }
                            taskPhotoAdapter.getData(imagePaths, wbsName);
                        }
                    }
                });
    }

    public static void getOko(final String wbsid, final String wbspath, final boolean isParent, final String wbsname, final boolean iswbs, final String type, final Activity activity) {
        final ArrayList<String> namess = new ArrayList<>();
        final ArrayList<String> ids = new ArrayList<>();
        final ArrayList<String> titlename = new ArrayList<>();
        Dates.getDialog(activity, "请求数据中");
        OkGo.post(Requests.WbsTaskGroup)
                .params("wbsId", wbsid)
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
                                Intent intent = new Intent(activity, TenanceviewActivity.class);
                                //加了任务数量的检查点
                                intent.putExtra("name", namess);

                                //检查点ID
                                intent.putExtra("ids", ids);
                                //检查点名称
                                intent.putExtra("title", titlename);
                                //节点ID
                                intent.putExtra("id", wbsid);
                                //节点路径
                                intent.putExtra("wbspath", wbspath);
                                //是否是父节点
                                intent.putExtra("isParent", isParent);
                                intent.putExtra("wbsname", wbsname);
                                intent.putExtra("iswbs", iswbs);
                                intent.putExtra("type", type);
                                intent.putExtra("status", "More");

                                activity.startActivity(intent);
                                Dates.disDialog();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.showShortToast("该节点未启动");
                            Intent intent = new Intent(activity, TenanceviewActivity.class);
                            //加了任务数量的检查点
                            intent.putExtra("name", namess);
                            //检查点ID
                            intent.putExtra("ids", ids);
                            //检查点名称
                            intent.putExtra("title", titlename);
                            //节点ID
                            intent.putExtra("id", wbsid);
                            //节点路径
                            intent.putExtra("wbspath", wbspath);
                            //是否是父节点
                            intent.putExtra("isParent", isParent);
                            intent.putExtra("wbsname", wbsname);
                            intent.putExtra("iswbs", iswbs);
                            intent.putExtra("type", type);
                            activity.startActivity(intent);
                            Dates.disDialog();
                        }

                    }
                });
    }

}
