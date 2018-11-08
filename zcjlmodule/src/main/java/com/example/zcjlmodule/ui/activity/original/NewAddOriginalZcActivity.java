package com.example.zcjlmodule.ui.activity.original;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.zcjlmodule.R;
import com.example.zcjlmodule.presenter.NewAddOriginalPresenter;
import com.example.zcjlmodule.ui.activity.original.enclosure.ApplyDateZcActivity;
import com.example.zcjlmodule.ui.activity.original.enclosure.ChoiceBidsZcActivity;
import com.example.zcjlmodule.ui.activity.original.enclosure.ChoiceHeadquartersZcActivity;
import com.example.zcjlmodule.ui.activity.original.enclosure.ChoiceProjectZcActivity;
import com.example.zcjlmodule.ui.activity.original.enclosure.StandardDecomposeZcActivity;
import com.example.zcjlmodule.view.NewAddOriginalView;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import measure.jjxx.com.baselibrary.adapter.PhotoPreview;
import measure.jjxx.com.baselibrary.adapter.PhotosAdapter;
import measure.jjxx.com.baselibrary.base.BaseMvpActivity;
import measure.jjxx.com.baselibrary.bean.PhotoviewBean;
import measure.jjxx.com.baselibrary.utils.PopCameraUtils;
import measure.jjxx.com.baselibrary.utils.FileUtils;
import measure.jjxx.com.baselibrary.utils.PhotoUtils;
import measure.jjxx.com.baselibrary.utils.TakePictureManager;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;


/**
 * description: 新增原始勘丈表
 *
 * @author lx
 *         date: 2018/10/16 0016 下午 2:29
 *         跳转界面：OriginalZcActivity
 */
