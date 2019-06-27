package com.example.administrator.newsdf.pzgc.activity.device;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.adapter.CorrectReplyAdapter;
import com.example.administrator.newsdf.pzgc.activity.device.utils.DeviceDetailsUtils;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.bean.CorrectReplyBean;
import com.example.administrator.newsdf.pzgc.callback.DeviceDetailsCallBackUtils;
import com.example.administrator.newsdf.pzgc.callback.Networkinterface;
import com.example.baselibrary.base.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.baselibrary.utils.log.LogUtil;
import com.example.administrator.newsdf.pzgc.utils.PopCameraUtils;
import com.example.administrator.newsdf.pzgc.utils.TakePictureManager;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ author lx
 * @ Created by: 2018/12/3 0003.
 * @ description: 整改回复（详情页编辑）
 * @ Activity：
 */

public class CorrectReplyActivity extends BaseActivity {
    private CorrectReplyAdapter mAdapter;
    private ArrayList<CorrectReplyBean> list;
    private Context mContext;
    private TextView checklistmeuntext;
    private RecyclerView devicedetailsRecycler;
    private LinearLayout deviceDetailsFunction;
    private PopCameraUtils PopCameraUtils;
    private static final int IMAGE_PICKER = 1011;
    private TakePictureManager takePictureManager;
    private int pos = 0;
    public static final String camera = "相机";
    private DeviceDetailsUtils detailsUtils;
    private String checkId;
    private ArrayList<String> deleteList;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details);

        mContext = this;
        Intent intent = getIntent();
        checkId = intent.getStringExtra("id");
        detailsUtils = new DeviceDetailsUtils();
        list = new ArrayList<>();
        deleteList = new ArrayList<>();
        //相机帮助类初始化
        takePictureManager = new TakePictureManager(CorrectReplyActivity.this);
        //数据填完后保存按钮
        checklistmeuntext = (TextView) findViewById(R.id.checklistmeuntext);
        checklistmeuntext.setText("保存");
        checklistmeuntext.setTextSize(15.0f);
        //显示安按钮
        checklistmeuntext.setVisibility(View.VISIBLE);
        TextView titleView = (TextView) findViewById(R.id.titleView);
        titleView.setText("整改回复");
        //返回
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        deviceDetailsFunction = (LinearLayout) findViewById(R.id.device_details_function);
        devicedetailsRecycler = (RecyclerView) findViewById(R.id.device_details_recycler);
        //隐藏控件，复用布局，这个界面不需要
        deviceDetailsFunction.setVisibility(View.GONE);
        //设置列表样式
        devicedetailsRecycler.setLayoutManager(new LinearLayoutManager(this));
        //实例化适配器
        mAdapter = new CorrectReplyAdapter(list, mContext);
        //给recyclerview设置适配器
        devicedetailsRecycler.setAdapter(mAdapter);
        //设置适配器点击事件
        mAdapter.setOnItemClickListener(new CorrectReplyAdapter.OnItemClickListener() {
            //调用相机、相册
            @Override
            public void addlick(final int position, final int adapterposition) {
                //记录点击的item位置，在从相册返回时使用（onActivityResult）
                pos = position;
                //相机相册选择弹窗帮助类
                PopCameraUtils = new PopCameraUtils();
                //展示弹出窗
                PopCameraUtils.showPopwindow((Activity) mContext, new PopCameraUtils.CameraCallback() {
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
                                        //获取到指定位置的图片集合
                                        ArrayList<Audio> tinglist = list.get(pos).getList();
                                        //新增数据
                                        tinglist.add(new Audio(outfile, ""));
                                        //刷新数据，并指定刷新的位置
                                        mAdapter.setupdate(list, pos);
                                        //删除原图
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

            //position 列表的位置，， 图片的位置 adapterposition
            //删除图片
            @Override
            public void deleteClick(int position, int adapterposition) {
                //删除图片
                //取出删除图片的集合
                ArrayList<Audio> tinglist = list.get(position).getList();
                //获取content（content为图片Id）
                String content = tinglist.get(adapterposition).getContent();
                //判断是否为null
                if (!content.isEmpty()) {
                    //不为null，添加到集合
                    deleteList.add(content);
                }
                tinglist.remove(adapterposition);
                //刷新数据，并指定刷新的位置
                mAdapter.setupdate(list, position);
            }
        });
        //网络请求
        detailsUtils.problemitemlist(checkId, new Networkinterface() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                list.addAll((Collection<? extends CorrectReplyBean>) map.get("data"));
                mAdapter.setNewData(list);
            }
        });
        checklistmeuntext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }


    /**
     * @param requestCode
     * @param resultCode
     * @param data        Activity的回调
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            //返回的数据不为空
            try {
                takePictureManager.attachToActivityForResult(requestCode, resultCode, data);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else if (requestCode == 1011 && resultCode == 1004) {
            try {
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
                                    //获取到指定位置的图片集合
                                    ArrayList<Audio> tinglist = list.get(pos).getList();
                                    //新增数据
                                    tinglist.add(new Audio(outfile, ""));
                                    //刷新数据，并指定刷新的位置
                                    mAdapter.setupdate(list, pos);
                                }
                            });
                        } else {
                            ToastUtils.showLongToast("请检查上传的图片是否损坏");
                        }
                    }
                }
            } catch (Exception e) {

            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (PopCameraUtils != null) {
            PopCameraUtils = null;
        }

    }

    /**
     * @内容: 保存界面数据
     * @author lx
     * @date: 2018/12/14 0014 下午 4:09
     */
    private void save() {
        //回复
        List<Map<String, Object>> mDatareply = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> reply = new HashMap<>();
            //检查项ID
            String detailsId = list.get(i).getBean().getId();
            //
            String replyId = list.get(i).getBean().getReplyId();
            //回复内容
            reply.put("reply", list.get(i).getBean().getReply());
            //单据传递过来来的ID（单据Id）
            reply.put("id", replyId);
            reply.put("checkId", checkId);
            reply.put("detailsId", detailsId);
            mDatareply.add(reply);
        }
        //图片
        Map<String, List<File>> file = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            //检查项ID
            String detailsId = list.get(i).getBean().getId();
            //取出集合中的图片
            ArrayList<Audio> filelist = list.get(i).getList();
            ArrayList<File> mdataFile = new ArrayList<>();
            for (int j = 0; j < filelist.size(); j++) {
                //取出content，判断是否为空
                String content = filelist.get(j).getContent();
                if (content.isEmpty()) {
                    //为空，为新增的图片
                    mdataFile.add(new File(filelist.get(j).getName()));
                }
            }
            LogUtil.i("list", mdataFile.size());
            file.put("file-" + detailsId, mdataFile);
        }
        JSONArray replys = new JSONArray(mDatareply);
        Dates.getDialog(this, "提交数据中...");
        detailsUtils.savereplyofsec(file, replys.toString(), Dates.listToStrings(deleteList), new Networkinterface() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                DeviceDetailsCallBackUtils.CallBackMethod();
                finish();
            }
        });
    }
}
