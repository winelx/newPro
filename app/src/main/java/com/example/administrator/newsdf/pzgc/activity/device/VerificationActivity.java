package com.example.administrator.newsdf.pzgc.activity.device;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.CheckPhotoAdapter;
import com.example.administrator.newsdf.pzgc.Adapter.CorrectReplyAdapter;
import com.example.administrator.newsdf.pzgc.Adapter.PhotoAdapter;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.PopCameraUtils;
import com.example.administrator.newsdf.pzgc.utils.TakePictureManager;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 * @description: 特种设备整改通知单验证界面
 * @author lx
 * @date: 2018/12/4 0004 下午 4:42
*/
public class VerificationActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout validation_status;
    private TextView category_item, checklistmeuntext;
    private EditText replyDescription;
    private RecyclerView check_reply_rec;
    private CheckPhotoAdapter mAdapter;
    private String status = null;
    private Context mContext;
    private ArrayList<Audio> imagepath;
    private PopCameraUtils PopCameraUtils;
    private TakePictureManager takePictureManager;
    private static final int IMAGE_PICKER = 1011;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_validation);
        mContext = this;
        imagepath = new ArrayList<>();
        //实例化弹出框
        PopCameraUtils = new PopCameraUtils();
        //初始化相机
        takePictureManager = new TakePictureManager(VerificationActivity.this);
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        checklistmeuntext = (TextView) findViewById(R.id.checklistmeuntext);
        checklistmeuntext.setText("确定");
        checklistmeuntext.setOnClickListener(this);
        //标题
        TextView title = (TextView) findViewById(R.id.titleView);
        title.setText("验证");
        //验证状态
        validation_status = (LinearLayout) findViewById(R.id.validation_status);
        validation_status.setOnClickListener(this);
        //验证状态提示
        category_item = (TextView) findViewById(R.id.category_item);
        //验证描述
        replyDescription = (EditText) findViewById(R.id.replyDescription);
        //验证附件
        check_reply_rec = (RecyclerView) findViewById(R.id.check_reply_rec);
        check_reply_rec.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        mAdapter = new CheckPhotoAdapter(mContext, imagepath, "device", false);
        check_reply_rec.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CheckPhotoAdapter.OnItemClickListener() {
            @Override
            public void addlick(View view, int position) {
                //展示弹出窗
                PopCameraUtils.showPopwindow((Activity) mContext, new PopCameraUtils.CameraCallback() {
                    @Override
                    public void onComple(String string) {
                        if ("相机".equals(string)) {
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
                        } else {
                            //相册
                            Intent intent = new Intent(mContext, ImageGridActivity.class);
                            startActivityForResult(intent, IMAGE_PICKER);
                        }
                    }
                });
            }

            @Override
            public void deleteClick(View view, int position) {
                imagepath.remove(position);
                mAdapter.getData(imagepath, true);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.validation_status:
                AlertDialog dialog = new AlertDialog.Builder(this).setTitle("是否验证通过")
                        .setNegativeButton("打回", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                status = "2";
                                category_item.setText("打回");
                                category_item.setTextColor(Color.parseColor("#FE0000"));
                            }
                        }).setPositiveButton("通过", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //处理确认按钮的点击事件
                                category_item.setText("通过");
                                category_item.setTextColor(Color.parseColor("#28c26A"));
                                status = "1";
                            }
                        })
                        .create();
                dialog.show();
                break;
            case R.id.checklistmeuntext:
                ToastUtils.showLongToast("确定");
                break;
            default:
                break;
        }
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     * @activity回调
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            //  调用相机的回调
            if (data != null) {
                try {
                    takePictureManager.attachToActivityForResult(requestCode, resultCode, data);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 1011 && resultCode == 1004) {
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

}
