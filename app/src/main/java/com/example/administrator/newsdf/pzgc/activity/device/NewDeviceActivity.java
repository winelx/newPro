package com.example.administrator.newsdf.pzgc.activity.device;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.NewDeviceAdapter;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Utils;

import java.util.ArrayList;

/**
 * 新增特种设备整改
 */
public class NewDeviceActivity extends BaseActivity implements View.OnClickListener {
    private ScrollView scrollViewl;
    private Utils Utils;
    private TextView billnumber, facilitynumber, newInspectOrg, newInspectFacility, newInspectFacilitymodel;
    private TextView newInspectData, newInspectUsername, problem;
    private LinearLayout newInspectOrgLin, newInspectFacilityLin, newInspectDataLin, newInspectUsernameLin;
    private EditText newInspectAddress, newInspectRemarks;
    private RecyclerView newInspectRecycler;
    private NewDeviceAdapter mAdapter;
    private Context mContext;
    private ArrayList<String> list;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_device);
        mContext = this;
        Utils = new Utils();
        list = new ArrayList<>();
        findId();
        Utils.setMargins(scrollViewl, 0, 0, 0, 0);
    }

    private void findId() {
        TextView title = (TextView) findViewById(R.id.titleView);
        title.setText("新增特种设备整改");
        problem = (TextView) findViewById(R.id.problem);
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.lower_hairs).setOnClickListener(this);
        findViewById(R.id.new_inspect_addproblem).setOnClickListener(this);
        scrollViewl = (ScrollView) findViewById(R.id.scrollView);
        //单据编号
        billnumber = (TextView) findViewById(R.id.new_inspect_billnumber);
        //整改组织
        newInspectOrgLin = (LinearLayout) findViewById(R.id.new_inspect_org_lin);
        newInspectOrg = (TextView) findViewById(R.id.new_inspect_org);
        //设备名称
        newInspectFacilityLin = (LinearLayout) findViewById(R.id.new_inspect_facility_lin);
        newInspectFacility = (TextView) findViewById(R.id.new_inspect_facility);
        //设备编号
        facilitynumber = (TextView) findViewById(R.id.new_inspect_facilitynumber);
        //型号规格
        newInspectFacilitymodel = (TextView) findViewById(R.id.new_inspect_facilitymodel);
        //设备使用地点
        newInspectAddress = (EditText) findViewById(R.id.new_inspect_address);
        //巡检日期
        newInspectDataLin = (LinearLayout) findViewById(R.id.new_inspect_data_lin);
        newInspectData = (TextView) findViewById(R.id.new_inspect_data);
        //整改负责人
        newInspectUsernameLin = (LinearLayout) findViewById(R.id.new_inspect_username_lin);
        newInspectUsername = (TextView) findViewById(R.id.new_inspect_username);
        //备注
        newInspectRemarks = (EditText) findViewById(R.id.new_inspect_remarks);
        //问题项列表
        newInspectRecycler = (RecyclerView) findViewById(R.id.new_inspect_recycler);
        newInspectRecycler.setLayoutManager(new LinearLayoutManager(this));
        newInspectRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        newInspectRecycler.setAdapter(mAdapter = new NewDeviceAdapter(mContext, list));
        scrollViewl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scrollViewl.post(new Runnable() {
                    public void run() {
                        scrollViewl.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lower_hairs:
                //下发整改
                break;
            case R.id.new_inspect_addproblem:
                //添加问题
//                problem.setVisibility(View.VISIBLE);
//                page++;
//                list.add("测试数据" + page);
//                mAdapter.setNewDate(list);
                startActivity(new Intent(mContext, ProblemItemActivity.class));
                break;
            default:
                break;
        }
    }
}
