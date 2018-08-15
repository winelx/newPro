package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.CheckNewAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.CheckUtils;
import com.example.administrator.newsdf.pzgc.bean.chekitemList;
import com.example.administrator.newsdf.pzgc.callback.TaskCallbackUtils;
import com.example.administrator.newsdf.pzgc.utils.DKDragView;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.joanzapata.iconify.widget.IconTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.lzy.okgo.OkGo.post;


/**
 * description:新增检查
 *
 * @author lx
 *         date: 2018/8/3 0006
 *         update: 2018/8/6 0006
 *         version:
 */
public class CheckNewAddActivity extends AppCompatActivity implements View.OnClickListener {
    //控件
    private PopupWindow mPopupWindow;
    private NumberPicker yearPicker, monthPicker, dayPicker;
    private TextView datatime, categoryItem, checklistmeuntext, titleView,
            checkNewWebtext, checkUsername, checkNewOrgname, wbsName;
    private LinearLayout check_new_data, checkImport, checkCategory, checkNewDialog, checkNewAddNumber;
    private DrawerLayout drawerLayout;
    private GridView checklist;
    private EditText checkNewNumber, checkNewTasktitle, checkNewTemporarysite;
    private Button checkNewButton;
    private DKDragView dkDragView;
    private String[] numbermonth, numberyear;
    //参数
    private String name, orgId, status = "", Id, categoryId = "", taskId;
    private int dateMonth, dayDate;
    private Date myDate = new Date();
    private CheckNewAdapter adapter;
    private ArrayList<chekitemList> mData;
    private static CheckNewAddActivity mContext;
    private IconTextView IconTextViewone, IconTextViewtwo;
    private LinearLayout checklistmeun;

    public static CheckNewAddActivity getInstance() {
        return mContext;
    }

    private CheckUtils checkUtils;
    ArrayList<View> viewlist = new ArrayList<>();
    ArrayList<View> tVisibility = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_new_add);
        Intent intent = getIntent();
        try {
            //wbs名称
            orgId = intent.getStringExtra("orgId");
            name = intent.getStringExtra("name");
            taskId = intent.getStringExtra("taskId");
        } catch (NullPointerException e) {
            e.printStackTrace();
            status = "-1";
            name = "";
            orgId = "";
            taskId = "";
        }
        findbyid();
        initData();
    }

    private void findbyid() {
        //分数
        checkNewAddNumber = (LinearLayout) findViewById(R.id.check_new_add_number);
        //wbs路径
        wbsName = (TextView) findViewById(R.id.check_wbspath);
        //指示箭头 类别和时间
        IconTextViewone = (IconTextView) findViewById(R.id.IconTextViewone);
        IconTextViewtwo = (IconTextView) findViewById(R.id.IconTextViewtwo);
        //添加入集合，根据操作进行隐藏
        tVisibility.add(IconTextViewone);
        tVisibility.add(IconTextViewtwo);
        //检查人
        checkUsername = (TextView) findViewById(R.id.check_username);
        //检查名称
        checkNewDialog = (LinearLayout) findViewById(R.id.check_new_dialog);
        //检查标段
        checkNewWebtext = (TextView) findViewById(R.id.check_new_webtext);
        //检查组织
        checkNewOrgname = (TextView) findViewById(R.id.check_new_orgname);
        //检查按钮
        checkNewButton = (Button) findViewById(R.id.check_new_buttons);
        checkNewButton.setOnClickListener(this);
        //分数
        checkNewNumber = (EditText) findViewById(R.id.check_new_number);
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
        check_new_data = (LinearLayout) findViewById(R.id.check_new_data);
        viewlist.add(check_new_data);
        //拖动控件
        dkDragView = (DKDragView) findViewById(R.id.float_suspension);
    }

    private void initData() {
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
        checkNewWebtext.setText(name);
        //显示meun控件
        checklistmeuntext.setVisibility(View.VISIBLE);
        //关闭边缘滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //侧滑栏关闭手势滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //展示侧拉界面后，背景透明度（当前透明度为完全透明）
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        check_new_data.setOnClickListener(this);
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
        checkNewOrgname.setText(SPUtils.getString(mContext, "username", ""));
        checkUsername.setText(SPUtils.getString(mContext, "staffName", ""));
        adapter = new CheckNewAdapter(mContext, mData);
        checklist.setAdapter(adapter);
        checklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext, CheckitemActivity.class));
            }
        });
        if ("1".equals(status)) {
            checklistmeun.setVisibility(View.GONE);
            statusT();
        } else if ("2".equals(status)) {
            statusF();
        } else {
            statusF();
        }
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
                            //如果存在一个或者都存在，就调用接口
                            setdate(wbspath, Id);
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
            case R.id.check_new_buttons:
                String str = checkNewButton.getText().toString();
                if ("开始检查".equals(str)) {
                    Intent intent2 = new Intent(mContext, CheckitemActivity.class);
                    intent2.putExtra("id", taskId);
                    intent2.putExtra("number", 0);
                    startActivity(intent2);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            //选择类别的返回数据
            categoryItem.setText(data.getStringExtra("data"));
            //类别Id，保存时上传Id即可
            categoryId = data.getStringExtra("id");
        } else if (requestCode == 2 && resultCode == 3) {
            //导入wbs的返回数据
            //选择的节点Id
            Id = data.getStringExtra("id");
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
        mPopupWindow.showAsDropDown(checkNewDialog);
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
                        titleView.setText(yeardata + "-" + monthdata + "-" + daydata);
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
        if (dateMonth == 2) {
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
     * popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            Utils.backgroundAlpha(1f, CheckNewAddActivity.this);
        }
    }

    public void setdate(String content, String nodeId) {
        Map<String, String> map = new HashMap<>();
        map.put("name", checkNewTasktitle.getText().toString());


        OkGo.<String>post(Requests.CHECKMANGERSAVE)
//                //所属标段
                .params("", taskId)
                .params("orgId", orgId)
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
                        try {
                            JSONObject jsonObject1 = new JSONObject(s);
                            int ret = jsonObject1.getInt("ret");
                            if (ret == 0) {
                                //更新列表界面
                                taskId = jsonObject1.getString("data");
                                TaskCallbackUtils.CallBackMethod();

                                statusT();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        try {
                            String str = response.body().string();
                            ToastUtils.showLongToast(str);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                    }
                });
    }

    public void getCategory() {
        post(Requests.GET_TASK_TYPE_BY_WBS_ID)
                .params("wbsId", Id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            ToastUtils.showShortToast(jsonObject.getString("msg"));
                            if (ret == 0) {
                                JSONObject json = jsonObject.getJSONObject("data");
                                categoryId = json.getString("id");
                                categoryItem.setText(json.getString("name"));
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
