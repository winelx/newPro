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
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.NewDeviceAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckuserActivity;
import com.example.administrator.newsdf.pzgc.activity.mine.OrganizationaActivity;
import com.example.administrator.newsdf.pzgc.callback.ProblemCallback;
import com.example.administrator.newsdf.pzgc.callback.ProblemCallbackUtils;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.example.administrator.newsdf.pzgc.utils.list.DialogUtils;

import java.util.ArrayList;

/**
 * @author lx
 * @description: 新增特种设备整改
 * @date: 2018/12/3 0003 上午 9:16
 */
public class NewDeviceActivity extends BaseActivity implements View.OnClickListener, ProblemCallback {
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
    private String userId, nodeId;
    private DialogUtils dialogUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_device);
        mContext = this;
        dialogUtils=new DialogUtils();
        //新增/删除问题的接口回调实例化
        ProblemCallbackUtils.setCallBack(this);
        //帮助类
        Utils = new Utils();
        list = new ArrayList<>();
        //初始化控件
        findId();
        //设置控件的外边距
        Utils.setMargins(scrollViewl, 0, 0, 0, 0);
    }

    private void findId() {
        TextView title = (TextView) findViewById(R.id.titleView);
        title.setText("新增特种设备整改");
        problem = (TextView) findViewById(R.id.problem);
        //返回
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //下发整改
        findViewById(R.id.lower_hairs).setOnClickListener(this);
        //添加问题
        findViewById(R.id.new_inspect_addproblem).setOnClickListener(this);
        //滚动布局
        scrollViewl = (ScrollView) findViewById(R.id.scrollView);
        //单据编号
        billnumber = (TextView) findViewById(R.id.new_inspect_billnumber);
        //整改组织
        newInspectOrgLin = (LinearLayout) findViewById(R.id.new_inspect_org_lin);
        newInspectOrgLin.setOnClickListener(this);
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
        newInspectDataLin.setOnClickListener(this);
        newInspectData = (TextView) findViewById(R.id.new_inspect_data);
        newInspectData.setText(Dates.getDay());
        //整改负责人
        newInspectUsernameLin = (LinearLayout) findViewById(R.id.new_inspect_username_lin);
        newInspectUsernameLin.setOnClickListener(this);
        newInspectUsername = (TextView) findViewById(R.id.new_inspect_username);
        //备注
        newInspectRemarks = (EditText) findViewById(R.id.new_inspect_remarks);
        //问题项列表
        newInspectRecycler = (RecyclerView) findViewById(R.id.new_inspect_recycler);
        //列表显示样式
        newInspectRecycler.setLayoutManager(new LinearLayoutManager(this));
        //设置分割线
        newInspectRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //设置适配器
        newInspectRecycler.setAdapter(mAdapter = new NewDeviceAdapter(mContext, list));
        scrollViewl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scrollViewl.post(new Runnable() {
                    @Override
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
            case R.id.new_inspect_username_lin:
                Intent intent1 = new Intent(mContext, CheckuserActivity.class);
                intent1.putExtra("orgId", SPUtils.getString(mContext, "orgId", ""));
                startActivityForResult(intent1, 5);
                break;
            case R.id.new_inspect_org_lin:
                //选择整改部位
                Intent intent12 = new Intent(mContext, OrganizationaActivity.class);
                intent12.putExtra("title", "选择标段");
                intent12.putExtra("data", "Rectifi");
                startActivityForResult(intent12, 3);
                break;
            case R.id.new_inspect_data_lin:
                //巡检日期
                dialogUtils.selectiontime(mContext, new DialogUtils.OnClickListener() {
                    @Override
                    public void onsuccess(String str) {
                        ToastUtils.showLongToast(str);
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * @description: 新增整改问题回调
     * @author lx
     * @date: 2018/12/3 0003 上午 9:52
     */
    @Override
    public void addProblem(String str) {

    }

    /**
     * @description: 删除整改问题回调
     * @author lx
     * @date: 2018/12/3 0003 上午 9:52
     */
    @Override
    public void deleteProblem(String str) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == 2) {
            userId = data.getStringExtra("id");
            newInspectUsername.setText(data.getStringExtra("name"));
        } else if (requestCode == 3 && resultCode == 2) {
            nodeId = data.getStringExtra("id");
            newInspectOrg.setText(data.getStringExtra("name"));
        }
    }
}
