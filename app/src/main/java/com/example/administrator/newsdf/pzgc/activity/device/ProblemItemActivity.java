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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.CheckPhotoAdapter;
import com.example.administrator.newsdf.pzgc.activity.device.utils.DeviceUtils;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.bean.DetailsBean;
import com.example.administrator.newsdf.pzgc.bean.ProblemBean;
import com.example.administrator.newsdf.pzgc.bean.ProblemFile;
import com.example.administrator.newsdf.pzgc.callback.CheckCallback3;
import com.example.administrator.newsdf.pzgc.callback.Networkinterface;
import com.example.administrator.newsdf.pzgc.callback.ProblemCallbackUtils;
import com.example.administrator.newsdf.pzgc.callback.ProblemItemCallbackUtils;
import com.example.baselibrary.view.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.PopCameraUtils;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.TakePictureManager;
import com.example.administrator.newsdf.pzgc.utils.list.DialogUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lx
 * @description: 新增/删除检查项
 * @date: 2018/11/30 0030 下午 5:00
 */
public class ProblemItemActivity extends BaseActivity implements View.OnClickListener, CheckCallback3 {
    private RecyclerView itemRecycler;
    private static final int IMAGE_PICKER = 1011;
    private Context mContext;
    private ArrayList<Audio> imagepath;
    private CheckPhotoAdapter photoAdapter;
    private ArrayList<String> deleteLis = new ArrayList<>();
    private TextView violationStandardsText, hiddenDangerGradeText, rectifyData, itemDelete;
    private EditText rectifyCause;
    //调用相机的manager
    private TakePictureManager takePictureManager;
    private TextView checklistmeuntext;
    private LinearLayout contentlin;
    private PopCameraUtils PopCameraUtils;
    private DialogUtils dialogUtils;
    private DeviceUtils deviceUtils;
    private String typeId, valueId, standId, checkId;
    private String[] strings = {"确定", "取消"};
    private String title = "是否删除该项问题";
    private String qdgId, qdId, facility;
    private boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);
        imagepath = new ArrayList<>();
        dialogUtils = new DialogUtils();
        deviceUtils = new DeviceUtils();
        mContext = this;
        ProblemItemCallbackUtils.setCallBack(this);
        init();
        Intent intent = getIntent();
        status = intent.getBooleanExtra("bean", true);
        typeId = intent.getStringExtra("typeId");
        facility = intent.getStringExtra("facility");
        checkId = intent.getStringExtra("checkId");
        if (!status) {
            itemDelete.setVisibility(View.VISIBLE);
            deviceUtils.secdetailsbyedit(typeId, new DeviceUtils.ProblemLitener() {
                @Override
                public void success(ProblemBean bean, ArrayList<ProblemFile> data) {
                    checklistmeuntext.setText("编辑");
                    rectifyCause.setEnabled(false);
                    //整改期限
                    rectifyData.setText(bean.getTerm());
                    //隐患等级
                    hiddenDangerGradeText.setText(bean.getHTLName());
                    valueId = bean.getHiddenTroubleLevel() + "";
                    //违反标准
                    violationStandardsText.setText(bean.getCisName());
                    standId = bean.getCisId();
                    qdgId = bean.getQdgId();
                    qdId = bean.getQdId();
                    //整改事由
                    rectifyCause.setText(bean.getCause());
                    if (data.size() > 0) {
                        for (int i = 0; i < data.size(); i++) {
                            imagepath.add(new Audio(Requests.networks + data.get(i).getFilepath(), data.get(i).getId()));
                        }
                        photoAdapter.getData(imagepath, false);
                    }
                }
            });
        } else {
            photoAdapter.getData(imagepath, true);
            itemDelete.setVisibility(View.GONE);
        }
    }

    /**
     * @description: 初始化控件
     * @author lx
     * @date: 2018/12/3 0003 下午 3:31
     */
    private void init() {
        TextView titleView = (TextView) findViewById(R.id.titleView);
        titleView.setText("问题项");
        contentlin = (LinearLayout) findViewById(R.id.contentlin);
        checklistmeuntext = (TextView) findViewById(R.id.checklistmeuntext);
        checklistmeuntext.setVisibility(View.VISIBLE);
        checklistmeuntext.setText("保存");
        checklistmeuntext.setOnClickListener(this);
        takePictureManager = new TakePictureManager(ProblemItemActivity.this);
        //删除Item
        itemDelete = (TextView) findViewById(R.id.item_delete);
        itemDelete.setOnClickListener(this);
        //违反标准
        findViewById(R.id.violation_standards).setOnClickListener(this);
        violationStandardsText = (TextView) findViewById(R.id.violation_standards_text);
        //隐患等级
        findViewById(R.id.hidden_danger_grade).setOnClickListener(this);
        hiddenDangerGradeText = (TextView) findViewById(R.id.hidden_danger_grade_text);
        //整改期限
        findViewById(R.id.rectify_data_lin).setOnClickListener(this);
        rectifyData = (TextView) findViewById(R.id.rectify_data);
        //整改事由
        rectifyCause = (EditText) findViewById(R.id.rectify_cause);
        //附件
        itemRecycler = (RecyclerView) findViewById(R.id.item_recycler);
        //样式
        itemRecycler.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        //添加动画
        itemRecycler.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        photoAdapter = new CheckPhotoAdapter(mContext, imagepath, "device", true);
        itemRecycler.setAdapter(photoAdapter);
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
        } else if (requestCode == 102 && resultCode == RESULT_OK) {
            String da = data.getStringExtra("label");
            valueId = data.getStringExtra("value");
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
                        Dates.getDialog(ProblemItemActivity.this, "删除数据中...");
                        deviceUtils.deleteitem(typeId, new Networkinterface() {
                            @Override
                            public void onsuccess(Map<String, Object> map) {
                                //删除
                                ToastUtils.showLongToast("删除成功");
                                ArrayList<DetailsBean> mData = new ArrayList<>();
                                ProblemCallbackUtils.ProblemCallback(mData);
                                finish();
                            }
                        });
                    }
                });
                break;
            case R.id.checklistmeuntext:
                if ("编辑".equals(checklistmeuntext.getText().toString())) {
                    photoAdapter.getData(imagepath, true);
                    checklistmeuntext.setText("保存");
                    rectifyCause.setEnabled(true);
                } else {
                    //关闭软键盘
                    hintKeyBoard();
                    //保存
                    problemsave();
                }
                break;
            case R.id.violation_standards:
                //违反标准
                if ("编辑".equals(checklistmeuntext.getText().toString())) {
                    ToastUtils.showLongToast("当前不是编辑状态");
                } else {
                    //违反标准
                    Intent intent = new Intent(mContext, DeviceViolatestandardActivity.class);
                    intent.putExtra("facility", facility);
                    startActivity(intent);
                }
                break;
            case R.id.hidden_danger_grade:
