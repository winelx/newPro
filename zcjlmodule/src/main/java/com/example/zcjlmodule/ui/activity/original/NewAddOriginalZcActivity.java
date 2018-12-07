package com.example.zcjlmodule.ui.activity.original;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.zcjlmodule.R;
import com.example.zcjlmodule.callback.NewAddCallback;
import com.example.zcjlmodule.callback.NewAddOriginalUtils;
import com.example.zcjlmodule.callback.OriginalZcCallBackUtils;
import com.example.zcjlmodule.presenter.NewAddOriginalPresenter;
import com.example.zcjlmodule.ui.activity.original.enclosure.ApplyDateZcActivity;
import com.example.zcjlmodule.ui.activity.original.enclosure.ChoiceBidsZcActivity;
import com.example.zcjlmodule.ui.activity.original.enclosure.ChoiceHeadquartersZcActivity;
import com.example.zcjlmodule.ui.activity.original.enclosure.ChoiceProjectZcActivity;
import com.example.zcjlmodule.ui.activity.original.enclosure.StandardDecomposeZcActivity;
import com.example.zcjlmodule.utils.activity.ExamineDismantlingUtils;
import com.example.zcjlmodule.view.NewAddOriginalView;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.PhotoPicker;
import measure.jjxx.com.baselibrary.adapter.PhotoPreview;
import measure.jjxx.com.baselibrary.adapter.PhotosAdapter;
import measure.jjxx.com.baselibrary.base.BaseMvpActivity;
import measure.jjxx.com.baselibrary.bean.ExamineBean;
import measure.jjxx.com.baselibrary.utils.BaseDialogUtils;
import measure.jjxx.com.baselibrary.utils.BaseUtils;
import measure.jjxx.com.baselibrary.utils.PhotoUtils;
import measure.jjxx.com.baselibrary.utils.PopCameraUtils;
import measure.jjxx.com.baselibrary.utils.TakePictureManager;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;
import measure.jjxx.com.baselibrary.utils.WindowUtils;


/**
 * description: 新增原始勘丈表
 *
 * @author lx
 *         date: 2018/10/16 0016 下午 2:29
 *         跳转界面：OriginalZcActivity
 */
