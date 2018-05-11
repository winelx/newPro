package com.example.administrator.newsdf.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
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
import com.example.administrator.newsdf.bean.Tab;
import com.example.administrator.newsdf.bean.work_fr_bright_bean;
import com.example.administrator.newsdf.callback.JPushCallUtils;
import com.example.administrator.newsdf.fragment.IndexFrament;
import com.example.administrator.newsdf.fragment.MineFragment;
import com.example.administrator.newsdf.fragment.WorkFragment;
import com.example.administrator.newsdf.utils.AppUtils;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.Requests;
import com.example.administrator.newsdf.utils.SPUtils;
import com.example.administrator.newsdf.utils.Utils;
import com.example.administrator.newsdf.utils.WbsDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import okhttp3.Call;
import okhttp3.Response;


/**
 * description: 承载fragemnt的界面
 *
 * @author lx
 *         date: 2018/3/15 0015 上午 9:14
 *         update: 2018/3/15 0015
 *         version:
 */
public class MainActivity extends AppCompatActivity {
    private FragmentTabHost mTabHost;
    private static MainActivity mContext;
    private LayoutInflater mInflater;
    private ArrayList<Tab> mTabs = new ArrayList<>();
    private Dates dates;
    private String version;
    private WbsDialog selfDialog;
    private TextView home_img_red;
    List<Shop> list;
    //集团亮点
    private ArrayList<work_fr_bright_bean> bridhtgroupList = new ArrayList<>();
    //分公司亮点
    private ArrayList<work_fr_bright_bean> brightcompangList = new ArrayList<>();
    //项目亮点
    private ArrayList<work_fr_bright_bean> brightprojectList = new ArrayList<>();

    public static MainActivity getInstance() {
        return mContext;
    }

    private long exitTime = 0;
    int width = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    home_img_red.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    home_img_red.setVisibility(View.GONE);
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
        mContext = this;
        dates = new Dates();
        //找到控件
        home_img_red = (TextView) findViewById(R.id.home_img_red);
        home_img_red.setVisibility(View.GONE);
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
        //权限请求
        HiPermission.create(mContext)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {
                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onDeny(String permission, int position) {

                    }

                    @Override
                    public void onGuarantee(String permission, int position) {

                    }
                });
        //新本版检测
        Uplogding();
        //数据处理
        initTab();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setExitTransition(slide);
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
                                    //更新地址
                                    String filePath = json.getString("downloadUrl");
                                    int lenght = version.compareTo(versions);
                                    if (lenght < 0) {
                                        //提示框
                                        show(filePath);
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
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void initTab() {
        //添加tab信息，存入集合进行展示
        Tab tab_home = new Tab(IndexFrament.class, R.string.home, R.drawable.tab_home_style, 0);
        Tab tab_work = new Tab(WorkFragment.class, R.string.work, R.drawable.tab_work_style, 0);
        Tab tab_hot = new Tab(MineFragment.class, R.string.mine, R.drawable.tab_mine_style, 0);
        mTabs.add(tab_home);
        mTabs.add(tab_work);
        mTabs.add(tab_hot);

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
                finish();
            }
            return true;
        }
        return true;
    }

    /**
     * 检测到有新版本后给的提示框
     */
    public void show(final String path) {
        selfDialog = new WbsDialog(mContext);
        selfDialog.setTitle("更新提示");
        selfDialog.setMessage("检测到有新版本，立即更新更新");
        selfDialog.setYesOnclickListener("确定", new WbsDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                selfDialog.dismiss();
            }
        });
        selfDialog.setNoOnclickListener("更新", new WbsDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(path);
                intent.setData(content_url);
                startActivity(intent);
                selfDialog.dismiss();
            }
        });
        selfDialog.show();
    }

    //在接受推送消息的广播出调用该方法（service/PushReceiver）
    public void getRedPoint() {
        home_img_red.setVisibility(View.VISIBLE);
        //向indexfragemnt 发送消息，显示推送小红点
        JPushCallUtils.removeCallBackMethod();
    }


}
