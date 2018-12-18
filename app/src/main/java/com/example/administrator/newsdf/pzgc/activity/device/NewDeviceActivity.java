package com.example.administrator.newsdf.pzgc.activity.device;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.NewDeviceAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckuserActivity;
import com.example.administrator.newsdf.pzgc.activity.device.utils.DeviceUtils;
import com.example.administrator.newsdf.pzgc.activity.mine.OrganizationaActivity;
import com.example.administrator.newsdf.pzgc.bean.DetailsBean;
import com.example.administrator.newsdf.pzgc.bean.Devicedetails;
import com.example.administrator.newsdf.pzgc.callback.ProblemCallback;
import com.example.administrator.newsdf.pzgc.callback.ProblemCallbackUtils;
import com.example.administrator.newsdf.pzgc.callback.TaskCallbackUtils;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.example.administrator.newsdf.pzgc.utils.list.DialogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lx
 * @description: 新增特种设备整改
 * @date: 2018/12/3 0003 上午 9:16
 */
public class NewDeviceActivity extends BaseActivity implements View.OnClickListener, ProblemCallback {
    private ScrollView scrollViewl;
    private Utils utils;
    private TextView billnumber, newInspectOrg, newInspectFacility;
    private TextView newInspectData, newInspectUsername, problem, checklistmeuntext, lowerHairs;
    private LinearLayout newInspectOrgLin, newInspectFacilityLin, newInspectDataLin, newInspectUsernameLin;
    private EditText newInspectAddress, newInspectRemarks, facilitynumber, newInspectFacilitymodel;
    private RecyclerView newInspectRecycler;
    private NewDeviceAdapter mAdapter;
    @SuppressLint("StaticFieldLeak")
    private static NewDeviceActivity mContext;
    private ArrayList<DetailsBean> mData;
    private int pos;
    private String userId, nodeId, facilityId;
    private DialogUtils dialogUtils;
    private String id;
    private DeviceUtils deviceUtils;
    private ArrayList<EditText> editTextslist;

