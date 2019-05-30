package com.example.administrator.newsdf.pzgc.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.newsdf.R;


/**
 * @author lx
 * @Created by: 2019/1/17 0017.
 * @description: 界面加载无数据帮助类
 * @Activity：
 */

public class EmptyUtils {
    private View emptyView;
    private TextView content;
    private ProgressBar progressBar;
    private Context mContext;
    private ImageView nodata;


    public EmptyUtils(Context mContext) {
        this.mContext = mContext;
    }

    public EmptyUtils() {
    }

    /**
     * @内容: 初始化
     * @author lx
     * @date: 2019/1/17 0017 下午 2:17
     */
    public View init() {
        emptyView = ((Activity) mContext).getLayoutInflater().inflate(R.layout.layout_emptyview, null);
        progressBar = emptyView.findViewById(R.id.emptyview_bar);
        progressBar.setVisibility(View.VISIBLE);
        content = emptyView.findViewById(R.id.emptyview_text);
        nodata = emptyView.findViewById(R.id.nodata);
        return emptyView;
    }

    /**
     * @内容: 初始化
     * @author lx
     * @date: 2019/1/17 0017 下午 2:17
     */
    public View init(Context mContext) {
        emptyView = ((Activity) mContext).getLayoutInflater().inflate(R.layout.layout_emptyview, null);
        progressBar = emptyView.findViewById(R.id.emptyview_bar);
        progressBar.setVisibility(View.VISIBLE);
        content = emptyView.findViewById(R.id.emptyview_text);
        nodata = emptyView.findViewById(R.id.nodata);
        return emptyView;
    }

    /**
     * @内容: 简单设置提示
     * @author lx
     * @date: 2019/1/17 0017 下午 2:17
     */
    public void noData(String str) {
        progressBar.setVisibility(View.GONE);
        nodata.setVisibility(View.VISIBLE);
        content.setText(str);
    }

    /**
     * @内容: 支持点击回调
     * @author lx
     * @date: 2019/1/17 0017 下午 2:16
     */
    public void setError(final Callback callback) {
        progressBar.setVisibility(View.GONE);
        nodata.setVisibility(View.VISIBLE);
        final SpannableStringBuilder style = new SpannableStringBuilder();
        //设置文字
        style.append("数据获取失败,  \n点击重试...");
        //设置部分文字点击事件
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Reload();
                callback.callback();
            }
        };
        style.setSpan(clickableSpan, 9, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        content.setText(style);
        //设置部分文字颜色
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#0000FF"));
        style.setSpan(foregroundColorSpan, 9, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //配置给TextView
        content.setMovementMethod(LinkMovementMethod.getInstance());
        content.setText(style);
    }

    /**
     * @内容: 重新加载
     * @author lx
     * @date: 2019/1/17 0017 下午 2:17
     */
    public void Reload() {
        progressBar.setVisibility(View.VISIBLE);
        nodata.setVisibility(View.GONE);
        content.setText("加载数据中");
    }

    /**
     * @author lx
     * @内容: 回调接口
     * @date: 2019/1/17 0017 下午 2:17
     */
    public interface Callback {
        void callback();
    }

    public void setGone() {
        emptyView.setVisibility(View.GONE);
    }
}
