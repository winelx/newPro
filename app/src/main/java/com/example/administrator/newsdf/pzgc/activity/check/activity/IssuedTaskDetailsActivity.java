package com.example.administrator.newsdf.pzgc.activity.check.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.administrator.newsdf.pzgc.bean.CheckDetailsContents;
import com.example.administrator.newsdf.pzgc.bean.CheckDetailsTop;
import com.example.administrator.newsdf.pzgc.callback.MoreTaskCallback;
import com.example.administrator.newsdf.pzgc.callback.MoreTaskCallbackUtils;
import com.example.administrator.newsdf.pzgc.callback.TaskCallbackUtils;
import com.example.administrator.newsdf.pzgc.utils.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
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
public class IssuedTaskDetailsActivity extends BaseActivity implements View.OnClickListener, MoreTaskCallback {
    private RecyclerView detailsRejected;
    private TextView infaceWbsPath, titleView, checklistmeuntext, checkDetailsSubmit,
            checkDetailsBlue, checkDetailsOrgin;
    private LinearLayout checkDetailsStatus, checkDetailsEditor;
    private IssuedTaskDetailsAdapter mAdater;
    private ArrayList<Object> mData;
    private ArrayList<CheckDetailsContent> mData2;
    private ArrayList<CheckDetailsContents> mData3;

