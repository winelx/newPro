package com.example.administrator.newsdf.pzgc.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.SettingAdapter;
import com.example.administrator.newsdf.pzgc.activity.audit.ReportActivity;
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
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.utils.LogUtil;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;
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
 *         时间：2017/11/23 0023:下午 15:37
 *         说明：
 */
public class WorkFragment extends Fragment {
    private View rootView;
    private Context mContext;
    private ArrayList<Audio> tasklist;
    private ArrayList<Audio> checklist;
    private ArrayList<Audio> reportlist;
    private GridView checkmanager, taskmanager, reportmanager;
    private SettingAdapter taskAdapter;
    private SettingAdapter checkAdapter;
    private SettingAdapter reportAdapter;
    private LinearLayout testmanager, card_list, reportlin;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//避免重复绘制界面
        if (rootView == null) {
            mContext = getActivity();
            rootView = inflater.inflate(R.layout.fragment_work, null);
            reportlist = new ArrayList<>();
            tasklist = new ArrayList<>();
            checklist = new ArrayList<>();
            //初始化控件Id
            findId();
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }

            Analysis();
            okgo();
            checkmanager.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String name = checklist.get(i).getName();
                    switch (name) {
                        case "检查标准":
                            //检查标准
                            startActivity(new Intent(mContext, CheckstandardListActivity.class));
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
                        default:
                    }
                }
            });
            taskmanager.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String name = tasklist.get(i).getName();
                    switch (name) {
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
                        default:
                    }
                }
            });
            reportmanager.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String name = reportlist.get(i).getName();
                    switch (name) {
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
                    }
                }
            });

        }
        return rootView;
    }

    private void findId() {
        checkmanager = rootView.findViewById(R.id.checkmanager);
        taskmanager = rootView.findViewById(R.id.taskmanager);
        reportmanager = rootView.findViewById(R.id.reportmanager);
        testmanager = rootView.findViewById(R.id.testmanager);
        card_list = rootView.findViewById(R.id.card_list);
        reportlin = rootView.findViewById(R.id.reportlin);
    }


    public void okgo() {
        OkGo.get(Requests.getMenu)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LogUtil.i("ss", s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray data = jsonObject.getJSONArray("data");
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject json = data.getJSONObject(i);
                                    String str4 = json.getString("任务统计");
                                    String str6 = json.getString("审核报表");
                                    String str7 = json.getString("整改统计");
                                    String str9 = json.getString("标段排名");
                                    if ("true".equals(str6)) {
                                        reportlist.add(new Audio("审核报表", str6));
                                    }
                                    if ("true".equals(str4)) {
                                        reportlist.add(new Audio("任务统计", str4));
                                    }

                                    if ("true".equals(str7)) {
                                        reportlist.add(new Audio("整改统计", str7));
                                    }
                                    if ("true".equals(str9)) {
                                        reportlist.add(new Audio("标段排名", str9));
                                    }

                                    String str3 = json.getString("任务管理");
                                    String str = json.getString("主动任务");
                                    String str2 = json.getString("任务下发");
                                    String str5 = json.getString("图纸标准");
                                    if ("true".equals(str3)) {
                                        tasklist.add(new Audio("任务管理", str3));
                                    }
                                    if ("true".equals(str2)) {
                                        tasklist.add(new Audio("任务下发", str2));
                                    }
                                    if ("true".equals(str5)) {
                                        tasklist.add(new Audio("图纸标准", str5));
                                    }
                                    if ("true".equals(str)) {
                                        tasklist.add(new Audio("主动任务", str));
                                    }

                                    String str10 = json.getString("检查标准");
                                    String str11 = json.getString("监管检查");
                                    String str8 = json.getString("整改通知");
                                    if ("true".equals(str10)) {
                                        checklist.add(new Audio("检查标准", str10));
                                    }
                                    if ("true".equals(str11)) {
                                        checklist.add(new Audio("监管检查", str11));
                                    }
                                    if ("true".equals(str8)) {
                                        checklist.add(new Audio("整改通知", str8));
                                        checklist.add(new Audio("特种设备", "true"));
                                    }

                                }
                                if (tasklist.size() > 0) {
                                    taskAdapter.getData(tasklist);
                                } else {
                                    testmanager.setVisibility(View.GONE);
                                }
                                if (checklist.size() > 0) {
                                    checkAdapter.getData(checklist);
                                } else {
                                    card_list.setVisibility(View.GONE);
                                }
                                if (reportlist.size() > 0) {
                                    reportAdapter.getData(reportlist);
                                } else {
                                    reportlin.setVisibility(View.GONE);
                                }
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
                        if (tasklist.size() > 0) {
                            taskAdapter.getData(tasklist);
                        } else {
                            testmanager.setVisibility(View.GONE);
                        }
                        if (checklist.size() > 0) {
                            checkAdapter.getData(checklist);
                        } else {
                            card_list.setVisibility(View.GONE);
                        }
                        if (reportlist.size() > 0) {
                            reportAdapter.getData(reportlist);
                        } else {
                            reportlin.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void Analysis() {
        //检查
        checkAdapter = new SettingAdapter<Audio>(checklist, R.layout.fragment_work_item) {
            @Override
            public void bindView(ViewHolder holder, Audio obj) {
                holder.setText(R.id.item_content, obj.getName());
                String str = obj.getName();
                switch (str) {
                    case "检查标准":
                        holder.setImageResource(R.id.item_iamge, R.mipmap.check_standard);
                        break;
                    case "监管检查":
                        holder.setImageResource(R.id.item_iamge, R.mipmap.check_management);
                        break;
                    case "整改通知":
                        holder.setImageResource(R.id.item_iamge, R.mipmap.check_notice);
                        break;
                    default:
                        break;
                }
            }
        };
        //统计
        reportAdapter = new SettingAdapter<Audio>(reportlist, R.layout.fragment_work_item) {
            @Override
            public void bindView(ViewHolder holder, Audio obj) {
                holder.setText(R.id.item_content, obj.getName());
                String str = obj.getName();
                switch (str) {
                    case "审核报表":
                        holder.setImageResource(R.id.item_iamge, R.mipmap.check_statistical);
                        break;
                    case "任务统计":
                        holder.setImageResource(R.id.item_iamge, R.mipmap.fr_work_statistical);
                        break;
                    case "整改统计":
                        holder.setImageResource(R.id.item_iamge, R.mipmap.fr_work_rectification);
                        break;
                    case "标段排名":
                        holder.setImageResource(R.id.item_iamge, R.mipmap.fr_work_ranking);
                        break;
                    default:
                        break;
                }
            }
        };
        //任务
        taskAdapter = new SettingAdapter<Audio>(tasklist, R.layout.fragment_work_item) {
            @Override
            public void bindView(ViewHolder holder, Audio obj) {
                holder.setText(R.id.item_content, obj.getName());
                String str = obj.getName();
                switch (str) {
                    case "任务管理":
                        holder.setImageResource(R.id.item_iamge, R.mipmap.fr_work_miss);
                        break;
                    case "任务下发":
                        holder.setImageResource(R.id.item_iamge, R.mipmap.fr_work_push);
                        break;
                    case "图纸标准":
                        holder.setImageResource(R.id.item_iamge, R.mipmap.fr_work_photo);
                        break;
                    case "主动任务":
                        holder.setImageResource(R.id.item_iamge, R.mipmap.fr_work_upload);
                        break;
                    default:
                        break;
                }
            }
        };
        taskmanager.setAdapter(taskAdapter);
        checkmanager.setAdapter(checkAdapter);
        reportmanager.setAdapter(reportAdapter);
    }
}