package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.CheckNewAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.CheckUtils;
import com.example.administrator.newsdf.pzgc.bean.chekitemList;
import com.example.administrator.newsdf.pzgc.callback.CheckNewCallback;
import com.example.administrator.newsdf.pzgc.callback.CheckNewCallbackUtils;
import com.example.administrator.newsdf.pzgc.callback.CheckTaskCallbackUtils;
import com.example.baselibrary.view.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.DKDragView;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.joanzapata.iconify.widget.IconTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.newsdf.R.id.check_new_buttons;
import static com.lzy.okgo.OkGo.post;


/**
 * description:新增检查
 *
 * @author lx
 *         date: 2018/8/3 0006
 *         update: 2018/8/6 0006
 *         version:
 */
public class CheckNewAddActivity extends BaseActivity implements View.OnClickListener, CheckNewCallback {
    //控件
    private PopupWindow mPopupWindow;
    private NumberPicker yearPicker, monthPicker, dayPicker;
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
    private String[] numbermonth, numberyear;
    //参数
    private String name, orgId, categoryId = "", taskId = "", nodeId;
    private int dateMonth, dayDate;
    private Date myDate = new Date();
    private CheckNewAdapter adapter;
    private ArrayList<chekitemList> mData;
    private static CheckNewAddActivity mContext;
    private IconTextView icontextviewone, icontextviewtwo;
    private LinearLayout checklistmeun, check_new_dialog;
    private SmartRefreshLayout smallLabel;

    public static CheckNewAddActivity getInstance() {
        return mContext;
    }

