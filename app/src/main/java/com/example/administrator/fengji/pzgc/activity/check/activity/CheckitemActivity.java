package com.example.administrator.fengji.pzgc.activity.check.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.fengji.R;

import com.example.administrator.fengji.camera.CropImageUtils;
import com.example.administrator.fengji.pzgc.activity.check.CheckUtils;
import com.example.administrator.fengji.pzgc.utils.PopCameraUtils;
import com.example.administrator.fengji.pzgc.utils.ToastUtils;
import com.example.administrator.fengji.pzgc.adapter.CheckNewAdapter;
import com.example.administrator.fengji.pzgc.adapter.CheckPhotoAdapter;
import com.example.administrator.fengji.pzgc.adapter.CheckitemAdapter;
import com.example.administrator.fengji.pzgc.bean.Audio;
import com.example.administrator.fengji.pzgc.bean.ChekItemBean;
import com.example.administrator.fengji.pzgc.bean.chekitemList;
import com.example.administrator.fengji.pzgc.callback.CheckNewCallbackUtils;
import com.example.administrator.fengji.pzgc.callback.MapCallback;
import com.example.administrator.fengji.pzgc.callback.MapCallbackUtils;
import com.example.administrator.fengji.pzgc.callback.TaskCallback;
import com.example.administrator.fengji.pzgc.callback.TaskCallbackUtils;
import com.example.administrator.fengji.pzgc.view.DKDragView;
import com.example.baselibrary.base.BaseActivity;


import com.example.administrator.fengji.pzgc.utils.Dates;
import com.example.baselibrary.inface.NetworkCallback;
import com.example.baselibrary.view.PermissionListener;
import com.example.baselibrary.utils.Requests;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.fengji.R.id.checklistmeun;
import static com.example.administrator.fengji.pzgc.utils.Dates.compressPixel;
import static com.lzy.okgo.OkGo.post;

/**
 * description: 检查项
 *
 * @author lx
 * date: 2018/8/7 0007 下午 1:39
 * update: 2018/8/7 0007
 * version:
 */
public class CheckitemActivity extends BaseActivity implements View.OnClickListener, MapCallback, TaskCallback {
    private LinearLayout checkItemTabup, checkItemTadown, scoreLin, switchLin;
    private TextView checkItemTabupText, checkItemTadownText, titleView,
            checklistmeuntext, describeImage;
    private TextView checkItemContentName, checkItemContentContentname,
            checkItemContentBz, checkItemContentStandarcore, checkItemContentCore;
    private EditText checkItemContentDescribe;
    private DKDragView dkDragView;
    private DrawerLayout drawerLayout;
    private GridView checklist, checklists;
    private RecyclerView checkStandardRec, photoadd;
    private LinearLayout drawerlayoutRight, checkContent;
    private Switch switch1, checkitemcontentStatus;

    private String score, taskId, success, checkitemtype = "";
    private Boolean generate = false;
    private static final int IMAGE_PICKER = 101;
    private int pos, size, number, item;
    private Context mContext;

    //删除的图片Id
    private ArrayList<String> deleteid = new ArrayList<>();
    //检查项数据
    private ArrayList<ChekItemBean> chekItem;
    //图片
    private ArrayList<Audio> imagepath;
    //检查项列表
    private ArrayList<chekitemList> mData, preposition, routine;

