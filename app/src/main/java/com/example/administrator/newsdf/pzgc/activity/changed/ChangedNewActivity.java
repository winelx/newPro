package com.example.administrator.newsdf.pzgc.activity.changed;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.changed.adapter.ChangedNewAdapter;
import com.example.administrator.newsdf.pzgc.activity.check.activity.CheckuserActivity;
import com.example.administrator.newsdf.pzgc.activity.mine.OrganizationaActivity;
import com.example.administrator.newsdf.pzgc.bean.ChagedNoticeDetails;
import com.example.administrator.newsdf.pzgc.bean.ChagedNoticeDetailslsit;
import com.example.administrator.newsdf.pzgc.callback.Networkinterface;
import com.example.administrator.newsdf.pzgc.callback.NetworkinterfaceCallbackUtils;
import com.example.administrator.newsdf.pzgc.callback.TaskCallbackUtils;
import com.example.baselibrary.view.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;
import com.example.administrator.newsdf.pzgc.utils.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/1/30 0030}
 * 描述：修改后的新增整改通知单
 * {@link }
 */
public class ChangedNewActivity extends BaseActivity implements View.OnClickListener, Networkinterface {
    private TextView chagedNumber, comTitle, chagedReleasePeople,
            chagedReleaseOrg, chagedOrganizeText, chagedHeadText, chagednumber;
    private RecyclerView recycler;
    private List<ChagedNoticeDetailslsit> list;
    private Context mContext;
    private LinearLayout problemItemLin, problemMenuLin;
    private ChangedNewAdapter adapter;
    private TextView comButton;
    //界面状态，用来显示是否展示导入添加问题
    public boolean status = true;
    public static final String KEEP = "保存";
    private String orgName, orgId, id = "";
    private String userName, userId;
    private ChagedUtils chagedUtils;
    private String chagedReleaseid, sendOrgid, dealId, motionNode;
    private TextView chagedReleaseProblem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaged_new);
        addActivity(this);
        mContext = this;
        Intent intent = getIntent();
        status = intent.getBooleanExtra("status", false);
        chagedUtils = new ChagedUtils();
        NetworkinterfaceCallbackUtils.setCallBack(this);
        list = new ArrayList<>();
        problemItemLin = (LinearLayout) findViewById(R.id.problem_item_lin);
        problemItemLin.setVisibility(View.GONE);
        problemMenuLin = (LinearLayout) findViewById(R.id.problem_menu_lin);
        //整改通知单编号
        chagednumber = (TextView) findViewById(R.id.chagednumber);
        //整改组织
        chagedOrganizeText = (TextView) findViewById(R.id.chaged_organize_text);
        //整改负责人
        chagedHeadText = (TextView) findViewById(R.id.chaged_head_text);
        //下发人
        chagedReleasePeople = (TextView) findViewById(R.id.chaged_release_people);
        chagedReleasePeople.setText(SPUtils.getString(mContext, "staffName", ""));
        //下发组织
        chagedReleaseOrg = (TextView) findViewById(R.id.chaged_release_org);
        chagedReleaseOrg.setText(SPUtils.getString(mContext, "username", ""));
        //标题
        comTitle = (TextView) findViewById(R.id.com_title);
        comTitle.setText("新增整改");
        //菜单
        comButton = (TextView) findViewById(R.id.com_button);
        comButton.setText("保存");
        comButton.setOnClickListener(this);
        //返回
        findViewById(R.id.com_back).setOnClickListener(this);
        //下发
        chagedReleaseProblem = (TextView) findViewById(R.id.chaged_release_problem);
        chagedReleaseProblem.setOnClickListener(this);
        //导入问题项
        findViewById(R.id.chaged_import_problem).setOnClickListener(this);
        //添加问题项
        findViewById(R.id.chaged_add_problem).setOnClickListener(this);
        //整改负责人
        findViewById(R.id.chaged_head_lin).setOnClickListener(this);
        //整改组织
        findViewById(R.id.chaged_organize_lin).setOnClickListener(this);
        //问题项
        recycler = (RecyclerView) findViewById(R.id.recycler);
        //设置展示style
        recycler.setLayoutManager(new LinearLayoutManager(this));
        //添加分割线
        recycler.addItemDecoration(new SimpleDividerItemDecoration(mContext));
        adapter = new ChangedNewAdapter(R.layout.adapter_item_chaged_new, list);
        recycler.setAdapter(adapter);
        /*问题项点击事件*/
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, ChagedProblemitemActivity.class);
                intent.putExtra("orgname", chagedOrganizeText.getText().toString());
                intent.putExtra("orgid", orgId);
                intent.putExtra("status", false);
                intent.putExtra("iwork",list.get(position).getIwork());
                //问题项Id
                intent.putExtra("noticeDelId", list.get(position).getId());
                //整改单Id
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        if (status) {
            id = intent.getStringExtra("id");
            comButton.setText("编辑");
            problemItemLin.setVisibility(View.VISIBLE);
            request();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                //后退
                finish();
                break;
            case R.id.chaged_release_problem:
                //下发
                if (status) {
                    if (list.size() > 0) {
                        AlertDialog alertDialog2 = new AlertDialog.Builder(mContext)
                                .setTitle("")
                                .setMessage("是否下发整改通知单")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    //添加"Yes"按钮
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        /*删除按钮*/
                                        send();
                                        dialogInterface.dismiss();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    //添加取消
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .create();
                        alertDialog2.show();
                    } else {
                        ToastUtils.showsnackbar(comButton, "还未添加问题项");
                    }
                } else {
                    ToastUtils.showsnackbar(comButton, "不是保存状态无法操作");
                }
                break;
            case R.id.chaged_import_problem:
                //导入
                Intent intent = new Intent(mContext, ChagedImportitemActivity.class);
                intent.putExtra("orgid", orgId);
                intent.putExtra("noticeId", id);
                startActivityForResult(intent, 1);
                break;
            case R.id.chaged_add_problem:
                //添加整改问题项
                Intent intent2 = new Intent(mContext, ChagedProblemitemActivity.class);
                intent2.putExtra("orgname", chagedOrganizeText.getText().toString());
                intent2.putExtra("orgid", orgId);
                intent2.putExtra("id", id);
                intent2.putExtra("status", true);
                startActivity(intent2);
                break;
            case R.id.chaged_head_lin:
                //选择联系人
                if (!status) {
                    if (orgId == null) {
                        ToastUtils.showsnackbar(comTitle, "请先选择整改组织");
                    } else {
                        Intent intent1 = new Intent(mContext, CheckuserActivity.class);
                        intent1.putExtra("orgId", orgId);
                        startActivityForResult(intent1, 5);
                    }

                } else {
                    ToastUtils.showsnackbar(comTitle, "不是编辑状态无法操作");
                }
                break;
            case R.id.chaged_organize_lin:
                //整改组织
                if (!status) {
                    Intent intent12 = new Intent(mContext, OrganizationaActivity.class);
                    intent12.putExtra("title", "整改组织");
                    intent12.putExtra("data", "Rectifi");
                    startActivityForResult(intent12, 3);
                } else {
                    ToastUtils.showsnackbar(comTitle, "不是编辑状态无法操作");
                }
                break;
            case R.id.com_button:
                /*菜单*/
                if (KEEP.equals(comButton.getText().toString())) {
                    //保存
                    if (orgId == null) {
                        ToastUtils.showsnackbar(comTitle, "还未选择整改组织");
                    } else {
                        if (userId == null) {
                            ToastUtils.showsnackbar(comTitle, "还未选择整改负责人");
                        } else {
                            Dates.getDialog(this, "提交数据中....");
                            save();
                        }
                    }
                } else {
                    //编辑
                    comButton.setText(KEEP);
                    status = false;
                    chagedReleaseProblem.setBackgroundColor(Color.parseColor("#888888"));
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == 2) {
            //选择负责人
            userId = data.getStringExtra("userid");
            userName = data.getStringExtra("name");
            chagedHeadText.setText(userName);
        } else if (requestCode == 3 && resultCode == 2) {
            //整改组织
            orgId = data.getStringExtra("id");
            orgName = data.getStringExtra("name");
            chagedOrganizeText.setText(orgName);
        } else if (requestCode == 1 && resultCode == 1) {
            request();
        }
    }

    /*保存*/
    public void save() {
        chagedUtils.setsavenoticeform(id, orgId, userId, SPUtils.getString(mContext, "orgId", null), new ChagedUtils.CallBack() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                Dates.disDialog();
                status = true;
                problemItemLin.setVisibility(View.VISIBLE);
                comButton.setText("编辑");
                if (list.size() > 0) {
                    chagedReleaseProblem.setBackgroundColor(Color.parseColor("#f88c37"));
                }else {
                    chagedReleaseProblem.setBackgroundColor(Color.parseColor("#888888"));
                }
                try {
                    TaskCallbackUtils.CallBackMethod();
                } catch (Exception e) {
                }
                if((String) map.get("id")!=null){
                    id = (String) map.get("id");
                }
                if (map.get("code").toString()!=null){
                    chagednumber.setText(map.get("code").toString());
                }
            }

            @Override
            public void onerror(String string) {
                Dates.disDialog();
                ToastUtils.showsnackbar(comTitle, string);
            }
        });
    }

    /*下发*/
    public void send() {
        if ("编辑".equals(comButton.getText().toString())) {
            chagedUtils.setsenddata(id, dealId, motionNode, 1, new ChagedUtils.CallBacks() {
                @Override
                public void onsuccess(String string) {
                    ToastUtils.showShortToastCenter(string);
                    try {
                        TaskCallbackUtils.CallBackMethod();
                    } catch (Exception e) {
                    }
                    finish();
                }
                @Override
                public void onerror(String str) {
                    Snackbar.make(comButton, str, Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void request() {
        chagedUtils.getNoticeFormMainInfo(id, new ChagedUtils.CallBack() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                ChagedNoticeDetails item = (ChagedNoticeDetails) map.get("bean");
                //处理节点Id，在getNoticeFormMainInfo接口有返回
                dealId = item.getDealId();
                //当前页面所属操作节点，在getNoticeFormMainInfo接口有返回
                motionNode = item.getMotionNode() + "";
                //id
                id = item.getId();
                //整改通知单编号
                chagednumber.setText(item.getCode());
                //整改组织
                chagedOrganizeText.setText(item.getRorgName());
                //整改组织Id
                orgId = item.getRectificationOrgid();
                //下发组织
                chagedReleaseOrg.setText(item.getSorgName());
                //下发组织Id
                sendOrgid = item.getSendOrgid();
                //整改负责人
                chagedHeadText.setText(item.getRuserName());
                //整改负责人id
                userId = item.getRectificationPerson();
                list.clear();
                list.addAll((ArrayList<ChagedNoticeDetailslsit>) map.get("list"));
                adapter.setNewData(list);
                if ("编辑".equals(comButton.getText().toString())) {
                    if (list.size() > 0) {
                        chagedReleaseProblem.setBackgroundColor(Color.parseColor("#f88c37"));
                    } else {
                        chagedReleaseProblem.setBackgroundColor(Color.parseColor("#888888"));
                    }
                }
            }

            @Override
            public void onerror(String str) {

            }
        });
    }

    @Override
    public void onsuccess(Map<String, Object> map) {
        String string = (String) map.get("problem");
        if ("problem".equals(string)) {
            request();
        }
    }
}
