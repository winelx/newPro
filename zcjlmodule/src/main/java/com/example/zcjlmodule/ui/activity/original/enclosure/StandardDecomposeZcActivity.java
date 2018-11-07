package com.example.zcjlmodule.ui.activity.original.enclosure;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zcjlmodule.R;
import com.example.zcjlmodule.bean.AttachProjectBean;
import com.example.zcjlmodule.utils.activity.MeasureUtils;

import java.util.ArrayList;
import java.util.List;

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
    private ArrayList<String> list;
    private String orgId, regionId;
    private MeasureUtils utils;
    private TextView regionname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_decompose_zc);
        list = new ArrayList<>();
        mContext = this;
        utils = new MeasureUtils();
        Intent intent = getIntent();
        orgId = intent.getStringExtra("orgId");
        //地区查询
        findViewById(R.id.standard_dec_region).setOnClickListener(this);
        //返回
        findViewById(R.id.toolbar_icon_back).setOnClickListener(this);
        //征拆类别查询
        findViewById(R.id.standard_dec_type).setOnClickListener(this);
        //单价查询
        findViewById(R.id.standard_dec_Price).setOnClickListener(this);
        for (int i = 0; i < 10; i++) {
            list.add("" + 1);
        }
        //区域名称
        regionname = (TextView) findViewById(R.id.region_name);
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
                intent.putExtra("name", list.get(position));
                setResult(105, intent);
                finish(); //结束当前的activity的生命周期
            }
        });

    }


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
            Intent intent=new Intent(mContext, DismantlingtypequeryZcActivity.class);
            intent.putExtra("orgId", orgId);
            startActivity(intent);

        } else if (i == R.id.standard_dec_Price) {
            //单价查询
        } else {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && requestCode == 101) {
            regionId = data.getStringExtra("id");
            regionname.setText(data.getStringExtra("name"));
        }
    }

    public class StandardDecomposeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public StandardDecomposeAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }
}
