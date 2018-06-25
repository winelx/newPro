package com.example.administrator.newsdf.activity.work;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.newsdf.Adapter.TaskPhotoAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.activity.home.HomeUtils;
import com.example.administrator.newsdf.bean.PhotoBean;
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

import static com.example.administrator.newsdf.R.id.drawerLayout_smart;
import static com.example.administrator.newsdf.R.id.drawer_layout;
import static com.example.administrator.newsdf.R.id.drawer_layout_list;

/**
 * description: 新增推送任务
 *
 * @author: lx
 * date: 2018/2/6 0006 上午 9:20
 * update: 2018/2/6 0006
 * version:
 */
public class NewpushActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView comButton, pushUsername, wbsText, replyButton;
    private IconTextView back;
    private LinearLayout newpushWbs, newpushUser;
    private EditText newpushCheck, pushContent, pushCheckstandard;
    private String wbsid, wbsname, userid, wbsPath, id;
    private Context mContent;
    boolean staus = false;
    private int page = 1;
    private ArrayList<PhotoBean> imagePaths;
    private TaskPhotoAdapter taskAdapter;

    private DrawerLayout drawerLayout;
    private SmartRefreshLayout drawerLayoutSmart;
    private ListView drawerLayoutList;
    private boolean drew = false;
    String type, wbspath;
    boolean isParent, iswbs;


    //弹出框
    private CircleImageView meun_standard, meun_photo, fab;
    private FloatMeunAnims floatMeunAnims;
    private boolean liststatus = true;
    boolean anim = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpush);
        mContent = NewpushActivity.this;
        floatMeunAnims = new FloatMeunAnims();
        imagePaths = new ArrayList<>();
        meun_standard = (CircleImageView) findViewById(R.id.meun_standard);
        meun_photo = (CircleImageView) findViewById(R.id.meun_photo);
        meun_photo.setOnClickListener(this);
        meun_standard.setOnClickListener(this);
        fab = (CircleImageView) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        drawerLayout = (DrawerLayout) findViewById(drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayoutSmart = (SmartRefreshLayout) findViewById(drawerLayout_smart);
        drawerLayoutSmart.setEnableRefresh(false);
        drawerLayoutList = (ListView) findViewById(drawer_layout_list);
        comButton = (TextView) findViewById(R.id.newpush_title);
        replyButton = (TextView) findViewById(R.id.newpush_button);
        //wbs名字
        wbsText = (TextView) findViewById(R.id.newpush_text);
        //推送人名字
        pushUsername = (TextView) findViewById(R.id.push_username);
        //选择推送人
        newpushWbs = (LinearLayout) findViewById(R.id.newpush_wbs);
        //选择推送人
        newpushUser = (LinearLayout) findViewById(R.id.newpush_user);
        //标准
        pushCheckstandard = (EditText) findViewById(R.id.push_checkstandard);
        //推送任务名字
        newpushCheck = (EditText) findViewById(R.id.newpush_check);
        //送内容
        pushContent = (EditText) findViewById(R.id.push_content);
        back = (IconTextView) findViewById(R.id.newpush_back);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //侧滑栏关闭手势滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        comButton.setText("下发任务");
        replyButton.setText("推送");
        Intent intent = getIntent();
        //上个界面传递过来的数据
        isParent = intent.getExtras().getBoolean("isParent");
        iswbs = intent.getExtras().getBoolean("iswbs");
        type = intent.getExtras().getString("type");
        wbspath = intent.getExtras().getString("wbspath");
        //wbsID
        wbsid = intent.getExtras().getString("wbsID");
        id = intent.getExtras().getString("wbsID");
        //wbs名称
        wbsname = intent.getExtras().getString("wbsname");
        //wbs路径
        wbsPath = intent.getExtras().getString("wbsPath");
        wbsText.setText(wbspath);
        //返回键
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //选择wbs
        newpushWbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewpushActivity.this, TaskWbsActivity.class);
                intent.putExtra("wbsname", wbsname);
                intent.putExtra("wbspath", wbspath);
                intent.putExtra("wbsID", wbsid);
                intent.putExtra("type", type);
                intent.putExtra("isParent", isParent);
                intent.putExtra("iswbs", iswbs);
                startActivityForResult(intent, 1);
            }
        });
        //推送
        replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Content = pushContent.getText().toString();
                if (TextUtils.isEmpty(userid) && TextUtils.isEmpty(Content) && TextUtils.isEmpty(wbsid)) {
                    Toast.makeText(mContent, "还有未填项", Toast.LENGTH_SHORT).show();
                } else {
                    replyButton.setEnabled(false);
                    Dates.getDialog(NewpushActivity.this, "推送中");
                    if (!staus) {
                        okgo();
                        staus = true;
                    }
                }

            }
        });
        //图册适配器
        taskAdapter = new TaskPhotoAdapter(imagePaths, NewpushActivity.this);
        drawerLayoutList.setAdapter(taskAdapter);
        //选择联系人
        newpushUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewpushActivity.this, ContactPeopleActivity.class);
                intent.putExtra("data", "newpush");
                startActivityForResult(intent, 1);
            }
        });

        /**
         *    侧拉listview上拉加载
         */
        drawerLayoutSmart.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                drew = false;
                //传入false表示加载失败
                if (liststatus) {
                    HomeUtils.photoAdm(wbsid, page, imagePaths, drew, taskAdapter, wbsText.getText().toString());
                } else {
                    HomeUtils.getStard(wbsid, page, imagePaths, drew, taskAdapter, wbsText.getText().toString());
                }
                refreshlayout.finishLoadmore(1000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断是不是Activity的返回，不是就是相机的返回
        if (requestCode == 1 && resultCode == RESULT_OK) {
            id = data.getStringExtra("id");
            wbsText.setText(data.getStringExtra("titles"));
            page = 1;
            photoAdm(wbsid);
        } else if (requestCode == 1 && resultCode == 2) {
            String name = data.getStringExtra("name");
            userid = data.getStringExtra("userId");
            pushUsername.setText(name);
        }
    }

    void okgo() {
        OkGo.post(Requests.newPush)
                .params("wbsId", id)
                .params("checkStandard", pushCheckstandard.getText().toString())
                .params("leaderId", userid)
                .params("label", newpushCheck.getText().toString())
                .params("content", pushContent.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String str = jsonObject.getString("msg");
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                finish();
                                Toast.makeText(mContent, str, Toast.LENGTH_SHORT).show();
                            } else {
                                staus = false;
                                Toast.makeText(mContent, str, Toast.LENGTH_SHORT).show();
                            }
                            newpushCheck.setText("");
                            replyButton.setEnabled(true);
                            Dates.disDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 查询图册
     */

    private void photoAdm(String string) {
        OkGo.post(Requests.Photolist)
                .params("WbsId", string)
                .params("page", page)
                .params("rows", 30)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.contains("data")) {
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
                                    filePath = Requests.networks + filePath;
                                    imagePaths.add(new PhotoBean(id, filePath, drawingNumber, drawingName, drawingGroupName));
                                }
                                //    wbspathl
                                taskAdapter.getData(imagePaths, wbsname);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (drew) {
                                imagePaths.clear();
                                imagePaths.add(new PhotoBean(null, "暂无数据", "暂无数据", "暂无数据", "暂无数据"));
                            }
                            taskAdapter.getData(imagePaths, wbsname);
                        }

                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.meun_photo:
                //请求图纸
                //加载第一页
                page = 1;
                //请求数据时清除之前的
                drew = true;
                //网络请求
                Dates.getDialog(NewpushActivity.this, "请求数据中...");
                HomeUtils.photoAdm(wbsid, page, imagePaths, drew, taskAdapter, wbsText.getText().toString());
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
                Dates.getDialog(NewpushActivity.this, "请求数据中...");
                HomeUtils.getStard(wbsid, page, imagePaths, drew, taskAdapter, wbsText.getText().toString());
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
    }
}

