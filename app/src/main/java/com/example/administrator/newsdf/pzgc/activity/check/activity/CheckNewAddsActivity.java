package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.CheckUtils;
import com.example.administrator.newsdf.pzgc.utils.DialogUtils;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.adapter.CheckNewAdapter;
import com.example.administrator.newsdf.pzgc.bean.chekitemList;
import com.example.administrator.newsdf.pzgc.callback.CheckNewCallback;
import com.example.administrator.newsdf.pzgc.callback.CheckNewCallbackUtils;
import com.example.administrator.newsdf.pzgc.callback.CheckTaskCallbackUtils;
import com.example.administrator.newsdf.pzgc.view.DKDragView;
import com.example.baselibrary.inface.NetworkCallback;
import com.example.baselibrary.inface.Onclicklitener;
import com.example.baselibrary.base.BaseActivity;

import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.baselibrary.ui.activity.SignatureViewActivity;
import com.example.baselibrary.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.example.baselibrary.view.BaseDialog;
import com.example.timepickter.TimePickerDialog;
import com.example.timepickter.data.Type;
import com.example.timepickter.listener.OnDateSetListener;
import com.joanzapata.iconify.widget.IconTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.Date;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.lzy.okgo.OkGo.post;

/**
 * Created by Administrator on 2018/8/31 0031.
 * 内业检查新增界面
 */

public class CheckNewAddsActivity extends BaseActivity implements View.OnClickListener, CheckNewCallback {
    /**
     * 控件
     */
    //选择日期
    private TimePickerDialog mDialogYearMonthDay;
    private TextView datatime, checkNewNumber, categoryItem, checklistmeuntext, titleView,
            checkNewWebtext, checkUsername, checkNewOrgname, wbsName;
    private LinearLayout checkNewData;
    private LinearLayout checkImport;
    private LinearLayout checkCategory;
    private DrawerLayout drawerLayout;
    private GridView checklist;
    private EditText checkNewTasktitle, checkNewTemporarysite;
    private Button checkNewButton;
    private DKDragView dkDragView;
    //参数
    private String name, orgId, categoryId = "", taskId, nodeId, type;
    private CheckNewAdapter adapter;
    private ArrayList<chekitemList> mData;
    private static CheckNewAddsActivity mContext;
    private IconTextView icontextviewone, icontextviewtwo;
    private LinearLayout checklistmeun;
    private LinearLayout checkNewDialog;
    private CheckUtils checkUtils;
    private ArrayList<View> viewlist = new ArrayList<>();
    private ArrayList<View> tVisibility = new ArrayList<>();
    //当前单据状态
    private int status;
    private String success = null;

