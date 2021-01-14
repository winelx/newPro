package com.example.administrator.fengji.pzgc.activity.bright;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.utils.ToastUtils;
import com.example.administrator.fengji.pzgc.adapter.BridhtAdapter;
import com.example.administrator.fengji.pzgc.activity.MainActivity;
import com.example.administrator.fengji.pzgc.bean.BrightBean;
import com.example.administrator.fengji.pzgc.callback.BrightCallBack;
import com.example.administrator.fengji.pzgc.callback.BrightCallBackUtils2;
import com.example.administrator.fengji.pzgc.utils.Dates;
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

import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.fengji.pzgc.utils.Dates.stampToDate;

/**
 * description: 亮点展示界面
 *
 * @author lx
 * date: 2018/4/25 0025 下午 2:32
 * update: 2018/4/25 0025
 * version:
 */
public class BridhtFragment3 extends Fragment implements BrightCallBack {
    View view;
    private int pos = 0;
    private BridhtAdapter mAdapter;
    private ArrayList<BrightBean> mData = new ArrayList<>();
    private SmartRefreshLayout refreshlayout;
    private LinearLayout nulllauout, layoutLoading;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.bright_list_view, container, false);
            BrightCallBackUtils2.setCallBack(this);
            RecyclerView brightspot_list = view.findViewById(R.id.brightspot_list);
            refreshlayout = view.findViewById(R.id.refreshlayout);
            layoutLoading = view.findViewById(R.id.layout_loading);
            nulllauout = view.findViewById(R.id.nulllauout);
            //是否启用越界拖动（仿苹果效果）1.0.4
            refreshlayout.setEnableOverScrollDrag(true);
            //禁止上拉
            refreshlayout.setEnableLoadmore(false);
            refreshlayout.setEnableRefresh(true);
            //仿ios越界
            refreshlayout.setEnableOverScrollBounce(true);
            LinearLayoutManager linearLayout = new LinearLayoutManager(MainActivity.getInstance());
            //添加Android自带的分割线
            brightspot_list.addItemDecoration(new DividerItemDecoration(MainActivity.getInstance(), DividerItemDecoration.VERTICAL));
            brightspot_list.setLayoutManager(linearLayout);
            //设置Adapter
            mAdapter = new BridhtAdapter(MainActivity.getInstance());
            brightspot_list.setAdapter(mAdapter);
            Bright(false);
            //上拉加载
            refreshlayout.setOnRefreshListener(new OnRefreshListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    Bright(true);
                    refreshlayout.finishRefresh(1000);
                }
            });
        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    private void Bright(final boolean stauts) {
        OkGo.<String>post(Requests.ListByType)
                //pos 是从0开始的，而传递的数据从1开始
                .params("type", 3)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            mData.clear();
                            if (jsonArray.length() > 0) {

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    String id;
                                    try {
                                        id = json.getString("id");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        id = "";
                                    }
                                    //标段ID
                                    String orgId;
                                    try {
                                        orgId = json.getString("orgId");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        orgId = "";
                                    }
                                    //标段名称
                                    String orgName;
                                    try {
                                        orgName = json.getString("orgName");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        orgName = "";
                                    }
                                    //任务名称
                                    String taskName;
                                    try {
                                        taskName = json.getString("taskName");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        taskName = "";
                                    }
                                    //责任人
                                    String leadername;
                                    try {
                                        leadername = json.getString("leaderName");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        leadername = "";
                                    }
                                    //图片
                                    String leaderImg;
                                    try {
                                        leaderImg = json.getString("leaderImg");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        leaderImg = "";
                                    }
                                    String TaskId;
                                    try {
                                        TaskId = json.getString("taskId");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        TaskId = "";
                                    }
                                    String updateDate;
                                    try {
                                        updateDate = stampToDate(json.getString("updateDate"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        updateDate = Dates.stampToDate(json.getString("createDate"));
                                    }
                                    ArrayList<String> ImagePaths = new ArrayList<String>();
                                    try {

                                        JSONArray filePaths = json.getJSONArray("filePaths");
                                        for (int j = 0; j < filePaths.length(); j++) {
                                            String path = Requests.networks + filePaths.get(j);
                                            ImagePaths.add(path);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    mData.add(new BrightBean(id, orgId, orgName, taskName, leadername, leaderImg, updateDate, TaskId, 2, ImagePaths));
                                }
                            } else {
                                if (stauts) {
                                    ToastUtils.showLongToast("暂无数据");
                                }
                            }
                            layoutLoading.setVisibility(View.GONE);
                            if (mData.size() > 0) {
                                nulllauout.setVisibility(View.GONE);
                            } else {
                                nulllauout.setVisibility(View.VISIBLE);
                            }
                            mAdapter.getData(mData);
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

    @Override
    public void bright() {
        Bright(false);
    }
}
