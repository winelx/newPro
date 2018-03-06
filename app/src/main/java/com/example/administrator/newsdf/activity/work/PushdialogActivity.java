package com.example.administrator.newsdf.activity.work;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.adapter.PopAdapterDialog;
import com.example.administrator.newsdf.camera.ToastUtils;

import java.util.ArrayList;

/**
 * description: 修改配置
 *
 * @author lx
 *         date: 2018/3/6 0006 上午 9:17
 *         update: 2018/3/6 0006
 *         version:
 */
public class PushdialogActivity extends Activity implements View.OnClickListener {

    private LinearLayout com_back;
    private PopupWindow popupWindow;
    private ArrayList<String> mData;
    private EditText    //任务内容
            pushDialog;
    private TextView
            //负责人
            pushDuty,
    //前置条件
    conditions,
    //推送内容
    pushContent;
    private RelativeLayout push_duty, push_conditions;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushdialog);
        com_back = findViewById(R.id.com_back);
        //任务内容
        pushDialog = findViewById(R.id.pushdialog_item_content);
        //负责人
        pushDuty = findViewById(R.id.push_item_duty);
        //前置条件
        conditions = findViewById(R.id.push_item_conditions);
        //推送内容
        pushContent = findViewById(R.id.push_item_content);
        findViewById(R.id.push_conditions).setOnClickListener(this);
        findViewById(R.id.push_duty).setOnClickListener(this);
        Intent intent = getIntent();
        try {

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        com_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.com_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast("保存");
            }
        });
    }

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
        PopAdapterDialog adapter = new PopAdapterDialog(mData, PushdialogActivity.this);
        lvList.setAdapter(adapter);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //前置条件
            case R.id.push_conditions:
                popWind();
                break;
            //负责人
            case R.id.push_duty:
                Intent intent = new Intent(PushdialogActivity.this, MemberActivity.class);
                intent.putExtra("data", "newpush");
                startActivityForResult(intent, 1);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            String name = data.getStringExtra("name");
            userid = data.getStringExtra("userId");
            pushDuty.setText(name);
        }
    }
}
