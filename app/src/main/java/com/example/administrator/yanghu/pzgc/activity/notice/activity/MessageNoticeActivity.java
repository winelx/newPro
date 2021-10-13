package com.example.administrator.yanghu.pzgc.activity.notice.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import androidx.navigation.fragment.NavHostFragment;

import com.example.administrator.yanghu.R;
import com.example.baselibrary.base.BaseActivity;

;

/**
 * author： lx
 * 创建时间： 2019/5/29 0029 9:56
 * 说明：  通知公告
 **/
public class MessageNoticeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagenotice);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frag_nav_main);
        return NavHostFragment.findNavController(fragment).navigateUp();
    }

    public void sethorizontal() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}