public class NewAddOriginalZcActivity extends BaseMvpActivity<NewAddOriginalPresenter> implements View.OnClickListener, NewAddOriginalView, NewAddCallback {
    private Context mContext;
    private TextView title;
    private RecyclerView photrecycler;
    //图片展示adapter
    private PhotosAdapter mPhotosAdapter;
    //返回的图片
    private ArrayList<ExamineBean> pathlist;
    //图标
    private ArrayList<ImageView> imagelist;
    //输入框
    private ArrayList<EditText> editTexlist;
    //传递过来的参数map
    private HashMap<String, String> message;
    //调用相机的manager
    private TakePictureManager takePictureManager;
    //根据status 类处理当前界面点击事件或者输入框是否可点击或者编辑
    private boolean status = true;
    //复制新增状态判断 区别是保存还是复制新增
    private boolean copylean = false;
    /**
     * 界面控件
     */
    //所属项目  所属标段  指挥部  界面下排功能按钮
    private LinearLayout newAddOriginalProject, newAddOriginalBids, newAddOriginalCommand, newAddOriginalFunction;
    //标准分解   申报期数  标题栏保存按钮
    private LinearLayout standarddecomposition, newAddOriginalApplydate, toolbarIconText;
    //省 城 县 镇  修改
    private TextView provincename, cityname, countyname, townname;
    //所属项目名称   所属标段名称  指挥部名称   征拆类型    单据编号
    private TextView newAddOriginalProjectname, newAddOriginalBidstext, newAddOriginalCommandtext, newAddOriginalCategory, originalDonumber;
    // 申报期数  计量单位   总金额   单价  标准分解
    private TextView newAddOriginalApplydateText, meterunitname, totalPrice, price, standardDetailNumber;
    //原始单号 申报数量
    private EditText newAddOriginalOriginalnumber, declareNum, remarks;
    //图标
    private ImageView newAddOriginalProjectnameImage, newAddOriginalBidstextImage, newAddOriginalCommandtextImage, newAddOriginalStandarddImage, newAddOriginalApplydateImage;
    //户主名字，省份证号码，电话，受益人 地址
    private EditText newAddOriginalName, newAddOriginalNumber, newAddOriginalPhone, newAddOriginalBeneficiary, detailAddress;
    //orgid
    private String orgId = "", Id = "";
    //类型 判断是新建还是传递参数展示
    private String type = "";
    private Intent intent;
    //所属项目ID 所属标段ID  指挥部Id  申报期数ID  分解标准Id
    private String ProjectId, BidsId, CommandId, numberId, standardDetail;
    //滑动控件
    private ScrollView scrollView;
    private BaseUtils utils;
    private Tiny.FileCompressOptions options;
    private ExamineDismantlingUtils dismantlingUtils;
    //删除图片集合
    private ArrayList<String> deleteList;
    /**
     * 保存数据
     */
    String Number, OriginalNam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_add_original_zc);
        mContext = this;
        utils = new BaseUtils();
        NewAddOriginalUtils.setCallBack(this);
        dismantlingUtils = new ExamineDismantlingUtils();
        //实例presenter
        mPresenter = new NewAddOriginalPresenter();
        //实例presenter
        mPresenter.mView = this;
        pathlist = new ArrayList<>();
        deleteList = new ArrayList<>();
        imagelist = new ArrayList<>();
        editTexlist = new ArrayList<>();
        //压缩图片
        options = new Tiny.FileCompressOptions();
        intent = getIntent();
        type = intent.getStringExtra("type");
        orgId = intent.getStringExtra("orgId");
        findId();
        init();
    }

    /**
     * 初始化控件
     */
    @SuppressLint({"WrongViewCast", "CutPasteId"})
    private void findId() {
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        newAddOriginalFunction = (LinearLayout) findViewById(R.id.new_add_original_function);
        //修改
        findViewById(R.id.new_add_original_modify).setOnClickListener(this);
        //复制新增
        findViewById(R.id.new_add_original_copy).setOnClickListener(this);
        //图标
        newAddOriginalProjectnameImage = (ImageView) findViewById(R.id.new_add_original_projectname_image);
        newAddOriginalBidstextImage = (ImageView) findViewById(R.id.new_add_original_bidstext_image);
        newAddOriginalCommandtextImage = (ImageView) findViewById(R.id.new_add_original_commandtext_image);
        newAddOriginalStandarddImage = (ImageView) findViewById(R.id.new_add_original_standardd_image);
        newAddOriginalApplydateImage = (ImageView) findViewById(R.id.new_add_original_applydate_image);
        //添加需要进行控制的图标
        imagelist.add(newAddOriginalProjectnameImage);
        imagelist.add(newAddOriginalBidstextImage);
        imagelist.add(newAddOriginalCommandtextImage);
        imagelist.add(newAddOriginalStandarddImage);
        imagelist.add(newAddOriginalApplydateImage);
        //输入框
        newAddOriginalName = (EditText) findViewById(R.id.new_add_original_name);
        newAddOriginalNumber = (EditText) findViewById(R.id.new_add_original_number);
        newAddOriginalPhone = (EditText) findViewById(R.id.new_add_original_phone);
        newAddOriginalBeneficiary = (EditText) findViewById(R.id.new_add_original_beneficiary);
        detailAddress = (EditText) findViewById(R.id.new_add_original_detailAddress);
        remarks = (EditText) findViewById(R.id.new_add_original_remarks);
        //保存
        toolbarIconText = (LinearLayout) findViewById(R.id.toolbar_text_text);
        toolbarIconText.setOnClickListener(this);
        //申报数量
        declareNum = (EditText) findViewById(R.id.new_add_original_declareNum);
        //户主
        editTexlist.add(newAddOriginalName);
        //身份证
        editTexlist.add(newAddOriginalNumber);
        //手机号
        editTexlist.add(newAddOriginalPhone);
        //受益人
        editTexlist.add(newAddOriginalBeneficiary);
        //详细地址
        editTexlist.add(detailAddress);
        editTexlist.add(remarks);
        editTexlist.add(declareNum);
        //省
        provincename = (TextView) findViewById(R.id.new_add_original_provinceName);
        //城市
        cityname = (TextView) findViewById(R.id.new_add_original_cityName);
        //区县
        countyname = (TextView) findViewById(R.id.new_add_original_countyName);
        //乡镇
        townname = (TextView) findViewById(R.id.new_add_original_townName);
        //计量单位
        meterunitname = (TextView) findViewById(R.id.new_add_original_meterUnitName);
        //征拆类型
        newAddOriginalCategory = (TextView) findViewById(R.id.new_add_original_category);
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
        standarddecomposition = (LinearLayout) findViewById(R.id.new_add_original_standarddecomposition);
        standarddecomposition.setOnClickListener(this);
        //原始单号
        newAddOriginalOriginalnumber = (EditText) findViewById(R.id.new_add_original_originalnumber);
        editTexlist.add(newAddOriginalOriginalnumber);
        //申报期数
        newAddOriginalApplydate = (LinearLayout) findViewById(R.id.new_add_original_applydate);
        //金额
        totalPrice = (TextView) findViewById(R.id.new_add_original_totalPrice);
        //单价
        price = (TextView) findViewById(R.id.new_add_original_price);
        //单据编号
        originalDonumber = (TextView) findViewById(R.id.new_add_original_donumber);
        //标准分解
        standardDetailNumber = (TextView) findViewById(R.id.new_add_original_standarddecompositiontext);
        newAddOriginalApplydate.setOnClickListener(this);
        //申报期数文字
        newAddOriginalApplydateText = (TextView) findViewById(R.id.new_add_original_applydate_text);
        findViewById(R.id.toolbar_icon_back).setOnClickListener(this);
        title = (TextView) findViewById(R.id.toolbar_icon_title);
        photrecycler = (RecyclerView) findViewById(R.id.newaddoriginal_recycler);
    }

    /**
     * 初始化数据
     */
    @SuppressLint("SetTextI18n")
    private void init() {
        ArrayList<String> data = new ArrayList<>();
        mPhotosAdapter = new PhotosAdapter(this, data, true);
        if (type.equals("old")) {
            hide();
            title.setText("查看原始勘丈表");
            //或者传递过来的参数集合
            message = (HashMap<String, String>) intent.getSerializableExtra("message");
            //获取ID
            Id = message.get("id");
            //单据编号
            originalDonumber.setText(message.get("number"));
            //原始单号
            newAddOriginalOriginalnumber.setText(message.get("rawNumber"));
            //所属项目
            newAddOriginalProjectname.setText(message.get("projectName"));
            //所属标段
            newAddOriginalBidstext.setText(message.get("tenderName"));
            //户主名字
            newAddOriginalName.setText(message.get("householder"));
            //征拆类型
            newAddOriginalCategory.setText(message.get("category"));
            //指挥部
            newAddOriginalCommandtext.setText(message.get("headquarterName"));
            //省
            provincename.setText(message.get("provinceName"));
            //城市
            cityname.setText(message.get("cityName"));
            //区
            countyname.setText(message.get("countyName"));
            //乡镇
            if (message.get("townName") != null && !message.get("townName").equals("null")) {
                townname.setText(message.get("townName"));
            }
            //地址
            if (message.get("detailAddress") != null) {
                detailAddress.setText(message.get("detailAddress"));
            }
            //计量单位
            meterunitname.setText(message.get("meterUnitName"));
            //申请期数
            newAddOriginalApplydateText.setText(message.get("periodName"));
            //金额
            totalPrice.setText(message.get("totalPrice"));
            //单价
            price.setText(message.get("price"));
            //身份证
            newAddOriginalNumber.setText(message.get("householderIdcard"));
            //申报数量
            declareNum.setText(message.get("declareNum"));
            //标准分解
            standardDetailNumber.setText(message.get("standardDetailNumber"));
            String remark = message.get("remarks");
            //备注
            if (!remark.isEmpty() && remark != null && !remark.equals("null")) {
                remarks.setText(remark);
            }
            //手机号
            String phone = message.get("householderPhone");
            if (!phone.isEmpty() && phone != null && !phone.equals("null")) {
                newAddOriginalPhone.setText(message.get("householderPhone"));
            }
            //征拆类
            newAddOriginalCategory.setText(message.get("levyTypeName"));
            //项目Id
            ProjectId = message.get("project");
            //标段ID
            BidsId = message.get("tender");
            //指挥部ID
            CommandId = message.get("headquarter");
            //申报期数ID
            numberId = message.get("period");
            //分解标准Id
            standardDetail = message.get("standardDetail");
            //受益人
            String beneficiary = message.get("beneficiary");
            if (!beneficiary.isEmpty() && beneficiary != null && !beneficiary.equals("null")) {
                newAddOriginalBeneficiary.setText(message.get("beneficiary"));
            }
            dismantlingUtils.getData(message.get("id"), new ExamineDismantlingUtils.onclick() {
                @Override
                public void onSuccess(ArrayList<ExamineBean> data) {
                    pathlist.clear();
                    pathlist.addAll(data);
                    getupdata();
                    if (data.size() > 0) {
                        photrecycler.setVisibility(View.VISIBLE);
                    } else {
                        photrecycler.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onError() {

                }
            });
        } else {
            title.setText("新增原始勘丈表");
            showview();
        }
        photrecycler.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        photrecycler.setAdapter(mPhotosAdapter);
        //点击事件--- 弹窗选择相机还是相册，相机相册返回的图片--展示
        mPhotosAdapter.setOnItemClickListener(new PhotosAdapter.OnItemClickListener() {
            @Override
            public void addlick(View view, int position) {
                //添加图片
                PopCameraUtils popCameraUtils = new PopCameraUtils();
                popCameraUtils.showPopwindow(NewAddOriginalZcActivity.this, new PopCameraUtils.CameraCallback() {
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
                                            pathlist.add(new ExamineBean("", "", outfile, "jpg"));
                                            getupdata();
                                            try {
                                                WindowUtils.delete(outFile);
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
                        } else {
                            PhotoPicker.builder()
                                    .setPhotoCount(9)
                                    .setShowCamera(true)
                                    .setShowGif(true)
                                    .setPreviewEnabled(false)
                                    .start(NewAddOriginalZcActivity.this, PhotoPicker.REQUEST_CODE);
                        }
                    }
                });
            }

            //点击图片查看
            @Override
            public void photoClick(View view, int position) {
                PhotoPreview.builder().setPhotos(PhotoUtils.getPhoto(pathlist, false)).setCurrentItem(position).start((Activity) mContext);
            }

            //删除图片
            @Override
            public void deleteClick(View view, int position) {
                String ids = pathlist.get(position).getId();
                if (ids != null) {
                    deleteList.add(ids);
                }
                pathlist.remove(position);
                getupdata();
            }
        });
        /**
         * 输入框监听
         */
        declareNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //获取输入内容
                String content = s.toString();
                String pricetext = price.getText().toString();
                boolean lean = mPresenter.isNumber(content);
                // 现在创建 matcher 对象
                if (lean) {
                    if (!TextUtils.isEmpty(pricetext)) {
                        //存在小数
                        BigDecimal num = new BigDecimal(content);
                        BigDecimal pricenum = new BigDecimal(pricetext);
                        //数量乘单价
                        BigDecimal maxnum = num.multiply(pricenum).setScale(2, BigDecimal.ROUND_HALF_UP);
                        totalPrice.setText(maxnum.toString());
                    } else {
                        ToastUtlis.getInstance().showShortToast("单价为空");
                        utils.hidekeyboard(mContext, declareNum);
                    }
                } else {
                    utils.hidekeyboard(mContext, declareNum);
                    Snackbar.make(declareNum, "只能保留四位小数并且数字开头不能为小数点", Snackbar.LENGTH_LONG)
                            .setAction("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.toolbar_icon_back) {
            if (status) {
                BaseDialogUtils.getprompt(mContext, "数据未保存,是否进行存", new BaseDialogUtils.onclicktlister() {
                    @Override
                    public void onsuccess() {
                        BaseDialogUtils.alertdialog.dismiss();
                        //保存数据
                        proving();
                    }

                    @Override
                    public void onerror() {
                        finish();
                    }
                });
            } else {
                finish();
            }
        } else if (i == R.id.new_add_original_project) {
            //所属项目
            if (status) {
                Intent intent1 = new Intent(mContext, ChoiceProjectZcActivity.class);
                intent1.putExtra("orgId", orgId);
                startActivityForResult(intent1, 102);
            } else {
//                ToastUtlis.getInstance().showShortToast("当前不是编辑状态");
            }
        } else if (i == R.id.new_add_original_bids) {
            //所属标段
            if (status) {
                Intent intent1 = new Intent(mContext, ChoiceBidsZcActivity.class);
                intent1.putExtra("orgId", orgId);
                startActivityForResult(intent1, 103);
            } else {
//                ToastUtlis.getInstance().showShortToast("当前不是编辑状态");
            }
        } else if (i == R.id.new_add_original_command) {
            //指挥部
            if (status) {
                Intent intent1 = new Intent(mContext, ChoiceHeadquartersZcActivity.class);
                intent1.putExtra("orgId", orgId);
                startActivityForResult(intent1, 104);
            } else {
//                ToastUtlis.getInstance().showShortToast("当前不是编辑状态");
            }
        } else if (i == R.id.new_add_original_standarddecomposition) {
            //分解标准
            if (status) {
                Intent intent1 = new Intent(mContext, StandardDecomposeZcActivity.class);
                //判断的新增还是修改
                intent1.putExtra("type", type);
                //判断的新增还是修改
                intent1.putExtra("orgId", orgId);
                startActivity(intent1);
            } else {
//                ToastUtlis.getInstance().showShortToast("当前不是编辑状态");
            }
        } else if (i == R.id.new_add_original_applydate) {
            //申报期数
            if (status) {
                Intent intent1 = new Intent(mContext, ApplyDateZcActivity.class);
                startActivityForResult(intent1, 106);
            } else {
//                ToastUtlis.getInstance().showShortToast("当前不是编辑状态");
            }
        } else if (i == R.id.new_add_original_modify) {
            //修改
            showview();  //显示布局
            title.setText("修改原始勘丈表");
        } else if (i == R.id.toolbar_text_text) {
            copylean = false;
            //保存
            proving();
        } else if (i == R.id.new_add_original_copy) {
            copylean = true;
            proving();
        } else {
        }
    }

    /**
     * 显示控件
     */
    private void showview() {
        mPhotosAdapter.status(true);
        //图片添加按钮
        mPhotosAdapter.addview(true);
        //显示保存按钮
        toolbarIconText.setVisibility(View.VISIBLE);
        //显示图标
        mPresenter.visibility(true, imagelist);
        //输入框可获取焦点
        mPresenter.clickable(true, editTexlist);
        //修改status 状态
        status = true;
        //隐藏底部功能按钮
        newAddOriginalFunction.setVisibility(View.GONE);
        //设置控件margin
        mPresenter.setMargins(scrollView, 0, 0, 0, 0);
        photrecycler.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏控件
     */
    private void hide() {
        mPhotosAdapter.status(false);
        //图片添加按钮隐藏
        mPhotosAdapter.addview(false);
        //隐藏保存按钮
        toolbarIconText.setVisibility(View.INVISIBLE);
        //隐藏图标
        mPresenter.visibility(false, imagelist);
        //输入框不可获取焦点
        mPresenter.clickable(false, editTexlist);
        //修改status 状态
        status = false;
        //显示底部功能按钮
        newAddOriginalFunction.setVisibility(View.VISIBLE);
        mPresenter.setMargins(scrollView, 0, 0, 0, 140);

    }

    /**
     * 回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            /**
             * 调用相机的回调
             */
            try {
                takePictureManager.attachToActivityForResult(requestCode, resultCode, data);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else if (requestCode == 102 && resultCode == 102) {
            //所属项目
            newAddOriginalProjectname.setText(data.getStringExtra("name"));
            ProjectId = data.getStringExtra("id");
        } else if (requestCode == 103 && resultCode == 103) {
            //所属标段
            newAddOriginalBidstext.setText(data.getStringExtra("name"));
            BidsId = data.getStringExtra("id");
        } else if (requestCode == 104 && resultCode == 104) {
            //指挥部
            newAddOriginalCommandtext.setText(data.getStringExtra("name"));
            CommandId = data.getStringExtra("id");
        } else if (requestCode == 106 && resultCode == 106) {
            //申报期数
            newAddOriginalApplydateText.setText(data.getStringExtra("name"));
            numberId = data.getStringExtra("id");
        } else if (requestCode == PhotoPicker.REQUEST_CODE) {
            //相册选择图片返回
            try {
                ArrayList<String> images = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                for (int i = 0; i < images.size(); i++) {
                    double mdouble = WindowUtils.getDirSize(new File(images.get(i)));
                    if (mdouble != 0.0) {
                        Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                        options.quality = 95;
                        Tiny.getInstance().source(images.get(i)).asFile().withOptions(options).compress(new FileCallback() {
                            @Override
                            public void callback(boolean isSuccess, String outfile) {
                                //添加进集合
                                pathlist.add(new ExamineBean("", "", outfile, "jpg"));
                                //填入listview，刷新界面
                                getupdata();
                            }
                        });
                    } else {
                        ToastUtlis.getInstance().showLongToast("请检查上传的图片是否损坏");
                    }
                }
            } catch (NullPointerException e) {
            }
        }
    }

    /**
     * 网络请求成功
     */
    @Override
    public void OnSuccess(String str, String number) {
        //关闭提示框
        BaseDialogUtils.dialog.dismiss();
        originalDonumber.setText(number);
        //隐藏保存按钮，显示修改和复制新增
        hide();
        //当前界面数据发生改变，更新列表数据
        OriginalZcCallBackUtils.updata();
        if (copylean) {
            ToastUtlis.getInstance().showShortToast("复制新增成");
        } else {
            //将保存成功的数据id赋值给id（新的单据如果不返回id,请求图片时就id就为空）
            Id = str;
            //修改标题栏
            title.setText("查看原始勘丈表");
            //重新请求图片，避免新增的图片没Id（）
            dismantlingUtils.getData(Id, new ExamineDismantlingUtils.onclick() {
                @Override
                public void onSuccess(ArrayList<ExamineBean> data) {
                    pathlist.clear();
                    pathlist.addAll(data);
                    getupdata();
                    if (pathlist.size() > 0) {
                        photrecycler.setVisibility(View.VISIBLE);
                    } else {
                        photrecycler.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onError() {

                }
            });
        }

    }

    /**
     * 网络请求失败
     */
    @Override
    public void OnError() {
        BaseDialogUtils.dialog.dismiss();
    }

    /**
     * 征拆类型返回
     *
     * @param map
     */

    @Override
    public void callback(Map<String, String> map) {
        //征拆类型
        newAddOriginalCategory.setText(map.get("levyTypeName"));
        //标准分解
        standardDetailNumber.setText(map.get("number"));
        standardDetail = map.get("id");
        //省份
        provincename.setText(map.get("provinceName"));
        //城市
        cityname.setText(map.get("cityName"));
        //区县
        countyname.setText(map.get("countyName"));
        //乡镇
        String str = map.get("townName");
        if (str != null && !str.isEmpty() && !"null".equals(str)) {
            townname.setText(str);
        }
        String meterUnit = map.get("meterUnitName");
        if (meterUnit != null && !meterUnit.isEmpty()) {
            //计量单位
            meterunitname.setText(meterUnit);
        }
        //单价
        price.setText(map.get("price"));
    }


    /**
     * 身份验证
     */
    private void proving() {
        //身份证
        Number = newAddOriginalNumber.getText().toString();
        //户主名字
        OriginalNam = newAddOriginalName.getText().toString();
        if (Number.length() == 18) {
            if (!OriginalNam.isEmpty()) {
                //验证身份证
                mPresenter.validateHouseholder(Id, orgId, Number, OriginalNam,
                        new NewAddOriginalPresenter.OnClickListener() {
                            @Override
                            public void onsuccess(boolean lean) {
                                //户主名称和身份证号码匹配
                                if (lean) {
                                    save();
                                } else {
                                    //身份证验证失败
                                    ToastUtlis.getInstance().showShortToast("身份证已被使用");
                                }
                            }
                        });
            } else {
                ToastUtlis.getInstance().showShortToast("户主姓名称不能为空");
            }
        } else {
            ToastUtlis.getInstance().showShortToast("身份证长度不对");
        }
    }

    /**
     * 保存
     */
    public void save() {
        Map<String, String> map = new HashMap<>();
        //必须传
        //组织Id
        map.put("orgId", orgId);
        //身份证
        map.put("householderIdcard", Number);
        //户主名称
        map.put("householder", OriginalNam);
        //“、、所属标段、、、、、、、”
        //原始单号
        if (!newAddOriginalOriginalnumber.getText().toString().isEmpty()) {
            map.put("rawNumber", newAddOriginalOriginalnumber.getText().toString());
            if (CommandId != null) {
                //指挥部
                map.put("headquarter", CommandId);
                if (ProjectId != null) {
                    //所属项目
                    map.put("project", ProjectId);
                    if (BidsId != null) {
                        //所属标段
                        map.put("tender", BidsId);
                        //标准分解Id
                        if (standardDetail != null) {
                            map.put("standardDetail", standardDetail);
                            //申报数量  Bigdecimal
                            if (declareNum.getText().length() > 0) {
                                map.put("declareNum", declareNum.getText().toString());
                                //详细地址
                                if (!detailAddress.getText().toString().isEmpty()) {
                                    map.put("detailAddress", detailAddress.getText().toString());
                                    //申请期数
                                    if (!newAddOriginalApplydateText.getText().toString().isEmpty()) {
                                        map.put("period", numberId);
                                        //非必传
                                        //申报金额
                                        map.put("totalPrice", totalPrice.getText().toString());
                                        //受益人
                                        map.put("beneficiary", newAddOriginalBeneficiary.getText().toString());
                                        //备注否
                                        map.put("remarks", remarks.getText().toString());
                                        //户主电话
                                        map.put("householderPhone", newAddOriginalPhone.getText().toString());
                                        if (deleteList.size() > 0) {
                                            String delete = BaseUtils.listToStrings(deleteList);
                                            map.put("ids", delete);
                                        }
                                        BaseDialogUtils.getDialog(mContext, "提交数据中", false);
                                        if (copylean) {
                                            //复制新增
                                            ArrayList<ExamineBean> list = new ArrayList<>();
                                            mPresenter.save(map, list);
                                        } else {
                                            //单据编号
                                            map.put("number", originalDonumber.getText().toString());
                                            //*****保存*****
                                            //判读是新增保存还是修改保存
                                            if (Id.length() > 0) {
                                                map.put("Id", Id);

                                            }
                                            mPresenter.save(map, pathlist);
                                            //生成单据编号
                                            String str = originalDonumber.getText().toString();
                                            if (str.isEmpty()) {
                                                createNumber(map);
                                            }
                                        }
                                    } else {
                                        ToastUtlis.getInstance().showShortToast("申报期数还未选择");
                                    }
                                } else {
                                    ToastUtlis.getInstance().showShortToast("详细地址还未填");
                                }
                            } else {
                                ToastUtlis.getInstance().showShortToast("申报数量不能为空");
                            }
                        } else {
                            ToastUtlis.getInstance().showShortToast("标准分解还未选择");
                        }
                    } else {
                        ToastUtlis.getInstance().showShortToast("所属标段还未选择");
                    }
                } else {
                    ToastUtlis.getInstance().showShortToast("所属项目还未选择");
                }
            } else {
                ToastUtlis.getInstance().showShortToast("指挥部还未选择");
            }
        } else {
            ToastUtlis.getInstance().showShortToast("原始单号不能为空");
        }

    }

    /**
     * 创建单据编号
     *
     * @param map
     */
    private void createNumber(Map<String, String> map) {
        Map<String, String> create = new HashMap<>();
        //遍历map中的值
        for (Map.Entry<String, String> entry : map.entrySet()) {
            //组织ID，分解标准ID，项目ID，指挥部ID
            if ("orgId".equals(entry.getKey()) || "standardDetail".equals(entry.getKey()) || "project".equals(entry.getKey()) || "headquarter".equals(entry.getKey())) {
                create.put(entry.getKey(), entry.getValue());
            }
        }
        mPresenter.createnumber(create, new NewAddOriginalPresenter.OnClickListener1() {
            @Override
            public void onsuccess(String str) {
                originalDonumber.setText(str);
            }
        });
    }

    /**
     * 更新图片
     */
    private void getupdata() {
        ArrayList list = new ArrayList();
        for (int i = 0; i < pathlist.size(); i++) {
            list.add(pathlist.get(i).getPath());
        }
        mPhotosAdapter.getData(list);
    }

    /**
     * 返回键重写
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (status) {
                BaseDialogUtils.getprompt(mContext, "数据未保存,是否进行存", new BaseDialogUtils.onclicktlister() {
                    @Override
                    public void onsuccess() {
                        BaseDialogUtils.alertdialog.dismiss();
                        //保存数据
                        proving();
                    }

                    @Override
                    public void onerror() {
                        finish();
                    }
                });
            } else {
                finish();
            }
            return true;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (utils != null) {
            utils = null;
        }
    }
}
