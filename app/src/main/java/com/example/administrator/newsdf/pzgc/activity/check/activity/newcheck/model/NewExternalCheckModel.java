package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.CheckNewBean;


import java.util.List;

public class NewExternalCheckModel {
    private TextView commit;
    private TextView meunButton;
    private NestedScrollView nestedScrollView;
    private String status;
    private Context mContext;
    private Activity activity;
    private EditText check_name;
    private LinearLayout checkType, check_time, project_type;


    public NewExternalCheckModel(TextView commit, TextView meunButton, NestedScrollView nestedScrollView,
                                 LinearLayout checkType, LinearLayout check_time,
                                 LinearLayout project_type, EditText check_name, String status) {
        this.commit = commit;
        this.meunButton = meunButton;
        this.nestedScrollView = nestedScrollView;
        this.status = status;
        this.checkType = checkType;
        this.check_time = check_time;
        this.project_type = project_type;
        this.check_name = check_name;

    }

    public void setContext(Activity activity) {
        this.activity = activity;
    }


    /**
     * 说明：编辑权限控制
     * 创建时间： 2020/7/2 0002 16:41
     *
     * @author winelx
     */
    public void setEditButton(boolean editButton) {
        //是否有编辑权限
        if (editButton) {
            //判断检查单是否已经确认完毕
            if ("6".equals(status)) {
                //如果确认完毕不能进行编辑
                meunButton.setVisibility(View.INVISIBLE);
            } else {
                //如果没有确认完毕可以进行编辑
                meunButton.setText("编辑");
                meunButton.setVisibility(View.VISIBLE);
            }
        } else {
            //不能进行编辑
            meunButton.setVisibility(View.INVISIBLE);
            setEnabled(false);
        }
    }

    /**
     * 说明：是否有提交权限
     * 创建时间： 2020/7/2 0002 16:41
     *
     * @author winelx
     */
    public void submitButton(Context mContext, boolean submit) {
        //是否有提交权限
        if (submit) {
            commit.setVisibility(View.VISIBLE);
            setMargin(nestedScrollView, 0, dip2px(mContext, 50), 0, dip2px(mContext, 50));
        } else {
            commit.setVisibility(View.GONE);
            setMargin(nestedScrollView, 0, dip2px(mContext, 50), 0, 0);
        }
    }

    /**
     * 说明：是否有确认权限
     * 创建时间： 2020/7/2 0002 16:41
     *
     * @author winelx
     */
    public void confirmButton(Context mContext, boolean confirm) {
        //是否有提交权限
        if (confirm) {
            commit.setVisibility(View.VISIBLE);
            setMargin(nestedScrollView, 0, dip2px(mContext, 50), 0, dip2px(mContext, 50));
        } else {
            commit.setVisibility(View.GONE);
            setMargin(nestedScrollView, 0, dip2px(mContext, 50), 0, 0);
        }
    }

    /**
     * 说明：按钮是否可以点击
     * 创建时间： 2020/7/2 0002 16:42
     *
     * @author winelx
     */
    public void setEnabled(boolean lean) {
        checkType.setEnabled(lean);
        check_time.setEnabled(lean);
        project_type.setEnabled(lean);
        check_name.setEnabled(lean);
    }

    /**
     * 说明：判断检查项是否检查完成
     * 创建时间： 2020/7/2 0002 17:31
     *
     * @author winelx
     */
    public boolean getCheckStatus(List<CheckNewBean.scorePane> list) {
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            CheckNewBean.scorePane bean = list.get(i);
            if (TextUtils.isEmpty(bean.getScore())) {
                count++;
            }
        }
        if (count > 0) {
            //没有检测完成
            return false;
        } else {
            //检测完成
            return true;
        }
    }

    public static void setMargin(View v, int left, int top, int right, int bottom) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            v.requestLayout();
        }
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);

    }
}
