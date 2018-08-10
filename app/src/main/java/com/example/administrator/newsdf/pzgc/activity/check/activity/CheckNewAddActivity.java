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
import com.example.administrator.newsdf.pzgc.utils.DKDragView;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.Date;

import static com.example.administrator.newsdf.R.id.check_new_button;
import static com.example.administrator.newsdf.R.id.checklistmeun;


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
    private TextView datatime, checkWbspath, categoryItem, checklistmeuntext, titleView, checkNewWebtext;
    private LinearLayout check_new_data, checkImport, checkCategory, checkNewDialog;
    private DrawerLayout drawerLayout;
    private GridView checklist;
    private EditText checkNewNumber, checkNewTasktitle, checkNewTemporarysite;
    private Button checkNewButton;
    private DKDragView dkDragView;
    private String[] numbermonth, numberyear;
    //参数
    private String name, orgId, status = "";
    private int dateMonth, dayDate;
    private Date myDate = new Date();
    private CheckNewAdapter adapter;
    private ArrayList<String> mData;
    private static CheckNewAddActivity mContext;
    private IconTextView IconTextViewone, IconTextViewtwo;

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
            status = intent.getStringExtra("status");
        } catch (NullPointerException e) {
            e.printStackTrace();
            status = "-1";
            name = "";
            orgId = "";
        }
        findbyid();
        initData();
    }

    private void findbyid() {
        IconTextViewone = (IconTextView) findViewById(R.id.IconTextViewone);
        IconTextViewtwo = (IconTextView) findViewById(R.id.IconTextViewtwo);
        tVisibility.add(IconTextViewone);
        tVisibility.add(IconTextViewtwo);

        checkNewDialog = (LinearLayout) findViewById(R.id.check_new_dialog);

        checkNewWebtext = (TextView) findViewById(R.id.check_new_webtext);

        checkNewButton = (Button) findViewById(check_new_button);
        //分数
        checkNewNumber = (EditText) findViewById(R.id.check_new_number);
        //标题   111
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
        checkWbspath = (TextView) findViewById(R.id.check_wbspath);
        //wbs Tree
        checklist = (GridView) findViewById(R.id.checklist);
        //检查标准类别
        checkCategory = (LinearLayout) findViewById(R.id.Check_category);
        viewlist.add(checkCategory);
        //抽屉控件
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //导入标段   1111
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
        for (int i = 0; i < 10; i++) {
            mData.add(i + 1 + "");
        }
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
        findViewById(checklistmeun).setOnClickListener(this);
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
                ToastUtils.showLongToast(position + "");
                startActivity(new Intent(mContext, CheckitemActivity.class));
            }
        });

        if ("1".equals(status)) {
            statusT();
        } else if ("2".equals(status)) {
            statusF();
        }else {
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
                startActivityForResult(intent, 1);
                break;
            case checklistmeun:
                String string = checklistmeuntext.getText().toString();
                if (string.equals("保存")) {
                    statusT();
                } else {
                    statusF();
                }
                break;
            case R.id.checklistback:
                finish();
                break;
            case check_new_button:
                String str = checkNewButton.getText().toString();
                if ("开始检查".equals(str)) {
                    startActivity(new Intent(mContext, CheckitemActivity.class));
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
        for (int i = 0; i <tVisibility.size() ; i++) {
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
        for (int i = 0; i <tVisibility.size() ; i++) {
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
            String str = data.getStringExtra("data");
            categoryItem.setText(str);
        } else if (requestCode == 2 && resultCode == 3) {
            checkWbspath.setText(data.getStringExtra("title"));
            checkWbspath.setVisibility(View.VISIBLE);
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





}
