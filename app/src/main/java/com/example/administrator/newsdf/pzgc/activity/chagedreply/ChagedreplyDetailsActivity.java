package com.example.administrator.newsdf.pzgc.activity.chagedreply;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.adapter.ChagedreplyDetailsAdapter;
import com.example.administrator.newsdf.pzgc.bean.NoticeItemDetailsRecord;
import com.example.administrator.newsdf.pzgc.bean.ReplyDetailsContent;
import com.example.administrator.newsdf.pzgc.bean.ReplyDetailsRecord;
import com.example.administrator.newsdf.pzgc.bean.ReplyDetailsText;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;

import java.util.ArrayList;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/16 0016}
 * 描述：整改回复单详情界面
 * {@link }
 */
public class ChagedreplyDetailsActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private LinearLayout deviceDetailsFunction;
    private TextView titleView;
    private ChagedreplyDetailsAdapter mAdapter;
    private ArrayList<Object> list;
    private Context mContext;
    private TextView device_details_proving, device_details_result, device_details_assign, device_details_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details);
        mContext = this;
        list = new ArrayList<>();
        list.add(new ReplyDetailsContent("ss"));
        list.add(new ReplyDetailsText("问题项"));
        list.add(new NoticeItemDetailsRecord("ss"));
        list.add(new NoticeItemDetailsRecord("ss"));
        list.add(new NoticeItemDetailsRecord("ss"));
        list.add(new ReplyDetailsText("操作记录"));
        list.add(new ReplyDetailsRecord("sss"));
        list.add(new ReplyDetailsRecord("sss"));
        list.add(new ReplyDetailsRecord("sss"));
        findViewById(R.id.checklistback).setOnClickListener(this);
        /*验证*/
        device_details_proving = (TextView) findViewById(R.id.device_details_proving);
        device_details_proving.setOnClickListener(this);
        /*我回复*/
        device_details_result = (TextView) findViewById(R.id.device_details_result);
        device_details_result.setOnClickListener(this);
        /*指派*/
        device_details_assign = (TextView) findViewById(R.id.device_details_assign);
        device_details_assign.setOnClickListener(this);
        /*编辑*/
        device_details_edit = (TextView) findViewById(R.id.device_details_edit);
        device_details_edit.setOnClickListener(this);
        /*标题*/
        titleView = (TextView) findViewById(R.id.titleView);
        /*功能按钮父布局*/
        deviceDetailsFunction = (LinearLayout) findViewById(R.id.device_details_function);
        /*内容展示控件*/
        recyclerView = (RecyclerView) findViewById(R.id.device_details_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter = new ChagedreplyDetailsAdapter(list, mContext));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checklistback:
                finish();
                break;
            case R.id.device_details_proving:
                startActivity(new Intent(this,ChagedReplyVerificationActivity.class));
                /*验证*/
                break;
            case R.id.device_details_result:
                /*回复*/
                break;
            case R.id.device_details_assign:
                /*指派*/
                break;
            case R.id.device_details_edit:
                /*编辑*/
                break;
            default:
                break;
        }
    }
}
