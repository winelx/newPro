package com.example.administrator.newsdf.pzgc.activity.notice.activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;;
import com.example.administrator.newsdf.R;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.utils.rx.RxBus;

import androidx.navigation.fragment.NavHostFragment;
import io.reactivex.functions.Consumer;

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
