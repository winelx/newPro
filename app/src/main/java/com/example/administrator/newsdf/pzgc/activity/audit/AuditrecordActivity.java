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
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.ScreenUtil;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

/**
 * description:
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
    private TextView record_title;
    private LinearLayout record_meun;
    private PopupWindow mPopupWindow;
    private float ste;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditrecord);
        mData = new ArrayList<>();
        ste = ScreenUtil.getDensity(App.getInstance());
        mContext = this;
        for (int i = 0; i < 100; i++) {
            mData.add(new AuditrecordBean("", "", "", "", ""));
        }
        record_back = (IconTextView) findViewById(R.id.record_back);
        record_title = (TextView) findViewById(R.id.record_title);
        record_meun = (LinearLayout) findViewById(R.id.record_meun);
        auditrecord_list = (ListView) findViewById(R.id.auditrecord_list);
        record_back.setOnClickListener(this);
        record_meun.setOnClickListener(this);
        mAdapter = new SettingAdapter<AuditrecordBean>(mData, R.layout.auditrecord_activity_item) {
            @Override
            public void bindView(ViewHolder holder, AuditrecordBean obj) {
                holder.setText(R.id.record_title, "人行道设施");
                holder.setText(R.id.record_path, "人行道设施人行道设施人行道设施人行道设施人行道设施");
                holder.setText(mContext, R.id.record_user, "上一节点审核人:" + "111", 7, R.color.black);
                holder.setText(mContext, R.id.record_status, "当前状态:" + "111", 4, R.color.finish_green);
            }
        };
        auditrecord_list.setAdapter(mAdapter);
        auditrecord_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AuditrecordActivity.this, AuditdetailsActivity.class);
                intent.putExtra("TaskId", " 367d63c46e9f409692f9e0d395efa1ca");
                startActivity(intent);
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
                switch (v.getId()) {
                    case R.id.audit_all:
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
            Utils.backgroundAlpha(1f, AuditrecordActivity.this);
        }
    }
}
