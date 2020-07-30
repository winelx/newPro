package com.example.administrator.newsdf.pzgc.activity.check.activity.record.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.Audio;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.ToastUtils;
import com.example.administrator.newsdf.pzgc.utils.Utils;
import com.example.baselibrary.base.BaseActivity;

import java.util.ArrayList;

/**
 * 说明：新增进度检查单界面
 * 创建时间： 2020/7/30 0030 10:13
 *
 * @author winelx
 */
public class NewRecordCheckActiviy extends BaseActivity implements View.OnClickListener {
    private TextView title, projectName, checkData, check_import_user, check_import_unuser, com_button;
    private EditText check_department, check_duty, problem, explain;
    private TextView check_user, check_unuser, delete_unuser, delete_user, commit;
    private LinearLayout toolbar_menu;
    private ImageView arrow_right;
    private RecyclerView recyclerview;
    private ArrayList<String> userlist;
    private ArrayList<String> unuserlist;
    private String orgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recordcheck);
        Intent intent = getIntent();
        orgId = intent.getStringExtra("orgid");
        findViewById(R.id.com_back).setOnClickListener(this);
        title = findViewById(R.id.com_title);
        title.setText("新增记录");
        com_button = findViewById(R.id.com_button);
        com_button.setText("保存");
        toolbar_menu = findViewById(R.id.toolbar_menu);
        toolbar_menu.setOnClickListener(this);
        commit = findViewById(R.id.commit);
        commit.setOnClickListener(this);
        projectName = findViewById(R.id.project_name);
        delete_user = findViewById(R.id.delete_user);
        delete_user.setOnClickListener(this);
        delete_unuser = findViewById(R.id.delete_unuser);
        delete_unuser.setOnClickListener(this);
        checkData = findViewById(R.id.check_data);
        check_import_user = findViewById(R.id.check_import_user);
        check_import_user.setOnClickListener(this);
        check_import_unuser = findViewById(R.id.check_import_unuser);
        check_import_unuser.setOnClickListener(this);
        check_department = findViewById(R.id.check_department);
        check_duty = findViewById(R.id.check_duty);
        problem = findViewById(R.id.problem);
        explain = findViewById(R.id.explain);
        check_user = findViewById(R.id.check_user);
        check_unuser = findViewById(R.id.check_unuser);
        arrow_right = findViewById(R.id.arrow_right);
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new GridLayoutManager(mContext, 4));
        setEnabled(true);
        userlist = new ArrayList<>();
        unuserlist = new ArrayList<>();
        check_user.setText(Dates.listToStrings(userlist));
        check_unuser.setText(Dates.listToStrings(unuserlist));
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.toolbar_menu) {
            if (com_button.getText().toString().equals("保存")) {
                com_button.setText("编辑");
                setEnabled(false);
            } else {
                com_button.setText("保存");
                setEnabled(true);
            }
        } else if (i == R.id.commit) {
            ToastUtils.showShortToast("点击");
        } else if (i == R.id.check_import_user) {
            Intent intent = new Intent(mContext, SuperviseCheckRecordUerListActivity.class);
            intent.putExtra("type", true);
            intent.putExtra("content", check_user.getText().toString());
            intent.putExtra("orgId", orgId);
            startActivityForResult(intent, 101);
        } else if (i == R.id.check_import_unuser) {
            Intent intent = new Intent(mContext, SuperviseCheckRecordUerListActivity.class);
            intent.putExtra("orgId", false);
            intent.putExtra("orgId", orgId);
            intent.putExtra("content", check_unuser.getText().toString());
            startActivityForResult(intent, 102);
        } else if (i == R.id.com_back) {
            finish();
        }else if (i==R.id.delete_user){
            check_user.setText("");
        }else if (i==R.id.delete_unuser){
            check_unuser.setText("");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == 101) {
            check_user.setText(data.getStringExtra("content"));
        } else if (requestCode == 102 && resultCode == 101) {
            check_unuser.setText(data.getStringExtra("content"));
        }
    }

    public void setEnabled(boolean lea) {
        projectName.setEnabled(lea);
        checkData.setEnabled(lea);
        commit.setEnabled(!lea);
        check_department.setEnabled(lea);
        check_duty.setEnabled(lea);
        problem.setEnabled(lea);
        explain.setEnabled(lea);
        check_import_user.setVisibility(lea ? View.VISIBLE : View.GONE);
        check_import_unuser.setVisibility(lea ? View.VISIBLE : View.GONE);
        arrow_right.setVisibility(lea ? View.VISIBLE : View.GONE);
        delete_user.setVisibility(lea ? View.VISIBLE : View.GONE);
        delete_unuser.setVisibility(lea ? View.VISIBLE : View.GONE);
        if (lea) {
            problem.setHint("请输入");
            explain.setHint("请输入");
            check_duty.setHint("请输入");
            check_department.setHint("请输入");
            projectName.setHint("请输入");
            checkData.setHint("请选择");
            commit.setBackgroundResource(R.color.gray);
        } else {
            problem.setHint("");
            explain.setHint("");
            check_duty.setHint("");
            check_department.setHint("");
            projectName.setHint("");
            checkData.setHint("");
            commit.setBackgroundResource(R.color.colorAccent);
        }

    }


}
