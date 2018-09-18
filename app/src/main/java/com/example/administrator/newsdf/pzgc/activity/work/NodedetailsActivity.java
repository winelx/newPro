package com.example.administrator.newsdf.pzgc.activity.work;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.TaskPhotoAdapter;
import com.example.administrator.newsdf.pzgc.activity.home.HomeUtils;
import com.example.administrator.newsdf.pzgc.activity.home.same.WorkareaActivity;
import com.example.administrator.newsdf.pzgc.bean.PhotoBean;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.FloatMeunAnims;
import com.example.administrator.newsdf.pzgc.utils.LogUtil;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;
import com.example.administrator.newsdf.pzgc.utils.WbsDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;


/**
 * @author lx
 */

/**
 * description:  节点任务配置
 *
 * @author lx
 *         date: 2018/3/22 0022 下午 2:39
 *         update: 2018/3/22 0022
 *         version:
 */
public class NodedetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView nodeWbsName, nodeWbsProject, nodeWbsType,
            nodeWbsStatus, nodeWbsUsername, nodeWbsProgress, node_lin_workarea_item;
    String wbsId, userID, status, wbsName, wbspath, type;

    private TextView nodeStartText, nodeStopText, nodeCompleteText, drawer_layout_text;
    private ImageView nodeStart, nodeStop, nodeComplete;
    private Dialog mCameraDialog;
    private Context mContext;
    private WbsDialog selfDialog;
    private int number;
    private ArrayList<PhotoBean> imagePaths;
    private SmartRefreshLayout smartRefreshLayout;
    private DrawerLayout drawer_layout;
    private ListView drawer_layout_list;
    private TaskPhotoAdapter taskAdapter;
    private int page = 1;
    private boolean drew = true, iswbs, isParent;
    private ArrayList<String> titlename;

    private ArrayList<String> ids = new ArrayList<>();
    private String usernameId, workArea, workareaId;
    //弹出框
    private CircleImageView fab;
    private LinearLayout meun_standard, meun_photo, node_lin_workarea;
    private FloatMeunAnims floatMeunAnims;
    private boolean liststatus = true;
    boolean anim = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodedetails);
        mContext = NodedetailsActivity.this;
        floatMeunAnims = new FloatMeunAnims();

        imagePaths = new ArrayList<>();
        Intent intent = getIntent();
        //节点ID
        iswbs = intent.getExtras().getBoolean("iswbs");
        isParent = intent.getExtras().getBoolean("isParent");
        type = intent.getExtras().getString("type");
        wbsId = intent.getExtras().getString("wbsId");
        wbsName = intent.getExtras().getString("wbsName");
        wbspath = intent.getExtras().getString("wbspath");
        findViewById(R.id.node_lin_complete).setOnClickListener(this);
        findViewById(R.id.node_lin_pro).setOnClickListener(this);
        findViewById(R.id.node_lin_stop).setOnClickListener(this);
        findViewById(R.id.node_lin_start).setOnClickListener(this);
        findViewById(R.id.node_commit).setOnClickListener(this);
        findViewById(R.id.node_lin_workarea).setOnClickListener(this);
        fab = (CircleImageView) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        node_lin_workarea_item = (TextView) findViewById(R.id.node_lin_workarea_item);
        meun_standard = (LinearLayout) findViewById(R.id.meun_standard);
        meun_photo = (LinearLayout) findViewById(R.id.meun_photo);
        drawer_layout_text = (TextView) findViewById(R.id.drawer_layout_text);

        meun_photo.setOnClickListener(this);
        meun_standard.setOnClickListener(this);
        //任务配置
        findViewById(R.id.node_configuration_task).setOnClickListener(this);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.drawerLayout_smart);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        nodeStartText = (TextView) findViewById(R.id.node_start_text);
        nodeStopText = (TextView) findViewById(R.id.node_stop_text);
        nodeCompleteText = (TextView) findViewById(R.id.node_complete_text);
        drawer_layout_list = (ListView) findViewById(R.id.drawer_layout_list);
        nodeStart = (ImageView) findViewById(R.id.node_start);
        nodeStop = (ImageView) findViewById(R.id.node_stop);
        nodeComplete = (ImageView) findViewById(R.id.node_complete);
        nodeWbsName = (TextView) findViewById(R.id.node_wbs_name);
        nodeWbsProject = (TextView) findViewById(R.id.node_wbs_project);
        nodeWbsType = (TextView) findViewById(R.id.node_wbs_type);
        nodeWbsStatus = (TextView) findViewById(R.id.node_wbs_status);
        nodeWbsUsername = (TextView) findViewById(R.id.node_wbs_username);
        nodeWbsProgress = (TextView) findViewById(R.id.node_wbs_progress);
        findViewById(R.id.user_list).setOnClickListener(this);
        //禁止下拉
        smartRefreshLayout.setEnableRefresh(false);
        //禁止手势
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //侧滑栏关闭手势滑动
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //背景透明度
        drawer_layout.setScrimColor(Color.TRANSPARENT);
        taskAdapter = new TaskPhotoAdapter(imagePaths, NodedetailsActivity.this);
        drawer_layout_list.setAdapter(taskAdapter);
        okgo();

        //返回
        findViewById(R.id.check_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /**
         *    侧拉listview上拉加载
         */
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                drew = false;
                page++;
                if (liststatus) {
                    HomeUtils.photoAdm(wbsId, page, imagePaths, drew, taskAdapter, wbsName);
                    //传入false表示加载失败
                } else {
                    HomeUtils.photoAdm(wbsId, page, imagePaths, drew, taskAdapter, wbsName);
                    //传入false表示加载失败
                }
                refreshlayout.finishLoadmore(1000);
            }
        });
    }
    //请求数据
    void okgo() {
        OkGo.<String>post(Requests.Wbsdetails)
                .params("id", wbsId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LogUtil.i("wbsOID", s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONObject json = jsonObject.getJSONObject("data");
                            nodeWbsUsername.setText(json.getString("leaderName"));
                            status = json.getString("status");
                            String type;
                            try {
                                type = json.getString("projectTypeName");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                type = "";
                            }
                            nodeWbsType.setText(type);
                            nodeWbsProject.setText(json.getString("orgName"));
                            nodeWbsName.setText(json.getString("name"));
                            userID = json.getString("leaderId");
                            String finish = json.getString("finish") + "%";
                            nodeWbsProgress.setText(finish);
                            nodeStatus(status);
                            workArea = json.getString("workArea");
                            node_lin_workarea_item.setText(workArea);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * @param str
     */
    private void nodeStatus(String str) {
        switch (str) {
            case "0":
                nodeWbsStatus.setText("未启动");
                nodeStartText.setTextColor(Color.parseColor("#5096F8"));
                nodeStart.setBackgroundResource(R.mipmap.node_start_f);
                break;
            case "1":
                nodeStartText.setTextColor(Color.parseColor("#808080"));
                nodeStopText.setTextColor(Color.parseColor("#f44949"));
                nodeCompleteText.setTextColor(Color.parseColor("#5096F8"));
                nodeStart.setBackgroundResource(R.mipmap.node_start);
                nodeStop.setBackgroundResource(R.mipmap.node_stop_f);
                nodeComplete.setBackgroundResource(R.mipmap.node_complete_f);
                nodeWbsStatus.setText("施工中");
                break;
            case "2":
                nodeStartText.setTextColor(Color.parseColor("#ff99cc00"));
                nodeCompleteText.setTextColor(Color.parseColor("#5096F8"));
                nodeStopText.setTextColor(Color.parseColor("#808080"));
                nodeStop.setBackgroundResource(R.mipmap.node_stop);
                nodeStart.setBackgroundResource(R.mipmap.node_start_f);
                nodeComplete.setBackgroundResource(R.mipmap.node_complete_f);
                nodeWbsStatus.setText("暂停施工");
                break;
            case "3":
                nodeStartText.setTextColor(Color.parseColor("#ff99cc00"));
                nodeStopText.setTextColor(Color.parseColor("#f44949"));
                nodeCompleteText.setTextColor(Color.parseColor("#808080"));
                nodeComplete.setBackgroundResource(R.mipmap.node_complete);
                nodeStart.setBackgroundResource(R.mipmap.node_start_f);
                nodeStop.setBackgroundResource(R.mipmap.node_stop_f);
                nodeWbsStatus.setText("完成施工");
                break;
            default:
                break;
        }

    }

    /**
     * 修改任务配置
     */
    void commit() {
        OkGo.<String>post(Requests.WbsTaskConfig)
                .params("id", wbsId)
                .params("leaderId", usernameId)
                .params("finish", number)
                .params("workarea", workareaId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LogUtil.i("wbsOID", s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            String msg = jsonObject.getString("msg");
                            if (ret == 0) {
                                okgo();
                                ToastUtils.showShortToast(msg);
                            } else {
                                ToastUtils.showShortToast(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /**
     * 任务状态修改
     */
    void okgo1(final String str) {
        String leaderId = SPUtils.getString(mContext, "staffId", null);
        if (leaderId.equals(userID)) {
            OkGo.post(Requests.WbsTaskConfig)
                    .params("id", wbsId)
                    .params("optStatus", str)
                    .params("leaderId", userID)
                    .params("finish", number + "")
                    .execute(new StringCallback() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                String msg = jsonObject.getString("msg");
                                ToastUtils.showShortToast(msg);
                                    okgo();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
        } else {
            ToastUtils.showLongToast("只有责任人能修改状态");
        }
    }

    /**
     * 弹出框。
     */
    public void setDialog() {
        mCameraDialog = new Dialog(NodedetailsActivity.this, R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(mContext).inflate(
                R.layout.node_editext, null);
        //初始化视图
        final Button send = root.findViewById(R.id.par_button);
        final EditText editext = root.findViewById(R.id.par_editext);
        //拿到回复人
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editext.getText().toString();
                if (content.length() != 0) {
                    number = Integer.valueOf(content);
                    if (number > 100 || number < 0) {
                        ToastUtils.showLongToast("输入范围:0--100");
                        editext.setText("");
                    } else {
                        nodeWbsProgress.setText(content + "%");
                        mCameraDialog.dismiss();
                    }
                } else {
                    ToastUtils.showShortToast("输入不能空");
                }
            }
        });
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        // 添加动画
        dialogWindow.setWindowAnimations(R.style.DialogAnimation);
        // 获取对话框当前的参数值
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        // 新位置X坐标
        lp.x = 0;
        // 新位置Y坐标
        lp.y = 0;
        // 宽度
        lp.width = (int) mContext.getResources().getDisplayMetrics().widthPixels;
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        // 透明度
        lp.alpha = 9f;
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }


    /**
     * 任务配置
     */
    String name, id;

    /**
     * 跳转界面请求的数据
     */
    private void getOko(final String str, final String wbsname) {
        Dates.getDialog(NodedetailsActivity.this, "请求数据中");
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
                                Intent intent = new Intent(NodedetailsActivity.this, MissionpushActivity.class);
                                //当前节点下任务项ID
                                intent.putExtra("ids", ids);
                                //当前节点任务项名
                                intent.putExtra("title", titlename);
                                intent.putExtra("titles", "任务配置");
                                //当前节点名称
                                intent.putExtra("wbsname", wbspath);
                                //当前节点ID
                                intent.putExtra("id", str);
                                //当前节点路径
                                intent.putExtra("wbsPath", wbsname);
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
                            Intent intent = new Intent(NodedetailsActivity.this, MissionpushActivity.class);
                            //当前节点下任务项ID
                            intent.putExtra("ids", ids);
                            //当前节点任务项名
                            intent.putExtra("title", titlename);
                            intent.putExtra("titles", "任务配置");
                            //当前节点名称
                            intent.putExtra("wbsPath", wbsname);
                            //当前节点ID
                            intent.putExtra("id", str);
                            //当前节点路径
                            intent.putExtra("wbsname", wbspath);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            String user = data.getStringExtra("name");
            usernameId = data.getStringExtra("userId");
            nodeWbsUsername.setText(user);
        } else if (requestCode == 2 && resultCode == 3) {
            node_lin_workarea_item.setText(data.getStringExtra("name"));
            workareaId = data.getStringExtra("userId");
        }
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.meun_photo:
                //请求图纸
                //加载第一页
                page = 1;
                //请求数据时清除之前的
                drew = true;
                imagePaths.clear();
                taskAdapter.getData(imagePaths, "");
                drawer_layout_text.setText("图纸");
                //网络请求
                Dates.getDialog(NodedetailsActivity.this, "请求数据中...");
                HomeUtils.photoAdm(wbsId, page, imagePaths, drew, taskAdapter, wbsName);
                //上拉加载的状态判断
                liststatus = true;
                drawer_layout.openDrawer(GravityCompat.START);
                break;
            case R.id.meun_standard:
                //标准
                //加载第一页
                page = 1;
                //请求数据时清除之前的
                drew = true;
                //上拉加载的状态判断
                liststatus = false;
                imagePaths.clear();
                drawer_layout_text.setText("标准");
                taskAdapter.getData(imagePaths, "");
                Dates.getDialog(NodedetailsActivity.this, "请求数据中...");
                HomeUtils.getStard(wbsId, page, imagePaths, drew, taskAdapter, wbsName);
                drawer_layout.openDrawer(GravityCompat.START);
                break;
            case R.id.fab:
                //打开meun选项
                if (anim) {
                    floatMeunAnims.doclickt(meun_photo, meun_standard, fab);
                    anim = false;
                } else {
                    floatMeunAnims.doclicktclose(meun_photo, meun_standard, fab);
                    anim = true;
                }
                break;
            case R.id.node_configuration_task:
                //任务配置
                getOko(wbsId, wbsName);
                break;
            case R.id.node_commit:
                commit();
                break;
            case R.id.node_lin_pro:
                setDialog();
                break;
            case R.id.user_list:
                //选择责任人
                Intent intent = new Intent(mContext, ContactPeopleActivity.class);
                intent.putExtra("data", "newpush");
                startActivityForResult(intent, 1);
                break;
            case R.id.node_lin_start:
                //更改状态，判断是否处于该状态 启动
                selfDialog = new WbsDialog(NodedetailsActivity.this);
                selfDialog.setMessage("是否更改当前状态");
                selfDialog.setYesOnclickListener("确定", new WbsDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        selfDialog.dismiss();
                        if (status == "1") {
                            ToastUtils.showShortToast("已经处于当前状态");
                        } else {
                            okgo1("1");
                        }
                    }
                });
                selfDialog.setNoOnclickListener("取消", new WbsDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        selfDialog.dismiss();
                    }
                });
                selfDialog.show();
                break;
            case R.id.node_lin_stop:
                //更改状态，判断是否处于该状态 暂停
                selfDialog = new WbsDialog(NodedetailsActivity.this);
                selfDialog.setMessage("是否更改当前状态");
                selfDialog.setYesOnclickListener("确定", new WbsDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        selfDialog.dismiss();
                        if (status.equals("0")) {
                            ToastUtils.showShortToast("不可改变当前状态");
                        } else {
                            if (status == "2") {
                                ToastUtils.showShortToast("已经处于当前状态");
                            } else {
                                okgo1("2");
                            }
                        }
                    }
                });
                selfDialog.setNoOnclickListener("取消", new WbsDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        selfDialog.dismiss();
                    }
                });
                selfDialog.show();
                break;
            case R.id.node_lin_complete:
                //完成
                selfDialog = new WbsDialog(NodedetailsActivity.this);
                selfDialog.setMessage("是否更改当前状态");
                selfDialog.setYesOnclickListener("确定", new WbsDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        selfDialog.dismiss();
                        String[] split = nodeWbsProgress.getText().toString().split("%");
                        String s1 = split[0];
                        if ("100".equals(s1)) {
                            ToastUtils.showLongToast(s1);
                            if (status.equals("0")) {
                                ToastUtils.showShortToast("不可改变当前状态");
                            } else {
                                if (status == "3") {
                                    ToastUtils.showShortToast("不能直接变更到此状态");
                                } else {
                                    okgo1("3");
                                }
                            }
                        } else {
                            ToastUtils.showLongToast("完成度必须为100%才能修改到完成状态");
                        }
                    }


                });
                selfDialog.setNoOnclickListener("取消", new WbsDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        selfDialog.dismiss();
                    }
                });
                selfDialog.show();
                break;
            case R.id.node_lin_workarea:
                //修改工区
                if (status.equals("0")){
                    Intent intent1 = new Intent(mContext, WorkareaActivity.class);
                    startActivityForResult(intent1, 2);
                }else {
                    ToastUtils.showShortToast("已启动，不可修改工区");
                }

                break;
            default:
                break;
        }
    }

}
