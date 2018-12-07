package com.example.administrator.newsdf.pzgc.activity.device;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
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
import com.example.administrator.newsdf.pzgc.activity.check.CheckUtils;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.bean.NewDeviceBean;
import com.example.administrator.newsdf.pzgc.callback.CheckCallback3;
import com.example.administrator.newsdf.pzgc.callback.ProblemCallbackUtils;
import com.example.administrator.newsdf.pzgc.callback.ProblemItemCallbackUtils;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.PopCameraUtils;
import com.example.administrator.newsdf.pzgc.utils.TakePictureManager;
import com.example.administrator.newsdf.pzgc.utils.list.DialogUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lx
 * @description: 新增/删除检查项
 * @date: 2018/11/30 0030 下午 5:00
 */
public class ProblemItemActivity extends BaseActivity implements View.OnClickListener, CheckCallback3 {
    private RecyclerView item_recycler;
    private static final int IMAGE_PICKER = 1011;
    private Context mContext;
    private ArrayList<Audio> imagepath;
    private CheckPhotoAdapter photoAdapter;
    private ArrayList<String> deleteLis = new ArrayList<>();
    private TextView violationStandardsText, hiddenDangerGradeText, rectifyData;
    private EditText rectifyCause;
    private NewDeviceBean bean = new NewDeviceBean();
    //调用相机的manager
    private TakePictureManager takePictureManager;
    private TextView checklistmeuntext;
    private LinearLayout contentlin;
    private PopCameraUtils PopCameraUtils;
    private DialogUtils dialogUtils;
    private int pos;
    String[] strings = {"确定", "取消"};
    String title = "是否删除该项问题";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);
        imagepath = new ArrayList<>();
        dialogUtils = new DialogUtils();
        mContext = this;

        ProblemItemCallbackUtils.setCallBack(this);
        init();
        Intent intent = getIntent();
        String status = intent.getStringExtra("bean");
        if ("false".equals(status)) {
            try {
                pos = intent.getIntExtra("pos", 0);
                NewDeviceActivity activity = NewDeviceActivity.getInstance();
                bean = activity.getBean();
                //违反标准
                violationStandardsText.setText(bean.getTitile());
                //隐患等级
                hiddenDangerGradeText.setText(bean.getGrade());
                //整改时间
                rectifyData.setText(bean.getData());
                //整改原因
                rectifyCause.setText(bean.getReason());
                //巡检附件
                imagepath.addAll(bean.getList());
            } catch (Exception e) {

            }
        }
