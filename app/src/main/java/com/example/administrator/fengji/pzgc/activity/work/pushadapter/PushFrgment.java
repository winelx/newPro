package com.example.administrator.fengji.pzgc.activity.work.pushadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.fengji.R;
import com.example.administrator.fengji.pzgc.utils.ToastUtils;
import com.example.administrator.fengji.pzgc.adapter.PushfragmentAdapter;
import com.example.administrator.fengji.pzgc.activity.work.ContactPeopleActivity;
import com.example.administrator.fengji.pzgc.activity.work.MissionpushActivity;
import com.example.administrator.fengji.pzgc.activity.work.PushdialogActivity;
import com.example.administrator.fengji.pzgc.bean.Push_item;
import com.example.administrator.fengji.pzgc.utils.Dates;
import com.example.baselibrary.utils.Requests;
import com.example.administrator.fengji.pzgc.utils.WbsDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author ：winelx
 *         时间：2017/12/2 0002:下午 16:49
 *         说明：
 */
@SuppressLint("ValidFragment")
public class PushFrgment extends Fragment implements BaseFragmentPagerAdapter.UpdateAble {
    private int pos = 0;
    private Context mContext;
    private ArrayList<Push_item> data = null;
    View view;
    private ListView mContentRlv;
    private RelativeLayout pushImg;
    //定义控件
    private TextView pushImgText;
    private ImageView pushImgNonew;
    /**
     * 定义自定义适配器
     */
    private PushfragmentAdapter myAdapter;
    /**
     * 存放选中项的集合
     */
    private List<String> deleSelect;
    String
            //内容
            content,
    //ID
    id,
    //标签
    label,
    //推送人
    leaderName,
    //推送时间
    sendTime,
    //推送次数
    sendTimes,
    //前置项
    preconditionsName,
    //用户ID
    leaderId,
    //前置项ID
    preconditions,
    //标准
    checkStandard;
    private Button headModify;
    private CheckBox cheAll;
    private WbsDialog selfDialog;
    private String strids;

    /**
     * 获取当前是第几个界面
     */
    @SuppressLint("ValidFragment")
    public PushFrgment(int pos) {
        this.pos = pos;
    }

