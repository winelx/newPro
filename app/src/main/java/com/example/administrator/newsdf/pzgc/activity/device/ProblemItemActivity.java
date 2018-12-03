package com.example.administrator.newsdf.pzgc.activity.device;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.CheckPermission;
import com.example.administrator.newsdf.camera.CropImageUtils;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.CheckPhotoAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.CheckUtils;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.callback.CheckCallback3;
import com.example.administrator.newsdf.pzgc.callback.ProblemCallback;
import com.example.administrator.newsdf.pzgc.callback.ProblemCallbackUtils;
import com.example.administrator.newsdf.pzgc.callback.ProblemItemCallbackUtils;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.PopCameraUtils;
import com.example.administrator.newsdf.pzgc.utils.TakePictureManager;
import com.example.administrator.newsdf.pzgc.utils.Utils;
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
    private CheckPermission checkPermission;
    private ArrayList<String> deleteLis = new ArrayList<>();
    private TextView violation_standards_text, hidden_danger_grade_text, rectify_data;
    private PopCameraUtils PopCameraUtils;
    private EditText rectify_cause;
    private int dateMonth, dayDate;
    private String[] numbermonth, numberyear;
    //调用相机的manager
    private TakePictureManager takePictureManager;
    private PopupWindow mPopupWindow;
    private NumberPicker yearPicker, monthPicker, dayPicker;
    private CheckUtils checkUtils;
    private Date myDate = new Date();
    private TextView checklistmeuntext;
    private LinearLayout problemgrade, contentlin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);
        imagepath = new ArrayList<>();
        mContext = this;
        checkUtils = new CheckUtils();
        //拿到月
        numbermonth = Utils.month;
        //拿到年
        numberyear = Utils.year;
        //获取当前月份
        dateMonth = myDate.getMonth();
        //天
        dayDate = myDate.getDate() - 1;
        ProblemItemCallbackUtils.setCallBack(this);
        //检查相机权限
        checkPermission = new CheckPermission(this) {
            @Override
            public void permissionSuccess() {
                CropImageUtils.getInstance().takePhoto(ProblemItemActivity.this);
            }

            @Override
            public void negativeButton() {
                //如果不重写，默认是finishddsfaasf
                //super.negativeButton();
                ToastUtils.showLongToast("权限申请失败！");
            }
        };
        init();
        showView();
    }

    /**
     * @description: 初始化控件
     * @author lx
     * @date: 2018/12/3 0003 下午 3:31
     */
    private void init() {
        problemgrade = (LinearLayout) findViewById(R.id.problemgrade);
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
        violation_standards_text = (TextView) findViewById(R.id.violation_standards_text);
        //隐患等级
        findViewById(R.id.hidden_danger_grade).setOnClickListener(this);
        hidden_danger_grade_text = (TextView) findViewById(R.id.hidden_danger_grade_text);
        //整改期限
        findViewById(R.id.rectify_data_lin).setOnClickListener(this);
        rectify_data = (TextView) findViewById(R.id.rectify_data);
        rectify_data.setText(Dates.getDay());
        //整改事由
        rectify_cause = (EditText) findViewById(R.id.rectify_cause);
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
                    public void onComple(String string) {
                        if ("相机".equals(string)) {
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
                        } else {
                            //相册
                            Intent intent = new Intent(mContext, ImageGridActivity.class);
                            startActivityForResult(intent, IMAGE_PICKER);
                        }
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
            /**
             * 调用相机的回调
             */
            try {
                takePictureManager.attachToActivityForResult(requestCode, resultCode, data);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
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
        }
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_delete:
                //删除
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                        .setMessage("是否删除该项问题")
                        //点击对话框以外的区域是否让对话框消失
                        .setCancelable(false)
                        //设置按钮
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ProblemCallbackUtils.deleteProblem("str");
                            }
                        })
                        //设置按钮
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                //显示对话框
                dialog.show();
                break;
            case R.id.checklistmeuntext:
                ProblemCallbackUtils.addProblem("str");
                break;
            case R.id.violation_standards:
                //违反标准
                startActivity(new Intent(mContext, DeviceViolatestandardActivity.class));
                break;
            case R.id.hidden_danger_grade:
                //隐患等级
                hideView();
                break;
            case R.id.rectify_data_lin:
                meunpop();
                //整改期限
                break;
            default:
                break;
        }
    }

    /**
     * 选择时间弹出框
     */
    private void meunpop() {
        View contentView = getPopupWindowContentView();
        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        //设置显示隐藏动画
        mPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 默认在mButton2的左下角显示
        mPopupWindow.showAsDropDown(rectify_data);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());
        Utils.backgroundAlpha(0.5f, ProblemItemActivity.this);
    }

    /**
     * \设置pop的点击事件
     */
    private View getPopupWindowContentView() {
        // 一个自定义的布局，作为显示的内容
        int layoutId = R.layout.popwind_daily;
        final View contentView = LayoutInflater.from(mContext).inflate(layoutId, null);
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.pop_determine:
                        //获取年
                        String yeardata = Utils.year[yearPicker.getValue()];
                        //获取月
                        int month = monthPicker.getValue();
                        String monthdata = Utils.month[month];
                        //获取天
                        int day = dayPicker.getValue();
                        String daydata;
                        if (monthdata.equals("02")) {
                            //是二月份
                            if (Utils.getyear().contains(yeardata)) {
                                daydata = Utils.daytwos[day];
                                //闰年
                            } else {
                                //平年
                                daydata = Utils.daytwo[day];
                            }
                        } else {
                            //不是二月份
                            if (monthdata.equals("01") || monthdata.equals("03") || monthdata.equals("05") || monthdata.equals("07") || monthdata.equals("08") || monthdata.equals("10") || monthdata.equals("012")) {
                                daydata = Utils.day[day];
                            } else {
                                daydata = Utils.dayth[day];
                            }

                        }
                        rectify_data.setText(yeardata + "-" + monthdata + "-" + daydata);
                        break;
                    case R.id.pop_dismiss:
                    default:
                        break;
                }
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        };
        //获取控件点击事件
        contentView.findViewById(R.id.pop_dismiss).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_determine).setOnClickListener(menuItemOnClickListener);
        //年的控件
        yearPicker = contentView.findViewById(R.id.years);
        //月
        monthPicker = contentView.findViewById(R.id.month);
        //每日
        dayPicker = contentView.findViewById(R.id.day);
        //初始化数据---年
        Utils.setPicker(yearPicker, Utils.year, Utils.titleyear());
        //初始化数据---月
        Utils.setPicker(monthPicker, Utils.month, dateMonth);
        //初始化数据---日
        String yeardata = Utils.year[yearPicker.getValue()];
        //如果当前月份是2月
        if (dateMonth == 2) {
            if (Utils.getyear().contains(yeardata)) {
                Utils.setPicker(dayPicker, Utils.daytwos, dayDate);
                //闰年
            } else {
                //平年
                Utils.setPicker(dayPicker, Utils.daytwo, dayDate);
            }
        } else {
            if (dateMonth == 0 || dateMonth == 2 || dateMonth == 4 || dateMonth == 6 || dateMonth == 7 || dateMonth == 9 || dateMonth == 11) {
                Utils.setPicker(dayPicker, Utils.day, dayDate);
            } else {
                Utils.setPicker(dayPicker, Utils.dayth, dayDate);
            }
        }
        //年份选择器。如果当前的月份是二月，
        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                checkUtils.setyear(monthPicker, dayPicker, i1, numberyear);
            }
        });
        //月份选择器。如果当前的月份是二月，
        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal,
                                      int newVal) {
                checkUtils.setMonth(yearPicker, monthPicker, dayPicker, newVal, numbermonth, numberyear);
            }
        });

        return contentView;
    }

    /**
     * @description: 回调
     * @author lx
     * @date: 2018/12/3 0003 下午 3:44
     */
    @Override
    public void update(String string) {

    }

    /**
     * popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            Utils.backgroundAlpha(1f, ProblemItemActivity.this);
        }
    }

    public void showView() {
        contentlin.setVisibility(View.VISIBLE);
        problemgrade.setVisibility(View.GONE);
    }

    public void hideView() {
        contentlin.setVisibility(View.GONE);
        problemgrade.setVisibility(View.VISIBLE);
    }

}
