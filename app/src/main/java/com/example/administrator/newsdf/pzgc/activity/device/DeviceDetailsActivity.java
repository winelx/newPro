package com.example.administrator.newsdf.pzgc.activity.device;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.DeviceDetailsAdapter;
import com.example.administrator.newsdf.pzgc.activity.device.utils.DeviceDetailsUtils;
import com.example.administrator.newsdf.pzgc.callback.CallBack;
import com.example.administrator.newsdf.pzgc.callback.DeviceDetailsCallBackUtils;
import com.example.administrator.newsdf.pzgc.callback.Networkinterface;
import com.example.administrator.newsdf.pzgc.callback.TaskCallbackUtils;
import com.example.baselibrary.view.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.example.administrator.newsdf.pzgc.utils.DialogUtils;
import com.lzy.okgo.OkGo;


import java.util.ArrayList;
import java.util.Map;

/**
 * @author lx
 * @date: 2018/11/28 0028 下午 4:21
 * @description: 特种设备详情页面
 */
public class DeviceDetailsActivity extends BaseActivity implements View.OnClickListener, CallBack {
    private RecyclerView mRecyclerView;
    private Context mContext;
    private DeviceDetailsAdapter mAdapter;
    private ArrayList<Object> list;
    private ArrayList<Integer> permission;
    private LinearLayout deviceDetailsFunction;
    private TextView deviceDetailsDown, deviceDetailsProving, deviceDetailsEdit, checklistmeuntext;
    private Utils utils;
    private DeviceDetailsUtils detailsUtils;
    private String id, orgId;
    private TextView deviceDetailsAssign;
    private TextView deviceDetailsResult, titleView;
    private ArrayList<TextView> viewlist;
    private String[] dialog = {"确定", "取消"};
    private boolean status = true;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details);
        addActivity(this);
        final Intent intent = getIntent();
        id = intent.getStringExtra("id");
        status = intent.getBooleanExtra("status", true);
        orgId = intent.getStringExtra("orgId");
        DeviceDetailsCallBackUtils.setCallBack(this);
        mContext = this;
        //帮助类（RecyclerView需要根据状态改变margin）
        utils = new Utils();
        //权限集合
        permission = new ArrayList<>();
        viewlist = new ArrayList<>();
        //界面数据存储集合
        list = new ArrayList<>();
        detailsUtils = new DeviceDetailsUtils();
        titleView = (TextView) findViewById(R.id.titleView);
        titleView.setText(intent.getStringExtra("orgname"));
        findViewById(R.id.checklistmeun).setOnClickListener(this);
        checklistmeuntext = (TextView) findViewById(R.id.checklistmeuntext);
        checklistmeuntext.setVisibility(View.VISIBLE);
        checklistmeuntext.setTextSize(10);
        checklistmeuntext.setText("处理记录");
        //提交
        deviceDetailsDown = (TextView) findViewById(R.id.device_details_up);
        deviceDetailsDown.setOnClickListener(this);
        //指派
        deviceDetailsAssign = (TextView) findViewById(R.id.device_details_assign);
        deviceDetailsAssign.setOnClickListener(this);
        //回复
        deviceDetailsResult = (TextView) findViewById(R.id.device_details_result);
        deviceDetailsResult.setOnClickListener(this);
        //验证
        deviceDetailsProving = (TextView) findViewById(R.id.device_details_proving);
        deviceDetailsProving.setOnClickListener(this);
        //编辑
        deviceDetailsEdit = (TextView) findViewById(R.id.device_details_edit);
        deviceDetailsEdit.setOnClickListener(this);
        //功能
        deviceDetailsFunction = (LinearLayout) findViewById(R.id.device_details_function);
        //recyclerview
        mRecyclerView = (RecyclerView) findViewById(R.id.device_details_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter = new DeviceDetailsAdapter(mContext, list));
        //返回
        findViewById(R.id.checklistback).setOnClickListener(this);
        //设置控件的margin值
        viewlist.add(deviceDetailsDown);
        viewlist.add(deviceDetailsAssign);
        viewlist.add(deviceDetailsResult);
        viewlist.add(deviceDetailsProving);
        viewlist.add(deviceDetailsEdit);
        mAdapter.setOnclickItemLitener(new DeviceDetailsAdapter.onclickitemlitener() {
            @Override
            public void seedetails() {
                Intent intent1 = new Intent(mContext, SeeDetailsActivity.class);
                intent1.putExtra("id", id);
                intent1.putExtra("orgname", titleView.getText().toString());
                startActivity(intent1);
            }
        });
        network(id);
    }

    /**
     * @内容: 点击事件
     * @author lx
     * @date: 2018/12/14 0014 上午 10:28
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.device_details_up:
                //提交
                if (permission.contains(22)) {
                    Dates.getDialogs(this, "提交数据中..");
                    deviceDetailsDown.setClickable(false);
                    //验证提交
                    submitValide();
                } else {
                    //回复提交
                    Dates.getDialogs(this, "提交数据中..");
                    submitreply();
                }
                break;
            case R.id.device_details_assign:
                //指派
                Intent intent1 = new Intent(mContext, SelectaccpectuserActivity.class);
                intent1.putExtra("id", id);
                intent1.putExtra("orgId", orgId);
                startActivityForResult(intent1, 202);
                break;
            case R.id.device_details_result:
                //回复
                Intent intent = new Intent(mContext, CorrectReplyActivity.class);
                //单据Id
                intent.putExtra("id", id);
                startActivity(intent);
                break;
            case R.id.device_details_proving:
                //验证
                if (permission.contains(10)) {
                    //直接提交验证提交后改为20状态
                    //跳转验证单不可选择附件
                    Intent intent2 = new Intent(mContext, VerificationActivity.class);
                    intent2.putExtra("checkId", id);
                    intent2.putExtra("status", false);
                    startActivity(intent2);
                } else {
                    opinion();
                    //20保存验证单 改为21,22
                }
                break;
            case R.id.device_details_edit:
                //编辑
                if (permission.contains(2)) {
                    //修改整改问题项
                    Intent intent2 = new Intent(mContext, CorrectReplyActivity.class);
                    intent2.putExtra("id", id);
                    startActivity(intent2);
                } else if (permission.contains(21)) {
                    //验证21
                    Intent intent2 = new Intent(mContext, VerificationActivity.class);
                    intent2.putExtra("checkId", id);
                    startActivity(intent2);
                }
                break;
            case R.id.checklistback:
                //返回
                finish();
                break;
            case R.id.checklistmeun:
                Intent intent2 = new Intent(mContext, DeviceRecordActivity.class);
                intent2.putExtra("id", id);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    //0指派、1创建回复、2编辑回复、3提交回复、10项目经理创建验证、20下发人创建验证、21下发人编辑验证、22下发人提交验证
    public void network(String id) {
        Dates.getDialogs(this, "请求数据中...");
        hideView();
        detailsUtils.details(id, new DeviceDetailsUtils.DeviceDetailslitener() {
            @Override
            public void onsuccess(ArrayList<Object> lists, ArrayList<Integer> data) {
                permission.clear();
                list.clear();
                permission.addAll(data);
                list.addAll(lists);
                mAdapter.setNewData(list);
                if (status) {
                    if (permission.size() > 0) {
                        deviceDetailsFunction.setVisibility(View.VISIBLE);
                        utils.setMargins(mRecyclerView, 0, 0, 0, 120);
                        authority();
                    } else {
                        utils.setMargins(mRecyclerView, 0, 0, 0, 0);
                        deviceDetailsFunction.setVisibility(View.GONE);
                    }
                } else {
                    deviceDetailsFunction.setVisibility(View.GONE);
                    utils.setMargins(mRecyclerView, 0, 0, 0, 0);
                }
            }
        });
    }

    /**
     * @内容: 根据返回的权限集合显示按钮
     * @author lx
     * @date: 2018/12/14 0014 上午 10:26
     */
    public void authority() {
        //0指派、1创建回复、2编辑回复、3提交回复、10项目经理创建验证、20下发人创建验证、21下发人编辑验证、22下发人提交验证
        for (int i = 0; i < permission.size(); i++) {
            Integer item = permission.get(i);
            switch (item) {
                case 0:
                    //指派
                    deviceDetailsAssign.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    //1创建回复
                    deviceDetailsResult.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    //编辑
                    deviceDetailsEdit.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    //提交
                    deviceDetailsDown.setVisibility(View.VISIBLE);
                    break;
                case 10:
                    //10项目经理创建验证
                    deviceDetailsProving.setVisibility(View.VISIBLE);
                    break;
                case 20:
                    //20下发人创建验证
                    //验证
                    deviceDetailsProving.setVisibility(View.VISIBLE);
                    break;
                case 21:
                    //21下发人编辑验证
                    //验证
                    deviceDetailsEdit.setVisibility(View.VISIBLE);
                    break;
                case 22:
                    //提交
                    deviceDetailsDown.setVisibility(View.VISIBLE);
                    //22下发人提交验证
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * @内容: 每次请求前隐藏按钮，请求完成后根据权限集合显示
     * @author lx
     * @date: 2018/12/14 0014 上午 10:27
     */
    public void hideView() {
        for (int i = 0; i < viewlist.size(); i++) {
            TextView view = viewlist.get(i);
            view.setVisibility(View.GONE);
        }
    }

    /**
     * @内容: activity回调
     * @author lx
     * @date: 2018/12/14 0014 上午 10:27
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 202 && resultCode == 202) {
            //指派的回调，返回指派人 名字 和ID
            String titlle = "是否指派给：" + data.getStringExtra("name");
            final String userid = data.getStringExtra("id");
            DialogUtils.Tipsdialog(mContext, titlle, dialog, new DialogUtils.OnClickListener() {
                @Override
                public void onsuccess(String str) {
                    detailsUtils.assignPersonOfSEC(id, userid, new Networkinterface() {
                        @Override
                        public void onsuccess(Map<String, Object> map) {
                            ToastUtils.showLongToast("指派成功");
                            TaskCallbackUtils.CallBackMethod();
                            network(id);
                        }
                    });
                }
            });
        }
    }

    /**
     * @内容: 验证
     * @author lx
     * @date: 2018/12/14 0014 上午 11:56
     */
    public void opinion() {
        Intent intent = new Intent(mContext, VerificationActivity.class);
        intent.putExtra("checkId", id);
        startActivity(intent);
    }

    /**
     * @内容: 提交回复
     * @author lx
     * @date: 2018/12/16 0016 下午 1:42
     */
    public void submitreply() {
        detailsUtils.submitreply(deviceDetailsDown, id, new Networkinterface() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                //提交成功刷新界面
                Dates.disDialog();
                TaskCallbackUtils.CallBackMethod();
                finish();
            }
        });
    }

    /**
     * @内容: 提交验证
     * @author lx
     * @date: 2018/12/16 0016 下午 3:24
     */
    public void submitValide() {
        detailsUtils.submitValide(id, new Networkinterface() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                //提交成功刷新界面
                Dates.disDialog();
                network(id);
                TaskCallbackUtils.CallBackMethod();

            }
        });
    }

    /**
     * @内容: 验证完成后刷新界面
     * @author lx
     * @date: 2018/12/16 0016 下午 3:01
     */
    @Override
    public void deleteTop() {
        network(id);
        TaskCallbackUtils.CallBackMethod();

    }

    //退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //取消当前界面的请求
            OkGo.getInstance().cancelTag("details");
            finish();
            return true;
        }
        return true;
    }
}