    private SmartRefreshLayout refreshLayout;
    private String pushid;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.push, container, false);
        data = new ArrayList<>();
        mContext = getActivity();

        pushImgNonew = view.findViewById(R.id.push_img_nonew);
        pushImg = view.findViewById(R.id.push_img);
        pushImgText = view.findViewById(R.id.push_img_text);
        mContentRlv = (ListView) view.findViewById(R.id.lv_data);
        headModify = view.findViewById(R.id.head_modify);
        cheAll = view.findViewById(R.id.che_all);
        myAdapter = new PushfragmentAdapter(mContext);
        mContentRlv.setAdapter(myAdapter);
        refreshLayout = view.findViewById(R.id.SmartRefreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //checkbox修改状态
                cheAll.setChecked(false);
                //清除推送集合数据
                ArrayList<String> list = new ArrayList<String>();
                MissionpushActivity missionpush = (MissionpushActivity) mContext;
                missionpush.getAllPush(list, false);
                okgo();
                refreshlayout.finishRefresh(1200);

            }
        });
        headModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取责任人ID
                //创建一个要推送内容的集合，不能直接在数据源data集合中直接进行操作，否则会报异常
                deleSelect = new ArrayList<String>();
                //把选中的条目要推送的条目放在deleSelect这个集合中
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getChecked()) {
                        deleSelect.add(data.get(i).getId());
                    }
                }
                //查看集合是否有数据
                if (deleSelect.size() != 0 && data.size() != 0) {
                    strids = Dates.listToStrings(deleSelect);
                    //批量修改数据
                    if (deleSelect.size() < 2) {
                        ToastUtils.showShortToastCenter("至少选择2条要批量修改的任务");
                    } else {
                        get();
                    }
                } else if (data.size() == 0) {
                    ToastUtils.showShortToastCenter("没有选项数据");
                } else if (deleSelect.size() == 0) {
                    ToastUtils.showShortToastCenter("请选中要推送的数据");
                }

            }
        });

        pushImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dates.getDialog(getActivity(), "请求数据中");
                okgo();
                cheAll.setChecked(false);

            }
        });
        cheAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cheAll.isChecked()) {
                    ArrayList<String> list = new ArrayList<String>();
                    MissionpushActivity missionpush = (MissionpushActivity) mContext;
                    for (int i = 0; i < data.size(); i++) {
                        list.add(data.get(i).getId());
                    }
                    missionpush.getAllPush(list, true);
                } else {
                    ArrayList<String> list = new ArrayList<String>();
                    MissionpushActivity missionpush = (MissionpushActivity) mContext;
                    missionpush.getAllPush(list, false);
                }

            }
        });
        initlistener();
        mContentRlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                MissionpushActivity activity = (MissionpushActivity) mContext;
                String WbsID = activity.getId();
                Intent intent = new Intent(mContext, PushdialogActivity.class);
                //用户名
                intent.putExtra("user", data.get(position).getLeaderName());
                //前置条件
                intent.putExtra("requirements", data.get(position).getPreconditionsName());
                //内容
                intent.putExtra("content", data.get(position).getContent());
                //任务名称
                intent.putExtra("label", data.get(position).getLabel());
                //用户ID
                intent.putExtra("userId", data.get(position).getLeaderId());
                //wbsId
                intent.putExtra("WbsID", WbsID);
                //推送次数
                intent.putExtra("sendTimes", data.get(position).getSendTimes());
                //前置项ID
                intent.putExtra("preconditions", data.get(position).getPreconditions());
                //标准
                intent.putExtra("checkStandard", data.get(position).getCheckStandard());
                //任务ID
                pushid = data.get(position).getId();
                intent.putExtra("id", data.get(position).getId());
                startActivityForResult(intent, 1);
            }
        });
        okgo();
        return view;
    }

    private void initlistener() {
        /**
         * 全选复选框设置事件监听
         */
        cheAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //判断列表中是否有数据
                if (data.size() != 0) {
                    if (isChecked) {
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setChecked(true);
                        }
                        //通知适配器更新UI
                        myAdapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setChecked(false);
                        }
                        //通知适配器更新UI
                        myAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Dates.disDialog();
    }

    /**
     * 请求界面
     */
    void okgo() {
        OkGo.post(Requests.PUSHList)
                .params("wbsId", PushAdapter.Content)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (s.contains("data")) {
                            data.clear();
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < 1; i++) {
                                    JSONObject json = jsonArray.getJSONObject(pos);
                                    JSONArray jsonArr = json.getJSONArray("casePointsList");
                                    for (int j = 0; j < jsonArr.length(); j++) {
                                        JSONObject josn1 = jsonArr.getJSONObject(j);
                                        try {
                                            content = josn1.getString("content");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            content = "";
                                        }
                                        try {
                                            id = josn1.getString("id");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            id = "";
                                        }
                                        try {
                                            label = josn1.getString("label");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            label = "";
                                        }
                                        try {
                                            leaderName = josn1.getString("leaderName");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            leaderName = "";
                                        }
                                        try {
                                            sendTime = josn1.getString("sendTime");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            sendTime = "";
                                        }
                                        try {
                                            sendTimes = josn1.getString("sendTimes");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            sendTimes = "";
                                        }
                                        try {
                                            preconditionsName = josn1.getString("preconditionsName");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            preconditionsName = "";
                                        }
                                        try {
                                            leaderId = josn1.getString("leaderId");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            leaderId = "";
                                        }
                                        try {
                                            preconditions = josn1.getString("preconditions");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            preconditions = "";
                                        }
                                        try {
                                            checkStandard = josn1.getString("checkStandard");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            checkStandard = "";
                                        }

                                        data.add(new Push_item(content, id, label, preconditionsName,
                                                leaderName, sendTime, sendTimes, false, leaderId, preconditions, checkStandard));
                                    }
                                    if (data.size() != 0) {
                                        myAdapter.getData(data);
                                        pushImg.setVisibility(View.GONE);
                                        Dates.disDialog();
                                    } else {
                                        pushImg.setVisibility(View.VISIBLE);
                                        pushImgText.setText("数据为空，点击刷新");
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        pushImg.setVisibility(View.VISIBLE);
                        pushImgNonew.setBackgroundResource(R.mipmap.nonetwork);
                        pushImgText.setText("网络请求失败，点击刷新");
                    }
                });
    }



    /**
     * 批量修改负责人
     */
    public void get() {
        Intent intent = new Intent(mContext, ContactPeopleActivity.class);
        intent.putExtra("data", "newpush");
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            String user = data.getStringExtra("name");
            String userId = data.getStringExtra("userId");
            Dialog(userId, user);
        } else if (requestCode == 1 && resultCode == 5) {
            okgo();
            //清除推送集合数据
            ArrayList<String> list = new ArrayList<String>();
            MissionpushActivity missionpush = (MissionpushActivity) mContext;
            missionpush.getAllPush(list, false);
            //checkbox修改状态
            cheAll.setChecked(false);
        }
    }

    public void Dialog(final String str, String name) {
        selfDialog = new WbsDialog(mContext);
        selfDialog.setMessage("是否修改已选择中项的责任人为" + name);
        selfDialog.setYesOnclickListener("确定", new WbsDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                OkGo.<String>post(Requests.WBSCASEPOINT)
                        .params("id", strids)
                        .params("leaderId", str)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                try {
                                    JSONObject json = new JSONObject(s);
                                    int ret = json.getInt("ret");
                                    String msg = json.getString("msg");
                                    ToastUtils.showShortToastCenter(msg);
                                    if (ret == 0) {
                                        MissionpushActivity activity = (MissionpushActivity) mContext;
                                        ArrayList<String> list = new ArrayList<String>();
                                        activity.getAllPush(list, false);
                                        cheAll.setChecked(false);
                                        okgo();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                try {
                                    ToastUtils.showShortToastCenter(response.body().string());
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        });
                selfDialog.dismiss();
            }

        });
        selfDialog.setNoOnclickListener("取消", new WbsDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                selfDialog.dismiss();
            }
        });
        selfDialog.show();
    }

    @Override
    public void update() {
        cheAll.setChecked(false);
        //清除推送集合数据
        ArrayList<String> list = new ArrayList<String>();
        MissionpushActivity missionpush = (MissionpushActivity) mContext;
        missionpush.getAllPush(list, false);
        okgo();
    }
}