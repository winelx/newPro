package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.activity;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.adapter.NewExternalCheckAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.adapter.NewExternalCheckGridAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.CheckType;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.Enum;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.utils.DrawableUtils;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.utils.GridLayoutItemDecoration;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.view.DKDragView;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.utils.rx.LiveDataBus;
import com.example.timepickter.TimePickerDialog;
import com.example.timepickter.data.Type;
import com.example.timepickter.listener.OnDateSetListener;

import java.util.ArrayList;
import java.util.Date;

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
    private TextView comTitle, comButton, commit, checkTypeContent, checkTimeContent, project_type_content;
    private LinearLayout checkType, check_time, project_type;
    private DrawerLayout drawerLayout;
    private NewExternalCheckAdapter adapter;
    private NewExternalCheckGridAdapter gridAdapter;
    private Context mContext;
    //选择日期
    private TimePickerDialog mDialogYearMonthDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_new_externalcheck);
        mContext = this;
        findViewById(R.id.toolbar_menu).setOnClickListener(this);
        findViewById(R.id.com_back).setOnClickListener(this);
        comTitle = findViewById(R.id.com_title);
        checkTypeContent = findViewById(R.id.check_type_content);
        commit = findViewById(R.id.commit);
        commit.setOnClickListener(this);
        check_time = findViewById(R.id.check_time);
        check_time.setOnClickListener(this);
        checkTimeContent = findViewById(R.id.check_time_content);
        checkType = findViewById(R.id.check_type);
        checkType.setOnClickListener(this);
        project_type = findViewById(R.id.project_type);
        project_type.setOnClickListener(this);
        project_type_content = findViewById(R.id.project_type_content);
        comButton = findViewById(R.id.com_button);
        drawerLayout = findViewById(R.id.drawer_layout);
        DrawableUtils.setDrawLayout(drawerLayout);
        //展示侧拉界面后，背景透明度（当前透明度为完全透明）
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        dkDragView = findViewById(R.id.suspension);
        //设置不允许超过的边界（左上右下）
        dkDragView.setBoundary(0, 130, 0, 230);
        dkDragView.setOnDragViewClickListener(new DKDragView.onDragViewClickListener() {
            @Override
            public void onClick() {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("");
        }
        adapter = new NewExternalCheckAdapter(R.layout.adapter_new_externalcheck, list);
        recyclerview.setAdapter(adapter);
        drawableRecycler = findViewById(R.id.drawable_recycler);
        drawableRecycler.setLayoutManager(new GridLayoutManager(mContext, 5));
        gridAdapter = new NewExternalCheckGridAdapter(R.layout.check_new_grid_item, list);
        drawableRecycler.addItemDecoration(new GridLayoutItemDecoration(this, R.drawable.item_divider));
        drawableRecycler.setAdapter(gridAdapter);
        Intent intent = getIntent();
        boolean status = intent.getBooleanExtra("status", false);
        if (status) {
            comTitle.setText("新增检查");
            comButton.setText("保存");
        } else {
            comTitle.setText("外业检查");
            comButton.setText("编辑");
        }
        gridAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                drawerLayout.closeDrawers();
                Intent intent1 = new Intent(mContext, ExternalCheckDetailActivity.class);
                intent1.putExtra("page", position);
                startActivity(intent1);
                finish();
            }
        });
        LiveDataBus.get().with("projecttype", CheckType.class).observe(this, new Observer<CheckType>() {
            @Override
            public void onChanged(@Nullable CheckType checkType) {
                project_type_content.setText(checkType.getName());
            }
        });
        dialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_menu:
                break;
            case R.id.commit:
                Intent intent1 = new Intent(mContext, ExternalCheckDetailActivity.class);
                intent1.putExtra("page", 0);
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
                Intent projecttype = new Intent(mContext, ExternalProjectTypeActivity.class);
                startActivity(projecttype);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == Enum.CHECK_TYPE) {
                checkTypeContent.setText(data.getStringExtra("name"));
            }
        }

    }

    /**
     * 说明：时间选择器初始化
     * 创建时间： 2020/7/1 0001 10:57
     *
     * @author winelx
     */
    public void dialog() {
        Date now = new Date();
        mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextSize(15)
                .setCyclic(false)
//                .setMinMillseconds(now.getTime() - (24 * 60 * 60 * 1000) * 2)
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


}
