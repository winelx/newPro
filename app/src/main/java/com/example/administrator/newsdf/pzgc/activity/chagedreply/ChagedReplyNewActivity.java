package com.example.administrator.newsdf.pzgc.activity.chagedreply;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.adapter.ChangedReplyNewAdapter;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.utils.ChagedreplyUtils;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.utils.bean.ChagedReply;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.utils.bean.Chagereplydetails;
import com.example.administrator.newsdf.pzgc.activity.chagedreply.utils.bean.RelationList;
import com.example.administrator.newsdf.pzgc.callback.Networkinterface;
import com.example.administrator.newsdf.pzgc.callback.NetworkinterfaceCallbackUtils;
import com.example.administrator.newsdf.pzgc.callback.TaskCallbackUtils;
import com.example.baselibrary.view.BaseActivity;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.SPUtils;
import com.example.administrator.newsdf.pzgc.utils.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lx
 * 版本：1.0
 * 创建日期：{2019/2/15 0015}
 * 描述：新增整改回复单
 */
public class ChagedReplyNewActivity extends BaseActivity implements View.OnClickListener, Networkinterface {
    private RecyclerView recycler;
    private ChangedReplyNewAdapter adapter;
    private Context mContext;
    private ArrayList<Chagereplydetails> list;
    private TextView comButton, number, chageditem, replycommit, comTitle;
    private LinearLayout replyImportProblem, toolbarMenu;
    private TextView chagedOrgname, chagedOrganizeText, sendorgname,
            sendusername, senddate, rectificationpersonname;
    private String orgId, orgname, id, motionNode, noticeId, replyPerson;
    private RelationList relation;
    private boolean lean = true;
    private boolean status = false;
    private int count;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chagedreply_new);
        addActivity(this);
        mContext = this;
        list = new ArrayList<>();
        final Intent intent = getIntent();
        try {
            id = intent.getStringExtra("id");
            lean = intent.getBooleanExtra("status", false);
        } catch (Exception e) {
        }
        NetworkinterfaceCallbackUtils.setCallBack(this);
        orgname = SPUtils.getString(mContext, "username", null);
        comTitle = (TextView) findViewById(R.id.com_title);
        comTitle.setText("新增整改回复");
        comButton = (TextView) findViewById(R.id.com_button);
        chagedOrgname = (TextView) findViewById(R.id.chaged_orgname);
        chagedOrgname.setText(orgname);
        chagedOrganizeText = (TextView) findViewById(R.id.chaged_organize_text);
        replycommit = (TextView) findViewById(R.id.replycommit);
        replycommit.setOnClickListener(this);
        sendorgname = (TextView) findViewById(R.id.sendorgname);
        sendusername = (TextView) findViewById(R.id.sendusername);
        chageditem = (TextView) findViewById(R.id.chageditem);
        senddate = (TextView) findViewById(R.id.senddate);
        number = (TextView) findViewById(R.id.number);
        rectificationpersonname = (TextView) findViewById(R.id.rectificationpersonname);
        orgId = SPUtils.getString(mContext, "orgId", null);
        findViewById(R.id.com_back).setOnClickListener(this);
        replyImportProblem = (LinearLayout) findViewById(R.id.reply_import_problem);
        replyImportProblem.setOnClickListener(this);
        findViewById(R.id.chaged_organize_lin).setOnClickListener(this);
        toolbarMenu = (LinearLayout) findViewById(R.id.toolbar_menu);
        toolbarMenu.setOnClickListener(this);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new SimpleDividerItemDecoration(mContext));
        adapter = new ChangedReplyNewAdapter(R.layout.adapter_item_chagedreyply_new, list);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent1 = new Intent(mContext, ChagedReplyBillActivity.class);
                intent1.putExtra("status", false);
                //是否回复
                intent1.putExtra("isReply", list.get(position).getIsReply());
                //问题项Id
                intent1.putExtra("replyDelId", list.get(position).getId());
                //回复单id
                intent1.putExtra("replyId", list.get(position).getReplyId());
                startActivity(intent1);
            }
        });
        if (lean) {
            comButton.setText("编辑");
            replyImportProblem.setVisibility(View.VISIBLE);
            chageditem.setVisibility(View.VISIBLE);
            request();
        } else {
            comButton.setText("保存");
            chageditem.setVisibility(View.GONE);
            replyImportProblem.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.com_back:
                finish();
                break;
            case R.id.replycommit:
                if ("编辑".equals(comButton.getText().toString())) {
                    if (list.size() > 0) {
                        if (count == 0) {
                            commit();
                        } else {
                            ToastUtils.showsnackbar(number, "还有问题项未回复");
                        }
                    } else {
                        ToastUtils.showShortToastCenter("并未导入通知单问题项");
                    }
                } else {
                    ToastUtils.showsnackbar(number, "请先保存回复单数据");
                }
                break;
            case R.id.reply_import_problem:
                /*导入问题项*/
                if ("编辑".equals(comButton.getText().toString())) {
                    Intent intent = new Intent(mContext, ChagedReplyImportActivity.class);
                    intent.putExtra("noticeId", noticeId);
                    intent.putExtra("id", id);
                    intent.putExtra("string", chagedOrganizeText.getText().toString());
                    startActivityForResult(intent, 0);
                } else {
                    ToastUtils.showShortToast("当前不是编辑状态");
                }
                break;
            case R.id.chaged_organize_lin:
                //关联整改通知单
                if ("保存".equals(comButton.getText().toString())) {
                    startActivityForResult(new Intent(mContext, ChagedReplyRelationActivity.class), 1);
                } else {
                    ToastUtils.showShortToast("当前状态不可点击");
                }
                break;
            case R.id.toolbar_menu:
                if ("保存".equals(comButton.getText().toString())) {
                    if (!chagedOrganizeText.getText().toString().isEmpty()) {
                        Dates.getDialogs(this, "提交数据中...");
                        Dates.backgroundAlpha(0.5f, this);
                        save();
                        toolbarMenu.setClickable(false);
                    } else {
                        ToastUtils.showShortToastCenter("请先选择关联整改通知单");
                    }
                } else {
                    comButton.setText("保存");
                    replycommit.setBackgroundColor(Color.parseColor("#888888"));
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
            //关联整改通知单
            relation = (RelationList) data.getSerializableExtra("content");
            //整改通知单编号
            chagedOrganizeText.setText(relation.getCode());
            //下发组织
            sendorgname.setText(relation.getSendOrgName());
            //下发人
            sendusername.setText(relation.getSendUserName());
            //下发日期
            senddate.setText(relation.getSendDate());
            //整改负责人
            rectificationpersonname.setText(relation.getRectificationPersonName());
            motionNode = relation.getMotionNode() + "";
            noticeId = relation.getId();
            replyPerson = relation.getAcceptPerson();
        } else if (requestCode == 0 && resultCode == 0) {
            //导入问题项
        }
    }

    /*保存*/
    private void save() {
        Map<String, String> map = new HashMap<>();
        if (id != null) {
            map.put("id", id);
        }
        if (replyPerson != null) {
            map.put("replyPerson", replyPerson);
        }
        //组织id
        map.put("rectificationOrgid", orgId);
        //运动节点
        map.put("motionNode", motionNode);
        //回复单Id
        map.put("noticeId", noticeId);
        ChagedreplyUtils.createReplyForm(map, new ChagedreplyUtils.MapCallBack() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                if ((String) map.get("id") != null) {
                    id = (String) map.get("id");
                }
                String code = (String) map.get("code");
                if (code != null && !TextUtils.isEmpty(code)) {
                    number.setText(code);
                }
                comButton.setText("编辑");
                replyImportProblem.setVisibility(View.VISIBLE);
                chageditem.setVisibility(View.VISIBLE);
                Dates.disDialog();
                toolbarMenu.setClickable(true);
                Dates.backgroundAlpha(1.0f, ChagedReplyNewActivity.this);
                ToastUtils.showsnackbar(comButton, "保存成功");
                if (list.size() > 0) {
                    if (count == 0) {
                        replycommit.setBackgroundColor(Color.parseColor("#f88c37"));
                    } else {
                        replycommit.setBackgroundColor(Color.parseColor("#888888"));
                    }
                }
            }

            @Override
            public void onerror(String string) {
                ToastUtils.showsnackbar(comButton, string);
                toolbarMenu.setClickable(true);
                Dates.disDialog();
                Dates.backgroundAlpha(1.0f, ChagedReplyNewActivity.this);
            }
        });
    }

    /*网络请求*/
    private void request() {
        ChagedreplyUtils.getReplyFormOfSaveStatus(id, new ChagedreplyUtils.MapCallBack() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                ChagedReply relation = (ChagedReply) map.get("bean");
                //整改通知单编号
                chagedOrganizeText.setText(relation.getNoticeCode());
                //整改回复单编号
                number.setText(relation.getCode());
                //下发组织
                sendorgname.setText(relation.getSorgName());
                //下发人
                sendusername.setText(relation.getSendUserName());
                //下发日期
                senddate.setText(relation.getSendDate());
                //整改组织
                chagedOrgname.setText(relation.getRorgName());
                //整改负责人
                rectificationpersonname.setText(relation.getRuserName());
                id = relation.getId();
                motionNode = relation.getMotionNode() + "";
                noticeId = relation.getNotice_id();
                list.clear();
                list.addAll((ArrayList<Chagereplydetails>) map.get("list"));
                adapter.setNewData(list);
                count = (int) map.get("count");
                //判断是否导入问题项
                if (list.size() > 0) {
                    if (count == 0) {
                        replycommit.setBackgroundColor(Color.parseColor("#f88c37"));
                    } else {
                        replycommit.setBackgroundColor(Color.parseColor("#888888"));
                    }
                }else {
                    replycommit.setBackgroundColor(Color.parseColor("#888888"));
                }
            }

            @Override
            public void onerror(String string) {
                ToastUtils.showsnackbar(comButton, string);
            }
        });
    }

    /*提交*/
    private void commit() {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                .setMessage("确定提交回复单？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    //添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        ChagedreplyUtils.submitReplyData(id, motionNode, new ChagedreplyUtils.ObjectCallBacks() {
                            @Override
                            public void onsuccess(String string) {
                                //刷新列表
                                try {
                                    TaskCallbackUtils.CallBackMethod();
                                } catch (Exception e) {
                                }
                                finish();
                            }

                            @Override
                            public void onerror(String string) {
                                ToastUtils.showsnackbar(comButton, string);
                            }
                        });

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
        alertDialog.show();
    }

    //接口回调刷新
    @Override
    public void onsuccess(Map<String, Object> map) {
        String str = (String) map.get("reply");
        if ("reply".equals(str)) {
            request();
        }
    }

    /*接口方法*/
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
