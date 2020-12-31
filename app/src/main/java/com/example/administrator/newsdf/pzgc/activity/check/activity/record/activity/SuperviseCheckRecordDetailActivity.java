package com.example.administrator.newsdf.pzgc.activity.check.activity.record.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.activity.record.bean.RecordDetailBean;
import com.example.administrator.newsdf.pzgc.activity.check.activity.record.utils.RecodModel;
import com.example.administrator.newsdf.pzgc.adapter.FiletypeAdapter;
import com.example.administrator.newsdf.pzgc.bean.FileTypeBean;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.utils.Requests;
import com.example.baselibrary.utils.network.NetworkAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明：监督检查记录详情
 * 创建时间： 2020/7/30 0030 10:05
 *
 * @author winelx
 */
public class SuperviseCheckRecordDetailActivity extends BaseActivity implements View.OnClickListener {
    private RecodModel recodModel;
    private RecyclerView recyclerview;
    private FiletypeAdapter fileAdapter;
    private Intent intent;
    private TextView code, checkProject, checkTime, checkBid, checkOrg, responsibilityPart, becheckpersion, checkUser;
    private TextView problem, explanation, com_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);
        findViewById(R.id.com_back).setOnClickListener(this);
        com_title = findViewById(R.id.com_title);
        com_title.setText("详情");
        recodModel = new RecodModel();
        intent = getIntent();
        intent.getStringExtra("id");
        code = findViewById(R.id.code);
        checkProject = findViewById(R.id.check_project);
        checkTime = findViewById(R.id.check_time);
        checkBid = findViewById(R.id.check_bid);
        checkOrg = findViewById(R.id.check_org);
        responsibilityPart = findViewById(R.id.responsibilityPart);
        becheckpersion = findViewById(R.id.becheckpersion);
        checkUser = findViewById(R.id.check_user);
        problem = findViewById(R.id.problem);
        explanation = findViewById(R.id.explanation);
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new GridLayoutManager(mContext, 4));
        fileAdapter = new FiletypeAdapter(mContext, new ArrayList<>());
        recyclerview.setAdapter(fileAdapter);
        rquest();
    }

    public void rquest() {
        recodModel.getspecialcheckrecordbyapp(intent.getStringExtra("id"), new NetworkAdapter() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onsuccess(Object object) {
                super.onsuccess(object);
                RecordDetailBean bean = (RecordDetailBean) object;
                code.setText(Utils.isNull(bean.getCode()));
                checkProject.setText("项目名称：" + Utils.isNull(bean.getOrgName()));
                checkTime.setText("检查日期：" + Utils.isNull(bean.getCheckDate()));
                checkBid.setText("检查组织：" + Utils.isNull(bean.getCheckOrgName()));
                checkOrg.setText("检查部门：" + Utils.isNull(bean.getCheckPart()));
                responsibilityPart.setText("责任部门：" + Utils.isNull(bean.getResponsibilityPart()));
                checkUser.setText(Utils.isNull(bean.getCheckPersion()));
                becheckpersion.setText(Utils.isNull(bean.getBeCheckPersion()));
                problem.setText(Utils.isNull(bean.getProblem()));
                explanation.setText(Utils.isNull(bean.getExplanation()));
                fileAdapter.getData(setPhoto(bean.getAttachmentList()));
            }

            @Override
            public void onsuccess(String string) {
                super.onsuccess(string);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.com_back) {
            finish();
        }
    }

    public ArrayList<FileTypeBean> setPhoto(List<RecordDetailBean.AttachmentListBean> photolist) {
        ArrayList<FileTypeBean> list = new ArrayList<>();
        if (photolist != null) {
            for (int i = 0; i < photolist.size(); i++) {
                RecordDetailBean.AttachmentListBean bean = photolist.get(i);
                list.add(new FileTypeBean(bean.getFilename(), Requests.networks + bean.getFilepath(), bean.getFileext()));
            }
        }
        return list;
    }
}
