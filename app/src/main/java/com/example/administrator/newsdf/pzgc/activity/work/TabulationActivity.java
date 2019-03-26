package com.example.administrator.newsdf.pzgc.activity.work;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.baselibrary.view.BaseActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;


/**
 * @author lx
 *         选择检查项
 */
public class TabulationActivity extends BaseActivity {
    private ArrayList<String> mData = null;
    private Context mContext;
    private TextView tv, com_title;
    private TagFlowLayout flowlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabulation);
        mContext = TabulationActivity.this;
        mData = new ArrayList<>();
        //    得到跳转到该Activity的Intent对象
        Intent intent = getIntent();
        //获取到intent传过来得集合
        mData = intent.getExtras().getStringArrayList("data");
        com_title = (TextView) findViewById(R.id.com_title);
        com_title.setText("选择任务项");
        flowlayout = (TagFlowLayout) findViewById(R.id.id_flowlayout);
        //初始化layoutinflater布局管理器
        final LayoutInflater mInflater = LayoutInflater.from(mContext);
        //控件自定义的适配器
        flowlayout.setAdapter(new TagAdapter<String>(mData) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                tv = (TextView) mInflater.inflate(R.layout.tabulation_item,
                        flowlayout, false);
                tv.setText(s);
                return tv;
            }
        });
        //flowlayout的自定义点击事件
        flowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                //回传数据到之前的 activity
                Intent intent = new Intent();
                intent.putExtra("position", position);
                //回传数据到主Activity
                setResult(2, intent);
                finish(); //此方法后才能返回主Activity
                return true;
            }
        });
        //返回键
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
