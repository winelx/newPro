package com.example.administrator.newsdf.pzgc.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.activity.audit.ReportActivity;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.ChagedreplyActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckRectificationWebActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckTaskWebActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckdownMessageActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckmanagementActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckstandardListActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.newcheck.activity.ExternalCheckActiviy;
import com.example.administrator.newsdf.pzgc.activity.check.activity.record.activity.SuperviseCheckRecordActivity;
import com.example.administrator.newsdf.pzgc.activity.check.webview.CheckabfillWebActivity;
import com.example.administrator.newsdf.pzgc.activity.device.DeviceActivity;
import com.example.administrator.newsdf.pzgc.activity.home.OrgrankingActivity;
import com.example.administrator.newsdf.pzgc.activity.pchoose.activity.PchooseActivity;
import com.example.administrator.newsdf.pzgc.activity.work.NotuploadActivity;
import com.example.administrator.newsdf.pzgc.activity.work.OrganiwbsActivity;
import com.example.administrator.newsdf.pzgc.activity.work.PushCheckActivity;
import com.example.administrator.newsdf.pzgc.special.loedger.activity.LoedgerActivity;
import com.example.administrator.newsdf.pzgc.special.programme.activity.ProgrammeActivity;
import com.example.administrator.newsdf.pzgc.utils.EmptyUtils;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.baselibrary.adapter.MessageFragmentAdapter;
import com.example.baselibrary.adapter.MessageFragmentItemAdapter;
import com.example.baselibrary.bean.ItemBean;
import com.example.baselibrary.bean.bean;
import com.example.baselibrary.utils.Requests;
import com.example.baselibrary.view.EmptyRecyclerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;


/**
 * @author ：lx
 * 时间：2017/11/23 0023:下午 15:37
 * 说明：
 */
