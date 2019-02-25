package com.example.administrator.newsdf.pzgc.activity.changed;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.changed.ChagedUtils.CallBack;
import com.example.administrator.newsdf.pzgc.activity.changed.adapter.ChagedNoticeDetailsAdapter;
import com.example.administrator.newsdf.pzgc.bean.ChagedNoticeDetails;
import com.example.administrator.newsdf.pzgc.bean.ChagedNoticeDetailslsit;
import com.example.administrator.newsdf.pzgc.callback.TaskCallbackUtils;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Utils;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/1 0001}
 * 描述：下发整改通知详情
 * {@link }
 */
public class ChagedNoticeDetailsActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView recycler;
    private ChagedNoticeDetailsAdapter adapter;
    private Context mContext;
    private ArrayList<Object> list;
    private LinearLayout deviceDetailsFunction;
    private TextView deviceDetailsAssign, checklistmeuntext;
    private TextView titleView, deviceDetailsResult;
    private ChagedUtils chagedUtils;
    private String billsId, dealId, motionNode, orgId;
    // (1：下发、添加问题项、导入问题项；2:指派；3：指派、我回复；)
    private int permission;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details);
        mContext = this;
        list = new ArrayList<>();
        chagedUtils = new ChagedUtils();
        Intent intent = getIntent();
        //通知单id
        billsId = intent.getStringExtra("id");
        orgId = intent.getStringExtra("orgId");
        titleView = (TextView) findViewById(R.id.titleView);
        titleView.setText(intent.getStringExtra("orgName"));
        /*返回*/
        findViewById(R.id.checklistback).setOnClickListener(this);
        //指派
        deviceDetailsAssign = (TextView) findViewById(R.id.device_details_assign);
        deviceDetailsAssign.setOnClickListener(this);
        //我回复
        deviceDetailsResult = (TextView) findViewById(R.id.device_details_result);
        deviceDetailsResult.setOnClickListener(this);
        //底部功能按钮父布局
        deviceDetailsFunction = (LinearLayout) findViewById(R.id.device_details_function);
        //默认隐藏
        deviceDetailsFunction.setVisibility(View.GONE);
        //内容展示列表
        recycler = (RecyclerView) findViewById(R.id.device_details_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChagedNoticeDetailsAdapter(mContext, list);
        recycler.setAdapter(adapter);
        Utils.setMargins(recycler, 0, 0, 0, 0);
        request();
        adapter.setOnClickListener(new ChagedNoticeDetailsAdapter.OnClickListener() {
            @Override
            public void onClick(int position, String string) {
                Intent intent1 = new Intent(mContext, ChagedNoticeItemDetailsActivity.class);
                //具体问题项的Id
                intent1.putExtra("id", string);
                startActivity(intent1);

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checklistback:
                finish();
                break;
            case R.id.device_details_assign:
                /*指派*/
                Intent intent = new Intent(mContext, ChagedContactsActivity.class);
                intent.putExtra("orgId", orgId);
                startActivityForResult(intent, 3);
                break;
            case R.id.device_details_result:
                android.support.v7.app.AlertDialog alertDialog2 = new android.support.v7.app.AlertDialog.Builder(mContext)
                        .setMessage("是否确认我来回复？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            //添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                reply();
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            //添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create();
                alertDialog2.show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == 2) {
            saveAssignPersonApp(data.getStringExtra("name"), data.getStringExtra("id"), data.getStringExtra("orgid"));
        }
    }

    public void saveAssignPersonApp(String userName, final String userId, final String orgId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("")
                .setMessage("确定将任务指派给:" + userName + " 吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //指派
                        chagedUtils.setassignPage(userId, billsId, motionNode, orgId, new ChagedUtils.CallBacks() {
                            @Override
                            public void onsuccess(String string) {
                                ToastUtils.showShortToastCenter("指派成功");
                                try {
                                    TaskCallbackUtils.CallBackMethod();
                                } catch (Exception e) {

                                }
                                finish();
                                /*   request();*/
                            }

                            @Override
                            public void onerror(String str) {
                                Snackbar.make(titleView, str, Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.show();
    }

    //我回复
    private void reply() {
        /*我回复*/
        chagedUtils.setsenddata(billsId, dealId, motionNode, 2, new ChagedUtils.CallBacks() {
            @Override
            public void onsuccess(String string) {
                ToastUtils.showShortToastCenter(string);
                try {
                    TaskCallbackUtils.CallBackMethod();
                } catch (Exception e) {

                }
                finish();
            }

            @Override
            public void onerror(String str) {
                Snackbar.make(titleView, str, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    /*网络请求*/
    private void request() {
        chagedUtils.getNoticeFormMainInfo(billsId, new CallBack() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                ChagedNoticeDetails item = (ChagedNoticeDetails) map.get("bean");
                //处理节点Id，在getNoticeFormMainInfo接口有返回
                dealId = item.getDealId();
                //当前页面所属操作节点，在getNoticeFormMainInfo接口有返回
                motionNode = item.getMotionNode() + "";
                //当前整改单状态
                permission = item.getPermission();
                list.clear();
                list.add(map.get("bean"));
                list.addAll((ArrayList<ChagedNoticeDetailslsit>) map.get("list2"));
                adapter.setNewData(list);
                switch (permission) {
                    case 2:
                        //指派
                        Utils.setMargins(recycler, 0, 0, 0, 140);
                        deviceDetailsFunction.setVisibility(View.VISIBLE);
                        deviceDetailsAssign.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        //指派、我回复
                        Utils.setMargins(recycler, 0, 0, 0, 140);
                        deviceDetailsAssign.setVisibility(View.VISIBLE);
                        deviceDetailsFunction.setVisibility(View.VISIBLE);
                        deviceDetailsResult.setVisibility(View.VISIBLE);
                        break;
                    default:
                        Utils.setMargins(recycler, 0, 0, 0, 0);
                        deviceDetailsFunction.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onerror(String str) {
                Snackbar.make(titleView, str, Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
