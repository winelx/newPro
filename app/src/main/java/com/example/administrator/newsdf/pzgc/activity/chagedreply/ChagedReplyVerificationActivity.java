package com.example.administrator.newsdf.pzgc.activity.chagedreply;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.utils.ChagedreplyUtils;
import com.example.administrator.newsdf.pzgc.callback.OgranCallbackUtils1;
import com.example.administrator.newsdf.pzgc.callback.TaskCallbackUtils;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/15 0015}
 * 描述：整改回复单验证
 * {@link }
 */
@SuppressLint("Registered")
public class ChagedReplyVerificationActivity extends BaseActivity implements View.OnClickListener {
    private TextView categoryItem;
    private EditText replyDescription;
    private Context mContext;
    private TextView titleView, category_item;
    private String id, motionnode;
    private int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_reply_verification);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        motionnode = intent.getIntExtra("motionnode", 01) + " ";
        mContext = this;
        titleView = (TextView) findViewById(R.id.titleView);
        titleView.setText("验证");
        category_item = (TextView) findViewById(R.id.category_item);
        findViewById(R.id.validation_status).setOnClickListener(this);
        findViewById(R.id.checklistmeun).setOnClickListener(this);
        TextView text = (TextView) findViewById(R.id.checklistmeuntext);
        text.setText("保存");
        categoryItem = (TextView) findViewById(R.id.category_item);
        replyDescription = (EditText) findViewById(R.id.replyDescription);
        findViewById(R.id.checklistback).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.validation_status:
                AlertDialog alertDialog2 = new AlertDialog.Builder(mContext)
                        .setMessage("是否验证通过")
                        .setPositiveButton("通过", new DialogInterface.OnClickListener() {
                            //添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                /*删除按钮*/
                                dialogInterface.dismiss();
                                status = 1;
                                category_item.setText("通过");
                                category_item.setTextColor(Color.parseColor("#28c26A"));

                            }
                        })
                        .setNegativeButton("打回", new DialogInterface.OnClickListener() {
                            //添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                status = 2;
                                category_item.setText("打回");
                                category_item.setTextColor(Color.parseColor("#FE0000"));

                            }
                        })
                        .create();
                alertDialog2.show();
                break;
            case R.id.checklistmeun:
                if (status == 0) {
                    ToastUtils.showShortToastCenter("请确认是否验证通过！");
                } else {
                    verification();

                }
                break;
            case R.id.checklistback:
                finish();
                break;
            default:
                break;
        }
    }

    /*验证*/
    private void verification() {
        /**
         *id  整改验证单id
         * motionNode  运动节点
         * verificationDate 验证日期
         * isby  是否通过，1通过2打回
         * verificationOpinion  意见
         */
        ChagedreplyUtils.getOrgInfoBycnfvalidReply(id, motionnode, replyDescription.getText().toString(), status, new ChagedreplyUtils.ObjectCallBacks() {
            @Override
            public void onsuccess(String string) {
                try {
                    //刷新单据详情
                    OgranCallbackUtils1.removeCallBackMethod();
                    //刷新列表
                    TaskCallbackUtils.CallBackMethod();
                } catch (Exception e) {
                }
                finish();
            }

            @Override
            public void onerror(String string) {
                Snackbar.make(titleView, string, Snackbar.LENGTH_SHORT).show();
            }
        });

    }
}
