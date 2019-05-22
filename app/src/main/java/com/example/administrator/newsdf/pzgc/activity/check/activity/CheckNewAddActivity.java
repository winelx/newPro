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
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.CheckNewAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.CheckUtils;
import com.example.administrator.newsdf.pzgc.bean.chekitemList;
import com.example.administrator.newsdf.pzgc.callback.CheckNewCallback;
import com.example.administrator.newsdf.pzgc.callback.CheckNewCallbackUtils;
import com.example.administrator.newsdf.pzgc.callback.CheckTaskCallbackUtils;

import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.DialogUtils;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.view.DKDragView;
import com.example.baselibrary.inface.NetworkCallback;
import com.example.baselibrary.inface.Onclicklitener;
import com.example.baselibrary.ui.activity.SignatureViewActivity;
import com.example.baselibrary.utils.Requests;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.view.BaseDialog;
import com.joanzapata.iconify.widget.IconTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.newsdf.R.id.check_new_buttons;
import static com.lzy.okgo.OkGo.post;


/**
 * description:新增检查
 *
 * @author lx
 * date: 2018/8/3 0006
 * update: 2018/8/6 0006
 * version:
 */
public class CheckNewAddActivity extends BaseActivity implements View.OnClickListener, CheckNewCallback {
    //控件
    private TextView datatime, checkNewNumber, categoryItem, checklistmeuntext, titleView,
            checkNewWebtext, checkUsername, checkNewOrgname, wbsName;
    private LinearLayout checkNewData;
    private LinearLayout checkImport, check_content;
    private LinearLayout checkCategory;
    private DrawerLayout drawerLayout;
    private GridView checklist, checklists;
    private EditText checkNewTasktitle, checkNewTemporarysite;
    private Button checkNewButton;
    private DKDragView dkDragView;
    //参数
    private String name, orgId, categoryId = "", taskId = "", nodeId;
    private CheckNewAdapter adapter, adapters;
    private ArrayList<chekitemList> mData, Preposition, Routine;
    private static CheckNewAddActivity mContext;
    private IconTextView icontextviewone, icontextviewtwo;
    private LinearLayout checklistmeun, checkNewDialog;


    public static CheckNewAddActivity getInstance() {
        return mContext;
    }

