package com.example.administrator.newsdf.pzgc.special.programme.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.baselibrary.base.BaseActivity;

/**
 * @Author lx
 * @创建时间 2019/8/2 0002 15:15
 * @说明 方案审核界面
 **/

public class ProgrammeApprovalActivity extends BaseActivity implements View.OnClickListener {
    private TextView categoryItem, usernames;
    private LinearLayout approvaluser;
    private String status = "";
    private String type = "";
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programmeapproval);
        findViewById(R.id.com_back).setOnClickListener(this);
        title = findViewById(R.id.com_title);
        title.setText("选择下一节点审核人");
        findViewById(R.id.validation_status).setOnClickListener(this);
        categoryItem = findViewById(R.id.category_item);
        usernames = findViewById(R.id.usernames);
        approvaluser = findViewById(R.id.approvaluser);
        approvaluser.setOnClickListener(this);

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
                                approvaluser.setVisibility(View.VISIBLE);
                                status = "1";
                            }
                        })
                        .create();
                dialog.show();
                break;
            case R.id.approvaluser:
                startActivity(new Intent(this, ProgrammeAuditorActivity.class));
                break;
            case R.id.com_back:
                finish();
                break;
            default:
                break;
        }
    }


}