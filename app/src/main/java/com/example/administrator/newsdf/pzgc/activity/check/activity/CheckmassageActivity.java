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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
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
import com.example.administrator.newsdf.pzgc.Adapter.CheckPhotoAdapter;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.callback.MapCallbackUtils;
import com.example.administrator.newsdf.pzgc.callback.TaskCallbackUtils;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.joanzapata.iconify.widget.IconTextView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.newsdf.R.id.check_message_lasttiem;
import static com.example.administrator.newsdf.R.id.check_message_org;
import static com.example.administrator.newsdf.R.id.checklistmeun;
import static com.example.administrator.newsdf.pzgc.utils.Dates.compressPixel;
import static com.lzy.okgo.OkGo.post;

/**
 * description: 下发整改通知
 *
 * @author lx
 *         date: 2018/8/9 0009 下午 3:09
 *         update: 2018/8/9 0009
 *         version:
 */
public class CheckmassageActivity extends BaseActivity implements View.OnClickListener {

    private static final int IMAGE_PICKER = 101;
    private int dateMonth, dayDate;

    private CheckPhotoAdapter photoAdapter;
    private Context mContext;
    private String[] numbermonth, numberyear;
    private CheckPermission checkPermission;
    //当前界面新增的图片（如果未返回，删除图片）
    private ArrayList<Audio> Imagepath;
    private PopupWindow mPopupWindow;

    private Date myDate = new Date();

    private Switch checkMessageSwitch;
    private TextView checkMessageTime, checkMessageUsernameText, checklistmeuntext;
    private RecyclerView photoadd;
    private LinearLayout checkMessageLasttiem, checkMessageDialog, checkMessageDate;
    private NumberPicker yearPicker, monthPicker, dayPicker;
    private RelativeLayout checkMessageContent;
    private EditText check_message_describe, checkMessageStandar;
    private TextView checkMessageUser, checkMessageOrg, MessageData, titleView;
    private Boolean generate;
    private String orgId, nameId = "";
    private String messageid = "", taskId, success;
    ArrayList<String> ids;
    private IconTextView threeIcon, twoIco, ontIcon;
    private ArrayList<View> arrayList = new ArrayList<>();
    LinearLayout check_message_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkmassage);
        mContext = CheckmassageActivity.this;
        checkPermission = new CheckPermission(this) {
            @Override
            public void permissionSuccess() {
                CropImageUtils.getInstance().takePhoto(CheckmassageActivity.this);
            }

            @Override
            public void negativeButton() {
                //如果不重写，默认是finishddsfaasf
                //super.negativeButton();
                ToastUtils.showLongToast("权限申请失败！");
            }
        };
        findID();
        initData();

    }

    private void findID() {
        threeIcon = (IconTextView) findViewById(R.id.threeIcon);
        arrayList.add(threeIcon);
        twoIco = (IconTextView) findViewById(R.id.twoIcon);
        arrayList.add(twoIco);
        ontIcon = (IconTextView) findViewById(R.id.oneIcon);
        arrayList.add(ontIcon);
        titleView = (TextView) findViewById(R.id.titleView);
        //检查人
        checkMessageUser = (TextView) findViewById(R.id.check_message_user);
        //检查组织
        checkMessageOrg = (TextView) findViewById(check_message_org);
        //检查时间
        MessageData = (TextView) findViewById(R.id.message_date);
        //检查时间linear
        checkMessageDate = (LinearLayout) findViewById(R.id.check_message_data);
        //是否生成通知linear
        checkMessageDialog = (LinearLayout) findViewById(R.id.check_message_dialog);
        //整改事由
        check_message_describe = (EditText) findViewById(R.id.check_message_describe);
        //违反标段
        checkMessageStandar = (EditText) findViewById(R.id.checkmessage_standar);
        //滚动父布局
        checkMessageContent = (RelativeLayout) findViewById(R.id.check_message_content);
        //标题
        checklistmeuntext = (TextView) findViewById(R.id.checklistmeuntext);
        //是否生成整改状态按钮
        checkMessageSwitch = (Switch) findViewById(R.id.check_message_switch);
        //负责人
        checkMessageUsernameText = (TextView) findViewById(R.id.check_message_username_text);
        //整改最后时间
        checkMessageTime = (TextView) findViewById(R.id.check_message_time);
        //整改最后时间linear
        checkMessageLasttiem = (LinearLayout) findViewById(check_message_lasttiem);
        //附件
        photoadd = (RecyclerView) findViewById(R.id.check_message_rec);
        check_message_username = (LinearLayout) findViewById(R.id.check_message_username);
        check_message_username.setOnClickListener(this);
        findViewById(checklistmeun).setOnClickListener(this);
        findViewById(R.id.checklistback).setOnClickListener(this);
        checkMessageDate.setOnClickListener(this);
        checkMessageLasttiem.setOnClickListener(this);
    }

    private void initData() {
        //获取当前月份
        dateMonth = myDate.getMonth();
        //拿到月
        numbermonth = Utils.month;
        //拿到年
        numberyear = Utils.year;
        //天
        dayDate = myDate.getDate() - 1;
        Imagepath = new ArrayList<>();
        ids = new ArrayList<>();
        ArrayList<String> path = new ArrayList<>();
        Intent intent = getIntent();
        ids = intent.getStringArrayListExtra("ids");
        path = intent.getStringArrayListExtra("path");
        orgId = intent.getStringExtra("orgId");
        generate = intent.getBooleanExtra("status", false);
        messageid = intent.getStringExtra("messageid");
        success = intent.getStringExtra("success");
        taskId = intent.getStringExtra("taskId");
        try {
            //组装id和路径
            if (ids.size() > 0) {
                for (int i = 0; i < ids.size(); i++) {
                    Imagepath.add(new Audio(path.get(i), ids.get(i)));
                }
            }
        } catch (NullPointerException e) {

        }
        checkMessageStandar.setText(intent.getStringExtra("content"));
        if (generate) {
            checkMessageSwitch.setChecked(generate);
            checkMessageContent.setVisibility(View.VISIBLE);
            checklistmeuntext.setVisibility(View.VISIBLE);
            getNoticeByApp();
        } else {
            checkMessageTime.setText(Dates.getDay());
            MessageData.setText(Dates.getDay());
            check_message_username.setClickable(true);
            check_message_describe.setText(intent.getStringExtra("describe"));
            checkMessageUser.setText(SPUtils.getString(mContext, "staffName", ""));
            checkMessageOrg.setText(SPUtils.getString(mContext, "username", ""));
            checkMessageContent.setVisibility(View.GONE);
            checklistmeuntext.setVisibility(View.VISIBLE);
        }
        //附件的recycleraview的适配器
        photoadd.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        photoadd.setItemAnimator(new DefaultItemAnimator());
        photoAdapter = new CheckPhotoAdapter(mContext, Imagepath, "Message", true);
        photoadd.setAdapter(photoAdapter);
        checkMessageSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    //打开
                    checklistmeuntext.setText("保存");
                    checkMessageTime.setText(Dates.getDay());
                    MessageData.setText(Dates.getDay());
                    checkMessageContent.setVisibility(View.VISIBLE);
                } else {
                    // 关闭
                    if (messageid.length() > 0) {
                        checklistmeuntext.setText("保存");
                    } else {
                        checklistmeuntext.setText("");
                    }
                    if (messageid.length() > 0) {
                        checkMessageContent.setVisibility(View.GONE);
                    } else {
                        checkMessageContent.setVisibility(View.GONE);
                    }
                }
            }
        });
