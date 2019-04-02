package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.CheckReportTreeListViewAdapters;
import com.example.administrator.newsdf.pzgc.activity.check.fragment.CheckMonthQuarterFragment;
import com.example.administrator.newsdf.pzgc.activity.work.pchoose.PshooseFragAdapte;
import com.example.administrator.newsdf.pzgc.callback.CheckCallBackUTils1;
import com.example.baselibrary.view.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.treeviews.bean.OrgBeans;
import com.example.administrator.newsdf.treeviews.bean.OrgenBeans;
import com.example.administrator.newsdf.treeviews.utils.Nodes;
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
 * description: 检查审核报表
 *
 * @author lx
 *         date: 2018/8/10 0010 上午 10:19
 *         update: 2018/8/10 0010
 *         version:
 */
public class CheckReportActivity extends BaseActivity implements View.OnClickListener {
    private TextView reportDaily, reportMonth, reportQuarter, comButton;
    private ViewPager viewpager;
    private ListView checkReport;
    private CheckReportTreeListViewAdapters<OrgBeans> mAdapter;
    private List<OrgBeans> mDatas2;
    private List<OrgenBeans> mData;
    private TextView textView;
    private static CheckReportActivity mContext;
    private DrawerLayout Reportdrawer;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }

    public static CheckReportActivity getInstance() {
        return mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_report);
        addActivity(this);
        mData = new ArrayList<>();
        mDatas2 = new ArrayList<>();
        //初始化控件
        mContext = this;
        Reportdrawer = (DrawerLayout) findViewById(R.id.Reportdrawer);
        textView = (TextView) findViewById(R.id.com_title);
        checkReport = (ListView) findViewById(R.id.check_report);
        comButton = (TextView) findViewById(R.id.com_button);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        reportDaily = (TextView) findViewById(R.id.report_daily);
        reportMonth = (TextView) findViewById(R.id.report_month);
        reportQuarter = (TextView) findViewById(R.id.report_quarter);
        reportDaily.setOnClickListener(this);
        reportMonth.setOnClickListener(this);
        reportQuarter.setOnClickListener(this);
        comButton.setOnClickListener(this);
        comButton.setText("选择组织");
        comButton.setTextSize(10);
        textView.setText("标段排名");
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //构造适配器
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new CheckMonthQuarterFragment());
//        fragments.add(new CheckMonthReportFragment());
//        fragments.add(new CheckMonthYearFragment());
        PshooseFragAdapte adapter = new PshooseFragAdapte(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(adapter);
        //缓存界面
        viewpager.setOffscreenPageLimit(3);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        //关闭边缘滑动
        Reportdrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //侧滑栏关闭手势滑动
        Reportdrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //展示侧拉界面后，背景透明度（当前透明度为完全透明）
        Reportdrawer.setScrimColor(Color.TRANSPARENT);
        initDatas();
    }

    /**
     * 月控件不同状态显示控制
     */
    public void daily() {
        reportDaily.setTextColor(Color.parseColor("#5096F8"));
        reportMonth.setTextColor(Color.parseColor("#999797"));
        reportQuarter.setTextColor(Color.parseColor("#999797"));
    }

    /**
     * 季度控件不同状态显示控制
     */
    public void month() {
        reportDaily.setTextColor(Color.parseColor("#999797"));
        reportMonth.setTextColor(Color.parseColor("#5096F8"));
        reportQuarter.setTextColor(Color.parseColor("#999797"));
    }

    /**
     * 年控件不同状态显示控制
     */
    public void quarter() {
        reportDaily.setTextColor(Color.parseColor("#999797"));
        reportMonth.setTextColor(Color.parseColor("#999797"));
        reportQuarter.setTextColor(Color.parseColor("#5096F8"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.report_daily:
                //月
                viewpager.setCurrentItem(0, false);
                break;
            case R.id.report_month:
                viewpager.setCurrentItem(1, false);
                //季度
                break;
            case R.id.report_quarter:
                viewpager.setCurrentItem(2, false);
                //面
                break;
            case R.id.com_button:
                Reportdrawer.openDrawer(GravityCompat.END);
                break;
            default:
                break;
        }
    }

    private boolean status = true;

    private void initDatas() {
        OkGo.<String>post(Requests.GET_REPORT_ORGS)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                JSONObject json = jsonArray1.getJSONObject(i);
                                String Id;
                                try {
                                    Id = json.getString("id");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Id = "";
                                }
                                String parentId;
                                try {
                                    parentId = json.getString("parent_id");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    //如果父ID为null
                                    parentId = "";
                                    //当做第一级处理
                                    status = false;
                                    mDatas2.add(new OrgBeans(1, 0, json.getString("name"), Id, parentId, json.getString("org_type")));
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
                                    type = json.getString("org_type");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    type = "";
                                }
                                try {
                                    mAdapter = new CheckReportTreeListViewAdapters<OrgBeans>(checkReport, CheckReportActivity.this,
                                            mDatas2, 0);
                                    checkReport.setAdapter(mAdapter);
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
                                            mAdapter = new CheckReportTreeListViewAdapters<>(checkReport, CheckReportActivity.this,
                                                    mDatas2, 0);
                                            checkReport.setAdapter(mAdapter);
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

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
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

    //添加数据
    public void getAdd(int position, Nodes node) {
        String str = node.getIds();
        for (int i = 0; i < mData.size(); i++) {
            String pid = mData.get(i).getParentId();
            if (str.equals(pid)) {
                mAdapter.addExtraNode(position, mData.get(i).getName(), mData.get(i).getId(), mData.get(i).getParentId(), mData.get(i).getType());
            }
        }

    }

    String orgId;

    public void setOrgId(String id, String name) {
        orgId = id;
        textView.setText(name);
        Reportdrawer.closeDrawers();
        CheckCallBackUTils1.CheckCallback(id);
//        CheckCallBackUTils2.CheckCallback2(id);
//        CheckCallBackUTils3.CheckCallback3(id);

    }
}
