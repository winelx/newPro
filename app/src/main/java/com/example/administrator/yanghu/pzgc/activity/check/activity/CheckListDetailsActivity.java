package com.example.administrator.yanghu.pzgc.activity.check.activity;

import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import android.widget.TextView;


import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.callback.CheckTaskCallbackUtils;
import com.example.administrator.yanghu.pzgc.utils.Dates;
import com.example.administrator.yanghu.pzgc.utils.Enums;
import com.example.administrator.yanghu.pzgc.utils.ToastUtils;
import com.example.administrator.yanghu.pzgc.adapter.CheckNewAdapter;
import com.example.administrator.yanghu.pzgc.activity.check.CheckUtils;
import com.example.administrator.yanghu.pzgc.bean.chekitemList;
import com.example.administrator.yanghu.pzgc.view.DKDragView;
import com.example.baselibrary.base.BaseActivity;

import com.example.baselibrary.inface.NetworkCallback;
import com.example.baselibrary.inface.Onclicklitener;
import com.example.baselibrary.ui.activity.SignatureViewActivity;
import com.example.baselibrary.utils.Requests;
import com.example.baselibrary.view.BaseDialog;
import com.joanzapata.iconify.widget.IconTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * description:t检查单详情
 *
 * @author lx
 * date: 2018/8/15 0015 上午 11:38
 * update: 2018/8/15 0015
 * version:
 */
public class CheckListDetailsActivity extends BaseActivity implements View.OnClickListener {
    //控件
    private TextView datatime, categoryItem, checkNewNumber, checklistmeuntext, titleView,
            checkNewWebtext, checkUsername, checkNewOrgname, wbsName;
    private LinearLayout checkNewData, checkImport, checkCategory, checkNewAddNumber, checkNewDialog, check_content;
    private DrawerLayout drawerLayout;
    private GridView checklist, checklists;
    private EditText checkNewTasktitle, checkNewTemporarysite;
    private Button checkNewButton;
    private DKDragView dkDragView;
    //参数
    private String type, Id, taskId,sysMsgNoticeId;
    private CheckNewAdapter adapter, adapters;
    private ArrayList<chekitemList> mData, Preposition, Routine;
    private static CheckListDetailsActivity mContext;
    private IconTextView icontextviewone, icontextviewtwo;
    private LinearLayout checklistmeun;
    private int status;

    public static CheckListDetailsActivity getInstance() {
        return mContext;
    }

