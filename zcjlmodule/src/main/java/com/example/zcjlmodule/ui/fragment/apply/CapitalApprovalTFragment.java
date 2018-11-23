package com.example.zcjlmodule.ui.fragment.apply;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zcjlmodule.R;
import com.example.zcjlmodule.adapter.CapitalApprovalAdapter;
import com.example.zcjlmodule.utils.fragment.ApprovalFragmentUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import measure.jjxx.com.baselibrary.base.LazyloadFragment;
import measure.jjxx.com.baselibrary.view.EmptyRecyclerView;

/**
 * @author lx
 * @Created by: 2018/11/22 0022.
 * @description:
 */

public class CapitalApprovalTFragment extends LazyloadFragment {
    private LinearLayout assemblyOrgSwitch;
    private TextView assemblyOrgname;
    private SmartRefreshLayout refreshLayout;
    private EmptyRecyclerView emptyRecyclerView;
    private View emptyView;
    private Context mContext;
    private String orgId, orgName;
    private CapitalApprovalAdapter mAdapter;
    private ArrayList<String> list;
    private ApprovalFragmentUtils fragmentUtils;

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
        mContext = getActivity();
        list = new ArrayList<>();
        fragmentUtils = new ApprovalFragmentUtils();
        findId();
        //是否启用下拉刷新功能
        refreshLayout.setEnableRefresh(false);
        //是否启用上拉加载功能
        refreshLayout.setEnableLoadmore(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableOverScrollDrag(true);
        //设置空数据布局
        emptyRecyclerView.setEmptyView(emptyView);
        //设置数据展示样式
        emptyRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //设置分割线
        emptyRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        emptyRecyclerView.setAdapter(mAdapter = new CapitalApprovalAdapter(R.layout.adapter_capitalapporval, list));
        emptyView.setVisibility(View.GONE);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

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
//        //空白数据界面
        emptyView = rootView.findViewById(R.id.recycler_empty);
        //机构组织父布局
        assemblyOrgSwitch = rootView.findViewById(R.id.assembly_org_switch);
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
//        for (int i = 0; i < 2; i++) {
//            list.add("测试数据" + i);
//        }
//        Map<String, String> map = new HashMap<>();
//        fragmentUtils.agree("", "", map, new ApprovalFragmentUtils.OnClickListener() {
//            @Override
//            public void onsuccess(String s) {
//                mAdapter.setNewData(list);
//            }
//        });
        mAdapter.setNewData(list);
    }
}
