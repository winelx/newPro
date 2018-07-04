package com.example.administrator.newsdf.pzgc.activity.audit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.AuditdetailsAdapter;
import com.example.administrator.newsdf.pzgc.bean.Aduio_comm;
import com.example.administrator.newsdf.pzgc.bean.Aduio_content;
import com.example.administrator.newsdf.pzgc.bean.Aduio_data;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.LogUtil;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * description:
 *
 * @author lx
 *         date: 2018/7/4 0004 下午 3:15
 *         update: 2018/7/4 0004
 *         version:
 */

public class AuditdetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static AuditdetailsActivity mContext;
    private RecyclerView mRecyclerView;
    private TextView wbspath;
    private String wbsName = null, wbsid;
    private ArrayList<Aduio_content> contents;
    private ArrayList<Aduio_data> aduioDatas;
    private ArrayList<Aduio_comm> aduioComms;
    private AuditdetailsAdapter mAdapter;

    public static AuditdetailsActivity getInstance() {
        return mContext;
    }

    private String taskId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditdetails);
        Intent intent = getIntent();
        taskId = intent.getExtras().getString("TaskId");
        mRecyclerView = (RecyclerView) findViewById(R.id.auditdetails_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new AuditdetailsAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
        wbspath = (TextView) findViewById(R.id.auditdetails_path);
        mContext = this;
        newArray();
        okgo(taskId);
    }

    private void newArray() {
        //得到跳转到该Activity的Intent对象
        contents = new ArrayList<>();
        aduioDatas = new ArrayList<>();
        aduioComms = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 完成详细数据
     */
    private void okgo(final String id) {
        Dates.getDialogs(AuditdetailsActivity.this, "请求数据中");
        OkGo.post(Requests.Detail)
                .params("wbsTaskId", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LogUtil.i("task", s);
                        //任务详情
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                contents.clear();
                                aduioDatas.clear();
                                aduioComms.clear();
                            }
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONObject wtMain = data.getJSONObject("wtMain");
                            JSONObject createBy = wtMain.getJSONObject("createBy");
                            JSONArray subWbsTaskMains = data.getJSONArray("subWbsTaskMains");
                            JSONArray comments = data.getJSONArray("comments");

                            int smartProjectType;
                            try {
                                smartProjectType = wtMain.getInt("smartProjectType");
                            } catch (JSONException e) {
                                smartProjectType = 0;
                            }

                            //任务详情
                            try {
                                wbsName = wtMain.getString("wbsName");
                            } catch (JSONException e) {
                                wbsName = "";
                            }
                            try {
                                //唯一标识
                                wtMainid = wtMain.getString("id");
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
                                wbsid = Sub.getString("wbsId");
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
                                aduioDatas.add(new Aduio_data(replyID, uploadId, replyUserName, replyUserHeaderURL, subName,
                                        subWbsname, uploadContent, updateDate, uploadAddr, false, false, false,
                                        false, false, false, attachments, comments.length() + "",
                                        userimage, filename, isSmartProject, isFavorite, smartProjectType));
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
                                aduioComms.add(0, new Aduio_comm(comments_id, replyId, realname, portrait, taskId, commentsStatus, statusName,
                                        commentsContent, replyTime, filePathsMin, filePaths));
                            }
                            if (contents.get(0).getStatus().equals("0")) {
                                aduioDatas.clear();
                                aduioComms.clear();
                            }
                            mAdapter.setmBanner(contents, aduioDatas, aduioComms);
                            Dates.disDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (wbsName.length() != 0) {
                            wbspath.setText(wbsName);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Dates.disDialog();
                    }
                });
    }

    /**
     * adapter获取ID
     */
    String wtMainid;

    public String getId() {
        return wtMainid;
    }
}