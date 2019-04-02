package com.example.administrator.newsdf.pzgc.activity.audit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.audit.fragment.DailyrecordFragment;
import com.example.administrator.newsdf.pzgc.activity.audit.fragment.MonthrecordFragment;
import com.example.administrator.newsdf.pzgc.activity.audit.fragment.QuarterrecordFragment;
import com.example.administrator.newsdf.pzgc.activity.work.pchoose.PshooseFragAdapte;
import com.example.administrator.newsdf.pzgc.callback.CallBackUtils;
import com.example.administrator.newsdf.pzgc.callback.HideCallbackUtils;
import com.example.administrator.newsdf.pzgc.callback.TaskCallbackUtils;
import com.example.baselibrary.view.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;
import com.example.administrator.newsdf.treeviews.ReportTreeListViewAdapters;
import com.example.administrator.newsdf.treeviews.bean.OrgBeans;
import com.example.administrator.newsdf.treeviews.bean.OrgenBeans;
import com.example.administrator.newsdf.treeviews.utils.Nodes;
import com.joanzapata.iconify.widget.IconTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * description:审核统计报表
 *
 * @author lx
 *         date: 2018/7/3 0003 上午 9:37
 *         update: 2018/7/3 0003
 *         version:
 */

public class ReportActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager reportViewpager;
    private static ReportActivity mContext;
    private TextView reportDaily, reportMonth, reportQuarter;
    TextView title_ll;

    public static ReportActivity getInstance() {
        return mContext;
    }

    private boolean status = true;
    String orgId;
    private List<OrgBeans> mDatas2;
    private List<OrgenBeans> mData;
    private ReportTreeListViewAdapters<OrgBeans> mAdapter;
    private ListView mTree;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        addActivity(this);
        Intent intent = getIntent();
        orgId = intent.getExtras().getString("orgId");
        //初始化控件
        mContext = this;
        mData = new ArrayList<>();
        mDatas2 = new ArrayList<>();
        findView();
        //初始化数据
        initData();
        initDatas();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }

    private void findView() {
        title_ll = (TextView) findViewById(R.id.title_ll);
        title_ll.setText(SPUtils.getString(mContext, "username", null));
        mTree = (ListView) findViewById(R.id.reporttree);
        IconTextView reprotBack = (IconTextView) findViewById(R.id.reprot_back);
        reportViewpager = (ViewPager) findViewById(R.id.report_viewpager);
        drawerLayout = (DrawerLayout) findViewById(R.id.Reportdrawer);
        reportDaily = (TextView) findViewById(R.id.report_daily);
        reportMonth = (TextView) findViewById(R.id.report_month);
        reportQuarter = (TextView) findViewById(R.id.report_quarter);
        reportDaily.setOnClickListener(this);
        reportMonth.setOnClickListener(this);
        reportQuarter.setOnClickListener(this);
        reprotBack.setOnClickListener(this);
        findViewById(R.id.switchorg).setOnClickListener(this);
        //关闭边缘滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //侧滑栏关闭手势滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //展示侧拉界面后，背景透明度（当前透明度为完全透明）
        drawerLayout.setScrimColor(Color.TRANSPARENT);
    }

    private void initData() {
        //构造适配器
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new DailyrecordFragment());
        fragments.add(new MonthrecordFragment());
        fragments.add(new QuarterrecordFragment());
        PshooseFragAdapte adapter = new PshooseFragAdapte(getSupportFragmentManager(), fragments);
        reportViewpager.setAdapter(adapter);
        //缓存界面
        reportViewpager.setOffscreenPageLimit(3);
        reportViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int currentPosition = 0;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position > currentPosition) {
                    //右滑
                    currentPosition = position;
                } else if (position < currentPosition) {
                    //左滑
                    currentPosition = position;
                }
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        daily();
                        break;
                    case 1:
                        month();
                        break;
                    case 2:
                        quarter();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 点击事件处理
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.report_daily:
                reportViewpager.setCurrentItem(0, false);
                break;
            case R.id.report_month:
                reportViewpager.setCurrentItem(1, false);
                break;
            case R.id.report_quarter:
                reportViewpager.setCurrentItem(2, false);
                break;
            case R.id.reprot_back:
                finish();
                break;
            case R.id.switchorg:
                //打开抽屉
                drawerLayout.openDrawer(GravityCompat.END);
                break;

            default:
                break;
        }
    }

    /**
     * 每日控件不同状态显示控制
     */
    public void daily() {
        reportDaily.setTextColor(Color.parseColor("#5096F8"));
        reportMonth.setTextColor(Color.parseColor("#999797"));
        reportQuarter.setTextColor(Color.parseColor("#999797"));
    }

    /**
     * 每月控件不同状态显示控制
     */
    public void month() {
        reportDaily.setTextColor(Color.parseColor("#999797"));
        reportMonth.setTextColor(Color.parseColor("#5096F8"));
        reportQuarter.setTextColor(Color.parseColor("#999797"));
    }

    /**
     * 每季控件不同状态显示控制
     */
    public void quarter() {
        reportDaily.setTextColor(Color.parseColor("#999797"));
        reportMonth.setTextColor(Color.parseColor("#999797"));
        reportQuarter.setTextColor(Color.parseColor("#5096F8"));
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String id, String name) {
        orgId = id;
        title_ll.setText(name);
        drawerLayout.closeDrawers();
        CallBackUtils.removeCallBackMethod();
        TaskCallbackUtils.CallBackMethod();
        HideCallbackUtils.removeCallBackMethod();
    }

    private void initDatas() {
        OkGo.<String>post(Requests.Swatchmakeup)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                                JSONObject json = jsonObject1.getJSONObject("organization");
                                String Id;
                                try {
                                    Id = json.getString("id");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Id = "";
                                }
                                String parentId;
                                try {
                                    parentId = json.getString("parentId");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    //如果父ID为null
                                    parentId = "";
                                    //当做第一级处理
                                    status = false;
                                    mDatas2.add(new OrgBeans(1, 0, json.getString("name"), Id, parentId, json.getString("orgtype")));
                                }
                                String name;
                                try {
                                    name = json.getString("name");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    name = "";
                                }
                                String type;
                                try {
                                    type = json.getString("orgtype");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    type = "";
                                }
                                try {
                                    mAdapter = new ReportTreeListViewAdapters<OrgBeans>(mTree, ReportActivity.this,
                                            mDatas2, 0);
                                    mTree.setAdapter(mAdapter);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }

                                mData.add(new OrgenBeans(Id, parentId, name, type));
                            }
                            if (status) {
                                //拿到所有的ID
                                final ArrayList<String> IDs = new ArrayList<String>();
                                for (int i = 0; i < mData.size(); i++) {
                                    IDs.add(mData.get(i).getId());
                                }
                                //循环集合
                                for (int i = 0; i < mData.size(); i++) {
                                    //取出父ID，
                                    String pernID = mData.get(i).getParentId();
                                    //用ID判断是否有父级相同的
                                    if (IDs.contains(pernID)) {
                                        //存在相同的的不处理
                                    } else {
                                        //不存在相同的当做第一级
                                        mDatas2.add(new OrgBeans(1, 0, mData.get(i).getName(), mData.get(i).getId(), mData.get(i).getParentId(), mData.get(i).getType()));
                                        try {
                                            mAdapter = new ReportTreeListViewAdapters<>(mTree, ReportActivity.this,
                                                    mDatas2, 0);
                                            mTree.setAdapter(mAdapter);
                                        } catch (IllegalAccessException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //添加数据
    public void getAdd(int position, Nodes node) {
        String str = node.getIds();
        for (int i = 0; i < mData.size(); i++) {
            String pid = mData.get(i).getParentId();
            if (str.equals(pid)) {
                mAdapter.addExtraNode(position, mData.get(i).getName(), mData.get(i).getId(), mData.get(i).getParentId(),mData.get(i).getType());
            }
        }

    }

    //判断是否显示图标
    public boolean getmIcon(Nodes node) {
        String str = node.getIds();
        for (int i = 0; i < mData.size(); i++) {
            String pid = mData.get(i).getParentId();
            if (str.equals(pid)) {
                return true;
            }
        }
        return false;
    }
}
