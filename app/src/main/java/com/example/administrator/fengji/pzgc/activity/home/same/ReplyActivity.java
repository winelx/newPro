package com.example.administrator.fengji.pzgc.activity.home.same;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.example.administrator.fengji.App;
import com.example.administrator.fengji.GreenDao.LoveDao;
import com.example.administrator.fengji.GreenDao.Shop;
import com.example.administrator.fengji.R;
import com.example.administrator.fengji.camera.CropImageUtils;
import com.example.administrator.fengji.camera.ImageUtil;
import com.example.administrator.fengji.pzgc.activity.check.activity.newcheck.bean.Enum;
import com.example.administrator.fengji.pzgc.activity.home.utils.HomeUtils;
import com.example.administrator.fengji.pzgc.activity.work.MmissPushActivity;
import com.example.administrator.fengji.pzgc.adapter.PhotoAdapter;
import com.example.administrator.fengji.pzgc.adapter.TaskPhotoAdapter;
import com.example.administrator.fengji.pzgc.bean.PhotoBean;
import com.example.administrator.fengji.pzgc.callback.TaskCallbackUtils;
import com.example.administrator.fengji.pzgc.service.LocationService;
import com.example.administrator.fengji.pzgc.utils.Dates;
import com.example.administrator.fengji.pzgc.utils.FloatMeunAnims;
import com.example.administrator.fengji.pzgc.utils.ToastUtils;
import com.example.administrator.fengji.pzgc.utils.WbsDialog;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.utils.Requests;
import com.example.baselibrary.utils.dialog.BaseDialogUtils;
import com.example.baselibrary.view.PermissionListener;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.fengji.R.id.drawer_layout;
import static com.example.administrator.fengji.pzgc.utils.Dates.compressPixel;


/**
 * description: 主动上传任务界面
 *
 * @author lx
 * date: 2018/2/6 0006 上午 11:14
 * update: 2018/2/6 0006
 * version:
 */
public class ReplyActivity extends BaseActivity implements View.OnClickListener {
    private PhotoAdapter photoAdapter;
    private ArrayList<File> files;
    private List<Shop> list;
    private RecyclerView photoadd;
    private LocationService locationService;
    private TextView repleyAddress, wbsText, comButton, title, tvNetSpeed, replyCheckItem, drawerLayoutText;
    private ImageView Save;
    private String latitude, longitude;
    private EditText replyText;
    private Context mContext;
    private ArrayList<String> pathimg;
    private String content = "", wbsname = "", wbsID = "", id = "";
    boolean status = false;
    int position;
    private String Title = "", checkId = "", checkname = "";
    private WbsDialog selfDialog;
    private static final int IMAGE_PICKER = 101;
    private Bitmap textBitmap = null;
    private boolean popstatus = false;
    private int page = 1;
    private ArrayList<PhotoBean> photoPopPaths, stardPaths;
    private DrawerLayout drawer;
    private SmartRefreshLayout smartRefreshLayout;
    private ListView drawerLayoutList;
    private TaskPhotoAdapter mAdapter;
    private boolean drew = true;
    private int num = 0;
    private String titlename;

    //弹出框
    private CircleImageView fab;
    private LinearLayout meunStandard, meunPhoto;

    private FloatMeunAnims floatMeunAnims;
    private boolean liststatus = true;
    boolean anim = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        floatMeunAnims = new FloatMeunAnims();
        mContext = ReplyActivity.this;
        pathimg = new ArrayList<>();
        stardPaths = new ArrayList<>();
        photoPopPaths = new ArrayList<>();
        list = LoveDao.queryLove();

