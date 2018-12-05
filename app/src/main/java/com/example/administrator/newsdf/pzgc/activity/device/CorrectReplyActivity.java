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
import android.widget.Toast;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.CorrectReplyAdapter;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.bean.CorrectReplyBean;
import com.example.administrator.newsdf.pzgc.bean.FileTypeBean;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details);
        mContext = this;
        //相机相册选择弹窗帮助类
        PopCameraUtils = new PopCameraUtils();
        //相机帮助类初始化
        takePictureManager = new TakePictureManager(CorrectReplyActivity.this);
        checklistmeuntext = (TextView) findViewById(R.id.checklistmeuntext);
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        deviceDetailsFunction = (LinearLayout) findViewById(R.id.device_details_function);
        devicedetailsRecycler = (RecyclerView) findViewById(R.id.device_details_recycler);
        deviceDetailsFunction.setVisibility(View.GONE);

        checklistmeuntext.setText("确定");
        checklistmeuntext.setVisibility(View.VISIBLE);
        devicedetailsRecycler.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        ArrayList<FileTypeBean> data = new ArrayList<>();
        data.add(new FileTypeBean("测试图片.jpg", "http://attach.bbs.miui.com/forum/201807/17/154537ujxutueesyj3mzt0.jpg", "jpg"));
        data.add(new FileTypeBean("测试图片.jpg", "http://img1.imgtn.bdimg.com/it/u=3099290229,4242430518&fm=27&gp=0.jpg", "jpg"));
        data.add(new FileTypeBean("测试图片.jpg", "http://i2.hdslb.com/bfs/archive/102d43ce9564fb9e27f699c02abbf3081f3ba197.jpg", "jpg"));
        data.add(new FileTypeBean("测试图片.jpg", "http://img.mp.itc.cn/upload/20170804/bb02fbb3accc4b95b857834228f3b137_th.jpg", "jpg"));
        data.add(new FileTypeBean("测试图片.jpg", "http://imgfs.oppo.cn/uploads/thread/attachment/2017/10/02/15069522405715.jpg", "jpg"));
        for (int i = 0; i < 2; i++) {
            ArrayList<Audio> audios = new ArrayList<>();
            audios.add(new Audio("http://attach.bbs.miui.com/forum/201807/17/154537ujxutueesyj3mzt0.jpg", "测试图片"));
            audios.add(new Audio("http://img1.imgtn.bdimg.com/it/u=3099290229,4242430518&fm=27&gp=0.jpg", "测试图片"));
            list.add(new CorrectReplyBean("测试违反标准", "B", "2018=12-01", "设备存放不合格", "按时整改", audios, data));
        }
        mAdapter = new CorrectReplyAdapter(list, mContext);
        devicedetailsRecycler.setAdapter(mAdapter);
        //设置适配器点击事件
        mAdapter.setOnItemClickListener(new CorrectReplyAdapter.OnItemClickListener() {
            //调用相机相册
            @Override
            public void addlick(final int position, final int adapterposition) {
                //记录点击的item位置，在从相册返回时使用（onActivityResult）
                pos = position;
                //展示弹出窗
                PopCameraUtils.showPopwindow((Activity) mContext, new PopCameraUtils.CameraCallback() {
                    @Override
                    public void onComple(String string) {
                        if ("相机".equals(string)) {
                            //拍照方式
                            takePictureManager.startTakeWayByCarema();
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
                                            tinglist.add(new Audio(outfile, "测试图片"));
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
                        } else {
                            //相册
                            Intent intent = new Intent(mContext, ImageGridActivity.class);
                            startActivityForResult(intent, IMAGE_PICKER);
                        }
                    }
                });
            }

            //删除图片
            @Override
            public void deleteClick(int position, int adapterposition) {
                //删除图片
                //获取到指定位置的图片集合
                ArrayList<Audio> tinglist = list.get(position).getList();
                //展出指定位置文件
                tinglist.remove(adapterposition);
//                //将集合中的图片更新
//                list.get(position).setList(tinglist);
                //刷新数据，并指定刷新的位置
                mAdapter.setupdate(list, position);
            }
        });
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
                                //获取到指定位置的图片集合
                                ArrayList<Audio> tinglist = list.get(pos).getList();
                                //新增数据
                                tinglist.add(new Audio(outfile, "测试图片"));
//                                            //将集合中的图片更新
//                                            list.get(position).setList(tinglist);
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
}
