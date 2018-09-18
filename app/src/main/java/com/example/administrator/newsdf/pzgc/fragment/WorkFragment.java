package com.example.administrator.newsdf.pzgc.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.audit.ReportActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckReportActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckWebActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckdownMessageActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckmanagementActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckstandardListActivity;
import com.example.administrator.newsdf.pzgc.activity.home.WebActivity;
import com.example.administrator.newsdf.pzgc.activity.work.NotuploadActivity;
import com.example.administrator.newsdf.pzgc.activity.work.OrganiwbsActivity;
import com.example.administrator.newsdf.pzgc.activity.work.PushCheckActivity;
import com.example.administrator.newsdf.pzgc.activity.work.pchoose.PchooseActivity;
import com.example.administrator.newsdf.pzgc.callback.BrightCallBack;
import com.example.administrator.newsdf.pzgc.callback.BrightCallBackUtils;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;


/**
 * @author ：lx
 *         时间：2017/11/23 0023:下午 15:37
 *         说明：
 */
public class WorkFragment extends Fragment implements BrightCallBack, View.OnClickListener {
    private View rootView;
    private Context mContext;

    @Override

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//避免重复绘制界面
        if (rootView == null) {
            mContext = getActivity();
            rootView = inflater.inflate(R.layout.fragment_work, null);
            //初始化饼状图集合
            BrightCallBackUtils.setCallBack(this);
            //初始化控件Id
            findId();
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    private void findId() {
        //任务管理
        rootView.findViewById(R.id.task_management).setOnClickListener(this);
        //任务下发
        rootView.findViewById(R.id.push).setOnClickListener(this);
        //图册管理
        rootView.findViewById(R.id.pphotoadm).setOnClickListener(this);
        //离线图纸
        rootView.findViewById(R.id.uploade).setOnClickListener(this);
        //统计报表
        rootView.findViewById(R.id.statementsaudit).setOnClickListener(this);
        //监管检查
        rootView.findViewById(R.id.check_management).setOnClickListener(this);
        //整改通知
        rootView.findViewById(R.id.check_notice).setOnClickListener(this);
        //标段排名
        rootView.findViewById(R.id.fr_work_ranking).setOnClickListener(this);
        //检查标准
        rootView.findViewById(R.id.check_standard).setOnClickListener(this);
        //整改统计
        rootView.findViewById(R.id.fr_work_rectification).setOnClickListener(this);
        //任务统计
        rootView.findViewById(R.id.fr_work_statistical).setOnClickListener(this);
    }

    @Override
    public void bright() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.task_management:
                //任务管理
                startActivity(new Intent(mContext, OrganiwbsActivity.class));
                break;
            case R.id.push:
                //任务下发
                startActivity(new Intent(mContext, PushCheckActivity.class));
                break;
            case R.id.pphotoadm:
                //图册管理
                startActivity(new Intent(mContext, PchooseActivity.class));
                break;
            case R.id.uploade:
                //离线图纸
                startActivity(new Intent(mContext, NotuploadActivity.class));
                break;
            case R.id.statementsaudit:
                //统计报表
                Intent intent = new Intent(mContext, ReportActivity.class);
                intent.putExtra("orgId", SPUtils.getString(mContext, "orgId", ""));
                startActivity(intent);
                break;
            case R.id.check_management:
                //监管检查
                startActivity(new Intent(mContext, CheckmanagementActivity.class));
                break;
            case R.id.check_notice:
                //整改通知
                startActivity(new Intent(mContext, CheckdownMessageActivity.class));
                break;
            case R.id.fr_work_ranking:
                //标段排名
                startActivity(new Intent(mContext, CheckReportActivity.class));
                break;
            case R.id.check_standard:
                //检查标准
                startActivity(new Intent(mContext, CheckstandardListActivity.class));
                break;
            case R.id.fr_work_rectification:
                startActivity(new Intent(mContext,CheckWebActivity.class));
                //整改统计
                break;
            case R.id.fr_work_statistical:
                //任务统计
                break;
            default:
                break;
        }
    }
}