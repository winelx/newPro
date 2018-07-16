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

import com.example.administrator.newsdf.App;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.bean.Auditbean;
import com.example.administrator.newsdf.pzgc.bean.Audittitlebean;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.LogUtil;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.ScreenUtil;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.joanzapata.iconify.widget.IconTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * description: 审核列表界面
 *
 * @author lx
 *         date: 2018/7/2 0002 下午 1:47
 *         update: 2018/7/2 0002
 *         version:
 */

public class AuditActivity extends AppCompatActivity {
    private Context mContext;
    private List<Auditbean> mData;
    private ArrayList<Audittitlebean> title;
    private ListView aduit_list;
    private SettingAdapter<Audittitlebean> adapter;
    private PopupWindow mPopupWindow;
    private IconTextView aduit_back;
    private LinearLayout audit_meunl;
    private float ste;
    private Integer integer = 1;
    private String nodeID = "3b13c30f83684f1cad2defbb579f02d7";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit);
        mContext = AuditActivity.this;
//        Intent intent = getIntent();
//        nodeID = intent.getExtras().getString("nodeId");
        ste = ScreenUtil.getDensity(App.getInstance());
        title = new ArrayList<>();
        audit_meunl = (LinearLayout) findViewById(R.id.audit_meun);
        aduit_back = (IconTextView) findViewById(R.id.aduit_back);
        aduit_list = (ListView) findViewById(R.id.aduit_list);
        adapter = new SettingAdapter<Audittitlebean>(title, R.layout.item_audit_elv) {
            @Override
            public void bindView(ViewHolder holder, Audittitlebean obj) {
                holder.setText(R.id.todaytime, obj.getCnDay());
                holder.setText(R.id.complete, "完成率:" + obj.getRatio());
                holder.setText(R.id.unfinished, "未审核:" + obj.getTip());
            }
        };
        aduit_list.setAdapter(adapter);
        //listview 点击事件
        aduit_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AuditActivity.this, AuditrecordActivity.class);
                intent.putExtra("date", title.get(position).getDate());
                intent.putExtra("nodeId", nodeID);
                intent.putExtra("day", title.get(position).getCnDay());
                intent.putExtra("ratio", title.get(position).getRatio());
                intent.putExtra("tip", title.get(position).getTip());

                startActivity(intent);
            }
        });
        //功能按钮
        audit_meunl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeunPop();
            }
        });
        //返回按钮
        aduit_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getData(integer);
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
        mPopupWindow.showAsDropDown(audit_meunl);
        Utils.backgroundAlpha(0.5f, AuditActivity.this);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());
    }

    //设置pop的点击事件
    private View getPopupWindowContentView() {
        // 一个自定义的布局，作为显示的内容
        // 布局ID
        int layoutId = R.layout.audit_popwindow;
        View contentView = LayoutInflater.from(this).inflate(layoutId, null);
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.audit_all:
                        startActivity(new Intent(AuditActivity.this, ReportActivity.class));
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
        contentView.findViewById(R.id.audit_all).setOnClickListener(menuItemOnClickListener);
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

    public void getData(Integer integer) {
        OkGo.post(Requests.TASKDATELIST)
                .params("page", integer)
                .params("size", "10")
                .params("id", nodeID)
                .params("day", "day")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LogUtil.i("ss", s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json = jsonArray.getJSONObject(i);
                                String cnDay = json.getString("cnDay");
                                String day = json.getString("date");
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
                            adapter.getData(title);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
