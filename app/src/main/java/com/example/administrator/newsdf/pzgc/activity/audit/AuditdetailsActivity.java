package com.example.administrator.newsdf.pzgc.activity.audit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.Adapter.AuditdetailsAdapter;
import com.example.administrator.newsdf.pzgc.activity.home.HomeUtils;
import com.example.administrator.newsdf.pzgc.callback.AuditDetailsCallback;
import com.example.administrator.newsdf.pzgc.callback.AuditDetailsCallbackUtils;
import com.example.administrator.newsdf.pzgc.callback.AuditDetailsrefreshCallback;
import com.example.administrator.newsdf.pzgc.callback.AuditDetailsrefreshCallbackUtils;

/**
 * description:审核功能的部位详情
 *
 * @author lx
 *         date: 2018/7/4 0004 下午 3:15
 *         update: 2018/7/4 0004
 *         version:
 */

public class AuditdetailsActivity extends AppCompatActivity implements View.OnClickListener, AuditDetailsCallback, AuditDetailsrefreshCallback {

    private static AuditdetailsActivity mContext;
    private RecyclerView mRecyclerView;
    private TextView wbspath;
    private String taskId, status;
    private AuditdetailsAdapter mAdapter;
    private HomeUtils mHomeUtils;

    public static AuditdetailsActivity getInstance() {
        return mContext;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditdetails);
        Intent intent = getIntent();
        mContext = this;
        //审核界面回调
        AuditDetailsCallbackUtils.setCallBack(this);
        AuditDetailsrefreshCallbackUtils.setCallBack(this);
        mHomeUtils = new HomeUtils();
        taskId = intent.getExtras().getString("TaskId");
        status = intent.getExtras().getString("status");
        mRecyclerView = (RecyclerView) findViewById(R.id.auditdetails_list);
        wbspath = (TextView) findViewById(R.id.auditdetails_path);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new AuditdetailsAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
        findViewById(R.id.taskManagemented).setOnClickListener(this);
        findViewById(R.id.aduit_back).setOnClickListener(this);
        findViewById(R.id.Auditrecords).setOnClickListener(this);
        mHomeUtils.TaskAudit(taskId, mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.taskManagemented:
                //跳转任务管理
//                if (status.equals("true")) {
                //
                HomeUtils.getOko(mHomeUtils.hasmap.get("id"), null, false, null, false, null, AuditdetailsActivity.this);
//                }
                break;
            case R.id.aduit_back:
                //返回
                finish();
                break;
            case R.id.Auditrecords:
                //审核记录
                Intent intent = new Intent(AuditdetailsActivity.this, RecordsActivity.class);
                intent.putExtra("taskid", taskId);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * adapter获取ID
     */
    public String getId() {
        return taskId;
    }

    /**
     * 获取状态
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    @Override
    public void updata() {
        wbspath.setText(mHomeUtils.hasmap.get("wbsName"));
    }

    @Override
    public void refreshs() {
        status = "1";
        mHomeUtils.TaskAudit(taskId, mAdapter);
    }

}