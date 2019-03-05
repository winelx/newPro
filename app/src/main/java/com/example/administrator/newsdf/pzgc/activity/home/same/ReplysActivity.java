package com.example.administrator.newsdf.pzgc.activity.home.same;

import android.Manifest;
import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.example.administrator.newsdf.App;
import com.example.administrator.newsdf.GreenDao.LoveDao;
import com.example.administrator.newsdf.GreenDao.Shop;
import com.example.administrator.newsdf.R;

import com.example.administrator.newsdf.camera.CropImageUtils;
import com.example.administrator.newsdf.camera.ImageUtil;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.PhotosAdapter;
import com.example.administrator.newsdf.pzgc.Adapter.TaskPhotoAdapter;

import com.example.administrator.newsdf.pzgc.activity.MainActivity;
import com.example.administrator.newsdf.pzgc.activity.home.HomeUtils;
import com.example.administrator.newsdf.pzgc.activity.work.TaskWbsActivity;
import com.example.administrator.newsdf.pzgc.bean.PhotoBean;
import com.example.administrator.newsdf.pzgc.service.LocationService;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;

import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.FloatMeunAnims;
import com.example.administrator.newsdf.pzgc.utils.PermissionListener;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.WbsDialog;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.newsdf.pzgc.utils.Dates.compressPixel;

/**
 * description:新增推送界面
 * autour: lx
 * date: 2018/2/5 0005 下午 2:53
 * update: 2018/2/5 0005
 * version:
 */
public class ReplysActivity extends BaseActivity implements View.OnClickListener {
    private PhotosAdapter photoAdapter;

    private ArrayList<PhotoBean> photoPopPaths, stardPaths;
    private ArrayList<File> files;
    private ArrayList<String> namess;
    private List<Shop> list;
    private RecyclerView photoadd;
    private LocationService locationService;
    private TextView repleyAddress, wbsNodeName, Sendtask, title, tvNetSpeed, replyCheckItem,drawer_layout_text;
    private ImageView Save;
    private String latitude, longitude;
    private EditText replyText;
    private Context mContext;
    private ProgressBar mProgressBar;
    private ArrayList<String> pathimg;
    private String content = "", wbspath = "", wbsID = "", id = "", wbstitle = "";
    boolean status = false;
    int position;
    private int page = 1;
    private ArrayList<String> check;
    private String Title = "", checkId = "", checkname = "";
    private WbsDialog selfDialog;
    private static final int IMAGE_PICKER = 101;
    private CircleImageView Circle;
    private SmartRefreshLayout smartRefreshLayout;
    private ListView drawerLayoutList;
    private DrawerLayout drawer;
    private TaskPhotoAdapter mAdapter;
    private boolean drew = true;
    private int num = 0;
    private String titles, type;
    private ProgressDialog dialog;
    private boolean isParent, iswbs;

    //弹出框
    private LinearLayout meun_standard, meun_photo;
    private FloatMeunAnims floatMeunAnims;
    private boolean liststatus = true;
    private boolean anim = true;
    private Bitmap textBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        mContext = ReplysActivity.this;
        floatMeunAnims = new FloatMeunAnims();

