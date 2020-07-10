package com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.activity;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.adapter.ExternalCheckListAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.Enum;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.ExternalCheckListBean;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.utils.ExternalModel;
import com.example.administrator.newsdf.pzgc.utils.PullDownMenu;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.inface.Onclicklitener;
import com.example.baselibrary.utils.network.NetworkAdapter;
import com.example.baselibrary.utils.rx.LiveDataBus;
import com.example.baselibrary.view.BaseDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
    private String[] strings = {"全部", "保存", "待分公司核查", "待集团核查", "待分公司确认", "待标段确", "已确认"};
    private PullDownMenu pullDownMenu = new PullDownMenu();
    private SmartRefreshLayout refreshlayout;
    private RecyclerView recycler;
    private Context mContext;
    private ExternalCheckListAdapter checkListAdapter;
    private ExternalModel externalModel;
    private String orgid;
    private List<ExternalCheckListBean> list;
    private int page = 1;
    private String status = "全部";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_check_external_list);
        mContext = this;
        list = new ArrayList<>();
        externalModel = new ExternalModel();
        Intent intent = getIntent();
        orgid = intent.getStringExtra("orgid");
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
        checkListAdapter = new ExternalCheckListAdapter(R.layout.adapter_item_externalchecklist, new ArrayList<>());
        recycler.setAdapter(checkListAdapter);
        refreshlayout = findViewById(R.id.refreshlayout);
        //是否在列表不满一页时候开启上拉加载功能
        refreshlayout.setEnableLoadmoreWhenContentNotFull(false);
        //下拉刷新
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                page = 1;
                getsafetychecklistbyapp();
                refreshlayout.finishRefresh(true);
            }
        });
        //上拉加载更多
        refreshlayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getsafetychecklistbyapp();
                refreshlayout.finishLoadmore(true);
            }
        });
        checkListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ExternalCheckListBean bean = (ExternalCheckListBean) adapter.getData().get(position);
                if (view.getId() == R.id.content_re) {
                    Intent intent = new Intent(mContext, NewExternalCheckActiviy.class);
                    intent.putExtra("isNew", "编辑");
                    intent.putExtra("status", bean.getStatus() + "");
                    intent.putExtra("id", bean.getId());
                    intent.putExtra("orgname", com_title.getText().toString());
                    intent.putExtra("orgid", orgid);
                    startActivity(intent);
                } else if (view.getId() == R.id.item_delete) {
                    BaseDialog.confirmdialog(mContext, "是否删除检查单", "", new Onclicklitener() {
                        @Override
                        public void confirm(String string) {
                            deletesafetycheckbyapp(bean.getId(), position);
                        }

                        @Override
                        public void cancel(String string) {

                        }
                    });
                }
            }
        });
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LiveDataBus.get().with("ex_list", String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                page = 1;
                getsafetychecklistbyapp();
            }
        });
        LiveDataBus.get().with("ex_list", String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                page = 1;
                getsafetychecklistbyapp();
            }
        });
        getsafetychecklistbyapp();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.toolbar_menu) {
            pullDownMenu.showPopMeun(this, checklistmeun, strings);
            pullDownMenu.setOnItemClickListener(new PullDownMenu.OnItemClickListener() {
                @Override
                public void onclick(int position, String string) {
                    //0：保存；2：待分公司核查；3：待集团核查；4：待分公司确认；5：待标段确认；6：已确认
                    switch (string) {
                        case "全部":
                            status = "全部";
                            break;
                        case "保存":
                            status = "0";
                            break;
                        case "待分公司核查":
                            status = "2";
                            break;
                        case "待集团核查":
                            status = "3";
                            break;
                        case "待分公司确认":
                            status = "4";
                            break;
                        case "待标段确":
                            status = "5";
                            break;
                        case "已确认":
                            status = "6";
                            break;
                        default:
                            break;
                    }
                    page = 1;
                    getsafetychecklistbyapp();
                }
            });
        } else if (i == R.id.list_add) {
            Intent intent = new Intent(mContext, NewExternalCheckActiviy.class);
            intent.putExtra("status", "新增");
            intent.putExtra("orgname", com_title.getText().toString());
            intent.putExtra("orgid", orgid);
            startActivity(intent);
        }
    }

    public void getsafetychecklistbyapp() {
        Map<String, String> map = new HashMap<>();
        map.put("orgId", orgid);
        map.put("page", page + "");
        map.put("rows", 20 + "");
        if (!Enum.All.equals(status)) {
            map.put("status", status);
        }
        externalModel.getsafetychecklistbyapp(map, new NetworkAdapter() {
            @Override
            public void onsuccess(Object object) {
                super.onsuccess(object);
                if (page == 1) {
                    list.clear();
                }
                list.addAll((List<ExternalCheckListBean>) object);
                checkListAdapter.setNewData(list);
            }

            @Override
            public void onerror(String string) {
                super.onerror(string);
            }
        });
    }


    private void deletesafetycheckbyapp(String id, int pos) {
        externalModel.deletesafetycheckbyapp(id, new NetworkAdapter() {
            @Override
            public void onsuccess() {
                super.onsuccess();
                list.remove(pos);
                checkListAdapter.setNewData(list);
            }
        });
    }
}
