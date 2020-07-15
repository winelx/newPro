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
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.ProcesshiscordBean;
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
    private TextView water, repulse;
    private TextView ascriptionOrg, ascriptionBid, ascriptionUser, fTotalSocre, totalSocre;
    private TextView comTitle, comButton, commit, checkTypeContent, checkTimeContent, projectTypeContent;
    private EditText checkName;
    private LinearLayout checkType, checkTime, projectType, poroject_score;
    private DrawerLayout drawerLayout;
    private NewExternalCheckAdapter adapter;
    private NestedScrollView nestedScrollView;
    private NewExternalCheckGridAdapter gridAdapter;
    private Context mContext;
    private TimePickerDialog mDialogYearMonthDay;
    private String checktype, protype, checkid, level, status, orgid, checkLevel;
    private ExternalModel externalModel;
    private NewExternalCheckModel newExternalCheckModel;
    private Intent intent;
    private List<CheckNewBean.scorePane> scorePaneList;
    private boolean edStatus = false;

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
        poroject_score = findViewById(R.id.poroject_score);
        fTotalSocre = findViewById(R.id.fTotalSocre);
        totalSocre = findViewById(R.id.totalSocre);
        repulse = findViewById(R.id.repulse);
        repulse.setOnClickListener(this);
        ascriptionOrg = findViewById(R.id.ascription_org);
        ascriptionBid = findViewById(R.id.ascription_bid);
        ascriptionUser = findViewById(R.id.ascription_user);
        checkTypeContent = findViewById(R.id.check_type_content);
        nestedScrollView = findViewById(R.id.scrollview);
        checkName = findViewById(R.id.check_name);
        water = findViewById(R.id.water);
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
        adapter = new NewExternalCheckAdapter(R.layout.adapter_new_externalcheck, new ArrayList<>());
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
                intent1.putExtra("checkLevel", checkLevel);
                intent1.putExtra("orgid", orgid);
                intent1.putExtra("status", status);
                intent1.putExtra("isLeveOption", scorePaneList.get(position).isLeveOption());
                //是否又编辑权限
                intent1.putExtra("edStatus", edStatus);
                startActivity(intent1);
            }
        });
        LiveDataBus.get().with("projecttype", CheckType.class).observe(this, new Observer<CheckType>() {
            @Override
            public void onChanged(@Nullable CheckType checkType) {
                projectTypeContent.setText(checkType.getName());
            }
        });
        LiveDataBus.get().with("ex_grid", Object.class).observe(this, new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object o) {
                getSafetyCheck();
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
                if ("开始检查".equals(commit.getText().toString())) {
                    Intent intent1 = new Intent(mContext, ExternalCheckDetailActivity.class);
                    intent1.putExtra("page", "0");
                    intent1.putExtra("checkid", checkid);
                    intent1.putExtra("scoreid", scorePaneList.get(0).getId());
                    intent1.putExtra("level", level);
                    intent1.putExtra("checkLevel", checkLevel);
                    intent1.putExtra("orgid", orgid);
                    intent1.putExtra("status", status);
                    //是否又编辑权限
                    intent1.putExtra("edStatus", edStatus);
                    startActivity(intent1);
                } else if ("提交".equals(commit.getText().toString()) || "确认并签名".equals(commit.getText().toString())) {
                    BaseDialog.confirmdialog(mContext, "是否提交数据", "", new Onclicklitener() {
                        @Override
                        public void confirm(String string) {
                            submitdatabyapp();
                        }

                        @Override
                        public void cancel(String string) {

                        }
                    });
                }
                break;
            case R.id.repulse:
                BaseDialog.confirmdialog(mContext, "是否打回检查", "", new Onclicklitener() {
                    @Override
                    public void confirm(String string) {
                        returnsafetycheckbyapp();
                    }

                    @Override
                    public void cancel(String string) {

                    }
                });
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
                    commit.setVisibility(View.INVISIBLE);
                    dkDragView.setVisibility(View.INVISIBLE);
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
                if (!checkTypeContent.getText().toString().equals(data.getStringExtra("name"))) {
                    checkTypeContent.setText(data.getStringExtra("name"));
                    checktype = data.getStringExtra("id");
                    projectTypeContent.setText("");
                    protype = "";
                }
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
        String isNew = intent.getStringExtra("isNew");
        if ("编辑".equals(isNew)) {
            checkid = intent.getStringExtra("id");
            comTitle.setText("外业检查");
            comButton.setVisibility(View.VISIBLE);
            getSafetyCheck();
        } else {
            newExternalCheckModel = new NewExternalCheckModel(commit, comButton, nestedScrollView,
                    checkType, checkTime, projectType, checkName, status);
            comTitle.setText("新增检查");
            comButton.setText("保存");
            dkDragView.setVisibility(View.INVISIBLE);
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
    public void setPermission(CheckNewBean.permission bean, SafetyCheck beans) {
        status = beans.getStatus();
        if (newExternalCheckModel == null) {
            newExternalCheckModel = new NewExternalCheckModel(commit, comButton, nestedScrollView,
                    checkType, checkTime, projectType, checkName, status);
        }
        //判断是否编辑权限
        edStatus = newExternalCheckModel.setEditButton(bean.isEditButton(), beans.getLevel(), beans.getCheckLevel());
        if (!level.equals("1")) {
            poroject_score.setVisibility(View.GONE);
        }
        if (edStatus) {
            comButton.setVisibility(View.VISIBLE);
        } else {
            comButton.setVisibility(View.GONE);
        }
        //如果已经有数据，界面默认不可编辑
        newExternalCheckModel.setEnabled(false);
        //判断检查项是否完成
        boolean lean = newExternalCheckModel.getCheckStatus(scorePaneList, checkLevel);
        //判断是有打回权限
        if (!level.equals(checkLevel)) {
            if (bean.isReturnButton()) {
                repulse.setVisibility(View.VISIBLE);
            } else {
                repulse.setVisibility(View.GONE);
            }
        } else {
            repulse.setVisibility(View.GONE);
        }
        // {0：保存；2：待分公司核查；3：待集团核查；4：待分公司确认；5：待标段确认；6：已确认}
        if ("0".equals(status) || "2".equals(status) || "3".equals(status)) {
            if (lean) {
                commit.setText("提交");
                commit.setBackgroundResource(R.color.orange);
                //判断是否有提交权限
                newExternalCheckModel.submitButton(mContext, bean.isSubmitButton());
            } else {
                commit.setText("开始检查");
                commit.setVisibility(View.VISIBLE);
                commit.setBackgroundResource(R.color.colorAccent);
                newExternalCheckModel.submitButton(mContext, bean.isSubmitButton());
            }
        } else if ("4".equals(status) || "5".equals(status)) {
            commit.setText("确认并签名");
            commit.setVisibility(View.VISIBLE);
            commit.setBackgroundResource(R.color.orange);
            repulse.setVisibility(View.GONE);
            newExternalCheckModel.confirmButton(mContext, bean.isConfirmButton());
        } else if ("6".equals(status)) {
            //已确认
            newExternalCheckModel.submitButton(mContext, false);
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
        map.put("orgName", ascriptionBid.getText().toString());
        //检查类型
        if (!TextUtils.isEmpty(checkTypeContent.getText().toString())) {
            map.put("checkType", checktype);
        } else {
            ToastUtils.showShortToast("检查类型没选");
            return;
        }
        if (!TextUtils.isEmpty(projectTypeContent.getText().toString())) {
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
                        commit.setVisibility(View.VISIBLE);
                        dkDragView.setVisibility(View.VISIBLE);
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
                scorePaneList.clear();
                scorePaneList.addAll((List<CheckNewBean.scorePane>) map1.get("scorePane"));
                gridAdapter.setNewData(scorePaneList);
                setContent((SafetyCheck) map1.get("safetyCheck"));
                setPermission(permission, (SafetyCheck) map1.get("safetyCheck"));
                getprocesshiscord();
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
        //检查类型
        checktype = beans.getCheckType();
        //工程类型
        protype = beans.getWbsTaskTypeId();
        //创建层级
        level = beans.getLevel();
        //当前检查层级
        checkLevel = beans.getCheckLevel();
        //状态
        status = beans.getStatus();
        //工程名称
        projectTypeContent.setText(beans.getWbsTaskTypeName());
        //检查事件
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

    /**
     * 说明：外业检查流程记录
     * 创建时间： 2020/7/7 0007 13:43
     *
     * @author winelx
     */
    public void getprocesshiscord() {
        externalModel.getprocesshiscord(checkid, new NetworkAdapter() {
            @Override
            public void onsuccess(Object object) {
                super.onsuccess(object);
                List<ProcesshiscordBean> list = (List<ProcesshiscordBean>) object;
                if (list != null) {
                    if (list.size() > 0) {
                        water.setVisibility(View.VISIBLE);
                        recyclerview.setVisibility(View.VISIBLE);
                    } else {
                        water.setVisibility(View.INVISIBLE);
                        recyclerview.setVisibility(View.INVISIBLE);
                    }
                }
                adapter.setNewData(list);
            }

            @Override
            public void onerror(String string) {
                super.onerror(string);
            }
        });
    }


    /**
     * 说明：外业检查 提交、确认方法
     * 创建时间： 2020/7/7 0007 16:54
     *
     * @author winelx
     */
    public void submitdatabyapp() {
        Map<String, String> map = new HashMap<>();
        map.put("checkLevel", checkLevel);
        map.put("safetyCheckId", checkid);
        externalModel.submitdatabyapp(map, new NetworkAdapter() {
            @Override
            public void onsuccess() {
                LiveDataBus.get().with("ex_list").setValue("刷新");
                finish();
            }
        });
    }

    /**
     * 说明：打回数据
     * *创建时间： 2020/7/13 0013 9:59
     *
     * @author winelx
     */
    public void returnsafetycheckbyapp() {
        Map<String, String> map = new HashMap<>();
        map.put("id", checkid);
        map.put("checkLevel", checkLevel);
        Dates.getDialogs(this, "正在打回数据");
        externalModel.returnsafetycheckbyapp(map, new NetworkAdapter() {
            @Override
            public void onsuccess() {
                super.onsuccess();
                Dates.disDialog();
                ToastUtils.showShortToastCenter("打回成功");
                finish();
                LiveDataBus.get().with("ex_list").setValue("刷新");
            }

            @Override
            public void onerror(String string) {
                super.onerror(string);
                Dates.disDialog();
            }
        });
    }
}
