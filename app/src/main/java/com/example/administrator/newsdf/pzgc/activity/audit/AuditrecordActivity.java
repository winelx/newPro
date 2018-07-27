package com.example.administrator.newsdf.pzgc.activity.audit;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.newsdf.App;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.bean.AuditrecordBean;
import com.example.administrator.newsdf.pzgc.callback.AuditrecordCallback;
import com.example.administrator.newsdf.pzgc.callback.AuditrecordCallbackUtils;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.ScreenUtil;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;


/**
 * description:审核任务列表
 *
 * @author lx
 *         date: 2018/7/5 0005 上午 9:43
 *         update: 2018/7/5 0005
 *         version:
 */

public class AuditrecordActivity extends AppCompatActivity implements View.OnClickListener, AuditrecordCallback {
    private SettingAdapter<AuditrecordBean> mAdapter;
    private ArrayList<AuditrecordBean> mData;
    private Context mContext;
    private LinearLayout recordMeun;
    private PopupWindow mPopupWindow;
    private int page = 1;
    private float ste;
    private String orgId, title;
    private String date;
    private int Success = 1;
    private SmartRefreshLayout smartRefreshLayout;
    private String one = "1", two = "2";
    //上拉加载数据判断
    private int status = 1;
    //待审核数
    String tip;
    TextView unfinished;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditrecord);
        mContext = this;
        AuditrecordCallbackUtils.setCallBack(this);
        mData = new ArrayList<>();
        Intent intent = getIntent();
        orgId = intent.getExtras().getString("orgId");
        date = intent.getExtras().getString("date");
        title = intent.getExtras().getString("title");
        String day = intent.getExtras().getString("day");
        String ratio = intent.getExtras().getString("ratio");
        tip = intent.getExtras().getString("tip");
        ste = ScreenUtil.getDensity(App.getInstance());
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        //关闭下拉刷新
        smartRefreshLayout.setEnableRefresh(false);
        //仿ios越界
        smartRefreshLayout.setEnableOverScrollBounce(true);
        //是否在加载的时候禁止列表的操作
        smartRefreshLayout.setDisableContentWhenLoading(false);
        findViewById(R.id.record_back).setOnClickListener(this);
        findViewById(R.id.record_title).setOnClickListener(this);
        recordMeun = (LinearLayout) findViewById(R.id.record_meun);
        ListView auditrecordList = (ListView) findViewById(R.id.auditrecord_list);
        TextView todaytime = (TextView) findViewById(R.id.todaytime);
        TextView record_title = (TextView) findViewById(R.id.record_title);
        record_title.setText(title);
        TextView complete = (TextView) findViewById(R.id.complete);
        complete.setText("完成率:" + ratio);
        unfinished = (TextView) findViewById(R.id.unfinished);
        if (tip.isEmpty()) {
            unfinished.setText("未审核:" + "0");
        } else {
            unfinished.setText("未审核:" + tip);
        }

        todaytime.setText(day);
        recordMeun.setOnClickListener(this);
        mAdapter = new SettingAdapter<AuditrecordBean>(mData, R.layout.auditrecord_activity_item) {
            @Override
            public void bindView(ViewHolder holder, AuditrecordBean obj) {
                holder.setText(R.id.record_title, obj.getTitle());
                holder.setText(R.id.record_path, obj.getWbspath());
                holder.setText(mContext, R.id.record_user, "时间:" + obj.getUser(), 3, R.color.black);
                String str = obj.getStatus();
                if ("1".equals(str)) {
                    //通过
                    holder.setText(mContext, R.id.record_status, "当前状态:通过", 4, R.color.finish_green);
                } else if ("2".equals(str)) {
                    //打回
                    holder.setText(mContext, R.id.record_status, "当前状态:打回", 4, R.color.red);
                } else {
                    //待审核
                    holder.setText(mContext, R.id.record_status, "当前状态:待审核", 4, R.color.yellow);
                }
            }
        };

        auditrecordList.setAdapter(mAdapter);
        auditrecordList.setEmptyView(findViewById(R.id.nullposion));
        auditrecordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AuditrecordActivity.this, AuditdetailsActivity.class);
                intent.putExtra("TaskId", mData.get(position).getId());
                intent.putExtra("status", mData.get(position).getStatus());
                startActivity(intent);
            }
        });
        getData();
        //上拉加载
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                if (status == 1) {
                    getData();
                } else {
                    http();
                }
                refreshlayout.finishLoadmore(800);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.record_back:
                finish();
                break;
            case R.id.record_meun:
                MeunPop();
                break;
            default:
                break;
        }
    }

    //弹出框
    private void MeunPop() {
        View contentView = getPopupWindowContentView();
        mPopupWindow = new PopupWindow(contentView,
                Dates.withFontSize(ste) + 20, Dates.higtFontSize(ste), true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置好参数之后再show
        // 默认在mButton2的左下角显示
        mPopupWindow.showAsDropDown(recordMeun);
        Utils.backgroundAlpha(0.5f, AuditrecordActivity.this);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());
    }

    //设置pop的点击事件
    private View getPopupWindowContentView() {
        // 一个自定义的布局，作为显示的内容
        // 布局ID
        int layoutId = R.layout.record_popwindow;
        View contentView = LayoutInflater.from(this).inflate(layoutId, null);
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dates.getDialog(AuditrecordActivity.this, "请求数据中...");
                switch (v.getId()) {
                    case R.id.audit_audit:
                        page = 1;
                        mData.clear();
                        status = 1;
                        getData();
                        break;
                    case R.id.audit_noaudit:
                        page = 1;
                        mData.clear();
                        Success = 1;
                        status = 2;
                        http();
                        break;
                    case R.id.audit_statistical:
                        page = 1;
                        Success = 2;
                        status = 3;
                        mData.clear();
                        http();
                        break;
                    default:
                        break;
                }
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        };
        contentView.findViewById(R.id.audit_audit).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.audit_noaudit).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.audit_statistical).setOnClickListener(menuItemOnClickListener);
        return contentView;
    }

    @Override
    public void updata() {
        if (!tip.isEmpty()) {
            Integer number = Integer.decode(tip);
            if (number > 0) {
                number = number - 1;
                unfinished.setText("未审核:" + number);
                tip = number + "";
            } else {
                unfinished.setText("未审核:" + 0);
            }
        }

        page = 1;
        mData.clear();
        getData();
    }

    /**
     * popWin关闭的事件，主要是为了将背景透明度改回来
     */
    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            Utils.backgroundAlpha(1f, AuditrecordActivity.this);
        }
    }

    public void getData() {
        OkGo.post(Requests.GET_TASK_LIST)
                .params("orgId", orgId)
                .params("day", date)
                .params("page", page)
                .params("status", 2)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    String name = json.getString("name");
                                    String id = json.getString("id");
                                    String str = json.getString("appWbsPath");
                                    String updateDate = json.getString("updateDate");
                                    updateDate = updateDate.substring(10, 16);
                                    String status;
                                    try {
                                        status = json.getString("pass");
                                    } catch (JSONException e) {
                                        status = "";
                                    }
                                    mData.add(new AuditrecordBean(id, name, str, updateDate, status));
                                }
                            }
                            Dates.disDialog();
                            mAdapter.getData(mData);
                            smartRefreshLayout.finishLoadmore();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void http() {
        OkGo.post(Requests.GET_AUDIT_TASK_LIST)
                .params("orgId", orgId)
                .params("day", date)
                .params("page", page)
                .params("status", Success)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json = jsonArray.getJSONObject(i);
                                String name = json.getString("detectionName");
                                String appWbsPath = json.getString("appWbsPath");
                                String status = json.getString("pass");
                                String updateDate = json.getString("updateDate");
                                updateDate = updateDate.substring(10, 16);
                                String id = json.getString("id");
                                mData.add(new AuditrecordBean(id, name, appWbsPath, updateDate, status));
                            }
                            mAdapter.getData(mData);
                            smartRefreshLayout.finishLoadmore();
                            Dates.disDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        //传入false表示刷新失败
        smartRefreshLayout.finishRefresh(true);
    }


}
