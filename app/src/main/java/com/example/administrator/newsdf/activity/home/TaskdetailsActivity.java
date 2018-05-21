package com.example.administrator.newsdf.activity.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.FileUtils;
import com.example.administrator.newsdf.Adapter.AudioAdapter;
import com.example.administrator.newsdf.Adapter.TaskPhotoAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.same.DirectlyreplyActivity;
import com.example.administrator.newsdf.bean.Aduio_comm;
import com.example.administrator.newsdf.bean.Aduio_content;
import com.example.administrator.newsdf.bean.Aduio_data;
import com.example.administrator.newsdf.bean.PhotoBean;
import com.example.administrator.newsdf.callback.DetailsCallback;
import com.example.administrator.newsdf.callback.DetailsCallbackUtils;
import com.example.administrator.newsdf.callback.TaskCallbackUtils;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.utils.CameDialog;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.LogUtil;
import com.example.administrator.newsdf.utils.Requests;
import com.example.administrator.newsdf.utils.SPUtils;
import com.joanzapata.iconify.widget.IconTextView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;


/**
 * description: 任务详情
 *
 * @author: lx
 * date: 2018/2/6 0006 上午 9:25
 * update: 2018/2/6 0006
 * version:
 */
public class TaskdetailsActivity extends AppCompatActivity implements DetailsCallback, View.OnClickListener {
    //界面适配器
    private AudioAdapter mAdapter;
    private String id;
    private ArrayList<Aduio_content> contents;
    private ArrayList<Aduio_data> aduioDatas;
    private ArrayList<Aduio_comm> aduioComms;
    private static TaskdetailsActivity mContext;
    private TextView wbsnam;
    private TextView wbspath;
    private TextView comButton, comTitle;
    private String wtMainid = null,  wbsid,status;
    private String wbsName = null, usernma;
    /**
     * 图片查看的圆形图标
     */
    private CircleImageView Circle;
    private ArrayList<PhotoBean> imagePaths;
    private int page = 1;
    /**
     * 侧滑界面的listview的适配器
     */
    private TaskPhotoAdapter taskPhotoAdapter;
    //侧滑界面
    private DrawerLayout drawerLayout;
    //下拉刷新
    private SmartRefreshLayout drawerLayoutSmart;
    private ListView drawerLayoutList;
    private boolean drew;
    private static final int IMAGE_PICKER = 101;
    /**
     * 任务回复时展示图片的适配器
     */
    private LinearLayout comImg;
    private RecyclerView mRecyclerView;
    private IconTextView iconTextView;
    /**
     * 是否需要返回后刷新界面状态
     */
    private boolean Refresh = true;

    public static TaskdetailsActivity getInstance() {
        return mContext;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_auditparticulars);
        newArray();
        finById();
        mContext = this;
        //权限
        DetailsCallbackUtils.setCallBack(this);
        usernma = SPUtils.getString(mContext, "staffName", null);


