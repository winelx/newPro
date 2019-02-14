package com.example.administrator.newsdf.pzgc.activity.changed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.changed.adapter.ChangedNewAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckuserActivity;
import com.example.administrator.newsdf.pzgc.activity.mine.OrganizationaActivity;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;
import com.example.administrator.newsdf.pzgc.utils.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/1/30 0030}
 * 描述：修改后的新增整改通知单
 * {@link }
 */
public class ChangedNewActivity extends BaseActivity implements View.OnClickListener {
    private TextView chagedNumber, comTitle, chaged_release_people, chaged_release_org;
    private RecyclerView recycler;
    private List<String> list;
    private Context mContext;
    private LinearLayout problemItemLin;
    private ChangedNewAdapter adapter;
    private TextView comButton;
    public boolean STATUS = true;
    public static final String KEEP = "保存";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_new);
        mContext = this;
        list = new ArrayList<>();
        problemItemLin = (LinearLayout) findViewById(R.id.problem_item_lin);
        problemItemLin.setVisibility(View.GONE);
        //下发人
        chaged_release_people = (TextView) findViewById(R.id.chaged_release_people);
        chaged_release_people.setText(SPUtils.getString(mContext, "staffName", ""));
        //下发组织
        chaged_release_org = (TextView) findViewById(R.id.chaged_release_org);
        chaged_release_org.setText(SPUtils.getString(mContext, "username", ""));
        //标题
        comTitle = (TextView) findViewById(R.id.com_title);
        comTitle.setText("新增整改");
        //菜单
        comButton = (TextView) findViewById(R.id.com_button);
        comButton.setText("保存");
        comButton.setOnClickListener(this);
        //返回
        findViewById(R.id.com_back).setOnClickListener(this);
        //下发
        findViewById(R.id.chaged_release_problem).setOnClickListener(this);
        //导入问题项
        findViewById(R.id.chaged_import_problem).setOnClickListener(this);
        //添加问题项
        findViewById(R.id.chaged_add_problem).setOnClickListener(this);
        //整改负责人
        findViewById(R.id.chaged_head_lin).setOnClickListener(this);
        //下发人
        findViewById(R.id.chaged_release_lin).setOnClickListener(this);
        //整改组织
        findViewById(R.id.chaged_organize_lin).setOnClickListener(this);
        //问题项
        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new SimpleDividerItemDecoration(mContext));
        adapter = new ChangedNewAdapter(R.layout.adapter_item_chaged_new, list);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, ChagedProblemitemActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                finish();
                break;
            case R.id.chaged_release_problem:
                if (KEEP.equals(comButton.getText().toString())) {

                } else {
                    //下发
                    ToastUtils.showShortToast("下发");
                }

                startActivity(new Intent(mContext, ChagedListActivity.class));
                break;
            case R.id.chaged_import_problem:
                if (STATUS) {
                    ToastUtils.showShortToast("导入问题项");
                    startActivity(new Intent(mContext, ChagedImportitemActivity.class));
                } else {
                    ToastUtils.showShortToast("不是编辑状态无法操作");
                }
                break;
            case R.id.chaged_add_problem:
                startActivity(new Intent(mContext, ChagedProblemitemActivity.class));
                break;
            case R.id.chaged_head_lin:
                if (STATUS) {
                    Intent intent1 = new Intent(mContext, CheckuserActivity.class);
                    intent1.putExtra("orgId", "");
                    startActivityForResult(intent1, 5);
                } else {
                    ToastUtils.showShortToast("不是编辑状态无法操作");
                }
                break;
            case R.id.chaged_release_lin:
                if (STATUS) {
                    ToastUtils.showShortToast("下发人");
                } else {
                    ToastUtils.showShortToast("不是编辑状态无法操作");
                }
                break;
            case R.id.chaged_organize_lin:
                if (STATUS) {
                    Intent intent12 = new Intent(mContext, OrganizationaActivity.class);
                    intent12.putExtra("title", "选择标段");
                    intent12.putExtra("data", "Rectifi");
                    startActivityForResult(intent12, 3);
                } else {
                    ToastUtils.showShortToast("不是编辑状态无法操作");
                }
                break;
            case R.id.com_button:
                if (KEEP.equals(comButton.getText().toString())) {
                    comButton.setText("编辑");
                    STATUS = false;
                    problemItemLin.setVisibility(View.VISIBLE);
                } else {
                    comButton.setText(KEEP);
                    STATUS = true;
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == 2) {
            //选择负责人
//            userId = data.getStringExtra("id");
//            userName = data.getStringExtra("name");
//            check_rectifi_font.setText(userName);
        } else if (requestCode == 3 && resultCode == 2) {
            /*OrgId = data.getStringExtra("id");
            orgName = data.getStringExtra("name");
            checkRectifiWbs.setText(orgName);*/
        }
    }
}
