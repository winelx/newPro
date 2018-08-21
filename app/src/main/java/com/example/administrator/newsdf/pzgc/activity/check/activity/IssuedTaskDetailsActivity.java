package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.camera.ToastUtils;
import com.example.administrator.newsdf.pzgc.Adapter.IssuedTaskDetailsAdapter;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.bean.CheckDetailsContent;
import com.example.administrator.newsdf.pzgc.bean.detailsBean;
import com.example.administrator.newsdf.pzgc.utils.Requests;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.newsdf.R.id.check_details_submit;

/**
 * description: 下发任务详情
 *
 * @author lx
 *         date: 2018/8/8 0008 下午 2:28
 *         update: 2018/8/8 0008
 *         version:
 */
public class IssuedTaskDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView detailsRejected;
    private TextView infaceWbsPath, titleView, checklistmeuntext, checkDetailsSubmit,
            checkDetailsBlue, checkDetailsOrgin;
    private LinearLayout checkDetailsStatus, checkDetailsEditor;
    private IssuedTaskDetailsAdapter mAdater;
    private ArrayList<Object> mData;
    private Context mContext;
    private String status = "未回复";
    private ArrayList<CheckDetailsContent> list;
    private String id = "";
    private ArrayList<detailsBean> detailsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issued_task_details);
        Intent intent = getIntent();
        mData = new ArrayList<>();

        list = new ArrayList<>();
        mContext = IssuedTaskDetailsActivity.this;
        checkDetailsEditor = (LinearLayout) findViewById(R.id.check_details_editor);
        checkDetailsStatus = (LinearLayout) findViewById(R.id.check_details_status);
        checkDetailsOrgin = (TextView) findViewById(R.id.check_details_orgin);
        checkDetailsBlue = (TextView) findViewById(R.id.check_details_blue);
        checkDetailsSubmit = (TextView) findViewById(check_details_submit);
        detailsRejected = (RecyclerView) findViewById(R.id.details_rejected);
        detailsRejected.setLayoutManager(new LinearLayoutManager(detailsRejected.getContext(), LinearLayoutManager.VERTICAL, false));
        infaceWbsPath = (TextView) findViewById(R.id.inface_wbs_path);
        titleView = (TextView) findViewById(R.id.titleView);
        checklistmeuntext = (TextView) findViewById(R.id.checklistmeuntext);
        checklistmeuntext.setVisibility(View.VISIBLE);
        checklistmeuntext.setTextSize(10);
        checklistmeuntext.setText("处理记录");
        ArrayList<String> path = new ArrayList<>();

        mAdater = new IssuedTaskDetailsAdapter(mData, mContext);
        detailsRejected.setAdapter(mAdater);
        checkDetailsSubmit.setOnClickListener(this);
        checkDetailsBlue.setOnClickListener(this);
        checklistmeuntext.setOnClickListener(this);
        checkDetailsOrgin.setOnClickListener(this);
        findViewById(R.id.checklistback).setOnClickListener(this);
        setstatus();
        try {
            id = intent.getStringExtra("id");
            if (id != null) {
                getData();
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checklistmeuntext:
                ToastUtils.showLongToast("记录");
                break;
            case R.id.checklistback:
                finish();
                break;
            case check_details_submit:
                ToastUtils.showLongToast("验证");
                startActivity(new Intent(mContext, CheckValidationActivity.class));
                break;
            case R.id.check_details_blue:
                String str = checkDetailsBlue.getText().toString();
                ToastUtils.showLongToast(str);
                checkDetailsSubmit.setVisibility(View.GONE);
                switch (str) {
                    case "指派":
                        startActivity(new Intent(mContext, CheckuserActivity.class));
                        break;
                    case "编辑":
                        startActivity(new Intent(mContext, CheckReplyActivity.class));
                        break;
                    default:
                        break;
                }
                break;
            case R.id.check_details_orgin:
                String check = checkDetailsOrgin.getText().toString();
                ToastUtils.showLongToast(check);
                switch (check) {
                    case "回复":
                        startActivity(new Intent(mContext, CheckReplyActivity.class));
                        break;
                    case "提交":

                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    public void setstatus() {
        switch (status) {
            case "未回复":
                checkDetailsSubmit.setVisibility(View.GONE);
                checkDetailsEditor.setVisibility(View.VISIBLE);
                if (list.size() > 0) {
                    checkDetailsBlue.setText("编辑");
                    checkDetailsOrgin.setText("提交");
                } else {
                    checkDetailsBlue.setText("指派");
                    checkDetailsOrgin.setText("回复");
                }
                break;
            case "未验证":
                checkDetailsEditor.setVisibility(View.GONE);
                checkDetailsSubmit.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    public void getData() {
        OkGo.post(Requests.getNoticeDateApp)
                .params("noticeId", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            mData.clear();
                            detailsBean = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(s);
                            JSONObject json = jsonObject.getJSONObject("data");
                            String wbspath = json.getString("rectificationPartName");
                            String sendPersonName = json.getString("sendPersonName");
                            String sendDate = json.getString("sendDate");
                            //所属标段
                            String rectificationOrgName = json.getString("rectificationOrgName");
                            //违反标准
                            String standardDelName = json.getString("standardDelName");
                            //整改事由
                            String checkplan = json.getString("checkplan");
                            //检查组织
                            String checkOrgName = json.getString("checkOrgName");
                            //附件
                            JSONArray attachmentList = json.getJSONArray("attachmentList");
                            ArrayList<Audio> achmentList = new ArrayList<Audio>();
                            if (attachmentList.length() > 0) {
                                for (int i = 0; i < attachmentList.length(); i++) {
                                    achmentList.add(new Audio(Requests.networks + json.getString("filepath"), json.getString("id")));
                                }
                            }
                            //整改负责人
                            String rectificationPersonName = json.getString("rectificationPesonName");
                            //整改最后时间
                            String rectificationDate = json.getString("rectificationDate");
                            //通知状态
                            String status = json.getString("status");
                            detailsBean.add(new detailsBean(wbspath, sendPersonName, sendDate, rectificationOrgName,
                                    standardDelName, checkplan, checkOrgName, achmentList, rectificationPersonName, rectificationDate, status));

                            mData.add(detailsBean);
                            mAdater.getData(mData);
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
