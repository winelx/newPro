package com.example.administrator.yanghu.pzgc.activity.check.activity.newcheck.fragment;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.activity.check.activity.newcheck.bean.MessageExternal;
import com.example.administrator.yanghu.pzgc.activity.check.activity.newcheck.utils.ExternalApi;
import com.example.administrator.yanghu.pzgc.bean.Audio;
import com.example.administrator.yanghu.pzgc.utils.Dates;
import com.example.administrator.yanghu.pzgc.utils.LazyloadFragment;
import com.example.administrator.yanghu.pzgc.utils.ToastUtils;
import com.example.administrator.yanghu.pzgc.utils.Utils;
import com.example.baselibrary.utils.network.NetWork;
import com.example.baselibrary.utils.rx.LiveDataBus;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class MessageExternalFragment extends LazyloadFragment implements View.OnClickListener {
    private String noticeId;
    private ArrayList<String> ids;
    private MessageExternalAdapter adapter;
    private SmartRefreshLayout refreshlayout;
    private RecyclerView recyclerView;
    private TextView com_button;
    private TextView title,top_title;

    @Override
    protected int setContentView() {
        return R.layout.activity_chaged_importchageditem;
    }

    @Override
    protected void init() {
        Intent intent = getActivity().getIntent();
        noticeId = intent.getStringExtra("noticeId");
        ids = new ArrayList<>();
        findViewById(R.id.com_back).setOnClickListener(this);
        title = (TextView) findViewById(R.id.com_title);
        top_title = (TextView) findViewById(R.id.top_title);
        top_title.setVisibility(View.VISIBLE);
        refreshlayout = (SmartRefreshLayout) findViewById(R.id.refreshlayout);
        com_button = (TextView) findViewById(R.id.com_button);
        com_button.setText("确定");
        com_button.setVisibility(View.GONE);
        //是否启用下拉刷新功能
        refreshlayout.setEnableRefresh(false);
        //是否启用上拉加载功能
        refreshlayout.setEnableLoadmore(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshlayout.setEnableOverScrollDrag(true);
        //是否在列表不满一页时候开启上拉加载功能
        refreshlayout.setEnableLoadmoreWhenContentNotFull(false);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MessageExternalAdapter(R.layout.adapter_chaged_checkitem, new ArrayList<>());
        recyclerView.setAdapter(adapter);
        LiveDataBus.get().with("ex_check_item", Audio.class).observe(this, new Observer<Audio>() {
            @Override
            public void onChanged(@Nullable Audio bean) {
                ids.clear();
                com_button.setVisibility(View.GONE);
                request(bean.getContent());
                top_title.setText(bean.getName());
                title.setText(bean.getTitle());
            }
        });
        com_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    @Override
    protected void lazyLoad() {

    }

    public void request(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("safetyCheckId", id);
        NetWork.postHttp(ExternalApi.GETCHOOSESAFEDELDATABYAPP, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        List<MessageExternal> list = com.alibaba.fastjson.JSONArray.parseArray(data.toString(), MessageExternal.class);
                        adapter.setNewData(list);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {

            }
        });
    }

    public void save() {
        Map<String, String> map = new HashMap<>();
        map.put("manageDelIdStr", Dates.listToStrings(ids));
        map.put("noticeId", noticeId);
        NetWork.postHttp(ExternalApi.SAVENOTECEDELBYSAFEBYAPP, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int ret = jsonObject.getInt("ret");
                    if (ret == 0) {
                        ToastUtils.showShortToast("导入成功");
                        Intent intent = new Intent();
                        //回传数据到主Activity
                        getActivity().setResult(1, intent);
                        getActivity().finish();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                LiveDataBus.get().with("ex_viewpager").setValue(0);
                break;
            default:
                break;
        }
    }

    public class MessageExternalAdapter extends BaseQuickAdapter<MessageExternal, BaseViewHolder> {
        public MessageExternalAdapter(int layoutResId, @Nullable List<MessageExternal> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MessageExternal checkitem) {
            if (checkitem.isStatus()) {
                helper.setBackgroundRes(R.id.check_image, R.mipmap.circular_ensure_true);
            } else {
                helper.setBackgroundRes(R.id.check_image, R.mipmap.circular_ensure_false);
            }
            helper.setText(R.id.check_item, "检测位置：" + checkitem.getPosition());
            //扣分标准
            helper.setText(R.id.check_standard, checkitem.getCheckStandard());
            helper.setText(R.id.title, checkitem.getName());
            helper.setText(R.id.describe, checkitem.getDescription());
            helper.setText(R.id.standardScore, "标准分：" + Utils.isNull(checkitem.getStandardScore()));
            helper.setVisible(R.id.getscore, false);
            ImageView check_image = helper.getView(R.id.check_image);
            check_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkitem.isStatus()) {
                        checkitem.setStatus(false);
                        ids.remove(checkitem.getId());
                        if (ids.size() == 0) {
                            com_button.setVisibility(View.GONE);
                        }
                        helper.setBackgroundRes(R.id.check_image, R.mipmap.circular_ensure_false);
                    } else {
                        checkitem.setStatus(true);
                        ids.add(checkitem.getId());
                        com_button.setVisibility(View.VISIBLE);
                        helper.setBackgroundRes(R.id.check_image, R.mipmap.circular_ensure_true);
                    }
                }
            });
        }
    }
}
