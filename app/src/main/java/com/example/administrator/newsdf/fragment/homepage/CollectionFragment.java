package com.example.administrator.newsdf.fragment.homepage;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.administrator.newsdf.Adapter.CollectionFrAdapter;
import com.example.administrator.newsdf.GreenDao.LoveDao;
import com.example.administrator.newsdf.GreenDao.Shop;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.bean.Home_item;
import com.example.administrator.newsdf.callback.HideCallback;
import com.example.administrator.newsdf.callback.HideCallbackUtils;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author lx
 *         Created by Administrator on 2018/5/16 0016.
 *         收藏
 */

public class CollectionFragment extends Fragment implements HideCallback {
    /**
     * 没有数据界面
     */
    private RelativeLayout home_frag_img;
    /**
     * 下拉控件
     */
    private SmartRefreshLayout refreshLayout;
    /**
     * 显示数据控件
     */
    private RecyclerView listView;
    //数据库数据
    private ArrayList<Home_item> mData = null;
    private CollectionFrAdapter mAdapter = null;
    private Context mContext;
    private ArrayList<String> Hidearray;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mContext = getActivity();
        refreshLayout = view.findViewById(R.id.SmartRefreshLayout);
        listView = view.findViewById(R.id.home_list);
        mData = new ArrayList<>();
        HideCallbackUtils.setCallBack(this);
        //设置布局管理器
        listView.setLayoutManager(new LinearLayoutManager(mContext));
        //设置适配器
        listView.setAdapter(mAdapter = new CollectionFrAdapter(mContext));
        //设置控制Item增删的动画
        listView.setItemAnimator(new DefaultItemAnimator());
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                //传入false表示刷新失败
                if (mAdapter.menuIsOpen()) {
                    mAdapter.closeMenu();
                }
                Okgo();
                refreshlayout.finishRefresh(1200);
            }
        });
        Okgo();
        return view;
    }

    public void Okgo() {
        putTop();
        OkGo.post(Requests.TaskMain)
                .params("isAll", "true")
                .execute(new StringCallback() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.contains("data")) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                int code = jsonObject.getInt("ret");
                                if (code == 0) {
                                    listView.setVisibility(View.VISIBLE);
                                    mData.clear();
                                }
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    String content = json.getString("content");
                                    String createTime = json.getString("createTime");
                                    if (createTime != null && !"".equals(createTime)) {
                                        createTime = createTime.substring(0, 10);
                                    } else {
                                        createTime = "";
                                    }
                                    String id = json.getString("id");
                                    String orgId = json.getString("orgId");
                                    String orgName = json.getString("orgName");
                                    String unfinish = json.getString("unfinish");
                                    //没有数据，那么所有数据都是未置顶的
                                    if (Hidearray.contains(id)){
                                        mData.add(new Home_item(content, createTime, id, orgId, orgName, unfinish, false));
                                    }
                                }
                                //将数据重新排序，将置顶的放在集合前面
                                //是否有数据
                                if (mData.size() != 0) {
                                    //刷新数据
                                    mAdapter.getData(mData);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.showShortToast("没有更多数据");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        ToastUtils.showShortToast("网络连接失败");

                    }
                });
    }

    private void putTop() {
        Hidearray = new ArrayList<>();
        List<Shop> list = new ArrayList<>();
        list = LoveDao.MineHide();
        for (int i = 0; i < list.size(); i++) {
            Hidearray.add(list.get(i).getCheckid());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter.menuIsOpen()) {
            mAdapter.closeMenu();
        }
    }

    @Override
    public void deleteTop() {
        Okgo();
    }
}