    public static NewDeviceActivity getInstance() {
        return mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_device);
        Intent intent = getIntent();//获取传来的intent对象
        id = intent.getStringExtra("id");//获取键值对的键名
        mContext = this;
        dialogUtils = new DialogUtils();
        deviceUtils = new DeviceUtils();
        //新增/删除问题的接口回调实例化
        ProblemCallbackUtils.setCallBack(this);
        //帮助类
        utils = new Utils();
        editTextslist = new ArrayList<>();
        mData = new ArrayList<>();
        //初始化控件
        findId();
        //设置控件的外边距
        utils.setMargins(scrollViewl, 0, 0, 0, 0);
        if (id != null) {
            fonclick();
            checklistmeuntext.setText("编辑");
            request();
        } else {
            checklistmeuntext.setText("保存");
        }
    }

    /**
     * @内容: 请求当前界面数据
     * @author lx
     * @date: 2018/12/16 0016 下午 2:29
     */
    private void request() {
        deviceUtils.getSECMainInfo(id, new DeviceUtils.MainInfolitener() {
            @Override
            public void success(Devicedetails bean, ArrayList<DetailsBean> data) {
                id = bean.getId();
                newInspectUsername.setText(bean.getPersonLiable());
                //整改负责人
                userId = bean.getPersonLiable();
                newInspectUsername.setText(bean.getPersonLiableName());
                //选择组织
                nodeId = bean.getOrgId();
                newInspectOrg.setText(bean.getOrgName());
                //设备名称
                newInspectFacility.setText(bean.getTypeName());
                facilityId = bean.getTypeId();
                //型号规格
                newInspectFacilitymodel.setText(bean.getTs());
                //设备编号
                facilitynumber.setText(bean.getEnumber());
                //巡检日期
                newInspectData.setText(bean.getCheckDate());
                //使用地点
                newInspectAddress.setText(bean.getPlace());
                //
                billnumber.setText(bean.getNumber());
                try {
                    newInspectRemarks.setText(bean.getRemarks());
                } catch (Exception e) {
                }
                mData.clear();
                mData.addAll(data);
                mAdapter.setNewDate(mData);
                if (mData.size() > 0) {
                    utils.setMargins(scrollViewl, 0, 0, 0, 110);
                    lowerHairs.setVisibility(View.VISIBLE);
                } else {
                    utils.setMargins(scrollViewl, 0, 0, 0, 0);
                    lowerHairs.setVisibility(View.GONE);
                }
                if (mData.size() > 0) {
                    problem.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void findId() {
        //保存按钮
        checklistmeuntext = (TextView) findViewById(R.id.checklistmeuntext);
        checklistmeuntext.setVisibility(View.VISIBLE);
        //保存
        findViewById(R.id.checklistmeun).setOnClickListener(this);
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
        lowerHairs = (TextView) findViewById(R.id.lower_hairs);
        lowerHairs.setOnClickListener(this);
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
        newInspectFacilityLin.setOnClickListener(this);
        newInspectFacility = (TextView) findViewById(R.id.new_inspect_facility);
        //设备编号
        facilitynumber = (EditText) findViewById(R.id.new_inspect_facilitynumber);
        editTextslist.add(facilitynumber);
        //型号规格
        newInspectFacilitymodel = (EditText) findViewById(R.id.new_inspect_facilitymodel);
        editTextslist.add(newInspectFacilitymodel);
        //设备使用地点
        newInspectAddress = (EditText) findViewById(R.id.new_inspect_address);
        editTextslist.add(newInspectAddress);
        //巡检日期
        newInspectDataLin = (LinearLayout) findViewById(R.id.new_inspect_data_lin);
        newInspectDataLin.setOnClickListener(this);
        newInspectData = (TextView) findViewById(R.id.new_inspect_data);
        //整改负责人
        newInspectUsernameLin = (LinearLayout) findViewById(R.id.new_inspect_username_lin);
        newInspectUsernameLin.setOnClickListener(this);
        newInspectUsername = (TextView) findViewById(R.id.new_inspect_username);
        //备注
        newInspectRemarks = (EditText) findViewById(R.id.new_inspect_remarks);
        editTextslist.add(newInspectRemarks);
        //问题项列表
        newInspectRecycler = (RecyclerView) findViewById(R.id.new_inspect_recycler);
        //列表显示样式
        newInspectRecycler.setLayoutManager(new LinearLayoutManager(this));
        //设置分割线
        newInspectRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //设置适配器
        newInspectRecycler.setAdapter(mAdapter = new NewDeviceAdapter(mContext, mData));
        //recyclerview的点击事件
        mAdapter.setOnClickListener(new NewDeviceAdapter.OnClickListener() {
            @Override
            public void onclick(View view, int position) {
                pos = position;
                Intent intent = new Intent(NewDeviceActivity.this, ProblemItemActivity.class);
                //问题项ID
                intent.putExtra("typeId", mData.get(position).getId());
                //状态
                intent.putExtra("bean", false);
                //单据ID
                intent.putExtra("checkId", id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lower_hairs:
                //下发整改
                deviceUtils.sendseccheck(id, new DeviceUtils.devicesavelitener() {
                    @Override
                    public void success(String number, String id) {
                        ToastUtils.showLongToast("下发成功");
                        //刷新列表
                        try {
                            TaskCallbackUtils.CallBackMethod();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        finish();
                    }
                });
                break;
            case R.id.new_inspect_addproblem:
                //添加问题
                if ("编辑".equals(checklistmeuntext.getText().toString())) {
                    if (facilityId != null) {
                        if (id != null) {
                            Intent intent = new Intent(mContext, ProblemItemActivity.class);
                            intent.putExtra("bean", true);
                            intent.putExtra("typeId", "");
                            intent.putExtra("facility", facilityId);
                            intent.putExtra("checkId", id);
                            pos = -1;
                            startActivity(intent);
                        } else {
                            ToastUtils.showLongToast("请先保存单据信息");
                        }
                    } else {
                        ToastUtils.showLongToast("请先选择设备名称");
                    }
                } else {
                    ToastUtils.showLongToast("请先保存数据");
                }

                break;
            case R.id.new_inspect_username_lin:
                //选择联系人
                if (!"编辑".equals(checklistmeuntext.getText().toString())) {
                    if ( nodeId != null) {
                        Intent intent1 = new Intent(mContext, CheckuserActivity.class);
                        intent1.putExtra("orgId", nodeId);
                        startActivityForResult(intent1, 5);
                    } else {
                        ToastUtils.showLongToast("请先选择整改组织");
                    }
                } else {
                    ToastUtils.showLongToast("不是编辑状态");
                }
                break;
            case R.id.new_inspect_org_lin:
                //选择整改部位
                if (!"编辑".equals(checklistmeuntext.getText().toString())) {
                    Intent intent12 = new Intent(mContext, OrganizationaActivity.class);
                    intent12.putExtra("title", "选择标段");
                    intent12.putExtra("data", "Rectifi");
                    startActivityForResult(intent12, 3);
                } else {
                    ToastUtils.showLongToast("不是编辑状态");
                }
                break;
            case R.id.new_inspect_data_lin:
                //巡检日期
                if (!"编辑".equals(checklistmeuntext.getText().toString())) {
                    dialogUtils.selectiontime(mContext, new DialogUtils.OnClickListener() {
                        @Override
                        public void onsuccess(String str) {
                            newInspectData.setText(str);
                        }
                    });
                } else {
                    ToastUtils.showLongToast("不是编辑状态");
                }
                break;
            case R.id.new_inspect_facility_lin:
                if (!"编辑".equals(checklistmeuntext.getText().toString())) {
                    //设备名称
                    Intent intent2 = new Intent(mContext, FacilitynameActivity.class);
                    startActivityForResult(intent2, 4);
                } else {
                    ToastUtils.showLongToast("不是编辑状态");
                }
                break;
            case R.id.checklistmeun:
                String str = checklistmeuntext.getText().toString();
                if (str.equals("编辑")) {
                    tonclick();
                    utils.setMargins(scrollViewl, 0, 0, 0, 0);
                    lowerHairs.setVisibility(View.GONE);
                    checklistmeuntext.setText("保存");
                } else if ("保存".equals(str)) {
                    //保存
                    deviceSave();
                }
                break;
            default:
                break;
        }

    }

    /**
     * @内容: 新增特种设备保存
     * @author lx
     * @date: 2018/12/11 0011 上午 9:19
     */
    private void deviceSave() {
        Map<String, Object> map = new HashMap<>();
        if (nodeId != null) {
            map.put("orgId", nodeId);
        } else {
            ToastUtils.showLongToast("整改组织还未选择");
            return;
        }
        if (facilityId != null) {
            map.put("typeId", facilityId);
        } else {
            ToastUtils.showLongToast("设备名称还未选择");
            return;
        }
        if (!TextUtils.isEmpty(facilitynumber.getText().toString())) {
            map.put("enumber", facilitynumber.getText().toString());
        } else {
            ToastUtils.showLongToast("设备编号还未填写");
            return;
        }
        if (!TextUtils.isEmpty(newInspectFacilitymodel.getText().toString())) {
            map.put("ts", newInspectFacilitymodel.getText().toString());
        } else {
            ToastUtils.showLongToast("型号规格还未填写");
            return;
        }
        if (!TextUtils.isEmpty(newInspectAddress.getText().toString())) {
            map.put("place", newInspectAddress.getText().toString());
        } else {
            ToastUtils.showLongToast("使用地点还填写");
            return;
        }
        if (!TextUtils.isEmpty(newInspectData.getText().toString())) {
            map.put("checkDate", newInspectData.getText().toString());
        } else {
            ToastUtils.showLongToast("巡检日期还未选择");
            return;
        }
        if (userId != null) {
            map.put("personLiable", userId);
        } else {
            ToastUtils.showLongToast("整改负责人还未选择");
            return;
        }
        //备注
        if (!TextUtils.isEmpty(newInspectRemarks.getText().toString())) {
            map.put("remarks", newInspectRemarks.getText().toString());
        }
        //id
        if (id != null) {
            map.put("id", id);
        }
        deviceUtils.devicesave(map, new DeviceUtils.devicesavelitener() {
            @Override
            public void success(String number, String string) {
                //显示单据编号
                billnumber.setText(number);
                //设置Id
                id = string;
                //更改按钮
                checklistmeuntext.setText("编辑");
                //关闭输入框
                fonclick();
                if (mData.size() > 0) {
                    utils.setMargins(scrollViewl, 0, 0, 0, 110);
                    lowerHairs.setVisibility(View.VISIBLE);
                } else {
                    utils.setMargins(scrollViewl, 0, 0, 0, 0);
                    lowerHairs.setVisibility(View.GONE);
                }
                //更新界面数据
                request();
            }
        });
    }

    /**
     * @内容: 输入框不能输入
     * @author lx
     * @date: 2018/12/12 0012 上午 10:05
     */
    public void fonclick() {
        for (int i = 0; i < editTextslist.size(); i++) {
            EditText editText = editTextslist.get(i);
            editText.setEnabled(false);
        }
    }

    /**
     * @内容: 输入框可以输入
     * @author lx
     * @date: 2018/12/12 0012 上午 10:05
     */
    public void tonclick() {
        for (int i = 0; i < editTextslist.size(); i++) {
            EditText editText = editTextslist.get(i);
            editText.setEnabled(true);
        }
    }

    /**
     * @内容: 新增删除问题的回调
     * @author lx
     * @date: 2018/12/12 0012 上午 10:04
     */
    @Override
    public void problemcallback(ArrayList<DetailsBean> list) {
        //刷新界面数据
        request();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == 2) {
            //整改负责人
            userId = data.getStringExtra("userid");
            newInspectUsername.setText(data.getStringExtra("name"));
        } else if (requestCode == 3 && resultCode == 2) {
            //选择组织
            nodeId = data.getStringExtra("id");
            newInspectOrg.setText(data.getStringExtra("name"));
        } else if (requestCode == 4 && resultCode == RESULT_OK) {
            //设备名称
            newInspectFacility.setText(data.getStringExtra("name"));
            facilityId = data.getStringExtra("id");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialogUtils != null) {
            dialogUtils = null;
        }
        if (utils != null) {
            utils = null;
        }
    }

}