//        showView();
    }

    /**
     * @description: 初始化控件
     * @author lx
     * @date: 2018/12/3 0003 下午 3:31
     */
    private void init() {
        contentlin = (LinearLayout) findViewById(R.id.contentlin);
        checklistmeuntext = (TextView) findViewById(R.id.checklistmeuntext);
        checklistmeuntext.setVisibility(View.VISIBLE);
        checklistmeuntext.setText("确定");
        checklistmeuntext.setOnClickListener(this);
        takePictureManager = new TakePictureManager(ProblemItemActivity.this);
        //删除Item
        findViewById(R.id.item_delete).setOnClickListener(this);
        //违反标准
        findViewById(R.id.violation_standards).setOnClickListener(this);
        violationStandardsText = (TextView) findViewById(R.id.violation_standards_text);
        //隐患等级
        findViewById(R.id.hidden_danger_grade).setOnClickListener(this);
        hiddenDangerGradeText = (TextView) findViewById(R.id.hidden_danger_grade_text);
        //整改期限
        findViewById(R.id.rectify_data_lin).setOnClickListener(this);
        rectifyData = (TextView) findViewById(R.id.rectify_data);
        rectifyData.setText(Dates.getDay());
        //整改事由
        rectifyCause = (EditText) findViewById(R.id.rectify_cause);
        //附件
        item_recycler = (RecyclerView) findViewById(R.id.item_recycler);
        //样式
        item_recycler.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        //添加动画
        item_recycler.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        photoAdapter = new CheckPhotoAdapter(mContext, imagepath, "device", false);
        item_recycler.setAdapter(photoAdapter);
        photoAdapter.setOnItemClickListener(new CheckPhotoAdapter.OnItemClickListener() {
            @Override
            public void addlick(View view, int position) {
                //选择相机相册
                PopCameraUtils = new PopCameraUtils();
                PopCameraUtils.showPopwindow((Activity) mContext, new PopCameraUtils.CameraCallback() {
                    @Override
                    public void oncamera() {
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
                                        //添加进集合
                                        imagepath.add(new Audio(outfile, ""));
                                        //填入listview，刷新界面
                                        photoAdapter.getData(imagepath, true);
                                    }
                                });
                            }

                            @Override
                            public void failed(int errorCode, List<String> deniedPermissions) {

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
                //删除图片
                String conet = imagepath.get(position).getContent();
                if (conet.length() > 0) {
                    deleteLis.add(imagepath.get(position).getContent());
                }
                imagepath.remove(position);
                photoAdapter.getData(imagepath, true);
            }
        });
        //返回
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * activity回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            //调用相机的回调
            try {
                takePictureManager.attachToActivityForResult(requestCode, resultCode, data);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else if (requestCode == 1011 && resultCode == 1004) {
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
                            //添加进集合
                            imagepath.add(new Audio(outfile, ""));
                            //填入listview，刷新界面
                            photoAdapter.getData(imagepath, true);
                        }
                    });
                } else {
                    ToastUtils.showLongToast("请检查上传的图片是否损坏");
                }
            }
        } else if (requestCode == 102) {
            String da = data.getStringExtra("grade");
            ToastUtils.showLongToast(da);
            hiddenDangerGradeText.setText(da);
        }
    }

    /**
     * @内容: 点击事件
     * @author lx
     * @date: 2018/12/5 0005 下午 3:22
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_delete:
                //删除
                DialogUtils.Tipsdialog(mContext, title, strings, new DialogUtils.OnClickListener() {
                    @Override
                    public void onsuccess(String str) {
                        ToastUtils.showLongToast("删除");
                        ProblemCallbackUtils.deleteProblem(pos);
                    }
                });
                break;
            case R.id.checklistmeuntext:
//                //整改时间
                bean.setData(rectifyData.getText().toString());
//                //隐患等级
                bean.setGrade(hiddenDangerGradeText.getText().toString());
//                //违反标准
                bean.setTitile(violationStandardsText.getText().toString());
//                //整改事由
                bean.setReason(rectifyCause.getText().toString());
//                //图片
                bean.setList(imagepath);
                ProblemCallbackUtils.addProblem(bean);
                this.finish();
                break;
            case R.id.violation_standards:
                //违反标准
                Intent intent = new Intent(mContext, GradeActivity.class);
                startActivity(intent);
                break;
            case R.id.hidden_danger_grade:
//                //隐患等级
                Intent intent1 = new Intent(mContext, GradeActivity.class);
                startActivityForResult(intent1, 102);
                break;
            case R.id.rectify_data_lin:
                //筛选时间
                dialogUtils.selectiontime(mContext, new DialogUtils.OnClickListener() {
                    @Override
                    public void onsuccess(String str) {
                        rectifyData.setText(str);
                    }
                });
                //整改期限
                break;
            default:
                break;
        }
    }

    /**
     * @description: 违反标准回调
     * @author lx
     * @date: 2018/12/3 0003 下午 3:44
     */
    @Override
    public void update(String string) {
        if (PopCameraUtils != null) {
            PopCameraUtils = null;
        }
        if (dialogUtils != null) {
            dialogUtils = null;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (PopCameraUtils != null) {
            PopCameraUtils = null;
        }
        if (dialogUtils != null) {
            dialogUtils = null;
        }
    }


}
