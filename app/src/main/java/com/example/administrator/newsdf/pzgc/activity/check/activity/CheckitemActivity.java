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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.FileUtils;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.CheckPermission;
import com.example.administrator.newsdf.camera.CropImageUtils;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.CheckNewAdapter;
import com.example.administrator.newsdf.pzgc.Adapter.PhotoAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.CheckUtils;
import com.example.administrator.newsdf.pzgc.bean.chekitemList;
import com.example.administrator.newsdf.pzgc.utils.DKDragView;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.Requests;
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

import okhttp3.Call;
import okhttp3.Response;

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
public class CheckitemActivity extends AppCompatActivity implements View.OnClickListener {
    private DKDragView dkDragView;
    private LinearLayout checkItemTadown, checkItemTabup, checkItemContentMassage;
    private TextView checkItemTabupText, checkItemTadownText, titleView, checkitemcontentStatus;
    private GridView checklist;
    private DrawerLayout drawerLayout;
    private RecyclerView photoadd;
    private CheckNewAdapter adapter;
    private PhotoAdapter photoAdapter;
    private ArrayList<chekitemList> mData;
    private CheckPermission checkPermission;
    private Context mContext;
    private ArrayList<String> Imagepath;
    private static final int IMAGE_PICKER = 101;
    private String taskId,orgId;
    private int number, size;
    private CheckUtils checkUtils;
    private TextView checkItemContentName, checkItemContentContentname, checkItemContentBz, checkItemContentStandarcore;
    private EditText checkItemContentCore, checkItemContentDescribe;
    private Switch switch1;
    private LinearLayout check_item_tabup, check_item_tadown;
    private ArrayList<String> imageId;
    private Boolean generate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkitem);
        checkUtils = new CheckUtils();
        Intent intent = getIntent();
        taskId = intent.getStringExtra("id");
        orgId = intent.getStringExtra("orgId");
        number = intent.getIntExtra("number", 0);
        size = intent.getIntExtra("size", 0);
        mData = new ArrayList<>();
        Imagepath = new ArrayList<>();
        mContext = this;
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

        check_item_tabup = (LinearLayout) findViewById(R.id.check_item_tabup);
        check_item_tadown = (LinearLayout) findViewById(R.id.check_item_tadown);
        //是否无此项
        switch1 = (Switch) findViewById(R.id.switch1);
        //是否生成整改通知
        checkitemcontentStatus = (TextView) findViewById(R.id.checkItemContent_status);
        //检查描述
        checkItemContentDescribe = (EditText) findViewById(R.id.check_item_content_describe);
        //检查项名称
        checkItemContentName = (TextView) findViewById(R.id.check_item_content_name);
        //检查项内容名称
        checkItemContentContentname = (TextView) findViewById(R.id.check_item_content_contentname);
        //检查项要求
        checkItemContentBz = (TextView) findViewById(R.id.check_item_content_bz);
        //标准分
        checkItemContentStandarcore = (TextView) findViewById(R.id.check_item_content_standarcore);
        //得分
        checkItemContentCore = (EditText) findViewById(R.id.check_item_content_core);
        checkitemcontentStatus = (TextView) findViewById(R.id.checkItemContent_status);
        //标题
        titleView = (TextView) findViewById(R.id.titleView);
        //整改通知
        findViewById(R.id.check_item_content_massage).setOnClickListener(this);
        //附件
        photoadd = (RecyclerView) findViewById(R.id.recycler_view);
        //抽屉控件
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        //拖动控件
        dkDragView = (DKDragView) findViewById(R.id.float_suspension);
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
        //下一项点击事件
        checkItemTadown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击下一项时，上一项文字变为灰色
                checkItemTabupText.setTextColor(Color.parseColor("#646464"));
                //文字变为白色
                checkItemTadownText.setTextColor(Color.parseColor("#ffffff"));
                //下一项背景变为灰色
                checkItemTadown.setBackgroundResource(R.drawable.tab_choose_down_gray);
                //上一下背景变为白色
                checkItemTabup.setBackgroundResource(R.drawable.tab_choose_up);
            }
        });
        //上一项点击事件
        checkItemTabup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //参考下一项点击事件
                checkItemTabupText.setTextColor(Color.parseColor("#ffffff"));
                checkItemTadownText.setTextColor(Color.parseColor("#646464"));
                checkItemTabup.setBackgroundResource(R.drawable.tab_choose_up_gray);
                checkItemTadown.setBackgroundResource(R.drawable.tab_choose_down);
            }
        });
        //右侧布局的gridview的适配器
        adapter = new CheckNewAdapter(mContext, mData);
        checklist.setAdapter(adapter);
        //附件的recycleraview的适配器
        photoadd.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        photoadd.setItemAnimator(new DefaultItemAnimator());
        photoAdapter = new PhotoAdapter(mContext, Imagepath, "Check");
        photoadd.setAdapter(photoAdapter);
        titleView.setText(number + "/" + size);
        if (number == 1) {
            check_item_tabup.setVisibility(View.INVISIBLE);
        } else if (number == size) {
            check_item_tadown.setVisibility(View.INVISIBLE);
        }
        checklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int page = position + 1;
                getdate(page);
                titleView.setText(page + "/" + size);
            }
        });
        getdate(number);
        getcheckitemList();
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
//                    //删除原图
                            Dates.deleteFile(path);
                        }
                    });

                }
            });
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checklistback:
                //删除已有图片
                for (int i = 0; i < Imagepath.size(); i++) {
                    FileUtils.deleteFile(Imagepath.get(i));
                }
                finish();
                break;
            case R.id.check_item_content_massage:
                Intent intent = new Intent(CheckitemActivity.this, CheckmassageActivity.class);
                intent.putExtra("list", Imagepath);
                intent.putExtra("orgId",orgId);
                intent.putExtra("imageId", imageId);
                intent.putExtra("standar", checkItemContentBz.getText().toString());
                intent.putExtra("describe", checkItemContentDescribe.getText().toString());
                intent.putExtra("status", generate);
                startActivity(intent);
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
                FileUtils.deleteFile(Imagepath.get(i));
            }
            finish();
            return true;
        }
        return true;
    }

    public void getdate(int page) {
        post(Requests.INFO_BY_MAIN_ID_AND_SQE)
                .params("id", taskId)
                .params("page", page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                imageId = new ArrayList<>();
                                Imagepath.clear();
                                JSONObject json = jsonObject.getJSONObject("data");
                                checkItemContentName.setText(json.getString("name"));
                                checkItemContentContentname.setText(json.getString("content"));
                                checkItemContentBz.setText(json.getString("standard"));
                                checkItemContentStandarcore.setText(json.getString("standardScore"));
                                checkItemContentCore.setText(json.getString("score"));
                                String describe = json.getString("describe");
                                if (describe.length() > 0) {
                                    checkItemContentDescribe.setText(describe);
                                } else {
                                    checkItemContentDescribe.setText("未输入描述");
                                }


                                try {
                                    generate = json.getBoolean("generate");
                                    if (generate) {
                                        checkitemcontentStatus.setText("是");
                                    } else {
                                        checkitemcontentStatus.setText("否");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    generate = false;
                                    checkitemcontentStatus.setText("否");
                                }
                                switch1.setChecked(json.getBoolean("noSuch"));
                                JSONArray attachments = json.getJSONArray("attachments");
                                if (attachments.length() > 0) {
                                    for (int i = 0; i < attachments.length(); i++) {
                                        JSONObject attach = attachments.getJSONObject(i);
                                        imageId.add(attach.getString("id"));
                                        Imagepath.add(Requests.networks + attach.getString("filepath"));
                                    }
                                    photoAdapter.getData(Imagepath);
                                }
                            } else {
                                ToastUtils.showShortToast(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                                        String standardScore = json.getString("standardScore");
                                        boolean noSuch = json.getBoolean("noSuch");
                                        boolean penalty = json.getBoolean("penalty");
                                        mData.add(new chekitemList(id, score, sequence, standardScore, noSuch, penalty));
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
}
