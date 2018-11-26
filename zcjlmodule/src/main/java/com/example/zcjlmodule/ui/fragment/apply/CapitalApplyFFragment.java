package com.example.zcjlmodule.ui.fragment.apply;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zcjlmodule.R;
import com.example.zcjlmodule.adapter.CapitalApplyAdapter;
import com.example.zcjlmodule.bean.CapitalBean;
import com.example.zcjlmodule.callback.Callback;
import com.example.zcjlmodule.callback.PayDetailCallBackUtils;
import com.example.zcjlmodule.ui.activity.apply.ApplyActivityZc;
import com.example.zcjlmodule.ui.activity.mine.ChangeorganizeZcActivity;
import com.example.zcjlmodule.utils.fragment.ApplyFragmentUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Map;

import measure.jjxx.com.baselibrary.base.LazyloadFragment;
import measure.jjxx.com.baselibrary.utils.SPUtils;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;
import measure.jjxx.com.baselibrary.view.EmptyRecyclerView;

/**
 * @author lx
 * @Created by: 2018/11/22 0022.
 * @description:资金申请单未审核
 */

public class CapitalApplyFFragment extends LazyloadFragment implements View.OnClickListener, Callback {
    private RelativeLayout assemblyOrgSwitch;
    private TextView assemblyOrgname;
    private SmartRefreshLayout refreshLayout;
    private EmptyRecyclerView emptyRecyclerView;
    private View emptyView;
    private Context mContext;
    private String orgId, orgName;
    private CapitalApplyAdapter mAdapter;
    private ArrayList<CapitalBean> list;
    private ApplyFragmentUtils fragmentUtils;

    @Override
    protected int setContentView() {
        return R.layout.fragment_capitalapproval;
    }

    /**
     * description: 初始化界面数据
     *
     * @author lx
     * date: 2018/11/23 0023 上午 9:42
     */
    @Override
    protected void init() {
        findId();
        fragmentUtils = new ApplyFragmentUtils();
        mContext = getActivity();
        list = new ArrayList<>();
        orgId = SPUtils.getString(mContext, "orgId", "");
        orgName = SPUtils.getString(mContext, "orgName", "");
        assemblyOrgname.setText(orgName + "");
        PayDetailCallBackUtils.setCallBack(this);
        //是否启用下拉刷新功能
        refreshLayout.setEnableRefresh(true);
        //是否启用上拉加载功能
        refreshLayout.setEnableLoadmore(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableOverScrollDrag(true);
        //设置数据展示样式
        emptyRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //空数据提示
        emptyRecyclerView.setEmptyView(emptyView);
        //设置分割线
        emptyRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        emptyRecyclerView.setAdapter(mAdapter = new CapitalApplyAdapter(R.layout.adapter_capitalapporval, list));
        emptyView.setVisibility(View.GONE);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, ApplyActivityZc.class);
                intent.putExtra("status", "false");
                //申请单ID
                intent.putExtra("applyId", list.get(position).getId());
                //组织名称
                intent.putExtra("orgName", assemblyOrgname.getText().toString());
                //组织Id
                intent.putExtra("orgId", orgId);
                startActivity(intent);
            }
        });
        //  下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //标记 记录为下拉刷新，网络请求后关闭下拉
                httprequest();
            }
        });
    }

    /**
     * description: 初始化控件ID
     *
     * @author lx
     * date: 2018/11/23 0023 上午 9:40
     */
    private void findId() {
        TextView tab_time = rootView.findViewById(R.id.tab_time);
        tab_time.setText("申请时间");
        //空白数据界面
        emptyView = rootView.findViewById(R.id.recycler_empty);
        //机构组织父布局
        assemblyOrgSwitch = rootView.findViewById(R.id.assembly_org_switch);
        assemblyOrgSwitch.setOnClickListener(this);
        //组织名称
        assemblyOrgname = rootView.findViewById(R.id.assembly_orgname);
        //刷新加载控件
        refreshLayout = rootView.findViewById(R.id.assembly_recycler_smart);
        //数据展示列表
        emptyRecyclerView = rootView.findViewById(R.id.assembly_recyclerview);
        //recyclerview 加载空白布局
        emptyView = rootView.findViewById(R.id.recycler_empty);
    }

    /**
     * description: 界面可见
     * 界面懒加载，在这时进行网络请求，数据处理
     *
     * @author lx
     * date: 2018/11/23 0023 上午 9:42
     */
    @Override
    protected void lazyLoad() {
        httprequest();
    }

    private void httprequest() {
        fragmentUtils.applylists(orgId, 0, new ApplyFragmentUtils.OnClickListener() {
            @Override
            public void onsuccess(ArrayList<CapitalBean> data) {
                //将之前的清除
                list.clear();
                //判断加载刷新
                refreshLayout.finishRefresh();
                //将返回的数据加载到集合中
                list.addAll(data);
                //更新界面
                mAdapter.setNewData(list);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.assembly_org_switch:
                //切换组织
                Intent intent = new Intent(mContext, ChangeorganizeZcActivity.class);
                intent.putExtra("type", "true");
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 切换组织返回数据
     *
     * @param map
     */
    @Override
    public void callback(Map<String, Object> map) {
        assemblyOrgname.setText(map.get("orgname") + "");
        orgId = map.get("orgId") + "";
        httprequest();
    }


}
