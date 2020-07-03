package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.adapter.ExternalCheckDetailAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.adapter.NewExternalCheckGridAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.CheckNewBean;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.Enum;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.utils.DrawableUtils;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.utils.ExternalModel;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.utils.GridLayoutItemDecoration;
import com.example.administrator.newsdf.pzgc.adapter.BasePhotoAdapter;

import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.PopCameraUtils;
import com.example.administrator.newsdf.pzgc.utils.TakePictureManager;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.view.DKDragView;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.bean.photoBean;
import com.example.baselibrary.utils.dialog.BaseDialogUtils;
import com.example.baselibrary.utils.network.NetworkAdapter;
import com.example.baselibrary.utils.rx.LiveDataBus;
import com.example.baselibrary.view.PermissionListener;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 说明：外业检查：单据详情审核页面
 * 创建时间： 2020/6/24 0024 9:57
 *
 * @author winelx
 */
public class ExternalCheckDetailActivity extends BaseActivity implements View.OnClickListener {
    private TextView toolbartext, title, tabPrevious, tabNext;
    private DKDragView dkDragView;
    private RecyclerView drawableRecycler, recycler, recPhoto;
    private DrawerLayout drawerLayout;
    private NewExternalCheckGridAdapter gridAdapter;
    private ExternalCheckDetailAdapter detailAdapter;
    private int page = 0;
    private ArrayList<CheckNewBean.scorePane> pagelist;
    private SmartRefreshLayout refreshlayout;
    private BasePhotoAdapter adapter;
    private Context mContext;
    private TextView orgText;
    private EditText describe, orgEditext, score;
    private Switch footerSwitch;
    //相机相册请求弹窗帮助类
    private PopCameraUtils popCameraUtils;
    private TakePictureManager takePictureManager;
    private ArrayList<photoBean> photoPaths;
    private final int RESULT = 102;
    private ArrayList<String> detelist;
    private ExternalModel externalModel;
    private String checkid, scoreid, level, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_externalcheck_detail);
        mContext = this;
        popCameraUtils = new PopCameraUtils();
        externalModel = new ExternalModel();
        takePictureManager = new TakePictureManager(this);
        pagelist = new ArrayList<>();
        photoPaths = new ArrayList<>();
        detelist = new ArrayList<>();
        findViewById(R.id.toolbar_menu).setOnClickListener(this);
        findViewById(R.id.com_back).setOnClickListener(this);
        toolbartext = findViewById(R.id.com_button);
        tabPrevious = findViewById(R.id.tab_previous);
        tabPrevious.setOnClickListener(this);
        tabNext = findViewById(R.id.tab_next);
        tabNext.setOnClickListener(this);
        title = findViewById(R.id.com_title);
        drawerLayout = findViewById(R.id.drawer_layout);
        DrawableUtils.setDrawLayout(drawerLayout);
        //展示侧拉界面后，背景透明度（当前透明度为完全透明）
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        dkDragView = findViewById(R.id.suspension);
        //设置不允许超过的边界（左上右下）
        dkDragView.setBoundary(0, 130, 0, 230);
        dkDragView.setOnDragViewClickListener(new DKDragView.onDragViewClickListener() {
            @Override
            public void onClick() {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });
        drawableRecycler = findViewById(R.id.drawable_recycler);
        drawableRecycler.setLayoutManager(new GridLayoutManager(this, 5));
        gridAdapter = new NewExternalCheckGridAdapter(R.layout.check_new_grid_item, new ArrayList<>());
        drawableRecycler.addItemDecoration(new GridLayoutItemDecoration(this, R.drawable.item_divider));
        drawableRecycler.setAdapter(gridAdapter);
        gridAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                page = position;
                setTitle();
                Reset();
                drawerLayout.closeDrawers();
            }
        });
        refreshlayout = findViewById(R.id.refreshlayout);
        //是否启用下拉刷新功能
        refreshlayout.setEnableRefresh(false);
        //是否启用上拉加载功能
        refreshlayout.setEnableLoadmore(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshlayout.setEnableOverScrollDrag(true);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> lis = new ArrayList<>();
        lis.add("");
        lis.add("");
        detailAdapter = new ExternalCheckDetailAdapter(R.layout.adapter_item_external_checkdetail, lis);
        detailAdapter.setHeaderView(getHeaderView());
        detailAdapter.setFooterView(getFooterView());
        recycler.setAdapter(detailAdapter);
        LiveDataBus.get().with("recycler", String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String str) {
                        recycler.smoothScrollToPosition(Integer.parseInt(str));
                    }
                });
        Intent intent = getIntent();
        page = intent.getIntExtra("page", 1);
        checkid = intent.getStringExtra("checkid");
        scoreid = intent.getStringExtra("scoreid");
        toolbartext.setText("保存");
        getScorePane();
        getSafetyCheckDelByApp(scoreid);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_menu:
                //保存
                break;
            case R.id.tab_next:
                if (page != pagelist.size() - 1) {
                    page++;
                    setTitle();
                    Reset();
                } else {
                    ToastUtils.showShortToast("已经是最后一项了");
                }
                break;
            case R.id.tab_previous:
                if (page > 0) {
                    page--;
                    setTitle();
                    Reset();
                } else {
                    ToastUtils.showShortToast("已经是第一项了");
                }
                break;
            case R.id.com_back:
                finish();
                break;
            default:
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    public void setTitle() {
        title.setText((page + 1) + "/" + pagelist.size());
        if (page == 0) {
            tabPrevious.setBackgroundResource(R.drawable.tab_choose_up_gray);
        } else {
            tabPrevious.setBackgroundResource(R.drawable.tab_choose_up_blue);
        }
        if (page == (pagelist.size() - 1)) {
            tabNext.setBackgroundResource(R.drawable.tab_choose_down_gray);
        } else {
            tabNext.setBackgroundResource(R.drawable.tab_choose_down_blue);
        }
    }

    /**
     * 说明：添加头部布局
     * 创建时间： 2020/6/10 0010 14:16
     *
     * @author winelx
     */
    public View getHeaderView() {
        View headerView = getLayoutInflater().inflate(R.layout.adapter_check_detail_header, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return headerView;
    }

    /**
     * 说明：添加尾部布局
     * 创建时间： 2020/6/10 0010 14:16
     *
     * @author winelx
     */
    public View getFooterView() {
        View footerView = getLayoutInflater().inflate(R.layout.adapter_check_detail_footer, null);
        footerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        recPhoto = footerView.findViewById(R.id.rec_photo);
        footerSwitch = footerView.findViewById(R.id.footer_switch);
        describe = footerView.findViewById(R.id.describe);
        score = footerView.findViewById(R.id.score);
        orgEditext = footerView.findViewById(R.id.org_editext);
        recPhoto.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new BasePhotoAdapter(mContext, photoPaths);
        recPhoto.setAdapter(adapter);
        orgText = footerView.findViewById(R.id.org_text);
        footerView.findViewById(R.id.import_org).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mContext, ExternalTreeActivity.class), RESULT);
            }
        });
        adapter.setOnItemClickListener(new BasePhotoAdapter.OnItemClickListener() {
            @Override
            public void addlick(View view, int position) {
                permisssion();
            }

            @Override
            public void delete(int position) {
                //删除
                photoPaths.remove(position);
                adapter.getData(photoPaths);
            }
        });
        return footerView;
    }

    /**
     * 说明：权限申请
     * 创建时间： 2020/3/6 0006 9:56
     * author  winelx
     */
    public void permisssion() {
        requestRunPermisssion(new String[]{Enum.CAMERA, Enum.FILEWRITE, Enum.FILEREAD}, new PermissionListener() {
            @SuppressLint("HandlerLeak")
            @Override
            public void onGranted() {
                //打开选择图片的弹窗
                popCameraUtils.showPopwindow(ExternalCheckDetailActivity.this, new PopCameraUtils.CameraCallback() {
                    @Override
                    public void oncamera() {
                        //拍照方式
                        takePictureManager.startTakeWayByCarema();
                        //回调
                        takePictureManager.setTakePictureCallBackListener(new TakePictureManager.takePictureCallBackListener() {
                            @Override
                            public void successful(boolean isTailor, final File outFile, Uri filePath) {
//                                    //压缩图片
                                Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                                Tiny.getInstance().source(outFile).asFile().withOptions(options).compress(new FileCallback() {
                                    @Override
                                    public void callback(boolean isSuccess, String outfile) {
                                        photoPaths.add(new photoBean(outfile, "jpg"));
                                        adapter.getData(photoPaths);
                                    }
                                });
                            }

                            //失败回调
                            @Override
                            public void failed(int errorCode, List<String> deniedPermissions) {
                            }
                        });
                    }

                    @Override
                    public void onalbum() {
                        //相册
                        Intent intent = new Intent(mContext, ImageGridActivity.class);
                        startActivityForResult(intent, Enum.IMAGE_PICKER);
                    }
                });
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                for (String permission : deniedPermission) {
                    if (permission.equals(Enum.CAMERA)) {
                        BaseDialogUtils.openAppDetails(mContext, "上传图片需要相机,请到权限管理中心打开");
                    } else {
                        BaseDialogUtils.openAppDetails(mContext, "APP更新需要手机存储权限,请到权限管理中心打开");
                    }
                }
            }
        });
    }

    /**
     * 说明：
     * 创建时间： 2020/6/30 0030 14:47
     *
     * @author winelx
     */
    public void Reset() {
        detelist.clear();
        photoPaths.clear();
        adapter.getData(photoPaths);
        orgText.setText("");
        describe.setText("");
        orgEditext.setText("");
        score.setText("");
        orgText.setVisibility(View.GONE);
    }

    /**
     * 回调
     * 创建时间： 2020/6/30 0030 14:06
     *
     * @author winelx
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Enum.IMAGE_CREAMA) {
            //相机拍照回调
            try {
                takePictureManager.attachToActivityForResult(requestCode, resultCode, data);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else if (requestCode == RESULT) {
            if (data != null) {
                orgText.setText(data.getStringExtra("content"));
                orgText.setVisibility(View.VISIBLE);
            }
        } else if (requestCode == Enum.IMAGE_PICKER) {
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
                                //新增数据
                                photoPaths.add(new photoBean(outfile, "jpg"));
                                //刷新数据，并指定刷新的位置
                                adapter.getData(photoPaths);
                            }
                        });
                    } else {
                        ToastUtils.showLongToast("请检查上传的图片是否损坏");
                    }
                }
            }
        }
    }

    public void getSafetyCheckDelByApp(String id) {
        externalModel.getSafetyCheckDelByApp(id, new NetworkAdapter() {
            @Override
            public void onsuccess(Object object) {
                super.onsuccess(object);
                Map<String, Object> map = (Map<String, Object>) object;
                setPermission(map);
            }
        });
    }

    /**
     * 说明：获取面板分数
     * 创建时间： 2020/7/3 0003 15:57
     *
     * @author winelx
     */
    public void getScorePane() {
        externalModel.getScorePane(checkid, new NetworkAdapter() {
            @Override
            public void onsuccess(Object object) {
                super.onsuccess(object);
                pagelist.addAll((List<CheckNewBean.scorePane>) object);
                gridAdapter.setNewData(pagelist);
                setTitle();
            }
        });
    }

    private void setPermission(Map<String, Object> map) {
        //level 控制显示内容
        // jIsedit:控制输入编辑
        if ("1".equals(level)) {

        } else if ("2".equals(level)) {

        } else if ("3".equals(level)) {

        }
    }
}
