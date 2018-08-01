package com.example.administrator.newsdf.pzgc.activity.home;

import android.app.Activity;
import android.content.Intent;

import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.AuditdetailsAdapter;
import com.example.administrator.newsdf.pzgc.Adapter.TaskPhotoAdapter;
import com.example.administrator.newsdf.pzgc.activity.work.TenanceviewActivity;
import com.example.administrator.newsdf.pzgc.bean.Aduio_comm;
import com.example.administrator.newsdf.pzgc.bean.Aduio_content;
import com.example.administrator.newsdf.pzgc.bean.Aduio_data;
import com.example.administrator.newsdf.pzgc.bean.OrganizationEntity;
import com.example.administrator.newsdf.pzgc.bean.PhotoBean;
import com.example.administrator.newsdf.pzgc.callback.AuditDetailsCallbackUtils;
import com.example.administrator.newsdf.pzgc.utils.LogUtil;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.treeView.TaskTreeListViewAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

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

    public HashMap<String, String> hasmap;
    String orgId;

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
                        LogUtil.i("dssw", obj.getString("id"));
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
                        organization.setId(obj.getString("org_id"));
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
                        organization.setTypes("3,5");
                    }

                    organization.setIswbs(true);


                    try {
                        //是否是父节点
                        organization.setIsparent(obj.getBoolean("isParent"));
                    } catch (JSONException e) {
                        organization.setIsparent(true);
                    }
                    try {
                        int isParentFlag = obj.getInt("hasChildren");
                        if (isParentFlag > 0) {
                            //是叶子节点
                            organization.setIsleaf("0");
                        } else {
                            organization.setIsleaf("1");
                            //不是叶子节点
                        }
                    } catch (JSONException e) {
                        organization.setIsleaf("");
                    }
                    try {
                        //组织机构父级节点
                        String str = obj.getString("parent_id");
                        organization.setParentId(str);

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
                        organization.setTitle(obj.getString("wbsIndex"));
                    } catch (JSONException e) {
                        organization.setTitle("");
                    }
                    try {
                        organization.setPhone(obj.getString("id"));
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
                        LogUtil.i("ss", s);
                        if (s.contains("data")) {
                            if (drew) {
                                imagePaths.clear();
                            }
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                if (jsonArray.length() > 0) {
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

                                } else {
                                    if (drew) {
                                        imagePaths.clear();
                                        imagePaths.add(new PhotoBean(
                                                "", "暂无数据", "图纸：暂无数据", "图纸：暂无数据", "图纸：暂无数据"));
                                    }
                                    taskPhotoAdapter.getData(imagePaths, wbsName);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (drew) {
                                imagePaths.clear();
                                imagePaths.add(new PhotoBean(
                                        "", "暂无数据", "图纸：暂无数据", "图纸：暂无数据", "图纸：暂无数据"));
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

                        }

                    }
                });
    }

    public static void getStard(final String WbsId, int page,
                                final ArrayList<PhotoBean> stardPats,
                                final boolean drew,
                                final TaskPhotoAdapter taskStardAdapter,
                                final String wbsName) {
        OkGo.<String>get(Requests.StandardList)
                .params("WbsId", WbsId)
                .params("page", page)
                .params("rows", 30)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LogUtil.i("ss4", s);
                        if (s.contains("data")) {
                            if (drew) {
                                stardPats.clear();
                            }
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json = jsonArray.getJSONObject(i);
                                        String id = (String) json.get("id");
                                        String filePath = (String) json.get("filePath");
                                        String drawingNumber;
                                        try {
                                            drawingNumber = (String) json.get("standardNumber");
                                        } catch (JSONException e) {
                                            drawingNumber = "";
                                        }
                                        String drawingName;
                                        try {
                                            drawingName = (String) json.get("standardName");
                                        } catch (JSONException e) {
                                            drawingName = "";
                                        }
                                        String drawingGroupName;
                                        try {
                                            drawingGroupName = (String) json.get("standardGroupName");
                                        } catch (JSONException e) {
                                            drawingGroupName = "";
                                        }
                                        filePath = Requests.networks + filePath;
                                        stardPats.add(new PhotoBean(id, filePath, drawingNumber, drawingName, drawingGroupName));
                                    }
                                    taskStardAdapter.getData(stardPats, wbsName);

                                } else {
                                    if (drew) {
                                        stardPats.clear();
                                        stardPats.add(new PhotoBean(
                                                "", "暂无数据", "标准：暂无数据", "标准：暂无数据", "标准：暂无数据"));
                                    }
                                    taskStardAdapter.getData(stardPats, wbsName);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (drew) {
                                stardPats.clear();
                                stardPats.add(new PhotoBean(
                                        "", "暂无数据", "标准：暂无数据", "标准：暂无数据", "标准：暂无数据"));
                            }
                            taskStardAdapter.getData(stardPats, wbsName);

                        }
                    }
                });
    }

    String iscallback;

    /**
     * 完成详细数据
     */
    public void TaskAudit(final String id, final AuditdetailsAdapter mAdapter) {
        hasmap = new HashMap<>();
        OkGo.post(Requests.Detail)
                .params("wbsTaskId", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //任务详情
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                ArrayList<Aduio_content> contents = new ArrayList<>();
                                ArrayList<Aduio_data> aduioDatas = new ArrayList<>();
                                ArrayList<Aduio_comm> aduioComms = new ArrayList<>();
                                JSONObject data = jsonObject.getJSONObject("data");
                                JSONObject wtMain = data.getJSONObject("wtMain");
                                JSONObject createBy = wtMain.getJSONObject("createBy");
                                JSONArray subWbsTaskMains;
                                try {
                                    subWbsTaskMains = data.getJSONArray("subWbsTaskMains");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    subWbsTaskMains = new JSONArray();
                                }
                                JSONArray taskHi;
                                try {
                                    taskHi = data.getJSONArray("taskHi");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    taskHi = new JSONArray();
                                }
                                JSONArray comments = data.getJSONArray("comments");

                                int smartProjectType;
                                try {
                                    smartProjectType = wtMain.getInt("smartProjectType");
                                } catch (JSONException e) {
                                    smartProjectType = 0;
                                }
                                //是否打回
                                try {
                                    iscallback = wtMain.getString("iscallback");
                                } catch (JSONException e) {
                                    iscallback = "";
                                }
                                String wbsName;
                                //任务详情
                                try {
                                    wbsName = wtMain.getString("wbsName");
                                    hasmap.put("wbsName", wbsName);
                                } catch (JSONException e) {
                                    wbsName = "";
                                }
                                String wtMainid;
                                try {
                                    //唯一标识
                                    wtMainid = wtMain.getString("id");
                                    hasmap.put("id", wtMainid);
                                } catch (JSONException e) {
                                    wtMainid = "";
                                }
                                String name;
                                try {
                                    ///检查点
                                    name = wtMain.getString("name");
                                } catch (JSONException e) {
                                    name = "";
                                }
                                //是否是提亮
                                int isSmartProject;
                                try {
                                    isSmartProject = wtMain.getInt("isSmartProject");
                                } catch (JSONException e) {
                                    //打回说明
                                    isSmartProject = 0;
                                }

                                String status;
                                //状态
                                try {
                                    status = wtMain.getString("status");
                                } catch (JSONException e) {
                                    status = "";
                                }
                                String content;
                                //推送内容
                                try {
                                    content = wtMain.getString("content");
                                } catch (JSONException e) {

                                    content = "";
                                }
                                String leaderName = null;
                                //负责人
                                try {
                                    leaderName = wtMain.getString("leaderName");
                                } catch (JSONException e) {

                                    leaderName = "";
                                }
                                String leaderId = null;
                                //负责人ID
                                try {
                                    leaderId = wtMain.getString("leaderId");
                                } catch (JSONException e) {
                                    leaderId = "";
                                }
                                //是否已读
                                String isread = null;
                                try {
                                    isread = wtMain.getString("isread");
                                } catch (JSONException e) {
                                    leaderId = "";
                                }
                                //创建人ID  (路径：wtMain –> createBy -> id)
                                String createByUserID;
                                try {
                                    createByUserID = createBy.getString("id");
                                } catch (JSONException e) {
                                    createByUserID = "";
                                }
                                //标准
                                String checkStandard;
                                try {
                                    checkStandard = wtMain.getString("checkStandard");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    checkStandard = "";
                                }
                                //部位
                                String partContent;
                                try {
                                    partContent = wtMain.getString("partContent");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    partContent = "";
                                }
                                //更新时间
                                String createDate = wtMain.getString("createDate");
                                //wbsname
                                wbsName = wtMain.getString("wbsName");
                                //转交id
                                String changeId = null;
                                String backdata;
                                try {
                                    backdata = wtMain.getString("updateDate");
                                } catch (JSONException e) {
                                    //打回说明
                                    backdata = ("");
                                }
                                contents.add(new Aduio_content(wtMainid, name, status, content,
                                        leaderName, leaderId, isread,
                                        createByUserID, checkStandard, createDate, wbsName, changeId,
                                        backdata, partContent));


                                for (int i = 0; i < subWbsTaskMains.length(); i++) {
                                    JSONObject Sub = subWbsTaskMains.getJSONObject(i);
                                    String replyID, uploadId, replyUserName, replyUserHeaderURL,
                                            subName, subWbsname,
                                            uploadContent, updateDate, uploadAddr;
                                    JSONArray hments = new JSONArray();
                                    try {
                                        hments = Sub.getJSONArray("attachments");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    //  (回复详情列表)
                                    try {
                                        //唯一标识
                                        replyID = Sub.getString("id");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        replyID = "";
                                    }
                                    try {
                                        //上传人ID
                                        uploadId = Sub.getString("leaderId");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        uploadId = "";
                                    }
                                    try {
                                        //检查点
                                        subName = Sub.getString("name");
                                    } catch (JSONException e) {
                                        subName = "";
                                    }
                                    try {
                                        //wbsName
                                        subWbsname = Sub.getString("wbsName");
                                    } catch (JSONException e) {
                                        subWbsname = "";
                                    }
                                    try {
                                        //上传时间
                                        updateDate = Sub.getString("uploadTime");
                                    } catch (JSONException e) {
                                        updateDate = "";
                                    }

                                    try {
                                        //上传内容说明
                                        uploadContent = Sub.getString("uploadContent");
                                    } catch (JSONException e) {
                                        uploadContent = "";
                                    }
                                    try {
                                        // 上传人姓名 （路径：subWbsTaskMains  -> uploadUser -> realname）
                                        replyUserName = wtMain.getJSONObject("uploadUser").getString("realname");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        replyUserName = "";
                                    }
                                    //头像
                                    try {
                                        replyUserHeaderURL = wtMain.getJSONObject("uploadUser").getString("portrait");
                                    } catch (JSONException e) {
                                        replyUserHeaderURL = "";
                                    }

                                    orgId = Sub.getString("wbsId");
                                    try {
                                        //上传地点
                                        uploadAddr = Sub.getString("uploadAddr");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        uploadAddr = "";
                                    }
                                    boolean isFavorite;
                                    try {
                                        isFavorite = wtMain.getBoolean("isFavorite");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        isFavorite = false;
                                    }
                                    String userimage;
                                    try {
                                        String path = wtMain.getJSONObject("uploadUser").getString("portrait");
                                        userimage = Requests.networks + path;
                                    } catch (JSONException e) {
                                        userimage = "";
                                    }
                                    ArrayList<String> attachments = new ArrayList<>();
                                    ArrayList<String> filename = new ArrayList<>();
                                    //任务回复图片
                                    if (hments.length() > 0) {
                                        for (int j = 0; j < hments.length(); j++) {
                                            JSONObject json = hments.getJSONObject(j);
                                            String path = json.getString("filepath");
                                            String name1 = json.getString("filename");
                                            filename.add(name1);
                                            attachments.add(Requests.networks + path);
                                        }
                                    }
                                    if (!uploadContent.isEmpty()) {
                                        aduioDatas.add(new Aduio_data(replyID, uploadId, replyUserName, replyUserHeaderURL, subName,
                                                subWbsname, uploadContent, updateDate, uploadAddr, false, false, false,
                                                false, false, false, attachments, comments.length() + "",
                                                userimage, filename, isSmartProject, isFavorite, smartProjectType));
                                    }
                                }

                                if (iscallback.equals("1")) {
                                    for (int i = 0; i < taskHi.length(); i++) {
                                        JSONObject Sub = taskHi.getJSONObject(i);
                                        String replyID, uploadId, replyUserName, replyUserHeaderURL,
                                                subName, subWbsname,
                                                uploadContent, updateDate, uploadAddr;
                                        JSONArray hments = new JSONArray();
                                        try {
                                            hments = Sub.getJSONArray("attachments");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        //  (回复详情列表)
                                        try {
                                            //唯一标识
                                            replyID = Sub.getString("id");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            replyID = "";
                                        }
                                        try {
                                            //上传人ID
                                            uploadId = Sub.getString("leaderId");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            uploadId = "";
                                        }

                                        try {
                                            //检查点
                                            subName = Sub.getString("name");
                                        } catch (JSONException e) {
                                            subName = "";
                                        }
                                        try {
                                            //wbsName
                                            subWbsname = Sub.getString("wbsName");
                                        } catch (JSONException e) {
                                            subWbsname = "";
                                        }
                                        try {
                                            //上传时间
                                            updateDate = Sub.getString("uploadTime");
                                        } catch (JSONException e) {
                                            updateDate = "";
                                        }

                                        try {
                                            //上传内容说明
                                            uploadContent = Sub.getString("uploadContent");
                                        } catch (JSONException e) {
                                            uploadContent = "";
                                        }
                                        try {
                                            // 上传人姓名 （路径：subWbsTaskMains  -> uploadUser -> realname）
                                            replyUserName = wtMain.getJSONObject("uploadUser").getString("realname");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            replyUserName = "";
                                        }
                                        //头像
                                        try {
                                            replyUserHeaderURL = wtMain.getJSONObject("uploadUser").getString("portrait");
                                        } catch (JSONException e) {
                                            replyUserHeaderURL = "";
                                        }
                                        try {
                                            //上传地点
                                            uploadAddr = Sub.getString("uploadAddr");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            uploadAddr = "";
                                        }
                                        boolean isFavorite;
                                        try {
                                            isFavorite = wtMain.getBoolean("isFavorite");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            isFavorite = false;
                                        }
                                        String userimage;
                                        try {
                                            String path = wtMain.getJSONObject("uploadUser").getString("portrait");
                                            userimage = Requests.networks + path;
                                        } catch (JSONException e) {
                                            userimage = "";
                                        }

                                        ArrayList<String> attachments = new ArrayList<>();
                                        ArrayList<String> filename = new ArrayList<>();
                                        //任务回复图片
                                        if (hments.length() > 0) {
                                            for (int j = 0; j < hments.length(); j++) {
                                                JSONObject json = hments.getJSONObject(j);
                                                String path = json.getString("filepath");
                                                String name1 = json.getString("filename");
                                                filename.add(name1);
                                                attachments.add(Requests.networks + path);
                                            }
                                        }

                                        if (!uploadContent.isEmpty()) {
                                            aduioDatas.add(new Aduio_data(replyID, uploadId, replyUserName, replyUserHeaderURL, subName,
                                                    subWbsname, uploadContent, updateDate, uploadAddr, false, false, false,
                                                    false, false, false, attachments, comments.length() + "",
                                                    userimage, filename, isSmartProject, isFavorite, smartProjectType));
                                        }
                                    }
                                }

                                /**
                                 * 回复评论
                                 */
                                for (int i = 0; i < comments.length(); i++) {
                                    JSONObject json = comments.getJSONObject(i);
                                    JSONObject user = json.getJSONObject("user");
                                    //回复评论列表
                                    //唯一标识
                                    String comments_id = json.getString("id");
                                    //回复人ID
                                    String replyId = json.getString("replyId");
                                    //回复人姓名(路径：comments –> user -> realname)
                                    String realname = user.getString("realname");
                                    String portrait;
                                    try {
                                        portrait = user.getString("portrait");
                                    } catch (JSONException e) {
                                        portrait = "";
                                    }
                                    //回复人头像(路径：comments –> user -> portrait)
                                    String taskId = null;
                                    String commentsStatus;
                                    try {
                                        commentsStatus = json.getString("status");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        commentsStatus = "";
                                    }
                                    String statusName = null;
                                    //Pinglun内容说明
                                    String commentsContent;
                                    try {
                                        commentsContent = json.getString("content");
                                    } catch (JSONException e) {
                                        commentsContent = "";
                                    }
                                    //回复压缩图
                                    ArrayList<String> filePathsMin = new ArrayList<String>();
                                    String PathsMin;
                                    try {
                                        JSONArray pathsMin = json.getJSONArray("filePathsMin");
                                        for (int j = 0; j < pathsMin.length(); j++) {
                                            JSONObject pathjson = pathsMin.getJSONObject(j);
                                            PathsMin = pathjson.getString("filepath");
                                            PathsMin = Requests.networks + PathsMin;
                                            filePathsMin.add(PathsMin);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    //回复原图
                                    ArrayList<String> filePaths = new ArrayList<String>();
                                    String imagePath;
                                    try {
                                        JSONArray paths = json.getJSONArray("filePaths");
                                        for (int j = 0; j < paths.length(); j++) {
                                            JSONObject pathjson = paths.getJSONObject(j);
                                            imagePath = pathjson.getString("filepath");
                                            imagePath = Requests.networks + imagePath;
                                            filePaths.add(imagePath);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    //评论时间
                                    String replyTime = json.getString("replyTime");
                                    aduioComms.add(new Aduio_comm(comments_id, replyId, realname, portrait, taskId, commentsStatus, statusName,
                                            commentsContent, replyTime, filePathsMin, filePaths));
                                }

                                if (contents.get(0).getStatus().equals("0")) {
                                    aduioDatas.clear();
                                    aduioComms.clear();
                                }
                                mAdapter.setmBanner(contents, aduioDatas, aduioComms);
                                AuditDetailsCallbackUtils.updata();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                    }
                });

    }

    public String getId() {
        return orgId;
    }



}