        //侧滑栏关闭
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //侧滑栏关闭手势滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //关闭下拉刷新
        drawerLayoutSmart.setEnableRefresh(false);
        comTitle.setText("任务详情");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new AudioAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);


        /**
         *    侧拉listview上拉加载
         */
        drawerLayoutSmart.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                drew = false;
                /**
                 *查询当前任务节点图册
                 */
                homeUtils.photoAdm(wbsid, page, imagePaths, drew, taskPhotoAdapter, wbsName);
                //传入false表示加载失败
                refreshlayout.finishLoadmore(1500);
            }
        });
        final Intent intent = getIntent();
        try {
            id = intent.getExtras().getString("TaskId");
            wbsid = intent.getExtras().getString("wbsid");
            status = intent.getExtras().getString("status");
            if (status.equals("true")) {
                iconTextView.setVisibility(View.VISIBLE);
            } else {
                iconTextView.setVisibility(View.GONE);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        okgo(id);
        taskPhotoAdapter = new TaskPhotoAdapter(imagePaths, TaskdetailsActivity.this);
        drawerLayoutList.setAdapter(taskPhotoAdapter);
    }

    private void finById() {
        comImg = (LinearLayout) findViewById(R.id.com_img);
        iconTextView= (IconTextView) findViewById(R.id.task_icontextview);
        comImg.setOnClickListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayoutSmart = (SmartRefreshLayout) findViewById(R.id.drawerLayout_smart);
        drawerLayoutList = (ListView) findViewById(R.id.drawer_layout_list);
        wbspath = (TextView) findViewById(R.id.wbspath);
        comButton = (TextView) findViewById(R.id.audio_com_button);
        comTitle = (TextView) findViewById(R.id.audio_com_title);
        mRecyclerView = (RecyclerView) findViewById(R.id.handover_status_recycler);
        findViewById(R.id.taskManagement).setOnClickListener(this);
        findViewById(R.id.adui_com_back).setOnClickListener(this);
        findViewById(R.id.fab).setOnClickListener(this);
        comButton.setOnClickListener(this);
        comButton.setVisibility(View.GONE);
    }

    private void newArray() {
        //得到跳转到该Activity的Intent对象
        contents = new ArrayList<>();
        aduioDatas = new ArrayList<>();
        aduioComms = new ArrayList<>();
        imagePaths = new ArrayList<>();
    }

    /**
     * adapter获取ID
     */
    public String getId() {
        return wtMainid;
    }

    /**
     * 返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //抛出异常，在任务管理界面返回时不需要刷新数据，
        try {
            //判断状态是否改变
            if (!Refresh) {
                //改变了，调用刷新数据方法
                TaskCallbackUtils.removeCallBackMethod();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
        return true;
    }


    /**
     * startResult返回数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String id = data.getStringExtra("frag_id");
            comButton.setVisibility(View.GONE);
            comImg.setVisibility(View.VISIBLE);
            Refresh = false;
            okgo(id);
        } else if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images =
                        (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                //循环取出数据
                for (int i = 0; i < images.size(); i++) {
                    //获取图片大小
                    double mdouble = Dates.getDirSize(new File(images.get(i).path));
                    //如果图片大小不等于0.0，说明图片正常，如果等于这个值，那说明图片是损坏的
                    if (mdouble != 0.0) {
                        //图片正常压缩
                        ToastUtils.showLongToast("ss");
                        Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                        Tiny.getInstance().source(images.get(i).path).asFile().withOptions(options).compress(new FileCallback() {
                            @Override
                            public void callback(boolean isSuccess, String outfile) {
                                CameDialog.path.add(outfile);
                                //回复任务选择的图片
                                CameDialog.Dialogadapter.getData(CameDialog.path);
                            }
                        });
                    }
                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 回调刷新界面
     */
    @Override
    public void deleteTop() {
        okgo(id);
    }


    public String gettaskId() {
        return id;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.adui_com_back:
                finish();
                break;
            case R.id.fab:
                page = 1;
                drew = true;
                /**
                 *查询当前任务节点图册
                 */
                homeUtils.photoAdm(wbsid, page, imagePaths, drew, taskPhotoAdapter, wbsName);
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.com_img:
                Intent intent1 = new Intent(TaskdetailsActivity.this, TaskRecordActivity.class);
                intent1.putExtra("taskId", id);
                startActivity(intent1);
                break;
            case R.id.audio_com_button:
                Intent intent = new Intent(TaskdetailsActivity.this, DirectlyreplyActivity.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, 1);
                break;
            case R.id.taskManagement:
                if (status.equals("true")) {
                    homeUtils.getOko(wbsid, null, false, null, false, null, TaskdetailsActivity.this);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 完成详细数据
     */
    private void okgo(final String id) {
        OkGo.post(Requests.Detail)
                .params("wbsTaskId", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LogUtil.i("sss", id);
                        LogUtil.i("sss", s);
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
                            boolean up;
                            boolean down;
                            try {
                                up = data.getBoolean("up");
                                down = data.getBoolean("down");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                up = false;
                                down = false;
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
                            String status;
                            //状态
                            try {
                                status = wtMain.getString("status");
                                switch (status) {
                                    case "1":
                                        comImg.setVisibility(View.GONE);
                                        break;
                                    case "2":
                                        comButton.setVisibility(View.GONE);
                                        comImg.setVisibility(View.VISIBLE);
                                        break;
                                    default:
                                        break;
                                }
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
                            //是否被打回过
                            String iscallback;
                            try {
                                iscallback = wtMain.getString("iscallback");
                            } catch (JSONException e) {
                                iscallback = "";
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
                                    createByUserID, iscallback, createDate, wbsName, changeId, backdata));
                            for (int i = 0; i < subWbsTaskMains.length(); i++) {
                                JSONObject Sub = subWbsTaskMains.getJSONObject(i);
                                String replyID, uploadId, replyUserName, replyUserHeaderURL,
                                        subName, subWbsname,
                                        uploadContent, updateDate, uploadAddr, subLeadername,
                                        subLeaderid, subIscallback, callbackContent;
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
                                    updateDate = Sub.getString("updateDate");
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
                                try {
                                    //任务负责人人
                                    subLeadername = Sub.getString("leaderName");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    subLeadername = "";
                                }
                                try {
                                    //任务负责人id
                                    subLeaderid = Sub.getString("leaderId");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    subLeaderid = "";
                                }
                                try {
                                    //是否被打回
                                    subIscallback = Sub.getString("iscallback");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    subIscallback = "";
                                }
                                try {
                                    //打回说明
                                    callbackContent = Sub.getString("callbackContent");
                                } catch (JSONException e) {
//打回说明
                                    callbackContent = "";
                                }
                                String callbackTime;
                                try {
                                    //打回说明
                                    callbackTime = Sub.getString("callbackTime");
                                } catch (JSONException e) {
//打回说明
                                    callbackTime = ("");
                                }
                                String callbackId;
                                try {//打回人ID
                                    callbackId = Sub.getString("callbackId");
                                } catch (JSONException e) {
                                    //打回说明
                                    callbackId = "";
                                }
                                int isSmartProject;
                                try {//打回人ID
                                    isSmartProject = Sub.getInt("isSmartProject");
                                } catch (JSONException e) {
                                    //打回说明
                                    isSmartProject = 0;
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
                                        subWbsname, uploadContent, updateDate, uploadAddr, subLeadername, subLeaderid, subIscallback,
                                        callbackContent, callbackTime, callbackId, attachments, comments.length() + "",
                                        userimage, filename, isSmartProject, up, down));
                            }

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
                                        commentsContent, replyTime,filePathsMin,filePaths));
                            }
                            if (contents.get(0).getStatus().equals("0")) {
                                aduioDatas.clear();
                                aduioComms.clear();
                                if (usernma.equals(wtMain.getString("leaderName"))) {
                                    comButton.setVisibility(View.VISIBLE);
                                } else {
                                    comButton.setVisibility(View.GONE);
                                }
                            }
                            mAdapter.setmBanner(contents);
                            mAdapter.getmListA(aduioDatas);
                            mAdapter.getmListB(aduioComms);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        wbspath.setText(wbsName);
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (CameDialog.path.size() != 0) {
            for (int i = 0; i < CameDialog.path.size(); i++) {
                FileUtils.deleteFile(CameDialog.path.get(i));
            }
        }
        CameDialog.path.clear();
    }
}
