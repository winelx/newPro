package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.adapter.ExternalCheckListAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.utils.ExternalModel;
import com.example.administrator.newsdf.pzgc.utils.PullDownMenu;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.utils.network.NetworkAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 说明：外业检查：标段检查单列表
 * 创建时间： 2020/6/23 0023 15:16
 *
 * @author winelx
 */
public class ExternalCheckListActiviy extends BaseActivity implements View.OnClickListener {
    private ImageView comImg, list_add;
    private TextView com_title;
    private LinearLayout checklistmeun;
    private String[] strings = {"保存", "待分公司核查", "待集团核查", "待分公司确认", "待标段确", "已确认"};
    private PullDownMenu pullDownMenu = new PullDownMenu();
    private SmartRefreshLayout refreshlayout;
    private RecyclerView recycler;
    private Context mContext;
    private ExternalCheckListAdapter checkListAdapter;
    private ExternalModel externalModel;
    private String orgid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_check_external_list);
        mContext = this;
        externalModel=new ExternalModel();
        Intent intent = getIntent();
        orgid=intent.getStringExtra("orgid");
        comImg = findViewById(R.id.com_img);
        com_title = findViewById(R.id.com_title);
        com_title.setText(intent.getStringExtra("orgName"));
        list_add = findViewById(R.id.list_add);
        list_add.setOnClickListener(this);
        comImg.setBackgroundResource(R.mipmap.meun);
        checklistmeun = findViewById(R.id.toolbar_menu);
        checklistmeun.setOnClickListener(this);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(mContext));
        List<String> lsit = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            lsit.add("ss");
        }
        checkListAdapter = new ExternalCheckListAdapter(R.layout.adapter_item_externalchecklist, lsit);
        recycler.setAdapter(checkListAdapter);
        refreshlayout = findViewById(R.id.refreshlayout);
        checkListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.content_re) {
                    Intent intent = new Intent(mContext, NewExternalCheckActiviy.class);
                    intent.putExtra("status", false);
                    startActivity(intent);
                }
            }
        });
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.toolbar_menu) {
//strings： 展示内容数组    例子：  String[] strings = {"征拆类型查询", "按区域查询", "按表单号查询", "按户主明细查询", "按期数查询"};
            pullDownMenu.showPopMeun(this, checklistmeun, strings);
            pullDownMenu.setOnItemClickListener(new PullDownMenu.OnItemClickListener() {
                @Override
                public void onclick(int position, String string) {

                }
            });
        } else if (i == R.id.list_add) {
            Intent intent = new Intent(mContext, NewExternalCheckActiviy.class);
            intent.putExtra("status", true);
            startActivity(intent);
        }
    }

    public void getsafetychecklistbyapp(){
        Map<String,String>map=new HashMap<>();
        map.put("orgId",orgid);
        map.put("page","");
        map.put("rows","");
        map.put("status","");
        externalModel.getsafetychecklistbyapp(map,new NetworkAdapter(){
            @Override
            public void onsuccess(Object object) {
                super.onsuccess(object);
            }

            @Override
            public void onerror(String string) {
                super.onerror(string);
            }
        });
    }
}
