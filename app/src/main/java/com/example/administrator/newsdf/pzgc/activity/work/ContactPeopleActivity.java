package com.example.administrator.newsdf.pzgc.activity.work;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.Icon;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.list.XListView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Response;


/**
 * description: 选择责任人
 *
 * @author lx
 *         date: 2018/4/19 0019 上午 11:33
 *         update: 2018/4/19 0019
 *         version:
 */
public class ContactPeopleActivity extends AppCompatActivity implements XListView.IXListViewListener {
    private XListView uslistView;
    private SettingAdapter<Icon> mAdapter = null;
    private ArrayList<Icon> mData;
    private ArrayList<Icon> searchData;
    private Context mContext;
    private LinearLayout backgroud;
    private PopupWindow mPopupWindow;
    private TextView Toolbar;
    private EditText search;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_people);
        mContext = ContactPeopleActivity.this;
        mData = new ArrayList<>();
        searchData = new ArrayList<>();
        searchData = new ArrayList<>();
        okgo();
        backgroud = (LinearLayout) findViewById(R.id.mine_backgroud);
        uslistView = (XListView) findViewById(R.id.us_listView);
        Toolbar = (TextView) findViewById(R.id.com_title);
        uslistView.setPullRefreshEnable(true);
        uslistView.setPullLoadEnable(false);
        uslistView.setAutoLoadEnable(false);
        uslistView.setXListViewListener(this);
        uslistView.setRefreshTime(getTime());
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.com_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });
        mAdapter = new SettingAdapter<Icon>(mData, R.layout.contact_item) {
            @Override
            public void bindView(SettingAdapter.ViewHolder holder, final Icon obj) {
                //头像
                holder.setImages(R.id.contact_acatar, obj.getImageUrl(), mContext);
                //名字
                holder.setText(R.id.content_name, obj.getName());
                holder.setText(R.id.content_phone, obj.getMoblie());
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
        uslistView.setAdapter(mAdapter);
    }
    //网络请求
    void okgo() {
        OkGo.post(Requests.Members)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            mData.clear();
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject content = jsonArray.getJSONObject(i);
                                String id = content.getString("id");
                                String userId = content.getString("userId");
                                String name = content.getString("name");
                                String moblie;
                                try {
                                    moblie = content.getString("positionName");
                                } catch (JSONException e) {
                                    moblie = "";
                                }
                                String imageUrl = content.getString("imageUrl");
                                imageUrl = Requests.networks + imageUrl;
                                mData.add(new Icon(id, userId, name, moblie, imageUrl));
                            }
                            if (mData.size() != 0) {
                                    mData.add(0, new Icon("", "", "", "无", ""));
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

    /**
     * 弹出框
     */
    private void dialog() {
        View contentView = getPopupWindowContentView();
        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
// 设置好参数之后再show
        mPopupWindow.showAsDropDown(Toolbar);
        backgroundAlpha(0.6f);
//添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());
    }

    //弹出框的点击事件
    private View getPopupWindowContentView() {
        // 一个自定义的布局，作为显示的内容
        // 布局ID
        int layoutId = R.layout.popup_content_layout;
        View contentView = LayoutInflater.from(this).inflate(layoutId, null);
        search = contentView.findViewById(R.id.menu_item1);
        //回车键搜索
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //是否是回车键
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    String string = search.getText().toString();
                    searchData.clear();
                    for (int i = 0; i < mData.size(); i++) {
                        String name = mData.get(i).getName();
                        if (name.contains(string)) {
                            searchData.add(mData.get(i));
                        }
                    }
                    //隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(ContactPeopleActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    mAdapter.getData(searchData);
                    mPopupWindow.dismiss();
                }
                return false;
            }
        });
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.menu_item2:
                        search.setText("");
                        break;
                    case R.id.recycler_view:
                        if (mPopupWindow != null) {
                            mPopupWindow.dismiss();
                        }
                        break;
                    default:
                        break;
                }
            }
        };
        contentView.findViewById(R.id.menu_item2).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.recycler_view).setOnClickListener(menuItemOnClickListener);
        return contentView;
    }

    //界面阴影
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }
}