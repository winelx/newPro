package com.example.administrator.newsdf.pzgc.fragment.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.FragmentHomeListAdapter;
import com.example.administrator.newsdf.pzgc.activity.home.MineListmessageActivity;
import com.example.administrator.newsdf.pzgc.bean.Home_item;
import com.example.administrator.newsdf.pzgc.callback.OgranCallback;
import com.example.administrator.newsdf.pzgc.callback.OgranCallbackUtils;
import com.example.administrator.newsdf.pzgc.callback.frehomeCallBack;
import com.example.administrator.newsdf.pzgc.callback.frehomeCallBackUtils;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.Requests;
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
 * @author lx
 *         Created by Administrator on 2017/11/21 0021.
 *         我的消息
 */

public class HomeMineFragment extends Fragment implements AdapterView.OnItemClickListener, OgranCallback, frehomeCallBack {
    private View rootView;
    private ExpandableListView expandable;
    private FragmentHomeListAdapter mAdapter;
    private ArrayList<Home_item> mData;
    private ArrayList<String> title;
    private Context mContext;
    private SmartRefreshLayout refreshLayout;
    private RelativeLayout homeFragImg;
    private TextView homeImgText;
    private ImageView homeImgNonews;
    private View.OnClickListener ivGoToChildClickListener;
    private Map<String, List<Home_item>> hasMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //避免重复绘制界面
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, null);
            Dates.getDialog(getActivity(), "请求数据中...");
            expandable = rootView.findViewById(R.id.expandable);
            homeFragImg = rootView.findViewById(R.id.home_frag_img);
            homeImgNonews = rootView.findViewById(R.id.home_img_nonews);
            homeImgText = rootView.findViewById(R.id.home_img_text);
            refreshLayout = rootView.findViewById(R.id.SmartRefreshLayout);
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
        //
        init();
        return rootView;
    }

    private void init() {
        mContext = getActivity();
        //切换组织接口回调（OrganizationaActivity）
        OgranCallbackUtils.setCallBack(this);
        //收藏刷新
        frehomeCallBackUtils.setCallBack(this);

        //设置布局管理器
        //设置适配器
//        mAdapter = new MyExpandableListAdapter(mContext);
        //设置控制Item增删的动画
        homeFragImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dates.getDialogs(getActivity(), "请求数据中");
                Okgo();
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Okgo();
                //传入false表示刷新失败
                refreshlayout.finishRefresh(1000);
            }
        });
        //网络请求
        ivGoToChildClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取被点击图标所在的group的索引
                Map<String, Object> map = (Map<String, Object>) v.getTag();
                int groupPosition = (int) map.get("groupPosition");
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

        Okgo();
    }

    //网络请求
    private void Okgo() {
        //请求数据库的数据
        OkGo.post(Requests.TaskMain)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.contains("data")) {
                            mData = new ArrayList<>();
                            title = new ArrayList<>();
                            hasMap = new HashMap<>();
                            try {
                                JSONObject jsonObject = new JSONObject(s);
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
                                    String isfavorite = json.getString("isfavorite");
                                    String orgId = json.getString("orgId");
                                    String orgName = json.getString("orgName");
                                    String parentid = json.getString("parent_id");
                                    String parentname = json.getString("parent_name");
                                    String unfinish = json.getString("unfinish");
                                    //将组织所属公司添加到集合
                                    if (!title.contains(parentname)) {
                                        title.add(parentname);
                                    }
                                    mData.add(new Home_item(content, createTime, id, orgId, orgName, unfinish, isfavorite, parentname, parentid, false));
                                }
                                //是否有数据
                                if (mData.size() != 0) {
                                    int random = (int) (Math.random() * 4) + 1;
                                    for (String str : title) {
                                        List<Home_item> list = new ArrayList<Home_item>();
                                        for (Home_item item : mData) {
                                            String name = item.getParentname();
                                            if (str.equals(name)) {
                                                list.add(item);
                                                hasMap.put(str, list);
                                            }
                                        }
                                    }
                                }
                                mAdapter = new FragmentHomeListAdapter(title, hasMap, mContext,
                                        ivGoToChildClickListener);
                                expandable.setAdapter(mAdapter);
                                //关闭刷新提示
                                refreshLayout.finishRefresh(false);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.showShortToast("没有更多数据");
                            homeFragImg.setVisibility(View.VISIBLE);
                            homeImgText.setText("暂无数据，点击刷新");
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Dates.disDialog();
                        ToastUtils.showShortToast("网络连接失败");
                        homeFragImg.setVisibility(View.VISIBLE);
                        homeImgNonews.setBackgroundResource(R.mipmap.nonetwork);
                        homeImgText.setText("请求确认网络是否正常，点击再次请求");
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //跳转列表界面
        Intent intent = new Intent(mContext, MineListmessageActivity.class);
        intent.putExtra("name", mData.get(position).getOrgname());
        intent.putExtra("orgId", mData.get(position).getOrgid());
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //关闭dialog和刷新
        Dates.disDialog();
        refreshLayout.finishRefresh(false);
    }

    //切换组织后刷新
    @Override
    public void taskCallback() {
        Okgo();
    }

    //取消收藏后刷新
    @Override
    public void bright() {
        Okgo();
    }
}