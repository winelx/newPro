package com.example.administrator.newsdf.activity.work;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.Adapter.TaskPhotoAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.HomeUtils;
import com.example.administrator.newsdf.activity.home.same.ReplysActivity;
import com.example.administrator.newsdf.activity.work.Adapter.TabAdapter;
import com.example.administrator.newsdf.bean.PhotoBean;
import com.example.administrator.newsdf.callback.MoreTaskCallback;
import com.example.administrator.newsdf.callback.MoreTaskCallbackUtils;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.FloatMeunAnims;
import com.example.administrator.newsdf.utils.Requests;
import com.joanzapata.iconify.widget.IconTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;


/**
 * description: //任务维护——列表查看
 *
 * @author lx
 *         date: 2018/2/8 0008 下午 5:01
 *         update: 2018/2/8 0008
 *         version:
 */
public class TenanceviewActivity extends AppCompatActivity implements View.OnClickListener, MoreTaskCallback {
    private String TAG = "TenanceviewActivity";
    private Context mContext;
    private ViewPager mViewPager;
    private IconTextView com_back;
    private TabLayout mTabLayout;
    private TabAdapter mAdapter;
    private RelativeLayout tabulation;
    private TextView title,drawer_layout_text;
    private LinearLayout com_img;
    private static int taskpage = 1;
    private int msg = 0, page = 1;
    private String id, wbspath, wbsname, type;
    private SmartRefreshLayout drawerlayoutSmart;
    private DrawerLayout drawerLayout;
    private TaskPhotoAdapter taskAdapter;
    private ListView drawerLayoutList;
    private boolean drew = true;
    private boolean isParent, iswbs;
    ArrayList<String> replly;
    private ArrayList<PhotoBean> imagePaths;
    private ArrayList<String> ids,
            names,
            titlename;
    //弹出框
    private CircleImageView fab;
    private LinearLayout meun_standard, meun_photo;
    private FloatMeunAnims floatMeunAnims;
    private boolean liststatus = true;
    boolean anim = true;
    private ArrayList<String> namess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_missionmte);
        mContext = TenanceviewActivity.this;
        floatMeunAnims = new FloatMeunAnims();
        MoreTaskCallbackUtils.setCallBack(this);
        //获取到intent传过来得集合
        names = new ArrayList<>();
        ids = new ArrayList<>();
        titlename = new ArrayList<>();
        imagePaths = new ArrayList<>();
        try {
            //ws得到跳转到该Activity的Intent对象
            Intent intent = getIntent();
            //加上检查数量的检查点
            names = intent.getExtras().getStringArrayList("name");
            ids = intent.getExtras().getStringArrayList("ids");
            titlename = intent.getExtras().getStringArrayList("title");
            id = intent.getExtras().getString("id");
            wbsname = intent.getExtras().getString("wbsname");
            //节点路径
            wbspath = intent.getExtras().getString("wbspath");
            isParent = intent.getExtras().getBoolean("isParent");
            iswbs = intent.getExtras().getBoolean("iswbs");
            type = intent.getExtras().getString("type");

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        drawer_layout_text= (TextView) findViewById(R.id.drawer_layout_text);
        drawerLayoutList = (ListView) findViewById(R.id.drawer_layout_list);
        drawerlayoutSmart = (SmartRefreshLayout) findViewById(R.id.drawerLayout_smart);
        drawerlayoutSmart.setEnableRefresh(false);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //侧滑栏关闭手势滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        fab = (CircleImageView) findViewById(R.id.fab);
        meun_standard = (LinearLayout) findViewById(R.id.meun_standard);
        meun_photo = (LinearLayout) findViewById(R.id.meun_photo);
        fab = (CircleImageView) findViewById(R.id.fab);
        meun_photo.setOnClickListener(this);
        meun_standard.setOnClickListener(this);
        fab.setOnClickListener(this);
        com_img = (LinearLayout) findViewById(R.id.tenac_img);
        tabulation = (RelativeLayout) findViewById(R.id.tabulation);
        title = (TextView) findViewById(R.id.com_title);
        com_back = (IconTextView) findViewById(R.id.com_back);
        title.setText("任务管理");
        taskAdapter = new TaskPhotoAdapter(imagePaths, TenanceviewActivity.this);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayoutList.setAdapter(taskAdapter);
        /**
         * 检查项
         */
        tabulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TenanceviewActivity.this, TabulationActivity.class);
                intent.putExtra("data", titlename);
                startActivityForResult(intent, 1);
            }
        });
        if (type == null) {
            com_img.setVisibility(View.GONE);
        }
        com_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TenanceviewActivity.this, ReplysActivity.class);
                intent.putExtra("position", -1);
                //节点名称
                intent.putExtra("title", "我很主动");
                //节点名称
                intent.putExtra("wbspath", wbspath);
                //节点ID
                intent.putExtra("id", id);
                intent.putExtra("isParent", isParent);
                intent.putExtra("wbstitle", wbsname);
                intent.putExtra("iswbs", iswbs);
                intent.putExtra("type", type);
                startActivityForResult(intent, 1);
            }
        });
        com_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /**
         *    侧拉listview上拉加载
         */
        drawerlayoutSmart.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                drew = false;
                page++;
                if (liststatus) {
                    HomeUtils.photoAdm(id, page, imagePaths, drew, taskAdapter, wbsname);
                    //传入false表示加载失败
                } else {
                    HomeUtils.getStard(id, page, imagePaths, drew, taskAdapter, wbsname);
                    //传入false表示加载失败
                }
                refreshlayout.finishLoadmore(1000);
            }
        });
        initView();
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tl_tab);
        if (names.size() > 3) {
            mTabLayout.setTabMode(0);
        }
        mViewPager = (ViewPager) findViewById(R.id.vp_pager);
        mViewPager.setOffscreenPageLimit(names.size());
        mAdapter = new TabAdapter(getSupportFragmentManager(), names);
        mAdapter.getAdate(ids, id);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
