package com.example.administrator.newsdf.pzgc.activity.chagedreply;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.FiletypeAdapter;
import com.example.administrator.newsdf.pzgc.bean.FileTypeBean;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.BasePhotoAdapter;
import com.example.administrator.newsdf.pzgc.Adapter.RectifierAdapter;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.utils.ChagedreplyUtils;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.utils.bean.ReplyBillBean;
import com.example.administrator.newsdf.pzgc.callback.NetworkinterfaceCallbackUtils;
import com.example.administrator.newsdf.pzgc.callback.OgranCallbackUtils;
import com.example.administrator.newsdf.pzgc.photopicker.PhotoPreview;
import com.example.baselibrary.view.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.PopCameraUtils;
import com.example.administrator.newsdf.pzgc.utils.TakePictureManager;
import com.example.baselibrary.bean.photoBean;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/15 0015}
 * 描述：整改通知单回复
 * {@link }
 */
public class ChagedReplyBillsActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private RecyclerView chagedOldRecycler, photoRecycler;
    private RectifierAdapter mAdapter;
    private FiletypeAdapter adapter;
    private ArrayList<FileTypeBean> photoPaths;
    private ArrayList<String> photolist;
    private EditText replydescription;
    private TextView content, rectificationreason, standarddel, delete;
    private String replyId, replyDelId;
    private boolean lean;
    //是否回复
    private int isReply;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chagedreply_bill);
        mContext = this;
        Intent intent = getIntent();
        isReply = intent.getIntExtra("isReply", 0);
        //回复单id
        replyId = intent.getStringExtra("replyId");
        lean = intent.getBooleanExtra("status", true);
        //问题项Id
        replyDelId = intent.getStringExtra("replyDelId");
        photoPaths = new ArrayList<>();
        photolist = new ArrayList<>();
        content = (TextView) findViewById(R.id.content);
        delete = (TextView) findViewById(R.id.delete);
        delete.setOnClickListener(this);
        findViewById(R.id.checklistback).setOnClickListener(this);
        findViewById(R.id.checklistmeun).setOnClickListener(this);
        TextView titleView = (TextView) findViewById(R.id.titleView);
        titleView.setText("查看详情");
        TextView checklistmeuntext = (TextView) findViewById(R.id.checklistmeuntext);
        checklistmeuntext.setTextSize(15f);
        checklistmeuntext.setVisibility(View.GONE);
        rectificationreason = (TextView) findViewById(R.id.rectificationreason);
        standarddel = (TextView) findViewById(R.id.standarddel);
        replydescription = (EditText) findViewById(R.id.replydescription);
        chagedOldRecycler = (RecyclerView) findViewById(R.id.chaged_old_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        // 设置 recyclerview 布局方式为横向布局
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        DividerItemDecoration divider1 = new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL);
        divider1.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.recycler_divider));
        chagedOldRecycler.setLayoutManager(layoutManager);
        chagedOldRecycler.addItemDecoration(divider1);
        mAdapter = new RectifierAdapter(mContext, photolist, new ArrayList<String>());
        chagedOldRecycler.setAdapter(mAdapter);
        //图片禁止下载（默认可以下载）
        mAdapter.setuploadstatus(false);
        /*w整改问题列表*/
        photoRecycler = (RecyclerView) findViewById(R.id.photo_recycler);
        adapter = new FiletypeAdapter(mContext, photoPaths);
        /*添加图片*/
        photoRecycler.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        photoRecycler.setAdapter(adapter);
        request();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checklistback:
                finish();
                break;
            case R.id.delete:
                AlertDialog alertDialog2 = new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("是否删除该项问题")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            //添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ToastUtils.showShortToast("确定");
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            //添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create();
                alertDialog2.show();
                break;
            case R.id.checklistmeun:
//                chaged();
                break;
            default:
                break;
        }
    }


    /*获取整改验证单数据详情*/
    private void request() {
        ChagedreplyUtils.getReplyFormDel(replyDelId, new ChagedreplyUtils.MapCallBack() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onsuccess(Map<String, Object> map) {
                ReplyBillBean billBean = (ReplyBillBean) map.get("bean");
                //整改部位
                String PartName = billBean.getRectificationPartName();
                String reason = billBean.getPartDetails();
                if (PartName == null || TextUtils.isEmpty(PartName)) {
                    if (reason == null) {
                        PartName = "无";
                    } else {
                        PartName = reason;
                    }
                } else {
                    if (reason != null && !TextUtils.isEmpty(reason)) {
                        PartName = PartName + ">>" + reason;
                    }
                }

                //存在问题
                String Reason = billBean.getRectificationReason();
                if (Reason == null) {
                    Reason = "";
                }
                if (TextUtils.isEmpty(PartName) || PartName == null) {
                    PartName = "无";
                }
                if (TextUtils.isEmpty(Reason) || Reason == null) {
                    Reason = "无";
                }
                content.setText("整改部位：" + PartName + "\n" +
                        "整改期限：" + billBean.getRectificationDate() + "\n" +
                        "整改扣总分分值：" + billBean.getStandardDelScore()
                );
                standarddel.setText(billBean.getStandardDelName());
                rectificationreason.setText("存在问题：" + Reason + "\n" + "整改前附件：");
                replydescription.setText(billBean.getReplyDescription());
                photolist.clear();
                //整改前图片集合
                ArrayList<photoBean> beforeFiles = new ArrayList<>();
                beforeFiles.addAll((ArrayList<photoBean>) map.get("beforeFiles"));
                //图片名称
                ArrayList<String> imagename = new ArrayList<>();
                for (int i = 0; i < beforeFiles.size(); i++) {
                    photolist.add(beforeFiles.get(i).getPhotopath());
                    imagename.add(beforeFiles.get(i).getPhotoname());
                }
                //图片适配器
                mAdapter.getData(photolist, imagename);
                photoPaths.clear();
                //整改后图片集合\
                ArrayList<photoBean> list = (ArrayList<photoBean>) map.get("afterFiles");
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        photoPaths.add(new FileTypeBean(list.get(i).getPhotoname(), list.get(i).getPhotopath(), list.get(i).getPhototype()));
                    }
                }
                adapter.getData(photoPaths);
                replydescription.setEnabled(false);
            }

            @Override
            public void onerror(String str) {
                Snackbar.make(chagedOldRecycler, str, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

}
