package com.example.zcjlmodule.ui.activity.original.enclosure;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zcjlmodule.R;
import com.example.zcjlmodule.bean.AttachProjectBean;
import com.example.zcjlmodule.bean.StandardDecomposeBean;
import com.example.zcjlmodule.utils.activity.MeasureUtils;
import com.example.zcjlmodule.utils.activity.StandardUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import measure.jjxx.com.baselibrary.base.BaseActivity;

/**
 * description: 选择标准分解
 *
 * @author lx
 *         date: 2018/10/18 0018 下午 3:48
 *         update: 2018/10/18 0018
 *         version:
 *         跳转界面： NewAddOriginalZcActivity
 */
public class StandardDecomposeZcActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView recycler;
    private StandardDecomposeAdapter mAdapter;
    private Context mContext;
    private ArrayList<StandardDecomposeBean> list;
    private String orgId, regionId, dismantleId, type;
    private TextView regionname, region_dismantle;
    private EditText pricenumber;
    private SmartRefreshLayout refreshLayout;
    private StandardUtils utils;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_decompose_zc);
        list = new ArrayList<>();
        utils = new StandardUtils();
        mContext = this;
        Intent intent = getIntent();
        orgId = intent.getStringExtra("orgId");
        type = intent.getStringExtra("type");
        //地区查询
        findViewById(R.id.standard_dec_region).setOnClickListener(this);
        //返回
        findViewById(R.id.toolbar_icon_back).setOnClickListener(this);
        //征拆类别查询
        findViewById(R.id.standard_dec_type).setOnClickListener(this);
        //单价查询
        findViewById(R.id.standard_dec_Price).setOnClickListener(this);
        //区域名称
        regionname = (TextView) findViewById(R.id.region_name);
        //征拆类型名称
        region_dismantle = (TextView) findViewById(R.id.region_dismantle);
        //单价
        pricenumber = (EditText) findViewById(R.id.standard_dec_pricenumber);
        //刷新控件
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.original_refreshlayout);
        //是否启用下拉刷新功能
        refreshLayout.setEnableRefresh(false);
        //是否启用上拉加载功能
        refreshLayout.setEnableLoadmore(true);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableOverScrollDrag(true);
        TextView titlke = (TextView) findViewById(R.id.toolbar_icon_title);
        titlke.setText("选择标准分解");
        recycler = (RecyclerView) findViewById(R.id.standard_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(mAdapter = new StandardDecomposeAdapter(R.layout.adapter_decompose_zc, list));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                // 获取用户计算后的结果
                intent.putExtra("name", list.get(position).getCountyName());
                intent.putExtra("id", list.get(position).getId());
                setResult(105, intent);
                finish(); //结束当前的activity的生命周期
            }
        });
        //重新软键盘的回车键
        /**
         * 　actionUnspecified        未指定         EditorInfo.IME_ACTION_UNSPECIFIED.
         　　actionNone                 动作            EditorInfo.IME_ACTION_NONE
         　　actionGo                    去往            EditorInfo.IME_ACTION_GO
         　　actionSearch               搜索            EditorInfo.IME_ACTION_SEARCH
         　　actionSend                 发送            EditorInfo.IME_ACTION_SEND
         　　actionNext                下一项           EditorInfo.IME_ACTION_NEXT
         　　actionDone               完成              EditorInfo.IME_ACTION_DONE
         */
        pricenumber.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        pricenumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                    pricenumber.requestFocus();
                    return true;
                }
                return false;
            }
        });
        httprequest();

    }

    /**
     * 点击事件
     *
     * @param view 控件
     */
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.toolbar_icon_back) {
            finish();
        } else if (i == R.id.standard_dec_region) {
            //地区查询
            Intent intent = new Intent(mContext, RegionZcActivity.class);
            intent.putExtra("orgId", orgId);
            startActivityForResult(intent, 101);
        } else if (i == R.id.standard_dec_type) {
            //征拆类型查询
            Intent intent = new Intent(mContext, DismantlingtypequeryZcActivity.class);
            intent.putExtra("orgId", orgId);
            startActivityForResult(intent, 102);
        } else if (i == R.id.standard_dec_Price) {
            //单价查询
        } else {
        }
    }

    /**
     * 回调
     *
     * @param requestCode 发送码
     * @param resultCode  接收码
     * @param data        返回数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            //区域
            try {
                regionId = data.getStringExtra("id");
                regionname.setText(data.getStringExtra("name"));
            } catch (Exception e) {
            }
        } else if (requestCode == 102) {
            //征拆类型
            try {
                dismantleId = data.getStringExtra("id");
                region_dismantle.setText(data.getStringExtra("name"));
            } catch (Exception e) {
            }
        }
        page = 1;
        httprequest();
    }

    /**
     * recyclerview适配器
     */
    public class StandardDecomposeAdapter extends BaseQuickAdapter<StandardDecomposeBean, BaseViewHolder> {

        public StandardDecomposeAdapter(@LayoutRes int layoutResId, @Nullable List<StandardDecomposeBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, StandardDecomposeBean item) {
            //单位
            helper.setText(R.id.unknit_stand_unit, "单位：" + item.getMeterUnitName());
            //文件名
            helper.setText(R.id.unknit_stand_title, item.getStandardNumber());
            //征拆类型
            helper.setText(R.id.unknit_stand_type, item.getLevyTypeName());
            //补偿类型
            helper.setText(R.id.unknit_stand_compensate, "补偿类型：" + item.getCompensateType());
            //单价
            helper.setText(R.id.unknit_stand_price, "单价：" + item.getPrice());
        }
    }

    /**
     * 网络请求
     */
    private void httprequest() {
        Map<String, Object> map = new HashMap<>();
        //区域
        map.put("regionId", regionId);
        //页数
        map.put("page", page);
        //征拆类型
        map.put("dismantleId", dismantleId);
        //单价
        map.put("price", pricenumber.getText().toString());
        if (type.equals("old")) {
            //类型ID
            map.put("standardId", orgId);
        }
        utils.request(map, new StandardUtils.OnClickListener() {
            @Override
            public void onsuccess(List<StandardDecomposeBean> data) {
                if (page == 1) {
                    list.clear();
                }
                list.addAll(data);
                mAdapter.setNewData(list);
            }

            @Override
            public void onerror() {

            }
        });
    }
}
