package com.example.zcjlmodule.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zcjlmodule.adapter.WorkFragmentAdapter;
import com.example.zcjlmodule.bean.WorkZcBean;
import com.example.zcjlmodule.bean.WorkItemZcBean;
import com.example.zcmodule.R;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.base.BaseFragment;

/**
 * description:  征拆首页的工作界面
 *  这个界面使用了recyclerview的嵌套使用，使用了两层
 *  （承载界面HomeZcActivity）
 * @author lx
 *         date: 2018/10/10 0010 下午 2:59
 *         update: 2018/10/10 0010
 *         version:
 */
public class WorkFragmentZc extends BaseFragment {
    private View rootView;
    private Context mContext;
    private RecyclerView mRecycler;
    private WorkFragmentAdapter adapter;
    private ArrayList<WorkZcBean> mData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //如果view为空就加载界面，否则就不加载，避免切换界面重新加载界面,减少界面的绘制，降低内存消耗
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_work_zc, null);
            initdata();
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    //初始化数据
    private void initdata() {
        mContext = getActivity();
        mData = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            ArrayList<WorkItemZcBean> item = new ArrayList<>();
            if (i == 0) {
                item.add(new WorkItemZcBean(R.mipmap.zc_fragment_work_payment, "支付清册"));
                item.add(new WorkItemZcBean(R.mipmap.zc_fragment_work_original, "原始勘丈表"));
                item.add(new WorkItemZcBean(R.mipmap.zc_fragment_work_standard, "征拆标准"));
                item.add(new WorkItemZcBean(R.mipmap.zc_fragment_work_applysingle, "资金申请单"));
                item.add(new WorkItemZcBean(R.mipmap.zc_fragment_work_capitalapprova, "资金审批单"));
                mData.add(new WorkZcBean("征拆协调", item));
            } else {
                item.add(new WorkItemZcBean(R.mipmap.zc_fragment_work_meteringapproval, "计量单据审核"));
                mData.add(new WorkZcBean("计量支付", item));
            }
        }
        TextView toolbarTitle = rootView.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("工作");
        mRecycler = rootView.findViewById(R.id.fragment_work_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycler.setAdapter(adapter = new WorkFragmentAdapter(mContext, mData));
    }

}
