package com.example.administrator.newsdf.pzgc.activity.chagedreply;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.adapter.ChagedreplyDetailsAdapter;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.utils.ChagedreplyUtils;
import com.example.administrator.newsdf.pzgc.bean.ReplyDetailsContent;
import com.example.administrator.newsdf.pzgc.callback.OgranCallback;
import com.example.administrator.newsdf.pzgc.callback.OgranCallbackUtils1;
import com.example.administrator.newsdf.pzgc.callback.TaskCallbackUtils;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.Utils;

import java.util.ArrayList;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/16 0016}
 * 描述：整改回复单详情界面
 * {@link }
 */
public class ChagedreplyDetailsActivity extends BaseActivity implements View.OnClickListener, OgranCallback {
    private RecyclerView recyclerView;
    private LinearLayout deviceDetailsFunction;
    private TextView titleView;
    private ChagedreplyDetailsAdapter mAdapter;
    private ArrayList<Object> list;
    private Context mContext;
    private TextView deviceDetailsProving, deviceDetailsUp,
            deviceDetailsResult, deviceDetailsAssign, deviceDetailsEdit;
    private String id, orgName;
    int status, p;
    private ReplyDetailsContent bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details);
        mContext = this;
        final Intent intent = getIntent();
        id = intent.getStringExtra("id");
        orgName = intent.getStringExtra("orgName");
        list = new ArrayList<>();
        OgranCallbackUtils1.setCallBack(this);
        findViewById(R.id.checklistback).setOnClickListener(this);
        /*验证*/
        deviceDetailsProving = (TextView) findViewById(R.id.device_details_proving);
        deviceDetailsProving.setOnClickListener(this);
        /*我回复*/
        deviceDetailsResult = (TextView) findViewById(R.id.device_details_result);
        deviceDetailsResult.setOnClickListener(this);
        /*提交*/
        deviceDetailsUp = (TextView) findViewById(R.id.device_details_up);
        deviceDetailsUp.setOnClickListener(this);
        /*编辑*/
        deviceDetailsEdit = (TextView) findViewById(R.id.device_details_edit);
        deviceDetailsEdit.setOnClickListener(this);
        /*标题*/
        titleView = (TextView) findViewById(R.id.titleView);
        titleView.setText(orgName);
        /*功能按钮父布局*/
        deviceDetailsFunction = (LinearLayout) findViewById(R.id.device_details_function);
        deviceDetailsFunction.setVisibility(View.GONE);
        /*内容展示控件*/
        recyclerView = (RecyclerView) findViewById(R.id.device_details_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter = new ChagedreplyDetailsAdapter(list, mContext));
        mAdapter.setOnclicktener(new ChagedreplyDetailsAdapter.onclicktener() {
            @Override
            public void onClick(int position, int isreply, String string) {
                //l  0：保存；1：验证中;2:已完成；3：打回
                int permission = bean.getPermission();
                if (status == 3) {
                    if (permission == 0) {
                        //跳转过去查看
                        Intent intent1 = new Intent(mContext, ChagedReplyBillsActivity.class);
                        intent1.putExtra("replyDelId", string);
                        intent1.putExtra("replyId", id);
                        intent1.putExtra("isReply", isreply);
                        intent1.putExtra("status", true);
                        startActivity(intent1);
                    } else {
                        //跳转过去修改
                        Intent intent1 = new Intent(mContext, ChagedReplyBillActivity.class);
                        intent1.putExtra("replyDelId", string);
                        intent1.putExtra("replyId", id);
                        intent1.putExtra("isReply", isreply);
                        intent1.putExtra("status", true);
                        startActivity(intent1);
                    }

                } else {
                    //权限 1：验证，打回；2:验证，打回；3：验证、打回；4：回复
                    if (permission == 1 || permission == 2 || permission == 3 || permission == 0) {
                        //跳转过去查看
                        Intent intent1 = new Intent(mContext, ChagedReplyBillsActivity.class);
                        intent1.putExtra("replyDelId", string);
                        intent1.putExtra("replyId", id);
                        intent1.putExtra("isReply", isreply);
                        intent1.putExtra("status", true);
                        startActivity(intent1);
                    } else {
                        //跳转过去修改
                        Intent intent1 = new Intent(mContext, ChagedReplyBillActivity.class);
                        intent1.putExtra("replyDelId", string);
                        intent1.putExtra("replyId", id);
                        intent1.putExtra("isReply", isreply);
                        intent1.putExtra("status", true);
                        startActivity(intent1);
                    }
                }

            }
        });
        request();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checklistback:
                finish();
                break;
            case R.id.device_details_proving:
                Intent intent = new Intent(this, ChagedReplyVerificationActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("motionnode", bean.getMotionNode());
                startActivity(intent);
                /*验证*/
                break;
            case R.id.device_details_result:
                /*我回复*/
                startActivity(new Intent(mContext, ChagedReplyBillActivity.class));
                break;
            case R.id.device_details_up:
                /*提交*/
                android.support.v7.app.AlertDialog alertDialog2 = new android.support.v7.app.AlertDialog.Builder(mContext)
                        .setMessage(Dates.setText(mContext, "整改问题项是否确认完成并提交？\n注：提交后将无法撤回", 18, 26, R.color.red))
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            //添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                submit();
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
            case R.id.device_details_edit:
                /*编辑*/
                startActivity(new Intent(this, ChagedReplyVerificationActivity.class));
                break;
            default:
                break;
        }
    }

    /*提交回复*/
    public void submit() {
        ChagedreplyUtils.submitReplyData(id, bean.getMotionNode() + "", new ChagedreplyUtils.ObjectCallBacks() {
            @Override
            public void onsuccess(String string) {
                request();
                try {
                    TaskCallbackUtils.CallBackMethod();
                } catch (Exception e) {

                }
            }

            @Override
            public void onerror(String string) {
                Snackbar.make(titleView, string, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    /*网络请求*/
    public void request() {
        ChagedreplyUtils.getReplyFormMainInfo(id, new ChagedreplyUtils.ListCallback() {
            @Override
            public void onsuccess(ArrayList<Object> data) {
                bean = (ReplyDetailsContent) data.get(0);
                status = bean.getStatus();
                list.clear();
                list.addAll(data);
                mAdapter.setNewData(list);
                //l  0：保存；1：验证中;2:已完成；3：打回
                int permission = bean.getPermission();
                switch (permission) {
                    case 1:
                    case 2:
                    case 3:
                        deviceDetailsFunction.setVisibility(View.VISIBLE);
                        deviceDetailsProving.setVisibility(View.VISIBLE);
                        Utils.setMargins(recyclerView, 0, 0, 0, 100);
                        break;
                    case 4:
                        deviceDetailsUp.setText("提交回复");
                        deviceDetailsUp.setVisibility(View.VISIBLE);
                        deviceDetailsFunction.setVisibility(View.VISIBLE);
                        Utils.setMargins(recyclerView, 0, 0, 0, 100);
                        break;
                    default:
                        deviceDetailsFunction.setVisibility(View.GONE);
                        Utils.setMargins(recyclerView, 0, 0, 0, 0);
                        break;
                }

            }

            @Override
            public void onerror(String string) {
                ToastUtils.showsnackbar(titleView, string);
            }
        });
    }

    //删除问题项回调
    @Override
    public void taskCallback() {
        request();
    }
}

