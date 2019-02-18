package com.example.administrator.newsdf.pzgc.activity.changed;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.BasePhotoAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckTreeActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckstandardListActivity;
import com.example.administrator.newsdf.pzgc.photopicker.PhotoPreview;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.PopCameraUtils;
import com.example.administrator.newsdf.pzgc.utils.TakePictureManager;
import com.example.administrator.newsdf.pzgc.utils.list.DialogUtils;
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
 * 描述：(添加)整改通知单问题项
 * {@link }
 */
public class ChagedProblemitemActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView photoRecycler;
    private BasePhotoAdapter adapter;
    private TextView menutext, comTitle, importPosition, chagedOrganizeText, violation_standard_text;
    private EditText exitextPosition, editProblem;
    private LinearLayout chagedOrganizeLin, violationStandard;
    private Context mContext;
    private ArrayList<photoBean> photolist;
    private PopCameraUtils popcamerautils;
    private TakePictureManager takePictureManager;
    private static final int IMAGE_PICKER = 1011;
    private boolean status = true;
    public static final String KEEP = "保存";
    private DialogUtils dialogUtils;
    private String orgName, orgId;
    //整改部位
    private String positionid;
    private ChagedUtils chagedUtils;
    private TextView chaged_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_problemitem);
        Intent intent = getIntent();
        orgName = intent.getStringExtra("orgname");
        orgId = intent.getStringExtra("orgid");
        mContext = this;
        chagedUtils = new ChagedUtils();
        photolist = new ArrayList<>();
        dialogUtils = new DialogUtils();
        //相机帮助类初始化
        takePictureManager = new TakePictureManager(ChagedProblemitemActivity.this);
        popcamerautils = new PopCameraUtils();
        /*整改部位*/
        chaged_position = (TextView) findViewById(R.id.chaged_position);
        /*违反标准*/
        violation_standard_text = (TextView) findViewById(R.id.violation_standard_text);
        /*整改部位*/
        exitextPosition = (EditText) findViewById(R.id.exitext_position);
        /*存在问题*/
        editProblem = (EditText) findViewById(R.id.edit_problem);
        /*整改期限*/
        chagedOrganizeText = (TextView) findViewById(R.id.chaged_organize_text);
        chagedOrganizeText.setText(Dates.getDay());
        chagedOrganizeText.setOnClickListener(this);
        /*导入*/
        findViewById(R.id.import_position).setOnClickListener(this);
        /*删除*/
        findViewById(R.id.check_item_delete).setOnClickListener(this);
        /*返回*/
        findViewById(R.id.com_back).setOnClickListener(this);
        /*菜单按钮*/
        findViewById(R.id.toolbar_menu).setOnClickListener(this);
        /*整改组织*/
        findViewById(R.id.chaged_organize_lin).setOnClickListener(this);
        /*违反标准*/
        findViewById(R.id.violation_standard).setOnClickListener(this);
        /*菜单按钮*/
        menutext = (TextView) findViewById(R.id.com_button);
        menutext.setText("保存");
        /*标题*/
        comTitle = (TextView) findViewById(R.id.com_title);
        comTitle.setText("问题项");
        /*w整改问题列表*/
        photoRecycler = (RecyclerView) findViewById(R.id.photo_recycler);
        adapter = new BasePhotoAdapter(mContext, photolist);
        /*添加图片*/
        photoRecycler.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        photoRecycler.setAdapter(adapter);
        /*点击事件*/
        adapter.setOnItemClickListener(new BasePhotoAdapter.OnItemClickListener() {
            @Override
            public void addlick(View view, int position) {
                //添加图片
                //相机相册选择弹窗帮助类
                //展示弹出窗
                popcamerautils.showPopwindow(ChagedProblemitemActivity.this, new PopCameraUtils.CameraCallback() {
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
                String str = menutext.getText().toString();
                if (KEEP.equals(str)) {
                    status = false;
                    menutext.setText("编辑");
                    adapter.addview(false);
                    statusclose();
                } else {
                    status = true;
                    menutext.setText("保存");
                    adapter.addview(true);
                    statusopen();
                }
                break;
            case R.id.check_item_delete:
                /*删除该项问题*/
                AlertDialog alertDialog2 = new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("是否删除该项问题")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            //添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                chagedUtils.setdelete("", new ChagedUtils.CallBacks() {
                                    @Override
                                    public void onsuccess(String string) {
                                        Snackbar.make(menutext, string, Snackbar.LENGTH_LONG).show();
                                        finish();
                                    }

                                    @Override
                                    public void onerror(String str) {
                                        Snackbar.make(menutext, str, Snackbar.LENGTH_LONG).show();
                                    }
                                });


                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            //添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create();
                alertDialog2.show();
                break;
            case R.id.import_position:
                //导入
                if (status) {
                    //选择标段
                    Intent intent1 = new Intent(mContext, CheckTreeActivity.class);
                    intent1.putExtra("orgId", orgId);
                    intent1.putExtra("name", orgName);
                    startActivityForResult(intent1, 4);
                } else {
                    ToastUtils.showShortToast("不是编辑状态无法操作");
                }
                break;
            case R.id.chaged_organize_lin:
                //整改期限
                if (status) {

                } else {
                    ToastUtils.showShortToast("不是编辑状态无法操作");
                }
                break;
            case R.id.violation_standard:
                //违反标准
                if (status) {
                    Intent intent = new Intent(mContext, CheckstandardListActivity.class);
                    intent.putExtra("title", "title");
                    startActivityForResult(intent, 1);
                } else {
                    ToastUtils.showShortToast("不是编辑状态无法操作");
                }
                break;
            case R.id.chaged_organize_text:
                dialogUtils.selectiontime(mContext, new DialogUtils.OnClickListener() {
                    @Override
                    public void onsuccess(String str) {
                        chagedOrganizeText.setText(str);
                    }
                });
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
        } else if (requestCode == 1 && resultCode == 2) {
            //违法标准
            violation_standard_text.setText(data.getStringExtra("content"));

        } else if (requestCode == 4 && resultCode == 3) {
            //整改部位
            positionid = data.getStringExtra("id");
            exitextPosition.setText(data.getStringExtra("name"));
            /*   intent.putExtra("title", node.getTitle());*/
        }
    }

    public void statusclose() {
        exitextPosition.setEnabled(false);
        editProblem.setEnabled(false);
    }

    public void statusopen() {
        exitextPosition.setEnabled(true);
        editProblem.setEnabled(true);
    }
}
