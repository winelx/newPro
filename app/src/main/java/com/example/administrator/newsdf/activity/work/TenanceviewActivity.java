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
import com.example.administrator.newsdf.activity.home.homeUtils;
import com.example.administrator.newsdf.activity.home.same.ReplysActivity;
import com.example.administrator.newsdf.activity.work.Adapter.TabAdapter;
import com.example.administrator.newsdf.bean.PhotoBean;
import com.joanzapata.iconify.widget.IconTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * description: //任务维护——列表查看
 *
 * @author lx
 *         date: 2018/2/8 0008 下午 5:01
 *         update: 2018/2/8 0008
 *         version:
 */
public class TenanceviewActivity extends AppCompatActivity {
    private String TAG = "TenanceviewActivity";
    private Context mContext;
    private ViewPager mViewPager;
    private IconTextView com_back;
    private TabLayout mTabLayout;
    private TabAdapter mAdapter;
    private RelativeLayout tabulation;
    private TextView title;
    private LinearLayout com_img;
    private ArrayList<String> ids = null,
            names = null,
            titlename = null;
    private int msg = 0, page = 1;
    private String id, wbspath, wbsname, type;
    private CircleImageView fab;
    private SmartRefreshLayout drawerlayoutSmart;
    private DrawerLayout drawerLayout;
    private ArrayList<PhotoBean> imagePaths = null;
    private TaskPhotoAdapter taskAdapter;
    private ListView drawerLayoutList;
    private boolean drew = true;
    private boolean isParent, iswbs;
    ArrayList<String> replly = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_missionmte);
        mContext = TenanceviewActivity.this;
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

        drawerLayoutList = (ListView) findViewById(R.id.drawer_layout_list);
        drawerlayoutSmart = (SmartRefreshLayout) findViewById(R.id.drawerLayout_smart);
        drawerlayoutSmart.setEnableRefresh(false);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //侧滑栏关闭手势滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        fab = (CircleImageView) findViewById(R.id.fab);
        com_img = (LinearLayout) findViewById(R.id.tenac_img);
        tabulation = (RelativeLayout) findViewById(R.id.tabulation);
        title = (TextView) findViewById(R.id.com_title);
        com_back = (IconTextView) findViewById(R.id.com_back);
        title.setText("任务管理");
        taskAdapter = new TaskPhotoAdapter(imagePaths, TenanceviewActivity.this);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayoutList.setAdapter(taskAdapter);
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                drew = true;
                homeUtils.photoAdm(id, page, imagePaths, drew, taskAdapter, wbspath);
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        /**
         *    侧拉listview上拉加载
         */
        drawerlayoutSmart.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                drew = false;
                homeUtils.photoAdm(id, page, imagePaths, drew, taskAdapter, wbspath);
                taskAdapter.getData(imagePaths, wbspath);
                //传入false表示加载失败
                refreshlayout.finishLoadmore(1500);
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
        mViewPager.setOffscreenPageLimit(10);
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
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //可以在这里拿出数据和状态
        ids = savedInstanceState.getStringArrayList("ids");
        names = savedInstanceState.getStringArrayList("name");
        titlename = savedInstanceState.getStringArrayList("titlename");
        wbspath = savedInstanceState.getString("wbspath");
        id = savedInstanceState.getString("id");
        initView();
    }


}
