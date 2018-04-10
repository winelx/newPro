package com.example.administrator.newsdf.activity.home.allmessage;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.utils.Dates;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import static com.example.administrator.newsdf.R.id.com_img;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class AllmessageActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 主界面的listview
     */
    private ListView listRecycler;
    /**
     * 主界面的下拉控件
     */
    private SmartRefreshLayout mSmartRefreshLayout;
    /**
     * 侧滑界面的下拉控件
     */
    private SmartRefreshLayout drawerLayoutSmart;

    /**
     * 加载页数
     */
    private static int pagers = 1;
    private static int pager = 1;

    /**
     * 不同状态数据
     */
    private static String dataState = "3";

    /**
     * Persen
     */
    private AllMessagePersen messagePersen;

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 弹出框
     */
    private PopupWindow mPopupWindow;

    /**
     * @param savedInstanceState
     */
    private LinearLayout popMeun;

    /**
     * @param savedInstanceState
     */
    private DrawerLayout drawerLayout;
    Dates mDates;
    String orgId, name, intent_back, wbsId = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allmessage);
        mContext = this;
        mDates = new Dates();
        Intent intent = getIntent();
        try {
            orgId = intent.getExtras().getString("orgId");
            intent_back = intent.getExtras().getString("back");
            name = intent.getExtras().getString("name");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        messagePersen = new AllMessagePersen();
        indata();
    }

    private void indata() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        popMeun = (LinearLayout) findViewById(com_img);
        popMeun.setOnClickListener(this);
        findViewById(R.id.fab).setOnClickListener(this);
        findViewById(R.id.com_back).setOnClickListener(this);
        //主界面的下拉控件
        mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.SmartRefreshLayout);
        //右滑界面
        drawerLayoutSmart = (SmartRefreshLayout) findViewById(R.id.drawerLayout_smart);
    }

    /**
     * 处理点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case com_img:
                /**
                 * 打开条件弹窗
                 */
                PopupWindowMeun(popMeun);
                break;
            case R.id.fab:
                /**
                 * 打开图册弹窗
                 */
                break;
            case R.id.com_back:
                /**
                 * 返回
                 */
                finish();
                break;
            default:
                break;
        }

    }

    private void PopupWindowMeun(View view) {
        View contentView = getPopupWindowContentView();
        mPopupWindow = new PopupWindow(contentView,
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置好参数之后再show
        // 默认在mButton2的左下角显示
        mPopupWindow.showAsDropDown(view);
        //屏幕亮度
        mDates.backgroundAlpha(0.5f, AllmessageActivity.this);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());
    }

    //设置pop的点击事件
    private View getPopupWindowContentView() {
        // 一个自定义的布局，作为显示的内容
        // 布局ID
        int layoutId = R.layout.popuplayout;
        View contentView = LayoutInflater.from(this).inflate(layoutId, null);
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.pop_computer:
                        //打开wbs树
                        drawerLayout.openDrawer(GravityCompat.START);
                        break;
                    case R.id.pop_All:
                        pagers = 1;
                        messagePersen.okHttp(mContext, orgId, wbsId, pagers, "3", null);
                        break;
                    case R.id.pop_financial:

                        break;
                    case R.id.pop_manage:

                        break;
                    default:
                        break;
                }
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }


            }
        };
        contentView.findViewById(R.id.pop_computer).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_All).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_financial).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.pop_manage).setOnClickListener(menuItemOnClickListener);
        return contentView;
    }

    /**
     * popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            mDates.backgroundAlpha(1f, AllmessageActivity.this);
        }
    }

}
