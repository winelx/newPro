package com.example.administrator.newsdf.pzgc.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.audit.ReportActivity;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.ChagedreplyActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckRectificationWebActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckReportActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckTaskWebActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckdownMessageActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckmanagementActivity;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckstandardListActivity;
import com.example.administrator.newsdf.pzgc.activity.device.DeviceActivity;
import com.example.administrator.newsdf.pzgc.activity.work.NotuploadActivity;
import com.example.administrator.newsdf.pzgc.activity.work.OrganiwbsActivity;
import com.example.administrator.newsdf.pzgc.activity.work.PushCheckActivity;
import com.example.administrator.newsdf.pzgc.activity.work.pchoose.PchooseActivity;
import com.example.administrator.newsdf.pzgc.utils.EmptyUtils;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;
import com.example.baselibrary.view.EmptyRecyclerView;
import com.example.baselibrary.adapter.MessageFragmentAdapter;
import com.example.baselibrary.adapter.MessageFragmentItemAdapter;
import com.example.baselibrary.bean.ItemBean;
import com.example.baselibrary.bean.bean;
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
    private EmptyRecyclerView workRecycler;
    private ArrayList<ItemBean> list;
    private MessageFragmentAdapter adapter;
    private EmptyUtils emptyUtils;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//避免重复绘制界面
        if (rootView == null) {
            mContext = getActivity();
            rootView = inflater.inflate(R.layout.fragment_work, null);
            reportlist = new ArrayList<>();
            tasklist = new ArrayList<>();
            checklist = new ArrayList<>();
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
                        intent.putExtra("orgId", SPUtils.getString(mContext, "orgId", ""));
                        startActivity(intent);
                        break;
                    case "整改统计":
                        startActivity(new Intent(mContext, CheckRectificationWebActivity.class));
                        break;
                    case "标段排名":
                        startActivity(new Intent(mContext, CheckReportActivity.class));
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
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject json = data.getJSONObject(i);
                                    String str4 = json.getString("任务统计");
                                    String str6 = json.getString("审核报表");
                                    String str7 = json.getString("整改统计");
                                    String str9 = json.getString("标段排名");
                                    if ("true".equals(str6)) {
                                        reportlist.add(new bean("审核报表", R.mipmap.check_statistical));
                                    }
                                    if ("true".equals(str4)) {
                                        reportlist.add(new bean("任务统计", R.mipmap.fr_work_statistical));
                                    }

                                    if ("true".equals(str7)) {
                                        reportlist.add(new bean("整改统计", R.mipmap.fr_work_rectification));
                                    }
                                    if ("true".equals(str9)) {
                                        reportlist.add(new bean("标段排名", R.mipmap.fr_work_ranking));
                                    }

                                    String str3 = json.getString("任务管理");
                                    String str = json.getString("主动任务");
                                    String str2 = json.getString("任务下发");
                                    String str5 = json.getString("图纸标准");
                                    if ("true".equals(str3)) {
                                        tasklist.add(new bean("任务管理", R.mipmap.fr_work_miss));
                                    }
                                    if ("true".equals(str2)) {
                                        tasklist.add(new bean("任务下发", R.mipmap.fr_work_push));
                                    }
                                    if ("true".equals(str5)) {
                                        tasklist.add(new bean("图纸标准", R.mipmap.fr_work_photo));
                                    }
                                    if ("true".equals(str)) {
                                        tasklist.add(new bean("主动任务", R.mipmap.fr_work_upload));
                                    }

                                    String str11 = json.getString("监管检查");
                                    String str8 = json.getString("整改通知");
                                    String device;
                                    String reply = json.getString("回复验证");
                                    try {
                                        device = json.getString("特种设备");
                                    } catch (Exception e) {
                                        device = "";
                                    }
                                    if ("true".equals(json.getString("检查标准"))) {
                                        checklist.add(new bean("检查标准", R.mipmap.check_standard));
                                    }
                                    if ("true".equals(str11)) {
                                        checklist.add(new bean("监管检查", R.mipmap.check_management));
                                    }
                                    if ("true".equals(str8)) {
                                        checklist.add(new bean("整改通知", R.mipmap.check_notice));
                                    }
                                    if ("true".equals(reply)) {
                                        checklist.add(new bean("回复验证", R.mipmap.reply_verification));
                                    }
                                    if ("true".equals(device)) {
                                        checklist.add(new bean("特种设备", R.mipmap.specialdevices));
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