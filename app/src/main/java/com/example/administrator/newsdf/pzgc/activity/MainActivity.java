package com.example.administrator.newsdf.pzgc.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.newsdf.GreenDao.LoveDao;
import com.example.administrator.newsdf.GreenDao.Shop;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.work.BrightspotFragment;
import com.example.administrator.newsdf.pzgc.bean.Tab;
import com.example.administrator.newsdf.pzgc.callback.JPushCallUtils;
import com.example.administrator.newsdf.pzgc.fragment.HomeFragment;
import com.example.administrator.newsdf.pzgc.fragment.IndexFrament;
import com.example.administrator.newsdf.pzgc.fragment.MineFragment;
import com.example.administrator.newsdf.pzgc.fragment.WorkFragment;
import com.example.administrator.newsdf.pzgc.utils.AppUtils;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.PermissionListener;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import okhttp3.Call;
import okhttp3.Response;


/**
 * description: 承载fragemnt的界面
 *
 * @author lx
 * date: 2018/3/15 0015 上午 9:14
 * update: 2018/3/15 0015
 * version:
 */
public class MainActivity extends BaseActivity {
    private FragmentTabHost mTabHost;
    private static MainActivity mContext;
    private LayoutInflater mInflater;
    private ArrayList<Tab> mTabs = new ArrayList<>();
    private String version;
    private TextView homeImgRed;
    private List<Shop> list;
    private boolean workbtight = false;

    public static MainActivity getInstance() {
        return mContext;
    }

    private long exitTime = 0;
    int width = 0;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    homeImgRed.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    homeImgRed.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mian);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getauthority();
        mContext = this;
        workbtight = false;
        //找到控件
        homeImgRed = (TextView) findViewById(R.id.home_img_red);
        homeImgRed.setVisibility(View.GONE);
        final String staffId = SPUtils.getString(MainActivity.this, "id", "");
        //设置极光推送别名Alia
        JPushInterface.setAlias(this, staffId, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {

            }
        });
        //屏幕宽度
        width = Utils.getScreenWidth(mContext) / 3;
        //获取当前版本
        version = AppUtils.getVersionName(mContext);
        //新本版检测
        Uplogding();
        //删除tiny压缩的图片
        if (LoveDao.queryLove().size() == 0) {
            //如果这个文件目录下没有图片保存在数据库，就将图片全部删除，（别的地方的图片是不需要保存的）
            String path = "/storage/emulated/0/Android/data/com.example.administrator.newsdf/tiny";
            delFolder(path);
        }
        //数据处理
        initTab();
    }

    @Override
    protected void onStart() {
        super.onStart();

        list = new ArrayList<>();
        list = LoveDao.JPushCart();
        if (list.size() > 0) {
            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage(msg);
        } else {
            Message msg = new Message();
            msg.what = 2;
            handler.sendMessage(msg);
        }
    }

    //新本版检测
    private void Uplogding() {
        OkGo.<String>post(Requests.UpLoading)
                .params("type", 1)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.contains("data")) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                int ret = jsonObject.getInt("ret");
                                if (ret == 0) {
                                    JSONObject json = jsonObject.getJSONObject("data");
                                    //版本号
                                    String versions = json.getString("version");
                                    String description = json.getString("description");
                                    //更新地址
                                    String filePath = json.getString("downloadUrl");
                                    int lenght = version.compareTo(versions);
                                    if (lenght < 0) {
                                        //提示框
                                        show(filePath, description);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void initTab() {
        //添加tab信息，存入集合进行展示
        Tab tabMessage = new Tab(HomeFragment.class, R.string.home, R.drawable.tab_home_style, 0);
        Tab tabIndex = new Tab(IndexFrament.class, R.string.message, R.drawable.tab_index_style, 0);
        Tab tabWork = new Tab(WorkFragment.class, R.string.work, R.drawable.tab_work_style, 0);
        Tab tabCheck = new Tab(BrightspotFragment.class, R.string.check, R.drawable.tab_check_style, 0);
        Tab tabHot = new Tab(MineFragment.class, R.string.mine, R.drawable.tab_mine_style, 0);
        mTabs.add(tabMessage);
        mTabs.add(tabIndex);
        mTabs.add(tabWork);
        mTabs.add(tabCheck);
        mTabs.add(tabHot);
        mTabHost = (FragmentTabHost) findViewById(R.id.mFragmentTabHost);
        for (Tab tab : mTabs) {
            //获取都文字
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
            mTabHost.addTab(tabSpec, tab.getFragemnt(), null);
        }
        //设置分割线
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_BEGINNING);
        //设置默认打开的界面
        mTabHost.setCurrentTab(0);

    }

    private View buildIndicator(Tab tab) {
        mInflater = LayoutInflater.from(this);
        //必须实现setup不然没法展示， getSupportFragmentManager用来装布局的
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        //mInflater记得初始化，不然会报空指针，没注意，被坑
        View view = mInflater.inflate(R.layout.tab_index, null);
        //获取控件ID
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView textview = (TextView) view.findViewById(R.id.text);
        //d动态添加图片文字，类似listview 的adapter的getItem，
        imageView.setBackgroundResource(tab.getIcon());
        textview.setText(tab.getTitle());
        return view;
    }

    //连续两次退出App
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                getSupportFragmentManager().popBackStack();
                Intent MyIntent = new Intent(Intent.ACTION_MAIN);
                MyIntent.addCategory(Intent.CATEGORY_HOME);
                startActivity(MyIntent);
                android.os.Process.killProcess(android.os.Process.myPid());
                finish();
            }
            return true;
        }
        return true;
    }


    /**
     * 检测到有新版本后给的提示框
     */
    public void show(final String path, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("新版本提示")
                .setMessage(message)
                .setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri contentUrl = Uri.parse(path);
                intent.setData(contentUrl);
                startActivity(intent);
                finish();

            }
        });
        builder.show();
    }

    /**
     *
     */
    public void getRedPoint() {
        homeImgRed.setVisibility(View.VISIBLE);
        //向indexfragemnt 发送消息，显示推送小红点
        JPushCallUtils.removeCallBackMethod();
    }


    public boolean wbright() {
        return workbtight;
    }


    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除指定文件夹下所有文件
//param path 文件夹完整绝对路径
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    public void getauthority() {
        //获取相机权限，定位权限，内存权限
        requestRunPermisssion(new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION}, new PermissionListener() {
            @Override
            public void onGranted() {
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

