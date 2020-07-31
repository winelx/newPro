package com.example.administrator.newsdf.pzgc.activity.check.activity.record.activity;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.activity.NewExternalCheckActiviy;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.adapter.ExternalCheckListAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.Enum;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.bean.ExternalCheckListBean;
import com.example.administrator.newsdf.pzgc.activity.check.activity.record.adapter.SuperviseCheckRecordListAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.record.bean.RecordlistBean;
import com.example.administrator.newsdf.pzgc.activity.check.activity.record.utils.RecodModel;
import com.example.administrator.newsdf.pzgc.utils.EmptyUtils;
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
 * 说明：监督检查记录列表
 * 创建时间： 2020/7/30 0030 10:05
 *
 * @author winelx
 */
public class SuperviseCheckRecordListActivity extends BaseActivity implements View.OnClickListener {
    private ImageView comImg, listAdd;
    private TextView comTitle;
    private LinearLayout checklistmeun;
    private String[] strings = {"全部", "保存", "已提交"};
    private PullDownMenu pullDownMenu = new PullDownMenu();
    private SmartRefreshLayout refreshlayout;
    private RecyclerView recycler;
    private Context mContext;
    private SuperviseCheckRecordListAdapter checkListAdapter;
    private String orgid;
    private List<RecordlistBean> list;
    private int page = 1;
    private String status = "全部";
    private RecodModel recodModel;
    private EmptyUtils emptyUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_check_external_list);
        mContext = this;
        recodModel = new RecodModel();
        list = new ArrayList<>();
        emptyUtils = new EmptyUtils(mContext);
        findViewById(R.id.com_back).setOnClickListener(this);
        findViewById(R.id.list_add).setOnClickListener(this);
        Intent intent = getIntent();
        orgid = intent.getStringExtra("orgid");
        comImg = findViewById(R.id.com_img);
        comTitle = findViewById(R.id.com_title);
        comTitle.setText(intent.getStringExtra("orgName"));
        listAdd = findViewById(R.id.list_add);
        listAdd.setOnClickListener(this);
        comImg.setBackgroundResource(R.mipmap.meun);
        checklistmeun = findViewById(R.id.toolbar_menu);
        checklistmeun.setOnClickListener(this);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(mContext));
        checkListAdapter = new SuperviseCheckRecordListAdapter(R.layout.adapter_item_recordchecklist, new ArrayList<>());
        checkListAdapter.setEmptyView(emptyUtils.init());
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
                RecordlistBean bean = (RecordlistBean) adapter.getData().get(position);
                if (view.getId() == R.id.content_re) {
                    if (bean.getStatus().equals("0")){
                        Intent intent = new Intent(mContext, NewRecordCheckActiviy.class);
                        intent.putExtra("orgname", comTitle.getText().toString());
                        intent.putExtra("orgid", orgid);
                        intent.putExtra("id", bean.getId());
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(mContext, SuperviseCheckRecordDetailActivity.class);
                        intent.putExtra("id", bean.getId());
                        startActivity(intent);
                    }

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
        checkListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

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
            backgroundAlpha(0.5f);
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
                        case "已提交":
                            status = "1";
                            break;
                        default:
                            break;
                    }
                    page = 1;
                    getsafetychecklistbyapp();
                }
            });
        } else if (i == R.id.list_add) {
            Intent intent = new Intent(mContext, NewRecordCheckActiviy.class);
            intent.putExtra("orgname", comTitle.getText().toString());
            intent.putExtra("orgid", orgid);
            startActivity(intent);
        } else if (i == R.id.com_back) {
            finish();
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
        recodModel.getSafetyCheckListByApp(map, new NetworkAdapter() {
            @Override
            public void onsuccess(Object object) {
                super.onsuccess(object);
                if (page == 1) {
                    list.clear();
                }
                list.addAll((List<RecordlistBean>) object);
                checkListAdapter.setNewData(list);
                if (list.size() == 0) {
                    emptyUtils.noData("暂无数据，切换选项试试");
                }
            }

            @Override
            public void onerror(String string) {
                super.onerror(string);
            }
        });
    }

    /**
     * 说明：删除检查单
     * 创建时间： 2020/7/30 0030 9:40
     *
     * @author winelx
     */
    private void deletesafetycheckbyapp(String id, int pos) {
        recodModel.deleteSpecialCheckRecordByApp(id, new NetworkAdapter() {
            @Override
            public void onsuccess() {
                super.onsuccess();
                list.remove(pos);
                checkListAdapter.setNewData(list);
            }

            @Override
            public void onerror(String string) {
                super.onerror(string);
                ToastUtils.showShortToast(string);
            }
        });
    }

    //界面亮度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }
}