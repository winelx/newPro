package com.example.administrator.newsdf.activity.work;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.bean.Icon;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.adapter.SettingAdapter;
import com.example.administrator.newsdf.camera.CheckPermission;
import com.example.administrator.newsdf.utils.Request;
import com.example.administrator.newsdf.utils.SPUtils;
import com.example.administrator.newsdf.utils.list.XListView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionItem;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/12/29 0029.
 */
//推送选择联系人
public class MemberActivity extends AppCompatActivity implements XListView.IXListViewListener {
    private XListView uslistView;
    private TextView textView, comtitle;
    private EditText useditext;
    private LinearLayout comback;
    private SettingAdapter mAdapter = null;
    private ArrayList<Icon> mData;
    private Context mContext;
    private SPUtils spUtils;
    private CheckPermission checkPermission;
    private LinearLayout backgroud;
    private EditText search_editext;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_project_respon);
        mContext = MemberActivity.this;
        mData = new ArrayList<>();
        List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
        permissonItems.add(new PermissionItem(Manifest.permission.CALL_PHONE, getString(R.string.permission_cus_item_phone), R.drawable.permission_ic_phone));
        HiPermission.create(MemberActivity.this)
                .permissions(permissonItems)
                .checkMutiPermission(null);

        search_editext = (EditText) findViewById(R.id.search_editext);
        backgroud = (LinearLayout) findViewById(R.id.mine_backgroud);
        uslistView = (XListView) findViewById(R.id.us_listView);

        findViewById(R.id.search_linear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = search_editext.getText().toString();
                ArrayList<Icon> icon = new ArrayList<Icon>();
                for (int i = 0; i < mData.size(); i++) {
                    String str = mData.get(i).getName();
                    if (result.equals(str)) {
                        icon.add(mData.get(i));
                    }
                }
                if (icon.size() != 0) {
                    mAdapter.getData(icon);
                }
            }
        });

        search_editext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //是否是回车键
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    //隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(MemberActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    result = search_editext.getText().toString();

                    ArrayList<Icon> icon = new ArrayList<Icon>();
                    for (int i = 0; i < mData.size(); i++) {
                        String str = mData.get(i).getName();
                        if (result.equals(str)) {
                            icon.add(mData.get(i));
                        }
                    }
                    if (icon.size() != 0) {
                        mAdapter.getData(icon);
                    }
                }
                return false;
            }
        });
        uslistView.setPullRefreshEnable(true);
        uslistView.setPullLoadEnable(false);
        uslistView.setAutoLoadEnable(false);
        uslistView.setXListViewListener(this);
        uslistView.setRefreshTime(getTime());
        comtitle = (TextView) findViewById(R.id.com_title);
        comback = (LinearLayout) findViewById(R.id.com_back);
        okgo();
        mAdapter = new SettingAdapter<Icon>(mData, R.layout.member_item) {
            @Override
            public void bindView(SettingAdapter.ViewHolder holder, final Icon obj) {
                //头像
                holder.setImage(R.id.circleImageView, obj.getImageUrl());
                //名字
                holder.setText(R.id.member_name, obj.getName());
                holder.setOnClickListener(R.id.member, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent newpush = new Intent();
                        newpush.putExtra("name", obj.getName());
                        newpush.putExtra("userId", obj.getId());
                        //回传数据到主Activity
                        setResult(2, newpush);
                        finish(); //此方法后才能返回主Activity
                    }
                });
            }
        };
        spUtils = new SPUtils();
        uslistView.setAdapter(mAdapter);
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //网络请求
    void okgo() {
        OkGo.post(Request.Members)
                .params("orgId", SPUtils.getString(mContext, "orgId", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        mData.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject content = jsonArray.getJSONObject(i);
                                String id = content.getString("id");
                                String userId = content.getString("userId");
                                String name = content.getString("name");
                                String moblie = content.getString("moblie");
                                String imageUrl = content.getString("imageUrl");
                                mData.add(new Icon(id, userId, name, moblie, imageUrl));
                            }
                            if (mData.size() != 0) {
                                backgroud.setVisibility(View.GONE);
                                mAdapter.getData(mData);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onRefresh() {
        okgo();
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                //进行是否登录判断
                onLoad();
                return false;
                //表示延迟3秒发送任务
            }
        }).sendEmptyMessageDelayed(0, 2500);

    }

    @Override
    public void onLoadMore() {

    }

    private void onLoad() {
        uslistView.stopRefresh();
        uslistView.setRefreshTime(getTime());
    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }
}
