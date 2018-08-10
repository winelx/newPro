package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.newsdf.App;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.ScreenUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/8 0008.
 */

/**
 * description: 检查通知模块通知列表
 *
 * @author lx
 *         date: 2018/8/8 0008 下午 4:03
 *         update: 2018/8/8 0008
 *         version:
 */
public class ChecknoticeMessagelistActivity extends AppCompatActivity implements View.OnClickListener {
    private SettingAdapter adapter;
    private ListView listView;
    ArrayList<String> mData;
    private Context mContext;
    private TextView titleView;
    private ImageView checklistmeunimage;
    private PopupWindow mPopupWindow;
    private float resolution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectmb_tree);
        resolution = ScreenUtil.getDensity(App.getInstance());
        checklistmeunimage = (ImageView) findViewById(R.id.checklistmeunimage);
        checklistmeunimage.setBackgroundResource(R.mipmap.meun);
        checklistmeunimage.setVisibility(View.VISIBLE);
        listView = (ListView) findViewById(R.id.maber_tree);
        titleView = (TextView) findViewById(R.id.titleView);
        titleView.setText("通知管理");
        listView.setDividerHeight(0);
        mContext = ChecknoticeMessagelistActivity.this;
        mData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mData.add("" + i);
        }
        adapter = new SettingAdapter<String>(mData, R.layout.check_notice_message) {
            @Override
            public void bindView(ViewHolder holder, String obj) {
                holder.setText(R.id.management_title, obj);
            }
        };
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.nullposion));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext, IssuedTaskDetailsActivity.class));
            }
        });
        findViewById(R.id.checklistback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        checklistmeunimage.setOnClickListener(this);
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
                switch (v.getId()) {
                    case R.id.pop_All:
                        ToastUtils.showShortToastCenter("已提交");
                        break;
                    case R.id.pop_financial:
                        ToastUtils.showShortToastCenter("未提交");
                        break;
                    case R.id.pop_manage:
                        ToastUtils.showShortToastCenter("为验证");
                        break;
                    case R.id.pop_back:
                        ToastUtils.showShortToastCenter("打回");
                        break;
                    default:
                        break;
                }
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        };

        contentView.findViewById(R.id.pop_All).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_financial).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_manage).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_back).setOnClickListener(menuItemOnClickListener);
        return contentView;
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
}
