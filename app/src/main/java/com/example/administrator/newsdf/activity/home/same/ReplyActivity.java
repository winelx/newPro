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
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.administrator.newsdf.GreenDao.LoveDao;
import com.example.administrator.newsdf.GreenDao.Shop;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.work.MmissPushActivity;
import com.example.administrator.newsdf.adapter.PhotoAdapter;
import com.example.administrator.newsdf.adapter.TaskPhotoAdapter;
import com.example.administrator.newsdf.baseApplication;
import com.example.administrator.newsdf.bean.PhotoBean;
import com.example.administrator.newsdf.camera.CheckPermission;
import com.example.administrator.newsdf.camera.CropImageUtils;
import com.example.administrator.newsdf.camera.ImageUtil;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.service.LocationService;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.Request;
import com.example.administrator.newsdf.utils.SPUtils;
import com.example.administrator.newsdf.utils.WbsDialog;
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


/**
 * description: 主动上传任务界面
 *
 * @author lx
 *         date: 2018/2/6 0006 上午 11:14
 *         update: 2018/2/6 0006
 *         version:
 */
public class ReplyActivity extends AppCompatActivity implements View.OnClickListener {
    private PhotoAdapter photoAdapter;
    private List<String> imagePaths;
    private ArrayList<File> files;
    List<Shop> list;
    private RecyclerView photoadd;
    private LocationService locationService;
    private TextView repley_address, wbs_text, com_button, title, tvNetSpeed, reply_check_item;
    private ImageView address, baoxun;
    private String Latitude, Longitude;
    private EditText reply_text;
    private Context mContext;
    private ProgressBar mProgressBar;
    private LinearLayout Progessn;
    private SPUtils spUtils;
    private ArrayList<String> pathimg, ids;
    private CheckPermission checkPermission;
    LinearLayout lin_sdfg;
    String content = "", wbsname = "", wbsID = "", id = "";
    boolean status = false;
    int position;
    private ArrayList<String> check;
    String Title = "", checkId = "", checkname = "";
    private WbsDialog selfDialog;
    private static final int IMAGE_PICKER = 101;
    private Bitmap textBitmap = null;
    private boolean popstatus = false;
    private int page = 1;
    private PopupWindow popupWindow;
    private CircleImageView fab;
    private ArrayList<PhotoBean> photoPopPaths;
    private DrawerLayout drawer;
    private SmartRefreshLayout smartRefreshLayout;
    private ListView drawer_layout_list;
    private TaskPhotoAdapter mAdapter;
    private boolean drew = true;
    private int num = 0;
    private String titlename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        mContext = ReplyActivity.this;
        imagePaths = new ArrayList<>();
        check = new ArrayList<>();
        pathimg = new ArrayList<>();
        ids = new ArrayList<>();
        list = new ArrayList<>();
        photoPopPaths = new ArrayList<>();
        list = LoveDao.queryLove();
        checkPermission = new CheckPermission(this) {
            @Override
            public void permissionSuccess() {
                CropImageUtils.getInstance().takePhoto(ReplyActivity.this);
            }

            @Override
            public void negativeButton() {
                //如果不重写，默认是finishddsfaasf
                //super.negativeButton();
                ToastUtils.showLongToast("权限申请失败！");
            }
        };

