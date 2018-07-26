package com.example.administrator.newsdf.pzgc.fragment.homepage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.MyExpandableListAdapter;
import com.example.administrator.newsdf.pzgc.bean.Home_item;
import com.example.administrator.newsdf.pzgc.callback.CallBack;
import com.example.administrator.newsdf.pzgc.callback.CallBackUtils;
import com.example.administrator.newsdf.pzgc.callback.OgranCallback;
import com.example.administrator.newsdf.pzgc.callback.OgranCallbackUtils;
import com.example.administrator.newsdf.pzgc.fragment.presenter.AllmessagePer;
import com.example.administrator.newsdf.pzgc.fragment.view.UiAllMessageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;
import java.util.Map;

/**
 * description: 全部消息
 *
 * @author lx
 *         date: 2018/3/16 0016 下午 1:45
 *         update: 2018/3/16 0016
 *         version:
 */
public class AllMessageFragment extends Fragment implements CallBack, OgranCallback, UiAllMessageView {
    private View rootView;
    private MyExpandableListAdapter mAdapter;
    private Context mContext;
    private SmartRefreshLayout refreshLayout;
    private ExpandableListView expandable;
    private View.OnClickListener ivGoToChildClickListener;
    private  int groupPosition=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_allmessage, null);
            mContext = getActivity();
            refreshLayout = rootView.findViewById(R.id.SmartRefreshLayout);
            expandable = rootView.findViewById(R.id.expandable);
            //禁止上拉
            refreshLayout.setEnableLoadmore(false);
            //仿ios越界
            refreshLayout.setEnableOverScrollBounce(true);
        }

        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        initdata();
        Onclick();
        return rootView;
    }

    private void Onclick() {
        //网络请求
        ivGoToChildClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取被点击图标所在的group的索引
                Map<String, Object> map = (Map<String, Object>) v.getTag();
                groupPosition = (int) map.get("groupPosition");
                //判断分组是否展开
                boolean isExpand = expandable.isGroupExpanded(groupPosition);
                if (isExpand) {
                    //收缩
                    expandable.collapseGroup(groupPosition);
                } else {
                    //展开
                    expandable.expandGroup(groupPosition);
                }
            }
        };

        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Intent();
                refreshlayout.finishRefresh(1000);
            }
        });
    }

    private void initdata() {
        new AllmessagePer(this).getMode();
        //推送的接口回调（）
        CallBackUtils.setCallBack(this);
        //切换组织接口回调（OrganizationaActivity）
        OgranCallbackUtils.setCallBack(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //关闭刷新
        refreshLayout.finishRefresh(false);
    }

    //切换组织刷新界面
    @Override
    public void taskCallback() {
        Intent();
    }

    /**
     * 将数据放到适配器中
     * @param list
     * @param map
     */
    @Override
    public void setAdapter(List<String> list, Map<String, List<Home_item>> map) {
        mAdapter = new MyExpandableListAdapter(list, map, mContext,
                ivGoToChildClickListener);
        expandable.setAdapter(mAdapter);
        //默认展开第一个分组
        refreshLayout.finishRefresh(false);
    }
    //重新加载数据
    public void Intent() {
        new AllmessagePer(this).getMode();
    }

    @Override
    public void deleteTop() {
        Intent();
    }
}