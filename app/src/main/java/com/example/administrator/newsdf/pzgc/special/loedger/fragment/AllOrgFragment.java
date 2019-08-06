package com.example.administrator.newsdf.pzgc.special.loedger.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.changed.ChagedListAllActivity;
import com.example.administrator.newsdf.pzgc.activity.changed.ChangedNewActivity;
import com.example.administrator.newsdf.pzgc.adapter.CheckListAdapter;
import com.example.administrator.newsdf.pzgc.bean.Home_item;
import com.example.administrator.newsdf.pzgc.special.loedger.activity.LoedgerlistActivity;
import com.example.administrator.newsdf.pzgc.utils.LazyloadFragment;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.baselibrary.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
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
 * @Author lx
 * @创建时间 2019/7/31 0031 10:43
 * @说明 全部组织
 **/
public class AllOrgFragment extends LazyloadFragment {
    private ArrayList<String> list;
    private Map<String, List<Home_item>> map;
    private List<Home_item> mData;
    private Context mContext;
    private ImageView checkNewadd;
    private ExpandableListView expandable;
    private CheckListAdapter mAdapter;

    @Override
    protected int setContentView() {
        return R.layout.checkdown_all;
    }

    @Override
    protected void init() {
        mContext = getActivity();
        list = new ArrayList<>();
        map = new HashMap<>();
        checkNewadd = rootView.findViewById(R.id.check_newadd);
        checkNewadd.setVisibility(View.GONE);
        expandable = rootView.findViewById(R.id.expandable);
        SmartRefreshLayout refreshLayout = rootView.findViewById(R.id.SmartRefreshLayout);
        refreshLayout.setEnableLoadmore(false);
        mAdapter = new CheckListAdapter(list, map, mContext);
        expandable.setAdapter(mAdapter);
        refreshLayout.finishRefresh(true);
        /**
         *   下拉刷新
         */
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //传入false表示刷新失败
                getdata();
                refreshlayout.finishRefresh(800);
            }
        });
        expandable.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String ids = map.get(list.get(groupPosition)).get(childPosition).getId();
                String orgname = map.get(list.get(groupPosition)).get(childPosition).getOrgname();
                Intent intent = new Intent(mContext, LoedgerlistActivity.class);
                intent.putExtra("orgid", ids);
                intent.putExtra("orgName", orgname);
                intent.putExtra("type", false);
                startActivity(intent);

                return false;
            }
        });

        getdata();
    }

    @Override
    protected void lazyLoad() {

    }

    public void getdata() {
        OkGo.get(Requests.GETORGINFOBYCNF)
                .params("isAll", true)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            list.clear();
                            map.clear();
                            mData = new ArrayList<Home_item>();
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    String count = json.getString("count");
                                    String id = json.getString("id");
                                    String name = json.getString("name");
                                    String org_type = json.getString("org_type");
                                    String parentId = json.getString("parentId");
                                    String parentName = json.getString("parentName");
                                    //将组织所属公司添加到集合
                                    if (!list.contains(parentName)) {
                                        list.add(parentName);
                                    }
                                    mData.add(new Home_item("", "", id, "", name, count, "", parentName, parentId, false));
                                }
                                //是否有数据
                                if (mData.size() != 0) {
                                    for (String str : list) {
                                        List<Home_item> list = new ArrayList<Home_item>();
                                        for (Home_item item : mData) {
                                            String name = item.getParentname();
                                            if (str.equals(name)) {
                                                list.add(item);
                                                map.put(str, list);
                                            }
                                        }
                                    }
                                }
                                mAdapter = new CheckListAdapter(list, map, mContext);
                                expandable.setAdapter(mAdapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }
}
