package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.App;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.CheckRectifyMessageAdapter;
import com.example.administrator.newsdf.pzgc.bean.MyNoticeDataBean;
import com.example.administrator.newsdf.pzgc.callback.TaskCallback;
import com.example.administrator.newsdf.pzgc.callback.TaskCallbackUtils;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.ScreenUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/8/8 0008.
 */

/**
 * description: 检查通知单我的模块通知列表
 *
 * @author lx
 *         date: 2018/8/8 0008 下午 4:03
 *         update: 2018/8/8 0008
 *         version:
 */
public class ChecknoticeMessagelistActivity extends AppCompatActivity implements View.OnClickListener, TaskCallback {
    private RecyclerView listView;
    private ArrayList<MyNoticeDataBean> mData;
    private Context mContext;
    private TextView titleView;
    private ImageView checklistmeunimage;
    private PopupWindow mPopupWindow;
    private SmartRefreshLayout refreshLayout;
    private float resolution;
    private String id;
    private String status = "3";
    private int page = 1;
    private CheckRectifyMessageAdapter mAdapter;
    private RelativeLayout back_not_null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checknoticemessage);
        Intent intent = getIntent();
        TaskCallbackUtils.setCallBack(this);
        mContext = ChecknoticeMessagelistActivity.this;
        id = intent.getStringExtra("id");
        resolution = ScreenUtil.getDensity(App.getInstance());
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        back_not_null = (RelativeLayout) findViewById(R.id.back_not_null);
        checklistmeunimage = (ImageView) findViewById(R.id.checklistmeunimage);
        checklistmeunimage.setBackgroundResource(R.mipmap.meun);
        checklistmeunimage.setVisibility(View.VISIBLE);
        listView = (RecyclerView) findViewById(R.id.maber_tree);
        titleView = (TextView) findViewById(R.id.titleView);
        titleView.setText(intent.getStringExtra("orgName"));
        mData = new ArrayList<>();
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        checklistmeunimage.setOnClickListener(this);

        /**
         *   下拉刷新
         */
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                mData.clear();
                getdate();
                //传入false表示刷新失败
                refreshlayout.finishRefresh(800);
            }
        });
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getdate();
                //传入false表示加载失败
                refreshlayout.finishLoadmore(800);
            }
        });
        listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //初始化适配器
        mAdapter = new CheckRectifyMessageAdapter(mContext);
        listView.setAdapter(mAdapter);
        getdate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checklistmeunimage:
                meun();
                break;
            default:
                break;
        }
    }

    private void meun() {
        //弹出框=
        View contentView = getPopupWindowContentView();
        mPopupWindow = new PopupWindow(contentView,
                Dates.withFontSize(resolution) + 20, Dates.higtFontSize(resolution), true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置好参数之后再show
        // 默认在mButton2的左下角显示
        mPopupWindow.showAsDropDown(checklistmeunimage);
        backgroundAlpha(0.5f);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());
    }

    public View getPopupWindowContentView() {
        // 一个自定义的布局，作为显示的内容
        // 布局ID
        int layoutId = R.layout.pop_checknotice;
        View contentView = LayoutInflater.from(this).inflate(layoutId, null);
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                mData.clear();
                switch (v.getId()) {
                    case R.id.pop_All:
                        status = "3";
                        break;
                    case R.id.pop_submit:
                        //待提交
                        status = "0";
                        break;
                    case R.id.pop_financial:
                        status = "1";
                        break;
                    case R.id.pop_manage:
                        status = "2";
                        break;
                    case R.id.pop_back:
                        status = "6";
                        break;
                    default:
                        break;
                }
                getdate();
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        };
        contentView.findViewById(R.id.pop_All).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_submit).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_financial).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_manage).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_back).setOnClickListener(menuItemOnClickListener);
        return contentView;
    }

    public void getdate() {
        Dates.getDialog(ChecknoticeMessagelistActivity.this,"请求数据中..");
        OkGo.post(Requests.GET_MY_NOTICE_DATA_APP)
                .params("rectificationOrgid", id)
                .params("status", status)
                .params("page", page)
                .params("size", 20)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                if (page == 1) {
                                    mData.clear();
                                }
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json = jsonArray.getJSONObject(i);
                                        //标题
                                        String partDetails;
                                        try {
                                            partDetails = json.getString("partDetails");
                                        } catch (JSONException e) {
                                            partDetails = "";
                                        }
                                        //检查组织
                                        String checkOrgName = json.getString("checkOrgName");
                                        //责任人
                                        String sendPersonName = json.getString("checkPersonName");
                                        //所属标段
                                        String rectificationOrgName = json.getString("rectificationPartName");
                                        //更新时间
                                        String updateDate;
                                        try {
                                            updateDate = json.getString("updateDate");
                                        } catch (JSONException e) {
                                            updateDate = json.getString("checkDate");
                                        }

                                        updateDate = updateDate.substring(0, 10);
                                        //积分
                                        String standardDelScore;
                                        try {
                                            standardDelScore = json.getString("standardDelScore");
                                        } catch (JSONException e) {
                                            standardDelScore = "";
                                        }
                                        //检查类别
                                        String standardDelName = json.getString("standardTypeName");
                                        //id
                                        String noticeId = json.getString("noticeId");
                                        //状态
                                        String status = json.getString("status");
                                        String motionNode;
                                        try {
                                            motionNode = json.getString("motionNode");
                                        } catch (JSONException e) {
                                            motionNode = "";
                                        }
                                        String sdealId = json.getString("sdealId");
                                        String verificationId;
                                        try {
                                            verificationId = json.getString("verificationId");
                                        } catch (JSONException e) {
                                            verificationId = "";
                                        }
                                        //是否回复
                                        Boolean isDeal = json.getBoolean("isDeal");
                                        String checkPersonName = json.getString("rectificationPersonName");
                                        String rectificationDate = json.getString("rectificationDate");
                                        rectificationDate = rectificationDate.substring(0, 10);
                                        mData.add(new MyNoticeDataBean(partDetails, checkOrgName, sendPersonName,
                                                rectificationOrgName, updateDate, standardDelScore, standardDelName, noticeId, status, motionNode, checkPersonName, rectificationDate, sdealId, verificationId, isDeal));
                                    }
                                }
                            } else {
                                ToastUtils.showLongToast(jsonObject.getString("msg"));
                            }
                            if (mData.size() > 0) {
                                back_not_null.setVisibility(View.GONE);
                            } else {
                                back_not_null.setVisibility(View.VISIBLE);
                            }
                            mAdapter.getData(mData);
                            Dates.disDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Dates.disDialog();
                    }
                });
    }

    //下发成功后刷新
    @Override
    public void taskCallback() {
        page = 1;
        getdate();
    }

    /**
     * popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }

    //界面亮度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    public void detele(final int pos) {
        OkGo.post(Requests.checkdeleteDateApp)
                .params("noticeId", mData.get(pos).getNoticeId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.getInt("ret") == 0) {
                                mData.remove(pos);
                                mAdapter.getData(mData);
                            } else {
                                ToastUtils.showShortToast(jsonObject.getString("mesg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void status(String status, String ids, int pos) {
        if ("未下发".equals(status)) {
            Intent intent = new Intent(mContext, CheckRectificationActivity.class);
            intent.putExtra("id", mData.get(pos).getNoticeId());
            startActivity(intent);
        } else {
            Intent intent = new Intent(mContext, IssuedTaskDetailsActivity.class);
            intent.putExtra("id", mData.get(pos).getNoticeId());
            intent.putExtra("verificationId", mData.get(pos).getVerificationId());
            intent.putExtra("title", titleView.getText().toString());
            intent.putExtra("sdealId", mData.get(pos).getSdealId());
            intent.putExtra("isDeal", mData.get(pos).isDeal());
            startActivity(intent);
        }
    }
}
