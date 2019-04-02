package com.example.administrator.newsdf.pzgc.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.App;
import com.example.baselibrary.view.BaseActivity;

import cn.jpush.android.api.JPushInterface;

import static com.example.administrator.newsdf.R.id.setting_new_message;

/**
 * 系统设置
 */
public class SettingActivity extends BaseActivity {
    private Switch
            //接受新消息
            settingNewMessage,
    //显示消息详情
    settingMessageDetails,
    //声音
    settingVoice,
    //震动
    settingVibration,
    //勿扰
    settingNotDisturb;
    private boolean notIsChecked = true;

    private LinearLayout
            //勿扰
            setting_disturb,
    //新消息
    setting_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        addActivity(this);
        initView();
        settingNotDisturb.setChecked(true);
        findViewById(R.id.com_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //勿扰
        settingNotDisturb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setting_disturb.setVisibility(View.GONE);
                } else {
                    setting_disturb.setVisibility(View.VISIBLE);

                }
            }
        });
        //接受
        settingNewMessage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setting_message.setVisibility(View.VISIBLE);
                    JPushInterface.resumePush(App.getInstance());
                } else {
                    setting_message.setVisibility(View.GONE);
                    JPushInterface.stopPush(App.getInstance());
                }
            }
        });
        //显示
        settingMessageDetails.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });
        //声音
        settingVoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });
        //震动
        settingVibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });
    }

    private void initView() {
        //接受新消息
        settingNewMessage = (Switch) findViewById(setting_new_message);
        //显示消息内容
        settingMessageDetails = (Switch) findViewById(R.id.setting_message_details);
        //声音
        settingVoice = (Switch) findViewById(R.id.setting_voice);
        // 震动
        settingVibration = (Switch) findViewById(R.id.setting_vibration);
        //勿扰
        settingNotDisturb = (Switch) findViewById(R.id.setting_not_disturb);
        //勿扰模式
        setting_disturb = (LinearLayout) findViewById(R.id.setting_disturb);
        //接受消息
        setting_message = (LinearLayout) findViewById(R.id.setting_message);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
