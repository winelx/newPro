package com.example.administrator.newsdf.pzgc.activity.notice.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.newsdf.R;

import com.example.administrator.newsdf.pzgc.activity.notice.NoticeUtils;
import com.example.administrator.newsdf.pzgc.bean.Proclamation;
import com.example.administrator.newsdf.pzgc.utils.EmptyUtils;
import com.example.administrator.newsdf.pzgc.utils.LazyloadFragment;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.navigation.Navigation;

/**
 * @author： lx
 * @创建时间： 2019/5/29 0029 9:56
 * @说明： 通知公告
 **/
public class MessageNoticeFragment extends LazyloadFragment implements View.OnClickListener {
    private TextView com_title;
    private RecyclerView recycler;
    private SmartRefreshLayout reshlayout;

    private ArrayList<Proclamation> list;

    private EmptyUtils emptyUtils;
    private NoticeUtils noticeUtils;
    private Adapter adapter;

    private int page = 1;

    @Override
    protected int setContentView() {
        return R.layout.fragment_messagenotice;
    }

    @Override
    protected void init() {
        findId();
        list = new ArrayList<>();
        com_title.setText("通知公告");
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        noticeUtils = new NoticeUtils();
        emptyUtils = new EmptyUtils(getContext());
        adapter = new Adapter(R.layout.messagenotice_item, list);
        recycler.setAdapter(adapter);
        adapter.setEmptyView(emptyUtils.init());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Navigation.findNavController(view).navigate(R.id.to_noticedetailsfragment);
            }
        });
        /**
         *   下拉刷新
         */
        reshlayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                reqeuse();
                //关闭刷新
                refreshlayout.finishRefresh();
            }
        });
        //上拉加载
        reshlayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                reqeuse();
                //关闭上拉加载
                refreshlayout.finishLoadmore();
            }
        });
        reqeuse();
    }

    private void findId() {
        com_title = rootView.findViewById(R.id.com_title);
        reshlayout = rootView.findViewById(R.id.reshlayout);
        recycler = rootView.findViewById(R.id.recycler);
        rootView.findViewById(R.id.com_back).setOnClickListener(this);
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

    private class Adapter extends BaseQuickAdapter<Proclamation, BaseViewHolder> {

        public Adapter(int layoutResId, @Nullable List<Proclamation> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Proclamation item) {
            helper.setText(R.id.content, item.getContent());
            //组织
            helper.setText(R.id.orgnanme, item.getOrgName() + "dsfadsafdsa");
            //创建人
            helper.setText(R.id.username, item.getCreateName());
            //时间
            helper.setText(R.id.timedata, item.getPublishDate().substring(0, 10));
        }
    }

    /*网络请求*/
    private void reqeuse() {
        Map<String, String> map = new HashMap<>();
        map.put("nowPage", page + "");
        noticeUtils.getdata(map, new NoticeUtils.CallBack() {
            @Override
            public void onsuccess(List<Proclamation> data) {
                //如果加载第一页，清除数据
                if (page == 1) {
                    list.clear();
                }
                list.addAll(data);
                adapter.setNewData(list);
            }

            @Override
            public void onerror(String str) {
                ToastUtils.showsnackbar(com_title, str);
                page--;
            }
        });
    }
}