        //发现ID
        findID();
        try {
            Intent intent = getIntent();
            //数据库第几个
            position = intent.getExtras().getInt("position");
            //输入内容
            content = intent.getExtras().getString("content");
            //wbs路径
            wbsname = intent.getExtras().getString("wbsname");
            //检查点
            //当前节点ID
            wbsID = intent.getExtras().getString("id");
            titlename = intent.getExtras().getString("title");
            title.setText(titlename);
            //图片字符串
            String Paths = intent.getExtras().getString("Imgpath");
            List<String> path = new ArrayList<>();
            path = Dates.stringToList(Paths);
            pathimg.addAll(path);
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
        //展示wbsname
        if (wbsname != null && wbsname.length() != 0) {
            wbsText.setText(wbsname);
        }
        //根据wbsname的长度判断默认是否展示图册按钮
        if (TextUtils.isEmpty(wbsID)) {
            fab.setVisibility(View.GONE);
        }
        //展示专递过来的回复数据
        replyText.setText(content);
        Save.setVisibility(View.VISIBLE);
        Save.setImageResource(R.mipmap.reply_baocun);
        permisssion();
        initDate();//recycclerView
    }


    /**
     * 定位
     */

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
        drawerLayoutText = (TextView) findViewById(R.id.drawer_layout_text);
        meunStandard = (LinearLayout) findViewById(R.id.meun_standard);
        meunPhoto = (LinearLayout) findViewById(R.id.meun_photo);
        meunPhoto.setVisibility(View.GONE);
        meunStandard.setVisibility(View.GONE);
        meunPhoto.setOnClickListener(this);
        meunStandard.setOnClickListener(this);
        //侧拉界面listview
        drawerLayoutList = (ListView) findViewById(R.id.drawer_layout_list);
        //侧拉界面
        drawer = (DrawerLayout) findViewById(drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //侧滑栏关闭手势滑动
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawer.setScrimColor(Color.TRANSPARENT);
        //图册查看按钮
        fab = (CircleImageView) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        fab.setVisibility(View.VISIBLE);
        //下拉控件，禁止下拉，只允许上拉加载更多
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        smartRefreshLayout.setEnableRefresh(false);
        //进度条
        tvNetSpeed = (TextView) findViewById(R.id.tvNetSpeed);
        //图片
        photoadd = (RecyclerView) findViewById(R.id.recycler_view);
        repleyAddress = (TextView) findViewById(R.id.repley_address);
        replyText = (EditText) findViewById(R.id.reply_text);
        //上传数据
        comButton = (TextView) findViewById(R.id.com_button);
        comButton.setOnClickListener(this);
        //检查项
        replyCheckItem = (TextView) findViewById(R.id.reply_check_item);
        //保存
        Save = (ImageView) findViewById(R.id.com_img);
        //wbs
        wbsText = (TextView) findViewById(R.id.wbs_text);
        //标题
        title = (TextView) findViewById(R.id.com_title);
        findViewById(R.id.reply_wbs).setOnClickListener(this);
        findViewById(R.id.reply_check).setOnClickListener(this);
        //进度条
        findViewById(R.id.com_back).setOnClickListener(this);
        /**
         *  上拉加载
         */
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                drew = false;
                //传入false表示加载失败
                refreshlayout.finishLoadmore(1500);
                if (liststatus) {
                    HomeUtils.photoAdm(wbsID, page, photoPopPaths, drew, mAdapter, wbsText.getText().toString());
                } else {
                    HomeUtils.getStard(wbsID, page, stardPaths, drew, mAdapter, wbsText.getText().toString());
                }
            }
        });
        mAdapter = new TaskPhotoAdapter(photoPopPaths, mContext);
        drawerLayoutList.setAdapter(mAdapter);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //存放到本地
                if (!status) {
                    selfDialog = new WbsDialog(ReplyActivity.this);
                    selfDialog.setMessage("是否保存本地");
                    selfDialog.setYesOnclickListener("确定", new WbsDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            selfDialog.dismiss();
                            Shop shop = new Shop();
                            shop.setType(Shop.TYPE_LOVE);
                            //路径
                            shop.setName(wbsText.getText().toString());
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
                } else {
                    selfDialog = new WbsDialog(ReplyActivity.this);
                    selfDialog.setMessage("是否保存本地");
                    selfDialog.setYesOnclickListener("确定", new WbsDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            selfDialog.dismiss();
                            //更新数据库数据
                            //拿到需要进行修改的记录
                            Shop shop = list.get(position);
                            shop.setName(wbsText.getText().toString());
                            //内容
                            shop.setContent(replyText.getText().toString());
                            //图片保存路径
                            shop.setImage_url(Dates.listToString(pathimg));
                            //wbsid
                            shop.setWebsid(wbsID);
                            //时间
                            shop.setTimme(Dates.getDate());
                            //checkid
                            shop.setCheckid(checkId);
                            //checkname
                            shop.setCheckname(replyCheckItem.getText().toString());
                            //更新
                            LoveDao.updateLove(shop);
                            //返回之前的界面
                            Intent intent = new Intent();
                            //回传数据到主Activity
                            setResult(RESULT_OK, intent);
                            //此方法后才能返回主Activity
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
                }
            }
        });


    }

    /**
     * 调用相机
     */

    public void Cream() {
        //隐藏键盘
        try {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(ReplyActivity.this.getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {

        }
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
                            CropImageUtils.getInstance().takePhoto(ReplyActivity.this);
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
                        popWindow.dismiss();
                        break;
                }
                popWindow.dismiss();
            }
        };

        btnCamera.setOnClickListener(listener);
        btn_camera_pop.setOnClickListener(listener);
        btnAlbum.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);
        ColorDrawable dw = new ColorDrawable(0x30000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 初始化recyclerview
     */
    private void initDate() {
        if (pathimg.size() == 0 || pathimg.get(0) == "") {
            pathimg.clear();
        }
        photoAdapter = new PhotoAdapter(this, pathimg, "Reply");
        photoadd.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        photoadd.setAdapter(photoAdapter);
        comButton.setBackgroundResource(R.mipmap.reply_commit);
    }

    /**
     * 网络请求
     */
    private void Okgo(ArrayList<File> file, final String address) {
        final ProgressDialog dialog = new ProgressDialog(mContext);
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
                            ToastUtils.showShortToast(msg);
                            if (ret == 0) {
                                TaskCallbackUtils.CallBackMethod();
                                //删除上传的原图图片
                                for (int i = 0; i < pathimg.size(); i++) {
                                    Dates.deleteFile(pathimg.get(i));
                                }
                                //删除数据库数据
                                if (!list.isEmpty() && position != -1) {
                                    LoveDao.deleteLove(list.get(position).getId());
                                }
                                Intent newpush = new Intent();
                                setResult(2, newpush);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        dialog.dismiss();
                    }

                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //wbs结构
            case R.id.reply_wbs:
                Intent intent = new Intent(ReplyActivity.this, MmissPushActivity.class);
                //标签，用来判读该进入那个界面，
                intent.putExtra("Type", "reply");
                intent.putExtra("wbsID", wbsID);
                startActivityForResult(intent, 1);
                break;
            case R.id.com_back:
                for (int i = 0; i < pathimg.size(); i++) {
                    Dates.deleteFile(pathimg.get(i));
                }
                finish();
                break;
            case R.id.fab:
                //打开meun选项
                if (anim) {
                    floatMeunAnims.doclickt(meunPhoto, meunStandard, fab);
                    anim = false;
                } else {
                    floatMeunAnims.doclicktclose(meunPhoto, meunStandard, fab);
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
                photoPopPaths.clear();
                mAdapter.getData(photoPopPaths, "");
                drawerLayoutText.setText("图纸");
                Dates.getDialog(ReplyActivity.this, "请求数据中...");
                HomeUtils.photoAdm(wbsID, page, photoPopPaths, drew, mAdapter, wbsText.getText().toString());
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
                photoPopPaths.clear();
                mAdapter.getData(photoPopPaths, "");
                drawerLayoutText.setText("标准");
                Dates.getDialog(ReplyActivity.this, "请求数据中...");
                HomeUtils.getStard(wbsID, page, stardPaths, drew, mAdapter, wbsText.getText().toString());
                drawer.openDrawer(GravityCompat.START);
                break;
            case R.id.reply_check:
                Intent intent1 = new Intent(ReplyActivity.this, Checkpoint.class);
                intent1.putExtra("wbsID", wbsID);
                startActivityForResult(intent1, 1);
                break;
            case R.id.com_button:
                files = new ArrayList<>();
                //讲图片转成file格式
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
            wbsID = data.getStringExtra("position");
            Title = data.getStringExtra("title");
            wbsText.setText(Title);
            fab.setVisibility(View.VISIBLE);
            checkId = "";
            replyCheckItem.setText(data.getStringExtra(""));
        } else if (requestCode == 1 && resultCode == 2) {
            //节点
            checkId = data.getStringExtra("id");
            replyCheckItem.setText(data.getStringExtra("name"));
        } else if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for (int i = 0; i < images.size(); i++) {
                    double mdouble = Dates.getDirSize(new File(images.get(i).path));
                    if (mdouble != 0.0) {
                        Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                        options.quality = 95;
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
            //返回图片
            CropImageUtils.getInstance().onActivityResult(this, requestCode, resultCode, data, new CropImageUtils.OnResultListener() {
                @Override
                public void takePhotoFinish(final String path) {
                    //   根据路径压缩图片并返回bitmap(2
                    //获取图片选择角度，旋转图片
                    Bitmap bitmap = CropImageUtils.rotaingImageView(CropImageUtils.readPictureDegree(path), compressPixel(path));
                    //给压缩的图片添加时间水印(1)
                    textBitmap = ImageUtil.drawTextToRightBottom(mContext,
                            bitmap, Dates.getDate() + "\n" + Bai_address, 15, Color.WHITE, 0, 0);
                    //保存添加水印的时间的图片
                    Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                    options.quality = 95;
                    Tiny.getInstance().source(textBitmap).asFile().withOptions(options).compress(new FileCallback() {
                        @Override
                        public void callback(boolean isSuccess, String outfile) {
                            pathimg.add(outfile);
                            //填入listview，刷新界面
                            photoAdapter.getData(pathimg);
//                    //删除原图
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
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     */
    private String Bai_address = "";
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                // 纬度
                latitude = location.getLatitude() + "";
                // 经度
                longitude = location.getLongitude() + "";
                Bai_address = location.getAddrStr();
                if (Bai_address != null && !Bai_address.equals("")) {
                } else {
                    Bai_address = "";
                }
                repleyAddress.setText(location.getAddrStr());
                // 定位停止SDK
                locationService.stop();
            }
        }
    };


    /**
     * 重写返回键，判断是否在上传数据，如果在上
     * 传就屏蔽
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!popstatus) {
                for (int i = 0; i < pathimg.size(); i++) {
                    Dates.deleteFile(pathimg.get(i));
                }
                finish();
            }
        }
        return false;
    }

    public void getauthority() {
        //获取相机权限，定位权限，内存权限
        requestRunPermisssion(new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION}, new PermissionListener() {
            @Override
            public void onGranted() {
                //表示所有权限都授权了
                CropImageUtils.getInstance().takePhoto(ReplyActivity.this);
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

    /**
     * 说明：权限申请
     * 创建时间： 2020/3/6 0006 9:56
     * author  winelx
     */
    public void permisssion() {
        requestRunPermisssion(new String[]{Enum.LOCATION}, new PermissionListener() {
            @SuppressLint("HandlerLeak")
            @Override
            public void onGranted() {
                loaction();//定位
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                for (String permission : deniedPermission) {
                    if (permission.equals(Enum.LOCATION)) {
                        BaseDialogUtils.openAppDetails(mContext, "请求位置需要定位权限,请到权限管理中心打开");
                    }
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        locationService.stop();
        super.onDestroy();
    }
}