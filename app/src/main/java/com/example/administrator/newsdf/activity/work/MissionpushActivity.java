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

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.work.PushAdapter.PushAdapter;
import com.example.administrator.newsdf.adapter.TaskPhotoAdapter;
import com.example.administrator.newsdf.bean.PhotoBean;
import com.example.administrator.newsdf.bean.Push_item;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.service.PushCallbackUtils;
import com.example.administrator.newsdf.utils.Dates;
import com.example.administrator.newsdf.utils.Request;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.newsdf.R.id.drawerLayout_smart;


/**
 * description: 任务推送
 *
 * @author lx
 *         date: 2018/3/22 0022 下午 2:39
 *         update: 2018/3/22 0022
 *         version:
 */
public class MissionpushActivity extends AppCompatActivity {
    private TextView title;
    private LinearLayout button;
    private LinearLayout com_img;
    private RelativeLayout tabulation;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private PushAdapter mAdapter;
    int msg = 0;
    int page = 1;
    ArrayList<ArrayList<Push_item>> push;
    ArrayList<String> titlename = null;
    ArrayList<String> ids = new ArrayList<>();
    private Context mContext;
    String id, wbsname;
    private ArrayList<PhotoBean> imagePaths;
    private CircleImageView fab;
    private SmartRefreshLayout smartRefreshLayout;
    private DrawerLayout drawer_layout;
    private ListView drawer_layout_list;
    private TaskPhotoAdapter taskAdapter;
    private boolean drew = true;
    //保存每个节目推送的ID
    private Map<String, List<String>> pushMap;
    private String titles;
    private String wbspathl;
    int pagss = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missionpush);
        mContext = MissionpushActivity.this;
        push = new ArrayList<>();
        pushMap = new HashMap<>();
        imagePaths = new ArrayList<>();
        fab = (CircleImageView) findViewById(R.id.fab);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(drawerLayout_smart);
        smartRefreshLayout.setEnableRefresh(false);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_layout_list = (ListView) findViewById(R.id.drawer_layout_list);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
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
        //获取到intent传过来得集合
        titlename = new ArrayList<>();
        try {
            titles = intent.getExtras().getString("titles");
            id = intent.getExtras().getString("id");
            wbsname = intent.getExtras().getString("wbsnam");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            ids = intent.getExtras().getStringArrayList("ids");
            titlename = intent.getExtras().getStringArrayList("title");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        title.setText(titles);
        initView();
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
                Intent intent = new Intent(MissionpushActivity.this, NewpushActivity.class);
                intent.putExtra("wbsname", wbsname);
                intent.putExtra("wbsID", id);
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                drew = true;
                photoAdm(id);
                drawer_layout.openDrawer(GravityCompat.START);
            }
        });
        /**
         *    侧拉listview上拉加载
         */
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                drew = false;
                photoAdm(id);
                //传入false表示加载失败
                refreshlayout.finishLoadmore(1500);
            }
        });

        //推送
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dates.getDialog(MissionpushActivity.this, "推送消息中");
                //拿到当前的Viewpager的页数
                String type = String.valueOf(mViewPager.getCurrentItem());
                List<String> list = new ArrayList<String>();
                list = pushMap.get(type);
                pagss = pagss + 1;
                String strids = Dates.listToString(list);
                if (strids != null) {
//                pushOkgo(strids);
                    PushCallbackUtils.removeCallBackMethod();
                } else {
                    Dates.disDialog();
                    ToastUtils.showShortToast("请选择推送项");
                }
            }
        });
    }

    private void initView() {
        taskAdapter = new TaskPhotoAdapter(imagePaths, MissionpushActivity.this);
        drawer_layout_list.setAdapter(taskAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.tl_tab);
        if (titlename.size() > 3) {
            mTabLayout.setTabMode(0);
        }
        mViewPager = (ViewPager) findViewById(R.id.vp_pager);
        mViewPager.setOffscreenPageLimit(6);
        mAdapter = new PushAdapter(getSupportFragmentManager(), titlename);
        mViewPager.setAdapter(mAdapter);
        mAdapter.getID(id);
        mTabLayout.setupWithViewPager(mViewPager);
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
    }

    /**
     * 接受activity返回的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            msg = data.getIntExtra("position", 1);
            mViewPager.setCurrentItem(msg);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 查询图册
     */
    private void photoAdm(String string) {
        OkGo.post(Request.Photolist)
                .params("WbsId", string)
                .params("page", page)
                .params("rows", 30)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.indexOf("data") != -1) {
                            if (drew) {
                                imagePaths.clear();
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
                                    imagePaths.add(new PhotoBean(id, filePath, drawingNumber, drawingName, drawingGroupName));
                                }
                                taskAdapter.getData(imagePaths, wbspathl);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (drew) {
                                imagePaths.clear();
                                imagePaths.add(new PhotoBean(null, "暂无数据", "暂无数据", "暂无数据", "暂无数据"));
                            }
                            taskAdapter.getData(imagePaths, wbspathl);
                        }

                    }
                });
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
        OkGo.post(Request.pushOKgo)
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
                                ToastUtils.showShortToast("推送成功");
                                PushCallbackUtils.removeCallBackMethod();
                            } else {
                                ToastUtils.showShortToast(msg);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public String getId() {
        return id;
    }

}