    private CheckNewAdapter adapter, adapters;
    private CheckitemAdapter mAdapter;
    private CheckPhotoAdapter photoAdapter;
    private InputMethodManager inputMethodManager;
    //相机相册请求弹窗帮助类
    private PopCameraUtils popCameraUtils;
    //检查模块板帮助类
    private CheckUtils checkUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkitem);
        //输入法
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        initdata();
        findId();
        //请求侧拉节目的列表
        getcheckitemList();
        //获取界面数据
        getdate(taskId, pos);
        //判断入口，是从已完成还是未提交进入，
        if (success != null) {
            //从已完成 打开通知单点击事件
            //功能按钮关闭
            checklistmeuntext.setVisibility(View.GONE);
        } else {
            //未提交
            //显示功能按钮
            checklistmeuntext.setVisibility(View.VISIBLE);
            //给功能按钮设置点击事件
            findViewById(checklistmeun).setOnClickListener(this);
        }
        setScore();
        onclick();
    }

    /*点击事件*/
    public void onclick() {
        //下一项点击事件
        checkItemTadown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = checklistmeuntext.getText().toString();
                //点击下一项时，上一项文字变为灰色
                checkItemTabupText.setTextColor(Color.parseColor("#646464"));
                //文字变为白色
                checkItemTadownText.setTextColor(Color.parseColor("#ffffff"));
                //下一项背景变为灰色
                checkItemTadown.setBackgroundResource(R.drawable.tab_choose_down_gray);
                //上一下背景变为白色
                checkItemTabup.setBackgroundResource(R.drawable.tab_choose_up);
                checkItemTabup.setClickable(false);
                checkItemTadown.setClickable(false);
                if ("编辑".equals(text)) {
                    getdate(taskId, pos + 1);
                } else {
                    saveDetails(true, "Tadown");
                }
            }
        });
        //上一项点击事件
        checkItemTabup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = checklistmeuntext.getText().toString();
                //参考下一项点击事件
                checkItemTabupText.setTextColor(Color.parseColor("#ffffff"));
                checkItemTadownText.setTextColor(Color.parseColor("#646464"));
                checkItemTabup.setBackgroundResource(R.drawable.tab_choose_up_gray);
                checkItemTadown.setBackgroundResource(R.drawable.tab_choose_down);
                checkItemTabup.setClickable(false);
                checkItemTadown.setClickable(false);
                if ("编辑".equals(text)) {
                    getdate(taskId, pos - 1);
                } else {
                    saveDetails(true, "Tabup");
                }
            }
        });

        //侧拉界面的gridview的点击事件，直接跳转到指定项界面
        //常规项
        checklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = checklistmeuntext.getText().toString();
                if ("编辑".equals(text)) {
                    //获取当前位置
                    pos = routine.get(position).getPos();
                    getdate(taskId, pos);
                } else {
                    //保存当前点击的项，在保存完当前界面后，赋值给pos
                    item = routine.get(position).getPos();
                    saveDetails(true, "item");
                }
                drawerLayout.closeDrawer(drawerlayoutRight);
            }
        });
        //前置项
        checklists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = checklistmeuntext.getText().toString();
                if ("编辑".equals(text)) {
                    //获取当前位置
                    pos = preposition.get(position).getPos();
                    getdate(taskId, pos);
                } else {
                    //保存当前点击的项，在保存完当前界面后，赋值给pos
                    item = preposition.get(position).getPos();
                    saveDetails(true, "item");
                }
                drawerLayout.closeDrawer(drawerlayoutRight);
            }
        });
        /**
         * 无此项的打卡和关闭判断
         */
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Dates.hintKeyBoard(CheckitemActivity.this);
                if (isChecked) {
                    //如果打开switch，将所有的项设置为合格
                    for (ChekItemBean item : chekItem) {
                        item.setStatus("true");
                        item.setResultscore(item.getScore() + "");
                    }
                    generate = false;
                    checkitemcontentStatus.setChecked(false);
                    checkItemContentCore.setText(checkItemContentStandarcore.getText().toString());
                    //并刷新界面
                    mAdapter.getData(chekItem);
                } else {
                    //如果打开switch，将所有的项设置为未选择
                    for (ChekItemBean item : chekItem) {
                        item.setStatus("");
                        item.setResultscore("");
                    }
                    //刷新界面
                    mAdapter.getData(chekItem);
                    checkItemContentCore.setText("");
                    checkItemContentCore.setHint("");
                }
            }
        });
        checkitemcontentStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean status = switch1.isChecked();
                if (status) {
                    ToastUtils.showLongToast("已选择无此项");
                    generate = false;
                    checkitemcontentStatus.setChecked(false);
                } else {
                    if (b) {
                        describeImage.setVisibility(View.VISIBLE);
                    } else {
                        describeImage.setVisibility(View.GONE);
                    }
                }
            }
        });

    }

    /**
     * 展示界面之前数据处理
     */

    private void initdata() {
        MapCallbackUtils.setCallBack(this);
        TaskCallbackUtils.setCallBack(this);
        popCameraUtils = new PopCameraUtils();
        checkUtils = new CheckUtils();
        Intent intent = getIntent();
        taskId = intent.getStringExtra("taskId");
        pos = intent.getIntExtra("position", 0);
        number = intent.getIntExtra("number", 0);
        size = intent.getIntExtra("size", 0);
        success = intent.getStringExtra("success");
        mData = new ArrayList<>();
        preposition = new ArrayList<>();
        routine = new ArrayList<>();
        imagepath = new ArrayList<>();
        chekItem = new ArrayList<>();
        mContext = this;
    }

    /**
     * 获取控件id及其初始化数据
     */
    private void findId() {
        //无此项
        switchLin = (LinearLayout) findViewById(R.id.switch_lin);
        //得分布局
        scoreLin = (LinearLayout) findViewById(R.id.check_item_content_score_lin);
        describeImage = (TextView) findViewById(R.id.describe_image);
        //检查标准
        checkStandardRec = (RecyclerView) findViewById(R.id.check_standard_rec);
        //操作键（保存/编辑）
        checklistmeuntext = (TextView) findViewById(R.id.checklistmeuntext);
        checklistmeuntext.setTextSize(15);
        //侧拉界面的父布局
        drawerlayoutRight = (LinearLayout) findViewById(R.id.drawerLayout_right);
        //前置项
        checkContent = (LinearLayout) findViewById(R.id.check_content);
        //是否无此项
        switch1 = (Switch) findViewById(R.id.switch1);
        //是否生成整改通知
        checkitemcontentStatus = (Switch) findViewById(R.id.checkItemContent_status);
        //检查描述
        checkItemContentDescribe = (EditText) findViewById(R.id.check_item_content_describe);
        //检查项内容名称
        checkItemContentContentname = (TextView) findViewById(R.id.check_item_content_contentname);
        //标准分
        checkItemContentStandarcore = (TextView) findViewById(R.id.check_item_content_standarcore);
        //得分
        checkItemContentCore = (TextView) findViewById(R.id.check_item_content_core);
        //抽屉控件
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        //拖动控件
        checklistmeuntext.setText("编辑");
        //检查项名
        checkItemContentName = (TextView) findViewById(R.id.check_item_content_name);
        //检查项要求
        checkItemContentBz = (TextView) findViewById(R.id.check_item_content_bz);
        //标题
        titleView = (TextView) findViewById(R.id.titleView);
        //附件
        photoadd = (RecyclerView) findViewById(R.id.recycler_view);
        //抽屉控件
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        //拖动控件
        dkDragView = (DKDragView) findViewById(R.id.float_suspension);
        //右侧拉布局
        checklist = (GridView) findViewById(R.id.checklist);
        checklists = (GridView) findViewById(R.id.checklist1);
        //下一项
        checkItemTadown = (LinearLayout) findViewById(R.id.check_item_tadown);
        //上一项
        checkItemTabup = (LinearLayout) findViewById(R.id.check_item_tabup);
        //上一项文字
        checkItemTabupText = (TextView) findViewById(R.id.check_item_tabup_text);
        //下一项文字
        checkItemTadownText = (TextView) findViewById(R.id.check_item_tadown_text);
        findViewById(R.id.checklistback).setOnClickListener(this);
        //拖动控件可拖动范围
        dkDragView.setBoundary(0, 0, 0, 170);
        //关闭边缘滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //侧滑栏关闭手势滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //展示侧拉界面后，背景透明度（当前透明度为完全透明）
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        dkDragView.setOnDragViewClickListener(new DKDragView.onDragViewClickListener() {
            @Override
            public void onClick() {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });
        //附件的recycleraview的适配器
        photoadd.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        photoadd.setItemAnimator(new DefaultItemAnimator());
        checklistmeuntext.setText("编辑");
        //右侧布局的gridview的适配器
        //常规
        adapter = new CheckNewAdapter(mContext, routine);
        checklist.setAdapter(adapter);
        //前置项
        adapters = new CheckNewAdapter(mContext, preposition);
        checklists.setAdapter(adapters);
        photoAdapter = new CheckPhotoAdapter(mContext, imagepath, "Check", false);
        photoadd.setAdapter(photoAdapter);
        //设置标题
        titleView.setText(number + "/" + size);
        //设置当number为1，不显示上一项，number等于检查项长度，不显示下一项，避免角标越界
        if (number == 1) {
            checkItemTabup.setVisibility(View.INVISIBLE);
        } else if (number == size) {
            checkItemTadown.setVisibility(View.INVISIBLE);
        }
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        //设置RecyclerView 布局
        checkStandardRec.setLayoutManager(layoutmanager);
        checkStandardRec.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mAdapter = new CheckitemAdapter(mContext, chekItem, inputMethodManager);
        checkStandardRec.setAdapter(mAdapter);
    }

    /**
     * 添加图片
     */
    public void showPopwindow() {
        popCameraUtils.showPopwindow(CheckitemActivity.this, new PopCameraUtils.CameraCallback() {
            @Override
            public void oncamera() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //请求权限
                    getauthority();
                } else {
                    /*打开相机*/
                    CropImageUtils.getInstance().takePhoto(CheckitemActivity.this);
                }
            }

            @Override
            public void onalbum() {
                //开启相册
                Intent intent = new Intent(mContext, ImageGridActivity.class);
                startActivityForResult(intent, IMAGE_PICKER);
            }
        });
    }

    /**
     * activity的回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //冲相册返回图片
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
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
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        } else {
            //从相机返回图片
            CropImageUtils.getInstance().onActivityResult(this, requestCode, resultCode, data, new CropImageUtils.OnResultListener() {
                @Override
                public void takePhotoFinish(final String path) {
                    //   根据路径压缩图片并返回bitmap(2
                    //获取图片选择角度，旋转图片
                    Bitmap bitmap = CropImageUtils.rotaingImageView(CropImageUtils.readPictureDegree(path), compressPixel(path));
                    //实例化Tiny.FileCompressOptions，并设置quality的数值（quality改变图片压缩质量）
                    Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                    options.quality = 95;
                    Tiny.getInstance().source(bitmap).asFile().withOptions(options).compress(new FileCallback() {
                        @Override
                        public void callback(boolean isSuccess, String outfile) {
                            imagepath.add(new Audio(outfile, ""));
                            //填入listview，刷新界面
                            photoAdapter.getData(imagepath, true);
                            //删除原图
                            Dates.deleteFile(path);
                        }
                    });

                }
            });
        }

    }

    /**
     * 点击事件处理
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checklistback:
                //返回当前界面
                //如果已完成的界面进入，就没有初始化CheckNewCallback，这时会抛异常
                try {
                    CheckNewCallbackUtils.CallBackMethod();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                //删除从相册相机生成的图片，避免占用内存
                for (int i = 0; i < imagepath.size(); i++) {
                    Dates.deleteFile(imagepath.get(i).getName());
                }
                finish();
                break;
            case R.id.checklistmeun:
                String text1 = checklistmeuntext.getText().toString();
                if ("编辑".equals(text1)) {
                    tClickableT();
                    checklistmeuntext.setText("保存");
                } else if ("保存".equals(text1)) {
                    savecontent(false, "1");
                } else {

                }
                break;
            default:
                break;
        }
    }

    /**
     * 保存按钮保存前判断
     */
    private void savecontent(final boolean isdata, final String tabup) {
        //检查项完成数，判断是否检查完成
        int count = 0;
        //检查项得分是否大于0，用来判断填的分数是否正确
        int size = 0;
        for (int i = 0; i < chekItem.size(); i++) {
            String type = chekItem.get(i).getStype();
            if ("2".equals(type)) {
                String status = chekItem.get(i).getStatus();
                if (status.isEmpty()) {
                    count++;
                }
            } else if ("3".equals(type)) {
                String status = chekItem.get(i).getStatus();
                if (status.isEmpty()) {
                    count++;
                }
            } else {
                String score = chekItem.get(i).getResultscore();
                if (score.isEmpty()) {
                    count++;
                } else {
                    BigDecimal bigDecimal = new BigDecimal(score);
                    int maxsize = bigDecimal.compareTo(chekItem.get(i).getScore());
                    int minsize = bigDecimal.compareTo(new BigDecimal("0"));
                    if (maxsize < 1) {
                        if (minsize >= 0) {
                        } else {
                            size++;
                        }
                    } else {
                        size++;
                    }
                }
            }
        }
        if (count > 0) {
            ToastUtils.showShortToastCenter("检查项还未填完");
        } else {
            if (size > 0) {
                Toast.makeText(CheckitemActivity.this, "检查项得分大于等0或者小于等于标准分", Toast.LENGTH_LONG).show();
            } else {
                //判断是否生产整改通知单
                boolean lean = checkitemcontentStatus.isChecked();
                if (lean) {
                    //生成看整改通知单
                    String string = checkItemContentDescribe.getText().toString();
                    if (!string.isEmpty()) {
                        //整改描述不为空
                        Save(isdata, tabup);
                    } else {
                        ToastUtils.showShortToastCenter("生成整改通知单后具体描述不能为空");
                    }
                } else {
                    Save(isdata, tabup);
                }
            }

        }
    }

    /**
     * 上下项保存前逻辑
     *
     * @param isdata 是否操作过
     * @param tabup  点击按钮
     */
    public void saveDetails(final boolean isdata, final String tabup) {
        int count = 0;
        int size = 0;
        for (int i = 0; i < chekItem.size(); i++) {
            String type = chekItem.get(i).getStype();
            if ("2".equals(type)) {
                String status = chekItem.get(i).getStatus();
                if (status.isEmpty()) {
                    count++;
                }
            } else if ("3".equals(type)) {
                String status = chekItem.get(i).getStatus();
                if (status.isEmpty()) {
                    count++;
                }
            } else {
                String score = chekItem.get(i).getResultscore();
                if (score.isEmpty()) {
                    count++;
                } else {
                    //判断分数
                    BigDecimal bigDecimal = new BigDecimal(score);
                    int maxsize = bigDecimal.compareTo(chekItem.get(i).getScore());
                    int minsize = bigDecimal.compareTo(new BigDecimal("0"));
                    if (maxsize < 1) {
                        if (minsize >= 0) {
                        } else {
                            size++;
                        }
                    } else {
                        size++;
                    }
                }
            }
        }
        if (count == 0 && size == 0) {
            //全部操作过
            if (size > 0) {
                checkItemTabup.setClickable(true);
                checkItemTadown.setClickable(true);
                Toast.makeText(CheckitemActivity.this, "检查项得分大于等0或者小于等于标准分", Toast.LENGTH_LONG).show();
            } else {
                boolean lean = checkitemcontentStatus.isChecked();
                if (lean) {
                    String string = checkItemContentDescribe.getText().toString();
                    if (!string.isEmpty()) {
                        Save(isdata, tabup);
                    } else {
                        checkItemTabup.setClickable(true);
                        checkItemTadown.setClickable(true);
                        ToastUtils.showShortToastCenter("生成整改通知单后具体描述不能为空");
                    }
                } else {
                    Save(isdata, tabup);
                }
            }
        } else if (count == chekItem.size()) {
            //没有操作过
            if ("Tabup".equals(tabup)) {
                getdate(taskId, pos - 1);
            } else if ("Tadown".equals(tabup)) {
                getdate(taskId, pos + 1);
            } else if ("item".equals(tabup)) {
                pos = item;
                getdate(taskId, pos);
            } else if ("1".equals(tabup)) {
                getdate(taskId, pos);
            }
        } else {
            //没有操作完、
            checkItemTabup.setClickable(true);
            checkItemTadown.setClickable(true);
            ToastUtils.showShortToastCenter("检查项还未填完");
        }
    }

    /**
     * 删除从网络获取的图片，保存的id，下次提交时给后台做判断
     *
     * @param position
     */
    public void delete(String position) {
        if (position.length() > 0) {
            deleteid.add(position);
        }
    }

    /**
     * 保存状态
     */
    public void tClickableF() {
        //图片
        photoAdapter.getData(imagepath, false);
        //无此项
        switch1.setClickable(false);
        checkitemcontentStatus.setClickable(false);
        checkitemcontentStatus.setClickable(false);
        //内容
        checkItemContentDescribe.setEnabled(false);
        //整改通知

        //刷新检查项
        mAdapter.getData(chekItem);
    }

    /**
     * 编辑状态
     */
    public void tClickableT() {
        switch1.setClickable(true);
        checkitemcontentStatus.setClickable(true);
        checkitemcontentStatus.setClickable(true);
        checkItemContentDescribe.setEnabled(true);
        photoAdapter.getData(imagepath, true);
        mAdapter.getData(chekItem);
    }

    /**
     * 给适配器做判断用
     *
     * @return
     */
    public String getstatus() {
        return checklistmeuntext.getText().toString();
    }

    /**
     * 给检查标准适配器做点击事件做判断用
     *
     * @return
     */
    public boolean getswitchstatus() {
        return switch1.isChecked();
    }

    /**
     * 删除通知单后刷新界面
     */
    @Override
    public void taskCallback() {
        getcheckitemList();
        checkitemcontentStatus.setClickable(false);
    }

    /**
     * 生成通知单或者修改通知单后更新界面
     */
    @Override
    public void getdata(Map<String, Object> map) {
        getcheckitemList();
        generate = true;
        checkitemcontentStatus.setClickable(true);
    }

    /**
     * 检查项分数计算
     */
    public void setScore() {
        BigDecimal score = new BigDecimal("0");
        if (!"2".equals(checkitemtype)) {
            for (int i = 0; i < chekItem.size(); i++) {
                String status = chekItem.get(i).getStatus();
                if ("true".equals(status)) {
                    score = score.add(chekItem.get(i).getScore());
                }
            }
            checkItemContentCore.setText(score + "");
        }
    }

    /**
     * 检查项列表
     */
    public void getcheckitemList() {
        checkUtils.getcheckitemlist(taskId, null, new NetworkCallback() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                mData.clear();
                preposition.clear();
                routine.clear();
                mData.addAll((ArrayList<chekitemList>) map.get("list"));
                if (mData.size() > 0) {
                    for (int i = 0; i < mData.size(); i++) {
                        if ("3".equals(mData.get(i).getS_type())) {
                            preposition.add(mData.get(i));
                        } else {
                            routine.add(mData.get(i));
                        }
                    }
                    //常规
                    adapter.getdate(routine);
                    //前置
                    if (preposition.size() != 0) {
                        adapters.getdate(preposition);
                        checkContent.setVisibility(View.VISIBLE);
                    } else {
                        checkContent.setVisibility(View.GONE);
                    }
                }
                if (mData.size() > 0) {
                    if ("3".equals(mData.get(pos - 1).getS_type())) {
                        scoreLin.setVisibility(View.GONE);
                        switchLin.setVisibility(View.GONE);
                    } else {
                        scoreLin.setVisibility(View.VISIBLE);
                        switchLin.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onerror(String s) {
                ToastUtils.showShortToast(s);
            }
        });
    }

    /**
     * 保存接口
     *
     * @param isdata  是否操作过
     * @param tabup-+
     */
    public void Save(final boolean isdata, final String tabup) {
        checklistmeuntext.setVisibility(View.INVISIBLE);
        Dates.hintKeyBoard(CheckitemActivity.this);
        Dates.getDialog(CheckitemActivity.this, "保存数据中...");
        ArrayList<File> file = new ArrayList<>();
        for (int i = 0; i < imagepath.size(); i++) {
            if (imagepath.get(i).getContent().length() > 0) {
            } else {
                file.add(new File(imagepath.get(i).getName()));
            }
        }
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < chekItem.size(); i++) {
            String type = chekItem.get(i).getStype();
            if ("2".equals(type)) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", chekItem.get(i).getId());
                map.put("pass", chekItem.get(i).getStatus());
                map.put("stype", chekItem.get(i).getStype());
                map.put("score", chekItem.get(i).getScore());
                list.add(map);
            } else {
                Map<String, Object> map = new HashMap<>();
                map.put("id", chekItem.get(i).getId());
                map.put("pass", chekItem.get(i).getStatus());
                map.put("stype", chekItem.get(i).getStype());
                map.put("score", chekItem.get(i).getResultscore());
                list.add(map);
            }
        }
        JSONArray json2 = new JSONArray(list);
        PostRequest mPostRequest = OkGo.post(Requests.SAVE_DETAILS)
                .params("details ", json2.toString())
                .params("checkManageId", taskId)
                .params("noSuch", switch1.isChecked())
                //是否生成整改通知单
                .params("generate", checkitemcontentStatus.isChecked())
                //Id
                .params("id", mData.get(pos - 1).getId())
                //得分
                .params("score", checkItemContentCore.getText().toString())
                //具体描述
                .params("describe", checkItemContentDescribe.getText().toString())
                .params("deleteFileId", Dates.listToStrings(deleteid));
        //附件(判断是否新增图片，没有新增就不上传图片。新增了就上传新增的)
        if (file.size() > 0) {
            mPostRequest.addFileParams("imagesList", file);
        } else {
            mPostRequest.isMultipart(true);
        }
        mPostRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Dates.disDialog();
                checklistmeuntext.setVisibility(View.VISIBLE);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        deleteid.clear();
                        imagepath.clear();
                        getcheckitemList();
                        if (isdata) {
                            checkItemContentDescribe.setText("");
                            checkItemContentDescribe.setText("");
                            if ("Tabup".equals(tabup)) {
                                getdate(taskId, pos - 1);
                            } else if ("Tadown".equals(tabup)) {
                                getdate(taskId, pos + 1);
                            } else if ("item".equals(tabup)) {
                                pos = item;
                                getdate(taskId, pos + 1);
                                getcheckitemList();
                            }
                        } else {
                            getdate(taskId, pos);
                        }
                    } else {
                        ToastUtils.showShortToast(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Dates.disDialog();
                checklistmeuntext.setVisibility(View.VISIBLE);
                checklistmeuntext.setText("保存");
            }
        });
    }

    /**
     * 获取界面数据
     *
     * @param id   id
     * @param page 当前页数
     */
    public void getdate(String id, final Integer page) {
        Dates.getDialogs((Activity) mContext, "请求数据中...");
        if (mData.size() != 0) {
            if ("3".equals(mData.get(page - 1).getS_type())) {
                scoreLin.setVisibility(View.GONE);
                switchLin.setVisibility(View.GONE);
            } else {
                scoreLin.setVisibility(View.VISIBLE);
                switchLin.setVisibility(View.VISIBLE);
            }
        }
        post(Requests.INFO_BY_MAIN_ID_AND_SQE)
                .params("id", id)
                .params("page", page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Dates.disDialog();
                        //请求成功，清空整改描述
                        checkItemContentDescribe.setText("");
                        checkItemTabup.setClickable(true);
                        checkItemTadown.setClickable(true);
                        try {
                            JSONObject jsonO = new JSONObject(s);
                            int ret = jsonO.getInt("ret");
                            if (ret == 0) {
                                chekItem.clear();
                                imagepath.clear();
                                pos = page;
                                titleView.setText(page + "/" + size);
                                JSONObject jsonObject = jsonO.getJSONObject("data");
                                JSONArray json = jsonObject.getJSONArray("data");

                                switch1.setChecked(jsonObject.getBoolean("noSuch"));
                                if (json.length() > 0) {
                                    for (int i = 0; i < json.length(); i++) {
                                        JSONObject jsonObject1 = json.getJSONObject(i);
                                        String standard = jsonObject1.getString("standard");
                                        String id = jsonObject1.getString("id");
                                        String status;
                                        try {
                                            status = jsonObject1.getString("pass");
                                        } catch (JSONException e) {
                                            status = "false";
                                        }
                                        String standardScore;
                                        try {
                                            standardScore = jsonObject1.getString("standardScore");
                                        } catch (JSONException e) {
                                            standardScore = "";
                                        }
                                        String resultscore;
                                        try {
                                            resultscore = jsonObject1.getString("score");
                                        } catch (JSONException e) {
                                            resultscore = "";
                                        }
                                        //基本项：1  检查项：2
                                        String stype = jsonObject1.getString("stype");
                                        //
                                        if (standardScore.isEmpty()) {
                                            BigDecimal decimal = new BigDecimal("0");
                                            chekItem.add(new ChekItemBean(id, decimal, standard, status, stype, resultscore));
                                        } else {
                                            BigDecimal decimal = new BigDecimal(standardScore);
                                            chekItem.add(new ChekItemBean(id, decimal, standard, status, stype, resultscore));
                                        }
                                    }
                                }
                                mAdapter.getData(chekItem);
                                setScore();
                                checkItemContentName.setText(jsonObject.getString("name"));
                                checkitemtype = jsonObject.getString("stype");
                                checkItemContentContentname.setText(jsonObject.getString("content"));
                                try {
                                    checkItemContentBz.setText(jsonObject.getString("bigstandard"));
                                } catch (JSONException e) {
                                    checkItemContentBz.setText("");
                                }
                                try {
                                    String srcor = jsonObject.getString("bigstandard");
                                    checkItemContentStandarcore.setText(srcor.replace(".0", ""));
                                } catch (JSONException e) {
                                    checkItemContentStandarcore.setText("");
                                }
                                String describe;
                                try {
                                    describe = jsonObject.getString("describe");
                                } catch (Exception e) {
                                    describe = "";
                                }
                                /*整改描述*/
                                checkItemContentDescribe.setText(describe);
                                JSONArray attachments = jsonObject.getJSONArray("attachments");
                                if (attachments.length() > 0) {
                                    for (int i = 0; i < attachments.length(); i++) {
                                        JSONObject attach = attachments.getJSONObject(i);
                                        imagepath.add(new Audio(Requests.networks + attach.getString("filepath"), attach.getString("id")));
                                    }
                                    photoAdapter.getData(imagepath, false);
                                } else {
                                    imagepath.clear();
                                    photoAdapter.getData(imagepath, false);
                                }
                                score = jsonObject.getString("bigscore");
                                if ("0.0".equals(score)) {
                                    //如果的得分为0
                                    int count = 0;
                                    //便利数据，判断集合的数据是否被操作
                                    for (int i = 0; i < chekItem.size(); i++) {
                                        String status = chekItem.get(i).getStatus();
                                        if (status.isEmpty()) {
                                            count++;
                                        }
                                    }
                                    if (count == chekItem.size()) {
                                        //没有操作过
                                        checkItemContentCore.setText("");
                                        checkItemContentCore.setHint("标准分自动计算");
                                    } else {
                                        //操作过
                                        checkItemContentCore.setText("0");
                                    }
                                } else {
                                    //分数不为0
                                    checkItemContentCore.setText(score);
                                }

                                if (page == 1) {
                                    checkItemTabup.setVisibility(View.INVISIBLE);
                                    checkItemTadown.setVisibility(View.VISIBLE);
                                } else if (page == size) {
                                    checkItemTabup.setVisibility(View.VISIBLE);
                                    checkItemTadown.setVisibility(View.INVISIBLE);
                                } else {
                                    checkItemTabup.setVisibility(View.VISIBLE);
                                    checkItemTadown.setVisibility(View.VISIBLE);
                                }
                                boolean gray = jsonObject.getBoolean("gray");
                                if (gray) {
                                    checklistmeuntext.setText("编辑");
                                    tClickableF();
                                } else {
                                    checklistmeuntext.setText("保存");
                                    tClickableT();
                                }
                                try {
                                    generate = jsonObject.getBoolean("generate");
                                    if (generate) {
                                        describeImage.setVisibility(View.VISIBLE);
                                        checkitemcontentStatus.setChecked(true);
                                    } else {
                                        describeImage.setVisibility(View.GONE);
                                        checkitemcontentStatus.setChecked(false);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    generate = false;
                                    checkitemcontentStatus.setChecked(false);
                                }

                            } else {
                                ToastUtils.showShortToast(jsonO.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Dates.disDialog();
                        checkItemTabup.setClickable(true);
                        checkItemTadown.setClickable(true);
                    }
                });
    }

    /*申请权限*/
    public void getauthority() {
        //获取相机权限，定位权限，内存权限
        requestRunPermisssion(new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted() {
                CropImageUtils.getInstance().takePhoto(CheckitemActivity.this);
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                for (String permission : deniedPermission) {
                    Toast.makeText(mContext, "被拒绝的权限：" +
                            permission, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * 实体返回键
     *
     * @param keyCode 参数
     * @param event   参数
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //返回当前界面
            //如果已完成的界面进入，就没有初始化CheckNewCallback，这时会抛异常
            try {
                CheckNewCallbackUtils.CallBackMethod();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            //删除从相册相机生成的图片，避免占用内存
            for (int i = 0; i < imagepath.size(); i++) {
                Dates.deleteFile(imagepath.get(i).getName());
            }
            finish();
            return true;
        }
        return true;
    }

}