public class WorkFragment extends Fragment {
    private View rootView;
    private Context mContext;
    private ArrayList<bean> tasklist;
    private ArrayList<bean> checklist;
    private ArrayList<bean> reportlist;
    private ArrayList<bean> abfill;
    private ArrayList<bean> special;
    private EmptyRecyclerView workRecycler;
    private ArrayList<ItemBean> list;
    private MessageFragmentAdapter adapter;
    private EmptyUtils emptyUtils;
    private SwipeRefreshLayout swiprefresh;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //避免重复绘制界面
        if (rootView == null) {
            mContext = getActivity();
            rootView = inflater.inflate(R.layout.fragment_work, null);
            reportlist = new ArrayList<>();
            tasklist = new ArrayList<>();
            checklist = new ArrayList<>();
            special = new ArrayList<>();
            abfill = new ArrayList<>();
            list = new ArrayList<>();
            emptyUtils = new EmptyUtils(mContext);
            //初始化控件Id
            findId();
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            okgo();
            emptyUtils.setError(new EmptyUtils.Callback() {
                @Override
                public void callback() {
                    okgo();
                }
            });
        }
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void findId() {
        swiprefresh = rootView.findViewById(R.id.swiprefresh);
        swiprefresh.setColorSchemeResources(R.color.colorAccent, R.color.finish_green, R.color.Orange, R.color.yellow);
        swiprefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                okgo();
                swiprefresh.setRefreshing(false);
            }
        });
        workRecycler = rootView.findViewById(R.id.work_recycler);
        workRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        workRecycler.setEmptyView(emptyUtils.init());
        adapter = new MessageFragmentAdapter(mContext, list);
        workRecycler.setAdapter(adapter);
        adapter.setOnclickitemlitener(new MessageFragmentItemAdapter.onclickitemlitener() {
            @Override
            public void onclick(String str, int position) {
                switch (str) {
                    case "检查标准":
                        startActivity(new Intent(mContext, CheckstandardListActivity.class));
                        break;
                    case "回复验证":
                        startActivity(new Intent(mContext, ChagedreplyActivity.class));
                        break;
                    case "监管检查":
                        startActivity(new Intent(mContext, CheckmanagementActivity.class));
                        break;
                    case "整改通知":
                        startActivity(new Intent(mContext, CheckdownMessageActivity.class));
                        break;
                    case "特种设备":
                        startActivity(new Intent(mContext, DeviceActivity.class));
                        break;
                    case "任务管理":
                        startActivity(new Intent(mContext, OrganiwbsActivity.class));
                        break;
                    case "任务下发":
                        startActivity(new Intent(mContext, PushCheckActivity.class));
                        break;
                    case "主动任务":
                        startActivity(new Intent(mContext, NotuploadActivity.class));
                        break;
                    case "图纸标准":
                        startActivity(new Intent(mContext, PchooseActivity.class));
                        break;
                    case "任务统计":
                        startActivity(new Intent(mContext, CheckTaskWebActivity.class));
                        break;
                    case "审核报表":
                        Intent intent = new Intent(mContext, ReportActivity.class);
                        intent.putExtra("orgid", SPUtils.getString(mContext, "orgId", ""));
                        startActivity(intent);
                        break;
                    case "整改统计":
                        startActivity(new Intent(mContext, CheckRectificationWebActivity.class)
                                .putExtra("url", "http://120.79.142.15/m/"));
                        break;
                    case "标段排名":
                        startActivity(new Intent(mContext, OrgrankingActivity.class));
                        break;
                    case "台账管理":
                        startActivity(new Intent(mContext, LoedgerActivity.class));
                        break;
                    case "方案管理":
                        startActivity(new Intent(mContext, ProgrammeActivity.class));
                        break;
                    case "外业检查":
                        startActivity(new Intent(mContext, ExternalCheckActiviy.class));
                        break;
                    case "监督检查记录":
                        startActivity(new Intent(mContext, SuperviseCheckRecordActivity.class));
                        break;
                    case "A类风险":
                        startActivity(new Intent(mContext, CheckabfillWebActivity.class)
                                .putExtra("url", Requests.networks + "/h5/abfill/index.html#/atree?modelType=4"));
//                        startActivity(new Intent(mContext, CheckRectificationWebActivity.class)
//                                .putExtra("url", Requests.networks + "/h5/taskcheck/index.html#/"));
                        break;
                    case "B类风险":
                        startActivity(new Intent(mContext, CheckabfillWebActivity.class)
                                .putExtra("url", Requests.networks + "/h5/abfill/index.html#/atree?modelType=3"));


                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void okgo() {
        OkGo.get(Requests.getMenu)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray data = jsonObject.getJSONArray("data");
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                list.clear();
                                reportlist.clear();
                                tasklist.clear();
                                checklist.clear();
                                special.clear();
                                abfill.clear();
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject json = data.getJSONObject(i);
                                    if ("true".equals(json.getString("审核报表"))) {
                                        reportlist.add(new bean("审核报表", R.mipmap.check_statistical));
                                    }
                                    if ("true".equals(json.getString("任务统计"))) {
                                        reportlist.add(new bean("任务统计", R.mipmap.fr_work_statistical));
                                    }
                                    if ("true".equals(json.getString("整改统计"))) {
                                        reportlist.add(new bean("整改统计", R.mipmap.fr_work_rectification));
                                    }
                                    if ("true".equals(json.getString("标段排名"))) {
                                        reportlist.add(new bean("标段排名", R.mipmap.fr_work_ranking));
                                    }
                                    if ("true".equals(json.getString("任务管理"))) {
                                        tasklist.add(new bean("任务管理", R.mipmap.fr_work_miss));
                                    }
                                    if ("true".equals(json.getString("任务下发"))) {
                                        tasklist.add(new bean("任务下发", R.mipmap.fr_work_push));
                                    }
                                    if ("true".equals(json.getString("图纸标准"))) {
                                        tasklist.add(new bean("图纸标准", R.mipmap.fr_work_photo));
                                    }
                                    if ("true".equals(json.getString("主动任务"))) {
                                        tasklist.add(new bean("主动任务", R.mipmap.fr_work_upload));
                                    }
                                    if ("true".equals(json.getString("检查标准"))) {
                                        checklist.add(new bean("检查标准", R.mipmap.check_standard));
                                    }
                                    if ("true".equals(json.getString("监管检查"))) {
                                        checklist.add(new bean("监管检查", R.mipmap.check_management));
                                    }
                                    if ("true".equals(json.getString("整改通知"))) {
                                        checklist.add(new bean("整改通知", R.mipmap.check_notice));
                                    }
                                    if ("true".equals(json.getString("回复验证"))) {
                                        checklist.add(new bean("回复验证", R.mipmap.reply_verification));
                                    }
                                    if ("true".equals(json.getString("特种设备"))) {
                                        checklist.add(new bean("特种设备", R.mipmap.specialdevices));
                                    }
                                    if ("true".equals(json.getString("外业检查"))) {
                                        checklist.add(new bean("外业检查", R.mipmap.reply_external));
                                    }
                                    if ("true".equals(json.getString("台账管理"))) {
                                        special.add(new bean("台账管理", R.mipmap.loedger));
                                    }
                                    if ("true".equals(json.getString("方案管理"))) {
                                        special.add(new bean("方案管理", R.mipmap.programme));
                                    }
                                    if ("true".equals(json.getString("监督检查记录"))) {
                                        special.add(new bean("监督检查记录", R.mipmap.work_check_record));
                                    }
                                    if ("true".equals(json.getString("A类风险"))) {
                                        abfill.add(new bean("A类风险", R.mipmap.alei));
                                    }
                                    if ("true".equals(json.getString("B类风险"))) {
                                        abfill.add(new bean("B类风险", R.mipmap.blei));
                                    }

                                }

                                if (tasklist.size() > 0) {
                                    list.add(new ItemBean(tasklist, "任务管理"));
                                }
                                if (checklist.size() > 0) {
                                    list.add(new ItemBean(checklist, "检查管理"));
                                }
                                if (reportlist.size() > 0) {
                                    list.add(new ItemBean(reportlist, "统计报表"));
                                }
                                if (special.size() > 0) {
                                    list.add(new ItemBean(special, "专项施工方案"));
                                }
                                if (abfill.size() > 0) {
                                    list.add(new ItemBean(abfill, "风险管控清单记录"));
                                }
                                adapter.setNewData(list);
                            } else {
                                ToastUtils.showShortToast(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                    }
                });
    }

}