    private Context mContext;
    private TextView inface_wbs_path;
    private String status = "未回复";
    private ArrayList<CheckDetailsContent> list;
    private Boolean isDeal;
    private String id = "", orgId, userName, userId, sdealId, motionNode, repyId, repycontent,
            verificationId, resultId, verstatus;
    ArrayList<Audio> replyArray;
    ArrayList<String> historyname = new ArrayList<>();
    ArrayList<String> historydata = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issued_task_details);
        Intent intent = getIntent();
        mData = new ArrayList<>();
        mData2 = new ArrayList<>();
        mData3 = new ArrayList<>();
        MoreTaskCallbackUtils.setCallBack(this);
        list = new ArrayList<>();
        mContext = IssuedTaskDetailsActivity.this;
        TextView titlle = (TextView) findViewById(R.id.titleView);
        titlle.setText(intent.getStringExtra("title"));
        inface_wbs_path = (TextView) findViewById(R.id.inface_wbs_path);
        checkDetailsEditor = (LinearLayout) findViewById(R.id.check_details_editor);
        checkDetailsStatus = (LinearLayout) findViewById(R.id.check_details_status);
        checkDetailsOrgin = (TextView) findViewById(R.id.check_details_orgin);
        checkDetailsBlue = (TextView) findViewById(R.id.check_details_blue);
        //验证
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
        checkDetailsOrgin.setOnClickListener(this);
        findViewById(R.id.checklistback).setOnClickListener(this);
        findViewById(R.id.checklistmeun).setOnClickListener(this);
        checkDetailsSubmit.setVisibility(View.GONE);
        checkDetailsEditor.setVisibility(View.GONE);
        try {
            id = intent.getStringExtra("id");
            sdealId = intent.getStringExtra("sdealId");
            isDeal = intent.getBooleanExtra("isDeal", true);
            verificationId = intent.getStringExtra("verificationId");
            if (id != null) {
                getData();
            }
        } catch (Exception e) {

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checklistmeun:
                Intent intent4 = new Intent(mContext, CheckHistoryActivity.class);
                intent4.putExtra("msg", historyname);
                intent4.putExtra("data", historydata);
                startActivity(intent4);
                break;
            case R.id.checklistback:
                finish();
                break;
            case check_details_submit:
                Intent intent1 = new Intent(mContext, CheckValidationActivity.class);
                intent1.putExtra("repyId", repyId);
                intent1.putExtra("noticeId", id);
                startActivity(intent1);

                break;
            case R.id.check_details_blue:
                String str = checkDetailsBlue.getText().toString();
                checkDetailsSubmit.setVisibility(View.GONE);
                switch (str) {
                    case "指派":
                        Intent intent = new Intent(mContext, CheckuserActivity.class);
                        intent.putExtra("orgId", orgId);
                        startActivityForResult(intent, 1);
                        break;
                    case "编辑":
                        ArrayList<String> list = new ArrayList<>();
                        ArrayList<String> ids = new ArrayList<>();
                        if (replyArray.size() > 0) {
                            for (int i = 0; i < replyArray.size(); i++) {
                                list.add(replyArray.get(i).getName());
                                ids.add(replyArray.get(i).getContent());
                            }
                        }
                        if (motionNode.equals("3")) {
                            Intent intent3 = new Intent(mContext, CheckValidationActivity.class);
                            intent3.putExtra("repyId", repyId);
                            intent3.putExtra("noticeId", id);
                            intent3.putExtra("id", resultId);
                            intent3.putExtra("sdealId", sdealId);
                            intent3.putExtra("list", list);
                            intent3.putExtra("status", verstatus);
                            intent3.putExtra("repycontent", repycontent);
                            intent3.putExtra("ids", ids);
                            startActivity(intent3);
                        } else {
                            Intent intent2 = new Intent(mContext, CheckReplyActivity.class);
                            intent2.putExtra("repyId", repyId);
                            intent2.putExtra("noticeId", id);
                            intent2.putExtra("sdealId", sdealId);
                            intent2.putExtra("list", list);
                            intent2.putExtra("repycontent", repycontent);
                            intent2.putExtra("ids", ids);
                            startActivity(intent2);
                        }
                        break;
                    default:
                        break;
                }
                break;
            case R.id.check_details_orgin:
                String check = checkDetailsOrgin.getText().toString();
                switch (check) {
                    case "回复":
                        Intent intent = new Intent(mContext, CheckReplyActivity.class);
                        intent.putExtra("noticeId", id);
                        intent.putExtra("sdealId", sdealId);
                        startActivity(intent);
                        break;
                    case "提交":
                        if (motionNode.equals("3")) {
                            verificationSubmit();
                        } else {
                            replySubmit();
                        }
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            userName = data.getStringExtra("name");
            userId = data.getStringExtra("id");
            saveAssignPersonApp();
        }
    }

    String motionCount1, motionCount2;

    public void getData() {
        OkGo.post(Requests.getNoticeDateApp)
                .params("noticeId", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            mData.clear();
                            mData2.clear();
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.getInt("ret") == 0) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                JSONObject json = data.getJSONObject("notice");
                                JSONArray replyList = data.getJSONArray("replyList");
                                JSONArray verificationList = data.getJSONArray("verificationList");
                                JSONArray histRecord;
                                try {
                                    histRecord = json.getJSONArray("histRecord");
                                } catch (JSONException e) {
                                    histRecord = new JSONArray();
                                }
                                if (histRecord.length() > 0) {
                                    historyname.clear();
                                    historydata.clear();
                                    for (int i = 0; i < histRecord.length(); i++) {
                                        JSONObject js = histRecord.getJSONObject(i);
                                        historyname.add(js.getString("msg"));
                                        historydata.add(js.getString("date"));
                                    }
                                }
                                orgId = json.getString("rectificationOrgid");
                                String wbspath = json.getString("standardTypeName");
                                inface_wbs_path.setText(json.getString("rectificationPartName"));
                                String sendPersonName = json.getString("sendPersonName");
                                String sendDate = json.getString("sendDate");
                                motionNode = json.getString("motionNode");
                                //整改事由
                                String rectificationOrgName = json.getString("rectificationReason");
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
                                        JSONObject jsonObject1 = attachmentList.getJSONObject(i);
                                        achmentList.add(new Audio(Requests.networks + jsonObject1.getString("filepath"), jsonObject1.getString("id")));
                                    }
                                }
                                //整改负责人
                                String rectificationPersonName;
                                try {
                                    rectificationPersonName = json.getString("rectificationPersonName");
                                } catch (JSONException e) {
                                    rectificationPersonName = "";
                                }
                                //整改最后时间
                                String rectificationDate = json.getString("rectificationDate");
                                //通知状态
                                String status = json.getString("status");
                                mData.add(new CheckDetailsTop(wbspath, sendPersonName, sendDate, rectificationOrgName,
                                        standardDelName, checkplan, checkOrgName, achmentList, rectificationPersonName, rectificationDate, status));
                                if (replyList.length() > 0) {
                                    replyArray = new ArrayList<Audio>();
                                    for (int i = 0; i <= replyList.length(); i++) {
                                        //回复结果
                                        if (i < replyList.length()) {
                                            JSONObject jsonObject1 = replyList.getJSONObject(i);
                                            String replyPersonName = jsonObject1.getString("replyPersonName");
                                            String replyDate = jsonObject1.getString("replyDate");
                                            if (i == 0) {
                                                repyId = jsonObject1.getString("id");
                                                repycontent = jsonObject1.getString("replyDescription");
                                            }
                                            String rectificationReason;
                                            motionCount1 = jsonObject1.getString("motionCount");
                                            try {
                                                rectificationReason = jsonObject1.getString("replyDescription");
                                            } catch (JSONException e) {
                                                rectificationReason = "";
                                            }
                                            ArrayList<Audio> replyimgArray = new ArrayList<Audio>();
                                            JSONArray replyimgList = jsonObject1.getJSONArray("attachmentList");

                                            if (replyimgList.length() > 0) {
                                                for (int j = 0; j < replyimgList.length(); j++) {
                                                    JSONObject replyimg = replyimgList.getJSONObject(j);
                                                    replyimgArray.add(new Audio(Requests.networks + replyimg.getString("filepath"), replyimg.getString("id")));

                                                }
                                            }
                                            if (i == 0) {
                                                for (int j = 0; j < replyimgArray.size(); j++) {
                                                    replyArray.add(replyimgArray.get(j));
                                                }
                                            }
                                            mData2.add(new CheckDetailsContent(replyPersonName, rectificationReason, replyDate, motionCount1, replyimgArray));
                                        }
                                        //验证结果
                                        if (i < verificationList.length()) {
                                            JSONObject jsonObject2 = verificationList.getJSONObject(i);
                                            String replyPersonName2 = jsonObject2.getString("verificationPersonName");
                                            String replyDate2 = jsonObject2.getString("updateDate");
                                            String isby = jsonObject2.getString("isby");
                                            motionCount2 = jsonObject2.getString("motionCount");
                                            if (i == 0) {
                                                if (motionCount1.equals(motionCount2)) {
                                                    repycontent = jsonObject2.getString("verificationOpinion");
                                                    resultId = jsonObject2.getString("id");
                                                    verstatus = jsonObject2.getString("isby");
                                                }
                                            }
                                            String repycontent2 = jsonObject2.getString("verificationOpinion");
                                            ArrayList<Audio> replyimgArray4 = new ArrayList<Audio>();
                                            JSONArray jsonArray = jsonObject2.getJSONArray("attachmentList");
                                            if (jsonArray.length() > 0) {
                                                for (int j = 0; j < jsonArray.length(); j++) {
                                                    JSONObject replyimg2 = jsonArray.getJSONObject(j);
                                                    replyimgArray4.add(new Audio(Requests.networks + replyimg2.getString("filepath"), replyimg2.getString("id")));
                                                }
                                            }
                                            if (i == 0) {
                                                if (motionCount1.equals(motionCount2)) {
                                                    replyArray.clear();
                                                    for (int j = 0; j < replyimgArray4.size(); j++) {
                                                        replyArray.add(replyimgArray4.get(j));
                                                    }
                                                }
                                            }
                                            mData3.add(new CheckDetailsContents(replyPersonName2, repycontent2, replyDate2, motionCount2, isby, replyimgArray4));
                                        }

                                    }
                                }
                                for (int i = 0; i < mData2.size(); i++) {
                                    int counts = i + 1;
                                    for (int j = 0; j < mData2.size(); j++) {
                                        int count = Integer.parseInt(mData2.get(j).getCount());
                                        if (counts == count) {
                                            mData.add(mData2.get(j));
                                            break;
                                        }
                                    }
                                    for (int j = 0; j < mData3.size(); j++) {
                                        int count = Integer.parseInt(mData3.get(j).getCount());
                                        if (counts == count) {
                                            mData.add(mData3.get(j));
                                            break;
                                        }
                                    }
                                }
                                mAdater.getData(mData);
                                //   0:未回复；1:回复未提交；2未验证；3:验证未提交；5完成)(保存：null,提交 0
                                if (!isDeal) {
                                    if (motionNode.equals("0")) {
                                        checkDetailsSubmit.setVisibility(View.GONE);
                                        checkDetailsEditor.setVisibility(View.VISIBLE);
                                        checkDetailsBlue.setText("指派");
                                        checkDetailsOrgin.setText("回复");
                                    } else if (motionNode.equals("1")) {
                                        checkDetailsSubmit.setVisibility(View.GONE);
                                        checkDetailsEditor.setVisibility(View.VISIBLE);
                                        checkDetailsBlue.setText("编辑");
                                        checkDetailsOrgin.setText("提交");
                                    } else if (motionNode.equals("2")) {
                                        checkDetailsEditor.setVisibility(View.GONE);
                                        checkDetailsSubmit.setVisibility(View.VISIBLE);
                                    } else if (motionNode.equals("3")) {
                                        checkDetailsSubmit.setVisibility(View.GONE);
                                        checkDetailsEditor.setVisibility(View.VISIBLE);
                                        //跳转验证界面
                                        checkDetailsBlue.setText("编辑");
                                        checkDetailsOrgin.setText("提交");
                                    } else if (motionNode.equals("5")) {
                                        checkDetailsSubmit.setVisibility(View.GONE);
                                        checkDetailsEditor.setVisibility(View.GONE);
                                    }
                                } else {
                                    checkDetailsSubmit.setVisibility(View.GONE);
                                    checkDetailsEditor.setVisibility(View.GONE);
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
                    }
                });
    }

