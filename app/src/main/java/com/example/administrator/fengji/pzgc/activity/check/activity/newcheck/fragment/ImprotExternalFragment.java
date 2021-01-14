package com.example.administrator.fengji.pzgc.activity.check.activity.newcheck.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.activity.check.activity.newcheck.bean.ImprotBean;
import com.example.administrator.fengji.pzgc.activity.check.activity.newcheck.utils.ExternalApi;
import com.example.administrator.fengji.pzgc.bean.Audio;
import com.example.administrator.fengji.pzgc.utils.Dates;
import com.example.administrator.fengji.pzgc.utils.EmptyUtils;
import com.example.administrator.fengji.pzgc.utils.LazyloadFragment;
import com.example.administrator.fengji.pzgc.utils.ToastUtils;
import com.example.baselibrary.utils.network.NetWork;
import com.example.baselibrary.utils.rx.LiveDataBus;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 说明：
 * 创建时间： 2020/7/14 0014 9:11
 *
 * @author winelx
 */
public class ImprotExternalFragment extends LazyloadFragment implements View.OnClickListener {
    private TextView com_title;
    private SmartRefreshLayout refreshlayout;
    private RecyclerView recycler_list;
    private int page = 1;
    private String orgId;
    private RecyclerViewAdapter adapter;
    private ArrayList<ImprotBean> mData;
    private EmptyUtils emptyUtils;

    @Override
    protected int setContentView() {
        return R.layout.activity_chaged_importchageditem;
    }

    @Override
    protected void init() {
        emptyUtils = new EmptyUtils(mContext);
        Intent intent = getActivity().getIntent();
        orgId = intent.getStringExtra("orgid");
        mData = new ArrayList<>();
        findViewById(R.id.com_back).setOnClickListener(this);
        com_title = (TextView) findViewById(R.id.com_title);
        com_title.setText("导入整改项");
        refreshlayout = (SmartRefreshLayout) findViewById(R.id.refreshlayout);
        //是否启用下拉刷新功能
        refreshlayout.setEnableRefresh(true);
        //是否启用上拉加载功能
        refreshlayout.setEnableLoadmore(true);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshlayout.setEnableOverScrollDrag(false);
        //是否在列表不满一页时候开启上拉加载功能
        refreshlayout.setEnableLoadmoreWhenContentNotFull(false);
        /* 下拉刷新*/
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                request();
                //关闭刷新
                refreshlayout.finishRefresh();
            }
        });
        /* 上拉加载*/
        refreshlayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                request();
                //关闭上拉加载
                refreshlayout.finishLoadmore();
            }
        });
        recycler_list = (RecyclerView) findViewById(R.id.recycler_list);
        recycler_list.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new RecyclerViewAdapter(R.layout.adapter_item_improtchageditem, new ArrayList<>());
        recycler_list.setAdapter(adapter);
        adapter.setEmptyView(emptyUtils.init());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ImprotBean bean = (ImprotBean) adapter.getData().get(position);
                LiveDataBus.get().with("ex_viewpager").setValue(1);
                LiveDataBus.get().with("ex_check_item").setValue(new Audio(bean.getName(),bean.getId(),bean.getWbsTaskTypeName()));
            }
        });
        request();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                getActivity().finish();
                break;
            default:
                break;
        }
    }

    public void request() {
        Map<String, String> map = new HashMap<>();
        map.put("orgId", orgId);
        map.put("page", page + "");
        map.put("rows", 30 + "");
        NetWork.getHttp(ExternalApi.GETCHOOSESAFEDATABYAPP, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        List<ImprotBean> list = com.alibaba.fastjson.JSONArray.parseArray(data.toString(), ImprotBean.class);
                        if (page == 1) {
                            mData.clear();
                        }
                        mData.addAll(list);
                        adapter.setNewData(mData);
                        if (mData.size() == 0) {
                            emptyUtils.noData("暂无数据");
                        }
                    } else {
                        ToastUtils.showShortToast(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                ToastUtils.showShortToast("请求失败");
            }
        });
    }

    public class RecyclerViewAdapter extends BaseQuickAdapter<ImprotBean, BaseViewHolder> {

        public RecyclerViewAdapter(int layoutResId, @Nullable List<ImprotBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ImprotBean item) {
            helper.setText(R.id.import_title, item.getWbsTaskTypeName());
//         helper.setText(R.id.import_content, item.getContent());
            helper.setText(R.id.import_checkpeople, "检查人：" + item.getCheckPersonName());
            helper.setText(R.id.import_checkdata, "检查日期：" + item.getCheckDate().substring(0, 10));
            helper.setText(R.id.import_checkorg, "检查组织：" + item.getCheckOrgName());
//            //总分
            helper.setText(R.id.import_checkscore, TextUtils.isEmpty(item.getTotalSocre()) ? "" : Dates.setText(mContext,"总分：" + item.getTotalSocre(),3,R.color.red));
            helper.setText(R.id.import_status, "外业检查");
            helper.setVisible(R.id.import_status, true);

        }
    }
}
