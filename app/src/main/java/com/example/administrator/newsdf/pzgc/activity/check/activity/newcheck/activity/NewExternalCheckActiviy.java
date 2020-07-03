package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.activity;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.adapter.NewExternalCheckAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.adapter.NewExternalCheckGridAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.CheckNewBean;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.CheckType;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.Enum;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.SafetyCheck;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.model.NewExternalCheckModel;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.utils.DrawableUtils;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.utils.ExternalModel;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.utils.GridLayoutItemDecoration;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.view.DKDragView;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.bean.bean;
import com.example.baselibrary.inface.Onclicklitener;
import com.example.baselibrary.utils.network.NetworkAdapter;
import com.example.baselibrary.utils.rx.LiveDataBus;
import com.example.baselibrary.view.BaseDialog;
import com.example.timepickter.TimePickerDialog;
import com.example.timepickter.data.Type;
import com.example.timepickter.listener.OnDateSetListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 说明：外业检查：新增检查项
 * 创建时间： 2020/6/23 0023 16:43
 *
 * @author winelx
 */
public class NewExternalCheckActiviy extends BaseActivity implements View.OnClickListener {
    private RecyclerView recyclerview;
    private RecyclerView drawableRecycler;
    private DKDragView dkDragView;
    private TextView ascriptionOrg, ascriptionBid, ascriptionUser, fTotalSocre, totalSocre;
    private TextView comTitle, comButton, commit, checkTypeContent, checkTimeContent, projectTypeContent;
    private EditText checkName;
    private LinearLayout checkType, checkTime, projectType;
    private DrawerLayout drawerLayout;
    private NewExternalCheckAdapter adapter;
    private NestedScrollView nestedScrollView;
    private NewExternalCheckGridAdapter gridAdapter;
    private Context mContext;
    private TimePickerDialog mDialogYearMonthDay;
    private String checktype, protype, checkid, level, status, orgid;
    private ExternalModel externalModel;
    private NewExternalCheckModel newExternalCheckModel;
    private Intent intent;
    private List<CheckNewBean.scorePane> scorePaneList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_new_externalcheck);
        mContext = this;
        intent = getIntent();
        scorePaneList = new ArrayList<>();
        externalModel = new ExternalModel();
        findViewById(R.id.toolbar_menu).setOnClickListener(this);
        findViewById(R.id.com_back).setOnClickListener(this);
        comTitle = findViewById(R.id.com_title);
        fTotalSocre = findViewById(R.id.fTotalSocre);
        totalSocre = findViewById(R.id.totalSocre);
        ascriptionOrg = findViewById(R.id.ascription_org);
        ascriptionBid = findViewById(R.id.ascription_bid);
        ascriptionUser = findViewById(R.id.ascription_user);
        checkTypeContent = findViewById(R.id.check_type_content);
        nestedScrollView = findViewById(R.id.scrollview);
        checkName = findViewById(R.id.check_name);
        commit = findViewById(R.id.commit);
        commit.setOnClickListener(this);
        checkTime = findViewById(R.id.check_time);
        checkTime.setOnClickListener(this);
        checkTimeContent = findViewById(R.id.check_time_content);
        checkType = findViewById(R.id.check_type);
        checkType.setOnClickListener(this);
        projectType = findViewById(R.id.project_type);
        projectType.setOnClickListener(this);
        projectTypeContent = findViewById(R.id.project_type_content);
        comButton = findViewById(R.id.com_button);
        comButton.setOnClickListener(this);
        drawerLayout = findViewById(R.id.drawer_layout);
        DrawableUtils.setDrawLayout(drawerLayout);
        //展示侧拉界面后，背景透明度（当前透明度为完全透明）
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        dkDragView = findViewById(R.id.suspension);
        //设置不允许超过的边界（左上右下）
        dkDragView.setBoundary(0, 130, 0, 220);
        dkDragView.setOnDragViewClickListener(new DKDragView.onDragViewClickListener() {
            @Override
            public void onClick() {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add("");
        }
        adapter = new NewExternalCheckAdapter(R.layout.adapter_new_externalcheck, list);
        recyclerview.setAdapter(adapter);
        drawableRecycler = findViewById(R.id.drawable_recycler);
        drawableRecycler.setLayoutManager(new GridLayoutManager(mContext, 5));
        gridAdapter = new NewExternalCheckGridAdapter(R.layout.check_new_grid_item, new ArrayList<>());
        drawableRecycler.addItemDecoration(new GridLayoutItemDecoration(this, R.drawable.item_divider));
        drawableRecycler.setAdapter(gridAdapter);
        gridAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                drawerLayout.closeDrawers();
                Intent intent1 = new Intent(mContext, ExternalCheckDetailActivity.class);
                intent1.putExtra("page", position);
                intent1.putExtra("checkid", checkid);
                intent1.putExtra("scoreid", scorePaneList.get(position).getId());
                intent1.putExtra("level", level);
                intent1.putExtra("status", status);
                startActivity(intent1);
            }
        });
        LiveDataBus.get().with("projecttype", CheckType.class).observe(this, new Observer<CheckType>() {
            @Override
            public void onChanged(@Nullable CheckType checkType) {
                projectTypeContent.setText(checkType.getName());
            }
        });
        timeselector();
        control();
    }

    /**
     * 说明：点击事件
     * 创建时间： 2020/7/3 0003 10:03
     *
     * @author winelx
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_menu:
                break;
            case R.id.commit:
                Intent intent1 = new Intent(mContext, ExternalCheckDetailActivity.class);
                intent1.putExtra("page", "0");
                intent1.putExtra("checkid", checkid);
                intent1.putExtra("scoreid", scorePaneList.get(0).getId());
                intent1.putExtra("level", level);
                intent1.putExtra("status", status);
                startActivity(intent1);
                break;
            case R.id.com_back:
                finish();
                break;
            case R.id.check_type:
                Intent intent = new Intent(mContext, ExternalCheckTypeActivity.class);
                startActivityForResult(intent, Enum.CHECK_TYPE);
                break;
            case R.id.check_time:
                mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.project_type:
                if (checktype != null) {
                    Intent projecttype = new Intent(mContext, ExternalProjectTypeActivity.class);
                    projecttype.putExtra("name", checktype);
                    startActivityForResult(projecttype, Enum.PRROJECT_TYPE);
                } else {
                    ToastUtils.showShortToast("请先选择检查类型");
                }
                break;
            case R.id.com_button:
                if ("编辑".equals(comButton.getText().toString())) {
                    comButton.setText("保存");
                    newExternalCheckModel.setEnabled(true);
                } else {
                    saveSafetyCheckByApp();
                }
            default:
                break;
        }
    }

    /**
     * 说明：回调数据
     * 创建时间： 2020/7/3 0003 10:03
     *
     * @author winelx
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == Enum.CHECK_TYPE) {
                checkTypeContent.setText(data.getStringExtra("name"));
                checktype = data.getStringExtra("id");
            } else if (resultCode == Enum.PRROJECT_TYPE) {
                projectTypeContent.setText(data.getStringExtra("name"));
                protype = data.getStringExtra("id");
            }
        }
    }

    /**
     * 说明：时间选择器初始化
     * 创建时间： 2020/7/1 0001 10:57
     *
     * @author winelx
     */
    public void timeselector() {
        Date now = new Date();
        mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextSize(15)
                .setCyclic(false)
//                .setMinMillseconds(now.getTime() - (24 * 60 * 60 * 1000) * 2)//最小时间
                .setCurrentMillseconds(now.getTime())
                .setWheelItemTextSelectorColor(getResources().getColor(com.example.baselibrary.R.color.colorAccent))
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        checkTimeContent.setText(Dates.stampToDates(millseconds));
                    }
                })//回调
                .build();
    }

    /**
     * 说明：判断是否新增还是查看
     * 创建时间： 2020/7/3 0003 9:34
     *
     * @author winelx
     */
    public void control() {
        status = intent.getStringExtra("status");
        newExternalCheckModel = new NewExternalCheckModel(commit, comButton, nestedScrollView,
                checkType, checkTime, projectType, checkName, status);
        newExternalCheckModel.setContext(this);
        String isNew = intent.getStringExtra("isNew");
        if ("编辑".equals(isNew)) {
            checkid = intent.getStringExtra("id");
            comTitle.setText("外业检查");
            getSafetyCheck();
        } else {
            comTitle.setText("新增检查");
            comButton.setText("保存");
            orgid = intent.getStringExtra("orgid");
            ascriptionOrg.setText(SPUtils.getString(mContext, "username", ""));
            ascriptionBid.setText(intent.getStringExtra("orgname"));
            ascriptionUser.setText(SPUtils.getString(mContext, "staffName", ""));
            newExternalCheckModel.submitButton(mContext, false);
        }
    }

    /**
     * 说明：权限控制
     * 创建时间： 2020/7/3 0003 10:02
     *
     * @author winelx
     */
    public void setPermission(CheckNewBean.permission bean) {
        //判断是否编辑权限
        newExternalCheckModel.setEditButton(bean.isEditButton());
        //如果已经有数据，界面默认不可编辑
        newExternalCheckModel.setEnabled(false);
        //判断检查项是否完成
        boolean lean = newExternalCheckModel.getCheckStatus(scorePaneList);
        if (lean) {
            //已经检查完成
            if ("0".equals(status)) {
                commit.setText("提交");
                commit.setBackgroundResource(R.color.orange);
                newExternalCheckModel.submitButton(mContext, bean.isSubmitButton());
            } else if ("6".equals(status)) {
                newExternalCheckModel.submitButton(mContext, false);
            } else {
                commit.setText("确认并签名");
                commit.setBackgroundResource(R.color.orange);
                newExternalCheckModel.confirmButton(mContext, bean.isConfirmButton());
            }
        } else {
            commit.setText("开始检查");
            commit.setBackgroundResource(R.color.colorAccent);
        }
    }

    /**
     * 说明：保存主要数据
     * 创建时间： 2020/7/3 0003 10:02
     *
     * @author winelx
     */
    public void saveSafetyCheckByApp() {
        Map<String, String> map = new HashMap<>();
        if (checkid != null) {
            map.put("id", checkid);
            //编辑层级
            map.put("level", level);
        }
        //组织id
        map.put("orgId", orgid);
        //检查名称
        if (!TextUtils.isEmpty(checkName.getText().toString())) {
            map.put("name", checkName.getText().toString());
        } else {
            ToastUtils.showShortToast("检查名称没填");
            return;
        }
        //组织名称
        map.put("orgName", intent.getStringExtra("orgname"));
        //检查类型
        if (checktype != null) {
            map.put("checkType", checktype);
        } else {
            ToastUtils.showShortToast("检查类型没选");
            return;
        }
        if (protype != null) {
            //工程类型
            map.put("wbsTaskTypeId", protype);
            //工程类型
            map.put("wbsTaskTypeName", projectTypeContent.getText().toString());
        } else {
            ToastUtils.showShortToast("工程类型没选");
            return;
        }
        //检查日期
        if (!TextUtils.isEmpty(checkTimeContent.getText().toString())) {
            map.put("checkDate", checkTimeContent.getText().toString());
        } else {
            ToastUtils.showShortToast("检查日期没选");
            return;
        }
        //检查人所在组织
        map.put("checkOrgName", SPUtils.getString(mContext, "username", ""));
        //当前登录人真实姓名
        map.put("checkPersonName", SPUtils.getString(mContext, "staffName", ""));
        BaseDialog.confirmdialog(mContext, "是否保存数据", "", new Onclicklitener() {
            @Override
            public void confirm(String string) {
                Dates.getDialogs((Activity) mContext, "提交数据中...");
                externalModel.saveSafetyCheckByApp(map, new NetworkAdapter() {
                    @Override
                    public void onsuccess(Object object) {
                        super.onsuccess();
                        Dates.disDialog();
                        CheckType bean = (CheckType) object;
                        if (checkid == null) {
                            LiveDataBus.get().with("ex_list").setValue("刷新");
                        }
                        checkid = bean.getId();
                        status = bean.getName();
                        getSafetyCheck();
                    }

                    @Override
                    public void onerror() {
                        super.onerror();
                        Dates.disDialog();
                    }
                });
            }

            @Override
            public void cancel(String string) {

            }
        });
    }

    /**
     * 说明：网络请求
     * 创建时间： 2020/7/2 0002 15:52
     *
     * @author winelx
     */
    public void getSafetyCheck() {
        Map<String, String> map = new HashMap<>(1);
        map.put("id", checkid);
        externalModel.getSafetyCheck(map, new NetworkAdapter() {
            @Override
            public void onsuccess(Object object) {
                super.onsuccess(object);
                Map<String, Object> map1 = (Map<String, Object>) object;
                CheckNewBean.permission permission = (CheckNewBean.permission) map1.get("permission");
                scorePaneList.addAll((List<CheckNewBean.scorePane>) map1.get("scorePane"));
                gridAdapter.setNewData(scorePaneList);
                setPermission(permission);
                setContent((SafetyCheck) map1.get("safetyCheck"));
            }
        });
    }

    /**
     * 说明：界面数据填写
     * 创建时间： 2020/7/3 0003 14:31
     *
     * @author winelx
     */
    public void setContent(SafetyCheck beans) {
        checkid = beans.getId();
        orgid = beans.getOrgId();
        checktype = beans.getCheckType();
        protype = beans.getWbsTaskTypeId();

        level = beans.getLevel();
        status = beans.getStatus();

        projectTypeContent.setText(beans.getWbsTaskTypeName());
        checkTimeContent.setText(beans.getCheckDate().substring(0, 10));

        fTotalSocre.setText(beans.getfTotalSocre());
        totalSocre.setText(beans.getTotalSocre());
        checkName.setText(beans.getName());
        //所属标段
        ascriptionOrg.setText(beans.getOrgName());
        //检查组织
        ascriptionBid.setText(beans.getCheckOrgName());
        //检查人
        ascriptionUser.setText(beans.getCheckPersonName());
        if ("8".equals(beans.getCheckType())) {
            checkTypeContent.setText("安全管理");
        } else {
            checkTypeContent.setText("质量管理");
        }
    }
}