    private CheckUtils checkUtils;
    ArrayList<View> viewlist = new ArrayList<>();
    ArrayList<View> tVisibility = new ArrayList<>();
    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_new_add);
        addActivity(this);
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
        check_new_dialog = (LinearLayout) findViewById(R.id.check_new_dialog);
        check_new_dialog.setVisibility(View.VISIBLE);
        smallLabel = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
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
        smallLabel.setEnableLoadmore(false);
        mData = new ArrayList<>();
        checkUtils = new CheckUtils();
        //拿到月
        numbermonth = Utils.month;
        //拿到年
        numberyear = Utils.year;
        mContext = this;
        //获取当前月份
        dateMonth = myDate.getMonth();
        //天
        dayDate = myDate.getDate() - 1;
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
                intent2.putExtra("number", position + 1);
                intent2.putExtra("position", position);
                intent2.putExtra("size", mData.size());
                intent2.putExtra("id", mData.get(position).getId());
                startActivity(intent2);

            }
        });
        smallLabel.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getcheckitemList();
                //传入false表示刷新失败
                refreshlayout.finishRefresh(800);
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
                    intent2.putExtra("position", 0);
                    intent2.putExtra("size", mData.size());
                    startActivity(intent2);
                } else if ("提交".equals(str)) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("提示");
                    builder.setMessage("是否提交数据");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            senddata();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
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
     * 选择时间弹出框
     */
    private void meunpop() {
        View contentView = getPopupWindowContentView();
        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        //设置显示隐藏动画
        mPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 默认在mButton2的左下角显示
        mPopupWindow.showAsDropDown(titleView);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());
        Utils.backgroundAlpha(0.5f, CheckNewAddActivity.this);
    }

    /**
     * \设置pop的点击事件
     */
    private View getPopupWindowContentView() {
        // 一个自定义的布局，作为显示的内容
        int layoutId = R.layout.popwind_daily;
        final View contentView = LayoutInflater.from(mContext).inflate(layoutId, null);
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.pop_determine:
                        //获取年
                        String yeardata = Utils.year[yearPicker.getValue()];
                        //获取月
                        int month = monthPicker.getValue();
                        String monthdata = Utils.month[month];
                        //获取天
                        int day = dayPicker.getValue();
                        String daydata;
                        if (monthdata.equals("02")) {
                            //是二月份
                            if (Utils.getyear().contains(yeardata)) {
                                daydata = Utils.daytwos[day];
                                //闰年
                            } else {
                                //平年
                                daydata = Utils.daytwo[day];
                            }
                        } else {
                            //不是二月份
                            if (monthdata.equals("01") || monthdata.equals("03") || monthdata.equals("05") || monthdata.equals("07") || monthdata.equals("08") || monthdata.equals("10") || monthdata.equals("012")) {
                                daydata = Utils.day[day];
                            } else {
                                daydata = Utils.dayth[day];
                            }

                        }
                        datatime.setText(yeardata + "-" + monthdata + "-" + daydata);
                        break;
                    case R.id.pop_dismiss:
                    default:
                        break;
                }
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        };


        //获取控件点击事件
        contentView.findViewById(R.id.pop_dismiss).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_determine).setOnClickListener(menuItemOnClickListener);
        //年的控件
        yearPicker = contentView.findViewById(R.id.years);
        //月
        monthPicker = contentView.findViewById(R.id.month);
        //每日
        dayPicker = contentView.findViewById(R.id.day);
        //初始化数据---年
        Utils.setPicker(yearPicker, Utils.year, Utils.titleyear());
        //初始化数据---月
        Utils.setPicker(monthPicker, Utils.month, dateMonth);
        //初始化数据---日
        String yeardata = Utils.year[yearPicker.getValue()];
        //如果当前月份是2月
        if ((dateMonth+1) == 2) {
            if (Utils.getyear().contains(yeardata)) {
                Utils.setPicker(dayPicker, Utils.daytwos, dayDate);
                //闰年
            } else {
                //平年
                Utils.setPicker(dayPicker, Utils.daytwo, dayDate);
            }
        } else {
            if (dateMonth == 0 || dateMonth == 2 || dateMonth == 4 || dateMonth == 6 || dateMonth == 7 || dateMonth == 9 || dateMonth == 11) {
                Utils.setPicker(dayPicker, Utils.day, dayDate);
            } else {
                Utils.setPicker(dayPicker, Utils.dayth, dayDate);
            }
        }
        //年份选择器。如果当前的月份是二月，
        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                checkUtils.setyear(monthPicker, dayPicker, i1, numberyear);
            }
        });
        //月份选择器。如果当前的月份是二月，
        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal,
                                      int newVal) {
                checkUtils.setMonth(yearPicker, monthPicker, dayPicker, newVal, numbermonth, numberyear);
            }
        });

        return contentView;
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
            Utils.backgroundAlpha(1f, CheckNewAddActivity.this);
        }
    }

    /**
     * 保存
     *
     * @param content
     * @param nodeId
     */
    public void Save(String content, String nodeId) {
        Dates.getDialogs(CheckNewAddActivity.this, "提交数据中...");
        OkGo.<String>post(Requests.CHECKMANGERSAVE)
//                //所属标段
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
        OkGo.<String>post(Requests.SIMPLE_DETAILS_LIST_BY_APP)
                .params("id", taskId)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        mData.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json = jsonArray.getJSONObject(i);
                                        String id = json.getString("id");
                                        String score = json.getString("score");
                                        String sequence = json.getString("sequence");
                                        String standardScore;
                                        try {
                                            standardScore = json.getString("standardScore");
                                        } catch (JSONException e) {
                                            standardScore = "";
                                        }
                                        boolean noSuch = json.getBoolean("noSuch");
                                        boolean generate;
                                        try {
                                            generate = json.getBoolean("generate");
                                        } catch (JSONException e) {
                                            generate = false;
                                        }
                                        boolean penalty = json.getBoolean("penalty");
                                        boolean gray = json.getBoolean("gray");
                                        int number = i + 1;
                                        mData.add(new chekitemList(id, score, sequence, standardScore, number + "", noSuch, penalty, generate, gray));
                                    }
                                }
                            }
                            if (mData.size() > 0) {
                                adapter.getdate(mData);
                                checkFinish();
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
                                    checkNewNumber.setText(json.getString("score"));
                                } catch (JSONException e) {
                                    checkNewNumber.setText("");
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
                                categoryId = json.getString("WbsTaskTypeId");
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
                                Boolean lean = jsonObject1.getBoolean("finish");
                                if (lean) {
                                    checkNewButton.setText("提交");
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
