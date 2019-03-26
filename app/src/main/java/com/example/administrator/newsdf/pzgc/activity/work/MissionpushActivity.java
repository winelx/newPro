package com.example.administrator.newsdf.pzgc.activity.work;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.pzgc.Adapter.TaskPhotoAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.home.utils.HomeUtils;
import com.example.administrator.newsdf.pzgc.activity.work.pushadapter.PushAdapter;
import com.example.administrator.newsdf.pzgc.bean.PhotoBean;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.baselibrary.view.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.FloatMeunAnims;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * description: 任务推送界面
 * 从viewpager的fragment界面发送要推送数据集合，用map存储数据，以fragment所处的页数为key
 *
 * @author lx
 *         date: 2018/3/22 0022 下午 2:39
 *         update: 2018/3/22 0022
 *         version:
 */
public class MissionpushActivity extends BaseActivity implements View.OnClickListener {
    private TextView title,drawer_layout_text;
    private LinearLayout button;
    private LinearLayout com_img;
    private RelativeLayout tabulation;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private PushAdapter mAdapter;
    int msg = 0, page = 1, pagss = 0;
    private ArrayList<String> titlename;
    private String id, wbsname;
    private ArrayList<PhotoBean> imagePaths;

    private SmartRefreshLayout smartRefreshLayout;
    private DrawerLayout drawer_layout;
    private ListView drawer_layout_list;
    private TaskPhotoAdapter taskAdapter;
    private boolean drew = true;
    //保存每个节目推送的ID
    private Map<String, List<String>> pushMap;
    private String titles;
    private ProgressDialog dialog;
    private String type, wbspath, wbsId;
    boolean isParent, iswbs;