//                //隐患等级
                if ("编辑".equals(checklistmeuntext.getText().toString())) {
                    ToastUtils.showLongToast("当前不是编辑状态");
                } else {
                    Intent intent1 = new Intent(mContext, GradeActivity.class);
                    startActivityForResult(intent1, 102);
                }
                break;
            case R.id.rectify_data_lin:
                //整改期限
                if ("编辑".equals(checklistmeuntext.getText().toString())) {
                    ToastUtils.showLongToast("当前不是编辑状态");
                } else {
                    //筛选时间
                    dialogUtils.selectiontime(mContext, new DialogUtils.OnClickListener() {
                        @Override
                        public void onsuccess(String str) {
                            rectifyData.setText(str);
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    /**
     * @内容: 保存
     * @author lx
     * @date: 2018/12/11 0011 下午 2:48
     */
    private void problemsave() {
        Dates.getDialog(this, "提交数据");
        ArrayList<File> file = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        //整改期限
        if (!rectifyData.getText().toString().isEmpty()) {
            map.put("term", rectifyData.getText().toString());
        } else {
            ToastUtils.showLongToast("整改期限未选择");
            Dates.disDialog();
            return;
        }
        //隐患等级
        if (valueId != null) {
            map.put("hiddenTroubleLevel", valueId);
        } else {
            ToastUtils.showLongToast("隐患等级未选择");
            Dates.disDialog();
            return;
        }
        //违反标准Id
        if (standId != null) {
            map.put("cisId", standId);
            map.put("checkId", checkId);
            map.put("qdgId", qdgId);
            map.put("qdId", qdId);
        } else {
            ToastUtils.showLongToast("违反标准未选择");
            Dates.disDialog();
            return;
        }
        //整改事由
        map.put("cause", rectifyCause.getText().toString());
        //图片
        for (int i = 0; i < imagepath.size(); i++) {
            //如果content内容为空，本地添加图片
            if (imagepath.get(i).getContent().isEmpty()) {
                file.add(new File(imagepath.get(i).getName()));
            }
        }
        //修改传Id
        if (!status) {
            map.put("id", typeId);
        }
        if (deleteLis.size() > 0) {
            map.put("deleteFileIds", Dates.listToStrings(deleteLis));
        }
        deviceUtils.saveSECDetails(map, file, new DeviceUtils.devicesavelitener() {
            @Override
            public void success(String number, String id) {
                //新增整改问题
                ToastUtils.showLongToast("保存成功");
                ArrayList<DetailsBean> mData = new ArrayList<>();
                ProblemCallbackUtils.ProblemCallback(mData);
                finish();
            }
        });

    }

    /**
     * @description: 违反标准回调
     * @author lx
     * @date: 2018/12/3 0003 下午 3:44
     */
    @Override
    public void update(String string) {
        String[] str = string.split("&&&");
        violationStandardsText.setText(str[0]);
        standId = str[1];
        qdgId = str[2];
        qdId = str[3];
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

    //隐藏键盘
    public void hintKeyBoard() {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

}
