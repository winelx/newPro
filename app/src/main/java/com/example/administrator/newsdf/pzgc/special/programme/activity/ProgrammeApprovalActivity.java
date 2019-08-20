package com.example.administrator.newsdf.pzgc.special.programme.activity;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.DialogUtils;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.utils.Api;
import com.example.baselibrary.utils.network.NetWork;
import com.example.baselibrary.utils.rx.LiveDataBus;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @Author lx
 * @创建时间 2019/8/2 0002 15:15
 * @说明 方案审核界面
 **/

public class ProgrammeApprovalActivity extends BaseActivity implements View.OnClickListener {
    private TextView categoryItem, usernames;
    private EditText replyDescription;

    private LinearLayout approvaluser;
    private String status = "";
    private String type = "";
    private TextView title;
    private String orgid, id;
    private ArrayList<Audio> mdata;
    private String isAssign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programmeapproval);
        Intent intent = getIntent();
        mdata = new ArrayList<>();
        orgid = intent.getStringExtra("orgid");
        isAssign = intent.getStringExtra("isAssign");
        id = intent.getStringExtra("id");
        findViewById(R.id.com_back).setOnClickListener(this);
        title = findViewById(R.id.com_title);
        title.setText("选择下一节点审核人");
        findViewById(R.id.validation_status).setOnClickListener(this);
        categoryItem = findViewById(R.id.category_item);
        findViewById(R.id.submit).setOnClickListener(this);
        replyDescription = findViewById(R.id.replyDescription);
        usernames = findViewById(R.id.usernames);
        approvaluser = findViewById(R.id.approvaluser);
        approvaluser.setOnClickListener(this);
        if ("0".equals(isAssign)) {
            approvaluser.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.validation_status:
                AlertDialog dialog = new AlertDialog.Builder(this).setTitle("是否验证通过")
                        .setNegativeButton("打回", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                status = "2";
                                categoryItem.setText("打回");
                                categoryItem.setTextColor(Color.parseColor("#FE0000"));
                                approvaluser.setVisibility(View.GONE);
                            }
                        }).setPositiveButton("通过", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //处理确认按钮的点击事件
                                categoryItem.setText("通过");
                                categoryItem.setTextColor(Color.parseColor("#28c26A"));
                                if ("0".equals(isAssign)) {
                                    approvaluser.setVisibility(View.GONE);
                                }else {
                                    approvaluser.setVisibility(View.VISIBLE);
                                }
                                status = "1";
                            }
                        })
                        .create();
                dialog.show();
                break;
            case R.id.approvaluser:
                Intent intent = new Intent(this, ProgrammeAuditorActivity.class);
                intent.putExtra("orgid", orgid);
                startActivityForResult(intent, 1);
                break;
            case R.id.com_back:
                finish();
                break;
            case R.id.submit:
                if (!status.isEmpty()) {
                    if ("0".equals(isAssign)) {
                        reqeust();
                    } else {
                        if (mdata.size() > 0) {
                            reqeust();
                        } else {
                            ToastUtils.showShortToast("还未选择下一节点审核人");
                        }
                    }

                } else {
                    ToastUtils.showShortToast("请确认是否审批通过");
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            mdata = (ArrayList<Audio>) data.getSerializableExtra("list");
            ArrayList<String> namelist = new ArrayList<>();
            for (int i = 0; i < mdata.size(); i++) {
                namelist.add(mdata.get(i).getName());
            }
            usernames.setText(Dates.listToString(namelist));
        }
    }

    public void reqeust() {
        Dates.getDialogs(this, "提交中...");
        ArrayList<String> idlsit = new ArrayList<>();
        for (int i = 0; i < mdata.size(); i++) {
            idlsit.add(mdata.get(i).getContent());
        }
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("isby", status);
        map.put("verificationOpinion", replyDescription.getText().toString());
        if ("0".equals(isAssign)) {
            map.put("assignPersonId", Dates.listToStrings(idlsit));
        }
        NetWork.postHttp(Api.SPECIALITEMPROJECTVERIFICATION, map, new NetWork.networkCallBack() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    JSONObject json = new JSONObject(s);
                    int ret = json.getInt("ret");
                    ToastUtils.showShortToast(json.getString("msg"));
                    if (ret == 0) {
                        //刷新详情界面
                        LiveDataBus.get().with("prodetails").setValue("");
                        //刷新列表界面
                        LiveDataBus.get().with("prolist").setValue("");
                        finish();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Dates.disDialog();

            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                Dates.disDialog();
            }
        });
    }
}
