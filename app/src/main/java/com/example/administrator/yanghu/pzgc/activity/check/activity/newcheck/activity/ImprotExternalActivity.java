package com.example.administrator.yanghu.pzgc.activity.check.activity.newcheck.activity;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.administrator.yanghu.R;
import com.example.administrator.yanghu.pzgc.activity.check.activity.newcheck.fragment.ImprotExternalFragment;
import com.example.administrator.yanghu.pzgc.activity.check.activity.newcheck.fragment.MessageExternalFragment;
import com.example.baselibrary.adapter.PshooseFragAdapte;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.utils.rx.LiveDataBus;
import com.example.baselibrary.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明：导入外业检查项
 * 创建时间： 2020/7/13 0013 17:53
 *
 * @author winelx
 */
public class ImprotExternalActivity extends BaseActivity {
    private NoScrollViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signatureview);
        viewPager = findViewById(R.id.noscroll);
        //构造适配器
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new ImprotExternalFragment());
        fragments.add(new MessageExternalFragment());
        PshooseFragAdapte adapter = new PshooseFragAdapte(getSupportFragmentManager(), fragments);
        //设定适配器
        viewPager.setAdapter(adapter);
        LiveDataBus.get().with("ex_viewpager", Integer.class).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                viewPager.setCurrentItem(integer);
            }
        });
    }

}
