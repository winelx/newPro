package com.example.administrator.newsdf.pzgc.activity.chagedreply;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.FileUtils;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.CheckPermission;
import com.example.administrator.newsdf.camera.CropImageUtils;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.CheckPhotoAdapter;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;

import java.util.ArrayList;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/15 0015}
 * 描述：整改回复单验证
 * {@link }
 */
public class ChagedReplyVerificationActivity extends BaseActivity implements View.OnClickListener {
    ;
    private RecyclerView checkReplyRec;
    private Context mContext;
    private CheckPermission checkPermission;
    private static final int IMAGE_PICKER = 101;
    private String id, noticeId, sdealId = "", repyId, repycontent;
    private EditText replyDescription;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_reply);

        checkPermission = new CheckPermission(this) {
            @Override
            public void permissionSuccess() {
                CropImageUtils.getInstance().takePhoto(ChagedReplyVerificationActivity.this);
            }

            @Override
            public void negativeButton() {
                //如果不重写，默认是finishddsfaasf
                //super.negativeButton();
                ToastUtils.showLongToast("权限申请失败！");
            }
        };
        TextView titleView = (TextView) findViewById(R.id.titleView);
        titleView.setText("整改回复");
        mContext = ChagedReplyVerificationActivity.this;
        replyDescription = (EditText) findViewById(R.id.replyDescription);
        TextView checklistmeuntext = (TextView) findViewById(R.id.checklistmeuntext);
        checklistmeuntext.setText("保存");
        checklistmeuntext.setVisibility(View.VISIBLE);
        checkReplyRec = (RecyclerView) findViewById(R.id.check_reply_rec);
        checkReplyRec.setVisibility(View.GONE);
        findViewById(R.id.checklistback).setOnClickListener(this);
        findViewById(R.id.checklistmeun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checklistback:
                finish();
                break;
            default:
                break;
        }
    }

}
