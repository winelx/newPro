package com.example.administrator.newsdf.activity.home.same;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.example.administrator.newsdf.Adapter.DirectlyreplyAdapter;
import com.example.administrator.newsdf.App;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.CheckPermission;
import com.example.administrator.newsdf.camera.CropImageUtils;
import com.example.administrator.newsdf.camera.ImageUtil;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.service.LocationService;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.LogUtil;
import com.example.administrator.newsdf.utils.Requests;
import com.example.administrator.newsdf.utils.SPUtils;
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

import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.newsdf.R.id.reply_button;


/**
 * description: 任务回复界面
 *
 * @author: lx
 * date: 2018/2/6 0006 上午 8:53
 * update: 2018/2/6 0006
 * version:
 */
public class DirectlyreplyActivity extends AppCompatActivity {
    private DirectlyreplyAdapter photoAdapter;
    private ArrayList<File> files;
    private RecyclerView photoadd;
    private LocationService locationService;
    private TextView repley_address, wbs_text, title;
    private LinearLayout com_button;
    private String Latitude, Longitude;
    private EditText reply_text, partContent;
    private Context mContext;
    private Bitmap textBitmap = null;
    private ArrayList<String> imagePaths;
    private CheckPermission checkPermission;
    String id = null;
    private static final int IMAGE_PICKER = 101;
    private int num = 0;
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codeplay_repley);

        mContext = DirectlyreplyActivity.this;
        imagePaths = new ArrayList<>();
        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        //动态权限
        checkPermission = new CheckPermission(this) {
            @Override
            public void permissionSuccess() {
                CropImageUtils.getInstance().takePhoto(DirectlyreplyActivity.this);
            }

            @Override
            public void negativeButton() {
                //如果不重写，默认是finishddsfaasf
                //super.negativeButton();
                ToastUtils.showLongToast("权限申请失败！");
            }
        };
        findID();   //发现ID
        title.setText("任务回复");