        //发现ID
        //有可能没有传递数据过来。所以如果没有传递，那就消费掉
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
            check = intent.getStringArrayListExtra("list");
            //当前节点ID
            wbsID = intent.getExtras().getString("id");
            titlename = intent.getExtras().getString("title");
            title.setText(titlename);
            //检查点id集合
            ids = intent.getExtras().getStringArrayList("ids");
            //图片字符串
            String Paths = intent.getExtras().getString("Imgpath");
            checkId = intent.getExtras().getString("Checkid");
            checkname = intent.getExtras().getString("Checkname");
            //转成集合
            imagePaths = Dates.stringToList(Paths);
            for (int i = 0; i < imagePaths.size(); i++) {
                Log.i("sss", imagePaths.get(i));
            }
            pathimg.addAll(imagePaths);
            if (checkname.length() < 0) {
                reply_check_item.setText("选择检查点");
            } else {
                reply_check_item.setText(checkname);
            }
            status = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            title.setText("我很主动");
        }


        if (wbsname != null && wbsname.length() != 0) {
            wbs_text.setText(wbsname);
        }
        if (TextUtils.isEmpty(wbsID)) {
            fab.setVisibility(View.GONE);
        }
        reply_text.setText(content);

        baoxun.setVisibility(View.VISIBLE);
        baoxun.setImageResource(R.mipmap.reply_baocun);
        //sp帮助类
        spUtils = new SPUtils();
        loaction();//定位
        initDate();//recycclerView
    }

    /**
     * 定位
     */

    private void loaction() {
        //定位初始化
        locationService = ((baseApplication) getApplication()).locationService;
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
        drawer_layout_list = (ListView) findViewById(R.id.drawer_layout_list);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //侧滑栏关闭手势滑动
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawer.setScrimColor(Color.TRANSPARENT);
        fab = (CircleImageView) findViewById(R.id.fabloating);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                drew = true;
                photoAdm(wbsID);
                drawer.openDrawer(GravityCompat.START);
            }
        });
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        smartRefreshLayout.setEnableRefresh(false);
        lin_sdfg = (LinearLayout) findViewById(R.id.lin_sdfg);
        tvNetSpeed = (TextView) findViewById(R.id.tvNetSpeed);
        //图片
        photoadd = (RecyclerView) findViewById(R.id.recycler_view);
        repley_address = (TextView) findViewById(R.id.repley_address);
        reply_text = (EditText) findViewById(R.id.reply_text);
        address = (ImageView) findViewById(R.id.address);
        com_button = (TextView) findViewById(R.id.com_button);
        reply_check_item = (TextView) findViewById(R.id.reply_check_item);
        baoxun = (ImageView) findViewById(R.id.com_img);
        wbs_text = (TextView) findViewById(R.id.wbs_text);
        title = (TextView) findViewById(R.id.com_title);
        findViewById(R.id.reply_wbs).setOnClickListener(this);
        findViewById(R.id.reply_check).setOnClickListener(this);
        Progessn = (LinearLayout) findViewById(R.id.Progess);
        mProgressBar = (ProgressBar) findViewById(R.id.reply_bar);
        findViewById(R.id.com_back).setOnClickListener(this);
        /**
         *  上拉加载
         */
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                drew = false;
                photoAdm(wbsID);
                //传入false表示加载失败
                refreshlayout.finishLoadmore(1500);
            }
        });
        mAdapter = new TaskPhotoAdapter(photoPopPaths, mContext);
        drawer_layout_list.setAdapter(mAdapter);
        baoxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //存放到本地
                if (status != true) {
                    selfDialog = new WbsDialog(ReplyActivity.this);
                    selfDialog.setMessage("是否保存本地");
                    selfDialog.setYesOnclickListener("确定", new WbsDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            selfDialog.dismiss();
                            Shop shop = new Shop();
                            shop.setType(Shop.TYPE_LOVE);
                            //路径
                            shop.setName(wbs_text.getText().toString());
                            //wbsID
                            shop.setWebsid(wbsID);
                            //时间
                            shop.setTimme(Dates.getDate());
                            //内容
                            shop.setContent(reply_text.getText().toString());
                            shop.setImage_url(Dates.listToString(pathimg));
                            shop.setCheckid(checkId);
                            //检查点
                            if (reply_check_item.getText().toString().length() != 0) {
                                shop.setCheckname(reply_check_item.getText().toString());
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
                            shop.setName(wbs_text.getText().toString());
                            //内容
                            shop.setContent(reply_text.getText().toString());
                            //图片保存路径
                            shop.setImage_url(Dates.listToString(pathimg));
                            //wbsid
                            shop.setWebsid(wbsID);
                            //时间
                            shop.setTimme(Dates.getDate());
                            //checkid
                            shop.setCheckid(checkId);
                            //checkname
                            shop.setCheckname(reply_check_item.getText().toString());
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
        com_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                files = new ArrayList<>();
                //讲图片转成file格式
                for (int i = 0; i < pathimg.size(); i++) {
                    files.add(new File(pathimg.get(i)));
                }
                if (wbsID == null || wbsID.equals("")) {
                    Toast.makeText(mContext, "没有选择wbs节点", Toast.LENGTH_SHORT).show();
                } else if ("".equals(reply_text.getText().toString())) {
                    Toast.makeText(mContext, "请输入具体内容描述", Toast.LENGTH_SHORT).show();
                } else {
                    Okgo(files, Bai_address);
                }
            }
        });

        reply_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    num++;
                    //在这里加判断的原因是点击一次软键盘的删除键,会触发两次回调事件
                    if (num % 2 != 0) {
                        String s = reply_text.getText().toString();
                        if (!TextUtils.isEmpty(s)) {
                            reply_text.setText("" + s.substring(0, s.length() - 1));
                            //将光标移到最后
                            reply_text.setSelection(reply_text.getText().length());
                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 调用相机
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
        photoAdapter = new PhotoAdapter(this, pathimg);
        photoadd.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        photoadd.setAdapter(photoAdapter);
        com_button.setBackgroundResource(R.mipmap.reply_commit);
    }

    /**
     * 网络请求
     */
    private void Okgo(ArrayList<File> file, final String address) {
        userPop();
        OkGo.post(Request.Uploade)
                .params("wbsId", wbsID)
                .params("uploadContent", reply_text.getText().toString())
                .params("latitude", Latitude)
                .params("longitude", Longitude)
                .params("uploadAddr", address)
                .params("wbsqastaffId", checkId)
                //上传图片
                .addFileParams("imagesList", file)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        popstatus = false;
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String msg = jsonObject.getString("msg");
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {

                                ToastUtils.showShortToast(msg);
                                if (!list.isEmpty() && position != -1) {
                                    LoveDao.deleteLove(list.get(position).getId());
                                }
                                //返回列表
                                Intent intent = new Intent();
                                //回传数据到主Activity
                                setResult(RESULT_OK, intent);
                                //此方法后才能返回主Activity
                                popupWindow.dismiss();
                                finish();
                            } else {
                                ToastUtils.showShortToast(msg);
                                popupWindow.dismiss();
                            }

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

                    //进度条
                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        super.upProgress(currentSize, totalSize, progress, networkSpeed);
                        mProgressBar.setProgress((int) (100 * progress));
                        tvNetSpeed.setText("已上传" + currentSize / 1024 / 1024 + "MB, 共" + totalSize / 1024 / 1024 + "MB;");
                    }
                });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //wbs结构
            case R.id.reply_wbs:
                Intent intent = new Intent(ReplyActivity.this, MmissPushActivity.class);
                intent.putExtra("data", "reply");
                intent.putExtra("wbsID", wbsID);
                startActivityForResult(intent, 1);
                break;
            case R.id.com_back:
                finish();
                break;
            //选择检查项
            case R.id.reply_check:
                Intent intent1 = new Intent(ReplyActivity.this, Checkpoint.class);
                intent1.putExtra("wbsID", wbsID);
                startActivityForResult(intent1, 1);
                break;
            default:
                break;
        }

    }

    String Titles;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断是不是Activity的返回，不是就是相机的返回
        if (requestCode == 1 && resultCode == RESULT_OK) {
            wbsID = data.getStringExtra("position");
            Title = data.getStringExtra("title");
            wbs_text.setText(Title);
            fab.setVisibility(View.VISIBLE);
            drew = true;
            photoAdm(wbsID);
        } else if (requestCode == 1 && resultCode == 2) {
            //节点
            checkId = data.getStringExtra("id");
            Titles = data.getStringExtra("name");
            reply_check_item.setText(data.getStringExtra("name"));

        } else if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for (int i = 0; i < images.size(); i++) {
                    Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                    Tiny.getInstance().source(images.get(i).path).asFile().withOptions(options).compress(new FileCallback() {
                        @Override
                        public void callback(boolean isSuccess, String outfile) {
                            //添加进集合
                            Log.i("ss",outfile);
                            pathimg.add(outfile);
                            //填入listview，刷新界面
                            photoAdapter.getData(pathimg);
//                    //删除原图

                        }
                    });
                }
                photoAdapter.getData(pathimg);
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        } else {
            //返回图片
            CropImageUtils.getInstance().onActivityResult(this, requestCode, resultCode, data, new CropImageUtils.OnResultListener() {
                @Override
                public void takePhotoFinish(final String path) {
                    //   根据路径压缩图片并返回bitmap(2
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
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private String Bai_address = "";
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
                if (Bai_address != null && !Bai_address.equals("")) {
                } else {
                    Bai_address = "";
                }
                repley_address.setText(location.getAddrStr());
                // 定位停止SDK
                locationService.stop();
            }
        }
    };

    //选择图片
    private void showPopwindow() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(ReplyActivity.this.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        View popView = View.inflate(this, R.layout.camera_pop_menu, null);

        Button btnCamera = (Button) popView.findViewById(R.id.btn_camera_pop_camera);
        Button btnAlbum = (Button) popView.findViewById(R.id.btn_camera_pop_album);
        Button btnCancel = (Button) popView.findViewById(R.id.btn_camera_pop_cancel);
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

    //弹窗
    public void userPop() {
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
        if (popstatus) {
        } else {
            finish();
        }
        return false;
    }

    /**
     * 查询图册
     */
    private void photoAdm(final String string) {
        OkGo.post(Request.Photolist)
                .params("WbsId", string)
                .params("page", page)
                .params("rows", 5)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.indexOf("data") != -1) {
                            if (drew) {
                                photoPopPaths.clear();
                            }
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    String id = (String) json.get("id");
                                    String filePath = (String) json.get("filePath");
                                    String drawingNumber = (String) json.get("drawingNumber");
                                    String drawingName = (String) json.get("drawingName");
                                    String drawingGroupName = (String) json.get("drawingGroupName");
                                    filePath = Request.networks + filePath;
                                    photoPopPaths.add(new PhotoBean(id, filePath, drawingNumber, drawingName, drawingGroupName));
                                }
                                mAdapter.getData(photoPopPaths, wbs_text.getText().toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (drew) {
                                photoPopPaths.clear();
                                photoPopPaths.add(new PhotoBean(id, "暂无数据", "暂无数据", "暂无数据", "暂无数据"));
                            }
                            mAdapter.getData(photoPopPaths, wbs_text.getText().toString());
                        }
                    }
                });
    }
}