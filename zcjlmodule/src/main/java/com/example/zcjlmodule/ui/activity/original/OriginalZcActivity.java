package com.example.zcjlmodule.ui.activity.original;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zcjlmodule.R;
import com.example.zcjlmodule.bean.OriginalZcBean;
import com.example.zcjlmodule.callback.Callback;
import com.example.zcjlmodule.callback.OriginalZcCallBack;
import com.example.zcjlmodule.callback.OriginalZcCallBackUtils;
import com.example.zcjlmodule.callback.PayDetailCallBackUtils;
import com.example.zcjlmodule.presenter.OriginalPresenter;
import com.example.zcjlmodule.ui.activity.mine.ChangeorganizeZcActivity;
import com.example.zcjlmodule.ui.activity.original.enclosure.DismantlingtypequeryZcActivity;
import com.example.zcjlmodule.ui.activity.original.enclosure.RegionZcActivity;
import com.example.zcjlmodule.utils.DialogUtils;
import com.example.zcjlmodule.view.OriginalView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import measure.jjxx.com.baselibrary.base.BaseMvpActivity;
import measure.jjxx.com.baselibrary.utils.SPUtils;
import measure.jjxx.com.baselibrary.utils.ScreenUtil;
import measure.jjxx.com.baselibrary.utils.TextUtils;
import measure.jjxx.com.baselibrary.utils.ToastUtlis;

/**
 * description: 原始勘丈表
 *
 * @author lx
 *         date: 2018/10/15 0015 下午 3:37
 *         update: 2018/10/15 0015
 *         跳转界面 WorkFragment
 */
public class OriginalZcActivity extends BaseMvpActivity<OriginalPresenter> implements OriginalView, View.OnClickListener, OriginalZcCallBack, Callback {
    private LinearLayout toolbarIconMeun, layoutEmptyView;
    private TextView toolbarIconTitle, emptyViewText;
    private TextView moneynumber, originalOrgname;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView originalRecycler;
    private OriginalAdapter mAdapter;
    private ProgressBar emptyViewBar;

