package com.example.administrator.yanghu.pzgc.activity.chagedreply;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.utils.Enums;
import com.example.administrator.yanghu.pzgc.utils.ToastUtils;
import com.example.administrator.yanghu.pzgc.activity.chagedreply.adapter.ChagedreplyDetailsAdapter;
import com.example.administrator.yanghu.pzgc.activity.chagedreply.utils.ChagedreplyUtils;
import com.example.administrator.yanghu.pzgc.bean.ReplyDetailsContent;
import com.example.baselibrary.base.BaseActivity;
import com.example.administrator.yanghu.pzgc.utils.Dates;
import com.example.baselibrary.inface.Onclicklitener;
import com.example.baselibrary.ui.activity.SignatureViewActivity;
import com.example.baselibrary.utils.rx.RxBus;
import com.example.administrator.yanghu.pzgc.utils.Utils;
import com.example.baselibrary.view.BaseDialog;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/16 0016}
 * 描述：整改回复单详情界面
 * {@link }
 */
public class ChagedreplyDetailsActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private LinearLayout deviceDetailsFunction;
    private TextView titleView;
    private TextView deviceDetailsProving, deviceDetailsUp, deviceDetailsResult, deviceDetailsEdit;

    private int status;
    private boolean taskstatus = true, onclickstatus = true;
    private String id, orgName, noticeId, authority, sysMsgNoticeId;

    private Utils utils;
    private Context mContext;
    private ReplyDetailsContent bean;
    private ChagedreplyDetailsAdapter mAdapter;
    private ArrayList<Object> list;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            request();
            //刷新列表
            RxBus.getInstance().send("chagedlist");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details);
        utils = new Utils();
        mContext = this;
        final Intent intent = getIntent();
        id = intent.getStringExtra("id");
        try {
            sysMsgNoticeId = intent.getStringExtra("sysMsgNoticeId");
        } catch (Exception e) {
            sysMsgNoticeId = null;
        }
        //整改单Id
        try {
            noticeId = intent.getStringExtra("noticeId");

        } catch (Exception e) {
            noticeId = "";
        }
        try {
            authority = intent.getStringExtra("authority");
        } catch (Exception e) {
            authority = null;
        }
        orgName = intent.getStringExtra("orgName");
        //false 能操作，，true只能看
        taskstatus = intent.getBooleanExtra("status", true);
        list = new ArrayList<>();
        RxBus.getInstance().subscribe(String.class, new Consumer<String>() {
            @Override
            public void accept(String path) {
                if ("chagedDetails".equals(path)) {
                    handler.sendMessage(new Message());
                }
            }
        });
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
                if (!taskstatus) {
                    //跳转过去查看
                    preview(string, isreply);
                } else {
                    if (status == 3) {
                        if (permission == 0) {
                            //跳转过去查看
                            preview(string, isreply);
                        } else {
                            //跳转过去修改
                            operation(string, isreply);
                        }
                    } else {
                        //权限 1：验证，打回；2:验证，打回；3：验证、打回；4：回复
                        if (permission == 1 || permission == 2 || permission == 3 || permission == 0) {
                            //跳转过去查看
                            preview(string, isreply);
                        } else {
                            //跳转过去修改

                        }
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
                /*验证*/
                Intent intent = new Intent(this, ChagedReplyVerificationActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("motionnode", bean.getMotionNode());
                startActivity(intent);
                break;
            case R.id.device_details_result:
                /*我回复*/
                startActivity(new Intent(mContext, ChagedReplyBillActivity.class));
                break;
            case R.id.device_details_up:
                if (onclickstatus) {
                    /*提交*/
                    android.support.v7.app.AlertDialog alertDialog2 = new android.support.v7.app.AlertDialog.Builder(mContext)
                            .setMessage(Dates.setText(mContext, "整改问题项是否确认完成并提交？\n注：提交后将无法撤回", 18, 26, R.color.red))
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                //添加"Yes"按钮
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    onclickstatus = false;
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

                }
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
                onclickstatus = true;
                request();
                try {
                    RxBus.getInstance().send("chagedlist");
                    /**
                     * 关联界面 NoticeActivity
                     */
                    RxBus.getInstance().send("刷新数据");
                } catch (Exception e) {

                }
            }

            @Override
            public void onerror(String string) {
                if (Enums.MYAUTOGRAPH.equals(string)) {
                    BaseDialog.confirmmessagedialog(mContext,
                            "确认签字失败",
                            "您当前还未设置我的签名",
                            "取消", "去设置签名", new Onclicklitener() {
                                @Override
                                public void confirm(String string) {
                                    startActivity(new Intent(mContext, SignatureViewActivity.class));
                                }

                                @Override
                                public void cancel(String string) {

                                }
                            });
                } else {
                    Snackbar.make(titleView, string, Snackbar.LENGTH_SHORT).show();
                }

                onclickstatus = true;
            }
        });
    }

    /*网络请求*/
    public void request() {
        ChagedreplyUtils.getReplyFormMainInfo(id, noticeId, authority, sysMsgNoticeId, new ChagedreplyUtils.ListCallback() {
            @Override
            public void onsuccess(ArrayList<Object> data) {
                bean = (ReplyDetailsContent) data.get(0);
                status = bean.getStatus();
                list.clear();
                list.addAll(data);
                mAdapter.setNewData(list);
                //l  0：保存；1：验证中;2:已完成；3：打回
                int permission = bean.getPermission();
                if (taskstatus) {
                    switch (permission) {
                        case 1:
                        case 2:
                        case 3:
                            deviceDetailsFunction.setVisibility(View.VISIBLE);
                            deviceDetailsProving.setVisibility(View.VISIBLE);
                            utils.setMargins(recyclerView, 0, 0, 0, 100);
                            break;
                        case 4:
                            deviceDetailsUp.setText("提交回复");
                            deviceDetailsUp.setVisibility(View.VISIBLE);
                            deviceDetailsFunction.setVisibility(View.VISIBLE);
                            utils.setMargins(recyclerView, 0, 0, 0, 100);
                            break;
                        default:
                            deviceDetailsFunction.setVisibility(View.GONE);
                            utils.setMargins(recyclerView, 0, 0, 0, 0);
                            break;
                    }
                } else {
                    deviceDetailsFunction.setVisibility(View.GONE);
                    utils.setMargins(recyclerView, 0, 0, 0, 0);
                }
            }

            @Override
            public void onerror(String string) {
                ToastUtils.showsnackbar(titleView, string);
            }
        });
    }

    public void preview(String string, int isreply) {
        //跳转过去查看
        Intent intent1 = new Intent(mContext, ChagedReplyBillsActivity.class);
        intent1.putExtra("replyDelId", string);
        intent1.putExtra("replyId", id);
        intent1.putExtra("isReply", isreply);
        intent1.putExtra("status", true);
        startActivity(intent1);
    }

    public void operation(String string, int isreply) {
        //跳转过去编辑
        Intent intent1 = new Intent(mContext, ChagedReplyBillActivity.class);
        intent1.putExtra("replyDelId", string);
        intent1.putExtra("replyId", id);
        intent1.putExtra("isReply", isreply);
        intent1.putExtra("status", true);
        startActivity(intent1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unSubcribe();
    }
}