//        if (messageid == null) {
//            checklistmeuntext.setText("保存");
//            checklistmeuntext.setVisibility(View.VISIBLE);
//            checkMessageSwitch.setClickable(true);
//            photoAdapter.getData(Imagepath, false);
//        }
        titleView.setText("生成整改通知单");
        if (success != null) {
            findViewById(checklistmeun).setVisibility(View.GONE);
        }
    }

    //添加图片选择功能
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
                            CropImageUtils.getInstance().takePhoto(CheckmassageActivity.this);
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
            checkMessageUsernameText.setText(data.getStringExtra("name"));
        } else if (requestCode == 2 && resultCode == 2) {
            checkMessageUsernameText.setText(data.getStringExtra("name"));
            nameId = data.getStringExtra("id");
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
                            Imagepath.add(new Audio(outfile, ""));
                            //填入listview，刷新界面
                            photoAdapter.getData(Imagepath, true);
//                    //删除原图
                            Dates.deleteFile(path);
                        }
                    });
                }
            });
        }
    }

    /**
     * 选择时间弹出框
     */
    private void meunpop(String str) {
        View contentView = getPopupWindowContentView(str);
        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        //设置显示隐藏动画
        mPopupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 默认在mButton2的左下角显示
        mPopupWindow.showAsDropDown(checkMessageDialog);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new CheckmassageActivity.poponDismissListener());
        Utils.backgroundAlpha(0.5f, CheckmassageActivity.this);
    }

    /**
     * \设置pop的点击事件
     */
    private View getPopupWindowContentView(final String str) {
        // 一个自定义的布局，作为显示的内容
        // 布局ID
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
                        if ("data".equals(str)) {
                            MessageData.setText(yeardata + "-" + monthdata + "-" + daydata);
                        } else {
                            checkMessageTime.setText(yeardata + "-" + monthdata + "-" + daydata);
                        }
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
        if ((dateMonth+1) == 2) {
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
                setyear(i1);
            }
        });
        //月份选择器。如果当前的月份是二月，
        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal,
                                      int newVal) {
                setMonth(newVal);
            }
        });

        return contentView;
    }

    /**
     * /设置选择月，控制二月天数
     */
    public void setMonth(int newVal) {
        String NewVal = numbermonth[newVal];
        String years = numberyear[yearPicker.getValue()];
        if (NewVal.equals("02")) {
            if (Utils.getyear().contains(years)) {
                //如果是闰年。二月有29天
                dayPicker.setDisplayedValues(null);
                dayPicker.setMaxValue(Utils.daytwos.length - 1);
                dayPicker.setDisplayedValues(Utils.daytwos);
                dayPicker.setMinValue(0);
            } else {
                //如果是平年。二月有28天
                dayPicker.setDisplayedValues(null);
                dayPicker.setMaxValue(Utils.daytwo.length - 1);
                dayPicker.setDisplayedValues(Utils.daytwo);
                dayPicker.setMinValue(0);
            }
        } else if (NewVal.equals("01") || NewVal.equals("03") || NewVal.equals("05") ||
                NewVal.equals("07") || NewVal.equals("08") || NewVal.equals("10") || NewVal.equals("12")) {
            dayPicker.setDisplayedValues(null);
            dayPicker.setMaxValue(Utils.day.length - 1);
            dayPicker.setDisplayedValues(Utils.day);
            dayPicker.setMinValue(0);
        } else if (NewVal.equals("04") || NewVal.equals("06") || NewVal.equals("09") || NewVal.equals("11")) {
            dayPicker.setDisplayedValues(null);
            dayPicker.setMaxValue(Utils.dayth.length - 1);
            dayPicker.setDisplayedValues(Utils.dayth);
            dayPicker.setMinValue(0);
        }
    }

    /**
     * /设置选择年，控制二月天数
     */
    public void setyear(int i1) {
        //月份
        String mont = Utils.month[monthPicker.getValue()];
        //年份
        String str = numberyear[i1];
        //如果选择中的月份是二月
        if (mont.equals("02")) {
            //判断是闰年还是平年
            if (Utils.getyear().contains(str)) {
                dayPicker.setDisplayedValues(null);
                dayPicker.setMaxValue(Utils.daytwos.length - 1);
                dayPicker.setDisplayedValues(Utils.daytwos);
                dayPicker.setMinValue(0);
            } else {
                dayPicker.setDisplayedValues(null);
                dayPicker.setMaxValue(Utils.daytwo.length - 1);
                dayPicker.setDisplayedValues(Utils.daytwo);
                dayPicker.setMinValue(0);
            }
        }
    }


    /**
     * popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            Utils.backgroundAlpha(1f, CheckmassageActivity.this);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case check_message_lasttiem:
                meunpop("last");
                break;
            case R.id.check_message_username:
                Intent intent = new Intent(mContext, CheckuserActivity.class);
                intent.putExtra("orgId", orgId);
                startActivityForResult(intent, 2);
                break;
            case R.id.checklistback:
                //删除当前界面新增的图片
                for (int i = 0; i < Imagepath.size(); i++) {
                    FileUtils.deleteFile(Imagepath.get(i).getName());
                }
                finish();
                break;
            case checklistmeun:
                String meuntext = checklistmeuntext.getText().toString();
                if ("保存".equals(meuntext)) {
                    boolean lean = checkMessageSwitch.isChecked();
                    if (!lean) {
                        setStatusT();
                        deletemessage();
                    } else {
                        if (nameId.length() > 0) {
                            Save();
                        } else {
                            ToastUtils.showShortToast("选择负责人");
                        }
                    }
                } else if ("编辑".equals(meuntext)) {
                    checklistmeuntext.setText("保存");
                    checkMessageSwitch.setClickable(true);
                    setStatusT();
                } else {

                }
                break;
            case R.id.check_message_data:
                meunpop("data");
                break;
            default:
                break;
        }
    }

    //连续两次退出App
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //删除无用图片
            for (int i = 0; i < Imagepath.size(); i++) {
                FileUtils.deleteFile(Imagepath.get(i).getName());
            }
            finish();
            return true;
        }
        return true;
    }

    /**
     * 保存
     */
    public void Save() {
        hintKeyBoard();
        ArrayList<File> file = new ArrayList<>();
        for (int i = 0; i < Imagepath.size(); i++) {
            if (Imagepath.get(i).getContent().length() > 0) {

            } else {
                file.add(new File(Imagepath.get(i).getName()));
            }
        }
        OkGo.post(Requests.CREATE_NOTICE_BY_APP)
                .isMultipart(true)
                .params("id", messageid)
                .params("detailsId", taskId)
                //违反标准
                .params("standardDelName", checkMessageStandar.getText().toString())
                //整改事宜
                .params("rectificationReason", check_message_describe.getText().toString())
                //检查人
                .params("checkPersonName", SPUtils.getString(mContext, "id", ""))
                // 检查组织
                .params("checkOrgid", orgId)
                //检查时间
                .params("checkDate", MessageData.getText().toString())
                //整改期限
                .params("rectificationDate", checkMessageTime.getText().toString())
                //整改负责人Id
                .params("rectificationPerson", nameId)
                .addFileParams("imagesList", file)
                .params("deleteFileId", Dates.listToStrings(deleteId))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            ToastUtils.showShortToast(jsonObject.getString("msg"));
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                Map<String, Object> map = new HashMap<>();
                                map.put("messageId", data.getString("id"));
                                MapCallbackUtils.CallBackMethod(map);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

    }

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

    ArrayList<String> deleteId = new ArrayList<>();

    /**
     * 删除接口返回的图片，保存时上传Id
     *
     * @param id
     */
    public void deleteid(String id) {
        deleteId.add(id);
    }

    //获取数据
    public void getNoticeByApp() {
        Dates.getDialog(CheckmassageActivity.this, "请求数据中...");
        post(Requests.GET_NOTICE_BY_APP)
                .params("id", messageid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                JSONObject json = jsonObject.getJSONObject("data");
                                messageid = json.getString("id");
                                String stard = json.getString("standardDelName");
                                checkMessageStandar.setText(stard);
                                String Reaso = json.getString("rectificationReason");
                                check_message_describe.setText(Reaso);
                                String messageuser = json.getString("checkPersonName");
                                checkMessageUser.setText(messageuser);
                                //
                                String checkOrgName = json.getString("checkOrgName");
                                checkMessageOrg.setText(checkOrgName);
                                //负责人ID
                                nameId = json.getString("rectificationPerson");
                                MessageData.setText(json.getString("checkDate").substring(0, 10));
                                checkMessageUsernameText.setText(json.getString("rectificationPersonName"));
                                checkMessageTime.setText(json.getString("rectificationDate").subSequence(0, 10));
                                JSONArray jsonArray = json.getJSONArray("attachments");
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json1 = jsonArray.getJSONObject(i);
                                        Imagepath.add(new Audio(Requests.networks + json1.getString("filepath"), json1.getString("id")));
                                    }
                                }
                                setStatusF();
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

    public void deletemessage() {
        OkGo.post(Requests.DELETEMESSAGE)
                .params("id", messageid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            ToastUtils.showLongToast(jsonObject.getString("msg"));
                            if (jsonObject.getInt("ret") == 0) {
                                TaskCallbackUtils.CallBackMethod();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public String getsttus() {
        return checklistmeuntext.getText().toString();
    }

    public void setTitleView() {
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.get(i).setVisibility(View.VISIBLE);
        }
    }

    public void setTitleView1() {
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.get(i).setVisibility(View.GONE);
        }
    }

    public void setStatusT() {
        checkMessageStandar.setEnabled(true);
        checkMessageLasttiem.setClickable(true);
        checkMessageDate.setClickable(true);
        check_message_describe.setEnabled(true);
        check_message_username.setClickable(true);
        checkMessageSwitch.setEnabled(true);
        checklistmeuntext.setText("保存");
        photoAdapter.getData(Imagepath, true);
        setTitleView();
    }

    public void setStatusF() {
        setTitleView1();
        checkMessageSwitch.setEnabled(false);
        checkMessageStandar.setEnabled(false);
        checkMessageLasttiem.setClickable(false);
        checkMessageDate.setClickable(false);
        check_message_describe.setEnabled(false);
        check_message_username.setClickable(false);
        checklistmeuntext.setText("编辑");
        photoAdapter.getData(Imagepath, false);
    }
}
