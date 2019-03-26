package com.example.administrator.newsdf.pzgc.activity.device;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;

import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.CheckPhotoAdapter;
import com.example.administrator.newsdf.pzgc.activity.device.utils.DeviceDetailsUtils;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.bean.VerificationBean;
import com.example.administrator.newsdf.pzgc.callback.DeviceDetailsCallBackUtils;
import com.example.administrator.newsdf.pzgc.callback.Networkinterface;
import com.example.baselibrary.view.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.PopCameraUtils;
import com.example.administrator.newsdf.pzgc.utils.TakePictureManager;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.okgo.OkGo;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lx
 * @description: 特种设备整改通知单验证界面
 * @date: 2018/12/4 0004 下午 4:42
 */
public class VerificationActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout validationStatus;
    private TextView categoryItem, checklistmeuntext, checkReplyFuj;
    private EditText replyDescription;
    private RecyclerView checkReplyRec;
    private CheckPhotoAdapter mAdapter;
    private int status;
    private Context mContext;
    private ArrayList<Audio> imagepath;
    private PopCameraUtils popcamerautils;
    private TakePictureManager takePictureManager;
    private static final int IMAGE_PICKER = 1011;
    private DeviceDetailsUtils detailsUtils;
    private int request = 101, request2 = 1004;
    private String checkId, id;
    //删除图片Id
    private ArrayList<String> detelefile = new ArrayList<>();
    private boolean lean = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_validation);
        Intent intent = getIntent();
        checkId = intent.getStringExtra("checkId");
        //判断是项目经理还是下发人验证
        lean = intent.getBooleanExtra("status", true);
        mContext = this;
        detailsUtils = new DeviceDetailsUtils();
        imagepath = new ArrayList<>();
        //初始化相机
        takePictureManager = new TakePictureManager(VerificationActivity.this);
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        checklistmeuntext = (TextView) findViewById(R.id.checklistmeuntext);
        checklistmeuntext.setText("保存");
        checklistmeuntext.setVisibility(View.VISIBLE);
        checklistmeuntext.setOnClickListener(this);
        checkReplyFuj = (TextView) findViewById(R.id.check_reply_fuj);
        //标题
        TextView title = (TextView) findViewById(R.id.titleView);
        title.setText("验证");
        //验证状态
        validationStatus = (LinearLayout) findViewById(R.id.validation_status);
        validationStatus.setOnClickListener(this);
        //验证状态提示
        categoryItem = (TextView) findViewById(R.id.category_item);
        //验证描述
        replyDescription = (EditText) findViewById(R.id.replyDescription);
        //验证附件
        checkReplyRec = (RecyclerView) findViewById(R.id.check_reply_rec);
        checkReplyRec.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        mAdapter = new CheckPhotoAdapter(mContext, imagepath, "device", true);
        checkReplyRec.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CheckPhotoAdapter.OnItemClickListener() {
            @Override
            public void addlick(View view, int position) {
                //展示弹出窗
                //实例化弹出框
                popcamerautils = new PopCameraUtils();
                popcamerautils.showPopwindow((Activity) mContext, new PopCameraUtils.CameraCallback() {
                    @Override
                    public void oncamera() {
                        //拍照方式
                        takePictureManager.startTakeWayByCarema();
                        takePictureManager.setTakePictureCallBackListener(new TakePictureManager.takePictureCallBackListener() {
                            @Override
                            public void successful(boolean isTailor, File outFile, final Uri filePath) {
                                //实例化Tiny.FileCompressOptions，并设置quality的数值（quality改变图片压缩质量）
                                Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                                options.quality = 95;
                                Tiny.getInstance().source(outFile).asFile().withOptions(options).compress(new FileCallback() {
                                    @Override
                                    public void callback(boolean isSuccess, String outfile) {
                                        imagepath.add(new Audio(outfile, ""));
                                        mAdapter.getData(imagepath, true);
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
            public void deleteClick(View view, int position) {
                //获取Id
                String content = imagepath.get(position).getContent();
                //判断是否为空
                if (!content.isEmpty()) {
                    //不为空，添加到删除集合
                    detelefile.add(content);
                }
                //从原有集合中删除
                imagepath.remove(position);
                //更新界面
                mAdapter.getData(imagepath, true);
            }
        });
        if (!lean) {
            //如果是项目经理验证，不用传附件
            checklistmeuntext.setText("提交");
            checkReplyFuj.setVisibility(View.GONE);
            checkReplyRec.setVisibility(View.GONE);;
        }
        request();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.validation_status:
                AlertDialog dialog = new AlertDialog.Builder(this).setTitle("是否验证通过")
                        .setNegativeButton("打回", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                status = 0;
                                categoryItem.setText("打回");
                                categoryItem.setTextColor(Color.parseColor("#FE0000"));
                            }
                        }).setPositiveButton("通过", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //处理确认按钮的点击事件
                                categoryItem.setText("通过");
                                categoryItem.setTextColor(Color.parseColor("#28c26A"));
                                status = 1;
                            }
                        })
                        .create();
                dialog.show();
                break;
            case R.id.checklistmeuntext:
                String status = categoryItem.getText().toString();
                if (!status.isEmpty()) {
                    save();
                } else {
                    ToastUtils.showLongToast("请确认是否验证通过");
                }
                break;
            default:
                break;
        }
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     * @ activity回调
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == request) {
            //  调用相机的回调
            try {
                takePictureManager.attachToActivityForResult(requestCode, resultCode, data);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else if (requestCode == IMAGE_PICKER && resultCode == request2) {
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
                                imagepath.add(new Audio(outfile, ""));
                                mAdapter.getData(imagepath, true);
                            }
                        });
                    } else {
                        ToastUtils.showLongToast("请检查上传的图片是否损坏");
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popcamerautils != null) {
            popcamerautils = null;
        }
    }

    /**
     * @内容: 获取当前验证数据
     * @author lx
     * @date: 2018/12/14 0014 上午 11:39
     */
    public void request() {
        detailsUtils.getvalidatedata(checkId, new Networkinterface() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onsuccess(Map<String, Object> map) {
                if (map.size() > 0) {
                    //界面数据
                    VerificationBean bean = (VerificationBean) map.get("data");
                    //拿到图片
                    imagepath = (ArrayList<Audio>) map.get("file");
                    //更新图片
                    mAdapter.getData(imagepath, true);
                    //验证单Id
                    id = bean.getId();
                    //验证结果0打回1通过
                    int validate = bean.getValidate();
                    if (validate == 0) {
                        categoryItem.setText("打回");
                        categoryItem.setTextColor(Color.parseColor("#FE0000"));
                    } else {
                        categoryItem.setText("通过");
                        categoryItem.setTextColor(Color.parseColor("#28c26A"));
                    }
                    //意见
                    replyDescription.setText(bean.getView());
                }
            }
        });
    }

    /**
     * @内容: 保存修改
     * @author lx
     * @date: 2018/12/14 0014 下午 1:53
     */
    public void save() {
        //意见
        String opinion = replyDescription.getText().toString();
        Map<String, String> map = new HashMap<>();
        map.put("checkId", checkId);
        map.put("validate", status + "");
        //是否是新增
        if (id != null) {
            map.put("id", id);
        }

        //意见是否为空
        if (!opinion.isEmpty()) {
            map.put("view", opinion);
        }
        //是否通过

        //添加图片
        ArrayList<File> file = new ArrayList<>();
        for (int i = 0; i < imagepath.size(); i++) {
            String str = imagepath.get(i).getContent();
            //判断是否有Id
            if (str.isEmpty()) {
                //没有Id，添加为新图片
                File file1 = new File(imagepath.get(i).getName());
                file.add(file1);
            }
        }
        //判断是否有删除图片
        if (detelefile.size() > 0) {
            String deleteFileIds = Dates.listToStrings(detelefile);
            map.put("deleteFileIds", deleteFileIds);
        }
        Dates.getDialogs(this, "提交数据中,,,");
        detailsUtils.saveValideByApp(map, file, new Networkinterface() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                if (map.containsKey("error")) {

                } else {
                    //不包含
                    DeviceDetailsCallBackUtils.CallBackMethod();
                    finish();
                }
            }
        });
    }

    //退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //取消当前界面的请求
            Dates.disDialog();
            OkGo.getInstance().cancelAll();
            finish();
            return true;
        }
        return true;
    }
}