public class NewAddOriginalZcActivity extends BaseMvpActivity<NewAddOriginalPresenter> implements View.OnClickListener, NewAddOriginalView {
    private Context mContext;
    private TextView title;
    private RecyclerView photrecycler;
    //图片展示adapter
    private PhotosAdapter mPhotosAdapter;
    //返回的图片
    private ArrayList<String> list;
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
    //  申报期数  计量单位   总金额   单价  标准分解
    private TextView newAddOriginalApplydateText, meterunitname, totalPrice, price, standardDetailNumber;
    //原始单号 申报数量
    private EditText newAddOriginalOriginalnumber, declareNum, remarks;
    //图标
    private ImageView newAddOriginalProjectnameImage, newAddOriginalBidstextImage, newAddOriginalCommandtextImage, newAddOriginalStandarddImage, newAddOriginalApplydateImage;
    //户主名字，省份证号码，电话，受益人
    private EditText newAddOriginalName, newAddOriginalNumber, newAddOriginalPhone, newAddOriginalBeneficiary, detailAddress;
    //orgid
    private String orgId = "", Id = "";
    //类型 判断是新建还是传递参数展示
    private String type = "";
    private Intent intent;
    //滑动控件
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_add_original_zc);
        mContext = this;
        //实例presenter
        mPresenter = new NewAddOriginalPresenter();
        //实例presenter
        mPresenter.mView = this;
        list = new ArrayList<>();
        imagelist = new ArrayList<>();
        editTexlist = new ArrayList<>();
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
        title.setText("新增原始勘丈表");
        photrecycler = (RecyclerView) findViewById(R.id.newaddoriginal_recycler);
    }

    /**
     * 初始化数据
     */
    private void init() {
        mPhotosAdapter = new PhotosAdapter(this, list, true);
        if (type.equals("old")) {
            hide();
            //或者传递过来的参数集合
            message = (HashMap<String, String>) intent.getSerializableExtra("message");
            //获取ID
            Id = message.get("id");
            //单据编号
            originalDonumber.setText(message.get("number"));
            //户主名字
            newAddOriginalName.setText(message.get("namecontent"));
            //征拆类型
            newAddOriginalCategory.setText(message.get("category"));
            //指挥部
            newAddOriginalCommandtext.setText(message.get("headquarterName"));
            //原始单号
            newAddOriginalOriginalnumber.setText(message.get("oldnumber"));
            //省
            provincename.setText(message.get("provinceName"));
            //城市
            cityname.setText(message.get("cityName"));
            //区
            countyname.setText(message.get("countyName"));
            //乡镇
            townname.setText(message.get("townName"));
            //地址
            detailAddress.setText(message.get("detailAddress"));
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
            //备注
            remarks.setText(message.get("remarks"));
        } else {
            showview();
        }
        photrecycler.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        photrecycler.setAdapter(mPhotosAdapter);
        //点击事件--- 弹窗选择相机还是相册，相机相册返回的图片--展示
        mPhotosAdapter.setOnItemClickListener(new PhotosAdapter.OnItemClickListener() {
            //添加图片
            @Override
            public void addlick(View view, int position) {
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
                ArrayList<PhotoviewBean> pathlsit = new ArrayList<PhotoviewBean>();
                for (int i = 0; i < list.size(); i++) {
                    pathlsit.add(new PhotoviewBean("", list.get(i), ""));
                }
                PhotoPreview.builder().setPhotos(PhotoUtils.getPhoto(list, false)).setCurrentItem(position).start((Activity) mContext);
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
            finish();
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
                intent1.putExtra("type", type);//判断的新增还是修改
                intent1.putExtra("orgId", orgId);//判断的新增还是修改
                startActivityForResult(intent1, 105);
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
        } else if (i == R.id.toolbar_text_text) {
            //隐藏布局
            hide();
            //保存
            save();
        } else if (i == R.id.new_add_original_copy) {
            ToastUtlis.getInstance().showShortToast("新增修改");
        } else {
        }
    }

    /**
     * 保存数据
     */
    private void save() {
        Map<String, Object> map = new HashMap<>();
        //主键Id（不传则新增) 否
        map.put("Id", Id);
        //组织Id
        map.put("orgId", orgId);
        //单据编号
        if (originalDonumber.getText().length() > 0) {
            map.put("number", originalDonumber.getText());
        }
        //原始单号
        if (newAddOriginalOriginalnumber.getText().length() > 0) {
            map.put("rawNumber", newAddOriginalOriginalnumber.getText());
        }
        //所属项目
        if (newAddOriginalProjectname.getText().length() > 0) {
            map.put("project", "");
        }
        //所属标段
        if (newAddOriginalBidstext.getText().length() > 0) {
            map.put("tender", "");
        }
        //指挥部
        if (newAddOriginalCommandtext.getText().length() > 0) {
            map.put("headquarter", "");
        }
        //期数
        if (newAddOriginalApplydateText.getText().length() > 0) {
            map.put("period", "");
        }
        //标准分解Id
        if (standardDetailNumber.getText().length() > 0) {
            map.put("standardDetail", "");
        }
        //详细地址
        if (detailAddress.getText().length() > 0) {
            map.put("detailAddress", "");
        }
        //申报数量  Bigdecimal
        if (declareNum.getText().length() > 0) {
            map.put("declareNum", "");
        }
        //申报金额   Bigdecimal
        if (totalPrice.getText().length() > 0) {
            map.put("totalPrice", "");
        }
        //户主姓名
        if (newAddOriginalName.getText().length() > 0) {
            map.put("householder", "");
        }
        //户主身份证
        if (newAddOriginalNumber.getText().length() > 0) {
            map.put("householderIdcard", "");
        }
        //户主电话 否
        map.put("householderPhone", "");
        //受益人 否
        if (newAddOriginalBeneficiary.getText().length() > 0) {
            map.put("beneficiary", "");
        }
        //备注 否
        if (remarks.getText().length() > 0) {
            map.put("remarks", "");
        }
        //保存完成
        mPresenter.save(map);
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
        mPresenter.setMargins(scrollView, 0, 0, 0, 0);
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

    //所属项目ID 所属标段ID  指挥部Id
    private String ProjectId, BidsId, CommandId, numberId;

    /**
     * 调用相机的回调
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
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
        } else if (requestCode == 105 && resultCode == 105) {
            //分解标准
            standardDetailNumber.setText(data.getStringExtra("name"));
        } else if (requestCode == 106 && resultCode == 106) {
            //申报期数
            newAddOriginalApplydateText.setText(data.getStringExtra("name"));
            numberId = data.getStringExtra("id");
        }
    }


    /**
     * 网络请求成功
     */
    @Override
    public void OnSuccess() {

    }

    /**
     * 网络请求失败
     */
    @Override
    public void OnError() {

    }
}
