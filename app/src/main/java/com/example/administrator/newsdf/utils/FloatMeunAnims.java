package com.example.administrator.newsdf.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.example.administrator.newsdf.R;

/**
 * Created by Administrator on 2018/6/25 0025.
 */

public class FloatMeunAnims {
    public void doclickt(final View view, final View view2, final  View view3) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator translation = ObjectAnimator.ofFloat(view2, "translationY", 0f, -160f);
        ObjectAnimator translation2 = ObjectAnimator.ofFloat(view, "translationY", -160f, -310f);
        translation.setDuration(300);
        translation2.setDuration(400);
        // 可以设置重复次数
        translation.setRepeatCount(0);
        translation2.setRepeatCount(0);
        //  可以设置插值器,使之按照一定的规律运动
        animatorSet.setInterpolator(new BounceInterpolator());
        // 开始方法
        animatorSet.play(translation2).with(translation);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            //动画开始
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                view3.setBackgroundResource(R.mipmap.diass_meun);
            }
        });
        animatorSet.start();
    }

    public void doclicktclose(final View view, final  View view2, final View view3) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator translation = ObjectAnimator.ofFloat(view2, "translationY", -160f, 0f);
        ObjectAnimator translation2 = ObjectAnimator.ofFloat(view, "translationY", -310f, -0f);
        translation.setDuration(300);
        translation2.setDuration(400);
        // 可以设置重复次数
        translation.setRepeatCount(0);
        translation2.setRepeatCount(0);
        // 开始方法
        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.play(translation).with(translation2);
        //为动画设置监听
        animatorSet.addListener(new AnimatorListenerAdapter() {
            //动画开始
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view3.setBackgroundResource(R.mipmap.floatbutton_meun);
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);
            }
        });
        animatorSet.start();
    }
}