//    //    noticeId = json.getString("noticeId");
////    sdealId = json.getString("sdealId");
//    acceptPerson  指派人id
//    acceptPersonName 指派人名称
//    assignDate 指派日期
//    remarks 备注
//    id 上一节点处理人id                                   对应列表的sdealId
//    noticeId 通知单id

    public void saveAssignPersonApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("")
                .setMessage("确定将任务指派给" + userName + "吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        setuser();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.show();
    }

    //整改回复成功关闭界面
    @Override
    public void newData() {
        finish();
    }

    public void replySubmit() {
        OkGo.post(Requests.replySubmit)
                .params("replyId", repyId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.getInt("ret") == 0) {
                                TaskCallbackUtils.CallBackMethod();
                                finish();
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

    public void verificationSubmit() {
        OkGo.post(Requests.verificationSubmit)
                .params("verificationId", resultId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.getInt("ret") == 0) {
                                TaskCallbackUtils.CallBackMethod();
                                finish();
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

    public void setuser() {
        OkGo.post(Requests.saveAssignPersonApp)
                //指派人ID
                .params("acceptPerson", userId)
                //指派人名称
                .params("acceptPersonName", userId)
                .params("assignDate", Dates.getDay())
                .params("id", sdealId)
                .params("noticeId", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                isDeal = true;
                                TaskCallbackUtils.CallBackMethod();
                                getData();
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

