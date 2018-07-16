package com.example.administrator.newsdf.pzgc.activity.audit;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.newsdf.App;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.bean.AuditrecordBean;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.ScreenUtil;
import com.example.administrator.newsdf.pzgc.utils.Utils;
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

public class AuditrecordActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView auditrecord_list;
    private SettingAdapter mAdapter;
    private ArrayList<AuditrecordBean> mData;
    private Context mContext;
    private IconTextView record_back;
    private TextView record_title, todaytime, complete, unfinished;
    private LinearLayout record_meun;
    private PopupWindow mPopupWindow;
    private int page = 1, status = 2;
    private float ste;
    private String nodId, date, day, retio, tip;
    private SmartRefreshLayout smartRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditrecord);
        mData = new ArrayList<>();
        Intent intent = getIntent();
        nodId = intent.getExtras().getString("nodeId");
        date = intent.getExtras().getString("date");
        day = intent.getExtras().getString("day");
        retio = intent.getExtras().getString("ratio");
        tip = intent.getExtras().getString("tip");
        ste = ScreenUtil.getDensity(App.getInstance());
        mContext = this;
//        for (int i = 0; i < 2; i++) {
//            mData.add(new AuditrecordBean("", "", "", "", ""));
//        }
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        //关闭下拉刷新
        smartRefreshLayout.setEnableRefresh(false);
        //仿ios越界
        smartRefreshLayout.setEnableOverScrollBounce(true);
        //是否在加载的时候禁止列表的操作
        smartRefreshLayout.setDisableContentWhenLoading(false);
        record_back = (IconTextView) findViewById(R.id.record_back);
        record_title = (TextView) findViewById(R.id.record_title);
        record_meun = (LinearLayout) findViewById(R.id.record_meun);
        auditrecord_list = (ListView) findViewById(R.id.auditrecord_list);
        record_back.setOnClickListener(this);
        todaytime = (TextView) findViewById(R.id.todaytime);
        complete = (TextView) findViewById(R.id.complete);
        unfinished = (TextView) findViewById(R.id.unfinished);
        todaytime.setText(day);
        complete.setText(setText("完成率：" + retio, 3, R.color.finish_green));
        unfinished.setText(setText("未审核:" + tip, 3, R.color.yellow));
        record_meun.setOnClickListener(this);
        mAdapter = new SettingAdapter<AuditrecordBean>(mData, R.layout.auditrecord_activity_item) {
            @Override
            public void bindView(ViewHolder holder, AuditrecordBean obj) {
                holder.setText(R.id.record_title, obj.getTitle());
                holder.setText(R.id.record_path, obj.getWbspath());
                holder.setText(mContext, R.id.record_user, "上一节点审核人:" + obj.getUser(), 7, R.color.black);
                String str = obj.getStatus();
                if (str.equals("1")) {
                    holder.setText(mContext, R.id.record_status, "当前状态:通过", 4, R.color.finish_green);
                } else if (str.equals("2")) {
                    holder.setText(mContext, R.id.record_status, "当前状态:打回", 4, R.color.red);
                } else {
                    holder.setText(mContext, R.id.record_status, "当前状态:待审核", 4, R.color.yellow);
                }
            }
        };
        auditrecord_list.setAdapter(mAdapter);
        auditrecord_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AuditrecordActivity.this, AuditdetailsActivity.class);
                intent.putExtra("TaskId", mData.get(position).getId());
                intent.putExtra("status", mData.get(position).getStatus());
                startActivity(intent);
            }
        });
        getData(status);
        //上拉加载

        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getData(status);
//                //传入false表示刷新失败
//                refreshlayout.finishLoadMore(800);
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
        mPopupWindow.showAsDropDown(record_meun);
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
                    case R.id.audit_all:
                        status = 1;
                        page = 1;
                        mData.clear();
                        getData(status);
                        break;
                    case R.id.audit_audit:
                        page = 1;
                        status = 2;
                        mData.clear();
                        getData(status);
                        break;
                    case R.id.audit_noaudit:
                        page = 1;
                        status = 3;
                        mData.clear();
                        getData(status);
                        break;
                    case R.id.audit_statistical:
                        page = 1;
                        status = 4;
                        mData.clear();
                        getData(status);
                        break;
                    default:
                        break;
                }
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        };
        contentView.findViewById(R.id.audit_all).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.audit_audit).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.audit_noaudit).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.audit_statistical).setOnClickListener(menuItemOnClickListener);
        return contentView;
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

    public void getData(int stauts) {
        OkGo.post(Requests.GET_TASK_LIST)
                .params("id", nodId)
                .params("day", date)
                .params("page", page)
                .params("status", stauts)
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
                                    String str = json.getString("content");
                                    String leaderName = json.getString("leaderName");
                                    String status;
                                    try {
                                        status = json.getString("pass");
                                    } catch (JSONException e) {
                                        status = "";
                                    }
                                    mData.add(new AuditrecordBean(id, name, str, leaderName, status));
                                }

                            } else {
                                ToastUtils.showLongToast("数据为空");
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

    public SpannableString setText(String str, int num, int color) {

        SpannableString sp = new SpannableString(str);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(R.color.black)), 0,
                num,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(mContext.getResources()
                        .getColor(color)), num + 1,
                str.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }

    @Override
    protected void onStop() {
        super.onStop();
        //传入false表示刷新失败
        smartRefreshLayout.finishRefresh(false);
    }

}
