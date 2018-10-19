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
import android.widget.EditText;
import android.widget.LinearLayout;
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
import measure.jjxx.com.baselibrary.utils.ToastUtlis;


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
    //根据status 类处理当前界面点击事件或者输入框是否可点击或者编辑
    private boolean status = true;
    /**
     * 界面控件
     */
    //所属项目  所属标段  指挥部
    private LinearLayout newAddOriginalProject, newAddOriginalBids, newAddOriginalCommand;
    //标准分解
    private LinearLayout Standarddecomposition, newAddOriginalApplydate;
    //所属项目名称   所属标段名称  指挥部名称
    private TextView newAddOriginalProjectname, newAddOriginalBidstext, newAddOriginalCommandtext;
    //标准分解名称  申报期数
    private TextView Standarddecompositiontext, newAddOriginalApplydateText;
    //原始单号
    private EditText newAddOriginalOriginalnumber;

    //所属标段
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_add_original_zc);
        mContext = this;
        findId();
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

    private void findId() {
        //所属项目
        newAddOriginalProject = (LinearLayout) findViewById(R.id.new_add_original_project);
        newAddOriginalProject.setOnClickListener(this);
        //所属项目名称
        newAddOriginalProjectname = (TextView) findViewById(R.id.new_add_original_projectname);
        //所属标段
        newAddOriginalBids = (LinearLayout) findViewById(R.id.new_add_original_bids);
        newAddOriginalBids.setOnClickListener(this);
        //所属标段名称
        newAddOriginalBidstext = (TextView) findViewById(R.id.new_add_original_bidstext);
        //指挥部
        newAddOriginalCommand = (LinearLayout) findViewById(R.id.new_add_original_command);
        newAddOriginalCommand.setOnClickListener(this);
        //指挥部名称
        newAddOriginalCommandtext = (TextView) findViewById(R.id.new_add_original_commandtext);
        //标准分解
        Standarddecomposition = (LinearLayout) findViewById(R.id.new_add_original_standarddecomposition);
        Standarddecomposition.setOnClickListener(this);
        //标准分解名称
        Standarddecompositiontext = (TextView) findViewById(R.id.new_add_original_standarddecompositiontext);
        //原始单号
        newAddOriginalOriginalnumber = (EditText) findViewById(R.id.new_add_original_originalnumber);
        //申报期数
        newAddOriginalApplydate = (LinearLayout) findViewById(R.id.new_add_original_applydate);
        newAddOriginalApplydate.setOnClickListener(this);
        newAddOriginalApplydateText = (TextView) findViewById(R.id.new_add_original_applydate_text);
    }

    private void init() {
        photrecycler = (RecyclerView) findViewById(R.id.newaddoriginal_recycler);
        mPhotosAdapter = new PhotosAdapter(this, list,true);
        photrecycler.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        photrecycler.setAdapter(mPhotosAdapter);
        //点击事件--- 弹窗选择相机还是相册，理相机相册返回的图片--展示
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
            case R.id.new_add_original_project:
                //所属项目
                if (status) {
                    startActivity(new Intent(mContext, ChoiceProjectZcActivity.class));
                } else {
                    ToastUtlis.getInstance().showShortToast("当前不是编辑状态");
                }
                break;
            case R.id.new_add_original_bids:
                //所属标段
                if (status) {
                    startActivity(new Intent(mContext, ChoiceBidsZcActivity.class));
                } else {
                    ToastUtlis.getInstance().showShortToast("当前不是编辑状态");
                }
                break;
            case R.id.new_add_original_command:
                //指挥部
                if (status) {
                    startActivity(new Intent(mContext, ChoiceHeadquartersZcActivity.class));
                } else {
                    ToastUtlis.getInstance().showShortToast("当前不是编辑状态");
                }
                break;
            case R.id.new_add_original_standarddecomposition:
                //分解标准
                if (status) {
                    startActivity(new Intent(mContext, StandardDecomposeZcActivity.class));
                } else {
                    ToastUtlis.getInstance().showShortToast("当前不是编辑状态");
                }
                break;
            case R.id.new_add_original_applydate:
                //分解标准
                if (status) {
                    startActivity(new Intent(mContext, ApplyDateZcActivity.class));
                } else {
                    ToastUtlis.getInstance().showShortToast("当前不是编辑状态");
                }
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