    private CheckUtils checkUtils;
    private DialogUtils dialogUtils;
    ArrayList<View> viewlist = new ArrayList<>();
    ArrayList<View> tVisibility = new ArrayList<>();


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
        try {
            //当前检查任务的id
            taskId = intent.getStringExtra("taskId");
        } catch (NullPointerException e) {
            e.printStackTrace();
            taskId = "0";
        }
        findbyid();
        initData();
        if (taskId == null) {
            checkNewOrgname.setText(SPUtils.getString(mContext, "username", ""));
            checkUsername.setText(SPUtils.getString(mContext, "staffName", ""));
            datatime.setText(Dates.getDay());
            checkNewWebtext.setText(name);
            statusF();
        } else {
            statusT();
            getcheckitemList();
            getdata();

        }
    }

    private void findbyid() {
        checkNewDialog = (LinearLayout) findViewById(R.id.check_new_dialog);
        checkNewDialog.setVisibility(View.VISIBLE);
        //前置检查标题
        check_content = findViewById(R.id.check_content);
        check_content.setVisibility(View.VISIBLE);
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
        //检查标段
        checkNewWebtext = (TextView) findViewById(R.id.check_new_webtext);
        //检查组织
        checkNewOrgname = (TextView) findViewById(R.id.check_new_orgname);
        //检查按钮
        checkNewButton = (Button) findViewById(check_new_buttons);
        checkNewButton.setOnClickListener(this);
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
        checklists = (GridView) findViewById(R.id.checklist1);
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
        Preposition = new ArrayList<>();
        Routine = new ArrayList<>();
        checkUtils = new CheckUtils();
        dialogUtils = new DialogUtils();
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
        //常规
        adapter = new CheckNewAdapter(mContext, Routine);
        checklist.setAdapter(adapter);
        //前置
        adapters = new CheckNewAdapter(mContext, Preposition);
        checklists.setAdapter(adapters);
        //常规
        checklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent(mContext, CheckitemActivity.class);
                int pos = Routine.get(position).getPos();
                intent2.putExtra("taskId", taskId);
                intent2.putExtra("orgId", orgId);
                intent2.putExtra("number", pos );
                intent2.putExtra("position", pos);
                intent2.putExtra("size", mData.size());
                intent2.putExtra("id", mData.get(position).getId());
                startActivity(intent2);

            }
        });
        //前置项
        checklists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent(mContext, CheckitemActivity.class);
                int pos = Preposition.get(position).getPos();
                intent2.putExtra("taskId", taskId);
                intent2.putExtra("orgId", orgId);
                intent2.putExtra("number", pos);
                intent2.putExtra("position", pos);
                intent2.putExtra("size", mData.size());
                intent2.putExtra("id", mData.get(position).getId());
                startActivity(intent2);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_new_data:
                //打开时间选择器
                meunpop();
                break;
            case R.id.check_import:
                Intent intent1 = new Intent(CheckNewAddActivity.this, CheckTreeActivity.class);
                intent1.putExtra("orgId", orgId);
                intent1.putExtra("name", name);
                startActivityForResult(intent1, 2);
                break;
            case R.id.Check_category:
                Intent intent = new Intent(mContext, CheckTaskCategoryActivity.class);
                intent.putExtra("wbsId", orgId);
                intent.putExtra("type", "1");
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
                        if (content.length() > 0 || wbspath.length() > 0) {
                            String title = checkNewTasktitle.getText().toString();
                            if (title.length() > 0) {
                                checklistmeun.setClickable(false);
                                //如果存在一个或者都存在，就调用接口
                                Save(wbspath, nodeId);
                            } else {
                                ToastUtils.showLongToast("检查计划名称不能为空");
                            }
                        } else {
                            ToastUtils.showLongToast("检查部位不能为空");
                        }
                    } else {
                        ToastUtils.showLongToast("类别不能为空");
                    }
                } else {
                    statusF();
                }
                break;
            case R.id.checklistback:
                finish();
                break;
            case check_new_buttons:
                String str = checkNewButton.getText().toString();
                if ("开始检查".equals(str)) {
                    Intent intent2 = new Intent(mContext, CheckitemActivity.class);
                    intent2.putExtra("taskId", taskId);
                    intent2.putExtra("orgId", orgId);
                    intent2.putExtra("number", 1);
                    intent2.putExtra("position", 1);
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
        checkNewButton.setVisibility(View.VISIBLE);
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

    /**
     * 界面回调
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
     * 选择时间弹出框
     */
    private void meunpop() {
        dialogUtils.selectiontime(mContext, new DialogUtils.OnClickListener() {
            @Override
            public void onsuccess(String str) {
                datatime.setText(str);
            }
        });
    }


    /**
     * 每次冲检查项返回时刷新当前界面数据
     */
    @Override
    public void updata() {
        getcheckitemList();
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
        Dates.getDialogs(CheckNewAddActivity.this, "提交数据中...");
        OkGo.<String>post(Requests.CHECKMANGERSAVE)
                //所属标段
                .params("name", checkNewTasktitle.getText().toString())
                .params("id", taskId)
                .params("orgId", orgId)
                .params("iwork", "1")
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
                                //更新列表界面
                                JSONObject json = jsonObject1.getJSONObject("data");
                                CheckTaskCallbackUtils.CallBackMethod();
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
                Preposition.clear();
                Routine.clear();
                mData.addAll((ArrayList<chekitemList>) map.get("list"));
                if (mData.size() > 0) {
                    for (int i = 0; i < mData.size(); i++) {
                        if (mData.get(i).getS_type().equals("3")) {
                            Preposition.add(mData.get(i));
                        } else {
                            Routine.add(mData.get(i));
                        }
                    }
                    //常规
                    adapter.getdate(Routine);
                    //前置
                    if (Preposition.size() != 0) {
                        adapters.getdate(Preposition);
                        check_content.setVisibility(View.VISIBLE);
                    } else {
                        check_content.setVisibility(View.GONE);
                    }
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
        Dates.getDialogs(CheckNewAddActivity.this, "请求数据中...");
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
                                    if (TextUtils.isEmpty(json.getString("score"))) {
                                        checkNewNumber.setText("0");
                                    } else {
                                        checkNewNumber.setText(json.getString("score"));
                                    }
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
                                checkNewButton.setVisibility(View.VISIBLE);
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
                                CheckTaskCallbackUtils.CallBackMethod();
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
                                    checkNewButton.setText("提交");
                                    checkNewButton.setVisibility(View.VISIBLE);
                                    checkNewButton.setBackgroundResource(R.color.Orange);
                                } else if ("3".equals(lean)) {
                                    checkNewButton.setText("确认并签名");
                                    checkNewButton.setVisibility(View.VISIBLE);
                                    checkNewButton.setBackgroundResource(R.color.Orange);
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

}
