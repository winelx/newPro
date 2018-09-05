package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.FileUtils;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.CheckPermission;
import com.example.administrator.newsdf.camera.CropImageUtils;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.CheckNewAdapter;
import com.example.administrator.newsdf.pzgc.Adapter.CheckPhotoAdapter;
import com.example.administrator.newsdf.pzgc.Adapter.CheckitemAdapter;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.bean.ChekItemBean;
import com.example.administrator.newsdf.pzgc.bean.chekitemList;
import com.example.administrator.newsdf.pzgc.callback.CheckNewCallbackUtils;
import com.example.administrator.newsdf.pzgc.callback.MapCallback;
import com.example.administrator.newsdf.pzgc.callback.MapCallbackUtils;
import com.example.administrator.newsdf.pzgc.callback.TaskCallback;
import com.example.administrator.newsdf.pzgc.callback.TaskCallbackUtils;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.DKDragView;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.Requests;
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

import static com.example.administrator.newsdf.R.id.checklistmeun;
import static com.example.administrator.newsdf.pzgc.utils.Dates.compressPixel;
import static com.lzy.okgo.OkGo.post;

/**
 * description: 检查项
 *
 * @author lx
 *         date: 2018/8/7 0007 下午 1:39
 *         update: 2018/8/7 0007
 *         version:
 */
