package com.example.administrator.fengji.pzgc.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.fengji.R;
import com.example.administrator.fengji.camera.TakePictureManager;
import com.example.administrator.fengji.pzgc.activity.check.activity.newcheck.bean.Enum;
import com.example.administrator.fengji.pzgc.adapter.BasePhotoAdapter;
import com.example.administrator.fengji.pzgc.utils.Dates;
import com.example.administrator.fengji.pzgc.utils.PopCameraUtils;
import com.example.administrator.fengji.pzgc.utils.SPUtils;
import com.example.administrator.fengji.pzgc.utils.ToastUtils;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.bean.photoBean;
import com.example.baselibrary.utils.Requests;
import com.example.baselibrary.utils.dialog.BaseDialogUtils;
import com.example.baselibrary.utils.network.NetWork;
import com.example.baselibrary.view.PermissionListener;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 说明：
 * 创建时间： 2020/12/14 0014 10:15
 *
 * @author winelx
 */
public class AddFrileUpdataActivity extends BaseActivity {
    private BasePhotoAdapter addPhotosAdapter;
    private ArrayList<photoBean> pathlist;
    private TakePictureManager takePictureManager;
    private String billId, relateFeild, relateTable, url, ty;
    private TextView toolbarIconTitle, save;
    private LinearLayout toolbarIconBack;
    private RecyclerView recycler;
    private PopCameraUtils popCameraUtils;
    private static final int IMAGE_PICKER = 1011;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfrileupdata);
        popCameraUtils = new PopCameraUtils();
        takePictureManager = new TakePictureManager(this);
        billId = getIntent().getStringExtra("billId");
        relateFeild = getIntent().getStringExtra("relateFeild");
        relateTable = getIntent().getStringExtra("relateTable");
        url = getIntent().getStringExtra("url");
        ty = getIntent().getStringExtra("ty");
        pathlist = new ArrayList();
        save = findViewById(R.id.save);
        toolbarIconBack = findViewById(R.id.com_back);
        toolbarIconTitle = findViewById(R.id.com_title);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new GridLayoutManager(mContext, 4));
        toolbarIconBack.setOnClickListener(v -> finish());
        toolbarIconTitle.setText("上传附件");
        recycler.setLayoutManager(new GridLayoutManager(mContext, 4));
        addPhotosAdapter = new BasePhotoAdapter(mContext, new ArrayList<>());
        recycler.setAdapter(addPhotosAdapter);
        addPhotosAdapter.addview(true);
        addPhotosAdapter.setOnItemClickListener(new BasePhotoAdapter.OnItemClickListener() {
            @Override
            public void addlick(View view, int position) {
                permisssion();
            }

            @Override
            public void delete(int position) {
                //删除图片
                pathlist.remove(position);
                addPhotosAdapter.getData(pathlist);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pathlist.size() > 0) {
                    request();
                } else {
                    ToastUtils.showShortToast("请添加上传的附件");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Enum.IMAGE_PICKER) {
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
                                //添加进集合
                                pathlist.add(new photoBean(outfile, "jpg"));
                                //填入listview，刷新界面
                                addPhotosAdapter.getData(pathlist);
                            }
                        });
                    } else {
                        ToastUtils.showLongToast("请检查上传的图片是否损坏");
                    }
                }
            }
        } else {
            if (takePictureManager != null) {
                takePictureManager.attachToActivityForResult(requestCode, resultCode, data);
            }
        }

    }

    public void request() {
        ArrayList<File> list = new ArrayList<>();
        for (int i = 0; i < pathlist.size(); i++) {
            list.add(new File(pathlist.get(i).getPhotopath()));
        }
        Map<String, String> map = new HashMap<>();
        map.put("relateFeild", relateFeild);
        map.put("relateTable", relateTable);
        map.put("billId", billId);
        map.put("ty", ty);
        map.put("userId", SPUtils.getString(mContext, "id", ""));
        Dates.getDialogs((Activity) mContext, "正在提交数据...");
        NetWork.postHttp(Requests.networks + url, map, list, true, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Dates.disDialog();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getInt("ret") == 0) {
                        ToastUtils.showShortToast("上传文件成功,请到pc端查看");
                        finish();
                    } else {
                        ToastUtils.showShortToast(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                Dates.disDialog();
                ToastUtils.showShortToast("请求失败");
            }
        });
    }

    /**
     * 权限申请
     */
    public void permisssion() {
        requestRunPermisssion(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted() {
                //展示弹出窗
                popCameraUtils.showPopwindow((Activity) mContext, new PopCameraUtils.CameraCallback() {
                    @Override
                    public void oncamera() {
                        // 开始拍照
                        takePictureManager.startTakeWayByCarema();
                        //数据返回
                        takePictureManager.setTakePictureCallBackListener(new TakePictureManager.takePictureCallBackListener() {
                            @Override
                            public void successful(boolean isTailor, final File outFile, Uri filePath) {
                                //实例化Tiny.FileCompressOptions，并设置quality的数值（quality改变图片压缩质量）
                                Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                                options.quality = 95;
                                Tiny.getInstance().source(outFile).asFile().withOptions(options).compress(new FileCallback() {
                                    @Override
                                    public void callback(boolean isSuccess, String file) {
                                        //添加进集合
                                        pathlist.add(new photoBean(file, "jpg"));
                                        //填入listview，刷新界面
                                        addPhotosAdapter.getData(pathlist);
                                        //删除相册原图
                                        Dates.deleteFile(outFile);
                                    }
                                });
                            }

                            @Override
                            public void failed(int errorCode, List<String> deniedPermissions) {
                                ToastUtils.showLongToast("相机权限被禁用，无法使用相机");
                            }
                        });
                    }

                    @Override
                    public void onalbum() {
                        //相册
                        Intent intent = new Intent(mContext, ImageGridActivity.class);
                        startActivityForResult(intent, IMAGE_PICKER);
                    }
                });
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                for (String permission : deniedPermission) {
                    if (permission.equals(Enum.CAMERA)) {
                        BaseDialogUtils.openAppDetails(mContext, "需要相机权限,请到权限管理中心打开");
                    } else {
                        BaseDialogUtils.openAppDetails(mContext, "更新需要手机存储权限,请到权限管理中心打开");
                    }
                }

            }
        });
    }
}