//  那我们如果真的需要监听tab的点击或者ViewPager的切换,则需要手动配置ViewPager的切换,例如:
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //切换ViewPager
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //新增任务返回处理
        if (requestCode == 1 && resultCode == RESULT_OK) {
            msg = data.getIntExtra("position", 1);
            replly = new ArrayList<>();
            replly = data.getStringArrayListExtra("name");
            mViewPager.setCurrentItem(msg);
            mAdapter.getData(replly);
            //点击tab返回的处理
        } else if (requestCode == 1 && resultCode == 2) {
            msg = data.getIntExtra("position", 1);
            mViewPager.setCurrentItem(msg);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (taskpage == 1) {
            taskpage++;
        } else {
            Updata();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //可以在这里拿出数据和状态
        ids = savedInstanceState.getStringArrayList("ids");
        names = savedInstanceState.getStringArrayList("name");
        titlename = savedInstanceState.getStringArrayList("titlename");
        wbspath = savedInstanceState.getString("wbspath");
        id = savedInstanceState.getString("id");
        initView();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.meun_photo:
                //请求图纸
                //加载第一页
                page = 1;
                //请求数据时清除之前的
                drew = true;
                //网络请求
                imagePaths.clear();
                taskAdapter.getData(imagePaths,"");
                drawer_layout_text.setText("图纸");
                Dates.getDialog(TenanceviewActivity.this, "请求数据中...");
                HomeUtils.photoAdm(id, page, imagePaths, drew, taskAdapter, wbspath);
                //上拉加载的状态判断
                liststatus = true;
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.meun_standard:
                //标准
                //加载第一页
                page = 1;
                //请求数据时清除之前的
                drew = true;
                //上拉加载的状态判断
                liststatus = false;
                imagePaths.clear();
                drawer_layout_text.setText("标准");
                taskAdapter.getData(imagePaths,"");
                Dates.getDialog(TenanceviewActivity.this, "请求数据中...");
                HomeUtils.getStard(id, page, imagePaths, drew, taskAdapter, wbspath);
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.fab:
                //打开meun选项
                if (anim) {
                    floatMeunAnims.doclickt(meun_photo, meun_standard, fab);
                    anim = false;
                } else {
                    floatMeunAnims.doclicktclose(meun_photo, meun_standard, fab);
                    anim = true;
                }
                break;
            default:
                break;
        }
    }
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
                                mAdapter = new TabAdapter(getSupportFragmentManager(), namess);
                                mAdapter.getAdate(ids, id);
                                mTabLayout.setupWithViewPager(mViewPager);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                    }
                });
    }

    @Override
    public void newData() {
        Updata();
    }
}