        //初始化集合
        initArray();
        findID();   //发现ID
        //有可能没有传递数据过来。所以如果没有传递，那就消费掉
        try {
            Intent intent = getIntent();
            //数据库第几个
            position = intent.getExtras().getInt("position");
            //输入内容
            content = intent.getExtras().getString("content");
            //wbs路径
            wbspath = intent.getExtras().getString("wbspath");
            wbstitle = intent.getExtras().getString("wbstitle");
            //检查点
            check = intent.getStringArrayListExtra("list");
            //当前节点ID
            wbsID = intent.getExtras().getString("id");
            //当前节点ID
            id = intent.getExtras().getString("id");
            title.setText(intent.getExtras().getString("title"));
            //检查点id集合
            iswbs = intent.getExtras().getBoolean("iswbs");
            type = intent.getExtras().getString("type");

            //
            isParent = intent.getExtras().getBoolean("isParent");
            //图片字符串
            checkId = intent.getExtras().getString("Checkid");
            checkname = intent.getExtras().getString("Checkname");
            if (checkname.length() < 0) {
                replyCheckItem.setText("选择检查点");
            } else {
                replyCheckItem.setText(checkname);
            }
            status = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            title.setText("我很主动");
        }
        wbsNodeName.setText(wbspath);
        replyText.setText(content);
        Save.setVisibility(View.VISIBLE);
        Save.setImageResource(R.mipmap.reply_baocun);
        //定位
        loaction();
        initDate();
    }

    private void initArray() {
        //任务项
        check = new ArrayList<>();
        //图片地址
        pathimg = new ArrayList<>();
        //图册图片路径
        photoPopPaths = new ArrayList<>();
        stardPaths = new ArrayList<>();
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

    /**
     * 发现ID
     */
    private void findID() {
        drawer_layout_text= (TextView) findViewById(R.id.drawer_layout_text);
        meun_standard = (LinearLayout) findViewById(R.id.meun_standard);
        meun_photo = (LinearLayout) findViewById(R.id.meun_photo);
        meun_photo.setOnClickListener(this);
        meun_standard.setOnClickListener(this);
        //图册list
        drawerLayoutList = (ListView) findViewById(R.id.drawer_layout_list);
        //抽屉控件
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //刷新控件
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        //查看图册控件
        Circle = (CircleImageView) findViewById(R.id.fab);
        //上传文件大小（未使用）
        tvNetSpeed = (TextView) findViewById(R.id.tvNetSpeed);
        //图片
        photoadd = (RecyclerView) findViewById(R.id.recycler_view);
        //定位地址
        repleyAddress = (TextView) findViewById(R.id.repley_address);
        //回复内容
        replyText = (EditText) findViewById(R.id.reply_text);
        //发送任务
        Sendtask = (TextView) findViewById(R.id.com_button);
        //检查项名称
        replyCheckItem = (TextView) findViewById(R.id.reply_check_item);
        //保存
        Save = (ImageView) findViewById(R.id.com_img);
        //节点名
        wbsNodeName = (TextView) findViewById(R.id.wbs_text);
        //标题
        title = (TextView) findViewById(R.id.com_title);

        findViewById(R.id.reply_wbs).setOnClickListener(this);
        findViewById(R.id.reply_check).setOnClickListener(this);
        findViewById(R.id.com_back).setOnClickListener(this);
        Circle.setOnClickListener(this);
        Save.setOnClickListener(this);
        Sendtask.setOnClickListener(this);
        mProgressBar = (ProgressBar) findViewById(R.id.reply_bar);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //侧滑栏关闭手势滑动
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawer.setScrimColor(Color.TRANSPARENT);
        mAdapter = new TaskPhotoAdapter(photoPopPaths, mContext);
        drawerLayoutList.setAdapter(mAdapter);
        smartRefreshLayout.setEnableRefresh(false);
        //上拉加载
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                drew = false;
                //传入false表示加载失败
                refreshlayout.finishLoadmore(1500);
                if (liststatus) {
                    HomeUtils.photoAdm(wbsID, page, photoPopPaths, drew, mAdapter, wbsNodeName.getText().toString());
                } else {
                    HomeUtils.getStard(wbsID, page, stardPaths, drew, mAdapter, wbsNodeName.getText().toString());
                }
            }
        });

    }

    /**
     * 调用相机图册
     */
    public void Cream() {
        showPopwindow();
    }

    /**
     * 初始化recyclerview
     */
    private void initDate() {
        if (pathimg.size() == 0 || pathimg.get(0) == "") {
            pathimg.clear();
        }
        photoAdapter = new PhotosAdapter(this, pathimg);
        photoadd.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        photoadd.setAdapter(photoAdapter);
        Sendtask.setBackgroundResource(R.mipmap.reply_commit);
    }

    /**
     * 点击事件判断
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.com_button:
                files = new ArrayList<>();
                //将图片转成file格式
                for (int i = 0; i < pathimg.size(); i++) {
                    files.add(new File(pathimg.get(i)));
                }
                if (wbsID == null || wbsID.equals("")) {
                    Toast.makeText(mContext, "没有选择wbs节点", Toast.LENGTH_SHORT).show();
                } else if ("".equals(replyText.getText().toString())) {
                    Toast.makeText(mContext, "请输入具体内容描述", Toast.LENGTH_SHORT).show();
                } else {
                    Okgo(files, Bai_address);
                }
                break;
            //选择wbs结构
            case R.id.reply_wbs:
                Intent intent = new Intent(ReplysActivity.this, TaskWbsActivity.class);
                intent.putExtra("wbsname", wbstitle);
                intent.putExtra("wbspath", wbspath);
                intent.putExtra("wbsID", wbsID);
                intent.putExtra("type", type);
                intent.putExtra("isParent", isParent);
                intent.putExtra("iswbs", iswbs);
                startActivityForResult(intent, 1);
                break;
            case R.id.com_back:
                for (int i = 0; i < pathimg.size(); i++) {
                    Dates.deleteFile(pathimg.get(i));
                }
                finish();
                break;
            //选择检查项
            case R.id.reply_check:
                Intent intent1 = new Intent(ReplysActivity.this, Checkpoint.class);
                intent1.putExtra("wbsID", wbsID);
                startActivityForResult(intent1, 1);
                break;
            case R.id.com_img:
                selfDialog = new WbsDialog(ReplysActivity.this);
                selfDialog.setMessage("是否保存本地");
                selfDialog.setYesOnclickListener("确定", new WbsDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        selfDialog.dismiss();
                        Shop shop = new Shop();
                        shop.setType(Shop.TYPE_LOVE);
                        //路径
                        shop.setName(wbsNodeName.getText().toString());
                        //wbsID
                        shop.setWebsid(wbsID);
                        //时间
                        shop.setTimme(Dates.getDate());
                        //内容
                        shop.setContent(replyText.getText().toString());
                        shop.setImage_url(Dates.listToString(pathimg));
                        shop.setCheckid(checkId);
                        //检查点
                        if (replyCheckItem.getText().toString().length() != 0) {
                            shop.setCheckname(replyCheckItem.getText().toString());
                        } else {
                            shop.setCheckname("自定义");
                        }
                        LoveDao.insertLove(shop);
                        finish();
                    }
                });
                selfDialog.setNoOnclickListener("取消", new WbsDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        selfDialog.dismiss();
                    }
                });
                selfDialog.show();
                break;
            case R.id.fab:
                //打开meun选项
                if (anim) {
                    floatMeunAnims.doclickt(meun_photo, meun_standard, Circle);
                    anim = false;
                } else {
                    floatMeunAnims.doclicktclose(meun_photo, meun_standard, Circle);
                    anim = true;
                }
                break;
            //选择检查项
            case R.id.meun_photo:
                //请求图纸
                //加载第一页
                page = 1;
                //请求数据时清除之前的
                drew = true;
                //网络请求
                Dates.getDialog(ReplysActivity.this, "请求数据中...");
                photoPopPaths.clear();
                drawer_layout_text.setText("图纸");
                mAdapter.getData(photoPopPaths,"");
                HomeUtils.photoAdm(wbsID, page, photoPopPaths, drew, mAdapter, wbsNodeName.getText().toString());
                //上拉加载的状态判断
                liststatus = true;
                drawer.openDrawer(GravityCompat.START);
                break;
            case R.id.meun_standard:
                //标准
                //加载第一页
                page = 1;
                //请求数据时清除之前的
                drew = true;
                //上拉加载的状态判断
                liststatus = false;
                drawer_layout_text.setText("标准");
                Dates.getDialog(ReplysActivity.this, "请求数据中...");
                photoPopPaths.clear();
                mAdapter.getData(photoPopPaths,"");
                HomeUtils.getStard(wbsID, page, stardPaths, drew, mAdapter, wbsNodeName.getText().toString());
                drawer.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断是不是Activity的返回，不是就是相机的返回
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //返回wbs
            id = data.getStringExtra("id");
            wbsNodeName.setText(data.getStringExtra("titles"));
            //请求图片页数
            page = 1;
            //清除选择项ID
            checkId = "";
            //清除选择项名称
            titles = "";
            //控件显示文字
            replyCheckItem.setText(data.getStringExtra(""));
            //请求图片
            HomeUtils.photoAdm(wbsID, page, photoPopPaths, drew, mAdapter, wbsNodeName.getText().toString());
        } else if (requestCode == 1 && resultCode == 2) {
            //检查点返回
            checkId = data.getStringExtra("id");
            titles = data.getStringExtra("name");
            replyCheckItem.setText(data.getStringExtra("name"));
        } else if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //相册返回数据
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for (int i = 0; i < images.size(); i++) {
                    double mdouble = Dates.getDirSize(new File(images.get(i).path));
                    if (mdouble != 0.0) {
                        //压缩图片
                        Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                        Tiny.getInstance().source(images.get(i).path).asFile().withOptions(options).compress(new FileCallback() {
                            @Override
                            public void callback(boolean isSuccess, String outfile) {
                                //添加进集合
                                pathimg.add(outfile);
                                //填入listview，刷新界面
                                photoAdapter.getData(pathimg);
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
            //拍照
            CropImageUtils.getInstance().onActivityResult(this, requestCode, resultCode, data, new CropImageUtils.OnResultListener() {
                @Override
                public void takePhotoFinish(final String path) {
                    //获取图片选择角度，旋转图片
                    Bitmap bitmap = CropImageUtils.rotaingImageView(CropImageUtils.readPictureDegree(path), compressPixel(path));
                    //给压缩的图片添加时间水印(1)
                    textBitmap = ImageUtil.drawTextToRightBottom(mContext,
                            bitmap, Dates.getDate() + Bai_address, 15, Color.WHITE, 0, 0);
                    //压缩保存添加水印的时间的图片
                    Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                    Tiny.getInstance().source(textBitmap).asFile().withOptions(options).compress(new FileCallback() {
                        @Override
                        public void callback(boolean isSuccess, String outfile) {
                            //添加进集合
                            pathimg.add(outfile);
                            //填入listview，刷新界面
                            photoAdapter.getData(pathimg);
                            //删除原图
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
        // TODO Auto-generated method stub
        //注销掉监听
        locationService.unregisterListener(mListener);
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    /**
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     */
    private String Bai_address = "";
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                latitude = location.getLatitude() + "";
                // 地址信息
                longitude = location.getLongitude() + "";
                Bai_address = location.getAddrStr();
                if (Bai_address != null && !Bai_address.equals("")) {
                } else {
                    Bai_address = "";
                }
                repleyAddress.setText(location.getAddrStr());
                // 定位SDK
                locationService.stop();
            }
        }
    };

    /**
     * 返回更新数据
     */
    private void Updata() {
        OkGo.post(Requests.WbsTaskGroup)
                .params("wbsId", id)
                .params("isNeedTotal", "true")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.contains("data")) {
                            namess = new ArrayList<String>();
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    String id = json.getString("id");
                                    String name = json.getString("detectionName");
                                    String totalNum = json.getString("totalNum");
                                    namess.add("   " + name + "(" + totalNum + ")" + "       ");
                                }
                                //返回列表
                                Intent intent = new Intent();
                                intent.putStringArrayListExtra("name", namess);
                                //回传数据到主Activity
                                setResult(RESULT_OK, intent);
                                //此方法后才能返回主Activity
                                finish();
                                dialog.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        dialog.dismiss();
                    }
                });
    }

    /**
     * 这是图册相机时的弹出框
     */
    private void showPopwindow() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(ReplysActivity.this.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        View popView = View.inflate(this, R.layout.camera_pop_menu, null);
        RelativeLayout btn_camera_pop = popView.findViewById(R.id.btn_pop_add);
        Button btnCamera = (Button) popView.findViewById(R.id.btn_camera_pop_camera);
        Button btnAlbum = (Button) popView.findViewById(R.id.btn_camera_pop_album);
        Button btnCancel = (Button) popView.findViewById(R.id.btn_camera_pop_cancel);

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        final PopupWindow popWindow = new PopupWindow(popView, width, height);
        popWindow.setAnimationStyle(R.style.AnimBottom);
        popWindow.setClippingEnabled(false);
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
                            getauthority();
                        } else {
                            CropImageUtils.getInstance().takePhoto(ReplysActivity.this);
                        }
                        break;
                    //相册图片
                    case R.id.btn_camera_pop_album:
                        //开启相册
                        Intent intent = new Intent(mContext, ImageGridActivity.class);
                        startActivityForResult(intent, IMAGE_PICKER);
                        break;
                    //关闭pop
                    case R.id.btn_camera_pop_cancel:
                        break;
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
        btn_camera_pop.setOnClickListener(listener);
        ColorDrawable dw = new ColorDrawable(0x30000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


    /**
     * 重写返回键，根据业务进行控制
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            for (int i = 0; i < pathimg.size(); i++) {
                Dates.deleteFile(pathimg.get(i));
            }
            finish();
        }
        return false;
    }

    /**
     * 网络请求
     */
    private void Okgo(ArrayList<File> file, final String address) {
        dialog = new ProgressDialog(mContext);
        dialog.setMessage("提交数据中...");
        // 设置是否可以通过点击Back键取消
        dialog.setCancelable(true);
        // 设置在点击Dialog外是否取消Dialog进度条
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        OkGo.post(Requests.Uploade)
                .isMultipart(true)
                .params("wbsId", wbsID)
                .params("uploadContent", replyText.getText().toString())
                .params("latitude", latitude)
                .params("longitude", longitude)
                .params("uploadAddr", address)
                .params("wbsqastaffId", checkId)
                //上传图片
                .addFileParams("imagesList", file)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            String msg = jsonObject.getString("msg");
                            if (ret == 0) {
                                //删除上传的图片
                                for (int i = 0; i < pathimg.size(); i++) {
                                    Dates.deleteFile(pathimg.get(i));
                                }
                                ToastUtils.showShortToast(msg);
                                if (position != -1) {
                                    LoveDao.deleteLove(list.get(position).getId());
                                }
                                Updata();
                            } else {
                                ToastUtils.showShortToast(msg);
                            }
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        dialog.dismiss();
                    }

                    //进度条
                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        super.upProgress(currentSize, totalSize, progress, networkSpeed);
                        mProgressBar.setProgress((int) (100 * progress));
                        tvNetSpeed.setText("已上传" + currentSize / 1024 / 1024 + "MB, 共" + totalSize / 1024 / 1024 + "MB;");
                    }
                });
    }

    public void getauthority() {
        //获取相机权限，定位权限，内存权限
        requestRunPermisssion(new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION}, new PermissionListener() {
            @Override
            public void onGranted() {
                //表示所有权限都授权了
                //表示所有权限都授权了
                CropImageUtils.getInstance().takePhoto(ReplysActivity.this);
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
