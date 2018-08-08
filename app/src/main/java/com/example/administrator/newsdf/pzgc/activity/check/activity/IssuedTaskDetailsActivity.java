package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.IssuedTaskDetailsAdapter;
import com.example.administrator.newsdf.pzgc.bean.CheckDetailsContent;
import com.example.administrator.newsdf.pzgc.bean.CheckDetailsTop;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

/**
 * description: 下发任务详情
 *
 * @author lx
 *         date: 2018/8/8 0008 下午 2:28
 *         update: 2018/8/8 0008
 *         version:
 */
public class IssuedTaskDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView detailsRejected;
    private TextView infaceWbsPath, titleView, checklistmeuntext;
    private IconTextView checklistback;
    private IssuedTaskDetailsAdapter mAdater;
    ArrayList<Object> mData;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issued_task_details);
        mData = new ArrayList<>();
        mContext = IssuedTaskDetailsActivity.this;
        detailsRejected = (RecyclerView) findViewById(R.id.details_rejected);
        detailsRejected.setLayoutManager(new LinearLayoutManager(detailsRejected.getContext(), LinearLayoutManager.VERTICAL, false));
        infaceWbsPath = (TextView) findViewById(R.id.inface_wbs_path);
        titleView = (TextView) findViewById(R.id.titleView);
        checklistmeuntext = (TextView) findViewById(R.id.checklistmeuntext);
        checklistmeuntext.setVisibility(View.VISIBLE);
        checklistmeuntext.setTextSize(10);
        checklistmeuntext.setText("处理记录");
        mData.add(new CheckDetailsContent("1", "1", "1", "1"));
        mData.add(new CheckDetailsTop("1"));
        mAdater = new IssuedTaskDetailsAdapter(mData, mContext);
        detailsRejected.setAdapter(mAdater);
        checklistmeuntext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checklistmeuntext:

                break;
            default:
                break;
        }
    }
}