    //弹出框
    private CircleImageView  fab;
    private LinearLayout meun_standard, meun_photo;
    private FloatMeunAnims floatMeunAnims;
    private boolean liststatus = true;
    boolean anim = true;
    int viewpagertype ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missionpush);
        floatMeunAnims = new FloatMeunAnims();
        pushMap = new HashMap<>();
        //获取到intent传过来得集合
        titlename = new ArrayList<>();
        imagePaths = new ArrayList<>();
        fab = (CircleImageView) findViewById(R.id.fab);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.drawerLayout_smart);
        smartRefreshLayout.setEnableRefresh(false);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_layout_list = (ListView) findViewById(R.id.drawer_layout_list);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        mTabLayout = (TabLayout) findViewById(R.id.tl_tab);
        mViewPager = (ViewPager) findViewById(R.id.vp_pager);
        meun_standard = (LinearLayout) findViewById(R.id.meun_standard);
        meun_photo = (LinearLayout) findViewById(R.id.meun_photo);
        drawer_layout_text= (TextView) findViewById(R.id.drawer_layout_text);

        meun_photo.setOnClickListener(this);
        meun_standard.setOnClickListener(this);
        fab.setOnClickListener(this);
        //侧滑栏关闭手势滑动
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawer_layout.setScrimColor(Color.TRANSPARENT);
        title = (TextView) findViewById(R.id.com_title);
        button = (LinearLayout) findViewById(R.id.com_button);
        com_img = (LinearLayout) findViewById(R.id.com_img);
        com_img.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
        tabulation = (RelativeLayout) findViewById(R.id.tabulation);
        Intent intent = getIntent();

        try {
            //标题
            titles = intent.getExtras().getString("titles");
            //节点ID（重新选择wbs节点后会变）
            id = intent.getExtras().getString("id");
            //节点ID（不变，）
            wbsId = intent.getExtras().getString("id");
            //节点名
            wbsname = intent.getExtras().getString("wbsname");
            //节点路径
            wbspath = intent.getExtras().getString("wbsPath");
            //类型
            type = intent.getExtras().getString("type");
            //是否是父节点
            isParent = intent.getExtras().getBoolean("isParent");
            //是否是wbs
            iswbs = intent.getExtras().getBoolean("iswbs");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            titlename = intent.getExtras().getStringArrayList("title");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        title.setText(titles);
        taskAdapter = new TaskPhotoAdapter(imagePaths, MissionpushActivity.this);
        drawer_layout_list.setAdapter(taskAdapter);

        mViewPager.setOffscreenPageLimit(6);
        mAdapter = new PushAdapter(getSupportFragmentManager(), titlename);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mAdapter.getID(id);
        //那我们如果真的需要监听tab的点击或者ViewPager的切换,则需要手动配置ViewPager的切换,例如:
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
        if (titlename.size() > 3) {
            mTabLayout.setTabMode(0);
        }
        //列表详情
        tabulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MissionpushActivity.this, TabulationActivity.class);
                intent.putExtra("data", titlename);
                startActivityForResult(intent, 1);
            }
        });
        //新增推送
        com_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpagertype =mViewPager.getCurrentItem();
                Intent intent = new Intent(MissionpushActivity.this, NewpushActivity.class);
                intent.putExtra("wbsname", wbsname);
                intent.putExtra("wbspath", wbspath);
                intent.putExtra("wbsID", wbsId);
                intent.putExtra("type", type);
                intent.putExtra("isParent", isParent);
                intent.putExtra("iswbs", iswbs);
                //节点名称
                intent.putExtra("title", "下发任务");
                startActivity(intent);
            }
        });

        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         *    侧拉listview上拉加载
         */
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                drew = false;
                page++;
                if (liststatus) {
                    HomeUtils.photoAdm(id, page, imagePaths, drew, taskAdapter, wbspath);
                    //传入false表示加载失败
                } else {
                    HomeUtils.getStard(id, page, imagePaths, drew, taskAdapter, wbspath);
                    //传入false表示加载失败
                }
                refreshlayout.finishLoadmore(1000);
            }
        });

        /**
         *    推送
         */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拿到当前的Viewpager的页数
                String type = String.valueOf(mViewPager.getCurrentItem());
                List<String> list = new ArrayList<String>();
                list = pushMap.get(type);
                pagss = pagss + 1;
                String strids = Dates.listToStrings(list);
                if (strids != null) {
                    dialog = new ProgressDialog(MissionpushActivity.this);
                    // 设置进度条的形式为圆形转动的进度条
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setMessage("推送任务中...");
                    // 设置是否可以通过点击Back键取消
                    dialog.setCancelable(true);
                    // 设置在点击Dialog外是否取消Dialog进度条
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    pushOkgo(strids);
                } else {
                    Dates.disDialog();
                    ToastUtils.showShortToastCenter("请选择推送项");
                }

            }

        });
    }

    /**
     * 接受activity返回的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            viewpagertype = data.getIntExtra("position", 1);
            mViewPager.setCurrentItem(viewpagertype);
        }
    }

    /**
     * 单选数据存储和删除
     *
     * @param id
     * @param lean
     */
    public void getpush(String id, boolean lean) {
        //拿到当前的Viewpager的页数
        String type = String.valueOf(mViewPager.getCurrentItem());
        //判断map是否存在
        if (pushMap.size() != 0) {
            //判断是否有当前页数的数据
            boolean flag = pushMap.containsKey(type);
            if (flag) {
                if (lean) {
                    List<String> Value = new ArrayList<>();
                    //根据key取value
                    Value = pushMap.get(type);
                    //添加数据
                    Value.add(id);
                    //清除数据
                    pushMap.remove(type);
                    //重新添加数据
                    pushMap.put(type, Value);

                } else {
                    List<String> Value1 = new ArrayList<>();
                    //拿到当前界面存的数据集合
                    Value1 = pushMap.get(type);
                    pushMap.remove(type);
                    if (Value1.size() == 1) {
                    } else {
                        for (int i = 0; i < Value1.size(); i++) {
                            //判断要删除的是哪个数据
                            if (Value1.get(i) == id) {
                                //删除数据
                                Value1.remove(i);
//                            //重新存入删除后的数据
                                pushMap.put(type, Value1);
                            }
                        }
                    }
                }
            } else {
                List<String> Value = new ArrayList<>();
                Value.add(id);
                pushMap.put(type + "", Value);
            }
        } else {
            List<String> Value = new ArrayList<>();
            Value.add(id);
            pushMap.put(type + "", Value);
        }
    }

    /**
     * 全选数据的存储和删除
     */
    public void getAllPush(ArrayList<String> ListId, boolean lean) {
        //拿到当前的Viewpager的页数
        String type = String.valueOf(mViewPager.getCurrentItem());
        if (lean) {
            //删除之前的所有数据
            pushMap.remove(type);
            //存储全部数据
            pushMap.put(type, ListId);
        } else {
            //全部取消
            pushMap.remove(type);
        }
    }

    /**
     * 推送请求
     */
    void pushOkgo(String str) {
        OkGo.post(Requests.pushOKgo)
                .tag(this)
                .isMultipart(true)
                .params("ids", str)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String msg = jsonObject.getString("msg");
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                Dates.disDialog();
                                ToastUtils.showShortToastCenter("推送成功");
                                if (mViewPager != null && mAdapter != null) {
                                    //拿到当前的Viewpager的页数
                                    int types = mViewPager.getCurrentItem();
                                    //设置当前选中的页面。
                                    mViewPager.setCurrentItem(types, true);
                                    //又一次载入position的页面
                                    mAdapter.update(types);
                                    dialog.dismiss();
                                }
                            } else {
                                ToastUtils.showShortToastCenter(msg);
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
                });
    }

    public String getId() {
        return id;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

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
                Dates.getDialog(MissionpushActivity.this, "请求数据中...");
                HomeUtils.photoAdm(id, page, imagePaths, drew, taskAdapter, wbspath);
                //上拉加载的状态判断
                liststatus = true;
                drawer_layout.openDrawer(GravityCompat.START);
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
                Dates.getDialog(MissionpushActivity.this, "请求数据中...");
                HomeUtils.getStard(id, page, imagePaths, drew, taskAdapter, wbspath);
                drawer_layout.openDrawer(GravityCompat.START);
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
}