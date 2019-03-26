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
public class ChagedReplyBillActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private RecyclerView chagedOldRecycler, photoRecycler;
    private RectifierAdapter mAdapter;
    private BasePhotoAdapter adapter;
    private ArrayList<photoBean> photoPaths;
    private ArrayList<String> photolist;
    private EditText replydescription;
    private TextView content, rectificationreason, standarddel, delete;
    private PopCameraUtils popcamerautils;
    private TakePictureManager takePictureManager;
    private static final int IMAGE_PICKER = 1011;
    private String replyId, replyDelId, optionType;
    private ArrayList<String> deletelist = new ArrayList<>();
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
        popcamerautils = new PopCameraUtils();
        takePictureManager = new TakePictureManager(ChagedReplyBillActivity.this);
        content = (TextView) findViewById(R.id.content);
        delete = (TextView) findViewById(R.id.delete);
        delete.setOnClickListener(this);
        findViewById(R.id.checklistback).setOnClickListener(this);
        findViewById(R.id.checklistmeun).setOnClickListener(this);
        TextView titleView = (TextView) findViewById(R.id.titleView);
        titleView.setText("整改回复");
        TextView checklistmeuntext = (TextView) findViewById(R.id.checklistmeuntext);
        checklistmeuntext.setText("保存");
        checklistmeuntext.setTextSize(15f);
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
        adapter = new BasePhotoAdapter(mContext, photoPaths);
        /*添加图片*/
        photoRecycler.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        photoRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new BasePhotoAdapter.OnItemClickListener() {
            @Override
            public void addlick(View view, int position) {
                //添加图片
                //相机相册选择弹窗帮助类
                //展示弹出窗
                popcamerautils.showPopwindow(ChagedReplyBillActivity.this, new PopCameraUtils.CameraCallback() {
                    @Override
                    public void oncamera() {
                        // 开始拍照
                        takePictureManager.startTakeWayByCarema();
                        //数据返回
                        takePictureManager.setTakePictureCallBackListener(new TakePictureManager.takePictureCallBackListener() {
                            @Override
                            public void successful(boolean isTailor, final File outFile, Uri filePath) {
                                //实例化Tiny.FileCompressOptions，并设置quality的数值（quality改变图片压缩质量）
                                Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                                options.quality = 95;
                                Tiny.getInstance().source(outFile).asFile().withOptions(options).compress(new FileCallback() {
                                    @Override
                                    public void callback(boolean isSuccess, String outfile) {
                                        //删除原图
                                        photoPaths.add(new photoBean(outfile, "", ""));
                                        adapter.getData(photoPaths);
                                        Dates.deleteFile(outFile);
                                    }
                                });
                            }

                            @Override
                            public void failed(int errorCode, List<String> deniedPermissions) {
                                ToastUtils.showLongToast("相机权限被禁用，无法使用相机");
                            }
                        });
                    }

                    @Override
                    public void onalbum() {
                        //相册
                        Intent intent = new Intent(mContext, ImageGridActivity.class);
                        startActivityForResult(intent, IMAGE_PICKER);
                    }
                });
            }

            @Override
            public void delete(int position) {
                //删除图片Id集合
                deletelist.add(photoPaths.get(position).getPhototype());
                //删除图片
                Dates.compressPixel(photoPaths.get(position).getPhotopath());
                photoPaths.remove(position);
                adapter.getData(photoPaths);
            }

            @Override
            public void seePhoto(int position) {
                ArrayList<String> imagepaths = new ArrayList<>();
                for (int i = 0; i < photoPaths.size(); i++) {
                    imagepaths.add(photoPaths.get(i).getPhotopath());
                }
                //查看图片
                PhotoPreview.builder()
                        //图片路径
                        .setPhotos(imagepaths)
                        //图片位置
                        .setCurrentItem(position)
                        //删除
                        .setShowDeleteButton(false)
                        //下载
                        .setShowUpLoadeButton(false)
                        // 图片名称
                        .setImagePath(new ArrayList<String>())
                        .start((Activity) mContext);
            }
        });
        if (lean) {
            delete.setVisibility(View.GONE);
        }
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
                                dialogInterface.dismiss();
                                delete();
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
                chaged();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == -1) {
            //  调用相机的回调
            try {
                takePictureManager.attachToActivityForResult(requestCode, resultCode, data);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else if (requestCode == IMAGE_PICKER && resultCode == 1004) {
            if (data != null) {
                //获取返回的图片路径集合
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for (int i = 0; i < images.size(); i++) {
                    double mdouble = Dates.getDirSize(new File(images.get(i).path));
                    if (mdouble != 0.0) {
                        //实例化Tiny.FileCompressOptions，并设置quality的数值（quality改变图片压缩质量）
                        Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                        options.quality = 95;
                        Tiny.getInstance().source(images.get(i).path).asFile().withOptions(options).compress(new FileCallback() {
                            @Override
                            public void callback(boolean isSuccess, String outfile) {
                                //删除原图
                                photoPaths.add(new photoBean(outfile, "", ""));
                                adapter.getData(photoPaths);
                            }
                        });
                    } else {
                        ToastUtils.showLongToast("请检查上传的图片是否损坏");
                    }
                }
            }
        }
    }

    /*获取整改验证单数据详情*/
    private void request() {
        ChagedreplyUtils.getReplyFormDel(replyDelId, new ChagedreplyUtils.MapCallBack() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onsuccess(Map<String, Object> map) {
                ReplyBillBean billBean = (ReplyBillBean) map.get("bean");
                optionType = billBean.getIsReply();
                //整改部位
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
                        "整改期限：" + billBean.getRectificationDate());
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
                //整改后图片集合
                photoPaths.addAll((ArrayList<photoBean>) map.get("afterFiles"));
                adapter.getData(photoPaths);
            }

            @Override
            public void onerror(String str) {
                Snackbar.make(chagedOldRecycler, str, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    /*创建、编辑回复单*/
    public void chaged() {
        String editext = replydescription.getText().toString();
        if (!editext.isEmpty()) {
            Dates.getDialog(this, "提交数据中...");
            ArrayList<File> fileList = new ArrayList<>();
            for (int i = 0; i < photoPaths.size(); i++) {
                String name = photoPaths.get(i).getPhotoname();
                if (name.isEmpty()) {
                    fileList.add(new File(photoPaths.get(i).getPhotopath()));
                }
            }
            Map<String, String> map = new HashMap<>();
            map.put("id", replyDelId);
            map.put("replyId", replyId);
            map.put("deleteFileIds", Dates.listToStrings(deletelist));
            map.put("replyDescription", editext);
            map.put("optionType", optionType);
            ChagedreplyUtils.editReplyFormDel(map, fileList, new ChagedreplyUtils.ObjectCallBacks() {
                @Override
                public void onsuccess(String string) {
                    ToastUtils.showShortToastCenter("保存成功");
                    Dates.disDialog();
                    try {
                        OgranCallbackUtils.removeCallBackMethod();
                        NetworkinterfaceCallbackUtils.Refresh("reply");
                    } catch (Exception e) {
                    }
                    finish();
                }

                @Override
                public void onerror(String string) {
                    Dates.disDialog();
                    ToastUtils.showsnackbar(rectificationreason, string);
                }
            });
        } else {
            ToastUtils.showShortToastCenter("整改描述不能为空");
        }
    }

    /*删除该项单据*/
    public void delete() {
        ChagedreplyUtils.deleteReplyDel(replyDelId, replyId, new ChagedreplyUtils.ObjectCallBacks() {
            @Override
            public void onsuccess(String string) {
                try {
                    //删除单据回调
                    OgranCallbackUtils.removeCallBackMethod();
                    NetworkinterfaceCallbackUtils.Refresh("reply");
                } catch (Exception e) {
                }
                finish();
            }

            @Override
            public void onerror(String string) {
                Snackbar.make(chagedOldRecycler, string, Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
