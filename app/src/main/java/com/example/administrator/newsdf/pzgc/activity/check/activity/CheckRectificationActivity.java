package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.FileUtils;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.CheckPermission;
import com.example.administrator.newsdf.camera.CropImageUtils;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.CheckPhotoAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.CheckUtils;
import com.example.administrator.newsdf.pzgc.activity.mine.OrganizationaActivity;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.callback.TaskCallbackUtils;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.joanzapata.iconify.widget.IconTextView;
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
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.newsdf.pzgc.utils.Dates.compressPixel;
import static com.lzy.okgo.OkGo.post;

/**
 * description: 新增整改
 *
 * @author lx
 *         date: 2018/8/9 0009 上午 8:54
 *         update: 2018/8/9 0009
 *         version:
 */
public class CheckRectificationActivity extends BaseActivity implements View.OnClickListener {
    private TextView titleView, checklistmeuntext, checkRectifiData, check_wbspath, checkRectifiSubmit,
            checkRectifiWbs, check_rectifi_font, category_item, check_new_tasktitle;
    private LinearLayout checkRectifi, check_import, checklistmeun, check_rectifi_user, check_new_data, check_standard;
    private Context mContext;
    private NumberPicker yearPicker, monthPicker, dayPicker;
    private RecyclerView recycler_view;
    private int dateMonth, dayDate;
    private Date myDate = new Date();
    private String[] numbermonth, numberyear;
    private CheckUtils checkUtils;
    private CheckPhotoAdapter photoAdapter;
    private ArrayList<Audio> Imagepath;
    private CheckPermission checkPermission;
    private static final int IMAGE_PICKER = 101;
    private TextView checkNewDataTx;
    private String categoryid, categoryedid, OrgId = null, orgName, nodeId, nodeName, userId, userName, standardDelScore, standardDelCode, id = "";
    private EditText check_rectifi_result, check_new_temporarysite;
    private LinearLayout check_org;
    private IconTextView oneIcon, twoIcon, threeIcon, fourIcon, fiveIcon;
    private ArrayList<View> listVIew = new ArrayList<>();
    private ArrayList<View> listEn = new ArrayList<>();
    private boolean status = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_rectification);
        Intent intent = getIntent();
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
        oneIcon = (IconTextView) findViewById(R.id.oneIcon);
        twoIcon = (IconTextView) findViewById(R.id.twoIcon);
        threeIcon = (IconTextView) findViewById(R.id.threeIcon);
        fourIcon = (IconTextView) findViewById(R.id.fourIcon);
        fiveIcon = (IconTextView) findViewById(R.id.fiveIcon);
        listVIew.add(oneIcon);
        listVIew.add(twoIcon);
        listVIew.add(threeIcon);
        listVIew.add(fourIcon);
        listVIew.add(fiveIcon);

        check_org = (LinearLayout) findViewById(R.id.check_org);
        listEn.add(check_org);
        check_org.setOnClickListener(this);
        check_new_tasktitle = (TextView) findViewById(R.id.check_new_tasktitle);
        check_rectifi_result = (EditText) findViewById(R.id.check_rectifi_result);
        check_new_temporarysite = (EditText) findViewById(R.id.check_new_temporarysite);
        listEn.add(check_new_tasktitle);
        listEn.add(check_rectifi_result);
        listEn.add(check_new_temporarysite);
        category_item = (TextView) findViewById(R.id.category_item);
        checkNewDataTx = (TextView) findViewById(R.id.check_new_data_tx);
        check_standard = (LinearLayout) findViewById(R.id.check_standard);
        listEn.add(check_standard);
        check_new_data = (LinearLayout) findViewById(R.id.check_new_data);
        listEn.add(check_new_data);
        check_rectifi_font = (TextView) findViewById(R.id.check_rectifi_font);
        check_rectifi_user = (LinearLayout) findViewById(R.id.check_rectifi_user);
        listEn.add(check_rectifi_user);
        checkRectifiWbs = (TextView) findViewById(R.id.check_rectifi_wbs);
        checklistmeun = (LinearLayout) findViewById(R.id.checklistmeun);
        checkRectifiSubmit = (TextView) findViewById(R.id.check_rectifi_submit);
        check_wbspath = (TextView) findViewById(R.id.check_wbspath);
        check_import = (LinearLayout) findViewById(R.id.check_import);
        listVIew.add(check_import);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        titleView = (TextView) findViewById(R.id.titleView);
        checkRectifi = (LinearLayout) findViewById(R.id.check_rectifi);
        listEn.add(checkRectifi);
        checklistmeuntext = (TextView) findViewById(R.id.checklistmeuntext);
        checkRectifiData = (TextView) findViewById(R.id.check_rectifi_data);
        checkRectifiData.setText(Dates.getDay());
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
        photoAdapter = new CheckPhotoAdapter(mContext, Imagepath, "Rectifi", false);
        recycler_view.setAdapter(photoAdapter);
        checkNewDataTx.setText(Dates.getDay());
        try {
            id = intent.getStringExtra("id");
            if (id != null) {
                checklistmeuntext.setText("编辑");
                checkRectifiSubmit.setBackgroundResource(R.color.Orange);
                Visibility(8);
                Enabled(false);
                getdata();
            }
        } catch (Exception e) {
            id = "";
        }
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
                                Imagepath.add(new Audio(outfile, ""));
                                //填入listview，刷新界面
                                photoAdapter.getData(Imagepath, true);
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
        } else if (requestCode == 1 && resultCode == 2) {
            categoryid = data.getStringExtra("dataid");
            categoryedid = data.getStringExtra("id");
            check_new_tasktitle.setText(data.getStringExtra("datastr"));
            category_item.setText(data.getStringExtra("content"));
            standardDelScore = data.getStringExtra("score");
            standardDelCode = data.getStringExtra("stancode");
        } else if (requestCode == 3 && resultCode == 2) {
            OrgId = data.getStringExtra("id");
            orgName = data.getStringExtra("name");
            checkRectifiWbs.setText(orgName);
        } else if (requestCode == 4 && resultCode == 3) {
            nodeId = data.getStringExtra("id");
            nodeName = data.getStringExtra("name");
            check_wbspath.setText(nodeName);
            check_wbspath.setVisibility(View.VISIBLE);
        } else if (requestCode == 5 && resultCode == 2) {
            userId = data.getStringExtra("id");
            userName = data.getStringExtra("name");
            check_rectifi_font.setText(userName);
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
                            //添加进集合
                            Imagepath.add(new Audio(outfile, ""));
                            //填入listview，刷新界面
                            photoAdapter.getData(Imagepath, true);
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
        if (status) {
            switch (v.getId()) {
                case R.id.check_standard:
                    Intent intent = new Intent(mContext, CheckstandardListActivity.class);
                    intent.putExtra("title","title");
                    startActivityForResult(intent, 1);
                    break;
                case R.id.checklistback:
                    if (Imagepath.size() > 0) {
                        for (int i = 0; i < Imagepath.size(); i++) {
                            FileUtils.deleteFile(Imagepath.get(i).getContent());
                        }
                    }
                    finish();
                    break;
                case R.id.check_rectifi:
                    meunpop();
                    break;
                case R.id.check_new_data:
                    meunpop();
                    break;
                case R.id.check_org:
                    //选择标段
                    Intent intent12 = new Intent(mContext, OrganizationaActivity.class);
                    intent12.putExtra("title", "选择标段");
                    intent12.putExtra("data", "Rectifi");
                    startActivityForResult(intent12, 3);
                    break;
                case R.id.check_import:
                    //选择
                    String content = checkRectifiWbs.getText().toString();
                    if (!content.isEmpty()) {
                        Intent intent1 = new Intent(mContext, CheckTreeActivity.class);
                        intent1.putExtra("orgId", OrgId);
                        intent1.putExtra("name", orgName);
                        startActivityForResult(intent1, 4);
                    } else {
                        ToastUtils.showLongToast("请先选择标段");
                    }
                    break;
                case R.id.checklistmeun:
                    String str = checklistmeuntext.getText().toString();
                    if ("保存".equals(str)) {

                        String user = check_rectifi_font.getText().toString();
                        if (user.length() > 0) {
                            String tasktitle = check_new_tasktitle.getText().toString();
                            if (tasktitle.length() > 0) {
                                String temporarysite = check_new_temporarysite.getText().toString();
                                String wsbpath = check_wbspath.getText().toString();
                                if (temporarysite.length() > 0 || wsbpath.length() > 0) {
                                    save();
                                } else {
                                    ToastUtils.showShortToast("整改部位不能为空");
                                }
                            } else {
                                ToastUtils.showShortToast("违反标准不能为空");
                            }
                        } else {
                            ToastUtils.showShortToast("整改负责人不能为空");
                        }
                    } else {
                        Visibility(0);
                        Enabled(true);
                        checklistmeuntext.setText("保存");
                        checkRectifiSubmit.setBackgroundResource(R.color.gray);
                        photoAdapter.getData(Imagepath, true);
                    }
                    break;
                case R.id.check_rectifi_submit:
                    String str1 = checklistmeuntext.getText().toString();
                    if ("编辑".equals(str1)) {
                        sendData();
                    }
                    break;
                case R.id.check_rectifi_user:
                    if (OrgId != null) {
                        Intent intent1 = new Intent(mContext, CheckuserActivity.class);
                        intent1.putExtra("orgId", OrgId);
                        startActivityForResult(intent1, 5);
                    } else {
                        ToastUtils.showLongToast("请先选择标段");
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //删除无用图片
            for (int i = 0; i < Imagepath.size(); i++) {
                FileUtils.deleteFile(Imagepath.get(i).getContent());
            }
            finish();
            return true;
        }
        return true;
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
        mPopupWindow.showAsDropDown(check_org);
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

    //隐藏 8  显示8
    private void Visibility(int number) {
        for (int i = 0; i < listVIew.size(); i++) {
            listVIew.get(i).setVisibility(number);
        }
    }

    public void Enabled(boolean status) {
        for (int i = 0; i < listEn.size(); i++) {
            listEn.get(i).setEnabled(status);
        }
    }

    ArrayList<String> deleteList = new ArrayList<>();

    public void delete(String id) {
        if (id.length() > 0) {
            deleteList.add(id);
        }
    }

    ArrayList<File> files;

    public void save() {
        Dates.getDialog(CheckRectificationActivity.this, "保存数据中...");
        files = new ArrayList<>();
        if (Imagepath.size() > 0) {
            for (int i = 0; i < Imagepath.size(); i++) {
                String content = Imagepath.get(i).getContent();
                if (content.isEmpty()) {
                    files.add(new File(Imagepath.get(i).getName()));
                }
            }
        }
        PostRequest str = OkGo.post(Requests.SAVE_NOTICE_APP)
                .params("rectificationOrgid", OrgId)
                .params("rectificationOrgName", orgName)
                .params("rectificationPart", nodeId)
                .params("standardDel", categoryedid)
                .params("standardDelCode", standardDelCode)
                .params("standardDelScore", standardDelScore)
                .params("standardDelName", category_item.getText().toString())
                .params("standardType", categoryid)
                .params("standardTypeName", check_new_tasktitle.getText().toString())
                .params("checkDate", checkNewDataTx.getText().toString())
                .params("rectificationPerson", userId)
                .params("rectificationPersonName", userName)
                .params("rectificationDate", checkRectifiData.getText().toString())
                .params("rectificationReason", check_rectifi_result.getText().toString())
                .params("rectificationPartName", nodeName)
                .params("partDetails", check_new_temporarysite.getText().toString())
                .params("id", id)
                .params("deleteFileId", Dates.listToStrings(deleteList));
        if (files.size() > 0) {
            str.addFileParams("attachment", files);
        } else {
            str.isMultipart(true);
        }
        str.execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        id = data.getString("id");
                        checklistmeuntext.setText("编辑");
                        checkRectifiSubmit.setBackgroundResource(R.color.Orange);
                        JSONArray jsonArray = data.getJSONArray("attachmentList");
                        Imagepath.clear();
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json = jsonArray.getJSONObject(i);
                                Imagepath.add(new Audio(Requests.networks + json.getString("filepath"), json.getString("id")));
                            }
                        }
                        photoAdapter.getData(Imagepath, false);
                        Visibility(8);
                        Enabled(false);
                    } else {
                        ToastUtils.showLongToast(jsonObject.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Dates.disDialog();
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Dates.disDialog();
                ToastUtils.showLongToast("请求失败");
            }
        });
    }

    public void getdata() {
        Dates.getDialog(CheckRectificationActivity.this, "请求数据中...");
        OkGo.<String>post(Requests.getNoticeDateApp)
                .params("noticeId", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                Imagepath.clear();
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                JSONObject json = jsonObject1.getJSONObject("notice");
                                nodeId = json.getString("rectificationPart");
                                nodeName = json.getString("rectificationPartName");
                                check_wbspath.setText(nodeName);
                                check_wbspath.setVisibility(View.VISIBLE);
                                id = json.getString("id");
                                OrgId = json.getString("rectificationOrgid");
                                category_item.setText(json.getString("standardDelName"));
                                orgName = json.getString("rectificationOrgName");
                                checkRectifiWbs.setText(orgName);
                                check_new_temporarysite.setText(json.getString("partDetails"));
                                userName = json.getString("rectificationPersonName");
                                check_rectifi_font.setText(userName);
                                userId = json.getString("rectificationPerson");
                                String rectificationDate = json.getString("rectificationDate");
                                checkRectifiData.setText(rectificationDate.substring(0, 10));
                                categoryedid = json.getString("standardDel");
                                String checkDate = json.getString("checkDate");
                                checkNewDataTx.setText(checkDate.substring(0, 10));
                                check_rectifi_result.setText(json.getString("rectificationReason"));
                                categoryid = json.getString("standardType");
                                check_new_tasktitle.setText(json.getString("standardTypeName"));
                                standardDelCode = json.getString("standardDelCode");
                                standardDelScore = json.getString("standardDelScore");
                                JSONArray attachment = json.getJSONArray("attachmentList");
                                if (attachment.length() > 0) {
                                    for (int i = 0; i < attachment.length(); i++) {
                                        JSONObject att = attachment.getJSONObject(i);
                                        Imagepath.add(new Audio(Requests.networks + att.getString("filepath"), att.getString("id")));
                                    }
                                }
                                photoAdapter.getData(Imagepath, false);
                            } else {
                                ToastUtils.showShortToast(jsonObject.getString("msg"));
                            }
                            Dates.disDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Dates.disDialog();
                    }
                });
    }

    public void sendData() {
        Dates.getDialog(CheckRectificationActivity.this, "请求数据中...");
        post(Requests.SEND_MESSAGE_DATA)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                try {
                                    TaskCallbackUtils.CallBackMethod();
                                } catch (Exception e) {
                                }
                                finish();
                            } else {
                                ToastUtils.showLongToast(jsonObject.getString("msg"));
                            }
                            Dates.disDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Dates.disDialog();
                    }
                });

    }

    public String getstatus() {
        return checklistmeuntext.getText().toString();
    }
/**
 * rectificationOrgid  // 标段id
 rectificationOrgName  // 标段名称
 @"rectificationPart"];// 整改部位
 @"standardDel"];// 违反标准id
 @"standardDelScore"];// 违反标准分数
 @"standardDelCode"];// 违反标准编码
 @"standardDelName"];// 违反标准名称
 @"standardType"];// 类别id
 @"standardTypeName"];// 类别名称
 @"checkDate"];// 检查时间
 @"rectificationPerson"];// 整改负责人id
 :@"rectificationPersonName"];// 整改负责人名称
 @"rectificationDate"];// 整改期限
 @"rectificationReason"];// 整改是由
 @"rectificationPartName"];// 部位名称补充说明
 @"attachment"];附件
 @"id"]; //修改时必填
 @"deleteFileId"];//修改时删除的附件ID，英文逗号拼接

 */
}
