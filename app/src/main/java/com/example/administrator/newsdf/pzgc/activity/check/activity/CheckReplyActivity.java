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
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.newsdf.R;

import com.example.administrator.newsdf.camera.CropImageUtils;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.CheckPhotoAdapter;

import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.callback.MoreTaskCallbackUtils;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.CameraUtils;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.PermissionListener;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.newsdf.pzgc.utils.Dates.compressPixel;

/**
 * description: 整改回复
 *
 * @author lx
 * date: 2018/8/9 0009 上午 10:12
 * update: 2018/8/9 0009
 * version:
 */
public class CheckReplyActivity extends BaseActivity implements View.OnClickListener {
    private CheckPhotoAdapter mAdapter;
    private RecyclerView checkReplyRec;
    private ArrayList<Audio> imagepath;
    private Context mContext;
    private static final int IMAGE_PICKER = 101;
    private String id, noticeId, sdealId = "", repyId, repycontent;
    private EditText replyDescription;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_reply);
        imagepath = new ArrayList<>();
        TextView titleView = (TextView) findViewById(R.id.titleView);
        titleView.setText("整改回复");
        mContext = CheckReplyActivity.this;
        replyDescription = (EditText) findViewById(R.id.replyDescription);
        TextView checklistmeuntext = (TextView) findViewById(R.id.checklistmeuntext);
        checklistmeuntext.setText("保存");
        checklistmeuntext.setVisibility(View.VISIBLE);
        checkReplyRec = (RecyclerView) findViewById(R.id.check_reply_rec);
        findViewById(R.id.checklistback).setOnClickListener(this);
        checkReplyRec.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        checkReplyRec.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new CheckPhotoAdapter(mContext, imagepath, "CheckReply", true);
        checkReplyRec.setAdapter(mAdapter);
        findViewById(R.id.checklistmeun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        Intent intent = getIntent();
        try {
            repyId = intent.getStringExtra("repyId");
            id = intent.getStringExtra("noticeId");
            repycontent = intent.getStringExtra("repycontent");
            sdealId = intent.getStringExtra("sdealId");
            list = intent.getStringArrayListExtra("list");
            ids = intent.getStringArrayListExtra("ids");
            replyDescription.setText(repycontent);
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    imagepath.add(new Audio(list.get(i), ids.get(i)));
                }
                mAdapter.getData(imagepath, true);
            }
        } catch (Exception e) {
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checklistback:
                for (int i = 0; i < imagepath.size(); i++) {
                    if (imagepath.get(i).getContent().length() > 0) {
                        Dates.deleteFile(imagepath.get(i).getName());
                    }
                }
                finish();
                break;
            default:
                break;
        }
    }

    //添加图片
    public void showPopwindow() {
        hintKeyBoard();
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
                            getauthority();
                        } else {
                            CropImageUtils.getInstance().takePhoto(CheckReplyActivity.this);
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
                                mAdapter.getData(imagepath, true);
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
                            mAdapter.getData(imagepath, true);
                            //删除原图
                            Dates.deleteFile(path);
                        }
                    });

                }
            });
        }

    }

    //连续两次退出App
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //删除无用图片
            for (int i = 0; i < imagepath.size(); i++) {
                if (imagepath.get(i).getContent().length() > 0) {
                    Dates.deleteFile(imagepath.get(i).getName());
                }
            }
            finish();
            return true;
        }
        return true;
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

    ArrayList<String> deleteLis = new ArrayList<>();

    public void delete(int pos) {
        String conet = imagepath.get(pos).getContent();
        if (conet.length() > 0) {
            deleteLis.add(imagepath.get(pos).getContent());
        }
        imagepath.remove(pos);
        mAdapter.getData(imagepath, true);
    }

    public void save() {
        String result = replyDescription.getText().toString();
        if (result.length() > 0) {
            ArrayList<File> files = new ArrayList<>();
            if (imagepath.size() > 0) {
                for (int i = 0; i < imagepath.size(); i++) {
                    String str = imagepath.get(i).getContent();
                    if (str.isEmpty()) {
                        files.add(new File(imagepath.get(i).getName()));
                    }

                }
            }
            Dates.getDialog(CheckReplyActivity.this, "提交数据中...");
            OkGo.post(Requests.saveReplyDataApp)
                    .isMultipart(true)
                    .params("id", repyId)
                    .params("noticeId", id)
                    .params("dealId", sdealId)
                    .params("deleteFileId", Dates.listToStrings(deleteLis))
                    .params("replyDescription", replyDescription.getText().toString())
                    .addFileParams("attachment", files)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                int ret = jsonObject.getInt("ret");
                                if (ret == 0) {
                                    MoreTaskCallbackUtils.removeCallBackMethod();
                                    finish();
                                } else {
                                    ToastUtils.showShortToast(jsonObject.getString("msg"));
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
                        }
                    });
        } else {
            ToastUtils.showLongToast("具体描述不能为空");
        }
    }

    public void getauthority() {
        CameraUtils.requestRunPermisssion(this, new PermissionListener() {
            @Override
            public void onGranted() {
                //表示所有权限都授权了
                CropImageUtils.getInstance().takePhoto(CheckReplyActivity.this);
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                for (String permission : deniedPermission) {
                    Toast.makeText(mContext, "被拒绝的权限：" +
                            permission, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
