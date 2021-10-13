package com.example.administrator.yanghu.pzgc.activity.check.activity.newcheck.activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.activity.check.activity.newcheck.adapter.ExternalCheckDetailAdapter;
import com.example.administrator.yanghu.pzgc.activity.check.activity.newcheck.adapter.NewExternalCheckGridAdapter;
import com.example.administrator.yanghu.pzgc.activity.check.activity.newcheck.bean.CheckDetailBean;
import com.example.administrator.yanghu.pzgc.activity.check.activity.newcheck.bean.CheckNewBean;
import com.example.administrator.yanghu.pzgc.activity.check.activity.newcheck.bean.Enum;
import com.example.administrator.yanghu.pzgc.activity.check.activity.newcheck.utils.DrawableUtils;
import com.example.administrator.yanghu.pzgc.activity.check.activity.newcheck.utils.ExternalModel;
import com.example.administrator.yanghu.pzgc.activity.check.activity.newcheck.utils.GridLayoutItemDecoration;
import com.example.administrator.yanghu.pzgc.adapter.BasePhotoAdapter;
import com.example.administrator.yanghu.pzgc.utils.Dates;
import com.example.administrator.yanghu.pzgc.utils.PopCameraUtils;
import com.example.administrator.yanghu.pzgc.utils.TakePictureManager;
import com.example.administrator.yanghu.pzgc.utils.ToastUtils;
import com.example.administrator.yanghu.pzgc.utils.Utils;
import com.example.administrator.yanghu.pzgc.view.DKDragView;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.bean.photoBean;
import com.example.baselibrary.inface.Onclicklitener;
import com.example.baselibrary.utils.Requests;
import com.example.baselibrary.utils.dialog.BaseDialogUtils;
import com.example.baselibrary.utils.network.NetworkAdapter;
import com.example.baselibrary.utils.rx.LiveDataBus;
import com.example.baselibrary.view.BaseDialog;
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
    /**
     * 分数列表
     */
    private NewExternalCheckGridAdapter gridAdapter;
    /**
     * 界面内容
     */
    private ExternalCheckDetailAdapter detailAdapter;
    private int page = 0;
    private List<CheckNewBean.scorePane> pagelist;
    private SmartRefreshLayout refreshlayout;
    private BasePhotoAdapter adapter;
    private Context mContext;
    private TextView standardscore, checkstandard, importOrg;
    private TextView headerContet, scoreText;
    private EditText describe, orgEditext, score, mangerScore;
    private ImageView scoreWarning;
    private Switch footerSwitch;
    private LinearLayout footerManger;
    /**
     * 相机相册请求弹窗帮助类
     */
    private PopCameraUtils popCameraUtils;
    private TakePictureManager takePictureManager;
    private ArrayList<photoBean> photoPaths;
    private ArrayList<String> detelist;
    private ExternalModel externalModel;
    private String checkid, scoreid, level, status, checkLevel, orgid, clickStatus;
    private boolean edStatus, isLeveOption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_externalcheck_detail);
        mContext = this;
        Intent intent = getIntent();
        page = intent.getIntExtra("page", 0);
        checkid = intent.getStringExtra("checkid");
        scoreid = intent.getStringExtra("scoreid");
        orgid = intent.getStringExtra("orgid");
        isLeveOption = intent.getBooleanExtra("isLeveOption", true);
        checkLevel = intent.getStringExtra("checkLevel");
        level = intent.getStringExtra("level");
        status = intent.getStringExtra("status");
        edStatus = intent.getBooleanExtra("edStatus", false);
        popCameraUtils = new PopCameraUtils();
        externalModel = new ExternalModel();
        takePictureManager = new TakePictureManager(this);
        pagelist = new ArrayList<>();
        photoPaths = new ArrayList<>();
        detelist = new ArrayList<>();
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
                isLeveOption = pagelist.get(position).isLeveOption();
                initCheckItem();
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
        detailAdapter = new ExternalCheckDetailAdapter(new ArrayList<>());
        detailAdapter.setHeaderView(getHeaderView());
        //如果状态大于3，进入确认流程，无需编辑
        if (Integer.parseInt(status) > 3) {
            toolbartext.setVisibility(View.INVISIBLE);
        } else {
            detailAdapter.setFooterView(getFooterView());
            toolbartext.setText("保存");
            toolbartext.setVisibility(View.VISIBLE);
        }
        recycler.setAdapter(detailAdapter);
        findViewById(R.id.toolbar_menu).setOnClickListener(this);
        findViewById(R.id.com_back).setOnClickListener(this);
        LiveDataBus.get().with("recycler", String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String str) {
                recycler.scrollToPosition(0);
            }
        });
        getScorePane();
        getSafetyCheckDelByApp(scoreid);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_menu:
                //保存
                if ("编辑".equals(toolbartext.getText().toString())) {
                    setEnabled(true);
                } else {
                    BaseDialog.confirmdialog(mContext, "是否保存数据", "", new Onclicklitener() {
                        @Override
                        public void confirm(String string) {
                            clickStatus = null;
                            saveSafetyCheckDelByApp();
                        }

                        @Override
                        public void cancel(String string) {
                        }
                    });
                }
                break;
            case R.id.tab_next:
                if (page != pagelist.size() - 1) {
                    if (toolbartext.getVisibility() == View.VISIBLE) {
                        if ("保存".equals(toolbartext.getText().toString())) {
                            if (!TextUtils.isEmpty(score.getText().toString())) {
                                clickStatus = "next";
                                saveSafetyCheckDelByApp();
                            } else {
                                page++;
                                isLeveOption = pagelist.get(page).isLeveOption();
                                initCheckItem();
                            }
                        } else {
                            page++;
                            isLeveOption = pagelist.get(page).isLeveOption();
                            initCheckItem();
                        }
                    } else {
                        page++;
                        isLeveOption = pagelist.get(page).isLeveOption();
                        initCheckItem();
                    }
                } else {
                    ToastUtils.showShortToast("已经是最后一项了");
                }

                break;
            case R.id.tab_previous:
                if (page > 0) {
                    if (toolbartext.getVisibility() == View.VISIBLE) {
                        if ("保存".equals(toolbartext.getText().toString())) {
                            if (!TextUtils.isEmpty(score.getText().toString())) {
                                clickStatus = "top";
                                saveSafetyCheckDelByApp();
                            } else {
                                page--;
                                isLeveOption = pagelist.get(page).isLeveOption();
                                initCheckItem();
                            }
                        } else {
                            page--;
                            isLeveOption = pagelist.get(page).isLeveOption();
                            initCheckItem();
                        }
                    } else {
                        page--;
                        isLeveOption = pagelist.get(page).isLeveOption();
                        initCheckItem();
                    }
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

    /**
     * 说明：添加头部布局
     * 创建时间： 2020/6/10 0010 14:16
     *
     * @author winelx
     */
    @SuppressLint("InflateParams")
    public View getHeaderView() {
        View headerView = getLayoutInflater().inflate(R.layout.adapter_check_detail_header, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        headerContet = headerView.findViewById(R.id.header_contet);
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
        TextView footerTitle = footerView.findViewById(R.id.footer_title);
        footerManger = footerView.findViewById(R.id.footer_manger);
        scoreText = footerView.findViewById(R.id.score_text);
        switch (checkLevel) {
            case "3":
                footerTitle.setText("集团公司质安部(A)");
                break;
            case "2":
                footerTitle.setText("分公司质安科(B)");
                break;
            case "1":
                footerTitle.setText("项目质安部(C)");

                footerManger.setVisibility(View.GONE);
                break;
            default:
                break;
        }
        if (checkLevel.equals(level)) {
            scoreText.setText("自评分");
        }else {
            scoreText.setText("评分");
        }
        scoreWarning = footerView.findViewById(R.id.score_warning);
        recPhoto = footerView.findViewById(R.id.rec_photo);
        footerSwitch = footerView.findViewById(R.id.footer_switch);
        describe = footerView.findViewById(R.id.describe);
        checkstandard = footerView.findViewById(R.id.checkstandard);
        standardscore = footerView.findViewById(R.id.standardscore);
        mangerScore = footerView.findViewById(R.id.manger_score);
        score = footerView.findViewById(R.id.score);
        orgEditext = footerView.findViewById(R.id.org_editext);
        recPhoto.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new BasePhotoAdapter(mContext, photoPaths);
        recPhoto.setAdapter(adapter);
        importOrg = footerView.findViewById(R.id.import_org);
        //如果创建层级和检查层级相同，那么就不能对管理扣分
        if (checkLevel.equals(level)) {
            footerManger.setVisibility(View.GONE);
        }
        importOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ExternalTreeActivity.class);
                intent.putExtra("orgid", orgid);
                startActivityForResult(intent, Enum.RESULT);
            }
        });
        adapter.setOnItemClickListener(new BasePhotoAdapter.OnItemClickListener() {
            @Override
            public void addlick(View view, int position) {
                permisssion();
            }

            @Override
            public void delete(int position) {
                photoBean bean = photoPaths.get(position);
                if (!TextUtils.isEmpty(bean.getPhotoname())) {
                    detelist.add(bean.getPhototype());
                }
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
        } else if (requestCode == Enum.RESULT) {
            if (data != null) {
                orgEditext.setText(data.getStringExtra("content"));
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

    /**
     * 说明：获取外业检查细项实体结对
     * 创建时间： 2020/7/3 0003 15:24
     *
     * @author winelx
     */
    public void getSafetyCheckDelByApp(String id) {
        externalModel.getSafetyCheckDelByApp(id, new NetworkAdapter() {
            @Override
            public void onsuccess(Object object) {
                super.onsuccess(object);
                Map<String, Object> map = (Map<String, Object>) object;
                setPermission(map);
            }

            @Override
            public void onerror(String string) {
                super.onerror(string);
                ToastUtils.showShortToast(string);
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
                pagelist.clear();
                pagelist = (List<CheckNewBean.scorePane>) object;
                gridAdapter.setNewData(pagelist);
                setTitle();
                LiveDataBus.get().with("ex_grid").setValue(pagelist);
            }
        });
    }

    /**
     * 说明：初始化footercontent 的内容
     * 创建时间： 2020/7/6 0006 15:53
     *
     * @author winelx
     */
    public void setFooterContent(Map<String, Object> map) {
        if (Integer.parseInt(status) <= 3) {
            switch (checkLevel) {
                case "1":
                    CheckDetailBean.Project project = (CheckDetailBean.Project) map.get("project");
                    //标准分
                    standardscore.setText(TextUtils.isEmpty(Utils.isNull(project.getbStandardScore())) ? "0" : project.getbStandardScore());
                    //考核扣分标准
                    checkstandard.setText(Utils.isNull(project.getbCheckStandard()));
                    //评分
                    try {
                        score.setText(Utils.isNull(project.getbScore()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //位置
                    orgEditext.setText(Utils.isNull(project.getbPosition()));
                    //具体描述
                    describe.setText(Utils.isNull(project.getbDescription()));
                    headerContet.setText(Utils.isNull(project.getName()));
                    //是否整改
                    if (project.getbGenerate() != null) {
                        if ("2".equals(project.getbGenerate())) {
                            footerSwitch.setChecked(true);
                        } else {
                            footerSwitch.setChecked(false);
                        }
                    }
                    if (edStatus) {
                        if (TextUtils.isEmpty(project.getbScore())) {
                            controlFooter(project.getCheckGrade(), true);
                        } else {
                            controlFooter(project.getCheckGrade(), false);
                        }

                    } else {
                        setEnabled(false);
                    }
                    photoPaths = getPhoto(project.getBFileList(), null, null);
                    adapter.getData(photoPaths);
                    break;
                case "2":
                    //分公司
                    CheckDetailBean.Company company = (CheckDetailBean.Company) map.get("company");
                    //管理扣分
                    mangerScore.setText(company.getbCheckScore());
                    //标准分
                    standardscore.setText(Utils.isNull(company.getfStandardScore()));
                    //考核扣分标准
                    checkstandard.setText(Utils.isNull(company.getfCheckStandard()));
                    //评分
                    try {
                        score.setText(Utils.isNull(company.getfScore()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //位置
                    orgEditext.setText(Utils.isNull(company.getfPosition()));
                    headerContet.setText(Utils.isNull(company.getName()));
                    //具体描述
                    describe.setText(Utils.isNull(company.getfDescription()));
                    //是否整改
                    if (company.getfGenerate() != null) {
                        if ("2".equals(company.getfGenerate())) {
                            footerSwitch.setChecked(true);
                        } else {
                            footerSwitch.setChecked(false);
                        }
                    }
                    if (edStatus) {
                        if (TextUtils.isEmpty(company.getfScore())) {
                            controlFooter(company.getCheckGrade(), true);
                        } else {
                            controlFooter(company.getCheckGrade(), false);
                        }
                    } else {
                        setEnabled(false);
                    }
                    photoPaths = getPhoto(null, company.getFFileList(), null);
                    adapter.getData(photoPaths);
                    break;
                case "3":
                    //集团
                    CheckDetailBean.Group group = (CheckDetailBean.Group) map.get("group");
                    //管理扣分
                    if (!TextUtils.isEmpty(Utils.isNull(group.getfCheckScore()))) {

                    } else {
                        mangerScore.setText("0");
                    }
                    //标准分
                    standardscore.setText(Utils.isNull(group.getjStandardScore()));
                    //考核扣分标准
                    checkstandard.setText(Utils.isNull(group.getjCheckStandard()));
                    //评分
                    try {
                        score.setText(Utils.isNull(group.getjScore()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //位置
                    orgEditext.setText(Utils.isNull(group.getjPosition()));
                    //具体描述
                    describe.setText(Utils.isNull(group.getjDescription()));
                    headerContet.setText(Utils.isNull(group.getName()));
                    //是否整改
                    if (group.getjGenerate() != null) {
                        if ("2".equals(group.getjGenerate())) {
                            footerSwitch.setChecked(true);
                        } else {
                            footerSwitch.setChecked(false);
                        }
                    }
                    if (edStatus) {
                        if (TextUtils.isEmpty(group.getjScore())) {
                            controlFooter(group.getCheckGrade(), true);
                        } else {
                            controlFooter(group.getCheckGrade(), false);
                        }
                    } else {
                        setEnabled(false);
                    }
                    photoPaths = getPhoto(null, null, group.getJFileList());
                    adapter.getData(photoPaths);
                    break;
                default:
                    break;
            }
        } else {
            if (map.containsKey("project")) {
                CheckDetailBean.Project project = (CheckDetailBean.Project) map.get("project");
                headerContet.setText(Utils.isNull(project.getName()));
            } else if (map.containsKey("company")) {
                CheckDetailBean.Company company = (CheckDetailBean.Company) map.get("company");
                headerContet.setText(Utils.isNull(company.getName()));
            } else if (map.containsKey("group")) {
                CheckDetailBean.Group group = (CheckDetailBean.Group) map.get("group");
                headerContet.setText(Utils.isNull(group.getName()));
            }
        }
    }

    /**
     * 说明：保存界面数据
     * 创建时间： 2020/7/6 0006 15:54
     *
     * @author winelx
     */
    public void saveSafetyCheckDelByApp() {
        Map<String, String> map = new HashMap<>();
        if (Integer.parseInt(checkLevel) > 1) {
            if (!TextUtils.isEmpty(standardscore.getText().toString()) && !TextUtils.isEmpty(mangerScore.getText().toString())) {
                int num = Integer.parseInt(mangerScore.getText().toString());
                int snum = Integer.parseInt(standardscore.getText().toString());
                if (num > snum) {
                    ToastUtils.showShortToastCenter("管理扣分不能大于标准分");
                    return;
                } else {
                    //管理扣分
                    if ("3".equals(checkLevel)) {
                        map.put("fCheckScore", mangerScore.getText().toString());
                    } else if ("2".equals(checkLevel)) {
                        map.put("bCheckScore", mangerScore.getText().toString());
                    }
                }
            } else {
                //管理扣分
                if ("3".equals(checkLevel)) {
                    map.put("fCheckScore", mangerScore.getText().toString());
                } else if ("2".equals(checkLevel)) {
                    map.put("bCheckScore", mangerScore.getText().toString());
                }
            }

        }
        map.put("safetyCheckId", checkid);
        map.put("id", scoreid);
        //评分
        if (scoreWarning.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(score.getText().toString())) {
                int num = Integer.parseInt(score.getText().toString());
                int snum = Integer.parseInt(standardscore.getText().toString());
                if (num > snum) {
                    ToastUtils.showShortToastCenter("评分不能大于标准分");
                    return;
                } else {
                    map.put(replace("jScore"), score.getText().toString());
                }
            } else {
                ToastUtils.showShortToast("评分不能为空");
                return;
            }
        }
        //位置
        map.put(replace("jPosition"), orgEditext.getText().toString());
        //具体描述
        map.put(replace("jDescription"), describe.getText().toString());
        //z是否整改
        map.put(replace("jGenerate"), footerSwitch.isChecked() ? "2" : "1");
        map.put(replace("bdeleteFileId"), Dates.listToStrings(detelist));
        map.put("checkLevel", checkLevel);
        ArrayList<File> files = new ArrayList<>();
        if (photoPaths.size() > 0) {
            for (int i = 0; i < photoPaths.size(); i++) {
                photoBean bean = photoPaths.get(i);
                if (bean.getPhotoname() == null) {
                    files.add(new File(bean.getPhotopath()));
                }
            }
        }
        Dates.getDialogs(this, "保存数据中...");
        externalModel.saveSafetyCheckDelByApp(map, files, new NetworkAdapter() {
            @Override
            public void onsuccess(String string) {
                super.onsuccess(string);
                Dates.disDialog();
                ToastUtils.showShortToast("保存成功");
                //更新记分面板
                getScorePane();
                if (clickStatus != null) {
                    isLeveOption = pagelist.get(page).isLeveOption();
                    if ("next".equals(clickStatus)) {
                        page++;
                    } else {
                        page--;
                    }
                    initCheckItem();
                } else {
                    getSafetyCheckDelByApp(scoreid);
                }
            }

            @Override
            public void onerror() {
                super.onerror();
                Dates.disDialog();
            }
        });
    }

    /**
     * 说明：字符提换
     * 创建时间： 2020/7/6 0006 17:16
     *
     * @author winelx
     */
    public String replace(String str) {
        if ("1".equals(checkLevel)) {
            StringBuilder project = new StringBuilder(str);
            project.replace(0, 1, "b");
            return project.toString();
        } else if ("2".equals(checkLevel)) {
            StringBuilder company = new StringBuilder(str);
            company.replace(0, 1, "f");
            return company.toString();
        } else if ("3".equals(checkLevel)) {
            StringBuilder stringBuilder = new StringBuilder(str);
            stringBuilder.replace(0, 1, "j");
            return stringBuilder.toString();
        }
        return null;
    }

    /**
     * 说明：界面是否可编辑
     * 创建时间： 2020/7/6 0006 17:16
     *
     * @author winelx
     */
    public void setEnabled(boolean lean) {
        if (Integer.parseInt(status) <= 3) {
            orgEditext.setEnabled(lean);
            adapter.addview(lean);
            score.setEnabled(lean);
            if (scoreWarning.getVisibility() == View.VISIBLE) {
                score.setEnabled(lean);
            } else {
                score.setEnabled(false);
            }
            describe.setEnabled(lean);
            footerSwitch.setClickable(lean);
            mangerScore.setEnabled(lean);
            if (edStatus) {
                if (lean) {
                    toolbartext.setText("保存");
                    importOrg.setVisibility(View.VISIBLE);
                    describe.setHint("请输入");
                    score.setHint("请输入");
                    mangerScore.setHint("请输入");
                    orgEditext.setHint("请输入");
                } else {
                    toolbartext.setText("编辑");
                    describe.setHint("");
                    score.setHint("");
                    mangerScore.setHint("");
                    orgEditext.setHint("");
                    importOrg.setVisibility(View.GONE);
                }
            } else {
                describe.setHint("");
                score.setHint("");
                mangerScore.setHint("");
                orgEditext.setHint("");
                toolbartext.setVisibility(View.GONE);
                importOrg.setVisibility(View.GONE);
            }

        }
    }

    /**
     * 说明：切换检查项界面初始化
     * 创建时间： 2020/7/7 0007 11:14
     *
     * @author winelx
     */
    public void initCheckItem() {
        setTitle();
        reset();
        if (!TextUtils.isEmpty(Utils.isNull(pagelist.get(page).getScore()))) {
            setEnabled(false);
        } else {
            setEnabled(true);
        }
        scoreid = pagelist.get(page).getId();
        getSafetyCheckDelByApp(scoreid);
    }

    /**
     * 说明：切换界面后，重置界面内容
     * 创建时间： 2020/6/30 0030 14:47
     *
     * @author winelx
     */
    public void reset() {
        //如果必须小于等于3，footer没有初始化，会报错
        if (Integer.parseInt(status) <= 3) {
            detelist.clear();
            photoPaths.clear();
            adapter.getData(photoPaths);
            describe.setText("");
            orgEditext.setText("");
            mangerScore.setText("");
            score.setText("");
            footerSwitch.setChecked(false);
        }

    }

    /**
     * 说明：界面内容显示控制
     * 创建时间： 2020/7/6 0006 14:56
     *
     * @author winelx
     */
    private void setPermission(Map<String, Object> map) {
        //level 控制显示内容
        detailAdapter.getlevel(level,checkLevel);
        List<Object> data = new ArrayList<>();
        //如果状态大于等于3进入确认流程，无需编辑
        if ("1".equals(level)) {
            //已经检查完成
            if (Integer.parseInt(status) > 3) {
                data.add(map.get("project"));
                data.add(map.get("company"));
                data.add(map.get("group"));
            } else {
                //没有检测完成，现在处在什么层级检查
                if (Enum.STATUS_THREE.equals(checkLevel)) {
                    //集团检查不用显示集团部分
                    data.add(map.get("project"));
                    data.add(map.get("company"));
                } else if (Enum.STATUS_TWO.equals(checkLevel)) {
                    //分公司不用显示分公司部分，
                    data.add(map.get("project"));
                }
            }
        } else if (Enum.STATUS_TWO.equals(level)) {
            //已经检查完成
            if (Integer.parseInt(status) > 3) {
                data.add(map.get("company"));
                data.add(map.get("group"));
            } else {
                //集团检查不用显示集团部分
                if (Enum.STATUS_THREE.equals(checkLevel)) {
                    data.add(map.get("company"));
                }
            }
        } else if (Enum.STATUS_THREE.equals(level)) {
            //是否已经检查完成
            if (Integer.parseInt(status) > 3) {
                data.add(map.get("group"));
            }
        }
        detailAdapter.setNewData(data);
        //判断是否有分数，如果有分数，已经处理过，界面改为不可编辑状态
        setFooterContent(map);

    }

    /**
     * 说明：设置标题显示内容
     * 创建时间： 2020/7/7 0007 14:33
     *
     * @author winelx
     */
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
     * 说明：图片展示
     * 创建时间： 2020/7/7 0007 14:59
     *
     * @author winelx
     */
    public ArrayList<photoBean> getPhoto(List<CheckDetailBean.Project.BFileListBean> project,
                                         List<CheckDetailBean.Company.FFileListBean> company,
                                         List<CheckDetailBean.Group.JFileListBean> group) {

        ArrayList<photoBean> list = new ArrayList<>();
        //标段
        if (project != null) {
            for (int i = 0; i < project.size(); i++) {
                CheckDetailBean.Project.BFileListBean beans = project.get(i);
                list.add(new photoBean(Requests.networks + "/" + beans.getFilepath(), beans.getFilename(), beans.getId()));
            }
        }
        //分公司
        if (company != null) {
            for (int i = 0; i < company.size(); i++) {
                CheckDetailBean.Company.FFileListBean beans = company.get(i);
                list.add(new photoBean(Requests.networks + "/" + beans.getFilepath(), beans.getFilename(), beans.getId()));
            }
        }
        //集团
        if (group != null) {
            for (int i = 0; i < group.size(); i++) {
                CheckDetailBean.Group.JFileListBean beans = group.get(i);
                list.add(new photoBean(Requests.networks + "/" + beans.getFilepath(), beans.getFilename(), beans.getId()));
            }
        }
        return list;
    }

    public void controlFooter(String checkGrade, boolean lean) {
        if ("1".equals(checkGrade)) {
            if ("1".equals(checkLevel)) {
                //判断是否有编辑权限
                if (edStatus) {
                    //自评分（必填）及其他项
                    //导入
                    orgEditext.setEnabled(lean);
                    importOrg.setVisibility(lean ? View.VISIBLE : View.GONE);
                    //图片
                    adapter.addview(lean);
                    //自评分
                    score.setEnabled(lean);
                    scoreWarning.setVisibility(View.VISIBLE);
                    if (lean) {
                        score.setHint("请输入");
                    } else {
                        score.setHint("");
                    }
                    //内容
                    describe.setEnabled(lean);
                    //是否整改
                    footerSwitch.setClickable(lean);
                    footerManger.setVisibility(View.GONE);
                    if (lean) {
                        toolbartext.setText("保存");
                        setEnabled(true);
                        importOrg.setVisibility(View.VISIBLE);
                    } else {
                        toolbartext.setText("编辑");
                        setEnabled(false);
                        importOrg.setVisibility(View.GONE);
                    }
                } else {
                    setEnabled(false);
                }
            } else if ("2".equals(checkLevel) || "3".equals(checkLevel)) {
                //判断是否有编辑权限
                if (edStatus) {
                    //自评分（必填）及其他项
                    //导入
                    orgEditext.setEnabled(lean);
                    importOrg.setVisibility(lean ? View.VISIBLE : View.GONE);
                    //图片
                    adapter.addview(lean);
                    //自评分
                    score.setEnabled(lean);
                    scoreWarning.setVisibility(View.VISIBLE);
                    if (lean) {
                        score.setHint("请输入");
                    } else {
                        score.setHint("");
                    }
                    //内容
                    describe.setEnabled(lean);
                    //是否整改
                    footerSwitch.setClickable(lean);
                    if (checkLevel.equals(level)) {
                        footerManger.setVisibility(View.GONE);
                        mangerScore.setEnabled(false);
                    } else {
                        footerManger.setVisibility(View.VISIBLE);
                        mangerScore.setEnabled(lean);
                    }
                    if (lean) {
                        toolbartext.setText("保存");
                        setEnabled(true);
                        importOrg.setVisibility(View.VISIBLE);
                    } else {
                        toolbartext.setText("编辑");
                        setEnabled(false);
                        importOrg.setVisibility(View.GONE);
                    }
                } else {
                    setEnabled(false);
                }
            }
        } else if ("2".equals(checkGrade)) {
            if ("1".equals(checkLevel) || "2".equals(checkLevel)) {
                //判断是否有编辑权限
                if (edStatus) {
                    //自评分（必填）及其他项
                    //导入
                    orgEditext.setEnabled(lean);
                    importOrg.setVisibility(lean ? View.VISIBLE : View.GONE);
                    //图片
                    adapter.addview(lean);
                    //自评分
                    score.setEnabled(lean);
                    scoreWarning.setVisibility(View.VISIBLE);
                    if (lean) {
                        score.setHint("请输入");
                    } else {
                        score.setHint("");
                    }
                    //内容
                    describe.setEnabled(lean);
                    //是否整改
                    footerSwitch.setClickable(lean);
                    if ("1".equals(checkLevel)) {
                        footerManger.setVisibility(View.GONE);
                    } else if ("2".equals(checkLevel)) {
                        if (checkLevel.equals(level)) {
                            footerManger.setVisibility(View.GONE);
                            mangerScore.setEnabled(false);
                        } else {
                            footerManger.setVisibility(View.VISIBLE);
                            mangerScore.setEnabled(lean);
                        }
                    }
                    if (lean) {
                        toolbartext.setText("保存");
                        setEnabled(true);
                        importOrg.setVisibility(View.VISIBLE);
                    } else {
                        toolbartext.setText("编辑");
                        setEnabled(false);
                        importOrg.setVisibility(View.GONE);
                    }
                } else {
                    setEnabled(false);
                }
            } else if ("3".equals(checkLevel)) {
                //判断是否有编辑权限
                if (edStatus) {
                    //自评分（必填）及其他项
                    //导入
                    orgEditext.setEnabled(false);
                    //图片
                    adapter.addview(false);
                    //自评分
                    score.setEnabled(false);
                    scoreWarning.setVisibility(View.INVISIBLE);
                    score.setHint("");
                    //内容
                    describe.setEnabled(false);
                    //是否整改
                    footerSwitch.setClickable(false);
                    if (checkLevel.equals(level)) {
                        footerManger.setVisibility(View.GONE);
                        mangerScore.setEnabled(false);
                    } else {
                        footerManger.setVisibility(View.VISIBLE);
                        mangerScore.setEnabled(lean);
                    }
                    toolbartext.setText("编辑");
                    importOrg.setVisibility(View.GONE);
                } else {
                    setEnabled(false);
                }
            }
        } else if ("3".equals(checkGrade)) {
            // 只有标段填写自评分（必填）及其他项，分公司进来可填写除了自评分以外的全部项
            if ("1".equals(checkLevel)) {
                //判断是否有编辑权限
                if (edStatus) {
                    //自评分（必填）及其他项
                    //导入
                    orgEditext.setEnabled(lean);
                    importOrg.setVisibility(lean ? View.VISIBLE : View.GONE);
                    //图片
                    adapter.addview(lean);
                    //自评分
                    score.setEnabled(lean);
                    scoreWarning.setVisibility(View.VISIBLE);
                    if (lean) {
                        score.setHint("请输入");
                    } else {
                        score.setHint("");
                    }
                    //内容
                    describe.setEnabled(lean);
                    //是否整改
                    footerSwitch.setClickable(lean);
                    footerManger.setVisibility(View.GONE);
                    if (lean) {
                        toolbartext.setText("保存");
                        setEnabled(true);
                        importOrg.setVisibility(View.VISIBLE);
                    } else {
                        toolbartext.setText("编辑");
                        setEnabled(false);
                        importOrg.setVisibility(View.GONE);
                    }
                } else {
                    setEnabled(false);
                }
            } else if ("2".equals(checkLevel)) {
                // 分公司进来可填写除了自评分以外的全部项
                //判断是否有编辑权限
                if (edStatus) {
                    //自评分（必填）及其他项
                    //导入
                    orgEditext.setEnabled(false);
                    //图片
                    adapter.addview(false);
                    //自评分
                    score.setEnabled(false);
                    scoreWarning.setVisibility(View.INVISIBLE);
                    score.setHint("");
                    //内容
                    describe.setEnabled(false);
                    //是否整改
                    footerSwitch.setClickable(false);
                    if (checkLevel.equals(level)) {
                        footerManger.setVisibility(View.GONE);
                        mangerScore.setEnabled(false);
                    } else {
                        footerManger.setVisibility(View.VISIBLE);
                        mangerScore.setEnabled(lean);
                    }
                    toolbartext.setText("编辑");
                    setEnabled(false);
                    importOrg.setVisibility(View.GONE);
                } else {
                    setEnabled(false);
                }
            }
        }
    }

}