//        reply_text.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_DEL) {
//                    num++;
//                    //在这里加判断的原因是点击一次软键盘的删除键,会触发两次回调事件
//                    if (num % 2 != 0) {
//                        String s = reply_text.getText().toString();
//                        if (!TextUtils.isEmpty(s)) {
//                            reply_text.setText("" + s.substring(0, s.length() - 1));
//                            //将光标移到最后
//                            reply_text.setSelection(reply_text.getText().length());
//                        }
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });
        loaction();//定位
        initDate();//recycclerView
    }

    //定位
    private void loaction() {
        //定位初始化
        locationService = ((App) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        locationService.start();// 定位SDK
    }

    //发现ID
    private void findID() {
        //部位名称
        partContent = (EditText) findViewById(R.id.partContent);
        //单选
        mRadioGroup = (RadioGroup) findViewById(R.id.codeplay_status);
        //标题
        title = (TextView) findViewById(R.id.com_title);
        //图片
        photoadd = (RecyclerView) findViewById(R.id.recycler_view);
        //地址
        repley_address = (TextView) findViewById(R.id.repley_address);
        //输入内容
        reply_text = (EditText) findViewById(R.id.reply_text);
        //提交
        com_button = (LinearLayout) findViewById(reply_button);
        //返回
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < imagePaths.size(); i++) {
                    Dates.deleteFile(imagePaths.get(i));
                }
                finish();
            }
        });
        com_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                files = new ArrayList<File>();
                String content = reply_text.getText().toString();
                for (int i = 0; i < imagePaths.size(); i++) {
                    files.add(new File(imagePaths.get(i)));
                }
                LogUtil.d("DirectlyreplyActivity", "files.size():" + files.size());
                if (content.isEmpty()) {
                    ToastUtils.showShortToast("回复内容不能为空");
                } else {
                    //保存状态，进行回退判断
                    SPUtils.putString(mContext, "back", "false");
                    if (partContent.getText().toString().length() != 0) {
                        RadioButton rb = (RadioButton) DirectlyreplyActivity.this.findViewById(mRadioGroup.getCheckedRadioButtonId());
                        try {
                            if (rb.getText() != null) {
                                String str = rb.getText().toString();
                                if (str == "是") {
                                } else {
                                    Okgo(Bai_address, files, false);
                                }
                            }
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            ToastUtils.showLongToast("该任务是否已经完成");
                        }
                    } else {
                        ToastUtils.showLongToast("具体部位不能为空");
                    }

                }
            }
        });
        com_button.setVisibility(View.VISIBLE);

    }

    //调用相机
    public void Cream() {
        showPopwindow();
    }

    //初始化recyclerview
    private void initDate() {
        photoAdapter = new DirectlyreplyAdapter(this, imagePaths);
        photoadd.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        photoadd.setAdapter(photoAdapter);
    }

    //网络请求
    private void Okgo(String address, ArrayList<File> file, boolean isAllFinished) {
        userPop();
        OkGo.post(Requests.Uploade)
                .params("id", id)
                .params("uploadContent", reply_text.getText().toString())
                .params("latitude", Latitude)
                .params("longitude", Longitude)
                .params("partContent", partContent.getText().toString())
                .params("isAllFinished ", isAllFinished)
                .params("uploadAddr", address)
                //上传图片
                .addFileParams("imagesList", file)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                //删除上传的图片
                                for (int i = 0; i < imagePaths.size(); i++) {
                                    Dates.deleteFile(imagePaths.get(i));
                                }
                                Intent intent = new Intent();
                                intent.putExtra("frag_id", id);
                                //回传数据到主Activity
                                setResult(RESULT_OK, intent);
                                finish(); //此方法后才能返回主Activity
                            }
                            popupWindow.dismiss();
                            popstatus = false;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        popupWindow.dismiss();
                        popstatus = false;
                    }


                });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for (int i = 0; i < images.size(); i++) {
                    double mdouble = Dates.getDirSize(new File(images.get(i).path));
                    if (mdouble != 0.0) {
                        Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                        Tiny.getInstance().source(images.get(i).path).asFile().withOptions(options).compress(new FileCallback() {
                            @Override
                            public void callback(boolean isSuccess, String outfile) {
                                //添加进集合
                                imagePaths.add(outfile);
                                //填入listview，刷新界面
                                photoAdapter.getData(imagePaths);
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
            CropImageUtils.getInstance().onActivityResult(this, requestCode, resultCode, data, new CropImageUtils.OnResultListener() {
                @Override
                public void takePhotoFinish(final String path) {
                    //根据路径压缩图片并返回bitmap
                    Bitmap bitmap = Dates.compressPixel(path);
                    //给压缩的图片添加时间水印(1)
                    String time = Dates.getDate();
                    textBitmap = ImageUtil.drawTextToRightBottom(mContext,
                            bitmap, time + Bai_address, 15, Color.WHITE, 0, 0);
                    //保存添加水印的时间的图片
                    Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                    Tiny.getInstance().source(textBitmap).asFile().withOptions(options).compress(new FileCallback() {
                        @Override
                        public void callback(boolean isSuccess, String outfile) {
                            //添加进集合
                            imagePaths.add(outfile);
                            //填入listview，刷新界面
                            photoAdapter.getData(imagePaths);
                            //删除相册原图
                            Dates.deleteFile(path);
                        }
                    });
                }
            });
        }


    }

    /***
     * Stop location service
     */
    @Override
    protected void onStop() {
        //注销掉监听
        locationService.unregisterListener(mListener);
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private String Bai_address = null;
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                // 纬度
                sb.append("\nlatitude : ");
                sb.append(location.getLatitude());
                Latitude = location.getLatitude() + "";
                // 经度
                sb.append("\nlontitude : ");
                sb.append(location.getLongitude());
                Longitude = location.getLongitude() + "";
                // 地址信息
                sb.append("\naddr : ");
                Bai_address = location.getAddrStr();
                repley_address.setText(location.getAddrStr());
                locationService.stop();// 定位SDK
            }
        }
    };

    private void showPopwindow() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(DirectlyreplyActivity.this.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        View popView = View.inflate(this, R.layout.camera_pop_menu, null);

        Button btnCamera = popView.findViewById(R.id.btn_camera_pop_camera);
        Button btnAlbum = popView.findViewById(R.id.btn_camera_pop_album);
        Button btnCancel = popView.findViewById(R.id.btn_camera_pop_cancel);
        RelativeLayout btn_camera_pop = popView.findViewById(R.id.btn_pop_add);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        final PopupWindow popWindow = new PopupWindow(popView, width, height);
        popWindow.setAnimationStyle(R.style.AnimBottom);
        popWindow.setFocusable(true);
        // 设置同意在外点击消失
        popWindow.setOutsideTouchable(false);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    //调用相机
                    case R.id.btn_camera_pop_camera:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            checkPermission.permission(CheckPermission.REQUEST_CODE_PERMISSION_CAMERA);
                        } else {
                            CropImageUtils.getInstance().takePhoto(DirectlyreplyActivity.this);
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

                        break;
                    //关闭pop
                    case R.id.btn_pop_add:
                        break;
                    default:
                        break;
                }
                popWindow.dismiss();
            }
        };
        btnCamera.setOnClickListener(listener);
        btnAlbum.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);
        ColorDrawable dw = new ColorDrawable(0x30000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    PopupWindow popupWindow;
    boolean popstatus = false;

    void userPop() {
        View view = getLayoutInflater().inflate(R.layout.pop_new_push, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
        //设置背景，
        popupWindow.setOutsideTouchable(false);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(false);
        //显示(靠中间)
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popstatus = true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //返回事件
            if (popstatus) {
            } else {
                for (int i = 0; i < imagePaths.size(); i++) {
                    Dates.deleteFile(imagePaths.get(i));
                }
                finish();
            }
        }

        return false;

    }

}