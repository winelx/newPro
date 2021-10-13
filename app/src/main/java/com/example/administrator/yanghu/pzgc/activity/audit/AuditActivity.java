package com.example.administrator.yanghu.pzgc.activity.audit;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.yanghu.App;
import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.adapter.SettingAdapter;
import com.example.administrator.yanghu.pzgc.bean.Audittitlebean;
import com.example.baselibrary.utils.screen.ScreenUtil;
import com.example.baselibrary.base.BaseActivity;
import com.example.administrator.yanghu.pzgc.utils.Dates;
import com.example.baselibrary.utils.Requests;

import com.example.administrator.yanghu.pzgc.utils.Utils;
import com.joanzapata.iconify.widget.IconTextView;
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


public class AuditActivity extends BaseActivity {
    private ArrayList<Audittitlebean> title;
    private ListView aduitList;
    private SettingAdapter<Audittitlebean> adapter;
    private PopupWindow mPopupWindow;
    private IconTextView aduitBack;
    private LinearLayout auditMeunl;
    private float ste;
    private Integer integer = 1;
    private String orgId = "", name;
    private SmartRefreshLayout refreshLayout;
    private LinearLayout nulllauout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit);
        Intent intent = getIntent();
        orgId = intent.getExtras().getString("orgId");
        name = intent.getExtras().getString("name");
        ste = ScreenUtil.getDensity(App.getInstance());
        title = new ArrayList<>();
        TextView titles = (TextView) findViewById(R.id.title);
        nulllauout = (LinearLayout) findViewById(R.id.nulllauout);
        titles.setText(name);
        auditMeunl = (LinearLayout) findViewById(R.id.audit_meun);
        aduitBack = (IconTextView) findViewById(R.id.aduit_back);
        aduitList = (ListView) findViewById(R.id.aduit_list);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        adapter = new SettingAdapter<Audittitlebean>(title, R.layout.item_audit_elv) {
            @Override
            public void bindView(ViewHolder holder, Audittitlebean obj) {
                holder.setText(R.id.todaytime, obj.getCnDay());
                holder.setText(R.id.complete, "完成率:" + obj.getRatio());
                if (obj.getTip().isEmpty()) {
                    holder.setText(R.id.unfinished, "未审核:" + "0");
                } else {
                    Integer str = Integer.decode(obj.getTip());
                    if (str > 0) {
                        holder.setText(R.id.unfinished, "未审核:" + obj.getTip());
                    } else {
                        holder.setText(R.id.unfinished, "未审核:" + "0");
                    }
                }
            }
        };
        aduitList.setAdapter(adapter);
        //listview 点击事件
        aduitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AuditActivity.this, AuditrecordActivity.class);
                intent.putExtra("date", title.get(position).getDate());
                intent.putExtra("title", name);
                intent.putExtra("orgId", orgId);
                intent.putExtra("ratio", title.get(position).getRatio());
                intent.putExtra("tip", title.get(position).getTip());
                intent.putExtra("day", title.get(position).getCnDay());
                startActivity(intent);
            }
        });
        //功能按钮
        auditMeunl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeunPop();
            }
        });
        //返回按钮
        aduitBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //getData(integer,true);
        /**
         * 下拉
         */
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                integer = 1;
                getData(integer, true);
                refreshlayout.finishRefresh(800);
            }
        });
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                integer++;
                getData(integer, false);
                refreshlayout.finishLoadmore(800);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        integer = 1;
        getData(integer, true);
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
        mPopupWindow.showAsDropDown(auditMeunl);
        Utils.backgroundAlpha(0.5f, AuditActivity.this);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());
    }
    //设置pop的点击事件
    private View getPopupWindowContentView() {
        // 一个自定义的布局，作为显示的内容
        // 布局ID
        int layoutId = R.layout.audit_popwindow;
        final View contentView = LayoutInflater.from(this).inflate(layoutId, null);
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.audit_statistical:
                        Intent intent = new Intent(AuditActivity.this, ReportActivity.class);
                        intent.putExtra("orgId", orgId);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        };
        contentView.findViewById(R.id.audit_statistical).setOnClickListener(menuItemOnClickListener);
        return contentView;
    }

    /**
     * popWin关闭的事件，主要是为了将背景透明度改回来
     */
    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            Utils.backgroundAlpha(1f, AuditActivity.this);
        }
    }

    public void getData(final Integer integer, final boolean lean) {
        Dates.getDialog(AuditActivity.this, "请求数据中..");
        OkGo.post(Requests.TASKDATELIST)
                .params("page", integer)
                .params("size", "10")
                .params("orgId", orgId)
                .params("day", "day")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Dates.disDialog();
                        if (integer == 1) {
                            title.clear();
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json = jsonArray.getJSONObject(i);
                                String cnDay = json.getString("cnDay");
                                String day = json.getString("data");
                                String ratio = json.getString("ratio");
                                String tip;
                                try {
                                    tip = json.getString("tip");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    tip = "";
                                }
                                title.add(new Audittitlebean(cnDay, ratio + "%", tip, day));
                            }
                            if (title.size() > 0) {
                                nulllauout.setVisibility(View.GONE);
                            } else {
                                nulllauout.setVisibility(View.VISIBLE);
                            }
                            adapter.getData(title);

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
}
