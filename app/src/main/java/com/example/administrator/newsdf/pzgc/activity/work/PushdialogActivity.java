package com.example.administrator.newsdf.pzgc.activity.work;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.PopAdapterDialog;
import com.example.administrator.newsdf.pzgc.bean.CasePointsBean;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.LogUtil;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.joanzapata.iconify.widget.IconTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * description: 修改配置
 *
 * @author lx
 *         date: 2018/3/6 0006 上午 9:17
 *         update: 2018/3/6 0006
 *         version:
 */
public class PushdialogActivity extends BaseActivity implements View.OnClickListener {

    private IconTextView com_back;
    private PopupWindow popupWindow;
    private ArrayList<CasePointsBean> mData;
    //任务内容
    private EditText pushDialog;
    //推送内容
    private EditText pushContent;
    private TextView
            //负责人
            pushDuty, sendtimes,
    //前置条件
    conditions;
    private TextView checkstandard;

    private String wbsId, content, user, userId, label, requirements,
            sendTimes, preconditions, id, Standard;
    private String taskcontent, pushcontent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushdialog);
        com_back = (IconTextView) findViewById(R.id.com_back);
        //标准
        checkstandard = (TextView) findViewById(R.id.pushdialog_checkStandard);
        //任务内容
        pushDialog = (EditText) findViewById(R.id.pushdialog_item_content);
        //负责人
        pushDuty = (TextView) findViewById(R.id.push_item_duty);
        //前置条件
        conditions = (TextView) findViewById(R.id.push_item_conditions);
        //推送内容
        pushContent = (EditText) findViewById(R.id.push_item_content);
        //推送次数
        sendtimes = (TextView) findViewById(R.id.pushdialog_sendnumber);

        findViewById(R.id.push_conditions).setOnClickListener(this);
        findViewById(R.id.push_duty).setOnClickListener(this);
        Intent intent = getIntent();
        try {
            //用户名
            user = intent.getStringExtra("user");
            //前置条件
            requirements = intent.getStringExtra("requirements");
            //内容
            content = intent.getStringExtra("content");
            //任务名称
            label = intent.getStringExtra("label");
            //用户ID
            userId = intent.getStringExtra("userId");
            //wbsID
            wbsId = intent.getStringExtra("WbsID");
            //推送次数
            sendTimes = intent.getStringExtra("sendTimes");
            //前置项ID
            preconditions = intent.getStringExtra("preconditions");
            //标准
            Standard = intent.getStringExtra("checkStandard");
            //任务ID
            id = intent.getStringExtra("id");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        pushDialog.setText(label);
        pushContent.setText(content);
        pushDuty.setText(user);
        if (Standard.isEmpty()) {
            checkstandard.setText("无");
        } else {
            checkstandard.setText(Standard);
        }

        if (requirements.length() != 0) {
            conditions.setText(requirements);
        } else {
            conditions.setText("无");
        }
        sendtimes.setText(sendTimes);
        com_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.com_button).setOnClickListener(this);
        //存放前置选项的集合
        mData = new ArrayList<>();
        mData.add(new CasePointsBean("","无","无"));
        conditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWind();
            }
        });
        //获取前置项
        CasePoints();
    }

    /**
     * 请求前置项
     */
    private void CasePoints() {
        OkGo.<String>post(Requests.CASDPOINTD)
                .params("wbsId", wbsId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response esponse) {
                        if (s.indexOf("data") != -1) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    String id;
                                    try {
                                        id = json.getString("id");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        id = "";
                                    }
                                    String label;
                                    try {
                                        label = json.getString("label");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        label = "";
                                    }
                                    String content;
                                    try {
                                        content = json.getString("content");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        content = "";
                                    }
                                    mData.add(new CasePointsBean(id, label, content));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.showShortToast("没有数据！");
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                    }
                });
    }

    /**
     * 展示前置项的弹窗
     */
    public void popWind() {
        View view = getLayoutInflater().inflate(R.layout.pop_push_duty, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
        //设置背景，
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        //显示(靠中间)
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        ListView lvList = view.findViewById(R.id.list_item);
        LinearLayout pop_dismiss = view.findViewById(R.id.pop_dismiss);
        RelativeLayout back = view.findViewById(R.id.node_pop_rel);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        PopAdapterDialog adapter = new PopAdapterDialog(mData, PushdialogActivity.this, preconditions);
        lvList.setAdapter(adapter);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //前置项名
                requirements = mData.get(position).getLabel();
                //前置项ID
                preconditions = mData.get(position).getID();
                //填数据
                conditions.setText(requirements);
                LogUtil.i("preconditions", preconditions);
                popupWindow.dismiss();
            }
        });
        pop_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //前置条件
            case R.id.push_conditions:
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                popWind();
                break;
            //负责人
            case R.id.push_duty:
                Intent intent = new Intent(PushdialogActivity.this, ContactPeopleActivity.class);
                intent.putExtra("data", "newpush");
                startActivityForResult(intent, 1);
                break;
            //保存数据
            case R.id.com_button:
                taskcontent = pushDialog.getText().toString();
                pushcontent = pushContent.getText().toString();
                getSubmit();
                break;
            default:
                break;
        }
    }

    /**
     * activcity回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //拿到责任人信息
        if (requestCode == 1 && resultCode == 2) {
            user = data.getStringExtra("name");
            userId = data.getStringExtra("userId");
            pushDuty.setText(user);
        }
    }

    /**
     * 保存数据
     */
    public void getSubmit() {
        OkGo.<String>post(Requests.WBSCASEPOINTs)
                //任务ID
                .params("id", id)
                //任务名称
                .params("label", taskcontent)
                //责任人ID
                .params("leaderId", userId)
                //推送内容
                .params("content", pushContent.getText().toString())
                //前置项ID
                .params("preconditions", preconditions)
                //前置项名称
                .params("preconditionsName", requirements)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                ToastUtils.showShortToast("修改成功");
                                Intent newpush = new Intent();
                                //回传数据到主Activity
                                setResult(5, newpush);
                                finish(); //此方法后才能返回主Activity
                            } else {
                                ToastUtils.showShortToast(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


    }

}
