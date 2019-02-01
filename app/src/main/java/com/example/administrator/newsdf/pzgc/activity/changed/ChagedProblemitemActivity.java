package com.example.administrator.newsdf.pzgc.activity.changed;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.BasePhotoAdapter;
import com.example.administrator.newsdf.pzgc.activity.mine.Text;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.photopicker.PhotoPreview;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
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
import java.util.List;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/1 0001}
 * 描述：整改通知单问题项
 * {@link }
 */
public class ChagedProblemitemActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView photo_recycler;
    private BasePhotoAdapter adapter;
    private TextView menutext, com_title;
    private Context mContext;
    private ArrayList<photoBean> photolist;
    private PopCameraUtils PopCameraUtils;
    private TakePictureManager takePictureManager;
    private static final int IMAGE_PICKER = 1011;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_problemitem);
        mContext = this;
        photolist = new ArrayList<>();
        //相机帮助类初始化
        takePictureManager = new TakePictureManager(ChagedProblemitemActivity.this);
        PopCameraUtils = new PopCameraUtils();
        findViewById(R.id.com_back).setOnClickListener(this);
        findViewById(R.id.toolbar_menu).setOnClickListener(this);
        menutext = (TextView) findViewById(R.id.com_button);
        menutext.setText("保存");
        com_title = (TextView) findViewById(R.id.com_title);
        com_title.setText("问题项");
        photo_recycler = (RecyclerView) findViewById(R.id.photo_recycler);

        adapter = new BasePhotoAdapter(mContext, photolist);
        photo_recycler.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        DividerItemDecoration divider1 = new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL);
        photo_recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new BasePhotoAdapter.OnItemClickListener() {
            @Override
            public void addlick(View view, int position) {
                //添加图片
                ToastUtils.showShortToast("添加图片");
                //相机相册选择弹窗帮助类
                //展示弹出窗
                PopCameraUtils.showPopwindow(ChagedProblemitemActivity.this, new PopCameraUtils.CameraCallback() {
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
                                        photolist.add(new photoBean(outfile, "", ""));
                                        adapter.getData(photolist);
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
                //删除图片
                Dates.compressPixel(photolist.get(position).getPhotopath());
                photolist.remove(position);
                adapter.getData(photolist);
            }

            @Override
            public void seePhoto(int position) {
                ArrayList<String> imagepaths = new ArrayList<>();
                for (int i = 0; i < photolist.size(); i++) {
                    imagepaths.add(photolist.get(i).getPhotopath());
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                finish();
                break;
            case R.id.toolbar_menu:
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
                                photolist.add(new photoBean(outfile, "", ""));
                                adapter.getData(photolist);
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
