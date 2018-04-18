package com.example.administrator.newsdf.activity.work;

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
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.newsdf.Adapter.TaskPhotoAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.homeUtils;
import com.example.administrator.newsdf.bean.Audio;
import com.example.administrator.newsdf.bean.PhotoBean;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.LogUtil;
import com.example.administrator.newsdf.utils.Request;
import com.example.administrator.newsdf.utils.WbsDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

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
            nodeWbsStatus, nodeWbsUsername, nodeWbsProgress;
    String wbsId, userID, status, wbsName, wbspath;

    ArrayList<Audio> mData;
    private PopupWindow popupWindow;
    private TextView nodeStartText, nodeStopText, nodeCompleteText;
    private ImageView nodeStart, nodeStop, nodeComplete;
    private Dialog mCameraDialog;
    private Context mContext;
    private WbsDialog selfDialog;
    private int number;
    private ArrayList<PhotoBean> imagePaths;
    private CircleImageView fab;
    private SmartRefreshLayout smartRefreshLayout;
    private DrawerLayout drawer_layout;
    private ListView drawer_layout_list;
    private TaskPhotoAdapter taskAdapter;
    private int page = 1;
    private boolean drew = true;
    private ArrayList<String> titlename;
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();
    EditText search;

    public NodedetailsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodedetails);
        mContext = NodedetailsActivity.this;
        mData = new ArrayList<>();
        imagePaths = new ArrayList<>();
        Intent intent = getIntent();
        //节点ID
        wbsId = intent.getExtras().getString("wbsId");
        wbsName = intent.getExtras().getString("wbsName");
        wbspath = intent.getExtras().getString("Name");
        findViewById(R.id.node_lin_complete).setOnClickListener(this);
        findViewById(R.id.node_lin_pro).setOnClickListener(this);
        findViewById(R.id.node_lin_stop).setOnClickListener(this);
        findViewById(R.id.node_lin_start).setOnClickListener(this);
        findViewById(R.id.node_commit).setOnClickListener(this);
        fab = (CircleImageView) findViewById(R.id.fab);
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
        userdetails();
        //返回
        findViewById(R.id.check_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //点击出侧拉
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                drew = true;
                homeUtils.photoAdm(wbsId, page, imagePaths, drew, taskAdapter, wbsName);
                drawer_layout.openDrawer(GravityCompat.START);
            }
        });
        /**
         *    侧拉listview上拉加载
         */
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                drew = false;
                homeUtils.photoAdm(wbsId, page, imagePaths, drew, taskAdapter, wbsName);
                taskAdapter.getData(imagePaths, wbsName);
                //传入false表示加载失败
                refreshlayout.finishLoadmore(1500);
            }
        });

    }

    void okgo() {
        OkGo.<String>post(Request.Wbsdetails)
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
     * 获取联系人
     */
    void userdetails() {
        OkGo.<String>post(Request.UserList)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                JSONObject json = jsonArray1.getJSONObject(i);
                                String id = json.getString("id");
                                String name = json.getString("name");
                                mData.add(new Audio(name, id));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                        if (status.equals("0")) {
                            ToastUtils.showShortToast("不可改变当前状态");
                        } else {
                            if (status == "3") {
                                ToastUtils.showShortToast("已经处于当前状态");
                            } else {
                                okgo1("3");
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

            default:
                break;
        }
    }

    /**
     * 修改
     */
    void commit() {
        OkGo.<String>post(Request.WbsTaskConfig)
                .params("id", wbsId)
                .params("leaderId", userID)
                .params("finish", number)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LogUtil.i("wbsOID", s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            String msg = jsonObject.getString("msg");
                            if (ret == 0) {
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
     * 状态修改
     */
    void okgo1(final String str) {
        OkGo.post(Request.WbsTaskConfig)
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
                            int ret = jsonObject.getInt("ret");
                            String msg = jsonObject.getString("msg");
                            if (ret != 0) {
                                if (Objects.equals(str, "1")) {
                                    ToastUtils.showShortToast(msg);
                                } else if (Objects.equals(str, "2")) {
                                    ToastUtils.showShortToast(msg);
                                } else if (Objects.equals(str, "3")) {
                                    ToastUtils.showShortToast(msg);
                                }
                            } else {
                                //接口调用成功
                                if (Objects.equals(str, "1")) {
                                    nodeStartText.setTextColor(Color.parseColor("#808080"));
                                    nodeStopText.setTextColor(Color.parseColor("#f44949"));
                                    nodeCompleteText.setTextColor(Color.parseColor("#5096F8"));
                                    nodeStart.setBackgroundResource(R.mipmap.node_start);
                                    nodeStop.setBackgroundResource(R.mipmap.node_stop_f);
                                    nodeComplete.setBackgroundResource(R.mipmap.node_complete_f);
                                    status = "1";
                                    nodeWbsStatus.setText("施工中");

                                } else if (Objects.equals(str, "2")) {
                                    nodeStartText.setTextColor(Color.parseColor("#ff99cc00"));
                                    nodeCompleteText.setTextColor(Color.parseColor("#5096F8"));
                                    nodeStopText.setTextColor(Color.parseColor("#808080"));
                                    nodeStop.setBackgroundResource(R.mipmap.node_stop);
                                    nodeStart.setBackgroundResource(R.mipmap.node_start_f);
                                    nodeComplete.setBackgroundResource(R.mipmap.node_complete_f);
                                    status = "2";
                                    nodeWbsStatus.setText("暂停施工");

                                } else if (Objects.equals(str, "3")) {
                                    nodeStartText.setTextColor(Color.parseColor("#ff99cc00"));
                                    nodeStopText.setTextColor(Color.parseColor("#5096F8"));
                                    nodeCompleteText.setTextColor(Color.parseColor("#808080"));
                                    nodeComplete.setBackgroundResource(R.mipmap.node_complete);
                                    nodeStart.setBackgroundResource(R.mipmap.node_start_f);
                                    nodeStop.setBackgroundResource(R.mipmap.node_stop_f);
                                    nodeWbsStatus.setText("已完成");
                                    status = "3";
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

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

    private void getOko(final String str, final String wbsname) {
        Dates.getDialog(NodedetailsActivity.this, "请求数据中");
        OkGo.post(Request.PUSHList)
                .params("wbsId", str)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.contains("data")) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                titlename = new ArrayList<>();
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
                                intent.putExtra("ids", ids);
                                intent.putExtra("title", titlename);
                                intent.putExtra("titles", "任务下发");
                                intent.putExtra("wbsPath", wbspath);
                                intent.putExtra("id", str);
                                intent.putExtra("wbsnam", wbsname);
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Dates.disDialog();
                        } else {
                            ToastUtils.showShortToast("该节点未启动");
                            Intent intent = new Intent(NodedetailsActivity.this, MissionpushActivity.class);
                            intent.putExtra("ids", ids);
                            intent.putExtra("title", titles);
                            intent.putExtra("id", str);
                            intent.putExtra("wbsnam", wbsname);
                            intent.putExtra("titles", "任务下发");
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
            userID = data.getStringExtra("userId");
            nodeWbsUsername.setText(user);
        }
    }
}