    public static CheckNewAddsActivity getInstance() {
        return mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_new_add);
        mContext = this;
        CheckNewCallbackUtils.setCallback(this);
        Intent intent = getIntent();
        //导入时的wbs
        orgId = intent.getStringExtra("orgId");
        //所属标段名称
        name = intent.getStringExtra("name");
        //
        type = intent.getStringExtra("type");
        try {
            //当前检查任务的id
            taskId = intent.getStringExtra("taskId");
        } catch (NullPointerException e) {
            e.printStackTrace();
            taskId = "";
        }
        findbyid();
        initData();
        if (taskId == null) {
            checkNewOrgname.setText(SPUtils.getString(mContext, "username", ""));
            checkUsername.setText(SPUtils.getString(mContext, "staffName", ""));
            datatime.setText(Dates.getDay().substring(0,7));
            checkNewWebtext.setText(name);
            statusF();
        } else {
            getdata();
            getcheckitemList();
        }
        //初始化时间选择器
        dialog();
    }

    private void findbyid() {
        //分数
        //wbs路径
        wbsName = (TextView) findViewById(R.id.check_wbspath);
        //指示箭头 类别和时间
        icontextviewone = (IconTextView) findViewById(R.id.IconTextViewone);
        icontextviewtwo = (IconTextView) findViewById(R.id.IconTextViewtwo);
        //添加入集合，根据操作进行隐藏
        tVisibility.add(icontextviewone);
        tVisibility.add(icontextviewtwo);
        //检查人
        checkUsername = (TextView) findViewById(R.id.check_username);
        //检查名称
        checkNewDialog = (LinearLayout) findViewById(R.id.check_new_dialog);
        checkNewDialog.setVisibility(View.GONE);
        //检查标段
        checkNewWebtext = (TextView) findViewById(R.id.check_new_webtext);
        //检查组织
        checkNewOrgname = (TextView) findViewById(R.id.check_new_orgname);
        //检查按钮
        checkNewButton = (Button) findViewById(R.id.check_new_buttons);
        //分数
        checkNewNumber = (TextView) findViewById(R.id.check_new_number);
        //标题
        checkNewTasktitle = (EditText) findViewById(R.id.check_new_tasktitle);
        viewlist.add(checkNewTasktitle);
        //临时部位
        checkNewTemporarysite = (EditText) findViewById(R.id.check_new_temporarysite);
        viewlist.add(checkNewTemporarysite);
        //meun
        checklistmeuntext = (TextView) findViewById(R.id.checklistmeuntext);
        checklistmeuntext.setTextSize(15);
        //检查标准类别
        categoryItem = (TextView) findViewById(R.id.category_item);
        //导入的wbs路径
        //wbs Tree
        checklist = (GridView) findViewById(R.id.checklist);
        //检查标准类别
        checkCategory = (LinearLayout) findViewById(R.id.Check_category);
        viewlist.add(checkCategory);
        //抽屉控件
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //导入标段
        checkImport = (LinearLayout) findViewById(R.id.check_import);
        tVisibility.add(checkImport);
        //时间选择器选择时间后显示
        titleView = (TextView) findViewById(R.id.titleView);
        //具体时间
        datatime = (TextView) findViewById(R.id.check_new_data_tx);
        //现在时间（弹出时间选择器）
        checkNewData = (LinearLayout) findViewById(R.id.check_new_data);
        viewlist.add(checkNewData);
        //拖动控件
        dkDragView = (DKDragView) findViewById(R.id.float_suspension);
    }

    private void initData() {
        mData = new ArrayList<>();
        checkUtils = new CheckUtils();
        //显示meun控件
        checklistmeuntext.setVisibility(View.VISIBLE);
        //关闭边缘滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //侧滑栏关闭手势滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //展示侧拉界面后，背景透明度（当前透明度为完全透明）
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        checkNewData.setOnClickListener(this);
        checkImport.setOnClickListener(this);
        checkCategory.setOnClickListener(this);
        checkNewButton.setOnClickListener(this);
        checklistmeun = (LinearLayout) findViewById(R.id.checklistmeun);
        checklistmeun.setOnClickListener(this);
        findViewById(R.id.checklistback).setOnClickListener(this);
        //设置不允许超过的边界（左上右下）
        dkDragView.setBoundary(0, 130, 0, 190);
        dkDragView.setOnDragViewClickListener(new DKDragView.onDragViewClickListener() {
            @Override
            public void onClick() {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });
        titleView.setText("新增检查");
        adapter = new CheckNewAdapter(mContext, mData);
        checklist.setAdapter(adapter);
        checklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent(mContext, CheckitemActivity.class);
                intent2.putExtra("taskId", taskId);
                intent2.putExtra("orgId", orgId);
                intent2.putExtra("number", mData.get(position).getPos());
                intent2.putExtra("position", mData.get(position).getPos());
                intent2.putExtra("size", mData.size());
                intent2.putExtra("id", mData.get(position).getId());
                intent2.putExtra("success", success);
                startActivity(intent2);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_new_data:
                //打开时间选择器
                mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.check_import:
                Intent intent1 = new Intent(CheckNewAddsActivity.this, CheckTreeActivity.class);
                intent1.putExtra("orgId", orgId);
                intent1.putExtra("name", name);
                startActivityForResult(intent1, 2);
                break;
            case R.id.Check_category:
                //类别
                Intent intent = new Intent(mContext, CheckTaskCategoryActivity.class);
                intent.putExtra("wbsId", orgId);
                intent.putExtra("type", type);
                startActivityForResult(intent, 1);
                break;
            case R.id.checklistmeun:
                //获取当前按钮名称，根据名称处理点击事件
                String string = checklistmeuntext.getText().toString();
                String content = checkNewTemporarysite.getText().toString();
                String wbspath = wbsName.getText().toString();
                if ("保存".equals(string)) {
                    //如果是保存，
                    //获取输入框内容，对比wbsName,
                    if (categoryItem.length() > 0) {
                        //选择的webs和手动输入内容必须存在一个，
                        String title = checkNewTasktitle.getText().toString();
                        if (title.length() > 0) {
                            checklistmeun.setClickable(false);
                            //如果存在一个或者都存在，就调用接口
                            Save(wbspath, nodeId);
                        } else {
                            ToastUtils.showLongToast("检查计划名称不能为空");
                        }
                    } else {
                        ToastUtils.showLongToast("类别不能为空");
                    }
                } else {
                    if (!string.isEmpty()) {
                        statusF();
                    }
                }
                break;
            case R.id.checklistback:
                finish();
                break;
            case R.id.check_new_buttons:
                String str = checkNewButton.getText().toString();
                if ("开始检查".equals(str)) {
                    Intent intent2 = new Intent(mContext, CheckitemActivity.class);
                    intent2.putExtra("taskId", taskId);
                    intent2.putExtra("orgId", orgId);
                    intent2.putExtra("number", mData.get(0).getPos());
                    intent2.putExtra("position", mData.get(0).getPos());
                    intent2.putExtra("size", mData.size());
                    startActivity(intent2);
                } else if ("提交".equals(str)) {
                    BaseDialog.confirmdialog(mContext, "提示", "是否提交数据", new Onclicklitener() {
                        @Override
                        public void confirm(String string) {
                            senddata();
                        }

                        @Override
                        public void cancel(String string) {

                        }
                    });

                } else if ("确认并签证".equals(str)) {
                    BaseDialog.confirmdialog(this, "是否确认？", null, new Onclicklitener() {
                        @Override
                        public void confirm(String string) {
                            ToastUtils.showShortToast(string);
                        }

                        @Override
                        public void cancel(String string) {
                            ToastUtils.showShortToast(string);
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    private void statusF() {
        checklistmeuntext.setText("保存");
        checkNewButton.setText("开始");
        checklistmeuntext.setVisibility(View.VISIBLE);
        checkNewButton.setBackgroundResource(R.color.gray);
        dkDragView.setVisibility(View.GONE);
        for (int i = 0; i < tVisibility.size(); i++) {
            tVisibility.get(i).setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < viewlist.size(); i++) {
            viewlist.get(i).setClickable(true);
            viewlist.get(i).setEnabled(true);
        }
    }

    private void statusT() {
        checklistmeuntext.setText("编辑");
        checkNewButton.setText("开始检查");
        checklistmeuntext.setVisibility(View.VISIBLE);
        checkImport.setVisibility(View.VISIBLE);
        checkNewButton.setBackgroundResource(R.color.colorAccent);
        dkDragView.setVisibility(View.VISIBLE);
        for (int i = 0; i < tVisibility.size(); i++) {
            tVisibility.get(i).setVisibility(View.GONE);
        }
        for (int i = 0; i < viewlist.size(); i++) {
            viewlist.get(i).setClickable(false);
            viewlist.get(i).setEnabled(false);
        }
    }

    private void submit() {
        checklistmeuntext.setText("编辑");
        checkNewButton.setText("提交");
        checkImport.setVisibility(View.VISIBLE);
        checkNewButton.setBackgroundResource(R.color.Orange);
        dkDragView.setVisibility(View.VISIBLE);
        for (int i = 0; i < tVisibility.size(); i++) {
            tVisibility.get(i).setVisibility(View.GONE);
        }
        for (int i = 0; i < viewlist.size(); i++) {
            viewlist.get(i).setClickable(false);
            viewlist.get(i).setEnabled(false);
        }
    }

    /**
     * 界面回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            //选择类别的返回数据
            categoryItem.setText(data.getStringExtra("data"));
            //类别Id，保存时上传类别的Id即可
            categoryId = data.getStringExtra("id");
        } else if (requestCode == 2 && resultCode == 3) {
            //导入wbs的返回数据
            //选择的节点Id,保存时上传
            nodeId = data.getStringExtra("id");
            //保存wbsname的值，用在保存时判断是否修改，
            wbsName.setText(data.getStringExtra("title"));
            wbsName.setVisibility(View.VISIBLE);
            //查询标段自带的类别
            getCategory();
        }
    }


    /**
     * 每次冲检查项返回时刷新当前界面数据
     */
    @Override
    public void updata() {
        getcheckitemList();
    }

    /**
     * popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            Utils.backgroundAlpha(1f, CheckNewAddsActivity.this);
        }
    }

    /**
     * 保存
     *
     * @param content
     * @param nodeId
     */
    public void Save(String content, String nodeId) {
        if (TextUtils.isEmpty(nodeId)) {
            nodeId = "";
        }
        Dates.getDialogs(CheckNewAddsActivity.this, "提交数据中...");
        OkGo.<String>post(Requests.CHECKMANGERSAVE)
//                //所属标段
                .params("name", checkNewTasktitle.getText().toString())
                .params("id", taskId)
                .params("orgId", orgId)
                .params("iwork", type)
                //检查部位Id
                .params("wbsMainId", nodeId)
                //手动输入的检查部位
                .params("partDetails", checkNewTemporarysite.getText().toString())
                //检查部位名称
                .params("wbsMainName", content)
                //检查标准类别Id
                .params("wbsTaskTypeId", categoryId)
                //检查日期
                .params("checkDate", datatime.getText().toString())
                //检测组织
                .params("checkOrgId", SPUtils.getString(mContext, "orgId", ""))
                //检查人
                .params("checkUser.id", SPUtils.getString(mContext, "id", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Dates.disDialog();
                        try {
                            JSONObject jsonObject1 = new JSONObject(s);
                            int ret = jsonObject1.getInt("ret");
                            if (ret == 0) {
                                ToastUtils.showShortToast("保存成功");
                                //更新列表界面
                                JSONObject json = jsonObject1.getJSONObject("data");
                                try {
                                    CheckTaskCallbackUtils.CallBackMethod();
                                } catch (Exception e) {
                                }
                                taskId = json.getString("id");
                                getcheckitemList();
                                statusT();
                            } else {
                                ToastUtils.showShortToast(jsonObject1.getString("msg"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        checklistmeun.setClickable(true);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        checklistmeun.setClickable(true);
                        Dates.disDialog();
                    }
                });
    }

    /**
     * 生成检查后的检查项列表
     */
    public void getcheckitemList() {
        checkUtils.getcheckitemlist(taskId, new NetworkCallback() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                mData.clear();
                mData.addAll((ArrayList<chekitemList>) map.get("list"));
                if (mData.size() > 0) {
                    adapter.getdate(mData);
                    checkFinish();
                }
            }

            @Override
            public void onerror(String s) {
                ToastUtils.showShortToast(s);
            }
        });
    }

    /**
     * 未提交的检查项获取数据
     */
    public void getdata() {
        Dates.getDialogs(CheckNewAddsActivity.this, "请求数据中...");
        post(Requests.CHECKGET_BY_ID)
                .params("Id", taskId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Dates.disDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                JSONObject json = jsonObject.getJSONObject("data");
                                //具体时间
                                datatime.setText(json.getString("checkDate"));
                                //检查标准类别
                                categoryItem.setText(json.getString("wbsTaskTypeName"));
                                //检查组织
                                checkNewOrgname.setText(json.getString("checkOrgName"));
                                try {
                                    nodeId = json.getString("nodeId");
                                } catch (JSONException e) {
                                    nodeId = "";
                                }
                                status = json.getInt("status");
                                //检查部位wbs
                                try {
                                    wbsName.setText(json.getString("wbsMainName"));
                                    wbsName.setVisibility(View.VISIBLE);
                                } catch (JSONException e) {
                                    wbsName.setText("");
                                }

                                //检查人
                                checkUsername.setText(json.getString("realname"));
                                //检查标题
                                String titikle = json.getString("name");
                                try {
                                    checkNewNumber.setText(json.getString("score"));
                                } catch (JSONException e) {
                                    checkNewNumber.setText("0");
                                }
                                checkNewTasktitle.setText(titikle);
                                //所属标段
                                checkNewWebtext.setText(json.getString("orgName"));
                                //检查部位
                                String partDetails = json.getString("partDetails");
                                if (partDetails.length() > 0) {
                                    checkNewTemporarysite.setText(partDetails);
                                    checkNewTemporarysite.setTextColor(Color.parseColor("#000000"));
                                }
                                try {
                                    categoryId = json.getString("WbsTaskTypeId");
                                } catch (Exception e) {
                                    categoryId = "";
                                }
                            } else {
                                ToastUtils.showShortToast(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
     * 根据标段选择类别
     */
    public void getCategory() {
        post(Requests.GET_TASK_TYPE_BY_WBS_ID)
                .params("wbsId", nodeId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                JSONObject json = jsonObject.getJSONObject("data");
                                categoryId = json.getString("id");
                                categoryItem.setText(json.getString("name"));
                            } else {
                                ToastUtils.showShortToast(jsonObject.getString("msg"));
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

    //提交
    public void senddata() {
        OkGo.get(Requests.SEND_DATA)
                .params("id", taskId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.getInt("ret") == 0) {
                                try {
                                    CheckTaskCallbackUtils.CallBackMethod();
                                } catch (Exception e) {
                                }
                                finish();
                            } else if (jsonObject.getInt("ret") == 5) {
                                BaseDialog.confirmmessagedialog(mContext,
                                        "确认签字失败",
                                        "您当前还未设置我的签名",
                                        null,
                                        "去设置签名", new Onclicklitener() {
                                            @Override
                                            public void confirm(String string) {
                                                startActivity(new Intent(mContext, SignatureViewActivity.class));
                                            }

                                            @Override
                                            public void cancel(String string) {

                                            }
                                        });
                            } else {
                                ToastUtils.showShortToast(jsonObject.getString("msg"));
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

    public void checkFinish() {
        OkGo.get(Requests.CHECK_FINISH)
                .params("id", taskId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                String lean = jsonObject1.getString("finish");
                                if ("2".equals(lean)) {
                                    submit();
                                    checkNewButton.setText("提交");
                                    checkNewButton.setVisibility(View.VISIBLE);
                                    checkNewButton.setBackgroundResource(R.color.Orange);
                                } else if ("3".equals(lean)) {
                                    checkNewButton.setText("确认并签名");
                                    success = "true";
                                    checkNewButton.setVisibility(View.VISIBLE);
                                    checkNewButton.setBackgroundResource(R.color.Orange);
                                } else if ("1".equals(lean)) {
                                    if (status == 1) {
                                        success = "true";
                                        checkNewButton.setVisibility(View.GONE);
                                        checklistmeuntext.setVisibility(View.GONE);
                                        checklistmeuntext.setText("");
                                    } else {
                                        success = null;
                                        checklistmeun.setVisibility(View.VISIBLE);
                                        checkNewButton.setVisibility(View.VISIBLE);
                                        checklistmeuntext.setVisibility(View.VISIBLE);
                                        statusT();
                                    }
                                }
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

    //时间选择器初始化
    public void dialog() {
        Date now = new Date();
        mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH)//展示模式
                .setWheelItemTextSize(15)//字体大小
                .setMinMillseconds(now.getTime() - (24 * 60 * 60 * 1000) * 2)//最小值时间
                .setCurrentMillseconds(now.getTime())//当前时间
                .setCyclic(false)
                .setWheelItemTextSelectorColor(getResources().getColor(com.example.baselibrary.R.color.colorAccent))//title栏颜色
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        datatime.setText(Dates.stampToDates(millseconds).substring(0,7));
                    }
                })//回调
                .build();
    }

}
