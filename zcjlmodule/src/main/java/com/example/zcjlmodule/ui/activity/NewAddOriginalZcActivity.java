package com.example.zcjlmodule.ui.activity;

import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;

import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.View;

import android.widget.TextView;


import com.example.zcmodule.R;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import measure.jjxx.com.baselibrary.adapter.PhotosAdapter;
import measure.jjxx.com.baselibrary.base.BaseActivity;
import measure.jjxx.com.baselibrary.utils.CameraUtils;
import measure.jjxx.com.baselibrary.utils.FileUtils;
import measure.jjxx.com.baselibrary.utils.TakePictureManager;


/**
 * description: 新增原始勘丈表
 *
 * @author lx
 *         date: 2018/10/16 0016 下午 2:29
 *         跳转界面：OriginalZcActivity
 */
public class NewAddOriginalZcActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;
    private RecyclerView photrecycler;
    private PhotosAdapter mPhotosAdapter;
    private ArrayList<String> list;
    private TakePictureManager takePictureManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_add_original_zc);
        findViewById(R.id.toolbar_icon_back).setOnClickListener(this);
        title = (TextView) findViewById(R.id.toolbar_icon_title);
        title.setText("新增原始勘丈表");
        list = new ArrayList<>();
        init();
    }

    private void init() {
        photrecycler = (RecyclerView) findViewById(R.id.newaddoriginal_recycler);
        mPhotosAdapter = new PhotosAdapter(this, list);
        photrecycler.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        photrecycler.setAdapter(mPhotosAdapter);
        //点击事件
        mPhotosAdapter.setOnItemClickListener(new PhotosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CameraUtils cameraUtils = new CameraUtils();
                cameraUtils.showPopwindow(NewAddOriginalZcActivity.this, new CameraUtils.CameraCallback() {
                    @Override
                    public void onComple(String string) {
                        if ("相机".equals(string)) {
                            takePictureManager = new TakePictureManager(NewAddOriginalZcActivity.this);
                            //拍照方式
                            takePictureManager.startTakeWayByCarema();
                            //回调
                            takePictureManager.setTakePictureCallBackListener(new TakePictureManager.takePictureCallBackListener() {
                                //成功拿到图片,isTailor 是否裁剪？ ,outFile 拿到的文件 ,filePath拿到的URl
                                @Override
                                public void successful(boolean isTailor, final File outFile, Uri filePath) {
                                    //压缩图片
                                    Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                                    Tiny.getInstance().source(outFile).asFile().withOptions(options).compress(new FileCallback() {
                                        @Override
                                        public void callback(boolean isSuccess, String outfile) {
                                            //添加进集合
                                            list.add(outfile);
                                            mPhotosAdapter.getData(list);
                                            try {
                                                FileUtils.delete(outFile);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                                }

                                //失败回调
                                @Override
                                public void failed(int errorCode, List<String> deniedPermissions) {
                                    Log.e("==w", deniedPermissions.toString());
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_icon_back:
                finish();
                break;
            default:
                break;
        }
    }


    //相机的回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        takePictureManager.attachToActivityForResult(requestCode, resultCode, data);

    }
}
