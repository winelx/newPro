package com.example.zcjlmodule.ui.activity;

import android.app.Activity;
import android.content.Context;
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

import measure.jjxx.com.baselibrary.adapter.PhotoPreview;
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
    private Context mContext;
    private TextView title;
    private RecyclerView photrecycler;
    private PhotosAdapter mPhotosAdapter;
    private ArrayList<String> list;
    private TakePictureManager takePictureManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_add_original_zc);
        mContext = this;
        findViewById(R.id.toolbar_icon_back).setOnClickListener(this);
        title = (TextView) findViewById(R.id.toolbar_icon_title);
        title.setText("新增原始勘丈表");
        list = new ArrayList<>();
        //            list.add(new PhotoviewBean("1221", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539765142464&di=47e520ceff2722d78a0dd1ff1140cf93&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fb8014a90f603738d952a8450be1bb051f819ec64.jpg", true));
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539765142464&di=47e520ceff2722d78a0dd1ff1140cf93&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fb8014a90f603738d952a8450be1bb051f819ec64.jpg");
//        for (int i = 0; i < 10; i++) {
//            list.add(new PhotoviewBean("1221", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539765142464&di=47e520ceff2722d78a0dd1ff1140cf93&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fb8014a90f603738d952a8450be1bb051f819ec64.jpg", true));
//            list.add(new PhotoviewBean("1221", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539765142464&di=b098c79cb7561b5004ce002f630dc55b&imgtype=0&src=http%3A%2F%2Fa.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F0b46f21fbe096b638e7daa2501338744ebf8ac5b.jpg", false));
//            list.add(new PhotoviewBean("1221", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539765142463&di=aac4f6ce0694ae0c03be6febed7a4cc3&imgtype=0&src=http%3A%2F%2Fd.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fc83d70cf3bc79f3dab11b040b7a1cd11738b29c9.jpg", true));
//            list.add(new PhotoviewBean("1221", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539765142463&di=97978715c09b79f30f1e029dd89827b3&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F241f95cad1c8a786b0eb4b016a09c93d71cf50ff.jpg", false));
//            list.add(new PhotoviewBean("1221", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539765142463&di=6ce30964dfccd1c7ffb25eaed20f94d8&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F241f95cad1c8a786f5d00e7a6a09c93d71cf50cf.jpg", true));
        //   }
        init();
    }

    private void init() {
        photrecycler = (RecyclerView) findViewById(R.id.newaddoriginal_recycler);
        mPhotosAdapter = new PhotosAdapter(this, list);
        photrecycler.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        photrecycler.setAdapter(mPhotosAdapter);
        //点击事件
        mPhotosAdapter.setOnItemClickListener(new PhotosAdapter.OnItemClickListener() {
            //添加图片
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

            //点击图片查看
            @Override
            public void photoClick(View view, int position) {
                PhotoPreview.builder().setPhotos(list).setCurrentItem(position).start((Activity) mContext);
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
        try {
            takePictureManager.attachToActivityForResult(requestCode, resultCode, data);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
