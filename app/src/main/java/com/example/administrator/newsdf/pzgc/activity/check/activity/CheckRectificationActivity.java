package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.CheckPermission;
import com.example.administrator.newsdf.camera.CropImageUtils;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.PhotoAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.CheckUtils;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import static com.example.administrator.newsdf.pzgc.utils.Dates.compressPixel;

/**
 * description: 新增整改
 *
 * @author lx
 *         date: 2018/8/9 0009 上午 8:54
 *         update: 2018/8/9 0009
 *         version:
 */
public class CheckRectificationActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView titleView, checklistmeuntext, checkRectifiData, check_wbspath, checkRectifiSubmit,
            checkRectifiWbs, check_rectifi_font;
    private LinearLayout checkRectifi, check_import, checklistmeun, check_rectifi_user, check_new_data,check_standard;
    private Context mContext;
    private NumberPicker yearPicker, monthPicker, dayPicker;
    private RecyclerView recycler_view;
    private int dateMonth, dayDate;
    private Date myDate = new Date();
    private String[] numbermonth, numberyear;
    private CheckUtils checkUtils;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> Imagepath;
    private CheckPermission checkPermission;
    private static final int IMAGE_PICKER = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_rectification);
        checkPermission = new CheckPermission(this) {
            @Override
            public void permissionSuccess() {
                CropImageUtils.getInstance().takePhoto(CheckRectificationActivity.this);
            }

            @Override
            public void negativeButton() {
                //如果不重写，默认是finishddsfaasf
                //super.negativeButton();
                ToastUtils.showLongToast("权限申请失败！");
            }
        };
        mContext = CheckRectificationActivity.this;
        checkUtils = new CheckUtils();
        Imagepath = new ArrayList<>();
        check_standard= (LinearLayout) findViewById(R.id.check_standard);
        check_new_data = (LinearLayout) findViewById(R.id.check_new_data);
        check_rectifi_font = (TextView) findViewById(R.id.check_rectifi_font);
        check_rectifi_user = (LinearLayout) findViewById(R.id.check_rectifi_user);
        checkRectifiWbs = (TextView) findViewById(R.id.check_rectifi_wbs);
        checklistmeun = (LinearLayout) findViewById(R.id.checklistmeun);
        checkRectifiSubmit = (TextView) findViewById(R.id.check_rectifi_submit);
        check_wbspath = (TextView) findViewById(R.id.check_wbspath);
        check_import = (LinearLayout) findViewById(R.id.check_import);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        titleView = (TextView) findViewById(R.id.titleView);
        checkRectifi = (LinearLayout) findViewById(R.id.check_rectifi);
        checklistmeuntext = (TextView) findViewById(R.id.checklistmeuntext);
        checkRectifiData = (TextView) findViewById(R.id.check_rectifi_data);
        checklistmeuntext.setVisibility(View.VISIBLE);
        checklistmeuntext.setText("保存");

        titleView.setText("新增整改");
        checkRectifi.setOnClickListener(this);
        check_import.setOnClickListener(this);
        checklistmeun.setOnClickListener(this);
        check_new_data.setOnClickListener(this);
        check_standard.setOnClickListener(this);
        check_rectifi_user.setOnClickListener(this);
        checkRectifiSubmit.setOnClickListener(this);
        findViewById(R.id.checklistback).setOnClickListener(this);
        //获取当前月份
        dateMonth = myDate.getMonth();
        //拿到月
        numbermonth = Utils.month;
        //拿到年
        numberyear = Utils.year;
        //天
        dayDate = myDate.getDate() - 1;
        recycler_view.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        photoAdapter = new PhotoAdapter(mContext, Imagepath, "Rectifi");
        recycler_view.setAdapter(photoAdapter);
    }

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
                                Imagepath.add(outfile);
                                //填入listview，刷新界面
                                photoAdapter.getData(Imagepath);
                            }
                        });
                    } else {
                        ToastUtils.showLongToast("请检查上传的图片是否损坏");
                    }
                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 1 && resultCode == 3) {
            check_wbspath.setText(data.getStringExtra("title"));
            check_wbspath.setVisibility(View.VISIBLE);
        } else if (requestCode == 2 && resultCode == 3) {
            check_rectifi_font.setText(data.getStringExtra("name"));
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
                            Imagepath.add(outfile);
                            //填入listview，刷新界面
                            photoAdapter.getData(Imagepath);
                            //删除原图
                            Dates.deleteFile(path);
                        }
                    });

                }
            });
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
            case R.id.check_standard:
                startActivity(new Intent(mContext,CheckstandardListActivity.class));
                break;
            case R.id.checklistback:
                finish();
                break;
            case R.id.check_rectifi:
                meunpop();
                break;
            case R.id.check_new_data:
                meunpop();
                break;
            case R.id.check_import:
                String content = checkRectifiWbs.getText().toString();
                if (!content.isEmpty()) {
                    Intent intent = new Intent(mContext, CheckTreeActivity.class);
                    startActivityForResult(intent, 1);
                } else {
                    ToastUtils.showLongToast("请先选择标段");
                }
                break;
            case R.id.checklistmeun:
                String str = checklistmeuntext.getText().toString();
                if ("保存".equals(str)) {
                    checklistmeuntext.setText("编辑");
                    checkRectifiSubmit.setBackgroundResource(R.color.Orange);
                } else {
                    checklistmeuntext.setText("保存");
                    checkRectifiSubmit.setBackgroundResource(R.color.gray);
                }
                break;
            case R.id.check_rectifi_submit:
                String str1 = checklistmeuntext.getText().toString();
                if ("编辑".equals(str1)) {
                    ToastUtils.showLongToast("下发");
                }
                break;
            case R.id.check_rectifi_user:
                startActivityForResult(new Intent(mContext, CheckuserActivity.class), 2);
                break;
            default:
                break;
        }
    }

    private PopupWindow mPopupWindow;

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
        mPopupWindow.showAsDropDown(checkRectifi);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());
        Utils.backgroundAlpha(0.5f, CheckRectificationActivity.this);
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
                        checkRectifiData.setText(yeardata + "-" + monthdata + "-" + daydata);
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
     * popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            Utils.backgroundAlpha(1f, CheckRectificationActivity.this);
        }

    }

    //添加图片
    public void showPopwindow() {
        //弹出现在相机和图册的蒙层
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        //初始化布局
        View popView = View.inflate(this, R.layout.camera_pop_menu, null);
        //初始化控件
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
                            CropImageUtils.getInstance().takePhoto(CheckRectificationActivity.this);
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
        btnAlbum.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);
        //设置背景颜色
        ColorDrawable dw = new ColorDrawable(0x30000000);
        popWindow.setBackgroundDrawable(dw);
        //显示位置
        popWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

}