public class CheckitemActivity extends BaseActivity implements View.OnClickListener, MapCallback, TaskCallback {
    private LinearLayout checkItemContentMassage, checkItemTabup, checkItemTadown;
    private TextView checkItemTabupText, checkItemTadownText, titleView, checkitemcontentStatus, checklistmeuntext;
    private DrawerLayout drawerLayout;
    private CheckNewAdapter adapter;
    private CheckPhotoAdapter photoAdapter;
    private ArrayList<chekitemList> mData;
    private CheckPermission checkPermission;
    private Context mContext;
    private ArrayList<Audio> imagepath;
    private static final int IMAGE_PICKER = 101;
    private String taskId, orgId;
    private int pos, size, number, item;
    private TextView checkItemContentName, checkItemContentContentname, checkItemContentBz, checkItemContentStandarcore, checkItemContentCore;
    private EditText checkItemContentDescribe;
    private Switch switch1;
    private String success, checkManageId, itemId, checkitemtype = "";
    private Boolean generate;
    private LinearLayout drawerlayoutRight;
    //删除的图片Id
    private ArrayList<String> deleteid = new ArrayList<>();
    private String score;
    private CheckitemAdapter mAdapter;
    private ArrayList<ChekItemBean> chekItem;
    private RecyclerView checkStandardRec, photoadd;
    private GridView checklist;
    private DKDragView dkDragView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkitem);
        initdata();
        findId();
        //请求侧拉节目的列表
        getcheckitemList();
        //获取界面数据
        getdate(taskId, number);
        //判断入口，是从已完成还是未提交进入，
        if (success != null) {
            //从已完成 打开通知单点击事件
            checkItemContentMassage.setClickable(true);
            //功能按钮关闭
            checklistmeuntext.setVisibility(View.GONE);
        } else {
            //未提交
            //显示功能按钮
            checklistmeuntext.setVisibility(View.VISIBLE);
            //给功能按钮设置点击事件
            findViewById(checklistmeun).setOnClickListener(this);
        }

        setScore(0);
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
                checkItemContentDescribe.setText("");
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
                checkItemContentDescribe.setText("");
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
        checklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = checklistmeuntext.getText().toString();
                if ("编辑".equals(text)) {
                    pos = position + 1;
                    getdate(taskId, pos);
                } else {
                    //保存当前点击的项，在保存完当前界面后，赋值给pos
                    item = position;
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
                if (isChecked) {
                    //如果打开switch，将所有的项设置为合格
                    for (ChekItemBean item : chekItem) {
                        item.setStatus("true");
                    }
                    checkItemContentCore.setText(checkItemContentStandarcore.getText().toString());
//                    setScore(0);
                    //并刷新界面
                    mAdapter.getData(chekItem);
//                    if (checkItemContentStandarcore.getText().toString().isEmpty()) {
//                        checkItemContentCore.setText("0");
//                    } else {
//                        BigDecimal standarcore = new BigDecimal((String) checkItemContentStandarcore.getText());
//                        if (standarcore == null) {
//                            standarcore = new BigDecimal("0");
//                        }
//                        checkItemContentCore.setText(checkItemContentStandarcore.getText().toString());
//                    }
                } else {
                    //如果打开switch，将所有的项设置为未选择
                    for (ChekItemBean item : chekItem) {
                        item.setStatus("");
                    }
                    //刷新界面
                    mAdapter.getData(chekItem);
                    checkItemContentCore.setText("");
                    checkItemContentCore.setHint("");
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
        Intent intent = getIntent();
        taskId = intent.getStringExtra("taskId");
        orgId = intent.getStringExtra("orgId");
        pos = intent.getIntExtra("position", 0);
        number = intent.getIntExtra("number", 0);
        size = intent.getIntExtra("size", 0);
        success = intent.getStringExtra("success");
        mData = new ArrayList<>();
        imagepath = new ArrayList<>();
        chekItem = new ArrayList<>();
        mContext = this;
        //检查相机权限
        checkPermission = new CheckPermission(this) {
            @Override
            public void permissionSuccess() {
                CropImageUtils.getInstance().takePhoto(CheckitemActivity.this);
            }

            @Override
            public void negativeButton() {
                //如果不重写，默认是finishddsfaasf
                //super.negativeButton();
                ToastUtils.showLongToast("权限申请失败！");
            }
        };
    }

    /**
     * 获取控件id及其初始化数据
     */
    private void findId() {
        //检查标准
        checkStandardRec = (RecyclerView) findViewById(R.id.check_standard_rec);
        //操作键（保存/剑姬）
        checklistmeuntext = (TextView) findViewById(R.id.checklistmeuntext);
        //侧拉界面的父布局
        drawerlayoutRight = (LinearLayout) findViewById(R.id.drawerLayout_right);
        //是否无此项
        switch1 = (Switch) findViewById(R.id.switch1);
        //是否生成整改通知
        checkitemcontentStatus = (TextView) findViewById(R.id.checkItemContent_status);
        //检查描述
        checkItemContentDescribe = (EditText) findViewById(R.id.check_item_content_describe);
        //检查项内容名称
        checkItemContentContentname = (TextView) findViewById(R.id.check_item_content_contentname);
        //标准分
        checkItemContentStandarcore = (TextView) findViewById(R.id.check_item_content_standarcore);
        //得分
        checkItemContentCore = (TextView) findViewById(R.id.check_item_content_core);
        //整改通知
        checkItemContentMassage = (LinearLayout) findViewById(R.id.check_item_content_massage);
        checkItemContentMassage.setOnClickListener(this);
        //附件
        photoadd = (RecyclerView) findViewById(R.id.recycler_view);
        //抽屉控件
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        //拖动控件
        checklistmeuntext.setText("编辑");
        //是否生成整改通知
        checkitemcontentStatus = (TextView) findViewById(R.id.checkItemContent_status);

        //检查项名称
        checkItemContentName = (TextView) findViewById(R.id.check_item_content_name);
        //检查项要求
        checkItemContentBz = (TextView) findViewById(R.id.check_item_content_bz);
        checkitemcontentStatus = (TextView) findViewById(R.id.checkItemContent_status);
        //标题
        titleView = (TextView) findViewById(R.id.titleView);
        //整改通知
        checkItemContentMassage = (LinearLayout) findViewById(R.id.check_item_content_massage);
        checkItemContentMassage.setOnClickListener(this);
        //附件
        photoadd = (RecyclerView) findViewById(R.id.recycler_view);
        //抽屉控件
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        //拖动控件
        dkDragView = (DKDragView) findViewById(R.id.float_suspension);
        //下一项
        checkItemTadown = (LinearLayout) findViewById(R.id.check_item_tadown);
        //上一项
        checkItemTabup = (LinearLayout) findViewById(R.id.check_item_tabup);
        //上一项文字
        checkItemTabupText = (TextView) findViewById(R.id.check_item_tabup_text);
        //下一项文字
        checkItemTadownText = (TextView) findViewById(R.id.check_item_tadown_text);
        //右侧拉布局
        checklist = (GridView) findViewById(R.id.checklist);
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
        adapter = new CheckNewAdapter(mContext, mData);
        checklist.setAdapter(adapter);
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
        mAdapter = new CheckitemAdapter(mContext, chekItem);
        checkStandardRec.setAdapter(mAdapter);
    }

    /**
     * 添加图片
     */
    public void showPopwindow() {
        //弹出现在相机和图册的蒙层
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        //初始化布局
        View popView = View.inflate(this, R.layout.camera_pop_menu, null);
        //初始化控件
        RelativeLayout btn_pop_add = popView.findViewById(R.id.btn_pop_add);
        Button btnCamera = popView.findViewById(R.id.btn_camera_pop_camera);
        Button btnAlbum = popView.findViewById(R.id.btn_camera_pop_album);
        Button btnCancel = popView.findViewById(R.id.btn_camera_pop_cancel);
        //获取屏幕宽高
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        final PopupWindow popWindow = new PopupWindow(popView, width, height);
        popWindow.setAnimationStyle(R.style.AnimBottom);
        popWindow.setFocusable(true);
        // 设置同意在外点击消失
        popWindow.setOutsideTouchable(true);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    //调用相机
                    case R.id.btn_camera_pop_camera:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            checkPermission.permission(CheckPermission.REQUEST_CODE_PERMISSION_CAMERA);
                        } else {
                            CropImageUtils.getInstance().takePhoto(CheckitemActivity.this);
                        }
                        break;
                    //相册图片
                    case R.id.btn_camera_pop_album:
                        //开启相册
                        Intent intent = new Intent(mContext, ImageGridActivity.class);
                        startActivityForResult(intent, IMAGE_PICKER);
                        break;
                    //
                    case R.id.btn_camera_pop_cancel:
                        //关闭pop
                    case R.id.btn_pop_add:
                    default:
                        break;
                }
                popWindow.dismiss();
            }
        };

        btnCamera.setOnClickListener(listener);
        btn_pop_add.setOnClickListener(listener);
        btnAlbum.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);
        //设置背景颜色
        ColorDrawable dw = new ColorDrawable(0x30000000);
        popWindow.setBackgroundDrawable(dw);
        //显示位置
        popWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
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
                    FileUtils.deleteFile(imagepath.get(i).getName());
                }
                finish();
                break;
            case R.id.check_item_content_massage:
                //获取无此项状态
                boolean status = switch1.isChecked();
                //获取通知单文字
                String messageStr = checkitemcontentStatus.getText().toString();
                //判断是从哪个界面进入
                if (success != null) {
                    //从已完成进入
                    if (status) {
                        ToastUtils.showLongToast("已选择无此项");
                    } else {
                        //获取文字做判断
                        if ("是".equals(messageStr)) {
                            messages();
                        } else {
                            ToastUtils.showShortToast("没有通知单哦！");
                        }
                    }
                } else {
                    //从未完成进入
                    if (status) {
                        ToastUtils.showLongToast("已选择无此项");
                    } else {
                        if ("是".equals(messageStr)) {
                            messages();
                        } else {
                            message();
                        }
                    }
                }
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

    private void savecontent(final boolean isdata, final String tabup) {
        int count = 0;
        for (int i = 0; i < chekItem.size(); i++) {
            String status = chekItem.get(i).getStatus();
            if (status.isEmpty()) {
                count++;
            }
        }
        if (count>0){
            ToastUtils.showShortToastCenter("检查项还未填完");
        }else {
            Save(isdata, tabup);
        }
    }

    /**
     * 实体返回键
     *
     * @param keyCode
     * @param event
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
                FileUtils.deleteFile(imagepath.get(i).getName());
            }
            finish();
            return true;
        }
        return true;
    }

    /**
     * 保存前的逻辑判断
     *
     * @param isdata
     * @param tabup
     */
    public void saveDetails(final boolean isdata, final String tabup) {
        int count = 0;
        for (int i = 0; i < chekItem.size(); i++) {
            String status = chekItem.get(i).getStatus();
            if (status.isEmpty()) {
                count++;
            }
        }
        if (count == 0) {
            //全部操作过
            Save(isdata, tabup);
        } else if (count == chekItem.size()) {
            //没有操作过
            if ("Tabup".equals(tabup)) {
                getdate(taskId, pos - 1);
            } else if ("Tadown".equals(tabup)) {
                getdate(taskId, pos + 1);
            } else if ("item".equals(tabup)) {
                pos = item;
                getdate(taskId, pos + 1);
            }else if ("1".equals(tabup)){
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
        photoAdapter.getData(imagepath, false);
        switch1.setClickable(false);
        checkItemContentDescribe.setEnabled(false);
        checkItemContentMassage.setClickable(false);
    }

    /**
     * 编辑状态
     */
    public void tClickableT() {
        switch1.setClickable(true);
        checkItemContentDescribe.setEnabled(true);
        photoAdapter.getData(imagepath, true);
        checkItemContentMassage.setClickable(true);
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
        generate = false;
        checkManageId = "";
        checkitemcontentStatus.setText("否");
    }

    /**
     * 生成通知单或者修改通知单后更新界面
     */
    @Override
    public void getdata(Map<String, Object> map) {
        getcheckitemList();
        generate = true;
        checkitemcontentStatus.setText("是");
        checkManageId = (String) map.get("messageId");

    }

    /**
     * 没有通知单时进入通知单
     */
    public void message() {
        ArrayList<String> ids = new ArrayList<>();
        ArrayList<String> path = new ArrayList<>();
        for (int i = 0; i < imagepath.size(); i++) {
            ids.add(imagepath.get(i).getContent());
            path.add(imagepath.get(i).getName());
        }
        Intent intent = new Intent(CheckitemActivity.this, CheckmassageActivity.class);
        //图片id
        intent.putExtra("ids", ids);
        //图片路径
        intent.putExtra("path", path);
        //查询责任人
        intent.putExtra("orgId", orgId);
        intent.putExtra("success", success);
        //状态
        intent.putExtra("status", generate);
        intent.putExtra("messageid", checkManageId);
        intent.putExtra("typeId", mData.get(pos - 1).getId());
        //具体描述
        intent.putExtra("describe", checkItemContentDescribe.getText().toString());
        //获取文字做判断
        String str = checkItemContentName.getText().toString() + "-" + checkItemContentContentname.getText().toString();
        for (int i = 0; i < chekItem.size(); i++) {
            String status1 = chekItem.get(i).getStatus();
            if ("false".equals(status1)) {
                str = str + "-" + chekItem.get(i).getContent();
            }
        }
        intent.putExtra("content", str);
        intent.putExtra("taskId", chekItem.get(0).getId());
        startActivity(intent);
    }

    /**
     * 有通知单时进入通知单
     */
    public void messages() {
        Intent intent = new Intent(CheckitemActivity.this, CheckmassageActivity.class);
        //查询责任人
        intent.putExtra("orgId", orgId);
        intent.putExtra("success", success);
        //状态
        intent.putExtra("status", generate);
        intent.putExtra("messageid", checkManageId);
        intent.putExtra("typeId", mData.get(pos - 1).getId());
        //具体描述
        intent.putExtra("describe", checkItemContentDescribe.getText().toString());
        //获取文字做判断
        String str = checkItemContentName.getText().toString() + "-" + checkItemContentContentname.getText().toString();
        for (int i = 0; i < chekItem.size(); i++) {
            String status1 = chekItem.get(i).getStatus();
            if (status1.equals("false")) {
                str = str + "-" + chekItem.get(i).getContent();
            }
        }
        intent.putExtra("content", str);
        intent.putExtra("taskId", chekItem.get(0).getId());
        startActivity(intent);
    }

    /**
     * 检查项分数计算
     */
    public void setScore(int pos) {
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
     * 获取界面数据
     *
     * @param id
     * @param page
     */
    public void getdate(String id, final Integer page) {
        Dates.getDialogs(CheckitemActivity.this, "请求数据中...");
        post(Requests.INFO_BY_MAIN_ID_AND_SQE)
                .params("id", id)
                .params("page", page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Dates.disDialog();
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
                                            status = "";
                                        }
                                        String standardScore;
                                        try {
                                            standardScore = jsonObject1.getString("standardScore");
                                        } catch (JSONException e) {
                                            standardScore = "";
                                        }
                                        String stype = jsonObject1.getString("stype");
                                        if (standardScore.isEmpty()) {
                                            BigDecimal decimal = new BigDecimal("0");
                                            chekItem.add(new ChekItemBean(id, decimal, standard, status, stype));
                                        } else {
                                            BigDecimal decimal = new BigDecimal(standardScore);
                                            chekItem.add(new ChekItemBean(id, decimal, standard, status, stype));
                                        }
                                    }
                                }
                                mAdapter.getData(chekItem);
                                setScore(0);
                                checkManageId = jsonObject.getString("noticeId");
                                try {
                                    itemId = jsonObject.getString("id");
                                } catch (JSONException e) {
                                    itemId = "";
                                }
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
                                String describe = jsonObject.getString("describe");

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
                                if ("0.0".equals(score)){
                                    //如果的得分为0
                                    int count=0;
                                    //便利数据，判断集合的数据是否被操作
                                    for (int i = 0; i < chekItem.size(); i++) {
                                        String status = chekItem.get(i).getStatus();
                                        if (status.isEmpty()) {
                                            count++;
                                        }
                                    }
                                    if (count==chekItem.size()){
                                        //没有操作过
                                        checkItemContentCore.setText("");
                                        checkItemContentCore.setHint("标准分自动计算");
                                    }else {
                                        //操作过
                                        checkItemContentCore.setText("0");
                                    }
                                }else {
                                    //分数不为0
                                    checkItemContentCore.setText(score.replace(".0", ""));
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
                                boolean gray= jsonObject.getBoolean("gray");
                                if (gray) {
                                    checklistmeuntext.setText("编辑");
                                    tClickableF();
                                }else {
                                    checklistmeuntext.setText("保存");
                                    tClickableT();
                                }
                                try {
                                    generate = jsonObject.getBoolean("generate");
                                    if (generate) {
                                        checkitemcontentStatus.setText("是");
                                        if (success != null) {
                                            checkItemContentMassage.setClickable(true);
                                        }
                                    } else {
                                        checkitemcontentStatus.setText("否");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    generate = false;
                                    checkitemcontentStatus.setText("否");
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

    /**
     * 生成检查后的检查项列表
     */
    public void getcheckitemList() {
        OkGo.<String>post(Requests.SIMPLE_DETAILS_LIST_BY_APP)
                .params("id", taskId)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                mData.clear();
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json = jsonArray.getJSONObject(i);
                                        String id = json.getString("id");
                                        String score = json.getString("score");
                                        String sequence = json.getString("sequence");
                                        String standardScore;
                                        try {
                                            standardScore = json.getString("standardScore");
                                        } catch (JSONException e) {
                                            standardScore = "";
                                        }
                                        boolean noSuch = json.getBoolean("noSuch");
                                        boolean gray = json.getBoolean("gray");
                                        boolean penalty = json.getBoolean("penalty");
                                        boolean generate;
                                        try {
                                            generate = json.getBoolean("generate");
                                        } catch (JSONException e) {
                                            generate = false;
                                        }
                                        int number = i + 1;
                                        mData.add(new chekitemList(id, score, sequence, number + "", standardScore, noSuch, penalty, generate, gray));
                                    }
                                }
                            }
                            adapter.getdate(mData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
    }

    /**
     * 保存接口
     *
     * @param isdata
     * @param tabup
     */
    public void Save(final boolean isdata, final String tabup) {
        checklistmeuntext.setText("");
        Dates.getDialog(CheckitemActivity.this,"保存数据中...");
        ArrayList<File> file = new ArrayList<>();
        for (int i = 0; i < imagepath.size(); i++) {
            if (imagepath.get(i).getContent().length() > 0) {
            } else {
                file.add(new File(imagepath.get(i).getName()));
            }
        }
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < chekItem.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", chekItem.get(i).getId());
            map.put("pass", chekItem.get(i).getStatus());
            map.put("stype", chekItem.get(i).getStype());
            list.add(map);
        }
        JSONArray json2 = new JSONArray(list);
        PostRequest mPostRequest = OkGo.post(Requests.SAVE_DETAILS)
                .params("details ", json2.toString())
                .params("checkManageId", taskId)
                .params("noSuch", switch1.isChecked())
                //是否生成整改通知单
                .params("generate", generate)
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
                checklistmeuntext.setText("保存");
            }
        });
    }

}

