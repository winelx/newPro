package com.example.administrator.newsdf.pzgc.activity.check;

import android.app.Dialog;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.OrganizationEntity;
import com.example.administrator.newsdf.pzgc.utils.TreeUtlis;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.example.administrator.newsdf.treeView.Node;
import com.example.administrator.newsdf.treeView.TaskTreeListViewAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CheckNewAddActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView titleView;
    private String[] numbermonth, numberyear;
    private PopupWindow mPopupWindow;
    private NumberPicker yearPicker, monthPicker, dayPicker;
    private TextView datatime, checkWbspath;
    private static CheckNewAddActivity mContext;
    private int dateMonth, dayDate;
    private Date myDate = new Date();
    private LinearLayout check_new_data, checkImport, checkCategory;
    private DrawerLayout drawerLayout;

    //传递参数
    private String name, wbsid;

    //tree
    private List<OrganizationEntity> mTreeDatas;
    private ArrayList<OrganizationEntity> organizationList;
    private TaskTreeListViewAdapter<OrganizationEntity> mTreeAdapter;
    private TreeUtlis treeUtlis;
    private ListView checklist;
    private Dialog progressDialog;

    public static CheckNewAddActivity getInstance() {
        return mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_new_add);
        mContext = this;
        treeUtlis = new TreeUtlis();
        mTreeDatas = new ArrayList<>();
        organizationList = new ArrayList<>();
        //获取当前月份
        dateMonth = myDate.getMonth();
        //拿到月
        numbermonth = Utils.month;
        //拿到年
        numberyear = Utils.year;
        //导入的wbs路径
        checkWbspath = (TextView) findViewById(R.id.check_wbspath);
        checklist = (ListView) findViewById(R.id.checklist);
        //检查标准类别
        checkCategory = (LinearLayout) findViewById(R.id.Check_category);
        //抽屉控件
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //导入标段
        checkImport = (LinearLayout) findViewById(R.id.check_import);
        //时间选择器选择时间后显示
        titleView = (TextView) findViewById(R.id.titleView);
        //关闭边缘滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //侧滑栏关闭手势滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //展示侧拉界面后，背景透明度（当前透明度为完全透明）
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        //具体时间
        datatime = (TextView) findViewById(R.id.check_new_data_tx);
        //现在时间（弹出时间选择器）
        check_new_data = (LinearLayout) findViewById(R.id.check_new_data);
        check_new_data.setOnClickListener(this);
        checkImport.setOnClickListener(this);
        checkCategory.setOnClickListener(this);
        titleView.setText("新增检查");
        //添加list数据
        OrganizationEntity bean = new OrganizationEntity("724ebfdc08b04e5dbad91b4693b20bfa", "",
                "测试9标", "0", false,
                true, "3,5", "",
                "", "", "测试9标", "", true);
        organizationList.add(bean);
        treeUtlis.getOrganization(mContext, organizationList, mTreeAdapter, mTreeDatas, checklist);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_new_data:
                //打开时间选择器
                meunpop();
                break;
            case R.id.check_import:
                drawerLayout.openDrawer(GravityCompat.END);
                break;
            case R.id.Check_category:
                startActivity(new Intent(mContext, CheckTaskCategoryActivity.class));
                break;
            default:
                break;
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
        mPopupWindow.showAsDropDown(datatime);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());
        Utils.backgroundAlpha(0.5f, CheckNewAddActivity.this);
    }

    /**
     * \设置pop的点击事件
     */
    private View getPopupWindowContentView() {
        // 一个自定义的布局，作为显示的内容
        // 布局ID
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
                setyear(i1);
            }
        });
        //月份选择器。如果当前的月份是二月，
        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal,
                                      int newVal) {
                setMonth(newVal);
            }
        });

        return contentView;
    }

    /**
     * /设置选择月，控制二月天数
     */
    public void setMonth(int newVal) {
        String NewVal = numbermonth[newVal];
        String years = numberyear[yearPicker.getValue()];
        if (NewVal.equals("02")) {
            if (Utils.getyear().contains(years)) {
                //如果是闰年。二月有29天
                dayPicker.setDisplayedValues(null);
                dayPicker.setMaxValue(Utils.daytwos.length - 1);
                dayPicker.setDisplayedValues(Utils.daytwos);
                dayPicker.setMinValue(0);
            } else {
                //如果是平年。二月有28天
                dayPicker.setDisplayedValues(null);
                dayPicker.setMaxValue(Utils.daytwo.length - 1);
                dayPicker.setDisplayedValues(Utils.daytwo);
                dayPicker.setMinValue(0);
            }
        } else if (NewVal.equals("01") || NewVal.equals("03") || NewVal.equals("05") ||
                NewVal.equals("07") || NewVal.equals("08") || NewVal.equals("10") || NewVal.equals("12")) {
            dayPicker.setDisplayedValues(null);
            dayPicker.setMaxValue(Utils.day.length - 1);
            dayPicker.setDisplayedValues(Utils.day);
            dayPicker.setMinValue(0);
        } else if (NewVal.equals("04") || NewVal.equals("06") || NewVal.equals("09") || NewVal.equals("11")) {
            dayPicker.setDisplayedValues(null);
            dayPicker.setMaxValue(Utils.dayth.length - 1);
            dayPicker.setDisplayedValues(Utils.dayth);
            dayPicker.setMinValue(0);
        }
    }

    /**
     * /设置选择年，控制二月天数
     */
    public void setyear(int i1) {
        //月份
        String mont = Utils.month[monthPicker.getValue()];
        //年份
        String str = numberyear[i1];
        //如果选择中的月份是二月
        if (mont.equals("02")) {
            //判断是闰年还是平年
            if (Utils.getyear().contains(str)) {
                dayPicker.setDisplayedValues(null);
                dayPicker.setMaxValue(Utils.daytwos.length - 1);
                dayPicker.setDisplayedValues(Utils.daytwos);
                dayPicker.setMinValue(0);
            } else {
                dayPicker.setDisplayedValues(null);
                dayPicker.setMaxValue(Utils.daytwo.length - 1);
                dayPicker.setDisplayedValues(Utils.daytwo);
                dayPicker.setMinValue(0);
            }
        }
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

    public void switchAct(Node node) {
        checkWbspath.setText(node.getTitle());
        checkWbspath.setVisibility(View.VISIBLE);
    }

    public void Dialog() {
        progressDialog = new Dialog(CheckNewAddActivity.getInstance(), R.style.progress_dialog);
        progressDialog.setContentView(R.layout.waiting_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView text = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        text.setText("请求数据中...");
        progressDialog.show();

    }

    public void dissdialog() {
        progressDialog.dismiss();
    }
}