    private Context mContext;
    private List<OriginalZcBean> list;
    //页数
    private int page = 0;
    private boolean status = true;
    //根据手机分辨率返回的尺寸
    private float dimension;
    //查询类型
    private static final String TYPE_ONE = "征拆类型查询";
    private static final String TYPE_TWO = "按区域查询";
    private static final String TYPE_THREE = "按表单号查询";
    private static final String TYPE_FOUR = "按户主明细查询";
    private static final String TYPE_FIVE = "按期数查询";
    private String orgId, periodId;
    private int stauts = 0;
    private ImageView AddData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original_zc);
        mContext = this;
        list = new ArrayList<>();
        //请求回调（callback方法的注册）
        OriginalZcCallBackUtils.setCallBack(this);
        PayDetailCallBackUtils.setCallBack(this);
        orgId = SPUtils.getString(mContext, "orgId", "");
        //获取屏幕对比比例1DP=？PX 比例有 1 ，2 ，3 ，4
        dimension = ScreenUtil.getDensity(mContext);
        mPresenter = new OriginalPresenter();
        mPresenter.mView = this;
        //调加
        AddData = (ImageView) findViewById(R.id.original_add);
        AddData.setOnClickListener(this);
        if (SPUtils.getString(mContext, "orgType", null).contains("4")) {
            AddData.setVisibility(View.VISIBLE);
        } else {
            AddData.setVisibility(View.GONE);
        }
        //返回
        findViewById(R.id.toolbar_icon_back).setOnClickListener(this);
        //组织机构
        findViewById(R.id.original_org).setOnClickListener(this);
        originalOrgname = (TextView) findViewById(R.id.original_orgname);
        originalOrgname.setText(SPUtils.getString(mContext, "orgName", ""));
        //合计金额
        moneynumber = (TextView) findViewById(R.id.original_moneynumber);
        //加载错误提示
        emptyViewText = (TextView) findViewById(R.id.layout_emptyView_text);
        emptyViewBar = (ProgressBar) findViewById(R.id.layout_emptyView_bar);
        layoutEmptyView = (LinearLayout) findViewById(R.id.layout_emptyView);
        //标题
        toolbarIconTitle = (TextView) findViewById(R.id.toolbar_icon_title);
        toolbarIconTitle.setText("原始勘丈表");
        //menu 状态处理
        toolbarIconMeun = (LinearLayout) findViewById(R.id.toolbar_icon_meun);
        toolbarIconMeun.setVisibility(View.VISIBLE);
        toolbarIconMeun.setOnClickListener(this);
        //刷新加载
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.original_refreshlayout);
        //是否启用列表惯性滑动到底部时自动加载更多
        refreshLayout.setEnableAutoLoadmore(false);
        recycler();
        refresh();
        //网络请求
        httprequest(true);
        mPresenter.getdata(orgId, page, null);

    }

    /**
     * recyclerView
     */
    private void recycler() {
        //列表
        originalRecycler = (RecyclerView) findViewById(R.id.original_recycler);
        //设置控件显示样式
        originalRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        //调加适配器，初始化布局和数据
        originalRecycler.setAdapter(mAdapter = new OriginalAdapter(R.layout.adapter_original_zc, list));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Map<String, String> message = new HashMap<String, String>();
                //id
                OriginalZcBean bean = list.get(position);
                String string = bean.toString();
                List<String> strlist = stringToList(string);
                for (int i = 0; i < strlist.size(); i++) {
//
                    String str = strlist.get(i);
                    message.put(str.substring(0, str.indexOf("=")) + "", str.substring(str.indexOf("=") + 1, str.length()) + "");
                }
                Intent intent = new Intent(mContext, NewAddOriginalZcActivity.class);
                intent.putExtra("message", (Serializable) message);
                intent.putExtra("type", "old");
                intent.putExtra("orgId", orgId);
                startActivity(intent);
            }
        });
    }

    /**
     * string转集合
     */
    public static List<String> stringToList(String strs) {
        if (strs == "" && strs.isEmpty()) {
        } else {
            String str[] = strs.split(",");
            return Arrays.asList(str);
        }
        return null;
    }

    /**
     * 加载刷新
     */
    private void refresh() {
        //  下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                httprequest(true);

            }
        });
        //上拉加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                httprequest(false);
            }
        });
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.toolbar_icon_back) {
            finish();
        } else if (i == R.id.original_org) {
            Intent intent = new Intent(mContext, ChangeorganizeZcActivity.class);
            startActivity(intent);
        } else if (i == R.id.original_add) {
            Intent intent = new Intent(mContext, NewAddOriginalZcActivity.class);
            intent.putExtra("type", "new");
            intent.putExtra("orgId", orgId);
            startActivity(intent);
        } else if (i == R.id.toolbar_icon_meun) {
            //menu
            DialogUtils.meunPop(OriginalZcActivity.this, toolbarIconMeun, dimension, new DialogUtils.onclick() {
                @Override
                public void openonclick(String string) {
                    if (TYPE_ONE.equals(string)) {
                        //按征拆类型查询
                        stauts = 1;
                        //征拆类型查询
                        Intent intent = new Intent(mContext, DismantlingtypequeryZcActivity.class);
                        intent.putExtra("orgId", orgId);
                        startActivityForResult(intent, 103);
                    } else if (TYPE_TWO.equals(string)) {
                        //区域查询
                        stauts = 2;
                        Intent intent = new Intent(mContext, RegionZcActivity.class);
                        intent.putExtra("orgId", orgId);
                        startActivityForResult(intent, 102);
                    } else if (TYPE_THREE.equals(string)) {
                        //表单
                        stauts = 3;
                        DialogUtils.dismantling(mContext, "按表单号查询", new DialogUtils.onclick() {
                            @Override
                            public void openonclick(String string) {
                                periodId = string;
                                //征拆类型
                                httprequest(true);
                            }
                        });
                    } else if (TYPE_FOUR.equals(string)) {
                        stauts = 4;
                        DialogUtils.dismantling(mContext, "按户主明细查询", new DialogUtils.onclick() {
                            @Override
                            public void openonclick(String string) {
                                periodId = string;
                                //征拆类型
                                httprequest(true);
                            }
                        });
                    } else if (TYPE_FIVE.equals(string)) {
                        //期数查询
                        stauts = 5;
                        Intent intent = new Intent(mContext, PeriodsQueryZcActivity.class);
                        intent.putExtra("orgId", orgId);
                        startActivityForResult(intent, 101);

                    } else {
                    }
                }
            });
        } else {
        }
    }

    /**
     * 请求成功
     *
     * @param data
     */
    @Override
    public void onSuccess(ArrayList<OriginalZcBean> data, String price) {
        String str;
        if (page == 0) {
            list.clear();
            if (price != null) {
                str = "合计金额：" + price;
                moneynumber.setText(TextUtils.setText(mContext, str, str.indexOf("：") + 1));
            } else {
                moneynumber.setText("合计金额：");
            }
        }
        list.addAll(data);
        if (list.size() > 0) {
            //list大于0，隐藏空白提示布局
            layoutEmptyView.setVisibility(View.GONE);
        } else {
            //list小于0，显示空白提示布局，隐藏等待框
            emptyViewText.setVisibility(View.VISIBLE);
            layoutEmptyView.setVisibility(View.VISIBLE);
            emptyViewBar.setVisibility(View.GONE);
        }
        mAdapter.setNewData(list);
        //刷新加载关闭
        if (status) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadmore();
        }
    }

    /**
     * 请求失败
     */
    @Override
    public void onError() {
        if (list.size() > 0) {
            //list大于0，隐藏空白提示布局
            layoutEmptyView.setVisibility(View.GONE);
        } else {
            //list小于0，显示空白提示布局，隐藏等待框
            emptyViewText.setVisibility(View.VISIBLE);
            layoutEmptyView.setVisibility(View.VISIBLE);
            emptyViewBar.setVisibility(View.GONE);
            emptyViewText.setText("请求失败");
        }
        //刷新加载关闭
        if (status) {
            refreshLayout.finishRefresh();
        } else {
            refreshLayout.finishLoadmore();
        }
    }

    /**
     * 切换组织的回调
     *
     * @param map
     */
    @Override
    public void callback(Map<String, Object> map) {
        //显示组织名称
        originalOrgname.setText(map.get("orgname") + "");
        //变更组织ID
        orgId = (String) map.get("orgId");
        String type = (String) map.get("type");
        if (type.contains("4")) {
            AddData.setVisibility(View.VISIBLE);
        } else {
            AddData.setVisibility(View.GONE);
        }
        httprequest(true);
    }

    //数据更新后刷新列表
    @Override
    public void updata() {
        httprequest(true);
    }

    /**
     * recyclerview适配器
     */
    public class OriginalAdapter extends BaseQuickAdapter<OriginalZcBean, BaseViewHolder> {
        OriginalAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }
        @Override
        protected void convert(BaseViewHolder helper, OriginalZcBean item) {
            //标题
            helper.setText(R.id.original_adapter_title, item.getNumber());
            //期数
            helper.setText(R.id.original_adapter_data, item.getPeriodName());
            //指挥部
            helper.setText(R.id.original_adapter_content, item.getHeadquarterName());
            //户主名称
            String str = "户主姓名：" + item.getHouseholder() + "(" + item.getTotalPrice() + ")";
            SpannableString string = TextUtils.setText(mContext, str, str.indexOf("("));
            helper.setText(R.id.original_adapter_namecontent, string);
            //类别
            helper.setText(R.id.original_adapter_category, "征拆类别：" + item.getLevyTypeName());
            //创建人
            helper.setText(R.id.original_adapter_createname, "创建人：" + item.getCreateName());
            //创建时间
            helper.setText(R.id.original_adapter_createdate, "创建日期：" + item.getCreateDate().substring(0,10));
        }
    }

    /**
     * 请求网络数据处理
     *
     * @param lean
     */
    public void httprequest(boolean lean) {
        if (lean) {
            page = 0;
            //标记刷新还是加载状态
            status = true;
        } else {
            page++;
            //标记刷新还是加载状态
            status = false;
        }
        Map<String, String> param = new HashMap<>();
        switch (stauts) {
            case 1:
                //征拆类型
                param.put("levyType", periodId);
                break;
            case 2:
                //区域查询
                param.put("region", periodId);
                break;
            case 3:
                //表单查询
                param.put("queryNum", periodId);
                break;
            case 4:
                //户主明细
                param.put("householder", periodId);
                break;
            case 5:
                //期数
                param.put("period", periodId);
                break;
            case 0:
            default:
                break;

        }
        mPresenter.getdata(orgId, page, param);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == 101) {
            //期数查询
            periodId = data.getStringExtra("id");
            httprequest(true);
        } else if (requestCode == 102 && resultCode == 101) {
            //区域查询
            periodId = data.getStringExtra("id");
            httprequest(true);
        } else if (requestCode == 103 && resultCode == 101) {
            //征拆类型查询
            periodId = data.getStringExtra("id");
            httprequest(true);
        }
    }
}
