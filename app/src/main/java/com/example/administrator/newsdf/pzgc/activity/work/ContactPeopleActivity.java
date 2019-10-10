package com.example.administrator.newsdf.pzgc.activity.work;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.Icon;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


/**
 * description: 选择责任人
 *
 * @author lx
 * date: 2018/4/19 0019 上午 11:33
 * update: 2018/4/19 0019
 * version:
 */
public class ContactPeopleActivity extends BaseActivity {
    private RecyclerView uslistView;
    private SmartRefreshLayout refreshlayout;
    private PeopleAdapter mAdapter;
    private ArrayList<Icon> mData;
    private ArrayList<Icon> searchData;
    private Context mContext;
    private LinearLayout backgroud;
    private PopupWindow mPopupWindow;
    private TextView toolbar;
    private EditText search;

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_people);

        mContext = ContactPeopleActivity.this;
        mData = new ArrayList<>();
        searchData = new ArrayList<>();
        searchData = new ArrayList<>();
        refreshlayout = (SmartRefreshLayout) findViewById(R.id.refreshlayout);
        //是否启用下拉刷新功能
        refreshlayout.setEnableRefresh(true);
        //是否启用上拉加载功能
        refreshlayout.setEnableLoadmore(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshlayout.setEnableOverScrollDrag(true);
        //是否在列表不满一页时候开启上拉加载功能
        refreshlayout.setEnableLoadmoreWhenContentNotFull(false);
        /* 下拉刷新*/
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                okgo();
                //关闭刷新
                refreshlayout.finishRefresh();
            }
        });
        backgroud = (LinearLayout) findViewById(R.id.mine_backgroud);
        uslistView = (RecyclerView) findViewById(R.id.us_listView);
        toolbar = (TextView) findViewById(R.id.com_title);
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
        uslistView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PeopleAdapter(R.layout.contact_item, mData);
        uslistView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent newpush = new Intent();
                newpush.putExtra("name", searchData.get(position).getName());
                newpush.putExtra("userId", searchData.get(position).getId());
                //回传数据到主Activity
                setResult(2, newpush);
                finish(); //此方法后才能返回主Activity
            }
        });
        okgo();
    }

    /**
     * 网络请求
     */
    void okgo() {
        OkGo.post(Requests.Members)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            mData.clear();
                            searchData.clear();
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
                                searchData.addAll(mData);
                                backgroud.setVisibility(View.GONE);
                                mAdapter.setNewData(searchData);
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
        mPopupWindow.showAsDropDown(toolbar);
        backgroundAlpha(0.6f);
//添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());
    }

    /**
     * 弹出框的点击事件
     * @return View
     */
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
                    try {
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(ContactPeopleActivity.this.getCurrentFocus()
                                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    } catch (Exception e) {

                    }
                    mAdapter.setNewData(searchData);
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

    /**
     * 界面阴影
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    /**
     * 弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }


    class PeopleAdapter extends BaseQuickAdapter<Icon, BaseViewHolder> {
        public PeopleAdapter(int layoutResId, @Nullable List<Icon> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, Icon obj) {
            //头像
            ImageView logoview = holder.getView(R.id.contact_acatar);
            Glide.with(mContext)
                    .load(obj.getImageUrl())
                    .into(logoview);
            //名字
            holder.setText(R.id.content_name, obj.getName());
            holder.setText(R.id.content_phone, obj.getMoblie());

        }
    }
}