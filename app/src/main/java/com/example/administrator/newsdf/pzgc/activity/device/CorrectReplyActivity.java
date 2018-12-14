package com.example.administrator.newsdf.pzgc.activity.device;

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
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.CorrectReplyAdapter;
import com.example.administrator.newsdf.pzgc.activity.device.utils.DeviceDetailsUtils;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.bean.CorrectReplyBean;
import com.example.administrator.newsdf.pzgc.bean.FileTypeBean;
import com.example.administrator.newsdf.pzgc.callback.Networkinterface;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.LogUtil;
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
 * @author lx
 * @Created by: 2018/12/3 0003.
 * @description:整改回复（详情页编辑）
 * @Activity：
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
        //相机相册选择弹窗帮助类
        PopCameraUtils = new PopCameraUtils();
        //相机帮助类初始化
        takePictureManager = new TakePictureManager(CorrectReplyActivity.this);
        //数据填完后保存按钮
        checklistmeuntext = (TextView) findViewById(R.id.checklistmeuntext);
        checklistmeuntext.setText("确定");
        //显示安按钮
        checklistmeuntext.setVisibility(View.VISIBLE);
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
                //展示弹出窗
                PopCameraUtils.showPopwindow((Activity) mContext, new PopCameraUtils.CameraCallback() {
                    @Override
                    public void oncamera() {
                        // 开始拍照
                        takePictureManager.startTakeWayByCarema();
                        //数据返回
                        takePictureManager.setTakePictureCallBackListener(new TakePictureManager.takePictureCallBackListener() {
                            @Override
                            public void successful(boolean isTailor, File outFile, Uri filePath) {
                                //实例化Tiny.FileCompressOptions，并设置quality的数值（quality改变图片压缩质量）
                                Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                                options.quality = 95;
                                Tiny.getInstance().source(outFile).asFile().withOptions(options).compress(new FileCallback() {
                                    @Override
                                    public void callback(boolean isSuccess, String outfile) {
                                        //获取到指定位置的图片集合
                                        ArrayList<Audio> tinglist = list.get(position).getList();
                                        //新增数据
                                        tinglist.add(new Audio(outfile, ""));
//                                            //将集合中的图片更新
                                        list.get(position).setList(tinglist);
                                        //刷新数据，并指定刷新的位置
                                        mAdapter.setupdate(list, position);
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

            //删除图片
            @Override
            public void deleteClick(int position, int adapterposition) {
                //删除图片
                //获取到指定位置的图片集合
                String itemId = list.get(position).getBean().getId();
                //取出删除图片的集合
                ArrayList<Audio> tinglist = list.get(position).getList();
                //获取content（content为图片Id）
                String content = tinglist.get(position).getContent();
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
            //  调用相机的回调
            if (data != null) {
                //返回的数据不为空
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
//        List<Map<String, Object>> mDatafile = new ArrayList<>();
//        List<Map<String, Object>> mDatareply = new ArrayList<>();
//        Map<String, Object> map = new HashMap<>();
//        //回复
//        Map<String, Object> replymap = new HashMap<>();
//        //从列表回复集合
//        Map<Integer, String> replulist = mAdapter.getReplylist();
//        for (int i = 0; i < list.size(); i++) {
//            //获取到id,将id作为key
//            String itemId = list.get(i).getBean().getReplyId();
//            //整改描述    //添加到map
//            replymap.put(itemId, replulist.get(i));
//            //将map添加到集合
//            mDatareply.add(replymap);
//            //整改图片
//            ArrayList<Audio> FileType = list.get(i).getList();
//            if (FileType.size() > 0) {
//                ArrayList<File> files = new ArrayList<>();
//                for (int j = 0; j < FileType.size(); j++) {
//                    String path = FileType.get(j).getName();
//                    File file = new File(path);
//                    files.add(file);
//                }
//                //添加到map
//                map.put(itemId, files);
//            }
//            //添加到集合
//            mDatafile.add(map);
//        }

        //回复
        List<Map<String, Object>> mDatareply = new ArrayList<>();
        //从列表回复集合
        Map<Integer, String> replulist = mAdapter.getReplylist();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> reply = new HashMap<>();
            //检查项ID
            String detailsId = list.get(i).getBean().getId();
            //
            String replyId = list.get(i).getBean().getReplyId();
            //回复内容
            reply.put("reply", replulist.get(i));
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
        detailsUtils.savereplyofsec(file, replys.toString(), Dates.listToString(deleteList), new Networkinterface() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                ToastUtils.showLongToast("编辑成功");
                finish();
            }
        });
    }
}