    ArrayList<View> viewlist = new ArrayList<>();
    ArrayList<View> tVisibility = new ArrayList<>();
    private CheckUtils checkUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_new_add);
        checkUtils = new CheckUtils();
        Intent intent = getIntent();
        Id = intent.getStringExtra("id");
        type = intent.getStringExtra("type");
        try {
            sysMsgNoticeId = intent.getStringExtra("sysMsgNoticeId");
        } catch (Exception e) {
            sysMsgNoticeId = null;
        }
        findbyid();
        initData();
        getCategory();
        //内业检查不需要检查部位
        if ("1".equals(type)) {
            checkNewDialog.setVisibility(View.VISIBLE);
        } else {
            checkNewDialog.setVisibility(View.GONE);
        }
    }

    private void findbyid() {
        //前置项
        check_content = findViewById(R.id.check_content);
        checkNewDialog = (LinearLayout) findViewById(R.id.check_new_dialog);
        //分数
        checkNewAddNumber = (LinearLayout) findViewById(R.id.check_new_add_number);
        checkNewAddNumber.setVisibility(View.VISIBLE);
        //wbs路径
        wbsName = (TextView) findViewById(R.id.check_wbspath);
        //指示箭头 类别和时间
        icontextviewone = (IconTextView) findViewById(R.id.IconTextViewone);
        icontextviewtwo = (IconTextView) findViewById(R.id.IconTextViewtwo);
        //添加入集合，根据操作进行隐藏
        tVisibility.add(icontextviewone);
        tVisibility.add(icontextviewtwo);
        //检查人
        checkUsername = (TextView) findViewById(R.id.check_username);
        //检查标段
        checkNewWebtext = (TextView) findViewById(R.id.check_new_webtext);
        //检查组织
        checkNewOrgname = (TextView) findViewById(R.id.check_new_orgname);
        //检查按钮
        checkNewButton = (Button) findViewById(R.id.check_new_buttons);
        checkNewButton.setOnClickListener(this);
        //分数
        checkNewNumber = (TextView) findViewById(R.id.check_new_number);
        //标题
        checkNewTasktitle = (EditText) findViewById(R.id.check_new_tasktitle);
        viewlist.add(checkNewTasktitle);
        //临时部位
        checkNewTemporarysite = (EditText) findViewById(R.id.check_new_temporarysite);
        viewlist.add(checkNewTemporarysite);
        //meun
        checklistmeuntext = (TextView) findViewById(R.id.checklistmeuntext);
        //检查标准类别
        categoryItem = (TextView) findViewById(R.id.category_item);
        //导入的wbs路径
        //wbs Tree
        checklist = (GridView) findViewById(R.id.checklist);
        checklists = (GridView) findViewById(R.id.checklist1);
        //检查标准类别
        checkCategory = (LinearLayout) findViewById(R.id.Check_category);
        viewlist.add(checkCategory);
        //抽屉控件
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //导入标段
        checkImport = (LinearLayout) findViewById(R.id.check_import);
        tVisibility.add(checkImport);
        //时间选择器选择时间后显示
        titleView = (TextView) findViewById(R.id.titleView);
        //具体时间
        datatime = (TextView) findViewById(R.id.check_new_data_tx);
        //现在时间（弹出时间选择器）
        checkNewData = (LinearLayout) findViewById(R.id.check_new_data);
        viewlist.add(checkNewData);
        //拖动控件
        dkDragView = (DKDragView) findViewById(R.id.float_suspension);
        dkDragView.setOnDragViewClickListener(new DKDragView.onDragViewClickListener() {
            @Override
            public void onClick() {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });
        //常规项
        checklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent(mContext, CheckitemActivity.class);
                int pos = Routine.get(position).getPos();
                intent2.putExtra("taskId", taskId);
                intent2.putExtra("number", pos);
                intent2.putExtra("position", pos);
                intent2.putExtra("size", mData.size());
                intent2.putExtra("success", "success");
                startActivity(intent2);
            }
        });
        //前置项
        checklists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent(mContext, CheckitemActivity.class);
                int pos = Preposition.get(position).getPos();
                intent2.putExtra("taskId", taskId);
                intent2.putExtra("number", pos);
                intent2.putExtra("position", pos);
                intent2.putExtra("size", mData.size());
                intent2.putExtra("success", "success");
                startActivity(intent2);
            }
        });
    }

    private void initData() {
        mData = new ArrayList<>();
        Preposition = new ArrayList<>();
        Routine = new ArrayList<>();
        mContext = this;
        //显示meun控件
        checklistmeuntext.setVisibility(View.VISIBLE);
        //关闭边缘滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        //侧滑栏关闭手势滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //展示侧拉界面后，背景透明度（当前透明度为完全透明）
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        checkNewData.setOnClickListener(this);
        checkImport.setOnClickListener(this);
        checkCategory.setOnClickListener(this);
        checklistmeun = (LinearLayout) findViewById(R.id.checklistmeun);
        checklistmeun.setOnClickListener(this);
        findViewById(R.id.checklistback).setOnClickListener(this);
        //设置不允许超过的边界（左上右下）
        dkDragView.setBoundary(0, 130, 0, 190);
        dkDragView.setOnDragViewClickListener(new DKDragView.onDragViewClickListener() {
            @Override
            public void onClick() {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });
        titleView.setText("检查详情");
        adapter = new CheckNewAdapter(mContext, Routine);
        checklist.setAdapter(adapter);
        //前置项
        adapters = new CheckNewAdapter(mContext, Preposition);
        checklists.setAdapter(adapters);
        checkNewButton.setVisibility(View.GONE);
        statusT();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checklistback:
                finish();
                break;
            case R.id.check_new_buttons:
                if ("确认并签名".equals(checkNewButton.getText().toString())) {
                    BaseDialog.confirmdialog(mContext, "是否确认签字", null, new Onclicklitener() {
                        @Override
                        public void confirm(String string) {
                            Dates.getDialogs(mContext, "提交数据中...");
                            CheckUtils.getautograph(Id, new NetworkCallback() {
                                @Override
                                public void onsuccess(Map<String, Object> map) {
                                    Dates.disDialog();
                                    ToastUtils.showShortToast("确认成功");
                                    checkFinish();
                                    try {
                                        CheckTaskCallbackUtils.CallBackMethod();
                                    } catch (Exception e) {
                                    }


                                }

                                @Override
                                public void onerror(String s) {
                                    Dates.disDialog();
                                    if (Enums.MYAUTOGRAPH.equals(s)) {
                                        BaseDialog.confirmmessagedialog(mContext,
                                                "确认签字失败",
                                                "您当前还未设置我的签名",
                                                "取消", "去设置签名", new Onclicklitener() {
                                                    @Override
                                                    public void confirm(String string) {
                                                        startActivity(new Intent(mContext, SignatureViewActivity.class));
                                                    }

                                                    @Override
                                                    public void cancel(String string) {

                                                    }
                                                });
                                    } else {
                                        ToastUtils.showShortToast(this.toString() + ":" + s);
                                    }
                                }
                            });
                        }

                        @Override
                        public void cancel(String string) {
                            //取消
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    private void statusT() {
        checklistmeuntext.setVisibility(View.GONE);
        checkNewButton.setText("开始检查");
        checkImport.setVisibility(View.VISIBLE);
        checkNewButton.setBackgroundResource(R.color.colorAccent);
        dkDragView.setVisibility(View.VISIBLE);
        for (int i = 0; i < tVisibility.size(); i++) {
            tVisibility.get(i).setVisibility(View.GONE);
        }
        for (int i = 0; i < viewlist.size(); i++) {
            viewlist.get(i).setClickable(false);
            viewlist.get(i).setEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            //选择类别的返回数据
            categoryItem.setText(data.getStringExtra("data"));
            //类别Id，保存时上传Id即可
//            categoryId = data.getStringExtra("id");
        } else if (requestCode == 2 && resultCode == 3) {
            //导入wbs的返回数据
            //选择的节点Id
            Id = data.getStringExtra("id");
            //保存wbsname的值，用在保存时判断是否修改，
            wbsName.setText(data.getStringExtra("title"));
            wbsName.setVisibility(View.VISIBLE);
            //查询标段自带的类别
            getCategory();
        }
    }

    public void getCategory() {
        CheckUtils.getCategory(Id,sysMsgNoticeId, new NetworkCallback() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                //具体时间
                datatime.setText(map.get("checkDate").toString());
                status=(int)map.get("status");
                //检查标准类别
                categoryItem.setText(map.get("wbsTaskTypeName").toString());
                //检查组织
                checkNewOrgname.setText(map.get("checkOrgName").toString());
                //检查部位wbs
                String wbspath = map.get("wbsMainName").toString();
                if (wbspath.length() > 0) {
                    wbsName.setText(wbspath);
                    wbsName.setVisibility(View.VISIBLE);
                }
                int iwork = Integer.parseInt(map.get("iwork").toString());
                //判断内业还是外业
                if (iwork == 1) {
                    checkNewDialog.setVisibility(View.VISIBLE);
                } else {
                    checkNewDialog.setVisibility(View.GONE);
                }

                checkNewTemporarysite.setText(map.get("wbsMainName").toString());
                //检查人
                checkUsername.setText(map.get("realname").toString());
                //检查标题
                String titikle = map.get("name").toString();
                if (titikle.length() > 0) {
                    checkNewTasktitle.setText(map.get("name").toString());
                } else {
                    checkNewTasktitle.setHint("未输入");
                }
                //所属标段
                checkNewWebtext.setText(map.get("orgName").toString());
                //检查部位
                String partDetails = map.get("partDetails").toString();
                if (partDetails.length() > 0) {
                    checkNewTemporarysite.setText(partDetails);
                    checkNewTemporarysite.setTextColor(Color.parseColor("#000000"));
                } else {
                    checkNewTemporarysite.setText("未输入");
                    checkNewTemporarysite.setTextColor(Color.parseColor("#888888"));
                }
                checkNewNumber.setText(map.get("score").toString());
                taskId = map.get("id").toString();
                checkItem();
            }

            @Override
            public void onerror(String s) {
                ToastUtils.showShortToast(s);
            }
        });
    }

    /**
     * 生成检查后的检查项列表
     */
    private void checkItem() {
        checkUtils.getcheckitemlist(taskId,sysMsgNoticeId, new NetworkCallback() {
            @Override
            public void onsuccess(Map<String, Object> map) {
                mData.clear();
                Preposition.clear();
                Routine.clear();
                mData.addAll((ArrayList<chekitemList>) map.get("list"));
                if (mData.size() > 0) {
                    for (int i = 0; i < mData.size(); i++) {
                        if (mData.get(i).getS_type().equals("3")) {
                            Preposition.add(mData.get(i));
                        } else {
                            Routine.add(mData.get(i));
                        }
                    }
                    //常规

                    adapter.getdate(Routine);
                    //前置
                    if (Preposition.size() != 0) {
                        adapters.getdate(Preposition);
                        check_content.setVisibility(View.VISIBLE);
                    } else {
                        check_content.setVisibility(View.GONE);
                    }
                    checkFinish();
                }
            }

            @Override
            public void onerror(String s) {
                ToastUtils.showShortToast(s);
            }
        });
    }

    public void checkFinish() {
        OkGo.get(Requests.CHECK_FINISH)
                .params("id", taskId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            int ret = jsonObject.getInt("ret");
                            if (ret == 0) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                String lean = jsonObject1.getString("finish");
                                if ("2".equals(lean)) {
                                    checkNewButton.setText("提交");
                                    checkNewButton.setVisibility(View.VISIBLE);
                                    checkNewButton.setBackgroundResource(R.color.Orange);
                                } else if ("3".equals(lean)) {
                                    checkNewButton.setText("确认并签名");
                                    checkNewButton.setVisibility(View.VISIBLE);
                                    checkNewButton.setBackgroundResource(R.color.Orange);
                                } else {
                                    //不显示
                                    if (status == 1) {
                                        checkNewButton.setVisibility(View.GONE);
                                        checklistmeuntext.setVisibility(View.GONE);
                                    } else {
                                        statusT();
                                    }
                                }
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

